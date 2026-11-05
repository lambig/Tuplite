"""Compile and execute the introductory program directly from the README."""
import os
from pathlib import Path
import re
import subprocess
import sys

source = next(block for block in re.findall(r'```java\n(.*?)```', Path('README.md').read_text(encoding='utf-8'), re.S) if 'class PriceExample' in block)
work = Path('build/readme-example').resolve()
work.mkdir(parents=True, exist_ok=True)
(work / 'PriceExample.java').write_text(source, encoding='utf-8')
classpath = sys.argv[1]
subprocess.run([str(Path(os.environ['JAVA_HOME']) / 'bin/javac'), '--release', '8', '-encoding', 'UTF-8', '-cp', classpath, '-d', str(work), str(work / 'PriceExample.java')], check=True)
runtime = Path(os.environ.get('TEST_JAVA_HOME', os.environ['JAVA_HOME'])) / 'bin/java'
result = subprocess.check_output([str(runtime), '-cp', str(work) + os.pathsep + classpath, 'PriceExample'], text=True)
assert result.splitlines() == ['1000', '4点', '900'], result
print('README example: subtotal=1000; quantity=4点; payable=900')
