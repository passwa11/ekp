<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.news.model.SysNewsMain"%>
<%@page import="com.landray.kmss.sys.news.util.SysNewsUtils"%>

<%
	String prefix = request.getContextPath();
%>

<list:data>
	<list:data-columns var="sysNewsMain" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<!-- 序号 -->	
		<list:data-column col="itemIndex" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false">
		    <c:out value="${status}"/>
		</list:data-column>
	    <!-- 主题-->	
		<list:data-column col="label" title="${ lfn:message('sys-news:sysNewsMain.docSubject') }" escape="false">
		    <c:out value="${sysNewsMain.docSubject}"/>
		</list:data-column>
		<list:data-column col="status" title="${ lfn:message('sys-news:sysNewsMain.mobile.top') }" escape="false">
			<c:if test="${sysNewsMain.fdIsTop==true}">
				<span class="muiComplexrStatusBorder"><c:out value="${ lfn:message('sys-news:sysNewsMain.mobile.top') }"/></span>
			</c:if>
		</list:data-column>
		<list:data-column col="creator"  escape="false" title="${ lfn:message('sys-news:sysNewsMain.publisher')}">
	           <c:if test="${sysNewsMain.fdAuthor==null}">
	                 <c:out value="${sysNewsMain.fdWriter}"/>
	           </c:if>
	            <c:if test="${sysNewsMain.fdAuthor!=null}">
	                <c:out value="${sysNewsMain.fdAuthor.fdName}"/>
	           </c:if>
		</list:data-column>
		 <!-- 创建时间-->
	 	<list:data-column col="created" title="${ lfn:message('sys-news:sysNewsMain.docCreateTime') }">
	        <kmss:showDate value="${sysNewsMain.docPublishTime}" type="date"></kmss:showDate>
      	</list:data-column>
      	 <!-- 摘要信息 -->
	 	<list:data-column col="summary" title="${ lfn:message('sys-news:sysNewsMain.fdDescription') }">
	        <c:out value="${sysNewsMain.fdDescription}"></c:out>
      	</list:data-column>
		 <!-- 点击率-->	
		<list:data-column col="count" title="${ lfn:message('sys-news:sysNewsMain.docHits')}">
		     <c:out value="${sysNewsMain.docReadCount}"/>
		</list:data-column>
		<!--链接-->
		<list:data-column col="href" escape="false">
			<c:choose>
				<c:when test="${sysNewsMain.fdLinkUrl!=null && sysNewsMain.fdLinkUrl!='' && sysNewsMain.fdIsLink && (sysNewsMain.docStatus eq '30' || sysNewsMain.docStatus eq '40')}">
		       		${sysNewsMain.fdLinkUrl}
				</c:when>
				<c:otherwise>
					/sys/news/sys_news_main/sysNewsMain.do?method=view&fdId=${sysNewsMain.fdId}	
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<c:if test="${sysNewsMain.fdIsPicNews == true }">
			<!--图标-->
			<list:data-column col="icon" escape="false">
			     <%
					Object newsObj = pageContext.getAttribute("sysNewsMain");
					if (newsObj != null) {
						SysNewsMain sysNewsMain = (SysNewsMain)newsObj;
						out.print(prefix + SysNewsUtils.getImgUrl(sysNewsMain));
					}
				%>
			</list:data-column>
		</c:if>
		<c:if test="${sysNewsMain.fdIsPicNews != true }">
			<!--图标（无图标时显示一张默认图标）-->
			<list:data-column col="icon" escape="false">
			     <% out.print(prefix + "/sys/news/images/mobile-default.png"); %>
			</list:data-column>
		</c:if>		
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>