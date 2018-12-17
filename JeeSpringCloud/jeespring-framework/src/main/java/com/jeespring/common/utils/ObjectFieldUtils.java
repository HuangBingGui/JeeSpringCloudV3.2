package com.jeespring.common.utils;

import java.lang.reflect.Field;

public class ObjectFieldUtils {
    /**
     *
     * 获取对象属性赋值
     *
     * @param dObject
     * @param fieldName
     *            字段别名
     * @return
     */
    public static Object getFieldValue(Object dObject, String fieldName) {

        Object result = null;
        try {
            Field fu = dObject.getClass().getDeclaredField(fieldName); // 获取对象的属性域
            try {
                fu.setAccessible(true); // 设置对象属性域的访问属性
                result = fu.get(dObject); // 获取对象属性域的属性值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 给对象属性赋值
     *
     * @param dObject
     * @param fieldName
     * @param val
     * @return
     */
    public static Object setFieldValue(Object dObject, String fieldName, Object val) {

        Object result = null;

        try {
            Field fu = dObject.getClass().getDeclaredField(fieldName); // 获取对象的属性域
            try {
                fu.setAccessible(true); // 设置对象属性域的访问属性
                fu.set(dObject, val); // 设置对象属性域的属性值
                result = fu.get(dObject); // 获取对象属性域的属性值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return result;
    }
}
