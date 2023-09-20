
seajs.use(['lui/framework/module', 'lui/jquery', 'lui/dialog', 'lui/topic', 'lang!fssc-project', 'lui/util/env', 'lui/framework/router/router-utils','lui/dialog_common'],
    function(Module, $, dialog, topic, lang, env, routerUtils) {
	
        var module = Module.find('fsscConfig');

        /**
         * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
         * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
         */
        module.controller(function($var, $lang, $function, $router) {
            //路由配置
            $router.define({
                startpath: '',
                routes: [
                   {
        				path : '/fssc_config_list', 
        				action : {
        					type : 'pageopen',
        					options : {
        						url : $var.$contextPath + '/fssc/config/fssc_config_list/index.jsp',
        						target : '_rIframe'
        					}
        				}
        			},{
        				path : '/fssc_config_score', 
        				action : {
        					type : 'pageopen',
        					options : {
        						url : $var.$contextPath + '/fssc/config/fssc_config_score/index.jsp',
        						target : '_rIframe'
        					}
        				}
        			},{
        				path : '/fssc_config_score_report', 
        				action : {
        					type : 'pageopen',
        					options : {
        						url : $var.$contextPath + '/fssc/config/fssc_config_report/fsscConfigScoreReport_search.jsp',
        						target : '_rIframe'
        					}
        				}
        			}
                ]
            });

            $function.openHref = function(href) {
                window.location.href = href;
            };
        });

    });