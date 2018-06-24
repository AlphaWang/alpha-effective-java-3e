# Item 53: Use varargs judiciously

<h3>Varargs method.</h3>
> accept zero or more arguments.<br>

sample code
```java
static int sum(int... args){
    int sum = 0;
    for (int arg : args){
        sum += arg;
    }
    return sum;
}
```
> Step<br>
>   1. ``create`` an array, array size is the number of arguments.<br>
>   2. ``put``  the argument values into array.<br>
>   3. ``pass`` the array to method<br>

<hr>

Bad code
```java
static int min(int... args){
    if(args.length == 0)
        throw new IllegalArgumentException("Too few arguments");
    int min = args[0];
    for (int i = 1; i <  args.length; i++){
        if (args[i] < min)
            min = args[i];
    }
    return min;
}
```
> Problem<br>
>   * invoke with ``no arguments``, fail at runtime.<br>
>   * ``min`` need to be initialized.<br>

Good code
```java
static int min(int firstArg, int... remainingArgs){
    int min = firstArg;
    for (int arg : remainingArgs){
        if (args < min)
            min = args;
    }
    return min;
}
```

### To summary
> varags are invaluable whtn you need to define methods with a variable number of arguments. 
Precede the varargs parameter with any required parameters, and be aware of the performance consequences
of using varargs.


<a href="./item_54_return_empty_collections_or_arrays,_not_nulls.md">next item</a>
