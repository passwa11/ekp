<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<div id="calendar_search_container" class="lui_calendar_search_container">
	<div class="lui_calendar_header">
		<table class="lui_calendar_header_tab">
			<tr>
				<td class="lui_calendar_header_left">
					<span class="lui_calendar_search_back">
						${lfn:message('button.back')}
					</span>
					<span class="lui_calendar_search">
						<span class="lui_calendar_search_inputContainer">
							<input type="text" class="lui_calendar_search_input" placeholder="${lfn:message('sys-ui:ui.category.keyword') }"/>
						</span>
						<input type="button"  class="lui_calendar_search_button"/>
					</span>
				</td>
			</tr>
		</table>
	</div>
	<list:listview id="lui_calendar_search_listview">
		<ui:source type="AjaxJson">
			{url:'/sys/subordinate/sysSubordinate.do?method=list&modelName=com.landray.kmss.km.calendar.model.KmCalendarMain&orgId=${JsParam.orgId}&rowsize=8'}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				onRowClick="window.opendialogInSearch(event,'!{fdId}')" name="columntable">
			<list:col-html  title="${ lfn:message('km-calendar:kmCalendarMain.docSubject') }">
			 {$ <span class="com_subject" style="text-align: left;">{%row['docSubject']%}</span> $}
			</list:col-html>
			<list:col-auto props="date;allday;recurrenceType;labelName"></list:col-auto>	
		</list:colTable>		
	</list:listview>
	<list:paging></list:paging>
</div>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/util/env','lang!sys-ui'],function($,dialog,topic,env,lang){
		$(function(){
			var calendarContainer = $('#calendar'),
				searchContainer = $('#calendar_search_container'),
				searchButton = $('.lui_calendar_search_button',searchContainer),
				searchInput = $('.lui_calendar_search_input',searchContainer),
				searchBack = $('.lui_calendar_search_back',searchContainer);
			function __searchFunc(){
				var value = searchInput.val();
				if(!value){
					dialog.failure(lang['ui.category.keyword'],null,null,'suggest',null,{
						autoCloseTimeout : 1
					});
					return;
				}
				topic.publish('calenadr.thing.search',{
					value : value
				});
			}
			searchButton.click(__searchFunc);
			searchInput.on('keyup',function(e) {
				e = e || window.event;
				if(e && e.keyCode == 13){ 
					__searchFunc();
			    }
			});
			searchBack.click(function(){
				searchContainer.hide();
				calendarContainer.show();
			});
		});
		topic.subscribe('calenadr.thing.search',function(args){
			var value = args.value,
				listview = LUI('lui_calendar_search_listview'),
				source = listview.source;
			source.setUrl(Com_SetUrlParameter(source.url,'subject',value));
			source.get();
			$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
		});
		window.opendialogInSearch = function(evt,fdId) {
			window.loading = dialog.loading();
			$.get("${LUI_ContextPath}/sys/subordinate/sysSubordinate.do?method=view&modelName=com.landray.kmss.km.calendar.model.KmCalendarMain&orgId=${JsParam.orgId}&modelId=" + fdId, function(data) {
				if(window.loading != null)
					window.loading.hide();
				
				$('#header_title').html('${lfn:message("km-calendar:subordinate.kmCalendarMain")}');
				$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
				$("#calendar_view_btn").show();//我的日程,默认一定显示操作栏
				//初始化内容
				if (data.docContent) {
                	$("#calendar_content").html(data.docContent);
                	$("#tr_content").show();
                } else {
                	$("#tr_content").hide();
                }
                var DateString = data.docStartTime;
				if (data.docFinishTim != null) {
					DateString += "-" + data.docFinishTime;
				}
				$("#calendar_date").html(DateString);//初始化日期
				
				var __title =  env.fn.formatText(data.docSubject);
				
				$("#calendar_title").html( __title );
				$("#calendar_view").css(getPos(evt,$("#calendar_view"))).fadeIn("fast");
				clearReminder('#simple_event');
			}, 'json');
			return false;
		};
		//获取位置
		var getPos = function(evt,showObj){
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
	});
</script>