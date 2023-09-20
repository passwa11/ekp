
seajs.use(['lui/framework/module', 'lui/jquery', 'lui/dialog', 'lui/topic', 'lang!elec-authen', 'lui/util/env', 'lui/framework/router/router-utils'],
    function(Module, $, dialog, topic, lang, env, routerUtils) {

        var module = Module.find('elecAuthen');

        /**
         * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
         * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
         */
        module.controller(function($var, $lang, $function, $router) {
            //路由配置
            $router.define({
                startpath: '/',
                routes: [

                    {
                        path: '/personalCriterionFdName0',
                        action: {
                            type: 'tabpanel',
                            options: {
                                panelId: 'elecAuthenPanel',
                                contents: {
                                    'ElecAuthenPersonalContent': {
                                        route: {
                                            path: '/personalCriterionFdName0'
                                        },
                                        cri: {
                                            'cri.q': 'fdName:'
                                        },
                                        selected: true
                                    }
                                }
                            }
                        }
                    }, {
                        path: '/priseCriterionFdStatus1',
                        action: {
                            type: 'tabpanel',
                            options: {
                                panelId: 'elecAuthenPanel',
                                contents: {
                                    'ElecAuthenPriseContent': {
                                        route: {
                                            path: '/priseCriterionFdStatus1'
                                        },
                                        cri: {
                                            'cri.q': 'fdStatus:0'
                                        },
                                        selected: true
                                    }
                                }
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