# Item 35: Use instance fields instead of ordinals

## ordinal()

All enums have an ordinal method, which returns the numerical position of each enum constant in its type.
You may be tempted to derive an associated int value from the ordinal:

```
 // Abuse of ordinal to derive an associated value - DON'T DO THIS
    public enum Ensemble {
        SOLO,   DUET,   TRIO, QUARTET, QUINTET,
        SEXTET, SEPTET, OCTET, NONET,  DECTET;
        
        public int numberOfMusicians() { return ordinal() + 1; }
    }
```

## Problems
- If the constants are reordered, the `numberOfMusicians` method will break.
- You can't add a second enum constant associated with an int value that you’ve already used.
- You can’t add a constant for an int value without adding constants for all intervening int values.

## Solution
- Never derive a value associated with an enum from its ordinal; store it in an instance field instead.
```
public enum Ensemble {
       SOLO(1), DUET(2), TRIO(3), QUARTET(4), QUINTET(5),
       SEXTET(6), SEPTET(7), OCTET(8), DOUBLE_QUARTET(8),
       NONET(9), DECTET(10), TRIPLE_QUARTET(12);

       private final int numberOfMusicians;

       Ensemble(int size) { this.numberOfMusicians = size; }
       public int numberOfMusicians() { return numberOfMusicians; }
}
```

## Summary
- `ordinal()` is designed for use by general-purpose enum-based data structures such as `EnumSet` and `EnumMap`.
- Unless you are writing code with this character, you are best off avoiding the `ordinal` method entirely.

