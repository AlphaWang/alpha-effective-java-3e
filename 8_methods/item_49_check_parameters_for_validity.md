# Item 49: Check parameters for validity

##### Bad Code Sample
```$xslt
    /**
     *
     * Return a BigInteger Whose value is (this mod m). This method
     * differs from the remainder method in that it always return a 
     * non-negative BigInteger
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
```
* <b>NullPointException</b> if the m is null 
* <b>ArithmeticException</b> if the m <= 0

> <h4>Solution 1: add annotation @NotNull</h4> 
      code logic is verified when IDE is compiling.<p>
  <h4>Solution 2: Objects.requireNonNull</h4>
      if the t is null will throw exception<br>
      Objects.requireNonNull(T t, String message), edit the error message.
  <h4>Solution 3: assert check</h4></br>

##### Code Sample
```$xslt
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
    public BigInteger mod(/* solution 1 @NotNull*/ BigInteger m){
        // solutions 2 m = Objects.requireNonNull(m, "the value of m is null");
        /* solutions 3 */ assert m !=null;
        
        if (m.signum() <= 0){
            throw new ArithmeticException("Modulus <= 0" + m);
        }
        return m.mod(new BigInteger("10"));
    }
```
<hr>
<h4>To the method producer</h4>

* Document the restrictions of the method.
* Enforce them with explicit checks at the beginning of the method body.

<h4>To the method consumer</h4>

* Know the restrictions of the method which you will use clearly.

<a href="./item_50_make_defensive_copies_when_needed.md">next item</a>