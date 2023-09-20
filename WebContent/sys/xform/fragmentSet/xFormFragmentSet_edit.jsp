<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<c:set var="sysFormTemplateFormPrefix" value="" />
<c:set var="fdKey" value="" />
<script language="JavaScript">
	$KMSSValidation();
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("data.js");
	<%-- 
	=====================================
	 数据字典加载相关
	===================================== 
	--%>
	var _xform_MainModelName = '';
	
	//限定范围仅为一个时才允许使用跟主文档相关的变量
	function _XForm_GetSysDictObj(modelName){
		var rtnVal = ""
		var scopeName = $("input[name='fdScopeId']").val();
		if (!scopeName){
			return rtnVal;
		}else{
			var scopeNameArr = new Array();
			scopeNameArr = scopeName.split(";");
			if (scopeNameArr.length === 1){
				return Formula_GetVarInfoByModelName(scopeName);
			}else{
				return rtnVal;
			}
		}
	}
	function _XForm_GetSysDictObj_${JsParam.fdKey}() {
		return _XForm_GetSysDictObj(_xform_MainModelName);
	}
</script>
<script>
	function sysFromFragmentSet_submit(method){
		var customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'].contentWindow;
		if(!customIframe){
			customIframe = window.frames['IFrame_FormTemplate_${JsParam.fdKey}'];
		}
		if(customIframe.Designer != null && customIframe.Designer.instance != null){
			customIframe.Designer.instance.isNeedBreakValidate = true;
		}
		//新建的时候，不切换表单页签直接点保存，fdIsChangedObj为true会生成一条对应的历史记录
		if (method === "save" && !customIframe.Designer){
			//确认是否更新为新的版本
			var fdIsChangedObj = document.getElementsByName("${sysFormTemplateFormPrefix}fdIsChanged")[0];
			
			fdIsChangedObj.value = "true";
		}
		if(typeof XForm_BeforeSubmitForm != 'undefined' && XForm_BeforeSubmitForm instanceof Function){
			XForm_BeforeSubmitForm(function(){
				Com_Submit(document.sysFormFragmentSetForm, method);
			});
		}else{
			Com_Submit(document.sysFormFragmentSetForm, method);
        }
		if(customIframe.Designer != null && customIframe.Designer.instance != null){
			customIframe.Designer.instance.isNeedBreakValidate = false;
		}
	}
	// XForm_ConfirmFormChangedEvent必须放在Xform_BuildValueInConfirm方法之前
	Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = XForm_ConfirmFormChangedEvent;

	function XForm_ConfirmFormChangedEvent(formObj, method, clearParameter, moreOptions,isNotSupportModalDailog , callback) {
		return XForm_ConfirmFormChangedFun(isNotSupportModalDailog , callback);
	}
	
	//所属分类的弹框
	function XFormFragmentSet_treeDialog() {
		Dialog_Tree(false, 'fdCategoryId', 'fdCategoryName', ',', 
				'sysFormFramentSetCategoryTreeService&parentId=!{value}', 
				"${lfn:message('sys-xform-fragmentSet:sysFormFragmentSet.docCategory')}", 
				null, null, null, null, null, 
				"${lfn:message('sys-xform-fragmentSet:sysFormFragmentSet.docCategory')}");
	}
	
	function selectScopeDialog() {
		Dialog_Tree(true,'fdScopeId', 'fdScopeName',';','SysFormFragmentSetScopeDataBean&parentNodeId=!{value}','<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.selectScope" />',false);
	}
	
