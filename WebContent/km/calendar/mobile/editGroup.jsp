<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.landray.kmss.km.calendar.forms.KmCalendarMainForm"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%
	request.setAttribute("formatter", ResourceUtil.getString("date.format.date"));
	KmCalendarMainForm kmCalendarMainForm = (KmCalendarMainForm)request.getAttribute("kmCalendarMainForm");
	String subject = StringEscapeUtils.escapeJavaScript(kmCalendarMainForm.getDocSubject());
	request.setAttribute("subject", subject);
	String currentUserId = UserUtil.getUser().getFdId();
	pageContext.setAttribute("currentUserId", currentUserId);
%>
<template:include ref="mobile.edit" compatibleMode="true" >

	<template:replace name="title">
		<c:if test="${empty kmCalendarMainForm.docSubject }">
			<bean:message bundle="km-calendar" key="kmCalendarMain.opt.create"/>
		</c:if>
	
		<c:if test="${not empty kmCalendarMainForm.docSubject }">
			<bean:message bundle="km-calendar" key="kmCalendarMain.opt.edit"/>
		</c:if>
		
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-calendar-edit.css"/>
		<mui:min-file name="mui-calendar.js"/>
	</template:replace>

	<template:replace name="content">
	<xform:config orient="vertical">
		<html:form action="/km/calendar/km_calendar_main/kmCalendarMain.do" >
			<html:hidden property="fdId" />
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdRecurrenceStr" />
			<html:hidden property="fdType"  value="event"/>
			<html:hidden property="fdIsGroup" value="true"/>
			<html:hidden property="docOwnerId" value="multiCreate"/>
			<html:hidden property="authReaderIds"/>
			<html:hidden property="authEditorIds"/>
			<input type="hidden" name="personGroupId" value="${personGroupId}" />
			<input type="hidden" name="mainGroupId" value="${mainGroupId}" />
			<html:hidden property="method_GET"/>
			
			<div class="gray" data-dojo-type="mui/view/DocScrollableView" id="scrollView" data-dojo-mixins="mui/form/_ValidateMixin">
				<div class="muiImportBox">
					<div data-dojo-type="mui/form/Textarea" class="docSubjectTextArea"
						data-dojo-props="name:'docSubject',value:'${subject}',showStatus:'edit',subject:'<bean:message bundle="km-calendar" key="kmCalendarMain.docContent" />',required:true,validate:'required maxLength(500)',opt:false,orient:'vertical'">
					</div>
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<div data-dojo-type="mui/form/Input" class="locationInput"
									data-dojo-props="name:'fdLocation',value:'${kmCalendarMainForm.fdLocation }',showStatus:'edit',subject:'<bean:message bundle="km-calendar" key="kmCalendarMain.fdLocation" />',validate:'maxLength(200)',opt:false,orient:'vertical'">
								</div>
							</td>
						</tr>
						<tr>
							<td id="docOwnerIds">
								<xform:address isLoadDataDict="false" mobile="true"
									showStatus="readOnly" propertyId="docOwnerIds"
									propertyName="docOwnerNames" orgType='ORG_TYPE_ALL'
									subject="${lfn:message('km-calendar:kmCalendarShareGroup.fdGroupMemberIds')}" idValue="${docOwnerIds }" nameValue="${docOwnerNames }"></xform:address>
							</td>
						</tr>
					</table>
				</div>
				<div class="muiHeader muiCalendarHeader">
					<div
						data-dojo-type="mui/nav/MobileCfgNavBar" 
						data-dojo-mixins="km/calendar/mobile/resource/js/NavBarMixin"
						data-dojo-props="defaultUrl:'/km/calendar/mobile/edit_nav.jsp',height:'6rem',scrollDir:''"
						class="kmCalendarNavBar">
					</div>
				</div>
				
				<div id="dateView" data-dojo-type="dojox/mobile/View">
					<%-- 是否全天 --%>
					<div class="muiCalendarAllDay">
						<%-- <bean:message bundle="km-calendar" key="kmCalendarMain.allDay"/> --%>
						<c:set var="allday" value="on"></c:set>
						<c:if test="${empty kmCalendarMainForm.fdIsAlldayevent or kmCalendarMainForm.fdIsAlldayevent == false }">
							<c:set var="allday" value="off"></c:set>
						</c:if>
						<div class="isAllDay_Switch">
						<div  class="isAllDay_Text">
						<bean:message bundle="km-calendar" key="kmCalendarMain.allDay"/></div>
						<div data-dojo-type="mui/form/Switch"
							 data-dojo-mixins="km/calendar/mobile/resource/js/SwitchMixin"
							 data-dojo-props="orient:'vertical',leftLabel:'',rightLabel:'',value:'${allday}',property:'fdIsAlldayevent'"
							 class="kmCalendarSwitch">
						</div>
						</div>
					</div>
					<html:hidden property="startHour"/>
					<html:hidden property="startMinute"/>
					<%-- 开始日期 --%>
					<xform:datetime 
						property="docStartTime" 
						subject="${lfn:message('km-calendar:mobile.kmCalendarMain.docStartTime') }"
						dateTimeType="date" mobile="true" showStatus="edit" className=""
						required="true" htmlElementProperties="id='docStartTime',class='dateTimeTransition'">
					</xform:datetime>
					<%-- 开始时间 --%>
					<xform:datetime 
						property="docStartHHmm"
						subject="${lfn:message('km-calendar:kmCalendarMain.docStartTime') }"
						dateTimeType="time" mobile="true" showStatus="edit"
						required="true" htmlElementProperties="id='docStartHHmm'">
					</xform:datetime>
					<%-- 结束日期 --%>
					<html:hidden property="endHour"/>
					<html:hidden property="endMinute"/>
					<xform:datetime 
						property="docFinishTime" 
						subject="${lfn:message('km-calendar:mobile.kmCalendarMain.docFinishTime') }"
						dateTimeType="date" mobile="true" showStatus="edit" className="dateTimeTransition"
						required="true" htmlElementProperties="id='docFinishTime',class='dateTimeTransition'">
					</xform:datetime>
					<xform:datetime 
						property="docFinishHHmm" 
						subject="${lfn:message('km-calendar:kmCalendarMain.docFinishTime') }"
						dateTimeType="time" mobile="true" showStatus="edit"
						required="true" htmlElementProperties="id='docFinishHHmm'">
					</xform:datetime>
				</div>
				
				<div id="recurrenceView" data-dojo-type="dojox/mobile/View">
					<c:choose>
						<c:when test="${kmCalendarMainForm.fdIsLunar}">
							<div>
								<xform:select subject="${lfn:message('km-calendar:kmCalendarMain.fdRecurrenceType') }" property="RECURRENCE_FREQ_LUNAR" showPleaseSelect="false" showStatus="view" htmlElementProperties="id='recurrence_freq'" mobile="true">
									<xform:enumsDataSource enumsType="km_calendar_recurrence_freq_lunar" />
								</xform:select>
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<xform:select subject="${lfn:message('km-calendar:kmCalendarMain.fdRecurrenceType') }" property="RECURRENCE_FREQ" showPleaseSelect="false" showStatus="edit" htmlElementProperties="id='recurrence_freq'" mobile="true">
									<xform:enumsDataSource enumsType="km_calendar_recurrence_freq" />
								</xform:select>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="moreset">
						<div data-dojo-type="mui/form/Select"
					         data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-calendar"  key="kmCalendarMain.recurrenceInterval"/>',value:'${kmCalendarMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
					         store:[<c:forEach begin="1" end="50" varStatus="index">
	                            {text:'<bean:message bundle="km-calendar" key="each"/>${index.count}<bean:message bundle="km-calendar" key="daily"/>',value:'${index.count}'}
	                            <c:if test="${ index.count!=50 }">,</c:if>
	                        </c:forEach>]">
						</div>
					</div>
					<div class="moreset">
						<div data-dojo-type="mui/form/Select"
					         data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-calendar"  key="kmCalendarMain.recurrenceInterval"/>',value:'${kmCalendarMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
					         store:[<c:forEach begin="1" end="50" varStatus="index">
	                            {text:'<bean:message bundle="km-calendar" key="each"/>${index.count}<bean:message bundle="km-calendar" key="week"/>',value:'${index.count}'}
	                            <c:if test="${ index.count!=50 }">,</c:if>
	                        </c:forEach>]">
						</div>
					</div>
					<div class="moreset">
						<div data-dojo-type="mui/form/Select"
					         data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-calendar"  key="kmCalendarMain.recurrenceInterval"/>',value:'${kmCalendarMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
					         store:[<c:forEach begin="1" end="50" varStatus="index">
	                            {text:'<bean:message bundle="km-calendar" key="each"/>${index.count}<bean:message bundle="km-calendar" key="month"/>',value:'${index.count}'}
	                            <c:if test="${ index.count!=50 }">,</c:if>
	                        </c:forEach>]">
						</div>
					</div>
					<div class="moreset">
						<div data-dojo-type="mui/form/Select"
					         data-dojo-props="name:'RECURRENCE_INTERVAL',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-calendar"  key="kmCalendarMain.recurrenceInterval"/>',value:'${kmCalendarMainForm.RECURRENCE_INTERVAL }',showPleaseSelect:false,orient:'vertical',
					         store:[<c:forEach begin="1" end="50" varStatus="index">
	                            {text:'<bean:message bundle="km-calendar" key="each"/>${index.count}<bean:message bundle="km-calendar" key="year"/>',value:'${index.count}'}
	                            <c:if test="${ index.count!=50 }">,</c:if>
	                        </c:forEach>]">
						</div>
					</div>
					<div class="moreset">
						<div data-dojo-type="mui/form/Select"
							 data-dojo-props="name:'RECURRENCE_WEEKS',showStatus:'edit',subject:'<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceTimeType" />',value:'${kmCalendarMainForm.RECURRENCE_WEEKS }',showPleaseSelect:false,orient:'vertical',
							 store:[{text:'<bean:message bundle="km-calendar" key="mobile.recurrence.week.Sunday"/>',value:'SU'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.week.Monday"/>',value:'MO'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.week.Tuesday"/>',value:'TU'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.week.Wednesday"/>',value:'WE'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.week.Thursday"/>',value:'TH'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.week.Friday"/>',value:'FR'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.week.Saturday"/>',value:'SA'}]"></div>
					</div>
					<div class="moreset">
						<div data-dojo-type="mui/form/Select"
							 data-dojo-props="name:'RECURRENCE_MONTH_TYPE',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceTimeType" />',value:'${kmCalendarMainForm.RECURRENCE_MONTH_TYPE }',showPleaseSelect:false,orient:'vertical',
							 store:[{text:'<bean:message bundle="km-calendar" key="recurrence.month.type.month"/>',value:'month'},
							 {text:'<bean:message bundle="km-calendar" key="recurrence.month.type.week"/>',value:'week'}]" >
						</div>
					</div>
					<div class="moreset">
						<div data-dojo-type="mui/form/Select"
							 data-dojo-props="name:'RECURRENCE_END_TYPE',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-calendar" key="kmCalendarMain.recurrenceEndType" />',value:'${kmCalendarMainForm.RECURRENCE_END_TYPE }',showPleaseSelect:false,orient:'vertical',
							 store:[{text:'<bean:message bundle="km-calendar" key="recurrence.end.type.never"/>',value:'NEVER'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.end.type.count" />',value:'COUNT'},
							 {text:'<bean:message bundle="km-calendar" key="mobile.recurrence.end.type.until" />',value:'UNTIL'}]">
						</div>
						<div class="endType">
							<xform:text showStatus="edit" validators="count" property="RECURRENCE_COUNT" onValueChange="reCount(this.value);" mobile="true"/>
						</div>
						<div class="endType">
							<xform:datetime showStatus="edit" property="RECURRENCE_UNTIL" onValueChange="untilChange" dateTimeType="date" mobile="true"/>
						</div>
					</div>
					<div class="moreset">
						<span style="padding-left: 1rem;"><bean:message bundle="km-calendar" key="kmCalendarMain.summary" />:</span>
						<input type="hidden" name="RECURRENCE_SUMMARY" />
				        <div id="summary" style="margin-left: 1rem"></div>
					</div>
				</div>
				
				<div id="notifyView" data-dojo-type="dojox/mobile/View">
					<c:import url="/km/calendar/mobile/kmCalendarNotifyRemindMain_edit.jsp"  charEncoding="UTF-8">
						<c:param name="formName" value="kmCalendarMainForm" />
				         <c:param name="fdKey" value="kmCalenarMainDoc" />
				         <c:param name="fdPrefix" value="event" />
				         <c:param name="fdModelName" value="com.landray.kmss.km.calendar.model.KmCalendarMain" />
					</c:import>
				</div>
				
				<div id="labelView" data-dojo-type="dojox/mobile/View">
					<div class="muiCalendarLabelContainer">
						<div data-dojo-type="mui/form/Select"
							 data-dojo-props="name:'labelId',mul:false,showStatus:'edit',subject:'<bean:message bundle="km-calendar"  key="kmCalendarMain.docLabel"/>',value:'${kmCalendarMainForm.labelId }',showPleaseSelect:false,orient:'vertical',
							 store:[{text:'<bean:message bundle='km-calendar' key='kmCalendarMain.group.header.title' />',value:''}]" id="labelId">
						</div>
					</div>
				</div>
				
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
					
				  	<c:if test="${ kmCalendarMainForm.method_GET == 'addGroupEvent' }">	
						<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
						  	data-dojo-props='colSize:2,href:"javascript:commitMethod(\"save\");",transition:"slide"'>
						  		<bean:message  key="button.submit"/>
						 </li>
					 </c:if>
					 <c:if test="${ kmCalendarMainForm.method_GET == 'editGroupEvent'  }">	
					 <li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
					  	data-dojo-props='colSize:2,href:"javascript:commitMethod(\"update\");",transition:"slide"'>
					  		<bean:message bundle="km-calendar"  key="mui.kmCalendarMain.update"/>
					 </li>
					 </c:if>
				   	
				</ul>
			</div>
		</html:form>
		</xform:config>
	</template:replace>


</template:include>
<script>
require(["mui/form/ajax-form!kmCalendarMainForm"]);
require(['dojo/query','dojo/topic','dijit/registry','dojo/ready','dojo/dom-style','dojo/dom-geometry','mui/dialog/Tip','mui/calendar/CalendarUtil','dojo/date/locale','mui/device/adapter','dojo/request','dojo/dom-attr'],
		function(query,topic,registry,ready,domStyle,domGeometry,Tip,cutil,locale,adapter,request,domAttr){
	window.authorityTypeSelect=function(value){
		var event_auth = query(".event_auth");
	    event_auth.forEach(function(node){
	    	if(value=="PUBLIC"||value=="PRIVATE"){
		    	//隐藏共享
		    	domStyle.set(node,'display','none');
			}else{
				//显示共享
				domStyle.set(node,'display','block');
			}
	    });
	};
	var labelIdObj;
	var allLabelArr;
	//选择框控件
	topic.subscribe('mui/form/select/callback',function(widget){
		var valueField = widget.valueField;
		var value = widget.value;
		if(valueField == 'docOwnerId'){
			var docOwnerIds = query("#docOwnerIds")[0];
			var ownerTip = query("#ownerTip")[0];
			if(value == 'multiCreate'){
				//显示群组地址本
				domStyle.set(docOwnerIds,'display','block');
				//隐藏授权提示
				domStyle.set(ownerTip,'display','none');
				//日程所有者为群组时只可以选默认标签(即"我的日历")
				var newArray = new Array();
				newArray.push(allLabelArr[0]);
				labelIdObj.values = newArray;
				labelIdObj.text = allLabelArr[0].text;
				labelIdObj.value = allLabelArr[0].value;
				labelIdObj._setTextAttr(labelIdObj.text);
				query('[name="labelId"]')[0].value = labelIdObj.value;
				registry.byId('authReader')._setCurIdsAttr('');
				registry.byId('authReader')._setCurNamesAttr('');
				registry.byId('authEditor')._setCurIdsAttr('');
				registry.byId('authEditor')._setCurNamesAttr('');
			}else{
				//隐藏群组地址本
				domStyle.set(docOwnerIds,'display','none');
				if(value=='${pageScope.currentUserId}'){
					//隐藏授权提示
					domStyle.set(ownerTip,'display','none');
					//日程所有者为自己时可选标签
					labelIdObj.values = allLabelArr;
					labelIdObj.text = allLabelArr[0].text;
					labelIdObj.value = allLabelArr[0].value;
					labelIdObj._setTextAttr(labelIdObj.text);
					query('[name="labelId"]')[0].value = labelIdObj.value;
				}else{
					//显示授权提示
					domStyle.set(ownerTip,'display','block');
					//日程所有者为别人时只可以选系统标签
					var otherArray = new Array();
					otherArray.push(allLabelArr[0]);
					var url = '${LUI_ContextPath}/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson&userId='+value;
					$.get(url,function(data){
						var newDataArr = JSON.parse(data);
						var dataArr = newDataArr.showLabelData;
						for(var i = 0;i < dataArr.length;i++){
							var obj = new Object();
							obj.text = dataArr[i].fdName;
							obj.value = dataArr[i].fdId;
							otherArray.push(obj);
						}
						labelIdObj.values = otherArray;
						labelIdObj.text = allLabelArr[0].text;
						labelIdObj.value = allLabelArr[0].value;
						labelIdObj._setTextAttr(labelIdObj.text);
						query('[name="labelId"]')[0].value = labelIdObj.value;
					});
				}
				//非群组日历,修改权限
				$.get('${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=loadAuthData&personId='+value,function(data){
					if(data){
						registry.byId('authReader')._setCurIdsAttr(data.readersIdStr);
						registry.byId('authReader')._setCurNamesAttr(data.readersNameStr);
						registry.byId('authEditor')._setCurIdsAttr(data.modifiersIdStr);
						registry.byId('authEditor')._setCurNamesAttr(data.modifiersNameStr);
					}
				});
			}
			return;
		}
		var freq=query("[name='RECURRENCE_FREQ']")[0].value;
		var interval=query("[name='RECURRENCE_INTERVAL']")[0].value;
		var count=query("[name='RECURRENCE_COUNT']")[0].value;
		var endType=query("[name='RECURRENCE_END_TYPE']")[0].value;
		var weeks=query("[name='RECURRENCE_WEEKS']")[0].value;
		var monthType=query("[name='RECURRENCE_MONTH_TYPE']")[0].value;
		if(valueField=='RECURRENCE_FREQ'){
			var moreset = query(".moreset");
			moreset.forEach(function(node,index){
				if(value!='NO'){
					if(index>5){
						domStyle.set(moreset[index],'display','block');
					}else{
						switch(value){
							case "DAILY":
								if(index==0){
									domStyle.set(moreset[index],'display','block');
								}else{
									domStyle.set(moreset[index],'display','none');
								}
								freq = "DAILY";
								break;
							case "WEEKLY":
								if(index==1||index==4){
									domStyle.set(moreset[index],'display','block');
								}else{
									domStyle.set(moreset[index],'display','none');
								}
								freq = "WEEKLY";
								break;
							case "MONTHLY":
								if(index==2||index==5){
									domStyle.set(moreset[index],'display','block');
								}else{
									domStyle.set(moreset[index],'display','none');
								}
								freq = "MONTHLY";
								break;
							case "YEARLY":
								if(index==3){
									domStyle.set(moreset[index],'display','block');
								}else{
									domStyle.set(moreset[index],'display','none');
								}
								freq = "YEARLY";
								break;
						}
					}
				}else{
					domStyle.set(node,'display','none');
					freq = "NO";
				}
			});
		}
		if(valueField=='RECURRENCE_INTERVAL'){
			interval = value;
		}
		if(valueField=='RECURRENCE_WEEKS'){
			weeks = value;
		}
		if(valueField=='RECURRENCE_MONTH_TYPE'){
			monthType = value;
		}
		if(valueField=='RECURRENCE_END_TYPE'){
			endType = value;
			var endTypeDom = query(".endType");
			if(value=='NEVER'){
				endTypeDom.forEach(function(node){
					domStyle.set(node,'display','none');
				});
			}
			if(value=='COUNT'){
				domStyle.set(endTypeDom[0],'display','block');
				domStyle.set(endTypeDom[1],'display','none');
			}
			if(value=='UNTIL'){
				domStyle.set(endTypeDom[0],'display','none');
				domStyle.set(endTypeDom[1],'display','block');
			}
		}
		summary(freq,interval,count,endType,weeks,monthType);
	});
	var values = {"SU":"日","MO":"一","TU":"二","WE":"三","TH":"四","FR":"五","SA":"六"};
	var en_values = {"SU":"Sun","MO":"Mon","TU":"Tue","WE":"Wed","TH":"Thu","FR":"Fri","SA":"Sat"}; 
	function summary(freq,interval,count,endType,weeks,monthType){
		var unit='';
		var solar = '<span style="margin-right:10px;">${lfn:message("km-calendar:solar")}</span>';//日历类型
		var intervalMsg="${lfn:message('km-calendar:kmCalendarMain.summary.interval')}";
		var recurrenceTime='';
		if(freq=='DAILY'){
			unit='${lfn:message("km-calendar:daily")}';
		}
		if(freq=='WEEKLY'){
			unit='${lfn:message("km-calendar:week")}';
			recurrenceTime+=unit;
			if(weeks!=''){
				var _weeks=weeks.split(";");
				if(Com_Parameter.Lang.indexOf("en")!=-1){
					recurrenceTime+=" ";
					for(var i=0;i<_weeks.length;i++){
						recurrenceTime+=en_values[_weeks[i]]+"、";
					}
				}else{
					for(var i=0;i<_weeks.length;i++){
						recurrenceTime+=values[_weeks[i]]+"、";
					}
				}
				recurrenceTime=recurrenceTime.substring(0,recurrenceTime.length-1);
			}
		}
		if(freq=='MONTHLY'){
			unit='${lfn:message("km-calendar:month")}';
			var d = query("input[name='docStartTime']")[0].value;
			var date = new Date();
			if(d!=''){
				date = Com_GetDate(d,'date','${formatter}');
			}
			if(monthType=='month'){
				recurrenceTime+="${lfn:message('km-calendar:kmCalendarMain.summary.eachMonth')}".replace("%day%",date.getDate());
			}
			if(monthType=='week'){
				recurrenceTime+="${lfn:message('km-calendar:kmCalendarMain.summary.eachWeek')}".replace("%order%",Math.ceil(date.getDate() / 7)).replace("%week%","${lfn:message('calendar.week.names')}".split(',')[date.getDay()]);
			}
		}
		if(freq=='YEARLY'){
			unit='${lfn:message("km-calendar:year")}';
		}
		intervalMsg=intervalMsg.replace("%interval%",interval).replace("%unit%",unit)+"</span>";
		var intervalStr="<span style='margin-right:10px;'>"+intervalMsg;
		if(recurrenceTime!=''&&weeks!=''){
			intervalStr+="<span style='margin-right:10px'>"+recurrenceTime+"</span>";
		}
		var endTypeStr = '<span style="margin-right:10px;">';
		switch(endType){
			case "NEVER":
				endTypeStr+="<bean:message bundle='km-calendar' key='recurrence.end.type.never'/>";
				break;
			case "COUNT":
				endTypeStr+="${lfn:message('km-calendar:kmCalendarMain.summary.freqEnd')}".replace("%count%",count);
				break;
			case "UNTIL":
				endTypeStr+="<bean:message bundle='km-calendar' key='recurrence.end.type.until'/><span id='untilTime'></span>";
				break;
		}
		endTypeStr+="</span>";
		var summary = query("#summary")[0];
		summary.innerHTML = solar+intervalStr+endTypeStr;
	}
	
	window.reCount = function(value){
		var _freq = query("[name='RECURRENCE_FREQ']")[0].value;
		var _interval = query("[name='RECURRENCE_INTERVAL']")[0].value;
		var _count = value;
		var _weeks = query("[name='RECURRENCE_WEEKS']")[0].value;
		var _monthType = query("[name='RECURRENCE_MONTH_TYPE']")[0].value;
		summary(_freq,_interval,_count,'COUNT',_weeks,_monthType);
	};
	
	window.untilChange=function(){
		var until = query("[name='RECURRENCE_UNTIL']")[0].value;
		query("#untilTime")[0].innerHTML=until;
	};
	
	//校验对象
	var validorObj=null;
	
	ready(function(){
		validorObj=registry.byId('scrollView');
		
		//全天,不显示type='time'的时间控件
		var fdIsAlldayevent=query('[name="fdIsAlldayevent"]')[0].value;
			startTimeWidget=registry.byId('docStartTime'),
			startHHmmWidget=registry.byId('docStartHHmm'),
			finishTimeWidget=registry.byId('docFinishTime'),
			finishHHmmWidget=registry.byId('docFinishHHmm');
		if(fdIsAlldayevent=='true'){
			hideHHmm();
		}else{
			showHHmm();
		}
		//初始化type='time'的时间控件
		var date=new Date();
		var intStartHour,intEndHour;
			//intEndMinute = date.getMinutes();
			if(date.getHours() == 23){
				date.setDate(date.getDate()+1);
				startTimeWidget.set("value",locale.format(date,{
					selector : 'time',
					timePattern : "${ lfn:message('date.format.date') }"
				}));
				finishTimeWidget.set("value",locale.format(date,{
					selector : 'time',
					timePattern : "${ lfn:message('date.format.date') }"
				}));
				intStartHour = '00';
				intEndHour = '01';
			}else{
				 intStartHour = date.getHours()+1;
				 intEndHour = date.getHours()+2;
				 if(intStartHour < 10)
					 intStartHour = '0' + intStartHour;
				 if(intEndHour < 10)
					 intEndHour = '0' + intEndHour;
				 if(intEndHour == 24)
					 intEndHour = '00';
			}
		var startHour=query('[name="startHour"]')[0].value || intStartHour,
			startMinute=query('[name="startMinute"]')[0].value || '00',
			endHour=query('[name="endHour"]')[0].value || intEndHour,
			endMinute=query('[name="endMinute"]')[0].value || '00';	
			
		startHHmmWidget.set('value',startHour+':'+startMinute);
		finishHHmmWidget.set('value',endHour+':'+endMinute);
		resziePage();
		if('${kmCalendarMainForm.method_GET}'=='edit'){
			if('${kmCalendarMainForm.fdIsLunar}'=='false'){
				var _freq='${kmCalendarMainForm.RECURRENCE_FREQ}';
				var moreset = query(".moreset");
				moreset.forEach(function(node,index){
					if(_freq!='NO'){
						if(index>5){
							domStyle.set(moreset[index],'display','block');
						}else{
							switch(_freq){
								case "DAILY":
									if(index==0){
										domStyle.set(moreset[index],'display','block');
									}
									break;
								case "WEEKLY":
									if(index==1||index==4){
										domStyle.set(moreset[index],'display','block');
									}
									break;
								case "MONTHLY":
									if(index==2||index==5){
										domStyle.set(moreset[index],'display','block');
									}
									break;
								case "YEARLY":
									if(index==3){
										domStyle.set(moreset[index],'display','block');
									}
									break;
							}
						}
						var _interval = '${kmCalendarMainForm.RECURRENCE_INTERVAL}';
						var _count = '${kmCalendarMainForm.RECURRENCE_COUNT}';
						var _endType = '${kmCalendarMainForm.RECURRENCE_END_TYPE}';
						var _weeks = '${kmCalendarMainForm.RECURRENCE_WEEKS}';
						var _monthType = '${kmCalendarMainForm.RECURRENCE_MONTH_TYPE}';
						summary(_freq,_interval,_count,_endType,_weeks,_monthType);
						var endTypeDom = query('.endType');
						if(_endType=='COUNT'){
							domStyle.set(endTypeDom[0],'display','block');
						}
						if(_endType=='UNTIL'){
							domStyle.set(endTypeDom[1],'display','block');
							untilChange();
						}
					}
				});
			}else{
				var _freq_lunar='${kmCalendarMainForm.RECURRENCE_FREQ_LUNAR}';
			}
		}
		var fdAuthorityType="${kmCalendarMainForm.fdAuthorityType}";
		authorityTypeSelect(fdAuthorityType);
		labelIdObj = registry.byId('labelId');
		allLabelArr = labelIdObj.values;
	});
	
	//切换全天时隐藏type='time'的时间控件
	topic.subscribe('km/calendar/statChanged',function(widget,value){
		if(value){
			hideHHmm();
		}else{
			showHHmm();
		}
	});
	
	function clearTr(flag){
		var TRS;
		if(flag){
			TRS = query("#TABLE_event_kmCalenarMainDoc_true .muiAgendaNormalTr");
			INPUTS = query("#TABLE_event_kmCalenarMainDoc_true .muiAgendaNormalTr td input");
		}else{
			TRS = query("#TABLE_event_kmCalenarMainDoc_false .muiAgendaNormalTr");
			INPUTS = query("#TABLE_event_kmCalenarMainDoc_false .muiAgendaNormalTr td input");
		}
		TRS.forEach(function(TR,index){
			domStyle.set(TR,'display','none');
		});
		INPUTS.forEach(function(INPUT,index){
			domAttr.set(INPUT,'value','');
		});
	}
	
	//显示type='time'的时间控件
	function hideHHmm(){
		var startHHmmWidget=registry.byId('docStartHHmm'),
			finishHHmmWidget=registry.byId('docFinishHHmm');
		//var w=domGeometry.getMarginSize(startHHmmWidget.domNode).w;
		//隐藏type='time'的时间控件
		domStyle.set(startHHmmWidget.domNode,'display','none');
		domStyle.set(finishHHmmWidget.domNode,'display','none');
		//修改type='date'的时间控件宽度
		//domStyle.set(startTimeWidget.domNode,'width',2*w+'px');
		//domStyle.set(finishTimeWidget.domNode,'width',2*w+'px');
		// 显示全天的提醒明细
		var allDayDiv = document.getElementById("allDay");
		var notAllDayDiv = document.getElementById("notAllDay");
		allDayDiv.style.display = 'block';
		notAllDayDiv.style.display = 'none';
		clearTr(false);
	}
	
	//显示type='time'的时间控件
	function showHHmm(){
		var startHHmmWidget=registry.byId('docStartHHmm'),
			finishHHmmWidget=registry.byId('docFinishHHmm');
		//修改type='date'的时间控件宽度
		//domStyle.set(startTimeWidget.domNode,'width',w+'px');
		//domStyle.set(finishTimeWidget.domNode,'width',w+'px');
		//隐藏type='time'的时间控件
		domStyle.set(startHHmmWidget.domNode,'display','');
		domStyle.set(finishHHmmWidget.domNode,'display','');
		// 显示非全天的提醒明细
		var allDayDiv = document.getElementById("allDay");
		var notAllDayDiv = document.getElementById("notAllDay");
		allDayDiv.style.display = 'none';
		notAllDayDiv.style.display = 'block';
		clearTr(true);
	}
	
	//type='time'的的时间控件发生变化
	topic.subscribe('/mui/form/datetime/change',function(widget){
		//开始时间变化
		if(widget.id=='docStartHHmm'){
			var _HHmm=widget.value.split(':');
			query('[name="startHour"]')[0].value=_HHmm[0];
			query('[name="startMinute"]')[0].value=_HHmm[1];
			var endHour = parseInt(_HHmm[0])+1;
			var endMin=_HHmm[1];
			if(endHour>22){
				endHour=23;
				endMin=59;
			}
			query('[name="endHour"]')[0].value= endHour;
			query('[name="endMinute"]')[0].value=endMin;
			var finishHHmmWidget=registry.byId('docFinishHHmm');
			finishHHmmWidget.set('value',endHour+':'+endMin);
		}
		//结束时间变化
		if(widget.id=='docFinishHHmm'){
			var _HHmm=widget.value.split(':');
			query('[name="endHour"]')[0].value=_HHmm[0];
			query('[name="endMinute"]')[0].value=_HHmm[1];
		}
	});
	
	window.validateCount = function(){
		var endType = query("[name='RECURRENCE_END_TYPE']")[0].value;
		var count = query("[name='RECURRENCE_COUNT']")[0].value;
		if(endType=="COUNT"){
			if(!/^[1-9]\d*$/.test(count)||count.length<=0){
				Tip.fail({
					text:'<bean:message key="kmCalendarMain.tip.validateCount.errorCount" bundle="km-calendar" />' 
				});
				return false;
			}
		}
		return true;
	}
	
	window.validateUntilTime = function(){
		var endType = query("[name='RECURRENCE_END_TYPE']")[0].value;
		var until = query("[name='RECURRENCE_UNTIL']")[0].value;
		if(endType=='UNTIL'){
			if(until==''){
				Tip.fail({
					text:'<bean:message key="kmCalendarMain.tip.validateUntilTime.notNull" bundle="km-calendar" />' 
				});
				return false;
			}else{
				var untilDate = Com_GetDate(until,'date','${formatter}');
				var startDate = Com_GetDate(query('[name="docStartTime"]')[0].value,'date','${formatter}');
				if(untilDate.getTime()<startDate.getTime()){
					Tip.fail({
						text:'<bean:message key="kmCalendarMain.tip.validateUntilTime.errorDate" bundle="km-calendar" />' 
					});
					return false;
				}
			}
		}
		return true;
	}
	
	window.commitMethod=function(method){
		//结束条件为次数才校验
		if(validateCount()==false){
			return false;
		}
		//结束条件为日期才校验
		if(validateUntilTime()==false){
			return false;
		}
		if(___validate()){
			if(method == 'save'){
				Com_Submit(document.forms[0],'saveEvent');
				var docOwnerId = query('[name="docOwnerId"]')[0].value;
				var selfId = '${pageScope.currentUserId}';
				if(docOwnerId == selfId){
					addCalendarToAdapter();
				}
				if(docOwnerId == 'multiCreate'){
					var docOwnerIds = query('[name="docOwnerIds"]')[0].value;
					var url = '${LUI_ContextPath}/sys/organization/mobile/address.do?method=include&personId='+docOwnerIds+'&selfId='+selfId;
					request.get(url, {handleAs : 'json',headers: {"accept": "application/json"}})
					.response.then(function(datas) {
						if(datas.status=='200'){
							if(datas.data){
								addCalendarToAdapter();
							}
						}
					});
				}
			}
			else if(method == 'update')
				Com_Submit(document.forms[0],'updateGroupEvent');
		}
			
	};
	
	function addCalendarToAdapter(){
		var notes = document.getElementById("labelId").getElementsByClassName("muiSelInput")[0].innerText;
		var loc = document.getElementsByName("fdLocation")[0].value;
		var title = document.getElementsByName("docSubject")[0].value;
		var fdIsAlldayevent = document.getElementsByName("fdIsAlldayevent")[0].value;
		var startT = document.getElementsByName("docStartTime")[0].value;
		var endT = document.getElementsByName("docFinishTime")[0].value;
		var allDay = true;
		if(fdIsAlldayevent=="false"){
			startT += " "+document.getElementsByName("docStartHHmm")[0].value+":00";
			endT += " "+document.getElementsByName("docFinishHHmm")[0].value+":00";
			allDay = false;
		}
		var alarms = new Array();
		var muiAgendaNormalTr = query(".muiAgendaNormalTr");
		muiAgendaNormalTr.forEach(function(node,index){
			var fdBeforeTime = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdBeforeTime")[0].value;
			var fdTimeUnit = document.getElementsByName("sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList["+index+"].fdTimeUnit")[0].value;
			if(fdTimeUnit=="minute"){
				fdBeforeTime *= 1;
			}
			if(fdTimeUnit=="hour"){
				fdBeforeTime *= 60;
			}
			if(fdTimeUnit=="day"){
				fdBeforeTime *= 60*24;
			}
			if(fdTimeUnit=="week"){
				fdBeforeTime *= 60*24*7;
			}
			alarms.push(fdBeforeTime);
		});
		var options = {
			title : title,
			loc : loc,
			notes : notes,
			url: Com_GetCurDnsHost()+'${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId=${kmCalendarMainForm.fdId}',
			startT : startT,
			endT : endT,
			allDay : allDay,
			alarms : alarms
		};
		adapter.addCalendar(options);
	}
	
	function ___validate(){
		var result=validorObj.validate();
		var startTime=query('[name="docStartTime"]')[0].value+' '+query('[name="docStartHHmm"]')[0].value,
			endTime=query('[name="docFinishTime"]')[0].value+' '+query('[name="docFinishHHmm"]')[0].value;
		startTime=cutil.parseDate(startTime);
		endTime=cutil.parseDate(endTime);
		if(endTime.getTime() < startTime.getTime()){
			Tip.fail({
				text:'<bean:message key="mobile.kmCalendarMaint.tip.validateTime.error" bundle="km-calendar" />' 
			});
			return false;
		}
		return result;
	}
	
	//提交后返回查看页面
	Com_Submit.ajaxAfterSubmit=function(){
		if('${ kmCalendarMainForm.method_GET}'=='edit'){
			setTimeout(function(){
				window.location='${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId=${kmCalendarMainForm.fdId}';
			},2000);
		}
	};
	
	//初始化页面
	function resziePage(){
		var scrollView=registry.byId('scrollView').domNode,
			dateView=registry.byId('dateView').domNode,
			recurrenceView=registry.byId('recurrenceView').domNode,
			labelView=registry.byId('labelView').domNode,
			notifyView=registry.byId('notifyView').domNode,
			minHeight=scrollView.offsetHeight - dateView.offsetTop;
		domStyle.set(dateView,'min-height',minHeight+'px');
		domStyle.set(recurrenceView,'min-height',minHeight+'px');
		domStyle.set(labelView,'min-height',minHeight+'px');
		domStyle.set(notifyView,'min-height',minHeight+'px');
	}
	
});
</script>



