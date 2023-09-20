<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="topic" list="${queryPage.list }" >
		<%--ID--%>
		<list:data-column property="fdId"></list:data-column>
		<%--主题--%>
		<list:data-column col="docSubject"  title="${ lfn:message('km-forum:kmForumTopic.docSubject') }" style="text-align:left;min-width:100px"  escape="false">
			<a class="com_subject textEllipsis" title="${topic.docSubject}" href="${LUI_ContextPath}/km/forum/km_forum/kmForumPost.do?method=view&fdForumId=${topic.kmForumCategory.fdId}&fdTopicId=${topic.fdId}" target="_blank">
				<c:out value="${topic.docSubject}" ></c:out>
			</a>
		</list:data-column>
		<%--回复数--%>
		<list:data-column col="fdReplyCount"  title="${ lfn:message('km-forum:portlet.kmForum.fdPostCount.portlet') }" headerClass="width80" escape="false" >
			<font color='green'><c:out value="${topic.fdReplyCount }"></c:out></font>
		</list:data-column>
		<%--最后回复时间--%>
		<list:data-column col="fdLastPostTime" title="${ lfn:message('km-forum:kmForumTopic.list.fdLastPostTime') }"  headerClass="width120" >
			<kmss:showDate value="${topic.fdLastPostTime}" type="date" /> 
		</list:data-column>
	</list:data-columns>
</list:data>