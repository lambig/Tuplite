package io.github.lambig.tuplite._2;

import static io.github.lambig.funcifextension.proposition.Proposition.and;
import static io.github.lambig.funcifextension.proposition.Proposition.subject;

import io.github.lambig.tuplite.Tuple;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 2個の値を位置と型の対応を保って扱うタプル。
 *
 * <p>各位置の参照は作成後に変わらず、各要素の変換では新しいタプルを返します。要素はnullも保持でき、
 * 要素自身の変更可能な状態は共有されます。equals/hashCodeはObjectから継承するため、
 * 要素が同じ別インスタンスも同値にはなりません。値による同値性や業務上の名前を持つ型が
 * 必要な場合は、専用のクラスを選んでください。
 *
 * <p>関数と述語の例外は呼出元へ伝わります。要素がnullの場合の変換・判定結果は、
 * 指定した関数・述語によって決まります。
 *
 * @param <A> 第1要素の型
 * @param <B> 第2要素の型
 */
public class Tuple2<A, B> implements Tuple {

  private final A _1;
  private final B _2;

  /**
   * 2個の値を、その位置の要素として保持するタプルを作ります。
   *
   * @param _1 第1要素。nullも保持可能
   * @param _2 第2要素。nullも保持可能
   */
  public Tuple2(A _1, B _2) {
    this._1 = _1;
    this._2 = _2;
  }

  /**
   * 第1要素を返します。
   *
   * @return 作成時に指定した第1要素
   */
  public A _1() {
    return this._1;
  }

  /**
   * 第2要素を返します。
   *
   * @return 作成時に指定した第2要素
   */
  public B _2() {
    return this._2;
  }

  /**
   * タプルの全要素から一つの結果を得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 全要素をまとめた変換結果の型
   * @param biFunction 全要素の組合せを一つの結果にする関数
   * @return 全要素を一つの値に変換する関数
   */
  public static <S, T, U> Function<Tuple2<S, T>, U> _2mapWith(BiFunction<S, T, U> biFunction) {
    return tuple2 -> tuple2.map(biFunction);
  }

  /**
   * 各要素を変換したタプルを得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 第1要素の変換後の型
   * @param <V> 第2要素の変換後の型
   * @param _1Function 第1要素を変換する関数
   * @param _2Function 第2要素を変換する関数
   * @return 各要素の変換結果を持つ新しいタプルを返す関数
   */
  public static <S, T, U, V> Function<Tuple2<S, T>, Tuple2<U, V>> _2mapWith(Function<S, U> _1Function, Function<T, V> _2Function) {
    return tuple2 -> tuple2.map(_1Function, _2Function);
  }

  /**
   * 第1要素だけを変換したタプルを得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 第1要素の変換後の型
   * @param function 第1要素を変換する関数
   * @return 他の要素を保持した新しいタプルを返す関数
   */
  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<U, T>> _2map1With(Function<S, U> function) {
    return tuple2 -> tuple2.map1(function);
  }

  /**
   * 第2要素だけを変換したタプルを得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 第2要素の変換後の型
   * @param function 第2要素を変換する関数
   * @return 他の要素を保持した新しいタプルを返す関数
   */
  public static <S, T, U> Function<Tuple2<S, T>, Tuple2<S, U>> _2map2With(Function<T, U> function) {
    return tuple2 -> tuple2.map2(function);
  }

  /**
   * 全要素の組合せが指定条件を満たすかを表す述語を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param biPredicate 全要素の組合せに対する条件
   * @return 指定条件を満たすタプルに対してtrueとなる述語
   */
  public static <S, T> Predicate<Tuple2<S, T>> _2testWith(BiPredicate<S, T> biPredicate) {
    return tuple2 -> tuple2.is(biPredicate);
  }

