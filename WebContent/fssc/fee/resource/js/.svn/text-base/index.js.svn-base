seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lui/framework/router/router-utils','lui/toolbar','lang!fssc-fee','lang!'],
		function(Module, jquery, dialog, topic,spaConst,routerUtils ,toolbar,lang,comlang){
	
	var module = Module.find('fsscFee');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
				startpath : '/listAll',
				routes : [{
					path : '/listAll', //所有流程
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'fsscFeePanel',
							contents : {
								'fsscFeeContent' : { title : comlang['list.alldoc'], route:{ path: '/listAll' }, cri :{'mydoc':'all','j_path':'/listAll'} , selected : true }
							}
						}
					}
				},{
					path : '/listCreate', //我起草的
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'fsscFeePanel',
							contents : {
								'fsscFeeContent' : { title : comlang['list.create'], route:{ path: '/listCreate' }, cri :{'mydoc':'create','j_path':'/listCreate'} , selected : true }
							}
						}
					}
				},{
					path : '/listApproved', //我已审的
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'fsscFeePanel',
							contents : {
								'fsscFeeContent' : { title : comlang['list.approved'], route:{ path: '/listApproved' }, cri :{'mydoc':'approved','j_path':'/listApproved'} , selected : true }
							}
						}
					}
				},{
					path : '/listApproval', //待审我的
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'fsscFeePanel',
							contents : {
								'fsscFeeContent' : { title : comlang['list.approval'], route:{ path: '/listApproval' }, cri :{ 'mydoc':'approval','j_path':'/listApproval'} , selected : true }
							}
						}
					}
				},{
					path : '/recover', //回收站
					action : {
						type : 'pageopen',
						options : {
							url : $var.$contextPath + '/eop/basedata/resource/jsp/sysRecycleBox.jsp?modelName=com.landray.kmss.fssc.fee.model.FsscFeeMain',
							target : '_rIframe'
						}
					}
				},{
					path : '/listLedger',
					action : function(){
						openPage($var.$contextPath +'/fssc/fee/fssc_fee_main/fsscFeeMain_search.jsp');
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
