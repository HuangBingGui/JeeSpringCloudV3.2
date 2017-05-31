package com.jeertdbase.rtd.service.extension;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class JSONObjectX {

		@SuppressWarnings("unchecked")
		public static <T> T parseObject(HttpServletRequest request,T t){
			if(!request.getMethod().equals("POST")) return null;
			 t=(T)JSONObject.parseObject(form2JSON(request),t.getClass());
			 return t;
		}
		public static String form2JSON(HttpServletRequest request) {
			JSONObject obj = new JSONObject();
			Map<String,String[]> map = request.getParameterMap();
			Iterator < String > itReq = map.keySet().iterator();
			while (itReq.hasNext()) {
				String key = itReq.next();
				String val = map.get(key).length > 0 ? map.get(key)[0] : "";
				obj.put(key, val);
			}
			return obj.toJSONString();
		}

}
