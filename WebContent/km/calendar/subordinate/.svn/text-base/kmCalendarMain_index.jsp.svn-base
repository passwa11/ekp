<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import=" com.landray.kmss.util.ResourceUtil"%>

<%
	request.setAttribute("dateFormatter", ResourceUtil.getString("date.format.date"));
	request.setAttribute("dateTimeFormatter",ResourceUtil.getString("date.format.datetime"));
	request.setAttribute("userPersonId",UserUtil.getUser().getFdId());
%>

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
</style>

<ui:calendar id="calendar" showStatus="drag" mode="default" layout="km.calendar.default">
	<ui:dataformat>
		<ui:source type="AjaxJson">
			{url:'/sys/subordinate/sysSubordinate.do?method=data&modelName=com.landray.kmss.km.calendar.model.KmCalendarMain&orgId=${JsParam.orgId}'}
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
<%@ include file="/km/calendar/subordinate/kmCalendarMain_search.jsp"%>
<%--查看日程DIV--%>
<div class="lui_calendar calendar_view" id="calendar_view" style="display: none;position: absolute;">
   	<div class="lui_calendar_top">
   		<div id="header_title" class="lui_calendar_title">
   			<bean:message bundle="km-calendar" key="kmCalendarMain.export.myCalendar"  />
   		</div>
        <div class="lui_calendar_close" onclick="close_view('calendar_view');"></div>
   	</div>
	<div class="calendar_view_content">
		<div class="view_sched_wrapper">
				<input type="hidden"  name="fdId" />
				<input type="hidden" name="fdIsGroup" />
		 		<table class="view_sched">
		 			<tr>
		 				<%--时间--%>
	               		<td class="title" width="50px">
	               			<bean:message bundle="km-calendar" key="kmCalendarMain.docTime"  />
	               		</td>
	                 	<td>
	                     	<div id="calendar_date" style="text-align: left"></div>
	                 	</td>
	             	</tr>
	             	<tr>
	             		<%--内容--%>
	                 	<td class="title" width="50px" valign="top">
	                     	<bean:message bundle="km-calendar" key="kmCalendarMain.docContent"  />
	                 	</td>
	                 	<td>
	                     	<div id="calendar_title" style="text-align: left;word-break: break-all;max-height: 100px;overflow-y: auto;min-width: 200px;"></div>
	                 	</td>
	             	</tr>
	             	<tr id="tr_relation_url" style="display: none;">
	             		<%--内容--%>
	                 	<td class="title" ></td>
	                 	<td>
	                     	<a id="calendar_relatation_url" style="text-align: left; cursor: pointer;color:blue;" target="_blank">${lfn:message('km-calendar:kmCalendarMain.calendar.source')}</a>
	                 	</td>
	             	</tr>
				</table>
		 </div>
	</div>
</div>

<%--查看下属笔记DIV--%>
<div class="lui_calendar note_view" id="note_view" style="display: none;position: absolute;">
	<div class="lui_calendar_top">
		<div class="lui_calendar_title">
			<bean:message bundle="km-calendar" key="kmCalendar.subordinate.note"  />
		</div>
		<div class="lui_calendar_close" onclick="close_view('note_view');"></div>
	</div>
	<div class="calendar_view_content">
		<div class="view_sched_wrapper">
			<form id="noteViewForm"  name="kmCalendarMainForm">
				<input type="hidden"  name="fdId" />
				<table class="view_sched">
					<tr>
						<%--标题--%>
						<td class="title" width="60px">
							<bean:message bundle="km-calendar" key="kmCalendarMain.docSubject"  />
						</td>
						<td>
							<div id="note_subject" style="text-align: left;word-break: break-all;"></div>
						</td>
					</tr>
					<tr>
						<%--内容--%>
						<td class="title" width="50px" valign="top">
							<bean:message bundle="km-calendar" key="kmCalendarMain.docContent"  />
						</td>
						<td>
							<div id="note_docContent" style="text-align: left;word-break: break-all;max-height: 100px;overflow-y: auto;min-width: 200px;"></div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">	
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/toolbar','lui/util/env'], function($,dialog , topic ,toolbar,env) {
		topic.subscribe('calendar.loaded',function(arg){
			console.log("calendar_setting:", $("#calendar_setting"));
			$("#calendar_setting").remove();
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
 	       	$("#calendar_add :input[name='docFinishTime']").val(kmcalendarDateUtil.formatDate(dateTime,"${dateFormatter}"));			 	        
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

		window.labelId_select = "";
		window.setLabelSelected=function(){
			$("#calendarViewForm :input[name='labelId']").val(labelId_select);
		};
		
		//显示日程
		topic.subscribe('calendar.thing.click',function(arg){
			console.log("查看下属日程...")
			$('#header_title').html('${lfn:message("km-calendar:subordinate.kmCalendarMain")}');
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
	});
	
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
	
	window.close_view=function(id){
		$("#"+id).fadeOut();//隐藏对话框
	};
</script>