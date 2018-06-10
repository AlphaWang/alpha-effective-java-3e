package com.effectivejava.ch06_enums_annotations;

public class Item34_1_IntConstantPattern {

    // The int enum pattern - severely deficient!
    public static final int APPLE_FUJI = 0;
    public static final int APPLE_PIPPIN = 1;
    public static final int APPLE_GRANNY_SMITH = 2;

    public static final int ORANGE_NAVEL = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD = 2;

    public static double calculate(int orangeType) {
        return (orangeType = ORANGE_NAVEL) / ORANGE_NAVEL;
    }

    public static void main(String[] args) {
        // The compiler won’t complain if you pass an apple to a method that expects an orange
        double result = calculate(APPLE_FUJI);
    }
}
