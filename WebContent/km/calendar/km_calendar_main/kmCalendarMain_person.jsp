<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.common.actions.RequestContext"%>
<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	request.setAttribute("userPersonId",UserUtil.getUser().getFdId());
	//UA=mobile跳转到移动端的主页(临时解决方案)
	if(MobileUtil.getClientType(new RequestContext(request)) > -1){
		response.sendRedirect("mobile/index.jsp");
	}
%>
<c:set var="form"  value="kmCalendarMainForm"></c:set>
<template:include ref="person.home">
	<%--页签标题--%>
	<template:replace name="title">${ lfn:message('km-calendar:module.km.calendar') }</template:replace>
	
	<%--右侧--%>
	<template:replace name="content">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/resource/css/calendar_main.css" />
		<script>
			seajs.use(['km/calendar/resource/js/dateUtil'], function(dateUtil) {
				window.kmcalendarDateUtil=dateUtil;
			});
		</script>
		<script type="text/javascript">	
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar','lui/util/env'], function($,dialog , topic ,toolbar,env) {
				//点击标签
				window.clickLabel=function(_this,id){
					var tabpanel = LUI('kmCalendarPanel');
					tabpanel.setSelectedIndex(0);
					tabpanel.props(0,{
						title : "${lfn:message('km-calendar:module.km.calendar.tree.my.calendar')}",
						visible : true
					});
					tabpanel.props(1,{
						visible : false
					});
					var hasClass=$(_this).children(":first").hasClass("label_div_on");
					//点击前处于选中状态
					resetMenuNavStyle();
					if(hasClass){
						$(_this).children(":first").addClass("label_div_off").removeClass("label_div_on");
						exceptLabelIds+=id+",";
					}else{
						$(_this).children(":first").addClass("label_div_on").removeClass("label_div_off");
						exceptLabelIds=exceptLabelIds.replace(id,"");
					}
					var url=LUI("calendar").source.source.url;
					LUI("calendar").source.source.setUrl(Com_SetUrlParameter(url,"exceptLabelIds",exceptLabelIds));//修改请求地址
					LUI('calendar').refreshSchedules();//重刷日历
					var searchContainer = $('#calendar_search_container'),
						calendarContainer = $('#calendar');
					if(searchContainer && searchContainer.length > 0){
						searchContainer.hide();
						calendarContainer.show();
					}
				};
				//点击群组
				window.clickGroup=function(id,name){
					var url="${LUI_ContextPath}/km/calendar/group.jsp?groupId="+id;
					var tabpanel = LUI('kmCalendarPanel');
					tabpanel.setSelectedIndex(1);
					tabpanel.props(0,{
						visible : false
					});
					tabpanel.props(1,{
						title : "${lfn:message('km-calendar:kmCalendar.nav.share.group')}",
						visible : true
					});
					//处于群组页面
					//var url=LUI("calendar").source.url;
					//LUI("calendar").source.setUrl(Com_SetUrlParameter(url,"groupId",id));//修改请求地址
					//LUI('calendar').refreshSchedules();//重刷日历
					var _iframe = LUI('kmCalendarGroupIframe');
					_iframe.reload(url);
				};
				
				// 监听新建更新等成功后刷新
				topic.subscribe('successReloadPage', function() {
					topic.publish('list.refresh');
				});

				//获取位置
				var getPos=function(evt,showObj){
					var sWidth=showObj.width(),
						sHeight=showObj.height();
					var leftMargin = $(".lui_list_left_sidebar_innerframe").width(), //200是左边导航栏的宽度
						topMargin = $(".lui_portal_header").height();  //50是上边导航栏的高度
					x=evt.pageX-leftMargin; 
					y=evt.pageY-topMargin;
					if(y + sHeight + topMargin> $(window).height() + 75 ){
						y-=sHeight;
					}
					if(x + sWidth + leftMargin> $(document.body).outerWidth(true)){
						x-=sWidth;
					}
					return {"top":y,"left":x};
				};

				//选择日程事件
				topic.subscribe('calendar.select',function(arg){
					LUI.$.ajax({
						url: '<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=searchOwners"/>',
						type: 'GET',
						dataType: 'json',
						async: false,
						success: function(data, textStatus, xhr) {//操作成功
							$("#multiOwner").hide();
							$("#ownerTip").hide();
							$('#docOwnerId > option').remove('option:not(.fdGroup)');
							for(var i=data.owners.length-1;i>=0;i--){
								var option=$('<option></option>');
								option.attr('value',data.owners[i][0]);
								option.html(data.owners[i][1]);
								if(data.owners[i][0]=="${userPersonId}"){
									//兼容IE浏览器
									setTimeout(function(){
										option.attr('selected','selected');//选中指定项
									},0);	
								}
								option.prependTo('#docOwnerId');
							}								
						}
					});
					$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
					//初始化时间
					var start=arg.start;
					var end=arg.end;
					if(end==null){
						end=start;
					}
					$("#calendar_add :input[name='docStartTime']").val(kmcalendarDateUtil.formatDate(start,"${dateFormatter}"));
				    $("#calendar_add :input[name='docFinishTime']").val(kmcalendarDateUtil.formatDate(end,"${dateFormatter}"));
				    //是否全天
				    $("#fdIsAlldayevent").prop("checked",arg.allDay);
				    if(arg.allDay){
				    	$("#startTimeDiv,#endTimeDiv").css("display","none");
				    	$("[name='startHour'],[name='startMinute'],[name='endHour'],[name='endMinute']").val(0);
					}else{
						var dateTime=new Date(start);
						$("#startTimeDiv,#endTimeDiv").css("display","inline");
						$("[name='startHour']").val(start.getHours());
				 	    $("[name='startMinute']").val(start.getMinutes());
			 	        dateTime.setHours(dateTime.getHours()+2);
			 	       	$("#calendar_add :input[name='docFinishTime']").val(kmcalendarDateUtil.formatDate(dateTime,"${dateFormatter}"));			 	        
			 	    	$("[name='endHour']").val(dateTime.getHours());
				 	    $("[name='endMinute']").val(dateTime.getMinutes());
					}
					//初始化标签
					if(LUI("label_edit")){
						LUI("label_edit").source.url = '/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson';
						LUI("label_edit").source.get();
					}
					//显示
	                $("#calendar_add").css(getPos(arg.evt,$("#calendar_add"))).fadeIn("fast");
	                $("#simple_fdType_event").val("event");
	                $("#simple_fdType_note").val("note");
					
					// 默认显示event编辑页面
	                $('#tab_event').addClass("current");
					$('#tab_note').removeClass("current");
					$('#simple_calendarTab').show();
					$('#simple_noteTab').hide();
					clearReminder('#simple_event');
				});
				
				//拖拽日程
				topic.subscribe('calendar.thing.change',function(arg){
					$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
					var fdId=arg.schedule.id;	
					if(arg.end==null){
						arg.end=kmcalendarDateUtil.cloneDate(arg.start);
						arg.end.setHours(arg.end.getHours()+1);
					}
					window.loading = dialog.loading();
					var param={
							"fdId":fdId,
							"dayDelta":arg.dayDelta,
							"minuteDelta":arg.minuteDelta,
							"isAllDayEvent":arg.schedule.allDay,
							"resize":arg.resize
					};
					$.post('<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=updateCalendarTime"/>',
						$.param(param,true),function(data){
							if(window.loading!=null) window.loading.hide();
							//操作成功
							if(data!=null && data.status==true){
								if(data.isRecurrence==true){//重复日程拖拽更新后直接刷新整个页面
									LUI("calendar").refreshSchedules();
								}else{//非重复不刷新
									arg.schedule.start=arg.start;
									arg.schedule.end=arg.end;
									LUI("calendar").updateSchedule(arg.schedule);
								}
							}
							//操作失败
							else{
								dialog.failure('<bean:message key="return.optFailure" />');
								arg.moveFun();
							}
					},'json');
				});

				window.labelId_select = "";
				window.setLabelSelected=function(){
					$("#calendarViewForm :input[name='labelId']").val(labelId_select);
				};
				
				//显示日程
				topic.subscribe('calendar.thing.click',function(arg){
					//题头:我的日程
					$('#header_title').html('${lfn:message("km-calendar:kmCalendarMain.calendar.header.title")}');
					$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
					$("#calendar_view_btn").show();//我的日程,默认一定显示操作栏
					if("note"!=arg.schedule.type){
						//初始化标签
						if(LUI("label_view")){
							var url = '/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson';
							LUI("label_view").source.url = url + "&userId=" + arg.schedule.ownerId;
							LUI("label_view").source.get();
						 	if(arg.schedule.labelId){//标签
			                	labelId_select = arg.schedule.labelId;
				                //setTimeout("setLabelSelected()",100);
				            }else{
				            	labelId_select="";
			                }
						}
						//初始化内容
						if(arg.schedule.content){
		                	$("#calendar_content").html(arg.schedule.content);
		                	$("#tr_content").show();
		                }else{
		                	$("#tr_content").hide();
		                }
		                //初始化链接
		                if(arg.schedule.relationUrl){
			                var relationUrl = arg.schedule.relationUrl;
		                	if(relationUrl.length>4 && relationUrl.substring(0,4)!="http"){
		                		relationUrl = '${LUI_ContextPath}' + relationUrl;
		                	}
		                	$("#calendar_relatation_url").attr("href",relationUrl);
		                	$("#tr_relation_url").show();
		                }else{
		                	$("#tr_relation_url").hide();
		                }
		              //初始化提醒
		                if(arg.schedule.hasSettedRemind=="true"){
			                $("#calendar_remind_icon").show();
		                	$("#calendar_remind_icon").removeClass("unremind");
		                }else{
		                	$("#calendar_remind_icon").hide();
		                }
						//显示时间
						var formatDate=Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk'||Com_Parameter['Lang']=='ja-jp')?"yyyy年MM月dd日":"MM/dd/yyyy";
						if(!arg.schedule.allDay){
							formatDate+=" HH:mm";
						}
		                var DateString=kmcalendarDateUtil.formatDate(arg.schedule.start,formatDate);
						if(arg.schedule.end!=null){
							DateString+="-"+kmcalendarDateUtil.formatDate(arg.schedule.end,formatDate);
						}
						$("#calendar_date").html(DateString);//初始化日期
						
						var __title =  env.fn.formatText(arg.schedule.title);
						
						$("#calendar_title").html( __title );
						$("#calendarViewForm :input[name='fdId']").val(arg.schedule.id);
						$("#calendar_view").css(getPos(arg.evt,$("#calendar_view"))).fadeIn("fast");
					}else{
						if(arg.schedule.content){//内容
							var content=arg.schedule.content.replace(/<p>/g,"").replace(/<\/p>/g,"<br>");
							content = content.replace(/<img.*>/g,"").replace(/<\/img>/,"");
							// 我的笔记富文本框html标签过滤 #170157
							// content = env.fn.formatText(content);
		                	$("#note_docContent").html(content);
		                }else{
		                	$("#note_docContent").html("");
		                }
						$("#note_subject").text(arg.schedule.title);
						$("#noteViewForm :input[name='fdId']").val(arg.schedule.id);
						$("#note_view").css(getPos(arg.evt,$("#note_view"))).fadeIn("fast");
					}
					clearReminder('#simple_event');
				});
				
				//初始化默认标签的颜色,针对单个日程
				window.setColor=function(schedule){
					var eventColor=$(".lui_calendar_color_event").eq(0).css("background-color");//默认日程颜色
					var noteColor=$(".lui_calendar_color_note").eq(0).css("background-color");//笔记颜色
					if(schedule.type=="note"){
						schedule.color=noteColor;
					}else if(schedule.color==null||schedule.color==""){
						schedule.color=eventColor;
					}
				};
				
				//初始化默认标签的颜色,针对集合
				window.setColors=function(data){
					for(var i=0;i<data.length;i++){
						setColor(data[i]);
					}
					return data;
				};

				//个人日程导出
				window.kmCalendarExport=function(){
					var url="/km/calendar/km_calendar_main/kmCalendarMain_setTime.jsp?type=myCalendar";
					dialog.iframe(url,"${lfn:message('km-calendar:kmCalendarMain.exportSelfTitle')}",function(){
						
					},{width:550,height:350});
				};
				
				//清除校验信息并将方框颜色置为灰色
				window.clearReminder=function(element){
					if(element=='#simple_event'){
						$("#docSubject").css("border-color","#ccc");
						$("#docStartTime").parents(".inputselectsgl").css("border-color","#ccc");
						$("#docFinishTime").parents(".inputselectsgl").css("border-color","#ccc");
					}else if(element=='#simple_note'){
						$("#docSubject_note").css("border-color","#ccc");
						$("#docContent_note").css("border-color","#ccc");
					}
					$(element).find("[validate]:input").each(function(){
						KMSSValidation_HideWarnHint(this);
					});
				};
				
				//管理标签
				window.kmCalendarList=function(){
					$("#calendar_add").fadeOut();
					dialog.iframe('/km/calendar/km_calendar_label/kmCalendarUserLabel.do?method=edit','${lfn:message("km-calendar:kmCalendarLabel.tab.list")}',function(value){
						if(value=="true"){
							if(LUI("label_nav")){
								LUI("label_nav").source.get();//操作成功,刷新标签导航栏
							}
							LUI('calendar').refreshSchedules();//重刷日历
						}else if(value=="false"){
							dialog.failure('<bean:message key="return.optFailure" />');
						}
				 	},{height:'400',width:'650'});
				};
				
				 //个人共享设置
				window.kmCalendarAuth=function(){
					dialog.iframe('/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=edit','${lfn:message("km-calendar:kmCalendar.setting.authSetting")}',null,{height:'500',width:'750'});
				};

				//关注群组
				window.kmCalendarShareGroup=function(){
					dialog.iframe('/km/calendar/km_calendar_share_group/kmCalendarUserShareGroup.do?method=edit','${lfn:message("km-calendar:kmCalendar.setting.groupSetting")}',function(value){
						if(value=="true"){
							if(LUI("share_group")){
								LUI("share_group").source.get();//刷新群组导航栏
							}
							//LUI('calendar').refreshSchedules();//重刷日历
						}else if(value=="false"){
							dialog.failure('<bean:message key="return.optFailure" />');
						}
					 },{height:'400',width:'750'});
				};
				
			});
		</script>
		<ui:tabpanel id="kmCalendarPanel" layout="sys.ui.tabpanel.list">
		<ui:content title="${lfn:message('km-calendar:module.km.calendar.tree.my.calendar')}">
		<ui:calendar id="calendar" showStatus="drag" mode="default" layout="km.calendar.default">
			<ui:dataformat>
				<ui:source type="AjaxJson">
					{url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=data&exceptLabelIds=${JsParam.exceptLabelIds}'}
				</ui:source>
				<ui:transform type="ScriptTransform">
					return setColors(data);
				</ui:transform>
			</ui:dataformat>
			<ui:render type="Template">
				{$<p title="{%env.fn.formatText(data['title'].replace(/(\n)+|(\r\n)+/g, ' ')%}">$}
				var str="";
				var start=kmcalendarDateUtil.parseDate(data['start']);
				if(data['allDay']=='1'){
					str+="${lfn:message('km-calendar:kmCalendarMain.allDay')} ";
				}else{
					var hours=start.getHours()<10?"0"+start.getHours():start.getHours();
					var minutes=start.getMinutes()<10?"0"+start.getMinutes():start.getMinutes();
					str+=hours+":"+minutes+" "
				}
				if(data['title']){
					str+=env.fn.formatText(data['title'].replace(/(\n)+|(\r\n)+/g, " "));
				}
				{$<span class="textEllipsis">{%str%}</span></p>$}
			</ui:render>
		</ui:calendar>
		 <%-- 日程查询 --%>
		 <%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_search.jsp"%>
		 <%--查看日程DIV--%>
		<%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_view.jsp"%>
		 <%--新建日程DIV--%>
		 <%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_edit.jsp"%>
		 </ui:content>
		 </ui:tabpanel>
	</template:replace>
</template:include>
