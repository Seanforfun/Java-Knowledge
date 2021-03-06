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

2. 通配符(_ )： _ 通配符能匹配出任意字符出现单一次数。

```SQL
# _ 替代了o，_位置可以填充所有单个字符。
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
3. * : 乘
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

### 联结表
#### 联结表的意义
1. 联结表是一种关系表的体现，可以通过实现关系表来减少数据库的存储量。
2. 多种产品可能对应同一个厂家，我们没有必要对所有的产品均存储一份厂家信息。我们就可以通过联结表来获得对应的所有信息。

#### 外键（foreign key）
外键是一张表中的某一列，它包含了另一个表的主键值，定义了两个表之间的关系。

优势
* 信息不会发生重复，不浪费时间和空间。
* 如果某个外键发生了改变，我们不需要到每一条对应的数据进行修改，只需要统一修改信息。
* 数据并不重复，所以对于数据的处理更加简单。

```Xml
1. test表
+----+-------+---------+--------+--------+---------------------+---------+---------+
| id | name  | surname | salary | number | currenttime         | groupid | company |
+----+-------+---------+--------+--------+---------------------+---------+---------+
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 |
|  2 | Yijia | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
|  3 | Jinyu | XIAO    |  80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 |
|  4 | test  | test    | 121212 |     12 | 2018-10-19 16:38:05 |       2 |       2 |
+----+-------+---------+--------+--------+---------------------+---------+---------+

2. company表
+----+----------+
| id | name     |
+----+----------+
|  1 | CIC      |
|  2 | MCMASTER |
+----+----------+
```

#### 等值联结：联结两张表：通过同时访问两张表联结两张表
```sql
SELECT test.id, CONCAT(test.name, " ", test.surname) as name, company.name as company from test, company;
+----+------------+----------+
| id | name       | company  |
+----+------------+----------+
|  1 | Botao XIAO | CIC      |
|  1 | Botao XIAO | MCMASTER |
|  2 | Yijia REN  | CIC      |
|  2 | Yijia REN  | MCMASTER |
|  3 | Jinyu XIAO | CIC      |
|  3 | Jinyu XIAO | MCMASTER |
|  4 | test test  | CIC      |
|  4 | test test  | MCMASTER |
+----+------------+----------+
```
此时发现总数量为表1* 表2, 此处出现了笛卡尔积。因为两张表不知道在何处进行联结。

#### 通过where限定联结的位置
```SQL
SELECT
	test.id, CONCAT(test.name, " ", surname) as name, company.name as company
FROM
	test, company
WHERE test.company = company.id;
+----+------------+----------+
| id | name       | company  |
+----+------------+----------+
|  1 | Botao XIAO | MCMASTER |
|  2 | Yijia REN  | CIC      |
|  3 | Jinyu XIAO | CIC      |
|  4 | test test  | MCMASTER |
+----+------------+----------+
```

#### 联结多个表
SQL对一条SELECT语句中可以联结的数量没有限制，创建的联结也基本相同。

### 创建高级联结
#### 创建表别名 AS
```sql
SELECT
	t.id, CONCAT(t.name, " ", t.surname) AS fullname, c.name AS company
FROM
	test AS t, company AS c
WHERE
	t.company = c.id;
+----+------------+----------+
| id | fullname   | name     |
+----+------------+----------+
|  1 | Botao XIAO | MCMASTER |
|  2 | Yijia REN  | CIC      |
|  3 | Jinyu XIAO | CIC      |
|  4 | test test  | MCMASTER |
+----+------------+----------+
```
此时我们为test设置了别名t，company设置了别名c。

#### 自联结
1. 通过子语句查询所有和Botao同一个company的数据。
```SQL
SELECT
	id, CONCAT(name, " ", surname) AS fullname
FROM test WHERE
company = (SELECT company
	FROM test
	WHERE name = 'BOTAO');
+----+------------+
| id | fullname   |
+----+------------+
|  1 | Botao XIAO |
|  4 | test test  |
+----+------------+
```

2. 使用联结的查询
```sql
SELECT t1.id, CONCAT(t1.name, " ", t1.surname) as fullname
FROM test t1, test t2
where
	t1.company = t2.company AND t2.name = 'BOTAO';
