<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendMain,com.landray.kmss.util.UserUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="sysAttendMain" list="${queryPage.list}" varIndex="status">
		<%
			SysAttendMain main = (SysAttendMain) pageContext.getAttribute("sysAttendMain");
			boolean isManager = UserUtil.checkUserId(main.getFdCategory().getFdManager().getFdId());
			boolean isEditor = UserUtil.checkUserModels(main.getFdCategory().getAuthAllEditors());
			pageContext.setAttribute("canPatch", isManager || isEditor);
		%>
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column col="_fdStatus" title="${ lfn:message('sys-attend:sysAttendMain.fdStatus') }">
			<c:out value="${sysAttendMain.fdStatus}"></c:out>
		</list:data-column>
		<list:data-column col="fdStatus" title="${ lfn:message('sys-attend:sysAttendMain.fdStatus') }">
			<sunbor:enumsShow value="${sysAttendMain.fdStatus}" enumsType="sysAttendMain_fdStatus" />
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }">
		</list:data-column>
		<list:data-column property="docAlterTime" title="${ lfn:message('sys-attend:sysAttendMain.docAlterTime') }">
		</list:data-column>
		<list:data-column property="fdDesc" title="${ lfn:message('sys-attend:sysAttendMain.fdDesc') }">
		</list:data-column>
		<list:data-column property="fdLng" title="${ lfn:message('sys-attend:sysAttendMain.fdLng') }">
		</list:data-column>
		<list:data-column property="fdLat" title="${ lfn:message('sys-attend:sysAttendMain.fdLat') }">
		</list:data-column>
		<list:data-column property="fdLocation" title="${ lfn:message('sys-attend:sysAttendMain.fdLocation') }">
		</list:data-column>
		<list:data-column col="fdCategoryId" title="${ lfn:message('sys-attend:sysAttendMain.fdCategory') }">
			<c:out value="${sysAttendMain.fdCategory.fdId}" />
		</list:data-column>
		<list:data-column col="fdCategoryName" title="${ lfn:message('sys-attend:sysAttendCategory.fdName') }">
			<c:out value="${sysAttendMain.fdCategory.fdName}" />
		</list:data-column>
		<list:data-column col="docCreatorName" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }">
			<c:if test="${sysAttendMain.fdOutPerson==null }">
				<c:out value="${sysAttendMain.docCreator.fdName}" />
			</c:if>
			<c:if test="${sysAttendMain.fdOutPerson!=null }">
				${sysAttendMain.fdOutPerson.fdName}(${sysAttendMain.fdOutPerson.fdPhoneNum})
			</c:if>
		</list:data-column>
		<list:data-column col="docCreatorImg" title="${ lfn:message('sys-attend:sysAttendMain.docCreator') }" escape="false">
			<person:headimageUrl contextPath="true" personId="${sysAttendMain.docCreator.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="fdState" title="${ lfn:message('sys-attend:sysAttendMain.fdState') }">
			<c:if test="${not empty sysAttendMain.fdState}">
				<sunbor:enumsShow value="${sysAttendMain.fdState}" enumsType="sysAttendMain_fdState" />
			</c:if>
			<c:if test="${empty sysAttendMain.fdState}">
				${ lfn:message('sys-attend:sysAttendMain.fdState.undo') }
			</c:if>
		</list:data-column>
		<list:data-column property="fdOutTarget" title="" escape="false">
		</list:data-column>
		<list:data-column col="fdOutPersonId" title="" escape="false">
			${sysAttendMain.fdOutPerson.fdId}
		</list:data-column>
		<list:data-column col="fdOutPersonName" title="" escape="false">
			${sysAttendMain.fdOutPerson.fdName}
		</list:data-column>
		<list:data-column col="fdOutPersonPhoneNum" title="" escape="false">
			${sysAttendMain.fdOutPerson.fdPhoneNum}
		</list:data-column>
		<list:data-column col="fdPersonType" title="" escape="false">
			<c:choose>
				<c:when test="${sysAttendMain.fdOutPerson!=null }">
					${ lfn:message('sys-attend:sysAttendMain.fdPersonType.outer') }
				</c:when>
				<c:when test="${sysAttendMain.fdOutTarget }">
					${ lfn:message('sys-attend:sysAttendMain.fdPersonType.inner.out') }
				</c:when>
				<c:otherwise>
					${ lfn:message('sys-attend:sysAttendMain.fdPersonType.inner') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdIsPatch" title="" escape="false">
			<c:if test="${sysAttendMain.fdSignPatch!=null}">
				${ lfn:message('sys-attend:sysAttendSignPatch.addPatch') }
			</c:if>
		</list:data-column>
		<list:data-column col="fdPatchPerson" title="" escape="false">
			${sysAttendMain.fdSignPatch.fdPatchPerson.fdName}
		</list:data-column>
		<list:data-column col="fdPatchTime" title="" escape="false">
			${sysAttendMain.fdSignPatch.fdPatchTime}
		</list:data-column>
		<list:data-column col="canPatch" title="" escape="false">
			${canPatch}
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>