<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.util.*"%>
<%@page import="com.landray.kmss.tic.rest.connector.model.TicRestMain,com.landray.kmss.tic.rest.connector.service.ITicRestMainService"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	ITicRestMainService ticRestMainService = (ITicRestMainService)SpringBeanUtil.getBean("ticRestMainService");
	TicRestMain tm = (TicRestMain) ticRestMainService.findByPrimaryKey(request.getParameter("fdId"));
	String inParams = tm.getFdOriParaIn();
	String inv = "";
	JSONObject jo = new JSONObject();
	if(StringUtil.isNotNull(inParams)){
		JSONArray a = JSONArray.fromObject(inParams);
		for(int i=0;i<a.size();i++){
			JSONObject p = a.getJSONObject(i);
			if("string".equals(p.getString("type"))){
				jo.accumulate(p.getString("name"),"");
			}else if("object".equals(p.getString("type"))){
				jo.accumulate(p.getString("name"),new JSONObject());
			}else if("array".equals(p.getString("type"))){
				jo.accumulate(p.getString("name"),new JSONArray());
			}else if("int".equals(p.getString("type")) || "long".equals(p.getString("type")|| "double".equals(p.getString("type"))){
				jo.accumulate(p.getString("name"),0);
			}else if("boolean".equals(p.getString("type"))){
				jo.accumulate(p.getString("name"),true);
			}
		}
	}
%>
<template:include ref="default.dialog">
<template:replace name="head">
<script type="text/javascript">
Com_IncludeFile("jquery.js|data.js|json2.js");
Com_IncludeFile("doclist.js|dialog.js", null, "js");
Com_IncludeFile("json2.js");
</script>
</template:replace>

<template:replace name="content">
<table class="tb_normal" width=98%>

	<tr>
	<td class="td_normal_title" width="25%">${lfn:message('tic-core-common:ticCoreFuncBase.fdParaIn')}</td>
	<td>
		<textarea name="fdInputParams" style="width:95%;height:140px;"><%=jo.toString(2)%></textarea>
	</td>
	</tr>
	<tr>
	<td class="td_normal_title" width="25%">${lfn:message('tic-core-common:ticCoreCommon.outParamValue')}</td>
	<td>
		<textarea name="fdOutParams" style="width:95%;height:300px;"></textarea>
	</td>
	</tr>

	<center>
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;padding-bottom:5px;">
			<ui:button style="width:120px" onclick="getRestData('${param.fdId}');" text="${lfn:message('tic-core-common:ticCoreCommon.debugGetData')}" />
			<ui:button style="width:80px" onclick="Com_CloseWindow();" text="${lfn:message('button.close') }" />
		</div>
</center>
</table>
<script>
	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$) {
		
		window.getRestData = function(id) {
				var 	fdInputParams=document.getElementsByName("fdInputParams")[0].value;
				window.file_load = dialog.loading();
				var dataUrl = '${LUI_ContextPath}/tic/rest/connector/tic_rest_main/ticRestMain.do?method=getRestData';
				$.ajax({
					url: dataUrl,
					type: 'POST',
					data:{fdId:id,fdInputParams:fdInputParams},
					dataType: 'json',
					error: function(data){
						if(window.file_load!=null){
							window.file_load.hide(); 
						}
						console.log(data)
						dialog.result(data.responseJSON);
					},
					success: function(data){
						console.log(data)
						if(data["errcode"]=="0"){
							document.getElementsByName("fdOutParams")[0].value=data["result"];
							window.file_load.hide(); 
						}else{
							dialog.alert("调试获取数据出错："+data["errmsg"], '', '',
								'', false,window);
							window.file_load.hide(); 
						}
					}
			   });
			}
	});

</script>
</template:replace>
</template:include>
