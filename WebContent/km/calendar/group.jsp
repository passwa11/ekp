<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//UA=mobile跳转到移动端的主页(临时解决方案)
	if(MobileUtil.getClientType(new RequestContext(request)) > -1){
		response.sendRedirect("mobile/group.jsp");
	}
%>
<template:include ref="default.list" spa="true">
	<%--日历框架JS、CSS--%>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/resource/css/calendar_main.css" />
	</template:replace>
	
	<%--页签标题--%>
	<template:replace name="title">${ lfn:message('km-calendar:module.km.calendar') }</template:replace>
	
	<template:replace name="nav">
	    <%-- 日历管理-新建日程 --%>
		<ui:combin ref="menu.nav.create">
			<ui:varParam name="title" value="${ lfn:message('km-calendar:module.km.calendar') }"></ui:varParam>
			<ui:varParam name="button">
				[
					{
						"text": "",
						"href":"javascript:void(0);",
						"icon": "km_calendar"
					}
				]
			</ui:varParam>
		</ui:combin>
		<div class="lui_list_nav_frame">
	      <ui:accordionpanel>
		    	<%--群组日程--%>
				<ui:content title="${lfn:message('km-calendar:kmCalendar.nav.share.group')}" expand="false">
					<%--日程分组提示--%>
				 	<div id="group_tips_parent" style="position: relative;width: 100%">
					<ul class="lui_list_nav_list"  id="lui_list_nav_group">
						<ui:dataview id="share_group">
							<ui:source type="AjaxJson">
								{url:'/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listUserGroupJson'}
							</ui:source>
							<ui:render type="Template">
								<c:import url="/km/calendar/tmpl/share_group.jsp" charEncoding="UTF-8"></c:import>
							</ui:render>
						</ui:dataview>
				 	</ul>
				 	</div>
				</ui:content>
				<%--后台--%>
				<kmss:authShow roles="ROLE_KMCALENDAR_BACKSTAGE_MANAGER">
				<ui:content title="${ lfn:message('list.otherOpt') }" expand="false">
					<ui:combin ref="menu.nav.simple">
						<ui:varParam name="source">
							<ui:source type="Static">
							[
								{
								"text" : "${ lfn:message('list.manager') }",
								<%-- "href" :  "javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/index.jsp#app/ekp/km/calendar','_blank');", --%>
								"href":"javascript:LUI.pageOpen('${LUI_ContextPath }/sys/profile/moduleindex.jsp?nav=/km/calendar/tree.jsp','_rIframe');",
								"icon" : "lui_iconfont_navleft_com_background"
								}
							]
							</ui:source>
						</ui:varParam>
					</ui:combin>
				</ui:content>
				</kmss:authShow>
		</ui:accordionpanel>
		</div>
	</template:replace>
	
	<%--右侧--%>
	<template:replace name="content">
		<script type="text/javascript">	
			seajs.use([
				'km/calendar/resource/js/dateUtil',
				'km/calendar/resource/js/calendar_group',
				'lui/jquery',
				'lui/topic',
				'lui/dialog'],
				 function(dateUtil,calendar,$,topic,dialog) {
				//群组类
				window.groupCalendar=calendar.GroupCalendarMode;
				//点击群组
				window.clickGroup=function(id,name){
					//处于群组页面
					var url=LUI("calendar").source.url;
					url += Com_SetUrlParameter(url,"groupName",encodeURI(name));
					LUI("calendar").source.setUrl(Com_SetUrlParameter(url,"groupId",id));//修改请求地址
					LUI('calendar').refreshSchedules();//重刷日历
				};
				//获取位置
				var getPos=function(evt,showObj){
					var sWidth=showObj.width();
					var sHeight=showObj.height();
					var leftMargin=$(".lui_list_left_sidebar_innerframe").width(),
						topMargin=$(".lui_portal_header").height();
					x=evt.pageX-leftMargin;
					y=evt.pageY-topMargin;
					if(y+sHeight>$(window.parent).height()){
						y-=sHeight;
					}
					if(x+sWidth>$(document.body).outerWidth(true)){
						x-=sWidth;
					}
					return {"top":y,"left":x};
				};
				
				//显示日程
				topic.subscribe('calendar.thing.click',function(arg){
					//题头:群组日程
					$('#header_title').html('${lfn:message("km-calendar:kmCalendarMain.group.header.title")}');
					//是否显示内容
					if(arg.schedule.content){
	                	$("#calendar_content").html(arg.schedule.content);
	                	$("#tr_content").show();
	                }else{
	                	$("#tr_content").hide();
	                }
	                //是否有提醒
	                if(arg.schedule.hasSettedRemind=="true"){
		                $("#calendar_remind_icon").show();
	                }else{
	                	$("#calendar_remind_icon").hide();
	                }
	                //是否显示标签
	                if(arg.schedule.labelId){
		                $("#labelId_view").val(arg.schedule.labelId);
	                }else{
	                	$("#labelId_view").val("");
	                }
	                //共享日程,判断是否有操作权限
	                $("#calendar_view_btn").hide();
	                $.post('<c:url value="/km/calendar/km_calendar_main/kmCalendarMain.do?method=checkEditAuth"/>',
	    	           $.param({"calendarId":arg.schedule.id},true),function(data){
							if(data['canEdit']==true){
								 $("#calendar_view_btn").show();
								
							}
    	                },'json');
					//显示时间
					var formatDate=Com_Parameter['Lang']!=null && (Com_Parameter['Lang']=='zh-cn' || Com_Parameter['Lang']=='zh-hk')?"yyyy年MM月dd日":"MM/dd/yyyy";
					if(!arg.schedule.allDay){
						formatDate+=" HH:mm";
					}
					var DateString=dateUtil.formatDate(arg.schedule.start,formatDate);
					if(arg.schedule.end!=null){
						DateString+="-"+dateUtil.formatDate(arg.schedule.end,formatDate);
					}
					$("#calendar_date").html(DateString);//初始化日期
					$("#calendar_title").text(arg.schedule.title);
					$("#calendarViewForm :input[name='fdId']").val(arg.schedule.id);
					$("#calendar_view").css(getPos(arg.evt,$("#calendar_view"))).fadeIn("fast");
				});

				//群组日程导出
				window.kmCalendarExport=function(){
					var url="/km/calendar/km_calendar_main/kmCalendarMain_setTime.jsp?type=groupCalendar";
					var locationUrl=LUI("calendar").source.url;
					url+="&groupId="+Com_GetUrlParameter(locationUrl,"groupId");//
					dialog.iframe(url,"${lfn:message('km-calendar:kmCalendarMain.exportGroupTitle')}",function(){
						
					},{width:550,height:350});
				};
				
			});
		</script>
		<div style="display: table;">
			<div style="display:table-cell;background:#fff;">
				<ui:calendar id="calendar" showStatus="edit" mode="groupCalendar"  layout="km.calendar.group" customMode="{'id':'groupCalendar','name':'群组日历','func':groupCalendar}">
					<ui:source type="AjaxJson">
						{url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=listGroupCalendar&groupId=${JsParam.groupId}'}
					</ui:source>
					
				</ui:calendar>
			</div>
		
			<div id ="search_box" >
				<div id="group_serarch">
					<input type="search"  placeholder="${lfn:message('km-calendar:km.calendar.group.user.search') }" value="" id="search_key_word"/><input type="button" id="search_btn" value="搜索"/>
				</div>
				<div style="height:1px;background:#ccc;margin-bottom:6px;"></div>
				<h3 id="group_name"><bean:message bundle="km-calendar" key="kmCalendar.setting.groupSetting" />-${lfn:decodeUnicode(JsParam.groupName)}</h3>
				<label><input type="checkbox" checked="true" id="select_all_person"/>全选</label>
				<ul id="person_view_list">
				</ul>	
				<list:paging id="group_page" pageSize="10"  layout="km.calendar.paging.default" viewSize="1"></list:paging>
			</div>	
		</div>
		<script>
			seajs.use(['lui/jquery','lui/topic', 'lui/dialog'],function($,topic,dialog){
				//当前获取的人员数据
				var dataArray = [];
				var selectArray = [];
				var groupPageSize=10;
				function createSearchDom(data){
					var domStr = "";
					var data = JSON.parse(data);
					for(var i = 0;i<data.length;i++){
						selectArray.push(data[i].id);
						if(i<groupPageSize){
							domStr+=createLi(data[i]);
						}
					}
					return domStr;
					
				}
				
				topic.subscribe("person_next_all",function(personIds){
					console.log(personIds)
					$.ajax({
						url:"${LUI_ContextPath}/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listGroupSearch&groupId=${JsParam.groupId}&personsId="+personIds,
						success:function(response){
							var data =JSON.parse(response);
							dataArray = dataArray.concat(data);
							for(var i = 0;i<data.length;i++){
								selectArray.push(data[i].id);
								var liNodes = $('.select_person');
								if(liNodes.length<10){
									var li = createLi(data[i]);
									$("#person_view_list").append($(li));
								}
							} 	
							LUI("group_page").currentPage=1;
							LUI("group_page").totalSize=dataArray.length;
							LUI("group_page").draw();
						}
					 })
				})
				
				topic.subscribe("paging.changed",function(e){
					$("#person_view_list").html("");
					var pageSize=LUI("group_page").pageSize;
					var currentPage=LUI("group_page").currentPage;
					currentPage=currentPage<=0?1:currentPage;
					var pageArray=dataArray.slice(pageSize*(currentPage-1),pageSize*currentPage);
					for(var i = 0;i<pageArray.length;i++){
						var li = createLi(pageArray[i]);
						$("#person_view_list").append($(li));
					}
					LUI("group_page").draw();
				})
				
				function createLi(data){
					var oLi = "<li>";
					var isCanRead =data.canRead?"display:inline-block;":"display:none;";
					var isCanAdd =data.canEditor?"display:inline-block;":"display:none;"
					var isCanModify =data.canModifier?"display:inline-block;":"display:none;"
					var ischecked="";
					if(selectArray.indexOf(data.id)>-1){
						ischecked="checked='true'";
					}
					oLi+="<div ><input type='checkbox' "+ischecked+" value='" + data.id + "' class='select_person'/></div>";
					oLi+="<div><img src='${LUI_ContextPath}/sys/organization/image.jsp?orgId="+data.id+"&size=m&s_time=1533273775478' alt=''/></div>";
					oLi+="<div class='search_person_info'><span >"+data.name+"</span><span class='search_person_info_opt' title="+data.dept+">"+data.dept+"</span></div>";
					oLi+="<div><span class='person_atuhor' style='display:inline-block;'><i title='<bean:message key="kmCalendarRequestAuth.modify.tooltip" bundle="km-calendar" />' style='"+isCanModify+"' class='lui_icon_s lui_icon_s_icon_pencil'></i><i title='<bean:message key="kmCalendarRequestAuth.edit.tooltip" bundle="km-calendar" />' style='"+isCanAdd+"' class='lui_icon_s lui_icon_s_icon_plus_sign'></i><i title='<bean:message key="kmCalendarRequestAuth.read.tooltip" bundle="km-calendar" />' style='"+isCanRead+"' class='lui_icon_s lui_icon_s_icon_eye_open'></i></span></div>";
					return oLi+"</li>";
				}
				$("#search_btn").on("click",function(){
					var keyword = $("#search_key_word").val();
					$.ajax({
						url:"${LUI_ContextPath}/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listGroupSearch&groupId=${JsParam.groupId}&loadAll=true&keyword="+keyword,
						success:function(data){
							$("#person_view_list").html("");
							$("#person_view_list").html(createSearchDom(data));
							//
							dataArray= JSON.parse(data);
							LUI("group_page").currentPage=1;
							LUI("group_page").totalSize=dataArray.length;
							LUI("group_page").draw();
							$("#person_view_list").on("click",'input',function(e){
								//改变左边栏人员显示
								var oIndex= $(e.target).parent().parent().index();
								if($(this).prop("checked")){
									if(selectArray.indexOf($(this).val())<0){
										selectArray.push($(this).val());
									}
								}
								else{
									if(selectArray.indexOf($(this).val())>-1){
										selectArray.splice(selectArray.indexOf($(this).val()),1);
									}
								}
								topic.publish("changeShowPerson",{id:$(this).val(),isChecked:$(this).prop("checked")});
								
								if(dataArray.length == selectArray.length ){
									$("#select_all_person").prop('checked',true);
								}else{
									$("#select_all_person").prop('checked',false);
								}
								changeShowPerson();
								
							});

							//全选
							$("#select_all_person").on("click",function(){
								var isChecked = $("#select_all_person").prop("checked");
								if(isChecked){
									$("#person_view_list input[type=checkbox]").prop("checked",true);
									selectArray=[];
									for(var i = 0;i<dataArray.length;i++){
										selectArray.push(dataArray[i].id);
									}
								}else{
									$("#person_view_list input[type=checkbox]").prop("checked",false);
									selectArray=[];
								}
								topic.publish("IsSelectAll",isChecked);
								changeShowPerson();
							});
						}
					})
				})
				//月视图
				function changeShowPerson(){
					var isChecked = $("#select_all_person").prop("checked");
					var calendarObj = LUI('calendar');
					if(calendarObj.mode !='groupCalendar'){
						var fdPersonIds = '';
						var liNodes = $('.select_person:checked');
						var orgIds = [];
						if(isChecked){
							fdPersonIds = '';
						}else{
							for(var i = 0 ;i < selectArray.length;i++){
								orgIds.push(selectArray[i]);
							}
							if(orgIds.length==0){
								fdPersonIds = 'no';
							}else{
								fdPersonIds = orgIds.join(',');
							}
						}
						var source = calendarObj.source.source;
						var url = source.url;
						url = Com_SetUrlParameter(url, "personsId",fdPersonIds);
						source.setUrl(url);
						LUI('calendar').refreshSchedules();
					}
				}
				LUI.ready(function(){
					$("#search_btn").click();
				});
				//请求他人权限
				window.kmCalendarRequestAuth=function(){
					dialog.iframe('/km/calendar/km_calendar_request_auth/kmCalendarRequestAuth.do?method=add','${lfn:message("km-calendar:table.kmCalendarRequestAuth")}',null,{height:'350',width:'750'});
				}
				
				 //个人共享设置
				window.kmCalendarAuth=function(){
					dialog.iframe('/km/calendar/km_calendar_auth/kmCalendarAuth.do?method=edit','${lfn:message("km-calendar:kmCalendar.setting.authSetting")}',null,{height:'500',width:'750'});
				};
		 	});
			
		</script>
		 <%--查看日程DIV--%>
		<%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_oview.jsp"%>
		 <%--新建日程DIV--%>
		 <%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_oedit.jsp"%>
		 
		 <script type="text/javascript">
		 	seajs.use(['lui/jquery'],function($){
		 		 $("#button_save_event,#button_delete_event,#div_remind_label_edit").remove();
		 	});
		 </script>
		 
	</template:replace>
</template:include>
