/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/HuangBingGui/jeespring">jeespring</a> All rights reserved.
 */
package com.jeespring.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 对象操作工具类, 继承org.apache.commons.lang3.ObjectUtils类
 * @author 黄炳桂 516821420@qq.com
 * @version 2014-6-29
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	/**
	 * 注解到对象复制，只复制能匹配上的方法。
	 * @param annotation
	 * @param object
	 */
	public static void annotationToObject(Object annotation, Object object){
		if (annotation != null){
			Class<?> annotationClass = annotation.getClass();
			Class<?> objectClass = object.getClass();
			for (Method m : objectClass.getMethods()){
				if (StringUtils.startsWith(m.getName(), "set")){
					try {
						String s = StringUtils.uncapitalize(StringUtils.substring(m.getName(), 3));
						Object obj = annotationClass.getMethod(s).invoke(annotation);
						if (obj != null && !"".equals(obj.toString())){
							if (object == null){
								object = objectClass.newInstance();
							}
							m.invoke(object, obj);
						}
					} catch (Exception e) {
						// 忽略所有设置失败方法
					}
				}
			}
		}
	}
	
	/**
	 * 序列化对象
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			if (object != null){
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				return baos.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反序列化对象
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			if (bytes != null && bytes.length > 0){
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static final int ErrorRe=-1;
	private static final String NullStringRe="";


	public static boolean isEmpty(Object o) {
		return o == null || o.toString().length() <= 0;
	}

	public static BigDecimal convertObjectToBigDecimal(Object o) {
		return o == null ? null :BigDecimal.valueOf(Double.parseDouble(o.toString()));
	}

	public static Integer convertObjectToInteger(Object o) {
		return o == null ? null : Integer.parseInt(o.toString());
	}

	public static String convertObjectToString(Object o) {
		return o == null ? null : o.toString();
	}

	public static Integer convertObjectToInteger2(Object o) {
		return o == null ? ErrorRe : Integer.parseInt(o.toString());
	}

	public static String convertObjectToString2(Object o) {
		return o == null ? NullStringRe : o.toString();
	}

	@SuppressWarnings("unchecked")
	private static <T> T getMapValue(Map<?, ?> params, Object key) {
		return (T) (null==params?null:params.get(key));
	}

	@SuppressWarnings("unchecked")
	public static <T> T getMapValue(Map<?, ?> params, Object key, Class<T> t) {
		return (T) getMapValue(params, key);
	}

	public static String getMapNotNullStrValue(Map<?, ?> params, Object key) {
		Object value = getMapValue(params, key);
		if(null == value) {
			return NullStringRe;
		}
		return String.valueOf(value);
	}

	public static int getMapNotNullIntValue(Map<?, ?> params, Object key) {
		Object value = getMapValue(params, key);
		if(null == value) {
			return 0;
		}
		int v = 0;
		if(value instanceof Integer) {
			v = ((Integer) value).intValue();
		} else {
			try {
				v = Integer.parseInt(String.valueOf(value));
			} catch (Exception e) { }
		}
		return v;
	}

	public static Integer getMapIntegerValue(Map<?, ?> params, Object key) {
		Object value = getMapValue(params, key);
		if(null == value) {
			return null;
		}
		Integer v = 0;
		if(value instanceof Integer) {
			v = ((Integer) value).intValue();
		} else {
			try {
				v = Integer.parseInt(String.valueOf(value));
			} catch (Exception e) {
				v = null;
			}
		}
		return v;
	}

	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.isEmpty();
	}

	public static boolean isNotEmpty(Collection<?> c) {
		return !isEmpty(c);
	}

	public static boolean isEmpty(Map<?, ?> c) {
		return c == null || c.isEmpty();
	}

	public static boolean isNotEmpty(Map<?, ?> c) {
		return !isEmpty(c);
	}
}
