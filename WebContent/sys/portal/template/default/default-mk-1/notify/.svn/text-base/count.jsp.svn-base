<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="lui_single_menu_header_text">
	<div title="${ lfn:message('sys-notify:header.type.todo') }" data-lui-switch-class='hover' onclick="window.open('${LUI_ContextPath}/sys/notify?dataType=todo#j_path=%2Fprocess','_blank')" class="lui_single_menu_header_daiban_div">
		<i class="lui_single_menu_header_icon lui_icon_s_daiban"></i>
		<span>待办</span>
		<div id="__notify_daiban__" class='lui_single_menu_header_num lui_single_menu_header_daiban com_prompt_num'>0</div>
	</div>
	<div title="${ lfn:message('sys-notify:header.type.view') }" data-lui-switch-class='hover' onclick="window.open('${LUI_ContextPath}/sys/notify?dataType=toview#j_path=%2Fread','_blank')" class="lui_single_menu_header_daiyue_div">
		<i class="lui_single_menu_header_icon lui_icon_s_daiyue"></i>
		<span>待阅</span>
		<div id="__notify_daiyue__" class='lui_single_menu_header_num lui_single_menu_header_daiyue com_prompt_num'>0</div>
	</div>
</div>
<script>

	seajs.use(['lui/topic'], function(topic) {
		LUI.ready(function(){
			var refreshTime = parseInt(${ param['refreshTime'] });
			if(isNaN(refreshTime) || refreshTime<1){
				refreshTime = 0;
			}
			var refreshNotify = function(){
				LUI.$.getJSON(Com_Parameter.ContextPath + "sys/notify/sys_notify_todo/sysNotifyTodo.do?method=sumTodoCount",function(json){
					if(json!=null){
						LUI.$("#__notify_daiban__").html(json.type_1==null?0:(json.type_1>100 ? '99+' : json.type_1));
						LUI.$("#__notify_daiyue__").html(json.type_2==null?0:(json.type_2>100 ? '99+' : json.type_2));
					}
				});
				if(refreshTime>0)
					window.setTimeout(refreshNotify,refreshTime*1000*60);
			};
			refreshNotify();
			topic.subscribe('portal.notify.refresh',function(){
				refreshNotify();
			});
		});
	});
</script>