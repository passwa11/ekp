<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<%@page import="com.landray.kmss.sys.xform.util.LangUtil"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<c:set var="xFormTemplateForm" value="${sysFormTemplateHistoryForm}" scope="request" />
<c:set var="sysFormTemplateFormResizePrefix" value="" scope="request" />
<c:set var="sysFormTemplateForm" value="${sysFormTemplateHistoryForm}" scope="request" />
<c:set var="sysFormTemplateFormPrefix" value="" scope="request" />
<%@ include file="/sys/xform/base/template_script.jsp" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/component/locker/resource/jNotify.jquery.css"/>" media="screen" />
<script type="text/javascript" src="<c:url value="/component/locker/resource/jNotify.jquery.js"/>"></script>
<script type="text/javascript">Com_IncludeFile("docutil.js|security.js|dialog.js|formula.js");</script>
<script>
	Com_IncludeFile("dialog.js");
	var mobile = ${mobile};
	Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = XForm_ConfirmFormChangedEvent;

	function XForm_ConfirmFormChangedEvent() {
		return XForm_ConfirmFormChangedFun();
	}

	Com_AddEventListener(window, "load", function() {
		LoadXForm(document.getElementById('TD_FormTemplate_${xFormTemplateForm.fdKey}'));
		$("[name='fdModelName']").val("${sysFormTemplateForm.fdModelName}");
		if("${xFormTemplateForm.fdMode}" == "<%=XFormConstant.TEMPLATE_SUBFORM%>"){
			$("#Subform_operation").attr("src","${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif");
			$("#SubForm_main_tr").show();
			$("#SubForm_operation_tr").show();
			$("span[name='subFormSpan']").show();
			$("span[name='subFormSpan']").text("<kmss:message key='sys-xform:sysSubFormTemplate.change_msg'/>");

			//多表单模式下, 只显示当前表单
			var currentFormId = Com_GetUrlParameter(location.href, "fdId");
			var defaultFormId = $("[name='fdId']").val();
			var customIframe = window.frames['IFrame_FormTemplate_${xFormTemplateForm.fdKey}'].contentWindow;
			if(!customIframe){
				customIframe = window.frames['IFrame_FormTemplate_${xFormTemplateForm.fdKey}'];
			}
			if (mobile) {
				var pcFormId = "";
				$("#table_docList_${xFormTemplateForm.fdKey}_form").find("tr").each(function(index, obj){
					if ($(obj).attr("id") != currentFormId) {
						$(obj).hide();
					} else {
						pcFormId = $(obj).find("input[name$='fdPcFormId']").val();
					}
				});
				if (defaultFormId == pcFormId) {
					pcFormId = "subform_default"
				}
				$("#TABLE_DocList_SubForm").find("tr").each(function(index, obj){
					if ($(obj).attr("id") != pcFormId) {
						$(obj).hide();
					}
				});
				var checkedTr = $("#TABLE_DocList_SubForm").find("tr[id='" + pcFormId + "']");
				var checkedTrId = setInterval(function() {
					if (customIframe.XForm_Design_Has_Init) {
						checkedTr.find("a").click();
						clearInterval(checkedTrId);
					}
				}, 100);
			} else {
				var pcFormId = currentFormId;
				if (defaultFormId == currentFormId) {
					pcFormId = "subform_default"
				}
				$("#TABLE_DocList_SubForm").find("tr").each(function(index, obj){
					if ($(obj).attr("id") != pcFormId) {
						$(obj).hide();
					}
				});
				$("#table_docList_${xFormTemplateForm.fdKey}_form").find("tr").each(function(index, obj){
					if ($(obj).find("input[name$='fdPcFormId']").val() != currentFormId) {
						$(obj).hide();
					}
				});
				var checkedTr = $("#TABLE_DocList_SubForm").find("tr[id='" + pcFormId + "']");
				var checkedTrId = setInterval(function() {
					if (customIframe.XForm_Design_Has_Init) {
						checkedTr.find("a").click();
						clearInterval(checkedTrId);
					}
				}, 100);
			}
		}else{
			$("#SubForm_main_tr").hide();
			$("#SubForm_operation_tr").hide();
			$("span[name='subFormSpan']").hide();
		}
	});

	var _xform_MainModelName = '${param.fdMainModelName}';
	var _xform_templateId = '${xFormTemplateForm.fdModelId}';
	var _xform_templateModelName = '${xFormTemplateForm.fdModelName}';


	function _XForm_GetTempExtDictObj(tempId) {
		return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId="+tempId).GetHashMapArray();
	}
	function _XForm_GetCommonExtDictObj(tempId) {
		return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=common&tempId="+tempId).GetHashMapArray();
	}
	function _XForm_GetExitFileDictObj(fileName) {
		return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=file&fileName="+fileName).GetHashMapArray();
	}
	function _XForm_GetSysDictObj(modelName){
		return Formula_GetVarInfoByModelName(modelName);
	}
	function _XForm_GetSysDictObj_${xFormTemplateForm.fdKey}() {
		return _XForm_GetSysDictObj(_xform_MainModelName);
	}

	function updateWarning(){
		var updateWarning = document.getElementById("templateHistoryRefMain").contentWindow.updateWarning;
		if(updateWarning && updateWarning != ""){
			if(!confirm(updateWarning)){
				return false;
			}
		}
		Com_Submit(document.sysFormTemplateHistoryForm, 'update');
	}

