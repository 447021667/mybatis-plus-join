package com.github.mybatisplus.interceptor;

import com.baomidou.mybatisplus.core.MybatisParameterHandler;
import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.mybatisplus.toolkit.Constant;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.cglib.beans.BeanMap;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 结果封装
 *
 * @author yulichang
 */
@Intercepts({@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})})
public class MyResultInterceptor implements Interceptor {

    private static Field parameterHandler = null;

    static {
        try {
            parameterHandler = DefaultResultSetHandler.class.getDeclaredField("parameterHandler");
            parameterHandler.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        DefaultResultSetHandler handler = (DefaultResultSetHandler) invocation.getTarget();
        Object result = invocation.proceed();
        if (Objects.isNull(result)) {
            return null;
        }
        Class<?> val = getFieldVal(handler);
        if (val == null) {
            return result;
        }
        if (result instanceof ArrayList) {
            List<Object> res = new ArrayList<>();
            for (Object i : ((ArrayList) result)) {
                if (i instanceof Map) {
                    res.add(mapToBean((Map<String, ?>) i, val));
                } else {
                    return result;
                }
            }
            return res;
        } else {
            return mapToBean((Map<String, ?>) result, val);
        }
    }

    public static <T> T mapToBean(Map<String, ?> map, Class<T> clazz) {
        T bean = ClassUtils.newInstance(clazz);
        BeanMap beanMap = BeanMap.create(bean);
        map.forEach((k, v) -> beanMap.put(StringUtils.underlineToCamel(k), v));
        return bean;
    }


    /**
     * 反射获取方法中的clazz
     * 先用反射获取,应该是可以通过拓展框架直接获取的, todo
     *
     * @see MybatisParameterHandler
     */
    public static Class<?> getFieldVal(DefaultResultSetHandler handler) {
        try {
            MybatisParameterHandler mybatisParameterHandler = (MybatisParameterHandler) parameterHandler.get(handler);
            Object object = mybatisParameterHandler.getParameterObject();
            if (Objects.isNull(object)) {
                return null;
            }
            if (object instanceof Map) {
                Map<?, ?> args = (Map<?, ?>) object;
                return (Class<?>) args.get(Constant.CLAZZ);
            }
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
