seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
		function($, strutil, dialog, topic, Module){
	var module = Module.find('sysNotify');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		var pathParam = window.location.hash; // 获取hash参数，用于传递到右侧IFrame内容页
		var isFirstLoad = true;
		
		//路由配置
		$router.define({
			startpath : '/process',
			routes : [
			        {
			        	path : '/process', //处理类
						action:function(){
							openPage($var.$contextPath +'/sys/notify/sys_notify_todo_ui/index_process.jsp'+(isFirstLoad?pathParam:''));
							isFirstLoad=false;
						}
			   		},{
			        	path : '/read', //阅读类
						action:function(){
							openPage($var.$contextPath +'/sys/notify/sys_notify_todo_ui/index_read.jsp'+(isFirstLoad?pathParam:''));
							isFirstLoad=false;
						}
			   		},{
						path : '/system', //系统通知类
						action:function(){
							openPage($var.$contextPath +'/sys/notify/sys_notify_todo_ui/index_system.jsp'+(isFirstLoad?pathParam:''));
							isFirstLoad=false;
						}
					},{
						path : '/fastview', //快速审批
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'notifyTabpanel',
								contents : {
									'notifyFastContent' : {route:{ path: '/fastview'} ,cri :{'j_path':'/fastview'}, selected : true }
								}
							}
						}
					},{
						path : '/star', //星标待办
						action:function(){
							openPage($var.$contextPath +'/sys/notify/sys_notify_todo_ui/index_star.jsp');
						}
					}
			   ]
		});
	});
});