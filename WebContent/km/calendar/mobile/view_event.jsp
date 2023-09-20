<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<template:include ref="mobile.view">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/mobile/resource/css/view.css?s_cache=${MUI_Cache}" />
	</template:replace>
	
	<template:replace name="title">
		<c:out value="${lfn:message('km-calendar:kmCalendarMain.detailDocContent') }"></c:out>
	</template:replace>
	
	<template:replace name="content">
		<div id="scrollView" class="gray" data-dojo-type="mui/view/DocScrollableView">
			<div class="muiScheduleReadBox">
				<div class="muiScheduleReadHeaer">
					<ul class="dateBar">
						<%-- 几天前？今天？几天后 --%>
						<li>
							<span class="curDate"></span>
						</li>
						<%-- 选中日期 --%>
						<li class="date">
							<c:out value="${param.currentDate }"></c:out>
						</li>
						<%-- 星期几 --%>
						<li class="week"></li>
					</ul>
				</div>
				<p class="txtContent">
					<c:if test="${ kmCalendarMainForm.fdAuthorityType == 'PRIVATE' }">
						<span class="icon fontmuis muis-lock"></span>
					</c:if>
					<xform:textarea property="docSubject" showStatus="view" ></xform:textarea>
				</p>
			</div>	
			
			<div class="muiScheduleDateBox">
				<ul class="inner">
					<%-- 开始时间 --%>
					<li class="colBar">
						<div class="dateBrand left">
		                    <div class="head blueBg">
		                    	<xform:datetime property="docStartTime" dateTimeType="date"></xform:datetime>
		                    </div>
		                    <div class="content">
		                    	<c:if test="${ kmCalendarMainForm.fdIsAlldayevent != 'true' }">
		                    		<c:out value="${kmCalendarMainForm.startHour}:${kmCalendarMainForm.startMinute }"></c:out>
		                    	</c:if>
		                    	<c:if test="${ kmCalendarMainForm.fdIsAlldayevent == 'true' }">
		                    		00:00
		                    	</c:if>
		                    </div>
		                    <a class="btn" href="#">
		                    	<bean:message bundle="km-calendar" key="kmCalendarMain.docStartTime" />
		                    </a>
		                </div>
					</li>
					<%-- 结束时间 --%>
					<li class="colBar">
						<div class="dateBrand right">
		                    <div class="head redBg">
		                    	<xform:datetime property="docFinishTime" dateTimeType="date"></xform:datetime>
		                    </div>
		                    <div class="content">
		                    	<c:if test="${ kmCalendarMainForm.fdIsAlldayevent != 'true' }">
		                    		<c:out value="${kmCalendarMainForm.endHour}:${kmCalendarMainForm.endMinute }"></c:out>
		                    	</c:if>
		                    	<c:if test="${ kmCalendarMainForm.fdIsAlldayevent == 'true' }">
		                    		23:59
		                    	</c:if>
		                    </div>
		                    <a class="btn" href="#">
		                    		<bean:message bundle="km-calendar" key="kmCalendarMain.docFinishTime" />
		                    </a>
		                </div>
					</li>
				</ul>
				<%-- 开始、结束时间差 --%>
				<div class="countdownBar">
					<i class="mui mui-alarm"></i>
					<div class="duration"></div>
				</div>
			</div>
			
			<div class="muiFormContent kmCalendarFormContent">
				<table class="muiSimple" cellpadding="0" cellspacing="0">
					<%--地点--%>
					<c:if test="${not empty kmCalendarMainForm.fdLocation }">
						<tr>
							<td class="muiTitle">
								<bean:message bundle="km-calendar" key="kmCalendarMain.fdLocation" />
							</td>
							<td>
								<xform:text property="fdLocation" mobile="true"/>
							</td>
						</tr>
					</c:if>
					<%--标签--%>
					<c:if test="${not empty kmCalendarMainForm.labelId }">
						<tr>
							<td class="muiTitle" style="vertical-align:middle;">
								<bean:message bundle="km-calendar" key="kmCalendarMain.docLabel" />
							</td>
							<td>
								<c:if test="${not empty kmCalendarMainForm.labelId }">
									<div class="docLabelContainer <c:if test='${empty kmCalendarMainForm.labelColor  || kmCalendarMainForm.labelColor  eq ""}'>muiCalendarDefaultLabel</c:if>"  <c:if test='${not empty kmCalendarMainForm.labelColor}'>style="background-color: ${kmCalendarMainForm.labelColor}"</c:if>>
										<c:out value="${kmCalendarMainForm.labelName }"></c:out>
										
									</div>
								</c:if>
								
								<c:if test="${empty kmCalendarMainForm.labelId }">
									<div class="docLabelContainer muiCalendarDefaultLabel">
										<bean:message bundle="km-calendar" key="kmCalendar.nav.title"/>
									</div>
								</c:if>
							</td>
						</tr>
					</c:if>
					
					<%--提醒--%>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="km-calendar" key="kmCalendarMain.fdNotifySet" />
						</td>
						<td>
							<div id="remind">
								<c:if test="${empty kmCalendarMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList }">
									<bean:message bundle="km-calendar" key="kmCalendarMain.noReminder" />
								</c:if>
								<c:if test="${not empty kmCalendarMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList }">
									<c:choose>
									<c:when test="${kmCalendarMainForm.fdIsAlldayevent eq 'true' }">
										<c:forEach items="${kmCalendarMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="remind">
											<div>
												<c:choose>
													<c:when test="${lfn:ceil(remind.fdBeforeTime/1440) eq 0}">
														<div><bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyDay.0" />,</div>
														<div>
															<c:set var="hour" value="${lfn:ceil(remind.fdBeforeTime%1440/60)}" />
															<c:choose>
																<c:when test="${lfn:ceil(remind.fdBeforeTime%60) eq 0}">
																	<c:set var="time" value="00" />
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${lfn:ceil(remind.fdBeforeTime%60) lt 10}">
																			<c:set var="time" value="0${lfn:ceil(remind.fdBeforeTime%60)}" />
																		</c:when>
																		<c:otherwise>
																			<c:set var="time" value="${lfn:ceil(remind.fdBeforeTime%60)}" />
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
															${hour}<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.hour" />${time}<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.time" />,
														</div>
													</c:when>
													<c:otherwise>
														<div>${lfn:ceil(remind.fdBeforeTime/1440)}天前,</div>
														<div>
															<c:set var="hour" value="${24-lfn:ceil(remind.fdBeforeTime%1440/60)}" />
															<c:choose>
																<c:when test="${60-lfn:ceil(remind.fdBeforeTime%60) eq 60}">
																	<c:set var="time" value="00" />
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${60-lfn:ceil(remind.fdBeforeTime%60) lt 10}">
																			<c:set var="time" value="0${60-lfn:ceil(remind.fdBeforeTime%60)}" />
																		</c:when>
																		<c:otherwise>
																			<c:set var="time" value="${60-lfn:ceil(remind.fdBeforeTime%60)}" />
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
															${hour}<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.hour" />${time}<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.time" />,
														</div>
													</c:otherwise>
												</c:choose>
												<div>
													<xform:select property="" value="${remind.fdNotifyType}" showStatus="view">
														<xform:enumsDataSource enumsType="sys_notify_fdNotifyType" />
													</xform:select>
									 				<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.notify" />
												</div>
											</div>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<c:forEach items="${kmCalendarMainForm.sysNotifyRemindMainContextForm.sysNotifyRemindMainFormList}" var="remind">
											<div>
												<div>
													<c:choose>
														<c:when test="${remind.fdBeforeTime eq 0}">
															<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.earlyTime.0" />,
														</c:when>
														<c:otherwise>
															<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.early" />${remind.fdBeforeTime}<xform:select property="" value="${remind.fdTimeUnit}" showStatus="view"><xform:enumsDataSource enumsType="sys_notify_fdTimeUnit" /></xform:select>,
														</c:otherwise>
													</c:choose>
												</div>
												<div>
													<xform:select property="" value="${remind.fdNotifyType}" showStatus="view">
														<xform:enumsDataSource enumsType="sys_notify_fdNotifyType" />
													</xform:select>
									 				<bean:message bundle="km-calendar" key="kmCalendarNotifyRemaindMain.notify" />
												</div>
											</div>
										</c:forEach>
									</c:otherwise>
								</c:choose>
								</c:if>
								
							</div>
						</td>
					</tr>
					<%--重复--%>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="km-calendar" key="recurrence.end.freq" />
						</td>
						<td>
							<c:choose>
								<c:when test="${kmCalendarMainForm.fdIsLunar}">
									<div>
										<xform:select property="RECURRENCE_FREQ_LUNAR" mobile="true">
											<xform:enumsDataSource enumsType="km_calendar_recurrence_freq_lunar" />
										</xform:select>
									</div>
								</c:when>
								<c:otherwise>
									<div>
										<xform:select property="RECURRENCE_FREQ" mobile="true">
											<xform:enumsDataSource enumsType="km_calendar_recurrence_freq" />
										</xform:select>
									</div>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<c:if test="${not empty kmCalendarMainForm.fdRelationUrl}">
						<%-- 查看日程来源 --%>
						<tr onclick="window.openSource('${kmCalendarMainForm.fdRelationUrl }');">
							<td class="muiTitle" >
								<bean:message bundle="km-calendar" key="kmCalendarMain.calendar.source"/>		
							</td>
						    <td  style="position:relative;">
						    <i class="mui mui-forward" style="position:absolute;top:50%;right: 0;margin-top:-0.8rem;line-height:1.5rem;"></i>
						    </td>
						</tr>
					</c:if>
					<c:if test="${kmCalendarMainForm.createdFrom=='KK_IM' && not empty kmCalendarMainForm.fdSourceSubject}">
						<%-- 来源于会话 --%>
						<tr mark-createdFrom="KK_IM" style="display:none;" onclick="window.openSource();">
							<td class="muiTitle" >
								<bean:message bundle="km-calendar" key="kmCalendarMain.calendar.source.kkim"/>		
							</td>
						    <td>
						    	<div style="word-wrap: break-word; word-break: break-all;">${kmCalendarMainForm.fdSourceSubject }</div>
						    </td>
						</tr>
					</c:if>
				</table>
			</div>
		</div>
		<div class="muiCalendarOptBar">
				<kmss:auth requestURL="/km/calendar/km_calendar_main/kmCalendarMain.do?method=delete&fdId=${param.fdId }">
					<div class="muiCalendarDeleteOpt" onclick="window.deleteCalendar();">
						<i class="mui mui-cancel"></i><bean:message key="button.delete" />
					</div>
				</kmss:auth>
				<kmss:auth requestURL="/km/calendar/km_calendar_main/kmCalendarMain.do?method=update&fdId=${HtmlParam.fdId }">
					<div class="muiCalendarEditOpt" onclick="window.editCalendar();">
						<i class="mui mui-calendarEdit"></i><bean:message key="button.edit" />
					</div>
				</kmss:auth>
			</div>
	</template:replace>
