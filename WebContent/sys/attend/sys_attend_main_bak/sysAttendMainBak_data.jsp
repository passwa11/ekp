<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.util.AttendUtil,com.landray.kmss.sys.attend.model.SysAttendMainBak"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<list:data>
	<list:data-columns var="sysAttendMain" list="${queryPage.list}" varIndex="status">
		<%
			SysAttendMainBak main = (SysAttendMainBak) pageContext.getAttribute("sysAttendMain");
			if(main.getFdOffType() != null && Integer.valueOf(5).equals(main.getFdStatus())) {
				pageContext.setAttribute("fdOffTypeText", AttendUtil.getLeaveTypeText(main.getFdOffType()));
			}
		%>
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<list:data-column col="_fdStatus" title="${ lfn:message('sys-attend:sysAttendMain.fdStatus1') }">
			<c:out value="${sysAttendMain.fdStatus}"></c:out>
		</list:data-column>
		<list:data-column col="fdStatus" title="${ lfn:message('sys-attend:sysAttendMain.fdStatus1') }">
			<c:choose>
				<c:when test="${(sysAttendMain.fdStatus=='0'|| sysAttendMain.fdStatus=='2' || sysAttendMain.fdStatus=='3') && sysAttendMain.fdState==2 }">
					 ${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }
				</c:when>
				<c:when test="${sysAttendMain.fdStatus==1 && sysAttendMain.fdOutside}">
					${ lfn:message('sys-attend:sysAttendMain.outside')}
				</c:when>
				<c:otherwise>
					<sunbor:enumsShow value="${sysAttendMain.fdStatus}" enumsType="sysAttendMain_fdStatus" />
				</c:otherwise>
			</c:choose>
			<c:if test="${sysAttendMain.fdStatus=='5' && not empty sysAttendMain.fdOffType && not empty fdOffTypeText}">
				（${fdOffTypeText}）
			</c:if>
		</list:data-column>
		<list:data-column property="docCreateTime" headerClass="width120" title="${ lfn:message('sys-attend:sysAttendMain.docCreateTime1') }">
		</list:data-column>
		<list:data-column property="docAlterTime" title="${ lfn:message('sys-attend:sysAttendMain.docAlterTime') }">
		</list:data-column>
		<list:data-column property="fdDesc" title="${ lfn:message('sys-attend:sysAttendMain.fdDesc') }">
		</list:data-column>
		<list:data-column property="fdLng" title="${ lfn:message('sys-attend:sysAttendMain.fdLng') }">
		</list:data-column>
		<list:data-column property="fdLat" title="${ lfn:message('sys-attend:sysAttendMain.fdLat') }">
		</list:data-column>
		<list:data-column col="fdLocation" title="${ lfn:message('sys-attend:sysAttendMain.fdLocation1') }">
			${sysAttendMain.fdLocation}
			<c:if test="${not empty sysAttendMain.fdWifiName }">
				<c:choose>
					<c:when test="${not empty sysAttendMain.fdLocation }">
						(${sysAttendMain.fdWifiName})
					</c:when>
					<c:otherwise>
						${sysAttendMain.fdWifiName}
					</c:otherwise>
				</c:choose>
			</c:if>
		</list:data-column>
		<list:data-column col="fdCategory.fdId" title="${ lfn:message('sys-attend:sysAttendMain.fdCategory') }">
			<c:out value="${sysAttendMain.fdCategoryId}" />
		</list:data-column>
		<list:data-column col="fdCategory.fdName" title="${ lfn:message('sys-attend:sysAttendCategory.attend.fdName') }">
			${categoryMap[sysAttendMain.fdCategoryId].fdName}
		</list:data-column>
		<list:data-column col="docCreator.fdName" title="${lfn:message('sys-attend:sysAttendMain.docCreator1')  }">
			${personMap[sysAttendMain.docCreatorId].fdName}
		</list:data-column>
		<list:data-column col="fdState" title="${ lfn:message('sys-attend:sysAttendMain.fdState') }">
			<c:choose>
				<c:when test="${(sysAttendMain.fdStatus=='0'|| sysAttendMain.fdStatus=='2' || sysAttendMain.fdStatus=='3') && sysAttendMain.fdState==null }">
					${ lfn:message('sys-attend:sysAttendMain.fdState.undo') }
				</c:when>
				<c:otherwise>
					<sunbor:enumsShow value="${sysAttendMain.fdState}" enumsType="sysAttendMain_fdState" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column col="fdSignType" title="${ lfn:message('sys-attend:sysAttendMain.export.fdSignType') }" escape="false">
			<c:choose>
			    <c:when test="${sysAttendMain.fdAppName=='dingding'}">
                    ${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.mechine') }
                </c:when>
				<c:when test="${not empty sysAttendMain.fdWifiName}">
					${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.wifi') }
				</c:when>
				<c:when test="${sysAttendMain.fdAppName !='dingding' && not empty sysAttendMain.fdLocation}">
					${ lfn:message('sys-attend:sysAttendMain.export.fdSignType.map') }
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>