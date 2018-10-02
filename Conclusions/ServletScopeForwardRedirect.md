# Servlet Scope

### Servlet的三个作用域
1. Request
2. Session
3. Application
* 从作用范围上看Application > Session > Request

### Request
#### Request的生命周期
1. 用户通过http协议将报文发送给服务器。
2. Servlet容器（服务器，例如Tomcat），容器会根据字段信息解析报文,生成HttpRequest对象，将报文中的信息封装到Request对象中。
3. Request作为一个作用域，可以承载一些数据，我们可以通过setAttribute,getAttribute和removeAttribute将数据封装到Request域中。
4. 如果需要转发，会通过request.getRequestDispather(地址).forward(request, response)。
5. 在服务器解析Response对象，生成HTTP报文返回给对象时，request对象被销毁，生命周期结束。

#### Request域
1. Request域是一种轻量级的域，生存周期较短，尽量使用Request域存储信息。

### Session
#### Session的生命周期
1. 当Servlet容器接收到某个用户的请求，会根据五元组信息（个人猜测）生成一个SessionId。
	* 如果当前SessionId存在，则直接使用。
	* 如果当前SessionId不存在，则会生成一个新的Session对象。
2. Session失效：
	* 用户关闭当前页面。
	* Session超时，一般默认时间为20min。
	* 调用session.invalidate()方法，使session失效。
3. Session也是面向用户的作用域，拥有session.setAttribute(), session.getAttribute()和session.removeAttribute()方法，可以用来存储在网站访问过程中需要的用户信息。

### Application
#### Application的生命周期
1. Application(ServletContext)当应用（此处理解为网站的jar/war程序）启动时生成一个application对象。
2. Application域中多用于存储全局变量和常量。

### 转发和重定向
#### Request转发
![转发过程](https://i.imgur.com/76jD7uQ.png)

### Response重定向
1. 当用户在当前请求中无法完成，通过重定向将可以获得资源的请求地址发送给用户。
2. 用户解析报文，并再次向服务器发送请求，请求可以获取资源的地址。
3. 此时浏览器的地址栏将会发生改变。
4. 重定向为两次请求。