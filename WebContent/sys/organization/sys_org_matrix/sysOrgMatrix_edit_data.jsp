<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.edit" sidebar="no">
    <template:replace name="title">
        <bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
            <ui:button text="${lfn:message('home.help')}" order="1"
                       onclick="Com_OpenWindow('sysOrgMatrix_edit_help.jsp');"></ui:button>
            <kmss:auth
                    requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=saveMatrixDataForm&fdId=${sysOrgMatrixForm.fdId}">
                <ui:button text="${lfn:message('button.save')}" order="2" onclick="saveMatrixData();"/>
            </kmss:auth>
            <ui:button text="${lfn:message('button.close') }" order="3" onclick="Com_CloseWindow();"/>
        </ui:toolbar>
    </template:replace>
    <template:replace name="head">
        <script language="JavaScript">
            // 当前版本
            window.curVersion = undefined;
            // 当前分组
            window.fdDataCateId = {};
            // 数据筛选（全局，但是只在当前版本生效）格式：{'列ID':['abc','def'],'列ID':'123'}
            window.filter = undefined;
            // 矩阵ID
            window.matrixId = '${sysOrgMatrixForm.fdId}';
            // 是否开启分组
            window.fdIsEnabledCate = '${sysOrgMatrixForm.fdIsEnabledCate}';
            // 用于渲染页面元素的矩阵数据
            window.MatrixResult = {
                'fdId': '${sysOrgMatrixForm.fdId}',
                'fdVersions': [],
                'fdDataCates': [],
                'fdRelationConditionals': [],
                'fdRelationResults': []
            };
            window.width = '${sysOrgMatrixForm.width}';
            // 模板下载路径
            window.downloadTemplateFormAction = "${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=downloadTemplate&fdId=";
            // 矩阵数据导出路径
            window.downloadMatrixDataFormAction = "${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=exportMatrixData&fdId=";
            // 多语言资源信息
            window.Msg_Info = {
                sysOrgMatrix_result_maxLen: '<bean:message bundle="sys-organization" key="sysOrgMatrix.result.maxLen"/>',
                errors_unknown: '<bean:message key="errors.unknown"/>',
                button_select: '<bean:message key="button.select"/>',
                button_delete: '<bean:message key="button.delete"/>',
                page_comfirmDelete: '<bean:message key="page.comfirmDelete"/>',
                button_ok: '<bean:message key="button.ok" />',
                button_cancel: '<bean:message key="button.cancel" />',
                button_clear: '<bean:message key="button.clear" />',
                delete_version: '<bean:message bundle="sys-organization" key="sysOrgMatrix.version.comfirmDelete"/>',
                select_notice: '<bean:message bundle="sys-organization" key="sysOrgMatrix.data.delete.notice"/>',
                button_replace: '<bean:message bundle="sys-organization" key="sysOrgMatrix.edit.batchReplace.button"/>',
                replace_note: '<bean:message bundle="sys-organization" key="sysOrgMatrix.edit.batchReplace.note"/>',
                filter_more: '<bean:message bundle="sys-organization" key="sysOrgMatrix.filter.more"/>',
                version_title: '<bean:message bundle="sys-organization" key="sysOrgMatrix.version.title"/>',
                version_updateAll: '<bean:message bundle="sys-organization" key="sysOrgMatrix.version.updateAll"/>',
                version_update: '<bean:message bundle="sys-organization" key="sysOrgMatrix.version.update"/>',
                setTemplate: '<bean:message bundle="sys-organization" key="sysOrgMatrix.setTemplate"/>',
                search_keyword: '<bean:message bundle="sys-organization" key="sysOrg.address.search.inputkeyword"/>'
            };
            // 页签集合，保存所有页签对象，可以通过版本名称获取
            window.matrixPanelArray = {};
            // 保存已经加载数据的页签
            window.matrixDataArray = {};
            // 加载数据JS
            Com_IncludeFile("data.js");
            Com_IncludeFile("dialog.js");
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
                /* 筛选 */
                window.addSearch = function (target, event) {
                    event.stopPropagation();
                    $('.sysOrgMartrixSearchItem').hide();
                    if ($(target).find('.sysOrgMartrixSearchItem').is(':hidden')) {
                        $(target).find('.sysOrgMartrixSearchItem').show().find('input').focus();
                        // 第一次加载筛选数据
                        searchBtn(target);
                    } else {
                        $(target).find('.sysOrgMartrixSearchItem').hide();
                    }
                }
                window.keySearch = function (target, event) {
                    event.stopPropagation();
                    if (event.keyCode == "13") {
                        searchBtn(target);
                    }
                }
                /* 筛选-阻止冒泡 */
                window.stopBub = function (event) {
                    event.stopPropagation();
                }
                window.searchBtn = function (target) {
                    var th = $(target).parents("th"),
                        tarValue = th.find('[name=keyword]').val(),
                        fieldId = th.data("field-id"),
                        ul = $(target).parent().find("ul.sysOrgMartrixSearchItemList");
                    ul.empty();
                    $.ajax({
                        url: "<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=filterData"/>",
                        type: "POST",
                        data: {
                            'matrixId': matrixId,
                            'version': curVersion,
                            'fieldId': fieldId,
                            'cateId': fdDataCateId[curVersion] || '',
                            'keyword': tarValue
                        },
                        dataType: 'json',
                        success: function (res) {
                            if (res.success) {
                                // 填充已经选中的过滤
                                var fields = undefined;
                                if (window.filter && window.filter[fieldId]) {
                                    fields = window.filter[fieldId];
                                }
                                for (let i = 0; i < res.data.length; i++) {
                                    var checked = "";
                                    if (fields && fields.indexOf(res.data[i].value) > -1) {
                                        checked = "checked";
                                    }
                                    ul.append($("<li/>").append($("<input type='checkbox' name='List_Selected' " + checked + "/>")
                                            .val(res.data[i].value)).append(res.data[i].text).unbind('click').click(function (event) {
                                            event.stopPropagation();
                                            filterData(this, event);
                                        })
                                    );
                                }
                                if (res.more) {
                                    ul.append("<li style='text-align: center;'><label>" + Msg_Info.filter_more + "</label></li>");
                                }
                                _resetTabHeight(th);
                            } else {
                                ul.append("<li><label>" + res.msg + "</label></li>");
                            }
                        }
                    });
                }
                /* 筛选-隐藏条件部分 */
                $(document).click(function () {
                    $(".sysOrgMartrixSearchItem").hide();
                    var tab = $("#lui_matrix_panel_content_" + curVersion + " > div.lui_matrix_data_tb_wrap > div.lui_matrix_data_tb_item");
                    if (tab.length > 1) {
                        $(tab[1]).css("height", "auto");
                    }
                });

                /* 数据筛选过滤 */
                window.filterData = function (elem, event) {
                    // 筛选项点击，{'列ID':['abc','def'],'列ID':'123'}
                    var cb = $(elem).find("input[type='checkbox']"),
                        th = $(elem).parents("th"),
                        icon = th.find("i.sysMatrix_tb_filterBtn");
                    if (event && event.target && event.target.tagName != 'INPUT') {
                        // 点击非多选框时，需要处理多选框的状态
                        cb.prop('checked', !cb.is(':checked'));
                    }
                    var th = $(elem).parents("th"),
                        fieldId = th.data("field-id"),
                        list = th.find("input:checked");
                    if (list.length > 0) {
                        var temp = [];
                        for (let j = 0; j < list.length; j++) {
                            temp.push(list[j].value);
                        }
                        if (temp.length > 0) {
                            window.filter = {};
                            window.filter[fieldId] = temp;
                        } else {
                            window.filter = undefined;
                        }
                    } else {
                        window.filter = undefined;
                    }
                    if (window.console) {
                        console.log("数据筛选条件：", window.filter);
                    }
                    // 移除其它样式
                    th.parent().find("i").removeClass("activate");
                    // 如果有过滤条件，增加样式
                    if (window.filter) {
                        icon.addClass("activate");
                    }
                    window.matrixPanelArray[window.curVersion].on("dataLoaded", function () {
                        _resetTabHeight(th);
                    });
                    // 重新加载数据
                    window.matrixPanelArray[window.curVersion].initData();
                }

                window._resetTabHeight = function (th) {
                    var ul = th.find("ul.sysOrgMartrixSearchItemList"),
                        tab = $("#lui_matrix_panel_content_" + curVersion + " > div.lui_matrix_data_tb_wrap > div.lui_matrix_data_tb_item"),
                        tab0 = $(tab[0]),
                        tab1 = $(tab[1]);
                    if (window.console) {
                        console.log("过滤器高度：", ul.height(), "，表格高度：", tab1.height(), "，序号高度：", tab0.height());
                    }
                    if (ul.height() >= tab.height() - 100) {
                        var newH = ul.height() + 120;
                        if (newH < tab0.height()) {
                            newH = tab0.height() + 9;
                        }
                        tab1.height(newH);
                    }
                    if (tab0.height() >= tab1.height()) {
                        tab1.css("height", "auto");
                    }
                }

                <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=updateVerState&fdId=${sysOrgMatrixForm.fdId}">
                /* 发布版本 */
                window.publishVer = function (isEnable) {
                    if (isEnable === 'false') {
                        // 需要先激活
                        $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=updateVerState" />', {
                            'fdMatrixId': window.matrixId,
                            'fdVersion': window.curVersion,
                            'fdIsEnable': true,
                            'fdId': window.matrixId
                        }, function (res) {
                            if (res.success) {
                                // 修改页面变量
                                for (var i = 0; i < MatrixResult.fdVersions.length; i++) {
                                    if (MatrixResult.fdVersions[i].fdName == window.curVersion) {
                                        MatrixResult.fdVersions[i].fdIsEnable = true;
                                    }
                                }
                                // 修改版本状态
                                var navTitle = $('[data-lui-mark="panel.nav.title"][title="' + window.curVersion + '"]');
                                navTitle.find("span.matrix_nonactivated").remove();
                                navTitle.find("i").removeClass("matrix_nonactivated");
                                _publishVer();
                            } else {
                                dialog.failure(res.msg);
                            }
                        }, 'json');
                    } else {
                        _publishVer();
                    }
                }

                /* 发布版本 */
                window._publishVer = function () {
                    var tip = $("#publishVerTip");
                    tip.find(".version").text(window.curVersion);
                    // 选择发布的模板
                    dialog.build({
                        config: {
                            width: 400,
                            height: 200,
                            lock: true,
                            title: Msg_Info.version_title,
                            content: {
                                type: "Html",
                                html: tip.html(),
                                buttons: [
                                    {
                                        name: Msg_Info.button_cancel,
                                        value: false,
                                        styleClass: 'lui_toolbar_btn_gray',
                                        fn: function (value, dialog) {
                                            dialog.hide(value);
                                        }
                                    },
                                    {
                                        name: Msg_Info.version_update,
                                        value: 1,
                                        styleClass: 'lui_toolbar_btn_gray',
                                        fn: function (value, dialog) {
                                            dialog.hide(value);
                                            publishSectionVer();
                                        }
                                    },
                                    {
                                        name: Msg_Info.version_updateAll,
                                        value: 2,
                                        fn: function (value, _dialog) {
                                            $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.do?method=updateTemplateVersion" />', {
                                                'matrixId': window.matrixId,
                                                'version': window.curVersion,
                                                'isAll': 'true',
                                                'fdId': window.matrixId
                                            }, function (res) {
                                                if (res.success) {
                                                    dialog.success('<bean:message key="return.optSuccess"/>');
                                                    topic.channel("maxtrix_relation").publish("list.refresh");
                                                } else {
                                                    dialog.failure(res.msg);
                                                }
                                                _dialog.hide(value);
                                            }, "json");
                                        }
                                    },
                                ]
                            }
                        },
                    }).show();
                }

                /* 发布部份版本 */
                window.publishSectionVer = function () {
                    dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrix_edit_data_template.jsp?fdId=" + window.MatrixResult.fdId + "&curVersion=" + window.curVersion,
                        Msg_Info.setTemplate, null, {
                            width: 900,
                            height: 600,
                            close: false,
                            buttons: [{
                                name: '<bean:message key="button.close"/>',
                                value: false,
                                fn: function (value, _dialog) {
                                    _dialog.hide();
                                    topic.channel("maxtrix_relation").publish("list.refresh");
                                }
                            }]
                        });
                }
                </kmss:auth>

                /* 增加一行 */
                window.addData = function () {
                    if (window.matrixPanelArray[window.curVersion]) {
                        window.matrixPanelArray[window.curVersion].addData();
                    } else {
                        dialog.alert("<bean:message bundle='sys-organization' key='sysOrgMatrix.version.empty'/>");
                    }
                }
                /* 删除一行 */
                window.delData = function (elem) {
                    if (window.matrixPanelArray[window.curVersion]) {
                        window.matrixPanelArray[window.curVersion].delData(elem);
                    } else {
                        dialog.alert("<bean:message bundle='sys-organization' key='sysOrgMatrix.version.empty'/>");
                    }
                }

                /* 批量删除 */
                window.delAllData = function () {
                    if (window.matrixPanelArray[window.curVersion]) {
                        window.matrixPanelArray[window.curVersion].delAllData();
                    } else {
                        dialog.alert("<bean:message bundle='sys-organization' key='sysOrgMatrix.version.empty'/>");
                    }
                }

                /* 导入(整个矩阵) */
                window.importData = function () {
                    window.open(Com_Parameter.ContextPath + "sys/organization/sys_org_matrix/sysOrgMatrix.do?method=importData&fdId=" + window.MatrixResult.fdId, "_blank");
                }

                /* 批量新增 */
                window.addMoreDatas = function () {
                    if (window.curVersion.indexOf("V") > -1) {
                        dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=addMoreDatas&fdId=" + window.MatrixResult.fdId + "&curVersion=" + window.curVersion,
                            '<bean:message key="sysOrgMatrix.edit.bulk" bundle="sys-organization"/>', null, {
                                width: 900,
                                height: 600,
                                buttons: [{
                                    name: '<bean:message key="button.ok"/>',
                                    value: true,
                                    focus: true,
                                    fn: function (value, _dialog) {
                                        var frame = _dialog.frame[0];
                                        var contentWin = $(frame).find("iframe")[0].contentWindow;
                                        contentWin.sumitAllData();
                                        topic.publish("buildTable", contentWin.resultList);
                                        _dialog.hide();
                                    }
                                }, {
                                    name: '<bean:message key="button.cancel"/>',
                                    styleClass: "lui_toolbar_btn_gray",
                                    value: false,
                                    fn: function (value, _dialog) {
                                        _dialog.hide();
                                    }
                                }]
                            });
                    } else {
                        dialog.alert("<bean:message key='sysOrgMatrix.edit.bulk.add.version.tip' bundle='sys-organization'/>");
                    }
                }

                /* 下载模板(整个矩阵) */
                window.downloadTemplate = function () {
                    $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getVersions" />', {'fdId': window.MatrixResult.fdId}, function (res) {
                        if (res.length < 1) {
                            dialog.alert("<bean:message bundle='sys-organization' key='sysOrgMatrix.version.nodata.error'/>");
                            return;
                        } else {
                            var downloadForm = $("#downloadTemplateForm");
                            downloadForm.attr("action", window.downloadTemplateFormAction + window.MatrixResult.fdId)
                            downloadForm.submit();
                        }
                    }, 'json');
                }
                /* 导出(整个矩阵) */
                window.exportData = function () {
                    $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getVersions" />', {'fdId': window.MatrixResult.fdId}, function (res) {
                        if (res.length < 1) {
                            dialog.alert("<bean:message bundle='sys-organization' key='sysOrgMatrix.version.nodata.error'/>");
                            return;
                        } else {
                            var downloadForm = $("#downloadMatrixDataForm");
                            downloadForm.attr("action", window.downloadMatrixDataFormAction + window.MatrixResult.fdId)
                            downloadForm.submit();
                        }
                    }, 'json');
                }

                /* 批量替换 */
                window.batchReplace = function () {
                    if (window.matrixPanelArray[window.curVersion]) {
                        window.matrixPanelArray[window.curVersion].batchReplace();
                    } else {
                        dialog.alert("<bean:message bundle='sys-organization' key='sysOrgMatrix.version.empty'/>");
                    }
                }
                /* 操作列tips切换 */
                $('.lui_text_primary.lui_matrix_link').hover(function () {
                    $(this).find('.sysOrgMatriTipsText').toggle();
                });

            });
        </script>
        <link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/table.css">
        <link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
    </template:replace>
    <template:replace name="content">
        <div style="width: 100%; margin: 30px auto;">
            <p class="txttitle">
                <bean:message bundle="sys-organization" key="sysOrgMatrix.data.edit"/>
                (<c:out value="${sysOrgMatrixForm.fdName}"/>)
            </p>

            <html:form action="/sys/organization/sys_org_matrix/sysOrgMatrix.do">
                <div class="lui_matrix_div_wrap">
                    <c:set var="titleicon" value=""/>
                    <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=deleteVersion&fdId=${sysOrgMatrixForm.fdId}">
                        <%-- 有权限时才显示“删除版本”按钮 --%>
                        <c:set var="titleicon" value="maxtri_btn_more"/>
                    </kmss:auth>
                    <ui:tabpanel id="lui_matrix_panel" layout="sys.ui.tabpanel.sucktop" var-average='false'
                                 var-useMaxWidth='true'>
                        <c:forEach items="${ sysOrgMatrixForm.fdVersions }" var="version">
                            <ui:content id="lui_matrix_panel_content_${ version.fdName }" title="${ version.fdName }"
                                        titleicon="maxtri_btn_more ${version.fdIsEnable ? '' : 'matrix_nonactivated'}">
                                <c:import
                                        url="/sys/organization/sys_org_matrix/sysOrgMatrix_edit_data_panel.jsp"
                                        charEncoding="UTF-8">
                                    <c:param name="fdMatrixId" value="${ sysOrgMatrixForm.fdId }"/>
                                    <c:param name="version" value="${ version.fdName }"/>
                                    <c:param name="fdIsEnable" value="${ version.fdIsEnable }"/>
                                </c:import>
                            </ui:content>
                        </c:forEach>
                        <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=addVersion&fdId=${sysOrgMatrixForm.fdId}">
                        <%-- 增加版本 --%>
                        <ui:content id="__add" title="+ ${lfn:message('sys-organization:sysOrgMatrix.addVersion')}" titleicon="maxtri_btn_add"></ui:content>
                        </kmss:auth>
                    </ui:tabpanel>
                        <%-- 矩阵与流程模板关系 --%>
                    <c:import url="/sys/organization/sys_org_matrix/sysOrgMatrixTemplate.jsp" charEncoding="UTF-8">
                        <c:param name="matrixId" value="${sysOrgMatrixForm.fdId}"></c:param>
                        <c:param name="type" value="edit"></c:param>
                    </c:import>
                </div>
            </html:form>
        </div>
        <!-- 临时数据，不需要提交，只限于本地临时使用 -->
        <form action="#" onsubmit="return false;" style="display: none;">
            <input type="hidden" name="__idField">
            <input type="hidden" name="__nameField">
        </form>
        <!-- 模板下载 -->
        <form id="downloadTemplateForm" method="post"></form>
        <!-- 矩阵数据下载 -->
        <form id="downloadMatrixDataForm" method="post"></form>
        <!-- 版本发布提示 -->
        <div id="publishVerTip" style="display: none;">
            <div class="public_version_tip">
                <div class="title"><bean:message bundle="sys-organization" key="sysOrgMatrix.version"/>
                    <span class="version"></span>
                    <bean:message bundle="sys-organization" key="sysOrgMatrix.template.updateSelect"/>
                </div>
                <div class="tip1">a / <span><bean:message bundle="sys-organization"
                                                          key="sysOrgMatrix.template.all"/></span><bean:message
                        bundle="sys-organization" key="sysOrgMatrix.template.update"/></div>
                <div class="tip2">b / <span><bean:message bundle="sys-organization"
                                                          key="sysOrgMatrix.template.sub"/></span><bean:message
                        bundle="sys-organization" key="sysOrgMatrix.template.update"/></div>
            </div>
        </div>
        <div id="MatrixConfigDiv" style="display: none;">
            ${MatrixConfig}
        </div>
        <script language="JavaScript">
            window.historyArray = [];
            window.lui_matrix_panel;
            var optVers = [];
            <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=copyVersion&fdId=${sysOrgMatrixForm.fdId}">
            // 复制版本
            optVers.push("copy");
            </kmss:auth>
            <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=deleteVersion&fdId=${sysOrgMatrixForm.fdId}">
            // 删除版本
            optVers.push("delete");
            </kmss:auth>
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic', 'lui/panel', 'sys/organization/resource/js/matrixPanel', 'lui/parser'], function ($, dialog, topic, panel, matrixPanel, parser) {
                validator = $KMSSValidation(document.forms['sysOrgMatrixForm']);
                // 数值区间校验
                validator.addValidator('numRange', "<bean:message key='sysOrgMatrix.range.form.error' bundle='sys-organization' />", function(v, e, o) {
                    var name = e.name, name2;
                    if(name.indexOf("_2") > -1) {
                        name2 = name;
                        name = name.substr(0, name.length -2);
                    } else {
                        name2 = name + "_2";
                    }
                    var val = $("[name='" + name + "']").val(),
                        val2 = $("[name='" + name2 + "']").val();
                    if(val == "" || val2 == "") {
                        return true;
                    } else {
                        return parseFloat(val) <= parseFloat(val2);
                    }
                });
                window.__initMatrixPanel = function() {
                    lui_matrix_panel = LUI("lui_matrix_panel");
                    // 页签渲染完，增加版本状态
                    lui_matrix_panel.on("layoutFinished", function () {
                        var __ver = {};
                        for(var i in MatrixResult.fdVersions) {
                            var ver = MatrixResult.fdVersions[i];
                            __ver[ver.fdName] = ver.fdIsEnable;
                        }
                        // 增加版本状态
                        this.navs.forEach(function (n) {
                            if(optVers.length == 0) {
                                $(n.navTitle).find("i").removeClass("maxtri_btn_more");
                            }
                            var _ver = $(n.navTitle).attr("title");
                            if(__ver[_ver]) {
                                return true;
                            }
                            if ($(n.navTitle).find("i").hasClass("matrix_nonactivated")) {
                                $(n.navTitle).append('<span class="matrix_nonactivated"><bean:message bundle="sys-organization" key="sysOrgMatrix.version.nonactivated"/></span>');
                            }
                        });
                    });
                    lui_matrix_panel.on("indexChanged", function (evt) {
                        if(this['noSave']) {
                            evt['noSave'] = this['noSave'];
                            this['noSave'] = undefined;
                        }
                        setTimeout(function () {
                            panelChange(evt);
                        }, 200);
                    });

                    // 针对页面渲染速度慢的浏览器，增加一些延时的操作
                    setTimeout(function () {
                        lui_matrix_panel.navs.forEach(function (n) {
                            if($(n.navTitle).find("span.matrix_nonactivated").length == 0) {
                                if ($(n.navTitle).find("i").hasClass("matrix_nonactivated")) {
                                    $(n.navTitle).append('<span class="matrix_nonactivated"><bean:message bundle="sys-organization" key="sysOrgMatrix.version.nonactivated"/></span>');
                                }
                            }
                        });
                    }, 500);
                }

                lui_matrix_panel = LUI("lui_matrix_panel");
                if(lui_matrix_panel) {
                    __initMatrixPanel();
                } else {
                    LUI.ready(function () {
                        __initMatrixPanel();
                    });
                }

                /* 生成分组标签 */
                var __to__;
                window.generateCateData = function () {
                    var panel_content = $("#lui_matrix_panel [data-lui-mark='panel.content']");
                    if (panel_content.length > 0) {
                        if (__to__) {
                            clearTimeout(__to__);
                        }
                        panel_content.each(function (i, n) {
                            generateCate($(n));
                        });
                    } else {
                        __to__ = setTimeout(function () {
                            generateCateData();
                        }, 100);
                    }
                }

                window.generateCate = function (elem) {
                    var dataCate = elem.find(".matrix_data_cate");
                    if (dataCate.length > 0) {
                        var cates = [];
                        for (var i = 0; i < MatrixResult.fdDataCates.length; i++) {
                            var data = MatrixResult.fdDataCates[i];
                            cates.push('<a class="com_bgcolor_d" href="javascript:;" style="color: #333;" data-cateid="' + data.fdId + '" onclick="switchCateData(this, \'' + data.fdId + '\');"><pre>' + data.fdName + '</pre></a>');
                        }
                        dataCate.html(cates.join(""));
                    }
                    selectFirstCate();
                }

                window.selectFirstCate = function () {
                    if (MatrixResult.fdDataCates.length > 0) {
                        var cateContent = $("#lui_matrix_panel_content_" + curVersion);
                        var item = cateContent.find(".lui_maxtrix_cate_item_dis");
                        if (item.length > 0) {
                            window.fdDataCateId[curVersion] = item.data("cateid");
                        } else {
                            // 选择第一个分组
                            var cate = cateContent.find(".matrix_data_cate a:first");
                            cate.addClass("lui_maxtrix_cate_item_dis");
                            window.fdDataCateId[curVersion] = cate.data("cateid");
                        }
                    }
                }

                initPanel = function (version) {
                    window.curVersion = version || MatrixResult.fdVersions[0].fdName;
                    var __panel = window.matrixPanelArray[window.curVersion];
                    if (window.console) {
                        console.log("所有页签：", window.matrixPanelArray);
                        console.log("当前版本：", window.curVersion);
                        console.log("当前页签：", __panel);
                    }
                    if (__panel) {
                        window.fdDataCateId[curVersion] = window.fdDataCateId[curVersion] || (MatrixResult.fdDataCates.length > 0 ? MatrixResult.fdDataCates[0].fdId : '');
                        __panel.setCateId(window.fdDataCateId[curVersion]);
                        // 生成表格
                        __panel.initDataTab();
                        // 判断是否有分组，有分组需要按分组取数据
                        selectFirstCate();
                        // 填充数据
                        __panel.initData();
                    } else if (lui_matrix_panel) {
                        lui_matrix_panel.setSelectedIndex(0);
                    }
                }
                panelChange = function (evt) {
                    // 获取当前点击的页签
                    var cur = evt.panel.contents[evt.index.after];
                    if (!cur) {
                        evt.cancel = true;
                        return false;
                    }
                    var temp = cur.config.title;
                    if (cur.id != "__add" && window.curVersion == temp) {
                        // 取消切换，防止复制渲染
                        evt.cancel = true;
                        return false;
                    } else if (window.curVersion == temp) {
                        return false;
                    }
                    // 页签切换，保存上一个页签的数据
                    var last = evt.panel.contents[evt.index.before];
                    if (!evt['noSave'] && last) {
                        var lastVer = last.config.title;
                        saveLastVerData(lastVer);
                    }
                    // 版本切换，清空筛选数据
                    window.filter = undefined;
                    initPanel(temp);
                }

                // 解析矩阵配置信息
                var MatrixConfig = JSON.parse($("#MatrixConfigDiv").text());
                MatrixResult.fdVersions = MatrixConfig.versions;
                MatrixResult.fdDataCates = MatrixConfig.cates;
                MatrixResult.fdRelationConditionals = MatrixConfig.conditionals;
                MatrixResult.fdRelationResults = MatrixConfig.results;
                if (MatrixResult.fdDataCates.length > 0) {
                    window.fdDataCateId[MatrixResult.fdVersions[0].fdName] = MatrixResult.fdDataCates[0].fdId;
                }
                if (!window.curVersion) {
                    if (window.console) {
                        console.log("页面初始加载");
                    }
                    initPanel();
                }
                // 生成分组
                generateCateData();

                <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=addVersion&fdId=${sysOrgMatrixForm.fdId}">
                // 增加版本
                $("#lui_matrix_panel").on("click", ".lui_tabpanel_navs_item_title", function (evt) {
                    if ($(evt.target).parent().find(".maxtri_btn_add").length > 0) {
                        $.post(Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=addVersion&fdMatrixId=' + window.matrixId + "&fdId=" + window.matrixId, function (res) {
                            if (window.console) {
                                console.log("增加新版本：", res);
                            }
                            if (res.success) {
                                // 先保存数据
                                saveLastVerData(window.curVersion);
                                // 增加版本
                                addVersion(res.version);
                                // 页面变量增加版本
                                MatrixResult.fdVersions.push({"fdName": res.version, "fdIsEnable": false});
                            } else {
                                dialog.failure(res.msg);
                            }
                        }, 'json');
                    }
                });
                // 增加版本
                addVersion = function (version, noSave) {
                    var max = $("#lui_matrix_panel").find("[data-lui-mark='panel.nav.title']").length - 1;
                    var _panel = lui_matrix_panel;
                    // 当前页签
                    var curNavFrame = lui_matrix_panel.navs[max].navFrame;
                    var newVer = version;
                    var newId = "lui_matrix_panel_content_" + newVer;
                    var newNavFrame = $('<div style="max-width:16.6%;" class="lui_tabpanel_sucktop_navs_item_l" data-lui-mark="panel.nav.frame" data-lui-switch-class="lui_tabpanel_sucktop_navs_item_selected">' +
                        '<div class="lui_tabpanel_sucktop_navs_item_r">' +
                        '<div class="lui_tabpanel_sucktop_navs_item_c" data-lui-mark="panel.nav.title" title="' + newVer + '">' +
                        '<i class="lui_panel_title_icon maxtri_btn_more matrix_nonactivated"></i>' +
                        '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">' + newVer + '</span>' +
                        '<span class="matrix_nonactivated"><bean:message bundle="sys-organization" key="sysOrgMatrix.version.nonactivated"/></span>' +
                        '</div></div></div>');
                    // 设置页签标题
                    newNavFrame.find(".lui_tabpanel_navs_item_title").text(newVer);
                    // 调整页签位置
                    curNavFrame.before(newNavFrame);
                    // 增加一个页签
                    var content = new panel.Content({
                        "title": newVer,
                        "id": newId,
                        "titleicon": "maxtri_btn_more matrix_nonactivated"
                    });
                    _panel.addChild(content);
                    _panel.children[max + 1] = _panel.children.splice(max + 2, 1, _panel.children[max + 1])[0];
                    _panel.contents[max] = _panel.contents.splice(max + 1, 1, _panel.contents[max])[0];
                    // 重新渲染页签
                    content.parent = _panel;
                    content.startup();
                    _panel.doLayout($("#lui_matrix_panel"));
                    // 选中新增加的页签
                    if(noSave) {
                        _panel['noSave'] = noSave;
                    }
                    // 填充页签模板
                    $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix_edit_data_panel.jsp?fdMatrixId=${sysOrgMatrixForm.fdId }" />', {
                        'version': newVer,
                        'fdIsEnable': false
                    }, function (html) {
                        var __content = $("#lui_matrix_panel_content_" + newVer);
                        __content.html(html);
                        // 解析页面断片（主要是解析分页组件）
                        parser.parse(__content[0]);
                        // 生成分组
                        generateCate(__content);
                        // 只生成表格，不填充数据
                        var __panel = window.matrixPanelArray[window.curVersion || MatrixResult.fdVersions[0].fdName];
                        if (__panel) {
                            __panel.initDataTab(true);
                        }
                        _panel.setSelectedIndex(max);
                    }, 'html');
                }
                </kmss:auth>
                <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=copyVersion&fdId=${sysOrgMatrixForm.fdId}">
                // 复制版本
                cloneVersion = function (event) {
                    event.stopPropagation();
                    var version = $(this).data("version");
                    if (window.console) {
                        console.log("复制版本：", version);
                    }
                    if (version == window.curVersion) {
                        // 如果是复制当前版本，需要先保存，再复制
                        saveCateData(window.curVersion, fdDataCateId[curVersion], '', function (res) {
                            $.post(Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=copyVersion&fdMatrixId=' + window.matrixId + '&fdVersion=' + version + "&fdId=" + window.matrixId, function (res) {
                                if (res.success) {
                                    // 在此模式下，由于已经做了保存操作，在切换版时需要不能再保存了，否则会出现数据重复
                                    addVersion(res.version, true);
                                } else {
                                    dialog.failure(res.msg);
                                }
                            }, 'json');
                        });
                    } else {
                        $.post(Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=copyVersion&fdMatrixId=' + window.matrixId + '&fdVersion=' + version + "&fdId=" + window.matrixId, function (res) {
                            if (res.success) {
                                addVersion(res.version);
                            } else {
                                dialog.failure(res.msg);
                            }
                        }, 'json');
                    }
                }
                </kmss:auth>
                <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=deleteVersion&fdId=${sysOrgMatrixForm.fdId}">
                // 删除版本
                deleteVersion = function (event) {
                    event.stopPropagation();
                    var __count = $("#lui_matrix_panel").find("[data-lui-mark='panel.nav.frame']").length;
                    if (__count <= 2) {
                        dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.version.delete.warn"/>');
                        return false;
                    }
                    var version = $(this).data("version");
                    dialog.confirm(Msg_Info.delete_version, function (value) {
                        if (value == true) {
                            if (window.console) {
                                console.log("删除版本：", version);
                            }
                            $.post(Com_Parameter.ContextPath + 'sys/organization/sys_org_matrix/sysOrgMatrix.do?method=deleteVersion&fdId=${sysOrgMatrixForm.fdId}', {'fdVersion': version}, function (res) {
                                dialog.result(res);
                                if (res.status) {
                                    // 删除标题
                                    $("#lui_matrix_panel").find("[data-lui-mark='panel.nav.frame']").each(function (i, n) {
                                        var _nav = $(n).find("[title='" + version + "']");
                                        if (_nav.length > 0) {
                                            $(n).remove();
                                        }
                                    });
                                    // 删除内容
                                    $("#lui_matrix_panel_content_" + version).parents("[data-lui-mark='panel.content']").remove();
                                    // 删除组件对象
                                    for (var i = 0; i < lui_matrix_panel.children.length; i++) {
                                        var child = lui_matrix_panel.children[i];
                                        if (child.config.title == version) {
                                            lui_matrix_panel.children.splice(i, 1);
                                            break;
                                        }
                                    }
                                    for (var i = 0; i < lui_matrix_panel.contents.length; i++) {
                                        var content = lui_matrix_panel.contents[i];
                                        if (content.config.title == version) {
                                            lui_matrix_panel.contents.splice(i, 1);
                                            break;
                                        }
                                    }
                                    // 重新渲染页面样式
                                    lui_matrix_panel.doLayout($("#lui_matrix_panel"));
                                }
                            }, 'json');
                        }
                    });
                }
                </kmss:auth>

                /* 更新多操作按钮 */
                $("#lui_matrix_panel").on("mouseenter", ".maxtri_btn_more", function (evt) {
                    $('.maxtri_btn_list').remove();
                    if ($(this).parent().children('.maxtri_btn_list').length == 0) {
                        var ver = $(this).parent().find(".lui_tabpanel_navs_item_title").text();
                        var btnList = $("<ul class='maxtri_btn_list'/>");
                        <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=copyVersion&fdId=${sysOrgMatrixForm.fdId}">
                        // 复制版本
                        btnList.append($("<li class='maxtri_btn_clone'>").data("version", ver).text('<bean:message bundle="sys-organization" key="sysOrgMatrix.version.copy"/>').click(cloneVersion));
                        </kmss:auth>
                        <kmss:auth requestURL="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=deleteVersion&fdId=${sysOrgMatrixForm.fdId}">
                        // 删除版本
                        btnList.append($("<li class='maxtri_btn_delete'>").data("version", ver).text('<bean:message bundle="sys-organization" key="sysOrgMatrix.version.delete"/>').click(deleteVersion));
                        </kmss:auth>
                        $(this).parent().append(btnList);
                    }
                });
                $("#lui_matrix_panel").on("mouseleave", ".lui_tabpanel_sucktop_navs_item_r", function (evt) {
                    $('.maxtri_btn_list').remove();
                });

                saveLastVerData = function (version) {
                    var _panel = $("#lui_matrix_panel_content_" + version);
                    if (_panel && _panel.length > 0) {
                        var cateid = undefined, catename = undefined;
                        if (fdIsEnabledCate == 'true') {
                            var cate = _panel.find(".lui_maxtrix_cate_item_dis");
                            if (cate.length == 0 && window.fdDataCateId[curVersion]) {
                                cate = _panel.find("[data-cateid='" + window.fdDataCateId[curVersion] + "']");
                            }
                            if (cate) {
                                cateid = cate.data("cateid");
                                catename = cate.text();
                            }
                            if (!cateid) {
                                // 开启分组，但是没有取到分组ID，忽略保存
                                return false;
                            }
                        }
                        saveCateData(version, cateid, catename, function (res) {
                            if (res) {
                                // 清空页面数据
                                $("#matrix_data_table_" + version).find("tr:gt(0)").remove();
                                $("#matrix_seq_table_" + version).find("tr:gt(0)").remove();
                                $("#matrix_opt_table_" + version).find("tr:gt(0)").remove();
                            }
                        });
                    }
                }

                /* 保存提交操作 */
                saveMatrixData = function () {
                    var _panel = $("#lui_matrix_panel_content_" + window.curVersion);
                    if (_panel && _panel.length > 0) {
                        var cateid = undefined, catename = undefined;
                        if (fdIsEnabledCate == 'true') {
                            var cate = _panel.find(".lui_maxtrix_cate_item_dis");
                            if (cate.length == 0 && window.fdDataCateId[curVersion]) {
                                cate = _panel.find("[data-cateid='" + window.fdDataCateId[curVersion] + "']");
                            }
                            if (cate) {
                                cateid = cate.data("cateid");
                                catename = cate.text();
                            }
                            if (!cateid) {
                                dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.update.error"/>');
                                // 开启分组，但是没有取到分组ID，忽略保存
                                return false;
                            }
                        }
                        saveCateData(window.curVersion, cateid, catename, function (res) {
                            Com_OpenWindow('<c:url value="/resource/jsp/success.jsp" />', '_self');
                        });
                    }
                }

                // ==================== 以下为公共方法（注意公共方法取页面元素时，需要指定当前版本） ==================

                /* 判断字符串结尾 */
                window.endWith = function (str, target) {
                    var start = str.length - target.length;
                    var arr = str.substr(start, target.length);
                    if (arr == target) {
                        return true;
                    }
                    return false;
                }

                /* 去除数组中的空字符串 */
                window.trimSpace = function (array) {
                    for (var i = 0; i < array.length; i++) {
                        if (array[i] == "" || array[i] == " " || array[i] == null || typeof (array[i]) == "undefined") {
                            array.splice(i, 1);
                            i = i - 1;
                        }
                    }
                    return array;
                }

                /* 唯一校验提示 */
                window.uniqueError = function (val) {
                    dialog.failure("<bean:message bundle='sys-organization' key='sysOrgMatrixRelation.fdIsUnique.err.left'/>" + val + "<bean:message bundle='sys-organization' key='sysOrgMatrixRelation.fdIsUnique.err.right'/>");
                }

                /* 地址本 */
                window.Dialog_Address_Cust = function (mulSelect, idField, nameField, splitStr, selectType, action) {
                    var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
                    var _idField = curArea.find("input[name='" + idField + "']");
                    var _nameField = curArea.find("input[name='" + nameField + "']");
                    var _tmplField = curArea.find("div[data-name='" + nameField + "']");

                    // 往临时表单填充数据
                    $("input[name='__idField']").val(_idField.val());
                    $("input[name='__nameField']").val(_nameField.val());

                    Dialog_Address(mulSelect, "__idField", "__nameField", splitStr, selectType, function (result) {
                        if (action) {
                            action(result, idField, nameField);
                        } else {
                            if (result.data.length > 0) {
                                var rowNum = _idField.parent().parent().parent().prevAll().length;
                                var ___idField = idField.replace(/\[[^\]]+\]/g, '');
                                var checked = checkUnique(___idField, rowNum, result.data[0].id);
                                if (checked) {
                                    var ids = [], names = [];
                                    for (var i = 0; i < result.data.length; i++) {
                                        ids.push(result.data[i].id);
                                        names.push(result.data[i].name);
                                    }
                                    _idField.val(ids.join(";"));
                                    _nameField.val(names.join(";"));
                                    _tmplField.html(names.join(";"));
                                } else {
                                    uniqueError(result.data[0].name);
                                }
                            } else {
                                _idField.val("");
                                _nameField.val("");
                                _tmplField.html("");
                            }
                        }
                        // 清除临时表单填充数据
                        $("input[name=__idField]").val("");
                        $("input[name=__nameField]").val("");
                    }, null, null, null, null, null, null, null);
                }

                /* 常量唯一性校验 */
                window.checkconstant = function (elem) {
                    var val = $(elem).val(),
                        name = $(elem).attr("name"),
                        name = name.replace(/\[[^\]]+\]/g, ''),
                        rowNum = $(elem).parent().parent().prevAll().length;
                    if (!checkUnique(name, rowNum, val)) {
                        $(elem).val("");
                        uniqueError(val);
                    }
                }

                /* 数值区间唯一性校验 */
                window.checknumRange = function(elem) {
                    var val = $(elem).val(),
                        name = $(elem).attr("name"),
                        name = name.replace(/\[[^\]]+\]/g, ''),
                        rowNum = $(elem).parent().parent().prevAll().length;
                    if(!checkUnique(name, rowNum, val)) {
                        $(elem).val("");
                        uniqueError(val);
                    }
                }

                /* 唯一性校验 */
                window.checkUnique = function(field, rowNum, value) {
                    var checked = true;
                    value = value.replace(/(^\s*)|(\s*$)/g, "");
                    if (value.length == 0) {
                        // 空数据不校验
                        return checked;
                    }
                    if (window.MatrixResult.fdRelationConditionals) {
                        for (var i = 0; i < window.MatrixResult.fdRelationConditionals.length; i++) {
                            var con = window.MatrixResult.fdRelationConditionals[i];
                            if (field == con.fdId && "true" == String(con.fdIsUnique)) {
                                var id = $("#matrix_seq_table_" + window.curVersion + " tr:eq(" + rowNum + ")").find("[type=checkbox]").val();
                                // 检查后台数据
                                var data = new KMSSData();
                                data.UseCache = false;
                                data.AddBeanData("sysOrgMatrixService&type=unique&matrixId=${sysOrgMatrixForm.fdId}&field=" + field + "&version=" + window.curVersion + "&id=" + id + "&value=" + window.encodeURIComponent(value));
                                var rtn = data.GetHashMapArray();
                                if (rtn.length > 0) {
                                    checked = false;
                                }
                                if (checked) {
                                    // 如果后台数据没有重复，还需要检查页面数据
                                    var tab = $("#matrix_data_table_" + window.curVersion),
                                        th = tab.find("th[data-field='" + con.fdFieldName + "']"),
                                        idx = th.prevAll().length;
                                    tab.find("tr").each(function (i, n) {
                                        var val = $(n).find("td:eq(" + idx + ")").find("[name^=" + field + "]").val();
                                        if (value == val && rowNum != i) {
                                            checked = false;
                                            return false;
                                        }
                                    });
                                }
                                break;
                            }
                        }
                    }
                    return checked;
                }

                /* 检查结果集数量 */
                window.resultCheck = function (rtnVal, idField, nameField) {
                    var curArea = $("#lui_matrix_panel_content_" + window.curVersion),
                        __idField = curArea.find("input[name='" + idField + "']"),
                        __nameField = curArea.find("input[name='" + nameField + "']");
                    if (rtnVal && rtnVal.data) {
                        // 大于27组数据，强制裁剪
                        if (rtnVal.data.length > 27) {
                            rtnVal.data = rtnVal.data.slice(0, 27);
                            // 弹出提示信息
                            dialog.alert(Msg_Info.sysOrgMatrix_result_maxLen);
                        }
                        var ids = [], names = [];
                        for (var i = 0; i < rtnVal.data.length; i++) {
                            ids.push(rtnVal.data[i].id);
                            names.push(rtnVal.data[i].name);
                        }
                        __idField.val(ids.join(";"));
                        __nameField.val(names.join(";"));
                    } else {
                        __idField.val("");
                        __nameField.val("");
                    }
                }

                /* 主要处理人+岗位的数据 */
                window.resultCheck2 = function (rtnVal, idField, nameField) {
                    var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
                    var split = idField.split("_"),
                        id = split[0],
                        type = split[1],
                        field = curArea.find("input[name='" + id + "']"),
                        __idField = curArea.find("input[name='" + idField + "']"),
                        __nameField = curArea.find("input[name='" + nameField + "']"),
                        value = field.val() || "{}",
                        json = JSON.parse(value);

                    var __postId, __postName;
                    // 如果选择的是人员，需要把岗位带出来
                    if (idField.indexOf("_person") > 0) {
                        var postIdField = idField.replace(/person/g, "post");
                        var __post = $(curArea).find("div[name='" + postIdField + "']");
                        __postId = __post.find("input[name='" + postIdField + "']"),
                            __postName = __post.find("input[name='" + nameField.replace(/person/g, "post") + "']");
                    }
                    if (rtnVal && rtnVal.data && rtnVal.data.length > 0) {
                        // 增加或替换
                        json[type] = rtnVal.data[0].id;
                        __idField.val(rtnVal.data[0].id);
                        __nameField.val(rtnVal.data[0].name);
                        if (__postId) {
                            // 根据人员获取该人员的岗位信息
                            var data = new KMSSData();
                            data.UseCache = false;
                            data.AddBeanData("sysOrgMatrixService&type=get_post&person=" + rtnVal.data[0].id);
                            var rtn = data.GetHashMapArray();
                            if (rtn.length > 0) {
                                var postId = rtn[0].postId;
                                var postName = rtn[0].postName;
                                // 岗位信息填充到页面
                                json['post'] = postId;
                                __postId.val(postId);
                                __postName.val(postName);
                            } else {
                                delete json['post'];
                                __postId.val("");
                                __postName.val("");
                            }
                        }
                    } else {
                        // 删除
                        delete json[type];
                        __idField.val("");
                        __nameField.val("");
                        if (__postId) {
                            delete json['post'];
                            __postId.val("");
                            __postName.val("");
                        }
                    }
                    field.val(JSON.stringify(json));
                }

                /* 自定义数据 */
                window.Dialog_CustData = function (mulSelect, idField, nameField, splitStr, treeBean, treeTitle) {
                    var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
                    var _idField = curArea.find("input[name='" + idField + "']");
                    var _nameField = curArea.find("input[name='" + nameField + "']");
                    // 往临时表单填充数据
                    $("input[name='__idField']").val(_idField.val());
                    $("input[name='__nameField']").val(_nameField.val());
                    Dialog_Tree(mulSelect, "__idField", "__nameField", splitStr, treeBean, treeTitle, null, function (result) {
                        if (result.data.length > 0) {
                            var rowNum = _idField.parent().parent().parent().prevAll().length;
                            var ___idField = idField.replace(/\[[^\]]+\]/g, '');
                            var checked = checkUnique(___idField, rowNum, result.data[0].id);
                            if (checked) {
                                var ids = [], names = [];
                                for (var i = 0; i < result.data.length; i++) {
                                    ids.push(result.data[i].id);
                                    names.push(result.data[i].name);
                                }
                                _idField.val(ids.join(";"));
                                _nameField.val(names.join(";"));
                            } else {
                                uniqueError(result.data[0].name);
                            }
                        } else {
                            _idField.val("");
                            _nameField.val("");
                        }
                        // 清除临时表单填充数据
                        $("input[name=__idField]").val("");
                        $("input[name=__nameField]").val("");
                    });
                }

                /* 系统主数据 */

                window.Dialog_MainData = function (fieldId, fieldName, title) {
                    var curArea = $("#lui_matrix_panel_content_" + window.curVersion);
                    var _idField = curArea.find("input[name='" + fieldId + "']");
                    var selected = _idField.val();
                    // fieldName过滤[X]字符
                    var _fieldName = fieldName.replace(/\[[^\]]+\]/g, '');
                    dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrixData_mainData.jsp?matrixId=" + window.MatrixResult.fdId + "&fieldName=" + _fieldName + "&selected=" + selected,
                        title, function (data) {
                            if (data) {
                                if (data == "clear") {
                                    curArea.find("input[name='" + fieldId + "']").val("");
                                    curArea.find("input[name='" + fieldName + "']").val("");
                                } else {
                                    var rowNum = _idField.parent().parent().parent().prevAll().length;
                                    var ___idField = fieldId.replace(/\[[^\]]+\]/g, '');
                                    var checked = checkUnique(___idField, rowNum, data.id);
                                    if (checked) {
                                        curArea.find("input[name='" + fieldId + "']").val(data.id);
                                        curArea.find("input[name='" + fieldName + "']").val(data.name);
                                    } else {
                                        uniqueError(data.name);
                                    }
                                }
                            }
                        }, {
                            width: 1200,
                            height: 600,
                            buttons: [{
                                name: Msg_Info.button_ok,
                                focus: true,
                                fn: function (value, dialog) {
                                    if (dialog.frame && dialog.frame.length > 0) {
                                        var frame = dialog.frame[0];
                                        var contentDoc = $(frame).find("iframe")[0].contentDocument;
                                        $(contentDoc).find("input[name='List_Selected']:checked").each(function (i, n) {
                                            value = {};
                                            value.id = $(n).val();
                                            value.name = $(n).parent().parent().find("td.mainData_title:first").text();
                                            return true;
                                        });
                                    }
                                    setTimeout(function () {
                                        dialog.hide(value);
                                    }, 200);
                                }
                            }, {
                                name: Msg_Info.button_cancel,
                                styleClass: 'lui_toolbar_btn_gray',
                                fn: function (value, dialog) {
                                    dialog.hide();
                                }
                            }, {
                                name: Msg_Info.button_clear,
                                styleClass: 'lui_toolbar_btn_gray',
                                fn: function (value, dialog) {
                                    dialog.hide("clear");
                                }
                            }]
                        });
                }

                /* 分组数据切换 */
                window.switchCateData = function (elem, dataCateId) {
                    var parent = $(elem).parent(),
                        cate = parent.find(".lui_maxtrix_cate_item_dis"),
                        cateid = cate.data("cateid"),
                        catename = cate.text();
                    // 禁用本按钮，启用其它按钮
                    if ($(elem).hasClass("lui_maxtrix_cate_item_dis")) {
                        return false;
                    }
                    parent.find("a").each(function (i, n) {
                        $(n).removeClass("lui_maxtrix_cate_item_dis");
                    });
                    $(elem).addClass("lui_maxtrix_cate_item_dis");
                    // 保存原分组数据，保存成功后，加载新分组数据
                    saveCateData(window.curVersion, cateid, catename, function (res) {
                        if (res) {
                            // 清空页面数据
                            $("#matrix_data_table_" + window.curVersion).find("tr:gt(0)").remove();
                            $("#matrix_seq_table_" + window.curVersion).find("tr:gt(0)").remove();
                            $("#matrix_opt_table_" + window.curVersion).find("tr:gt(0)").remove();
                            // 重新加载分组数据
                            window.fdDataCateId[curVersion] = dataCateId;
                            var _panel = window.matrixPanelArray[window.curVersion];
                            if (_panel) {
                                _panel.setCateId(window.fdDataCateId[curVersion]);
                                _panel.initData();
                            }
                        }
                    });
                }

                /* 保存分组数据 */
                window.saveCateData = function (version, cateid, catename, callback) {
                    // 表单校验
                    if(!validator.validate()) {
                        return false;
                    }
                    if (window.console) {
                        console.log("保存分组数据：", version, cateid, catename);
                    }
                    var datas = [];
                    $("#matrix_seq_table_" + version + " tbody").find("tr:gt(0)").each(function (i, n) {
                        var obj = {};
                        var fdId = $(n).find("[type=checkbox]").val();
                        if (fdId.length > 0 && fdId != 'on' && fdId.indexOf("new_") == -1) {
                            obj['fdId'] = fdId;
                        }
                        var hasVal = false;
                        $("#matrix_data_table_" + version).find("tbody tr:eq(" + (i + 1) + ")").find("[data-type=fieldId]").each(function (j, m) {
                            var name = $(m).attr("name").replace(/\[[^\]]+\]/g, '');
                            var value = $(m).val();
                            if (value.length > 0) {
                                hasVal = true;
                                obj[name] = value;
                            }
                        });
                        if (hasVal) {
                            datas.push(obj);
                        }
                    });
                    if (datas.length == 0) {
                        if (callback) {
                            callback(true);
                        }
                        return false;
                    }
                    $.ajax({
                        url: '${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=saveMatrixDataByCate',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            'matrixId': window.MatrixResult.fdId,
                            'version': version,
                            'cateId': cateid || '',
                            'matrixData': JSON.stringify(datas)
                        },
                        success: function (res) {
                            if (callback) {
                                callback(res);
                            } else {
                                if (!res.status) {
                                    dialog.failure(res.msg);
                                }
                            }
                        },
                        error: function () {
                            dialog.failure(Msg_Info.errors_unknown);
                        }
                    });
                }

                $(function () {
                    topic.subscribe('buildTable', function (evt) {
                        if (evt && evt.length > 0) {
                            if (window.matrixPanelArray) {
                                if (window.console) {
                                    console.log("批量增加：", evt);
                                }
                                // 填充数据
                                window.matrixPanelArray[window.curVersion || "V1"].initDataByBulkAdd(panel.page, evt);
                            }
                        }
                    });
                });
            });
        </script>
    </template:replace>
</template:include>