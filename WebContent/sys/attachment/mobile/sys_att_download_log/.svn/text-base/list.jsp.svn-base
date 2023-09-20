<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.sys.organization.service.ISysOrgElementService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttDownloadLog"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list}" varIndex="index">
		<!-- 文件标题 -->
		<list:data-column property="fdFileName" title="${ lfn:message('sys-attachment:sysAttDownloadLog.fdFileName') }">
		</list:data-column>
		<!-- 操作人 -->
		<list:data-column col="fdName" title="${ lfn:message('sys-attachment:sysAttDownloadLog.docCreatorName') }" escape="false">
			<c:out value="${item.docCreatorName }"></c:out>
		</list:data-column>
		<!--操作人头像-->
		<list:data-column col="fdIcon" escape="false">
			<person:headimageUrl personId="${item.docCreatorId }" size="m" />
		</list:data-column>
		<!-- 部门 -->
		<list:data-column col="fdDeptName" title="${ lfn:message('sys-attachment:sysAttDownloadLog.fdDeptName') }">
			<%
				SysAttDownloadLog log = (SysAttDownloadLog)pageContext.getAttribute("item");
				if(StringUtil.isNotNull(log.getFdDeptId())) {
					ISysOrgElementService service = (ISysOrgElementService)SpringBeanUtil.getBean("sysOrgElementService");
					SysOrgElement dept = (SysOrgElement)service.findByPrimaryKey(log.getFdDeptId());
					out.print(dept.getFdName());
				}else {
					out.print("");
				}
			%>
		</list:data-column>
		<!-- 下载时间 -->
		<list:data-column style="width:200px;" col="fdCreateTime" title="${ lfn:message('sys-attachment:sysAttDownloadLog.docCreateTime') }">
		     <kmss:showDate value="${item.docCreateTime}" type="datetime"/>
		</list:data-column>
		<!-- 客户端IP -->
		<list:data-column property="fdIp" title="${ lfn:message('sys-attachment:sysAttDownloadLog.fdIp') }">
		</list:data-column> 
		<list:data-column col="dataType">
			<c:out value="downLog"></c:out>
		</list:data-column> 
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
	
</list:data>