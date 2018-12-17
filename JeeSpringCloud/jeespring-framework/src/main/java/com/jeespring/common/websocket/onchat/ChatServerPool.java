package com.jeespring.common.websocket.onchat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.java_websocket.WebSocket;

public class ChatServerPool {

	private static final Map<WebSocket,String> userconnections = new HashMap<WebSocket,String>();
	
	/**
	 * 获取用户名
	 * @param session
	 */
	public static String getUserByKey(WebSocket conn){
		return userconnections.get(conn);
	}
	
	/**
	 * 获取WebSocket
	 * @param user
	 */
	public static WebSocket getWebSocketByUser(String user){
		Set<WebSocket> keySet = userconnections.keySet();
		synchronized (keySet) {
			for (WebSocket conn : keySet) {
				String cuser = userconnections.get(conn);
				if(cuser.equals(user)){
					return conn;
				}
			}
		}
		return null;
	}
	
	/**
	 * 向连接池中添加连接
	 * @param inbound
	 */
	public static void addUser(String user, WebSocket conn){
		userconnections.put(conn,user);	//添加连接
	}
	
	/**
	 * 获取所有的在线用户
	 * @return
	 */
	public static Collection<String> getOnlineUser(){
//		List<String> setUsers = new ArrayList<String>();
		Collection<String> setUsers = userconnections.values();
//		for(String u:setUser){
//			setUsers.add("<a onclick=\"toUserMsg('"+u+"');\">"+u+"</a>");
//		}
		return setUsers;
	}
	
	/**
	 * 移除连接池中的连接
	 * @param inbound
	 */
	public static boolean removeUser(WebSocket conn){
		if(userconnections.containsKey(conn)){
			userconnections.remove(conn);	//移除连接
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 向特定的用户发送数据
	 * @param user
	 * @param message
	 */
	public static void sendMessageToUser(WebSocket conn,String message){
		if(null != conn && null != userconnections.get(conn)){
			conn.send(message);
		}
	}
	
	/**
	 * 向所有的用户发送消息
	 * @param message
	 */
	public static void sendMessage(String message){
		Set<WebSocket> keySet = userconnections.keySet();
		synchronized (keySet) {
			for (WebSocket conn : keySet) {
				String user = userconnections.get(conn);
				if(user != null){
					conn.send(message);
				}
			}
		}
	}
}
