/**
 * 路由跳转设置
 */
seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lui/framework/router/router-utils','lui/toolbar','lui/util/env'],
		function(Module, jquery, dialog, topic,spaConst,routerUtils ,toolbar, env){
	
	var module = Module.find('appform');
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/form_base_design',
			routes : [{
				path : '/form_base_design', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/modelingAppModel.do?method=edit&fdId=' + $var.id + "&enableFlow=" + $var.enableFlow + "&appName=" + $var.appName + "&appIcon=" + $var.appIcon ,
						target : '_rIframe'
					}
				}
			},{
				path : '/form_base_cfg', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/form/cfgIndex.jsp?fdAppModelId=' + $var.id + "&enableFlow=" + $var.enableFlow,
						target : '_rIframe'
					}
				}
			},{
				path : '/mechanism',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/mechanism/cfgIndex.jsp?fdAppModelId=' + $var.id + "&enableFlow=" + $var.enableFlow,
						target : '_rIframe'
					}
				}
			},{
				path : '/relation_relation',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/relation/index.jsp?main=relation&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/relation_trigger',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/relation/index.jsp?main=trigger&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/relation_operation',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/relation/index.jsp?main=operation&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/views_business',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/view/index.jsp?main=business&fdModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/lbpm', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/form/flow/index.jsp?fdModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/view', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/view/index.jsp?main=listview&fdModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/listview_main', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/view/index.jsp?main=listview_main&fdModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/view_main', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/view/index.jsp?main=view_main&fdModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/portletView_main', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/view/index.jsp?main=portletView_main&fdModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/treeView_main',
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/base/view/index.jsp?main=treeView_main&fdModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/right', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/auth/index.jsp?main=form&hasFlow='+ ($var.enableFlow=='true'?'true':'false')+'&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/right_form', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/auth/index.jsp?main=right_form&hasFlow='+ ($var.enableFlow=='true'?'true':'false')+'&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/right_opr', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/auth/index.jsp?main=right_opr&hasFlow='+ ($var.enableFlow=='true'?'true':'false')+'&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/right_flow', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/auth/index.jsp?main=right_flow&hasFlow='+ ($var.enableFlow=='true'?'true':'false')+'&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			},{
				path : '/right_default', 
				action : {
					type : 'pageopen',
					options : {
						url : '/sys/modeling/auth/index.jsp?main=right_default&hasFlow='+ ($var.enableFlow=='true'?'true':'false')+'&fdAppModelId=' + $var.id,
						target : '_rIframe'
					}
				}
			}]
		});
		
	})
})