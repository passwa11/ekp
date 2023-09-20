<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.DateUtil"%>
<%@ page import="com.landray.kmss.km.imeeting.forms.KmImeetingSummaryForm"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<mui:min-file name="mui-imeeting-summaryview.css"/>
		<mui:min-file name="mui-task-view.css"/>
	</template:replace>
	
	<template:replace name="title">
			<c:out value="${ kmImeetingTopicForm.docSubject}"></c:out>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView" class="gray" data-dojo-type="mui/view/DocScrollableView">
			<div class="muiTaskInfoBanner">
				<dl class="txtInfoBar">
					<dt>
						<%--名称--%>
						<xform:text property="docSubject"></xform:text>
					</dt>
				</dl>
			</div>
			
			<div data-dojo-type="mui/fixed/Fixed" id="fixed">
				<div data-dojo-type="mui/fixed/FixedItem">
					<%--切换页签--%>
					<div class="muiHeader">
						<div
							data-dojo-type="mui/nav/MobileCfgNavBar" 
							data-dojo-props="defaultUrl:'/km/imeeting/mobile/topic_view_nav.jsp' ">
						</div>
					</div>
				</div>
			</div>
			
			<%--办结内容--%>
			<div id="contentView" data-dojo-type="dojox/mobile/View">
				<div data-dojo-type="mui/panel/AccordionPanel">
					<div class="muiFormContent muiFlowInfoW">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
							<tr>
								<td  class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.docSubject"/>
								</td>
								<td >
									<c:out value="${kmImeetingTopicForm.docSubject}"></c:out>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdTopicCategory"/>
								</td>
								<td  >	
									<xform:text property="fdTopicCategoryName" showStatus="view" mobile="true"></xform:text>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdNo"/>
								</td>
								<td>
									<c:if test="${kmImeetingTopicForm.docStatus==10 || kmImeetingTopicForm.docStatus==null || kmImeetingTopicForm.docStatus=='' }">
									   提交后自动生成
									</c:if>
									<c:if test="${kmImeetingTopicForm.fdNo!='' && kmImeetingTopicForm.fdNo!=null && kmImeetingTopicForm.docStatus!=10 }">
				                   	 	${ kmImeetingTopicForm.fdNo}
				                	</c:if>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdReporter"/>
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdReporterName}"></c:out>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdChargeUnit"/>
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdChargeUnitName}"></c:out>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdMaterialStaff"/>
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdMaterialStaffName}"></c:out>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdSourceSubject"/>
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdSourceSubject}"></c:out>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdAttendUnit"/>
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdAttendUnitNames}"></c:out>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdListenUnit"/>
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdListenUnitNames}"></c:out>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									议题正文
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdListenUnitNames}"></c:out>
									<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainonline"/>
										<c:param  name="fdMulti" value="false" />
										<c:param name="uploadAfterSelect" value="true" />  
										<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId }" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
									</c:import>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									议题材料
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.fdListenUnitNames}"></c:out>
									<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="attachment" />
										<c:param name="uploadAfterSelect" value="true" />  
										<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId }" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
									</c:import>
								</td>
							</tr>
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdContent"/>
								</td>
								<td>
									<xform:textarea property="fdContent" style="width:97.5%;height:80px" mobile="true" validators="senWordsValidator(kmImeetingTopicForm)"/>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.docCreator"/>
								</td>
								<td>
									 <c:out value="${kmImeetingTopicForm.docCreatorName}"></c:out>
								</td>
							</tr>
							
							<tr>
								<td class="muiTitle">
									<bean:message bundle="km-imeeting" key="kmImeetingTopic.docCreateTime"/>
								</td>
								<td>
									<c:out value="${kmImeetingTopicForm.docCreateTime}"></c:out>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			
			<%--流程记录--%>
			<div data-dojo-type="dojox/mobile/View" id="folwView">
				<c:import url="/sys/lbpmservice/mobile/lbpm_audit_note/import/view.jsp" charEncoding="UTF-8">
					<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId }"/>
					<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic"/>
					<c:param name="formBeanName" value="kmImeetingTopicForm"/>
				</c:import>
			</div>
			<template:include file="/sys/lbpmservice/mobile/import/tarbar.jsp" 
			      docStatus="${kmImeetingTopicForm.docStatus}" 
				  editUrl=""
				  formName="kmImeetingTopicForm"
				  viewName="lbpmView"
				  allowReview="true">
			</template:include>
		</div>
		
		<c:import url="/sys/lbpmservice/mobile/import/view.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="kmImeetingTopicForm" />
			<c:param name="fdKey" value="mainTopic" />
			<c:param name="viewName" value="lbpmView" />
			<c:param name="backTo" value="scrollView" />
		</c:import>
			
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
			var processView=registry.byId("folwView");
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
	
});

</script>


