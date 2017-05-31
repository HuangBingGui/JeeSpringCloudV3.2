package com.jeertdbase.rtd.service.extension;
import java.util.Properties;  
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PropertiesUtil {
	    private static ResourceLoader loader = ResourceLoader.getInstance();  
	    private static ConcurrentMap<String, String> configMap = new ConcurrentHashMap<String, String>();  
	    private static final String DEFAULT_CONFIG_FILE = "test.properties";  
	  
	    private static Properties prop = null;  
	  
	    public static String getStringByKey(String key, String propName) {  
	        try {  
	            prop = loader.getPropFromProperties(propName);  
	        } catch (Exception e) {  
	            throw new RuntimeException(e);  
	        }  
	        key = key.trim();  
	        if (!configMap.containsKey(key)) {  
	            if (prop.getProperty(key) != null) {  
	                configMap.put(key, prop.getProperty(key));  
	            }  
	        }  
	        return configMap.get(key);  
	    }  
	  
	    public static String getStringByKey(String key) {  
	        return getStringByKey(key, DEFAULT_CONFIG_FILE);  
	    }  
	  
	    public static Properties getProperties() {  
	        try {  
	            return loader.getPropFromProperties(DEFAULT_CONFIG_FILE);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
}
