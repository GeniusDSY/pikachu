package cn.edu.cqupt.pikachu.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author :DengSiYuan
 * @date :2021/3/12 13:49
 * @desc : 索引数据缓存
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    /**
     * 应用程序上下文数据
     */
    private static ApplicationContext applicationContext;

    /**
     * 数据缓存
     */
    private static final Map<Class, Object> dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    /**
     * 获取索引类
     *
     * @param clazz 类
     * @param <T> 类型
     * @return 类
     */
    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz) {
        T instance = (T) dataTableMap.get(clazz);
        if (null != instance) {
            return instance;
        }

        dataTableMap.put(clazz, bean(clazz));
        return (T) dataTableMap.get(clazz);
    }

    /**
     * 通过Bean的名称获取Bean
     *
     * @param beanName Bean的名称
     * @param <T> Bean的类型
     * @return Bean实例
     */
    @SuppressWarnings("all")
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 通过类型获取Bean
     *
     * @param clazz Bean的类
     * @param <T> 类型
     * @return Bean实例
     */
    @SuppressWarnings("all")
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

}
