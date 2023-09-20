<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="net.sf.json.JSONObject" %>
<%@page import="com.landray.kmss.sys.modeling.base.util.ListviewEnumUtil" %>
<%@page import="com.landray.kmss.sys.modeling.base.forms.ModelingAppListviewForm" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<script type="text/javascript">
    Com_IncludeFile("select.js");
    Com_IncludeFile("dialog.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("formula.js");
    Com_IncludeFile("doclist.js");
    Com_IncludeFile("calendar.js");
    Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
    Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
    Com_IncludeFile("Sortable.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/js/', 'js', true);
    Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
        + 'sys/modeling/base/resources/js/', 'js', true);
    Com_IncludeFile("g6.4.3.3.min.js", Com_Parameter.ContextPath + 'sys/modeling/base/resources/antv/', 'js', true);
</script>

<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/sourcePanel.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/collection.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/mindMap.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/space/res/css/spaceView.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/showFilters.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/space/res/css/common.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/space/res/css/template.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${KMSS_Parameter_ContextPath}sys/modeling/base/space/res/css/styleLayout.css?s_cache=${LUI_Cache}"/>

<div class="model-body-content" id="editContent_resPanel">
    <div class="model-mind-map-top">
        <div class="model-mind-map-left">
            <div class="modeling-pam-back">
                <div onclick="returnListPage()">
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
                </div>
            </div>
            <div class="model-mind-map-title listviewName" title="${modelingAppSpaceForm.fdName}">
                <c:if test="${empty modelingAppSpaceForm.fdName}">
                    ${lfn:message('sys-modeling-base:modelingAppSpace.no.name')}
                </c:if>
                ${modelingAppSpaceForm.fdName}
            </div>
        </div>
        <div class="modeling-pam-top-right">
            <ul>
                <li onclick="dosubmit('update')" class="active">${lfn:message('sys-modeling-base:modeling.save')}</li>
            </ul>
        </div>
    </div>
    <div class="model-edit">
        <!-- 左侧预览 starts -->
        <div class="model-edit-left">

        </div>
        <!-- 左侧预览 end -->
        <!-- 右侧编辑 starts -->
        <div class="model-edit-right">
            <div class="model-edit-right-wrap">
                <div class="model-edit-view-bar">
                    <div resPanel-bar-mark="basic">${lfn:message('sys-modeling-base:listview.basic.set')}</div>
                    <div resPanel-bar-mark="frame">${lfn:message('sys-modeling-base:modelingAppSpace.style.setting')}</div>
                    <div resPanel-bar-mark="content">${lfn:message('sys-modeling-base:modelingAppSpace.portlet.setting')}</div>
                </div>
                <div class="model-edit-view-content">
                    <div class="model-edit-view-content-wrap">
                        <html:form action="/sys/modeling/base/modelingAppSpace.do">
                            <div style="height: 100%;box-sizing: border-box;margin-top: 30px" id="mindMapEdit">
                                <center>
                                    <html:hidden property="fdId"/>
                                    <input type="hidden" name="fdApplicationId"
                                           value="<c:out value='${modelingAppSpaceForm.fdApplicationId}' />">
                                    <table class="tb_simple model-view-panel-table" width="100%" id="mindMapEditTable">
                                            <%--基本信息--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="basic">
                                            <td>
                                                <table id="mindMapBasicDom" class="tb_simple model-view-panel-table"
                                                       width="100%">
                                                    <tr>
                                                        <td class="td_normal_title title_required common_title">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingAppSpace.pc.name"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdName" _xform_type="text" class="mind-map-fdName" style="width:100%">
                                                                <input name="fdName"  class="name-content-text" value="${modelingAppSpaceForm.fdName}" type="text"
                                                                       validate="required maxLength(36)" style="width:97%;">
                                                                <span class="txtstrong">*</span>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title common_title">
                                                            <bean:message bundle="sys-modeling-base"
                                                                          key="modelingBusiness.fdDesc"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="model-view-panel-table-td">
                                                            <div id="_xform_fdDesc" _xform_type="text"
                                                                 style="width:100%">
                                                                <xform:textarea property="fdDesc" style="width:100%"
                                                                                showStatus="edit" placeholder="${lfn:message('sys-modeling-base:modeling.please.enter')}"
                                                                                validators="maxLength(450)"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td class="td_normal_title title_required common_title">
                                                                ${lfn:message('sys-modeling-base:respanel.fdAuthReaders')}
                                                        </td>
                                                    </tr>
                                                    <tr id="fdOrgElementTr">
                                                        <td width=100% class="model-view-panel-table-td">
                                                            <xform:address textarea="true" mulSelect="true"
                                                                           propertyId="authSearchReaderIds"
                                                                           propertyName="authSearchReaderNames"
                                                                           style="width: 95%;height:120px;"></xform:address>
                                                            <br>
                                                            <div style="color: #999999;">${lfn:message('sys-modeling-base:respanel.Empty.everyone.operate')}</div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                            <%--样式布局--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="frame">
                                            <td>
                                                <div class="mind-map-display-setting">
                                                    <div data-lui-type="sys/modeling/base/space/res/js/incStyleGenerator!IncStyleGenerator"
                                                         id="incStyleGenerator">
                                                        <script type="text/config">
                                                         {
                                                            storeData : $.parseJSON('${modelingAppSpaceForm.fdConfig}' || '{}'),
                                                            headerClass : 'panel-style-header',
                                                            mainClass : 'panel-style-main'
                                                         }
                                                        </script>
                                                        <div class="panel-portlet-content">
                                                            <div class="panel-style-header"></div>
                                                            <div class="panel-style-main"></div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                            <%--部件设置--%>
                                        <tr class="resPanel-bar-content" resPanel-bar-content="content">
                                            <td>
                                                <div class="mind-map-portlet-setting">
                                                        <div class="panel-portlet-content">
                                                            <div class="panel-portlet-main"></div>
                                                            <div class="panel-portlet-header" style="width: 100% ;height: 600px;" >
                                                                <div style="width: 100%;height: 130px;background: url('../base/space/res/images/no_portlet.png') no-repeat center;background-size: contain;position: absolute;margin-top: 150px;">
                                                                </div>
                                                                <div style="position: absolute;top: 350px;text-align: center; width: 100%;color: #999">
                                                                    <p style="font-size: 12px;font-family: PingFangSC-Regular;margin-bottom: 8px;">${lfn:message('sys-modeling-base:modelingAppSpace.pc.no.components')}</p>
                                                                    <p style="font-size: 12px;font-family: PingFangSC-Regular">${lfn:message('sys-modeling-base:modelingAppSpace.pc.please.toadd.portlet')}</p>
                                                                </div>
                                                            </div>
                                                        </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </center>
                                <br>
                            </div>
                            <input type="hidden" name="fdApplicationId"
                                   value="<c:out value='${modelingAppSpaceForm.fdApplicationId}' />">
                            <input type="hidden" name="docCreateTime"
                                   value="<c:out value='${modelingAppSpaceForm.docCreateTime}' />">
                            <input type="hidden" name="docCreatorId"
                                   value="<c:out value='${modelingAppSpaceForm.docCreatorId}' />">
                            <input type="hidden" name="fdConfig"
                                   value="<c:out value='${modelingAppSpaceForm.fdConfig}' />">
                            <html:hidden property="method_GET"/>
                        </html:form>
                    </div>
                </div>
            </div>
        </div>
        <!-- 右侧编辑 end -->
    </div>
</div>
<script type="text/javascript">

    window.colorChooserHintInfo = {
        cancelText: '${ lfn:message('sys-modeling-base:modeling.Cancel') }',
        chooseText: '${ lfn:message('sys-modeling-base:modeling.button.ok') }'
    };

    Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
    Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
    Com_IncludeFile("spectrumColorPicker.js", Com_Parameter.ContextPath + 'sys/modeling/base/views/business/res/', 'js', true);

    $KMSSValidation();
    var listviewOption = {
        fdApplicationId: "${modelingAppSpaceForm.fdApplicationId}",
    };
    function returnListPage() {
        var url = Com_Parameter.ContextPath + 'sys/modeling/base/space/index_body.jsp?fdAppId=${modelingAppSpaceForm.fdApplicationId}';
        var iframe = window.parent.document.getElementById("trigger_iframe");
        var tabTitle = window.parent.document.getElementById("space-title");
        $(tabTitle).css("display","block");
        $(iframe).attr("src", url);
        return false;
    }
    seajs.use(["sys/modeling/base/space/res/js/spaceMindMap", "lui/dialog", "lui/jquery", 'lui/topic']
        , function (spaceMindMap, dialog, $, topic) {
            function init() {
                var cfg = {
                    fdApplicationId: "${modelingAppSpaceForm.fdApplicationId}",
                    fdConfig: $("[name='fdConfig']").val()
                };
                window.spaceMindMap = new spaceMindMap.spaceMindMap(cfg);
                window.spaceMindMap.startup();
            }

            init();
            window.onclose = function () {
                $dialog.hide(null);
            };
            window.dosubmit = function (type) {
                LUI("incStyleGenerator").getRightKeyData();
                var isPass =  LUI("incStyleGenerator").submitChecked();
                if(isPass == false){
                    dialog.confirm('${lfn:message('sys-modeling-base:modelingAppSpace.verification.fail')}',function(value){
                        return;
                    });
                    return;
                }
                var fdConfig = LUI("incStyleGenerator").getKeyData();
                $("input[name='fdConfig']").val(JSON.stringify(fdConfig));
                if(beforeSubmitValidate()){
                    Com_Submit(document.modelingAppSpaceForm, type);
                }
                var tabTitle = window.parent.document.getElementById("space-title");
                $(tabTitle).css("display","block");
            };
            window.changeStyle = function (obj) {
                $(obj).siblings().removeClass("active");
                $(obj).addClass("active");
            }
            window.beforeSubmitValidate = function(){
                var $KMSSValidation = $GetKMSSDefaultValidation();
                var fdName = $("input[name='fdName']",document)[0];
                var result = $KMSSValidation.validateElement(fdName);
                var maxLength = $KMSSValidation.getValidator("maxLength(36)");
                if (!result){
                    if(maxLength.test($("input[name='fdName']").val(),fdName) == false){
                        $(".validation-advice").remove();
                        $(".validation-container").remove();
                        //超长提示框
                        $(".mind-map-fdName").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                            "<table class=\"validation-table\"><tbody><tr><td>" +
                            "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                            "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">${lfn:message('sys-modeling-base:modelingBusiness.fdName')}</span>" +
                            "${lfn:message('sys-modeling-base:listview.enter.up.12.Chinese')}</td></tr></tbody></table></div></div>"));
                    }else{
                        $(".validation-advice").remove();
                        $(".validation-container").remove();
                        //必填提示框
                        $(".mind-map-fdName").append("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                            "<table class=\"validation-table\"><tbody><tr><td>" +
                            "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                            "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">${lfn:message('sys-modeling-base:modelingBusiness.fdName')}</span>" +
                            "${lfn:message('sys-modeling-base:kmReviewMain.notNull')}</td></tr></tbody></table></div></div>");
                    }
                    return false;
                }
                return true;
            }
            window.triggleSelectdatetime = function (event, dom, type, name) {
                var input = $(dom).find("input[name='" + name + "']");
                if (type == "DateTime") {
                    selectDateTime(event, input);
                } else if (type == "Date") {
                    selectDate(event, input);
                } else {
                    selectTime(event, input);
                }
            }
            window.statisticsValidateNum = function(dom){
                var $whereRule = $(dom).parents('.open_rule_setting_where_rule');
                var v = $(dom).val();
                if(v.length == 0 || (!isNaN(v) && !/^\s+$/.test(v)&& /^.{1,20}$/.test(v) && /(\.)?\d$/.test(v))){
                    if(0 < $whereRule.find('.validation-advice').length){
                        $whereRule.find('.validation-advice').remove();
                        $whereRule.css("height","30px");
                    }
                    return true;
                }else{
                    if(0 == $whereRule.find('.validation-advice').length){
                        var html = '<div class="validation-advice" style="width: 520px;" id="advice-_validate_1" _reminder="true">' +
                            '<table class="validation-table">' +
                            '<tr>' +
                            '<td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td>' +
                            '<td class="validation-advice-msg"><span class="validation-advice-title"></span>${lfn:message("sys-modeling-base:listview.must.valid.number")}</td>' +
                            '</tr>' +
                            '</table></div>';
                        $whereRule.append(html)
                        $whereRule.css("height","70px");
                    }
                    return false;
                }
            }
        })
    LUI.ready(function(){
        $("input[name='fdName']").blur(function(){
            $(".validation-advice").remove();
        });
    });
</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>