</script>
<kmss:windowTitle moduleKey="sys-xform-fragmentSet:table.sysFormFragmentSet"  subjectKey="sys-xform-fragmentSet:sysFormFragmentSet.templateSet" subject="${sysFormFragmentSetForm.fdName}" />
<html:form action="/sys/xform/fragmentSet/xFormFragmentSet.do" >
	<div id="optBarDiv">
		<c:if test="${sysFormFragmentSetForm.method_GET=='edit'}">
			<%--更新--%>
			<input id="languageUpdate" type=button value="<bean:message key="button.update"/>"
				onclick="sysFromFragmentSet_submit('update');">
		</c:if>
		 <c:if test="${sysFormFragmentSetForm.method_GET=='add' || sysFormFragmentSetForm.method_GET=='clone'}">
		 	<%--新增--%>
			<input type=button value="<bean:message key="button.save"/>"
				onclick="sysFromFragmentSet_submit('save');">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="sysFromFragmentSet_submit('saveadd');">
		</c:if> 
			<%--关闭--%>
			<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	
	<p class="txttitle">
		<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.templateSet" />
	</p>
	<center>
	<script>
		Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
	</script> 
	<table id="Label_Tabel" width=95%>
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.basicInfo'/>">
			<td>
				<table class="tb_normal" width=100%>
					<html:hidden property="fdId" />
					<%--片段集名称--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdName" />
						</td>
						<td width=85% colspan="3">
							<xform:text property="fdName" style="width:80%;" required="true"></xform:text>
						</td>
					</tr>
					<%--片段集描述--%>
					<%-- <tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdDesc" />
						</td>
						<td width=85% colspan="3"><html:textarea property="fdDesc" style="width:80%;" /></td>
					</tr> --%>
					<%--类别--%>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdCatoryName" />
						</td>
						<td width=85% colspan="3">
							<xform:dialog required="true" subject="${lfn:message('sys-xform-fragmentSet:sysFormFragmentSet.fdCatoryName') }" propertyId="fdCategoryId" style="width:80%"
											propertyName="fdCategoryName" dialogJs="XFormFragmentSet_treeDialog()">
							</xform:dialog>
						</td>
					</tr>
					<!-- 排序号 -->
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message	bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.fdOrder" />
						</td>
						<td width=85% colspan="3">
							<xform:text property="fdOrder" style="width:80%;" validators="digits min(0)" />
						</td>
					</tr>
					 <!-- 使用范围 -->
					 <tr id="scope">
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.scope" />
						</td>
						<td width=85% colspan="3">
							<!-- 被选中的模板ID集合“;”分割 -->
							<xform:text property="fdScopeId" showStatus="noShow"></xform:text>
							<xform:textarea property="fdScopeName" showStatus="edit" style="width:96%" htmlElementProperties="readOnly onclick='selectScopeDialog();'"></xform:textarea>
							<a href="javascript:void(0);" onclick="selectScopeDialog();"><bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.selectScope" /></a>
							<br>
							<bean:message key="sysFormFragmentSet.scope.desc" bundle="sys-xform-fragmentSet"/>
						</td>
					</tr>
				<%---新建时，不显示 创建人，创建时间 ---%>
               <c:if test="${sysFormFragmentSetForm.method_GET=='edit'}">
					<tr>
						<!-- 创建人员 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docCreatorId" />
						</td>
						<td width=35%>
							<html:text property="docCreatorName" readonly="true" style="width:50%;" />
						</td>
						
						<!-- 创建时间 -->
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docCreateTime" />
						</td>
						<td width=35%>
							<html:text property="docCreateTime" readonly="true" style="width:50%;" />
						</td>
					</tr>
					<c:if test="${not empty sysFormFragmentSetForm.docAlterorName}">
						<tr>
							<!-- 修改人 -->
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlteror" />
							</td>
							<td width=35%>
								<bean:write name="sysFormFragmentSetForm" property="docAlterorName" />
							</td>
							
							<!-- 修改时间 -->
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.docAlterTime" />
							</td>
							<td width=35%>
								<bean:write name="sysFormFragmentSetForm" property="docAlterTime" />
							</td>
						</tr>
					</c:if>
				</c:if>
			</table>
			</td>
		</tr>
  			
		<!-- 表单 -->
		<tr LKS_LabelName="<bean:message bundle='sys-xform-fragmentSet' key='sysFormFragmentSet.templateSet'/>">
			<td>
				<table class="tb_normal" width=100% id="TB_FormTemplate_${HtmlParam.fdKey}">
					<%@ include file="/sys/xform/base/sysFormTemplateDisplay_edit.jsp"%>
				</table>
			</td>
		</tr>	
		
		<%--多语言 --%>
		<%-- <%  if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()) {%>
			<c:import url="/sys/xform/lang/include/sysFormMultiLang_edit.jsp"	charEncoding="UTF-8">
					<c:param name="formName" value="sysFormFragmentSetForm" />
					<c:param name="fdKey" value="reviewMainDoc" />
			</c:import>
		<% } %> --%>
		
	</table>
	</center>
	
	<html:hidden property="method_GET" />
	<script>
		$KMSSValidation();
	</script>
</html:form>

<%@ include file="/resource/jsp/edit_down.jsp"%>
