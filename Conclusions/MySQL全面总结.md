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
### 组合Where语句

```
+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
|  2 | Yijia | REN     |  10000 |
|  3 | Jinyu | XIAO    |  80000 |
+----+-------+---------+--------+
```

#### AND操作符
在使用条件语句的同时，一个条件变量一般是不够的，我们就需要使用AND操作符来连接多个条件变量，将会返回符合所有条件变量的数据。

```SQL
# 使用and连接了两个条件变量，surname和salary。
SELECT *
FROM test
WHERE
	surname = 'XIAO' AND salary > 90000;

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
+----+-------+---------+--------+
1 row in set
```

#### OR操作符
通过OR操作符，只要满足了任意一个条件变量，我们就会返回该条信息。

```SQL
SELECT *
FROM test
WHERE
	surname = 'REN' or salary > '90000';

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
|  2 | Yijia | REN     |  10000 |
+----+-------+---------+--------+
2 rows in set
```

#### 计算次序
在使用条件变量的过程中，连接操作符是有优先级设置的。

* NOT > AND > OR
* 在我们使用的过程中，我们需要用（）来限定优先级。括号内的限定优先。

```SQL
SELECT *
FROM test
WHERE
	id = 2 OR id = 3 AND salary < 10000;

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  2 | Yijia | REN     |  10000 |
+----+-------+---------+--------+
1 row in set

SELECT *
FROM test
WHERE
	(id = 2 OR id = 3) AND salary < 10000;

Empty set
```

#### IN操作符
通过IN来限定范围。和BETWEEN AND的使用不同。
* BETWEEN AND：给了上限和下限，返回所有范围内的值。
* IN： 给出多个值，如果in内的值存在，则返回该值。

```SQL
SELECT *
FROM test
WHERE
	salary IN (90000, 10000);

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  2 | Yijia | REN     |  10000 |
+----+-------+---------+--------+
1 row in set

SELECT *
FROM test
WHERE
	name in ('Botao', 'xxxx');

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
+----+-------+---------+--------+
1 row in set
```

为什么要使用IN操作符：
1. 在使用长的合法选项清单时，IN操作符更加直观。
2. 在使用IN时，计算的次序更容易管理。
3. IN操作符比OR更快。
4. IN操作符可以包含其他的WHERE语句，可以更动态的建立WHERE子句。

#### NOT操作符
NOT操作符可以为之后的条件变量取反。
NOT操作符在MySQL中只能针对IN, BETWEEN进行取反，在别的BDMS中，大多可以对所有的操作符进行操作。

```SQL
SELECT *
FROM test
WHERE
	id NOT IN (1,3);

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  2 | Yijia | REN     |  10000 |
+----+-------+---------+--------+
1 row in set
```

### 通配符过滤 LIKE
在进行数据过滤的过程中，不一定能够提供所有的数据进行筛选，所以我们要使用模糊查询。
在模糊查询的过程中，我们使用关键词LIKE，并且使用通配符进行模糊匹配。

#### 通配符
1. %通配符： %通配符能匹配出任何字符出现任意次数。

```SQL
# 匹配出所有姓中包含X的信息。
SELECT *
FROM test
WHERE
	surname like '%X%';

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
|  3 | Jinyu | XIAO    |  80000 |
+----+-------+---------+--------+
2 rows in set
```

2. _通配符： _通配符能匹配出任意字符出现单一次数。

```SQL
# _替代了o，_位置可以填充所有单个字符。
SELECT *
FROM test
WHERE
	name LIKE 'B_tao';

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
+----+-------+---------+--------+
1 row in set
```

3. 通配符的使用技巧。在通配符的使用过程中，这种匹配的方法是好事较长，我们需要一些使用技巧。
* 不要过度使用通配符，如果别的操作可以替代，尽量使用别的操作。
* 如果无法避免使用通配符，不要将通配符放在搜索模式的开始处，不然速度是最慢的。
* 仔细注意通配符的位置，错误的通配符可能会造成错误的结果。

### 正则表达式匹配REGEXP
1. 基本字符匹配（.）：.表示匹配任意一个字符。

