<%@ page import="com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter" %>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgElement" %>
<%@ page import="com.landray.kmss.km.review.model.KmReviewFeedbackInfo" %>
<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<list:data>
	<list:data-columns var="kmReviewFeedbackInfo" list="${queryPage.list}" varIndex="index">
	    <list:data-column property="fdId">
		</list:data-column>
		<list:data-column style="width:35px;" col="fdSerial" title="${ lfn:message('page.serial') }">
			${index+1}
		</list:data-column>
		<list:data-column style="width:35px;" col="fdHasAttachment" escape="false">
			<c:if test="${kmReviewFeedbackInfo.fdHasAttachment }">
				<img src="../img/fj.png" title="${ lfn:message('km-review:kmReviewFeedbackInfo.hasAttachment') }">
			</c:if>
		</list:data-column>
		<list:data-column property="fdSummary" title="${ lfn:message('km-review:kmReviewFeedbackInfo.fdSummary') }">
		</list:data-column>
		<list:data-column style="width:100px;" property="fdCreator.fdName" title="${ lfn:message('km-review:kmReviewFeedbackInfo.docCreatorId') }">
		</list:data-column>
		<list:data-column style="width:120px; white-space:nowrap;" property="docCreateTime" title="${ lfn:message('km-review:kmReviewFeedbackInfo.docCreatorTime') }">
		</list:data-column>

		<list:data-column col="created" escape="false" title="${ lfn:message('sys-task:sysTaskMain.docCreateTime') }" >
			<kmss:showDate isInterval="true" value="${kmReviewFeedbackInfo.docCreateTime}" type="datetime"></kmss:showDate>
		</list:data-column>

		<list:data-column  property="docContent" >
		</list:data-column>
		<list:data-column  property="fdNotifyPeople" >
		</list:data-column>
		<list:data-column col="href" escape="false"><%--移动端反馈详情页面地址--%>
			/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=view&fdId=${kmReviewFeedbackInfo.fdId}
		</list:data-column>
		<list:data-column col="feedbackCreatorHeadUrl" escape="false"><%--移动端反馈详情页面地址--%>
			<%--${kmReviewFeedbackInfo.fdCreator.fdId}--%>
			<%
				KmReviewFeedbackInfo kmReviewFeedbackInfo=(KmReviewFeedbackInfo)pageContext.getAttribute("kmReviewFeedbackInfo");
				String path = PersonInfoServiceGetter.getPersonHeadimageUrl(kmReviewFeedbackInfo.getFdCreator().getFdId());
				out.print(path.substring(1));
			%>


		</list:data-column>
	</list:data-columns>

	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"></list:data-paging>

</list:data>
