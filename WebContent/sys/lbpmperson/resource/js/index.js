seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/module'],
		function($, strutil, dialog, topic, Module){
	var module = Module.find('sysLbpmPerson');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
			startpath : '/createDoc',
			routes : [{
				path : '/createDoc', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/indexCreateDoc.jsp',
						target : '_rIframe'
					}
				}
			},
			{
				path : '/draft', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_draft/draft_clusterIndex.jsp?method=listDraft',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/abandon', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_abandon/abandon_clusterIndex.jsp?method=listAbandon',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/create', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_creator/creator_clusterIndex.jsp?method=listCreator',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/start', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_creator/creator_clusterIndex.jsp?method=listCreator',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/approve', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_approval/approval_clusterIndex.jsp?method=listApproval',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/approved', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_approved/approved_clusterIndex.jsp?method=listApproved',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/track', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_track/track_clusterIndex.jsp?method=listTrack',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/auth', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_flow_auth/auth_clusterIndex.jsp',
						target : '_rIframe'
					}
				}
			}
			
			,
			{
				path : '/createSummary', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmperson/person_efficiency/summary_index.jsp',
						target : '_rIframe'
					}
				}
			}
			,
			{
				path : '/usage', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmservice/support/lbpm_usage/lbpmUsage.do?method=definePerson',
						target : '_rIframe'
					}
				}
			}
			
			,
			{
				path : '/lbpmIdentity', //
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/lbpmservice/support/lbpm_person_set/default_identity.jsp',
						target : '_rIframe'
					}
				}
			}
			]
		});
		
		 

		//新建文档
		window.setPersonMainIframe=function(url){
			//$router.push(url, null);
			LUI.$("[data-lui-type*=AccordionPanel] li[data-path='"+url+"']").click();
		}
	});
});