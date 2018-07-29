package com.effectivejava.ch08_method;


public class Varargs {

    static int sum(int... args){
        int sum = 0;
        for (int arg : args){
            sum += arg;
        }
        return sum;
    }

    public static void main(String[] args){
        System.out.println(sum());
    }
}
