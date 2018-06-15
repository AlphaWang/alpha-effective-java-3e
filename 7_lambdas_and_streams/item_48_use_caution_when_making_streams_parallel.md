# Item 48: Use caution when making streams parallel

## Premature optimization is the root of all evil -- DonaldKnuth

```java

// Stream-based program to generate the first 20 Mersenne primes
   public static void main(String[] args) {
       primes().map(p -> TWO.pow(p.intValueExact()).subtract(ONE))
           .filter(mersenne -> mersenne.isProbablePrime(50))
           .limit(20)
           .forEach(System.out::println);
}
   static Stream<BigInteger> primes() {
       return Stream.iterate(TWO, BigInteger::nextProbablePrime);
}

```
#### parallelizing a pipeline is unlikely to increase its performance if the source is from Stream.iterate, or the intermediate operation limit is used.
- Do not parallelize stream pipelines indiscriminately
- Forkjoin pool works based on split mechanism.
- performance gains from parallelism are best on streams over ArrayList, HashMap, HashSet, and ConcurrentHashMap instances; arrays; int ranges; and long ranges.

### How to gain parallel performance
  - they( ArrayList, HashMap, HashSet, and ConcurrentHashMap instances; arrays; int ranges; and long ranges.) can all be accurately and cheaply split into subranges of any desired sizes, which makes it easy to divide work among parallel threads.
  -  The abstraction used by the streams library to perform this task is the **spliterator**, which is returned by the spliterator method on Stream and Iterable.
  - locality of reference is best fit for parallel stream. Primitive arrays are best at locality. If data is placed togheter with in a block it greatly increases hit ratio of processor cache.
  - Reduction,(min,max,count,sum) and *short circuit* operation like anyMatch,allMatch,noneMatch are best fit for parallel stream.
  - Collect terminal operation is not good, overhead of combining is costly.
  - To gain best parallel performance, best tune splitIterator method. 
 
### Not only can parallelizing a stream lead to poor performance, including liveness failures; it can lead to incorrect results and unpredictable behavior
- Correctness of a program can be harmed with parallel stream
  - There are also strict contracts,requirements
    - associative (2x(3x4) = (2x3)x4) order of applying operation shouldn't change result
    - non-interfering : Operations should not mutate data will be accessed by further operation
    - stateless : Its return value should depend on only its input
- To preserve order of result, use ..ordered operation like forEachOrdered

### you must test the performance before and after the change to ensure that it is worth doing
- you must test the performance before and after the change to ensure that it is worth doing 
- you should perform the test in a realistic system setting.
 
### Under the right circumstances, it is possible to achieve near-linear speedup in the number of processor cores simply by add- ing a parallel call to a stream pipeline

```java
// Prime-counting stream pipeline - benefits from parallelization
   static long pi(long n) {
       return LongStream.rangeClosed(2, n)
           .mapToObj(BigInteger::valueOf)
           .filter(i -> i.isProbablePrime(50))
           .count();
}
//31 seconds
```

```java
// Prime-counting stream pipeline - parallel version
   static long pi(long n) {
       return LongStream.rangeClosed(2, n)
}
.parallel()
.mapToObj(BigInteger::valueOf)
.filter(i -> i.isProbablePrime(50))
.count();

//9.2 seconds
```
- Why It was fast? I guess.... 
  - LongStream (easy to split)
  - Reduction(count) 
  - No Limit Operator
  - Locality Of Reference (Data is contiguous) 
  
### If you are going to parallelize a stream of random numbers, start with a SplittableRandom instance rather than a ThreadLocalRandom
- SplittableRandom is designed for precisely this use
- ThreadLocalRandom is designed for use by a single thread  


## In conclusion, Just Don't Do It

### Find Correctness bug in below code
```java
List<Entity> saved = 
IntStream.range(0, 100).
.parallel().mapToObj(pk -> {
	Entity entity = new Entity();
	entity.setId(pk);
	entity.setCreatedAt(new Date());
	entity.setModifiedAt(new Date());
	BackofficeAuthentication authentication = SecurityContext.getAuthentication();
	entity.setCreatedBy(authentication.getName());
	entity.setModifiedBy(authentication.getName());
	repoistory.save(entity);
}).collect(toList());
```
- This code runs in Spring Environment
- Some entities were saved sucessfully, others were not.
- Why.....?