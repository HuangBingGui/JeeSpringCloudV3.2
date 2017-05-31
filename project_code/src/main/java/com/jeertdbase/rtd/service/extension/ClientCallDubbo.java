package com.jeertdbase.rtd.service.extension;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public  class ClientCallDubbo {
	
	/**get**/
	public static String Get(HttpServletRequest request,String apiUrl) throws Exception {		
		HashMap<String,String> map=RequestParameterNames(request);
		String str= null;
		String content = getContent(map);//解析参数（请求的内容）
	    String urlNameString = apiUrl + "?" + content;
		URL url = new URL(urlNameString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setReadTimeout(5000);//将读超时设置为指定的超时，以毫秒为单位。用一个非零值指定在建立到资源的连接后从 Input 流读入时的超时时间。如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。
		con.setDoInput(true);//指示应用程序要从 URL 连接读取数据。
		con.setRequestMethod("GET");//设置请求方式
		if(con.getResponseCode() == 200){//当请求成功时，接收数据（状态码“200”为成功连接的意思“ok”）
			InputStream  is = con.getInputStream();
			str = formatIsToString(is);
		}
		return str;
	}
	
	/**post**/
	public static String Post(HttpServletRequest request, String apiUrl) throws Exception {
		HashMap<String,String> map=RequestParameterNames(request);
        String str = null;
		String content = getContent(map);//解析参数（请求的内容）
		if(content==null)
		   content="";
		URL url = new URL(apiUrl);//根据参数创建URL对象
        HttpURLConnection con = (HttpURLConnection)url.openConnection();//得到HttpURLConnection对象
        con.setRequestMethod("POST");
        con.setReadTimeout(5000);
        con.setDoInput(true);
        con.setDoOutput(true);//指示应用程序要将数据写入 URL 连接。
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Content-Length", String.valueOf(content.length()));//设置内容长度
        OutputStream os = con.getOutputStream();
        os.write(content.getBytes("utf-8"));//发送参数内容
        os.flush();
        os.close();
        if(con.getResponseCode() == 200){
            str = formatIsToString(con.getInputStream());
        } 
        
        System.out.println("参数：" +con.getResponseCode() );  
        return str;
	}

	private static  HashMap<String, String> RequestParameterNames(HttpServletRequest request) {
		HashMap<String,String>	map=new HashMap<String, String>();
		Enumeration params=request.getParameterNames();
		while (params.hasMoreElements()) {
			 String paramName = (String) params.nextElement();  
		      String[] paramValues = request.getParameterValues(paramName);  
		      if (paramValues.length == 1) {  
		        String paramValue = paramValues[0];  
		        if (paramValue.length() != 0) {     
		         System.out.println("参数：" + paramName + "=" + paramValue);  
		          map.put(paramName, paramValue);  
		        }  
		  }
		}
		return map;
	}

	private static  String getContent(HashMap<String, String> params) throws UnsupportedEncodingException {
        String content = null;
        Set<Entry<String, String>> set = params.entrySet();//Map.entrySet 方法返回映射的 collection 视图，其中的元素属于此类
        StringBuilder sb = new StringBuilder();
        for(Entry<String,String> i: set){//将参数解析为"name=tom&age=21"的模式
            sb.append(i.getKey()).append("=")
            .append(URLEncoder.encode(i.getValue(), "utf-8"))
            .append("&");
        }
        if(sb.length() > 1){
            content = sb.substring(0, sb.length()-1);
        }
        return content;
    }
	
	private static  String formatIsToString(InputStream is) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        try {
            while( (len=is.read(buf)) != -1){
                baos.write(buf, 0, len);
            }
            baos.flush();
            baos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(baos.toByteArray(),"utf-8");
    }

	private String form2JSON(HttpServletRequest request) {
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
	
    private String getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
        	BufferedReader reader = req.getReader();
                 char[]buff = new char[1024];
                 int len;
                 while((len = reader.read(buff)) != -1) {
                          sb.append(buff,0, len);
                 }
        }catch (IOException e) {
                 e.printStackTrace();
        }
        return sb.toString();
    }
}
