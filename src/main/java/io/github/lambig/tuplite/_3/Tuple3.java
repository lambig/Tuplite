package io.github.lambig.tuplite._3;

import static io.github.lambig.funcifextension.proposition.Proposition.and;
import static io.github.lambig.funcifextension.proposition.Proposition.or;
import static io.github.lambig.funcifextension.proposition.Proposition.predicate;
import static io.github.lambig.funcifextension.proposition.Proposition.subject;

import io.github.lambig.funcifextension.function.TriFunction;
import io.github.lambig.funcifextension.predicate.TriPredicate;
import io.github.lambig.tuplite.Tuple;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 3個の値を位置と型の対応を保って扱うタプル。
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
 * @param <C> 第3要素の型
 */
public class Tuple3<A, B, C> implements Tuple {
  private final A _1;
  private final B _2;
  private final C _3;

  /**
   * 3個の値を、その位置の要素として保持するタプルを作ります。
   *
   * @param _1 第1要素。nullも保持可能
   * @param _2 第2要素。nullも保持可能
   * @param _3 第3要素。nullも保持可能
   */
  public Tuple3(A _1, B _2, C _3) {
    this._1 = _1;
    this._2 = _2;
    this._3 = _3;
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
   * 第3要素を返します。
   *
   * @return 作成時に指定した第3要素
   */
  public C _3() {
    return this._3;
  }

  /**
   * タプルの全要素から一つの結果を得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param <V> 全要素をまとめた変換結果の型
   * @param triFunction 全要素の組合せを一つの結果にする関数
   * @return 全要素を一つの値に変換する関数
   */
  public static <S, T, U, V> Function<Tuple3<S, T, U>, V> _3mapWith(TriFunction<S, T, U, V> triFunction) {
    return tuple3 -> tuple3.map(triFunction);
  }

  /**
   * 各要素を変換したタプルを得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param <X> 第1要素の変換後の型
   * @param <Y> 第2要素の変換後の型
   * @param <Z> 第3要素の変換後の型
   * @param _1Function 第1要素を変換する関数
   * @param _2Function 第2要素を変換する関数
   * @param _3Function 第3要素を変換する関数
   * @return 各要素の変換結果を持つ新しいタプルを返す関数
   */
  public static <S, T, U, X, Y, Z> Function<Tuple3<S, T, U>, Tuple3<X, Y, Z>> _3mapWith(
      Function<S, X> _1Function,
      Function<T, Y> _2Function,
      Function<U, Z> _3Function) {
    return tuple3 -> tuple3.map(_1Function, _2Function, _3Function);
  }

  /**
   * 第1要素だけを変換したタプルを得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param <X> 第1要素の変換後の型
   * @param function 第1要素を変換する関数
   * @return 他の要素を保持した新しいタプルを返す関数
   */
  public static <S, T, U, X> Function<Tuple3<S, T, U>, Tuple3<X, T, U>> _3map1With(Function<S, X> function) {
    return tuple3 -> tuple3.map1(function);
  }

  /**
   * 第2要素だけを変換したタプルを得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param <Y> 第2要素の変換後の型
   * @param function 第2要素を変換する関数
   * @return 他の要素を保持した新しいタプルを返す関数
   */
  public static <S, T, U, Y> Function<Tuple3<S, T, U>, Tuple3<S, Y, U>> _3map2With(Function<T, Y> function) {
    return tuple3 -> tuple3.map2(function);
  }

  /**
   * 第3要素だけを変換したタプルを得る関数を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param <Z> 第3要素の変換後の型
   * @param function 第3要素を変換する関数
   * @return 他の要素を保持した新しいタプルを返す関数
   */
  public static <S, T, U, Z> Function<Tuple3<S, T, U>, Tuple3<S, T, Z>> _3map3With(Function<U, Z> function) {
    return tuple3 -> tuple3.map3(function);
  }

  /**
   * 全要素の組合せが指定条件を満たすかを表す述語を返します。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param triPredicate 全要素の組合せに対する条件
   * @return 指定条件を満たすタプルに対してtrueとなる述語
   */
  public static <S, T, U> Predicate<Tuple3<S, T, U>> _3testWith(TriPredicate<S, T, U> triPredicate) {
    return tuple3 -> tuple3.is(triPredicate);
  }

  /**
   * いずれかの要素が対応する条件を満たすかを表す述語を返します。
   *
   * <p>第1要素から順に判定し、trueとなった後の条件は判定しません。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param _1Predicate 第1要素に対する条件
   * @param _2Predicate 第2要素に対する条件
   * @param _3Predicate 第3要素に対する条件
   * @return 一つ以上の条件が成立すればtrueとなる述語
   */
  public static <S, T, U> Predicate<Tuple3<S, T, U>> _3testAnyWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate, Predicate<U> _3Predicate) {
    return
        tuple3 ->
            tuple3
                .map(predicate(_1Predicate), predicate(_2Predicate), predicate(_3Predicate))
                .map((_1Proposition, _2Proposition, _3Proposition) -> or(_1Proposition, _2Proposition, _3Proposition))
                .test();
  }

