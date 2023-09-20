seajs.use(['lui/framework/module','lui/jquery','lui/dialog','lui/topic','lang!km-imeeting','lui/util/env','km/imeeting/resource/js/dateUtil'],
		function(Module, $, dialog, topic ,lang,env,dateUtil){
	
	var module = Module.find('kmImeeting');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/myWorkbench',
			routes : [{
				path : '/myAttend', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'myAttend' : { title : lang['kmImeeting.tree.meeting.myAttend'], route:{ path: '/myAttend' }, cri :{'cri.q':'mymeeting:myAttend','except':'docStatus:00','j_path':'/myAttend'}, selected : true }
						}
					}
				}
			},{
				path : '/committee', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'myHost' : { title : lang['kmImeeting.committee'], route:{ path: '/committee' }, cri :{'cri.q':'mymeeting:myHost','except':'docStatus:00','j_path':'/committee'}, selected : true }
						}
					}
				}
			},{
				path : '/drafted', 
				children : [{
				path:'/myMeeting',
				action : {
					type : 'tabpanel',
					options : {
							panelId : 'kmImeetingPanel',
							contents : {
								'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/drafted/myMeeting' }, cri :{'mymeeting':'myCreate','except':'docStatus:00','j_path':'/drafted/myMeeting'}, selected : true },
								'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/drafted/mySummary' }, cri :{'mysummary':'myCreate','except':'docStatus:00','j_path':'/drafted/mySummary'}},
								'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/drafted/myTopic' }, cri :{'mytopic':'myCreate','except':'docStatus:00','j_path':'/drafted/myTopic'}}
							}
						}
					}
				},{
					path:'/mySummary',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'kmImeetingPanel',
							contents : {
								'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/drafted/myMeeting' }, cri :{'mymeeting':'myCreate','except':'docStatus:00','j_path':'/drafted/myMeeting'} },
								'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/drafted/mySummary' }, cri :{'mysummary':'myCreate','except':'docStatus:00','j_path':'/drafted/mySummary'}, selected : true },
								'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/drafted/myTopic' }, cri :{'mytopic':'myCreate','except':'docStatus:00','j_path':'/drafted/myTopic'}}
							}
						}
					}
				},{
					path:'/myTopic',
					action : {
						type : 'tabpanel',
						options : {
							panelId : 'kmImeetingPanel',
							contents : {
								'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/drafted/myMeeting' }, cri :{'mymeeting':'myCreate','except':'docStatus:00','j_path':'/drafted/myMeeting'} },
								'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/drafted/mySummary' }, cri :{'mysummary':'myCreate','except':'docStatus:00','j_path':'/drafted/mySummary'}},
								'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/drafted/myTopic' }, cri :{'mytopic':'myCreate','except':'docStatus:00','j_path':'/drafted/myTopic'}, selected : true}
							}
						}
					}
				}]
			},{
				path : '/approval', 
				children : [{
					path:'/myMeeting',
					action : {
						type : 'tabpanel',
						options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/approval/myMeeting' }, cri :{'mymeeting':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/myMeeting'}, selected : true },
									'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/approval/mySummary' }, cri :{'mysummary':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/mySummary'}},
									'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/approval/myTopic' }, cri :{'mytopic':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/myTopic'}}
								}
							}
						}
					},{
						path:'/mySummary',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/approval/myMeeting' }, cri :{'mymeeting':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/myMeeting'} },
									'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/approval/mySummary' }, cri :{'mysummary':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/mySummary'}, selected : true },
									'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/approval/myTopic' }, cri :{'mytopic':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/myTopic'}}
								}
							}
						}
					},{
						path:'/myTopic',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/approval/myMeeting' }, cri :{'mymeeting':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/myMeeting'} },
									'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/approval/mySummary' }, cri :{'mysummary':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/mySummary'}},
									'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/approval/myTopic' }, cri :{'mytopic':'myApproval','except':'docStatus:10_11_30_00','j_path':'/approval/myTopic'}, selected : true}
								}
							}
						}
					}]
			},{
				path : '/approvaled', 
				children : [{
					path:'/myMeeting',
					action : {
						type : 'tabpanel',
						options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/approvaled/myMeeting' }, cri :{'mymeeting':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/myMeeting'}, selected : true },
									'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/approvaled/mySummary' }, cri :{'mysummary':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/mySummary'}},
									'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/approvaled/myTopic' }, cri :{'mytopic':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/myTopic'}}
								}
							}
						}
					},{
						path:'/mySummary',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/approvaled/myMeeting' }, cri :{'mymeeting':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/myMeeting'} },
									'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/approvaled/mySummary' }, cri :{'mysummary':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/mySummary'}, selected : true },
									'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/approvaled/myTopic' }, cri :{'mytopic':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/myTopic'}}
								}
							}
						}
					},{
						path:'/myTopic',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.tree.meeting'], route:{ path: '/approvaled/myMeeting' }, cri :{'mymeeting':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/myMeeting'} },
									'mySummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/approvaled/mySummary' }, cri :{'mysummary':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/mySummary'} },
									'myTopic' : { title : lang['kmImeeting.tree.myHandleTopic'], route:{ path: '/approvaled/myTopic' }, cri :{'mytopic':'myApproved','except':'docStatus:10_00','j_path':'/approvaled/myTopic'}, selected : true}
								}
							}
						}
					}]
			},{
				path : '/myCalendar', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'mycalendar' : { title : lang['kkmImeeting.tree.calender'], route:{ path: '/myCalendar' },  selected : true }
						}
					}
				}
			},{
				path : '/myWorkbench', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'myWorkbench' : { title : lang['workbench.person'], route:{ path: '/myWorkbench' },  selected : true }
						}
					}
				}
			},{
				path : '/kmImeeting_paln', 
				children : [{
					path:'/meetingPlace',
					action : {
						type : 'tabpanel',
						options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'meetingPlace' : { title : lang['kmImeetingRes.fdPlace'], route:{ path: '/kmImeeting_paln/meetingPlace' }, selected : true },
									'meetingEquipment' : { title : lang['table.kmImeetingEquipment'], route:{ path: '/kmImeeting_paln/meetingEquipment' }}
								}
							}
						}
					},{
						path:'/meetingEquipment',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'meetingPlace' : { title : lang['kmImeetingRes.fdPlace'], route:{ path: '/kmImeeting_paln/meetingPlace' } },
									'meetingEquipment' : { title : lang['table.kmImeetingEquipment'], route:{ path: '/kmImeeting_paln/meetingEquipment' },selected : true }
								}
							}
						}
					}]
			},{
				path : '/kmImeeting_cyclicity', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'meetingCyclicity' : { title : lang['kmImeeting.tree.cyclicity.meeting'], route:{ path: '/kmImeeting_cyclicity' }, cri :{'j_path':'/kmImeeting_cyclicity'} , selected : true }
						}
					}
				}
			},{
				path : '/kmImeeting_topic', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'meetingTopic' : { title : lang['kmImeeting.tree.topic'], route:{ path: '/kmImeeting_topic' }, cri :{'j_path':'/kmImeeting_topic'} , selected : true }
						}
					}
				}
			},{
				path : '/kmImeeting_fixed', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'meetingFixed' : { title : lang['kmImeeting.tree.query'], route:{ path: '/kmImeeting_fixed' }, cri :{'j_path':'/kmImeeting_fixed'} , selected : true }
						}
					}
				}
			},{
				path : '/kmImeeting_data', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'meetingData' : { title : lang['kmImeeting.tree.data'], route:{ path: '/kmImeeting_data' }, selected : true }
						}
					}
				}
			},{
				path : '/myHandleSummary', 
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'myHandleSummary' : { title : lang['kmImeeting.tree.myHandleSummary'], route:{ path: '/myHandleSummary' }, cri :{'j_path':'/myHandleSummary'} ,selected : true }
						}
					}
				}
			},{
				path : '/listDiscard', 
				action : function(){
					moduleAPI.kmImeeting.openSearch($var.$contextPath +"/km/imeeting/import/kmImeeting_abandom.jsp");
				}
			},{
				path : '/deptStat', 
				action : function(){
					moduleAPI.kmImeeting.openSearch($var.$contextPath +"/km/imeeting/import/kmImeeting_stat.jsp?stat_key=dept.stat");
				}
			},{
				path : '/abandom', 
				children : [{
					path:'/myMeeting',
					action : {
						type : 'tabpanel',
						options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.panel.status.abandom'], route:{ path: '/abandom/myMeeting' }, cri :{'meetingStatus':'00','j_path':'/abandom/myMeeting'}, selected : true },
									'mySummary' : { title : lang['kmImeetingSummary.panel.status.abandom'], route:{ path: '/abandom/mySummary' }, cri :{'docStatus':'00','j_path':'/abandom/mySummary'}},
									'myTopic' : { title : lang['kmImeetingTopic.panel.status.abandom'], route:{ path: '/abandom/myTopic' }, cri :{'docStatus':'00','j_path':'/abandom/myTopic'}}
								}
							}
						}
					},{
						path:'/mySummary',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.panel.status.abandom'], route:{ path: '/abandom/myMeeting' }, cri :{'meetingStatus':'00','j_path':'/abandom/myMeeting'} },
									'mySummary' : { title : lang['kmImeetingSummary.panel.status.abandom'], route:{ path: '/abandom/mySummary' }, cri :{'docStatus':'00','j_path':'/abandom/mySummary'}, selected : true },
									'myTopic' : { title : lang['kmImeetingTopic.panel.status.abandom'], route:{ path: '/abandom/myTopic' }, cri :{'docStatus':'00','j_path':'/abandom/myTopic'}}
								}
							}
						}
					},{
						path:'/myTopic',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'myMeeting' : { title : lang['kmImeeting.panel.status.abandom'], route:{ path: '/abandom/myMeeting' }, cri :{'meetingStatus':'00','j_path':'/abandom/myMeeting'} },
									'mySummary' : { title : lang['kmImeetingSummary.panel.status.abandom'], route:{ path: '/abandom/mySummary' }, cri :{'docStatus':'00','j_path':'/abandom/mySummary'}},
									'myTopic' : { title : lang['kmImeetingTopic.panel.status.abandom'], route:{ path: '/abandom/myTopic' }, cri :{'docStatus':'00','j_path':'/abandom/myTopic'}, selected : true }
								}
							}
						}
					}]
			}
			/*,{
				path : '/sys/subordinate', // 下属工作
				action : {
					type : 'tabpanel',
					options : {
						panelId : 'kmImeetingPanel',
						contents : {
							'subordinate' : { title : lang['subordinate.kmImeeting'], route:{ path: '/subordinate' }, cri :{'j_path':'/subordinate'} ,selected : true }
						}
					}
				}
			}*/,
			{
				path : '/sys/subordinate', // 下属工作
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/subordinate/moduleindex.jsp?moduleMessageKey=km-imeeting:module.km.imeeting',
						target : '_rIframe'
					}
				}
			}
			,{
				path : '/management', // 后台管理
				action : function (){
					//修复后台配置出现大片空白区域 #106178
					var contextPath = env.fn.getConfig().contextPath;
					LUI.pageOpen( contextPath + '/sys/profile/moduleindex.jsp?nav=/km/imeeting/tree.jsp','_rIframe');
//					type : 'tabpanel',
//					options : {
//						panelId : 'kmImeetingPanel',
//						contents : {
//							'management' : { route:{ path: '/management' }, cri :{'j_path':'/management'} ,selected : true }
//						}
//					}
				}
			},
			{
				path : '/dept_stat', 
				children : [{
					path:'/dept_stat',
					action : {
						type : 'tabpanel',
						options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'deptStat' : { title : lang['kmImeetingStat.dept.stat'], route:{ path: '/dept_stat/dept_stat' }, selected : true },
									'deptStatMon' : { title : lang['kmImeetingStat.dept.statMon'], route:{ path: '/dept_stat/dept_statMon' }}
								}
							}
						}
					},{
						path:'/dept_statMon',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'deptStat' : { title : lang['kmImeetingStat.dept.stat'], route:{ path: '/dept_stat/dept_stat' } },
									'deptStatMon' : { title : lang['kmImeetingStat.dept.statMon'], route:{ path: '/dept_stat/dept_statMon' },selected : true }
								}
							}
						}
					}]
			},
			{
				path : '/person_stat', 
				children : [{
					path:'/person_stat',
					action : {
						type : 'tabpanel',
						options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'personStat' : { title : lang['kmImeetingStat.person.stat'], route:{ path: '/person_stat/person_stat' }, selected : true },
									'personStatMon' : { title : lang['kmImeetingStat.person.statMon'], route:{ path: '/person_stat/person_statMon' }}
								}
							}
						}
					},{
						path:'/person_statMon',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'personStat' : { title : lang['kmImeetingStat.person.stat'], route:{ path: '/person_stat/person_stat' } },
									'personStatMon' : { title : lang['kmImeetingStat.person.statMon'], route:{ path: '/person_stat/person_statMon' },selected : true }
								}
							}
						}
					}]
			},
			{
				path : '/resource_stat', 
				children : [{
					path:'/resource_stat',
					action : {
						type : 'tabpanel',
						options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'resourceStat' : { title : lang['kmImeetingStat.resource.stat'], route:{ path: '/resource_stat/resource_stat' }, selected : true },
									'resourceStatMon' : { title : lang['kmImeetingStat.resource.statMon'], route:{ path: '/resource_stat/resource_statMon' }}
								}
							}
						}
					},{
						path:'/resource_statMon',
						action : {
							type : 'tabpanel',
							options : {
								panelId : 'kmImeetingPanel',
								contents : {
									'resourceStat' : { title : lang['kmImeetingStat.resource.stat'], route:{ path: '/resource_stat/resource_stat' } },
									'resourceStatMon' : { title : lang['kmImeetingStat.resource.statMon'], route:{ path: '/resource_stat/resource_statMon' },selected : true }
								}
							}
						}
					}]
			},
			{
				path : '/datamng', //数据管理
				action : function(){
					openDatamng();
				}
			}
			]
		});		
		
		$function.openHref=function(href){
			window.location.href=href;
		};
		$function.openSearch=function(url){
			LUI.pageOpen(url,'_rIframe');
		};
		
			//切换菜单栏
		$function.switchMenuItem = function(obj, panel,menuType){
				 LUI.pageHide("_rIframe");
				var tabpanel = LUI(panel);
				switch(menuType){
					case 'myCalendar' :  
					tabpanel.setSelectedIndex(0);
					tabpanel.props(0,{
						visible : true
					});
					topic.publish('spa.change.add', {
						target : obj
					});
					break;
					case 'other' :  
						tabpanel.setSelectedIndex(0);
						tabpanel.props(0,{
							visible : true
						});
						topic.publish('spa.change.add', {
							target : obj
						});
						break;	
				}
			};
		
			// 监听新建更新等成功后刷新
			topic.subscribe('successReloadPage', function() {
				setTimeout(function(){
					if(typeof LUI('____calendar____imeeting')!='undefined'){
					LUI('____calendar____imeeting').refreshSchedules();
					}
				}, 100);
			});  
			

			//新建会议
			$function.addDoc = function() {
					dialog.categoryForNewFile(
							'com.landray.kmss.km.imeeting.model.KmImeetingTemplate',
							'/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{id}',false,null,null,'${JsParam.categoryId}');
		 	};

			//数据初始化
		 	$function.transformData=function(datas){
				var main=datas.main;
				for(var key in main){
					for(var i=0;i<main[key].list.length;i++){
						var item=main[key].list[i];
						if(checkStatus(item)==-1){
							item.color=$('.meeting_calendar_label_unhold').css('background-color');
						}
						if(checkStatus(item)==0){
							item.color=$('.meeting_calendar_label_holding').css('background-color');
						}
						if(checkStatus(item)==1){
							item.color=$('.meeting_calendar_label_hold').css('background-color');
						}
					}
				}
				return datas;
			};

			//当前会议状态
			var checkStatus=function(item){
				var startDate=dateUtil.parseDate(item.start),endDate=dateUtil.parseDate(item.end);
				var now=new Date(parseFloat('${now}'));
				//未召开
				if(now.getTime()<startDate.getTime()){
					return -1;
				}
				//进行中
				if(now.getTime()>=startDate.getTime() && now.getTime()<=endDate.getTime()){
					return 0;
				}
				//已召开
				if(now.getTime()>endDate.getTime()){
					return 1;
				}
			};
		
			//定位
			var getPos=function(evt,obj){
				var sWidth=obj.width();var sHeight=obj.height();
				x=evt.pageX;
				y=evt.pageY;
				if(y+sHeight>$(window).height()){
					y-=sHeight;
				}
				if(x+sWidth>$(document.body).outerWidth(true)){
					x-=sWidth;
				}
				return {"top":y,"left":x};
			};
			
			//新建
			topic.subscribe('calendar.select',function(arg){
				$('.meeting_calendar_dialog').hide();
				var addDialog=$('#meeting_calendar_add');
				//时间格式2014-7-11~2014-7-12
				var date=dateUtil.formatDate(dateUtil.parseDate(arg.start),'${dateFormatter}');
				if(arg.start!=arg.end){
					date+="~"+arg.end;
				}
				var start = dateUtil.formatDate( dateUtil.parseDate(arg.start,'YYYY-MM-hh'),'${dateFormatter}'),
					end = dateUtil.formatDate( dateUtil.parseDate(arg.end,'YYYY-MM-hh'),'${dateFormatter}');
				addDialog.find('.date').html(date);//时间字符串
				addDialog.find('.fdHoldDate').html(start);//召开时间
				addDialog.find('.fdFinishDate').html(end);//结束时间
				addDialog.find('.resId').html(arg.resId);//地点ID
				addDialog.find('.resName').html(env.fn.formatText(arg.resName) );//地点
				addDialog.css(getPos(arg.evt,addDialog)).fadeIn("fast");
				var nowdate = dateUtil.formatDate(new Date(),'${dateFormatter}');
				var startDate = dateUtil.formatDate(dateUtil.parseDate(arg.start),'${dateFormatter}');
				if(nowdate>startDate){
					$('#book_add_btn').hide();
					$('#meeting_add_btn').hide();
				}else{
					$('#book_add_btn').show();
					$('#meeting_add_btn').show();
				}
			});

			//查看
			topic.subscribe('calendar.thing.click',function(arg){
				$('.meeting_calendar_dialog').hide();
				var viewDialog;//弹出框
				if(arg.data.type =="book"){
					viewDialog=$("#meeting_calendar_bookview");//会议室预约弹出框
					viewDialog.find(".fdRemark").html(env.fn.formatText( textEllipsis(arg.data.fdRemark) ));//备注
				}else{
					viewDialog=$("#meeting_calendar_mainview");//会议安排弹出框
					viewDialog.find(".fdHost").html(arg.data.fdHost);//主持人
				}
				//时间格式2014-7-11~2014-7-12
				var date=dateUtil.formatDate(arg.data.start,"${dateTimeFormatter}");
				if(arg.data.start!=arg.data.end){
					date+=" ~ "+dateUtil.formatDate(arg.data.end,"${dateTimeFormatter}");
				}
				viewDialog.find(".fdId").html(arg.data.fdId);//fdId
				viewDialog.find(".fdName").html( env.fn.formatText(arg.data.title) );//会议题目
				viewDialog.find(".fdPlace").html( env.fn.formatText(arg.data.fdPlaceName) );//地点
				viewDialog.find(".fdHoldDate").html(date);//召开时间

				var creator=arg.data.creator;
				if(arg.data.dept){
					creator+="("+arg.data.dept+")";//（部门）
				}
				viewDialog.find(".docCreator").html(creator);//人员（部门）
				
				if(arg.data.type=="book"){//会议预约按钮权限检测
					$('#book_delete_btn,#book_edit_btn').hide();
					$.ajax({
						url: $var.$contextPath +"/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=checkAuth",
						type: 'POST',
						dataType: 'json',
						data: {fdId: arg.data.fdId},
						success: function(data, textStatus, xhr) {//操作成功
							if(data.canEdit){
								$('#book_edit_btn').show();
							}
							if(data.canDelete){
								$('#book_delete_btn').show();
							}
						}
					});
				}else{//会议安排查看按钮权限检测
					$('#meeting_view_btn').hide();
					$.ajax({
						url: $var.$contextPath +"/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=checkViewAuth",
						type: 'POST',
						dataType: 'json',
						data: {fdId: arg.data.fdId},
						success: function(data, textStatus, xhr) {//操作成功
							if(data.canView){
								$('#meeting_view_btn').show();
							}
						}
					});
				}
				var today = new Date();
				if($('#book_change_btn').length > 0 ){
					if(today.getTime() > arg.data.start.getTime() || '${userId}' != arg.data.creatorId){
						$('#book_change_btn').hide();
					}else{
						$('#book_change_btn').show();
					}
				}
				viewDialog.find(".type").html(arg.data.type);
				viewDialog.css(getPos(arg.evt,viewDialog)).fadeIn("fast");
			});
			
			//字符串截取
			function textEllipsis(text){
				text = text || '';
				if(text && text.length>200){
					text=text.substring(0,200)+"......";
				}
				return text;
			}
			//数据管理
			function openDatamng(){
				var url = $var.$contextPath + '/sys/datamng/nav_index.jsp?modulePrefix=/km/imeeting/';
				LUI.pageOpen(url, '_rIframe');
			}
	});
});