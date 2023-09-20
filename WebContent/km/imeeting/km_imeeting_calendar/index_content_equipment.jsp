<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	request.setAttribute("now", new Date().getTime());
%>
<template:include ref="default.simple" spa="true">
	<%-- 右侧内容区域 --%>
	<template:replace name="body"> 
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/km_imeeting_calendar/calendar.css" />
		<script>
			var SYS_SEARCH_MODEL_NAME ="com.landray.kmss.km.imeeting.model.KmImeetingEquipment";
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
							LUI('____calendar____equipment').refreshSchedules();
						}, 100);
					});  
					
					//浏览器窗口变化时，重新渲染日历,避免日历点击事件窜位
					window.onresize = function(){
						setTimeout(function(){
							if($('#res_calenadr_loadmore').css("display")=='none'){
							LUI('____calendar____equipment').refreshSchedules();
							}
						}, 100);
					};

					//新建会议
			 		window.addDoc = function() {
							dialog.categoryForNewFile(
									'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
									'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{id}',false,null,null,'${JsParam.categoryId}');
				 	};

					//数据初始化
					window.transformData=function(datas){
						var main=datas.main;
						for(var key in main){
							for(var i=0;i<main[key].list.length;i++){
								var item=main[key].list[i];
								if(checkStatus(item)==-1){
									item.color=$('.meeting_calendar_label_unhold').css('background-color');
								}
								if(checkStatus(item)==0){
									item.color=$('.meeting_calendar_label_holding').css('background-color');
								}
							}
						}
						datas.categoryName = '<bean:message key="kmImeetingCalendar.equipment" bundle="km-imeeting" />';
						return datas;
					};
					
					//当前设备使用状态
					var checkStatus=function(item){
						var startDate=dateUtil.parseDate(item.start),endDate=dateUtil.parseDate(item.end);
						var now=new Date(parseFloat('${now}'));
						//未召开
						if(now.getTime()<startDate.getTime()){
							return -1;
						}
						//进行中
						if(now.getTime()>=startDate.getTime() && now.getTime()<=endDate.getTime()){
							return 0;
						}
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
						//避免会议室列表过少导致遮挡情况
						if(y < 0) {
							y = 0;
						}
						return {"top":y,"left":x};
					};
					
					//查看
					topic.subscribe('calendar.thing.click',function(arg){
						$('.meeting_calendar_dialog').hide();
						var viewDialog;//弹出框
						viewDialog=$("#meeting_calendar_mainview");//会议安排弹出框
						viewDialog.find(".fdHost").html(arg.data.fdHost);//主持人
						//时间格式2014-7-11~2014-7-12
						var date=dateUtil.formatDate(arg.data.start,"${dateTimeFormatter}");
						if(arg.data.start!=arg.data.end){
							date+=" ~ "+dateUtil.formatDate(arg.data.end,"${dateTimeFormatter}");
						}
						viewDialog.find(".fdId").html(arg.data.fdId);//fdId
						viewDialog.find(".fdName").html( env.fn.formatText(arg.data.title) );//会议题目
						viewDialog.find(".fdPlace").html( env.fn.formatText(arg.data.fdPlaceName) );//地点
						viewDialog.find(".fdHoldDate").html(date);//召开时间
						
						viewDialog.find('.fdRecurrenceStr').html(arg.data.recurrenceStr || '');
						var cycleNode = viewDialog.find(".isCycle");
						var repeatInfoNode = viewDialog.find(".repeatInfo");
						var fdRepeatInfoNode = viewDialog.find(".fdRepeatInfo");
						if(arg.data.isCycle == 'true'){
							cycleNode.show();
							cycleNode.html("周期会议");
							repeatInfoNode.show();
							fdRepeatInfoNode.html(arg.data.fdRepeatInfo);
						}else{
							cycleNode.hide();
							repeatInfoNode.hide();
						}

						var creator=arg.data.creator;
						if(arg.data.dept){
							creator+="("+arg.data.dept+")";//（部门）
						}
						viewDialog.find(".docCreator").html(creator);//人员（部门）
						//会议安排查看按钮权限检测
						$('#meeting_view_btn').hide();
						$.ajax({
							url: "${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=checkViewAuth",
							type: 'POST',
							dataType: 'json',
							data: {fdId: arg.data.fdId},
							success: function(data, textStatus, xhr) {//操作成功
								if(data.canView){
									$('#meeting_view_btn').show();
								}
							}
						});
						viewDialog.find(".type").html(arg.data.type);
						viewDialog.css(getPos(arg.evt,viewDialog)).fadeIn("fast");
					});
					
					//字符串截取
					function textEllipsis(text){
						text = text || '';
						if(text && text.length>200){
							text=text.substring(0,200)+"......";
						}
						return text;
					}
			});
		</script>
		<ui:calendar id="____calendar____equipment" showStatus="view" mode="day" type="lui/rescalendar!ResCalendar" layout="km.imeeting.calendar.equipment">
			<%--数据--%>
			<ui:dataformat>
				<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_calendar/kmImeetingCalendar.do?method=equcalendar'}
				</ui:source>
				<ui:transform type="ScriptTransform">
					return transformData(data);
				</ui:transform>
			</ui:dataformat>
		</ui:calendar>
		<%@ include file="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_view.jsp"%>
	</template:replace>
</template:include>