</script>

<kmss:windowTitle moduleKey="sys-xform:xform.title"
				  subjectKey="sys-xform:tree.xform.def"
				  subject="历史模板" />

<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.save"/>"
		   onclick="updateWarning();" />
	<input type="button" value="<bean:message key="button.close"/>"
		   onclick="Com_CloseWindow();" />
</div>
<html:form action="/sys/xform/base/sys_form_template_history/sysFormTemplateHistory.do" method="post">
	<html:hidden property="fdId"/>
	<html:hidden property="fdDesignerHtml"></html:hidden>
	<html:hidden property="fdMetadataXml"></html:hidden>
	<html:hidden property="fdCss"/>
	<html:hidden property="fdCssDesigner"/>
	<input type="hidden" name = "_sysFormTemplateHistory" value="true"/>

	<p class="txttitle">表单历史模板_V${xFormTemplateForm.fdTemplateEdition }</p>
	<center>
		<table id="Label_Tabel" width=95%>
			<tr LKS_LabelName="模板信息">
				<td>
					<table class="tb_normal" width=100% id="SubFormTable_${xFormTemplateForm.fdKey}">
						<!-- 移动端设计 -->
						<tr  id="XForm_${xFormTemplateForm.fdKey}_CustomTemplateMobileRow">
							<td colspan=3>
								<c:import url="/sys/xform/designer/mobile/sysFormTemplate_mobile_designer.jsp" charEncoding="UTF-8">
									<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
									<c:param name="formName" value="sysFormTemplateHistoryForm" />
									<c:param name="fdKey" value="${xFormTemplateForm.fdKey}" />
									<c:param name="history" value="true" />
								</c:import>
							</td>
						</tr>
						<tr id="pc_form_${xFormTemplateForm.fdKey}">
							<td style="width: 20%;display:none;" id="SubForm_main_tr">
								<div id="DIV_SubForm_${xFormTemplateForm.fdKey}" style="border:1px #d2d2d2 solid;">
									<div id="Sub_title_div" style="height:36px;border-bottom:1px solid #d2d2d2;">
										<table class="subTable" style="width:100%">
											<tr>
												<td style="width: 38%;padding:8px"><div style="margin-left:4px;font-size:12px;font-weight:bold;"><bean:message bundle="sys-xform" key="sysSubFormTemplate.multiform_configuration" /></div></td>
												<td style="width: 32%;padding:8px"><a id="operationA2" style="cursor:pointer;color:#47b5e6;font-weight:bold;font-size:12px;" onclick="Designer_SubForm_Xml();"><bean:message bundle="sys-xform" key="sysSubFormTemplate.view_xml" /></a></td>
												<td style="width: 30%;padding:8px" align="right">
													<a href="javascript:void(0)" style="cursor:pointer;" onclick="SubForm_AddRow(this,false);">
														<img src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/add.png" border="0" title="<bean:message bundle="sys-xform" key="sysSubFormTemplate.add" />"/>
													</a>
												</td>
											</tr>
										</table>
									</div>
									<c:set var="mobile" value="true" scope="request"/>
									<div id="SubFormDiv" style="overflow-x:hidden;overflow-y:auto;text-align:center;">
										<c:import url="/sys/xform/base/sysSubFormTemplate_edit.jsp"	charEncoding="UTF-8">
											<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
											<c:param name="formName" value="sysFormTemplateHistoryForm" />
											<c:param name="fdKey" value="${xFormTemplateForm.fdKey}" />
										</c:import>
									</div>
									<div id="SubFormLoadMsg" style="font-weight:bold;display:none;color:rgb(153, 153, 153);margin-left: 10px;"><bean:message bundle="sys-xform" key="sysSubFormTemplate.loadMsg" />
									</div>
									<div id="controlsDiv" style="overflow-x:hidden;overflow-y:auto;">
									</div>
								</div>
							</td>
							<td style="width:7px;display:none" id="SubForm_operation_tr">
								<image id="Subform_operation" style="cursor:pointer;" src="${KMSS_Parameter_ContextPath}sys/xform/base/resource/icon/varrowleft.gif" onclick="_Designer_SubFormAddHide(this);"/>
							</td>
							<td>
								<table class="tb_normal" width="100%" id="TB_FormTemplate_${xFormTemplateForm.fdKey}">
									<tr style="display:none;">
										<td>
											<!-- 移动配置开关start -->
											<div class="layoutMobileWrap" style="display: inline-block" id="FormTemplate_${xFormTemplateForm.fdKey}_Layout">
												<div class="layoutMobileSwitch">
													<xform:checkbox property="${sysFormTemplateFormPrefix}fdUseDefaultLayout" >
														<xform:simpleDataSource value="true"><bean:message bundle="sys-xform" key="sysFormTemplate.fdUseDefaultLayoutDesc"/></xform:simpleDataSource>
													</xform:checkbox>
												</div>
												<div class="lui_prompt_tooltip">
													<label class="lui_prompt_tooltip_drop">
														<img src="${KMSS_Parameter_ContextPath}sys/xform/designer/style/img/promptControl.png">
													</label>
													<div class="lui_dropdown_tooltip_menu" name="useDefaultLayoutTip" style="display: none;"><bean:message bundle="sys-xform" key="sysFormTemplate.fdUseDefaultLayoutTip"/></div>
												</div>
											</div>
											<!-- 移动配置开关end -->
										</td>
									</tr>
									<c:import url="/sys/xform/base/sysFormTemplateHistoryDisplay_edit.jsp"	charEncoding="UTF-8">
										<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }"></c:param>
										<c:param name="formName" value="sysFormTemplateHistoryForm"></c:param>
										<c:param name="noProcessFlow" value="true"></c:param>
									</c:import>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
				<%--多语言 --%>
			<%  if(LangUtil.isEnableMultiLang(request.getParameter("fdMainModelName"), "model") && LangUtil.isEnableAdminDoMultiLang()) {%>
			<c:import url="/sys/xform/lang/include/sysFormHistoryMultiLang_edit.jsp"	charEncoding="UTF-8">
				<c:param name="formName" value="sysFormTemplateHistoryForm" />
				<c:param name="sysFormTemplateFormPrefix" value="${sysFormTemplateFormPrefix }" />
			</c:import>
			<% } %>
				<%--被引用的主文档 --%>
			<c:import url="/sys/xform/base/sys_form_template_history/sysFormTemplateHistoryRefMain_view.jsp"	charEncoding="UTF-8">
				<c:param name="formName" value="sysFormTemplateHistoryForm" />
				<c:param name="fdMainModelName" value="${fdMainModelName}" />
			</c:import>
		</table>
	</center>
</html:form>

<%@ include file="/resource/jsp/edit_down.jsp"%>