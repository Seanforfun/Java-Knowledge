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