# HTTP V2.0
原来已经总结过一次了HTTP，但是感觉还是需要不断地总结才能记住。上次的总结可以[参考](https://github.com/Seanforfun/ItcastLearningSmallDemos/blob/master/HTTP%E6%80%BB%E7%BB%93.txt).

### Http的特点
1. 支持server/client模式。
2. 简单：当client向server发送信息时，只需要指定URL，并且携带必要的请求参数或消息体。
3. 灵活：实际上http的传输不仅仅是少量信息的传输，实际上我们也会用http传输视频，文件等等信息。此时我们需要使用Content-Type设置信息。
4. 无状态：协议对于事物的处理没有记忆能力。

### Http协议介绍
#### Http的url
http://[url]:[port]

#### HTTP请求消息
* HTTP的构成：格式： Method + Request-URI + HTTP version
1. HTTP request line
2. HTTP request head
3. HTTP request body

* HTTP的请求方法（RESTFul）：
1. GET: 请求获取Request-URI所标识的资源。
2. POST：在Request-URI所标识的资源后附加新的提交数据。
3. PUT： 请求服务器存出一个资源，并用Request-URI作为其标识。
4. DELETE: 请求服务器删除Request-URI所标识的资源。

* GET和POST的区别
1. GET只会从服务器获取数据，而POST可能会修改服务器上的数据。
2. GET的资源请求是明文的，类似?name=sdfsa&age=12, 而POST的请求消息被放在request body中。
3. GET的请求消息会因为浏览器的不同而出现长度的限制，而POST却没有请求的长度限制。
4. POST更加安全。

* HTTP的请求解析
```Http
Firefox中摘出的一段GET请求报文头
发送的url是：http://localhost:8080/ssm/items/editItems.action?id=1

Host	localhost:8080
User-Agent	Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/56.0

Accept:请求报文可通过一个“Accept”报文头属性告诉服务端 客户端接受什么类型的响应。
Accept的值可以为一个或多个MIME类型。
Accept	text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8

Accept-Language	zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
Accept-Encoding	gzip, deflate

Refer：表示这个请求是从哪个URL过来的。第一次打开一个网页没有值。
Referer	http://localhost:8080/ssm/items/queryItems.action

Cookie:用于存储cookie的值，可以理解为cookie域。其中JSESSIONID指的是当前客户端请求属于哪一个Session。
SESSIONID具体了解：https://blog.csdn.net/java_faep/article/details/78082802
当用户首次与Web服务器建立连接的时候，服务器会给用户分发一个 SessionID作为标识。用户每次提交页面，浏览器都会把这个SessionID包含在 HTTP头中提交给Web服务器，这样Web服务器就能区分当前请求页面的是哪一个客户端。这个SessionID就是保存在客户端的，属于客户端Session。
Cookie	JSESSIONID=98CD0D522E04A001E177684A0D46389B

Connection：是否需要保活，如果keep-alive会采用HTTP1.1协议
Connection	keep-alive
Upgrade-Insecure-Requests	1

对应的响应报文头：
Server	Apache-Coyote/1.1
Content-Type	text/html;charset=UTF-8
Content-Language	zh-CN
Content-Length	1849
Date	Wed, 04 Apr 2018 17:06:11 GMT
Cache-Control：响应输出到客户端后，服务端通过该报文头属告诉客户端如何控制响应内容的缓存。 
Cache-Control max-age=3600 
```

### HTTP响应
1. 1xx 消息，一般是告诉客户端，请求已经收到了，正在处理，别急...
2. 2xx 处理成功，一般表示：请求收悉、我明白你要的、请求已受理、已经处理完成等信息.
3. 3xx 重定向到其它地方。它让客户端再发起一个请求以完成整个处理。
4. 4xx 处理发生错误，责任在客户端，如客户端的请求一个不存在的资源，客户端未被授权，禁止访问等。
5. 5xx 处理发生错误，责任在服务端，如服务端抛出异常，路由出错，HTTP版本不支持等。

### 引用
1. [Netty 权威指南 第十章（请购买正版书，该连接只提供短期阅读）](https://github.com/Seanforfun/Books/tree/master/Netty)