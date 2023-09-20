<%@ page import="com.landray.kmss.util.UserUtil" %><%--实施反馈页面--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<mui:cache-file name="mui-feedbackInfo.css" cacheType="md5"/>
<div data-dojo-type="mui/view/DocView" id="feedbackView" style="display: none">
	 <div id="feedbackContent" class="muiCirculation">
         <html:form action="/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=save&method_GET=add&mobile=true">
	     	  <div data-dojo-type="mui/view/DocView" data-dojo-mixins="mui/form/_ValidateMixin" style="overflow:visible " id="feedbackScrollView">
	     		  <html:hidden property="fdMainId" value="${JsParam.fdMainId}" />
	     		  <html:hidden property="docCreatorName" value="<%=UserUtil.getUser().getFdName()%>" />
	     		  <html:hidden property="docCreatorId" value="<%=UserUtil.getUser().getFdId()%>" />
                   <html:hidden property="docCreatorTime" value="${JsParam.docCreatorTime}" /> <%--反馈时间在提交的时候通过js添加--%>
                   <table class="muiSimple" cellpadding="0" cellspacing="0">
	     				<tr>
	     					<td>
	     						<label class="muiReplyTitle"><bean:message bundle="km-review" key="kmReviewFeedbackInfo.fdSummary" /></label>
	     						<div style="display: inline-block;height: 30px;float: right;" >
	     							<xform:text  isLoadDataDict="true" className="inlineBlock" property="fdSummary" align="right" mobile="true" style="display: inline-block;height:100px;line-height:100px;" showStatus="edit" required="true" subject="提要"></xform:text>
	     						</div>


	     					</td>
	     				</tr>
	     				<tr>
	     					<td>
	     						<div style="margin-bottom: 10px;" class="muiReplyTitle"><bean:message bundle="km-review" key="kmReviewFeedbackInfo.docContent" /></div>
	     						<div class="feedbackInfo_docContent" style="background: #F8F8F8;">
	     							<xform:textarea isLoadDataDict="true" htmlElementProperties="data-actor-expand='true'"  property="docContent" mobile="true" style="height:100%;width:96%" showStatus="edit"  subject=" 反馈内容"></xform:textarea>
	     						</div>
	     					</td>
	     				</tr>
	     				<tr>
	     					<td class="feedback_attachment">
	     						<div class="muiReplyTitle" style="float:left;line-height:5rem"><bean:message bundle="sys-circulation" key="sysCirculationMain.attachment" /></div>
	     						<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
	     							<c:param name="fdKey" value="feedBackAttachment" />
	     							<c:param name="fdMulti" value="true" />
	     							<c:param name="fdAttType" value="" />
	     							<c:param name="fdModelName" value="com.landray.kmss.km.review.model.KmReviewFeedbackInfo"/>
	     						    <c:param name="widgitId" value="feedbackAtt" />
	     						</c:import>
	     					</td>
	     				</tr>
	     				<tr>
	     					<td class="feedback_fdNotifyPeople">
	     						<div class="muiReplyTitle" line-height:5rem >
                                     <bean:message bundle="km-review" key="kmReviewFeedbackInfo.fdNotifyPeople" />
                                 </div>
	     						<div id="main_m" style="display:block;margin-top: 0.5rem">
	     							<xform:address htmlElementProperties="id='fdNotifyPeople_m'" showStatus="edit" mobile="true" propertyId="fdNotifyId"  propertyName="fdNotifyPeople"  mulSelect="true" textarea="true"
	     										   orgType="ORG_TYPE_ALLORG" subject="${ lfn:message('km-review:kmReviewFeedbackInfo.fdNotifyPeople') }"
	     										   style="width:95%" >
	     							</xform:address>
	     						</div>
	     					</td>
	     				</tr>
	     		        <tr>
	     					<td class="feedback_editNotifyType">
								<div class="muiReplyTitle" line-height:5rem >
									<bean:message bundle="km-review" key="notify.type" />
								</div>
	     						<%-- 通知方式 --%>
	     						<kmss:editNotifyType id="feedbackInfo_fdNotifyType"   property="fdNotifyType" mobile="true"  />
	     					</td>
	     				</tr>
                   </table>
	     	  </div>
	     	  <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" class="muiViewBottom">
	     	  	<li data-dojo-type="km/review/mobile/km_review_feedback_info/js/FeedbackInfoOperationButton" data-dojo-props="fdType:'cancel'">
	     	  		<bean:message key="button.cancel" />
	     	  	</li>
	     	  	<li data-dojo-type="km/review/mobile/km_review_feedback_info/js/FeedbackInfoOperationButton" data-dojo-props="fdType:'ok'" class="muiBtnDefault mainTabBarButton">
	     	  		<bean:message key="button.update" />
	     	  	</li>
	     	  </ul>
	     </html:form>
	</div>
</div>
