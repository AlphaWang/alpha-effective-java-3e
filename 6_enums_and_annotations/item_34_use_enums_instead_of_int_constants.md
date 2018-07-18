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

``` java
public enum Apple  { FUJI, PIPPIN, GRANNY_SMITH }
public enum Orange { NAVEL, TEMPLE, BLOOD }
```

The basic idea behind enum:
they are classes that export one instance for each enumeration constant via a public static final field.
- Enum types are instance-controlled.
- are a generalization of singletons.

**Advantages:**
- rectify the deficiencies of int enums.  
    > 1. compile-time type safety.  
    > 2. each type has its own namespace.  
    > 3. ...
- enum types let you add arbitrary methods and fields and implement arbitrary interfaces.  
(i.e. associate different data/behavior with each enum type)  
[see example code](../main/src/java/com/effectivejava/ch06_enums_annotations/Item34_2_Enum.java)   
    > 1. provide high-quality implementations of all the Object methods.  
    > 2. implement Comparable, Serializable.  

``` java
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
- Use enums any time you need a set of constants whose members are known at compile time.

## Strategy Enum

A disadvantage of `constant-specific method implementations` is that they make it harder to share code among enum constants.

Question: What's the problem of this code?
``` java
// Enum that switches on its value to share code - questionable
   enum PayrollDay {
       MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
       SATURDAY, SUNDAY;

       private static final int MINS_PER_SHIFT = 8 * 60;

       int pay(int minutesWorked, int payRate) {
           int basePay = minutesWorked * payRate;
           int overtimePay;
           switch(this) {
             case SATURDAY: case SUNDAY: // Weekend
               overtimePay = basePay / 2;
               break;
             default: // Weekday
               overtimePay = minutesWorked <= MINS_PER_SHIFT ? 0 : (minutesWorked - MINS_PER_SHIFT) * payRate / 2;
           }
           return basePay + overtimePay;
       }
}
```

A: it is dangerous from a maintenance perspective. Suppose you add an element to the enum, perhaps a special value to represent a vacation day, but forget to add a corresponding case to the switch statement.

To perform the pay calculation safely with constant-specific method implementations,
- you would have to duplicate the overtime pay computation for each constant,
- or move the computation into two helper methods, one for weekdays and one for weekend days, and invoke the appropriate helper method from each constant.
Either approach would result in a fair amount of boilerplate code.
[see example code](../main/src/java/com/effectivejava/ch06_enums_annotations/Item34_4_StrategyEnum.java)


What you really want is to be forced to choose an overtime pay strategy each time you add an enum constant.
Luckily, there is a nice way to achieve this. The idea is to move the overtime pay computation into a private nested enum, and to pass an instance of this strategy enum to the constructor for the PayrollDay enum.

``` java
// The strategy enum pattern
   enum PayrollDay {
       MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY,
       SATURDAY(PayType.WEEKEND), SUNDAY(PayType.WEEKEND);
       private final PayType payType;
       PayrollDay(PayType payType) { this.payType = payType; }
       PayrollDay() { this(PayType.WEEKDAY); }  // Default
       int pay(int minutesWorked, int payRate) {
           return payType.pay(minutesWorked, payRate);
       }
       
       // The strategy enum type
       private enum PayType {
           WEEKDAY {
               int overtimePay(int minsWorked, int payRate) {
                   return minsWorked <= MINS_PER_SHIFT ? 0 :
                     (minsWorked - MINS_PER_SHIFT) * payRate / 2;
               }
           }, WEEKEND {
               int overtimePay(int minsWorked, int payRate) {
                   return minsWorked * payRate / 2;
               }
           };
           abstract int overtimePay(int mins, int payRate);
           private static final int MINS_PER_SHIFT = 8 * 60;
           int pay(int minsWorked, int payRate) {
               int basePay = minsWorked * payRate;
               return basePay + overtimePay(minsWorked, payRate);
           }
       }
}
```


## Summary
In summary,
- the advantages of enum types over int constants are compelling.
- Enums are more readable, safer, and more powerful.
- Many enums require no explicit constructors or members, but others benefit from **associating data** with each constant and providing methods whose behavior is affected by this data.
- Fewer enums benefit from associating multiple behaviors with a single method.
In this relatively rare case, prefer **constant-specific methods** to enums that switch on their own values.
- Consider the **strategy enum** pattern if some, but not all, enum constants share common behaviors.

