<%-- 入职部门 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%");%>
<div id="_fdSignStaff" valField="fdSignStaffId" xform_type="address">
  <xform:address 
            isLoadDataDict="false"
            showStatus="edit"
            htmlElementProperties="id='fdSalaryStaff'"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="<%=parse.getStyle()%>"
			subject="${lfn:message('hr-ratify:hrRatifySign.fdSignStaff')}"
			propertyId="fdSignStaffId" propertyName="fdSignStaffName"
			orgType='ORG_TYPE_PERSON' className="input" onValueChange="addDeptPost">
   </xform:address>
      </div>
   