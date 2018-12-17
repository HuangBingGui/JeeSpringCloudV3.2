/**
 * * Copyright &copy; 2015-2020 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpringCloud</a> All rights reserved..
 */
package com.company.project.modules.ylttrip.entity;

import org.hibernate.validator.constraints.Length;
import com.jeespring.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 订单Entity
 * @author JeeSpring
 * @version 2018-12-13
 */
public class TfTicket extends AbstractBaseEntity<TfTicket> {
	
	private static final long serialVersionUID = 1L;
	private String ticketNo;		// 订单编号
	private String goodsNo;		// 商品编号
	private String goodsItemId;		// 种类编号
	private String goodsItemName;		// 种类名称
	private Long goodsNum;		// 商品数量
    private Double sumGoodsNum;		// sum商品数量
	private Double price;		// 商品单价
    private Double sumPrice;		// sum商品单价
	private Double salePrice;		// 订单金额
    private Double sumSalePrice;		// sum订单金额
	private com.jeespring.modules.sys.entity.User user;		// 下单人
	private java.util.Date orderDate;		// 下单时间
	private String state;		// 订单状态
	private String stateLabel;		// 订单状态Label
	private String statePicture;		// 订单状态Picture
	private java.util.Date stateDate;		// 状态时间
	private String custName;		// 客户姓名
	private String linkPhone;		// 联系电话
	private String address;		// 收货地址
	private Long payType;		// 付款方式
	private String payTypeLabel;		// 付款方式Label
	private String payTypePicture;		// 付款方式Picture
	private String logistId;		// 物流编号
	private String logistComp;		// 物流公司
	private String checkinCode;		// 入园号
	private String reserveId;		// 票务系统订单号
	private String remark;		// 订单备注
	private java.util.Date beginOrderDate;		// 开始 下单时间
	private java.util.Date endOrderDate;		// 结束 下单时间
	private java.util.Date beginStateDate;		// 开始 状态时间
	private java.util.Date endStateDate;		// 结束 状态时间
	
	public TfTicket() {
		super();
	}

	public TfTicket(String id){
		super(id);
	}

	@Length(min=1, max=25, message="订单编号长度必须介于 1 和 25 之间")
				@ExcelField(title="订单编号", align=2, sort=1)
	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}


	@Length(min=0, max=25, message="商品编号长度必须介于 0 和 25 之间")
				@ExcelField(title="商品编号", align=2, sort=2)
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}


	@Length(min=0, max=255, message="种类编号长度必须介于 0 和 255 之间")
				@ExcelField(title="种类编号", align=2, sort=3)
	public String getGoodsItemId() {
		return goodsItemId;
	}

	public void setGoodsItemId(String goodsItemId) {
		this.goodsItemId = goodsItemId;
	}


	@Length(min=0, max=255, message="种类名称长度必须介于 0 和 255 之间")
				@ExcelField(title="种类名称", align=2, sort=4)
	public String getGoodsItemName() {
		return goodsItemName;
	}

	public void setGoodsItemName(String goodsItemName) {
		this.goodsItemName = goodsItemName;
	}


				@ExcelField(title="商品数量", align=2, sort=5)
	public Long getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Long goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Double getSumGoodsNum() {
		return sumGoodsNum;
	}

	public void setSumGoodsNum(Double goodsNum) {
		this.sumGoodsNum = goodsNum;
	}

				@ExcelField(title="商品单价", align=2, sort=6)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double price) {
		this.sumPrice = price;
	}

				@ExcelField(title="订单金额", align=2, sort=7)
	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getSumSalePrice() {
		return sumSalePrice;
	}

	public void setSumSalePrice(Double salePrice) {
		this.sumSalePrice = salePrice;
	}

				@ExcelField(title="下单人", fieldType=User.class, value="user.name", align=2, sort=8)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
				@ExcelField(title="下单时间", align=2, sort=9)
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	@Length(min=0, max=2, message="订单状态长度必须介于 0 和 2 之间")
				@ExcelField(title="订单状态", dictType="STATE", align=2, sort=10)
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	public String getStateLabel() {
		return DictUtils.getDictLabel(state,"STATE","");
	}
	public String getStatePicture() {
		return DictUtils.getDictPicture(state,"STATE","");
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
				@ExcelField(title="状态时间", align=2, sort=11)
	public Date getStateDate() {
		return stateDate;
	}

	public void setStateDate(Date stateDate) {
		this.stateDate = stateDate;
	}


	@Length(min=0, max=50, message="客户姓名长度必须介于 0 和 50 之间")
				@ExcelField(title="客户姓名", align=2, sort=12)
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}


	@Length(min=0, max=50, message="联系电话长度必须介于 0 和 50 之间")
				@ExcelField(title="联系电话", align=2, sort=13)
	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}


	@Length(min=0, max=500, message="收货地址长度必须介于 0 和 500 之间")
				@ExcelField(title="收货地址", align=2, sort=14)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


				@ExcelField(title="付款方式", dictType="PAY_TYPE", align=2, sort=15)
	public Long getPayType() {
		return payType;
	}

	public void setPayType(Long payType) {
		this.payType = payType;
	}


	public String getPayTypeLabel() {
		return DictUtils.getDictLabel(payType,"PAY_TYPE","");
	}
	public String getPayTypePicture() {
		return DictUtils.getDictPicture(payType,"PAY_TYPE","");
	}
	@Length(min=0, max=25, message="物流编号长度必须介于 0 和 25 之间")
				@ExcelField(title="物流编号", align=2, sort=16)
	public String getLogistId() {
		return logistId;
	}

	public void setLogistId(String logistId) {
		this.logistId = logistId;
	}


	@Length(min=0, max=200, message="物流公司长度必须介于 0 和 200 之间")
				@ExcelField(title="物流公司", align=2, sort=17)
	public String getLogistComp() {
		return logistComp;
	}

	public void setLogistComp(String logistComp) {
		this.logistComp = logistComp;
	}


	@Length(min=0, max=255, message="入园号长度必须介于 0 和 255 之间")
				@ExcelField(title="入园号", align=2, sort=18)
	public String getCheckinCode() {
		return checkinCode;
	}

	public void setCheckinCode(String checkinCode) {
		this.checkinCode = checkinCode;
	}


	@Length(min=0, max=30, message="票务系统订单号长度必须介于 0 和 30 之间")
				@ExcelField(title="票务系统订单号", align=2, sort=20)
	public String getReserveId() {
		return reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}


	@Length(min=0, max=500, message="订单备注长度必须介于 0 和 500 之间")
				@ExcelField(title="订单备注", align=2, sort=21)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getBeginOrderDate() {
		return beginOrderDate;
	}

	public void setBeginOrderDate(Date beginOrderDate) {
		this.beginOrderDate = beginOrderDate;
	}
	
	public Date getEndOrderDate() {
		return endOrderDate;
	}

	public void setEndOrderDate(Date endOrderDate) {
		this.endOrderDate = endOrderDate;
	}
		
	public Date getBeginStateDate() {
		return beginStateDate;
	}

	public void setBeginStateDate(Date beginStateDate) {
		this.beginStateDate = beginStateDate;
	}
	
	public Date getEndStateDate() {
		return endStateDate;
	}

	public void setEndStateDate(Date endStateDate) {
		this.endStateDate = endStateDate;
	}
		
}