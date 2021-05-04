package io.github.lambig.tuplite._2;


import static io.github.lambig.funcifextension.proposition.Proposition.and;
import static io.github.lambig.funcifextension.proposition.Proposition.subject;


import io.github.lambig.tuplite.Tuple;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;


/**
 * 2-Tuple
 *
 * @param <A> 第1要素
 * @param <B> 第2要素
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Accessors(fluent = true)
public class Tuple2<A, B> implements Tuple {

  private final A _1;
  private final B _2;

  public static <S, T, U> Function<Tuple2<S, T>, U> _2mapWith(BiFunction<S, T, U> biFunction) {
    return tuple2 -> tuple2.map(biFunction);
  }

  public static <S, T, U, V> Function<Tuple2<S, T>, Tuple2<U, V>> _2mapWith(Function<S, U> _1Function, Function<T, V> _2Function) {
    return tuple2 -> tuple2.map(_1Function, _2Function);
  }

  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<U, T>> _2map1With(Function<S, U> function) {
    return tuple2 -> tuple2.map1(function);
  }

  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<S, U>> _2map2With(Function<T, U> function) {
    return tuple2 -> tuple2.map2(function);
  }

  public static <S, T> Predicate<Tuple2<S, T>> _2testWith(BiPredicate<S, T> biPredicate) {
    return tuple2 -> tuple2.is(biPredicate);
  }

  public static <S, T> Predicate<Tuple2<S, T>> _2testAnyWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate) {
    return tuple2 -> tuple2.map(_1Predicate::test, _2Predicate::test).map((a, b) -> a || b);
  }

  public static <S, T> Predicate<Tuple2<S, T>> _2testAllWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate) {
    return tuple2 -> tuple2.is(_1Predicate, _2Predicate);
  }

  /**
   * 2要素から新しい2-Tupleを返す。
   *
   * @param _1  要素1
   * @param _2  要素2
   * @param <A> 要素1型
   * @param <B> 要素2型
   * @return 新しい2-Tuple
   */
  public static <A, B> Tuple2<A, B> tuple(A _1, B _2) {
    return new Tuple2<>(_1, _2);
  }

  /**
   * 2要素から新しい2-Tupleを返す。
   *
   * @param _1  要素1
   * @param _2  要素2
   * @param <A> 要素1型
   * @param <B> 要素2型
   * @return 新しい2-Tuple
   */
  public static <A, B> Tuple2<A, B> tuple2(A _1, B _2) {
    return tuple(_1, _2);
  }


  /**
   * コンストラクタの_1部分適用。
   *
   * <p>2-Tupleのコンストラクタ1部分適用を取得する
   *
   * @param _1  要素1
   * @param <X> 要素1型
   * @return 2-Tupleのビルダ
   */
  public static <X> Tuple2_1to2<X> with1(X _1) {
    return new Tuple2_1to2<X>() {
      @Override
      public <Y> Tuple2<X, Y> and2(Y _2) {
        return tuple(_1, _2);
      }
    };
  }

  /**
   * 2-Tupleをオブジェクトに変換する。
   *
   * @param biFunction マッピング
   * @param <C>        出力型
   * @return 変換済みオブジェクト
   */
  public <C> C map(BiFunction<A, B, C> biFunction) {
    return biFunction.apply(this._1, this._2);
  }

  /**
   * 要素1,2のマッピングを受け取り、それらを適用した新しい2-Tupleを返す。
   *
   * @param _1Function 要素1マッピング
   * @param _2Function 要素2マッピング
   * @param <F>        要素1出力型
   * @param <S>        要素2出力側
   * @return 新しい2-Tuple
   */
  public <F, S> Tuple2<F, S> map(Function<A, F> _1Function, Function<B, S> _2Function) {
    return tuple(_1Function.apply(this._1), _2Function.apply(this._2));
  }

  /**
   * 要素1のマッピングを受け取り、要素2はそのままに新しい2-Tupleを返す。
   *
   * @param function 要素1マッピング
   * @param <N>      要素1出力型
   * @return 要素2と、要素1にマッピングを適用した新要素1を持つ新しい2-Tuple
   */
  public <N> Tuple2<N, B> map1(Function<A, N> function) {
    return tuple(function.apply(this._1), this._2);
  }

  /**
   * 要素2のマッピングを受け取り、要素1はそのままに新しい2-Tupleを返す。
   *
   * @param function 要素2マッピング
   * @param <N>      要素2出力型
   * @return 要素1と要素2マッピングを適用した新要素2を持つ新しい2-Tuple
   */
  public <N> Tuple2<A, N> map2(Function<B, N> function) {
    return tuple(this._1, function.apply(this._2));
  }

  public boolean is(BiPredicate<A, B> biPredicate) {
    return biPredicate.test(this._1, this._2);
  }

  public boolean is(Predicate<A> _1Predicate, Predicate<B> _2Predicate) {
    return
        and(
            subject(this._1).satisfies(_1Predicate),
            subject(this._2).satisfies(_2Predicate))
            .test();
  }

  interface Tuple2_1to2<A> {
    <B> Tuple2<A, B> and2(B _2);
  }
}
