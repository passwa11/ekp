
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%@ include file="/resource/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<link rel=stylesheet href="../css/panel.css">
<script>
Com_IncludeFile("validation.jsp|validation.js|plugin.js|jquery.js|doclist.js|docutil.js|dialog.js|formula.js");
</script>
</head>
<body style="margin:0px">
<center>
<form>
<c:if test="${JsParam.notTitle ne 'true' }">
	<div class="lbpm_freeflow_winTitle">
		<c:choose>
			<c:when test="${JsParam.nodeType eq 'reviewNode' }">
				<bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.newAddReviewNode" />
			</c:when>
			<c:when test="${JsParam.nodeType eq 'signNode' }">
				<bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.newAddSignNode" />
			</c:when>
			<c:when test="${JsParam.nodeType eq 'sendNode' }">
				<bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.newAddSendNode" />
			</c:when>
		</c:choose>
	</div>
</c:if>
<br>
<br>
<table style="height:99.9%; width:90%;padding: 8px" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td>
			<div class="lbpm_freeflow_row">
				<div class="lbpm_freeflow_title">
					<bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.addFreeFlow" />
				</div>
				<div class="lbpm_freeflow_detail">
					<c:choose>
						<c:when test="${JsParam.nodeType eq 'sendNode' }">
							<c:choose>
								<c:when test="${JsParam.isTemplate eq 'true' }">
									<xform:address propertyName="handlerNames" propertyId="handlerIds" subject="节点审批人" orgType="ORG_TYPE_ALL | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" ></xform:address>
								</c:when>
								<c:otherwise>
									<xform:address propertyName="handlerNames" propertyId="handlerIds" required="true" subject="节点审批人" orgType="ORG_TYPE_ALL | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" ></xform:address>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${JsParam.isTemplate eq 'true' }">
									<xform:address propertyName="handlerNames" propertyId="handlerIds" subject="节点审批人" orgType="ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" ></xform:address>
								</c:when>
								<c:otherwise>
									<xform:address propertyName="handlerNames" propertyId="handlerIds" required="true" subject="节点审批人" orgType="ORG_TYPE_POSTORPERSON | ORG_TYPE_ROLE" mulSelect="true" showStatus="edit" style="width:95%" ></xform:address>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</td>
	</tr>
	<tr>
		<td>
			<div class="lbpm_freeflow_row">
				<div class="lbpm_freeflow_title">
					<bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.branchType" />
				</div>
				<div class="lbpm_freeflow_detail lbpm_freeflow_type">
					<label class="checked">
						<div class="lbpm_freeflow_type_checkbox"><input type="radio" name="handlerType" value="3" checked><span><bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.default" /></span><!-- 将所选审批人合并一个节点  --></div>
						<div class="lbpm_freeflow_type_img"><img id="default_selected" src="../images/default_selected.png"></div>
					</label>
					<label style="padding-left: 10px;">
						<div class="lbpm_freeflow_type_checkbox"><input type="radio" name="handlerType" value="0"><span><bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.serial" /></span><!-- 将所选审批人逐个串发  --></div>
						<div class="lbpm_freeflow_type_img"><img id="serial_select" src="../images/serial_noselected.png"></div>
					</label>
					<label style="padding-left: 10px;">
						<div class="lbpm_freeflow_type_checkbox"><input type="radio" name="handlerType" value="1"><span><bean:message bundle="sys-lbpm-engine" key="lbpmFreeFlow.parallel" /></span><!-- 将所选审批人逐个并发  --></div>
						<div class="lbpm_freeflow_type_img"><img id="spit_select" src="../images/spit_noselected.png"></div>
					</label>
				</div>
			</div>
		</td>
	</tr>
</table>
<div class="lbpm_freeflow_operation_row">
	<div class="lbpm_freeflow_btn" onclick="save();">确定</div>
	<div class="lbpm_freeflow_btn" onclick="closeDialog();">取消</div>
</div>
</form>
</center>
<script>
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
</script>
<script>
var _validation = $KMSSValidation();
var dialogObject = null;
if(window.showModalDialog) {
	dialogObject = window.dialogArguments;
	var popup = Com_GetUrlParameter(location.href,"popup");
	if (dialogObject == null && popup == "true") {
		dialogObject = parent.Com_Parameter.Dialog;
	}
} else if(opener) {
	dialogObject = opener.Com_Parameter.Dialog;
} else {
	var popup = Com_GetUrlParameter(location.href,"popup");
	if (dialogObject == null && popup == "true") {
		dialogObject = parent.Com_Parameter.Dialog;
	}
}

$(document).ready(function () {
	$(".lbpm_freeflow_type>label").click(function(){
		$(".lbpm_freeflow_type>label").removeClass("checked");
		$(this).addClass("checked");
		$(this).find('input:radio[name="handlerType"]').prop("checked","true");
		var handlerTypeValue=$('input:radio[name="handlerType"]:checked').val();
		if(handlerTypeValue=="3"){
			$("#default_selected").attr("src","../images/default_selected.png");
			$("#serial_select").attr("src","../images/serial_noselected.png");
			$("#spit_select").attr("src","../images/spit_noselected.png");
		}
		if(handlerTypeValue=="0"){
			$("#default_selected").attr("src","../images/default_noselected.png");
			$("#serial_select").attr("src","../images/serial_selected.png");
			$("#spit_select").attr("src","../images/spit_noselected.png");
		}
		if(handlerTypeValue=="1"){
			$("#default_selected").attr("src","../images/default_noselected.png");
			$("#serial_select").attr("src","../images/serial_noselected.png");
			$("#spit_select").attr("src","../images/spit_selected.png");
		}
    });
});

</script>
<script>
function Com_GetUrlParameter(param){
	var url = location.href;
	var re = new RegExp();
	re.compile("[\\?&]"+param+"=([^&]*)", "i");
	var arr = re.exec(url);
	if(arr==null)
		return null;
	else
		return decodeURIComponent(arr[1]);
}

function returnDialogValue(){
	if(!_validation.validate())
		return;
	returnValue = buildReturnValue();
	window.close();
}

function save(){
	if (typeof(window.$dialog) != 'undefined') {
		if(!_validation.validate())
			return;
		returnValue = buildReturnValue();
		$dialog.hide(returnValue);
	} else {
		returnDialogValue();
	}
}

function buildReturnValue(){
	var rtn = {};
	rtn["handlerIds"] = $("[name='handlerIds']").val();
	rtn["handlerNames"] = $("[name='handlerNames']").val();
	rtn["handlerType"] = $("[name='handlerType']:checked").val();
	return rtn;
}

function closeDialog(){
	if(!confirm('<bean:message key="message.closeWindow"/>')){
		return;
	}
	if (typeof(window.$dialog) != 'undefined') {
		$dialog.hide();
	}else{
		Com_CloseWindow();
	}
}
</script>
</body>
</html>