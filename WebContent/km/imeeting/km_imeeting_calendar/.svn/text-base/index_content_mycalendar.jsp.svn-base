<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	//UA=mobile跳转到移动端的主页(临时解决方案)
	if(MobileUtil.getClientType(new RequestContext(request)) > -1){
		response.sendRedirect(request.getContextPath() + "/km/imeeting/mobile/index.jsp");
	}
%>
<template:include ref="default.simple" spa="true">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/km_imeeting_calendar/calendar.css" />
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/resource/css/index.css" />
	</template:replace>
	<%-- 右侧内容区域 --%>
	<template:replace name="body">
	<style>
		.calendar-title-limit {
			width:12px;
			height:13px;
			margin-left:5px;
			display:inline-block;
			margin-right:3px;
			vertical-align:middle;
		}
	</style>
		<script>
			seajs.use(['theme!list']);	
			seajs.use(['km/imeeting/resource/js/dateUtil'], function(dateUtil) {
				window.dateUtil=dateUtil;
			});
		</script>
		<ui:calendar id="calendar" showStatus="view" mode="default" layout="km.imeeting.calendar.my">
			<%--数据--%>
			<ui:source type="AjaxJson">
				{url:'/km/imeeting/km_imeeting_calendar/kmImeetingCalendar.do?method=mycalendar&showType=my'}
			</ui:source>
			<ui:render type="Template">
				var formaStr=seajs.data.env.pattern.datetime+"{ ' ~ ' "+seajs.data.env.pattern.datetime+"}";
				var title=dateUtil.formatDate(data['start'],formaStr)+" ~ "+dateUtil.formatDate(data['end'],formaStr);
				title+="&#xd;"+ env.fn.formatText(data['title'].replace(/(\n)+|(\r\n)+/g, " "));
				{$<p title="{%title%}" style="cursor: pointer;">$}
				var str="";
				var hours=data['start'].getHours()<10?"0"+data['start'].getHours():data['start'].getHours();
				var minutes=data['start'].getMinutes()<10?"0"+data['start'].getMinutes():data['start'].getMinutes();
				str+=hours+":"+minutes+" "
				if(data['title']){
					str+=env.fn.formatText(data['title'].replace(/(\n)+|(\r\n)+/g, " "));
				}
				{$<span class="textEllipsis">{%str%}</span></p>$}
			</ui:render>
			<%--不显示全天（会议不存在全天）--%>
			<ui:event event="viewOrModeChange">
				$('.fc-agenda-allday').hide();
				<c:if test="${'1' eq param.isWorkbench }">
					window.frameElement.style.height = $("#calendar").height()+"px";
					setTimeout(function() {
						window.parent.resizeMainChart();
					},500);
					if(arguments && arguments[0]) {
						if(arguments[0].view.name == 'month') {
							$(".lui_calendar_header").addClass("month").removeClass("week");
						} else if(arguments[0].view.name == 'basicWeek') {
							$(".lui_calendar_header").addClass("week").removeClass("month");
						}
					}
				</c:if>
			</ui:event>
		</ui:calendar>
		
		<script>
			var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingMain;com.landray.kmss.km.imeeting.model.KmImeetingSummary";
		
			seajs.use([
			    'lui/dialog',       
				'lui/topic',
				'lui/jquery',
				'km/imeeting/resource/js/dateUtil',
				'lui/util/env'
				], function(dialog,topic,$,dateUtil,env) {
					
				
					// 监听新建更新等成功后刷新
					topic.subscribe('successReloadPage', function() {
						setTimeout(function(){
							LUI('calendar').refreshSchedules();
						}, 100);
					});
					
					
					// 监听事件
					topic.subscribe('calendar.loaded', function(evt) {
						<c:if test="${'1' eq param.isWorkbench }">
							window.frameElement.style.height = $("#calendar").height()+"px";
							setTimeout(function() {
								window.parent.resizeMainChart();
							},500);
						</c:if>
					});
					
					// 监听事件
					topic.subscribe('calendar.loading', function(evt) {
						<c:if test="${'1' eq param.isWorkbench }">
							if(!evt.status) {
								window.frameElement.style.height = $("#calendar").height()+"px";
								setTimeout(function() {
									window.parent.resizeMainChart();
								},500);
								var wd = $(".lui_calendar_header").width()-$(".lui_calendar_list_first").width();
								$(".lui_calendar_content_list .lui_calendar_list_second .lui_calendar_list_data").width(wd);
							}
						</c:if>
					});
				
					//新建会议
			 		window.addDoc = function() {
							dialog.categoryForNewFile(
									'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
									'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{id}',false,null,null,'${JsParam.categoryId}');
				 	};
				
					//定位
					var getPos=function(evt,obj){
						var sWidth=obj.width();var sHeight=obj.height();
						x=evt.pageX;
						y=evt.pageY;
						if(y+sHeight>$(window).height()){
							y-=sHeight;
						}
						if(x+sWidth>$(document.body).outerWidth(true)){
							x-=sWidth;
							//避免遮挡#103209
							if(x < 0) {
								x = 0;
							} 
						}
						//避免会议室列表过少导致遮挡情况
						if(y < 0) {
							y = 0;
						}
						return {"top":y,"left":x};
						
					};
					
					topic.subscribe('FullCalendar:click',function(arg){
						var selectDate = arg.date;
						var addDialog=$('#meeting_calendar_add');
						//时间格式2014-7-11
						var date=dateUtil.formatDate(arg.date,'${dateFormatter}');
						addDialog.find('.date').html(date);//时间字符串
						addDialog.find('.fdHoldDate').html(date);//召开时间
						addDialog.find('.fdFinishDate').html(date);//结束时间
						addDialog.find('.resId').parent().hide();//地点隐藏
						//addDialog.find(".meeting_calendar_dialog_buttom").css("line-height","15px");
						addDialog.css(getPos(arg.evt,addDialog)).fadeIn("fast");
						var today = new Date();
						today.setHours(0);
						today.setMinutes(0);
						today.setSeconds(0);
						today.setMilliseconds(0);
						var hideBtn = today.getTime()>selectDate.getTime();
						if(hideBtn){
							$('#book_add_btn').hide();
							$('#meeting_add_btn').hide();
							$('#video_add_btn').hide();
						}else{
							$('#book_add_btn').show();
							$('#meeting_add_btn').show();
							$('#video_add_btn').show();
						}
					});
					
					//查看
					topic.subscribe('calendar.thing.click',function(arg){
						$('.meeting_calendar_dialog').hide();
						var viewDialog;//弹出框
						viewDialog=$("#meeting_calendar_mainview");//会议安排弹出框
						viewDialog.find(".fdHost").html(arg.schedule.fdHost);//主持人
						//时间格式2014-7-11~2014-7-12
						var date=dateUtil.formatDate(arg.schedule.start,"${dateTimeFormatter}");
						if(arg.schedule.start!=arg.schedule.end){
							date+=" ~ "+dateUtil.formatDate(arg.schedule.end,"${dateTimeFormatter}");
						}
						var creator=arg.schedule.creator;
						if(arg.schedule.dept){
							creator+="("+arg.schedule.dept+")";//（部门）
						}
						viewDialog.find(".docCreator").text(creator);//人员（部门）
						viewDialog.find(".fdId").html(arg.schedule.fdId);//fdId
						viewDialog.find(".fdName").text(arg.schedule.title);//会议题目
						viewDialog.find(".fdPlace").text(arg.schedule.fdPlaceName);//地点
						viewDialog.find(".fdPlaceDetail").html( env.fn.formatText(arg.schedule.fdPlaceDetail) );//设备信息
						viewDialog.find(".fdPlaceDetail").attr("title",arg.schedule.fdPlaceDetail);
						viewDialog.find(".fdHoldDate").html(date);//召开时间
						viewDialog.find(".type").html(arg.schedule.type);
						var remarkTitle = env.fn.formatText(arg.schedule.fdRemark);
						remarkTitle = remarkTitle.replaceAll("<br>","");
						viewDialog.find(".fdRemark").attr("title", remarkTitle).html(env.fn.formatText(arg.schedule.fdRemark));//备注
						var cycleNode = viewDialog.find(".isCycle");
						var repeatInfoNode = viewDialog.find(".repeatInfo");
						var fdRepeatInfoNode = viewDialog.find(".fdRepeatInfo");
						if(arg.schedule.isCycle == 'true'){
							cycleNode.show();
							cycleNode.html('<bean:message bundle="km-imeeting" key="kmImeeting.tree.cyclicity.meeting" />');
							repeatInfoNode.show();
							fdRepeatInfoNode.html(arg.schedule.fdRepeatInfo);
						}else{
							cycleNode.hide();
							repeatInfoNode.hide();
							fdRepeatInfoNode.html("");
						}
						viewDialog.css(getPos(arg.evt,viewDialog)).fadeIn("fast");
					});
			});
			if(window.domain){
				window.domain.autoResize();
			}
		</script>
		<%@ include file="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_view.jsp"%>
		<%@ include file="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_edit.jsp"%>
	</template:replace>
</template:include>