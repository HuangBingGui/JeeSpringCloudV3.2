package com.jeertd.rtd.api.extension;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONObjectX {
	
	    public <T> String toJSONString(T t){
			return JSON.toJSONString(t);
		}
	
		public <T> void parseObject(T t){
			//Page<TestData> page =(Page<TestData>)JSON.parseObject(string, new TypeReference<Page<TestData>>(){});
		}
		
		public static <T> T parseObject(HttpServletRequest request,T c){
			 return parseObject(request,c); 
		}
	
		@SuppressWarnings("unchecked")
		public static <T> T parseObject(HttpServletRequest request,Class<?> c){
			 return (T)JSONObject.parseObject(form2JSONString(request).toJSONString(),c); 
		}
		
		@SuppressWarnings("unchecked")
		public static <T> T parseObject(HttpServletRequest request,Class<?> c,String keyName){
			 return (T)JSONObject.parseObject(form2JSONString(request,keyName).toJSONString(),c); 
		}
		
		@SuppressWarnings("unchecked")
		public static <T> List<T> parseObjectArray(HttpServletRequest request,Class<?> c,String keyName){
			List<T> lt = (List<T>)JSON.parseArray(form2JSONArrayString(request,keyName).toJSONString().replace("\"", "'"), c);  
			return lt;
		}
		
		private static JSONObject form2JSONString(HttpServletRequest request,String keyName) {
			JSONObject jsonObjTemp=(JSONObject)form2JSONString(request).get(keyName);
			return jsonObjTemp;
		}
	
		private static JSONArray form2JSONArrayString(HttpServletRequest request,String keyName) {
			JSONArray jsonObjTemp=(JSONArray)form2JSONString(request).get(keyName);
			return jsonObjTemp;
		}
		
		private static JSONObject form2JSONString(HttpServletRequest request) {
			Map<String,String[]> reqParMap = request.getParameterMap();
			Iterator < String > itReq = reqParMap.keySet().iterator();
			JSONObject jsonObjMain=new JSONObject();
			while (itReq.hasNext()) {
				String key = itReq.next();
				String val = reqParMap.get(key).length > 0 ? reqParMap.get(key)[0] : "";
				String[] keys=key.split("\\.");
				//属性
				if(keys.length==1){jsonObjMain.put(key, reqParMap.get(key)[0]);continue;}
				JSONObject jsonObject=new JSONObject();
				//对象属性和数组
				for(int i=keys.length-1;i>=0;i--){
					//属性
					if(i==keys.length-1) jsonObject.put(keys[i], val);
					//对象
					else if(keys[i].indexOf("[")<0 && keys[i].indexOf("]")<0){
						if(i>0){
							if(!jsonObject.containsKey(keys[i])){//包一层JSONObject
								JSONObject jsonObjTemp=new JSONObject();
								jsonObjTemp.put(keys[i], jsonObject);
								jsonObject=jsonObjTemp;								
							}else{//找到JSONObject加入新属性
								JSONObject jsonObjTemp=(JSONObject)jsonObject.get(keys[i]);
								jsonObjTemp.put(keys[i], jsonObject);
							}
						}else if(i==0){//第一层
							if(jsonObjMain.containsKey(keys[i])){//jsonObjMain包含本属性
								JSONObject jsonObjTemp=(JSONObject)jsonObjMain.get(keys[i]);
								Iterator<String> itJsonObj = jsonObject.keySet().iterator();  
								while(itJsonObj.hasNext()){  
								    String itJsonObjKey = itJsonObj.next();
								    //if(jsonObject.get(itJsonObjKey).getClass().getName()=="com.alibaba.fastjson.JSONObject" || jsonObject.get(itJsonObjKey).getClass().getName()=="java.lang.String"){
								    jsonObjTemp.put(itJsonObjKey, jsonObject.get(itJsonObjKey));
								    //}
								} 
							}else{//jsonObjMain不包含本属性
								jsonObjMain.put(keys[i], jsonObject);
							}
						}
					}
					else if(keys[i].indexOf("[")>=0 && keys[i].indexOf("]")>=0){//集合
						Integer arrIndex=Integer.parseInt(key.substring(key.indexOf("[")+1, key.indexOf("]")));
						String keyClass=key.substring(0, keys[i].indexOf("["));
						JSONArray jsonArray=(JSONArray)(jsonObjMain.containsKey(keyClass) ? jsonObjMain.get(keyClass):new JSONArray());
						if(arrIndex<jsonArray.size()){
							JSONObject objx=(JSONObject)jsonArray.get(arrIndex);
							Iterator<String> itJsonObj = jsonObject.keySet().iterator();  
							while(itJsonObj.hasNext()){  
							    String itJsonObjKey = itJsonObj.next();  
							    objx.put(itJsonObjKey, jsonObject.get(itJsonObjKey));
							}  
						}else{
							jsonArray.add(jsonObject);
							jsonObjMain.put(keyClass,jsonArray);
						}
					}
				}
			}
			return jsonObjMain;
		}		
}
