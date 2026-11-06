# 開発・候補検証・公開

利用結果とAPIの選び方は[README](README.md)を参照してください。ここではライブラリを変更・検証・公開するための手順を扱います。

## 候補版を検証する

JDK 17以上、Maven 3.9以上、Python 3、POSIX shellを使います。Gradle Wrapperは9.7.1です。WindowsではWSL等を使用できます。

```sh
sh gradlew clean check publishMavenJavaPublicationToCandidateRepository
sh verify-consumers.sh candidate
```

候補リポジトリは`build/repository`です。候補Tupliteだけをそこから解決し、FIE 1.1.3はMaven Centralの公開版を使います。
`TEST_JAVA_HOME`を別のJDKへ設定すると、build用JDKを保ったまま単体試験と独立導入例の実行JDKを選べます。CIではJava 8／25を使います。

検証は既存試験に加え、別packageからの構築、null、同値性、mapの位置と例外、判定の短絡、関数化した操作の再利用を対象にします。空の依存キャッシュでMaven／Gradleの例を動かし、実行時依存がTupliteとFIEだけであることを確認します。

公開済み1.0.0との型・メソッドシグネチャ、Java 8 bytecodeを照合します。許容するAPI差は構築用の三つのインターフェースをpublicにすることです。1.0.0に対してコンパイルした利用コードを旧依存・新依存の組で実行し、構築APIの別packageからのコンパイルが旧版で失敗、候補版で成功することも確認します。
結果は`build/consumer-results/candidate`、`build/test-results`、`build/compatibility`に出力します。

## 公開物を作成する

`SIGNING_KEY`にASCII-armored秘密鍵、`SIGNING_PASSWORD`にそのパスフレーズを環境から設定して実行します。値はソースやコマンドラインへ記載しないでください。

```sh
sh gradlew clean check centralBundle -PreleaseBundle
```

`build/central/Tuplite-1.0.1-central.zip`に、JAR・sources・日本語Javadoc・POM・Gradle Module Metadataとそれぞれの署名・チェックサムが入ります。POMではFIE 1.1.3をcompile依存として公開します。署名が欠けるとbundle生成は失敗します。
公開者はZip内のファイル一覧、署名、チェックサム、メタデータの参照先を確認し、[Central Portal](https://central.sonatype.com/publishing)で検証・公開します。buildタスクの成果物はローカルのZipです。

公開URLから取得した公開物・署名とbundleを照合した後、Maven Centralを参照して導入確認を実行します。

```sh
sh verify-consumers.sh public 1.0.1
```

公開URLでの取得と導入確認が成功したら、READMEの候補版表示を公開済みへ更新し、利用側の更新・回帰を進めます。
