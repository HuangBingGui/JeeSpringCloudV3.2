<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>
<i id="${id}Icon" class="icon-${not empty value?value:' hide'}"></i>&nbsp;<span id="${id}IconLabel">${not empty value?value:'无'}</span>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}"/><a id="${id}Button" href="javascript:" class="btn btn-primary">选择</a>&nbsp;&nbsp;
<input id="${id}clear" class="btn btn-default" type="button" value="清除" onclick="clear()"/>
<script type="text/javascript">
	$("#${id}Button").click(function(){
		top.layer.open({
			type: 2, 
			title:"选择图标",
			area: ['700px',  $(top.document).height()-180+"px"],
		    content: '${ctx}/tag/iconselect?value="+$("#${id}").val()',
		    btn: ['确定', '关闭'],
		    yes: function(index, layero){ //或者使用btn1
		    	var icon = layero.find("iframe")[0].contentWindow.$("#icon").val();
            	$("#${id}Icon").attr("class", "fa "+icon);
                $("#${id}IconLabel").text(icon);
                $("#${id}").val(icon);
                top.layer.close(index);
		    },cancel: function(index){ //或者使用btn2
		    	
		    }
		});
	});
	$("#${id}clear").click(function(){
		 $("#${id}Icon").attr("class", "icon- hide");
         $("#${id}IconLabel").text("无");
         $("#${id}").val("");

	});
</script>