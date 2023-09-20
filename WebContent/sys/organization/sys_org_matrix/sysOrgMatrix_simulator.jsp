<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="content">
        <p class="txttitle" style="margin: 30px 0;"><bean:message bundle="sys-organization"
                                                                  key="sysOrgMatrix.simulator"/></p>
        <center>
            <table class="tb_normal" width=95%>
                <tr>
                    <td class="td_normal_title" width=20%>
                        <bean:message bundle="sys-organization" key="table.sysOrgMatrix"/>
                    </td>
                    <td width="80%" colspan="3">
                        <input type="hidden" name="matrixId">
                        <input type="text" name="matrixName" class="inputsgl" readonly="readonly">
                        <a href="#"
                           onclick="Dialog_Tree(false, 'matrixId', 'matrixName', null, 'sysOrgMatrixService&parent=!{value}', '${lfn:message('sys-organization:sysOrgMatrix.simulator.select')}', null, selectMatrix);">
                            <bean:message key="button.select"/>
                        </a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <select id="version" name="version" style="display: none;"></select>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width=20%>
                        <bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.conditional"/>
                    </td>
                    <td width="80%">
                        <table id="fdConditionals" class="tb_normal" width="100%">
                            <tr class="tr_normal_title">
                                <td width="10%" align="center">
                                    <span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
                                </td>
                                <td width="40%">
                                    <bean:message bundle="sys-organization"
                                                  key="sysOrgMatrix.simulator.conditional.type"/>
                                </td>
                                <td width="40%">
                                    <bean:message bundle="sys-organization"
                                                  key="sysOrgMatrix.simulator.conditional.value"/>
                                </td>
                                <td width="10%">
                                    <img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add"
                                         onclick="if(checkMatrix())DocList_AddRow('fdConditionals', Conditional_Row);"
                                         style="cursor:pointer">
                                </td>
                            </tr>
                            <!--基准行-->
                            <tr KMSS_IsReferRow="1" style="display:none">
                                <td KMSS_IsRowIndex="1" align="center"></td>
                                <td></td>
                                <td>
                                    <input type="hidden" name="id[!{index}]">
                                    <input type="text" name="name[!{index}]" class="inputsgl" style="width:70%">
                                    <a href="#" name="select[!{index}]"
                                       onclick="selectConditional('id[!{index}]', 'name[!{index}]', this);"
                                       style="display: none;">
                                        <bean:message key="dialog.selectOrg"/>
                                    </a>
                                </td>
                                <td>
                                    <div style="text-align:center">
                                        <img src="<c:url value="/resource/style/default/icons/delete.gif"/>"
                                             name="__del" alt="del"
                                             onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);"
                                             style="cursor:pointer">&nbsp;&nbsp;
                                        <img src="<c:url value="/resource/style/default/icons/up.gif"/>" alt="up"
                                             onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);"
                                             style="cursor:pointer">&nbsp;&nbsp;
                                        <img src="<c:url value="/resource/style/default/icons/down.gif"/>" alt="down"
                                             onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);"
                                             style="cursor:pointer">
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width=20%>
                        <bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.result"/>
                    </td>
                    <td width="80%">
                        <input type="hidden" name="resultId">
                        <input type="text" name="resultName" class="inputsgl" readonly="readonly" style="width:70%;">
                        <a href="javascript:selectResult();"><bean:message key="button.select"/></a>
                        &nbsp;&nbsp;&nbsp;
                        <ui:button text="${lfn:message('sys-organization:sysOrgRoleConf.simulator.calculate')}"
                                   onclick="startCalculate();"></ui:button>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width=20%>
                        <bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.option"/>
                    </td>
                    <td width=80%>
                        <label><input type="radio" name="option" value="1" checked="checked"/><bean:message
                                bundle="sys-organization" key="sysOrgMatrix.simulator.option1"/></label>
                        <label><input type="radio" name="option" value="2"/><bean:message bundle="sys-organization"
                                                                                          key="sysOrgMatrix.simulator.option2"/></label>
                        <label><input type="radio" name="option" value="3"/><bean:message bundle="sys-organization"
                                                                                          key="sysOrgMatrix.simulator.option3"/></label>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width=20%>
                        <bean:message bundle="sys-organization" key="sysOrgRoleConf.simulator.result"/>
                    </td>
                    <td width=80% id="TD_Result"></td>
                </tr>
                <tr>
                    <td class="td_normal_title" width=20%>
                        <bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.help"/>
                    </td>
                    <td width=80%>
                        <bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.help.text1"/><br><br>
                        <bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.help.text2"/><br><br>
                        <bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.help.text3"/><br><br>
                    </td>
                </tr>
            </table>
        </center>
        <script>
            Com_IncludeFile('dialog.js|doclist.js|data.js|jquery.js|plugin.js');
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                window.Conditional_Row = [null, null];
                window.Conditional_Last = undefined;
                window.Conditional = {};

                // 选择矩阵
                window.selectMatrix = function (rtnVal) {
                    if (rtnVal && rtnVal.data.length > 0) {
                        var matrixId = rtnVal.data[0].id;
                        if (matrixId != Conditional_Last) {
                            // 清空条件和结果
                            $("#fdConditionals").find("tr").each(function (i, n) {
                                if (i > 0) {
                                    $(n).find("[name=__del]").click();
                                }
                            });
                            $("input[name=resultId]").val("");
                            $("input[name=resultName]").val("");

                            // 获取条件字段
                            var data = new KMSSData();
                            data.UseCache = false;
                            data.AddBeanData("sysOrgMatrixService&id=" + matrixId + "&rtnType=1");
                            var rtn = data.GetHashMapArray();
                            // 动态生成一个下拉框
                            if (rtn.length > 0) {
                                var select = [];
                                select.push('<select name="conditional[!{index}]" onchange="conditionalChange(this.value, this);" style="width:70%">');
                                select.push('<option value=""><bean:message key="page.firstOption"/></option>');
                                for (var i = 0; i < rtn.length; i++) {
                                    Conditional[rtn[i].value] = rtn[i];
                                    select.push('<option value="' + rtn[i].value + '" data-type="' + rtn[i].type + '" data-maindata="' + (rtn[i].mainDataType || '') + '" data-fieldname="' + (rtn[i].fieldName || '') + '">' + rtn[i].text + '</option>');
                                }
                                select.push('</select>');
                                select.push('<select name="conditional_type[!{index}]" style="display: none;">');
                                select.push('<option value="fdId">ID</option>');
                                select.push('<option value="fdName"><bean:message key="model.fdName"/></option>');
                                select.push('</select>');
                                Conditional_Row[1] = select.join("");
                            }
                            // 保存上一次选择的矩阵
                            Conditional_Last = matrixId;
                            // 获取版本号
                            $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=getVersions" />', {'fdId': matrixId}, function (res) {
                                var __ver = $("#version");
                                __ver.empty();
                                if (res) {
                                    var opts = [];
                                    for (var i = 0; i < res.length; i++) {
                                        if (res[i].fdIsEnable) {
                                            opts.push("<option value='" + res[i].fdName + "'>" + res[i].fdName + "</option>");
                                        } else {
                                            // 禁用
                                            opts.push("<option value='" + res[i].fdName + "' disabled='disabled'>" + res[i].fdName + "(<bean:message bundle="sys-organization" key="sys.org.available.false"/>)</option>");
                                        }
                                    }
                                    __ver.append(opts.join(''));
                                    __ver.show();
                                } else {
                                    __ver.hide();
                                }
                            }, 'json');
                        }
                    }
                }

                // 检查是否已经选择矩阵
                window.checkMatrix = function () {
                    var matrixId = $("input[name=matrixId]").val();
                    if (matrixId.length < 1) {
                        dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.empty"/>');
                        return false;
                    }
                    return true;
                }

                // 选择条件
                window.conditionalChange = function (value, elem) {
                    if (value.length > 0) {
                        var idx = $(elem).attr("name").match(/\d+/);
                        var type = $(elem).find("option:selected").data("type");
                        var maindata = $(elem).find("option:selected").data("maindata");
                        var fieldname = $(elem).find("option:selected").data("fieldname");
                        var temp = $("select[name='conditional_type[" + idx + "]']");
                        var id = $("input[name='id[" + idx + "]']");
                        var name = $("input[name='name[" + idx + "]']");
                        // 清空原来的数据
                        id.val("");
                        name.val("");
                        // 移除校验
                        name.removeAttr("validate");
                        var select = $("a[name='select[" + idx + "]']");
                        if (type == "org" || type == "dept" || type == "post" || type == "person" || type == "group") {
                            // 组织架构
                            temp.show();
                            select.show();
                            name.attr("readonly", "readonly");
                            select.data("type", type);
                        } else if(type == "numRange") {
                            // 数值区间
                            temp.hide();
                            select.hide();
                            name.removeAttr("readonly");
                            name.attr("validate", "number");
                        } else if (type != "constant") {
                            // 主数据
                            temp.show();
                            select.show();
                            name.attr("readonly", "readonly");
                            select.data("fieldname", fieldname);
                            select.data("type", maindata);
                            select.data("id", type);
                        } else {
                            // 常量
                            temp.hide();
                            select.hide();
                            name.removeAttr("readonly");
                        }

                    }
                }

                // 选择结果
                window.selectResult = function () {
                    if (checkMatrix()) {
                        var matrixId = $("input[name=matrixId]").val();
                        Dialog_Tree(true, 'resultId', 'resultName', null, 'sysOrgMatrixService&id=' + matrixId + '&rtnType=2', '<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.select.result"/>');
                    }
                }

                // 选择条件
                window.selectConditional = function (id, name, elem) {
                    var dataid = $(elem).data("id");
                    var type = $(elem).data("type");
                    var fieldname = $(elem).data("fieldname");
                    if (type == "org" || type == "dept" || type == "post" || type == "person" || type == "group") {
                        var orgType = type == "org" ? 1 : type == "dept" ? 2 : type == "post" ? 4 : type == "person" ? 8 : type == "group" ? 16 : "";
                        Dialog_Address(false, id, name, null, orgType, null, null, null, null, null, null, null, null);
                    } else if (type == "sys") {
                        Dialog_MainData(id, name, fieldname, '<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.select.maindata"/>');
                    } else if (type == "cust") {
                        Dialog_Tree(false, id, name, null, 'sysOrgMatrixMainDataService&id=' + dataid, '<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.select.cust"/>');
                    }
                }

                // 开始计算
                window.startCalculate = function () {
                    // 打包所有入参
                    // 格式：{'id': '矩阵ID', 'results': '结果1ID;结果2ID', 'option': 1, 'conditionals': [{'id':'条件1ID', 'type': 'fdId/fdName', 'value': '条件值1'}, {'id':'条件2ID', 'type': 'fdId/fdName', 'value': '条件值2'}]}
                    if (checkMatrix()) {
                        var matrixId = $("input[name=matrixId]").val();
                        var version = $("select[name=version]").val();
                        var results = $("input[name=resultId]").val();
                        var resultName = $("input[name=resultName]").val();
                        var option = $("input[name=option]:checked").val();
                        if (results.length < 1) {
                            dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.result.empty"/>');
                            return false;
                        }
                        var conditionals = [], hasError = false;
                        $("#fdConditionals").find("tr").each(function (i, n) {
                            if (i > 0) {
                                // 取条件类型
                                var conditional = $("select[name='conditional[" + (i - 1) + "]']").find("option:selected");
                                var id = conditional.val();
                                var type = "fdId";
                                var value = $("input[name='name[" + (i - 1) + "]']").val();
                                if(conditional.data("type") == "numRange") {
                                    value = value.replace(/(^\s*)|(\s*$)/g, "");
                                    if(value.length > 0) {
                                        // 数值区间
                                        var v = value;
                                        if(!isNaN(v) && !/^\s+$/.test(v)&& /^.{1,20}$/.test(v) && /(\.)?\d$/.test(v)) {
                                            // 校验通过
                                        } else {
                                            var msg = '<bean:message key="errors.number" />';
                                            dialog.alert(msg.replace("{0}", conditional.text()));
                                            hasError = true;
                                            return false;
                                        }
                                    }
                                } else if(conditional.data("type") != "constant") {
                                    type = $("select[name='conditional_type[" + (i - 1) + "]']").val();
                                    if (type == "fdId") {
                                        value = $("input[name='id[" + (i - 1) + "]']").val();
                                    }
                                }
                                conditionals.push({"id": id, "type": type, "value": value});
                            }
                        });
                        if(hasError) {
                            return ;
                        }
                        if (conditionals.length < 1) {
                            dialog.alert('<bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.conditional.empty"/>');
                            return false;
                        }
                        var data = {
                            "id": matrixId,
                            "version": version,
                            "results": results,
                            "option": option,
                            "conditionals": conditionals
                        };
                        $.post('<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=simulation"/>', {"data": JSON.stringify(data)}, function (res) {
                            var content = [];
                            if (res.success) {
                                var resultIds = results.split(";");
                                var resultNames = resultName.split(";");
                                for (var k = 0; k < resultIds.length; k++) {
                                    content.push('<h3>' + resultNames[k] + '</h3>');
                                    content.push('<table class="tb_normal" width=100%>');
                                    content.push('<tr align="center">');
                                    content.push('<td class="td_normal_title" width=10%><bean:message key="page.serial"/></td>');
                                    content.push('<td class="td_normal_title" width=10%><bean:message key="sys.common.viewInfo.type"/></td>');
                                    content.push('<td class="td_normal_title" width=30%><bean:message key="prompt.name"/></td>');
                                    content.push('<td class="td_normal_title" width=50%><bean:message bundle="sys-organization" key="sysOrgMatrix.simulator.dept"/></td>');
                                    content.push('</tr>');

                                    var datas = res.data[resultIds[k]];
                                    if (!datas || datas.length == 0) {
                                        content.push('<tr align="center">');
                                        content.push('<td colspan="4"><bean:message key="return.noRecord"/></td>');
                                        content.push('</tr>');
                                    } else {
                                        for (var i = 0; i < datas.length; i++) {
                                            var obj = datas[i];
                                            content.push('<tr>');
                                            content.push('<td align="center">' + (i + 1) + '</td>');
                                            content.push('<td align="center">' + (obj.type == 4 ? '<bean:message bundle="sys-organization" key="sysOrgMatrix.result.type.post"/>' : '<bean:message bundle="sys-organization" key="sysOrgMatrix.result.type.person"/>') + '</td>');
                                            content.push('<td>' + obj.name + '</td>');
                                            content.push('<td>' + obj.pname + '</td>');
                                            content.push('</tr>');
                                        }
                                    }
                                    content.push('</table>');
                                }
                            } else {
                                content.push('<table class="tb_normal" width=100%>');
                                content.push('<tr align="center">');
                                content.push('<td colspan="4" style="color: red;">' + res.msg + '</td>');
                                content.push('</tr>');
                                content.push('</table>');
                            }
                            $("#TD_Result").html(content.join(""));
                        }, 'json');
                    }
                }
                // 系统主数据
                window.Dialog_MainData = function (id, name, fieldName, title) {
                    var selected = $("input[name='" + id + "']").val();
                    var matrixId = $("input[name=matrixId]").val();
                    dialog.iframe("/sys/organization/sys_org_matrix/sysOrgMatrixData_mainData.jsp?matrixId=" + matrixId + "&fieldName=" + fieldName + "&selected=" + selected,
                        title, function (data) {
                            if (data) {
                                if (data == "clear") {
                                    $("input[name='" + id + "']").val("");
                                    $("input[name='" + name + "']").val("");
                                } else {
                                    $("input[name='" + id + "']").val(data.id);
                                    $("input[name='" + name + "']").val(data.name);
                                }
                            }
                        }, {
                            width: 1200,
                            height: 600,
                            buttons: [{
                                name: '<bean:message key="button.ok" />',
                                focus: true,
                                fn: function (value, dialog) {
                                    if (dialog.frame && dialog.frame.length > 0) {
                                        var frame = dialog.frame[0];
                                        var contentDoc = $(frame).find("iframe")[0].contentDocument;
                                        $(contentDoc).find("input[name='List_Selected']:checked").each(function (i, n) {
                                            value = {};
                                            value.id = $(n).val();
                                            value.name = $(n).parent().parent().find("td.mainData_title").text();
                                            return true;
                                        });
                                    }
                                    setTimeout(function () {
                                        dialog.hide(value);
                                    }, 200);
                                }
                            }, {
                                name: '<bean:message key="button.cancel" />',
                                styleClass: 'lui_toolbar_btn_gray',
                                fn: function (value, dialog) {
                                    dialog.hide();
                                }
                            }, {
                                name: '<bean:message key="button.clear" />',
                                styleClass: 'lui_toolbar_btn_gray',
                                fn: function (value, dialog) {
                                    dialog.hide("clear");
                                }
                            }]
                        });
                }
                $(function() {
                    setTimeout(function() {
                        // 初始化
                        var paramMatrixId = "${JsParam.matrixId}";
                        if (paramMatrixId.length > 0) {
                            var paramMatrixName = decodeURIComponent("${JsParam.matrixName}");
                            $("input[name=matrixId]").val(paramMatrixId);
                            $("input[name=matrixName]").val(paramMatrixName);
                            window.selectMatrix({"data": [{"id": paramMatrixId, "name": paramMatrixName}]});
                        }
                    }, 500);
                });
            });
        </script>
        <script>
            seajs.use(['lui/jquery'], function ($) {
                DocList_Info.push('fdConditionals');
                $(function () {
                    DocListFunc_Init();
                    var TableInfo = DocList_TableInfo['fdConditionals'];
                    TableInfo.fieldFormatNames.push('conditional[!{index}]');
                    TableInfo.fieldFormatNames.push('conditional_type[!{index}]');
                    TableInfo.fieldNames.push('conditional[!{index}]');
                    TableInfo.fieldNames.push('conditional_type[!{index}]');
                });
            });
        </script>
    </template:replace>
</template:include>
