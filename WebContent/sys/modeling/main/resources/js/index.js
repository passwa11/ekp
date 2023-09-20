seajs.use(['lui/framework/module', 'lui/jquery', 'lui/dialog', 'lui/topic', 'lui/spa/const', 'lui/framework/router/router-utils', 'lui/toolbar', 'lui/spa/Spa'
        , 'sys/modeling/main/ui/modelingUtil', 'lui/framework/router/router-utils', 'lui/util/env'],
    function (Module, jquery, dialog, topic, spaConst, routerUtils, toolbar, Spa, modelingUtil, routerUtils, env) {

        var module = Module.find('sysModeling');

        if(!module){
            return
        }

        /**
         * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
         * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
         */
        module.controller(function ($var, $lang, $function, $router) {
            /*
             * 路由配置
             *
             * 动态路由注意：
             * 1、动态路由路径后面必须添加“-dynamic”以识别
             * 2、动态路由在parmas里面必须定义属性key的值，值为节点唯一定位值的属性名
             * 3、具体参考listmodel-dynamic和dbNavTree-dynamic
             * */
            $router.define({
                routes: [{
                    path: '/listmodel-dynamic',
                    action: function (params) {
                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset({
                                fdAppModelId: params.modelId,
                                modelKey: params.type,
                                listviewId: params.listviewId
                            });
                        }
                        openSearch(params);
                    }
                }, {
                    path: '/dbNavTree-dynamic', //图表中心
                    action: function (params) {
                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset({
                                type: params.type,
                                dbcenterId: params.dbcenterId
                            });
                        }
                        openDbNavTree(params);
                    }
                }, {
                    path: '/showPortal-dynamic', //portal门户
                    action: function (params) {
                        // 初始化时
                        if (params.$isInit) {
                            //params = routerUtils.getRouter()._getHashParams(window);
                            //保存时最新配置的门户页面
                            params = {
                                type: window.portalData.params.type,
                                href: window.portalData.params.href
                            };
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset({
                                type: params.type,
                                href: params.href
                            });
                        }
                        openPortalPage(params);
                    }
                }, {
                    path: '/resPanel-dynamic', //业务场景-资源面板
                    action: function (params) {
                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset(params);
                        }
                        openBusiness(params);
                    }
                }, {
                    path: '/gantt-dynamic', //业务场景-甘特图
                    action: function (params) {
                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset(params);
                        }
                        openBusiness(params);
                    }
                },  {
                    path: '/mindMap-dynamic', //思维导图
                    action: function (params) {
                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset(params);
                        }
                        openBusiness(params);
                    }
                },  {
                    path: '/calendar-dynamic', //日历视图
                    action: function (params) {
                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset(params);
                        }
                        openBusiness(params);
                    }
                },{
                    path: '/link-dynamic', //自定义连接
                    action: function (params) {

                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset(params);
                        }
                        openLink(params);
                    }
                }, {
                    path: '/collection-dynamic', //新列表视图
                    action: function (params) {

                        // 初始化时
                        if (params.$isInit) {
                            params = routerUtils.getRouter()._getHashParams(window);
                            Spa.spa.reset(params);
                        } else {
                            // 点击时
                            Spa.spa.reset(params);
                        }
                        openCollection(params)
                    }
                },
                    {
                        path: '/treeView-dynamic', //树形视图
                        action: function (params) {

                            // 初始化时
                            if (params.$isInit) {
                                params = routerUtils.getRouter()._getHashParams(window);
                                Spa.spa.reset(params);
                            } else {
                                // 点击时
                                Spa.spa.reset(params);
                            }
                            openTreeView(params)
                        }
                    },
                    {
                        path: '/space-dynamic', //业务空间
                        action: function (params) {

                            // 初始化时
                            if (params.$isInit) {
                                params = routerUtils.getRouter()._getHashParams(window);
                                Spa.spa.reset(params);
                            } else {
                                // 点击时
                                Spa.spa.reset(params);
                            }
                            openSpace(params)
                        }
                    }]

            });

            //打开门户
            function openPortalPage(params) {
                var url = $var.$contextPath;
                url += params.href;
                //LUI.pageOpen(url, '_rIframe');
                //一下是对门户页面的导航和页眉去除，粗暴解决，希望后续门户能做页面去除页眉和导航的支持
                var iframe = document.getElementById("mainIframe");
                var loading = dialog.loading();
                $(iframe).load(function () {
                    //隐藏导航和页眉和页脚
                    var mDocument = iframe.contentWindow.document;
                    var headerDom = mDocument.getElementsByClassName("lui_portal_header")[0];
                    changePortalPageStyle(headerDom, {"display": "none"});
                    var footerDom = mDocument.getElementsByClassName("lui_portal_footer")[0];
                    changePortalPageStyle(footerDom, {"display": "none"});
                    var containerDom = mDocument.getElementsByClassName("lui_portal_container")[0];
                    var frameDom = $(containerDom).find(".lui_list_body_frame")[0];
                    changePortalPageStyle(frameDom, {"margin-left": "0px"});
                    iframe.contentWindow.LUI.ready(function () {
                        var navDom = mDocument.getElementsByClassName("lui_list_left_sidebar_frame")[0];
                        changePortalPageStyle(navDom, {"display": "none"});
                        changePortalPageStyle($(navDom).parents("td:eq(0)")[0], {"display": "none"});
                        var headerDom = mDocument.getElementsByClassName("lui_portal_header")[0];
                        changePortalPageStyle(headerDom, {"display": "none"});
                        var footerDom = mDocument.getElementsByClassName("lui_portal_footer")[0];
                        changePortalPageStyle(footerDom, {"display": "none"});
                        var containerDom = mDocument.getElementsByClassName("lui_portal_container")[0];
                        var frameDom = $(containerDom).find(".lui_list_body_frame")[0];
                        changePortalPageStyle(frameDom, {"margin-left": "0px"});
                    });

                    //重置大小
                    openPageResize();
                    setTimeout(function () {
                        var mDocument = $("iframe#mainIframe")[0].contentWindow.document;
                        var containerDom = mDocument.getElementsByClassName("lui_portal_container")[0];
                        changePortalPageStyle(containerDom, {"top": "0"});
                        $("#mainContent").css({
                            "display": ""
                        })
                        loading && loading.hide();
                    }, 2000);
                })
                $(iframe).attr('src', url);
            }

            function changePortalPageStyle(dom, style) {
                if (dom) {
                    $(dom).css(style);
                }
            }

            function openPageResize() {
                try {
                    var $mainIframe = $("#mainIframe");
                    var iframeDom = $mainIframe[0];
                    var iframeWindow = iframeDom.contentWindow;
                    var $iframeContentBody = $(iframeWindow.document).find("body");
                    var iframeContentHeight = $iframeContentBody.outerHeight(true); // IFrame嵌入的页面内容高度
                    var iframeContentScrollHeight = iframeWindow.document.documentElement.scrollHeight; // IFrame嵌入的页面滚动高度（总高度）
                    var iframeHeight = iframeContentScrollHeight > iframeContentHeight ? iframeContentScrollHeight : iframeContentHeight;
                    $mainIframe.height(iframeHeight); // 重置IFrame的高度

                    var headerH = $(".lui_portal_header").outerHeight();
                    var contentH = document.documentElement.clientHeight - headerH - 35;
                    $mainIframe.css({
                        "width": "100%",
                        "min-height": contentH + "px"
                    })
                } catch (e) {
                }
            }

            //查询
            function openSearch(params) {
                var url = $var.$contextPath + '/sys/modeling/main/listview.do?method=index&canClose=false&isNew=true';
                url = Com_SetUrlParameter(url, 'listviewId', params.listviewId);
                url = Com_SetUrlParameter(url, 'fdAppModelId', params.fdAppModelId);
                url = Com_SetUrlParameter(url, 'isFlow', (params.modelKey === 'flow'));
                LUI.pageOpen(url, '_rIframe');
            }

            function openCollection(params) {
                var url = $var.$contextPath + '/sys/modeling/main/collectionView.do?method=index&canClose=false&isNew=true';
                url = Com_SetUrlParameter(url, 'listviewId', params.listviewId);
                url = Com_SetUrlParameter(url, 'fdAppModelId', params.fdAppModelId);
                url = Com_SetUrlParameter(url, 'isFlow', (params.modelKey === 'flow'));
                LUI.pageOpen(url, '_rIframe');
            }

            function openTreeView(params) {
                var url = $var.$contextPath + '/sys/modeling/main/business.do?method=index';
                url = Com_SetUrlParameter(url, 'modelId', params.modelId);
                url = Com_SetUrlParameter(url, 'businessId', params.businessId);
                url = Com_SetUrlParameter(url, 'type', params.bType);
                LUI.pageOpen(url, '_rIframe');
            }

            function openSpace(params) {
                var url = $var.$contextPath + '/sys/modeling/main/modelingAppSpace.do?method=index';
                url = Com_SetUrlParameter(url, 'spaceId', params.spaceId);
                url = Com_SetUrlParameter(url, 'fdAppId', params.fdAppId);
                LUI.pageOpen(url, '_rIframe');
            }

            //打开业务场景
            function openBusiness(params) {
                var url = $var.$contextPath + '/sys/modeling/main/business.do?method=index';
                url = Com_SetUrlParameter(url, 'modelId', params.modelId);
                url = Com_SetUrlParameter(url, 'businessId', params.businessId);
                url = Com_SetUrlParameter(url, 'type', params.bType);
                LUI.pageOpen(url, '_rIframe');
            }

            //图表中心
            function openDbNavTree(params) {
                var url = $var.$contextPath;
                if (params["type"] == "custom") {
                    url += "/dbcenter/echarts/db_echarts_custom/dbEchartsCustom.do?method=view&fdId=" + params["dbcenterId"] + "&showButton=0&contentPaved=true"+"&chartDefault=1";
                }
                if (params["type"] == "chart") {
                    url += "/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=view&fdId=" + params["dbcenterId"] + "&showButton=0"+"&chartDefault=1";
                }
                if (params["type"] == "table") {
                    url += "/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=viewRpt&fdId=" + params["dbcenterId"] + "&showButton=0"+"&chartDefault=1";
                }
                if (params["type"] == "chartset") {
                    url += "/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=view&isModeling=1&fdId=" + params["dbcenterId"] + "&showButton=0"+"&chartDefault=1";
                }
                LUI.pageOpen(url, '_rIframe');
            }

            //打开自定义连接诶
            function openLink(params) {
                var url = $var.$contextPath + params.link;
                LUI.pageOpen(url, '_rIframe');
            }
        })

    })