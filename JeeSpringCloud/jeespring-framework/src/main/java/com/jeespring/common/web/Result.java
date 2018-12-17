package com.jeespring.common.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result extends HashMap<String, Object> implements Map<String, Object>{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private HashMap<String,Object> resultHashMap = new HashMap<String, Object>();

    public void setResultObject(Object obejct) {
        put("RESULT", obejct);
    }

    public void setResultExtend(String key,Object object){
        JSONObject jsonObject= JSON.parseObject(JSON.toJSONString(get("RESULT")));
        jsonObject.put(key,object);
        put("RESULT", jsonObject);
    }

	public void setResultHashMap(String item,Object obejct) {
        resultHashMap.put(item,obejct);
        put("RESULT", resultHashMap);
    }

    public void setResultCode(Object resultCode) {
        put("CODE", resultCode);
    }

    public Object getResultCoe(){
        return get("CODE");
    }

    public Object getResultCode(){
        return get("CODE");
    }

    public <T> List<T> getResutObjectList() {

        return (List<T>) get("RESULT");
    }

    public <V, K> Map<K,V> getResutObjectMap() {
        return (Map<K, V>) get("RESULT");
    }

    public <T extends Object> T getResultObject() {
        return (T) get("RESULT");
    }
}
