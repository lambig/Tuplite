[![CI](https://github.com/lambig/Tuplite/actions/workflows/ci.yml/badge.svg)](https://github.com/lambig/Tuplite/actions/workflows/ci.yml)
# Tuplite

型の異なる二つ・三つの値を一組にし、その組合せから結果を求めたり、要素を変換・判定したりするJavaライブラリです。価格と数量を組にして金額を求め、数量だけを表示用の文字列へ変換できます。

**1.0.1** はMaven Centralから利用できます。以下の依存宣言と[実行例](example/src/main/java/example/TupleExample.java)で、導入から結果の確認まで進められます。

## 導入

利用時の最低Javaは8です。ソースからのbuildにはJDK 17以上を使います。公開依存はFIE（`functional-interface-extension:1.1.3`）一つで、`TriFunction`／`TriPredicate`もTupliteだけの依存宣言で使えます。

Gradle:

```groovy
repositories { mavenCentral() }
dependencies { implementation 'io.github.lambig:Tuplite:1.0.1' }
```

Maven:

```xml
<dependency>
  <groupId>io.github.lambig</groupId>
  <artifactId>Tuplite</artifactId>
  <version>1.0.1</version>
</dependency>
```

## 最初の例

```java
import io.github.lambig.tuplite._2.Tuple2;
import io.github.lambig.tuplite._3.Tuple3;

class PriceExample {
    public static void main(String[] args) {
        Tuple2<Integer, Integer> line = Tuple2.with1(250).and2(4);
        System.out.println(line.<Integer>map((price, quantity) -> price * quantity)); // 1000
        System.out.println(line.map2(quantity -> quantity + "点")._2());    // 4点
        Tuple3<Integer, Integer, Integer> discounted =
                Tuple3.with1(250)._2(4).and3(100);
        System.out.println(discounted.<Integer>map(
                (price, quantity, discount) -> price * quantity - discount)); // 900
    }
}
```

公開版の独立した実行例は、リポジトリ取得後に次で動かせます。例のソース、依存宣言、実行コマンドを一緒に管理しています。

```sh
# JDK 17以上でコンパイルし、既定では同じJDKで実行
sh gradlew -p example clean runExample
mvn -f example/pom.xml clean compile dependency:build-classpath -Dmdep.outputFile=target/runtime-classpath.txt
java -cp "example/target/classes:$(cat example/target/runtime-classpath.txt)" example.TupleExample
```

どちらも `subtotal=1000; payable=900; tuple-api=OK` を出力します。コマンドはPOSIX shell用です。WindowsではWSL等を利用するか、クラスパス区切りを`;`にしたJavaコマンドを使ってください。

## 操作と得られる結果

| 操作 | 結果 |
| --- | --- |
| `tuple`、`tuple2`／`tuple3`、コンストラクタ | 指定位置に値を保持する新しいタプル |
| `with1(...).and2(...)` | 第1要素を先に決めたTuple2 |
| `with1(...)._2and3(...)`、`with1(...)._2(...).and3(...)`、`with1_2(...).and3(...)` | 一部の要素を先に決めたTuple3 |
| `_1()`、`_2()`、`_3()` | 対応する位置の要素 |
| `map(BiFunction)`／`map(TriFunction)` | 全要素の組合せから得た一つの値 |
| `map(Function, ...)` | 各要素を変換した新しいタプル |
| `map1`／`map2`／`map3` | 指定位置だけを変換し、他の要素を保持した新しいタプル |
| `is(BiPredicate)`／`is(TriPredicate)` | 全要素の組合せが条件を満たすか |
| `is(Predicate, ...)` | すべての要素が対応する条件を満たすか |

`_2mapWith`／`_3mapWith`等は、タプルから結果を得る`Function`を返します。`_2testWith`／`_3testWith`等は、条件を満たすタプルを選ぶ`Predicate`を返します。`Optional.map`や`Stream.filter`へそのまま渡せます。返された関数・述語にタプルを渡すと、その要素の変換・判定結果が得られます。

```java
Optional<Integer> subtotal = Optional.of(line)
        .map(Tuple2._2mapWith((price, quantity) -> price * quantity));
boolean eligible = line.is(price -> price > 0, quantity -> quantity > 0);
```

この断片は`java.util.Optional`と`Tuple2`をimportした、上の`line`がある文脈で使います。全APIを使う例は[TupleExample](example/src/main/java/example/TupleExample.java)を参照してください。

## 条件を組み合わせる

| 条件 | trueの意味 | 判定の範囲 |
| --- | --- | --- |
| `is(Predicate, ...)`、`_2testAllWith`、`_3testAllWith` | 全条件を満たす | 第1要素から順に調べ、最初のfalseで終了（短絡評価） |
| `_2testAnyWith` | 二つのうち一つ以上を満たす | 第1、第2の順で両方を判定する |
| `_3testAnyWith` | 三つのうち一つ以上を満たす | 第1要素から順に調べ、最初のtrueで終了（短絡評価） |

たとえば`_2testAnyWith`で価格・数量の条件を指定すると、価格の条件がtrueの場合も数量の条件を判定します。`_3testAnyWith`で価格の条件がtrueなら、その時点で全体の結果がtrueに決まります。条件内に記録や更新などの処理を含む場合も、この範囲で実行されます。

判定・変換中に例外が発生した場合は、その時点で処理を終了して例外を呼出元へ伝えます。nullの関数・述語を呼び出すと`NullPointerException`になります。

## 要素の保持と比較

タプルの各位置には、作成時に指定した要素への参照を保持します。たとえばリストを要素にして、そのリストへ項目を追加すると、タプルから取得するリストにも追加が反映されます。`map1`等による変換では、変換結果と他の位置の要素を持つ新しいタプルが得られます。

要素と変換結果にはnullを含められます。null要素を扱う変換・判定は、指定した関数・述語に従います。

`equals`／`hashCode`は`Object`から継承し、インスタンスの同一性に基づきます。要素の値を比較したい場合は、`_1()`等で取り出した値を使って比較できます。

同じ要素を持つ別々のタプルを`HashSet`へ追加すると、それぞれが保持されます。要素の値による重複排除や`Map`のキー検索には、値に基づく`equals`／`hashCode`を持つキーを使用してください。

## 型の条件と更新

関数・述語の型引数にはタプルの要素型を対応させます。`Tuple2<Integer, Integer>.map1`には`Function<Integer, String>`や、`Integer`を受け取るラムダを渡せます。既存の`Function<Number, String> asText`を使う場合は、`value -> asText.apply(value)`で要素型に合わせられます。

1.0.1では、別packageから`with1(...).and2(...)`等の段階的な構築を使えます。従来のメソッド名・引数・戻り型を引き継ぎ、FIE依存は1.1.3になります。コンストラクタと各要素の取得方法もJavadocで参照できます。

更新時はTupliteの版を変更し、依存ツリーでFIE 1.1.3が解決されること、同じ導入例と利用側の試験が通ることを確認します。FIEを別の版へ変更する場合も、この導入例と利用側の試験で互換性を確認してください。1.0.0へ戻す場合はTupliteとFIEの組を戻し、別packageでの構築には`tuple(...)`等の全要素を指定する操作を使ってください。

APIの詳細は[1.0.1のJavadoc JAR](https://repo.maven.apache.org/maven2/io/github/lambig/Tuplite/1.0.1/Tuplite-1.0.1-javadoc.jar)を取得・展開し、`index.html`を開いて参照できます。ソースから生成する場合は、`sh gradlew javadoc`で生成する`build/docs/javadoc/index.html`を参照してください。
不具合や文書の疑問は[Issues](https://github.com/lambig/Tuplite/issues)へ、使用版と再現例を添えて報告してください。build・互換性検証・公開手順は[DEVELOPING.md](DEVELOPING.md)にあります。
