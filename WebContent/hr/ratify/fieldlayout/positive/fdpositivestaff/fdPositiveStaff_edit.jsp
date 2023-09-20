<%-- 转正人员 --%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.fieldlayout.SysFieldsParamsParse"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<%parse.addStyle("width", "control_width", "45%");%>
  <%-- <xform:address 
            isLoadDataDict="false"
            showStatus="edit"
            htmlElementProperties="id='fdSalaryStaff'"
            mobile="${param.mobile eq 'true'? 'true':'false'}"
			required="true" style="<%=parse.getStyle()%>"
			subject="${lfn:message('hr-ratify:hrRatifyPositive.fdPositiveStaff')}"
			propertyId="fdPositiveStaffId" propertyName="fdPositiveStaffName"
			orgType='ORG_TYPE_PERSON' className="input" onValueChange="addDeptPost">
   </xform:address> --%>
<c:set var="haveRight" value="false" />
<kmss:auth requestURL="/hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=list&personStatus=positive">
	<c:set var="haveRight" value="true" />
</kmss:auth>
   <c:choose>
		<c:when test="${param.mobile eq 'true'}">
			<c:choose>
				<c:when test="${haveRight eq 'false'}">
					<!--没有权限默认是自己-->
					<html:hidden property="fdPositiveStaffName" value="<%=UserUtil.getUser().getFdName()%>" />
					<html:hidden property="fdPositiveStaffId" value="<%=UserUtil.getUser().getFdId()%>" />
					<c:out value="<%=UserUtil.getUser().getFdName()%>"/>
				</c:when>
			<c:otherwise>
				<div id="_fdPositiveStaffId"  xform_type="select">
					<xform:select property="fdPositiveStaffId" mobile="true" showPleaseSelect="true" showStatus="edit" mul="false" htmlElementProperties="id='fdSalaryStaff'" required="true" onValueChange="addDeptPost">
						<xform:beanDataSource serviceBean="hrStaffPersonInfoService" selectBlock="fdId,fdName" whereBlock="fdOrgPerson is not null and (fdStatus='trial' or fdStatus='practice')"></xform:beanDataSource>
					</xform:select>
				</div>
			</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${haveRight eq 'false'}">
					<!--没有权限默认是自己-->
					<html:hidden property="fdPositiveStaffName" value="<%=UserUtil.getUser().getFdName()%>" />
					<html:hidden property="fdPositiveStaffId" value="<%=UserUtil.getUser().getFdId()%>" />
					<c:out value="<%=UserUtil.getUser().getFdName()%>"/>
				</c:when>
				<c:otherwise>
					<div id="_fdPositiveStaffId"  xform_type="dialog">
						<xform:dialog propertyId="fdPositiveStaffId" propertyName="fdPositiveStaffName" required="true" showStatus="edit" mulSelect="false"
							className="inputsgl" style="<%=parse.getStyle()%>"
							subject="${lfn:message('hr-ratify:hrRatifyPositive.fdPositiveStaff')}">
							selectStaffPersonInfo();
						</xform:dialog>
					</div>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
   