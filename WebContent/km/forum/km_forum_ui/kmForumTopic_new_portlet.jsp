<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.km.forum.model.KmForumConfig"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	
	<list:data-columns var="topic" list="${queryPage.list }">
		<%--ID--%>
	   <list:data-column property="fdId"></list:data-column>
	
	    <c:set var="replyCount" value="${topic.fdReplyCount}" scope="request" />
	    <c:set var="fdSticked" value="${topic.fdSticked}" scope="request" />
	    <c:set var="fdPinked" value="${topic.fdPinked}" scope="request" />
		
		<%--显示内容--%>
		<list:data-column col=""  title="${ lfn:message('km-forum:kmForumScore.fdPostCount.portlet') }" escape="false">
			<link href="${LUI_ContextPath}/km/forum/resource/css/forum_portlet.css" rel="stylesheet" type="text/css" />
			<%  request.setAttribute("hot",null);
				request.setAttribute("top",null);
				request.setAttribute("pink",null);
			   KmForumConfig forumConfig = new KmForumConfig();
			   int hotReplyCount = Integer.parseInt(forumConfig.getHotReplyCount());
			   int replyCount = Integer.parseInt(request.getAttribute("replyCount").toString());
			   
			   if (replyCount>=hotReplyCount){
				   request.setAttribute("hot","hot");
			   }
			   
			   Boolean sticked = Boolean.parseBoolean(request.getAttribute("fdSticked").toString());
			   if (sticked){
				   request.setAttribute("top","top");
			   }
			   
			   Boolean pink = Boolean.parseBoolean(request.getAttribute("fdPinked").toString());
			   if (pink){
				   request.setAttribute("pink","pink");
			   } 
            %>
            <div class='new_portlet'>
                <c:if test="${not empty pink}"> 
                	<a class='${pink}' title='${lfn:message('km-forum:kmForumTopic.status.pink')}'><span style='color: white;'><c:out value="${lfn:message('km-forum:kmForumTopic.status.pink.sub')}"/></span></a>
                </c:if> 
				<c:if test="${not empty hot}">
					<a class='${hot}' title='${lfn:message('km-forum:kmForumTopic.status.hot')}'><span style='color: white;'><c:out value="${lfn:message('km-forum:kmForumTopic.status.hot.sub')}"/></span></a>
				</c:if>
				<c:if test="${not empty top}">
					<a class='${top}' title='${lfn:message('km-forum:kmForumTopic.status.top')}'><span style='color: white;'><c:out value="${lfn:message('km-forum:kmForumTopic.status.top.sub')}"/></span></a>
				</c:if>
				<a class='text' target="_blank"
				 href="${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=view&fdForumId=${topic.kmForumCategory.fdId}&fdTopicId=${topic.fdId}" ><c:out value="${topic.docSubject}"/>
				</a>
				<a class='count' title='${lfn:message('km-forum:kmForumPost.replyCount')}' ><c:out value="${topic.fdReplyCount}"/></a>
			</div>
		</list:data-column>
		
		<%--是否置顶--%>
		<%--
		<list:data-column col="fdSticked"  title="${ lfn:message('km-forum:kmForumScore.fdPostCount.portlet') }" escape="false">
			<c:out value="${topic.fdSticked}"/>
		</list:data-column>--%>
		<%--是否精华--%>
		<%--
		<list:data-column col="fdPinked"  title="${ lfn:message('km-forum:kmForumScore.fdPostCount.portlet') }" escape="false">
			<c:out value="${topic.fdPinked}"/>
		</list:data-column>--%>
		
		<%--主题--%>
		<%--
		<list:data-column col="docSubject"  title="${ lfn:message('km-forum:kmForumScore.fdPostCount.portlet') }" escape="false">
			<c:out value="${topic.docSubject}"/>
		</list:data-column>--%>
		
		<%--回帖数--%>
		<%--
		<list:data-column col="fdReplyCount"  title="${ lfn:message('km-forum:kmForumScore.fdPostCount.portlet') }" escape="false">
			<c:out value="${topic.fdReplyCount}"/>
		</list:data-column>--%>
		
		
	</list:data-columns>
</list:data>