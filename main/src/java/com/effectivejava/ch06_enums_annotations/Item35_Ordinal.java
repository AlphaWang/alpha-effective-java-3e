package com.effectivejava.ch06_enums_annotations;

public class Item35_Ordinal {

    // Abuse of ordinal to derive an associated value - DON'T DO THIS
    public enum Ensemble1 {
        SOLO, DUET, TRIO, QUARTET, QUINTET, SEXTET, SEPTET, OCTET, NONET, DECTET;

        public int numberOfMusicians() {
            return ordinal() + 1;
        }
    }

    public enum Ensemble {
        SOLO(1),
        DUET(2),
        TRIO(3),
        QUARTET(4),
        QUINTET(5),
        SEXTET(6),
        SEPTET(7),
        OCTET(8),
        DOUBLE_QUARTET(8),
        NONET(9),
        DECTET(10),
        TRIPLE_QUARTET(12);

        private final int numberOfMusicians;

        Ensemble(int size) {
            this.numberOfMusicians = size;
        }

        public int numberOfMusicians() {
            return numberOfMusicians;
        }
    }

}
