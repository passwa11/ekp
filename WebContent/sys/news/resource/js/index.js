seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module','lui/spa/Spa','lui/toolbar', 'lui/spa/const'],
		function($, strutil, dialog, topic, Module, Spa,toolbar,spaConst){
	var module = Module.find('sysNews');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		$router.define({
			startpath : '/allNews',
			routes : [{
				path : '/allNews', //所有新闻
				action : {
					type : 'content',
					options : {
						cri : {'cri.q':'docStatus:30',"j_path":"/allNews"}
					}
				}
			},{
				path : '/topNews', //置顶新闻
				action : {
					type : 'content',
					options : {
						cri : {'cri.q':'docStatus:30;fdIsTop:1','j_path':"/topNews"}
					}
				}
			},{
				path : '/approval', //待我审的
				action : {
					type : 'content',
					options : {
						cri : {'mydoc':'approval,'+$lang.approvalNews,'j_path':"/approval"}
					}
				}
				
			},{
				path : '/approved', //我已审的
				action : {
					type : 'content',
					options : {
						cri : {'mydoc':'approved,'+$lang.approvedNews,'j_path':"/approved"}
					}
				}
				
			},{
				path : '/myNews', //我提交的
				action : {
					type : 'content',
					options : {
						cri : {'mydoc':'create,'+$lang.myNews,'j_path':"/myNews"}
					}
				}
				
			},
			{
				path : '/recover',
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.sys.news.model.SysNewsMain',
						target : '_rIframe'
					}
				}
			},
			{
				path : '/management',
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/sys/news/tree.jsp',
						target : '_rIframe'
					}
				}
			}
			]
		});
			
		 window.sysNewsMain = {
					open : function(key, value) {

						Spa.spa.setValue(key, value);
					},
					clear : function() {

						Spa.spa.clear();
					}

				};

		 
		 	topic.subscribe(spaConst.SPA_CHANGE_VALUES, function(evt) {
		 		var path = Spa.spa.getValue("j_path");
				if(path == '/allNews') {
					if(LUI('setTop')){LUI('setTop').setVisible(true);}
					if(LUI('unSetTop')){LUI('unSetTop').setVisible(true);}
					if(LUI('unPublish')){LUI('unPublish').setVisible(true);}
				}
			});
		 
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				topic.publish('list.refresh');
			});
			
			//新建
			$function.addDoc = function() {
				var docCategory = Spa.spa.getValue('docCategory') || '';
					dialog.simpleCategoryForNewFile(
							'com.landray.kmss.sys.news.model.SysNewsTemplate',
							'/sys/news/sys_news_main/sysNewsMain.do?method=add&fdTemplateId=!{id}&.fdTemplate=!{id}&i.docTemplate=!{id}',false,null,null,docCategory);
			};
			//删除
			$function.delDoc = function(){
				var values = [];
				$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				if(values.length==0){
					dialog.alert($lang.pageNoSelect);
					return;
				}
				var docCategory = Spa.spa.getValue('docCategory') || '';
				var config = {
						url : $var.$contextPath+'/sys/news/sys_news_main/sysNewsMain.do?method=deleteall&categoryId='+docCategory, // 删除数据的URL
						data : $.param({"List_Selected":values},true), // 要删除的数据
						modelName : "com.landray.kmss.sys.news.model.SysNewsMain" // 主要是判断此文档是否有部署软删除
					};
				// 通用删除方法
				Com_Delete(config, delCallback);
			};
		
			LUI.ready(function(){
				/*if(LUI('setTop')){LUI('setTop').setVisible(false);	}
				if(LUI('unSetTop')){LUI('unSetTop').setVisible(false);}
				if(LUI('publish')){	LUI('publish').setVisible(false);	}
				if(LUI('unPublish')){LUI('unPublish').setVisible(false);}*/
				//LUI('criteria1').setValue('docStatus', '30');
			//	setTimeout("initMenuNav()", 300);
			});
			
			$function.initMenuNav = function(){
				 var fdIsTop = getValueByHash("fdIsTop");
		 		 var key = getValueByHash("mydoc");
		 		 if(fdIsTop == "1") {
		 			key="fdIsTop";
		 		 }else if(!key) {
		 			key="all";
		 		 }
		 		resetMenuNavStyle($("#_"+key));
			 };
			 
				function showButtons(checkUrl,key,btn){
					var data = new Array();
					data.push([key,checkUrl]);
					 $.ajax({
		       			  url: $var.$contextPath+"/sys/authorization/SysAuthUrlCheckAction.do?method=checkUrlAuth",
		       			  dataType:"json",
		       			  type:"post",
		       			  data:{"data":LUI.stringify(data)},
		       			  async:false,
		       			  success: function(rtn){
			       		 		for(var i=0;i<rtn.length;i++){
				                  if(rtn[i][key] == 'true'){
				                	  if(LUI(btn)){LUI(btn).setVisible(true);}
				                   }
			  		            }
		       			  }
		       		  });
				};
			 
			 topic.subscribe('criteria.spa.changed',function(evt){
					if(LUI('setTop')){LUI('setTop').setVisible(false);}
					if(LUI('unSetTop')){LUI('unSetTop').setVisible(false);}
					if(LUI('publish')){LUI('publish').setVisible(false);}
					if(LUI('unPublish')){LUI('unPublish').setVisible(false);}
					var categoryId = Spa.spa.getValue('docCategory') || '';
					//var checkDelUrl = "/sys/news/sys_news_main/sysNewsMain.do?method=deleteall&status="+status+"&categoryId="+categoryId+"&nodeType="+nodeType;
					
					if(evt['criterions'].length>0){
                   for(var i=0;i<evt['criterions'].length;i++){
                       //发布状态显示【置顶】、【取消发布】按钮
                 	  if(evt['criterions'][i].key=="docStatus"){
							if(evt['criterions'][i].value.length==1){
								if(evt['criterions'][i].value[0]=="30"){
									var checkTopUrl = "/sys/news/sys_news_main/sysNewsMain.do?method=setTop&status=30&categoryId="+categoryId;
									var checkPubUrl = "/sys/news/sys_news_main/sysNewsMain.do?method=setPublish&status=30&categoryId="+categoryId;
									showButtons(checkTopUrl,'topBtn','setTop');
									showButtons(checkTopUrl,'topBtn','unSetTop');
									showButtons(checkPubUrl,'pubBtn','unPublish');
								}
								if(evt['criterions'][i].value[0]=="40"){
									var checkPubUrl = "/sys/news/sys_news_main/sysNewsMain.do?method=setPublish&status=40&categoryId="+categoryId;
									showButtons(checkPubUrl,'pubBtn','publish');
								}							
							}
					       }
                       //置顶查询显示【取消置顶】按钮
                       if(evt['criterions'][i].key=="fdIsTop"){
 							if(evt['criterions'][i].value.length==1){
 								if(evt['criterions'][i].value[0]=="1"){
 									var checkTopUrl = "/sys/news/sys_news_main/sysNewsMain.do?method=setTop&status=1&categoryId="+categoryId;
 									showButtons(checkTopUrl,'topBtn','setTop');
 									showButtons(checkTopUrl,'topBtn','unSetTop');
 								}
 							}
 					   }
 						
 						
                   }					
				  }
				});

			 $function.setTop=function(isTop){
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				    if(values.length==0){
					dialog.alert($lang.pageNoSelect);
					return;
				   }
				  var days=null;
					if(isTop){
					dialog.iframe("/sys/news/sys_news_main/sysNewsMain_topday.jsp",$lang.topTime,function (value){
                          days=value;
                      	if(days==null){
								return;
							}else{
											window.del_load = dialog.loading();
									//		$.post('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setTop"/>',
									//				$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days},true),topCallback,'json');	
											$.ajax({
												url: $var.$contextPath+'/sys/news/sys_news_main/sysNewsMain.do?method=setTop',
												type: 'POST',
												data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days,categoryId:'${JsParam.categoryId}'},true),
												dataType: 'json',
												error: function(data){
													if(window.del_load!=null){
														window.del_load.hide(); 
													}
													dialog.failure($lang.optFailure);
												},
												success: topCallback
											});									
							}
							},{width:400,height : 200});
					}else{		
						    days=0;		
						    dialog.confirm($lang.setTopConfirmCancel,function(value){
							if(value==true){
								window.del_load = dialog.loading();
							//	$.post('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setTop"/>',
							//			$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days},true),topCallback,'json');
								$.ajax({
									url: $var.$contextPath+'/sys/news/sys_news_main/sysNewsMain.do?method=setTop',
									type: 'POST',
									data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days,categoryId:'${JsParam.categoryId}'},true),
									dataType: 'json',
									error: function(data){
										if(window.del_load!=null){
											window.del_load.hide(); 
										}
										dialog.failure($lang.optFailure);
									},
									success: topCallback
								});		
							}
						});					
					}
				};
				
				$function.op=function(optype) {
					var values = [];
					$("input[name='List_Selected']:checked").each(function(){
						values.push($(this).val());
					});
				    if(values.length==0){
					   dialog.alert($lang.pageNoSelect);
					   return;
				    }
                 //取消发布确认框
					if(optype==false){
						dialog.confirm($lang.confirmCancel,function(value){
							if(value==true){
								window.del_load = dialog.loading();
								$.post($var.$contextPath+'/sys/news/sys_news_main/sysNewsMain.do?method=setPublish',
										$.param({"List_Selected":values,"op":optype,categoryId:'${JsParam.categoryId}'},true),publishCallback,'json')
										.error(function(data){
											if(window.del_load!=null){
												window.del_load.hide(); 
											}
											dialog.failure($lang.optFailure);
										 });		
							}
						});
				    }else{
						window.del_load = dialog.loading();
						$.post($var.$contextPath+'/sys/news/sys_news_main/sysNewsMain.do?method=setPublish',
								$.param({"List_Selected":values,"op":optype,categoryId:'${JsParam.categoryId}'},true),publishCallback,'json')
								.error(function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.failure($lang.optFailure);
								 });		
				    }	  
				};


            //置顶、取消置顶回调
				function topCallback (data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success($lang.optSuccess);
					}else{
						dialog.failure($lang.optFailure);
					}
				};

           //发布、取消发布回调
				function publishCallback(data){
					if(window.del_load!=null)
						window.del_load.hide();
					if(data!=null && data.status==true){
						topic.publish("list.refresh");
						dialog.success($lang.optSuccess);
					}else{
						dialog.failure($lang.optFailure);
					}
				};

			//删除回调	
				function delCallback(data){
				if(window.del_load!=null)
					window.del_load.hide();
				if(data!=null && data.status==true){
					topic.publish("list.refresh");
					dialog.success($lang.optSuccess);
				}else{
					dialog.failure($lang.optFailure);
				}
			};
		 
		});
		 
			
		

});
