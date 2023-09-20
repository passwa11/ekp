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
			{"url":"/km/calendar/km_calendar_main/kmCalendarMain.do?method=page&rowsize=8"}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				onRowClick="window.opendialogInSearch('!{fdId}')" name="columntable">
			<list:col-html  title="${ lfn:message('km-calendar:kmCalendarMain.docSubject') }">
			 {$ <span class="com_subject" style="text-align: left;">{%row['docSubject']%}</span> $}
			</list:col-html>
			<list:col-auto props="date;allday;recurrenceType;labelName"></list:col-auto>	
		</list:colTable>		
	</list:listview>
	<list:paging></list:paging>
</div>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lang!sys-ui'],function($,dialog,topic,lang){
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
		window.opendialogInSearch = function(fdId){
			var url = "/km/calendar/km_calendar_main/kmCalendarMain.do?method=edit&fdId=" + fdId,
				header = $('<div/>');
			$('<span/>').appendTo(header)
						.html('${lfn:message("km-calendar:kmCalendarMain.opt.edit")}')
						.css('cursor','pointer')
						.attr('id','event_base_label')
						.addClass('event_lable_select');
			$('<span/>').appendTo(header)
						.html('|${lfn:message("km-calendar:kmCalendar.label.table.share")}')
						.css('cursor','pointer')
						.attr('id','event_auth_label')
						.addClass('event_lable_unselect');
			dialog.iframe(url,header.html(),function(rtn){
				if(rtn!=null){
					
				}
			},{width:700,height:550});
		};
	});
</script>