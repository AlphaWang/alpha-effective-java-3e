package com.effectivejava.ch08_method;



import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.Objects;

public class Item49 {

    /**
     *
     * Return a BigInteger Whose value is (this mod m). This method
     * differs from the remainder method in that it always return a non-negative BigInteger
     *
     * @param m the modulus, which must be positive
     * @return this mod m
     * @throws ArithmeticException if m is less than or equal to 0
     */
    public BigInteger mod(BigInteger m){
        if (m.signum() <= 0){
            throw new ArithmeticException("Modulus <= 0" + m);
        }
        return m.mod(new BigInteger("10"));
    }

    /*@NotNull/@Nullable*/
    /**
     *
     * Return a BigInteger Whose value is (this mod m). This method
     * differs from the remainder method in that it always return a
     * non-negative BigInteger
     *
     * @param m the modulus, m can not be null, which must be positive
     * @return this mod m
     * @throws NullPointerException if m is null
     * @throws ArithmeticException if m is less than or equal to 0
     */
    public BigInteger Mod(/* solution 1 @NotNull*/ BigInteger m){
        // solutions 2 m = Objects.requireNonNull(m, "the value of m is null");
        /* solutions 3*/ assert m !=null;

        if (m.signum() <= 0){
            throw new ArithmeticException("Modulus <= 0" + m);
        }
        return m.mod(new BigInteger("10"));
    }

    public static void main(String[] args){
        Item49 item49 = new Item49();

        try {
            System.out.println(item49.mod(null));
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            System.out.println(item49.mod(BigInteger.valueOf(0)));
        }catch (ArithmeticException e){
            e.printStackTrace();
        }

        try {
            System.out.println(item49.mod(BigInteger.valueOf(10)));
        }catch (ArithmeticException e){
            e.printStackTrace();
        }

        try {
            System.out.println(item49.Mod(null));
        }catch (ArithmeticException e){
            e.printStackTrace();
        }
    }
}
