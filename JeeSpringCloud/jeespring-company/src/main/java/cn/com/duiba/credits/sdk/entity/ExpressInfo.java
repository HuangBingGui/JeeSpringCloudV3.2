package cn.com.duiba.credits.sdk.entity;

/**
 * 构建开发者批量发货的发货信息
 * @author Yang-yudong
 *
 */

public class ExpressInfo {
	
	private String orderNum;//兑吧订单号
	private String expressNum;//快递单号
	private String expressType;//快递类型
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getExpressNum() {
		return expressNum;
	}
	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}
	public String getExpressType() {
		return expressType;
	}
	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}
	
	/**
	 * 构建发货信息的toString方法
	 */
	@Override
	public String toString() {
		return orderNum + "|"+ expressType + "|" + expressNum + ",";
	}
	
	
}
