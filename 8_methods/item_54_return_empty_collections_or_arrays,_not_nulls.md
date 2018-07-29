# Item 54: Return empty collections or arrays, not nulls

> If the method return null in place of empty collections or arrays

```java
ArrayList<Integer> list = filterNegative(Lists.newList(xxx,xxx,xxx));
// add null check
if (null == list)
    return;
```
> If the ``null`` check logic is miss.
> The program can fail when ``filterNegative`` method return <b>null</b>


### How to avoid the allocation empty collection ?
> Immutable empty collection
> * Collections.emptyList() <br>
> * Collections.emptySet() <br>
> * Collections.emptyMap() <br>

<hr>

> Q: ``return cheeseInStock.toArray(new Cheese[cheeseInStock.size()])``

<hr>

### To Summary 
> Never return null inplace of an empty array or collection.<br>
> It makes your API more difficult to use and more prone to error.<br>
> It has no performance advantages<br>

<a href="./item_55_return_optionals_judiciouslly.md">next item</a>