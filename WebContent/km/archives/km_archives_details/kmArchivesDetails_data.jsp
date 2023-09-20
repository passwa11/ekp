<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.km.archives.model.KmArchivesDetails"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="kmArchivesDetails" list="${queryPage.list}" varIndex="status">
        <list:data-column property="fdId" />
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdArchives.docSubject" escape="false" title="${lfn:message('km-archives:kmArchivesMain.docSubject')}">
        	<span class="com_subject"><c:out value="${kmArchivesDetails.fdArchives.docSubject }"/></span>
        </list:data-column>
        <list:data-column property="fdArchives.docNumber" title="${lfn:message('km-archives:kmArchivesMain.docNumber')}" />
        <list:data-column property="fdArchives.fdDenseLevel" title="${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}" />
        <list:data-column col="fdReturnDate" title="${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}">
			<kmss:showDate value="${ kmArchivesDetails.fdReturnDate }" type="datetime"></kmss:showDate>
		</list:data-column>
		<list:data-column col="fdRenewReturnDate" title="${lfn:message('km-archives:kmArchivesDetails.fdRenewReturnDate')}">
			<kmss:showDate value="${kmArchivesDetails.fdRenewReturnDate }" type="datetime"></kmss:showDate>
		</list:data-column>
		<!-- 授权范围 -->
		<list:data-column col="fdAuthorityRange" title="${lfn:message('km-archives:kmArchivesDetails.fdAuthorityRange')}">
			<%
				String fdRange = ((KmArchivesDetails)pageContext.getAttribute("kmArchivesDetails")).getFdAuthorityRange();
				String result = "";
				if(StringUtil.isNotNull(fdRange)) {
					if(fdRange.contains("copy")) {
						result += ResourceUtil.getString("kmArchivesConfig.fdDefaultRange.copy", "km-archives")+";";
					}
					if(fdRange.contains("download")) {
						result += ResourceUtil.getString("kmArchivesConfig.fdDefaultRange.download", "km-archives")+";";
					}
					if(fdRange.contains("print")) {
						result += ResourceUtil.getString("kmArchivesConfig.fdDefaultRange.print", "km-archives")+";";
					}
					result = result.substring(0, result.length()-1);
				}
				out.print(result);
			%>
		</list:data-column>
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
