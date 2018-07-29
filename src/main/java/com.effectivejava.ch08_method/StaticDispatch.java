package com.effectivejava.ch08_method;

public class StaticDispatch {
    static class Human{}
    static class Man extends Human{}
    static class Woman extends Human{}

    public static void sayHello(Human human){System.out.println("human");}
    public static void sayHello(Man man){System.out.println("man");}
    public static void sayHello(Woman woman){System.out.println("woman");}
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        sayHello(man);
        sayHello(woman);
    }
}
