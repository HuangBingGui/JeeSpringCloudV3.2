<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入url --%>
<button id="btnExport" class="btn btn-default btn-sm" data-toggle="tooltip" data-placement="left" title="导出"><i class="fa fa-file-excel-o"></i> 导出</button>
<script type="text/javascript">
$(document).ready(function() {

	$("#btnExport").click(function(){
		top.layer.confirm('确认要导出Excel吗?', {icon: 3, title:'系统提示'}, function(index){
		    //do something
		    	//导出之前备份
		    	var url =  $("#searchForm").attr("action");
		    	var pageNo =  $("#pageNo").val();
		    	var pageSize = $("#pageSize").val();
		    	//导出excel
		        $("#searchForm").attr("action","${url}");
			    $("#pageNo").val(-1);
				$("#pageSize").val(-1);
				$("#searchForm").submit();

				//导出excel之后还原
				$("#searchForm").attr("action",url);
			    $("#pageNo").val(pageNo);
				$("#pageSize").val(pageSize);
		    //top.layer.close(index);
		});
	});
    
});


</script>