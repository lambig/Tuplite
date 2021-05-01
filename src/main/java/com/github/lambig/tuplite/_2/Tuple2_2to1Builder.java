package com.github.lambig.tuplite._2;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Tuple2_2to1Builder<L, R> implements Function<L, Tuple2<L, R>> {

  private final Function<L, R> function;

  public Tuple2<L, R> _1(L _1) {
    return new Tuple2<>(_1, function.apply(_1));
  }

  @Override
  public Tuple2<L, R> apply(L l) {
    return this._1(l);
  }
}
