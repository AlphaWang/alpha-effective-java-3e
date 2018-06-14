# Item 42: Prefer lambdas to anonymous classes



##prefer lambdas to anonymous class
```java
  //Bad
  Collections.sort(words, new Comparator<String>() {
       public int compare(String s1, String s2) {
           return Integer.compare(s1.length(), s2.length());
       }
});
```
``` java
  //Good
  Collections.sort(words,
  (s1, s2) -> Integer.compare(s1.length(), s2.length()));
```

- why anonymous class is **obsolete**?
  - lambda is more **concise** and evident
  - Utilize type inference, omit the type if it is clear
  
  
  ```java
  //Better
  Collections.sort(words, comparingInt(String::length));
  //Use sort method in List interface
  words.sort(comparingInt(String::length));
```  


##Explicit is better than implicit. ##


```java
// Decent but verbose
// Enum type with constant-specific class bodies & data (Item 34)
   public enum Operation {
       PLUS("+") {
public double apply(double x, double y) { return x + y; } },
MINUS("-") {
public double apply(double x, double y) { return x - y; }
       },
       TIMES("*") {
public double apply(double x, double y) { return x * y; } },
DIVIDE("/") {
public double apply(double x, double y) { return x / y; }
};
       private final String symbol;
       Operation(String symbol) { this.symbol = symbol; }
       @Override public String toString() { return symbol; }
       public abstract double apply(double x, double y);
   }

```
```java
// Enum with function object fields & constant-specific behavior
public enum Operation {
PLUS ("+",(x,y)->x+y),
MINUS ("-", (x, y) -> x - y),
TIMES ("*", (x, y) -> x * y),
DIVIDE("/", (x, y) -> x / y);
       private final String symbol;
       private final DoubleBinaryOperator op;
       Operation(String symbol, DoubleBinaryOperator op) {
           this.symbol = symbol;
           this.op = op;
}
       @Override public String toString() { return symbol; }
       public double apply(double x, double y) {
           return op.applyAsDouble(x, y);
} }
```

##Caveat 
- Lambda doesn't have **name** and **documentation**
  - Use lambda only it ends with a line and self-explanatory, or **don't use it**
  - Three line is **maximum**
  
```java

  //Bad Name, Bad readability 
  List<String> tokens = Arrays.asList("a", "ab", "abc", "abcd", "abcde", "b", "bc", "bcd", "bcde");
  List<String> reversedStartingWithB = Lists.newArrayList();
  
  tokens.stream().filter(x -> x.startsWith("b")).forEach(x -> {
  	StringBuilder stringBuilder = new StringBuilder();
  	for(int i = x.length() ; i >= 0 ; i-- ){
  		stringBuilder.append(x.charAt(i));
  	}
  	reversedStartingWithB.add(stringBuilder.toString());
  })
  
  
  
```  


```java
  //Reasonable parameter name, Brevity with shorter line, No stupid for each
  List<String> tokens = Arrays.asList("a", "ab", "abc", "abcd", "abcde", "b", "bc", "bcd", "bcde");
    List<String> reversedStartingWithB = tokens
    .stream().filter(token -> token.startsWith("b"))
    .map(CoupangStringUtils::reverse).collect(toList());

```

- What anonymous class can do
  - Use When you instantiate abstract class
  - Lambda never reach references of itself
    - this means enclosing instance 
    
    
```java

public abstract class TestAbstractClass {
		public abstract String speakLoudly(Integer i);
		public abstract String toString();
		public abstract String printThis();
		public abstract String printLambdaThis();
	}


	@Test
	public void anonymousClassCanInstantiateAbstractClass(){
		TestAbstractClass testAbstractClass = new TestAbstractClass() {
			@Override
			public String speakLoudly(Integer i) {
				return "Speak loudly !!!! volume : " + i;
			}

			@Override
			public String toString() {
				return "I am AnonymousAbstractClass!";
			}

			@Override
			public String printThis() {
				return this.toString();
			}

			@Override
			public String printLambdaThis() {
				Supplier<String> lambdaThis = (this::toString);
				return lambdaThis.get();
			}
		};
		Assertions.assertThat(testAbstractClass.speakLoudly(100)).isEqualTo("Speak loudly !!!! volume : 100");
		Assertions.assertThat(testAbstractClass.printThis()).isEqualTo("I am AnonymousAbstractClass!");
		Assertions.assertThat(testAbstractClass.printLambdaThis()).isEqualTo("I am AnonymousAbstractClass!");

	}

```    

##In summary, as of Java 8, lambdas are by far the best way to represent small function objects. Don’t use anonymous classes for function objects unless you have to create instances of types that aren’t functional interfaces. Also, remember that lambdas make it so easy to represent small function objects that it opens the door to functional programming techniques that were not previously practical in Java.