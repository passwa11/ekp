<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService,com.landray.kmss.sys.organization.model.SysOrgElement" %>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendSynDing,com.landray.kmss.sys.attend.model.SysAttendCategory" %>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService" %>
<%@ page import="com.landray.kmss.sys.attend.util.CategoryUtil" %>
<%
	ISysOrgCoreService sysOrgCoreService=(ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService");
	ISysAttendCategoryService sysAttendCategoryService=(ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
%>
<list:data>
	<list:data-columns list="${queryPage.list }" var="sysAttendSynDing"
		varIndex="status">
		<%
				SysAttendSynDing main = (SysAttendSynDing) pageContext.getAttribute("sysAttendSynDing");
				SysOrgElement fdPerson = sysOrgCoreService.findByPrimaryKey(main.getFdPersonId());
				pageContext.setAttribute("fdPerson", fdPerson);
				if(main !=null) {
					SysAttendCategory category = CategoryUtil.getCategoryById(main.getFdGroupId());
					pageContext.setAttribute("categoryName", category == null ? "" : category.getFdName());
				}
			%>
		<list:data-column property="fdId"></list:data-column>
		<list:data-column col="index">${status+1}</list:data-column>
		<list:data-column col="fdPersonId" title="${lfn:message('sys-attend:sysAttendMain.docCreator') }">
			<c:out value="${fdPerson.getFdName() }"></c:out>
		</list:data-column>
		<list:data-column col="fdPersonId" title="${lfn:message('sys-attend:sysAttendMain.docCreatorDept') }">
			<c:out value="${fdPerson.getFdParent().getFdName() }"></c:out>
		</list:data-column>
		<list:data-column col="fdBaseCheckTime" title="${lfn:message('sys-attend:sysAttendMain.export.shouldTime.attend') }">
			<kmss:showDate value="${sysAttendSynDing.fdBaseCheckTime }"
				type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdUserCheckTime" title="${lfn:message('sys-attend:sysAttendMain.export.signTime') }">
			<kmss:showDate value="${sysAttendSynDing.fdUserCheckTime }"
				type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdSourceType" title="${lfn:message('sys-attend:sysAttendMain.export.fdSignType') }">
			<sunbor:enumsShow value="${sysAttendSynDing.fdSourceType}" enumsType="sysAttendMain_fdSourceType" />
		</list:data-column>
		<list:data-column col="fdAppName" title="${lfn:message('sys-attend:sysAttendMain.export.fdAppName') }">
			<c:choose>
				<c:when test="${sysAttendSynDing.fdAppName=='dingding' }">
					${ lfn:message('sys-attend:sysAttendMain.fdAppName.dingDing') }
				</c:when>
				<c:when test="${sysAttendSynDing.fdAppName=='qywx' }">
					${ lfn:message('sys-attend:sysAttendMain.fdAppName.qywx') }
				</c:when>
				<c:when test="${empty sysAttendSynDing.fdAppName }">
					${ lfn:message('sys-attend:sysAttendMain.fdAppName.ekp') }
				</c:when>
				<c:otherwise>
					${sysAttendSynDing.fdAppName}
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdUserAddress" title="${lfn:message('sys-attend:sysAttendMain.export.fdLocation') }">
			<c:out value="${sysAttendSynDing.fdUserAddress }"></c:out>
		</list:data-column>
		<list:data-column col="fdGroupId" title="${lfn:message('sys-attend:sysAttendCategory.attend') }">
			<c:out value="${categoryName }"></c:out>
		</list:data-column>
		<list:data-column col="fdTimeResult" title="${lfn:message('sys-attend:sysAttendMain.export.fdStatus') }">
			<c:choose>
				<c:when test="${not empty sysAttendSynDing.fdInvalidRecordType}">${lfn:message('sys-attend:sysAttendMain.fdStatus.invalid') }</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${sysAttendSynDing.fdTimeResult=='Late' || sysAttendSynDing.fdTimeResult=='SeriousLate'  || sysAttendSynDing.fdTimeResult=='Absenteeism' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.late') }</c:when>
						<c:when test="${sysAttendSynDing.fdTimeResult =='Early' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.left') }</c:when>
						<c:when test="${sysAttendSynDing.fdTimeResult =='Normal' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }</c:when>
						<c:when test="${sysAttendSynDing.fdTimeResult =='NotSigned' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }</c:when>
						<c:when test="${sysAttendSynDing.fdTimeResult =='Trip' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.business') }</c:when>
						<c:when test="${sysAttendSynDing.fdTimeResult =='Leave' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }</c:when>
						<c:when test="${sysAttendSynDing.fdTimeResult =='Outgoing' }">${lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }</c:when>
						<c:otherwise></c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<c:if test="${sysAttendSynDing.fdLocationResult=='Outside'}">
				(${lfn:message('sys-attend:sysAttendMain.outside') })
			</c:if>
		</list:data-column>
		<list:data-column col="docCreateTime" title="${lfn:message('sys-attend:sysAttendSynDing.docCreateTime') }">
			<kmss:showDate value="${sysAttendSynDing.docCreateTime }"
				type="datetime"></kmss:showDate>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}" />
</list:data>