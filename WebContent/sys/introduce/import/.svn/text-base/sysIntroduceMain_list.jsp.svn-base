<%@page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/sunbor.tld" prefix="sunbor"%>
<%@ include file="/resource/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="sysIntroduceMain" list="${queryPage.list}">
		<list:data-column property="fdIntroducer.fdName" title="${ lfn:message('sys-introduce:sysIntroduceMain.fdIntroducer') }">
		</list:data-column>
		<list:data-column property="fdIntroducer.fdId"></list:data-column>
		<list:data-column property="fdId"></list:data-column>
		<list:data-column property="fdCancelFlag"></list:data-column>
		<list:data-column col="currentUserFdId">
			<%
				out.print(UserUtil.getKMSSUser().getUserId());
			%>
		</list:data-column>
		<list:data-column property="fdIntroduceToPerson"></list:data-column>
		<list:data-column property="fdIntroduceToEssence"></list:data-column>
		<list:data-column property="fdIntroduceTime" title="${ lfn:message('sys-introduce:sysIntroduceMain.fdIntroduceTime') }">
		</list:data-column>
		<list:data-column col="introduceType" title="${ lfn:message('sys-introduce:sysIntroduceMain.introduce.type') }">
			<c:if test="${sysIntroduceMain.fdIntroduceToEssence&&(sysIntroduceMain.docStatus eq null or sysIntroduceMain.docStatus==30)}">
				<bean:message key="sysIntroduceMain.introduce.show.type.essence" bundle="sys-introduce" />
			</c:if>
			<c:if test="${sysIntroduceMain.fdIntroduceToNews}">
				<bean:message key="sysIntroduceMain.introduce.show.type.news" bundle="sys-introduce" />
			</c:if>
			<c:if test="${sysIntroduceMain.fdIntroduceToPerson}">
			</c:if>
		</list:data-column>
		<list:data-column property="introduceGoalNames" title="${ lfn:message('sys-introduce:sysIntroduceMain.fdIntroduceTo') }">
		</list:data-column>
		<list:data-column property="fdIntroduceGrade" title="${ lfn:message('sys-introduce:sysIntroduceMain.fdIntroduceGrade') }">
		</list:data-column>
		<list:data-column col="fdIntroduceReason" escape="false" title="${ lfn:message('sys-introduce:sysIntroduceMain.fdIntroduceReason') }">
			<c:out value="${ sysIntroduceMain.fdIntroduceReason}"></c:out>
		</list:data-column>
		
		
		<list:data-column col="showCancelFlag" escape="false">
			<c:choose>
				<c:when test="${sysIntroduceMain.fdCancelFlag eq 1 }">
					<span><bean:message key="sysIntroduceMain.cancel.already" bundle="sys-introduce" /></span>
				</c:when>
				<c:otherwise>
					<kmss:auth
							requestURL="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=personCanserIntro&fdModelName=${sysIntroduceMain.fdModelName}&fdModelId=${sysIntroduceMain.fdModelId }&introduceFdId=${sysIntroduceMain.fdId}">
							<span id="${sysIntroduceMain.fdId}" is-essence="${sysIntroduceMain.fdIntroduceToEssence}"  is-person="${sysIntroduceMain.fdIntroduceToPerson}"   class="canser_introduce" onclick="intr_opt.canserPersonIntroduce(this)">
								<bean:message key="sysIntroduceMain.cancel.button" bundle="sys-introduce" />
							</span>
					</kmss:auth>
				</c:otherwise>
			</c:choose>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>
	
</list:data>