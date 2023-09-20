seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lui/spa/const','lang!km-smissive'],
		function(Module, jquery, dialog, topic ,spaConst,lang){
	
	var module = Module.find('kmSmissive');
	
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
						type : 'content',
						options : {
							cri : {'j_path':'/listAll','cri.q':'docStatus:30'}
						}
	
					}
				},{
					path : '/listCreate', //我起草的
					action : {
						type : 'content',
						options : {
							cri : {
								mydoc : "create," + lang['smissive.create.my'],
								'j_path':'/listCreate'
							}
						}

					}
				},{
					path : '/listApproved', //我已审的
					action : {
						type : 'content',
						options : {
							cri : {
								mydoc : "approved,"+ lang['smissive.approved.my'],
								'j_path':'/listApproved'
							}
						}

					}
				},{
					path : '/listApproval', //待审我的
					action : {
						type : 'content',
						options : {
							cri : {
								mydoc : "approval,"+ lang['smissive.approval.my'],
								'j_path':'/listApproval'
							}
						}

					}
				},{
					path : '/recover', //回收站
					action : {
						type : 'pageopen',
						options : {
							url : $var.$contextPath + '/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.km.smissive.model.KmSmissiveMain',
							target : '_rIframe'
						}
					}
				},{
					path : '/management', // 后台管理
					action : {
						type : 'pageopen',
						options : {
							url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/km/smissive/tree.jsp',
							target : '_rIframe'
						}
					}
				}]
			});
			
			topic.subscribe(spaConst.SPA_CHANGE_VALUES, function(evt) {
				LUI.pageHide('_rIframe');
			});
			
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
			//新建
			$function.addDoc = function() {
					dialog.simpleCategoryForNewFile(
							'com.landray.kmss.km.smissive.model.KmSmissiveTemplate',
							'/km/smissive/km_smissive_main/kmSmissiveMain.do?method=add&categoryId=!{id}',false,null,null,null);
			};
			//删除
			$function.delDoc = function(){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				if(values.length==0){
					dialog.alert($lang['pageNoSelect']);
					return;
				}
				var url= $var.$contextPath + '/km/smissive/km_smissive_main/kmSmissiveMain.do?method=deleteall'
					
				var config = {
						url : url, // 删除数据的URL
						data : $.param({"List_Selected":values},true), // 要删除的数据
						modelName : "com.landray.kmss.km.smissive.model.KmSmissiveMain"
				};
				function delCallback(data){
					topic.publish("list.refresh");
					dialog.result(data);
				};
				// 通用删除方法
				Com_Delete(config, delCallback);
			};
	});
});