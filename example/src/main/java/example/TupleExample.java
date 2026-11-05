package example;

import io.github.lambig.funcifextension.function.TriFunction;
import io.github.lambig.funcifextension.predicate.TriPredicate;
import io.github.lambig.tuplite._2.Tuple2;
import io.github.lambig.tuplite._3.Tuple3;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/** Tupliteだけを依存宣言して動かす、価格と数量の例。 */
public final class TupleExample {
    public static void main(String[] args) {
        Tuple2<Integer, Integer> line = Tuple2.with1(250).and2(4);
        int subtotal = line.map((price, quantity) -> price * quantity);
        Tuple2<Integer, String> label = line.map2(quantity -> quantity + "点");
        require(label._1() == 250 && "4点".equals(label._2()));
        require(line.is(price -> price > 0, quantity -> quantity > 0));
        require(line.is((price, quantity) -> price * quantity <= 1000));
        require(Optional.of(line).map(Tuple2._2mapWith((price, quantity) -> price * quantity)).get() == subtotal);
        require(Tuple2._2testAnyWith((Integer price) -> price >= 250, (Integer quantity) -> quantity >= 10).test(line));
        require(Tuple2._2testAllWith((Integer price) -> price > 0, (Integer quantity) -> quantity > 0).test(line));
        require(Tuple2._2testWith((Integer price, Integer quantity) -> price * quantity == subtotal).test(line));
        require(line.map1(price -> price + 50)._1() == 300);
        require(Tuple2.<Integer, Integer, Integer>_2map1With((Integer price) -> price + 50).apply(line)._1() == 300);
        require(Tuple2.<Integer, Integer, Integer>_2map2With((Integer quantity) -> quantity + 1).apply(line)._2() == 5);
        require(Tuple2._2mapWith((Integer price) -> price.toString(), (Integer quantity) -> quantity.toString()).apply(line)._1().equals("250"));

        Tuple3<Integer, Integer, Integer> discounted = Tuple3.with1(250)._2(4).and3(100);
        TriFunction<Integer, Integer, Integer, Integer> total = (price, quantity, discount) -> price * quantity - discount;
        TriPredicate<Integer, Integer, Integer> acceptable = (price, quantity, discount) -> price * quantity >= discount;
        int payable = discounted.map(total);
        require(discounted.is(acceptable));
        require(discounted.is(price -> price > 0, quantity -> quantity > 0, discount -> discount >= 0));
        require(Tuple3._3mapWith(total).apply(discounted) == payable);
        require(Tuple3._3testWith(acceptable).test(discounted));
        require(Tuple3._3testAnyWith((Integer price) -> price > 0, (Integer quantity) -> quantity > 10, (Integer discount) -> discount > 100).test(discounted));
        require(Tuple3._3testAllWith((Integer price) -> price > 0, (Integer quantity) -> quantity > 0, (Integer discount) -> discount >= 0).test(discounted));
        require(discounted.map1(price -> price + 1)._1() == 251);
        require(discounted.map2(quantity -> quantity + 1)._2() == 5);
        require(discounted.map3(discount -> discount + 1)._3() == 101);
        require(Tuple3.<Integer, Integer, Integer, Integer>_3map1With((Integer price) -> price + 1).apply(discounted)._1() == 251);
        require(Tuple3.<Integer, Integer, Integer, Integer>_3map2With((Integer quantity) -> quantity + 1).apply(discounted)._2() == 5);
        require(Tuple3.<Integer, Integer, Integer, Integer>_3map3With((Integer discount) -> discount + 1).apply(discounted)._3() == 101);
        require(Tuple3._3mapWith((Integer price) -> price.toString(), (Integer quantity) -> quantity.toString(), (Integer discount) -> discount.toString()).apply(discounted)._3().equals("100"));
        require(discounted.map(Object::toString, Object::toString, Object::toString)._2().equals("4"));
        require(Tuple3.with1(250)._2and3(4, 100).map(total) == payable);
        require(Tuple3.with1_2(250, 4).and3(100).map(total) == payable);
        require(Tuple3.tuple(250, 4, 100).map(total) == payable);
        require(Tuple3.tuple3(250, 4, 100).map(total) == payable);
        require(new Tuple3<>(250, 4, 100).map(total) == payable);
        require(Tuple2.tuple(250, 4).map(Integer::sum) == 254);
        require(Tuple2.tuple2(250, 4)._1() == 250);
        require(new Tuple2<>(250, 4)._2() == 4);
        require(Tuple2.tuple2(null, 4).is(Objects::isNull, quantity -> quantity == 4));
        require(!line.equals(Tuple2.tuple2(250, 4)));

        // 型の異なる変換を、タプルの宣言型に合わせたラムダで指定する。
        Function<Number, String> asText = Object::toString;
        require(line.map1(value -> asText.apply(value))._1().equals("250"));
        require(subtotal == 1000 && payable == 900);
        System.out.println("subtotal=1000; payable=900; tuple-api=OK");
    }

    private static void require(boolean condition) {
        if (!condition) throw new AssertionError("Tuple example contract failed");
    }
}
