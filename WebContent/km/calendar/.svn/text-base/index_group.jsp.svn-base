<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	request.setAttribute("userPersonId",UserUtil.getUser().getFdId());
	//UA=mobile跳转到移动端的主页(临时解决方案)
	if(MobileUtil.getClientType(new RequestContext(request)) > -1){
		response.sendRedirect("mobile/group.jsp");
	}
	SysAgendaBaseConfig sysAgendaBaseConfig = new SysAgendaBaseConfig();
	String calendarDisplayType = sysAgendaBaseConfig.getCalendarDisplayType();
	if(StringUtil.isNull(calendarDisplayType) || calendarDisplayType.equals("month")) {
		calendarDisplayType = "default";
	}else{
		calendarDisplayType = "groupCalendar";
	}
	request.setAttribute("calendarMode",calendarDisplayType);
%>
<template:include ref="default.simple" spa="true">
	<template:replace name="body">
	<%--日历框架JS、CSS--%>
	<link rel="Stylesheet" href="${LUI_ContextPath}/km/calendar/resource/css/calendar_main.css" />
	<script type="text/javascript">
		seajs.use(['theme!list']);	
	</script>
	<c:import url="/km/calendar/import/nav.jsp" charEncoding="UTF-8">
		<c:param name="key" value="group"></c:param>
		<c:param name="showLeft" value="false"></c:param>
	</c:import>
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
						window.kmcalendarDateUtil=dateUtil;

						// 监听新建更新等成功后刷新
						topic.subscribe('successReloadPage', function() {
							topic.publish('list.refresh');
						});

						//获取位置
						var getPos=function(evt,showObj){
							var sWidth=showObj.width();
							var sHeight=showObj.height();
							var leftMargin=$(".lui_list_left_sidebar_innerframe").width(),
									topMargin=$(".lui_portal_header").height();
							x=evt.pageX-leftMargin;
							y=evt.pageY-topMargin;
							//console.log("sWidth:"+sWidth+"sHeight:"+sHeight+"x:"+x+"y:"+y+"$(window.parent).height():"+$(window.parent).height());
							//console.log("$(window.parent):"+$(window.parent));
							//windowParentHeight:获取到的数值减去40大约才为页面真实高度。
							// 由获取windows的高度换成获取body的高度 modify by wangjf
							var windowParentHeight = $(document.body).outerHeight(true);
							/*处理纵向弹出框位置*/
							if(y+sHeight < windowParentHeight){
								//点击位置为编辑框左上角，不做处理
							}else if( y>sHeight && y+sHeight> windowParentHeight){
								//点击位置为弹框左下角
								y-=sHeight;
							}else if(windowParentHeight< 2*sHeight){
								//点击位置为页面中间
								y=(windowParentHeight-sHeight)/2 + 100;
							}
							/*处理横向弹出框位置*/
							if(x+sWidth>$(document.body).outerWidth(true)){
								x-=sWidth;
							}
							return {"top":y,"left":x};
						};

						topic.subscribe('calendar.select',function(arg){
							$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
							//初始化时间
							if(!arg.personId){
								var start=arg.start;
							}else{
								var start=Com_GetDate(arg.start);
							}
							var end=arg.end;
							if(end==null){
								end=start;
							}
							$("#calendar_add :input[name='docStartTime']").val(dateUtil.formatDate(start,"${dateFormatter}"));
							$("#calendar_add :input[name='docFinishTime']").val(dateUtil.formatDate(end,"${dateFormatter}"));
							if(!arg.personId){
								$("#calendar_add :input[name='docOwnerId']").val("multiCreate");
								$("#calendar_add #docOwnerName").html('<bean:message bundle="km-calendar" key="kmCalendarShareGroup.fdGroup" />');
								//显示群组地址本
								$("#multiOwner").show();
								window.eventValidation.addElements($('#multiOwner')[0],'required');
							}else{
								$("#calendar_add :input[name='docOwnerId']").val(arg.personId);
								if(arg.personId=='${userPersonId}'){
									$("#calendar_add #docOwnerName").html('<bean:message bundle="km-calendar" key="kmCalendarMain.docOwner.self" />');
								}else{
									$("#calendar_add #docOwnerName").html(arg.personName);
									$("#calendar_add :input[name='docOwnerName']").val(arg.personName);
								}
								//隐藏群组地址本
								$("#multiOwner").hide();
								window.eventValidation.removeElements($('#multiOwner')[0],'required');
							}
							//是否全天
							$("#fdIsAlldayevent").prop("checked",false);
							var dateTime=new Date(start);
							$("#startTimeDiv,#endTimeDiv").css("display","inline");
							var now = new Date();
							$("[name='startHour']").val(now.getHours()+1);
							$("[name='startMinute']").val(start.getMinutes());
							dateTime.setHours(now.getHours()+2);
							$("#calendar_add :input[name='docFinishTime']").val(dateUtil.formatDate(dateTime,"${dateFormatter}"));
							$("[name='endHour']").val(dateTime.getHours());
							$("[name='endMinute']").val(dateTime.getMinutes());
							//初始化标签
							if(LUI("label_edit")){
								LUI("label_edit").source.url = '/km/calendar/km_calendar_label/kmCalendarLabel.do?method=listJson&userId='+arg.personId;
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

						//显示日程
						topic.subscribe('calendar.thing.click',function(arg){
							//题头:群组日程
							var header_title='${lfn:message("km-calendar:module.km.calendar.tree.share.calendar")}';
							if(arg.schedule.owner){
								header_title+="-"+arg.schedule.owner;
							}
							$('#header_title').html(header_title);
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
										if(data['canEdit']==true || arg.schedule.canModifier){
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
							setTimeout(function(){
								$("#calendar_view").css(getPos(arg.evt,$("#calendar_view"))).fadeIn("fast");
							},500);
						});

						//群组日程导出
						window.kmCalendarExport=function(){
							var url="/km/calendar/km_calendar_main/kmCalendarMain_setTime.jsp?type=groupCalendar";
							var locationUrl=LUI("calendar").source.source.url;
							url+="&groupId="+Com_GetUrlParameter(locationUrl,"groupId");//
							dialog.iframe(url,"${lfn:message('km-calendar:kmCalendarMain.exportGroupTitle')}",function(){

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

					});
		</script>
		<div style="display:table">
			<div style="display:table-cell;background:#fff;">
				<ui:calendar id="calendar" showStatus="edit" mode="${calendarMode}" layout="km.calendar.group" customMode="{'id':'groupCalendar','name':'群组日历','func':groupCalendar}">
					<ui:dataformat>
						<ui:source type="AjaxJson">
							{url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=listGroupCalendar&groupId=${JsParam.groupId}&isShare=true'}
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
						{$<p style="cursor:pointer;" title="{%title%}">$}
						var str=data['owner'] + ' ';
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
					</ui:render>
				</ui:calendar>
			</div>
			
			<div id ="search_box" >
				<div id="group_serarch">
					<input type="search"  placeholder="${lfn:message('km-calendar:km.calendar.group.user.search') }" value="" id="search_key_word"/><input type="button" id="search_btn" value="${lfn:message('button.search') }"/>
				</div>
				<div style="height:1px;background:#ccc;margin-bottom:6px;"></div>
				<h3 id="group_name"><bean:message bundle="km-calendar" key="kmCalendar.setting.groupSetting" />-${lfn:decodeUnicode(JsParam.groupName)}</h3>
				<label><input type="checkbox" checked="true" id="select_all_person"/>${lfn:message('km-calendar:km.calendar.group.selectAll') }</label>
				<ul id="person_view_list">
				</ul>	
				<list:paging id="group_page" pageSize="10"  layout="km.calendar.paging.default" viewSize="1"></list:paging>
			</div>
		</div>
		<script>
		seajs.use(['lui/jquery','lui/topic'],function($,topic){
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
				var isCanModify =data.canModifier?"display:inline-block;":"display:none;";
				var ischecked="";
				if(selectArray.indexOf(data.id)>-1){
					ischecked="checked='true'";
				}
				oLi+="<div ><input type='checkbox' "+ischecked+" value='" + data.id + "' class='select_person'/></div>";
				oLi+="<div><img src='${LUI_ContextPath}/sys/organization/image.jsp?orgId="+data.id+"&size=m&s_time=${LUI_Cache}' alt=''/></div>";
				oLi+="<div class='search_person_info'><span >"+data.name+"</span><span class='search_person_info_opt' title="+data.dept+">"+data.dept+"</span></div>";
				oLi+="<div><span class='person_atuhor' style='display:inline-block;'><i title='<bean:message key="kmCalendarRequestAuth.modify.tooltip" bundle="km-calendar" />' style='"+isCanModify+"' class='lui_icon_s lui_icon_s_icon_pencil'></i><i title='<bean:message key="kmCalendarRequestAuth.edit.tooltip" bundle="km-calendar" />' style='"+isCanAdd+"' class='lui_icon_s lui_icon_s_icon_plus_sign'></i><i title='<bean:message key="kmCalendarRequestAuth.read.tooltip" bundle="km-calendar" />' style='"+isCanRead+"' class='lui_icon_s lui_icon_s_icon_eye_open'></i></span></div>";
				return oLi+"</li>";
			}
			//共享日程下的人员查询后，左侧日历显示结果不正确 #105260
			function showChangePersonBySearch(){
				var selectArrayPersons= $("#person_view_list").find('input');
				if(!selectArrayPersons||selectArrayPersons.length==0){
					return;
				}
				selectArray=[];
				topic.publish("IsSelectAll",false);
				for(var i=0;i<selectArrayPersons.length;i++){
					var selectPersonId=$(selectArrayPersons[i]).val();
					var isPersonCheck=$(selectArrayPersons[i]).prop("checked");
					if(isPersonCheck){
						if(selectArray.indexOf(selectPersonId)<0){
							selectArray.push(selectPersonId);
						}
					}
					else{
						if(selectArray.indexOf(selectPersonId)>-1){
							selectArray.splice(selectPersonId,1);
						}
					}
					topic.publish("changeShowPerson",{id:selectPersonId,isChecked:isPersonCheck});
					
					if(dataArray.length == selectArray.length ){
						$("#select_all_person").prop('checked',true);
					}else{
						$("#select_all_person").prop('checked',false);
					}
					changeShowPerson(true);
				}
				LUI('calendar').refreshSchedules();
			}
			//搜索按钮
			$("#search_btn").on("click",function(){
				var keyword = $("#search_key_word").val();
				$.ajax({
					url:"${LUI_ContextPath}/km/calendar/km_calendar_share_group/kmCalendarShareGroup.do?method=listGroupSearch&groupId=${JsParam.groupId}&loadAll=true&keyword="+keyword+"&isShare=true",
					success:function(data){
						$("#person_view_list").html("");
						$("#person_view_list").html(createSearchDom(data));
						
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
							$(".lui_calendar_content_nodata").hide(); //没有更多的数据可加载了.....
							$(".lui_calendar_content_loadmore").show();//加载更多
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
						if(!window.__init__ && (!keyword || keyword.length>0)) {
							if(window.console) {
								console.log("人员变更查询日程信息");
							}
							showChangePersonBySearch();
						}
						window.__init__ = false;
					}
				})
			});
			window.setColor=function(schedule){
				var eventColor='#ecfefe';//默认日程颜色
				var noteColor=eventColor;//笔记颜色
				var groupEventColor='#ecfefe';//群组日程颜色
				//共享日程只显示一种颜色
				schedule.color=groupEventColor;
			};
			
			//初始化默认标签的颜色,针对集合
			window.setColors=function(data){
				for(var i=0;i<data.length;i++){
					setColor(data[i]);
				}
				return data;
			};
			//月视图, #144559 修复 周共享日程筛选问题
			function changeShowPerson(noRefresh){
				var calendarObj = LUI('calendar');
				var fdPersonIds = '';
				var orgIds = [];
				for(var i = 0 ;i < selectArray.length;i++){
					orgIds.push(selectArray[i]);
				}
				if(orgIds.length==0){
					fdPersonIds = 'no';
				}else{
					fdPersonIds = orgIds.join(',');
				}
				var source = calendarObj.source.source;
				var url = source.url;
				url = Com_SetUrlParameter(url, "personsId",fdPersonIds);
				source.setUrl(url);
				if(!noRefresh){
					LUI('calendar').refreshSchedules();
				}
			}
			window.__init__ = true;
			LUI.ready(function(){
				$("#search_btn").click();
			});
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
