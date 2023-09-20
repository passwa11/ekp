<%-- 收文日期 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%
	parse.addStyle("width", "control_width", "45%");
%>
<c:choose>
	<c:when test="${param.mobile eq 'false'}">
		<div id="_fdSignBeginDate"  xform_type="datetime">
			<xform:datetime property="fdSignBeginDate" mobile="${param.mobile eq 'true'? 'true':'false'}"
							required="true"
							showStatus="edit"
							dateTimeType="date"
							onValueChange="onBegindateChange"
							subject="${lfn:message('hr-ratify:hrRatifySign.fdSignBeginDate')}"
							style="<%=parse.getStyle()%>" />
		</div>
		<script type="text/javascript">
			var validation = $KMSSValidation();
			window.onBegindateChange = function(value,element){
				validation.validateElement(element);
				var enddate = $('input[name="fdSignEndDate"]')[0];
				if(element.name=='fdSignBeginDate' && enddate){
					validation.validateElement(enddate);
				}
			};
		</script>
	</c:when>
	<c:otherwise>
		<div id="_fdSignBeginDate"  xform_type="datetime">
			<xform:datetime property="fdSignBeginDate" mobile="${param.mobile eq 'true'? 'true':'false'}"
							required="true"
							showStatus="edit"
							dateTimeType="date"
							subject="${lfn:message('hr-ratify:hrRatifySign.fdSignBeginDate')}"
							style="<%=parse.getStyle()%>" />
		</div>
	</c:otherwise>
</c:choose>