<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="target" type="java.lang.String" required="false"%>
<%@ attribute name="label" type="java.lang.String" required="false"%>
<button class="btn btn-white  btn-sm" data-toggle="tooltip" data-placement="left" onclick="edit()" title="修改"><i class="fa fa-file-text-o"></i> ${label==null?'修改':label}</button>

<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入table的id和controller的url --%>
<script type="text/javascript">
$(document).ready(function() {
    $('#${id} thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
    	  $('#${id} tbody tr td input.i-checks').iCheck('check');
    	});

    $('#${id} thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
    	  $('#${id} tbody tr td input.i-checks').iCheck('uncheck');
    	});
    
});

	function edit(){

		// var url = $(this).attr('data-url');
		  var str="";
		  var ids="";
		  var size = $("#${id} tbody tr td input.i-checks:checked").size();
		  if(size == 0 ){
				top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
				return;
			  }

		  if(size > 1 ){
				top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
				return;
			  }
		    var id =  $("#${id} tbody tr td input.i-checks:checkbox:checked").attr("id");
		    openDialog("修改"+'${title}',"${url}?id="+id,"${width==null?'800px':width}", "${height==null?'500px':height}","${target}");
		}

</script>