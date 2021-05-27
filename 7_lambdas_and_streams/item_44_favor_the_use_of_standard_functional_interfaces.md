# Item 44: Favor the use of standard functional interfaces

### Do not invent wheel again, Stick with standard functional interface as you can

- Standard Functional Interfaces 
  - UnaryOperator `T apply(T t)` 
  - BinaryOperator<T> `T apply(T t1, T t2)`
  - Predicate<T> `boolean test(T t)`
  - Function<T,R> `R apply(T t)`
  - Supplier<T> `T get()`
  - Consumer<T> `void accept(T t)`
  - ...primitive types interfaces 

```java
// AS-IS
   protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
      return size() > 100;
}
```

```java
  // Unnecessary functional interface
    interface EldestEntryRemovalFunction<K,V>{
         boolean remove(Map<K,V> map, Map.Entry<K,V> eldest);
  }
```


```java 
  //Prefer to use standard functional interfaces
  interface BiPredicate<Map<K,V>, Map.Entry<K,V>> EldestEntryRemovalFunction;
```



- If one of the standard functional interfaces does the job, you should generally use it in preference to a purpose-built functional interface
  - make your API easier to learn,Reduce cognitive load when reading code(I know what BiPredicate does!)
  - interoperability benefit (Like design pattern!)


- ### When to write your own? ###
  - Of course you need to write your own if none of the standard ones does what you need
  - For example, you require a predicate that takes three parameters
  - one that throws a checked exception
  - When you need **explicit name** to symbolize what modules do, this is strong reason

### Comparator vs ToIntBiFunction<T,T>
```java
@FunctionalInterface
public interface ToIntBiFunction<T, U> {
    int applyAsInt(T t, U u);
}
```

```java
public interface Comparator<T> {
    int compare(T o1, T o2);
    }

```

- Why Comparator was implemented by itself?
  - Its name itself provides excellent documentation 
  - the Comparator interface has strong requirements on what constitutes a valid instance (Transitive, Reflexive, Symmetric, Consistent) 
  - the interface is heavily outfitted with useful default methods to transform and combine comparators 
  
- Lesson from Comparator
  - It will be commonly used and could benefit from a descriptive name.
  - It has a strong contract associated with it.
  - It would benefit from custom default methods.

### Don’t be tempted to use basic functional interfaces with boxed primitives instead of primitive functional interfaces.
```java
//Stupid
long currency = 1000;
long dollors = 125;
Function<Long, Long> dollarToWon = x -> x * currency;
long myDollorToWon = dollarToWon.apply(dollors);
```

```java
//smart
long currency = 1000;
long dollors = 125;
LongFunction<Long> dollarToWon = x -> x * currency;
long myDollorToWon = dollarToWon.apply(dollors);
```
```java
@FunctionalInterface
public interface LongFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(long value);
}
```

### Always annotate your functional interfaces with the @FunctionalInterface annotation.
- It is a statement of programmer intent that serves three purposes
  -  it tells readers of the class and its documentation that the interface was designed to enable lambdas
  - it keeps you honest because the interface won’t compile unless it has exactly one abstract method
  - it prevents maintainers from accidentally adding abstract methods to the interface as it evolves


### Do not provide a method with multiple overloadings that take different functional interfaces in the same argument position if it could create a possible ambiguity in the client.

 ```java
    public interface ExecutorService extends Executor {
      <T> Future<T> submit(Callable<T> task);
      Future<?> submit(Runnable task);
    }
```
```java

```
```java
	@Test
	public void FunctionalInterfaceOverloadingSucks() {
		ExecutorService executorService = Executors.newFixedThreadPool(10); Callable<Void> foo = () -> { System.out.println("bar"); return null;};
    		executorService.submit((Runnable)foo);
	}
	^ No Compile Error : Even no unchecked warning
	^ But Runtime Error : ClassCastException(java.lang.ClassCastException: com.xx.productreview.interfaces.common.TestUtils$$Lambda$1/1121453612 cannot be cast to java.lang.Runnable) 
```

## Stick with Standard functional interfacces but keep your eyes open to the exceptional case like Comparator