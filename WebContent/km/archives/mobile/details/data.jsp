<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.landray.kmss.km.archives.model.KmArchivesConfig"%>
<%@ page import="com.landray.kmss.km.archives.model.KmArchivesDetails"%>
<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>

<list:data>
	<list:data-columns var="kmArchivesDetails" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
	    <%-- 主题--%>	
		<list:data-column col="label" title="${ lfn:message('km-archives:kmArchivesMain.docSubject') }" escape="false">
		    <c:out value="${kmArchivesDetails.fdArchives.docSubject }"/>
		</list:data-column>
		<%--状态--%>
		<list:data-column col="status" title="${ lfn:message('km-archives:kmArchivesDetails.fdStatus') }" escape="false">
			<c:choose>
				<c:when test="${kmArchivesDetails.fdStatus=='0'}">
					0
				</c:when>
				<c:when test="${kmArchivesDetails.fdStatus=='1'}">
					1
				</c:when>
				<c:when test="${kmArchivesDetails.fdStatus=='2'}">
					2
				</c:when>
				<c:when test="${kmArchivesDetails.fdStatus=='3'}">
					3
				</c:when>
			</c:choose>
		</list:data-column>
		<%-- 剩余时间 --%>
		<list:data-column col="creator" title="${ lfn:message('km-archives:kmArchivesDetails.remainDate') }" escape="false">
	    	<style>
	    		.muiComplexrCreator{
		    		min-width: 48px;
		    		float: none;
				    padding-right: 1rem;
				    overflow: hidden;
				    text-overflow: ellipsis;
				    width: 40px;
	    		}
	    	</style>
	    	<%
				Date oldFdReturnDate = ((KmArchivesDetails)pageContext.getAttribute("kmArchivesDetails")).getFdReturnDate();
				Date newFdReturnDate = ((KmArchivesDetails)pageContext.getAttribute("kmArchivesDetails")).getFdRenewReturnDate();
				Date fdReturnDate = newFdReturnDate==null?oldFdReturnDate:newFdReturnDate;
				Date nowDate = new Date();
				long between = fdReturnDate.getTime() - nowDate.getTime();
				String expiredMsg = ResourceUtil.getString("km-archives:kmArchivesDetails.expired");
				String dayMsg = ResourceUtil.getString("km-archives:kmArchivesDetails.day");
				String hourMsg = ResourceUtil.getString("km-archives:kmArchivesDetails.hour");
				String remainDateMsg = ResourceUtil.getString("km-archives:kmArchivesDetails.remainDate");
				String validityDateMsg = ResourceUtil.getString("km-archives:kmArchivesMain.fdValidityDate");
				String foreverStr = ResourceUtil.getString("km-archives:kmArchivesMain.fdValidityDate.forever");
				String fdStatus = ((KmArchivesDetails)pageContext.getAttribute("kmArchivesDetails")).getFdStatus();
				StringBuffer sb = new StringBuffer();
				boolean isRedShow = false;
				String notifyStr = new KmArchivesConfig().getFdEarlyReturnDate();
				if(between > 0 && StringUtil.isNotNull(notifyStr)) {
					sb.append(remainDateMsg+" ");
					int notify = Integer.parseInt(notifyStr)*24;
					int remain = (int)(between/1000/60/60);
					if(remain<=notify)
						isRedShow = true;
					if(remain > 24) {
						int day = remain/24;
						int hour = remain % 24;
						sb.append(day+dayMsg+hour+hourMsg);
					}else {
						sb.append(remain+hourMsg);
					}
				}else{
					sb.append(validityDateMsg+" ");
					Date fdValidityDate = ((KmArchivesDetails)pageContext.getAttribute("kmArchivesDetails")).getFdArchives().getFdValidityDate();
					if(fdValidityDate != null){
						if(nowDate.getTime()>fdValidityDate.getTime()){
							sb.append(expiredMsg);
						}else{
							String fdValidityDateStr = DateUtil.convertDateToString(fdValidityDate, DateUtil.TYPE_DATE, request.getLocale());
							sb.append(fdValidityDateStr);
						}
					}else{
						sb.append(foreverStr);
					}
				}
				pageContext.setAttribute("isRedShow", isRedShow);
				pageContext.setAttribute("sb", sb.toString());
			%>
			<c:choose>
				<c:when test="${isRedShow}">
					<font color="red">${sb}</font>
				</c:when>
				<c:otherwise>
					${sb}
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
			/km/archives/km_archives_main/kmArchivesMain.do?method=view&fdId=${kmArchivesDetails.fdArchives.fdId}
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>