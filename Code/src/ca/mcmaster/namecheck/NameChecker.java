package ca.mcmaster.namecheck;

import java.util.EnumSet;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic.Kind;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 20, 2018 5:27:30 PM
 * @version 1.0
 */
public class NameChecker {
	//A messager provides the way for an annotation processor to report error messages, warnings, and other notices.
	private final Messager messager; 
	private final NameCheckScanner nameCheckScanner = new NameCheckScanner();
	public NameChecker(ProcessingEnvironment processingEnv) {
		this.messager = processingEnv.getMessager();
	}
	public void checkNames(Element e){
		nameCheckScanner.scan(e);
	}
	
	/**
	 * @author SeanForFun
	 * @date  Jun 21, 2018 10:04:58 AM
	 * @Description 
	 *  类或接口：驼峰式命名，首字母大写。
	 *  方法：驼峰式命名，首字母小写。
	 *  类、实例变量：驼峰式命名，首字母小写。
	 *  常量：全部大写。
	 * @version 1.0
	 */
	private class NameCheckScanner extends ElementScanner8<Void, Void>{
		
		/* (non-Javadoc)
		 * @see javax.lang.model.util.ElementScanner6#visitType(javax.lang.model.element.TypeElement, java.lang.Object)
		 * 用于检测Java类
		 */
		@Override
		public Void visitType(TypeElement e, Void p) {
			scan(e.getTypeParameters(), p);
			checkCamelCase(e, true);
			super.visitType(e, p);
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.lang.model.util.ElementScanner7#visitVariable(javax.lang.model.element.VariableElement, java.lang.Object)
		 * 如果这个变量是枚举或是常量则全部大写，否则遵守驼峰命名。
		 */
		@Override
		public Void visitVariable(VariableElement e, Void p) {
			if(e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e))
				checkAllCaps(e);
			else
				checkCamelCase(e, false);
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.lang.model.util.ElementScanner6#visitExecutable(javax.lang.model.element.ExecutableElement, java.lang.Object)
		 * 检查方法是否合法。
		 */
		@Override
		public Void visitExecutable(ExecutableElement e, Void p) {
			if(e.getKind() == ElementKind.METHOD){
				String name = e.getSimpleName().toString();
				if(name.equals(e.getEnclosingElement().getSimpleName().toString())){
					messager.printMessage(Kind.WARNING, "方法" + name + "不应该和类名相同", e);
				}
				checkCamelCase(e, false);
			}
			super.visitExecutable(e, p);
			return null;
		}
		
		private boolean heuristicallyConstant(VariableElement e){
			// 接口中的字段一定是常量。
			if(e.getEnclosingElement().getKind() == ElementKind.INTERFACE)
				return true;
			else if(e.getKind() == ElementKind.FIELD && e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)))
				return true;
			else
				return false;
		}

		/**
		 * @Description:
		 *  检查传入的Element是否符合驼峰法则。
		 *  Element: Token
		 *  initialCaps: 首字母是否应该大写。 
		 * @Return: void
		 */
		private void checkCamelCase(Element e, boolean initialCaps){
			String name = e.getSimpleName().toString();
			boolean previousUpper = false;
			boolean conventional = true;
			int firstCodePoint = name.codePointAt(0);
			if(Character.isUpperCase(firstCodePoint)){	//第一个码点是大写
				previousUpper = true;
				if(!initialCaps){
					messager.printMessage(Kind.WARNING, "名称" + name + "应当以小写字母开头", e);
					return;
				}
			}else if(Character.isLowerCase(firstCodePoint)){
				if(initialCaps){
					messager.printMessage(Kind.WARNING, "名称" + name + "应当以大写字母开头", e);
					return;
				}
			}else 
				conventional = false;
			
			if(conventional){
				int cp = firstCodePoint;	//cp指向第一个码点。
				for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)){
					cp = name.charAt(i);
					if(Character.isUpperCase(cp)){
						if(previousUpper){
							conventional = false;
							break;
						}
						previousUpper = true;
					}else
						previousUpper = false;
				}
			}
			
			if(!conventional){
				messager.printMessage(Kind.WARNING, "名称" + name + "应当符合驼峰命名法", e);
			}
		}
		
		/**
		 * @Description:检查所有的字母，要求第一个字母是大写，别的所有的字母都是大写或下划线。
		 * 不能有两个连续的下划线。
		 * @Return: void
		 */
		private void checkAllCaps(Element e){
			String name = e.getSimpleName().toString();
			int firstCodePoint = name.codePointAt(0);
			boolean conventional = true;
			
			if(!Character.isUpperCase(firstCodePoint))
				conventional = false;
			else{
				boolean previousUnderScore = false;
				int cp = firstCodePoint;
				for(int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)){
					cp = name.charAt(i);
					if(cp == (int)'_'){
						if(previousUnderScore)	conventional = false;
						previousUnderScore = true;
						break;
					}else {
						previousUnderScore = false;
						if(!Character.isDigit(cp) && !Character.isUpperCase(cp)){
							conventional = false;
							break;
						}
					}
				}
			}
			
			if(!conventional){
				messager.printMessage(Kind.WARNING, "常量" + name + "应当以下划线或全部大写命名，首字母应该为大写", e);
			}
		}
	}
}
