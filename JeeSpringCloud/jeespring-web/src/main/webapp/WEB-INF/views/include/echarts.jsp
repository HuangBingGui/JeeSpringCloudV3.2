<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${ctxStatic}/echarts-2.2.7/build/dist/echarts.js"></script>
<script type="text/javascript">
	// 路径配置
	require.config({
		paths : {
			echarts : '${ctxStatic}/echarts-2.2.7/build/dist'
		} 
	});
</script>
<style type="text/css">
    .main000 {
        height: 360px;
        /*width: 778px !important;*/
        overflow: hidden;
        padding : 10px;
        margin-bottom: 10px;
        border: 1px solid #e3e3e3;
        -webkit-border-radius: 4px;
           -moz-border-radius: 4px;
                border-radius: 4px;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
           -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.05);
    }
</style>