```SQL
SELECT *
FROM test
WHERE
	name REGEXP '.i';

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  2 | Yijia | REN     |  10000 |
|  3 | Jinyu | XIAO    |  80000 |
+----+-------+---------+--------+
2 rows in set
```

2. |操作符： 通过|搜索多个串之一，为其中的任意一个。

```SQL
# name中出现i或者o。
SELECT *
FROM test
WHERE
	name REGEXP 'i|o';

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
|  2 | Yijia | REN     |  10000 |
|  3 | Jinyu | XIAO    |  80000 |
+----+-------+---------+--------+
3 rows in set
```

3. 匹配多个字符之一[]

```SQL
# 匹配出首字符是ABX中的按任意一个。
SELECT *
FROM test
WHERE
	name REGEXP '[ABX]otao';

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
+----+-------+---------+--------+
1 row in set
```

4. 范围匹配符

```SQL
#匹配首字母在A-Z的范围内。
SELECT *
FROM test
WHERE
	name REGEXP '[A-Z]otao';

+----+-------+---------+--------+
| id | name  | surname | salary |
+----+-------+---------+--------+
|  1 | Botao | XIAO    | 100000 |
+----+-------+---------+--------+
1 row in set

#匹配首字母不在A-Z范围内。
SELECT *
FROM test
WHERE
	name REGEXP '[^A-Z]otao';

Empty set
```

### 计算字段

#### 什么是计算字段
1. 数据库中所存储的字段不一定是我们所需要的，有的时候我们在前端时候通过表达式（例如EL）进行拼接，实际上我们还可以在数据库端生成我们需要的字段，这就是计算字段。
2. 计算字段不是实际存在在数据库中的，我们在使用select的过程中利用类似拼接的操作构造出来的。
3. 可能使用数据端的计算字段会比页面端要快一些。

#### CONCAT() 多数SQL的DBMS使用+或是||,MySQL略有不同。CONCAT(字段1， 字段2...)

```SQL
# 使用CONCAT拼接姓名
SELECT
	CONCAT(name, ' ', surname)
FROM test
ORDER BY surname DESC;

+----------------------------+
| CONCAT(name, ' ', surname) |
+----------------------------+
| Botao XIAO                 |
| Jinyu XIAO                 |
| Yijia REN                  |
+----------------------------+
3 rows in set
```

#### TRIM, LTRIM, RTRIM去除字符串左右的空格。
1. TRIM(字段): 去除字段前后的空格。
2. LTRIM（字段）: 去除字段左侧的空格。
3. RTRIM(字段)： 去除字段右侧的空格。

```SQL
# 显示原本左右有空格的字段
SELECT CONCAT(name, ' ', surname) AS fullname
FROM test
ORDER BY surname DESC;

+---------------------------------+
| fullname                        |
+---------------------------------+
|               Botao        XIAO |
| Jinyu XIAO                      |
| Yijia REN                       |
+---------------------------------+
3 rows in set

# 去除左侧的空格
SELECT CONCAT(LTRIM(name), ' ', surname) AS fullname
FROM test
ORDER BY surname DESC;

+-------------------+
| fullname          |
+-------------------+
| Botao        XIAO |
| Jinyu XIAO        |
| Yijia REN         |
+-------------------+
3 rows in set

# 去除右侧的空格
SELECT CONCAT(RTRIM(name), ' ', surname) AS fullname
FROM test
ORDER BY surname DESC;

+--------------------------+
| fullname                 |
+--------------------------+
|               Botao XIAO |
| Jinyu XIAO               |
| Yijia REN                |
+--------------------------+
3 rows in set

# 通过TRIM去除左右的空格
SELECT CONCAT(TRIM(name), ' ', surname) AS fullname
FROM test
ORDER BY surname DESC;

+------------+
| fullname   |
+------------+
| Botao XIAO |
| Jinyu XIAO |
| Yijia REN  |
+------------+
3 rows in set
```

#### 执行算数计算
1. +: 加
2. -：减
3. *: 乘
4. /：除

