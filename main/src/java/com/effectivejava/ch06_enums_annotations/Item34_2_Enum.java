package com.effectivejava.ch06_enums_annotations;

public class Item34_2_Enum {

    /**
     * Enum type that switches on its own value - questionable
     */
    enum Operation1 {

        PLUS, MINUS, TIMES, DIVIDE;

        // Do the arithmetic operation represented by this constant
        public double apply(double x, double y) {
            switch (this) {
                case PLUS:
                    return x + y;
                case MINUS:
                    return x - y;
                case TIMES:
                    return x * y;
                case DIVIDE:
                    return x / y;
            }
            throw new AssertionError("Unknown op: " + this);
        }
    }

    /**
     * Enum type with constant-specific method implementations.
     * associate different behaviour with each enum.
     */
    enum Operation2 {
        PLUS {
            public double apply(double x, double y) {
                return x + y;
            }
        }, MINUS {
            public double apply(double x, double y) {
                return x - y;
            }
        }, TIMES {
            public double apply(double x, double y) {
                return x * y;
            }
        }, DIVIDE {
            public double apply(double x, double y) {
                return x / y;
            }
        };

        public abstract double apply(double x, double y);
    }

    /**
     * Enum type with constant-specific class bodies and data
     * associate different data with each enum.
     */
    enum Operation3 {
        PLUS("+") {
            public double apply(double x, double y) {
                return x + y;
            }
        }, MINUS("-") {
            public double apply(double x, double y) {
                return x - y;
            }
        }, TIMES("*") {
            public double apply(double x, double y) {
                return x * y;
            }
        }, DIVIDE("/") {
            public double apply(double x, double y) {
                return x / y;
            }
        };

        private final String symbol;

        Operation3(String symbol) {
            this.symbol = symbol;
        }

        @Override public String toString() {
            return symbol;
        }

        public abstract double apply(double x, double y);
    }
    
}


