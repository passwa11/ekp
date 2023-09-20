seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lui/framework/router/router-utils','lui/toolbar','lang!fssc-expense'],
		function(Module, jquery, dialog, topic,spaConst,routerUtils ,toolbar,lang){
	
	var module = Module.find('fsscExpense');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/listCreate',
			routes : [{
				path : '/listAll', //所有流程
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'fsscExpensePanel',
						contents : {
							'fsscExpenseContent' : { title : $lang['allFlow'], route:{ path: '/listAll' }, cri :{'mydoc':'all','j_path':'/listAll'} , selected : true }
						}
					}
				}
			},{
				path : '/listCreate', //我起草的
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'fsscExpensePanel',
						contents : {
							'fsscExpenseContent' : { title : $lang['myCreate'], route:{ path: '/listCreate' }, cri :{'mydoc':'create','j_path':'/listCreate'} , selected : true }
						}
					}
				}
			},{
				path : '/listApproved', //我已审的
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'fsscExpensePanel',
						contents : {
							'fsscExpenseContent' : { title : $lang['myApproved'], route:{ path: '/listApproved' }, cri :{'mydoc':'approved','j_path':'/listApproved'} , selected : true }
						}
					}
				}
			},{
				path : '/listApproval', //待审我的
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'fsscExpensePanel',
						contents : {
							'fsscExpenseContent' : { title : $lang['myApproval'], route:{ path: '/listApproval' }, cri :{ 'mydoc':'approval','j_path':'/listApproval'} , selected : true }
						}
					}
				}
			},{
				path : '/listClaimant', //我的费用
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'fsscExpensePanel',
						contents : {
							'fsscExpenseContent' : { title : lang['fsExpenseMain.index.myExpense'], route:{ path: '/listClaimant' }, cri :{ 'mydoc':'fdClaimant','j_path':'/listClaimant'} , selected : true }
						}
					}
				}
			},{
				path : '/listShare',
				action : function(){
					openPage($var.$contextPath +'/fssc/expense/fssc_expense_share_main/index.jsp');
				}
			},{
				path : '/listBalance',
				action : function(){
					openPage($var.$contextPath +'/fssc/expense/fssc_expense_balance/index.jsp');
				}
			},{
				path : '/listDaiFuKuan', //所有流程
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'fsscExpensePanel',
						contents : {
							'fsscExpenseContent' : { title : $lang['DaiFuKuan'], route:{ path: '/listDaiFuKuan' }, cri :{'fdPaymentStatus':'10','j_path':'/listDaiFuKuan'} , selected : true }
						}
					}
				}
			},{
				path : '/listYiFuKuan', //所有流程
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'fsscExpensePanel',
						contents : {
							'fsscExpenseContent' : { title : $lang['YiFuKuan'], route:{ path: '/listYiFuKuan' }, cri :{'fdPaymentStatus':'30','j_path':'/listYiFuKuan'} , selected : true }
						}
					}
				}
			},{
				path : '/listChuNa',
				action : function(){
					openPage($var.$contextPath +'/fssc/cashier/fssc_cashier_payment/index.jsp');
				}
			},{
				path : '/listBaoXiaoTaiZhang',
				action : function(){
					openPage($var.$contextPath +'/fssc/expense/fssc_expense_main/fsscExpenseMain_search.jsp');
				}
			},{
				path : '/listJinXiangTaiZhang',
				action : function(){
					openPage($var.$contextPath +'/fssc/cashier/fssc_cashier_payment/index.jsp');
				}
			},{
				path : '/recover', //回收站
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/eop/basedata/resource/jsp/sysRecycleBox.jsp?modelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain;com.landray.kmss.fssc.expense.model.FsscExpenseBalance;com.landray.kmss.fssc.expense.model.FsscExpenseShareMain',
						target : '_rIframe'
					}
				}
			}]
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
