<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="category" list="${queryPage.list}">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="fdImageUrl">
			 <%
			    out.print("/km/forum/mobile/resource/images/bbbg.jpg");
			 %>
		</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
		       /km/forum/mobile/index.jsp?moduleName=${category.fdName}&filter=1&queryStr=%2Fkm%2Fforum%2Fkm_forum%2FkmForumTopicIndex.do%3Fmethod%3DlistChildren%26q.categoryId%3D${category.fdId}%26orderby%3DfdLastPostTime%26ordertype%3Ddown
		</list:data-column>
		<list:data-column headerStyle="width:60px" property="fdName" title="板块名称" style="text-align:left">
		</list:data-column>
		<list:data-column headerStyle="width:120px" property="fdDescription" title="板块简介">
		</list:data-column>
		<c:set var="topicCount" value="topicCount${category.fdId}"/>
	    <c:set var="replyCount" value="replyCount${category.fdId}"/>
		<list:data-column headerStyle="width:60px" col="topicCount" title="发帖数">
		    ${countJson[topicCount]}
		</list:data-column>
		<list:data-column headerStyle="width:60px" col="replyCount" title="回帖数">
		    ${countJson[replyCount]}
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>
