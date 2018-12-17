package com.jeespring.modules.iim.entity;

import java.util.ArrayList;
import java.util.List;

public class LayJson {
	
	private int status;
	private String msg;
	private List<Group> data = new ArrayList();
	public void setData(List<Group> data) {
		this.data = data;
	}
	public List<Group> getData() {
		return data;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}

}
