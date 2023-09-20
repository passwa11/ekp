<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="xFormTemplateForm" value="${sysFormFragmentSetHistoryForm}" />
<!-- 片段集历史版本view页面 -->
<script>
Com_IncludeFile("dialog.js");
Com_Parameter.IsAutoTransferPara = true;
function confirmDelete(msg) {
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}


 seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic'], function($, strutil, dialog , topic) { 
	var cateId = "${JsParam.categoryId}";
 	//同步
		window.synchronous = function(id){
			var values = [];
			var fdId = document.getElementsByName("fdId")[0].value;
			if(id) {
				values.push(id);
 		} else {
			$("input[name='List_Selected']:checked",document.getElementById("templateRefFragmentSet").contentWindow.document).each(function() {
				values.push($(this).val());
			});
 		}
		if(values.length==0){
			dialog.alert('<bean:message key="page.noSelect"/>');
			return;
		}
		var iframe = document.getElementById("templateRefFragmentSet").contentWindow;
		//选择同步版本
		var url  = '<c:url value="/sys/xform/fragmentSet/xFormFragmentSet.do?method=synchronous"/>';
		action = function (rtnVal){
			$.ajax({
				url : url,
				type : 'POST',
				data : $.param({"List_Selected" : values,"fdId":rtnVal.data[0].id,"isHistory":true,fdRefId:fdId,isLatest:false}, true),
				dataType : 'json',
				error : function(data) {
					if(window.del_load != null) {
						window.del_load.hide(); 
					}
					/* dialog.result(data.responseJSON); */
					iframe.location.href=iframe.location.href;
				},
				success: function(data) {
					if(window.del_load != null){
						window.del_load.hide(); 
						 topic.publish("list.refresh"); 
					}
					iframe.location.href=iframe.location.href;
					dialog.result(data);
				}
		   });
		}
		var kmssDialog = new KMSSDialog(false);
		kmssDialog.BindingField("megerFragmentSetId", "megerFragmentSetName", null, null);
		kmssDialog.SetAfterShow(action);
		kmssDialog.URL = Com_Parameter.ContextPath + "sys/xform/fragmentSet/meger/sysFormFragmentSetHistory_select.jsp?isHistory=true&fdId=" + fdId;
		kmssDialog.Show(window.screen.width*710/1366,window.screen.height*550/768);
	};
	 }); 
</script>
<kmss:windowTitle moduleKey="sys-xform-fragmentSet:module.xform.manage"
	subjectKey="sys-xform-fragmentSet:table.sysFormFragmentSet"
	subject="${sysFormFragmentSetHistoryForm.fdName}" />


<div id="optBarDiv">
	<c:if test="${param.versionType eq null || param.versionType ne 'new'}">
		<input type="button" value="<bean:message bundle="sys-xform-fragmentSet" key="button.synchronous"/>"
		onclick="synchronous();">
		<kmss:auth
			requestURL="/sys/xform/fragmentSet/xFormFragmentSet.do?method=edit&fdId=${param.fdId}"
			requestMethod="GET">
			<input type="button" value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}/sys/xform/fragmentSet/history/xFormFragmentSetHistory.do?method=editHistory&fdId=${param.fdId}&fdMainModelName=${param.fdMainModelName}','_self');">
		</kmss:auth>
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p class="txttitle">
	<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.historyVersion" />_V${xFormTemplateForm.fdTemplateEdition }
</p>
<center>
	<html:hidden name="sysFormFragmentSetHistoryForm" property="fdId" />
	<table id="Label_Tabel" width=95%>
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
					<tr>
						<!-- 需要合并的片段集id -->
						<div style="display:none;">
							<input  type="hidden" name="megerFragmentSetId" value=""/>
							<input  type="hidden" name="megerFragmentSetName" value=""/>		
						</div>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdName" />
						</td>
						<td width=85% colspan="3">
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdName" />
						</td>
					</tr>
					<%--适用类别--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdCatoryName" />
						</td>
						<td width=85% colspan="3">
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdCategoryName" />
						</td>
					</tr>
					<!-- 排序号 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message	bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdOrder" />
						</td>
						<td width=85% colspan="3">
							<bean:write property="fdOrder" name="sysFormFragmentSetHistoryForm" />
						</td>
					</tr>
					<tr>
					<!-- 使用范围 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.scope" />
						</td>
						<td width=85% colspan="3">
							<bean:write property="fdScopeName" name="sysFormFragmentSetHistoryForm" />
						</td>
					</tr>
					<tr>
						<!-- 修改人 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlteror" />
						</td>
						<td width=35%>
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdAlterorName" />
						</td>
						
						<!-- 修改时间 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlterTime" />
						</td>
						<td width=35%>
							<bean:write name="sysFormFragmentSetHistoryForm" property="fdAlterTime" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<!-- 片段集 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.templateSet'/>">
			<td>
				<table class="tb_normal" width=100% id="TB_FormTemplate_${HtmlParam.fdKey}">
					<%@ include file="/sys/xform/base/sysFormTemplateDisplay_view.jsp"%>
				</table>
			</td>
		</tr>
		<!-- 被引用表单模板 -->
		<c:import url="/sys/xform/fragmentSet/ref/sysFormRefFragmentSetHistory_view.jsp" charEncoding="UTF-8">
			<c:param name="isHistory" value="true"></c:param>
			<c:param name="versionType" value="${param.versionType}"></c:param>
		</c:import>
	</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>