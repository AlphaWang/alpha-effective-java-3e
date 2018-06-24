# Item 52: Use overloading judiciously


### overload
The method's <b>name</b> is same, the <b>parameters count</b> or <b>type</b> or <b>position</b> is different. <br>


<h4>Static-Dispatch</h4> 
> The choice of which overloading to invoke is made at compile time.

```$xslt
public class CollectionClassfier {

    public static String classify(Set<?> s){ return "set"; }
    public static String classify(List<?> l){ return "list"; }
    public static String classify(Collection<?> c){ return "unknown"; }

    public static void main(String[] args){
        Collection<?>[] collections = { new HashSet<String>(), 
                                        new ArrayList<String>(), 
                                        new HashMap<String, String>().values()};
        for (Collection<?> c : collections){
            System.out.println(classify(c));
        }
    }
}
```
> output

```
unknown
unknown
unknown
```

> As above sample, the ``Collection<?> c`` this variable's ``Static Type(Apparent Type)`` is collection. <br>
> the compiler use the ``Static Type`` to decide to invoke which method.<br>
> the each ``c`` variable of the loop, has different ``Actual Type``<br>

<b>Do not export two overloadings with the same number of parameter</b><br>
<b>Give the method different names instead of overloading</b>
* ObjectOutputStream.writeBoolean(boolean)
* ObjectOutputStream.writeInt(int)
## Bonus

<h4>Dynamic-Dispatch</h4> 

> The choice of which overriding is dynamic

```$xslt
public class DynamicDispatch {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("man say hello");
        }
    }
    static class Woman extends Human{
        @Override
        protected void sayHello() {
            System.out.println("woman say hello");
        }
    }
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
    }
}
```
> output

```
man say hello
woman say hello
```

>javap -c

```$xslt
public class com.effectivejava.ch08_method.DynamicDispatch {
  public com.effectivejava.ch08_method.DynamicDispatch();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #2                  // class com/effectivejava/ch08_method/DynamicDispatch$Man
       3: dup
       4: invokespecial #3                  // Method com/effectivejava/ch08_method/DynamicDispatch$Man."<init>":()V
       7: astore_1
       8: new           #4                  // class com/effectivejava/ch08_method/DynamicDispatch$Woman
      11: dup
      12: invokespecial #5                  // Method com/effectivejava/ch08_method/DynamicDispatch$Woman."<init>":()V
      15: astore_2
      16: aload_1
      17: invokevirtual #6                  // Method com/effectivejava/ch08_method/DynamicDispatch$Human.sayHello:()V
      20: aload_2
      21: invokevirtual #6                  // Method com/effectivejava/ch08_method/DynamicDispatch$Human.sayHello:()V
      24: return
}

```
> astore_1 => ``Man``<br>
> astore_2 => ``Woman``<br>

