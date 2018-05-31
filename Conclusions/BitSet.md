# BitSet
## BitSet的原理
Java BitSet可以按位存储，计算机中一个字节(byte)占8位(bit)；
而BitSet是位操作的对象，值只有0或1（即true 和 false），内部维护一个long数组，初始化只有一个long segement，所以BitSet最小的size是64；随着存储的元素越来越多，BitSet内部会自动扩充，一次扩充64位，最终内部是由N个long segement 来存储；
默认情况下，BitSet所有位都是0即false；

## BitSet的应用场景
海量数据去重、排序、压缩存储

## BitSet的基本操作
and（与）、or（或）、xor（异或）

## 优点：
* 按位存储，内存占用空间小
* 丰富的api操作

## 缺点：
* 线程不安全
* BitSet内部动态扩展long型数组，若数据稀疏会占用较大的内存

## Reference
[JavaBitSet学习](https://www.cnblogs.com/xupengzhang/p/7966755.html)