  /**
   * いずれかの要素が対応する条件を満たすかを表す述語を返します。
   *
   * <p>第1、第2要素の順に両方の条件を判定します。第1条件がtrueでも第2条件の副作用や例外は省略されません。両方の述語を指定してください。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param _1Predicate 第1要素に対する条件
   * @param _2Predicate 第2要素に対する条件
   * @return 一つ以上の条件が成立すればtrueとなる述語
   */
  public static <S, T> Predicate<Tuple2<S, T>> _2testAnyWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate) {
    return tuple2 -> tuple2.map(_1Predicate::test, _2Predicate::test).map((a, b) -> a || b);
  }

  /**
   * すべての要素が対応する条件を満たすかを表す述語を返します。
   *
   * <p>第1要素から順に判定し、falseとなった後の条件は判定しません。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param _1Predicate 第1要素に対する条件
   * @param _2Predicate 第2要素に対する条件
   * @return すべての条件が成立すればtrueとなる述語
   */
  public static <S, T> Predicate<Tuple2<S, T>> _2testAllWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate) {
    return tuple2 -> tuple2.is(_1Predicate, _2Predicate);
  }

  /**
   * 指定した2個の値を位置どおりに保持するタプルを返します。
   *
   * @param <A> 第1要素の型
   * @param <B> 第2要素の型
   * @param _1 第1要素。nullも保持可能
   * @param _2 第2要素。nullも保持可能
   * @return 指定した要素を持つ新しいタプル
   */
  public static <A, B> Tuple2<A, B> tuple(A _1, B _2) {
    return new Tuple2<>(_1, _2);
  }

  /**
   * 指定した2個の値を位置どおりに保持するタプルを返します。
   *
   * @param <A> 第1要素の型
   * @param <B> 第2要素の型
   * @param _1 第1要素。nullも保持可能
   * @param _2 第2要素。nullも保持可能
   * @return 指定した要素を持つ新しいタプル
   */
  public static <A, B> Tuple2<A, B> tuple2(A _1, B _2) {
    return tuple(_1, _2);
  }

  /**
   * 第1要素を決め、残りの要素を指定してタプルを作るための入口を返します。
   *
   * @param <X> 第1要素の型
   * @param _1 第1要素。nullも保持可能
   * @return 第1要素を保持する構築用のオブジェクト
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
   * 全要素を一組として変換した結果を返します。
   *
   * @param <C> 全要素をまとめた変換結果の型
   * @param biFunction 全要素の組合せを一つの結果にする関数
   * @return 関数の結果。nullを含む
   */
  public <C> C map(BiFunction<A, B, C> biFunction) {
    return biFunction.apply(this._1, this._2);
  }

  /**
   * 各要素を変換した値を持つ新しいタプルを返します。
   *
   * <p>変換は第1要素から順に行います。途中で例外が発生した場合、後の要素は変換しません。
   *
   * @param <F> 第1要素の変換後の型
   * @param <S> 第2要素の変換後の型
   * @param _1Function 第1要素を変換する関数
   * @param _2Function 第2要素を変換する関数
   * @return 対応する変換結果を持つ新しいタプル
   */
  public <F, S> Tuple2<F, S> map(Function<A, F> _1Function, Function<B, S> _2Function) {
    return tuple(_1Function.apply(this._1), _2Function.apply(this._2));
  }

  /**
   * 第1要素を変換し、他の要素を保持した新しいタプルを返します。
   *
   * @param <N> 第1要素の変換後の型
   * @param function 第1要素を変換する関数
   * @return 指定位置だけが変換された新しいタプル
   */
  public <N> Tuple2<N, B> map1(Function<A, N> function) {
    return tuple(function.apply(this._1), this._2);
  }

  /**
   * 第2要素を変換し、他の要素を保持した新しいタプルを返します。
   *
   * @param <N> 第2要素の変換後の型
   * @param function 第2要素を変換する関数
   * @return 指定位置だけが変換された新しいタプル
   */
  public <N> Tuple2<A, N> map2(Function<B, N> function) {
    return tuple(this._1, function.apply(this._2));
  }

  /**
   * 全要素の組合せが指定条件を満たすかを返します。
   *
   * @param biPredicate 全要素の組合せに対する条件
   * @return 指定した条件が成立すればtrue
   */
  public boolean is(BiPredicate<A, B> biPredicate) {
    return biPredicate.test(this._1, this._2);
  }

  /**
   * すべての要素が対応する条件を満たすかを返します。
   *
   * <p>第1要素から順に判定し、falseとなった後の条件は判定しません。
   *
   * @param _1Predicate 第1要素に対する条件
   * @param _2Predicate 第2要素に対する条件
   * @return すべての条件が成立すればtrue
   */
  public boolean is(Predicate<A> _1Predicate, Predicate<B> _2Predicate) {
    return
        and(
            subject(this._1).satisfies(_1Predicate),
            subject(this._2).satisfies(_2Predicate))
            .test();
  }

  /**
   * 第1要素を共有し、第2要素の異なるタプルを作れる構築用インターフェース。
   *
   * @param <A> 第1要素の型
   */
  public interface Tuple2_1to2<A> {
    /**
     * 保持した第1要素と指定した第2要素のタプルを返します。
     * @param <B> 第2要素の型
     * @param _2 第2要素。nullも保持可能
     * @return 新しいタプル
     */
    <B> Tuple2<A, B> and2(B _2);
  }
}
