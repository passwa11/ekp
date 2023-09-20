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
		   		data-dojo-props="isMul:false, operateType:'change'"
				id="selectCertButton">
				<html:hidden property="fdContractId"/>
				<div id="_fdContractName" xform_type="text">
				<xform:text property="fdContractName" subject="原合同" mobile="true" required="true" showStatus="edit" />
				</div>	
			</div>
			
		</c:when>
		<c:otherwise>
		<div id="_fdContract"  valField="fdContractId" xform_type="dialog">
			<xform:dialog propertyId="fdContractId"  propertyName="fdContractName" showStatus="edit" required="true"
					className="inputsgl" style="<%=parse.getStyle()%>" 
					subject="${lfn:message('hr-ratify:hrRatifyChange.fdContract') }">
			 	 	selectContract('Change');
			</xform:dialog>
	  </div>	
		</c:otherwise>
	</c:choose>
