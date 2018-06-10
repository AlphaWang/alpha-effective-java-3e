# Item 34: Use enums instead of int constants

## int enum pattern

Before enums: **int enum pattern**

 ``` java
    // The int enum pattern - severely deficient!
    public static final int APPLE_FUJI         = 0;
    public static final int APPLE_PIPPIN       = 1;
    public static final int APPLE_GRANNY_SMITH = 2;
    public static final int ORANGE_NAVEL  = 0;
    public static final int ORANGE_TEMPLE = 1;
    public static final int ORANGE_BLOOD  = 2;
 ```


**Shortcomings:**
 - no type safety. [see example code](../main/src/java/com/effectivejava/ch06_enums_annotations/Item34_1_IntConstantPattern.java)
 - no namespaces for int enum groups.
 - Because int enums are constant variables, their int values are compiled into the clients that use them.
    > If the value associated with an int enum is changed, its clients must be recompiled.

 - There is no easy way to translate int enum constants into printable strings.
 - There is no reliable way to iterate over all the int enum constants in a group.
 
## enums

```
public enum Apple  { FUJI, PIPPIN, GRANNY_SMITH }
public enum Orange { NAVEL, TEMPLE, BLOOD }
```

The basic idea behind enum:
they are classes that export one instance for each enumeration constant via a public static final field.
- Enum types are instance-controlled.
- are a generalization of singletons.

**Advantages:**
- rectifying the deficiencies of int enums.
    > 1. compile-time type safety.
    > 2. each type has its own namespace.
    > 3. ...
- enum types let you add arbitrary methods and fields and implement arbitrary interfaces.
(i.e. associate different data/behavior with each enum type)
[see example code](../main/src/java/com/effectivejava/ch06_enums_annotations/Item34_2_Enums.java)
    > 1. provide high-quality implementations of all the Object methods.
    > 2. implement Comparable, Serializable.

```
// Enum type with constant-specific class bodies and data
// associate different data with each enum.
enum Operation3 {
	PLUS("+") {
		public double apply(double x, double y) {
			return x + y;
		}
	},
	MINUS("-") {
		public double apply(double x, double y) {
			return x - y;
		}
	},
	TIMES("*") {
		public double apply(double x, double y) {
			return x * y;
		}
	},
	DIVIDE("/") {
		public double apply(double x, double y) {
			return x / y;
		}
	};

	private final String symbol;

	Operation3(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return symbol;
	}

	public abstract double apply(double x, double y);
}
```

    
**Tips:**
- Just as with other classes, unless you have a compelling reason to expose an enum method to its clients, declare it private or, if need be, package-private.
- If an enum is generally useful, it should be a top-level class; if its use is tied to a specific top-level class, it should be a member class of that top-level class.
- If you override the `toString` method in an enum type, consider writing a `fromString` method to translate the custom string representation back to the corresponding enum.
[see example code](../main/src/java/com/effectivejava/ch06_enums_annotations/Item34_3_FromString.java)