```SQL
# 将salary * number

SELECT CONCAT(TRIM(name), ' ', surname) AS fullname, salary * number AS total
FROM test
ORDER BY total DESC;

+------------+---------+
| fullname   | total   |
+------------+---------+
| Botao XIAO | 1200000 |
| Jinyu XIAO |  960000 |
| Yijia REN  |  120000 |
+------------+---------+
3 rows in set

# 显示当前时间
SELECT NOW() AS time;

+---------------------+
| time                |
+---------------------+
| 2018-10-10 13:49:10 |
+---------------------+
1 row in set
```

### 函数
#### 文本处理函数
| 函数 | 说明 | 使用方法 |
| :------: | :------: | :------: |
| LEFT() | 截取字符串左侧的n位子串 | SELECT LEFT(字符串, 截取的位数) |
| LENGTH() | 返回字符串的长度 | SELECT LENGTH(字符串) |
| LOCATE() | 找出字符串的子串位置 | SELECT LOCATE(子串， 字符串) 注意：此处的index是从1开始的 |
| LOWER()， UPPER() | 将字符串转换成小/大写 | SELECT LOWER(字符串)，UPPER(字符串) |
| LTRIM(), RTRIM() | 去除左右两侧的空格 | LTRIM(字符串), RTRIM(字符串) |
| SUBSTRING() | 返回字符串的子串 | SUBSTRING(字符串， 起始位置， 长度) |

