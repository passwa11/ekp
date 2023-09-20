<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>	
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<html:form action="/hr/ratify/hr_ratify_leave_reason/hrRatifyLeaveReason.do" > 
	<html:hidden property="method_GET"/> 
	<div id="optBarDiv">
		<c:if test="${hrRatifyLeaveReasonForm.method_GET=='edit'}">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="Com_Submit(document.hrRatifyLeaveReasonForm, 'update');">
		</c:if>
		<c:if test="${hrRatifyLeaveReasonForm.method_GET=='add'}">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="Com_Submit(document.hrRatifyLeaveReasonForm, 'save');">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="Com_Submit(document.hrRatifyLeaveReasonForm, 'saveadd');">
		</c:if>
		<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
	</div>
	<p class="txttitle">
		<c:choose>
			<c:when test="${hrRatifyLeaveReasonForm.fdType eq 'entry' }">
				<bean:message bundle="hr-ratify" key="hrRatifyLeaveReason.fdName.entry"/>
			</c:when>
			<c:otherwise>
				<bean:message bundle="hr-ratify" key="hrRatifyLeaveReason.fdName.entry"/>
			</c:otherwise>
		</c:choose>
	</p>
	<center>
		<html:hidden property="fdId"/>
		<html:hidden property="fdType"/>
		<table class="tb_normal" width=95%> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message  bundle="hr-ratify" key="hrRatifyLeaveReason.fdOrder"/>
				</td><td width=35%>
					<xform:text	property="fdOrder" style="width:90%;" />
				</td>
				<td class="td_normal_title" width=15%>
					<c:choose>
						<c:when test="${hrRatifyLeaveReasonForm.fdType eq 'entry' }">
							<bean:message bundle="hr-ratify" key="hrRatifyLeaveReason.fdName.entry"/>
						</c:when>
						<c:otherwise>
							<bean:message  bundle="hr-ratify" key="hrRatifyLeaveReason.fdName.entry"/>
						</c:otherwise>
					</c:choose>
				</td><td width=35%>
					<xform:text	property="fdName" validators="uniqueFdName" style="width:90%;" />
				</td>
			</tr>
		</table>
	</center>
</html:form>

<script>
	var _validation = $KMSSValidation(document.forms['hrRatifyLeaveReasonForm']);
	var FdNameValidators = {
		'uniqueFdName': {
			error : "放弃入职原因已使用,请重新输入!",
			test :  function(value){
					var result = fdNameCheckUnique("hrRatifyLeaveReasonService",value);
					if (!result) 
						return false;
					return true;
			}
		}
	};
	
	_validation.addValidators(FdNameValidators);
		
	
	function fdNameCheckUnique(bean,fdName) {
		var fdId = document.getElementsByName("fdId")[0].value;
		var	url=encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" + bean + "&fdName=" + fdName + "&fdId=" + fdId);
		var xmlHttpRequest;
		if (window.XMLHttpRequest) { // Non-IE browsers
			xmlHttpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) { // IE
			try {
				xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (othermicrosoft) {
				try {
					xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (failed) {
					xmlHttpRequest = false;
				}
			}
		}
		if (xmlHttpRequest) {
			xmlHttpRequest.open("GET", url, false);
			xmlHttpRequest.send();
			var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
			if (result != "") {
				return false;
			}
		}
		return true;
	}
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>