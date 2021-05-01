package com.github.lambig.tuplite._2;

import com.github.lambig.tuplite.Tuple;
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

  private static final long serialVersionUID = -3197850122265725618L;

  private final A _1;
  private final B _2;

  public static <S, T, U> Function<Tuple2<S, T>, U> mapWith(BiFunction<S, T, U> biFunction) {
    return tuple2 -> tuple2.map(biFunction);
  }

  public static <S, T, U, V> Function<Tuple2<S, T>, Tuple2<U, V>> mapWith(Function<S, U> _1Function, Function<T, V> _2Function) {
    return tuple2 -> tuple2.map(_1Function, _2Function);
  }

  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<U, T>> map_1With(Function<S, U> function) {
    return tuple2 -> tuple2.map_1(function);
  }

  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<U, T>> biMap_1With(BiFunction<S, T, U> biFunction) {
    return tuple2 -> tuple2.biMap_1(biFunction);
  }

  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<S, U>> map_2With(Function<T, U> function) {
    return tuple2 -> tuple2.map_2(function);
  }

  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<S, U>> biMap_2With(BiFunction<S, T, U> biFunction) {
    return tuple2 -> tuple2.biMap_2(biFunction);
  }


  public static <S, T> Predicate<Tuple2<S, T>> testWith(BiPredicate<S, T> biPredicate) {
    return tuple2 -> tuple2.map(biPredicate::test);
  }

  public static <S, T> Predicate<Tuple2<S, T>> testAnyWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate) {
    return tuple2 -> tuple2.map(_1Predicate::test, _2Predicate::test).map((a, b) -> a || b);
  }

  public static <S, T> Predicate<Tuple2<S, T>> testAllWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate) {
    return tuple2 -> tuple2.map(_1Predicate::test, _2Predicate::test).map((a, b) -> a && b);
  }

  /**
   * 要素2から新しい2-Tupleを返す。
   *
   * @param a   要素1
   * @param b   要素2
   * @param <A> 要素1型
   * @param <B> 要素2型
   * @return 新しい2-Tuple
   */
  public static <A, B> Tuple2<A, B> tuple2(A a, B b) {
    return new Tuple2<>(a, b);
  }


  /**
   * コンストラクタの_1部分適用。
   *
   * <p>2-Tupleのビルダを取得する
   *
   * @param _1  要素1
   * @param <A> 要素1型
   * @return 2-Tupleのビルダ
   */
  public static <A> Tuple2_1to2Builder<A> with_1(A _1) {
    return new Tuple2_1to2Builder<>(_1);
  }

  /**
   * 要素1から要素2を導出できる場合に、先に導出法を与えることで2-Tupleのビルダを取得する。
   *
   * @param to_2 1→2の導出関数
   * @param <A>  要素1型
   * @param <B>  要素2型
   * @return 2-Tupleのビルダ
   */
  public static <A, B> Tuple2_2to1Builder<A, B> _2From(Function<A, B> to_2) {
    return new Tuple2_2to1Builder<>(to_2);
  }

  /**
   * 要素1と2の反転を実施する。
   *
   * @return 反転済みインスタンス
   */
  public Tuple2<B, A> invert() {
    return new Tuple2<>(this._2, this._1);
  }

  /**
   * 2-Tupleをオブジェクトに変換する。
   *
   * @param biFunction マッピング
   * @param <C>        出力型a
   * @return 変換済みオブジェクト
   */
  public <C> C map(BiFunction<A, B, C> biFunction) {
    return biFunction.apply(this._1(), this._2());
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
    return new Tuple2<>(_1Function.apply(this._1), _2Function.apply(this._2));
  }

  /**
   * 要素1,2から新しい要素2へのマッピングを受け取り、要素1はそのままに新しい2-Tupleを返す。
   *
   * @param biFunction 要素2から新しい要素2へのマッピング
   * @param <N>        新しい要素2型
   * @return 要素1をシャローコピーし、マッピングを適用した要素2を持つ新しい2-Tuple
   */
  public <N> Tuple2<A, N> biMap_2(BiFunction<A, B, N> biFunction) {
    return new Tuple2<>(this._1, biFunction.apply(this._1, this._2));
  }

  /**
   * 要素2のマッピングを受け取り、要素1はそのままに新しい2-Tupleを返す。
   *
   * @param function 要素2マッピング
   * @param <N>      要素2出力型
   * @return 要素1をシャローコピーし、マッピングを適用した要素2を持つ新しい2-Tuple
   */
  public <N> Tuple2<A, N> map_2(Function<B, N> function) {
    return new Tuple2<>(this._1, function.apply(this._2));
  }

  /**
   * 要素1,2から新しい要素1へのマッピングを受け取り、要素2はそのままに新しい2-Tupleを返す。
   *
   * @param biFunction 要素1,2から新しい要素1へのマッピング
   * @param <N>        新しい要素1型
   * @return 要素2をシャローコピーし、マッピングを適用した要素1を持つ新しい2-Tuple
   */
  public <N> Tuple2<N, B> biMap_1(BiFunction<A, B, N> biFunction) {
    return new Tuple2<>(biFunction.apply(this._1, this._2), this._2);
  }

  /**
   * 要素1のマッピングを受け取り、要素2はそのままに新しい2-Tupleを返す。
   *
   * @param function 要素1マッピング
   * @param <N>      要素1出力型
   * @return 要素2をシャローコピーし、マッピングを適用した要素1を持つ新しい2-Tuple
   */
  public <N> Tuple2<N, B> map_1(Function<A, N> function) {
    return new Tuple2<>(function.apply(this._1), this._2);
  }
}
