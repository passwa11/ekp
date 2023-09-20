<%-- 调出部门--%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%
parse.addStyle("width", "control_width", "45%");
request.setAttribute("style", parse.getStyle());
%>
<div id="_fdTransferLeaveDept" valField="fdTransferLeaveDeptId" xform_type="address">
  <%-- <xform:address 
            isLoadDataDict="false"
            showStatus="readOnly"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="<%=parse.getStyle()%>"
			subject="${lfn:message('hr-ratify:hrRatifyTransfer.fdTransferLeaveDept')}"
			propertyId="fdTransferLeaveDeptId" propertyName="fdTransferLeaveDeptName"
			orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT' className="input" htmlElementProperties="id='fdTransferLeaveDept'">
   </xform:address> --%>
   <xform:text
            isLoadDataDict="false"
            showStatus="readOnly"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="border-bottom:1px solid #d5d5d5;${style }"
			subject="${lfn:message('hr-ratify:hrRatifyTransfer.fdTransferLeaveDept')}"
			property="fdTransferLeaveDeptName" 
			className="input inputsgl" htmlElementProperties="id='fdTransferLeaveDept' placeholder='选择调岗人员后自动获取'">
   </xform:text>
   <xform:text
            isLoadDataDict="false"
            showStatus="noShow"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="display:none;${style }"
			property="fdTransferLeaveDeptId" 
			className="input inputsgl" 
			htmlElementProperties="id='fdTransferLeaveDeptId'">
   </xform:text>
   </div>
   