	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<div id="calendar_search_container" class="lui_calendar_search_container">
	<div class="lui_calendar_header" style="background: #fcfbfc">
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
			{url:'/kms/learn/kms_learn_acti_personal/kmsLearnActiPersonal.do?method=data&fdOwn=1'}
		</ui:source>
		<!-- 列表视图 -->	
				<list:colTable isDefault="false" 
					rowHref="/kms/learn/kms_learn_activity/kmsLearnActivity.do?method=todo&fdId=!{fdAvtiId}"
					name="columntable">
					<list:col-serial />
					<list:col-html
						title="${ lfn:message('kms-learn:kmsLearnMain.docSubject')}"
						headerStyle="width:30%">
								{$
									{%row['icon']%}
									<span class="com_subject">{%row['fdAvtiClassName']%}</span>
								$}
						
							</list:col-html>
					<list:col-auto
						props="fdAvtiName,fdCreatorName,docCreateTime,fdAvtiClassEndTime" />
					<list:col-html
						title="${ lfn:message('kms-learn:kmsLearnActiPersonal.acti.docStatus')}">
							{$
								{%row['docStatus']%}
							$}
					</list:col-html>
						<list:col-html
						title="${ lfn:message('kms-learn:kmsLearnMainPersonal.fdWayStatus.progress')}">
							{$
								{%row['fdProcess']%}
							$}
					</list:col-html>
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
			
			searchInput.keydown(function(event){
				if(event.keyCode == 13){
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
			});
			
			searchButton.click(function(){
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
			source.setUrl(Com_SetUrlParameter(source.url,'q.docSubject',value));
			source.get();
			$("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
		});
	});
</script>
