<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%><list:data>
	<list:data-columns var="sysAuditlog" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="fdSubject" title="${lfn:message('sys-auditlog:sysAuditlog.fdSubject')}" style="text-align:left;min-width:180px">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdOperator" title="${lfn:message('sys-auditlog:sysAuditlog.fdOperator')}">
		</list:data-column>
		<list:data-column headerClass="width100" property="fdIp" title="${lfn:message('sys-auditlog:sysAuditlog.fdIp')}">
		</list:data-column>
		<list:data-column headerClass="width120" col="fdModelName" title="${lfn:message('sys-auditlog:sysAuditlog.fdModelName')}">
			<c:set var="_fdModelName" value="${sysAuditlog.fdModelName}"/>	
			<%
				String modelName = (String)pageContext.getAttribute("_fdModelName");
				SysDictModel model = SysDataDict.getInstance().getModel(modelName);
				if(model!=null){
					modelName=model.getMessageKey();
					if(StringUtil.isNotNull(modelName))
						pageContext.setAttribute("_fdModelName",ResourceUtil.getString(modelName));
				}
			%>
			${_fdModelName}
		</list:data-column>
		<list:data-column headerClass="width80" col="fdOptType" title="${lfn:message('sys-auditlog:sysAuditlog.fdOptType')}">
			<sunbor:enumsShow value="${sysAuditlog.fdOptType}"	
		   		bundle="sys-auditlog" enumsType="sysAuditlog_fdOptType"/>
		</list:data-column>
		<list:data-column headerClass="width120" col="fdCreateTime" title="${lfn:message('sys-auditlog:sysAuditlog.fdCreateTime')}">
		    <kmss:showDate value="${sysAuditlog.fdCreateTime}" type="datetime"/>
		</list:data-column>
		<list:data-column col="fdBackUpKey">
			<c:out value="${sysAuditlog.fdBackUpKey}"/>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>