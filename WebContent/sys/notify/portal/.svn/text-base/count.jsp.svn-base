<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

	<div class="lui_portal_header_text">
		<div title="${ lfn:message('sys-notify:header.type.todo') }" onclick="window.open('${LUI_ContextPath}/sys/notify?dataType=todo#j_path=%2Fprocess','_blank')" class="lui_portal_header_daiban_div">
			<div class="lui_icon_s lui_icon_s_daiban" style="vertical-align: text-top;"></div>
			<div id="__notify_daiban__" class='lui_portal_header_daiban'></div>
		</div>
		<div title="${ lfn:message('sys-notify:header.type.view') }" onclick="window.open('${LUI_ContextPath}/sys/notify?dataType=toview#j_path=%2Fread','_blank')" class="lui_portal_header_daiyue_div">
			<div class="lui_icon_s lui_icon_s_daiyue" style="vertical-align: text-top;"></div>
			<div id="__notify_daiyue__" class='lui_portal_header_daiyue'></div>
		</div>
	</div>
<script>

seajs.use(['lui/topic','lui/refreshTodo'], function(topic,refreshTodo) {
	LUI.ready(function(){ 
		 
		var refreshTime = parseInt("${ param['refreshTime'] }");
		if(isNaN(refreshTime) || refreshTime<1){
			refreshTime = 0;
		}
		
		var refreshNotify = function(init){
			LUI.$.getJSON(Com_Parameter.ContextPath + "sys/notify/sys_notify_todo/sysNotifyTodo.do?method=sumTodoCount",function(json){
				if(json!=null){
					LUI.$("#__notify_daiban__").html(json.type_1==null?0:json.type_1);
					LUI.$("#__notify_daiyue__").html(json.type_2==null?0:json.type_2);				
				}
			});
			if(init){
				refreshTime =init;
			}
			if(refreshTime>0)
				window.setTimeout(refreshNotify,refreshTime*1000*60);
		}; 
		topic.subscribe('portal.notify.refresh',function(){ 
			refreshNotify();
		});
		
		var refreshCallBack = function(){ 
			//执行刷新
			refreshNotify(0);
			//让所有子部件中有监听该属性的执行对应的操作
			var iframes=document.getElementsByTagName("iframe");
			if(iframes){  
				for(var i=0;i<iframes.length;i++) 
				{   
					var iframeInfo =$(iframes[i]);
					var src = iframeInfo.attr('src'); 
					if(src && src.indexOf('sys/notify/sys_notify_todo/sysNotifyTodo.do?method=list') > -1){  
						iframeInfo.attr('src', iframeInfo.attr('src')); 
					} 
				} 
			}
		} 
		//第一个参数是执行待办刷新，第二个参数是初始化参数。
		refreshTodo.initRefreshTodo(refreshCallBack,refreshNotify);
		
	});
});
</script>

<script src="${LUI_ContextPath}/sys/profile/resource/js/notification.js" type="text/javascript"></script>