+----+------------+
| id | fullname   |
+----+------------+
|  1 | Botao XIAO |
|  4 | test test  |
+----+------------+
```
我们多次使用同一张表，可以避免子语句而通过等值联结进行筛选。该方法比子语句快得多。

### 外联结
1. 许多联结讲一个表中的行与另一个表中的行进行关联，但有时候会需要包含没有关联行的那些行。

```SQL
# 此时，我们可以发现id为4的对应的company为NULL。
+----+-------+---------+--------+--------+---------------------+---------+---------+
| id | name  | surname | salary | number | currenttime         | groupid | company |
+----+-------+---------+--------+--------+---------------------+---------+---------+
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 |
|  2 | Yijia | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
|  3 | Jinyu | XIAO    |  80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 |
|  4 | test  | test    | 121212 |     12 | 2018-10-19 17:51:52 |       2 | NULL    |
+----+-------+---------+--------+--------+---------------------+---------+---------+
```

2. 使用内联结的结果无法显示id为4的信息,因为该条信息对应的company是NULL，所以必然无法匹配WHERE或是内联结。
```SQL
# 内联结
SELECT t.id, CONCAT(t.name, " ", t.surname) AS fullname, company.name
FROM test t INNER JOIN company
ON t.company = company.id;
# 子语句
SELECT t.id, CONCAT(t.name, " ", t.surname) AS fullname
FROM test t
WHERE t.company IN (select id FROM company);
# 等值联结
SELECT t.id, CONCAT(t.name, " ", t.surname) AS fullname, c.name
FROM test t, company c
WHERE t.company = c.id;
+----+------------+----------+
| id | fullname   | name     |
+----+------------+----------+
|  1 | Botao XIAO | MCMASTER |
|  2 | Yijia REN  | CIC      |
|  3 | Jinyu XIAO | CIC      |
+----+------------+----------+
```

3. 使用外联结从test表向为基点，显示所有的左侧的信息。LEFT OUTER JOIN
```Sql
SELECT t.id AS id, CONCAT(t.name, " ", t.surname) AS fullname, company.name
FROM test t
LEFT OUTER JOIN company
ON t.company = company.id;
+----+------------+----------+
| id | fullname   | name     |
+----+------------+----------+
|  1 | Botao XIAO | MCMASTER |
|  2 | Yijia REN  | CIC      |
|  3 | Jinyu XIAO | CIC      |
|  4 | test test  | NULL     |
+----+------------+----------+
```

4. 使用右外联结，以右侧的表为基准点，显示所有的右侧信息。 RIGHT OUTER JOIN
我们可以看到对应右侧的所有的信息都可以找到左侧的对应，实现了右侧基准点。
```Sql
SELECT *
FROM test
RIGHT OUTER JOIN company
on test.company = company.id;
+----+-------+---------+--------+--------+---------------------+---------+---------+----+----------+
| id | name  | surname | salary | number | currenttime         | groupid | company | id | name     |
+----+-------+---------+--------+--------+---------------------+---------+---------+----+----------+
|  2 | Yijia | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |  1 | CIC      |
|  3 | Jinyu | XIAO    |  80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 |  1 | CIC      |
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 |  2 | MCMASTER |
+----+-------+---------+--------+--------+---------------------+---------+---------+----+----------+
```

### 通过UNION实现组合查询
1. 通过UNION实现组合查询，我们通过UNION连接两个SELECT语句，可以实现组合查询。
2. ORDER BY只能运用在最后一条SELECT语句上。
```SQL
SELECT *
FROM test
WHERE salary < 80000;
+----+-------+---------+--------+--------+---------------------+---------+---------+
| id | name  | surname | salary | number | currenttime         | groupid | company |
+----+-------+---------+--------+--------+---------------------+---------+---------+
|  2 | Yijia | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
+----+-------+---------+--------+--------+---------------------+---------+---------+
SELECT *
FROM test
WHERE id in (1, 4);
+----+-------+---------+--------+--------+---------------------+---------+---------+
| id | name  | surname | salary | number | currenttime         | groupid | company |
+----+-------+---------+--------+--------+---------------------+---------+---------+
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 |
|  4 | test  | test    | 121212 |     12 | 2018-10-19 17:51:52 |       2 | NULL    |
+----+-------+---------+--------+--------+---------------------+---------+---------+
SELECT *
FROM test
WHERE salary < 80000
UNION
SELECT *
FROM test
WHERE id in (1, 4);
+----+-------+---------+--------+--------+---------------------+---------+---------+
| id | name  | surname | salary | number | currenttime         | groupid | company |
+----+-------+---------+--------+--------+---------------------+---------+---------+
|  2 | Yijia | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
|  1 | Botao | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 |
|  4 | test  | test    | 121212 |     12 | 2018-10-19 17:51:52 |       2 | NULL    |
+----+-------+---------+--------+--------+---------------------+---------+---------+
```

### 插入数据
#### 插入数据 INSERT INTO (字段1， 字段2...) VALUES (信息1， 信息2...)

```SQL
# 插入数据，添加一个新的信息elex，数据的插入没有返回值，也可以理解为DAO层添加函数的返回值为void。
INSERT INTO company
(name) VALUES ('elex');

