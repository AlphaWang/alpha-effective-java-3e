# Item 37: Use EnumMap instead of ordinal indexing

## Background

[Example](../main/src/java/com/effectivejava/ch06_enums_annotations/Item37_EnumMap.java)

``` java
   class Plant {
        enum LifeCycle {
            ANNUAL, PERENNIAL, BIENNIAL
        }

        final String name;
        final LifeCycle lifeCycle;

        Plant(String name, LifeCycle lifeCycle) {
            this.name = name;
            this.lifeCycle = lifeCycle;
        }

        @Override
        public String toString() {
            return name;
        }
    }

   // Using ordinal() to index into an array - DON'T DO THIS!
   Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
   for (int i = 0; i < plantsByLifeCycle.length; i++) {
       plantsByLifeCycle[i] = new HashSet<>();
   }

   List<Plant> garden = new ArrayList<>();
   garden.add(new Plant("Tree", Plant.LifeCycle.PERENNIAL));
   garden.add(new Plant("Grass", Plant.LifeCycle.ANNUAL));

   for (Plant p : garden) {
       plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
   }

   for (int i = 0; i < plantsByLifeCycle.length; i++) {
       System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
   }

```

**Problems**
- Class cast: arrays are not compatible with generics.
- The most serious problem with this technique is that when you access an array that is indexed by an enum’s ordinal,
it is your responsibility to use the correct int value.

## EnumMap

- The array is effectively serving as a map from the enum to a value, so you might as well use a Map.
- There is a very fast Map implementation designed for use with enum keys, known as `java.util.EnumMap`.

``` java
Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
    plantsByLifeCycle.put(lc, new HashSet<>());
}

for (Plant p : garden) {
    plantsByLifeCycle.get(p.lifeCycle).add(p);
}

// {ANNUAL=[Grass], PERENNIAL=[Tree], BIENNIAL=[]}
System.out.println(plantsByLifeCycle);
```


**Advantages**
- it's shorter, clearer, safer.
    > can be further shortened by using a stream.
- there's no unsafe cast.
- no possibility for error in computing array indices.
- it's comparable in speed to the original version.
    > EnumMap uses such an array internally, but it hides this implementation detail from the programmer.

## Nested EnumMap
[see example code](../main/src/java/com/effectivejava/ch06_enums_annotations/Item37_NestedEnumMap.java)


## Summary
In summary,
- it is rarely appropriate to use ordinals to index into arrays: use EnumMap instead.
- If the relationship you are representing is multidimensional, use `EnumMap<..., EnumMap<...>>`.
- This is a special case of the general principle that application programmers should rarely, if ever, use Enum.ordinal (Item 35).