  /**
   * すべての要素が対応する条件を満たすかを表す述語を返します。
   *
   * <p>第1要素から順に判定し、falseとなった後の条件は判定しません。
   *
   * @param <S> 入力タプルの第1要素の型
   * @param <T> 入力タプルの第2要素の型
   * @param <U> 入力タプルの第3要素の型
   * @param _1Predicate 第1要素に対する条件
   * @param _2Predicate 第2要素に対する条件
   * @param _3Predicate 第3要素に対する条件
   * @return すべての条件が成立すればtrueとなる述語
   */
  public static <S, T, U> Predicate<Tuple3<S, T, U>> _3testAllWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate, Predicate<U> _3Predicate) {
    return tuple3 -> tuple3.is(_1Predicate, _2Predicate, _3Predicate);
  }

  /**
   * 第1要素を決め、残りの要素を指定してタプルを作るための入口を返します。
   *
   * @param <X> 第1要素の型
   * @param _1 第1要素。nullも保持可能
   * @return 第1要素を保持する構築用のオブジェクト
   */
  public static <X> Tuple3_with1<X> with1(X _1) {
    return new Tuple3_with1<X>() {
      @Override
      public <Y, Z> Tuple3<X, Y, Z> _2and3(Y _2, Z _3) {
        return tuple(_1, _2, _3);
      }

      @Override
      public <Y> Tuple3_with1and2<X, Y> _2(Y _2) {
        return new Tuple3_with1and2<X, Y>() {
          @Override
          public <Z> Tuple3<X, Y, Z> and3(Z _3) {
            return tuple(_1, _2, _3);
          }
        };
      }
    };
  }

  /**
   * 第1・第2要素を決め、第3要素を指定してタプルを作るための入口を返します。
   *
   * @param <X> 第1要素の型
   * @param <Y> 第2要素の型
   * @param _1 第1要素。nullも保持可能
   * @param _2 第2要素。nullも保持可能
   * @return 第1・第2要素を保持する構築用のオブジェクト
   */
  public static <X, Y> Tuple3_with1and2<X, Y> with1_2(X _1, Y _2) {
    return new Tuple3_with1and2<X, Y>() {
      @Override
      public <Z> Tuple3<X, Y, Z> and3(Z _3) {
        return tuple(_1, _2, _3);
      }
    };
  }

  /**
   * 指定した3個の値を位置どおりに保持するタプルを返します。
   *
   * @param <A> 第1要素の型
   * @param <B> 第2要素の型
   * @param <C> 第3要素の型
   * @param a 第1要素。nullも保持可能
   * @param b 第2要素。nullも保持可能
   * @param c 第3要素。nullも保持可能
   * @return 指定した要素を持つ新しいタプル
   */
  public static <A, B, C> Tuple3<A, B, C> tuple(A a, B b, C c) {
    return new Tuple3<>(a, b, c);
  }

  /**
   * 指定した3個の値を位置どおりに保持するタプルを返します。
   *
   * @param <A> 第1要素の型
   * @param <B> 第2要素の型
   * @param <C> 第3要素の型
   * @param a 第1要素。nullも保持可能
   * @param b 第2要素。nullも保持可能
   * @param c 第3要素。nullも保持可能
   * @return 指定した要素を持つ新しいタプル
   */
  public static <A, B, C> Tuple3<A, B, C> tuple3(A a, B b, C c) {
    return tuple(a, b, c);
  }

  /**
   * 全要素を一組として変換した結果を返します。
   *
   * @param <X> 全要素をまとめた変換結果の型
   * @param triFunction 全要素の組合せを一つの結果にする関数
   * @return 関数の結果。nullを含む
   */
  public <X> X map(TriFunction<A, B, C, X> triFunction) {
    return triFunction.apply(this._1, this._2, this._3);
  }

  /**
   * 各要素を変換した値を持つ新しいタプルを返します。
   *
   * <p>変換は第1要素から順に行います。途中で例外が発生した場合、後の要素は変換しません。
   *
   * @param <X> 第1要素の変換後の型
   * @param <Y> 第2要素の変換後の型
   * @param <Z> 第3要素の変換後の型
   * @param _1Function 第1要素を変換する関数
   * @param _2Function 第2要素を変換する関数
   * @param _3Function 第3要素を変換する関数
   * @return 対応する変換結果を持つ新しいタプル
   */
  public <X, Y, Z> Tuple3<X, Y, Z> map(Function<A, X> _1Function, Function<B, Y> _2Function, Function<C, Z> _3Function) {
    return tuple(_1Function.apply(this._1), _2Function.apply(this._2), _3Function.apply(this._3));
  }

  /**
   * 第1要素を変換し、他の要素を保持した新しいタプルを返します。
   *
   * @param <X> 第1要素の変換後の型
   * @param function 第1要素を変換する関数
   * @return 指定位置だけが変換された新しいタプル
   */
  public <X> Tuple3<X, B, C> map1(Function<A, X> function) {
    return tuple(function.apply(this._1), this._2, this._3);
  }

  /**
   * 第2要素を変換し、他の要素を保持した新しいタプルを返します。
   *
   * @param <Y> 第2要素の変換後の型
   * @param function 第2要素を変換する関数
   * @return 指定位置だけが変換された新しいタプル
   */
  public <Y> Tuple3<A, Y, C> map2(Function<B, Y> function) {
    return tuple(this._1, function.apply(this._2), this._3);
  }

  /**
   * 第3要素を変換し、他の要素を保持した新しいタプルを返します。
   *
   * @param <Z> 第3要素の変換後の型
   * @param function 第3要素を変換する関数
   * @return 指定位置だけが変換された新しいタプル
   */
  public <Z> Tuple3<A, B, Z> map3(Function<C, Z> function) {
    return tuple(this._1, this._2, function.apply(this._3));
  }

  /**
   * 全要素の組合せが指定条件を満たすかを返します。
   *
   * @param triPredicate 全要素の組合せに対する条件
   * @return 指定した条件が成立すればtrue
   */
  public boolean is(TriPredicate<A, B, C> triPredicate) {
    return triPredicate.test(this._1, this._2, this._3);
  }

  /**
   * すべての要素が対応する条件を満たすかを返します。
   *
   * <p>第1要素から順に判定し、falseとなった後の条件は判定しません。
   *
   * @param _1Predicate 第1要素に対する条件
   * @param _2Predicate 第2要素に対する条件
   * @param _3Predicate 第3要素に対する条件
   * @return すべての条件が成立すればtrue
   */
  public boolean is(Predicate<A> _1Predicate, Predicate<B> _2Predicate, Predicate<C> _3Predicate) {
    return
        and(
            subject(this._1).satisfies(_1Predicate),
            subject(this._2).satisfies(_2Predicate),
            subject(this._3).satisfies(_3Predicate))
            .test();
  }

  /**
   * 第1要素を共有し、第2・第3要素の異なるタプルを作れる構築用インターフェース。
   *
   * @param <X> 第1要素の型
   */
  public interface Tuple3_with1<X> {
    /**
     * 保持した第1要素と指定した第2・第3要素のタプルを返します。
     * @param <Y> 第2要素の型
     * @param <Z> 第3要素の型
     * @param _2 第2要素。nullも保持可能
     * @param _3 第3要素。nullも保持可能
     * @return 新しいタプル
     */
    <Y, Z> Tuple3<X, Y, Z> _2and3(Y _2, Z _3);

    /**
     * 第2要素も決め、第3要素を指定するための入口を返します。
     * @param <Y> 第2要素の型
     * @param _2 第2要素。nullも保持可能
     * @return 第1・第2要素を保持する構築用のオブジェクト
     */
    <Y> Tuple3_with1and2<X, Y> _2(Y _2);
  }

  /**
   * 第1・第2要素を共有し、第3要素の異なるタプルを作れる構築用インターフェース。
   *
   * @param <X> 第1要素の型
   * @param <Y> 第2要素の型
   */
  public interface Tuple3_with1and2<X, Y> {
    /**
     * 保持した第1・第2要素と指定した第3要素のタプルを返します。
     * @param <Z> 第3要素の型
     * @param _3 第3要素。nullも保持可能
     * @return 新しいタプル
     */
    <Z> Tuple3<X, Y, Z> and3(Z _3);
  }
}
