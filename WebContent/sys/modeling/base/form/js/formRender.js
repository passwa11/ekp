/**
 *
 */
seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
    window.buildNode = function (data) {
        var node = $('<li/>').attr("class", "lui_profile_block_grid_item itemStyle_1");
        node.css("overflow", 'visible');
        node.attr("data-formid", data.id);
        $head = $("<div class='form_item_head'/>").appendTo(node);
        var mode = data.mode | "3";
        var isEnableFlow = data.type === true || data.type === 1 || data.type === "1";
        var copyToText;
        if (isEnableFlow) {
            //有流程
            $head.append($("<i/>").addClass('form_popup_trigger').addClass("flow_icon has_flow_icon").css("postion", "relative"));
            copyToText = '无流程';
        } else {
            //无流程
            $head.append($("<i/>").addClass('form_popup_trigger').addClass("flow_icon no_flow_icon").css("postion", "relative"));
            copyToText = '有流程';
        }

        //更新样式
        var $block = $("<div class='appMenu_item_block'>").appendTo(node);
        $block.append('<div class="appMenu_main"><div class="appMenu_main_icon" style="top:60px"><i class="iconfont_nav ' + data.icon + '"></i></div><div style="top:122px" class="appMenu_main_title">' + data.name + '</div></div>');
        //遮罩
        var $cover = $('<div class="appMenu_main_cover">');
        $block.append($cover);
        var $edit = $('<div class="modeling_app_edit">');
        var $btnBar = $('<div class="modeling_app_btn_bar">');
        var $moreBtn = $('<div class="modeling_app_btn modeling_app_btn_more" title="更多">');
        var $outBtn = $('<div class="modeling_app_btn modeling_app_btn_out" title="导出">');
        var $propertyBtn = $('<div class="modeling_app_btn modeling_app_btn_property" title="属性">');
        $edit.append('<i class="modeling_app_icon"></i> <span class="modeling_app_text">编辑</span>');
        var moreBtnStr = '<div class="modeling_app_icon_item"><i class="modeling_app_icon"></i><div class="modeling_app_btn_title">更多</div>' +
            '<div class="form_popup_4text form_popup">' +
            '    <ul class="modeling_app_operation_alert">';
        if ("3" == mode) {
            //#136221 多表不支持复制
            moreBtnStr = moreBtnStr + '<li data-script-click="copy">复制为' + copyToText + '表单</li>';
        }
        moreBtnStr = moreBtnStr + '<li data-script-click="delete">删除</li>' +
            '    </ul>' +
            '</div></div>';
        $moreBtn.append(moreBtnStr)
        $outBtn.append('<div class="modeling_app_icon_item"><i class="modeling_app_icon"></i><div class="modeling_app_btn_title">导出</div></div>');
        $propertyBtn.append('<div class="modeling_app_icon_item"><i class="modeling_app_icon"></i><div class="modeling_app_btn_title">属性</div></div>');
        $btnBar.append($moreBtn);
        $btnBar.append($outBtn);
        $btnBar.append($propertyBtn);
        $cover.append($edit);
        $cover.append($btnBar);
       //#142907 暂时先做成hover显示
       /* $moreBtn.on('click', function () {
            $(this).toggleClass("form_popup_expand");
        });*/
        $moreBtn.find("[data-script-click]").each(function (index, dom) {
            var type = $(dom).attr("data-script-click");
            $(dom).on("click", function (event) {
                stopBubble(event);
                if (type === "copy") {
                    var formId = $(this).parents("li").eq(0).attr("data-formid");
                    copyFunc(formId, copyToText);
                } else if (type === "delete") {
                    var formId = $(this).parents("li").eq(0).attr("data-formid");
                    deleteFunc(formId);
                }
            });
        });

        $outBtn.on('click', function () {
            var formId = $(this).parents("li").eq(0).attr("data-formid");
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method=exportForm&fdModelId=" + formId;
            if ($('#exportDownLoadIframe').length > 0) {
                $('#exportDownLoadIframe')[0].src = url;
            } else {
                var elemIF = document.createElement("iframe");
                elemIF.id = "exportDownLoadIframe";
                elemIF.src = url;
                elemIF.style.display = "none";
                document.body.appendChild(elemIF);
            }
        });
        $propertyBtn.on('click', function () {
            var formId = $(this).parents("li").eq(0).attr("data-formid");
            var dialogUrl = "/sys/modeling/base/modelingAppModel.do?method=viewFormInfo&fdId=" + formId;
            dialog.iframe(dialogUrl, "表单属性", null, {width: 900, height: 500, close: true});
        });
        $edit.on('click', function () {
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=frame&fdId=" + data.id;
            top.open(url, "_self");
        });
        $block.hover(
            function () {
                $cover.addClass("hover");
            },
            function () {
                $cover.removeClass("hover");
            }
        );
        return node;
    };

    window.copyFunc = function (formId, copyToText) {
        dialog.confirm("确认将表单复制为" + copyToText + "表单？", function (value) {
            if (value === true) {
                window._loading = dialog.loading();
                $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=copyForm&fdAppModelId=" + formId,
                    type: "GET",
                    dataType: "json",
                    success: function (rs) {
                        if (rs.status) {
                            dialog.success("表单复制完成！");
                            LUI("formList").doRefresh()
                        } else {
                            dialog.failure(rs);
                        }
                        if (window._loading != null)
                            window._loading.hide();
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if (XMLHttpRequest.status === 403) {
                            dialog.failure("您没有该操作权限！");
                        } else {
                            dialog.failure(textStatus);
                        }
                        if (window._loading != null)
                            window._loading.hide();
                    }
                });
            }
        });
    };

    window.deleteFunc = function (formId) {
        dialog.confirm("一旦选择了删除，所选记录的相关数据都会被删除，无法恢复！您确认要执行此删除操作吗？", function (value) {
            if (value === true) {
                var requestType = "get";
                $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=deleteByAjax&fdId=" + formId,
                    type: requestType,
                    jsonp: "jsonpcallback",
                    success: function (rs) {
                        if (rs.status === "00") {
                            dialog.success("“" + rs.data.dialog.fdName + "”表单已删除!");
                            LUI("formList").doRefresh()
                        } else if (rs.status === "02") {
                            //弹框提示
                            var url = '/sys/modeling/base/resources/jsp/dialog_relation.jsp';
                            dialog.iframe(url, "删除关联模块", function (data) {
                            }, {
                                width: 600,
                                height: 400,
                                params: {relatedDatas: rs.data.dialog, delObjType: 'form'}
                            });
                        } else {
                            dialog.failure(rs.errmsg);
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if (XMLHttpRequest.status === 403) {
                            dialog.failure("您没有该操作权限！");
                        } else {
                            dialog.failure(textStatus);
                        }
                    }
                });
            }
        });
    };
    window.stopBubble = function (e) {
        //如果提供了事件对象，则这是一个非IE浏览器
        if (e && e.stopPropagation)
        //因此它支持W3C的stopPropagation()方法
            e.stopPropagation();
        else
        //否则，我们需要使用IE的方式来取消事件冒泡
            window.event.cancelBubble = true;
    }
});
var element = render.parent.element;
$(element).html("");
var formInfos = data.formInfos;
if (data == null || data.length == 0) {
    done();
} else {
    var ul = $('<ul>').attr('class', 'form_operation_ul lui_profile_listview_card_page').appendTo(element);
    ul.append(buildFixBox());	// 构建固定无流程表单和有流程表达方块
    for (var i = 0; i < formInfos.length; i++) {
        var node = buildNode(formInfos[i]);
        node.appendTo(ul);
    }
}

function buildFixBox() {
    var html = "";
    var nodes = [{
        name: "新建无流程表单",
        value: "1",
        icon: "no_flow_icon"
    }, {
        name: "新建流程表单",
        value: "2",
        icon: "has_flow_icon"
    }];
    for (var i = 0; i < nodes.length; i++) {
        html += "<li class='lui_profile_block_grid_item lui_profile_block_grid_item_add' onclick='createForm(\"" + nodes[i].value + "\");'>";
        html += "<div class='app_add_plus'>";
        html += "<i class='create_icon " + nodes[i].icon + "'></i>";
        html += "</div>";
        html += "<div class='app_create_des'>" + nodes[i].name + "</div>";
        html += "</li>";
    }
    return html;
}