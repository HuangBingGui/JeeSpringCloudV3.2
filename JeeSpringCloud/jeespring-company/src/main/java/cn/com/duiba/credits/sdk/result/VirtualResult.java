package cn.com.duiba.credits.sdk.result;

public class VirtualResult {

	private String status; //充值是否成功的状态
	private String errorMessage="";//失败信息
	private String supplierBizId="";//虚拟商品充值流水号
	private Long credits=-1L;
	
	public VirtualResult(String status){
		this.status=status;
	}
	
	
	public String toString(){
		if(status=="success"){
			return "{'status':'success','credits':'"+credits+"','supplierBizId':'"+supplierBizId+"'}";
		}else if(status=="process"){
			return "{'status':'success','credits':'"+credits+"','supplierBizId':'"+supplierBizId+"'}";
		}else 
			return "{'status':'fail','errorMessage':'"+errorMessage+"','supplierBizId':'"+supplierBizId+"'}";
			
	}   


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getSupplierBizId() {
		return supplierBizId;
	}


	public void setSupplierBizId(String supplierBizId) {
		this.supplierBizId = supplierBizId;
	}


	public Long getCredits() {
		return credits;
	}


	public void setCredits(Long credits) {
		this.credits = credits;
	}

}
