<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
<template:replace name="head">
<%@ include file="/sys/lbpmservice/include/sysLbpmProcessConstant.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("data.js|dialog.js|jquery.js", null, "js");
	lbpm.workitem.constant.COMMONPAGEFIRSTOPTION = '<bean:message key="page.firstOption" />';
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/sys/lbpmservice/workitem/workitem_common_usage.js"></script>
<script>
	var isOnClick = false;
	var SettingInfo = null;
	
	//统一调用此方法获取默认值与功能开关的值
	function getSettingInfo(){
		if (SettingInfo == null) {
			SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0];
		}
		return SettingInfo;
	} 
	
	//获取list页面选择的流程记录的id
	function initDocIds(docIds) {
		var values = [];
		if(docIds){
			values.push(docIds);
		} else {
			var contentId = "${JsParam.contentId}";
			if(contentId){
				$("#"+contentId+" input[name='List_Selected']:checked",parent.document).each(function() {
					values.push($(this).val());
				});
			}else{
				$("input[name='List_Selected']:checked",parent.document).each(function() {
					values.push($(this).val());
				});
			}
		}
		if(values.length <= 0 && top){//为了兼容某些情况下需要遮罩全覆盖问题
			values = top.selectDocIds || [];
		}
		$("input[name='docIds']").val(values);
	}
	$(function() {
		var docIds = Com_GetUrlParameter(this.URL, "docIds");
		initDocIds(docIds);
		//根据开关是否显示通知
		var lbpmSettingInfo = getSettingInfo();
		var isNotifyCurrentHandler = lbpmSettingInfo["isNotifyCurrentHandler"];
		if (isNotifyCurrentHandler === "false"){
			$("#notifyTypeRow").remove();
		}
	});
	// 检验废弃操作的审批意见必填
	function validateMustSignYourSuggestion(){
		if($("input[name='operationType'][checked]").val() == "admin_abandon") {
			var fdContentObj = $('[name=fdUsageContent]')[0];
			if(fdContentObj && fdContentObj.value != ""){
				return true;
			}
			alert(lbpm.constant.opt.MustSignYourSuggestion);
			return false;
		}
		return true;
	}
	// 变更操作选择项
	function changeSelect(obj){
		if($("input[name='operationType'][checked]").val() == obj.value){
			return;
		}
		$("input[name='operationType'][checked]").removeAttr("checked");
		$(obj).attr("checked", "checked");
	};

	//特权操作窗口调用不到node_common_review.js的lbpm.globals.popupWindow方法，故自行添加
	lbpm.globals.popupWindow=function(url,width,height,param){
		var left = (screen.width-width)/2;
		var top = (screen.height-height)/2;
		if(window.showModalDialog){
			var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
			var rtnVal=window.showModalDialog(url, param, winStyle);
			if(param.AfterShow)
				param.AfterShow(rtnVal);
		}else{
			var winStyle = "resizable=1,scrollbars=1,width="+width+",height="+height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
			Com_Parameter.Dialog = param;
			var tmpwin=window.open(url, "_blank", winStyle);
			if(tmpwin){
				tmpwin.onbeforeunload=function(){
					if(param.AfterShow && !param.AfterShow._isShow) {
						param.AfterShow._isShow = true;
						param.AfterShow(tmpwin.returnValue);
					}
				}
			}		
		}
	};

	//初始化
	$(document).ready(function() {
		setTimeout(function(){
			for(var i=0; i<lbpm.onLoadEvents.once.length; i++){
				lbpm.onLoadEvents.once[i]();
			}
			for(var i=0; i<lbpm.onLoadEvents.delay.length; i++){
				lbpm.onLoadEvents.delay[i]();
			}
		},0);
	});
