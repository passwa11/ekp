/**
 * 查看页面中的TAB（列表视图、查看视图）绑定点击自适应iframe高度的事件（因为在TAB展开前无法正确获取其窗口内容高度）
 */
let supportExpand = false;
function bindUiContent() {
    //是否有标签
    let tabDiv = $('div[data-lui-type="lui/panel!TabPage"]');
    if (tabDiv.length === 0) {
        tabDiv = $('div[data-lui-type="lui/panel!TabPanel"]');
        supportExpand = true;
        if (tabDiv.length === 0) {
            return;
        }
    }
    let interval = setInterval(bindUiContentInterval, 50);

    function bindUiContentInterval() {
        let navItems;
        if (supportExpand) {
            navItems = $('div[data-lui-mark="panel.nav.frame"]');
        } else {
            navItems = $('.lui_tabpage_float_nav_item')
        }
        if (navItems.length === 0)
            return;
        clearInterval(interval);
        bindUiContentItem(navItems);
    }

    function bindUiContentItem(navItems) {
        navItems.each(function (i) {
            $(this).on('click', function () {
                navItemClick(i);
            });
            //#125233，草稿状态下，右侧流程审批不显示
            let tabPageContents;
            if (supportExpand) {
                tabPageContents = $('div[data-lui-mark="panel.content"]:eq(' + i + ')');
            } else {
                tabPageContents = $(".lui_tabpage_float_content:eq(" + i + ")");
            }
            if($(this).hasClass("lui_tabpanel_vertical_icon_navs_item_selected")){
                if($(this).css("display") == "none"){
                    tabPageContents.css("display","none");
                    $(this).next().trigger("click");
                }
            }
        });
    }

    function navItemClick(index) {
        let tabPageContents;
        if (supportExpand) {
            tabPageContents = $('div[data-lui-mark="panel.content"]:eq(' + index + ')');
        } else {
            tabPageContents = $(".lui_tabpage_float_content:eq(" + index + ")");
        }
        //隐藏时不执行自适应
        if(tabPageContents.css("display") === "none" || tabPageContents.css("position") === "absolute")
            return;
        let iframe = $("iframe[name^='if_']", tabPageContents);
        if (iframe[0]) {
            iframe[0].contentWindow.iframeAutoFit();
        }
    }
}

$(function(){
    bindUiContent();
});