+----+----------+
| id | name     |
+----+----------+
|  1 | CIC      |
|  2 | MCMASTER |
|  3 | elex     |
+----+----------+
```
1. 总是使用列的列表：一般不要使用未给出明确的列表的INSERT语句，因为即使表被扩展后给出了明确的列名也可以正常使用。
2. 如果不提供列名，则必须提供所有的列的值。如果提供了列名，必须给该列提出值。
3. 省略列：如果表定义允许，可以在insert操作中省略某些列：
	* 该列允许被定义为NULL。
	* 表定义中给出默认值，如果未给出值，将使用默认值。
4. INSERT INTO可能非常耗时，INSERT操作会降低SELECT语句的性能。可以使用INSERT LOW_PRIORITY INTO.

#### 插入多条信息,一次插入多条信息的效率大于分次提交多条信息。
一次性提交多条insert语句。
```SQL
INSERT INTO
company (name)
VALUES ('AAA');
INSERT INTO
company (name)
VALUES ('BBB');

+----+----------+
| id | name     |
+----+----------+
|  1 | CIC      |
|  2 | MCMASTER |
|  3 | elex     |
|  4 | valeo    |
|  5 | AAA      |
|  6 | BBB      |
+----+----------+
```

#### INSERT和SELECT同时使用
通过INSERT向表中加入信息，而信息是通过SELECT从另一个表中读取出来的。

```SQL
# company_new和company的字段是一样的。此条信息实际上就是将company的信息复制到company_new中。
INSERT INTO company_new (id, name)
SELECT id, name FROM company;

