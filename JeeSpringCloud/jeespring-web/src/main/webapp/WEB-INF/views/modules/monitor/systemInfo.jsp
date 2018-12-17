<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<!-- 引入bootstrap插件 -->
	<!-- bootswatch主题效果不怎么好，停止切换，使用官网的默认版本，如果你想切换主题请访问http://bootswatch.com/下载最新版版主题，by刘高峰 -->
	<!--  <link href="${ctxStatic}/bootstrap/3.3.4/css_${not empty cookie.theme.value ? cookie.theme.value : 'default'}/bootstrap.min.css" type="text/css" rel="stylesheet" />-->
	<link href="${ctxStatic}/bootstrap/3.3.4/css_default/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/bootstrap/3.3.4/js/bootstrap.min.js"  type="text/javascript"></script>
	<link href="${ctxStatic}/awesome/4.4/css/font-awesome.min.css" rel="stylesheet" />
	<link href="${ctxStatic}/common/css/style.css?v=3.2.0" type="text/css" rel="stylesheet" />
</head>
<body>
<table class="table table-hover">
	<tbody>
		<tr>
			<td class="left">ip地址</td>
			<td id="hostIp" class="left">${systemInfo.hostIp}</td>
		</tr>
		<tr>
			<td class="left">主机名</td>

			<td class="left" id="hostName">${systemInfo.hostName}</td>
		</tr>
		<tr>
			<td class="left">操作系统的名称</td>

			<td class="left" id="osName">${systemInfo.osName}</td>
		</tr>
		<tr>
			<td class="left">操作系统的构架</td>

			<td class="left" id="arch">${systemInfo.arch}</td>
		</tr>
		<tr>
			<td class="left">操作系统的版本</td>

			<td class="left" id="osVersion">${systemInfo.osVersion}</td>
		</tr>
		<tr>
			<td class="left">处理器个数</td>

			<td class="left" id="processors">${systemInfo.processors}</td>
		</tr>
		<tr>
			<td class="left">Java的运行环境版本</td>

			<td class="left" id="javaVersion">${systemInfo.javaVersion}</td>
		</tr>
		<tr>
			<td class="left">Java供应商的URL</td>

			<td class="left" id="javaUrl">${systemInfo.javaUrl}</td>
		</tr>
		<tr>
			<td class="left">Java的安装路径</td>

			<td class="left" id="javaHome">${systemInfo.javaHome}</td>
		</tr>
		<tr>
			<td class="left">临时文件路径</td>

			<td class="left" id="tmpdir">${systemInfo.tmpdir}</td>
		</tr>
	</tbody>
</table>
</body>
</html>