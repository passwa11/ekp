<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ page import="com.landray.kmss.km.imeeting.forms.KmImeetingSummaryForm"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//移动端发布时间只显示日期，不显示时间
	KmImeetingSummaryForm kmImeetingSummaryForm = (KmImeetingSummaryForm)request.getAttribute("kmImeetingSummaryForm");
	kmImeetingSummaryForm.setDocPublishTime(DateUtil.convertDateToString(DateUtil.convertStringToDate(
			kmImeetingSummaryForm.getDocPublishTime(), DateUtil.TYPE_DATETIME,null), DateUtil.TYPE_DATE,null));
%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<mui:min-file name="mui-imeeting-summaryview.css"/>
		<mui:min-file name="mui-task-view.css"/>
	</template:replace>
	
	<template:replace name="title">
		<c:if test="${not empty kmImeetingSummaryForm.fdName}">
			<c:out value="${ kmImeetingSummaryForm.fdName}"></c:out>
		</c:if>
		<c:if test="${empty kmImeetingSummaryForm.fdName}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	
	<template:replace name="content">
		<div id="scrollView"  class="gray"
			data-dojo-type="mui/view/DocScrollableView">
			
			<div class="muiSummaryInfoBanner">
			
			
				<div class="muiSummaryInfoMask"></div>
				<div class="muiSummaryInfoCard">
					<div class="muiSummaryInfoItem muiSummaryInfoTitle">
						<xform:text property="fdName"></xform:text>
					</div>
					
					<div class="muiSummaryInfoItem">
						<bean:message bundle="km-imeeting" key="mobile.info.time"/>：
						<xform:datetime property="docPublishTime" dateTimeType="date"></xform:datetime>
					</div>
					
					<div class="muiSummaryInfoItem">
						<div style="display: inline-block; margin-right: 4rem;">
							<bean:message bundle="km-imeeting" key="mobile.info.host"/>：
							<xform:text property="fdHostName"></xform:text>
						</div>
						
						<c:if test="${kmImeetingSummaryForm.docStatus >= '30'}">
							<div style="display: inline-block;">
								<bean:message bundle="km-imeeting" key="mobile.info.read"/>：
								<c:choose>
									<c:when test="${empty kmImeetingSummaryForm.docReadCount }">0</c:when>
									<c:otherwise>
										<c:out value="${kmImeetingSummaryForm.docReadCount}"/>
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
							
					</div>
					
				</div>
			
			</div>
			
			<div data-dojo-type="mui/fixed/Fixed" id="fixed">
				<div data-dojo-type="mui/fixed/FixedItem"  class="ImeetingFixedItem">
					<%--切换页签--%>
					<div class="muiHeader">
						<div
							data-dojo-type="mui/nav/MobileCfgNavBar" 
							data-dojo-props=" defaultUrl:'/km/imeeting/mobile/summary_view_nav.jsp?docStatus=${kmImeetingSummaryForm.docStatus }' ">
						</div>
					</div>
				</div>
			</div>
			
			<%--会议信息页签--%>
			<div id="contentView" data-dojo-type="dojox/mobile/View">
			
				<%-- 
				<kmss:auth requestURL="/sys/task/sys_task_main/sysTaskMain.do?method=add" requestMethod="GET">
					<ul data-dojo-type="sys/task/mobile/resource/js/OverflowTabBar">
    					<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtn muiBtnFeedback" 
  							data-dojo-props="icon:'mui mui-task-icon',colSize:2,href:'/sys/task/sys_task_main/sysTaskMain.do?method=add&fdModelId=${JsParam.fdId}&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary'">${lfn:message('sys-task:tag.task')}</li>
					</ul>
				</kmss:auth>
				--%>
				
				<div class="muiAccordionPanelContent muiSummaryPanelContent" style="margin-top: 0;">
					<div class="muiFormContent">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<%--会议模板--%>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdTemplate" />
								</td>
								<td>
									<xform:text property="fdTemplateName" mobile="true"/>
								</td>
							</tr>
							<%--会议主持人 --%>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdHost" />
								</td>
								<td>
									<c:if test="${not empty kmImeetingSummaryForm.fdHostId }">
										<xform:address propertyName="fdHostName" propertyId="fdHostId" mobile="true"></xform:address>
									</c:if>
									<c:if test="${not empty kmImeetingSummaryForm.fdOtherHostPerson }">
										&nbsp;<xform:text property="fdOtherHostPerson"></xform:text>
									</c:if>
								</td>
							</tr>
							<%--会议地点--%>
							<c:choose>
								<c:when test="${not empty kmImeetingSummaryForm.fdVicePlaceNames or not empty kmImeetingSummaryForm.fdOtherVicePlace }">
									<tr>
										<td class="muiTitle">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMainPlace"/>
										</td>
										<td>
											<xform:text property="fdPlaceName"></xform:text>
											<xform:text property="fdOtherPlace"></xform:text>
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdVicePlace"/>
										</td>
										<td>
											<xform:text property="fdVicePlaceNames"></xform:text>
											<xform:text property="fdOtherVicePlace"></xform:text>
										</td>
									</tr>										
								</c:when>
								<c:otherwise>
									<tr>
										<td class="muiTitle">
											<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlace" />
										</td>
										<td>
											<xform:text property="fdPlaceName"></xform:text>
											<xform:text property="fdOtherPlace"></xform:text>
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
							<%--会议时间--%>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate" />
								</td>
								<td>
									<c:out value="${kmImeetingSummaryForm.fdHoldDate }"></c:out> ~
									<c:out value="${kmImeetingSummaryForm.fdFinishDate }"></c:out>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<kmss:ifModuleExist path="/sys/task/">
					<div data-dojo-type="mui/panel/AccordionPanel" data-dojo-props="fixed:false">
						<%-- 相关任务 --%>
						<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-task" key="sysTaskMain.relatedTask"/>',icon:'mui-task-icon'">
							<div data-dojo-type="dojox/mobile/View">
								<ul data-dojo-type="sys/task/mobile/resource/js/list/SysTaskJsonStoreList"
									data-dojo-mixins="sys/task/mobile/resource/js/list/CalendarItemListMixin"
									data-dojo-props="url:'/sys/task/sys_task_main/sysTaskIndex.do?method=list&fdModelId=${kmImeetingSummaryForm.fdId}&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary',lazy:false">
								</ul>
								<div class="muiAccordionPanelContentBottom">
									<kmss:auth requestURL="/sys/task/sys_task_main/sysTaskMain.do?method=add" requestMethod="GET">
										<button type="button"
											class="muiAccordionPanelContentBtn"
											onclick="window.open('${LUI_ContextPath }/sys/task/sys_task_main/sysTaskMain.do?method=add&fdModelId=${JsParam.fdId}&fdModelName=com.landray.kmss.km.imeeting.model.KmImeetingSummary', '_self')">
											+<bean:message bundle="km-imeeting" key="mobile.button.task.plan"/>
										</button>
									</kmss:auth>
								</div>
							</div>
						</div>
					</div>
				</kmss:ifModuleExist>
			</div>
			<%--会议人员页签--%>
			<div id="personView" data-dojo-type="dojox/mobile/View">
				<div data-dojo-type="mui/panel/AccordionPanel" data-dojo-props="fixed:false" style="margin-top: 0;">
					<%--参加人员 --%>
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanAttendPersons"/>',icon:''">
						<div class="txtContent">
							<ul class="muiMeetingList">
								<c:if test="${not empty kmImeetingSummaryForm.fdPlanAttendPersonIds }">
									<li>
										<div data-dojo-type="mui/person/PersonList" style="margin-left:.8rem;"
											data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.fdAttendPersons"/>',personId:'${kmImeetingSummaryForm.fdPlanAttendPersonIds }'">											
										</div>
									</li>
								</c:if>
								<c:if test="${not empty kmImeetingSummaryForm.fdPlanOtherAttendPerson }">
									<li>
										<strong>
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>
										</strong>
										<div class="staffList">
											<c:out value="${kmImeetingSummaryForm.fdPlanOtherAttendPerson }"></c:out>
										</div>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
					
					<%--列席人员 --%>
					
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanParticipantPersons"/>',icon:''">
						<div class="txtContent">
							<ul class="muiMeetingList">
								<c:if test="${not empty kmImeetingSummaryForm.fdPlanParticipantPersonIds }">
									<li>
										<div data-dojo-type="mui/person/PersonList" style="margin-left:.8rem;"
											data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.fdParticipantPersons"/>',personId:'${kmImeetingSummaryForm.fdPlanParticipantPersonIds }'">
										</div>
									</li>
								</c:if>
								<c:if test="${not empty kmImeetingSummaryForm.fdPlanOtherParticipantPersons }">
									<li>
										<strong>
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>
										</strong>
										<div class="staffList">
											<c:out value="${kmImeetingSummaryForm.fdPlanOtherParticipantPersons }"></c:out>
										</div>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
					
					<%--实际参加人员 --%>
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdActualAttendPersons"/>',icon:''">
						<div class="txtContent">
							<ul class="muiMeetingList">
								<c:if test="${not empty kmImeetingSummaryForm.fdActualAttendPersonIds }">
									<li>
										<div data-dojo-type="mui/person/PersonList" style="margin-left:.8rem;"
										data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingSummary.fdActualAttendPersons"/>',personId:'${kmImeetingSummaryForm.fdActualAttendPersonIds }'">											
										</div>
									</li>
								</c:if>
								<c:if test="${not empty kmImeetingSummaryForm.fdActualOtherAttendPersons }">
									<li>
										<strong>
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>
										</strong>
										<div class="staffList">
											<c:out value="${kmImeetingSummaryForm.fdActualOtherAttendPersons }"></c:out>
										</div>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
					
					<%--抄送人员 --%>
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdCopyToPersons"/>',icon:''">
						<div class="txtContent">
							<ul class="muiMeetingList">
								<c:if test="${not empty kmImeetingSummaryForm.fdCopyToPersonIds }">
									<li>
										<div data-dojo-type="mui/person/PersonList" style="margin-left:.8rem;"
											data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingMain.person.inner"/>',detailTitle:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.fdCopyToPersons"/>',personId:'${kmImeetingSummaryForm.fdCopyToPersonIds }'">
										</div>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
					
				</div>
			
			</div>
			
			
			<%--会议决议 --%>	
			<div id="processView" data-dojo-type="dojox/mobile/View">
				<div data-dojo-type="mui/panel/AccordionPanel"
					data-dojo-props="fixed:false"
					style="margin-top: 0;">
					<div data-dojo-type="mui/panel/Content" class="proceePanelContent" data-dojo-props="title:'<bean:message bundle="km-imeeting"  key="mobile.kmImeetingSummary.desion"/>',icon:''">
						<c:if test="${kmImeetingSummaryForm.fdContentType=='rtf'}">
							<div id="_____rtf_____docContent" class="muiFieldRtf">
								${kmImeetingSummaryForm.docContent}
							</div>
						</c:if>
						<c:if test="${kmImeetingSummaryForm.fdContentType=='word'}">
						
							<c:set var="___attForms" value="${kmImeetingSummaryForm.attachmentForms['editonline']}" />
							<c:if test="${___attForms!=null && fn:length(___attForms.attachments)>0}">
						 		<c:forEach var="sysAttMain" items="${___attForms.attachments}" varStatus="vsStatus">
						 			<c:set var="attMainId" value="${sysAttMain.fdId }"></c:set>
						 			<%
										SysAttMain sysAttMain = (SysAttMain) pageContext
														.getAttribute("sysAttMain");
										String path = SysAttViewerUtil.getViewerPath(
												sysAttMain, request);
										if (StringUtil.isNotNull(path)){
											pageContext.setAttribute("hasThumbnail", "true");
											pageContext.setAttribute("hasViewer", "true");
										}
										pageContext.setAttribute("_sysAttMain", sysAttMain);
									%>
						 		</c:forEach>
						 	</c:if>
						 	
						 	<div class="muiReadButton" >
	        					<c:import url="/sys/attachment/mobile/import/viewContent.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="kmImeetingSummaryForm"></c:param>
									<c:param name="fdKey" value="editonline"></c:param>
								</c:import> 
	        				</div>
						</c:if>
						
						<%--附件--%>
						<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmImeetingSummaryForm" />
							<c:param name="fdKey" value="attachment" />
							<c:param name="fdModelId" value="${JsParam.fdId }" />
							<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
						</c:import> 
						
						<%--查阅会议纪要--%>
						<c:if test="${not empty kmImeetingSummaryForm.fdMeetingId}">
						<kmss:auth
							requestURL="/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=${kmImeetingSummaryForm.fdMeetingId}"
							requestMethod="GET">
							<div class="muiReadButton" onclick="window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId=${kmImeetingSummaryForm.fdMeetingId}','_self')">
	        					<bean:message bundle="km-imeeting"  key="mobile.kmImeetingSummary.viewNotify"/>
	        				</div>
						</kmss:auth>
						</c:if>
						
					</div>
					
						<div data-dojo-type="mui/panel/Content" class="muiImeetingPanelContent" data-dojo-props="title:'流程记录',icon:''">
							<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId }"/>
								<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary"/>
								<c:param name="formBeanName" value="kmImeetingSummaryForm"/>
							</c:import>
						</div>
				</div>
			</div>
			
			
			<%--底部按钮 --%>
			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
			      editUrl="javascript:building();"
				  formName="kmImeetingSummaryForm"
				  viewName="lbpmView"
				  allowReview="true">
				<template:replace name="flowArea">
					<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
					  <c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary"></c:param>
					  <c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}"></c:param>
					  <c:param name="fdSubject" value="${kmImeetingSummaryForm.fdName}"></c:param>
					  <c:param name="showOption" value="label"></c:param>
				  	</c:import>
				</template:replace>
				<template:replace name="publishArea">
					<c:import url="/sys/bookmark/mobile/import/view.jsp" charEncoding="UTF-8">
					  <c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary"></c:param>
					  <c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}"></c:param>
					  <c:param name="fdSubject" value="${kmImeetingSummaryForm.fdName}"></c:param>
					  <c:param name="showOption" value="label"></c:param>
				  	</c:import>
				</template:replace>
			</template:include>
		</div>
			
		<%--流程信息 --%>	
		<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingSummaryForm" />
			<c:param name="fdKey" value="ImeetingSummary" />
			<c:param name="viewName" value="lbpmView" />
			<c:param name="backTo" value="scrollView" />
		</c:import>	
		
		<!-- 钉钉图标 -->
		<kmss:ifModuleExist path="/third/ding">
			<c:import url="/third/ding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingSummaryForm" />
			</c:import>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/third/lding">
			<c:import url="/third/lding/import/ding_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingSummaryForm" />
			</c:import>
		</kmss:ifModuleExist>
		<!-- 钉钉图标 end -->
		

	</template:replace>


