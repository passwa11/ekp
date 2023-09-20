<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
            copyToText = '${lfn:message('sys-modeling-base:modeling.no.flow')}';
        } else {
            //无流程
            $head.append($("<i/>").addClass('form_popup_trigger').addClass("flow_icon no_flow_icon").css("postion", "relative"));
            copyToText = '${lfn:message('sys-modeling-base:modeling.flow')}';
        }

        //更新样式
        var $block = $("<div class='appMenu_item_block'>").appendTo(node);
        $block.append('<div class="appMenu_main"><div class="appMenu_main_icon" style="top:60px"><i class="iconfont_nav ' + data.icon + '"></i></div><div style="top:122px" class="appMenu_main_title">' + data.name + '</div></div>');
        //遮罩
        var $cover = $('<div class="appMenu_main_cover">');
        $block.append($cover);
        var $edit = $('<div class="modeling_app_edit">');
        var $btnBar = $('<div class="modeling_app_btn_bar">');
        var $moreBtn = $('<div class="modeling_app_btn modeling_app_btn_more" title="${lfn:message('sys-modeling-base:modeling.more')}">');
        var $outBtn = $('<div class="modeling_app_btn modeling_app_btn_out" title="${lfn:message('sys-modeling-base:enums.operation.def.7')}">');
        var $propertyBtn = $('<div class="modeling_app_btn modeling_app_btn_property" title="${lfn:message('sys-modeling-base:modelingImportKey.property')}">');
        $edit.append('<i class="modeling_app_icon"></i> <span class="modeling_app_text">${lfn:message('sys-modeling-base:enums.operation.def.3')}</span>');
        var moreBtnStr = '<div class="modeling_app_icon_item"><i class="modeling_app_icon"></i><div class="modeling_app_btn_title">${lfn:message('sys-modeling-base:modeling.more')}</div>' +
            '<div class="form_popup_4text form_popup">' +
            '    <ul class="modeling_app_operation_alert">';
        moreBtnStr = moreBtnStr + '<li data-script-click="sameCopy">${lfn:message('sys-modeling-base:modeling.page.sameCopy')}</li>';
    if ("3" == mode) {
            //#136221 多表不支持复制
            moreBtnStr = moreBtnStr + '<li data-script-click="copy">${lfn:message('sys-modeling-base:modeling.page.copy')}' + copyToText + '${lfn:message('sys-modeling-base:table.modelingAppModel')}</li>';
        }
        moreBtnStr = moreBtnStr + '<li data-script-click="delete">${lfn:message('sys-modeling-base:modeling.page.delete')}</li>' +
            '    </ul>' +
            '</div></div>';
        $moreBtn.append(moreBtnStr)
        $outBtn.append('<div class="modeling_app_icon_item"><i class="modeling_app_icon"></i><div class="modeling_app_btn_title">${lfn:message('sys-modeling-base:enums.operation.def.7')}</div></div>');
        $propertyBtn.append('<div class="modeling_app_icon_item"><i class="modeling_app_icon"></i><div class="modeling_app_btn_title">${lfn:message('sys-modeling-base:modelingImportKey.property')}</div></div>');
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
                }else if(type === "sameCopy"){
                    var formId = $(this).parents("li").eq(0).attr("data-formid");
                    sameCopyFunc(formId);
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
            dialog.iframe(dialogUrl, "${lfn:message('sys-modeling-base:modeling.form.FormProperties')}", null, {width: 900, height: 500, close: true});
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

    window.sameCopyFunc = function(formId){
        if(formInfos.length >= appInfos.maxNum){
             dialog.alert("${lfn:message('sys-modeling-base:modelingLicense.up.to.30.forms')}");
        }else{
            dialog.confirm("${lfn:message('sys-modeling-base:modeling.page.confirm.sameCopy')}"+"?", function (value) {
                if (value === true) {
                    window._loading = dialog.loading();
                    $.ajax({
                        url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=copyForm&fdAppModelId=" + formId+"&isSameCopy=true",
                        type: "GET",
                        dataType: "json",
                        success: function (rs) {
                            if (rs.status) {
                                dialog.success("${lfn:message('sys-modeling-base:modeling.form.copy.complete')}");
                                LUI("formList").doRefresh()
                            } else {
                                dialog.failure(rs);
                            }
                            if (window._loading != null)
                                window._loading.hide();
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            if (XMLHttpRequest.status === 403) {
                                dialog.failure("${lfn:message('sys-modeling-base:modeling.form.OperateTips')}");
                            } else {
                                dialog.failure(textStatus);
                            }
                            if (window._loading != null)
                                window._loading.hide();
                        }
                    });
                }
            });
        }
    }

    window.copyFunc = function (formId, copyToText) {
        if(formInfos.length >= appInfos.maxNum){
         dialog.alert("${lfn:message('sys-modeling-base:modelingLicense.up.to.30.forms')}");
          }else{
            var msg ="${lfn:message('sys-modeling-base:modeling.page.confirm.copy')}" + copyToText + "${lfn:message('sys-modeling-base:table.modelingAppModel')}"+"?";
            if(copyToText == "${lfn:message('sys-modeling-base:modeling.no.flow')}"){
                msg ="${lfn:message('sys-modeling-base:modeling.page.confirm.copy')}" + copyToText + "${lfn:message('sys-modeling-base:table.modelingAppModel')}"+"?"+"${lfn:message('sys-modeling-base:modeling.model.copyFormTips')}";
            }
            dialog.confirm(msg, function (value) {
            if (value === true) {
                window._loading = dialog.loading();
                $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=copyForm&fdAppModelId=" + formId,
                    type: "GET",
                    dataType: "json",
                    success: function (rs) {
                        if (rs.status) {
                            dialog.success("${lfn:message('sys-modeling-base:modeling.form.copy.complete')}");
                            LUI("formList").doRefresh()
                        } else {
                            dialog.failure(rs);
                        }
                        if (window._loading != null)
                            window._loading.hide();
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        if (XMLHttpRequest.status === 403) {
                            dialog.failure("${lfn:message('sys-modeling-base:modeling.form.OperateTips')}");
                        } else {
                            dialog.failure(textStatus);
                        }
                        if (window._loading != null)
                            window._loading.hide();
                    }
                });
            }
          });
         }
    };

    window.deleteFunc = function (formId) {
        dialog.confirm("${lfn:message('sys-modeling-base:modeling.form.DeleteTips')}", function (value) {
            if (value === true) {
                var requestType = "get";
                $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/modelingAppModel.do?method=deleteByAjax&fdId=" + formId,
                    type: requestType,
                    jsonp: "jsonpcallback",
                    success: function (rs) {
                        if (rs.status === "00") {
                            dialog.success("“" + rs.data.dialog.fdName + "”${lfn:message('sys-modeling-base:modeling.form.del.complete')}");
                            LUI("formList").doRefresh()
                        } else if (rs.status === "02") {
                            //弹框提示
                            var url = '/sys/modeling/base/resources/jsp/dialog_relation.jsp';
                            dialog.iframe(url, "${lfn:message('sys-modeling-base:modelingAppListview.relatedDialogTitle')}", function (data) {
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
                            dialog.failure("${lfn:message('sys-modeling-base:modeling.form.OperateTips')}");
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
var viewTypeForm = data.viewTypeForm;
if (data == null || data.length == 0) {
    done();
} else {
    if(formInfos.length>0){
        if(viewTypeForm == "1"){
            $(".listTableButton").hide();
            var ul = $('<div>').attr('class', 'form_operation_ul lui_profile_listview_card_page').appendTo(element);
            if (appInfos.selfBuild != false){
                ul.append(buildFixBox());
            }
            // 构建固定无流程表单和有流程表达方块
            for (var i = 0; i < formInfos.length; i++) {
                var node = buildNode(formInfos[i]);
                node.appendTo(ul);
            }
            if (appInfos.selfBuild == false){
                $('[data-script-click=copy]').css('display','none');
                $('[data-script-click=sameCopy]').css('display','none');
            }
        }else{
            $(".listTableButton").show();
            var $div = $("<div></div>");
            var $table = $("<table class='app_listView_table'><tr><th width='30%'>${lfn:message('sys-modeling-base:modelingBehaviorLog.fdAppModelName')}</th><th width='140'>${lfn:message('sys-modeling-base:modelingAppListview.docCreator')}</th><th  width='220'>${lfn:message('sys-modeling-base:modelingAppListview.docCreateTime')}</th><th width='220'>${lfn:message('sys-modeling-base:modelingAppView.docAlterTime')}</th><th width='140'>${lfn:message('sys-modeling-base:modeling.form.type')}</th><th width='220'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</th></tr></table>")
            $div.append($table);
            for (var i = 0; i < formInfos.length; i++) {
                var formData = formInfos[i];
                var name = formData.name;
                var id = formData.id;
                var icon = formData.icon;
                var type = formData.type;
                var docCreateTime = formData.docCreateTime;
                var docCreatorName = formData.docCreatorName;
                var mode = formData.mode | "3";
                var docAlterTime = formData.docAlterTime;
                var copyToText;
                var formFlowText ;
                var isEnableFlow = formData.type === true || formData.type === 1 || formData.type === "1";
                if (isEnableFlow) {
                    //有流程
                    copyToText = '${lfn:message('sys-modeling-base:modeling.no.flow')}';
                    formFlowText = '${lfn:message('sys-modeling-base:modeling.flow')}';
                } else {
                    //无流程
                    copyToText = '${lfn:message('sys-modeling-base:modeling.flow')}';
                    formFlowText ='${lfn:message('sys-modeling-base:modeling.no.flow')}';
                }
                var $tr = $("<tr></tr>");
                $tr.append("<td>"+name+"</td><td>"+docCreatorName+"</td><td>"+docCreateTime+"</td><td>"+docAlterTime+"</td><td>"+formFlowText+"</td>");
                $buttonTd = $("<td></td>");
                $edit = $("<a class='btn_txt_app' data-formlist-boxtype='edit' data-formlist-id='"+id+"'>${lfn:message('sys-modeling-base:enums.operation.def.3')}</a>")
                var $propertyBtn = $("<a class='btn_txt_app' data-formlist-boxtype='property' data-formlist-id='"+id+"'>${lfn:message('sys-modeling-base:modelingImportKey.property')}</a>")
                var $moreBtn = $("<a class='btn_txt_app modeling_form_table_operation' data-formlist-id='"+id+"'>${lfn:message('sys-modeling-base:modeling.more')}</a>");

                var moreBtnStr = "<span class='form_table_more_button' style='display: none'><ul class='buttonOptionList'> <li data-table-oper-method='export' data-formlist-formInfosLength='"+formInfos.length+"' data-formlist-copyToText='"+copyToText+"' data-formlist-id='"+id+"'>${lfn:message('sys-modeling-base:enums.operation.def.7')}</li>";
                if(appInfos.selfBuild == true){
                   moreBtnStr = moreBtnStr + "<li data-table-oper-method='sameCopy' data-formlist-formInfosLength='"+formInfos.length+"'  data-formlist-copyToText='' data-formlist-id='"+id+"'>${lfn:message('sys-modeling-base:modeling.page.sameCopy')}</li>";
                }
                if (mode == "3" &&  appInfos.selfBuild == true) {
                    moreBtnStr = moreBtnStr + "<li data-table-oper-method='copy' data-formlist-formInfosLength='"+formInfos.length+"'  data-formlist-copyToText='"+copyToText+"' data-formlist-id='"+id+"'>${lfn:message('sys-modeling-base:modeling.page.copy')}" + copyToText + "${lfn:message('sys-modeling-base:table.modelingAppModel')}</li>";
                 }
                 moreBtnStr = moreBtnStr + "<li data-table-oper-method='delete' data-formlist-formInfosLength='"+formInfos.length+"' data-formlist-copyToText='"+copyToText+"' data-formlist-id='"+id+"'>${lfn:message('sys-modeling-base:modeling.page.delete')}</li></ul>";
                 $moreBtn.append($(moreBtnStr));
                $buttonTd.append($edit);
                $buttonTd.append($propertyBtn);
                $buttonTd.append($moreBtn);
                $tr.append($buttonTd);
                $table.append($tr);
            }
            $(element).html($div);
        }
    }else{
        //#170639 业务表单卡片、列表模式，通过关键字搜索无结果时，建议应用列表、卡片保持一致 增加缺省页
        var $emptyDiv = $("<div class='form_empty' style='margin-top: 70px'><div class='form_empty_img'><div></div><p>${lfn:message('sys-modeling-base:modeling.norecords.tip')}</p></div></div>");
        $(element).html($emptyDiv);
    }
}

function buildFixBox() {
    var html = "";
    var nodes = [{
        name: "${lfn:message('sys-modeling-base:modeling.form.AddFlowlessForm')}",
        value: "1",
        icon: "no_flow_icon"
    }, {
        name: "${lfn:message('sys-modeling-base:modeling.form.AddFlowForm')}",
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