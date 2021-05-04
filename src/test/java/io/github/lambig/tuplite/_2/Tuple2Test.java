package io.github.lambig.tuplite._2;

import static io.github.lambig.tuplite._2.Tuple2._2map1With;
import static io.github.lambig.tuplite._2.Tuple2._2map2With;
import static io.github.lambig.tuplite._2.Tuple2._2mapWith;
import static io.github.lambig.tuplite._2.Tuple2._2testAllWith;
import static io.github.lambig.tuplite._2.Tuple2._2testAnyWith;
import static io.github.lambig.tuplite._2.Tuple2._2testWith;
import static io.github.lambig.tuplite._2.Tuple2.with1;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Tuple2Test {

  @Nested
  class 生成テスト {
    @Test
    void 入力値が設定されていること_2引数() {
      //SetUp
      //Exercise
      Tuple2<Integer, Integer> actual = Tuple2.tuple2(1, 2);
      //Verify
      assertThat(actual).matches(tuple2 -> tuple2._1() == 1 && tuple2._2() == 2);
    }

    @Test
    void 入力値が設定されていること_1と2() {
      //SetUp
      //Exercise
      Tuple2<Integer, Integer> actual = with1(1).and2(2);
      //Verify
      assertThat(actual).matches(tuple2 -> tuple2._1() == 1 && tuple2._2() == 2);
    }
  }

  @Nested
  class mapのテスト_1引数 {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      Tuple2<Integer, Integer> actual = original.map(i -> i + 1, i -> i - 1);
      //Verify
      assertThat(actual).matches(tuple2 -> tuple2._1() == 2 && tuple2._2() == 1);
    }
  }

  @Nested
  class mapのテスト_2引数 {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      Integer actual = original.map(Integer::sum);
      //Verify
      assertThat(actual).isEqualTo(3);
    }
  }

  @Nested
  class map_2のテスト {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      Tuple2<Integer, Integer> actual = original.map2(i -> i + 1);
      //Verify
      assertThat(actual).matches(tuple2 -> tuple2._1() == 1 && tuple2._2() == 3);
    }
  }

  @Nested
  class map_1のテスト {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      Tuple2<Integer, Integer> actual = original.map1(i -> i + 1);
      //Verify
      assertThat(actual).matches(tuple2 -> tuple2._1() == 2 && tuple2._2() == 2);
    }
  }

  @Nested
  class mapWithのテスト_biFunction {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      Integer actual = Optional.of(original).map(Tuple2._2mapWith(Integer::sum)).get();
      //Verify
      assertThat(actual).isEqualTo(3);
    }
  }

  @Nested
  class mapWithのテスト_2Function {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      UnaryOperator<Integer> add1 = anyInt -> anyInt + 1;
      //Exercise
      Tuple2<Integer, Integer> actual = Optional.of(original).map(_2mapWith(add1, add1)).get();
      //Verify
      assertThat(actual)
          .matches(
              _2testAllWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 3)));
    }
  }

  @Nested
  class map_1Withのテスト {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      UnaryOperator<Integer> add1 = anyInt -> anyInt + 1;
      //Exercise
      Tuple2<Integer, Integer> actual = Optional.of(original).map(_2map1With(add1)).get();
      //Verify
      assertThat(actual)
          .matches(
              _2testAllWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 2)));
    }
  }

  @Nested
  class map_2Withのテスト {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      UnaryOperator<Integer> add1 = anyInt -> anyInt + 1;
      //Exercise
      Tuple2<Integer, Integer> actual = Optional.of(original).map(_2map2With(add1)).get();
      //Verify
      assertThat(actual)
          .matches(
              _2testAllWith(
                  _1 -> Objects.equals(_1, 1),
                  _2 -> Objects.equals(_2, 3)));
    }
  }

  @Nested
  class testWithのテスト {
    @Test
    void 述語が真となる場合trueを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original).filter(_2testWith((_1, _2) -> _1 + _2 == 3)).isPresent();
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void 述語が偽となる場合falseを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original).filter(_2testWith((_1, _2) -> _1 + _2 == 4)).isPresent();
      //Verify
      assertThat(actual).isFalse();
    }
  }

  @Nested
  class testAnyWithのテスト {
    @Test
    void 述語がともに真となる場合trueを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAnyWith(
                  _1 -> Objects.equals(_1, 1),
                  _2 -> Objects.equals(_2, 2)))
          .isPresent();
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void 述語1のみが真となる場合trueを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAnyWith(
                  _1 -> Objects.equals(_1, 1),
                  _2 -> Objects.equals(_2, 3)))
          .isPresent();
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void 述語2のみが真となる場合trueを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAnyWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 2)))
          .isPresent();
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void 述語がともに偽となる場合falseを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAnyWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 1)))
          .isPresent();
      //Verify
      assertThat(actual).isFalse();
    }
  }

  @Nested
  class testAllWithのテスト {
    @Test
    void 述語がともに真となる場合trueを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAnyWith(
                  _1 -> Objects.equals(_1, 1),
                  _2 -> Objects.equals(_2, 2)))
          .isPresent();
      //Verify
      assertThat(actual).isTrue();
    }

    @Test
    void 述語1のみが真となる場合falseを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAllWith(
                  _1 -> Objects.equals(_1, 1),
                  _2 -> Objects.equals(_2, 3)))
          .isPresent();
      //Verify
      assertThat(actual).isFalse();
    }

    @Test
    void 述語2のみが真となる場合falseを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAllWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 2)))
          .isPresent();
      //Verify
      assertThat(actual).isFalse();
    }

    @Test
    void 述語がともに偽となる場合falseを返すこと() {
      //SetUp
      Tuple2<Integer, Integer> original = new Tuple2<>(1, 2);
      //Exercise
      boolean actual = Optional.of(original)
          .filter(
              _2testAllWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 1)))
          .isPresent();
      //Verify
      assertThat(actual).isFalse();
    }
  }
}