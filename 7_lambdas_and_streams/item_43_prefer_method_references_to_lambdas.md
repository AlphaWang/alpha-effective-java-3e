# Item 43: Prefer method references to lambdas

## Pros
- It is clearer to read.

```java
//boring
map.merge(key, 1, (count, incr) -> count + incr);
```

```java
//fun
map.merge(key, 1, Integer::sum);
```

- Sometimes lambda is shorter, then don't hesitate to use lambda instead.
```java
//boring
service.execute(GoshThisClassNameIsHumongous::action);
...collect(Collectiors.toMap(SomeClass::getName,Function.identity()); 
```

```java
//fun
service.execute(() -> action());
...collect(Collectiors.toMap(SomeClass::getName,x -> x);
```

- Method reference types
  - Static
    - ``Integer::parseInt`` -> ``str -> Integer.parseInt(str)``
  - Bound
    - ``Instant.now)::isAfter`` -> ``Instant then = Instant.now(); t -> then.isAfter(t);``
  - Unbound
    - ``String::toLowerCase`` -> ``str -> str.toLowerCase()``
  - Class Constructor
    - ``TreeMap<K,V>::new`` -> ``() -> new TreeMap<K,V>``
  - Array Constructor
    - ``int[]::new`` -> ``len -> new int[len]``
    
    
### In summary, method references often provide a more succinct alternative to lambdas. Where method references are shorter and clearer, use them; where they aren’t, stick with lambdas.         