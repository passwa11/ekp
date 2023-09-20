seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lui/framework/router/router-utils','lui/toolbar','lang!fssc-budget'],
		function(Module, $, dialog, topic,spaConst,routerUtils ,toolbar,lang){
	
	var module = Module.find('fsscBudget');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/budgetMain',
			routes : [
			          {
			        	  path : '/budgetMain',
							action : function(){
								openPage($var.$contextPath +'/fssc/budget/fssc_budget_main/index.jsp?fdSchemeId='+$var.fdSchemeId);
							}
			          },
			          {
						path : '/budgetAdjust', 
						action : function(){
							openPage($var.$contextPath +'/fssc/budget/fssc_budget_adjust_main/index.jsp?fdSchemeId='+$var.fdSchemeId);
						}
			          },
			          {
						path : '/budgetData', 
						action : function(){
							openPage($var.$contextPath +'/fssc/budget/fssc_budget_data/index.jsp?fdSchemeId='+$var.fdSchemeId);
						}
				      },
			          {
						path : '/executeLedger', 
						action : function(){
							openPage($var.$contextPath +'/fssc/budget/fssc_budget_report/fsscBudgetExecuteLedger_search.jsp');
						}
				      },
				      {
							path : '/countReport', 
							action : function(){
								openPage($var.$contextPath +'/fssc/budget/fssc_budget_report/fsscBudgetCountReport_search.jsp');
							}
					      },
			          {
						path : '/transferBudget', 
						action : function(){
							openPage($var.$contextPath +'/fssc/budget/resource/jsp/fsscBudgetTransfer.jsp');
						}
				      },
			          {
						path : '/initImportBudget', 
						action : function(){
							openPage($var.$contextPath +'/fssc/budget/resource/jsp/fsscBudgetInitImport.jsp');
						}
				      },
				      {
							path : '/recover', //回收站
							action : {
								type : 'pageopen',
								options : {
									url : $var.$contextPath + '/eop/basedata/resource/jsp/sysRecycleBox.jsp?modelName=com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain',
									target : '_rIframe'
								}
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
	});
});
