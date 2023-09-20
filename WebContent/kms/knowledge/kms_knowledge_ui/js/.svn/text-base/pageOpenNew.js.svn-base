/**
 * 打开页面
 * @param {String} url:打开的链接。根据target的不同请求内容可能是完整网页，可能是HTML片段
 * @param {String} target:链接目标。_blank(打开新窗口)、_self(刷新当前页面)、_content(刷新content)、_iframe(刷新Iframe)、_rIframe(刷新右侧Iframe)
 * @param {Object} features:特性,如{ transition : 'slideDown' , pageClass : 'my custom pageClass'  }
 * @param customHashParams {Object} 自定义hash参数，除j_start、j_target、j_path等系统公共hash参数之外的自定义hash参数，参数名必须以"c_"作为起始，如c_app_title、c_app_url
 */
var pageOpenNew = function(url, target, features, customHashParams){
    var view = LUI.getPageView();

    if("_rIframe" == target) {
        seajs.use( [ 'lui/topic' ], function(topic) {
            topic.publish("lui/page/show/rIrame", features);
        });
    }

    if(view){
        features = features || {};
        features.curWindow = window;
        view.open(url, target, features, customHashParams);
    }else{
        _____pageOpen(url, target, features, customHashParams);
    }
};

var _____pageOpen = function(url, target, features, customHashParams){
    var targets = '_blank;_self;_parent;_top';
    if(target == '_rIframe' && typeof(openPageNew) !== 'undefined'){
        openPageNew(url, {
            closeable : false
        });
        return;
    }
    if(targets.indexOf(target) == -1){
        target = '_top';
    }
    LUI.addHashParamToCookie(url,customHashParams);
    window.open(url, target);
};

seajs.use(['lui/jquery','lui/dialog','sys/ui/extend/template/module/export','lui/topic'],
    function($, dialog, $export, topic) {

        var loading = null;
        var openPageReisizeTimeout = null;
        function openPageResize(){
            try{
                var $mainIframe = $("#mainIframe");
                var iframeDom = $mainIframe[0];
                var iframeWindow = iframeDom.contentWindow;
                var $iframeContentBody = $(iframeWindow.document).find("body");
                var iframeContentHeight = $iframeContentBody.outerHeight(true); // IFrame嵌入的页面内容高度
                var iframeContentScrollHeight = iframeWindow.document.documentElement.scrollHeight; // IFrame嵌入的页面滚动高度（总高度）
                var iframeHeight = iframeContentHeight;
                $mainIframe.height(iframeHeight); // 重置IFrame的高度
            }catch(e){}
        }


        var openPageReisizeTimer = null,
            openPageReisizeCounter = 500;
        function openPageResizeTimerFunction(){
            openPageResize();
            //openPageReisizeCounter = openPageReisizeCounter;
            openPageReisizeTimer = window.setTimeout(openPageResizeTimerFunction, openPageReisizeCounter);
        }

        window.openQuery = function(){

            if(openPageReisizeTimeout != null){
                window.clearTimeout(openPageReisizeTimeout);
            }
            var $iframeContainer = $('#mainContent'),
                $queryContainer = $("#queryListView"),
                $iframe = $("#mainIframe");
            //打开Query时移除Iframe中的内容，避免后续出现
            $iframe.attr('src','about:blank');
            $iframe.load(function(){
                $queryContainer.show();
            });
            $iframeContainer.hide();
            $queryContainer.show();
        };

        window.openPageNew = function(url, features){

            //topic.publish("nav.operation.clearStatus", null);

            features = features || {};
            var $queryContainer = $("#queryListView"),
                $iframeContainer = $('#mainContent'),
                $iframe = $("#mainIframe");

            //#75471  知识分类二级页面，当选择左侧的阅读记录后，刷新整个网页，右侧页面为空 by hejf
            //不知道为什么直接show()在没打开控制台的情况下会不展现，只能用setTimeout
            setTimeout(function(){
                $iframeContainer = $('#mainContent').show();
            },500);

            if(typeof(features.closeable) !== 'undefined'
                && !features.closeable ){
                var $close = $iframeContainer.find('.lui_list_mainContent_close');
                $close.hide();
            }
            if(url){
                loading = dialog.loading();
                if (openPageReisizeTimeout != null) {
                    window.clearTimeout(openPageReisizeTimeout);
                }

                //#83564：后台配置优化
                var headerH = $(".lui_portal_header").outerHeight();
                var contentH = document.documentElement.clientHeight - headerH - 35;
                var $newIframe = $('<iframe frameborder="no" scrolling="no" border="0" />')
                    .css({'width': '100%', 'min-height': contentH + "px"})
                    .appendTo($iframeContainer);
                $newIframe.attr('id', 'mainIframe');
                $newIframe.addClass(features.transition || 'fadeIn');

                $iframe.remove();
                ___showIframe($newIframe);

                // #144090 火狐游览器：用户只有默认权限，查看首页，知识分类排行和统计显示空白
                setTimeout(function () {
                    $newIframe.attr('src', url);

                    $newIframe.load(function () {
                        $queryContainer.hide();

                        $newIframe.removeClass(features.transition || 'fadeIn');
                        loading && loading.hide();
                    });
                }, 300);

                openPageReisizeTimer = window.setTimeout(openPageResizeTimerFunction, openPageReisizeCounter);
            }else{
                openPageResize();
            }
            var scrollTop = $('body').scrollTop();
            if(scrollTop > $queryContainer.offset().top + 100){
                $("html,body").animate( {
                    scrollTop : $queryContainer.offset().top
                }, 300);
            }
        };

        /**
         * 使用z-index+opacity显示/隐藏IFrame，display:none会导致在IFrame内部获取高度错误
         */
        function ___showIframe(iframe){
            //iframe.show()
            $(iframe).css({
                'position' : '',
                'top' : '',
                'left' : '',
                'z-index' : '',
                'opacity' : ''
            })
        }

        function ___hideIframe(iframe){
            //$(iframe).hide();
            $(iframe).css({
                'position' : 'absolute',
                'top' : '0',
                'left' : '0',
                'z-index' : '-1',
                'opacity' : '0'
            });
        }

        window.listExport = $export.listExport;
        window.listExportExOperation = $export.listExportExOperation;
        window.openWindowWithPost = $export.openWindowWithPost;

        window.hideExport = $export.hideExport;

    });
