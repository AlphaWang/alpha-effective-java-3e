# Item 50: Make defensive copies when needed
You must program defensively, with the assumption that clients of your class will do their best to destroy its invariants.

##### An immutable object sample, like below.
```java
public final class Period {

    private final Date start;
    private final Date end;

    /**
     * @param start          the beginning of the period
     * @param end            the end of the period, must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException     if start or end is null
     */
    public Period(Date start, Date end) {
        /*
        * from Item-49
        * */
        Objects.requireNonNull(start, "start is null");
        Objects.requireNonNull(end, "end is null");
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
        this.start = start;
        this.end = end;
    }

    public Date getEnd() {
        return end;
    }

    public Date getStart() {
        return start;
    }

    public static void main(String[] args){
        Date start = new Date();
        Date end = new Date();
        Period period = new Period(start, end);
        period.getEnd().setYear(100);
    }
}
```

> <h4>Attack</h4>
> Reset the period's end field

<h3>Solution </h3>
> Solution 1: use LocalDateTime/ZoneDateTime. since Java 8

```java
public final class Period {

    private final LocalDateTime start;
    private final LocalDateTime end;

    ...
    
    public static void main(String[] args){
        LocalDateTime startLD =  LocalDateTime.now();
        LocalDateTime endLD =  LocalDateTime.now();
        Period period = new Period(startLD, endLD);
        // period.getEnd() there is not set method

    }
}
```

> Solution 2: Defensive copy to mutable object.

```java
public final class Period {

    ...

    public Date getEnd() {
        return new Date(end.getTime());
    }
    
    ...
    
    public static void main(String[] args){
        Date start = new Date();
        Date end = new Date();
        Period period = new Period(start, end);
        period.getEnd().setYear(100);
    }
}
```


<h4>Summary</h4>

* If a class has mutable components that it gets from or return to its client, the class must defensively copy 
these components. If the cost of the copy would be prohibitive and class trusts its clients not to modify 
the components inappropriately, then the defensive copy maybe replaced by documentation outlining the clients's 
responsibility not to modify the affected components.

<a href="./item_51_design_method_signatures_carefully.md">next item</a>