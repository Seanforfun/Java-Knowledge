# Java NIO
JAVA nio出现在Java 1.4中，基于一些资料，在没有学习之前，我认为nio是一种多路复用技术，此观点可能会在后期进行更改，提出新的想法。

借助着对网上诸多教程的总结，我想认真了解NIO的使用方法和原理，了解NIO的实现和使用。

### I/O模型
1. 应用在用户态（user mode）发送一个资源读取的请求，Linux系统会切换到内核态（kernel mode），检查资源是否存在。
2. 如果数据存在，内核将资源从存储空间（kernel mode）中拷贝至应用程序中（user mode）。
3. I/O网络模型（包括了两次I/O，文件系统的IO，一次网络SocketIO）：
    * 接收到请求后，从用户态转换成内核态，获取资源并转为用户态。
    * 获取资源后，CPU将请求的资源从用户态拷贝至内核态的SocketIO之中（即将资源通过网络发出），最后转换回用户态。
    ![Imgur](https://i.imgur.com/1sr5Bwa.png)

### 阻塞(Blocking)和非阻塞(Non-Blocking)
那么阻塞（blocking IO）和非阻塞（non-blocking IO）的区别就在于第一个阶段，如果数据没有就绪，在查看数据是否就绪的过程中是一直等待，还是直接返回一个标志信息。

### 同步（Synchronize）和异步（Asynchronize）
1. 同步和异步表现的是线程之间的关系，两个线程之间，要么是同步的，要么是异步的。通常用来形容方法的调用。
2. 同步方法调用一旦开始，调用者必须等到方法调用返回后，才能继续后续的行为。例如，A线程调用了B线程，A线程此时就被阻塞了，必须等到B线程的方法结束才会进行A线程才会继续。
3. 异步方法调用更像一个消息传递，一旦开始，方法调用就会立即返回，调用者就可以继续后续的操作。而，异步方法通常会在另外一个线程中，“真实”地执行着。整个过程，不会阻碍调用者的工作。因为异步方法是在另一个线程中进行的，调用者本身是无法确定异步方法会合适返回。

### 请求响应发展史
这一段是对当年学习UNIX网络编程的回忆以及在知乎上面的一些结果的总结。
1. 最开始的时候，用电脑上网的人很少，所以服务器每接收到一个请求，都会fork出一个新的线程用于处理该请求。随着用户量的增大，服务器的资源迅速爆炸。
2. 在一开始，创建出大量的socket连接，有一条额外的线程用于轮询查看哪一个socket有的响应。
3. 对每一个socket进行注册，当来了一个请求，可以根据注册表立刻找到该条socket。

## NIO
NIO最重要的组件为：
* Channel: Channel 有点象流。 数据可以从Channel读到Buffer中，也可以从Buffer 写到Channel中。
* Buffer: 缓存区
* Selector： Selector允许单线程处理多个 Channel。

### Channel
Java NIO的通道类似流，但又有些不同：
* 既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。
* 通道可以异步地读写。
* 通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。
![Imgur](https://i.imgur.com/AJ2Ipqq.png)

Channel有几个重要的实现类，分别对应了文件，UDP,TCP
1. FileChannel 从文件中读写数据。
2. DatagramChannel 能通过UDP读写网络中的数据。
3. SocketChannel 能通过TCP读写网络中的数据。
4. ServerSocketChannel可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。

#### 从文件中通过NIO读取
从某个文件路径中读取文件内容并打印出来。
```Java
private static void readFileByFileChannel(String filename) throws IOException {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(filename, "rw");
            FileChannel channel = file.getChannel();    //获取文件的channel
            ByteBuffer buffer = ByteBuffer.allocate(48);    //定义一个Buffer用于从Channel中读取数据或是向channel中写入数据。
            while (channel.read(buffer) != -1){
                buffer.flip();  //读写反转，position变成0。
                while(buffer.hasRemaining())
                    System.out.print((char)buffer.get());
                buffer.clear(); //buffer不会覆盖，要通过该方法将position设置为0。
            }
        }finally {
            file.close();
        }
    }
```

### Buffer
Buffer的使用流程：
1. 写入数据到Buffer
2. 调用flip()方法
3. 从Buffer中读取数据
4. 调用clear()方法或者compact()方法

从文件中读出并写入另一个文件中
```Java
private static void copyFileByFileChannel(String from, String to) throws IOException {
        RandomAccessFile in = null;
        RandomAccessFile out = null;
        try {
            in = new RandomAccessFile(from, "r");
            out = new RandomAccessFile(to, "rw");
            FileChannel inChannel = in.getChannel();
            FileChannel outChannel = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(48);
            while (inChannel.read(buffer) != -1){   //将资源读入buffer
                buffer.flip();
                while (buffer.hasRemaining())
                    outChannel.write(buffer);   //将buffer写到新的文件中。
                buffer.clear(); //将buffer的position设置为0。
            }
        }finally {
            in.close();
            out.close();
        }
    }
```

##### Buffer的capacity, position, limit
* Capacity: 创建的Buffer的大小。
* postion： 在向buffer写入的过程中，position表示当前写指针和0之间的offset。最大值为limit - 1。
* limit: 在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。

##### Buffer的类型，基本对应了所有的基本数据类型
* ByteBuffer
* MappedByteBuffer
* CharBuffer
* DoubleBuffer
* FloatBuffer
* IntBuffer
* LongBuffer
* ShortBuffer

##### Buffer的分配
ByteBuffer.allocate(字节数)；

##### 向Buffer中写入数据。
 向buffer中写入数据主要分为两种，一种是从channel中向buffer中写入数据， 另一种是直接向buffer中写入数据
 * channel.write()
 * buffer.put()
 * 例子，通过buffer.put()向buffer中写入数据
 ```Java
 private static void streamToBuffer(String src, String dest) throws IOException {
        File f = null;
        RandomAccessFile out = null;
        InputStream in = null;
        try{
            f = new File(src);
            out = new RandomAccessFile(dest, "rw");
            FileChannel outChannel = out.getChannel();
            in = new FileInputStream(f);
            ByteBuffer buffer = ByteBuffer.allocate(48);
            byte[] b = new byte[48];
            while (in.read(b) != -1){
                buffer.put(b);
                buffer.flip();  //由写入buffer转为写出buffer，调用flip方法。
                while (buffer.hasRemaining())
                    outChannel.write(buffer);
                buffer.clear();
            }
            outChannel.close();
        }finally {
            in.close();
        }
    }
 ```
 
 ##### 从buffer中读出数据，此处例子省略，可以参考第一个例子。
 * channel.write(buffer)
 * buffer.get()
  
##### rewind()方法
Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。

##### equals和compareTo方法已经被重写，用于比较buffer内的内容。

### Scatter（发散）/Gather（聚集）
分散和聚集都是针对数据而言的。
1. 分散（scatter）从Channel中读取是指在读操作时将读取的数据写入多个buffer中。因此，Channel将从Channel中读取的数据“分散（scatter）”到多个Buffer中。
![Imgur](https://i.imgur.com/DtGlKzh.png)
```Java
ByteBuffer head = ByteBuffer.allocate(128);
ByteBuffer body = ByteBuffer.allocate(1024);
ByteBuffer[] buffers = {head, body};    //将数据进行发散到head和body之中
channel.read(buffers);  //只有将head写满后，才会写入body，这说明了发散适用于大小固定的数据。
```
2. 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
![Imgur](https://i.imgur.com/qcUaChl.png)
scatter / gather经常用于需要将传输的数据分开处理的场合，例如传输一个由消息头和消息体组成的消息，你可能会将消息体和消息头分散到不同的buffer中，这样你可以方便的处理消息头和消息体。
```Java
ByteBuffer head = ByteBuffer.allocate(128);
ByteBuffer body = ByteBuffer.allocate(1024);
ByteBuffer[] buffers = {head, body};
channel.write(buffers); //buffers数组是write()方法的入参，write()方法会按照buffer在数组中的顺序，将数据写入到channel，注意只有position和limit之间的数据才会被写入。因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。
```

### 引用
1. [同步(Synchronous)和异步(Asynchronous)](https://www.cnblogs.com/anny0404/p/5691379.html)
2. [浅析Linux中的零拷贝技术](https://www.jianshu.com/p/fad3339e3448)
3. [Java NIO系列教程（一） Java NIO 概述](http://ifeve.com/overview/)