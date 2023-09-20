<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesDetails"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesDetails" list="${queryPage.list}" varIndex="status" custom="false">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

		<!-- 借阅人 -->
		<list:data-column col="fdBorrower.fdName" title="${lfn:message('km-archives:kmArchivesDetails.fdBorrower') }">
			<c:out value="${kmArchivesDetails.fdBorrower.fdName }"></c:out>
		</list:data-column>
		<!-- 权限 -->
		<list:data-column col="fdAuthorityRange" title="${lfn:message('km-archives:kmArchivesDetails.fdAuthorityRange') }" escape="false">
			<xform:checkbox property="" value="${kmArchivesDetails.fdAuthorityRange }" showStatus="view">
				<xform:simpleDataSource value="copy">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.copy') }</xform:simpleDataSource>
				<xform:simpleDataSource value="download">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.download') }</xform:simpleDataSource>
				<xform:simpleDataSource value="print">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.print') }</xform:simpleDataSource>
			</xform:checkbox>
		</list:data-column>
		<!-- 状态 -->
		<list:data-column col="fdStatus" title="${lfn:message('km-archives:kmArchivesDetails.fdStatus') }">
			<c:choose>
				<c:when test="${kmArchivesDetails.fdStatus eq '1' }">
					<c:out value="${lfn:message('km-archives:enums.borrow_status.1') }"/>
				</c:when>
				<c:when test="${kmArchivesDetails.fdStatus eq '2' }">
					<c:out value="${lfn:message('km-archives:enums.borrow_status.2') }"/>
				</c:when>
				<c:otherwise>
					<c:out value="${lfn:message('km-archives:enums.borrow_status.3') }"/>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 归还时间 -->
		<list:data-column col="fdReturnDate" title="${lfn:message('km-archives:kmArchivesDetails.fdReturnDate') }">
			<c:choose>
				<c:when test="${not empty kmArchivesDetails.fdRenewReturnDate }">
					<kmss:showDate value="${kmArchivesDetails.fdRenewReturnDate }" type="datetime"></kmss:showDate>
				</c:when>
				<c:otherwise>
					<kmss:showDate value="${kmArchivesDetails.fdReturnDate }" type="datetime"></kmss:showDate>
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<!-- 剩余时间 -->
		<list:data-column col="fdRemindDate" title="${lfn:message('km-archives:kmArchivesDetails.remainDate')}" escape="false">
			<font color="red">
			<%
				Date oldFdReturnDate = ((KmArchivesDetails)pageContext.getAttribute("kmArchivesDetails")).getFdReturnDate();
				Date newFdReturnDate = ((KmArchivesDetails)pageContext.getAttribute("kmArchivesDetails")).getFdRenewReturnDate();
				Date fdReturnDate = newFdReturnDate==null?oldFdReturnDate:newFdReturnDate;
				long between = fdReturnDate.getTime() - new Date().getTime();
				String expiredMsg = ResourceUtil.getString("km-archives:kmArchivesDetails.expired");
				String dayMsg = ResourceUtil.getString("km-archives:kmArchivesDetails.day");
				String hourMsg = ResourceUtil.getString("km-archives:kmArchivesDetails.hour");
				if(between <= 0) {
					out.print(expiredMsg);
				}else {
					int remain = (int)(between/1000/60/60);
					if(remain > 24) {
						int day = remain/24;
						int hour = remain % 24;
						out.print(day+dayMsg+hour+hourMsg);
					}else {
						out.print(remain+hourMsg);
					}
				}
			%>
			</font>
		</list:data-column>
      
    </list:data-columns>
    <%-- 分页信息生成 --%>
        <list:data-paging page="${queryPage}" />
</list:data>
