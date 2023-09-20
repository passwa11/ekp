<%-- 入职部门 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%");%>
<div id="_fdRehireDept"  valField="fdRehireDeptId"  xform_type="address">
  <xform:address 
            isLoadDataDict="false"
            showStatus="edit"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="<%=parse.getStyle()%>"
			subject="${lfn:message('hr-ratify:hrRatifyRehire.fdRehireDept')}"
			propertyId="fdRehireDeptId" propertyName="fdRehireDeptName"
			orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT' className="input" htmlElementProperties="id='fdRehireDept'">
   </xform:address>
    </div>	
   