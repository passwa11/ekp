<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="sysAttendMainExc" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column col="fdDesc" title="${ lfn:message('sys-attend:sysAttendMainExc.fdDesc') }">
			<c:out value="${sysAttendMainExc.fdDesc}" />
		</list:data-column>
		<list:data-column property="docCreateTime" title="${ lfn:message('sys-attend:sysAttendMainExc.docCreateTime') }">
		</list:data-column>
		<list:data-column col="docCreatorName" title="打卡人员">
			<c:out value="${sysAttendMainExc.fdAttendMain.docCreator.fdName}" />
		</list:data-column>
		<list:data-column col="icon" escape="false">
			<person:headimageUrl contextPath="true" personId="${sysAttendMainExc.fdAttendMain.docCreator.fdId}" size="m" />
		</list:data-column>
		<list:data-column col="fdAttendStatus" title="打卡状态">
			${sysAttendMainExc.fdAttendMain.fdStatus}
		</list:data-column>
		<list:data-column col="fdWorkType" title="打卡状态">
			${sysAttendMainExc.fdAttendMain.fdWorkType}
		</list:data-column>
		<list:data-column col="fdAttendStatusTxt" title="打卡状态">
			<c:choose>
				<c:when test="${sysAttendMainExc.fdAttendMain.fdStatus==1 && sysAttendMainExc.fdAttendMain.fdOutside}">
					${ lfn:message('sys-attend:sysAttendMain.fdOutside') }
				</c:when>
				<c:otherwise>
					<sunbor:enumsShow value="${sysAttendMainExc.fdAttendMain.fdStatus}" enumsType="sysAttendMain_fdStatus" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>