+----+----------+
| id | name     |
+----+----------+
|  1 | CIC      |
|  2 | MCMASTER |
|  3 | elex     |
|  4 | valeo    |
|  5 | AAA      |
|  6 | BBB      |
+----+----------+
```

### 更新、删除数据
#### 更新数据UPDATE
1. 要更新的表的名字。
2. 列名和它们的新值。
3. 确定要更新的过滤条件。

```SQL
# 更新id为4的name和surname
UPDATE test
SET name='test111', surname='test222'
WHERE id = 4;
+----+---------+---------+--------+--------+---------------------+---------+---------+
| id | name    | surname | salary | number | currenttime         | groupid | company |
+----+---------+---------+--------+--------+---------------------+---------+---------+
|  1 | Botao   | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 |
|  2 | Yijia   | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
|  3 | Jinyu   | XIAO    |  80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 |
|  4 | test111 | test222 | 121212 |     12 | 2018-10-22 17:44:26 |       2 | NULL    |
+----+---------+---------+--------+--------+---------------------+---------+---------+
```

#### 同时更新多条语句UPDATE
```SQL
# 使用了ignore字段，即使有的语句中发生了错误，我们也能更新别的语句。
update ignore test set name='1212', surname='1212',happy='2222' where id=4; update ignore test set name='1212', surname='1212'；
```

#### 删除DELETE，将会删除表中的信息，不删除表本身。
1. 选定删除的表。
2. 选定要删除行的过滤条件。如果不指定，将会删除当前表中的所有信息。
3. 如果要删除表，使用TRUNCATE TABLE。
```sql
# 删除id=5的信息。
DELETE FROM test
WHERE id = 5;
```

#### 更新和删除的指导原则
1. 每次使用UPDATE和DELETE的过程中都要使用WHERE。
2. 保证每个表都有主键。尽可能使用主键，多个值和值的范围。
3. 在使用UPDATE和DELETE之前，使用SELECT进行测试，保证它过滤的是正确的记录。

### 创建和操作表
#### 创建表
1. 使用NULL值，在创建表中使用NULL，表明当前列可以为NULL；NOT NULL说明当前字段必须有值，不可以为NULL；
2. 主键， PRIMARY KEY(列名1， 列名2)。
3. 使用AUTO_INCREMENT使得某些列自增。通过SELECT LAST_INSERT_ID（）获取最后一次自增的数值。
4. 使用默认值DEFAULT。
5. 设置引擎： ENGINE=InnoDB
![Engine](https://i.imgur.com/pkUZ2BU.png)

#### 更新表
1. 增加表中的字段 ALTER TABLE 表名 ADD 列名 类型 选项
```SQL
# 增加一个varchar字段名为region，默认值为CANADA
ALTER TABLE test
ADD region varchar(100) NOT NULL DEFAULT 'Canada';
+----+---------+---------+--------+--------+---------------------+---------+---------+--------+
| id | name    | surname | salary | number | currenttime         | groupid | company | region |
+----+---------+---------+--------+--------+---------------------+---------+---------+--------+
|  1 | Botao   | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 | Canada |
|  2 | Yijia   | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 | Canada |
|  3 | Jinyu   | XIAO    |  80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 | Canada |
|  4 | test111 | test222 | 121212 |     12 | 2018-10-22 17:44:26 |       2 | NULL    | Canada |
+----+---------+---------+--------+--------+---------------------+---------+---------+--------+
```

2. 删除表中的列 ALTER TABLE 表名 DROP COLUMN 列名
```SQL
# 删除名为region的列并删除这些列中的信息。
ALTER TABLE test DROP COLUMN region;
+----+---------+---------+--------+--------+---------------------+---------+---------+
| id | name    | surname | salary | number | currenttime         | groupid | company |
+----+---------+---------+--------+--------+---------------------+---------+---------+
|  1 | Botao   | XIAO    | 100000 |     12 | 2018-10-19 16:37:32 |       1 |       2 |
|  2 | Yijia   | REN     |  10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
|  3 | Jinyu   | XIAO    |  80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 |
|  4 | test111 | test222 | 121212 |     12 | 2018-10-22 17:44:26 |       2 | NULL    |
+----+---------+---------+--------+--------+---------------------+---------+---------+
```

3. 修改表增加外键 ALTER TABLE 表名 ADD CONSTRAINT 约束名 FOREIGN KEY(外键字段) REFERENCES 外键表(外键表中字段);需要非常注意的使用ALTER TABLE，在改动前应该做一个完整的备份。

```SQL
# 添加一条约束tt，
ALTER TABLE test ADD CONSTRAINT tt
FOREIGN KEY(company) references company(id);
```

4. 删除表 DROP TABLE 表名
```SQL
# 删除某个表
DROP TABLE company_new;
Query OK, 0 rows affected
```

5. 重命名表 RENAME TABLE 表名 TO 新表名；
```SQL
# 将test表重命名为info
RENAME TABLE test TO info；
```

### 使用视图
我个人认为，视图的含义是我们在逻辑上抽象出一张新的表，这张表格就是我们通过SELECT语句查到的。通过保存视图避免了重复的查询过程，简化了我们的开发，存储了视图值，但是视图本身是一个抽象的含义，我们并没有为此新建一张表，实际上我们只是存储当前的语句并且在使用中作为子语句使用。
```SQL
# 创建一张名为highsalary的视图
CREATE VIEW highsalary AS
SELECT CONCAT(name, " ", surname) AS fullname
FROM info
WHERE salary > 8000;
SELECT * FROM highsalary;
+-----------------+
| fullname        |
+-----------------+
| Seanforfun XIAO |
| Irene REN       |
+-----------------+
# 查看创建视图的语句
SHOW CREATE VIEW highsalary；
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `highsalary` AS select concat(`info`.`name`,' ',`info`.`SURNAME`) AS `fullname` from `info` where (`info`.`salary` > 8000)
# 删除highsalary视图
DROP VIEW highsalary；
```
1. 增加了SQL的可重用性。
2. 创建可重用的视图值：例如我们在创建视图时添加了对信息的处理，以后只需要每次调用视图就会出现规定格式的结果。
3. 过滤不想要的数据。 （IS NOT NULL）
4. 在视图中使用计算字段。

### 存储过程 简单，安全，高性能
1. 存储过程简单来说，就是为以后的使用而保存的一条或多条MySQL语句的集合。
2. 我们将处理封装在容易使用的单元中，简化复杂的操作。
3. 在测试和开发的过程中我们遵循实用相同的存储过程，保证了数据的一致性。
4. 简化了对变动的管理，如果表名，列名或者逻辑业务发生了变化，我们只需要改变存储过程即可，使用它的人甚至东瀑布需要看到这样的变化。

#### 创建存储过程 CREATE PROCUDURE 存储过程名
1. 存储过程的添加，调用和删除
```SQL
# 创建一个存储过程，用于计算平均工资
CREATE PROCEDURE avgprice()
BEGIN
    SELECT AVG(salary) AS average_salary
    FROM INFO;