</template:include>
<script>
require(['dojo/ready','dojo/query','dojo/date','mui/calendar/CalendarUtil',"mui/dialog/Dialog","mui/device/adapter",
		"dojo/dom-construct","dojo/_base/lang","dojo/request","mui/util","dojo/dom-style"],
		function(ready,query,date,cutil,Dialog,adapter,domConstruct,lang,request,util,domStyle){
	ready(function(){
		//开始、结束时间差
		var start=cutil.parseDate('${kmCalendarMainForm.docStartTime}'),
			end=cutil.parseDate('${kmCalendarMainForm.docFinishTime}'),
			duration=date.difference(start,end,'day')+1,//时间间隔
			allDay='${kmCalendarMainForm.fdIsAlldayevent}';//是否全天
		if(duration==1 && allDay=='false'){
			var startHour=parseInt('${kmCalendarMainForm.startHour}'),
				endHour=parseInt('${kmCalendarMainForm.endHour}'),
				startMinute=parseInt('${kmCalendarMainForm.startMinute}'),
				endMinute=parseInt('${kmCalendarMainForm.endMinute}'),
				start = startHour * 60 + startMinute,
				end = endHour * 60 + endMinute,
				_hour=parseInt((end - start)/60),
				_minute= (end - start)%60;
			query('.duration')[0].innerHTML='';
			if(_hour != 0){
				query('.duration')[0].innerHTML+=parseInt(_hour)+'<bean:message key="date.interval.hour" />';
			}
			if(_minute != 0){
				query('.duration')[0].innerHTML+=parseInt(_minute)+'<bean:message key="date.interval.minute" />';
			}
		}else{
			query('.duration')[0].innerHTML=duration+'<bean:message key="date.interval.day" />';
		}
		
		//选中日初始化
		var currentDate='${param.currentDate}';
		if(currentDate){
			currentDate=cutil.parseDate(currentDate);
		}else{
			//获取日程的开始时间
			currentDate = cutil.parseDate('${kmCalendarMainForm.docStartTime}');
		}
		var weekArray='${lfn:message("calendar.week.names")}'.split(','),
			now=new Date();
		now.setHours(0,0,0,0);
		var duration=date.difference(currentDate,now,"day");
		query('.week')[0].innerHTML=weekArray[currentDate.getDay()];//设置星期
		if(duration < 0){
			duration=0-duration;
			curDateStr='<em>'+duration+'</em>${lfn:message("km-calendar:daily")}';
			if(dojoConfig.locale == 'en-us'){
				curDateStr += ' ';
			}
			curDateStr += '${lfn:message("km-calendar:after")}';
		}else if(duration == 0){
			curDateStr='<em class="Today">${lfn:message("sys-ui:ui.calendar.today")}</em>';
		}else{
			curDateStr='<em>'+duration+'</em>${lfn:message("km-calendar:daily")}';
			if(dojoConfig.locale == 'en-us'){
				curDateStr += ' ';
			}
			curDateStr += '${lfn:message("km-calendar:before")}';
		}
		query('.curDate')[0].innerHTML=curDateStr;
		
		//kkim
		initKKIM();
	});
	
	//删除日程
	window.deleteCalendar=function(){
		var contentNode = domConstruct.create('div', {
			className : 'muiBackDialogElement',
			innerHTML : '<div><bean:message bundle="km-calendar" key="kmCalendarMain.opt.delete.tip.mobile" /><div>'
		});
		Dialog.element({
			'title' : '<bean:message bundle="sys-mobile"  key="mui.dialog.tips"/>',
			'showClass' : 'muiBackDialogShow',
			'element' : contentNode,
			'scrollable' : false,
			'parseable': false,
			'buttons' : [ {
				title : '<bean:message key="button.cancel" />',
				fn : function(dialog) {
					dialog.hide();
				}
			} ,{
				title : '<bean:message key="button.ok" />',
				fn : lang.hitch(this,function(dialog) {
					//ajax删除日程
					var url='${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=delete&fdId=${param.fdId}'
					request
					.get(url, {handleAs : 'json',headers: {"accept": "application/json"}})
					.response
					.then(function(datas) {
						if(datas.status=='200'){
							dialog.hide();
							adapter.goBack();
							//location.href='${LUI_ContextPath}/km/calendar/mobile/index.jsp?fromOpt=del';
						}
					});
				})
			} ]
		});
	};
	
	//编辑日程
	window.editCalendar=function(){
		location.href='${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=edit&fdId=${param.fdId}&_referer='+encodeURIComponent('${LUI_ContextPath}/km/calendar/km_calendar_main/kmCalendarMain.do?method=view&fdId=${param.fdId}&_='+new Date().getTime());
	};
	window.initKKIM = function(){
		var fdMessageIndex = "${kmCalendarMainForm.fdMessageIndex}";
		var fdSessionType = '${kmCalendarMainForm.fdSessionType}';
		if(!fdSessionType || !adapter.isSessionMemberByMsg){
			return;
		}
		var options ={
				msgSenderID:'${kmCalendarMainForm.fdMessageSenderId}',
				msgReceiverID:'${kmCalendarMainForm.fdMessageReceiverId}',
				sessionType:fdSessionType
		}
		console.log("kk日程来源跳转相关参数:"+JSON.stringify(options));
		adapter.isSessionMemberByMsg(options,function(res){
			domStyle.set(query('div[mark-createdFrom="KK_IM"]')[0],'display','block');
		},function(){
		});
	};
	window.openSource=function(url){
		if(url){
			url = util.formatUrl(url);
			window.open(url,'_self');
			return;
		}

		var fdMessageIndex = "${kmCalendarMainForm.fdMessageIndex}";
		var fdSessionType = '${kmCalendarMainForm.fdSessionType}';
		if(!fdSessionType || !adapter.isSessionMemberByMsg){
			return;
		}
		var options ={
				msgSenderID:'${kmCalendarMainForm.fdMessageSenderId}',
				msgReceiverID:'${kmCalendarMainForm.fdMessageReceiverId}',
				sessionType:fdSessionType
		}
		console.log("kk日程来源跳转相关参数:"+JSON.stringify(options));
		adapter.isSessionMemberByMsg(options,function(res){
			var _options ={
				msgIndex:fdMessageIndex
			};
			if(fdSessionType==0){
				_options.userId=res.chatID;
				console.log("调用p2p聊天窗口参数:"+JSON.stringify(_options));
				adapter.openChat(_options,function(){
					console.log("调用p2p聊天窗口成功");
				},function(){
					console.log("调用p2p聊天窗口失败");
				});
				return;
			}
			if(fdSessionType==1 || fdSessionType==2){
				_options.groupID=res.chatID;
				console.log("调用群聊窗口参数:"+JSON.stringify(_options));
				adapter.sendGroup(_options,function(){
					console.log("调用群聊窗口成功:");
				},function(err){
					console.log("调用群聊窗口失败:"+err);
				});
				return;
			}
		},function(){
		});
	};
	
});
</script>



