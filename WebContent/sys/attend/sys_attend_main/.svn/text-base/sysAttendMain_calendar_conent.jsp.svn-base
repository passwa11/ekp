<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<ui:calendar id="calendar" showStatus="view" layout="sys.attend.calendar.default" mode="attendCalendar" customMode="{'id':'attendCalendar','name':'考勤日历','func':'sys/attend/sys_attend_main/calendar/attendCalendar.js'}">
	<ui:source type="AjaxJson">
		{url:'/sys/attend/sys_attend_main/sysAttendMain.do?method=mycalendar&categoryType=${categoryType }'}
	</ui:source>
	<ui:render type="Template">
		<c:if test="${categoryType eq 'attend'}">
			var fdStatus = data['fdStatus'],
				fdStatusText = '';
			var fdOutside = data['fdOutside'],
				fdOutsideText = "${ lfn:message('sys-attend:sysAttendMain.outside') }";
			switch(fdStatus){
				case 0 : 
					fdStatusText =  "${ lfn:message('sys-attend:sysAttendMain.fdStatus.unSign') }"; break;
				case 1 : 
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }"; break;
				case 2 : 
					 fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.late') }"; break;
				case 3 : 
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.left') }"; break;
				case 4 : 
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.business') }"; break;
				case 5 : 
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }"; break;
				case 6 : 
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }"; break;
				default : break;
			}
			
			if(fdStatus == 1 && fdOutside){
				fdStatusText = fdOutsideText;
			}
			
			var fdState = data['fdState'],
				fdStateText = '';
			switch(fdState) {
				case 0 : 
					fdStateText =  "${ lfn:message('sys-attend:sysAttendMain.fdState.undo') }"; break;
				case 1 : 
					fdStateText = "${ lfn:message('sys-attend:sysAttendMain.fdState.doing') }"; break;
				case 2 : 
					fdStateText = "${ lfn:message('sys-attend:sysAttendMain.fdState.done') }"; break;
				case 3 : 
					fdStateText = "${ lfn:message('sys-attend:sysAttendMain.fdState.refuse') }"; break;
				default : break;
			}
			
			var fdWorkType = data['fdWorkType'],
				fdWorkTypeText = '';
			if(fdWorkType == 0) {
				fdWorkTypeText =  "${ lfn:message('sys-attend:sysAttendMain.fdWorkType.onwork') }";
			} else if(fdWorkType == 1) {
				fdWorkTypeText = "${ lfn:message('sys-attend:sysAttendMain.fdWorkType.offwork') }";
			}
				
			var attendTime = data['attendTime'];
				
			var title = '';
			var statusClassName = '';
			if(fdStatus == 1){
				if(fdOutside) {
					title = attendTime + ' ' + fdWorkTypeText + fdStatusText;
					statusClassName = 'sysAttendCalIcon outside';
				} else {
					title = attendTime + ' ' + fdWorkTypeText + fdStatusText;
					statusClassName = 'sysAttendCalIcon normal';
				}
			}
			
			if(fdStatus == 0 || fdStatus == 2 || fdStatus == 3
				|| data['fdOsdReviewType'] == 1 && fdOutside){
				if(fdState) {
					switch(data['fdState']) {
					case 0: 
					case 1: 
					case 3:
						title = attendTime + ' ' + fdWorkTypeText + fdStatusText + '（' + fdStateText + '）';
						statusClassName = 'sysAttendCalIcon late';
						break;
					case 2: {
						title = attendTime + ' ' + fdWorkTypeText + "${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok') }";
						statusClassName = 'sysAttendCalIcon normal';
						break;
					}
					default: break;
					}
				} else {
					title = attendTime + ' ' + fdWorkTypeText + fdStatusText;
					statusClassName = 'sysAttendCalIcon late';
				}
			}
			
			if(fdStatus == 4 || fdStatus == 5 || fdStatus == 6){
				if(data['fdBussSigned']=='true'){
					title = attendTime + ' ' + fdStatusText + ' ' + (fdOutside ? "${ lfn:message('sys-attend:sysAttendMain.outside') }":'');
					if(fdStatus == 5 && data['fdOffTypeText']){
						title = attendTime +' ' + fdWorkTypeText +' '+ data['fdOffTypeText'];
					}
				}else{
					var onMissTxt = '${ lfn:message('sys-attend:sysAttendMain.status.onMissTxt') }';
					var offMissTxt = '${ lfn:message('sys-attend:sysAttendMain.status.offMissTxt') }';
					var attendTimeTxt = fdWorkType==0 ? onMissTxt:offMissTxt;
					
					if(fdStatus == 5 && data['fdOffTypeText']){
						title = attendTimeTxt + ' ' + data['fdOffTypeText'];
					}else{
						title = attendTimeTxt + ' ' +fdStatusText;
					}
				}
				statusClassName = 'sysAttendCalIcon normal';
			}
			
			if(data['fdBussHead']=='true'){
				var fdBussTitle = data['fdBussTitle'],
				    fdBussStartTime = data['fdBussStartTime'],
				    fdBussEndTime = data['fdBussEndTime'];
				{$ <a class="sysAttendCalItem textEllipsis lui_attendCalendar_status_title" href="javascript:void(0);">$}
			
				{$		<span class="lui_attendCalendar_txt">
							<span class="sysAttendCalItemLabel">{%fdStatusText%}</span>
							<span class="sysAttendCalItemLabel_tile">{%fdBussTitle%}</span>
						</span>
						
				  </a>$}
			}else{
				{$ <a class="sysAttendCalItem textEllipsis" href="javascript:void(0);" title="{%title%}">$}
				{$		<i class="{%statusClassName%}"></i> $}
				{$		<span class="sysAttendCalItemLabel">{%title%}</span></a>$}
			}
			
		</c:if>
		<c:if test="${categoryType eq 'custom' }">
			var title = [data['attendTime'], "${ lfn:message('sys-attend:sysAttendMain.signin') }"].join(' ');
			var statusClassName = 'sysAttendCalIcon normal';
			
			{$ <a class="sysAttendCalItem textEllipsis" href="javascript:void(0);" title="{%title%}">$}
			{$		<i class="{%statusClassName%}"></i> $}
			{$		<span class="sysAttendCalItemLabel">{%title%}</span></a>$}
		</c:if>
	</ui:render>
