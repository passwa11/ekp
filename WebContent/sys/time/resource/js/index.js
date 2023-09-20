seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lang!sys-time','lui/framework/router/router-utils'],
		function(Module, jquery, dialog, topic, lang, routerUtils){
	
	var module = Module.find('sysTime');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		
		//路由配置
		$router.define({
			startpath : '/leaveAmount',
			routes : [
			{
				path : '/leaveAmount',
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'leavePanel',
						contents : {
							'leaveAmountContent' : { title : lang['sysTimeLeaveRule.leaveAmount'], route:{ path: '/leaveAmount' }, selected : true }
						}
					}
				}
			},
			{
				path : '/leaveDetail',
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'leavePanel',
						contents : {
							'leaveDetailContent' : { title : lang['sysTimeLeaveRule.leaveDetail'], route:{ path: '/leaveDetail' }, selected : true }
						}
					}
				}
			},
			{
				path : '/background', 
				action :  {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/profile/index.jsp#lbpm/timeArea',
						target : '_blank'
					}
				}
			},
			{
				path : '/management', // 后台管理
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/sys/time/tree.jsp',
						target : '_rIframe'
					}
				}
			}
			],
		});
		
	});
	
});