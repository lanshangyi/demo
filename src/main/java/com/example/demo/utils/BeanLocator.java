package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihongliang on 2017/2/28.
 */
@Slf4j
@Component
public class BeanLocator implements ApplicationContextAware {
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        String[] names = context.getBeanNamesForType(clazz);
        if (names == null || names.length == 0) {
            return null;
        }
        return context.getBean(names[0], clazz);
    }

    public static <T> List<T> getBeans(Class<T> clazz) {
        String[] names = context.getBeanNamesForType(clazz);
        if (names == null || names.length == 0) {
            return null;
        }
        List<T> beans = new ArrayList<T>();
        for (String name : names) {
            beans.add(context.getBean(name, clazz));
        }
        return beans;
    }

    public static <T> T getBean(Class<T> clazz, BeanSelector<T> selector) {
        String[] names = context.getBeanNamesForType(clazz);
        if (names == null || names.length == 0) {
            return null;
        }

        for (String beanName : names) {
            T bean = context.getBean(beanName, clazz);
            if (selector.select(bean, context)) {
                return bean;
            }
        }

        return null;
    }

    public static String getSystemAttr(String name) {
        return BeanLocator.getApplicationContext().getEnvironment().getProperty(name);
    }

    protected static ApplicationContext context = null;

//    public static void setApplicationContext(ConfigurableApplicationContext context) {
//        BeanLocator.context = context;
//    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    /**
     * context可能存在多个满足条件的bean,由用户实现选择逻辑
     *
     * @param <T>
     * @author pippo
     * @since 2010-8-16
     */
    public static interface BeanSelector<T> {

        boolean select(T bean, ApplicationContext context);

    }

	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		if(BeanLocator.context == null) {

			BeanLocator.context = arg0;

        }

        log.info("ApplicationContext配置成功,applicationContext对象："+BeanLocator.context);
	}
}
