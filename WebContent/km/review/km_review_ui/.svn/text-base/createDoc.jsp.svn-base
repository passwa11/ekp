<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.review.service.IKmReviewMainService"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<script type="text/javascript">
			seajs.use(['theme!list']);
		</script>
		<%
			String DraftCount = ((IKmReviewMainService)SpringBeanUtil.getBean("kmReviewMainService")).getCount("draft",true);
			pageContext.setAttribute("draftTitle",ResourceUtil.getString("km-review:kmReview.tree.draftBox")+"("+DraftCount+")");
		%>
		<div>
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-useMaxWidth='true'>
			  <ui:content title="${ lfn:message('km-review:kmReview.nav.create') }">
			  		<div style="background-color: #fff">
						<%--#153388-流程发起页面增加统计数据-开始--%>
						<%@ include file="/km/review/km_review_ui/km_review_summary_count.jsp" %>
						<%--#153388-流程发起页面增加统计数据-结束--%>
			  			<%@ include file="/sys/lbpmperson/import/usualCate_ui.jsp"%>
			  			<%@ include file="/sys/person/sys_person_favorite_category/favorite_category_flat_ui.jsp"%>
						<%@ include file="/sys/lbpmperson/import/allProcess.jsp"%>
					</div>
			  </ui:content>
			  <ui:content title="<div id='cir_label_draftTitle'>${draftTitle}</div>">
			  		<%@ include file="/km/review/km_review_ui/draftBox.jsp"%>
			  </ui:content>
		    </ui:tabpanel>
	  </div>
	</template:replace>
</template:include>