END;
# 调用存储过程
CALL avgprice()；
# 删除存储过程
DROP PROCEDURE avgprice;
# 如果存在就删除
DROP PROCEDURE avgsalary IF EXISTS;
```

#### 使用参数
所有的参数都应该以@开头，在存储过程中传入参数，而变量则是内存中的一个特定的位置。
```Sql
# 创建存储过程，声明变量，声明存储过程。
# IN:传递给存储过程
# OUT：从存储过程传出
# INOUT:对存储过程传入和传出
# INTO： 在BEGIN和END之间写出存储过程的代码，用来检索值，保存到相应的变量。通过INTO指定关键字。
CREATE PROCEDURE salarys(
OUT sl DECIMAL(8,2),
OUT sh DECIMAL(8,2),
OUT sa DECIMAL(8,2)
)
BEGIN
SELECT MAX(salary) INTO sh FROM info;
SELECT MIN(salary) INTO sl FROM info;
SELECT AVG(salary) INTO sa FROM info;
END;
# 调用存储过程，我个人感觉就是创建一块内存，将查找出的值通过存储过程的调用将值附给自己定义的变量。
# 定义了三个变量（实际上是三块内存空间，我们传入三个变量）
# 运行该程序实行存储过程。
CALL salarys(@lowsalary, @highsalary, @avgsalary);
# 通过SELECT语句调用存储过程
SELECT @lowsalary, @highsalary, @avgsalary;
+------------+-------------+------------+
| @lowsalary | @highsalary | @avgsalary |
+------------+-------------+------------+
| 10000.00   | 121212.00   | 77803.00   |
+------------+-------------+------------+
# 使用IN, OUT传入传出变量,接收一个参数，将计算所得到的值传入total；
CREATE PROCEDURE totalsalary(
IN month INT,
OUT total DOUBLE
)
BEGIN
SELECT SUM(info.salary * month)
INTO total
FROM info;
END;
# 调用存储过程,传入一个值，并将返回值传入@total，此处要注意，变量是一个值，不能返回多个值。
CALL totalsalary(12, @total);
# 获取@total的值
SELECT @total；
+---------+
| @total  |
+---------+
| 3734544 |
+---------+
```

#### 使用智能存储过程
所谓的智能存储过程我理解为就是创建一个函数，在其中使用一些逻辑判断。
```SQL
# 创建一个存储过程，在存储过程中定义了一些变量
# 根据接收到的布尔值决定是否应该进行扣税
CREATE PROCEDURE aftertaxsalary(
IN month INT,
IN taxable BOOLEAN,
IN id INT,
OUT result DOUBLE
)COMMENT 'Get after tax salary'
BEGIN
	-- Declear a variable used to save salary before tax --
	DECLARE total DOUBLE;
	-- Declare tax rate --
	DECLARE taxrate DOUBLE DEFAULT 0.8;
	SELECT SUM(month * salary)
	INTO total
	FROM info
	WHERE info.id = id;
	-- Add logical --
	IF taxable THEN
		SELECT total * taxrate INTO total;
	END IF;
	-- Assign result to variable --
	SELECT total INTO result;