</template:include>
<script type="text/javascript">
require(['dojo/_base/array','dojo/topic','dojo/query','dijit/registry','dojo/dom-geometry','mui/rtf/RtfResize'],
		function(array,topic,query,registry,domGeometry,RtfResize){
	
	//切换标签重新计算高度
	var _position=domGeometry.position(query('#fixed')[0]),
		_scrollTop=0;
	topic.subscribe("/mui/list/_runSlideAnimation",function(srcObj, evt) {
		_scrollTop= Math.abs(evt.to.y);
	});
	topic.subscribe("/mui/navitem/_selected",function(){
		var view=registry.byId("scrollView");
		
		if(_scrollTop > _position.y){
			view.handleToTopTopic(null,{
				y: 0 - (_position.y)
			});
		}
	});
	
	//切换标签时resize rtf中的表格
	var hasResize=false;
	topic.subscribe("/mui/navitem/_selected",function(widget,args){
		setTimeout(function(){
			var processView=registry.byId("processView");
			if(!hasResize && processView && processView.isVisible() ){
				var arr=query('.muiFieldRtf');
				array.forEach(arr,function(item){
					new RtfResize({
						containerNode:item
					});
				});
				hasResize=true;
			}
		},100);
	});
	
	//阅读word形式的纪要
	window.readWord=function(){
		var type = "${_sysAttMain.fdContentType}";
		var href = '${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${_sysAttMain.fdId}&viewer=aspose_mobilehtmlviewer';
		var downLoadUrl = "${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${_sysAttMain.fdId}";
		var hasViewer = "${hasViewer }";
		if(hasViewer !='true' && type !='img'){
			href = downLoadUrl;
		}
		window.location.href=href;
	};
	
	
});
$(document).ready(function(){
	<kmss:ifModuleExist path="/sys/task/">
		var ul = $('ul[data-dojo-type="sys/task/mobile/resource/js/OverflowTabBar"]');
		if(ul.length&&ul.length>0){
			$('.muiSummaryPanelContent').css('margin','0px');
			ul.css('background-color','#eeeeee');
		}
	</kmss:ifModuleExist>
});
</script>


