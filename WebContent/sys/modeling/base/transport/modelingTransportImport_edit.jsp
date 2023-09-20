<%@page import="com.landray.kmss.sys.modeling.base.model.ModelingImportConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include file="/sys/profile/resource/template/list.jsp" frameShowTop="no">
    <template:replace name="content">
        <link href="${LUI_ContextPath}/sys/profile/listShow/css/common.css" rel="stylesheet">
        <link href="${LUI_ContextPath}/sys/profile/listShow/css/maincss.css" rel="stylesheet">
        <link href="${LUI_ContextPath}/sys/modeling/base/resources/css/modeling.css" rel="stylesheet">
        <link rel="Stylesheet" href="${LUI_ContextPath}/sys/attend/resource/css/attend.css" />
        <style>
            .form-item {
                margin: 10px 0 10px 0;
            }

            .lui_custom_list_box_content .form-item {
                margin-left: 0px;
            }

            #transportTable tr td {
                padding: 5px 0px;
            }

            .messageNotNull {
                display: inline-block;
                width: 20px;
                height: 20px;
            }

            .lui_custom_list_box_content_col_content_line .lui_custom_list_box_content_col_content_left {
                display: none;
            }

            .lui_custom_list_box_content_col_content_line:hover .lui_custom_list_box_content_col_content_left {
                display: block;
            }

            .lui_custom_list_box_content_col_content .lui_custom_list_box_content_col_content_line:first-child .lui_custom_list_box_content_col_content_left span:first-child {
                display: none !important;
            }

            .lui_custom_list_box_content_col_content .lui_custom_list_box_content_col_content_line:last-child .lui_custom_list_box_content_col_content_left span:nth-child(2) {
                display: none !important;
            }

            #primaryKey-group {
                display: none;
            }
            .messageSpan{
                cursor: default;
            }

            .mainModelNameTip{
                display:inline-block;
                margin: 5px 0 10px 0;
                font-size: 12px;
                padding-right: 10px;
                padding-left: 16px;
                color:#FF9431;
                background: url(../base/resources/images/form-tip@2x.png) no-repeat left;
                background: url(../base/resources/images/form-tip.png) no-repeat \9;
                background-size: 12px 12px;
            }
        </style>
        <form class="modeling-import" action="${LUI_ContextPath}/sys/modeling/main/modelingImportConfig.do"
              method="post" id="modelingImportConfigForm" name="modelingImportConfigForm"
              onsubmit="return beforeSubmit();">
            <div class="lui_custom_list_container">
                <table>
                    <tbody>
                    <tr>
                        <td>
                            <div class="form-item"><b>${lfn:message('sys-modeling-base:modelingImportConfig.fdTemplateName')}：</b>
                                <xform:text property="fdName" required="true" validators="maxLength(100)"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="form-item"><b>${lfn:message('sys-modeling-base:modelingImportConfig.fdImportType')}：</b>
                                <xform:radio property="fdImportType" required="true"
                                             value="${modelingImportConfigForm.method_GET eq 'add'?'1':''}"
                                             onValueChange="checkNotNullProperties(this);"/>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <!-- 主要内容 Starts -->
                <div class="lui_custom_list_box">
                    <div class="lui_custom_list_box_content">
                        <div class="lui_custom_list_box_content_container">
                            <div class="lui_custom_list_box_content_row">
                                    <%-- 待选列表--%>
                                <div class="lui_custom_list_box_content_col">
                                    <div class="item">
                                        <div class="lui_custom_list_box_content_col_header">
                                            <span class="lui_custom_list_box_content_header_word">
                                                <bean:message bundle='sys-profile' key='sys.profile.select'/>${lfn:message('sys-modeling-base:enums.viewtab_type.1')}</span>
                                            <label class="lui_custom_list_checkbox right">
                                            </label>
                                        </div>
                                        <div class="lui_custom_list_box_content_col_content" id="unselectedlist">
                                            <c:forEach var="unselected" items="${modelingImportConfigForm.optionList}">
                                                <div id="unselected_${unselected.fdName}"
                                                     class="lui_custom_list_checkbox" lui-mark="unselectedlist_item" title="${ unselected.showName }"
                                                     onclick="toRight(this,'${unselected.fdName }','${unselected.showName }')">
                                                    <label>${unselected.showName } </label>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="lui_custom_list_box_content_col">
                                    <div class="col_add_right" onclick="checkAllUnselected(this)">${lfn:message('sys-modeling-base:modeling.dataValidate.ALLJoin')}</div>
                                    <div class="col_reset" onclick="allToLeft()">${lfn:message('sys-modeling-base:button.reset')}</div>
                                </div>
                               <%-- 已选列表--%>
                                <div class="lui_custom_list_box_content_col">
                                    <div class="item">
                                        <div class="lui_custom_list_box_content_col_header">
                                            <span class="lui_custom_list_box_content_header_word">
                                                <bean:message bundle='sys-profile' key='sys.profile.selected'/>${lfn:message('sys-modeling-base:enums.viewtab_type.1')}
                                            </span>
                                            <span class="lui_custom_list_box_content_link_right">
                                                <div class="model-ques" title="${lfn:message('sys-modeling-base:import.required.cannot.cancel')}"></div>
                                            </span>
                                        </div>
                                        <div class="lui_custom_list_box_content_col_content" id="selectedlist">
                                            <c:forEach var="selected" items="${modelingImportConfigForm.propertyList }">
                                                <div id="selected_${selected.fdName}"
                                                     class="lui_custom_list_box_content_col_content_line "
                                                     data-key="${selected.fdName}"
                                                     data-notnull="${ selected.notNull}">
                                                    <span class="messageNotNull"
                                                          style="${selected.notNull ? 'color:red;':''}">${selected.notNull ? '*':' '}</span>
                                                    <span class="messageSpan" title="${ selected.showName }">${ selected.showName }</span>
                                                    <div class="lui_custom_list_box_content_col_content_left">
                                                        <span class="toTopSpan" onclick="toTop(this)">${lfn:message('sys-modeling-base:modelingTransport.button.up')}</span>
                                                        <span class="toDownSpan" onclick="toDown(this)">${lfn:message('sys-modeling-base:modelingTransport.button.down')}</span>
                                                        <c:if test="${ not selected.notNull}">
                                                            <span class="btn_to_left" onclick="toLeft(this)">${lfn:message('sys-modeling-base:modelingTransport.button.delete')}</span>
                                                        </c:if>
                                                        <c:if test="${ selected.notNull}">
                                                            <span></span>
                                                        </c:if>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-item" id="primaryKey-group">
                                <div class="mainModelNameTip">${lfn:message('sys-modeling-base:import.config.tips')}</div><br>
                                    ${lfn:message('sys-modeling-base:modelingTransport.label.primaryKey')}：
                                <select name="primaryKey1" id="primaryKey1" style="width:20%"
                                        modeling-validation="primaryKey1; ${lfn:message('sys-modeling-base:modelingTransport.label.primaryKey')};required;name:primaryKey1;id:primaryKey-group">
                                    <option value=""></option>
                                    <c:forEach items="${modelingImportConfigForm.primaryKeyOptionList }"
                                               var="commonProperty">
                                        <c:if test="${commonProperty.fdName == modelingImportConfigForm.primaryKey1}">
                                            <option value="${commonProperty.fdName }"
                                                    selected>${commonProperty.showName }</option>
                                        </c:if>
                                        <c:if test="${commonProperty.fdName != modelingImportConfigForm.primaryKey1}">
                                            <option value="${commonProperty.fdName }">${commonProperty.showName }</option>
                                        </c:if>
                                    </c:forEach>
                                </select><font id='star' style='display:none' color='red'>*</font>
                                <select name="primaryKey2" style="width:20%">
                                    <option value=""></option>
                                    <c:forEach items="${modelingImportConfigForm.primaryKeyOptionList }"
                                               var="commonProperty">
                                        <c:if test="${commonProperty.fdName == modelingImportConfigForm.primaryKey2}">
                                            <option value="${commonProperty.fdName }"
                                                    selected>${commonProperty.showName }</option>
                                        </c:if>
                                        <c:if test="${commonProperty.fdName != modelingImportConfigForm.primaryKey2}">
                                            <option value="${commonProperty.fdName }">${commonProperty.showName }</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select name="primaryKey3" style="width:20%">
                                    <option value=""></option>
                                    <c:forEach items="${modelingImportConfigForm.primaryKeyOptionList }"
                                               var="commonProperty">
                                        <c:if test="${commonProperty.fdName == modelingImportConfigForm.primaryKey3}">
                                            <option value="${commonProperty.fdName }"
                                                    selected>${commonProperty.showName }</option>
                                        </c:if>
                                        <c:if test="${commonProperty.fdName != modelingImportConfigForm.primaryKey3}">
                                            <option value="${commonProperty.fdName }">${commonProperty.showName }</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                            <table id="transportTable">
                                <c:forEach items="${selectedForeignKeyHtmlList }" var="array">
                                    <tr kmss_propertyName='${array[0]}'>
                                        <td colspan="2">
                                            <font color="blue">${array[1]}</font>&nbsp;
                                            <bean:message bundle="sys-transport" key="sysTransport.label.foreignKey"/>&nbsp;
                                                ${array[2] }
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <div class="lui_custom_list_box_content_col_btn">
                                <html:hidden property="fdId"/>
                                <html:hidden property="fdModelId"/>
                                <input type="hidden" name="selectedPropertyNames">
                                    <%-- <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="defaultShow()"><bean:message bundle='sys-profile' key='sys.profile.button.reset'/></a> --%>
                                <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)"
                                   onclick="saveForm()"><bean:message bundle='sys-profile'
                                                                      key='sys.profile.button.save'/></a>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 主要内容 Ends -->


            </div>
        </form>

        <script type="text/javascript">
            Com_IncludeFile("docutil.js|optbar.js|validator.jsp|validation.js|plugin.js|validation.jsp|eventbus.js|xform.js", null, "js");
            Com_IncludeFile("localValidate.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/js/', 'js', true);

            function saveForm() {
                if ($("#primaryKey1").attr("validate") == "required") {
                    _localValidation();
                }
                if ('${modelingImportConfigForm.method_GET}' == 'add') {
                    Com_Submit(document.modelingImportConfigForm, "save");
                } else if ('${modelingImportConfigForm.method_GET}' == 'edit') {
                    Com_Submit(document.modelingImportConfigForm, "update");
                }
            }

            /**
             * 选择
             * @param ele
             * @param id
             * @param message
             */
            function toRight(ele, id, message) {
                var html = '<div id="selected_' + id + '" class="lui_custom_list_box_content_col_content_line" data-key="' + id + '">' +
                    '  <span class="messageNotNull"> </span>' +
                    '<span class="messageSpan" title="'+message+'">' + message + '</span>' +
                    '<div class="lui_custom_list_box_content_col_content_left">' +
                    '<span class="toTopSpan" onclick="toTop(this)">${lfn:message('sys-modeling-base:modelingTransport.button.up')}</span>' +
                    '<span class="toDownSpan" onclick="toDown(this)">${lfn:message('sys-modeling-base:modelingTransport.button.down')}</span>' +
                    '<span class="btn_to_left" onclick="toLeft(this)">${lfn:message('sys-modeling-base:modelingTransport.button.delete')}</span>' +
                    '</div>' +
                    '</div>';
                $("#selectedlist").append($(html));
                $(ele).remove();
                reflesh(3);
            }

            /**
             * 移除
             * @param elem
             */
            function toLeft(elem) {
                var id = $(elem).parent().parent().data("key");
                var message = $(elem).parent().parent().find(".messageSpan").text();
                var html = '<div class="lui_custom_list_checkbox" id="unselected_' + id + '" lui-mark="unselectedlist_item" onclick="toRight(this,\'' + id + '\',\'' + message + '\')">' +
                    '<label>' + message + '</label></div>';
                $("#unselectedlist").append($(html));
                $(elem).parent().parent().remove();
                $("#unselectedCheck").prop("checked", false);
                // console.log('删除：' + id);
                reflesh(1, id);
            }

            // 全部删除
            function allToLeft() {
                $(".btn_to_left").each(function () {
                    var self = this;
                    // 必填的不能删除
                    var display =$(self).css('display');
                    if (display!='none' && 'true' != $(self).data("key")) {
                        toLeft(self);
                    }
                });
            }

            // 上移
            function toTop(elem) {
                if ($(elem).parent().parent().index() != 0) {
                    $(elem).parent().parent().prev().before($(elem).parent().parent());
                }
            }

            function toDown(elem) {
                $(elem).parent().parent().next().after($(elem).parent().parent());
            }

            function checkAllUnselected() {
                $("[lui-mark=\"unselectedlist_item\"]").each(function () {
                    $(this).trigger($.Event("click"));
                });
            }

            function reflesh(condition, id) {
                var form = document.modelingImportConfigForm;
                var foreignKeys = ';${modelingImportConfigForm.foreignKeyString}';
                var notNullPropertyNames = '${notNullPropertyNames}';
                var table = document.getElementById("transportTable");

                switch (condition) {
                    case 1: // 删除一个
                        for (var i = 0; i < table.rows.length; i++) {
                            var tr = table.rows[i];
                            var propertyName = tr.getAttribute("kmss_propertyName");
                            // 在已选列表中查找value等于当前行对应的属性名的option，如果找到说明该option要删除
                            if (id == propertyName) {
                                table.deleteRow(i);
                                return;
                            }
                        }
                        break;
                    case 2: // 全部删除
                        for (var i = table.rows.length - 1; i >= 0; i--) {
                            var tr = table.rows[i];
                            var propertyName = tr.getAttribute("kmss_propertyName");
                            var found = false;
                            var selectedOptions = $("#selectedlist .lui_custom_list_box_content_col_content_line");
                            // 在已选列表中查找value等于当前行对应的属性名的option，如果没找到说明该option已删除
                            for (var j = 0; j < selectedOptions.length; j++) {
                                if (propertyName == $(selectedOptions[j]).data("key")) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) table.deleteRow(i);
                        }
                        break;
                    default:
                        var selectedlist = $("#selectedlist .lui_custom_list_box_content_col_content_line");
                        for (var j = 0; j < selectedlist.length; j++) { // 遍历“已选列表”
                            var propertyName = $(selectedlist[j]).data("key");
                            if (foreignKeys.indexOf(';' + propertyName + ';') >= 0) { // 如果是外键，则在下面的表格行中查找
                                var found = false;
                                for (var i = 0; i < table.rows.length; i++) {
                                    var tr = table.rows[i];
                                    if (propertyName == tr.getAttribute("kmss_propertyName")) {
                                        found = true;
                                        break;
                                    }
                                }
                                if (!found) { // 没找到则添加一行
                                    var optionHtmlArray = new Array();
                                    <c:forEach var="entry" items="${modelingImportConfigForm.foreignKeyPropertyOptionHtmlMap}">
                                    optionHtmlArray["${entry.key}"] = '${entry.value}';
                                    </c:forEach>
                                    var tr = table.insertRow(table.rows.length);
                                    tr.setAttribute("kmss_propertyName", propertyName);
                                    var td = tr.insertCell(0);
                                    td.setAttribute("colSpan", "2");
                                    var tdContent = '<font color="blue">';
                                    tdContent += $(selectedlist[j]).children(".messageSpan").text();
                                    tdContent += '</font>';
                                    tdContent += '  <bean:message  bundle="sys-transport" key="sysTransport.label.foreignKey"/>  ';
                                    var req_selectHtml = '<select name="' + propertyName + '" style="width:20%" subject="' + $(selectedlist[j]).children(".messageSpan").text() + '" validate="required">';
                                    req_selectHtml += '<option></option>';
                                    req_selectHtml += optionHtmlArray[propertyName];
                                    req_selectHtml += '</select>';
                                    var selectHtml = '<select name="' + propertyName + '" style="width:20%">';
                                    selectHtml += '<option></option>';
                                    selectHtml += optionHtmlArray[propertyName];
                                    selectHtml += '</select>';
                                    tdContent += req_selectHtml + '<span class="txtstrong">*</span>';
                                    tdContent += selectHtml;
                                    tdContent += selectHtml;
                                    td.insertAdjacentHTML('afterBegin', tdContent);
                                }
                            }
                        }
                }
            }

            // 提交之前补充数据
            function beforeSubmit() {
                var form = document.modelingImportConfigForm;
                form.selectedPropertyNames.value = ""; //每次清空所选属性，防止每一次因模板名称为空等校验没通过时，属性重复一次
                var selectedOptions = $("#selectedlist .lui_custom_list_box_content_col_content_line");
                if (selectedOptions.length == 0) {
                    alert('<bean:message  bundle="sys-transport" key="sysTransport.error.none.selected"/>');
                    return false;
                }
                //if (checkForeignKeyProperty() && checkPrimaryKey() ) {
                for (var j = 0; j < selectedOptions.length; j++) { // 遍历“已选列表”
                    var propertyName = $(selectedOptions[j]).data("key");
                    form.selectedPropertyNames.value += propertyName + ';';
                }
                return true;
                //}
                //else return false;
            }

            function checkNotNullProperties(radioObj) {
                //切换类型时移除掉点击提交时的校验框
                $(".modeling-validation-show").remove();
                var notNullPropertyNames = '${notNullPropertyNames}';
                var form = document.modelingImportConfigForm;
                var selectedOptions = $("#selectedlist .lui_custom_list_box_content_col_content_line");
                var options = form.options;
                // 检查所有的必选项是否已被选择
                var notNullPropertyArray = notNullPropertyNames.split(';');
                for (var i = 0; i < notNullPropertyArray.length; i++) {
                    var notNullProperty = notNullPropertyArray[i];
                    if (notNullProperty.length == 0) continue;
                    var found = false;
                    // 遍历“已选项”列表，检查是否选择了此必选项
                    for (var j = 0; j < selectedOptions.length; j++) {
                        var propertyName = $(selectedOptions[j]).data("key");
                        if (propertyName == notNullProperty) {
                            found = true;
                            break;
                        }
                    }
                    // 如果此必选项未被选择，则需要自动选上
                    if (!found) {
                        $("#" + notNullProperty).click();
                    }
                }

                if (radioObj.value > 1)
                    setStar(true);
                else
                    setStar(false);
            }

            function setStar(flag) {
                if (flag) {
                    $("#primaryKey1").attr("validate", "required");
                    $("#primaryKey-group").show();
                    document.getElementById('star').style.display = "";
                } else {
                    $("#primaryKey1").attr("validate", "");
                    $("#primaryKey-group").hide();
                    document.getElementById('star').style.display = "none";
                }
            }

            //原必填字段
            var RequiredFields = [];

            function initRequiredFields() {
                $('#selectedlist').find("div[id^='selected_'][data-notnull='true']").each(function () {
                    RequiredFields.push($(this).attr("id").replace("selected_", ""));
                });
            }

            function updatePrimaryKeysRequired() {
                //不需要再必填的字段
                let toNotRequired = [];
                //需要必填的字段
                let toRequired = [];
                //当前选中的主键
                let PrimaryKeys = [];
                $("select[name^='primaryKey']").each(function () {
                    if ($(this).val() !== "")
                        PrimaryKeys.push($(this).val());
                });

                //需要必填的
                for (let p in PrimaryKeys) {
                    if ($.inArray(PrimaryKeys[p], RequiredFields) === -1 && $.inArray(PrimaryKeys[p], toRequired) === -1) {
                        toRequired.push(PrimaryKeys[p]);
                    }
                }

                //不需要再必填的
                $('#selectedlist').find("div[id^='selected_'][data-notnull='true']").each(function () {
                    let id = $(this).attr("id").replace("selected_", "");
                    if ($.inArray(id, RequiredFields) === -1 && $.inArray(id, toRequired) === -1) {
                        toNotRequired.push(id);
                    }
                });

                //取消必填
                for (let p in toNotRequired) {
                    _setUnSelected(toNotRequired[p]);
                }

                //设置必填
                for (let p in toRequired) {
                    let id = toRequired[p];
                    //尝试在已选列表中设置为必填
                    let result = _setSelected(id);
                    if (!result) {
                        //不在已选列表中
                        $('#unselectedlist').find("div[id='unselected_" + id + "']").click();
                        setTimeout("_setSelected('" + id + "')", 200);
                    }
                }
            }

            /**
             * 取消必填
             * @param id
             * @private
             */
            function _setUnSelected(id) {
                let selectedDiv = $('#selectedlist').find("div[id='selected_" + id + "']");
                if (selectedDiv.size() > 0) {
                    selectedDiv.removeAttr("data-notnull");
                    let notNullSpan = selectedDiv.find(".messageNotNull");
                    notNullSpan.css("color", "");
                    notNullSpan.text(" ");
                    selectedDiv.find(".btn_to_left").show();
                    //置底
                    let toDownSpan = selectedDiv.find(".toDownSpan");
                    for (let i = 0; i < 100; i++) {
                        let attr = toDownSpan.parent().parent().next().attr('data-notnull');
                        if (attr === undefined || attr === 'false') {
                            break;
                        }
                        toDownSpan.click();
                    }
                }
            }

            /**
             * 设置必填
             * @param id
             * @returns {boolean}
             * @private
             */
            function _setSelected(id) {
                let selectedDiv = $('#selectedlist').find("div[id='selected_" + id + "']");
                if (selectedDiv.size() > 0) {
                    //在已选列表中
                    selectedDiv.attr("data-notnull", true);
                    let notNullSpan = selectedDiv.find(".messageNotNull");
                    notNullSpan.css("color", "red");
                    notNullSpan.text("*");
                    selectedDiv.find(".btn_to_left").hide();
                    //置顶
                    let toTopSpan = selectedDiv.find(".toTopSpan");
                    for (let i = 0; i < 100; i++) {
                        if (toTopSpan.parent().parent().prev().attr('data-notnull') === 'true') {
                            break;
                        }
                        toTopSpan.click();
                    }
                    return true;
                }
                return false;
            }

            function _bindEvent() {
                $("select[name^='primaryKey']").each(function (i, o) {
                    $(o).change(updatePrimaryKeysRequired);
                });
                $("#primaryKey1").on("click", function () {
                    if ($(this).attr("validate") == "required") {
                        _localValidation();
                    }
                })
            }

            LUI.ready(function () {
                var importType = '${modelingImportConfigForm.fdImportType}';
                setStar(importType === '2' || importType === '3');
                $(".com_goto").remove();
                initRequiredFields();
                updatePrimaryKeysRequired();
                _bindEvent();
            });
        </script>
        <script type="text/javascript">
            $KMSSValidation(document.forms['modelingImportConfigForm']);
        </script>
    </template:replace>
</template:include>