# Item 46: Prefer side-effect-free functions in streams


### Dillema
- pure function : Its result depends on only input 
- lambda expression came from mathematical concepts
- In mathematics, function is *pure function* in nature  
  - f(x) = x+1; return value is only depends on x
- A Computer consists of <Memory, Instruction, Calculator>
- Function; method in computer science can be side effect free or not;
  - IntFunction<Integer> pureMethod= (x -> x+1), side effect free
  
  ```java
    Random<Integer> randomizer;
    IntFunction<Integer> nonPureMethod = (x -> x + randomizer.nextInt());
  ```
  - This has side effect; return value doesn't depend on input value
- If we call pure function a million times, result will be same always while we call with same input
- If we call the latter function a million times, Return value changes everytime        
- Lambda expression came from mathematical background but implemented in computer programming language. What should we do?

### Prefer side-effect-free functions(lambda expression) in streams

- Stick with pure function in every stage of pipeline.
- Parallelism benefits by pure functional expression
- Prevent malfunctions 

```java
   // Uses the streams API but not the paradigm--Don't do this!
   Map<String, Long> freq = new HashMap<>();
   try (Stream<String> words = new Scanner(file).tokens()) {
       words.forEach(word -> {
           freq.merge(word.toLowerCase(), 1L, Long::sum);
}); }
```
- It mutates external state ; Map<String, Long> freq
- What if it is parallel stream? It works with luck, It stuck without luck
- The forEach operation should be used only to report the result of a stream computation, not to perform the computation

```java

   // Proper use of streams to initialize a frequency table
   Map<String, Long> freq;
   try (Stream<String> words = new Scanner(file).tokens()) {
       freq = words
           .collect(groupingBy(String::toLowerCase, counting()));
    }
```

- It is shorter and clearer.
- Stream itself explains.


### GIST: Do not use forEach for computation, only for report of computation, stick with collectors like joining, toMap, grouppingBy!