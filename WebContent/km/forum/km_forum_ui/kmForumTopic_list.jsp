<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.km.forum.model.KmForumConfig,com.landray.kmss.km.forum.model.KmForumPost"%>
<%@ page import="java.util.*"%>
<% 
   int hotReplyCount = Integer.parseInt(new KmForumConfig().getHotReplyCount());
   request.setAttribute("hotReplyCount",hotReplyCount);
%>
<list:data>
	<list:data-columns var="kmForumTopic" list="${queryPage.list }">
	    <list:data-column property="fdId">
		</list:data-column >
		<list:data-column col="method" escape="false">
	       <c:choose> 	
		      <c:when test="${kmForumTopic.fdStatus=='10'}">
		           <c:out value="viewDraft"/>
		      </c:when>
		       <c:otherwise>
		          <c:out value="view"/>
		      </c:otherwise>
		   </c:choose> 
		</list:data-column >
	    <!--版块id-->
	    <list:data-column  property="kmForumCategory.fdId">
		</list:data-column>
		<!--主题-->
	    <list:data-column col="docSubject" title="${ lfn:message('km-forum:kmForumTopic.docSubject') }" escape="false" style="text-align:left;min-width:200px">
	                    <c:if test="${kmForumTopic.fdSticked==true}">
	                       <span title="${ lfn:message('km-forum:kmForumTopic.button.stick') }">
							  <img src="${LUI_ContextPath}/km/forum/resource/images/i_top.png" border="0">
						   </span>
						</c:if>				
						<c:if test="${kmForumTopic.fdPinked==true}">
						    <span title="${ lfn:message('km-forum:kmForumTopic.pink.title') }">
							  <img src="${LUI_ContextPath}/km/forum/resource/images/i_pink.png" border="0">
							</span>
						</c:if>
						<c:if test="${kmForumTopic.fdReplyCount>=hotReplyCount}">
						   <span title="${ lfn:message('km-forum:kmForumTopic.hot.title') }">
						       <img src="${LUI_ContextPath}/km/forum/resource/images/i_hot.png" border="0">
						   </span>
						</c:if>
						<c:if test="${kmForumTopic.fdStatus=='40'}">
						  <span title="${ lfn:message('km-forum:kmForumTopic.status.conclude') }">
						      <img src="${LUI_ContextPath}/km/forum/resource/images/end.gif" border="0">
						  </span>
						</c:if>
						<span class="com_subject"><c:out value="${kmForumTopic.docSubject}"/></span>
		</list:data-column>
	    <!--版块-->
	    <list:data-column headerClass="width120" property="kmForumCategory.fdName" title="${ lfn:message('km-forum:kmForumTopic.fdForumId') }">
		</list:data-column>
	    <!--作者-->
	    <list:data-column headerClass="width80" col="fdPoster.fdName" title="${ lfn:message('km-forum:kmForumTopic.fdPosterId') }" escape="false"> 
	                <c:if test="${kmForumTopic.fdIsAnonymous==false}">
					    <ui:person personId="${kmForumTopic.fdPoster.fdId}" personName="${kmForumTopic.fdPoster.fdName}"></ui:person>
					</c:if>
					<c:if test="${kmForumTopic.fdIsAnonymous==true}">
						<bean:message  bundle="km-forum" key="kmForumTopic.fdIsAnonymous.title"/>
					</c:if>
	              
		</list:data-column>
		<!--回复-->
		<list:data-column headerClass="width40" property="fdReplyCount" title="${ lfn:message('km-forum:kmForumTopic.fdReplyCount') }">
		</list:data-column>
		<!--查看-->
		<list:data-column headerClass="width40" property="fdHitCount" title="${ lfn:message('km-forum:kmForumTopic.fdHitCount') }">
		</list:data-column>
		 <!--最后回复-->
	    <list:data-column headerClass="width80" col="fdLastPosterName" escape="false" title="${ lfn:message('km-forum:kmForumTopic.fdLastPosterId') }">
	      <c:if test="${not empty queryPage.list}">    
	           <c:set var="posts" value="${kmForumTopic.forumPosts}" scope="request"/>
		      <%
		      List topicPosts = (List) request.getAttribute("posts");
		      if (topicPosts != null && !topicPosts.isEmpty()) {
		      	if (topicPosts.size() == 1) {
		      		//显示匿名
		      		request.setAttribute("showAno", false);
		      	} else {
		      		request.setAttribute("showAno", true);
		      		KmForumPost lastPost = (KmForumPost) topicPosts.get(topicPosts.size() - 1);
		      		if (lastPost != null && lastPost.getFdIsAnonymous()) {
		      	request.setAttribute("lastReplyAno", true);
		      		}
		      	}
		      } else {
		      	request.setAttribute("showAno", false);
		      }
		      %> 
	          
	          <c:if test="${!empty kmForumTopic.fdLastPosterName && showAno==true}">
	          	  <c:if test="${lastReplyAno==true}">
	          	  	 <c:out value="${kmForumTopic.fdLastPosterName}" />
	          	  </c:if>
				 <c:if test="${lastReplyAno !=true}">
	          	  	 <c:out value="${kmForumTopic.fdLastPoster.fdName}" />
	          	  </c:if>
			  </c:if>
			  <c:if test="${showAno==false}">
			  			-
			  </c:if>
			  <c:if test="${empty kmForumTopic.fdLastPosterName}">
			  			-
			  </c:if>
		  </c:if>	  
		</list:data-column>
	    <!--更新时间-->
	    <list:data-column headerClass="width120" col="fdLastPostTime" escape="false" title="${ lfn:message('km-forum:kmForumTopic.list.fdLastPostTime') }">
			<kmss:showDate type="datetime" isInterval="true" showTitle="true" value="${kmForumTopic.fdLastPostTime}"/>     	
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>