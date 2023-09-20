<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysAttendMainExc" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column col="fdStatus" title="${ lfn:message('sys-attend:sysAttendMainExc.fdStatus') }">
			<sunbor:enumsShow value="${sysAttendMainExc.fdStatus}" enumsType="sysAttendMainExc_fdStatus" />
		</list:data-column>
		<list:data-column col="docStatus" title="${ lfn:message('sys-attend:sysAttendMainExc.docStatus') }">
			<sunbor:enumsShow value="${sysAttendMainExc.docStatus}" enumsType="sysAttendMainExc_docStatus" />
		</list:data-column>
		<list:data-column property="fdDesc" title="${ lfn:message('sys-attend:sysAttendMainExc.fdDesc') }">
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime') }">
		</list:data-column>
		<list:data-column property="docHandleTime" title="${ lfn:message('sys-attend:sysAttendMainExc.docHandleTime') }">
		</list:data-column>
		<list:data-column col="fdAttendMain.fdId" title="${ lfn:message('sys-attend:sysAttendMainExc.fdAttendMain') }">
			<c:out value="${sysAttendMainExc.fdAttendMain.fdId}" />
		</list:data-column>
		<list:data-column col="fdHandler.fdName" title="${ lfn:message('sys-attend:sysAttendMainExc.fdHandler') }">
			<c:out value="${sysAttendMainExc.fdHandler.fdName}" />
		</list:data-column>
		<list:data-column col="fdAttendMain.docCreatorName" title="${ lfn:message('sys-attend:sysAttendMain.docCreator1') }">
			<c:out value="${sysAttendMainExc.fdAttendMain.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="fdAttendMain.docCreateTime" title="${ lfn:message('sys-attend:sysAttendMain.docCreateTime1') }">
			<kmss:showDate value="${sysAttendMainExc.fdAttendMain.docCreateTime}" type="datetime" ></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdAttendMain.fdLocation" title="${ lfn:message('sys-attend:sysAttendMain.fdLocation1') }">
			<c:out value="${sysAttendMainExc.fdAttendMain.fdLocation}" />
		</list:data-column>
		<list:data-column col="fdAttendMain.categoryName" title="${ lfn:message('sys-attend:sysAttendCategory.attend') }"> 
			<c:choose>
				<c:when test="${sysAttendMainExc.fdAttendMain.fdCategory !=null}">
					<c:out value="${sysAttendMainExc.fdAttendMain.fdCategory.fdName}" />
				</c:when>
				<c:otherwise>
					<c:out value="${sysAttendMainExc.fdAttendMain.fdHisCategory.fdName}" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdAttendMain.fdStatus" title="${ lfn:message('sys-attend:sysAttendMain.fdStatus1') }">
			<c:if test="${sysAttendMainExc.fdAttendMain.fdState==2 }">
				   ${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }
			</c:if>
			<c:if test="${sysAttendMainExc.fdAttendMain.fdState!=2 }">
				<sunbor:enumsShow value="${sysAttendMainExc.fdAttendMain.fdStatus}" enumsType="sysAttendMain_fdStatus" />
			</c:if>
			
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>