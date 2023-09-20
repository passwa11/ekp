<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.config.design.SysConfigs"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script>
var Search_ModelName = "${param.type}";
var Search_CustomPara = "${param.customPara}"; // 自定义参数
function Search_Show(){
	Search_Div.style.top = 45+"px";
	Search_Div.style.display = "";
}

function Search_Hide(){
	Search_Div.style.display = "none";
}

function Search_Simple(){
	var keyField = document.getElementsByName("Search_Keyword")[0];
	if(keyField.value==""){
		alert('<bean:message key="error.search.keywords.required"/>');
		keyField.focus();
		return;
	}
	Search_Hide();
	var url = "<c:url value='/third/ding/oms_relation_model/omsRelationModel.do?method=list'/>";
	var seq = parseInt(Com_GetUrlParameter(url, "s_seq"));
	seq = isNaN(seq)?1:seq+1;
	url = Com_SetUrlParameter(url, "s_seq", seq);
 	url = Com_SetUrlParameter(url, "type", Search_ModelName);
	url = Com_SetUrlParameter(url, "search", keyField.value);
	Com_OpenWindow(url,"_self");
}
function Search_Reset(){
	Search_Hide();
	var url = "<c:url value='/third/ding/oms_relation_model/omsRelationModel.do?method=list'/>";
	var seq = parseInt(Com_GetUrlParameter(url, "s_seq"));
	seq = isNaN(seq)?1:seq+1;
	url = Com_SetUrlParameter(url, "s_seq", seq);
 	url = Com_SetUrlParameter(url, "type", Search_ModelName);
	Com_OpenWindow(url,"_self");
}
</script>
<div id="Search_Div" style="display:none; position:absolute; top:5px; right:20px;">
	<table class="tb_search">
		<tr>
			<td nowrap>
				&nbsp;<bean:message key="message.quickSearch"/>:<bean:message key="message.keyword"/>
			</td>
			<td nowrap>
				<input size="32" name="Search_Keyword" class="input_search" onkeydown="if (event.keyCode == 13 && this.value !='') Search_Simple();" >&nbsp;&nbsp;
				<input type="button" class="btn_search" onclick="Search_Simple();" value=" <bean:message key="button.search"/> ">&nbsp;&nbsp;
				<input type="button" class="btn_search" onclick="Search_Reset();" value=" <bean:message key="button.reset"/> ">&nbsp;
			</td>
			<td valign="top">
				<a href="#">
					<img alt="<bean:message key="button.close"/>" border="0" src="${KMSS_Parameter_StylePath}icons/x.gif" width="5" height="5" hspace="2" vspace="2" onclick="Search_Hide();">
				</a>
				&nbsp;
			</td>
		</tr>
	</table>
</div>