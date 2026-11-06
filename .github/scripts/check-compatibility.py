"""Compare existing signatures and execute a client compiled against Tuplite 1.0.0."""
import hashlib
import os
from pathlib import Path
import re
import subprocess
import sys
import urllib.request
from zipfile import ZipFile
import xml.etree.ElementTree as ET

candidate = Path(sys.argv[1]).resolve()
work = Path('build/compatibility').resolve()
work.mkdir(parents=True, exist_ok=True)
ns = {'m': 'http://maven.apache.org/POM/4.0.0'}
dependencies = ET.parse(candidate.with_suffix('.pom')).findall('.//m:dependency', ns)
assert len(dependencies) == 1
assert [dependencies[0].find('m:' + key, ns).text for key in ('groupId', 'artifactId', 'version', 'scope')] == [
    'io.github.lambig', 'functional-interface-extension', '1.1.3', 'compile']

def download(artifact, version):
    name = f'{artifact}-{version}.jar'
    url = f'https://repo.maven.apache.org/maven2/io/github/lambig/{artifact}/{version}/{name}'
    target = work / name
    target.write_bytes(urllib.request.urlopen(url, timeout=60).read())
    assert hashlib.sha1(target.read_bytes()).hexdigest() == urllib.request.urlopen(url + '.sha1', timeout=60).read().decode().strip()
    return target

baseline = download('Tuplite', '1.0.0')
assert hashlib.sha256(baseline.read_bytes()).hexdigest() == '036ad25b8d48fa86a4600001e75db39b67b43361d0689e73e3721c2dad9476db'
old_fie = download('functional-interface-extension', '1.0.0')
new_fie = download('functional-interface-extension', '1.1.3')
java_home = Path(os.environ['JAVA_HOME'])
runtime = Path(os.environ.get('TEST_JAVA_HOME', os.environ['JAVA_HOME'])) / 'bin/java'

def classes(jar):
    with ZipFile(jar) as archive:
        names = sorted(name for name in archive.namelist() if name.endswith('.class'))
        for name in names:
            assert int.from_bytes(archive.read(name)[6:8], 'big') == 52, name
    return [name[:-6].replace('/', '.') for name in names]

types = classes(baseline)
assert types == classes(candidate)
builders = ('Tuple2$Tuple2_1to2', 'Tuple3$Tuple3_with1', 'Tuple3$Tuple3_with1and2')
for name in types:
    def api(jar):
        return subprocess.check_output([str(java_home / 'bin/javap'), '-public', '-s', '-classpath', str(jar), name], text=True)
    old, new = api(baseline), api(candidate)
    (work / (name + '-baseline-api.txt')).write_text(old, encoding='utf-8')
    (work / (name + '-candidate-api.txt')).write_text(new, encoding='utf-8')
    def normalize(value):
        # Method declaration order is not part of the API. Only the three formerly inaccessible builders gain public visibility.
        if any(name.endswith(builder) for builder in builders):
            value = value.replace('public interface ', 'interface ')
        return sorted(line.strip() for line in value.splitlines() if not line.startswith('Compiled from'))
    assert normalize(old) == normalize(new), name + ': unexpected signature change'

client = work / 'client'
client.mkdir(exist_ok=True)
source = work / 'OldClient.java'
source.write_text('''import io.github.lambig.tuplite._2.Tuple2;
import io.github.lambig.tuplite._3.Tuple3;
public class OldClient {
  public static void main(String[] args) {
    Tuple2<Integer, Integer> pair = new Tuple2<>(250, 4);
    Tuple3<Integer, Integer, Integer> triple = new Tuple3<>(250, 4, 100);
    if (pair.map((a,b) -> a*b) != 1000 || triple.map((a,b,c) -> a*b-c) != 900
        || !pair.is(a -> a > 0, b -> b > 0)
        || !triple.is(a -> a > 0, b -> b > 0, c -> c >= 0)) throw new AssertionError();
    System.out.println("Old compiled client: OK");
  }
}''', encoding='utf-8')
subprocess.run([str(java_home / 'bin/javac'), '--release', '8', '-classpath', os.pathsep.join(map(str, (baseline, old_fie))), '-d', str(client), str(source)], check=True)
for library, fie in ((baseline, old_fie), (baseline, new_fie), (candidate, new_fie)):
    subprocess.run([str(runtime), '-cp', os.pathsep.join(map(str, (client, library, fie))), 'OldClient'], check=True)

# Demonstrate the external-package access defect against the published baseline, and its correction in the candidate.
builder_source = work / 'BuilderClient.java'
builder_source.write_text('''import io.github.lambig.tuplite._2.Tuple2;
import io.github.lambig.tuplite._3.Tuple3;
public class BuilderClient {
  public static void main(String[] args) {
    if (Tuple2.with1(250).and2(4)._2() != 4
        || Tuple3.with1(250)._2(4).and3(100)._3() != 100
        || Tuple3.with1(250)._2and3(4,100)._3() != 100
        || Tuple3.with1_2(250,4).and3(100)._3() != 100) throw new AssertionError();
    System.out.println("External builder client: OK");
  }
}''', encoding='utf-8')
for library, should_compile in ((baseline, False), (candidate, True)):
    result = subprocess.run([str(java_home / 'bin/javac'), '--release', '8', '-classpath', os.pathsep.join(map(str, (library, new_fie))), '-d', str(client), str(builder_source)], capture_output=True, text=True)
    (work / (library.stem + '-builder-compile.log')).write_text(result.stderr, encoding='utf-8')
    assert (result.returncode == 0) == should_compile
    if not should_compile:
        assert 'inaccessible class or interface' in result.stderr, result.stderr
subprocess.run([str(runtime), '-cp', os.pathsep.join(map(str, (client, candidate, new_fie))), 'BuilderClient'], check=True)
print(f'Existing signatures preserved: {len(types)} classes; builder visibility widened in three interfaces; bytecode=52')
print(f'Baseline SHA256: {hashlib.sha256(baseline.read_bytes()).hexdigest()}')
print('Old client passed with old and updated FIE; candidate POM exports only FIE 1.1.3')
