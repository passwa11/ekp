<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/lbpmservice/taglib/taglib.tld" prefix="xlang"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%
String textWidth="85%";
if(MultiLangTextGroupTag.isLangSuportEnabled()){
	textWidth="70%";
}
request.setAttribute("textWidth",textWidth);
pageContext.setAttribute("isLangSuportEnabled", MultiLangTextGroupTag.isLangSuportEnabled());
%>

<tr LKS_LabelName="<bean:message key="table.lbpmExtAuditPoint" bundle="sys-lbpmext-auditpoint" />">
	<td>
		<input type="hidden" id="ext_lbpmExtAuditPointCfg" name="ext_lbpmExtAuditPointCfg" value="">
		<table class="tb_normal" id="TABLE_auditpoint" width="100%">
			<tr>
				<td  width="10%" class="td_normal_title">
					<bean:message key="page.serial"/>
				</td>
				<td  width="54%"  class="td_normal_title">
					<bean:message bundle="sys-lbpmext-auditpoint" key="lbpmExtAuditPoint.fdTitle"/>
				</td>
				<td  width="6%"  class="td_normal_title">
					<bean:message bundle="sys-lbpmext-auditpoint" key="lbpmExtAuditPoint.fdIsImportant"/>
				</td>
				<td  width="10%"  id="_addOprBtn" align="center" class="td_normal_title">
					<img src="${KMSS_Parameter_StylePath}icons/add.gif" style="cursor:pointer" onclick="DocList_AddRow('TABLE_auditpoint');"  title="<kmss:message key="FlowChartObject.Lang.Event.eventAdd" bundle="sys-lbpm-engine" />">
				</td>
			</tr>
			<!--基准行-->
			<tr KMSS_IsReferRow="1" style="display:none">
				<td KMSS_IsRowIndex="1">
					<c:out value="!{index}"></c:out>
				</td>
				<td>
					<%-- <input name="fdParamJsonItem[!{index}].fdTitle" class="inputsgl" value="" type="text" validate="required" style="width:<%=textWidth%>;" /><span class="txtstrong">*</span>
					<xlang:lbpmlang property="fdParamJsonItem[!{index}].fdTitle" style="width:70%;" langs=""/> --%>
					<c:if test="${!isLangSuportEnabled }">
						<input name="fdParamJsonItem[!{index}].fdTitle" class="inputsgl" value="" type="text" validate="required" style="width:<%=textWidth%>;" /><span class="txtstrong">*</span>
					</c:if>
					<c:if test="${isLangSuportEnabled }">
						<xlang:lbpmlangNew property="fdParamJsonItem[!{index}].fdTitle" validators="required" style="width:${requestScope.textWidth };" className="inputsgl" langs=""/>
					</c:if>
				</td>
				<td align="center">
					<input type="checkbox" value="true" name="fdParamJsonItem[!{index}].fdIsImportant">
				</td>
				<td>
					<img src="${KMSS_Parameter_StylePath}icons/up.gif" alt="del" onclick="DocList_MoveRow(-1)" style="cursor:pointer">
					<img src="${KMSS_Parameter_StylePath}icons/down.gif" alt="del" onclick="DocList_MoveRow(1)" style="cursor:pointer">
					<img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del" onclick="DocList_DeleteRow()" style="cursor:pointer">
				</td>
			</tr>
		</table>
	</td>