END;
# 检查存储过程的创建信息
SHOW CREATE PROCEDURE aftertaxsalary;
# 显示所有的存储过程信息
SHOW PROCEDURE STATUS;
+------+----------------+-----------+----------------+---------------------+---------------------+---------------+----------------------+----------------------+----------------------+--------------------+
| Db   | Name           | Type      | Definer        | Modified            | Created             | Security_type | Comment              | character_set_client | collation_connection | Database Collation |
+------+----------------+-----------+----------------+---------------------+---------------------+---------------+----------------------+----------------------+----------------------+--------------------+
| test | aftertaxsalary | PROCEDURE | root@localhost | 2018-10-23 11:50:08 | 2018-10-23 11:50:08 | DEFINER       | Get after tax salary | utf8                 | utf8_general_ci      | utf8_general_ci    |
| test | salarys        | PROCEDURE | root@localhost | 2018-10-23 11:02:22 | 2018-10-23 11:02:22 | DEFINER       |                      | utf8                 | utf8_general_ci      | utf8_general_ci    |
| test | totalsalary    | PROCEDURE | root@localhost | 2018-10-23 11:22:30 | 2018-10-23 11:22:30 | DEFINER       |                      | utf8                 | utf8_general_ci      | utf8_general_ci    |
+------+----------------+-----------+----------------+---------------------+---------------------+---------------+----------------------+----------------------+----------------------+--------------------+
```

#### 游标 CURSOR，实际上就是存储过程中的FOR循环
使用游标对数据进行遍历
```sql
# 创建一个游标，打开，关闭游标。
CREATE PROCEDURE processsalary()
BEGIN
	DECLARE temp DOUBLE;
	-- 定义一个游标，用于遍历一个列的集合
	DECLARE salaryvalue CURSOR
	FOR SELECT info.salary FROM info;
	# 打开游标
	OPEN salaryvalue;
	FETCH salaryvalue INTO temp;
	# 关闭游标
	CLOSE salaryvalue;
END;
# 创建一个表，向其中加入数据
CREATE PROCEDURE insertvalue(
IN salary DOUBLE
)
BEGIN
	INSERT INTO copy1 (salary) values (salary);
END;
# 遍历一列数据
CREATE PROCEDURE iteratesalary()
BEGIN
	DECLARE o DOUBLE;
	DECLARE done BOOLEAN DEFAULT false;
	DECLARE cur CURSOR FOR SELECT salary FROM info;
	DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = true;
	-- 如果表不存在，创建张新的表
	CREATE TABLE IF NOT EXISTS copy1(id INT(11) not null auto_increment primary key, salary DOUBLE);
	OPEN cur;
	REPEAT
		FETCH cur INTO o;
		-- 在存储过程中调用别的存储过程
		CALL insertvalue(o);
	-- 直到done=false我们结束循环
	UNTIL done END REPEAT;
	CLOSE cur;
END;
```

### 触发器TRIGGER
所有的MySQL语句和存储过程都需要被调用才能执行，但是我们可以通过触发器来让一些语句可以直接被调用。
1. 唯一的触发器名。
2. 触发器关联的表。
3. 触发器影响的活动（DELETE, UPDATE, INSERT）
4. 触发器执行的时机（BEFORE, AFTER）, BEFORE一般是用于净化数据。
```SQL
# 创建一个触发器，向log表中插入当前的时间。
CREATE PROCEDURE insertlog(
IN text VARCHAR(200)
)
BEGIN
    CREATE TABLE IF NOT EXISTS log(id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, log varchar(200));
    INSERT INTO log (log) VALUES (text);
