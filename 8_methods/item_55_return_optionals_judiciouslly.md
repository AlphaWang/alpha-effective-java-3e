# Item 55: Return optionals judiciously

### Optional

sample code
```java
public staic <E extends Comparable<E>> E max(Collection<E> c){
    if (c.isEmpty()){
        throw new IllegalArgumentException("Empty collection");
    }
    
    E result = null;
    for(E e: c){
        if (result == null || e.compareTo(result) > 0){
            result = Objects.requireNonNull(e);
        }
    }
    return result;
}
```
#####  Optional version<br>
> 1st

```java
public staic <E extends Comparable<E>> Optional<E> max(Collection<E> c){
    if (c.isEmpty()){
        return Optional.empty();
    }
    
    E result = null;
    for(E e: c){
        if (result == null || e.compareTo(result) > 0){
            result = Objects.requireNonNull(e);
        }
    }
    return Optional.valueOf(result);
}
```

> 2st

```java
public staic <E extends Comparable<E>> Optional<E> max(Collection<E> c){
    return c.stream().max(Comparator.naturalOrder());
}
```

> Never return a null value from a ``Optional`` returning method.

<hr>

Example 

> Set default value
> ``String lastWordInLexicon = max(word).orElse("No words ....");``<br>
> Set throw exception 
> ``String lastWordInLexicon = max(word).orElseThrow(NullPointException::new);``<br>
 * orElseGet
 * filter
 * map
 * flatMap
 * ifPresent
 * isPresent
 * get
 
<hr>

> 1. Container types, including collections, maps, streams, arrays<br>
> <b>but should not be the optional</b>
> 2. Declare a method to return Optional<T>. When the clients will have to perform special processing if no result is returned.
> 3. Never return an optional of a boxed primitive type.<br>
> <b> Boolean / Byte / Character /  Short / Float</b>
> 4. Never appropriate to use an optional as a key, value, or element in a collection or array.

<hr>

### To summarize

> If you find yourself writing a method that can't alway return a value and you believe
 it is important tat users of the method consider this possibility every time they call it,
 then you should probably return an optional.<br>
> <b>For performance-critical methods, return null or throw an Exception is better.</b>
