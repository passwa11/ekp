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
<template:include ref="default.list" spa="true">
	<%--日历框架JS、CSS--%>
	<template:replace name="head">
		<%@ include file="../jsp/nav.jsp" %>
		<%@ include file="../jsp/changeheader.jsp" %>
		<%@ include file="../jsp/changelist.jsp" %>
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/resource/css/calendar_main.css" />
		<script>
			seajs.use(['km/calendar/resource/js/dateUtil'], function(dateUtil) {
				window.kmcalendarDateUtil=dateUtil;
			});
		</script>
		<style>
			#kmCalendarGroupIframe iframe{
				min-height: 600px;
			}
			#personGroupIframe iframe{
				min-height: 600px;
			}
			#subordinateIframe iframe{
				min-height: 600px;
			}
			#managerIframe iframe{
				min-height: 600px;
			}
		</style>
	</template:replace>
	
	<%--页签标题--%>
	<template:replace name="title">${ lfn:message('km-calendar:module.km.calendar') }</template:replace>
	
	<template:replace name="nav">
	    <%-- 日历管理-新建日程 --%>
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('km-calendar:module.km.calendar') }"></ui:varParam>
			<ui:varParam name="button">
					[{
						"text": "",
						"href": "javascript:void(0)'",
						"icon": "km_calendar"
					}]
			</ui:varParam>
		</ui:combin>
		<div class="lui_list_nav_frame">
		   <ui:accordionpanel>
		   		<c:import url="/km/calendar/import/nav.jsp" charEncoding="UTF-8">
		   			<c:param name="key" value="calendar"></c:param>
		   		</c:import>
			</ui:accordionpanel>
		</div>
	</template:replace>
	
	<%--右侧--%>
	<template:replace name="content">
		<script type="text/javascript">	
			seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar','lui/util/env'], function($,dialog , topic ,toolbar,env) {
				LUI.ready(function() {
					var tabpanel = LUI('kmCalendarPanel');
				  	tabpanel.props(1,{
					  	visible : false
				  	});
				  	tabpanel.props(2,{
						visible : false
					});
				  	tabpanel.props(3,{
						visible : false
					});
				  	tabpanel.props(4,{
						visible : false
					});
				  	openNotifyView();
				});
				
				window.openNotifyView=function(){
					var fdId='${param.fdId}';
					var r='${param.r}';
					if(fdId && !getCookies(fdId+r)){
						$.post('<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=checkCalendarIsExists"/>',
								$.param({"fdId":fdId},true),function(data){
									console.log(data);
									if(data!=null && data.status==true){
										var d = new Date();
									    d.setTime(d.getTime()+(24*60*60*1000));
									    document.cookie = fdId+r+"=calendarId; "+"expires="+d.toGMTString();
										dialog.iframe('/km/calendar/km_calendar_main/kmCalendarMain.do?method=notifyView&fdId=${param.fdId}',
												'${lfn:message("km-calendar:kmCalendarMain.export.myCalendar")}',null,{height:'355',width:'475'});
									}
							},'json'); 
					}
				}
				var getCookies=function(cname){
				    var name = cname + "=";
				    var ca = document.cookie.split(';');
				    for(var i=0; i<ca.length; i++) {
				        var c = ca[i].trim();
				        if (c.indexOf(name)==0) { return c.substring(name.length,c.length); }
				    }
				    return "";
				}
				
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
					tabpanel.props(2,{
						visible : false
					});
					tabpanel.props(3,{
						visible : false
					});
					tabpanel.props(4,{
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
					var url="${LUI_ContextPath}/km/calendar/index_group.jsp?groupId="+id+"&groupName="+encodeURI(name);
					var tabpanel = LUI('kmCalendarPanel');
					tabpanel.setSelectedIndex(1);
					tabpanel.props(0,{
						visible : false
					});
					tabpanel.props(1,{
						title : "${lfn:message('km-calendar:kmCalendar.nav.share.group')}",
						visible : true
					});
					tabpanel.props(2,{
						visible : false
					});
					tabpanel.props(3,{
						visible : false
					});
					tabpanel.props(4,{
						visible : false
					});
					//处于群组页面
					//var url=LUI("calendar").source.url;
					//LUI("calendar").source.setUrl(Com_SetUrlParameter(url,"groupId",id));//修改请求地址
					//LUI('calendar').refreshSchedules();//重刷日历
					var _iframe = LUI('kmCalendarGroupIframe');
					_iframe.reload(url);
				};
				
				//点击人员群组
				window.clickPersonGroup = function(id,name){
					var url="${LUI_ContextPath}/km/calendar/index_person_group.jsp?personGroupId="+id+"&personGroupName="+encodeURI(name);
					var tabpanel = LUI('kmCalendarPanel');
					tabpanel.setSelectedIndex(2);
					tabpanel.props(0,{
						visible : false
					});
					tabpanel.props(1,{
						visible : false
					});
					tabpanel.props(2,{
						title : "${lfn:message('km-calendar:kmCalendarMain.group.header.title')}",
						visible : true
					});
					tabpanel.props(3,{
						visible : false
					});
					tabpanel.props(4,{
						visible : false
					});
					var _iframe = LUI('personGroupIframe');
					_iframe.reload(url);
				}
				
				// 下属工作
				window.clickSubordinate = function() {
					var url="${LUI_ContextPath}/sys/subordinate/moduleindex.jsp?moduleMessageKey=km-calendar:module.km.calendar";
					var tabpanel = LUI('kmCalendarPanel');
					tabpanel.setSelectedIndex(3);
					tabpanel.props(0,{
						visible : false
					});
					tabpanel.props(1,{
						visible : false
					});
					tabpanel.props(2,{
						visible : false
					});
					tabpanel.props(3,{
						title : "${lfn:message('km-calendar:subordinate.kmCalendarMain') }",
						visible : false
					});
					tabpanel.props(4,{
						visible : false
					});
					var _iframe = LUI('subordinateIframe');
					_iframe.reload(url);
				}
				// 后台配置
				window.clickmanagement = function() {
					var url="${LUI_ContextPath}/sys/profile/moduleindex.jsp?nav=/km/calendar/tree.jsp";
					var tabpanel = LUI('kmCalendarPanel');
					tabpanel.setSelectedIndex(4);
					tabpanel.props(0,{
						visible : false
					});
					tabpanel.props(1,{
						visible : false
					});
					tabpanel.props(2,{
						visible : false
					});
					tabpanel.props(3,{
						visible : false
					});
					tabpanel.props(4,{
						title : "${lfn:message('list.manager') }",
						visible : true
					});
					var _iframe = LUI('managerIframe');
					_iframe.reload(url);
				}
				
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
					if(y + sHeight + topMargin> $(window).height() + 125 ){
						y-=sHeight;
					}
					if(x + sWidth + leftMargin> $(document.body).outerWidth(true)){
						x-=sWidth;
					}
					return {"top":y,"left":x};
				};

				//选择日程事件
				topic.subscribe('calendar.select',function(arg){
					$("#multiOwner").hide();
					$("#ownerTip").hide();
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
				    $("#fdIsAlldayevent").prop("checked",false);
				    var dateTime=new Date(start);
					$("#startTimeDiv,#endTimeDiv").css("display","inline");
					var now = new Date();
					$("[name='startHour']").val(now.getHours()+1);
			 	    $("[name='startMinute']").val(start.getMinutes());
		 	        dateTime.setHours(now.getHours()+2);
		 	       	//$("#calendar_add :input[name='docFinishTime']").val(kmcalendarDateUtil.formatDate(dateTime,"${dateFormatter}"));	#112485		 	        
		 	    	$("[name='endHour']").val(dateTime.getHours());
			 	    $("[name='endMinute']").val(dateTime.getMinutes());
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
								dialog.success('<bean:message key="return.optFailure" />');
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
				            	if(arg.schedule.isGroup!=null&&arg.schedule.isGroup==true)
				            		labelId_select="myGroupEvent";
								else
									labelId_select="myEvent";
			                }
						}
						//初始化内容
						if(arg.schedule.content){
		                	$("#calendar_content").html(arg.schedule.content);
		                	$("#tr_content").show();
		                }else{
		                	$("#tr_content").hide();
		                }
						//初始化地点
						if(arg.schedule.fdLocation){
		                	$("#calendar_location").html(arg.schedule.fdLocation);
		                	$("#tr_location").show();
		                }else{
		                	$("#tr_location").hide();
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
		              //初始化私人日程图标
		                if(arg.schedule.isPrivate){
		                	$("#calendar_remind_lock").show();
		                }else{
		                	$("#calendar_remind_lock").hide();
		                }
		              //初始化提醒
		                if(arg.schedule.hasSettedRemind=="true"){
			                $("#calendar_remind_change").show();
			                $("#calendar_remind_icon").show();
		                	$("#calendar_remind_icon").removeClass("unremind");
		                }else{
		                	$("#calendar_remind_change").hide();
		                	$("#calendar_remind_icon").hide();
		                }
						//显示时间
						var formatDate=Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk'||Com_Parameter['Lang']=='ja-jp')?"yyyy年MM月dd日":Com_Parameter['Date_format'];
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
						$("#calendarViewForm :input[name='fdIsGroup']").val(arg.schedule.isGroup);
						$("#calendar_view").css(getPos(arg.evt,$("#calendar_view"))).fadeIn("fast");
					}else{
						if(arg.schedule.content){//内容
							var content=arg.schedule.content.replace(/<p>/g,"").replace(/<\/p>/g,"<br>");
							content = content.replace(/<img[^>]*>/g,"").replace(/<\/img>/,"");
							//content = env.fn.formatText(content);
		                	$("#note_docContent").html(content);
		                	$("#note_docContent > table").css({"width":"100%"});
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
					var groupEventColor=$(".lui_calendar_color_groupEvent").eq(0).css("background-color");//群组日程颜色
					if(schedule.type=="note"){
						schedule.color=noteColor;
					}else if(schedule.color==null||schedule.color==""){
						if(schedule.isGroup!=null&&schedule.isGroup==true)
							schedule.color=groupEventColor;
						else
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
				
				//个人共享权限
				window.kmCalendarAuth=function(){
					var url="/km/calendar/km_calendar_auth_list/kmCalendarAuthList_dialog.jsp?fdPersonId=${currentUserId }";
					dialog.iframe(url, '<span style="width: 600px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;display: block;">' 
						+ '<bean:message bundle="km-calendar" key="kmCalendar.setting.authSetting"/>' 
						+ '（<bean:message bundle="km-calendar" key="kmCalendar.authSetting.personName"/>' + '${currentUserName }' 
						+ '；<bean:message bundle="km-calendar" key="kmCalendar.authSetting.deptName"/>' + '${currentUserDept }' + '）</span>', 
						function(arg){
						
					},{width:800,height:550});
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
				var title = "";
				if(data['title']){
					title = env.fn.formatText(data['title'].replace(/(\n)+|(\r\n)+/g, " "));
				}
				var pclass="";
				if(data['isPrivate']){
					pclass="calendar_content_title";
				}
				{$<p title="{%title%}" class="{%pclass%}">$}
				var str="";
				var start=kmcalendarDateUtil.parseDate(data['start']);
				if(data['allDay']=='1'){
					str+="${lfn:message('km-calendar:kmCalendarMain.allDay')} ";
				}else{
					var hours=start.getHours()<10?"0"+start.getHours():start.getHours();
					var minutes=start.getMinutes()<10?"0"+start.getMinutes():start.getMinutes();
					str+=hours+":"+minutes+" "
				}
				str+=title;
				{$<span class="textEllipsis">{%str%}</span></p>$}
				if(data['isPrivate']){
					{$<div class="lui_calendar_icon_lock"><div  class="lui_icon_s lui_icon_s_icon_lock"></div></div>$}
				}
			</ui:render>
		</ui:calendar>
		 <%-- 日程查询 --%>
		 <%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_search.jsp"%>
		 <%--查看日程DIV--%>
		<%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_view.jsp"%>
		 <%--新建日程DIV--%>
		 <%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_edit.jsp"%>
		 </ui:content>
		 <ui:content title="${lfn:message('km-calendar:kmCalendar.nav.share.group')}">
	 	 	<ui:iframe id="kmCalendarGroupIframe" cfg-takeHash="true" src="${LUI_ContextPath }/km/calendar/index_group.jsp"></ui:iframe>
	 	 </ui:content>
	 	 <ui:content title="${lfn:message('km-calendar:kmCalendarMain.group.header.title')}">
	 	 	<ui:iframe id="personGroupIframe" cfg-takeHash="true" src="${LUI_ContextPath }/km/calendar/index_person_group.jsp"></ui:iframe>
	 	 </ui:content>
	 	 <ui:content title="${lfn:message('km-calendar:subordinate.kmCalendarMain') }">
	 	 	<ui:iframe id="subordinateIframe" cfg-takeHash="true" src="${LUI_ContextPath }/sys/subordinate/moduleindex.jsp?moduleMessageKey=km-calendar:module.km.calendar"></ui:iframe>
	 	 </ui:content>
	 	 <ui:content title="${lfn:message('list.manager') }">
	 	 	<ui:iframe id="managerIframe" cfg-takeHash="true" src="${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/km/calendar/tree.jsp"></ui:iframe>
	 	 </ui:content>
		 </ui:tabpanel>
		 <div id="group_tips"  style="display: none">
		 	<a class="group_tips_close" title="${lfn:message('button.close')}" href="javascript:void(0);"  onclick="close_tips();"></a>
        	<i class="group_tips_trig"></i>
		 	 <div class="group_tips_title">
            	${lfn:message('km-calendar:kmCalendarShareGroup.tip') }：<span><b>${lfn:message('km-calendar:kmCalendar.nav.share.group')}</b></span>
            </div>
             <div class="group_tips_content">
	            <ul>
	                <li>${lfn:message('km-calendar:kmCalendarShareGroup.tip.one') }</li>
	                <li>${lfn:message('km-calendar:kmCalendarShareGroup.tip.two') }</li>
	                <li>${lfn:message('km-calendar:kmCalendarShareGroup.tip.three') }</li>
	            </ul>
	            <div class="group_tips_img">
	                <a href="javascript:void(0);" onclick="close_tips();kmCalendarShareGroup();">
	                	<c:choose>
	                		<c:when test="${not empty Lang && Lang != 'zh-cn' && Lang !='zh-hk' }">
	                			<img src="${LUI_ContextPath}/km/calendar/resource/images/_tips_img_en.png" alt="分组" />
	                		</c:when>
	                		<c:otherwise>
	                			<img src="${LUI_ContextPath}/km/calendar/resource/images/_tips_img.png" alt="分组" />
	                		</c:otherwise>
	                	</c:choose>
	                </a>
	            </div>
        	</div>
	 	</div>
	</template:replace>
</template:include>
