<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/dialog','lui/topic','lui/framework/router/router-utils'], function($, dialog , topic,routerUtils) {
		LUI.ready(function(){
		});
		
		//删除
		window.mngDelete = function(){
			var values = [];
			$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			dialog.confirm('<bean:message bundle="sys-notify" key="sysNotifyTodo.confirm.finish"/>',function(value){
				if(value==true){
					window.del_load = dialog.loading();
					$.post('<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=deleteall"/>',
							$.param({"List_Selected":values},true),SdelCallback,'json');
				}
			});
		};
		window.SdelCallback = function(data){
			if(window.del_load!=null)
				window.del_load.hide();
			if(data!=null && data.status==true){
				topic.channel('list_todo').publish("list.refresh");
				topic.channel('list_toview').publish('list.refresh');
				topic.publish('portal.notify.refresh');
				dialog.success('<bean:message key="return.optSuccess" />');
			}else{
				dialog.failure('<bean:message key="return.optFailure" />');
			}
		};
		//切换标签
		window.switchNotifyTab = function(index){
			LUI('tabpanel').setSelectedIndex(index);
		};
		//审批等操作完成后，自动刷新列表
		topic.subscribe('successReloadPage', function() {
			topic.channel('list_todo').publish('list.refresh');
			topic.channel('list_toview').publish('list.refresh');
			topic.publish('portal.notify.refresh');
		});
		
	//add by wubing date:2016-02-24
	//设置星标
	window.doStar = function(star){
		var values = [];
		$("input[name='List_Selected']:checked").each(function(){
				values.push($(this).val());
			});
		if(values.length==0){
			dialog.alert('<bean:message key="page.noSelect"/>');
			return;
		}
		window.star_load = dialog.loading();
		$.post('<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=doStar"/>',
					$.param({"List_Selected":values ,"star":star},true),SstarCallback,'json');
	};
	window.doSingleStar = function(star,idValue){
		var values = [];
		values.push(idValue);
		window.star_load = dialog.loading();
		$.post('<c:url value="/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=doStar"/>',
					$.param({"List_Selected":values ,"star":star},true),SstarCallback,'json');
	};
	
	window.getCurPath = function(){
		var path = '/pending';
		var router = routerUtils.getRouter();
		if(router){
			var path = router.curRoute.path;
		}
		var starCon = '${starCon}';
		if(starCon){
			var index = LUI('notifyTabpanel').selectedIndex;
			if(index==0){
				path = '/pending';
			}
			if(index==1){
				path = '/unread';
			}
			if(index==2){
				path = '/processed';
			}
			if(index==3){
				path = '/read';
			}
		}
		return path;
	};
	window.SstarCallback = function(data){
		if(window.star_load!=null)
			window.star_load.hide();
		if(data!=null && data.status==true){
			var router = routerUtils.getRouter();
			var path = getCurPath();
			if(path.indexOf('pending')>-1){
				topic.channel('list_todo').publish("list.refresh");
			}else if(path.indexOf('unread')>-1){
				topic.channel('list_toview').publish("list.refresh");
			}else if(path.indexOf('processed')>-1){
				topic.channel('list_done').publish("list.refresh");
			}else if(path.indexOf('read')>-1){
				topic.channel('list_toview_done').publish("list.refresh");
			}else{
				topic.channel('list_todo').publish("list.refresh");
			}
			dialog.success('<bean:message key="return.optSuccess" />');
		}else{
			dialog.failure('<bean:message key="return.optFailure" />');
		}
	};

		window.onNotifyClick = function(obj,fdType,url){
			if(url){
				Com_OpenWindow(url);
			} else {
				var href = $(obj).data("href");
				if(href) {
					Com_OpenWindow(href);
				}
			}
			//待阅异步处理
			if(fdType=='2'){
				setTimeout(function(){
					topic.channel('list_toview').publish('list.refresh');
					topic.channel('list_toview_done').publish("list.refresh");
					topic.publish('portal.notify.refresh');
				},2000);
			}
		}
	});
</script>

