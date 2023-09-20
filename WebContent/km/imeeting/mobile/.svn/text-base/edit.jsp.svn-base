<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@page import="com.landray.kmss.km.imeeting.util.KmImeetingConfigUtil"%>
<%@page import="com.landray.kmss.km.imeeting.util.KKUtil"%>
<%@page import="java.util.Map"%>
<%
	String modelName = "com.landray.kmss.km.imeeting.model.KmImeetingConfig";
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map map = sysAppConfigService.findByKey(modelName);
	request.setAttribute("kmImeetingConfig", map);
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	request.setAttribute("isVideoMeetingEnable", KmImeetingConfigUtil.isVideoMeetingEnable());
%>
<%-- 会议只支持极简形式的创建（即无模板的形式） --%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/edit.css" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/edit_topic.css" />
	</template:replace>
	<%--页签名--%>
	<template:replace name="title">
		<c:choose>
			<c:when test="${ kmImeetingMainForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${kmImeetingMainForm.fdName} - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	
	<%-- 内容区 --%>
	<template:replace name="content">
	
	<xform:config  orient="vertical">
		<html:form action="/km/imeeting/km_imeeting_main/kmImeetingMain.do">
			<html:hidden property="fdId" />
			<html:hidden property="authAreaId" />
			<html:hidden property="docStatus" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdNotifyerId" />
			<html:hidden property="fdChangeMeetingFlag" />
			<html:hidden property="fdChangeType" value = "${kmImeetingMainForm.fdChangeType}" />
			<html:hidden property="syncDataToCalendarTime" value="sendNotify"/>
			<html:hidden property="fdSummaryFlag" />
			<html:hidden property="method_GET" />
			<html:hidden property="fdModelId" value = "${kmImeetingMainForm.fdModelId}" />
			<html:hidden property="fdModelName" value = "${kmImeetingMainForm.fdModelName}" />
			<html:hidden property="fdPhaseId" value = "${kmImeetingMainForm.fdPhaseId}" />
			<html:hidden property="fdWorkId" value = "${kmImeetingMainForm.fdWorkId}" />
			<html:hidden property="fdTemplateId"/>
			<html:hidden property="beforeChangeContent"/>
			<html:hidden property="fdRecurrenceStr" />
			<html:hidden property="bookId" value="${HtmlParam.bookId }"/>
			<%-- 会议历史操作信息 --%>
			<div style="display: none;">
				<c:forEach items="${kmImeetingMainForm.kmImeetingMainHistoryForms}"  var="kmImeetingMainHistoryItem" varStatus="vstatus">
					<input type="hidden" name="kmImeetingMainHistoryForms[${vstatus.index}].fdId" value="${kmImeetingMainHistoryItem.fdId}" />
				</c:forEach>
			</div>
			<script type="text/javascript">
			   	require(["dojo/store/Memory","dojo/topic","dijit/registry"],function(Memory,topic,registry){
			   		var navData = [{'text':'01    <bean:message bundle="km-imeeting" key="mobile.kmImeetingSummary.nav.information" />',
			   			'moveTo':'scrollView','selected':true},{'text':'02    <bean:message bundle="km-imeeting" key="kmImeetingTemplate.mainflow" />',
				   		'moveTo':'lbpmView'}]
			   		window._narStore = new Memory({data:navData});
			   		var changeNav = function(view){
			   			var wgt = registry.byId("_flowNav");
			   			for(var i=0;i<wgt.getChildren().length;i++){
			   				var tmpChild = wgt.getChildren()[i];
			   				if(view.id == tmpChild.moveTo){
			   					tmpChild.beingSelected(tmpChild.domNode);
			   					return;
			   				}
			   			}
			   		}
			   		topic.subscribe("mui/form/validateFail",function(view){
			   			changeNav(view);
			   		});
					topic.subscribe("mui/view/currentView",function(view){
						changeNav(view);
			   		});
			   	});
		   </script>
		   <c:if test="${kmImeetingMainForm.fdTemplateId!=null}">
	   			<div data-dojo-type="mui/fixed/Fixed" class="muiFlowEditFixed">
					<div data-dojo-type="mui/fixed/FixedItem" class="muiFlowEditFixedItem">
						<div data-dojo-type="mui/nav/NavBarStore" id="_flowNav" data-dojo-props="store:_narStore">
						</div>
					</div>
				</div>
			</c:if>
			<div id="scrollView"  class="gray" data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin">
				<div id="meetingInfo" class="meetingBaseInfo" data-dojo-type="mui/panel/Content">
					<div class="muiFormContent">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
						
							<c:if test="${kmImeetingMainForm.fdChangeMeetingFlag=='true' }">
								<tr>
									<td>
										<xform:textarea mobile="true" property="changeMeetingReason" htmlElementProperties="data-actor-expand='true'"
											required="true" showStatus="edit" validators="maxLength(1500)"></xform:textarea>
										<html:hidden property="beforeChangeContent"/>
									</td>
								</tr>
							</c:if>
						
							<%-- 会议名称 --%>
							<tr>
								<td>
									<xform:text property="fdName" mobile="true" required="true" />	
								</td>
							</tr>
							<c:choose>
								<c:when test="${kmImeetingMainForm.fdTemplateId ==null || kmImeetingMainForm.fdTemplateId ==null }">
									<tr>
										<td class="muiTitle">
											<div class="kmMeetingSwitchContainer">
												<c:set var="needFeedback" value="on"></c:set>
												<c:if test="${empty kmImeetingMainForm.fdNeedFeedback or kmImeetingMainForm.fdNeedFeedback == false }">
													<c:set var="needFeedback" value="off"></c:set>
												</c:if>
												<div class="isVideo_Switch">
												<div  class="isVideo_Text muiFormEleTitle">
												<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedFeedback" />
												</div>
												<div data-dojo-type="mui/form/Switch"
													 data-dojo-mixins="km/imeeting/mobile/resource/js/SwitchMixin"
													 data-dojo-props="orient:'vertical',leftLabel:'',rightLabel:'',value:'${needFeedback}',property:'fdNeedFeedback'"
													 class="kmMeetingSwitch">
												</div>
												</div>
											</div>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<html:hidden property="fdNeedFeedback" value="true"/>
								</c:otherwise>
							</c:choose>
							
							<%-- 会议时间 --%>
							<tr>
								<td>
									<xform:datetime htmlElementProperties="id='fdHoldDate'" property="fdHoldDate" dateTimeType="datetime" mobile="true" required="true" validators="after beforeFinishDate" onValueChange="handleDurationChange"></xform:datetime>
								</td>
							</tr>
							<tr>
								<td>
									<xform:datetime htmlElementProperties="id='fdFinishDate'" property="fdFinishDate" dateTimeType="datetime" mobile="true" required="true" validators="after afterHoldDate" onValueChange="handleDurationChange"></xform:datetime>
								</td>
							</tr>
							<%-- 会议历时 --%>
							<tr>
								<td>
									<xform:text property="fdHoldDurationHour" mobile="true" 
										showStatus="readOnly" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHoldDuration') }(${lfn:message('date.interval.hour') })"/>	
								</td>
							</tr>
							<%
							 	if(KmImeetingConfigUtil.isBoenEnable()|| KKUtil.isKkVideoMeetingEnable()){
							 %>
							<tr class="feedBackDeadlineTr"> 
								<td>
									<xform:datetime htmlElementProperties="id='fdFeedBackDeadline'" subject="${lfn:message('km-imeeting:kmImeetingMain.fdFeedBackDeadline') }" property="fdFeedBackDeadline" dateTimeType="datetime" mobile="true" required="true" validators="after valDeadline"></xform:datetime>
								</td>
							</tr>
							<%} %>
							<c:if test="${kmImeetingMainForm.isCycle eq 'true' || kmImeetingMainForm.fdChangeType eq 'after' || ( not empty kmImeetingMainForm.fdRepeatType && empty kmImeetingMainForm.fdChangeType)}">
							 	<c:if test="${kmImeetingConfig.useCyclicity eq '2' || kmImeetingConfig.useCyclicity eq '3' && fn:contains(kmImeetingConfig.useCyclicityPersonId,userId) == true}">
								<tr>
									<td>
										<!-- 重复规则 -->
										<div id="recurrenceView" data-dojo-type="dojox/mobile/View">
											<div>
												<xform:select
													subject="${lfn:message('km-imeeting:kmImeetingMain.fdRecurrenceType') }"
													property="RECURRENCE_FREQ" showPleaseSelect="false"
													showStatus="edit" htmlElementProperties="id='recurrence_freq'"
													mobile="true">
													<xform:enumsDataSource enumsType="km_imeeting_recurrence_freq" />
												</xform:select>
											</div>
					
											<div class="moreset">
												<div data-dojo-type="mui/form/Select"
													data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
													         store:[<c:forEach begin="1" end="50" varStatus="index">
									                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="daily"/>',value:'${index.count}'}
									                            <c:if test="${ index.count!=50 }">,</c:if>
									                        </c:forEach>]">
												</div>
											</div>
											<div class="moreset">
												<div data-dojo-type="mui/form/Select"
													data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
													         store:[<c:forEach begin="1" end="50" varStatus="index">
									                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="week"/>',value:'${index.count}'}
									                            <c:if test="${ index.count!=50 }">,</c:if>
									                        </c:forEach>]">
												</div>
											</div>
											<div class="moreset">
												<div data-dojo-type="mui/form/Select"
													data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
													         store:[<c:forEach begin="1" end="50" varStatus="index">
									                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="month"/>',value:'${index.count}'}
									                            <c:if test="${ index.count!=50 }">,</c:if>
									                        </c:forEach>]">
												</div>
											</div>
											<div class="moreset">
												<div data-dojo-type="mui/form/Select"
													data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting"  key="kmImeetingMain.recurrenceInterval"/>',value:'${kmImeetingMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
													         store:[<c:forEach begin="1" end="50" varStatus="index">
									                            {text:'<bean:message bundle="km-imeeting" key="each"/>${index.count}<bean:message bundle="km-imeeting" key="year"/>',value:'${index.count}'}
									                            <c:if test="${ index.count!=50 }">,</c:if>
									                        </c:forEach>]">
												</div>
											</div>
											<div class="moreset">
												<div data-dojo-type="mui/form/Select"
													data-dojo-props="name:'RECURRENCE_WEEKS',showStatus:'edit',subject:'<bean:message bundle="km-imeeting" key="kmImeetingMain.recurrenceTimeType" />',value:'${kmImeetingMainForm.RECURRENCE_WEEKS }',showPleaseSelect:false,orient:'vertical',
															 store:[{text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Sunday"/>',value:'SU'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Monday"/>',value:'MO'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Tuesday"/>',value:'TU'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Wednesday"/>',value:'WE'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Thursday"/>',value:'TH'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Friday"/>',value:'FR'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.week.Saturday"/>',value:'SA'}]"></div>
											</div>
											<div class="moreset">
												<div data-dojo-type="mui/form/Select"
													data-dojo-props="name:'RECURRENCE_MONTH_TYPE',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting" key="kmImeetingMain.recurrenceTimeType" />',value:'${kmImeetingMainForm.RECURRENCE_MONTH_TYPE }',showPleaseSelect:false,orient:'vertical',
															 store:[{text:'<bean:message bundle="km-imeeting" key="recurrence.month.type.month"/>',value:'month'},
															 {text:'<bean:message bundle="km-imeeting" key="recurrence.month.type.week"/>',value:'week'}]">
												</div>
											</div>
											<div class="moreset">
												<div data-dojo-type="mui/form/Select"
													data-dojo-props="name:'RECURRENCE_END_TYPE',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-imeeting" key="kmImeetingMain.recurrenceEndType" />',value:'${kmImeetingMainForm.RECURRENCE_END_TYPE }',showPleaseSelect:false,orient:'vertical',
															 store:[{text:'<bean:message bundle="km-imeeting" key="recurrence.end.type.never"/>',value:'NEVER'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.end.type.count" />',value:'COUNT'},
															 {text:'<bean:message bundle="km-imeeting" key="mobile.recurrence.end.type.until" />',value:'UNTIL'}]">
												</div>
												<div class="endType">
													<xform:text showStatus="edit" validators="count min(1)"
														property="RECURRENCE_COUNT"
														onValueChange="reCount(this.value);" mobile="true" />
												</div>
												<div class="endType">
													<xform:datetime showStatus="edit" property="RECURRENCE_UNTIL"
														onValueChange="untilChange" dateTimeType="date" mobile="true" />
												</div>
											</div>
											<div class="moreset">
												<span style="padding-left: 1rem;"><bean:message
														bundle="km-imeeting" key="kmImeetingMain.summary" />:</span> <input
													type="hidden" name="RECURRENCE_SUMMARY" />
												<div id="summary" style="margin-left: 1rem"></div>
											</div>
										</div>
									</td>
								</tr>
								</c:if>
							</c:if>
							<%if(KmImeetingConfigUtil.isVideoMeetingEnable()){ %>
							<c:choose>
								<c:when test="${kmImeetingMainForm.fdTemplateId !=null}">
									<tr>
										<td class="muiTitle">
											<div class="kmMeetingSwitchContainer">
												<c:set var="isVideo" value="on"></c:set>
												<c:if test="${empty kmImeetingMainForm.fdIsVideo or kmImeetingMainForm.fdIsVideo == false }">
													<c:set var="isVideo" value="off"></c:set>
												</c:if>
												<div class="isVideo_Switch">
												<div  class="isVideo_Text muiFormEleTitle">
													<bean:message bundle="km-imeeting" key="kmImeetingMain.fdIsVideo"/>
												</div>
												<div data-dojo-type="mui/form/Switch"
													 data-dojo-mixins="km/imeeting/mobile/resource/js/SwitchMixin"
													 data-dojo-props="orient:'vertical',leftLabel:'',rightLabel:'',value:'${isVideo}',property:'fdIsVideo'"
													 class="kmMeetingSwitch">
												</div>
												</div>
											</div>
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<html:hidden property="fdIsVideo" value="true"/>
								</c:otherwise>
							</c:choose>
							<tr>
								<td class="muiTitle">
									<div class="kmMeetingSwitchContainer">
										<c:set var="needplace" value="on"></c:set>
										<c:if test="${empty kmImeetingMainForm.fdNeedPlace or kmImeetingMainForm.fdNeedPlace == false }">
											<c:set var="needplace" value="off"></c:set>
										</c:if>
										<div class="needPlace_Switch">
										<div  class="needPlace_Text muiFormEleTitle">
											<bean:message bundle="km-imeeting" key="kmImeetingMain.fdNeedPlace"/>
										</div>
										<div data-dojo-type="mui/form/Switch"
											 data-dojo-mixins="km/imeeting/mobile/resource/js/SwitchMixin"
											 data-dojo-props="orient:'vertical',leftLabel:'',rightLabel:'',value:'${needplace}',property:'fdNeedPlace'"
											 class="kmMeetingSwitch">
										</div>
										</div>
									</div>
								</td>
							</tr>
							<%}else{ %>
								<html:hidden property="fdIsVideo" value="false"/>
								<html:hidden property="fdNeedPlace" value="true"/>
							<%} %>
							<%-- 会议地点 --%>
							<tr class="placeResTr">
								<td class="muiTitle">
									<c:choose>
										<c:when test="${fdNeedMultiRes}">
											<div>
												<div id="placeComponent"
													data-dojo-type="km/imeeting/mobile/resource/js/PlaceComponent"
													data-dojo-props='
														"subject":"${lfn:message("km-imeeting:kmImeetingMain.fdMainPlace") }",
														"idField":"fdPlaceId",
														"nameField":"fdPlaceName",
														"showStatus":"edit",
														"curIds":"${kmImeetingMainForm.fdPlaceId}",
														"curNames":"${kmImeetingMainForm.fdPlaceName}",
														"validate":"validateUserTime",
														orient:"vertical",
														required:false'>
												</div>
												<div style="margin-top:10px">
													<xform:text validators="validateplace" property="fdOtherPlace" style="width:46%;" mobile="true" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace') }'"></xform:text>
												</div>
												<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"/>
											</div>
											<div>
												<div id="vicePlaceComponent"
													data-dojo-type="km/imeeting/mobile/resource/js/PlaceComponent"
													data-dojo-props='
														"subject":"${lfn:message("km-imeeting:kmImeetingMain.fdVicePlaces") }",
														"idField":"fdVicePlaceIds",
														"nameField":"fdVicePlaceNames",
														"validateInputName":"fdVicePlaceUserTimes",
														"showStatus":"edit",
														"curIds":"${kmImeetingMainForm.fdVicePlaceIds}",
														"curNames":"${kmImeetingMainForm.fdVicePlaceNames}",
														"validate":"validateViceUserTimes",
														orient:"vertical",
														isMul: "true",
														modelName: "com.landray.kmss.km.imeeting.model.KmImeetingRes",
														required:false'>
												</div>
												<div>
													<xform:text property="fdOtherVicePlace" style="width:46%;" mobile="true" subject="${lfn:message('km-imeeting:kmImeetingMain.fdOtherVicePlace') }"  htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherVicePlace') }'"></xform:text>
												</div>
												<input type="hidden" name="fdVicePlaceUserTimes" value="${ kmImeetingMainForm.fdVicePlaceUserTimes }"/>
											</div>
										</c:when>
										<c:otherwise>
											<div id="placeComponent"
												data-dojo-type="km/imeeting/mobile/resource/js/PlaceComponent"
												data-dojo-props='
													"subject":"${lfn:message("km-imeeting:kmImeetingMain.fdPlace") }",
													"idField":"fdPlaceId",
													"nameField":"fdPlaceName",
													"showStatus":"edit",
													"curIds":"${kmImeetingMainForm.fdPlaceId}",
													"curNames":"${kmImeetingMainForm.fdPlaceName}",
													"validate":"validateUserTime",
													"orient":"vertical",
													"required":false'></div>
											<div id="otherPlace" style="margin-top:10px">
												<xform:text validators="validateplace"  property="fdOtherPlace" style="width:46%;" mobile="true" subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherMainPlace') }'"></xform:text>
											</div>
											<input type="hidden" name="fdPlaceUserTime" value="${ kmImeetingMainForm.fdUserTime}"
												subject="${lfn:message('km-imeeting:kmImeetingMain.fdPlace') }"/>
										</c:otherwise>
									</c:choose>
								
								</td>
							</tr>
							<%-- 会议主持人 --%>
							<tr>
								<td id="presidtor">
									<xform:address propertyName="fdHostName" propertyId="fdHostId" subject="${lfn:message('km-imeeting:kmImeetingMain.fdHost') }"
									orgType="ORG_TYPE_PERSON" required="false" mobile="true"></xform:address>
									<div style="margin-top:10px">
										<xform:text property="fdOtherHostPerson" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherHostPerson') }'" style="width:47%;" mobile="true" />
									</div>
								</td>
							</tr>
							<%-- 参会人 --%>
							<tr>
								<td class="muiTitle" id="attendPersons">
										<xform:address propertyId="fdAttendPersonIds" showStatus="edit" propertyName="fdAttendPersonNames"
										htmlElementProperties="id='fdAttendPersonIds'" subject="${lfn:message('km-imeeting:kmImeetingMain.fdAttendPersons')}"
										orgType='${kmImeetingMainForm.fdTemplateId!=null?"ORG_TYPE_ALL":"ORG_TYPE_PERSON"}' mulSelect="true"  mobile="true" validators="validateattend"></xform:address>
										<%--外部参加人员--%>
										<xform:textarea  property="fdOtherAttendPerson" htmlElementProperties="placeholder='${lfn:message('km-imeeting:kmImeetingMain.fdOtherAttendPerson') }'" validators="validateattend maxLength(1500)" mobile="true"/>
										<span class="txtstrong" style="color: red;position: absolute;z-index: 8;left: 95%;">*</span>
								</td>
							</tr>
							<c:if test="${kmImeetingMainForm.fdTemplateId!=null}">
								<%
								 	if(KmImeetingConfigUtil.isBoenEnable()){
								 %>
							 	<tr>
									<td class="muiTitle" id="controlPersons">
										<xform:address propertyId="fdControlPersonId" showStatus="edit" propertyName="fdControlPersonName" 
											subject="${lfn:message('km-imeeting:kmImeetingMain.fdControlPerson')}" orgType="ORG_TYPE_PERSON" mulSelect="false"  mobile="true" required="true"></xform:address>
									</td>
							 	</tr>
							 	<tr>
							 		<%--监票人--%>
									<td class="muiTitle" id="ballotPersons">
									<xform:address propertyId="fdBallotPersonIds" showStatus="edit" propertyName="fdBallotPersonNames" 
											subject="${lfn:message('km-imeeting:kmImeetingMain.fdBallotPerson')}" orgType="ORG_TYPE_PERSON" mulSelect="false"  mobile="true"></xform:address>
									</td>
							 	</tr>
							 	<%
								 	}
								 %>
							 </c:if>
						</table>
					</div>
				</div>
				<!-- 会议议题 -->
				<c:if test="${kmImeetingMainForm.fdTemplateId!=null}">
					<div style="background-color: #fff;padding: 5px 0;">
						<p class="headerTitle" style="display: block;">
							<bean:message bundle="km-imeeting" key="kmImeetingAgenda.info"/>
						</p>
						<c:choose>
					  		<c:when test="${kmImeetingMainForm.method_GET == 'add'}">
					  			<%
					 			if("true".equals(KmImeetingConfigUtil.isTopicMng())){
						 		%>
						 			<html:hidden property="fdIsTopic" value="1"/>
						 			<%@include file="/km/imeeting/mobile/agenda_editTopic.jsp"%>
						 		<%}else{ %>
						 			<html:hidden property="fdIsTopic" value="0"/>
						 			<%@include file="/km/imeeting/mobile/agenda_edit.jsp"%>
						 		<%} %>
					  		</c:when>
					  		<c:otherwise>
								<c:choose>
						 			<c:when test="${kmImeetingMainForm.fdIsTopic eq 'true'}">
						 				<html:hidden property="fdIsTopic" value="1"/>
						 			   <%@include file="/km/imeeting/mobile/agenda_editTopic.jsp"%>
						 			</c:when>
						 			<c:otherwise>
						 				<html:hidden property="fdIsTopic" value="0"/>
						 				<%@include file="/km/imeeting/mobile/agenda_edit.jsp"%>
						 			</c:otherwise>
						 		</c:choose>
					  		</c:otherwise>
					  	</c:choose>
					</div>
					<div class="relativeData">
						<div style="margin:0 0rem 1rem 0;flex:1;"><bean:message bundle="km-imeeting" key="kmImeetingMain.attachment"/></div>
						<div style="flex:4;">
							<c:import url="/sys/attachment/mobile/import/edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="attachment" />
								<c:param name="formName" value="kmImeetingMainForm"/>
							</c:import>
						</div>
					</div>
				</c:if>
				<!-- 会议目的 -->
				<div class="meetingBaseInfo meetingBaseAim" data-dojo-type="mui/panel/Content">
					<xform:textarea  property="fdMeetingAim" mobile="true" placeholder='${lfn:message("km-imeeting:kmImeetingMain.FillInAim") }'></xform:textarea>
				</div>
				<%-- 通知方式，取系统默认的 --%>
				<div style="display: none;">
					<xform:radio property="fdNotifyType" showStatus="edit">
		   				<xform:enumsDataSource enumsType="km_imeeting_main_fd_notify_type" />
					</xform:radio>
					<kmss:editNotifyType property="fdNotifyWay" />
				</div>
					<div class="meetingMoreOpt">
						<bean:message bundle="km-imeeting" key="kmImeetingMain.tip.addMore"/>
						<i class="meetingMoreIcon mui mui-down-n"></i>
					</div>
					<div class="meetingMoreInfo" data-dojo-type="mui/panel/Content" style="background-color: white;">
						<div class="muiFormContent">
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<%-- 纪要录入人--%>
								<tr>
									<td>
										<xform:address propertyId="fdSummaryInputPersonId" propertyName="fdSummaryInputPersonName" 
											subject="${lfn:message('km-imeeting:kmImeetingMain.fdSummaryInputPerson')}"
											orgType="ORG_TYPE_PERSON" mobile="true"></xform:address>
									</td>
								</tr>
								<c:if test="${kmImeetingMainForm.fdTemplateId!=null}">
								<!-- 纪要完成时间 -->
									<tr>
										<td>
											<xform:datetime  property="fdSummaryCompleteTime" dateTimeType="datetime" validators="validateSummaryTime" mobile="true"></xform:datetime>
										</td>
									</tr>
								</c:if>
								<%-- 会议组织人--%>
								<tr>
									<td>
										<xform:address propertyName="fdEmceeName" propertyId="fdEmceeId" 
											subject="${lfn:message('km-imeeting:kmImeetingTemplate.fdEmcee')}"
											orgType="ORG_TYPE_PERSON" mobile="true"></xform:address>
										<xform:address propertyName="fdAssistPersonIds" propertyId="fdAssistPersonNames" showStatus="noShow"
											orgType="ORG_TYPE_PERSON" mobile="true"></xform:address>
										<xform:text property="fdArrange" showStatus="noShow" mobile="true"></xform:text>
										<xform:text property="fdOtherAssistPersons" showStatus="noShow" mobile="true"></xform:text>
									</td>
								</tr>
								<%-- 会议组织部门--%>
								<tr>
									<td>
										<xform:address propertyName="docDeptName" propertyId="docDeptId" 
											subject="${lfn:message('km-imeeting:kmImeetingMain.docDept')}"
											orgType="ORG_TYPE_DEPT" mobile="true" required="true"></xform:address>
									</td>
								</tr>
								<%-- 会议辅助设备--%>
								<tr class="placeResTr">
									<td>
										<div id="equipmentComponent"
												data-dojo-type="km/imeeting/mobile/resource/js/EquipmentComponent"
												data-dojo-props='"subject":"${lfn:message("km-imeeting:kmImeetingMain.kmImeetingEquipment") }","idField":"kmImeetingEquipmentIds","nameField":"kmImeetingEquipmentNames","showStatus":"edit","curIds":"${kmImeetingMainForm.kmImeetingEquipmentIds}","curNames":"${kmImeetingMainForm.kmImeetingEquipmentNames}",orient:"vertical"'></div>
									</td>
								</tr>
								<%-- 会议辅助服务--%>
								<tr class="placeResTr">
									<td>
										<xform:checkbox property="kmImeetingDeviceIds" showStatus="edit"  subject="${lfn:message('km-imeeting:kmImeetingMain.kmImeetingDevices')}" mobile="true">
											<xform:beanDataSource serviceBean="kmImeetingDeviceService" selectBlock="fdId,fdName" whereBlock="kmImeetingDevice.fdIsAvailable=true" orderBy="fdOrder" />
										</xform:checkbox>
									</td>
								</tr>
							</table>	
						</div>
					</div>	
				<c:if test="${kmImeetingMainForm.fdTemplateId!=null}">
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
					  	<li data-dojo-type="mui/tabbar/TabBarButton"
					  		data-dojo-props='colSize:2,moveTo:"lbpmView",transition:"slide"'>
						  	<bean:message bundle="km-imeeting" key="button.next"/>
					  	</li>
					</ul>
				</c:if>
			</div>
			<c:if test="${kmImeetingMainForm.fdTemplateId!=null}">
				<c:import url="/sys/lbpmservice/mobile/import/edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingMainForm" />
					<c:param name="fdKey" value="ImeetingMain" />
					<c:param name="viewName" value="lbpmView" />
					<c:param name="backTo" value="scrollView" />
					<c:param name="onClickSubmitButton" value="imeeting_submit();" />
				</c:import>
			</c:if>
		<%-- 	<!-- 会议组织人 -->
			<input type="hidden" value="${kmImeetingMainForm.fdEmceeId}" name="fdEmceeId"/>
			<input type="hidden" value="${kmImeetingMainForm.fdEmceeName}" name="fdEmceeName"/>
			<!-- 会议组织部门 -->
			<input type="hidden" value="${kmImeetingMainForm.docDeptId}" name="docDeptId"/>
			<input type="hidden" value="${kmImeetingMainForm.docDeptName}" name="docDeptName"/>
			<!-- 纪要人员 -->
			<input type="hidden" value="${kmImeetingMainForm.fdSummaryInputPersonId}" name="fdSummaryInputPersonId"/>
			<input type="hidden" value="${kmImeetingMainForm.fdSummaryInputPersonName }" name="fdSummaryInputPersonName"/> --%>
			<!-- 权限继承 -->
				<!-- 可以阅读者 -->
				<input type="hidden" value="${kmImeetingMainForm.authReaderIds}" name="authReaderIds" />
				<input type="hidden" value="${kmImeetingMainForm.authEditorIds}" name="authEditorIds" />
				<!-- 附件可拷贝者 -->
				<input type="hidden" value="${kmImeetingMainForm.authAttCopyIds}" name="authAttCopyIds"/>
				<!-- 附件可下载者 -->
				<input type="hidden" value="${kmImeetingMainForm.authAttDownloadIds}" name="authAttDownloadIds"/>
				<!--  -->
				<input type="hidden" value="${kmImeetingMainForm.authAttPrintIds}" name="authAttPrintIds"/>
 			<!-- 日程继承 -->
			<c:forEach items="${kmImeetingMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="i" varStatus="vs">
				<input type="hidden" value="${kmImeetingMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[vs.index].fdId }" 
					name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vs.index}].fdId" />
				<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vs.index}].fdModelName" 
				value="com.landray.kmss.km.imeeting.model.KmImeetingMain">
				<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vs.index}].fdNotifyType" value="todo">
				<input  type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vs.index}].fdBeforeTime" 
				value="${kmImeetingMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[vs.index].fdBeforeTime}" >
				<input type="hidden" name="sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[${vs.index}].fdTimeUnit" 
				value="${kmImeetingMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList[vs.index].fdTimeUnit}" />
			</c:forEach>
			<!-- 发布隐藏机制 -->
			<c:import url="/sys/news/import/sysNewsPublishMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="fdKey" value="ImeetingMain" />
				<c:param name="isShow" value="false" />
			</c:import>
			
			<c:if test="${kmImeetingMainForm.fdTemplateId==null}">
				<input name="isCloud" type="hidden" value="1"/>
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				  	<c:choose>
				  		<c:when test="${kmImeetingMainForm.method_GET == 'edit'}">
							<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
							  	data-dojo-props='colSize:2,href:"javascript:commitMethod(\"update\");",transition:"slide"'>
					  			<bean:message bundle="km-imeeting" key="kmImeeting.changeMeeting"/>
					  		</li>
				  		</c:when>
				  		<c:otherwise>
							<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
							  	data-dojo-props='colSize:2,href:"javascript:commitMethod(\"save\");",transition:"slide"'>
							  	<bean:message bundle="km-imeeting" key="kmImeetingMain.button.createCloudMeeting"/>
							 </li>
				  		</c:otherwise>
				  	</c:choose>
				</ul> 	
			</c:if>
				
		</html:form>	
	</xform:config>
	</template:replace>

</template:include>
<script>
	window.imeeting_submit=function(){
		var requestMethod = '${kmImeetingMainForm.method_GET }';
		var noTemplate = '${kmImeetingMainForm.fdTemplateId!=null}';
		var fdChangeFlag = "${kmImeetingMainForm.fdChangeMeetingFlag}";
		if(fdChangeFlag == "true"){
			window.commitMethod("updateChange",noTemplate);
		}else{
			if(requestMethod== 'edit'){
				window.commitMethod("update",noTemplate);
			}else{
				window.commitMethod("save",noTemplate);
			}
		}
	}
</script>
<%@ include file="/km/imeeting/mobile/edit_js.jsp"%>