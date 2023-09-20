<%@page import="com.landray.kmss.sys.lbpmservice.taglib.MultiLangTextGroupTag"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmUsageContent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|xform.js");
Com_IncludeFile("data.js",Com_Parameter.ContextPath	+ "resource/js/","js",true);
Com_IncludeFile("xml.js",Com_Parameter.ContextPath	+ "resource/js/","js",true);
</script>
<script type="text/javascript">
	function submitUpdateForm(opType) {
		seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
			/* var fdUsageContent= $("[name='fdUsageContent']");
			if(!fdUsageContent.val()){
				dialog.alert("${lfn:message('sys-lbpmperson:lbpmperson.batchreview.usageNotNull')}");
				return;
			} */
			$("input[name='oprGroup']").val(opType);
			//document.updateForm.submit();
			Com_Submit(document.updateForm);
		});
	}
	function person_initialCommonUsages() {
		var url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=getUsagesInfo';
		var kmssData = new KMSSData();
		kmssData.SendToUrl(url,
				function(http_request) {
					var responseText = http_request.responseText;
					var names = responseText ? decodeURIComponent(responseText) : null;
					var usageContents;
					if (names != null && names != "") {
						usageContents = names.split("\n");
					}
					person_initialCommonUsageObj("commonUsages",
							usageContents);
		});
	}
	function person_initialCommonUsageObj(commonUsageObjName, usageContents) {
		var commonUsageObj = document.getElementsByName(commonUsageObjName);
		if (commonUsageObj == null || commonUsageObj.length == 0) {
			return;
		}
		commonUsageObj = commonUsageObj[0];
		while (commonUsageObj.childNodes.length > 0) {
			commonUsageObj.removeChild(commonUsageObj.childNodes[0]);
		}
		var option = document.createElement("option");
		option.appendChild(document
				.createTextNode('<bean:message key="page.firstOption" />'));
		option.value = "";
		commonUsageObj.appendChild(option);
		if (usageContents != null) {
			option = null;
			for ( var i = 0; i < usageContents.length; i++) {
				option = document.createElement("option");
				var usageContent = usageContents[i];
				while (usageContent.indexOf("nbsp;") != -1) {
					usageContent = usageContent.replace("&nbsp;", " ");
				}
				var optTextLength = 30;
				var optText = usageContent.length > optTextLength ? usageContent
						.substr(0, optTextLength) + '...'
						: usageContent;
				option.appendChild(document.createTextNode(optText));
				option.value = usageContent;
				commonUsageObj.appendChild(option);
			}
		}
	}
	function person_setUsages(commonUsagesObj) {
		if (commonUsagesObj.selectedIndex > 0) {
			var fdUsageContent = document.getElementsByName("fdUsageContent")[0];
			fdUsageContent.value = commonUsagesObj.options[commonUsagesObj.selectedIndex].value;
		}
	};
	function close_window(){
		//关闭窗口不执行回调
		var parent = window;
		while (parent) {
			if (typeof(parent.$dialog) != 'undefined') {
				setTimeout(function() {
					parent.$dialog.content.hide();
					parent.$dialog.overlay.hide();
				}, 1);
			
				return;
			}
			if (parent == parent.parent)
				break;
			parent = parent.parent;
		}
	}
</script>
<style>
textarea::-webkit-input-placeholder {
	font-size: 14px;
}
</style>
<form name="updateForm" action="${LUI_ContextPath }/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=approveAll" method="post">
<input type="hidden" name="processIds" value="${param.processIds }"/>
<input type="hidden" name="oprGroup"/>
<table style="width:92%;margin-left:30px;font-size:14px">
	<tr>
		<td>
			<span style="font-size:14px;font-weight:bold;">${lfn:message('sys-lbpmperson:lbpmperson.batchreview.usage')}</span>&nbsp;&nbsp; 
			<select name="commonUsages" onchange="person_setUsages(this)" style="width:120px;overflow-x:hidden;font-size:14px;">
				<option value=""></option>
			</select>
			<br><br>
			<%
				LbpmUsageContent lbpmUsageContent = new LbpmUsageContent();
				String defaultNote=MultiLangTextGroupTag.getValueByUserLang(
					lbpmUsageContent.getDefaultUsageContent4Lang(),
					lbpmUsageContent.getDefaultUsageContent());
				if(StringUtil.isNull(defaultNote)){
					defaultNote="";
				}
			%>
			<textarea name="fdUsageContent" subject="${lfn:message('sys-lbpmperson:lbpmperson.batchreview.audit')}" validate="required" required="required" style="width:97%;height:140px;display:inline-block;font-size:14px;" placeholder="${lfn:message('sys-lbpmperson:lbpmperson.batchreview.opinion')}"><%=defaultNote%></textarea>
			<span class="txtstrong" style="display:inline-block;vertical-align:top;">*</span>
		</td>
	</tr>
</table>
<div style="top:233px;position:absolute;left:35%;">
	<input type="button" style="border-radius:4px;font-size:14px;" class="lui_form_button" value="${lfn:message('sys-lbpmperson:lbpmperson.batchreview.agreeall')}" onclick="submitUpdateForm('batchpass');"/>
	<input type="button" style="border-radius:4px;margin-left:20px;font-size:14px;" class="lui_form_button" value="${lfn:message('sys-lbpmperson:lbpmperson.batchreview.refuseorback')}" onclick="submitUpdateForm('batchrefuse');"/>
</div>
</form>
<script>
$KMSSValidation();
$(function(){
	person_initialCommonUsages();
});
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>