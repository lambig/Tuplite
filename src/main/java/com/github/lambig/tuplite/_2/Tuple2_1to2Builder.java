package com.github.lambig.tuplite._2;

import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Tuple2_1to2Builder<L> {

  private final L _1;

  public <R> Tuple2<L, R> _2(R _2) {
    return new Tuple2<>(this._1, _2);
  }

  public <R> Tuple2<L, R> _2(Function<L, R> to_2) {
    return new Tuple2<>(this._1, to_2.apply(this._1));
  }
}