</ui:calendar>
<div id="calendar_buss_dialog_view" class="calendar_buss_dialog" style="position: absolute; display: none">
	<span class="fdBussUrl" style="display: none;"></span>
	<%--顶部--%>
	<div class="calendar_buss_dialog_top">
		<div class="calendar_buss_dialog_title">
			
		</div>
		<div class="calendar_buss_dialog_close" onclick="CalendarBussDialogClose()"></div>
	</div>
	<%--内容区--%>
	<div class="calendar_buss_dialog_content" >
		<div>
			<%--名称--%>
			<span class="title" style="display:block;float: left;">
				${ lfn:message('sys-attend:myCalendar.fdBussTitle') }
			</span>
			<span class="fdName" style="display:block;float: left;text-align: justify;width:230px;word-break: break-all;"></span>
			<div style="clear: both;"></div>
		</div>
		<div>
			<%--时间--%>
			<span class="title">
				${ lfn:message('sys-attend:myCalendar.fdBussTime') }
			</span>
			<span class="fdBussTime"></span>
		</div>
	</div>
	<%--底部--%>
	<div class="calendar_buss_dialog_buttom">
		<%-- 查看 --%>
		<div  style="text-align: center;"><ui:button id="calendar_view_btn" text="${lfn:message('button.view') }"  styleClass="lui_toolbar_btn_gray" onclick="bussView()"/></div>
	</div>
</div>
<script>
	seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
		topic.subscribe('calendar.loaded',function(arg){
			if(parent.document.all("mainIframe")){
				parent.document.all("mainIframe").style.height=(document.body.offsetHeight+30)+'px';
			}
		});
		topic.subscribe('calendar.thing.click',function(arg){
			var fdId=arg.schedule.fdId;
			if(arg.schedule.fdBussHead=='true'){
				var fdStatus = arg.schedule.fdStatus;
				var fdStatusText = '';
				if(fdStatus==4){
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.business') }";
				}
				if(fdStatus==5){
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave') }";
				}
				if(fdStatus==6){
					fdStatusText = "${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing') }";
				}
				var viewDialog=$("#calendar_buss_dialog_view");
				viewDialog.find(".calendar_buss_dialog_title").text(fdStatusText);
				viewDialog.find(".fdName").text(arg.schedule.fdBussTitle);
				viewDialog.find(".fdBussTime").text(arg.schedule.fdBussStartTime+"~"+arg.schedule.fdBussEndTime);
				viewDialog.find(".fdBussUrl").text(arg.schedule.href);
				viewDialog.css(getPos(arg.evt,viewDialog)).fadeIn("fast");
				//window.open('${LUI_ContextPath}'+arg.schedule.href,"_blank");
				return;
			}
			window.open('${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain.do?method=view&fdId='+fdId,"_blank");
		});
		window.CalendarBussDialogClose = function(){
			$('#calendar_buss_dialog_view').fadeOut("fast");
		};
		//定位
		window.getPos=function(evt,obj){
			var sWidth=obj.width();var sHeight=obj.height();
			var x=evt.pageX;
			var y=evt.pageY;
			if(y+sHeight>$(window).height()){
				y-=sHeight;
			}
			if(x+sWidth>$(document.body).outerWidth(true)){
				x-=sWidth;
			}
			var topY = (y-50>10)?(y-50):10;
			var leftX = (x-200>10)?(x-200):10;
			return {"top":topY,"left":leftX};
		};
		window.bussView = function(){
			var href = $('#calendar_buss_dialog_view').find(".fdBussUrl").text();
			window.open('${LUI_ContextPath}'+href,"_blank");
		}
		window.openBussPage = function(href){
			window.open('${LUI_ContextPath}'+href,"_blank");
		};
		window.onStatusOver = function(evt){
			var left = $(evt).offset().left;
			var p = $(document).width()-200-260;
			var pLeft = 100;
			if(left > p){
				pLeft = -100
			}
			$(evt).parent().find('.lui_attendCalendar_title_tip').css('left',pLeft+'px').show();
		};
		window.onStatusLeave = function(evt){
			$(evt).parent().find('.lui_attendCalendar_title_tip').hide();
		};
	});
</script>