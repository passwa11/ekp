<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/kmss-html.tld" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信对接模块系统参数配置</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	color: #000;
	font-family: arial;
}
fieldset {
	border: none;
	-webkit-margin-start: 2px;
	-webkit-margin-end: 2px;
	-webkit-padding-before: 0.35em;
	-webkit-padding-start: 0.75em;
	-webkit-padding-end: 0.75em;
	-webkit-padding-after: 0.625em;
}

input[type="submit"] {
	padding: 0 15px;
	border: 0;
	background: #f40;
	text-align: center;
	text-decoration: none;
	color: #fff;
	-webkit-border-radius: 2px;
	text-shadow: 0 -1px 1px #ca3511;
	min-width: 100%;
	height: 40px;
	font-size: 24px;
	text-shadow: 0 -1px 0 #441307;
	background: -webkit-gradient(linear, left top, left bottom, from(#0095cc), to(#00678e));
}

span {
	line-height: 3px;
}

.infos {
	color: red;
}
</style>
<script type="text/javascript">
	function submint(){
		Com_Submit(document.wechatSysConfigForm,'save');
	}
</script>
<body>
<html:form action="/third/wechat/wechatMainConfig.do?method=save">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>" onclick="submint();">
</div>
<p class="txttitle">微信对接模块系统参数配置表</p>
<center>
<table class="tb_normal">
        <tr>
		    <td class="td_normal_title" width=15%>License：</td>
				<td >
					<xform:text property="confiLicense" style="width:100%"></xform:text>
				</td>
				<td class="td_normal_title" width=15%>企业号文件下载地址：</td>
				<td >
					<xform:text property="qydownloadUrl" style="width:100%"></xform:text>
				</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>微云系统地址：</td>
			<td width=25%>
				<xform:text property="weiyuSystemUrl" style="width:100%"></xform:text>
			</td>
			<td class="td_normal_title" width=15%>企业号系统地址：</td>
			<td>
			    <xform:text property="qyhSystemUrl" style="width:100%"></xform:text>
			</td>
		</tr>
		
		<tr>
			<td class="td_normal_title" width=15%>微云待办是否启用</td>
			<td width=25%> <xform:radio property="weiyuConfStatus" showStatus="edit" >
					<xform:simpleDataSource value="1">启用</xform:simpleDataSource>
					<xform:simpleDataSource value="0">禁用</xform:simpleDataSource>
				  </xform:radio>
			</td>
			<td class="td_normal_title" width=15% >企业待办是否启用</td>
			<td><xform:radio property="qyhConfStatus" showStatus="edit" >
				<xform:simpleDataSource value="1">启用</xform:simpleDataSource>
					<xform:simpleDataSource value="0">禁用</xform:simpleDataSource>
				</xform:radio>
			</td>
		</tr>
		
		<tr>
			<td class="td_normal_title" width=15%>微云微信通知地址：</td>
			<td >
				<xform:text property="notifyUrl" style="width:100%"></xform:text>
			</td>
			<td class="td_normal_title" width=15%>企业微信通知地址：</td>
			<td >
				<xform:text property="qyNotifyUrl" style="width:100%"></xform:text>
			</td>
		</tr>
		<!-- 
		<tr>
			<td colspan="4" align="center"><input type="submit"  value="保存"></td>
		</tr> -->
</table>
</center>
</html:form>
</body>
</html>