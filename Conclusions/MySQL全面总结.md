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

#### 检索不同的行： SELECT DISTINCT (列名1， 列名2) FROM (表名)。
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

#### 限制出现结果的数量 LIMIT (开始行), (行数);在实际使用中多用于分页对象。
#### 实际上还有另外一中语法可以限制出现信息的数量， LIMIT 4 OFFSET 3; 这表示从行号3开始取，取出4条信息。
```SQL
SELECT
  name
FROM
  test
LIMIT
  0, 1; # 此处说明出现的第一条数据的行号是0，并且总共只出现1条数据。
+------------+
| name       |
+------------+
| Seanforfun |
+------------+
1 row in set

SELECT name
FROM test
LIMIT 3 OFFSET 1;
+------------+
| name       |
+------------+
| Irene      |
| Jenny      |
| Seanforfun |
+------------+
3 rows in set
```

#### 使用完全限定的表名
1. 定位列： 表名.列名
2. 定位表: 数据库名.表名

```SQL
SELECT test.id  # 说明当前列是位于test表中的。
from test.test; # 当前表是位于数据库test中的test表。
+----+
| id |
+----+
|  1 |
|  2 |
|  3 |
|  4 |
+----+
4 rows in set
```

### 数据检索排序
本模块主要讲解ORDER BY语句的使用方式。

如果不进行排序，数据一般会以它们在底层中的顺序进行显示，这可以是数据添加到表中的顺序。如果数据进行了删除，MySQL底层会对数据进行优化，则数据顺序有可能会发生变化，此时我们需要用ORDER BY字段确定数据的顺序。

#### 使用ORDER BY排序
```SQL
SELECT name FROM test ORDER BY name;
+------------+
| name       |
+------------+
| Irene      |
| Jenny      |
| Seanforfun |
| Seanforfun |
+------------+
4 rows in set

SELECT * FROM test ORDER BY id;
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

#### 按序排列多个列 ORDER BY (字段1)， (字段2)...
1. 针对多个列进行排列，打个比方，我们首先按照姓进行排列，然后通过名字进行排列。
2. 将优先排序的字段放在前面。

```SQL
SELECT *
FROM test
ORDER BY surname, name; # 首先会对surname进行排序，然后再对name进行排序。

+----+------------+---------+
| id | name       | SURNAME |
+----+------------+---------+
|  2 | Irene      | REN     |
|  3 | Jenny      | XIAO    |
|  1 | Seanforfun | XIAO    |
|  4 | Seanforfun | XIAO    |
+----+------------+---------+
4 rows in set
```

#### 指定方向进行排序 DESC ASC
1. ASC: 升序，系统默认的排序方式是升序。
2. DESC:降序。
3. 配合ORDER BY字段使用，在要确定顺序的字段后面加入ASC或DESC，默认的字段是ASC。

```SQL
SELECT *
FROM test
ORDER BY surname DESC, name;

+----+------------+---------+
| id | name       | SURNAME |
+----+------------+---------+
|  3 | Jenny      | XIAO    |
|  1 | Seanforfun | XIAO    |
|  4 | Seanforfun | XIAO    |
|  2 | Irene      | REN     |
+----+------------+---------+
4 rows in set
```

#### ORDER BY和LIMIT的结合
我们通过该种方法可以取出排序的前几位。

```SQL
SELECT * FROM test ORDER BY salary DESC LIMIT 0, 1;

+----+------------+---------+--------+
| id | name       | SURNAME | salary |
+----+------------+---------+--------+
|  1 | Seanforfun | XIAO    |  10000 |
+----+------------+---------+--------+
1 row in set
```

### 数据过滤
数据库一般包含着大量的信息，我们在对数据进行操作时往往要对数据进行过滤，此处我们会选用where关键字对条件进行筛选以达到我们需要的效果。

实际上有两种方法对数据进行过滤，第一种是通过数据库过滤，返回应用端的直接就是我们需要的数据集合，而第二种方法我们在数据库端返回所有的数据，然后再后台对数据进行筛选。显然第一种方法的效率是更高的。

| 操作符 | 说明 |
| :------: | :------: |
| = | 等于 |
| ！= | 不等于 |
| < | 小于 |
| > | 大于 |
| <= | 小于等于 |
| >= | 大于等于 |
| BETWEEN | 在两个值之间 |

```SQL
# 检查单个值

SELECT *
FROM test
WHERE salary < 10000;

+----+-------+---------+--------+
| id | name  | SURNAME | salary |
+----+-------+---------+--------+
|  2 | Irene | REN     |   8999 |
|  3 | Jenny | XIAO    |   8000 |
+----+-------+---------+--------+
2 rows in set

# 检查范围，使用BETWEEN会将两端都包括进去。
SELECT *
FROM test
WHERE
  salary between 8000 and 10000;

+----+------------+---------+--------+
| id | name       | SURNAME | salary |
+----+------------+---------+--------+
|  1 | Seanforfun | XIAO    |  10000 |
|  2 | Irene      | REN     |   8999 |
|  3 | Jenny      | XIAO    |   8000 |
+----+------------+---------+--------+
3 rows in set

# 空值检查 IS NULL
SELECT *
FROM test
where
 salary IS NULL;

+----+------------+---------+--------+
| id | name       | SURNAME | salary |
+----+------------+---------+--------+
|  4 | Seanforfun | XIAO    | NULL   |
+----+------------+---------+--------+
1 row in set
```
