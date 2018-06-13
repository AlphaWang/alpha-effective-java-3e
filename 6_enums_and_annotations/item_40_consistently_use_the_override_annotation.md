# Item 40: Consistently use the Override annotation

## Background

Can you spot the bug?

```java
    public static class Bigram {
        
        private final char first;
        private final char second;
        
        public Bigram(char first, char second) {
            this.first  = first;
            this.second = second;
        }
        
        public boolean equals(Bigram b) {
            return b.first == first && b.second == second;
        }
        
        public int hashCode() {
            return 31 * first + second;
        }
        
        public static void main(String[] args) {
            Set<Bigram> s = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    s.add(new Bigram(ch, ch));
                }
            }
            
            //260, NOT 26 
            System.out.println(s.size());
        }
    }

```

Clearly, the author of the `Bigram` class intended toLuckily, the compiler can help you find this error, but only if you help it by telling it that you intend to override Object.equals. override the equals method (Item 10) and even remembered to override hashCode in tandem (Item 11).   
Unfortunately, our hapless programmer failed to override equals, overloading it instead (Item 52).

## @Override
Luckily, the compiler can help you find this error, but only if you help it by telling it that you intend to override Object.equals.

```
Bigram.java:10: method does not override or implement a method from a supertype
       @Override public boolean equals(Bigram b) {
       ^
```
You will immediately realize what you did wrong.


## Summary

- the compiler can protect you from a great many errors if you use the Override annotation on every method declaration that you believe to override a supertype declaration, 
- with one exception: In concrete classes, you need not annotate methods that you believe to override abstract method declarations (though it is not harmful to do so).
    > In an abstract class or an interface, however, it is worth annotating all methods that you believe to override superclass or superinterface methods, whether con- crete or abstract.