[![CI](https://github.com/lambig/Tuplite/actions/workflows/ci.yml/badge.svg)](https://github.com/lambig/Tuplite/actions/workflows/ci.yml)
# Tuplite

型の異なる二つ・三つの値を一組にし、その組合せから結果を求めたり、要素を変換・判定したりするJavaライブラリです。価格と数量を組にして金額を求め、数量だけを表示用の文字列へ変換できます。

このブランチは **1.0.1候補版** です。以下の依存宣言は公開完了後にMaven Centralから使えます。公開前は[開発手順](DEVELOPING.md)の候補リポジトリと、同じ[実行例](example/src/main/java/example/TupleExample.java)で確認してください。

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

`_2mapWith`／`_3mapWith`等は、タプルから結果を得る`Function`を返します。`_2testWith`／`_3testWith`等は、条件を満たすタプルを選ぶ`Predicate`を返します。`Optional.map`や`Stream.filter`へそのまま渡せます。関数化した時点では要素の変換や判定は起こらず、渡されたタプルに対して実行されます。

```java
Optional<Integer> subtotal = Optional.of(line)
        .map(Tuple2._2mapWith((price, quantity) -> price * quantity));
boolean eligible = line.is(price -> price > 0, quantity -> quantity > 0);
```

この断片は`java.util.Optional`と`Tuple2`をimportした、上の`line`がある文脈で使います。全APIを使う例は[TupleExample](example/src/main/java/example/TupleExample.java)を参照してください。

## 条件の組合せ、null、同値性

| 条件 | trueの意味 | 判定の範囲 |
| --- | --- | --- |
| `is(Predicate, ...)`、`_2testAllWith`、`_3testAllWith` | 全条件を満たす | 第1要素から順に調べ、falseになった後の条件は判定しない |
| `_2testAnyWith` | 二つのうち一つ以上を満たす | 両方を第1、第2の順で判定する。第1条件がtrueでも第2条件は省略されない |
| `_3testAnyWith` | 三つのうち一つ以上を満たす | 第1要素から順に調べ、trueになった後の条件は判定しない |

このTuple2／Tuple3の違いは1.0.0から維持しています。副作用や例外がある述語では、最終的な真偽値以外にも違いが出ます。`_2testAnyWith`には二つの述語が必要です。判定や変換で例外が発生すると呼出元へ伝わり、後続の処理は行われません。nullの関数を呼び出そうとした場合は`NullPointerException`です。短絡により呼ばれない述語は評価されません。

要素はnullを保持でき、変換結果もnullにできます。null要素をどう扱うかは、指定する関数・述語が決めます。タプル内の参照は固定ですが、要素が可変オブジェクトならその状態は共有されます。値のコピーや深い不変性を保証する型ではありません。

`equals`／`hashCode`は`Object`のものです。同じ要素を持つ二つのタプルも、別インスタンスなら等しくありません。値で比較するキー、業務上の名前、入力の妥当性を持つデータには、専用のクラスが適しています。

## 型の条件と更新

関数・述語の型引数は宣言された要素型に対応します。たとえば`Tuple2<Integer, Integer>.map1`に既存の`Function<Number, String>`を直接渡すことはできません。`value -> asText.apply(value)`のように要素型に適合するラムダで利用できます。今回この型契約は変更していません。

1.0.1候補では、従来のメソッド名・引数・戻り型を維持し、構築途中の型をpublicにして別packageからの`with1`等の連続呼出しを可能にします。FIE依存は1.0.0から1.1.3へ更新します。全要素コンストラクタとアクセサは、公開APIの文書に載る通常のJavaメソッドとして定義し、Lombokによる生成を不要にしています。

更新時はTupliteの版を変更し、依存ツリーでFIE 1.1.3が解決されること、同じ導入例と利用側の試験が通ることを確認します。FIEを別の版へ変更する場合も、この導入例と利用側の試験で互換性を確認してください。戻す場合はTupliteとFIEの組を戻し、別packageからの構築APIは旧版で使えない点にも注意してください。

APIの詳細はsources／Javadoc JAR、または`sh gradlew javadoc`で生成する`build/docs/javadoc/index.html`を参照してください。候補版のAPI文書は同じ候補buildに対応します。公開後の版付き文書は[Javadoc](https://javadoc.io/doc/io.github.lambig/Tuplite/1.0.1)でも参照できます。
不具合や文書の疑問は[Issues](https://github.com/lambig/Tuplite/issues)へ、使用版と再現例を添えて報告してください。build・互換性検証・公開手順は[DEVELOPING.md](DEVELOPING.md)にあります。
