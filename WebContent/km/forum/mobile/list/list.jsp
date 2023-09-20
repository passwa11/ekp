<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.km.forum.model.KmForumConfig,com.landray.kmss.km.forum.model.KmForumTopic"%>
<% 
   int hotReplyCount = Integer.parseInt(new KmForumConfig().getHotReplyCount());
   request.setAttribute("hotReplyCount",hotReplyCount);
%>
<list:data>
	<list:data-columns var="kmForumTopic" list="${queryPage.list }" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
	    <!-- 主题-->	
		<list:data-column col="label" title="${ lfn:message('km-forum:kmForumTopic.docSubject') }" escape="false">
		    <c:out value="${kmForumTopic.docSubject}"/>
		</list:data-column>
		<list:data-column col="status" title="${ lfn:message('km-forum:kmForumTopic.docSubject') }" escape="false">
			<c:if test="${kmForumTopic.fdSticked==true}">
                <span class="mui_ekp_portal_km_forum_top">
                	<c:out value="${ lfn:message('km-forum:kmForumTopic.status.top') }"/>
                </span>
			</c:if>				
			<c:if test="${kmForumTopic.fdPinked==true}">
				<span class="mui_ekp_portal_km_forum_essence">
                	<c:out value="${ lfn:message('km-forum:kmForumTopic.status.pink') }"/>
                </span>
			</c:if>
			<c:if test="${kmForumTopic.fdReplyCount>=hotReplyCount}">
				<i class="fontmuis muis-hot mui_portal_km_forum_icon_hot"></i>
			</c:if>
			<c:if test="${kmForumTopic.fdStatus=='40'}">
				<i class="fontmuis muis-lock  mui_portal_km_forum_icon_lock"></i>
			</c:if>
		</list:data-column>
		
		<list:data-column col="lock" title="${ lfn:message('km-forum:kmForumTopic.docSubject') }" escape="false">
			
		</list:data-column>
		
		<list:data-column col="category" escape="false">
		 		<c:out value="${kmForumTopic.kmForumCategory.fdName}"/>
		</list:data-column>
		<list:data-column col="categoryId" escape="false">
		 		<c:out value="${kmForumTopic.kmForumCategory.fdId}"/>
		</list:data-column>
		<c:if test="${kmForumTopic.fdSticked==true}">
			<list:data-column col="isTop" escape="true">
			 		<c:out value="1"/>
			</list:data-column>
		</c:if>
		 <!-- 创建时间-->
	 	<list:data-column col="created" title="${ lfn:message('km-forum:kmForumTopic.docCreateTime')}" escape="false">
	        <kmss:showDate type="datetime" isInterval="true" showTitle="true" value="${kmForumTopic.fdLastPostTime}"/> 
      	</list:data-column>
      	<list:data-column col="icon" escape="false">
      		<c:if test="${kmForumTopic.fdIsAnonymous == false}">
			    <person:headimageUrl personId="${kmForumTopic.fdPoster.fdId}" size="m" />
      		</c:if>
      		<c:if test="${kmForumTopic.fdIsAnonymous == true}">
			    /km/forum/resource/images/user_anon_img.png
      		</c:if>
		</list:data-column>
		 <!-- 点击率-->	
		<list:data-column col="count" title="${  lfn:message('km-forum:kmForumTopic.fdHitCount')}">
			    <c:out value="${kmForumTopic.fdHitCount}"/>
		</list:data-column>
		 <!-- 回帖数-->	
		<list:data-column col="replay" title="${  lfn:message('km-forum:kmForumTopic.fdReplyCount')}">
			    <c:out value="${kmForumTopic.fdReplyCount}"/>
		</list:data-column>
		<!-- 摘要-->
		<list:data-column col="summary"  title="${ lfn:message('km-forum:kmForumTopic.docSummary')}">
		     ${kmForumTopic.docSummary}
		</list:data-column>
		<list:data-column col="thumbs"  escape="false">
			<%
			KmForumTopic kmForumTopic = (KmForumTopic)pageContext.getAttribute("kmForumTopic");
				if(kmForumTopic.getFdThumbInfo() !=null){
					
					String contextPath = request.getContextPath();
					String thumb= kmForumTopic.getFdThumbInfo().replaceAll("picthumb=big","picthumb=small").replaceAll("&amp;","&").replaceAll(contextPath,"");
					
					kmForumTopic.setFdThumbInfo(thumb);
				}
			%>
		      ${kmForumTopic.fdThumbInfo}
		</list:data-column>
		<!--链接-->
		<list:data-column col="href" escape="false">
			/km/forum/mobile/view.jsp?fdTopicId=${kmForumTopic.fdId}
		</list:data-column>
		
		<list:data-column col="supportCount">
			 ${kmForumTopic.forumPosts[0].docPraiseCount}
		</list:data-column>
		
		<list:data-column col="creator"  escape="false" title="${ lfn:message('km-forum:kmForumTopic.fdPosterId')}">
	           <c:if test="${kmForumTopic.fdIsAnonymous==false}">
				    <c:out value="${kmForumTopic.fdPoster.fdName}"/>
				</c:if>
				<c:if test="${kmForumTopic.fdIsAnonymous==true}">
					<bean:message  bundle="km-forum" key="kmForumTopic.fdIsAnonymous.title"/>
				</c:if>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>