#### 时间处理函数
| 函数 | 说明 | 使用方法 |
| :------: | :------: | :------: |
| ADDDATE() | 增加一个日期（天，周） | [ADDDATE使用方法](http://www.w3school.com.cn/sql/func_date_add.asp) Example： SELECT name, ADDDATE(currenttime, interval 2 day) FROM test;|
| ADDTIME() | 增加一个时间（小时， 分，秒） | Example： select name, ADDDATE(currenttime, interval 2 HOUR) from test;|
| CURDATE() | 返回当前日期 | SELECT CURDATE();|
| CURTIME() | 返回当前时间 | SELECT CURTIME();|
| DATE_FORMAT() | 返回某种格式的时间 | [DATE_FORMAT()使用方法](http://www.w3school.com.cn/sql/func_date_format.asp)|
| HOUR(), MINUTE()， SECOND() | 返回当前时间的小时,分钟和秒 | SELECT HOUR(时间)， MINUTE(时间)， SECOND(时间);|
| DATE(), TIME() | 返回当前时间中的日期部分和时间部分 | SELECT DATE(时间), TIME(时间);|

* 返回某个时间段内的数据。
```SQL
SELECT * FROM test WHERE DATE(currenttime) BETWEEN '2016-1-1' AND '2020-1-1';

+----+-------+---------+--------+--------+---------------------+
| id | name  | surname | salary | number | currenttime         |
+----+-------+---------+--------+--------+---------------------+
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-10 14:26:49 |
|  2 | Yijia | REN     |  10000 |     12 | 2018-10-10 14:26:49 |
|  3 | Jinyu | XIAO    |  80000 |     12 | 2018-10-10 14:26:49 |
+----+-------+---------+--------+--------+---------------------+
3 rows in set
```

#### 数值处理函数
| 函数 | 说明 | 使用方法 |
| :------: | :------: | :------: |
| ABS() | 返回绝对值 | SELECT ABS(数值) |
| COS(), SIN(), TAN() | 返回三角函数 | SELECT COS(数值), SIN(数值), TAN(数值) |
| EXP() | 返回指数值 | SELECT EXP(数值) |
| MOD() | 返回余数 | SELECT MOD(除数， 被除数) |
| RAND() | 返回一个0 - 1的随机数 | SELECT RAND() |

### 聚集函数
#### AVG(): 平均值

```SQL
# 返回平均工资
SELECT AVG(SALARY) AS average_salary
FROM test;

+--------------------+
| average_salary     |
+--------------------+
| 63333.333333333336 |
+--------------------+
1 row in set
```

#### COUNT(): 返回数据的个数

```SQL
# 表中数据的个数。
SELECT COUNT(id) AS data_number
FROM test;

+-------------+
| data_number |
+-------------+
|           3 |
+-------------+
1 row in set
```

#### MAX()， MIN():返回数据中的最大值/最小值

```SQL
# 返回salary的最大值。
SELECT MAX(salary)
FROM test;

+-------------+
| MAX(salary) |
+-------------+
|      100000 |
+-------------+
1 row in set

# 返回salary的最小值。
SELECT MIN(salary)
FROM test;

+-------------+
| MIN(salary) |
+-------------+
|       10000 |
+-------------+
1 row in set
```

#### SUM(): 返回总和

```SQL
SELECT SUM(salary) FROM test;

+-------------+
| SUM(salary) |
+-------------+
|      190000 |
+-------------+
1 row in set
```

### 分组数据 GROUP BY， 通过某个数值进行分组

```SQL
# 显示相同groupid的数据的个数。

SELECT groupid, COUNT(*)
FROM test
GROUP BY groupid;

+---------+----------+
| groupid | COUNT(*) |
+---------+----------+
|       1 |        3 |
|       2 |        1 |
+---------+----------+
2 rows in set

# 使用WITH ROLLUP进行汇总

SELECT groupid, COUNT(*)
FROM test
GROUP BY groupid WITH ROLLUP;

+---------+----------+
| groupid | COUNT(*) |
+---------+----------+
|       1 |        3 |
|       2 |        1 |
| NULL    |        4 |
+---------+----------+
3 rows in set
```

#### 过滤分组HAVING
1. WHERE： 是用来过滤数据。
2. HAVING: 用来过滤分组。

```SQL
SELECT groupid, COUNT(*)
FROM test
GROUP BY groupid
HAVING COUNT(*) > 1;

+---------+----------+
| groupid | COUNT(*) |
+---------+----------+
|       1 |        3 |
+---------+----------+
1 row in set

SELECT *
FROM test
GROUP BY groupid
having sum(salary > 10000);
```

#### SELECT子句顺序
SELECT | FROM | WHERE | GROUP BY | HAVING | ORDER BY | LIMIT

### 子查询
子查询：通过SELECT语句查询数据，并将该数据用作外层的SELECT语句的过滤条件。
步骤
1. 通过SELECT语句查询出数据。
2. 将上一步的结果作为筛选条件，用于当前SELECT语句的WHERE中，用于限定条件。

```
+----+-------+---------+--------+--------+---------------------+---------+
| id | name  | surname | salary | number | currenttime         | groupid |
+----+-------+---------+--------+--------+---------------------+---------+
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-10 15:31:55 |       1 |
|  2 | Yijia | REN     |  10000 |     12 | 2018-10-10 15:31:55 |       1 |
|  3 | Jinyu | XIAO    |  80000 |     12 | 2018-10-10 15:31:55 |       1 |
|  4 | test  | test    | 121212 |     12 | 2018-10-10 15:34:51 |       2 |
+----+-------+---------+--------+--------+---------------------+---------+
+----+--------+
| id | gender |
+----+--------+
|  1 | MALE   |
|  2 | FEMALE |
|  3 | FEMALE |
|  4 | MALE   |
+----+--------+
```

* 通过子查询限定性别，查出test表中的信息。

```SQL
SELECT *
FROM test
WHERE
	id IN (SELECT id FROM gender WHERE gender = 'MALE');

+----+-------+---------+--------+--------+---------------------+---------+
| id | name  | surname | salary | number | currenttime         | groupid |
+----+-------+---------+--------+--------+---------------------+---------+
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-10 15:31:55 |       1 |
|  4 | test  | test    | 121212 |     12 | 2018-10-10 15:34:51 |       2 |
+----+-------+---------+--------+--------+---------------------+---------+

在执行包含子查询语句的查询语句时，MySQL遵循从内向外的原则：
1. 执行SELECT id FROM gender WHERE gender = 'MALE'； 将所有的id找到。
2. 将步骤1的结果带入原语句，并进行外层查询。
```