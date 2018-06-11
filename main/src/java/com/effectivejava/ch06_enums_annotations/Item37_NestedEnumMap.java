package com.effectivejava.ch06_enums_annotations;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class Item37_NestedEnumMap {

    /**
     * Using ordinal() to index array of arrays - DON'T DO THIS!
     *
     * this program uses such an array to map two phases to a phase transition.
     *
     *  **Disadvantages**
     *
     *  - the compiler has no way of knowing the relationship between ordinals and array indices.
     *  - If you make a mistake in the transition table or forget to update it when you modify the `Phase` or `Phase.Transition` enum type, your program will fail at runtime.
     *  - the size of the table is quadratic in the number of phases, even if the number of non-null entries is smaller.
     */
    public enum Phase {
        
        SOLID, LIQUID, GAS;

        public enum Transition {
            MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

            // Rows indexed by from-ordinal, cols by to-ordinal
            private static final Transition[][] TRANSITIONS = {
                { null, MELT, SUBLIME },
                { FREEZE, null, BOIL },
                { DEPOSIT, CONDENSE, null } };

            // Returns the phase transition from one phase to another
            public static Transition from(Phase from, Phase to) {
                return TRANSITIONS[from.ordinal()][to.ordinal()];
            }
        }
    }

    /**
     * Using a nested EnumMap to associate data with enum pairs.
     *
     * If the relationship you are representing is multidimensional, use EnumMap<..., EnumMap<...>>
     *
     * Because each phase transition is indexed by a pair of phase enums,
     * you are best off representing the relationship as a map from one enum (the “from” phase)
     * to a map from the second enum (the “to” phase) to the result (the phase transition).
     */
    public enum Phase2 {

        SOLID, LIQUID, GAS;

        public enum Transition {
            MELT(SOLID, LIQUID), FREEZE(LIQUID, SOLID),
            BOIL(LIQUID, GAS),   CONDENSE(GAS, LIQUID),
            SUBLIME(SOLID, GAS), DEPOSIT(GAS, SOLID);

            private final Phase2 from;
            private final Phase2 to;

            Transition(Phase2 from, Phase2 to) {
                this.from = from;
                this.to = to;
            }

            // Initialize the phase transition map
            private static final Map<Phase2, Map<Phase2, Transition>> m = Stream.of(values()).collect(groupingBy(
                t -> t.from,
                () -> new EnumMap<>(Phase2.class),
                toMap(t -> t.to, t -> t, (x, y) -> y, () -> new EnumMap<>(Phase2.class))));

            // Initialize the phase transition map (< Java8)
            private static final Map<Phase2, Map<Phase2, Transition>> m2 = new EnumMap<>(Phase2.class);
            static {
                for (Phase2 p : Phase2.values()) {
                    m2.put(p, new EnumMap<>(Phase2.class));
                }
                for (Transition trans: Transition.values()) {
                    m2.get(trans.from).put(trans.to, trans);
                }
            }
            
            public static Transition from(Phase2 from, Phase2 to) {
                return m.get(from).get(to);
            }
        }
    }
}
