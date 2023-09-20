<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="wxMenuModel" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<!-- 微信应用ID -->
		<list:data-column  property="fdAgentId" title="${ lfn:message('third-weixin-work:third.wx.menu.agentId') }" />
		<!-- 序号 -->
		<list:data-column  property="fdAgentName" title="${ lfn:message('third-weixin-work:third.wx.menu.agentName') }" />
		<!-- 所属分类 -->
		<list:data-column col="fdPublished" title="${ lfn:message('third-weixin-work:third.wx.menu.btn.publish.title') }" >
			<c:if test="${wxMenuModel.fdPublished eq '1'}">
				<bean:message bundle="third-weixin-work" key="third.wx.menu.btn.publish.yes"/>
			</c:if>
			<c:if test="${empty wxMenuModel.fdPublished}">
				<bean:message bundle="third-weixin-work" key="third.wx.menu.btn.publish.no"/>
			</c:if>
		</list:data-column>
		
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
