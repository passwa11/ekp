<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.*,com.landray.kmss.util.*,java.io.*,org.apache.commons.io.IOUtils,
	com.landray.kmss.sys.modeling.log.ModelingLogTool
	" %>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css" />
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/common.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
<script src="${LUI_ContextPath}/resource/js/jquery.js"></script>
<title>
	低代码平台后台运行日志
</title>
<template:include ref="default.simple" spa="true" rwd="true">
	<template:replace name="body">
		<script type="text/javascript">
		Com_IncludeFile("dialog.js");
		seajs.use(['theme!list', 'theme!portal']);	
		</script>
		<div style="overflow: scroll;height: 100%;padding-left:10px;box-sizing: border-box;">
		<%
		String logpath = request.getParameter("logpath");
		if(StringUtil.isNull(logpath)){
			List<Map<String,String>> list = ModelingLogTool.getAllFileName();
			if(list==null){
				out.print("没有生成建模日志，或建模日志存放路径有可能没有在admin.do中进行配置！");
			}
		%>
		<table style="margin-top:10px">
			<tr>
				<td width="400px;" valign="top">
					<table width="100%" border="0" style="border-collapse: collapse;" class="tb_normal">
						<tr>
							<td>
							显示最新20条日志文件，
							<br>只取最新日志<input type='text' name="num" size='10'>行数,默认全部日志
							</td>
						</tr>
					</table>
					<table width="100%" border="0" style="border-collapse: collapse;" class="tb_normal">
						<tr class="tr_normal_title">
						<td  style="padding: 10px 10px;">日志文件</td>
						<td align="center"  style="padding: 10px 10px;">文件大小</td>
						<td align="center"  style="padding: 10px 10px;"></td>
						</tr>
						<%
						for(int i=0;i<list.size();i++){
							if(i==20){
								break;
							}						
							Map<String,String> map =  list.get(i);
							String str = "<a href=\"javascript:doOpen('"+map.get("apath")+"','"+map.get("name")+"');\">"+map.get("name")+"</a>";
							String show = "<a href=\"javascript:doOpen('"+map.get("apath")+"','"+map.get("name")+"');\">显示</a>";
						%>
						<tr>
							<td><%=str%></td>
							<td><%=map.get("size")%></td>
							<td align="center"><%=show%></td>
						</tr>
					<%
						}
					%>
					</table>
				</td>
				<td>
					<table id="showLog" width="800px" border="0" style="border-collapse: collapse;" class="tb_normal">
						<tr class="tr_normal_title">
							<td>以下显示日志内容</td>
						</tr>
						<tr>
							<td>
								<iframe name="contentFrame" id="contentFrame" src="" style="border: 1px solid rgb(221, 221, 221); width: 100%;  height:800px;"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%}%>
		</div>

	</template:replace>
</template:include>


<script>
function doOpen(path,fn){
	var num = document.getElementsByName("num")[0].value;
	//window.open("log.jsp?logpath="+path+"&num="+num);
	document.getElementById("contentFrame").src="logshow.jsp?logpath="+path+"&num="+num;
}

$(document).ready(function(){
		var height=$(document).height();
		var width=$(document).width();
		$("#showLog").height(height-65);
		$("#showLog").width(width-400);
		$("#contentFrame").height(height-65);
		$("#contentFrame").width(width-400);
	});  
			
	</script>

