/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeespring.org/">JeeSpring</a> All rights reserved.
 */
package com.jeespring.modules.echarts.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;

/**
 * 城市气温Entity
 * @author lgf
 * @version 2016-06-02
 */
public class ChinaWeatherDataBean extends AbstractBaseEntity<ChinaWeatherDataBean> {
	
	private static final long serialVersionUID = 1L;
	private Date datestr;		// 日期
	private Double beijingMaxTemp;		// 北京最高气温
	private Double beijingMinTemp;		// 北京最低气温
	private Double changchunMaxTemp;		// 长春最高气温
	private Double changchunMinTemp;		// 长春最低气温
	private Double shenyangMaxTemp;		// 沈阳最高气温
	private Double shenyangMinTemp;		// 沈阳最低气温
	private Double haerbinMaxTemp;		// 哈尔滨最高气温
	private Double haerbinMinTemp;		// 哈尔滨最低气温
	
	public ChinaWeatherDataBean() {
		super();
	}

	public ChinaWeatherDataBean(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="日期", align=2, sort=7)
	public Date getDatestr() {
		return datestr;
	}

	public void setDatestr(Date datestr) {
		this.datestr = datestr;
	}
	
	@ExcelField(title="北京最高气温", align=2, sort=8)
	public Double getBeijingMaxTemp() {
		return beijingMaxTemp;
	}

	public void setBeijingMaxTemp(Double beijingMaxTemp) {
		this.beijingMaxTemp = beijingMaxTemp;
	}
	
	@ExcelField(title="北京最低气温", align=2, sort=9)
	public Double getBeijingMinTemp() {
		return beijingMinTemp;
	}

	public void setBeijingMinTemp(Double beijingMinTemp) {
		this.beijingMinTemp = beijingMinTemp;
	}
	
	@ExcelField(title="长春最高气温", align=2, sort=10)
	public Double getChangchunMaxTemp() {
		return changchunMaxTemp;
	}

	public void setChangchunMaxTemp(Double changchunMaxTemp) {
		this.changchunMaxTemp = changchunMaxTemp;
	}
	
	@ExcelField(title="长春最低气温", align=2, sort=11)
	public Double getChangchunMinTemp() {
		return changchunMinTemp;
	}

	public void setChangchunMinTemp(Double changchunMinTemp) {
		this.changchunMinTemp = changchunMinTemp;
	}
	
	@ExcelField(title="沈阳最高气温", align=2, sort=12)
	public Double getShenyangMaxTemp() {
		return shenyangMaxTemp;
	}

	public void setShenyangMaxTemp(Double shenyangMaxTemp) {
		this.shenyangMaxTemp = shenyangMaxTemp;
	}
	
	@ExcelField(title="沈阳最低气温", align=2, sort=13)
	public Double getShenyangMinTemp() {
		return shenyangMinTemp;
	}

	public void setShenyangMinTemp(Double shenyangMinTemp) {
		this.shenyangMinTemp = shenyangMinTemp;
	}
	
	@ExcelField(title="哈尔滨最高气温", align=2, sort=14)
	public Double getHaerbinMaxTemp() {
		return haerbinMaxTemp;
	}

	public void setHaerbinMaxTemp(Double haerbinMaxTemp) {
		this.haerbinMaxTemp = haerbinMaxTemp;
	}
	
	@ExcelField(title="哈尔滨最低气温", align=2, sort=15)
	public Double getHaerbinMinTemp() {
		return haerbinMinTemp;
	}

	public void setHaerbinMinTemp(Double haerbinMinTemp) {
		this.haerbinMinTemp = haerbinMinTemp;
	}
	
}