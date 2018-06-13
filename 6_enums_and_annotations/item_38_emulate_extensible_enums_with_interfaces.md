# Item 38: Emulate extensible enums with interfaces

[Example](../main/src/java/com/effectivejava/ch06_enums_annotations/Item38_ExtensibleEnum.java)


It's in possible to have one enumerated type extend another. Why?
- It is confusing that elements of an extension type are instances of the base type and not vice versa. 
- There is no good way to enumerate over all of the elements of a base type and its extensions. 
- Extensibility would complicate many aspects of the design and implementation.

## Extensible enumerated types

But there is at least one compelling use case for extensible enumerated types, which is operation codes, also known as opcodes.

The basic idea is to take advantage of the fact that **enum types can implement arbitrary interfaces**

```java
   /**
     * Emulated extensible enum using an interface
     */
    public interface Operation {
        double apply(double x, double y);
    }

    /**
     * This is the enum in Item34.
     */
    enum BasicOperation implements Operation {
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

        @Override public String toString() {
            return symbol;
        }

        public abstract double apply(double x, double y);
    }
```

While the enum type (BasicOperation) is not extensible, the interface type (Operation) is.

### How to extend it?

```java
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
```

### How to pass in an entire extension enum type?

Solution #1: `bounded type token` 

```java
    /**
     * Pass in an entire extension enum type:
     * Solution #1: `bounded type token`
     */
    private static <T extends Enum<T> & Operation> void test( Class<T> opEnumType, double x, double y) {
        for (Operation op : opEnumType.getEnumConstants()) {
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
    }
```

Solution #2: `bounded wildcard type`
```java
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
            test2(Arrays.asList(ExtendedOperation.values()), x, y);
    
    //        5.000000 ^ 2.000000 = 25.000000
    //        5.000000 % 2.000000 = 1.000000
    //        5.000000 / 2.000000 = 2.500000
            List<Operation> operations = new ArrayList<>();
            operations.addAll(Arrays.asList(ExtendedOperation.values()));
            operations.add(BasicOperation.DIVIDE);
            test2(operations, x, y);
        }
```


Solution #2 is a bit more flexible: it allows the caller to combine operations from multiple implementation types. 

## Disadvantages
- implementations cannot be inherited from one enum type to another.
- so it may have duplicate code, like `symbol`.
> If there were a larger amount of shared functionality, you could encapsulate it in a helper class or a static helper method to eliminate the code duplication.

## Summary
- while you cannot write an extensible enum type, you can emulate it by writing an interface to accompany a basic enum type that implements the interface. 
- This allows clients to write their own enums (or other types) that implement the interface. 
- Instances of these types can then be used wherever instances of the basic enum type can be used, assuming APIs are written in terms of the interface.

