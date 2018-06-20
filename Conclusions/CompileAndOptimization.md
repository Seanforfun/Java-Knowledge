# CompileAndOptimization

### 早期（编译期）优化
> Java 语言的编译期是一端不确定的操作过程。这是一个把*.java转化成*.class的过程。

Sun javac的编译过程可以个分成三步：
1. 解析与填充符号表过程。
2. 插入式注解处理器的注解处理过程。
3. 分析与字节码生成过程。
![javac的编译过程](https://i.imgur.com/NDOFDuR.jpg)

### 源码解析（OpenJDK8）
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