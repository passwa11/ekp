<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.DateUtil"%>


<%
	request.setAttribute("formatter", ResourceUtil.getString("date.format.date"));
%>
<template:include ref="default.dialog">
	<template:replace name="content">
		<link rel="stylesheet" href="${LUI_ContextPath}/km/calendar/resource/css/custom.css"/>
		<script type="text/javascript">
			Com_IncludeFile(
					"calendar.js",
					null, "js");
			seajs.use([ 'lui/jquery', 'lui/dialog','km/calendar/resource/js/dateUtil','lui/calendar' ],function($, dialog,dateUtil,calendar) {
			LUI.ready(function() {
						//开始、结束时间差
						var start = formatDate('${kmCalendarMainForm.docStartTime}',"${formatter}"), 
						end = formatDate('${kmCalendarMainForm.docFinishTime}',"${formatter}"), 
						duration = DateDiff(start,end,'day')+1, //时间间隔
						allDay = '${kmCalendarMainForm.fdIsAlldayevent}',//是否全天
						isLunar='${kmCalendarMainForm.fdIsLunar}';//是否农历
						var yearCount=DateDiff(start,end,'year');
						var lunarStartDate=calendar.solarOnlyDate(start);
						var lunarEndDate=calendar.solarOnlyDate(end);
						var docStartTimeHTML="",docFinishTimeHTML="";
						if("true"==isLunar){
							docStartTimeHTML='${lfn:message("km-calendar:lunar")}'+getTimeHTML('${kmCalendarMainForm.lunarStartYear}','${kmCalendarMainForm.lunarStartMonth}','${kmCalendarMainForm.lunarStartDay}',yearCount);
							docFinishTimeHTML='${lfn:message("km-calendar:lunar")}'+getTimeHTML('${kmCalendarMainForm.lunarEndYear}','${kmCalendarMainForm.lunarEndMonth}','${kmCalendarMainForm.lunarEndDay}',yearCount);
						}
						else{
							docStartTimeHTML=getTimeHTML(start.getFullYear(),( start.getMonth()+1),start.getDate(),yearCount);
							docFinishTimeHTML=getTimeHTML(end.getFullYear(),( end.getMonth()+1),end.getDate(),yearCount);
						}

						$("#docStartTime").html(docStartTimeHTML);
						$("#docFinishTime").html(docFinishTimeHTML);
						
						if (duration == 1
								&& allDay == 'false') {
							var startHour = parseInt('${kmCalendarMainForm.startHour}'), 
							endHour = parseInt('${kmCalendarMainForm.endHour}'), 
							startMinute = parseInt('${kmCalendarMainForm.startMinute}'), 
							endMinute = parseInt('${kmCalendarMainForm.endMinute}');
							if("true"==isLunar){
								startHour = parseInt('${kmCalendarMainForm.lunarStartHour}'); 
								endHour = parseInt('${kmCalendarMainForm.lunarEndHour}');
								startMinute = parseInt('${kmCalendarMainForm.lunarStartMinute}');
								endMinute = parseInt('${kmCalendarMainForm.lunarEndMinute}');
							}
							var start = startHour
									* 60 + startMinute, end = endHour
									* 60 + endMinute, _hour = parseInt((end - start) / 60), _minute = (end - start) % 60;
							$('.duration')[0].innerHTML = '';
							if (_hour != 0) {
								$('.duration')[0].innerHTML += parseInt(_hour)
										+ '<bean:message key="date.interval.hour" />';
							}
							if (_minute != 0) {
								$('.duration')[0].innerHTML += parseInt(_minute)
										+ '<bean:message key="date.interval.minute" />';
							}
						} else {
							$('.duration')[0].innerHTML = duration
									+ '<bean:message key="date.interval.day" />';
						}
						
						//选中日初始化
						var currentDate = '${kmCalendarMainForm.docStartTime}';
						if (currentDate) {
							currentDate = formatDate(currentDate,"${formatter}");
						} else {
							currentDate = new Date();
						}
						var weekArray = '${lfn:message("calendar.week.names")}'
								.split(','), now = new Date();
						now.setHours(0, 0, 0, 0);
						var duration = DateDiff(currentDate, now,'day');
						var lang=Com_Parameter['Lang']==null?"":Com_Parameter['Lang'];
						if (duration < 0) {
							duration = 0 - duration;
							if(duration==1){
								curDateStr = '<em class="Today">${lfn:message("km-calendar:tomorrow")}</em>';
							}
							else{
								curDateStr = '<em>'
									+ duration
									+ '</em>${lfn:message("km-calendar:daily")}';
								if (lang == 'en-us') {
									curDateStr += ' ';
								}
								curDateStr += '${lfn:message("km-calendar:after")}';
							}
						} else if (duration == 0) {
							curDateStr = '<em class="Today">${lfn:message("sys-ui:ui.calendar.today")}</em>';
						} else if(duration == 1){
							curDateStr = '<em class="Today">${lfn:message("km-calendar:yesterday")}</em>';
						}else {
							curDateStr = '<em>'
									+ duration
									+ '</em>${lfn:message("km-calendar:daily")}';
							if (lang == 'en-us') {
								curDateStr += ' ';
							}
							curDateStr += '${lfn:message("km-calendar:before")}';
						}
						$('.remind_calendar_label')[0].innerHTML = curDateStr;
						
					});
			
			window.openSource = function(relationUrl) {
				if (relationUrl) {
                	if(relationUrl.length>4 && relationUrl.substring(0,4)!="http"){
                		relationUrl = '${LUI_ContextPath}' + relationUrl;
                	}
					window.open(relationUrl, 'blank');
					return;
				}
			};
			//点击确定,修改标签、提醒设置
			window.updateLabelAndRemind=function(){
				window.loading = dialog.loading();
				var fdId="${param.fdId}";
				var labelId=$("form[name=kmCalendarMainForm] select[name=labelId]")[0].value;//修改标签
				$.post('<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=checkRemind&fdId="/>' + fdId, function(res) {
					if(res.success) {
						$.get('<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=updateLabelAndRemind"/>',
							$.param({"fdId":fdId,"labelId":labelId,"clearRemind":false},true),function(data){
							window.$dialog.hide("true");//关闭对话框
								if(window.loading!=null)
									window.loading.hide();
								if(data!=null && data.status==true){
									if(data.changeGroup==true){
										dialog.failure('<bean:message bundle="km-calendar" key="kmCalendarMain.event.not.exchangeGroupEvent" />');
										return ;
									}
									 //重复日程直接刷新整个界面
									if(data.isRecurrence != null && data.isRecurrence==true){
										window.parent.LUI('calendar').refreshSchedules();
										return ;
									}else{
										window.parent.setColor(data.schedule);
										window.parent.LUI('calendar').updateSchedule(data.schedule);
									} 
								}else{
									dialog.failure('<bean:message key="return.optFailure" />');
								}
							},'json');
					} else {
						if(window.loading!=null)
							window.loading.hide();
						dialog.alert(res.msg);
					}
				}, "json");
			};
			//删除日程
			window.delDoc=function(divId,form){
				var fdId="${param.fdId}";
				var fdIsGroup = '${kmCalendarMainForm.fdIsGroup}';
				if(fdIsGroup == 'true'){
					dialog.confirm('<bean:message bundle="km-calendar" key="kmCalendarMain.groupEvent.delete.msg2" />',function(flag){
						if(flag)
							ajaxDelDoc(divId,fdId)
					});
				}else{
					ajaxDelDoc(divId,fdId);
				}
				
			};
			
			window.ajaxDelDoc=function(divId,fdId){
				var url="${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=delete&fdId="+fdId;
				window.loading=dialog.loading();
				$.get(url,function(data){
					if(window.loading!=null)
						window.loading.hide();
					if(data!=null && data.status==true){
						window.parent.LUI('calendar').removeSchedule(fdId);//删除日程 
					}
					else{
						dialog.failure('<bean:message key="return.optFailure" />');
					}
					window.$dialog.hide("true");//关闭对话框
				},'json');
			}

			//日程详细设置
			window.eidtEventDetail=function(){
				var fdId="${param.fdId}";
				var url = "/km/calendar/km_calendar_main/kmCalendarMain.do?method=edit&fdId="+fdId;
				var isAllDayEvent = '${kmCalendarMainForm.fdIsAlldayevent}';
				var isLunar = '${kmCalendarMainForm.fdIsLunar}';
				var subject = '${kmCalendarMainForm.docSubject}';
				subject=encodeURIComponent(subject);//转码
				var startTime = '${kmCalendarMainForm.docStartTime}';
				var endTime = '${kmCalendarMainForm.docFinishTime}';
				var labelId = $("form[name=kmCalendarMainForm] select[name=labelId]")[0].value;
				url += "&isAllDayEvent="+isAllDayEvent+"&subject="+subject+"&startTime="+startTime+"&endTime="+endTime+"&labelId="+labelId;
				if(!isAllDayEvent){
					 var startHour = isLunar=='true'?'${kmCalendarMainForm.lunarStartHour}':'${kmCalendarMainForm.startHour}';
					 var startMinute = isLunar=='true'?'${kmCalendarMainForm.lunarStartMinute}':'${kmCalendarMainForm.startMinute}';
					 var endHour = isLunar=='true'?'${kmCalendarMainForm.lunarEndHour}':'${kmCalendarMainForm.endHour}';
					 var endMinute = isLunar=='true'?'${kmCalendarMainForm.lunarEndMinute}':'${kmCalendarMainForm.endMinute}';
					 url += "&startHour="+startHour+"&startMinute="+startMinute+"&endHour="+endHour+"&endMinute="+endMinute;
				}
				var header = '<ul class="clrfix schedule_share"><li class="current" id="event_base_label">${lfn:message("km-calendar:kmCalendarMain.opt.edit")}</li>'
						+ '<li>|</li><li id="event_auth_label">${lfn:message("km-calendar:kmCalendar.label.table.share")}</li>'
						+ '</ul>';
				dialog.iframe(url,header,function(rtn){
					window.$dialog.hide("true");
					if(rtn!=null){
						if(rtn.method=="delete"){
							window.parent.LUI('calendar').removeSchedule(fdId);//删除日程
						}else{
							//重复日程直接刷新整个界面
							if(rtn.isRecurrence != null && rtn.isRecurrence==true){
								window.parent.LUI('calendar').refreshSchedules();
								return ;
							}
							if(rtn.schedule!=null){
								if(typeof setColor!="undefined"){
									window.parent.setColor(rtn.schedule);
								}
								window.parent.LUI('calendar').updateSchedule(rtn.schedule);
							}
						}
					}
				},{width:700,height:550});
			};
			window.DateDiff=function(sDate, eDate,opt) { //sDate和eDate是yyyy-MM-dd格式
				var date1 = new Date(sDate);
				var date2 = new Date(eDate);
				if(opt=="day"){
					var date3=date2.getTime()-date1.getTime();
					var days=Math.floor(date3/(24*3600*1000));
					return days;
				}
				if(opt=="year"){
					var year=date2.getFullYear()-date1.getFullYear();
					return year;
				}
			};
			window.getTimeHTML=function(y,m,d,count){
				var yearStr='${lfn:message("km-calendar:year")}';
				var monthStr='${lfn:message("km-calendar:month")}';
				var dayStr='${lfn:message("km-calendar:day")}';
				return count>0 ? y+yearStr+m+monthStr+d+dayStr : m+monthStr+d+dayStr;
			};
			window.setLabelSelected=function(){
				if(typeof "${kmCalendarMainForm.labelId}"!="undefined"){
					$("select[name='labelId']").val("${kmCalendarMainForm.labelId}");
					$("select[name='labelId']").change();
				}													
			};
		})
		</script>
	<html:form action="/km/calendar/km_calendar_main/kmCalendarMain.do">
		<!-- 时间管理弹窗 Starts -->
		<div class="lui_calendar_remind_iframe">
			<!-- 头部 -->
			<div class="lui_calendar_remind_head">
				<span class="remind_calendar_label label_primary lui_text_primary"></span>
			</div>
			<!-- 主体内容 -->
			<div class="lui_calendar_remind_content">
				<!-- 时间跨度 -->
				<div class="remind_calendar_period">
					<div class="remind_calendar_item calendar_period_startDate">
						<h4 class="remind_calendar_date" id="docStartTime">
						</h4>
						<p class="remind_calendar_time">
							<c:if test="${ kmCalendarMainForm.fdIsAlldayevent != 'true' }">
								<c:out
									value="${'true'.equals(kmCalendarMainForm.fdIsLunar)? kmCalendarMainForm.lunarStartHour:kmCalendarMainForm.startHour}:${'true'.equals(kmCalendarMainForm.fdIsLunar)? kmCalendarMainForm.lunarStartMinute:kmCalendarMainForm.startMinute }"></c:out>
							</c:if>
							<c:if test="${ kmCalendarMainForm.fdIsAlldayevent == 'true' }">
			             		00:00
			             	</c:if>
						</p>
					</div>
					<div class="remind_calendar_item calendar_item_connect">
						<p class="duration"></p>
					</div>
					<div class="remind_calendar_item calendar_period_endDate">
						<h4 class="remind_calendar_date" id="docFinishTime">
						</h4>
						<p class="remind_calendar_time">
							<c:if test="${ kmCalendarMainForm.fdIsAlldayevent != 'true' }">
								<c:out
									value="${'true'.equals(kmCalendarMainForm.fdIsLunar)? kmCalendarMainForm.lunarEndHour:kmCalendarMainForm.endHour}:${'true'.equals(kmCalendarMainForm.fdIsLunar)? kmCalendarMainForm.lunarEndMinute:kmCalendarMainForm.endMinute }"></c:out>
							</c:if>
							<c:if test="${ kmCalendarMainForm.fdIsAlldayevent == 'true' }">
		                    		23:59
		                    	</c:if>
						</p>
					</div>
				</div>


				<!-- 提示信息 -->
				<div class="remind_calendar_desc">
					<p>
						<xform:textarea property="docSubject" showStatus="view"></xform:textarea>
					</p>
				</div>

				<!-- 提醒内容 -->
				<div class="remind_calendar_content">
					<p class="remind_calendar_btnGroup">
						<c:if test="${not empty kmCalendarMainForm.fdRelationUrl}">
							<span class="lui_text_primary fr"
								onclick="window.openSource('${kmCalendarMainForm.fdRelationUrl }');">
								<%-- 查看日程来源 --%> <bean:message bundle="km-calendar"
									key="kmCalendarMain.calendar.source" /> <i
								class="remind_calendar_arrow calendar_arrow_right"></i>
							</span>
							</c:if>
					</p>
					
				</div>
			</div>
			<!-- 底部按钮 -->
			<div class="lui_calendar_remind_footer">
				<ul id="ul_label_edit" class="lui_list_nav_list remind_calendar_footer_fl">
					<ui:dataview id="labelId" name="labelId">
						<ui:source type="AjaxJson">
							{url:'/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson'}
						</ui:source>
						<ui:render type="Template">
								<c:import url="/km/calendar/tmpl/label_select.jsp" charEncoding="UTF-8"></c:import>
						</ui:render>
						<ui:event event="load">
							setLabelSelected();
						</ui:event>
					</ui:dataview>
				</ul>
				<div class="lui_calendar_remind_footer_fr">
					<ui:button text="${lfn:message('button.ok')}"  onclick="updateLabelAndRemind();"/>
						<ui:button text="${lfn:message('button.delete')}"  styleClass="lui_toolbar_btn_gray"  onclick="delDoc('kmCalendarMainForm','kmCalendarMainForm');"/>
						<ui:button text="${lfn:message('km-calendar:kmCalendarMain.moreSetting')}"   styleClass="lui_toolbar_btn_gray"  id="btn_Calendar_detail" onclick="eidtEventDetail();"/>
				</div>
			</div>
		</div>
		<!-- 时间管理弹窗 Ends -->
		</html:form>
	</template:replace>
</template:include>