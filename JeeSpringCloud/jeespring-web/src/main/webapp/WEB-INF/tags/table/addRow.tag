<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<%@ attribute name="title" type="java.lang.String" required="true"%>
<%@ attribute name="width" type="java.lang.String" required="false"%>
<%@ attribute name="height" type="java.lang.String" required="false"%>
<%@ attribute name="target" type="java.lang.String" required="false"%>
<%@ attribute name="label" type="java.lang.String" required="false"%>
<button class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="add()" title="添加"><i class="fa fa-plus"></i> ${label==null?'添加':label}</button>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入table的id和controller的url --%>
<script type="text/javascript">
	function add(){
	    window.location.href=${url};
		//openDialog("新增"+'${title}',"${url}","${width==null?'800px':width}", "${height==null?'500px':height}","${target}");
	}
</script>