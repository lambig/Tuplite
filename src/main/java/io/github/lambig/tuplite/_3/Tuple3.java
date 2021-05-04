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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 3-Tuple
 *
 * @param <A> 第1要素
 * @param <B> 第2要素
 * @param <C> 第2要素
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Accessors(fluent = true)
public class Tuple3<A, B, C> implements Tuple {
  private final A _1;
  private final B _2;
  private final C _3;

  public static <S, T, U, V> Function<Tuple3<S, T, U>, V> _3mapWith(TriFunction<S, T, U, V> triFunction) {
    return tuple3 -> tuple3.map(triFunction);
  }

  public static <S, T, U, X, Y, Z> Function<Tuple3<S, T, U>, Tuple3<X, Y, Z>> _3mapWith(
      Function<S, X> _1Function,
      Function<T, Y> _2Function,
      Function<U, Z> _3Function) {
    return tuple3 -> tuple3.map(_1Function, _2Function, _3Function);
  }

  public static <S, T, U, X> Function<Tuple3<S, T, U>, Tuple3<X, T, U>> _3map1With(Function<S, X> function) {
    return tuple3 -> tuple3.map1(function);
  }

  public static <S, T, U, Y> Function<Tuple3<S, T, U>, Tuple3<S, Y, U>> _3map2With(Function<T, Y> function) {
    return tuple3 -> tuple3.map2(function);
  }

  public static <S, T, U, Z> Function<Tuple3<S, T, U>, Tuple3<S, T, Z>> _3map3With(Function<U, Z> function) {
    return tuple3 -> tuple3.map3(function);
  }

  public static <S, T, U> Predicate<Tuple3<S, T, U>> _3testWith(TriPredicate<S, T, U> triPredicate) {
    return tuple3 -> tuple3.is(triPredicate);
  }

  public static <S, T, U> Predicate<Tuple3<S, T, U>> _3testAnyWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate, Predicate<U> _3Predicate) {
    return
        tuple3 ->
            tuple3
                .map(predicate(_1Predicate), predicate(_2Predicate), predicate(_3Predicate))
                .map((_1Proposition, _2Proposition, _3Proposition) -> or(_1Proposition, _2Proposition, _3Proposition))
                .test();
  }

  public static <S, T, U> Predicate<Tuple3<S, T, U>> _3testAllWith(Predicate<S> _1Predicate, Predicate<T> _2Predicate, Predicate<U> _3Predicate) {
    return tuple3 -> tuple3.is(_1Predicate, _2Predicate, _3Predicate);
  }

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

  public static <X, Y> Tuple3_with1and2<X, Y> with1_2(X _1, Y _2) {
    return new Tuple3_with1and2<X, Y>() {
      @Override
      public <Z> Tuple3<X, Y, Z> and3(Z _3) {
        return tuple(_1, _2, _3);
      }
    };
  }


  /**
   * 3要素から新しい3-Tupleを返す。
   *
   * @param a   要素1
   * @param b   要素2
   * @param c   要素3
   * @param <A> 要素1型
   * @param <B> 要素2型
   * @param <C> 要素3型
   * @return 新しい3-Tuple
   */
  public static <A, B, C> Tuple3<A, B, C> tuple(A a, B b, C c) {
    return new Tuple3<>(a, b, c);
  }

  /**
   * 3要素から新しい3-Tupleを返す。
   *
   * @param a   要素1
   * @param b   要素2
   * @param c   要素3
   * @param <A> 要素1型
   * @param <B> 要素2型
   * @param <C> 要素3型
   * @return 新しい3-Tuple
   */
  public static <A, B, C> Tuple3<A, B, C> tuple3(A a, B b, C c) {
    return tuple(a, b, c);
  }

  /**
   * 3-Tupleをオブジェクトに変換する。
   *
   * @param triFunction マッピング
   * @param <X>         出力型
   * @return 変換済みオブジェクト
   */
  public <X> X map(TriFunction<A, B, C, X> triFunction) {
    return triFunction.apply(this._1, this._2, this._3);
  }

  /**
   * 各要素のマッピングを受け取り、それらを適用した新しい3-Tupleを返す。
   *
   * @param _1Function 要素1マッピング
   * @param _2Function 要素2マッピング
   * @param _3Function 要素3マッピング
   * @param <X>        要素1出力型
   * @param <Y>        要素2出力側
   * @param <Z>        要素3出力側
   * @return 新しい3-Tuple
   */
  public <X, Y, Z> Tuple3<X, Y, Z> map(Function<A, X> _1Function, Function<B, Y> _2Function, Function<C, Z> _3Function) {
    return tuple(_1Function.apply(this._1), _2Function.apply(this._2), _3Function.apply(this._3));
  }

  /**
   * 要素1のマッピングを受け取り、他の要素はそのままに新しい3-Tupleを返す。
   *
   * @param function 要素1マッピング
   * @param <X>      要素1出力型
   * @return 要素1にマッピングを適用した新要素1を持つ新しい3-Tuple
   */
  public <X> Tuple3<X, B, C> map1(Function<A, X> function) {
    return tuple(function.apply(this._1), this._2, this._3);
  }

  /**
   * 要素2のマッピングを受け取り、他の要素はそのままに新しい3-Tupleを返す。
   *
   * @param function 要素2マッピング
   * @param <Y>      要素2出力型
   * @return 要素2にマッピングを適用した新要素2を持つ新しい3-Tuple
   */
  public <Y> Tuple3<A, Y, C> map2(Function<B, Y> function) {
    return tuple(this._1, function.apply(this._2), this._3);
  }

  /**
   * 要素3のマッピングを受け取り、他の要素はそのままに新しい3-Tupleを返す。
   *
   * @param function 要素3マッピング
   * @param <Z>      要素3出力型
   * @return 要素3にマッピングを適用した新要素2を持つ新しい3-Tuple
   */
  public <Z> Tuple3<A, B, Z> map3(Function<C, Z> function) {
    return tuple(this._1, this._2, function.apply(this._3));
  }

  public boolean is(TriPredicate<A, B, C> triPredicate) {
    return triPredicate.test(this._1, this._2, this._3);
  }

  public boolean is(Predicate<A> _1Predicate, Predicate<B> _2Predicate, Predicate<C> _3Predicate) {
    return
        and(
            subject(this._1).satisfies(_1Predicate),
            subject(this._2).satisfies(_2Predicate),
            subject(this._3).satisfies(_3Predicate))
            .test();
  }

  interface Tuple3_with1<X> {
    <Y, Z> Tuple3<X, Y, Z> _2and3(Y _2, Z _3);

    <Y> Tuple3_with1and2<X, Y> _2(Y _2);
  }

  interface Tuple3_with1and2<X, Y> {
    <Z> Tuple3<X, Y, Z> and3(Z _3);
  }
}
