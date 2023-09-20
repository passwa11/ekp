seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
		function($, strutil, dialog, topic, Module){
	var module = Module.find('kmComminfo');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
						startpath : '/all',
						routes : [ {
							path : '/all', //所有文档
							action : {
								type : 'content',
								options : {
									cri : {'j_path':'/all'}
								}
							}
						}, {
							path : '/management', // 后台管理
							action : {
								type : 'pageopen',
								options : {
									url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/km/comminfo/tree_config.jsp',
									target : '_rIframe'
								}
							}
						}
			          ]
		});
	});
});