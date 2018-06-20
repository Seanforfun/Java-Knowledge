# CompileAndOptimization

### 早期（编译期）优化
> Java 语言的编译期是一端不确定的操作过程。这是一个把*.java转化成*.class的过程。

Sun javac的编译过程可以个分成三步：
1. 解析与填充符号表过程。
2. 插入式注解处理器的注解处理过程。
3. 分析与字节码生成过程。
![javac的编译过程](https://i.imgur.com/NDOFDuR.jpg)

### 源码解析（OpenJDK8）
> 编译的入口：com.sun.tools.javac.main.JavaCompiler

```Java
 try {
            initProcessAnnotations(processors);	//准备过程：初始化插入式注解处理器。

            // These method calls must be chained to avoid memory leaks
            delegateCompiler =
                processAnnotations(	//过程2：执行注解处理
					//enterTrees: 过程1.2:输入到符号表
					//parseFiles:过程1.1：词法分析、语法分析
                    enterTrees(stopIfError(CompileState.PARSE, parseFiles(sourceFileObjects))),
                    classnames);

            delegateCompiler.compile2();	//过程3：分析及字节码生成。
			//在compile2中
			{
				case BY_TODO:
                while (!todo.isEmpty())
				//generate,过程3.4：生成字节码
				//desugar,过程3.3：解语法糖
				//flow，过程3.2：数据流分析
				//attribute,过程3.1：标注
                    generate(desugar(flow(attribute(todo.remove()))));
                break;
			}
            delegateCompiler.close();
            elapsed_msec = delegateCompiler.elapsed_msec;
        } catch (Abort ex) {
            if (devVerbose)
                ex.printStackTrace(System.err);
        } finally {
            if (procEnvImpl != null)
                procEnvImpl.close();
        }
```

#### 解析与符号表填充
> 通过parseFiles()方法实现了编译原理中的词法分析和语法分析。

1. 词法分析(lexical analysis)将源代码的字符流转变成标记（Token）集合，单个Token是编译过程中的最小元素。例如int a = c + b, 可以被分解成int, a, =, c, +, b这六个Token.
2. 语法分析(grammatical analysis)根据Token序列来构造抽象语法树（Abstract Syntax Tree）的过程。AST中的每个结点都代表着程序代码中的一个语法结构。

#### 填充符号表
> 通过enterTrees()方法实现了填充符号表

1. 符号表(Symbol Table)是一组符号地址和符号信息。
2. 这个方法的出口是一个待处理列表(TO DO LIST)。包含了每一个编译单元的抽象语法树的顶级结点。

#### 注解处理器
> 在JDK5中注解是在运行期发挥作用的。而JDK6中提供了一组插入式注解器的标准，可以读取，修改，添加抽象语法树中的任意元素。