</script>
</template:replace>
<template:replace name="content">
<form
	action="${pageContext.request.contextPath}/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do"
	method="post">
	<p>&nbsp;</p>
	<div class="lui_form_content_frame">
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width="15%"><bean:message
						bundle="sys-lbpmservice" key="lbpmNode.createDraft.commonUsages" />
				</td>
				<td colspan="3"><select name="commonUsages"
					onchange="lbpm.globals.setUsages(this);"
					style="width: 92px; overflow-x: hidden; padding-left: 0px;">
						<option value="">
							<bean:message key="page.firstOption" />
						</option>
				</select> <a href="#"
					onclick="Com_EventPreventDefault();lbpm.globals.openDefiniateUsageWindow();">
						<bean:message bundle="sys-lbpmservice"
							key="lbpmNode.createDraft.commonUsages.definite" />
				</a></td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%"><bean:message
						bundle="sys-lbpmservice" key="lbpmNode.createDraft.opinion" /></td>
				<td colspan="3">
					<table width=100% border=0 class="tb_noborder">
						<tr>
							<td><textarea name="fdUsageContent" class="inputMul"
									style="width: 100%;" alertText="" key="auditNode"></textarea></td>
						</tr>
						<tr>
							<td><label id="currentNodeDescription"></label></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%"><bean:message
						bundle="sys-lbpmservice"
						key="lbpmNode.processingNode.operationMethods" /></td>
				<td colspan="3">
				<c:if test="${param.oneKey == '1'}">
					<label><input type="radio" name="operationType" value="admin_press"
						checked readOnly="true" />
					<bean:message bundle="sys-lbpmservice"
						key="lbpm.operation.admin_press" /></label>
				</c:if>
				<c:if test="${param.oneKey != '1'}">
				<c:choose>
					<c:when test="${param.fdType=='error'}">
						<label><input type="radio" onclick="changeSelect(this);" name="operationType" value="admin_retry"
							checked readOnly="true" />
						<bean:message bundle="sys-lbpmservice"
							key="lbpm.operation.retryErrorQueue" /></label>
						<label><input type="radio" onclick="changeSelect(this)" name="operationType" value="admin_abandon"
							readOnly="true" />
					</c:when>
					<c:otherwise>
						<label><input type="radio" onclick="changeSelect(this)" name="operationType" value="admin_abandon"
							checked readOnly="true" />
					</c:otherwise>
				</c:choose>
				<bean:message bundle="sys-lbpmservice"
						key="lbpm.operation.admin_abandon" /></label>
				<label><input type="radio" onclick="changeSelect(this)" name="operationType"
					value="admin_pass" readOnly="true" />
				<bean:message bundle="sys-lbpmservice"
						key="lbpm.operation.admin_pass" /></label>
				</c:if>
				</td>
			</tr>
			<tr>
				<td id="rerunIfErrorTDTitle" class="td_normal_title" width="15%">
					<kmss:message
						key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.addition.rerunEventTitle" />
				</td>
				<td id="rerunIfErrorTDContent" colspan="3"><label
					id="rerunIfErrorLabel"> <input type="checkbox"
						id="rerunIfError" value="true"> <kmss:message
							key="sys-lbpmservice-operation-admin:lbpmOperations.fdOperType.addition.rerunEventFlag" />
				</label></td>
			</tr>
			<tr id="notifyTypeRow">
				<td class="td_normal_title"  width="18%">
					<bean:message bundle="sys-lbpmservice" key="lbpmNode.currentNode.notify.type" />
				</td>
				<td colspan="3" id="systemNotifyTypeTD">
					<kmss:editNotifyType property="notifyType"/>
				</td>
			</tr>
		</table>
	</div>
	<center>
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;padding-bottom:20px;">
			<ui:button style="width:80px" onclick="if(validateMustSignYourSuggestion() && (!isOnClick)){isOnClick = true;document.forms[0].submit();}" text="${lfn:message('button.update') }" />
		</div>
	</center>

	<input type="hidden" name="docIds" value="" /> 
	<input type="hidden" name="method" value="batchPrivil" />
	<input type="hidden" name="methodType" value="${HtmlParam.method}" /> 
	<input type="hidden" name="fdType" value="${HtmlParam.fdType}" /> 
</form>
</template:replace>
</template:include>