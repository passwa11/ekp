/**
 * 路由跳转设置
 */
seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lui/framework/router/router-utils','lui/toolbar','lui/util/env'],
		function(Module, jquery, dialog, topic,spaConst,routerUtils ,toolbar, env){
	
	var module = Module.find('modelingApp');
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		
		//路由配置
		$router.define({
			startpath : '/form',
			routes : [{
				path : '/baseinfo', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/modelingApplication.do?method=edit&fdId=' + $var.appId,
						target : '_rIframe'
					}
				}
			},{
				path : '/form', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/modelingAppModel.do?method=editForm&fdAppId='+$var.appId,
						target : '_rIframe'
					}
				}
			},{
				path : '/space',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/space/index.jsp?fdAppId=' + $var.appId,
						target : '_rIframe'
					}
				}
			}/*,{
				path : '/menu',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/profile/nav/index.jsp?fdAppId=' + $var.appId,
						target : '_rIframe'
					}
				}
			}*/,{
				path : '/report',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/rpt_tree.jsp?fdId=' + $var.appId,
						target : '_rIframe'
					}
				}
			}/*,{
				path : '/mobile',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/mobile/index.jsp?fdAppId=' + $var.appId,
						target : '_rIframe'
					}
				}
			}*/,{
				path : '/rpt',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/rpt/index.jsp?fdAppId=' + $var.appId,
						target : '_rIframe'
					}
				}
			}]
		});
		
	})
})