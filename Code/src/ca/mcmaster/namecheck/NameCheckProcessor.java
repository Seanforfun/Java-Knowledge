package ca.mcmaster.namecheck;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jun 20, 2018 5:25:30 PM
 * @version 1.0
 */
@SupportedAnnotationTypes("*")
public class NameCheckProcessor extends AbstractProcessor {
	private NameChecker nameChecker;
	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		nameChecker = new NameChecker(processingEnv);
	}
	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		if(!roundEnv.processingOver()){
			for(Element e : roundEnv.getRootElements()){
				nameChecker.checkNames(e);
			}
		}
		return false;
	}

}
