package cn.com.duiba.credits.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class AssembleTool {

	public static String assembleUrl(String url,Map<String, String> params){
		if(!url.endsWith("?")){
			url+="?";
		}
		for(String key:params.keySet()){
			try {
				if(params.get(key)==null || params.get(key).length()==0){
					url+=key+"="+params.get(key)+"&";
				}else{
					url+=key+"="+URLEncoder.encode(params.get(key), "utf-8")+"&";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return url;
	}
	
}
