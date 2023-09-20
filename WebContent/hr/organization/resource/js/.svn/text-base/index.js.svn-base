seajs.use(['lui/framework/module', 'lui/jquery', 'lui/dialog', 'lui/topic', 'lang!hr-organization', 'lui/util/env', 'lui/framework/router/router-utils'],
    function(Module, $, dialog, topic, lang, env, routerUtils) {

        var module = Module.find('hrOrganization');

        /**
         * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
         * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
         */
        module.controller(function($var, $lang, $function, $router) {
            //路由配置
            $router.define({
                startpath: '/org',
                routes: [
             			{
             				path : '/org', //组织架构维护
//             				action : function(){
//             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_tree/index.jsp','_rIframe');
//             				}
            				action : {
            					type : 'tabpanel',
            					options : {
            						panelId : 'hrOrgPanel',
            						contents : {
            							'HrOrgContent' : { route:{ path: '/org' }, cri :{'j_path':'/org'}, selected : true }
            						}
            					}
            				}
             			},{
             				path : '/staffingLevel', //职务管理
//             				action : {
//             					type : 'tabpanel',
//             					options : {
//             						panelId : 'hrOrgPanel',
//             						contents : {
//             							'HrOrgStaffingLevelContent' : { route:{ path: '/hrOrgStaffingLevel' }, cri :{'j_path':'/hrOrgStaffingLevel'}, selected : true }
//             						}
//             					}
//             				}
             				action:function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_staffing_level/index.jsp','_rIframe');	
             				}
             			},
             			{
             				path : '/hrOrgGrade', //职等
             				action:function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_grade/index.jsp','_rIframe');	
             				}
             			},
             			{
             				path : '/hrOrgRank', //职级
             				action:function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_rank/index.jsp','_rIframe');	
             				}
             			},
             			{
             				path : '/hrOrgPost', //岗位管理
             				action:function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_post/index.jsp','_rIframe');	
             				}
             			},
             			{
             				path : '/invalidPost', //已停用岗位管理
             				action:function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_post/disable/index.jsp','_rIframe');	
             				}
             			},
             			{
             				path : '/hrOrgPostSeq', //岗位序列
             				action:function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_post_seq/index.jsp','_rIframe');	
             				}
             			},
             			{
             				path : '/hrOrgConPost', //兼岗管理
             				action:function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_con_post/index.jsp','_rIframe');	
             				}
             			},
             			{
             				path : '/invalidOrg', //已停用组织
             				action :function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_element/disable/index.jsp','_rIframe');
             				}
             			},
             			{
             				path : '/feedback', //人组织架构图
             				action : function(){
								LUI.pageOpen($var.$contextPath +'/sys/profile/moduleindex.jsp?nav=/hr/organization/hr_org_tree.jsp','_rIframe');
             				}
             			},
             			{
             				path : '/compile', //人组织编制管理
             				action :function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_compile/index.jsp','_rIframe');
             				}
             			},{
             				path : '/entry', //组织架构维护
             				action :function(){
             					LUI.pageOpen($var.$contextPath + '/hr/organization/hr_organization_tree/index.jsp','_rIframe');
             				}
             			},
             			{
            				path : '/management', // 后台管理
            				action : {
            					type : 'pageopen',
            					options : {
            						url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/hr/organization/tree.jsp',
            						target : '_rIframe'
            					}
            				}
             			}
                     ]
            });

            $function.openHref = function(href) {
                window.location.href = href;
            };
            $function.openSearch=function(url){
    			LUI.pageOpen(url,'_rIframe');
    		};
            // 监听新建更新等成功后刷新
    		topic.subscribe('successReloadPage', function() {
    			topic.publish('list.refresh');
    		});
    		
           
        });

    });