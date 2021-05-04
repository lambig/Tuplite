package io.github.lambig.tuplite._3;

import static io.github.lambig.tuplite._3.Tuple3._3map1With;
import static io.github.lambig.tuplite._3.Tuple3._3map2With;
import static io.github.lambig.tuplite._3.Tuple3._3map3With;
import static io.github.lambig.tuplite._3.Tuple3._3mapWith;
import static io.github.lambig.tuplite._3.Tuple3._3testAllWith;
import static io.github.lambig.tuplite._3.Tuple3._3testAnyWith;
import static io.github.lambig.tuplite._3.Tuple3._3testWith;
import static io.github.lambig.tuplite._3.Tuple3.tuple3;
import static io.github.lambig.tuplite._3.Tuple3.with1;
import static io.github.lambig.tuplite._3.Tuple3.with1_2;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class Tuple3Test {

  @Nested
  class 生成テスト {
    @Test
    void 入力値が設定されていること_3引数() {
      //SetUp
      //Exercise
      Tuple3<Integer, Integer, Integer> actual = tuple3(1, 2, 3);
      //Verify
      assertThat(actual._1()).isEqualTo(1);
      assertThat(actual._2()).isEqualTo(2);
      assertThat(actual._3()).isEqualTo(3);
    }

    @Test
    void 入力値が設定されていること_2引数() {
      //SetUp
      //Exercise
      Tuple3<Integer, Integer, Integer> actual = with1_2(1, 2).and3(3);
      //Verify
      assertThat(actual)
          .matches(
              tuple -> tuple.is(
                  _1 -> _1 == 1,
                  _2 -> _2 == 2,
                  _3 -> _3 == 3));
    }

    @Test
    void 入力値が設定されていること_1引数_2引数() {
      //SetUp
      //Exercise
      Tuple3<Integer, Integer, Integer> actual = with1(1)._2and3(2, 3);
      //Verify
      assertThat(actual)
          .matches(
              tuple -> tuple.is(
                  _1 -> _1 == 1,
                  _2 -> _2 == 2,
                  _3 -> _3 == 3));
    }

    @Test
    void 入力値が設定されていること_1引数_1引数_1引数() {
      //SetUp
      //Exercise
      Tuple3<Integer, Integer, Integer> actual = with1(1)._2(2).and3(3);
      //Verify
      assertThat(actual)
          .matches(
              tuple -> tuple.is(
                  _1 -> _1 == 1,
                  _2 -> _2 == 2,
                  _3 -> _3 == 3));
    }
  }

  @Nested
  class mapのテスト_1引数関数版 {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .map(
                  _3mapWith(
                      _1 -> _1 + 1,
                      _2 -> _2 + 2,
                      _3 -> _3 + 3));
      //Verify
      assertThat(actual)
          .get()
          .matches(
              tuple -> tuple.is(
                  _1 -> _1 == 2,
                  _2 -> _2 == 4,
                  _3 -> _3 == 6));
    }
  }

  @Nested
  class mapのテスト_3引数関数版 {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Integer> actual =
          Optional
              .of(original)
              .flatMap(
                  _3mapWith(
                      (_1, _2, _3) ->
                          Stream
                              .of(_1, _2, _3)
                              .reduce(Integer::sum)));
      //Verify
      assertThat(actual).hasValue(6);
    }
  }

  @Nested
  class map_1のテスト {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .map(_3map1With(_1 -> _1 + 1));
      //Verify
      assertThat(actual)
          .get()
          .matches(
              tuple -> tuple.is(
                  _1 -> _1 == 2,
                  _2 -> _2 == 2,
                  _3 -> _3 == 3));
    }
  }


  @Nested
  class map_2のテスト {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .map(_3map2With(_2 -> _2 + 1));
      //Verify
      assertThat(actual)
          .get()
          .matches(
              tuple -> tuple.is(
                  _1 -> _1 == 1,
                  _2 -> _2 == 3,
                  _3 -> _3 == 3));
    }
  }

  @Nested
  class map_3のテスト {
    @Test
    void 関数が適用されていること() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .map(_3map3With(_3 -> _3 + 1));
      //Verify
      assertThat(actual)
          .get()
          .matches(
              tuple -> tuple.is(
                  _1 -> _1 == 1,
                  _2 -> _2 == 2,
                  _3 -> _3 == 4));
    }
  }

  @Nested
  class testWithのテスト {
    @Test
    void 述語が真となる場合trueを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .filter(_3testWith((_1, _2, _3) -> _1 + _2 + _3 == 6));
      //Verify
      assertThat(actual).isPresent();
    }

    @Test
    void 述語が偽となる場合falseを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .filter(_3testWith((_1, _2, _3) -> _1 + _2 + _3 == 7));
      //Verify
      assertThat(actual).isEmpty();
    }
  }

  @Nested
  class testAnyWithのテスト {
    @Test
    void 述語がすべて真となる場合trueを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .filter(
                  _3testAnyWith(
                      _1 -> _1 == 1,
                      _2 -> _2 == 2,
                      _3 -> _3 == 3));
      //Verify
      assertThat(actual).isPresent();
    }

    @Test
    void 真偽両方の述語が含まれる場合trueを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual = Optional.of(original)
          .filter(
              _3testAnyWith(
                  _1 -> Objects.equals(_1, 1),
                  _2 -> Objects.equals(_2, 3),
                  _3 -> Objects.equals(_3, 2)));
      //Verify
      assertThat(actual).isPresent();
    }

    @Test
    void 述語がすべて偽となる場合falseを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual = Optional.of(original)
          .filter(
              _3testAnyWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 3),
                  _3 -> Objects.equals(_3, 1)));
      //Verify
      assertThat(actual).isEmpty();
    }
  }

  @Nested
  class testAllWithのテスト {
    @Test
    void 述語がすべて真となる場合trueを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual =
          Optional
              .of(original)
              .filter(
                  _3testAllWith(
                      _1 -> _1 == 1,
                      _2 -> _2 == 2,
                      _3 -> _3 == 3));
      //Verify
      assertThat(actual).isPresent();
    }

    @Test
    void 真偽両方の述語が含まれる場合falseを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual = Optional.of(original)
          .filter(
              _3testAllWith(
                  _1 -> Objects.equals(_1, 1),
                  _2 -> Objects.equals(_2, 3),
                  _3 -> Objects.equals(_3, 2)));
      //Verify
      assertThat(actual).isEmpty();
    }

    @Test
    void 述語がすべて偽となる場合falseを返すこと() {
      //SetUp
      Tuple3<Integer, Integer, Integer> original = new Tuple3<>(1, 2, 3);
      //Exercise
      Optional<Tuple3<Integer, Integer, Integer>> actual = Optional.of(original)
          .filter(
              _3testAllWith(
                  _1 -> Objects.equals(_1, 2),
                  _2 -> Objects.equals(_2, 3),
                  _3 -> Objects.equals(_3, 1)));
      //Verify
      assertThat(actual).isEmpty();
    }
  }
}