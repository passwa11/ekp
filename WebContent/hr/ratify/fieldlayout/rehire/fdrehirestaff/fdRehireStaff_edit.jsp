<%-- 入职部门 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%");%>
<c:choose>
	<c:when test="${param.mobile eq 'true'}">
	<div id="_fdRehireStaff" valField="fdRehireStaffId" xform_type="address">
		<xform:address 
            isLoadDataDict="false"
            showStatus="edit"
             htmlElementProperties="id='fdSalaryStaff'"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="<%=parse.getStyle()%>"
			subject="${lfn:message('hr-ratify:hrRatifyRehire.fdRehireStaff')}"
			propertyId="fdRehireStaffId" propertyName="fdRehireStaffName"
			orgType='ORG_FLAG_AVAILABLENO' className="input" onValueChange="addDeptPost">
   		</xform:address>
   		 </div>
	</c:when>
	<c:otherwise>
		<html:hidden property="fdRehireStaffId"/>
   		<xform:text property="fdRehireStaffName" mobile="${param.mobile eq 'true'? 'true':'false'}" required="true" style="<%=parse.getStyle()%>" showStatus="readOnly" className="inputsgl"></xform:text>
   		<span><a href="#" onclick="javascript:selectInvalid();">${lfn:message('hr-ratify:hrRehire.choose')}</a></span>
	</c:otherwise>
</c:choose>