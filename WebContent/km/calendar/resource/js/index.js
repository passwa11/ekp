seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
		function($, strutil, dialog, topic, Module){
	var module = Module.find('kmCalendar');
	
	module.controller(function($var,$router){
		//路由配置
		$router.define({
			routes : [
				{
					path : '/management',//后台管理
					action : function(){
						LUI.pageOpen( $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/km/calendar/tree.jsp','_rIframe');
					}
				}
	   		]
		});
		
	});
});