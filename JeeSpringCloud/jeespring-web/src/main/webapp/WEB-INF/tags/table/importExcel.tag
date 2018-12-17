<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入controller的url --%>
<button id="btnImport" class="btn btn-default btn-sm" data-toggle="tooltip" data-placement="left" title="导入"><i class="fa fa-folder-open-o"></i> 导入</button>
<div id="importBox" class="hide">
		<form id="importForm" action="${url}" method="post" enctype="multipart/form-data"
			 style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！<br/>　　
		</form>
</div>
<script type="text/javascript">
$(document).ready(function() {
	$("#btnImport").click(function(){
		layer.open({
		    type: 1,
		    area: [500, 300],
		    title:"导入数据",
		    content:$("#importBox").html() ,
		    btn: ['下载模板','确定', '关闭'],
			    btn1: function(index, layero){
				  window.location.href='${url}/template';
			  },
		    btn2: function(index, layero){
			    $("#importBox").remove();
    	       	$("#importForm").submit();
				//top.layer.close(index);
			  },

			  btn3: function(index){
				  //top.layer.close(index);
    	       }
		});
	});
    
});

</script>