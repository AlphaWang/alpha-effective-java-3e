package com.effectivejava.ch06_enums_annotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Item38_ExtensibleEnum {

    /**
     * Emulated extensible enum using an interface
     */
    public interface Operation {
        double apply(double x, double y);
    }

    /**
     * This is the enum in Item34.
     */
    public enum BasicOperation implements Operation {
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

        BasicOperation(String symbol) {
            this.symbol = symbol;
        }

        @Override 
        public String toString() {
            return symbol;
        }

        public abstract double apply(double x, double y);
    }

    /**
     * Emulated extension enum
     */
    public enum ExtendedOperation implements Operation {
        EXP("^") {
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        },
        REMAINDER("%") {
            public double apply(double x, double y) {
                return x % y; }
        };
        
        private final String symbol;
        
        ExtendedOperation(String symbol) {
            this.symbol = symbol;
        }
        @Override 
        public String toString() {
            return symbol;
        } 
    }

    /**
     * Pass in an entire extension enum type:
     * Solution #1: `bounded type token`
     */
    private static <T extends Enum<T> & Operation> void test( Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }

    /**
     * Pass in an entire extension enum type:
     * Solution #2: `bounded wildcard type`
     * 
     * It's 
     */
    private static void test2(Collection<? extends Operation> opSet, double x, double y) {
        for (Operation op : opSet) {
            System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));
        }
    }
   

    public static void main(String[] args) { 
        double x = 5; // Double.parseDouble(args[0]); 
        double y = 2; // Double.parseDouble(args[1]);

//        5.000000 ^ 2.000000 = 25.000000
//        5.000000 % 2.000000 = 1.000000
        test(ExtendedOperation.class, x, y);
//        5.000000 + 2.000000 = 7.000000
//        5.000000 - 2.000000 = 3.000000
//        5.000000 * 2.000000 = 10.000000
//        5.000000 / 2.000000 = 2.500000
        test(BasicOperation.class, x, y);

//        5.000000 ^ 2.000000 = 25.000000
//        5.000000 % 2.000000 = 1.000000
        test2(Arrays.asList(ExtendedOperation.values()), x, y);

//        5.000000 ^ 2.000000 = 25.000000
//        5.000000 % 2.000000 = 1.000000
//        5.000000 / 2.000000 = 2.500000
        List<Operation> operations = new ArrayList<>();
        operations.addAll(Arrays.asList(ExtendedOperation.values()));
        operations.add(BasicOperation.DIVIDE);
        test2(operations, x, y);
    }
}