</tr>
<script>
/**
titleLangs =[
			{"lang":"zh-CN","value":"要点1"},{"lang":"en-US","value":"YADIAN1"}
		];
**/

	Com_IncludeFile("json2.js");

	AttributeObject.Init.EditModeFuns.push(function() {
		var paramJsonStr =$("#ext_lbpmExtAuditPointCfg").val();
		if(paramJsonStr!=""){
			paramJson = $.parseJSON(paramJsonStr);
			for(var i=0;i<paramJson.length;i++){
				var html=[];
				html.push(null);
				var defValue=paramJson[i].fdTitle;
				if(isLangSuportEnabled && typeof paramJson[i].titleLangs !="undefined"){
					defValue = _getLangLabelByJson(paramJson[i].fdTitle,paramJson[i].titleLangs,langJson["official"]["value"]);
				}
				//html.push('<input name="fdParamJsonItem['+i+'].fdTitle" class="inputsgl" value="'+defValue+'" type="text" validate="required" style="width:<%=textWidth%>;"><span class="txtstrong">*</span>'+getAuditpointTitleLang(i,paramJson[i]));
				//html.push("")
				//html.push('<input type="checkbox" '+(paramJson[i].fdIsImportant=="true"?"checked":"")+' value="true" name="fdParamJsonItem[!{index}].fdIsImportant">');
				//DocList_AddRow('TABLE_auditpoint',html);
				DocList_AddRow('TABLE_auditpoint');
				//初始化数据
				if(isLangSuportEnabled){
					initAuditpoint(i,paramJson[i],defValue);
				}else{
					$("input[name='fdParamJsonItem["+i+"].fdTitle']").val(defValue);
					if(paramJson[i].fdIsImportant=="true"){
						$("#TABLE_auditpoint").find("input[name='fdParamJsonItem["+i+"].fdIsImportant']").prop("checked",true);
					}
				}
			}
		}
	});
	
	function initAuditpoint(index,param,defValue){
		var langs = param.titleLangs;
		if(typeof langs =="undefined" || langs==null){
			langs =[];
			for(var i=0;i<langJson["support"].length;i++){
				var lang={"lang":langJson["support"][i]["value"],value:""};
				langs.push(lang);
			}
		}
		$(".multiLang").children().find("input[name='fdParamJsonItem["+index+"].fdTitle']").val(defValue);
		for(var i=0; i<langs.length; i++){
			var lang = langs[i].lang;
			var value = langs[i].value;
			$(".multiLang").children().find("input[name='fdParamJsonItem["+index+"].fdTitle_"+lang+"']").val(value);
		}
		if(param.fdIsImportant=="true"){
			$("#TABLE_auditpoint").find("input[name='fdParamJsonItem["+index+"].fdIsImportant']").prop("checked",true);
		}
	}
	
	function getAuditpointTitleLang(index,param){
		if(!isLangSuportEnabled){
			return "";
		}
		var langs = param.titleLangs;
		if(typeof langs =="undefined" || langs==null){
			langs =[];
			for(var i=0;i<langJson["support"].length;i++){
				var lang={"lang":langJson["support"][i]["value"],value:""};
				langs.push(lang);
			}
		}
		var html=[];
		html.push('<span id="fdParamJsonItem['+index+'].fdTitle_span" name="fdParamJsonItem['+index+'].fdTitle_span">');
		html.push(langJson["official"]["text"]+'(<kmss:message key="lbpm.lang.multi.tip.name" bundle="sys-lbpmservice" />)');
		for(var i=0;i<langJson["support"].length;i++){
			var value=_getLangLabelByJson("",langs,langJson["support"][i]["value"]);
			if(langJson["support"][i]["value"]==langJson["official"]["value"]){
				html.push('<input name="fdParamJsonItem['+index+'].fdTitle_'+langJson["support"][i]["value"]
						+'" id="fdParamJsonItem['+index+'].fdTitle_'+langJson["support"][i]["value"]+'" value="'+value+'" type="hidden" />');
			}else{
				html.push('<br>');
				html.push('<input name="fdParamJsonItem['+index+'].fdTitle_'+langJson["support"][i]["value"]
						+'" id="fdParamJsonItem['+index+'].fdTitle_'+langJson["support"][i]["value"]+'" class="inputsgl" value="'+value+'" type="text"  style="width:70%;" />');
				html.push(langJson["support"][i]["text"]);
			}
		}
		html.push('</span>');
		return html.join('');
	}

	AttributeObject.Init.ViewModeFuns.push(function() {
		var paramJsonStr =$("#ext_lbpmExtAuditPointCfg").val();
		$("#_addOprBtn").html("");
		if(paramJsonStr!=""){
			paramJson = $.parseJSON(paramJsonStr);
			for(var i=0;i<paramJson.length;i++){
				var html=[];
				html.push(null);
				var defValue=paramJson[i].fdTitle;
				if(isLangSuportEnabled && typeof paramJson[i].titleLangs !="undefined"){
					defValue = _getLangLabelByJson(paramJson[i].fdTitle,paramJson[i].titleLangs,userLang);
				}
				/*#156220 审批要点存在xss问题*/
				defValue=defValue.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
				html.push(defValue);
				html.push('<input type="checkbox" disabled '+(paramJson[i].fdIsImportant=="true"?"checked":"")+'>');
				html.push("");
				DocList_AddRow('TABLE_auditpoint',html);
			}
		}
	});

	AttributeObject.SubmitFuns.unshift(function(data){
		var len = document.getElementById("TABLE_auditpoint").rows.length-1;
		var paramJson=[];
		for(var i=0;i<len;i++){
			var fdTitle=$("input[name='fdParamJsonItem["+i+"].fdTitle']").val();
			var elName="fdParamJsonItem["+i+"].fdTitle";
			var langs = _getLangByElName(elName,0);
			var fdIsImportant=$("input[name='fdParamJsonItem["+i+"].fdIsImportant']")[0];
			if(fdTitle!=""){
				fdTitle = fdTitle.replace(/["\""]/g,"").replace(/["\'"]/g,"").replace(/[" "]/g,"");
				var param = {
					fdTitle:fdTitle,fdIsImportant:(fdIsImportant.checked?"true":"false"),titleLangs:langs
				};
				paramJson.push(param);
			}
		}
		$("input[name='ext_lbpmExtAuditPointCfg']").val(JSON.stringify(paramJson));
	});

	AttributeObject.CheckDataFuns.push(function(data) {
		var len = document.getElementById("TABLE_auditpoint").rows.length-1;
		for(var i=0;i<len;i++){
			var fdTitle=$("input[name='fdParamJsonItem["+i+"].fdTitle']").val();
			if(fdTitle==""){
				alert('<kmss:message key="lbpmExtAuditPoint.fdTitle.required" bundle="sys-lbpmext-auditpoint" />'.replace("{0}", ""+(i+1)));
				return false;
			}
			if(_checkLength(fdTitle)>=200){
				alert('<kmss:message key="lbpmExtAuditPoint.fdTitle.length" bundle="sys-lbpmext-auditpoint" />');
				return false;
			}
		}
		return true;
	});

	function _checkLength(str){
		return str.replace(/[\u0391-\uFFE5]/g,"aa").length;
	 }

</script>
<script type="text/javascript">
<!--
DocList_Info.push("TABLE_auditpoint");
//-->
</script>