# Item 45: Use streams judiciously

  - Stream API is *lazily* evaluated
  
  ```java
  IntStream.range(Integer.MIN_VALUE, Integer.MAX_VALUE)
    			.limit(10)
    			.forEach(System.out::println);  
  ```
  - Stream API is *fluent*
  - Stream API is *versatile*
   

```java
	// Prints all large anagram groups in a dictionary iteratively
	// isAnagram("12345", "54321").isTrue()
	// isAnagram("abcd", "dbc").isFalse()
	
	public class Anagrams {
		public static void main(String[] args) throws IOException {
			File dictionary = new File(args[0]);
			int minGroupSize = Integer.parseInt(args[1]);
			Map<String, Set<String>> groups = new HashMap<>();
			try (Scanner s = new Scanner(dictionary)) {
				while (s.hasNext()) {
					String word = s.next();
					groups.computeIfAbsent(alphabetize(word),
						(unused) -> new TreeSet<>()).add(word);
				}
			}
			for (Set<String> group : groups.values())
				if (group.size() >= minGroupSize)
					System.out.println(group.size() + ": " + group);
		}

		private static String alphabetize(String s) {
			char[] a = s.toCharArray();
			Arrays.sort(a);
			return new String(a);
		}
	}

```

```java
// Overuse of streams - don't do this!
	public class Anagrams {
		public static void main(String[] args) throws IOException {
			Path dictionary = Paths.get(args[0]);
			int minGroupSize = Integer.parseInt(args[1]);
			try (Stream<String> words = Files.lines(dictionary)) {
				words.collect(
					groupingBy(word -> word.chars().sorted()
						.collect(StringBuilder::new,
							(sb, c) -> sb.append((char) c),
							StringBuilder::append).toString()))
					.values().stream()
					.filter(group -> group.size() >= minGroupSize)
					.map(group -> group.size() + ": " + group)
					.forEach(System.out::println);
			}
		}
	}

```

- Shorter, but Hard to read
- Less readable
- Hard to **read and maintain**

```java
// Tasteful use of streams enhances clarity and conciseness
public class Anagrams {
	public static void main(String[] args) throws IOException {
		Path dictionary = Paths.get(args[0]);
		int minGroupSize = Integer.parseInt(args[1]);
		try (Stream<String> words = Files.lines(dictionary)) {
			words.collect(groupingBy(word -> alphabetize(word)))
				.values().stream()
				.filter(group -> group.size() >= minGroupSize).forEach(g -> System.out.println(g.size() + ": " + g));
		}
	}
	// alphabetize method is the same as in original version
}
```

- It's better
- Naming works 
- Careful naming is essential to the readability of stream pipelines (Because that's what we only have!)
-  Using helper methods is even more important for readability in stream pipelines than in iterative code

```java
//When Stream don't work
   "Hello world!".chars().forEach(System.out::print);
  ^721011081081113211911111410810033
```

```java
//Right result, but looks dirty and boring
"Hello world!".chars().forEach(x -> System.out.print((char) x));
```

- you should refrain from using streams to process char values.
- refactor existing code to use streams and use them in new code only where it makes sense to do so


### Lambda is not silver bullet

- From a code block,you can read or modify any local variable in scope;from a lambda, you can only read final or effectively final variables [JLS 4.12.4], and you can’t modify any local variables.
- From a code block, you can return from the enclosing method, break or continue an enclosing loop, or throw any checked exception that this method is declared to throw; from a lambda you can do none of these things.

```java
//code block can break any time
for(Integer number : infinites {
	if(number >= 10){
		return;
	}
} 

//but lambda..?
Stream.iterate(1, x -> x+1)
.forEach(x -> {
	if(x >= 10){
		return; //It doesn't work
	}
}); //Use limit(10) instead of return but it only works when it is sequential stream
```

### Use stream in context where you should apply function to *every* elements

- Uniformly transform sequences of elements
- Filter sequences of elements  
- Combine sequences of elements using a single operation (for example to add them, concatenate them, or compute their minimum)
- ...

### Stream can't access certain elements meanwhile in multiple stages of pipeline

- Mersenne primes : Primes which can be expressed as 2^p -1(let p be prime), for example : 2^3 -1 = 7


```java
//Infinite prime stream
static Stream<BigInteger> primes() {
       return Stream.iterate(TWO, BigInteger::nextProbablePrime);
}

//TWO.pow(x) = 2^x
//Print out first 20 mersenne primes
public static void main(String[] args){
	primes().map(p->TWO.pow(p.intValueExact()).subtract(ONE)) //f(p) = 2^p-1
	.filter(mersenne->mersenne.isProbablePrime(50)) //Check if it is probably prime
	.limit(20)
	.forEach(System.out::println);
	}

```

- Now We have number Mersenne(p) = 2^p -1, but how can we know p?
  - It is in initial stream ( primes() ) and not accessible from terminal stream.
  - Luckily we can get like this, but it needs additional computation
  
  ```java
     .forEach(mp -> System.out.println(mp.bitLength() + ": " + mp));
  ```
  
### Sometimes it is never merely obvious....
```java

// Iterative Cartesian product computation
// CartesianProduct(['1','2','3'],['a','b','c'])
// == ['1a','1b','1c','2a','2b','2c','3a','3b','3c']
private static List<Card> newDeck(){
	List<Card> result=new ArrayList<>();
	for(Suit suit:Suit.values())
	  for(Rank rank:Rank.values())
	      result.add(new Card(suit,rank));
	          return result;
	}

```  

```java
// Stream-based Cartesian product computation
   private static List<Card> newDeck() {
       return Stream.of(Suit.values())
       .flatMap(suit -> Stream.of(Rank.values())
       .map(rank -> new Card(suit, rank))) .collect(toList());
```


- Which is better? case by case, personal preference

##  If you’re not sure whether a task is better served by streams or iteration, try both and see which works better.  