# Item 36: Use EnumSet instead of bit fields

 [see example code](../main/src/java/com/effectivejava/ch06_enums_annotations/Item36_EnumSet.java)

## bit fields
What is bit fields?


If the elements of an enumerated type are used primarily in sets, it is traditional to use the int enum pattern (Item 34), assigning a different power of 2 to each con- stant:

```
    // Bit field enumeration constants - OBSOLETE!
    public static class Text {
        public static final int STYLE_BOLD = 1 << 0; // 1
        public static final int STYLE_ITALIC = 1 << 1;  // 2
        public static final int STYLE_UNDERLINE = 1 << 2;  // 4
        public static final int STYLE_STRIKETHROUGH = 1 << 3;  // 8

        // Parameter is bitwise OR of zero or more STYLE_ constants
        public void applyStyles(int styles) {
            System.out.print(styles);
        }
    }
```


**Advantages**
- This representation lets you use the bitwise OR operation to combine several constants into a set, known as a bit field:
  ```
  text.applyStyles(STYLE_BOLD | STYLE_ITALIC);
  ```
- The bit field representation also lets you perform set operations such as `union` and `intersection` efficiently using bitwise arithmetic.

**Disadvantage**
- all the disadvantages of int enum constants. [Item34](item_34_use_enums_instead_of_int_constants.md)
- you have to predict the maximum number of bits you’ll ever need at the time you’re writing the API and choose a type for the bit field (typically int or long) accordingly.

## EnumSet

The java.util package provides the EnumSet class to efficiently represent sets of values drawn from a single enum type.
This class implements the Set interface, providing all of the richness, type safety, and interoperability you get with any other Set implementation.
```
    // EnumSet - a modern replacement for bit fields
    public static class EnumSetText {

        public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }

        // Any Set could be passed in, but EnumSet is clearly best
        public void applyStyles(Set<Style> styles) {
            System.out.print(styles);
        }
    }
    
    // Client Code:
    text.applyStyles(EnumSet.of(Style.BOLD, Style.ITALIC));
```

**Advantages**
- The `EnumSet` class combines the conciseness and performance of bit fields with all the many advantages of enum types described in Item 34.


**Disadvantage**
- it is not possible to create an immutable EnumSet, but this will likely be remedied in an upcoming release.
- In the meantime, you can wrap an EnumSet with `Collections.unmodifiableSet`, but conciseness and performance will suffer.


## Related Items
> Item 1: Consider static factory methods instead of constructors

```
    public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
        ...
        if (universe.length <= 64)
            return new RegularEnumSet<>(elementType, universe);
        else
            return new JumboEnumSet<>(elementType, universe);
    }
```

If the underlying enum type has 64 or fewer elements—and most do—the entire EnumSet is represented with a single `long`, so its performance is comparable to that of a bit field.

## Summary
- just because an enumerated type will be used in sets, there is no reason to represent it with bit fields.
