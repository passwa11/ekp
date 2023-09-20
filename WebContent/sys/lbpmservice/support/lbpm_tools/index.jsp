<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link href="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_tools/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
Com_IncludeFile("jquery.js|dialog.js|formula.js|doclist.js|json2.js");
</script>
<title><bean:message bundle="sys-lbpmservice-support" key="lbpmTools.tools" /></title>
<!-- 根据访问入口决定上方显示关闭按钮或者显示当前路径 -->
<c:choose>
	<c:when test="${param.s_default=='true'}">
		<span class="txtlistpath"><div class="lui_icon_s lui_icon_s_home" style="float: left;"></div><div style="float: left;"><bean:message key="page.curPath"/>${fn:escapeXml(param.s_path)}</div></span>
	</c:when>
	<c:otherwise>
		<template:block name="path" >
			<c:if test="${empty param.s_path}">
				<div id="optBarDiv">
					<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
				</div>
			</c:if>
		</template:block>
	</c:otherwise>
</c:choose>
<!-- <div style="text-align: center;"> -->
<br>
<p class="txttitle">
	<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.tools.batch" />
</p>
<br>
<center>
<div class="lui_lbpm_tools_container">
	<div class="lui_lbpm_tools_tab">
		<div class="lui_lbpm_tools_tab-wrap">
 			<div class="lui_lbpm_tools_tab-list-wrap">
    			<ul class="lui_lbpm_tools_tab-list">
      				<li class="active" style="border-right:1px solid #D5D5D5">
			        	<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.updateCostTime" />
			        </li>
				    <li style="border-right:1px solid #D5D5D5">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.updateNodeName" />
				    </li>
			        <li style="border-right:1px solid #D5D5D5">
			        	<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.updateFingerPrint" />
			        </li>
			        <li style="border-right:1px solid #D5D5D5">
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.updateFacePrint" />
					</li>
			        <li style="border-right:1px solid #D5D5D5">
			        	<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.updateNodeTmeout" />
			        </li>
			        <li style="border-right:1px solid #D5D5D5">
			        	<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.notifyType" />
			        </li>
			        <li style="border-right:1px solid #D5D5D5">
			        	<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.workTimeUpdate" />
			        </li>
					<li>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmTools.updateHandlerName" />
					</li>
    			</ul>
  			</div>
		</div>
		<!-- 根据访问入口确定内容宽度 -->
		<c:choose>
			<c:when test="${param.s_default=='true'}">
				<div class="lui_lbpm_tools_tab-content" style="width:55%;">
			</c:when>
			<c:otherwise>
				<div class="lui_lbpm_tools_tab-content" style="width:40%;">
			</c:otherwise>
		</c:choose>
			<ul class="lui_lbpm_tools_tab-content-list">
			    <li class="active">
			    	<%@ include file="/sys/lbpmservice/support/lbpm_tools/cost_time/lbpm_tool_cost_time.jsp"%>
				</li>
				<li>
					<%@ include file="/sys/lbpmservice/support/lbpm_tools/node_name/lbpm_tool_node_name.jsp"%>
				</li>
				<li>
					<%@ include file="/sys/lbpmservice/support/lbpm_tools/finger_print/lbpm_tool_finger_print.jsp"%>
				</li>
				<li>
					<%@ include file="/sys/lbpmservice/support/lbpm_tools/face_print/lbpm_tool_face_print.jsp"%>
				</li>
				<li>
					<%@ include file="/sys/lbpmservice/support/lbpm_tools/node_timeout/lbpm_tool_node_timeout.jsp"%>
				</li>
				<li>
					<%@ include file="/sys/lbpmservice/support/lbpm_tools/notify_type/lbpm_tool_notify_type.jsp"%>
				</li>
				<li>
					<%@ include file="/sys/lbpmservice/support/lbpm_tools/work_time/lbpm_tool_work_time.jsp"%>
				</li>
				<li>
					<%@ include file="/sys/lbpmservice/support/lbpm_tools/handler_name/lbpm_tool_handler_name.jsp"%>
				</li>
		    </ul>
		</div>
	</div>
</div>
<div id="lbpmToolDiv" style="width:${JsParam.s_default eq 'true'?75:60}%;border:1px solid #b4b4b4;display:none;">
	<div style="width:98%">
		<ui:iframe id="lbpmToolIframe"></ui:iframe>
	</div>
</div>
</center>
<script>
var lbpm_tools_validation = $KMSSValidation();

//多标签切换
$('.lui_lbpm_tools_tab-list>li').click(function() {
	$("#lbpmToolDiv").hide();
	$(this).parent().find('li').removeClass('active');
	$(this).addClass('active');
	var idx = $(this).index();
	var showContent = '.lui_lbpm_tools_tab-content-list>li:eq(' + idx + ')';
	$(this).parents('.lui_lbpm_tools_tab').find('.lui_lbpm_tools_tab-content-list>li').hide();
	$(this).parents('.lui_lbpm_tools_tab').find(showContent).fadeIn();
});

