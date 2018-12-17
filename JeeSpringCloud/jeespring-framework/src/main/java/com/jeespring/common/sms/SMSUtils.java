package com.jeespring.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.jeespring.common.security.Digests;

/*
功能:		企信通PHP HTTP接口 发送短信
修改日期:	2014-03-19
说明:		http://api.cnsms.cn/?ac=send&uid=用户账号&pwd=MD5位32密码&mobile=号码&content=内容
状态:
	100 发送成功
	101 验证失败
	102 短信不足
	103 操作失败
	104 非法字符
	105 内容过多
	106 号码过多
	107 频率过快
	108 号码内容空
	109 账号冻结
	110 禁止频繁单条发送
	111 系统暂定发送
	112 号码不正确
	120 系统升级
*/
public class SMSUtils {


	//发送短信，uid，pwd，参数值请向企信通申请， tel：发送的手机号， content:发送的内容
	public static String send(String uid, String pwd, String tel, String content) throws IOException {
		try{
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://api.cnsms.cn/?");

			// 向StringBuffer追加用户名
			sb.append("ac=send&uid="+uid);//在此申请企信通uid，并进行配置用户名

			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			sb.append("&encode=utf8");

			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			sb.append("&pwd="+Digests.string2MD5(pwd));//在此申请企信通uid，并进行配置密码

			// 向StringBuffer追加手机号码
			sb.append("&mobile="+tel);
			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&content="+URLEncoder.encode(content,"utf8"));

			// 创建url对象
			URL url = new URL(sb.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// 返回发送结果
			String inputline = in.readLine();
			return inputline;

		}catch (Exception e){
			return "";
		}
	}
	public static String sendPass(String tel, String password) throws IOException {
		//发送内容
		String content = "您的新密码是："+password+"，请登录系统，重新设置密码。";

		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://api.cnsms.cn/?");

		// 向StringBuffer追加用户名
		sb.append("ac=send&uid=");//设置用户名

		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&encode=utf8");

		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&pwd=");//设置密码

		// 向StringBuffer追加手机号码
		sb.append("&mobile="+tel);
		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content="+URLEncoder.encode(content,"utf8"));

		// 创建url对象
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		// 返回发送结果
		String inputline = in.readLine();
		return inputline;

	}


}