package cn.apidev.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 在SpringBoot项目中使用
 *
 */
@Component
public class ApidevApplicationContext implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	ApidevApplicationContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public final static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public final static Object getBean(String beanName, Class<?> requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }
}
