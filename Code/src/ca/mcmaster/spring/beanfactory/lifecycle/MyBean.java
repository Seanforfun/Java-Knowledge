package ca.mcmaster.spring.beanfactory.lifecycle;
/**
 * @author SeanForFun E-mail:xiaob6@mcmaster.ca
 * @date Jul 17, 2018 12:33:50 PM
 * @version 1.0
 */
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
public class MyBean implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean{
    private BeanFactory beanFactory;
    private String name;
    private String tag;
    public MyBean(){
        System.out.println("调用构造器实例化Bean");
    }
    public void printName(){
        System.out.println("MyBean name is: " + name);
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
        System.out.println("设置Bean的属性为：" + tag);
    }
    @Override
    public void destroy() throws Exception {
        System.out.println("调用DisposableBean接口的destroy方法");
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用InitializingBean接口的afterPropertiesSet方法");
    }
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        System.out.println("调用BeanFactoryAware接口的setBeanFactory方法");
    }
    @Override
    public void setBeanName(String name) {
        this.name = name;
        System.out.println("调用BeanNameAware接口的setBeanName方法");
    }
}
