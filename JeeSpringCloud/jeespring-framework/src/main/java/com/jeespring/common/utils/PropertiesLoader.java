/**
 * Copyright (c) 2005-2011 springside.org.cn
 * <p>
 * $Id: PropertiesLoader.java 1690 2012-02-22 13:42:00Z calvinxiu $
 */
package com.jeespring.common.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.yaml.snakeyaml.Yaml;

/**
 * Properties文件载入工具类. 可载入多个properties文件, 相同的属性在最后载入的文件中的值将会覆盖之前的值，但以System的Property优先.
 *
 * @author calvin
 * @version 2013-05-15
 */
@SuppressWarnings("rawtypes")
public class PropertiesLoader {

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    private final Properties properties;

    public PropertiesLoader(String... resourcesPaths) {
        properties = loadProperties(resourcesPaths);
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * 取出Property，但以System的Property优先,取不到返回空字符串.
     */
    private String getValue(String key) {
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }
        return "";
    }

    /**
     * 取出String类型的Property，但以System的Property优先,如果都为Null则抛出异常.
     */
    public String getProperty(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return value;
    }

    /**
     * 取出String类型的Property，但以System的Property优先.如果都为Null则返回Default值.
     */
    public String getProperty(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Integer getInteger(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }

    /**
     * 取出Integer类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null或内容错误则抛出异常.
     */
    public Double getDouble(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }

    /**
     * 取出Double类型的Property，但以System的Property优先.如果都为Null则返回Default值，如果内容错误则抛出异常
     */
    public Double getDouble(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null抛出异常,如果内容不是true/false则返回false.
     */
    public Boolean getBoolean(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }

    /**
     * 取出Boolean类型的Property，但以System的Property优先.如果都为Null则返回Default值,如果内容不为true/false则返回false.
     */
    public Boolean getBoolean(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }

    /**
     * 载入多个文件, 文件路径使用Spring Resource格式.
     */
    private Properties loadProperties(String... resourcesPaths) {
        Properties props = new Properties();

        for (String location : resourcesPaths) {
            if (location.endsWith(".properties")) {
                InputStreamReader reader = null;
                try {
                    Resource resource = resourceLoader.getResource(location);
                    reader = new InputStreamReader(resource.getInputStream(), "utf-8");
                    props.load(reader);
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            } else if (location.endsWith(".yml")) {
                try {
                    Resource resource = resourceLoader.getResource(location);
                    Yaml yaml = new Yaml();
                    Map map = yaml.loadAs(resource.getInputStream(), Map.class);
                    map = resolv(map);
                    for (Object key : map.keySet()) {
                        props.put(key, map.get(key));
                    }
                } catch (Exception e) {
                    //e.printStackTrace(System.out);
                }
            }
        }
        return props;
    }

    /**
     * 递归解析map
     *
     * @param map yml初次解析的map
     * @return 解析后的map
     */
    private static Map<String, String> resolv(Map map) {
        Map<String, String> values = new HashMap<>();
        for (Object obj : map.keySet()) {
            String currentkey = obj.toString();
            Object currentObj = map.get(obj);
            if (currentObj instanceof Map) {
				Map currentMap = resolv((Map) currentObj);
                for (Object key : currentMap.keySet()) {
                    String mapKey = currentkey + "." + key.toString();
                    values.put(mapKey, currentMap.get(key).toString());
                }
            } else {
                values.put(currentkey, String.valueOf(currentObj));
            }
        }
        return values;
    }
}
