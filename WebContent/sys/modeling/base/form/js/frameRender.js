/**
 *
 */
var element = render.parent.element;

var ul = $('<ul>').attr('class', 'model-sidebar-wrap').appendTo(element);
for (var i = 0; i < data.length; i++) {
    var node = buildNode(data[i], ul);
    //node.appendTo(ul);
}
ul.after("<br class='hermite'/>")
function buildNode(data, container) {
    var node = $('<li />');
    if (container)
        node.addClass("model-sidebar-item");
    else
        node.addClass("model-sidebar-child-item");
    node.attr("title",data.text);
    var itemBox = $('<div class="model-sidebar-item-box" />');
    itemBox.append('<i class="icon iconfont_modeling ' + data.icon + '"></i>');
    $('<span class="model-sidebar-desc"/>').text(data.text).appendTo(itemBox);
    itemBox.appendTo(node);
    if (data.show === "false") {
        return;
    }
    if (data.href === "#expansion") {
        node.addClass("model-item-expansion ");
        var childrenData = data.children;
        var childUl = $('<ul>').attr('class', 'model-sidebar-wrap model-sidebar-wrap-child');
        var childRouterSelected = false;
        for (var i = 0; i < childrenData.length; i++) {
            seajs.use(['lui/framework/router/router-utils'], function (routerUtils) {
                var router = routerUtils.getRouter(true);
                var curPath = router._getHashPath();
                if (curPath === childrenData[i].href) {
                    node.addClass("item-expansion");
                    childUl.addClass('item-expansion-child')
                }
            });
            var childrenNode = buildNode(childrenData[i]);
            childUl.append(childrenNode)
        }
        //二级
        node.on('click', function () {
            LUI.$("li").removeClass("aside_selected");
            $(this).addClass('aside_selected');
            $(this).toggleClass('item-expansion');
            LUI.$(".model-item-expansion").removeClass("this-sidebar-item ");
            $(this).find(".model-sidebar-wrap-child").toggleClass('item-expansion-child');
        });

        childUl.appendTo(node);
        node.appendTo(container);

        // 首次进来时
        seajs.use(['lui/framework/router/router-utils'], function (routerUtils) {
            var router = routerUtils.getRouter(true);
            var curPath = router._getHashPath();
            if (!curPath) {
                if (data.key === "form") {
                    $(node).toggleClass('item-expansion');
                    childUl.toggleClass('item-expansion-child');
                }
            }
        });
    } else {
        /*	seajs.use(['lui/framework/router/router-utils'],function(routerUtils){
            if(routerUtils.equalPath(data.href)
                    || routerUtils.isParentPath(data.href)){
                $(node).addClass('head_selected');
            }
        });*/

    	// 首次进来时
        seajs.use(['lui/framework/router/router-utils'], function (routerUtils) {
            var router = routerUtils.getRouter(true);
            var curPath = router._getHashPath();
            if (curPath) {
                if (curPath === data.href) {
                    $(node).addClass('aside_selected');
                }
            } else {
                if (data.href === "/form_base_design") {
                    $(node).addClass('aside_selected');
                }
            }
        });

        node.on('click', function () {
             LUI.$("li").removeClass("aside_selected");
             $(this).addClass('aside_selected');
            LUI.$(".model-item-expansion").removeClass("this-sidebar-item ");
            $(this).parent().parent(".model-item-expansion").addClass('this-sidebar-item')
            seajs.use(['lui/framework/router/router-utils'], function (routerUtils) {
                var $router = routerUtils.getRouter();
                $router.push(data.href, data.params || {});
            });
            event.stopPropagation()
        });
        node.appendTo(container);
    }

    return node;
}