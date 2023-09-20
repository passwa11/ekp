<%@ page language="java" pageEncoding="UTF-8"
	import="com.landray.kmss.sys.notify.service.ISysNotifyCategoryService,com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="net.sf.json.JSONArray,net.sf.json.JSONObject,java.util.List" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<% 
	//提前获取业务聚合分类
	ISysNotifyCategoryService sysNotifyCategoryService = (ISysNotifyCategoryService) SpringBeanUtil
		.getBean("sysNotifyCategoryService");
	java.util.List cate = sysNotifyCategoryService.getCategorys();
	request.setAttribute("cateList",cate);
	JSONArray array = new JSONArray();
	for (int i = 0; i < cate.size(); i++) {
		Object[] obj = (Object[]) cate.get(i);
		JSONObject json = new JSONObject();
		json.put("text", obj[1]);
		json.put("value", obj[0]);
		array.add(json);
	}
	request.setAttribute("cateJsonArr",array.toString());
%>

<template:include ref="person.home">
	<template:replace name="title">
		<c:out value="${ lfn:message('sys-notify:table.sysNotifyTodo') }"></c:out>
	</template:replace>
	<template:replace name="content">
	<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/notify/resource/css/notify.css"/>
	<div style="width:100%">
	   <ui:tabpanel layout="sys.ui.tabpanel.list" id="tabpanel">
		 	 <%@ include file="/sys/notify/sys_notify_todo_ui/index_todo.jsp"%>
			  <%@ include file="/sys/notify/sys_notify_todo_ui/index_toview.jsp"%>
			  <%@ include file="/sys/notify/sys_notify_todo_ui/index_todo_done.jsp"%>
			  <%@ include file="/sys/notify/sys_notify_todo_ui/index_toview_done.jsp"%>
			  <%@ include file="/sys/lbpmperson/person_flow_approval/approval_index_todo.jsp"%>
			  <script type="text/javascript">
					seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
						LUI.ready(function(){
							var dataType = "${JsParam.dataType}";
							var done = dataType && dataType.indexOf('done')!=-1;
							var index = 0;
							if(done){;//兼容处理 dataType:todo,toview,tododone,toviewdone,done,todo;toview,tododone;toviewdone
								index = dataType=='toviewdone' ? 3:2;
							}else{
								index = dataType=='toview' ? 1:0;
							}
							if(dataType=='fast'){
								index=4;
							}
							LUI('tabpanel').selectedIndex=index;
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
					window.SstarCallback = function(data){
						if(window.star_load!=null)
							window.star_load.hide();
						if(data!=null && data.status==true){
							var index = LUI('tabpanel').selectedIndex;
							if(index==0){
								topic.channel('list_todo').publish("list.refresh");
							}else if(index==1){
								topic.channel('list_toview').publish("list.refresh");
							}else if(index==2){
								topic.channel('list_done').publish("list.refresh");
							}else if(index==3){
								topic.channel('list_toview_done').publish("list.refresh");
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
									topic.publish('portal.notify.refresh');
								},2000);
							}
						}
					});
				</script>
		</ui:tabpanel>
	  </div> 
	</template:replace>
</template:include>
