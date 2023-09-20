<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	
%>
	<%-- 右侧内容区域 --%>
	<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/km_imeeting_calendar/calendar.css" />
		<script>
			seajs.use(['theme!list']);	
			seajs.use(['km/imeeting/resource/js/dateUtil'], function(dateUtil) {
				window.dateUtil=dateUtil;
			});
		</script>
		<ui:calendar id="mycalendar" showStatus="view" mode="default" layout="km.imeeting.calendar.my">
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
						}
						return {"top":y,"left":x};
					};

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
						viewDialog.find(".fdHoldDate").html(date);//召开时间
						viewDialog.find(".type").html(arg.schedule.type);
						viewDialog.css(getPos(arg.evt,viewDialog)).fadeIn("fast");
					});
			});
		</script>
		<%@ include file="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_view.jsp"%>
