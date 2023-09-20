seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
		function($, strutil, dialog, topic, Module){
	var module = Module.find('kmSignature');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/listAll',
			routes : [{
				path : '/listAll', //所有签章
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmSignaturePanel',
						contents : {
							kmSignatureContent : {title : $lang['allSig'], route : {path : '/listAll'},cri : {'cri.q' : 'fdIsAvailable:1'}, selected : true}
						}
					}
				}
			},{
				path : '/listCreate', //我创建的
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmSignaturePanel',
						contents : {
							kmSignatureContent : {title : $lang['createdByMe'], route : {path : '/listCreate'},cri : {'cri.q' : 'fdIsAvailable:1','mydoc' : 'create'}, selected : true}
						}
					}
				}
			},{
				path : '/listAuthorize', //授权我的
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmSignaturePanel',
						contents : {
							kmSignatureContent : {title : $lang['authorizeMe'], route : {path : '/listAuthorize'},cri : {'cri.q' : 'fdIsAvailable:1','mydoc' : 'authorize'}, selected : true}
						}
					}
				}
			},{
				path : '/management', // 后台管理
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/km/signature/tree.jsp',
						target : '_rIframe'
					}
				}
			}]
		});
		// 监听新建更新等成功后刷新
		topic.subscribe('successReloadPage', function() {
			topic.publish('list.refresh');
		});
		//控制批量置为无效按钮的显示
		topic.subscribe('criteria.spa.changed',function(evt){
			//按钮对象存在则先隐藏
			if(LUI('fdAvailable')){
				LUI('fdAvailable').setVisible(false);
			}
		    if(evt['criterions'].length>0){
				for(var i=0;i<evt['criterions'].length;i++){
					//控制批量领取和批量归还按钮的显示
					if(evt['criterions'][i].key == "fdIsAvailable"){
						if(evt['criterions'][i].value.length == 1){
							if(evt['criterions'][i].value[0] == "1"){
								 LUI('fdAvailable').setVisible(true);
							}
						}
					}
				}
			}
		});

		//新建
		$function.addDoc = function() {
			Com_OpenWindow(Com_Parameter.ContextPath+'km/signature/km_signature_main/kmSignatureMain.do?method=add&docType='+$var['docType'],'_blank');
		};

		//批量置为无效
		$function.invalidatedAll = function(){
			var values = [];
			$("input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
			if(values.length == 0){
				dialog.alert($lang['pageNoSelect']);
				return;
			}
			dialog.confirm($lang['fdIsAvailableConfirm'],function(flag){
				if(flag==true){
					ids = values.join(";");
					window.del_load = dialog.loading();
					$.post(Com_Parameter.ContextPath+'km/signature/km_signature_main/kmSignatureMain.do?method=invalidatedAll',
							$.param({"List_Selected":ids}),delCallback,'json');
				}
			});
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
			dialog.confirm($lang['comfirmDelete'],function(value){
				if(value==true){
					window.del_load = dialog.loading();
					$.post(Com_Parameter.ContextPath+'km/signature/km_signature_main/kmSignatureMain.do?method=deleteall&categoryId='+$var['categoryId'],
							$.param({"List_Selected":values},true),delCallback,'json');
				}
			});
		};
		
		//删除回调函数
		window.delCallback = function(data){
			if(window.del_load!=null)
				window.del_load.hide();
			if(data!=null && data.status==true){
				topic.publish("list.refresh");
				dialog.success($lang['optSuccess']);
			}else{
				dialog.failure($lang['optFailure']);
			}
		};
		
	});
	
});