//选择流程模板
function lbpmToolsSelectSubFlow(idFiled,nameFiled,cateIdsFiled,cateNamesFiled,modelNamesFiled,moduleNamesFiled,templateIdsFiled,templateNamesFiled) {
	var treeTitle = '<kmss:message key="lbpmTools.range" bundle="sys-lbpmservice-support" />';
	Dialog_Tree(true, idFiled, nameFiled, ';', 'lbpmAuthorizeScopeTreeService&top=true&filter=false',
			treeTitle, null, function(obj){
				afterClickedScopeDialogAction(obj,nameFiled,cateIdsFiled,cateNamesFiled,modelNamesFiled,moduleNamesFiled,templateIdsFiled,templateNamesFiled);
			});
}

function afterClickedScopeDialogAction(obj,nameFiled,cateIdsFiled,cateNamesFiled,modelNamesFiled,moduleNamesFiled,templateIdsFiled,templateNamesFiled){
	if(obj == null){
		return false;
	}
	var fdScopeFormAuthorizeCateIdsObj = document.getElementsByName(cateIdsFiled)[0];
	var fdScopeFormAuthorizeCateNamesObj = document.getElementsByName(cateNamesFiled)[0];
	var fdScopeFormModelNamesObj = document.getElementsByName(modelNamesFiled)[0];
	var fdScopeFormModuleNamesObj = document.getElementsByName(moduleNamesFiled)[0];
	var fdScopeFormTemplateIdsObj = document.getElementsByName(templateIdsFiled)[0];
	var fdScopeFormTemplateNamesObj = document.getElementsByName(templateNamesFiled)[0];
	var fdScopeFormAuthorizeCateShowtextsObj = document.getElementsByName(nameFiled)[0];
	var fdScopeFormAuthorizeCateIds = "";
	var fdScopeFormAuthorizeCateNames = "";
	var fdScopeFormModelNames = "";
	var fdScopeFormModuleNames = "";
	var fdScopeFormTemplateIds = "";
	var fdScopeFormTemplateNames = "";
	var fdScopeFormAuthorizeCateShowtexts = "";
	for(var i = 0; i < obj.data.length; i++){
		var urlValue = obj.data[i].id;
		var showText = GetUrlParameter_Unescape(urlValue, "showText");
		var categoryId = GetUrlParameter_Unescape(urlValue, "categoryId");
		var categoryName = GetUrlParameter_Unescape(urlValue, "categoryName");
		var modelName = GetUrlParameter_Unescape(urlValue, "modelName");
		var moduleName = GetUrlParameter_Unescape(urlValue, "moduleName");
		var templateId = GetUrlParameter_Unescape(urlValue, "templateId");
		var templateName = GetUrlParameter_Unescape(urlValue, "templateName");
		fdScopeFormAuthorizeCateIds += (categoryId == null?" ":categoryId) + ";";
		fdScopeFormAuthorizeCateNames += (categoryName == null?" ":categoryName) + ";";
		fdScopeFormModelNames += (modelName == null?" ":modelName) + ";";
		fdScopeFormModuleNames += (moduleName == null?" ":moduleName) + ";";
		fdScopeFormTemplateIds += (templateId == null?" ":templateId) + ";";
		fdScopeFormTemplateNames += (templateName == null?" ":templateName) + ";";
		fdScopeFormAuthorizeCateShowtexts += (showText == null?" ":showText) + ";";
	}
	var isChange = false;
	if(fdScopeFormAuthorizeCateIdsObj.value != fdScopeFormAuthorizeCateIds || fdScopeFormTemplateIdsObj.value != fdScopeFormTemplateIds){
		isChange = true;
	}
	fdScopeFormAuthorizeCateIdsObj.value = fdScopeFormAuthorizeCateIds;
	fdScopeFormAuthorizeCateNamesObj.value = fdScopeFormAuthorizeCateNames;
	fdScopeFormModelNamesObj.value = fdScopeFormModelNames;
	fdScopeFormModuleNamesObj.value = fdScopeFormModuleNames;
	fdScopeFormTemplateIdsObj.value = fdScopeFormTemplateIds;
	fdScopeFormTemplateNamesObj.value = fdScopeFormTemplateNames;
	//fdScopeFormAuthorizeCateShowtextsObj.value = fdScopeFormAuthorizeCateShowtexts;
	return isChange;
}

/**
 * 获取URL中的参数（使用unescape对返回参数值解码）
 */
function GetUrlParameter_Unescape(url, param){
	var re = new RegExp();
	re.compile("[\\?&]"+param+"=([^&]*)", "i");
	var arr = re.exec(url);
	if(arr==null) {
		return null;
	} else {
		return unescape(arr[1]);
	}
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>