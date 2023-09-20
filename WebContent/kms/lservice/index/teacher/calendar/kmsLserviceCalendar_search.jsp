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
		{url:'/kms/train/kms_train_main/kmsTrainMain.do?method=data&dataType=pic&rowsize=16&myDoc=teacher&type=fdPlanId'}
	</ui:source>
		<!-- 列表视图 -->	
		<list:colTable isDefault="false" rowHref="/kms/train/kms_train_main/kmsTrainMain.do?method=view&type=viewToEdit&fdId=!{fdId}&operator=admin" name="gridtableList">
			<list:col-serial></list:col-serial>
			<list:col-html title="${lfn:message('kms-train:kmsTrainMain.docSubject')}" style="width:30%;padding:0 8px">
							if(row['icon']) {
								{$
									<span title="{%row['icontext']%}" class="lui_icon_s {%row['icon']%}"></span> 
								$}
							}	
							{$
								<span class="com_subject">{%decodeHTML(row['docSubject'],true)%}</span>
							$}
			</list:col-html>
			
			<list:col-html title="${lfn:message('kms-train:kmsTrainMain.planCategory')}">
						if(row['trainDocSubject']) {
							{$
								{%row['trainDocSubject']%}
							$}
						}
			</list:col-html>
			
			<list:col-html title="${lfn:message('kms-train:kmsTrain.4m.trainBeginTime')}">
				if(row['trainPlanDate']) {
							{$
								{%row['trainPlanDate']%}
							$}
						}
			</list:col-html>
			<list:col-auto props="docAuthor.fdName;docCategory.fdName;docCreateTime"></list:col-auto>
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
	seajs.use("kms/train/admin/js/adm_train_main.js");
</script>
