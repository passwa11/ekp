var element = render.parent.element;
if (data == null || data.length == 0) {
    done();
} else {
    seajs.use(['sys/modeling/main/ui/modelingUtil', 'lui/framework/router/router-utils'],
        function (modelingUtil, routerUtils) {
            var ul = $('<ul>').attr('class', 'lui_list_nav_list').appendTo(element);
            var path = routerUtils.getRouter(true)._getHashPath() || "";
            var isDynamicModel = path.indexOf("-dynamic") > -1 ? true : false;
            for (var i = 0; i < data.length; i++) {
                var node = buildNode(data[i]);
                // 默认第一个模块被选中
                // 当前链接是否有路由
                if (!path) {
                    if (i == 0) {
                        if (!window.portalURL) {
                            node.trigger($.Event("click"));
                        }
                    }
                } else {
                    if (isDynamicModel) {
                        if (modelingUtil.equalPath(data[i])) {
                            $(node).addClass('lui_list_nav_selected');
                        }
                    } else if (routerUtils.equalPath(data[i].href)) {
                        $(node).addClass('lui_list_nav_selected');
                    }
                }

                node.appendTo(ul);
            }
        });
    //监听清除组件内部选中项事件
    seajs.use(['lui/topic'], function (topic) {
        topic.subscribe('nav.operation.clearStatus', function (evt) {
            $(".lui_list_nav_list > li", render.parent.element).removeClass("lui_list_nav_selected");
        });
    });
}

function buildNode(data) {
    var node = $('<li/>'),
        link = $('<a />').appendTo(node),
        $icon = $('<i/>');

    $icon.addClass('iconfont');
    $icon.addClass(data.icon);
    //console.log("data",data);
    link.append($icon);
    if (data.icon) {
        $icon.addClass(data.icon);
    }
    link.append($('<span />').text(data.text));
    node.attr("title",data.text);
    //node.attr("data-path",data.href);

    node.on('click', function () {
        //移除导航选中状态
        LUI.$("[data-lui-type*=AccordionPanel] li").removeClass("lui_list_nav_selected");
        //移除导航头部选中状态
        seajs.use(['lui/topic'], function (topic) {
            topic.publish("nav.operation.clearStatus", null);
        });
        $(this).addClass('lui_list_nav_selected');
        if (data.router) {
            seajs.use(['lui/framework/router/router-utils'],
                function (routerUtils) {
                    try {
                        var $router = routerUtils.getRouter();
                        $router.push(data.href, data.params || {});
                    } catch (e) {
                        console.error("导航点击事件绑定失效", e)
                    }

                });
        } else {
            if (data.href.toLowerCase().indexOf("javascript:") > -1) {
                location.href = data.href;
            } else {
                LUI.pageOpen(data.href, data.target || '_blank');
            }
        }
    });
    return node;
}
