<!-- 原合同 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
    <%
		parse.addStyle("width", "control_width", "95%");
		required = Boolean.parseBoolean(parse.getParamValue("control_required", "true"));
	%>
	
	<c:choose>
		<c:when test="${param.mobile eq 'true'}">
			<div 
		   		data-dojo-type="hr/ratify/mobile/resource/js/contractSelectorButton"
		   		data-dojo-mixins="hr/ratify/mobile/resource/js/contractSelectorMixin"
		   		data-dojo-props="isMul:false, operateType:'remove'"
				id="selectCertButton">
				<html:hidden property="fdRemoveContractId"/>
				<xform:text property="fdRemoveContractName" subject="原合同" mobile="true" required="true" showStatus="edit" />
			</div>
		</c:when>
		<c:otherwise>
			<xform:dialog propertyId="fdRemoveContractId" propertyName="fdRemoveContractName" showStatus="edit" required="true"
					className="inputsgl" style="<%=parse.getStyle()%>" 
					subject="${lfn:message('hr-ratify:hrRatifyRemove.fdRemoveContract') }">
			 	 	selectContract('Remove');
			</xform:dialog>
		</c:otherwise>
	</c:choose>