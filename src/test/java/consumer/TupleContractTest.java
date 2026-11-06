package consumer;

import io.github.lambig.tuplite._2.Tuple2;
import io.github.lambig.tuplite._3.Tuple3;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class TupleContractTest {
    @Test void externalBuildersCanBeNamedAndReused() {
        Tuple2.Tuple2_1to2<String> pair = Tuple2.with1("item");
        assertThat(pair.and2(1)._2()).isEqualTo(1);
        assertThat(pair.and2(2)._2()).isEqualTo(2);
        Tuple3.Tuple3_with1<String> triple = Tuple3.with1("item");
        Tuple3.Tuple3_with1and2<String, Integer> priced = triple._2(250);
        assertThat(priced.and3(4)._3()).isEqualTo(4);
        assertThat(priced.and3(5)._3()).isEqualTo(5);
        assertThat(triple._2and3(250, 4)._1()).isEqualTo("item");
        assertThat(Tuple3.with1_2("item", 250).and3(4)._2()).isEqualTo(250);
    }

    @Test void elementsKeepReferencesIncludingNullWithoutValueEquality() {
        List<String> names = new ArrayList<>();
        Tuple2<List<String>, String> pair = new Tuple2<>(names, null);
        Tuple3<List<String>, String, Integer> triple = new Tuple3<>(names, null, 1);
        names.add("changed");
        assertThat(pair._1()).isSameAs(names).containsExactly("changed");
        assertThat(triple._1()).isSameAs(names);
        assertThat(pair._2()).isNull();
        assertThat(triple._2()).isNull();
        assertThat(pair).isNotEqualTo(new Tuple2<>(names, null));
        assertThat(triple).isNotEqualTo(new Tuple3<>(names, null, 1));
        assertThat(pair.map1(value -> null)._1()).isNull();
        assertThat(triple.map3(value -> null)._3()).isNull();
    }

    @Test void mappingKeepsPositionsAndStopsOnTheOriginalException() {
        List<Integer> calls = new ArrayList<>();
        Tuple3<Integer, Integer, Integer> original = Tuple3.tuple3(1, 2, 3);
        Tuple3<Integer, Integer, Integer> changed = original.map(
                x -> { calls.add(1); return x + 10; },
                x -> { calls.add(2); return x + 20; },
                x -> { calls.add(3); return x + 30; });
        assertThat(calls).containsExactly(1, 2, 3);
        assertThat(Arrays.asList(changed._1(), changed._2(), changed._3())).containsExactly(11, 22, 33);
        assertThat(original._1()).isEqualTo(1);
        RuntimeException failure = new IllegalStateException("mapping failed");
        calls.clear();
        assertThatThrownBy(() -> original.map(
                x -> { calls.add(1); return x; },
                x -> { throw failure; },
                x -> { calls.add(3); return x; })).isSameAs(failure);
        assertThat(calls).containsExactly(1);
        assertThatThrownBy(() -> Tuple2.tuple2(1, 2).map(
                x -> { throw failure; }, x -> { calls.add(2); return x; })).isSameAs(failure);
        assertThat(calls).containsExactly(1);
    }

    @Test void allConditionsShortCircuitAtFalseIncludingAnUnusedNullPredicate() {
        Predicate<Integer> unused = null;
        assertThat(Tuple2.tuple2(1, 2).is(x -> false, unused)).isFalse();
        assertThat(Tuple3.tuple3(1, 2, 3).is(x -> true, x -> false, unused)).isFalse();
        assertThat(Tuple2._2testAllWith((Integer x) -> false, unused).test(Tuple2.tuple2(1, 2))).isFalse();
        assertThat(Tuple3._3testAllWith((Integer x) -> false, unused, unused).test(Tuple3.tuple3(1, 2, 3))).isFalse();
        assertThatThrownBy(() -> Tuple2.tuple2(1, 2).is(x -> true, unused)).isInstanceOf(NullPointerException.class);
    }

    @Test void pairAnyKeepsBothChecksWhileTripleAnyShortCircuits() {
        List<Integer> calls = new ArrayList<>();
        assertThat(Tuple2._2testAnyWith(
                (Integer x) -> { calls.add(1); return true; },
                (Integer x) -> { calls.add(2); return false; }).test(Tuple2.tuple2(1, 2))).isTrue();
        assertThat(calls).containsExactly(1, 2);
        calls.clear();
        assertThat(Tuple3._3testAnyWith(
                (Integer x) -> { calls.add(1); return true; },
                (Integer x) -> { calls.add(2); return false; },
                (Integer x) -> { calls.add(3); return false; }).test(Tuple3.tuple3(1, 2, 3))).isTrue();
        assertThat(calls).containsExactly(1);
        RuntimeException failure = new IllegalStateException("predicate failed");
        assertThatThrownBy(() -> Tuple2._2testAnyWith((Integer x) -> true, (Integer x) -> { throw failure; })
                .test(Tuple2.tuple2(1, 2))).isSameAs(failure);
    }

    @Test void functionalAdaptersUseTheTupleProvidedAtInvocation() {
        List<Integer> calls = new ArrayList<>();
        java.util.function.Function<Tuple2<Integer, Integer>, Integer> sum = Tuple2._2mapWith((a, b) -> {
            calls.add(a); return a + b;
        });
        assertThat(calls).isEmpty();
        assertThat(sum.apply(Tuple2.tuple2(1, 2))).isEqualTo(3);
        assertThat(sum.apply(Tuple2.tuple2(4, 5))).isEqualTo(9);
        assertThat(calls).containsExactly(1, 4);
    }
}
