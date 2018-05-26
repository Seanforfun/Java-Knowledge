# Comparable and Comparator

## Comparable
```Java
public interface Comparable<T> {
    public int compareTo(T o);
}
```
* An interface used for comparasion.
* The classes implement it heed to create a instance and call this method to do comparasion.
`a.compareTo(b);`

## Comparator
```Java
int compare(T o1, T o2);
```
* In order to use comparator, we need to create a comparator instance and call the compare method:
`c.compare(a, b);`
* Comparator has been updated in JDK1.8 for Stream API and added a lot of default method in this interface.