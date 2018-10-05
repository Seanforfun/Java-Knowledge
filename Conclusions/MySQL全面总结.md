# MySql总结
本文档是基于《MySQL必知必会》的总结，希望完成于2018年12月之前。本书包含了关于MySQL较为全面的基础语法，希望可以弥补我在开发过程中因为对于MySQL基础了解不扎实所造成的一些问题。并且在日后的开发中再遇到类似的问题可以通过阅读本文档进行回忆并加以利用。

在总结的最开始部分，我并没有遵循一定的规范编写我的SQL指令，但是最好的规范是在编写指令时，对于SQL关键字，我们使用大写，对于字段等等元信息我们使用小写加以区分和阅读。

### 了解MySQL，
1. 数据库：保存有组织的数据的容器（通常是一个文件或一组文件）。
  * DBMS(database manage system): 数据库管理软件，和数据库本身有一些区别，我们可以理解为我们使用DBMS访问数据库。
2. 表：表是一种结构化的文件，可用来存储某种特定类型的数据。
  * 每个表的名字是唯一的，表中存储的是相对聚合的信息，我理解表中一般存储的是一个类中的信息。
  * 表定义了数据如何储存， 可以存储什么样的数据（varchar， int，etc），数据如何分解，各个部分数据如何命名。
  * 描述表的信息被称为*模式(schema)* ,我认为这就是表的元数据。
3. 表中的单元（列，行，主键）
  * 列Column
    * 列是表中的一个字段，定义了表中所存储的一种信息。比如姓名是一个列，性别是一个列。
  * 数据类型DateType
    * 解释了每一个列可以存储的数据类型。
  * 行Row
    * 一行数据实际上是一组数据，在Java中，我理解为存储了一个对象的信息。
  * 主键PrimaryKey
    * 在一个表中，一行数据所存储的独有的数据，我们可以通过主键定位表中的数据。
    * 主键不能为空NULL。
    * 主键在表中一定是唯一的。
      * 不更新主键列中的值；
      * 不重用主键列的值；
      * 不在主键列中使用可能会更改的值。（例如，如果使用一个
名字作为主键以标识某个供应商，当该供应商合并和更改其名字时，必须更改这个主键。）

### 使用MySQL
1. 连接到MySQL的信息
* 主机名（计算机名）：如果是连接到本地的服务器，一般使用localhost。
* 端口： 一般MySQL会占用电脑的3306端口。
* 合法的用户名。
* 用户口令。

2. 选择数据库:我们打开了DBMS，其连接到MySQL这个数据系统，里面可以存储多个数据库，此时我们需要选择我们需要连接的数据库。

```SQL
# 显示当前数据库系统中所有的数据库
show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| sys                |
| test               |
+--------------------+

# 选择使用的数据库
use test；
Database changed

# 显示当前数据库中的所有可用的表
show tables;
+----------------+
| Tables_in_test |
+----------------+
| user           |
+----------------+
1 row in set

# 显示某张表中的数据模式
show columns from user;
+---------------+--------------+------+-----+---------+----------------+
| Field         | Type         | Null | Key | Default | Extra          |
+---------------+--------------+------+-----+---------+----------------+
| id            | bigint(20)   | NO   | PRI | NULL    | auto_increment |
| nickname      | varchar(40)  | NO   |     | NULL    |                |
| NAME          | varchar(40)  | NO   |     | NULL    |                |
| PASSWORD      | varchar(40)  | NO   |     | NULL    |                |
| email         | varchar(100) | NO   |     | NULL    |                |
| pic           | varchar(255) | YES  |     | NULL    |                |
| activeTime    | bigint(20)   | NO   |     | NULL    |                |
| lastLoginTime | bigint(20)   | YES  |     | NULL    |                |
| bio           | text         | YES  |     | NULL    |                |
| country       | varchar(60)  | YES  |     | NULL    |                |
| province      | varchar(60)  | YES  |     | NULL    |                |
| city          | varchar(60)  | YES  |     | NULL    |                |
| activestatus  | int(3)       | NO   |     | NULL    |                |
| url           | varchar(255) | NO   |     | NULL    |                |
| intro         | text         | YES  |     | NULL    |                |
| admin         | int(3)       | NO   |     | NULL    |                |
| ipAddr        | varchar(100) | YES  |     | NULL    |                |
+---------------+--------------+------+-----+---------+----------------+
17 rows in set

# 显示服务器的状态
show status;

# 显示授权用户
show grants;
+---------------------------------------------------------------------+
| Grants for root@localhost                                           |
+---------------------------------------------------------------------+
| GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost' WITH GRANT OPTION |
| GRANT PROXY ON ''@'' TO 'root'@'localhost' WITH GRANT OPTION        |
+---------------------------------------------------------------------+
2 rows in set

# 显示errors和warnings
show errors;
show warnings;
```

### 数据检索
1. 查找数据（Select）

#### 检索某个列: select (列名) from (表名);
  此处的数据是没有经过排序的，所以每一次结果不一定是顺序相同的。
```SQL
select name from test;
+------------+
| name       |
+------------+
| Seanforfun |
| Irene      |
| Jenny      |
+------------+
3 rows in set
```

#### 检索多个列： SELECT （列名1， 列名2 ..） FROM （表名）；
```SQL
SELECT id, name from test;
+----+------------+
| id | name       |
+----+------------+
|  1 | Seanforfun |
|  2 | Irene      |
|  3 | Jenny      |
+----+------------+
3 rows in set
```

#### 检索多个列: 使用通配符*，但是*这种检索似乎会造成速度上的损失。
```SQL
SELECT
  *
FROM
  test;
+----+------------+
| id | name       |
+----+------------+
|  1 | Seanforfun |
|  2 | Irene      |
|  3 | Jenny      |
+----+------------+
3 rows in set
```

### 检索不同的行： SELECT DISTINCT (列名1， 列名2) FROM (表名)。
```SQL
SELECT DISTINCT
  name
FROM
  test;
+------------+
| name       |
+------------+
| Seanforfun |
| Irene      |
| Jenny      |
+------------+
3 rows in set

# 当我们使用DISTINCT时，如果我们跟着多个字段，对于任意一行，如果有一个值不相同，不被视为DISTINCT。
SELECT DISTINCT
  id, name
FROM
  test;
+----+------------+
| id | name       |
+----+------------+
|  1 | Seanforfun |
|  2 | Irene      |
|  3 | Jenny      |
|  4 | Seanforfun |
+----+------------+
4 rows in set
```
