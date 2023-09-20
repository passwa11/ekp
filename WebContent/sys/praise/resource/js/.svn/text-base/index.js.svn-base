seajs.use([ 'lui/framework/module' ], function(Module) {
	Module.install('sysPraise', {
		//模块变量
		$var : {},
		//模块多语言
		$lang : {},
		//搜索标识符
		$search : ''
	});
});

var sysPraise = {
	navOpenPage : function(url, target) {
		var view = LUI.getPageView();

		if (view) {

			view.open(url, '_rIframe', {
				transition : 'fadeIn',
				history : true
			});

		} else {
			if(target){
				window.open(url, '_self');
			} else {
				LUI.pageOpen(url,'_rIframe');
			}

		}

	}
}

seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
		function($, strutil, dialog, topic, Module){
			var module = Module.find('sysPraise');

			module.controller(function($var,$router){
				//路由配置
				$router.define({
					routes : [
						{
							path : '/management',//后台管理
							action : function(){
								sysPraise.navOpenPage($var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/sys/praise/tree.jsp');
							}
						}
					]
				});
			});
		}
);