END;
# 创建一个触发器，在info表被修改后调用insertlog存储过程
CREATE TRIGGER infoupdate
AFTER UPDATE ON info
FOR EACH ROW
CALL insertlog(now());
# 使用DELETE触发器，可以调用一张OLD表，这张表是只读的，并不能修改。
# 我们可以利用delete触发器备份数据。
CREATE TRIGGER deleteinfo
AFTER DELETE ON info
FOR EACH ROW
BEGIN
    INSERT INTO log (log) VALUES (old.salary);
END;
+----+---------------------+
| id | log                 |
+----+---------------------+
|  1 | 2018-10-23 14:18:28 |
|  2 | 121212              |
+----+---------------------+
```

### 事务管理
1. 事务：一组SQL语句
2. 回退：撤销SQL语句的过程
3. 提交：将未储存的SQL语句结果写入数据表
4. 保留点：对保留点进行回退

#### 控制事务管理
1. 开启事务
```SQL
START TRANSACTION;
```
2. 使用回退
```SQL
SELECT * FROM info;
# 开启事务
START TRANSACTION;
DELETE FROM info;
SELECT * FROM info;
# 事务回滚
ROLLBACK;
SELECT * FROM info;
+----+-------+---------+-----------+--------+---------------------+---------+---------+
| id | name  | surname | salary    | number | currenttime         | groupid | company |
+----+-------+---------+-----------+--------+---------------------+---------+---------+
|  1 | Botao | XIAO    | 123456567 |     12 | 2018-10-23 14:13:30 |       1 |       2 |
|  2 | Yijia | REN     |     10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
|  3 | Jinyu | XIAO    |     80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 |
+----+-------+---------+-----------+--------+---------------------+---------+---------+
3 rows in set
Query OK, 0 rows affected
Query OK, 3 rows affected
Empty set
Query OK, 0 rows affected
+----+-------+---------+-----------+--------+---------------------+---------+---------+
| id | name  | surname | salary    | number | currenttime         | groupid | company |
+----+-------+---------+-----------+--------+---------------------+---------+---------+
|  1 | Botao | XIAO    | 123456567 |     12 | 2018-10-23 14:13:30 |       1 |       2 |
|  2 | Yijia | REN     |     10000 |     12 | 2018-10-19 16:38:01 |       1 |       1 |
|  3 | Jinyu | XIAO    |     80000 |     12 | 2018-10-19 16:38:03 |       1 |       1 |
+----+-------+---------+-----------+--------+---------------------+---------+---------+
3 rows in set
# 提交事务，修改表结果，并提交结果
SELECT * FROM info；
START TRANSACTION；
UPDATE info set salary=1000000 WHERE id = 1;
COMMIT;
SELECT * FROM info；
+----+-------+---------+---------+--------+---------------------+---------+---------+
| id | name  | surname | salary  | number | currenttime         | groupid | company |
+----+-------+---------+---------+--------+---------------------+---------+---------+
|  1 | Botao | XIAO    | 1000000 |     12 | 2018-10-23 15:02:24 |       1 |       2 |
|  2 | Yijia | REN     |  800000 |     12 | 2018-10-23 15:00:53 |       1 |       1 |
|  3 | Jinyu | XIAO    |  800000 |     12 | 2018-10-23 15:00:53 |       1 |       1 |
+----+-------+---------+---------+--------+---------------------+---------+---------+
```

#### 保存点SAVEPOINT
```SQL
# 设置一个保留点
SAVEPOINT delete1;
# 回滚到一个保留点
ROLLBACK TO delete1;
```

### 备份数据
1. 使用mysqldump转储所有数据到某个外部文件。
2. 使用mysqlhotcopy从某个数据库复制所有数据。
3. 可以使用BACKUP TABLE或SELECT INTO OUTFILE转储所有数据到某个外部文件


