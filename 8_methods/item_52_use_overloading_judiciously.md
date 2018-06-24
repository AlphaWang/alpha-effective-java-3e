# Item 52: Use overloading judiciously


### overload
The method's <b>name</b> is same, the <b>parameters count</b> or <b>type</b> or <b>position</b> is different. <br>


<h4>Static-Dispatch</h4> 
> The choice of which overloading to invoke is made at compile time.

```java
public class StaticDispatch {
    static class Human{}
    static class Man extends Human{}
    static class Woman extends Human{}

    public static void sayHello(Human human){System.out.println("human");}
    public static void sayHello(Man man){System.out.println("man");}
    public static void sayHello(Woman woman){System.out.println("woman");}
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        sayHello(man);
        sayHello(woman);
    }
}
```
> output

```
human
human
```

> javap -c

```$xslt
public class com.effectivejava.ch08_method.StaticDispatch {
  public com.effectivejava.ch08_method.StaticDispatch();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void sayHello(com.effectivejava.ch08_method.StaticDispatch$Human);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #3                  // String human
       5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return

  public static void sayHello(com.effectivejava.ch08_method.StaticDispatch$Man);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #5                  // String man
       5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return

  public static void sayHello(com.effectivejava.ch08_method.StaticDispatch$Woman);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #6                  // String woman
       5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #7                  // class com/effectivejava/ch08_method/StaticDispatch$Man
       3: dup
       4: invokespecial #8                  // Method com/effectivejava/ch08_method/StaticDispatch$Man."<init>":()V
       7: astore_1
       8: new           #9                  // class com/effectivejava/ch08_method/StaticDispatch$Woman
      11: dup
      12: invokespecial #10                 // Method com/effectivejava/ch08_method/StaticDispatch$Woman."<init>":()V
      15: astore_2
      16: aload_1
      17: invokestatic  #11                 // Method sayHello:(Lcom/effectivejava/ch08_method/StaticDispatch$Human;)V
      20: aload_2
      21: invokestatic  #11                 // Method sayHello:(Lcom/effectivejava/ch08_method/StaticDispatch$Human;)V
      24: return
}
```
> 17: invokestatic  #11 => ``Method sayHello:(Lcom/effectivejava/ch08_method/StaticDispatch$Human;)``<br>
> 21: invokestatic  #11 => ``Method sayHello:(Lcom/effectivejava/ch08_method/StaticDispatch$Human;)``<br>

## Summary
> As above sample, the ``Human man/woman`` this variable's ``Static Type(Apparent Type)`` is Human. <br>
> the compiler use the ``Static Type`` to decide to invoke which method.<br>
> the ``Man/Woman`` is the variables' ``Actual Type``  <br>

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

<a href="./item_53_use_varargs_judiciously.md">next item</a>