<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	request.setAttribute("userPersonId",UserUtil.getUser().getFdId());
	SysAgendaBaseConfig sysAgendaBaseConfig = new SysAgendaBaseConfig();
	String calendarDisplayType = sysAgendaBaseConfig.getCalendarDisplayType();
	if(StringUtil.isNull(calendarDisplayType) || calendarDisplayType.equals("month")) {
		calendarDisplayType = "default";
	}else{
		calendarDisplayType = "personGroupCalendar";
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
		<c:param name="key" value="personGroup"></c:param>
		<c:param name="showLeft" value="false"></c:param>
	</c:import>
		<script type="text/javascript">	
			seajs.use([
				'km/calendar/resource/js/dateUtil',
				'km/calendar/resource/js/calendar_personGroup',
				'lui/jquery',
				'lui/topic',
				'lui/dialog'],
				 function(dateUtil,calendar,$,topic,dialog) {
				//群组类
				window.personGroupCalendar=calendar.PersonGroupCalendarMode;
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
					//var windowParentHeight = $(window.parent).height()-110;
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
					/* #140282 防止弹窗显示过高而被挡住 */
					if(y < 10) {
						y = 10;
					}
					return {"top":y,"left":x};
				};
				
				topic.subscribe('calendar.select',function(arg){
					if(!arg.personId){
						return;
					}
					$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
					//初始化时间
					var start=Com_GetDate(arg.start);
					var end=arg.end;
					if(end==null){
						end=start;
					}
					$("#calendar_add :input[name='docStartTime']").val(dateUtil.formatDate(start,"${dateFormatter}"));
				    $("#calendar_add :input[name='docFinishTime']").val(dateUtil.formatDate(end,"${dateFormatter}"));
				    $("#calendar_add :input[name='docOwnerId']").val(arg.personId);
				    if(arg.personId=='${userPersonId}'){
				    	$("#calendar_add #docOwnerName").html('<bean:message bundle="km-calendar" key="kmCalendarMain.docOwner.self" />');
				    }else{
				    	$("#calendar_add #docOwnerName").html(arg.personName);
				    	$("#calendar_add :input[name='docOwnerName']").val(arg.personName);
				    	$("#owner_desc").text('<bean:message bundle="km-calendar" key="kmCalendar.create.groupEvent.desc" />');
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
					if(typeof arg.schedule.isGroup=='undefined' || arg.schedule.isGroup==false){
						$('#header_title').html('${lfn:message("km-calendar:kmCalendarMain.person.header.title")}');
						$("#tr_person_names").hide();
					}else{
						$('#header_title').html('${lfn:message("km-calendar:kmCalendarMain.group.header.title")}');
						$('#tr_person_names').show();
						$('#person_names').html(arg.schedule.personNames);
					}
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
							if(arg.schedule.isGroup && arg.schedule.isGroup==true){
								if(data['canEdit']==true)
									$("#calendar_view_btn").show();
							}else{
								if(data['canEdit']==true || arg.schedule.canModifier)
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
					$("#calendarViewForm :input[name='fdIsGroup']").val(arg.schedule.isGroup);
					$("#mainGroupId").val(arg.schedule.mainGroupId);
					setTimeout(function(){
						$("#calendar_view").css(getPos(arg.evt,$("#calendar_view"))).fadeIn("fast");
					},500);
				});

				//群组日程导出
				window.kmCalendarExport=function(){
					var url="/km/calendar/km_calendar_main/kmCalendarMain_setTime.jsp?type=personGroupCalendar";
					var locationUrl=LUI("calendar").source.source.url;
					url+="&personGroupId="+Com_GetUrlParameter(locationUrl,"personGroupId");//
					dialog.iframe(url,"${lfn:message('km-calendar:kmCalendarMain.exportGroupTitle')}",function(){
						
					},{width:550,height:350});
				};
				
				window.addGroupCalendar=function(url){
					dialog.iframe(url,"${lfn:message('km-calendar:kmCalendarMain.btn.add.calendar')}",function(){
						
					},{width:700,height:550});
				}
				
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
		<div style="display:table-cell;background:#fff;">
			<ui:calendar id="calendar" showStatus="edit" mode="${calendarMode}"  layout="km.calendar.personGroup" customMode="{'id':'personGroupCalendar','name':'群组日历','func':personGroupCalendar}">
				<ui:dataformat>
					<ui:source type="AjaxJson">
						{url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=listPersonGroupCalendar&personGroupId=${JsParam.personGroupId}'}
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
				<h3 id="group_name"><bean:message bundle="km-calendar" key="kmCalendarShareGroup.fdGroup" />-${lfn:decodeUnicode(JsParam.personGroupName)}</h3>
				<label><input type="checkbox" checked="true" id="select_all_person"/>${lfn:message('km-calendar:km.calendar.group.selectAll') }</label>
				<ul id="person_view_list">
				</ul>	
				<list:paging id="personGroup_page" pageSize="10"  layout="km.calendar.paging.default" viewSize="1"></list:paging>
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
			//根据左边动态增加右边人员
			topic.subscribe("person_next_person_all",function(personIds){
				console.log(personIds);
				$.ajax({
					url:"${LUI_ContextPath}/km/calendar/km_calendar_person_group/kmCalendarPersonGroup.do?method=listPersonGroupSearch&personGroupId=${JsParam.personGroupId}&personsId="+personIds,
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
						LUI("personGroup_page").currentPage=1;
						LUI("personGroup_page").totalSize=dataArray.length;
						LUI("personGroup_page").draw();
					}
				 })
			})
			
			topic.subscribe("paging.changed",function(e){
				$("#person_view_list").html("");
				var pageSize=LUI("personGroup_page").pageSize;
				var currentPage=LUI("personGroup_page").currentPage;
				currentPage=currentPage<=0?1:currentPage;
				var pageArray=dataArray.slice(pageSize*(currentPage-1),pageSize*currentPage);
				for(var i = 0;i<pageArray.length;i++){
					var li = createLi(pageArray[i]);
					$("#person_view_list").append($(li));
				}
				LUI("personGroup_page").draw();
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
				oLi+="<div ><input type='checkbox' "+ischecked+" value='" + data.id + "'  class='select_person'/></div>";
				oLi+="<div><img src='${LUI_ContextPath}/sys/organization/image.jsp?orgId="+data.id+"&size=m&s_time=1533273775478' alt=''/></div>";
				oLi+="<div class='search_person_info'><span >"+data.name+"</span><span class='search_person_info_opt' title="+data.dept+">"+data.dept+"</span></div>";
				oLi+="<div><span class='person_atuhor' style='display:inline-block;'><i title='<bean:message key="kmCalendarRequestAuth.modify.tooltip" bundle="km-calendar" />' style='"+isCanModify+"' class='lui_icon_s lui_icon_s_icon_pencil'></i><i title='<bean:message key="kmCalendarRequestAuth.edit.tooltip" bundle="km-calendar" />' style='"+isCanAdd+"' class='lui_icon_s lui_icon_s_icon_plus_sign'></i><i title='<bean:message key="kmCalendarRequestAuth.read.tooltip" bundle="km-calendar" />' style='"+isCanRead+"' class='lui_icon_s lui_icon_s_icon_eye_open'></i></span></div>";
				return oLi;
			}
			$("#search_btn").on("click",function(){
				var keyword = $("#search_key_word").val();
				//#166390 对关键词中文（ie乱码）搜索 -post请求
				$.ajax({
					url:"${LUI_ContextPath}/km/calendar/km_calendar_person_group/kmCalendarPersonGroup.do?method=listPersonGroupSearch&personGroupId=${JsParam.personGroupId}&loadAll=true",
					type : 'POST',
					data : $.param({"keyword":keyword}, true),
					success:function(data){
						$("#person_view_list").html("");
						$("#person_view_list").html(createSearchDom(data));
						dataArray = JSON.parse(data);
						LUI("personGroup_page").currentPage=1;
						LUI("personGroup_page").totalSize=dataArray.length;
						LUI("personGroup_page").draw();
						$("#person_view_list").on("click","input",function(e){
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
							topic.publish("changeShowPersonGroup",{id:$(this).val(),isChecked:$(this).prop("checked")});

							if(dataArray.length == selectArray.length){
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
			});
			window.setColor=function(schedule){
				var eventColor='#ecfefe';//默认日程颜色
				var noteColor=eventColor;//笔记颜色
				var groupEventColor='#00ccff';//群组日程颜色
		
				if(schedule.isGroup!=null&&schedule.isGroup==true)
					schedule.color=groupEventColor;
				else
					schedule.color=eventColor;
			};
			
			//初始化默认标签的颜色,针对集合
			window.setColors=function(data){
				for(var i=0;i<data.length;i++){
					setColor(data[i]);
				}
				return data;
			};
			//月视图, #144559 修复 周共享日程筛选问题
			function changeShowPerson(){
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
				LUI('calendar').refreshSchedules();
			}
			LUI.ready(function(){
				$("#search_btn").click();
			});
	 	});
		</script>
		 <%--查看日程DIV--%>
		<%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_groupView.jsp"%>
		 <%--新建日程DIV--%>
		 <%@ include file="/km/calendar/km_calendar_main/kmCalendarMain_oedit.jsp"%>
		 
		 <script type="text/javascript">
		 	seajs.use(['lui/jquery'],function($){
		 		 $("#button_save_event,#button_delete_event,#div_remind_label_edit").remove();
		 	});
		 </script>
		 
	</template:replace>
</template:include>
