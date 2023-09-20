<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="java.util.Date"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.km.imeeting.util.ImeetingCateUtil"%>
<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	request.setAttribute("userId", UserUtil.getUser().getFdId());
	request.setAttribute("cateIds",ImeetingCateUtil.getDefCates());
%>
<template:include ref="default.simple">
	
	<%-- 右侧内容区域 --%>
	<template:replace name="body">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/km_imeeting_calendar/calendar.css" />
		<script>
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
							LUI('____calendar____imeeting').refreshSchedules();
						}, 100);
					});  
					
					//LUI('____calendar____imeeting').config.vars.selectedCategories="1111";
					
					LUI.ready(function(){
						if(LUI('____calendar____imeeting')){
							LUI('____calendar____imeeting').on("viewOrModeChange", function(info){
								var mode = info.mode;
								$.ajax({
									url: "${LUI_ContextPath}/km/imeeting/km_imeeting_calendar/kmImeetingCalendar.do?method=getDefCate",
									type: 'POST',
									dataType: 'json',
									success: function(data) {//操作成功
										if(data && data.defCateId){
											if(LUI('____calendar____imeeting').modeSetting[mode] && LUI('____calendar____imeeting').modeSetting[mode].cache){
												//LUI('____calendar____imeeting').modeSetting[mode].cache.selectedCategories=data.defCateId;
												LUI('____calendar____imeeting').modeSetting[mode].cache.render();
											}
										}
									}
								});
							});
						}
					});
					
					//浏览器窗口变化时，重新渲染日历,避免日历点击事件窜位
					/*
					window.onresize = function(){
						setTimeout(function(){
							LUI('____calendar____imeeting').refreshSchedules();
						}, 100);
					};
					*/
					
					//新建会议
			 		window.addDoc = function() {
							dialog.categoryForNewFile(
									'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
									'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{id}',false,null,null,'${JsParam.categoryId}');
				 	};

					//当前会议状态
					var checkStatus=function(item){
						var startDate=Com_GetDate(item.start),endDate=Com_GetDate(item.end);
						var now=new Date();
						if((item.type == 'meeting' && item.docStatus < 30) || (item.type == 'book' && item.fdHasExam == 'wait')){
							return -2;
						}else{
							//未召开
							if(now.getTime()<startDate.getTime()){
								return -1;
							}
							//进行中
							if(now.getTime()>=startDate.getTime() && now.getTime()<=endDate.getTime()){
								return 0;
							}
							//已召开
							if(now.getTime()>endDate.getTime()){
								return 1;
							}
						}
					};
					
					//数据初始化
					window.transformData=function(datas){
						var main=datas.main;
						for(var key in main){

							var t = main[key];
							
							t._renderResRow = function(row, data) {
								
								$(row).css('cursor', 'pointer');
								
								$(row).click(function(){
									window.open('${LUI_ContextPath}/km/imeeting/km_imeeting_res/kmImeetingRes.do?method=view&fdId=' + data.fdId);
								});
								
								if(data.fdNeedExam) {
									var spanText = '<bean:message key="kmImeetingCalendar.res.exam" bundle="km-imeeting"/>';
									$('<span class="tag">'+spanText+'</span>').appendTo($(row).find('div'));
								}
							}
							
							for(var i=0; i < t.list.length; i++){
								var item = t.list[i];
								if(checkStatus(item)==-2){
									item.color = '#F9905A';
								}
								if(checkStatus(item)==-1){
									item.color = '#51b749';
								}
								if(checkStatus(item)==0){
									item.color = '#5484ed';
								}
								if(checkStatus(item)==1){
									item.color = '#fbd75b';
								}
							}
						}
						datas.categoryName = '<bean:message key="kmImeetingRes.fdPlace" bundle="km-imeeting" />';
						return datas;
					};
					
					//定位
					var getPos=function(evt,obj){
						var sWidth=obj.width();var sHeight=obj.height();
						x=evt.pageX;
						y=evt.pageY;
						if((y+sHeight)>$(window).height()){
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
					
					//新建
					topic.subscribe('calendar.select',function(arg){
						$('.meeting_calendar_dialog').hide();
						var addDialog=$('#meeting_calendar_add');
						//时间格式2014-7-11~2014-7-12
						var date=dateUtil.formatDate(dateUtil.parseDate(arg.start),'${dateFormatter}');
						var endTime;
						if(arg.time) {
							date=dateUtil.formatDate(dateUtil.parseDate(arg.start + " " + arg.time),'${dateTimeFormatter}');
							var temp = arg.time.split(":");
							endTime = (parseInt(temp[0]) + 1) + ":" + temp[1];
						}
						if(arg.start!=arg.end){
							date+="~"+arg.end;
							if(arg.time) {
								date += " " + endTime;
							}
						}
						var start = dateUtil.formatDate( dateUtil.parseDate(arg.start,'${dateFormatter}'),'${dateFormatter}'),
							end = dateUtil.formatDate( dateUtil.parseDate(arg.end,'${dateFormatter}'),'${dateFormatter}');
						if(arg.time) {
							start = dateUtil.formatDate( dateUtil.parseDate(arg.start + " " + arg.time,'${dateTimeFormatter}'),'${dateTimeFormatter}'),
							end = dateUtil.formatDate( dateUtil.parseDate(arg.end + " " + endTime,'${dateTimeFormatter}'),'${dateTimeFormatter}');
						}
						addDialog.find('.date').html(date);//时间字符串
						addDialog.find('.fdHoldDate').html(start);//召开时间
						addDialog.find('.fdFinishDate').html(end);//结束时间
						addDialog.find('.resId').html(arg.resId);//地点ID
						addDialog.find('.resName').html(arg.resName);//地点
						addDialog.css(getPos(arg.evt,addDialog)).fadeIn("fast");
						var nowdate = dateUtil.formatDate(new Date(),'${dateFormatter}');
						var startDate = dateUtil.formatDate(dateUtil.parseDate(arg.start),'${dateFormatter}');
						if(arg.time) {
							nowdate = dateUtil.formatDate(new Date(),'${dateTimeFormatter}');
							startDate = dateUtil.formatDate(dateUtil.parseDate(arg.start + " " + arg.time),'${dateTimeFormatter}');
						}
						var nowTimesTamp = new Date(nowdate).getTime();
						var startTimesTamp = new Date(startDate).getTime();
						if(nowTimesTamp > startTimesTamp){
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
						if(arg.data.type =="book"){
							viewDialog=$("#meeting_calendar_bookview");//会议室预约弹出框
							var remarkTitle = env.fn.formatText(arg.data.fdRemark);
							remarkTitle = remarkTitle.replaceAll("<br>","");
							viewDialog.find(".fdRemark").attr("title", remarkTitle).html(env.fn.formatText( textEllipsis(arg.data.fdRemark) ));//备注
							var fdHasExam = arg.data.fdHasExam;
							if(typeof(fdHasExam) != 'undefined'){
								var fdHasExamDiv = viewDialog.find(".fdHasExam");
								if(fdHasExam == 'wait')
									fdHasExamDiv.addClass('wait').removeClass('refuse').html('<bean:message key="kmImeetingCalendar.res.wait" bundle="km-imeeting"/>');
								else if(fdHasExam == 'false')
									fdHasExamDiv.addClass('refuse').removeClass('wait').html('<bean:message key="kmImeetingCalendar.res.false" bundle="km-imeeting"/>');
								else
									fdHasExamDiv.removeClass('refuse').removeClass('wait').html('');
							}
						}else{
							viewDialog=$("#meeting_calendar_mainview");//会议安排弹出框
							viewDialog.find(".fdHost").html(arg.data.fdHost);//主持人
							viewDialog.find(".fdRemark").attr("title", arg.data.fdRemark).html(env.fn.formatText( textEllipsis(arg.data.fdRemark) ));//备注
							var fdHasExamDiv = viewDialog.find(".fdHasExam");
							var docStatus = arg.data.docStatus;
							if(docStatus=='20')
								fdHasExamDiv.addClass('wait').html('<bean:message key="kmImeetingCalendar.res.wait" bundle="km-imeeting"/>');
							else
								fdHasExamDiv.removeClass('wait').html('');
						}
						//时间格式2014-7-11~2014-7-12
						var date=dateUtil.formatDate(arg.data.start,"${dateTimeFormatter}");
						if(arg.data.start!=arg.data.end){
							date+=" ~ "+dateUtil.formatDate(arg.data.end,"${dateTimeFormatter}");
						}
						viewDialog.find(".fdId").html(arg.data.fdId);//fdId
						viewDialog.find(".fdName").html( env.fn.formatText(arg.data.title) );//会议题目
						viewDialog.find(".fdPlace").html( env.fn.formatText(arg.data.fdPlaceName) );//地点
						viewDialog.find(".fdPlaceDetail").html( env.fn.formatText(arg.data.fdPlaceDetail) );//设备信息
						viewDialog.find(".fdPlaceDetail").attr("title",arg.data.fdPlaceDetail);
						viewDialog.find('._fdHoldDate').html(dateUtil.formatDate(arg.data.start,"${dateTimeFormatter}"));//召开时间
						viewDialog.find('._fdFinishDate').html(dateUtil.formatDate(arg.data.end,"${dateTimeFormatter}"));//结束时间
						viewDialog.find(".fdHoldDate").html(date);//会议时间
						viewDialog.find('.fdRecurrenceStr').html(arg.data.recurrenceStr || '');
						var cycleNode = viewDialog.find(".isCycle");
						var repeatInfoNode = viewDialog.find(".repeatInfo");
						var fdRepeatInfoNode = viewDialog.find(".fdRepeatInfo");
						if(arg.data.isCycle == 'true'){
							cycleNode.show();
							cycleNode.html('<bean:message bundle="km-imeeting" key="tips.plan.recurring" />');
							repeatInfoNode.show();
							viewDialog.find(".fdRepeatLi").show();
							fdRepeatInfoNode.html(arg.data.fdRepeatInfo);
						}else{
							cycleNode.hide();
							repeatInfoNode.hide();
							viewDialog.find(".fdRepeatLi").hide();
							fdRepeatInfoNode.html("");
						}
						var creator=arg.data.creator;
						if(arg.data.dept){
							creator+="("+arg.data.dept+")";//（部门）
						}
						viewDialog.find(".docCreator").html(creator);//人员（部门）
						
						if(arg.data.type=="book"){//会议预约按钮权限检测
							$('#book_delete_btn,#book_edit_btn').hide();
							$.ajax({
								url: "${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=checkAuth",
								type: 'POST',
								dataType: 'json',
								data: {fdId: arg.data.fdId},
								success: function(data, textStatus, xhr) {//操作成功
									if(data.canEdit){
										$('#book_edit_btn').show();
									}
									if(data.canDelete){
										$('#book_delete_btn').show();
									}
								}
							});
						}else{//会议安排查看按钮权限检测
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
						}
						var today = new Date();
						if($('#book_change_btn').length > 0 ){
							if(today.getTime() > arg.data.start.getTime() || '${userId}' != arg.data.creatorId || arg.data.recurrenceStr){
								$('#book_change_btn').hide();
							}else if(arg.data.fdHasExam == 'wait' || arg.data.fdHasExam == 'false'){
								$('#book_change_btn').hide();
							}else{
								$('#book_change_btn').show();
							}
						}
						//提前结束预约按钮
						if($('#book_early_end_btn').length > 0){
							if(today.getTime() < arg.data.start.getTime() || today.getTime() > arg.data.end.getTime() || '${userId}' != arg.data.creatorId || arg.data.recurrenceStr){
								$('#book_early_end_btn').hide();
							}else if(arg.data.fdHasExam == 'wait' || arg.data.fdHasExam == 'false'){
								$('#book_early_end_btn').hide();
							}else{
								$('#book_early_end_btn').show();
							}
						}
						viewDialog.find(".type").html(arg.data.type);
						 //是否显示重复日程标示
						/* var iconNode = viewDialog.find(".meeting_calendar_dialog_recurrenceIcon");
						if(arg.data.recurrenceStr){
							iconNode.show();
						}else{
							iconNode.hide();
						} */
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
		<ui:calendar id="____calendar____imeeting" showStatus="drag" mode="week" type="lui/rescalendar!ResCalendar"  layout="km.imeeting.calendar.default">
			
			<ui:var name="selectedCategories" value="${cateIds}"></ui:var>
			
			<%--数据--%>
			<ui:dataformat id="_calendar_imeeting_data">
				<ui:source type="AjaxJson">
					{url:'/km/imeeting/km_imeeting_calendar/kmImeetingCalendar.do?method=rescalendar'}
				</ui:source>
				<ui:transform type="ScriptTransform">
					return transformData(data);
				</ui:transform>
			</ui:dataformat>
		</ui:calendar>
		<c:import url="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_edit.jsp?showVideoEnable=false" charEncoding="UTF-8"></c:import>
		<%@ include file="/km/imeeting/km_imeeting_calendar/kmImeetingCalendar_view.jsp"%>
	</template:replace>
</template:include>