<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<template:include ref="mobile.view" >
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
					<%--内容--%>
					<tr>
						<td class="muiTitle">
							<bean:message bundle="km-calendar" key="kmCalendarMain.docContent" />
						</td>
						<td>
							<xform:rtf property="docContent" mobile="true"></xform:rtf>
						</td>
					</tr>
					<%--附件--%>
					<tr>
						<td colspan="2">
							<c:import url="/sys/attachment/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmCalendarMainForm" />
								<c:param name="fdKey" value="fdAttachment" />
							</c:import>
						</td>
					</tr>
				</table>
			</div>
			
			
			
		</div>
	</template:replace>
</template:include>
<script>
require(['dojo/ready','dojo/query','dojo/date','mui/calendar/CalendarUtil',"mui/dialog/Dialog","dojo/dom-construct","dojo/_base/lang","dojo/request","mui/util"],
		function(ready,query,date,cutil,Dialog,domConstruct,lang,request,util){
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
			currentDate=new Date();
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
	});
	
});
</script>



