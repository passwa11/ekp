seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
		function($, strutil, dialog, topic, Module){
	
	var module = Module.find('kmArchives');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/myBorrow',
			routes : [
			        {
			        	path : '/myBorrow', //我的借阅
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmArchivesPanel',
								contents : {
									kmArchivesBorrowContent : { title : $lang.myBorrow, route:{ path: '/myBorrow' }, cri :{'mydoc':'create','j_path':'/myBorrow'} , selected : true }
								}
							}
						}
			   		},
					{
						path : '/myApproval', //待我审的
						action : function() {
							examineDoc('myApproval');
						}
					},
					{
						path : '/myApproved', //我已审的
						action : function() {
							examineDoc('myApproved');
						}
					},
			   		{
			        	path : '/allBorrow', //所有借阅
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmArchivesPanel',
								contents : {
									kmArchivesBorrowContent : { title : $lang.allBorrow, route:{ path: '/allBorrow' }, cri :{'mydoc':'all','j_path':'/allBorrow'} , selected : true }
								}
							}
						}
			   		},
			   		{
			        	path : '/listAll', 
			        	children: [{
			        		path : '/myCreate', // 我录入的
			        		action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmArchivesMyCreate : { title : "我录入的", route:{ path: '/listAll/myCreate' }, cri :{'j_path':'/listAll/myCreate'} , selected : true },
			        					kmArchivesMyDoc : { title : "档案录入", route:{ path: '/listAll/addArchives' }, cri :{'j_path':'/listAll/addArchives'}},
										kmArchivesMyDrafts : { title : $lang.myDrafts, route:{ path: '/listAll/myDrafts' }, cri :{'j_path':'/listAll/myDrafts'}}
									}
								}
							}
			        	},{
			        		path : '/addArchives', // 档案录入addArchives
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmArchivesMyCreate : { title : "我录入的", route:{ path: '/listAll/myCreate' }, cri :{'j_path':'/listAll/myCreate'}},
			        					kmArchivesMyDoc : { title : "档案录入", route:{ path: '/listAll/addArchives' }, cri :{'j_path':'/listAll/addArchives'} , selected : true },
										kmArchivesMyDrafts : { title : $lang.myDrafts, route:{ path: '/listAll/myDrafts' }, cri :{'j_path':'/listAll/myDrafts'}}
									}
								}
							}
			        	},{
			        		path : '/myDrafts', // 草稿箱
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmArchivesMyCreate : { title : "我录入的", route:{ path: '/listAll/myCreate' }, cri :{'j_path':'/listAll/myCreate'}},
			        					kmArchivesMyDoc : { title : "档案录入", route:{ path: '/listAll/addArchives' }, cri :{'j_path':'/listAll/addArchives'} },
										kmArchivesMyDrafts : { title : $lang.myDrafts, route:{ path: '/listAll/myDrafts' }, cri :{'j_path':'/listAll/myDrafts'} , selected : true}
									}
								}
							}
			        	}]
			   		},
			   		{
			        	path : '/examineDoc', //档案审核，待审/已审
			        	children: [{
			        		path : '/myApprovalDoc', // 待审
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										myApprovalContent : { title : $lang.myApproval, route:{ path: '/examineDoc/myApprovalDoc' }, cri :{'j_path':'/examineDoc', 'mydoc':'approval'} , selected : true },
										myApprovedContent : { title : $lang.myApproved, route:{ path: '/examineDoc/myApprovedDoc' }, cri :{'j_path':'/examineDoc', 'mydoc':'approved'} }
									}
								}
							}
			        	},{
			        		path : '/myApprovedDoc', // 已审
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										myApprovalContent : { title : $lang.myApproval, route:{ path: '/examineDoc/myApprovalDoc' }, cri :{'j_path':'/examineDoc', 'mydoc':'approval'} },
										myApprovedContent : { title : $lang.myApproved, route:{ path: '/examineDoc/myApprovedDoc' }, cri :{'j_path':'/examineDoc', 'mydoc':'approved'}, selected : true }
									}
								}
							}
			        	}]
			   		},
			   		{
			        	path : '/myBorrowDetails', //档案借阅
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmArchivesPanel',
								contents : {
									kmArchivesDetailsContent : { title : $lang.myBorrowDetails, route:{ path: '/myBorrowDetails' }, cri :{'mydoc':'all','j_path':'/myBorrowDetails'} , selected : true }
								}
							}
						}
			   		},
			   		{
			        	path : '/operation', //档案鉴定/销毁
			        	children: [{
			        		path : '/myAppraiseDetails', //档案鉴定
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmAppraiseContent : { title : $lang.myAppraiseDetails, route:{ path: '/operation/myAppraiseDetails' }, cri :{'j_path':'/operation/myAppraiseDetails'} , selected : true },
										kmDestroyContent : { title : $lang.myDestroyDetails, route:{ path: '/operation/myDestroyDetails' }, cri :{'j_path':'/operation/myDestroyDetails'} }
									}
								}
							}
			        	},{
			        		path : '/myDestroyDetails', // 档案销毁
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmAppraiseContent : { title : $lang.myAppraiseDetails, route:{ path: '/operation/myAppraiseDetails' }, cri :{'j_path':'/operation/myAppraiseDetails'} },
										kmDestroyContent : { title : $lang.myDestroyDetails, route:{ path: '/operation/myDestroyDetails' }, cri :{'j_path':'/operation/myDestroyDetails'}, selected : true }
									}
								}
							}
			        	}]
						
			   		},
			   		{
			        	path : '/record', //档案记录
			        	children:[{
			        		path : '/appraise', //鉴定记录
			        		action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmArchivesAppraiseContent : { title : $lang.appraise, route:{ path: '/record/appraise' }, cri :{'j_path':'/record/appraise'} , selected : true },
										kmArchivesDestroyContent : { title : $lang.destroy, route:{ path: '/record/destroy' }, cri :{'j_path':'/record/destroy'} }
									}
								}
							}
			        	},
			        	{
			        		path : '/destroy', //销毁记录
			        		action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmArchivesAppraiseContent : { title : $lang.appraise, route:{ path: '/record/appraise' }, cri :{'j_path':'/record/appraise'} },
										kmArchivesDestroyContent : { title : $lang.destroy, route:{ path: '/record/destroy' }, cri :{'j_path':'/record/destroy'} , selected : true }
									}
								}
							}
			        	}]
						
			   		},
			   		{
			        	path : '/allArchives', //档案库
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmArchivesPanel',
								contents : {
									kmArchivesMainContent : { title : $lang.allArchives, route:{ path: '/allArchives' }, cri :{'mydoc':'all','j_path':'/allArchives'} , selected : true }
								}
							}
						}
			   		},
			   		{
			        	path : '/preFileArchives', //预归档库
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmArchivesPanel',
								contents : {
									kmArchivesPreFileContent : { title : $lang.preFileArchives, route:{ path: '/preFileArchives' }, cri :{'docStatus':'10','j_path':'/preFileArchives'} , selected : true }
								}
							}
						}
			   		},
			   		{
						path : '/discard', //废弃
						children : [{
							path : '/borrow', //废弃的借阅申请
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmArchivesBorrowContent : { title : $lang.DiscardBorrow, route:{ path: '/discard/borrow' }, cri :{ 'docStatus':'00','j_path':'/discard/borrow' } , selected : true},
										kmArchivesMainContent : { title : $lang.DiscardArchives,route:{ path: '/discard/archives' } , cri :{ 'docStatus':'00','j_path':'/discard/archives' } }
									}
								}
							}
						},{
							path : '/archives', //废弃的档案
							action : {
								type : 'tabpanel',
								options : {
									panelId : 'kmArchivesPanel',
									contents : {
										kmArchivesBorrowContent : { title : $lang.DiscardBorrow, route:{ path: '/discard/borrow' }, cri :{ 'docStatus':'00','j_path':'/discard/borrow' } },
										kmArchivesMainContent : { title : $lang.DiscardArchives,route:{ path: '/discard/archives' } , cri :{ 'docStatus':'00','j_path':'/discard/archives' } , selected : true}
									}
								}
							}
						}]
					},
					{
						path : '/recover',
						action : function(){
							LUI.pageOpen( $var.$contextPath + '/km/archives/import/sysRecycleBox.jsp','_rIframe');
						}
					},
					{
						path : '/management', // 后台管理
						action : {
							type : 'pageopen',
							options : {
								url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/km/archives/tree.jsp',
								target : '_rIframe'
							}
						}
					}
			   ]
		});
		function createDoc(mainModelName, modelName){
			var url = $var.$contextPath+'/km/archives/km_archives_main/createDoc.jsp';
			url = Com_SetUrlParameter(url, 'mainModelName', mainModelName);
			url = Com_SetUrlParameter(url, 'modelName', modelName);
			url = Com_SetUrlParameter(url, 'isSimpleCategory', 'true');
			LUI.pageOpen(url, '_rIframe');
		};
		function examineDoc(type){
			var url = $var.$contextPath+'/km/archives/km_archives_main/examineDoc.jsp?type=' + type;
			LUI.pageOpen(url, '_rIframe');
		};
	});
});