
seajs.use(['lui/framework/module', 'lui/jquery', 'lui/dialog', 'lui/topic', 'lang!hr-ratify', 'lui/util/env', 'lui/framework/router/router-utils'],
    function(Module, $, dialog, topic, lang, env, routerUtils) {

        var module = Module.find('hrRatify');

        /**
         * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
         * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
         */
        module.controller(function($var, $lang, $function, $router) {
            //路由配置
            $router.define({
                startpath: '/mainCriterionMydoc0',
                routes: [

                    {
                        path: '/mainCriterionMydoc0',
                        action: {
                            type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifyMainContent': {
                                    	title : $lang['mainCriterionMydoc0'],
                                    	route: {
                                            path: '/mainCriterionMydoc0'
                                        },
                                        cri: {
                                            'j_path' : '/mainCriterionMydoc0',
                                        	'mydoc': 'create'
                                        },
                                        selected: true
                                    }
                                }
                            }
                        }
                    }, {
                        path: '/mainCriterionMydoc1',
                        action: {
                            type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifyMainContent': {
                                    	title : $lang['mainCriterionMydoc1'],
                                    	route: {
                                            path: '/mainCriterionMydoc1'
                                        },
                                        cri: {
                                        	'j_path' : '/mainCriterionMydoc1',
                                        	'mydoc': 'approval'
                                        },
                                        selected: true
                                    }
                                }
                            }
                        }
                    }, {
                        path: '/mainCriterionMydoc2',
                        action: {
                            type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifyMainContent': {
                                    	title : $lang['mainCriterionMydoc2'],
                                    	route: {
                                    		path: '/mainCriterionMydoc2'
                                        },
                                        cri: {
                                        	'j_path' : '/mainCriterionMydoc2',
                                        	'mydoc': 'approved'
                                        },
                                        selected: true
                                    }
                                }
                            }
                        }
                    }, {
                        path: '/custome0',
                        action: {
                            type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents : {
                                	'HrRatifyCustomeContent' : {
                                		title : $lang['custom0'],
                                		route : {
                                			path : '/custome0'
                                		},
                                		cri: {
                                			'j_path' : '/custome0'
                                		},
                                		selected : true
                                	}
                                }
                            }
                        }
                    }, {
                        path: '/main',
                        action: {
                        	type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifyMainContent': {
                                    	title : $lang['main'],
                                    	route: {
                                            path: '/main'
                                        },
                                        cri: {
                                        	'j_path' : '/main',
                                        	'mydoc': 'all'
                                        },
                                        selected: true
                                    }
                                }
                            }
                        }
                    }, {
                        path: '/custome1',
                        action: {
                            type: 'pageopen',
                            options: {
                                url: $var.$contextPath + '/hr/ratify/hr_ratify_main/createDoc.jsp?isSimpleCategory=false&mainModelName=com.landray.kmss.hr.ratify.model.HrRatifyMain&modelName=com.landray.kmss.hr.ratify.model.HrRatifyTemplate',
                                target: '_rIframe'
                            }
                        }
                    }, {
                    	path : '/follow',
                    	action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyMainContent' : { 
        								title : $lang['follow'], 
        								route: { 
        									path: '/follow'
        								}, 
        								cri: { 
        									'j_path':'/follow',
        									'doctype':'follow'
        								}, 
        								selected : true 
        							}
        						}
        					}
        				}
        			}, {
        				path : '/feedback', //流程反馈
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyMainContent' : { 
        								title : $lang['feedback'], 
        								route : { 
        									path: '/feedback' 
        								}, 
        								cri : { 
        									'j_path':'/feedback',
        									'doctype':'feedback',
        									'cri.q':'feedStatus:41'
        								} , 
        								selected : true 
        							}
        						}
        					}
        				}
        			}, {
        				path : '/search1', //员工入职流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchEntryContent': {
                                    	route: {
                                            path: '/search1'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search2', //员工转正流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchPositiveContent': {
                                    	route: {
                                            path: '/search2'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search3', //员工调岗流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchTransferContent': {
                                    	route: {
                                            path: '/search3'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search4', //员工离职流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchLeaveContent': {
                                    	route: {
                                            path: '/search4'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search5', //员工解聘流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchFireContent': {
                                    	route: {
                                            path: '/search5'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search6', //员工退休流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchRetireContent': {
                                    	route: {
                                            path: '/search6'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search7', //员工返聘流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchRehireContent': {
                                    	route: {
                                            path: '/search7'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search8', //员工调薪流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchSalaryContent': {
                                    	route: {
                                            path: '/search8'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search9', //合同签订流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchSignContent': {
                                    	route: {
                                            path: '/search9'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search10', //合同变更流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchChangeContent': {
                                    	route: {
                                            path: '/search10'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search11', //合同解除流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchRemoveContent': {
                                    	route: {
                                            path: '/search11'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			}, {
        				path : '/search12', //其他流程查询
        				action : {
        					type: 'tabpanel',
                            options: {
                                panelId: 'hrRatifyPanel',
                                contents: {
                                    'HrRatifySearchOtherContent': {
                                    	route: {
                                            path: '/search12'
                                        },
                                        selected: true
                                    }
                                }
                            }
        				}
        			},
        			{
        				path : '/entryManageWait', // 入职管理-待入职
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrEntryManageWait' : { route:{ path: '/entryManageWait' }, selected : true },
        							'HrEntryManageRecent' : { route:{ path: '/entryManageRecent'}},
        							'HrEntryManageAbandon' : { route:{ path: '/entryManageAbandon'}}
        						}
        					}
        				}
        			},
        			{
        				path : '/entryManageRecent', // 入职管理-最近入职
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrEntryManageWait' : { route:{ path: '/entryManageWait' }},
        							'HrEntryManageRecent' : { route:{ path: '/entryManageRecent'}, selected : true },
        							'HrEntryManageAbandon' : { route:{ path: '/entryManageAbandon'}}
        						}
        					}
        				}
        			},
        			{
        				path : '/entryManageAbandon', // 入职管理-放弃入职
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrEntryManageWait' : { route:{ path: '/entryManageWait' }},
        							'HrEntryManageRecent' : { route:{ path: '/entryManageRecent'}},
        							'HrEntryManageAbandon' : { route:{ path: '/entryManageAbandon'}, selected : true }
        						}
        					}
        				}
        			},
        			{
        				path : '/positiveManage', //转正管理
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrPositiveManageContent' : { route:{ path: '/positiveManage' }, cri :{'j_path':'/positiveManage'}, selected : true }
        						}
        					}
        				}
        			},
        			{
        				path : '/leaveManageWait', //离职管理-待离职
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrLeaveManageWait' : { route:{ path: '/leaveManageWait'}, cri :{'j_path':'/leaveManageWait'}, selected : true },
        							'HrLeaveManageContent' : { route:{ path: '/leaveManage' }, cri :{'j_path':'/leaveManage'}}
        						}
        					}
        				}
        			},
        			{
        				path : '/leaveManage', //离职管理-已离职
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrLeaveManageContent' : { route:{ path: '/leaveManage' }, cri :{'j_path':'/leaveManage'}, selected : true },
        							'HrLeaveManageWait' : { route:{ path: '/leaveManageWait'}, cri :{'j_path':'/leaveManageWait'}}
        						}
        					}
        				}
        			},
        			{
        				path : '/transferManage', //人事调动-岗位调动
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrTransferContent' : { route:{ path: '/transferManage' }, cri :{'j_path':'/transferManage'}, selected : true },
        							'HrTransferContentSalary' : { route:{ path: '/transferManageSalary' }, cri :{'j_path':'/transferManageSalary'} }
        						}
        					}
        				}
        			},
        			{
        				path : '/transferManageSalary', //人事调动-薪水调动
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrTransferContent' : { route:{ path: '/transferManage' }, cri :{'j_path':'/transferManage'} },
        							'HrTransferContentSalary' : { route:{ path: '/transferManageSalary' }, cri :{'j_path':'/transferManageSalary'}, selected : true }
        						}
        					}
        				}
        			},
        			{
        				path : '/contractSign', //人事合同--签订
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrContractSignContent' : { route:{ path: '/contractSign' }, cri :{'j_path':'/contractSign'}, selected : true },
        							'HrContractNoSignContent' : { route:{ path: '/contractNoSign' }, cri :{'j_path':'/contractNoSign'}}
        						}
        					}
        				}
        			},
        			{
        				path : '/contractNoSign', //人事合同--未签订
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrContractSignContent' : { route:{ path: '/contractSign' }, cri :{'j_path':'/contractSign'}},
        							'HrContractNoSignContent' : { route:{ path: '/contractNoSign' }, cri :{'j_path':'/contractNoSign'}, selected : true }
        						}
        					}
        				}
        			},{
        				path : '/entry', //员工入职
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyEntryContent' : { route:{ path: '/entry' }, cri :{'j_path':'/entry'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/positive', //员工转正
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyPositiveContent' : { route:{ path: '/positive' }, cri :{'j_path':'/positive'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/transfer', //员工调岗
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyTransferContent' : { route:{ path: '/transfer' }, cri :{'j_path':'/transfer'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/leave', //员工离职
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyLeaveContent' : { route:{ path: '/leave' }, cri :{'j_path':'/leave'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/fire', //员工解聘
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyFireContent' : { route:{ path: '/fire' }, cri :{'j_path':'/fire'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/retire', //员工退休
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyRetireContent' : { route:{ path: '/retire' }, cri :{'j_path':'/retire'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/rehire', //员工返聘
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyRehireContent' : { route:{ path: '/rehire' }, cri :{'j_path':'/rehire'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/salary', //员工调薪
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifySalaryContent' : { route:{ path: '/salary' }, cri :{'j_path':'/salary'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/sign', //合同签订
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifySignContent' : { route:{ path: '/sign' }, cri :{'j_path':'/sign'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/change', //合同变更
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyChangeContent' : { route:{ path: '/change' }, cri :{'j_path':'/change'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/remove', //合同解除
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyRemoveContent' : { route:{ path: '/remove' }, cri :{'j_path':'/remove'}, selected : true }
        						}
        					}
        				}
        			}, {
        				path : '/listFiling', //归档箱
        				action : {
        					type : 'tabpanel',
        					options : {
        						panelId : 'hrRatifyPanel',
        						contents : {
        							'HrRatifyMainContent' : { title : lang['hrRatifyMain.fillingBox'], route:{ path: '/listFiling' }, cri :{ 'fdIsFile':'1','j_path':'/listFiling' } , selected : true }
        						}
        					}
        				}
        			},  {
        	        	path : '/sys/subordinate',//人事流程-下属工作
        				action : {
        					type : 'pageopen',
        	  				options : {url : $var.$contextPath + '/sys/subordinate/moduleindex.jsp?moduleMessageKey=hr-ratify:module.hr.ratify',target : '_rIframe'}
        				}
        			},  {
        				path : '/recover',
        				action : {
        					type : 'pageopen',
        					options : {url : $var.$contextPath + '/sys/recycle/import/sysRecycle_index.jsp?modelName=com.landray.kmss.hr.ratify.model.HrRatifyMain',target : '_rIframe'}
        				}
        			},{
        				path : '/management', // 后台管理
        				action : {
        					type : 'pageopen',
        					options : {
        						url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/hr/ratify/tree.jsp',
        						target : '_rIframe'
        					}
        				}
        			}
                ]
            });

            $function.openHref = function(href) {
                window.location.href = href;
            };
            // 监听新建更新等成功后刷新
    		topic.subscribe('successReloadPage', function() {
    			topic.publish('list.refresh');
    		});
    		$function.openPreview = function(id){
    			if (seajs.data.env.isSpa) {
    				topic.publish("nav.operation.clearStatus", null);
    				var router = routerUtils.getRouter();
    				if (router) {
    					router.push('/main',{
    						cri : { 'cri.q' : 'docTemplate:' + id }
    					});
    				}
    			}
    		};
    		var cateId= '', nodeType = '',docStatus = 0;
    		//根据筛选器分类异步校验权限
    		topic.subscribe('criteria.spa.changed',function(evt){
    			//筛选器变化时清空分类和节点类型的值
    			cateId = "";  
    			nodeType = "";
    			var hasCate = false;
    			for(var i=0;i<evt['criterions'].length;i++){
    				  //获取分类id和类型
                 	  if(evt['criterions'][i].key == "docTemplate"){
                 		 hasCate = true;
                     	 cateId= evt['criterions'][i].value[0];
    	                 nodeType = evt['criterions'][i].nodeType;
                 	  }
                 	   if(evt['criterions'][i].key == 'docStatus') {
    					  docStatus = evt['criterions'][i].value[0];
    				  }
    			}		
    		});
            //新建文档
    		$function.addDoc = function(){
				dialog.category('com.landray.kmss.hr.ratify.model.HrRatifyTemplate','docTemplateId','docTemplateName',false,function(rtn) {
					if (rtn != false
							&& rtn != null) {
						addByTemplate(rtn.id);
					}
				});
    		};
    		function addByTemplate(tempId) {
				if (tempId != null && tempId != '') {
					var data = new KMSSData();
					var url = Com_Parameter.ContextPath
							+ "hr/ratify/hr_ratify_main/hrRatifyMain.do?method=loadRatifyTemplate&tempId="
							+ tempId;
					var results;
					data.SendToUrl(url, function(data) {
						results = data.responseText;

					}, false);
					window.open(Com_Parameter.ContextPath
							+ results, '_blank');
				}
			};
    		//删除文档
    		$function.delDoc = function(draft){
    			var values = [];
    			$("input[name='List_Selected']:checked").each(function(){
    				values.push($(this).val());
    			});
    			if(values.length==0){
    				dialog.alert($lang['pageNoSelect']);
    				return;
    			}
    			var url = $var.$contextPath + '/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=deleteall';
    			url = Com_SetUrlParameter(url, 'categoryId', cateId);
    			url = Com_SetUrlParameter(url, 'nodeType', nodeType); 
    			if(draft == '0'){
    				url = $var.$contextPath + '/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=deleteall&status=10';
    			}
    			var config = {
    				url : url, // 删除数据的URL
    				data : $.param({"List_Selected":values},true), // 要删除的数据
    				modelName : "com.landray.kmss.hr.ratify.model.HrRatifyMain" // 主要是判断此文档是否有部署软删除
    			};
    			// 通用删除方法
    			function delCallback(data){
    				topic.publish("list.refresh");
    				dialog.result(data);
    			}
    			Com_Delete(config, delCallback);
    		};
            //查询
    		function openSearch(modelName){
    			var url = $var.$contextPath + '/sys/search/ui/nav_search_new.jsp';
    			url = Com_SetUrlParameter(url, 'modelName', modelName);
    			url = Com_SetUrlParameter(url, 'searchTitle',  encodeURIComponent($lang['search']));
    			LUI.pageOpen(url, '_rIframe');
    		}
        });

    });