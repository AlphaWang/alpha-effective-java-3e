# Item 89: For instance control, prefer enum types to readResolve

## readResolve 
 - allows you to substitute another instance for the one created by readObject.
 - but newly created object(invoked after it is deserialized) can becomes eligible for garbage collection.
 - if you depend on readResolve for instance control, all instance fields with object reference types must be declared 
   and all instance fields with object reference types must be declared transient. 

```java
//target to serializable
    public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() { ... }
    public void leaveTheBuilding() { ... }
}
```

```java
// Broken singleton - has nontransient object reference field!
public class Elvis implements Serializable {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() { }
    private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };
    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
    private Object readResolve() {
        return INSTANCE;
    }
}
```
-  problem 
    - This allows a carefully crafted stream to “steal” a reference to the originally deserialized singleton at the time the contents of the object reference field are deserialized.

```java
// 'steal' class 
public class ElvisStealer implements Serializable {
    static Elvis impersonator;
    private Elvis payload;
    private Object readResolve() {
        // Save a reference to the "unresolved" Elvis instance
        impersonator = payload;
        // Return object of correct type for favoriteSongs field
        return new String[] { "A Fool Such as I" };
    }
    private static final long serialVersionUID = 0;
}
```    

```java
// Enum singleton - the preferred approach
public enum Elvis {
    INSTANCE;
    private String[] favoriteSongs = { "Hound Dog", "Heartbreak Hotel" };
    public void printFavorites() {
        System.out.println(Arrays.toString(favoriteSongs));
    }
}
```

The accessibility of readResolve is significant so If you place a readResolve method on a final class, it should be private. 

## summarize
 - use enum types to enforce instance control invariants wherever possible. 
 - If this is not possible and you need a class to be both serializable and instance-controlled, provide a readResolve method and ensure that all of the class’s instance fields are either primitive or transient.

