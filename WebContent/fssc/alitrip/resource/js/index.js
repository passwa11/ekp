seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lui/framework/router/router-utils','lui/toolbar','lang!fssc-fee','lang!'],
		function(Module, jquery, dialog, topic,spaConst,routerUtils ,toolbar,lang,comlang){
	
	var module = Module.find('fsscAlitrip');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
				startpath : '/listOrder',
				routes : [{
					path : '/listOrder',
					action : function(){
						openPage($var.$contextPath +'/fssc/alitrip/fssc_alitrip_order/index.jsp');
					}
				},
				{
					path : '/listLog',
					action : function(){
						openPage($var.$contextPath +'/fssc/alitrip/fssc_alitrip_log/index.jsp');
					}
				}
				]
		});
		
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			setTimeout(function(){topic.publish('list.refresh');
			},1000);
		});
		
		
 		$function.getFromHash = function(key){
 			var params = window.location.hash ? window.location.hash.substr(1)
 					.split("&") : [], paramsObject = {};
 					for (var i = 0; i < params.length; i++) {
 						if (!params[i])
 							continue;
 						var a = params[i].split("=");
 						if(a[0] == key){
 							return decodeURIComponent(a[1]);
 						}
 					}
 					return "";
 		};
		
		//查询
		function openSearch(url){
			LUI.pageOpen($var.$contextPath+url, '_rIframe');
		}
		
	});
});
