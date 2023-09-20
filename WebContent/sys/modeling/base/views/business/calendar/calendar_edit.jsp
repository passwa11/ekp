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
    Com_IncludeFile("view.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
    Com_IncludeFile("dialog.css", "${LUI_ContextPath}/sys/modeling/base/resources/css/", "css", true);
</script>

<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/collection.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/views/business/res/modelingCalendar.css?s_cache=${LUI_Cache}"/>
<style>


</style>

<html:form action="/sys/modeling/base/calendar.do">
    <div class="modeling-pam-top">
        <div calss="modeling-pam-top-left">
            <div class="modeling-pam-back" >
                <div onclick="returnListPage('pc')">
                    <i></i>
                    <p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</p>
                </div>
            </div>
            <div class="listviewName">
            </div>
        </div>
        <div class="modeling-pam-top-center">
            <ul>
                <li value="pc" class="active" onclick="changeContentView('pc')">PC端</li>
                <li value="mobile" onclick="changeContentView('mobile')">移动端</li>
            </ul>
        </div>
        <div class="modeling-pam-top-right">
            <ul>
                <c:if test="${modelingCalendarForm.method_GET=='edit' || modelingCalendarForm.method_GET=='editTemplate'}">
                    <li onclick="dosubmit('update');" class="active"><bean:message key="button.update"/></li>
                </c:if>
                <c:if test="${modelingCalendarForm.method_GET=='add'}">
                    <li onclick="dosubmit('save');" class="active" ><bean:message key="button.save"/></li>
                </c:if>
            </ul>
        </div>
    </div>
    <html:hidden property="fdId"/>
    <div class="modeling-pam-content">
    <!--pc-->
    <div id="modeling-pam-content-pc">
        <div class="modeling-pam-content-pc-left">
            <div class="model-body-content" id="editContent_pc">
                <div class="model-edit">
                    <!--左边预览图-->
                    <div class="model-edit-left">
                        <div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview" style="display: none">
                            <div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
                            <div data-lui-type="lui/view/render!Template" style="display:none;">
                                <script type="text/config">
                                        {
                                            src : '/sys/modeling/base/views/business/res/preview/calendarPcTable.html#'
                                        }
                                         </script>
                            </div>
                        </div>
                    </div>
                    <!--右边内容区-->
                    <div class="modeling-pam-content-pc-right">
                        <div class="model-edit-right">
                            <div class="model-edit-right-wrap" style="overflow-y: auto;height:560px">
                                <div class="model-edit-view-title">
                                    <ul>
                                        <li class="setting active" calendar-bar-mark="basic"  value="common" onclick="changeRightContentView('common')"><span>${lfn:message('sys-modeling-base:listview.basic.set')}</span></li>
                                        <li value="design" calendar-bar-mark="design"  onclick="changeRightContentView('design')"><span>${lfn:message('sys-modeling-base:listview.view.set')}</span></li>
                                    </ul>
                                </div>
                                <div class="model-edit-view-content">
                                    <!--基础设置-->
                                    <div class="model-edit-view-content-common" id="editContent_pc_common"></div>
                                    <!--视图设置-->
                                    <div class="model-edit-view-content-design" id="editContent_pc_design">
                                        <div data-lui-type="sys/modeling/base/views/business/res/calendar/calendarViewContainer!CalendarViewContainer"
                                             id="pcViewContainer" style="display:none;margin-top:10px">
                                            <script type="text/config">
                                                        {
                                                            storeData : $("[name='fdConfig']").val(),
                                                            headerClass : 'panel-tab-header',
                                                            mainClass : 'panel-tab-main',
                                                            mode : "pc",
                                                            isMultiTab: false,
                                                            fdType:"",
                                                            xformId:'${xformId}'
                                                        }
                                                    </script>
                                            <div class="panel-tab-content">
                                                <div class="panel-tab-header"></div>
                                                <div class="panel-tab-main"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--mobile-->
    <div id="modeling-pam-content-mobile">
        <!--左边预览图-->
        <div class="model-body-content" id="editContent_mobile">
            <!-- 这里放入你的组件 starts -->
            <div class="model-edit">
                <div class="model-edit-left">
                    <div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview_mobile"
                         style="display: none">
                        <div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
                        <div data-lui-type="lui/view/render!Template" style="display:none;">
                            <script type="text/config">
                                   {
                                         src : '/sys/modeling/base/views/business/res/preview/calendarMobileTable.html#'
                                   }
                                </script>
                        </div>
                    </div>
                </div>
                <!--右边内容区-->
                <div class="modeling-pam-content-mobile-right">
                    <div class="model-edit-right">
                        <div class="model-edit-right-wrap" style="overflow: auto;height:580px">
                            <div class="model-edit-view-title">
                                <ul>
                                    <li class="setting active" value="common" onclick="changeRightContentView('common')"><span>${lfn:message('sys-modeling-base:listview.basic.set')}</span></li>
                                    <li value="design" onclick="changeRightContentView('design')"><span>${lfn:message('sys-modeling-base:listview.view.set')}</span></li>
                                </ul>
                            </div>
                            <div class="model-edit-view-content">
                                <!--基础设置-->
                                <div class="model-edit-view-content-common" id="editContent_mobile_common">

                                </div>
                                <!--视图设置-->
                                <div class="model-edit-view-content-design" id="editContent_mobile_design">
                                    <div data-lui-type="sys/modeling/base/views/business/res/calendar/calendarViewContainer!CalendarViewContainer"
                                         id="mobileViewContainer" style="display:none;margin-top:10px">
                                        <script type="text/config">
                                                     {
                                                        storeData : $("[name='fdConfig']").val(),
                                                        headerClass : 'panel-tab-header',
                                                        mainClass : 'panel-tab-main',
                                                        mode : "mobile",
                                                        isMultiTab: false,
                                                        fdType:'',
                                                        xformId:'${xformId}'
                                                    }
                                                </script>
                                        <div class="panel-tab-content">
                                            <div class="panel-tab-header"></div>
                                            <div class="panel-tab-main"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--基础设置的模板-->
    <div class="model-edit-view-content-common board-view" id="editContent_common" style="display: none">
        <div class="model-edit-view-content-common-name">
            <div class="common_title">
                <bean:message  bundle="sys-modeling-base" key="modelingAppListview.fdName"/>
            </div>
            <div class="name-content">
                <div _xform_type="text" class="collection-view-name">
                    <input name="fdName" subject="名称" class="name-content-text" value="${modelingCalendarForm.fdName}" type="text"
                           validate="required maxLength(36)" style="width:97%;">
                    <span class="txtstrong">*</span>
                </div>
            </div>
        </div>
        <!--可访问者-->
        <div id="fdOrgElementTr">
            <div class="auth-common-reader-head">
                <div class="common_title">
                  ${lfn:message('sys-modeling-base:respanel.fdAuthReaders')}
                </div>
                <div class="common-auth-type common-auth-reader">
                    <input type="radio" name="auth-filter" value="0" checked>${lfn:message('sys-modeling-base:listview.unified.set')}
                    <input type="radio" name="auth-filter" value="1">${lfn:message('sys-modeling-base:calendar.independent.set')}
                </div>
            </div>
            <div width=85% class="model-view-panel-table-td common-auth-type-unify" style="display: none;">
                <xform:address textarea="true"
                               mulSelect="true" propertyId="authReaderIds"
                               propertyName="authReaderNames" style="width:91%;height:120px;margin:10px 20px;border: 1px solid #DFE3E9;"></xform:address>
                <div style="color: #999999;margin:0px 20px;">${lfn:message('sys-modeling-base:modeling.empty.everyone.access')}</div>
            </div>
            <div width=85% class="model-view-panel-table-td common-auth-type-alone" style="display: none;">
                <div class="auth-type-title">pc</div>
                <xform:address textarea="true"
                               mulSelect="true" propertyId="pcAuthReaderIds"
                               propertyName="pcAuthReaderNames" style="width:90%;height:120px;margin:10px 20px;border: 1px solid #DFE3E9;"></xform:address>
                <div style="color: #999999;margin:0px 20px;">${lfn:message('sys-modeling-base:modeling.empty.everyone.access')}</div>
                <div class="auth-type-pc-title">${lfn:message('sys-modeling-base:listview.mobile')}</div>
                <xform:address textarea="true"
                               mulSelect="true" propertyId="mobileAuthReaderIds"
                               propertyName="mobileAuthReaderNames" style="width:90%;height:120px;margin:10px 20px;border: 1px solid #DFE3E9;"></xform:address>
                <div style="color: #999999;margin:0px 20px;">${lfn:message('sys-modeling-base:modeling.empty.everyone.access')}</div>
            </div>
        </div>
        <!--权限过滤-->
        <div class="common-auth-filter">
            <div class="common_title" style="display: inline-block">
                    ${lfn:message('sys-modeling-base:listview.fdAuthEnabled')}
                <div class="modeling-viewcover-tip">
                    <span>${lfn:message('sys-modeling-base:calendar.tips')}</span>
                </div>
            </div>
            <div class="common-auth-value" style="display: inline-block;float: right;margin:10px 32px;">
                <ui:switch property="fdAuthEnabled" checkVal="1" unCheckVal="0"></ui:switch>
            </div>
        </div>
        <!--数据过滤-->
        <div class="common-data-filter">
            <div class="auth-common-reader-head">
                <div class="common_title" style="display: inline-block">
                    <%--数据过滤--%>
                    ${lfn:message('sys-modeling-base:listview.data.filter')}
                </div>
                <div class="common-data-filter-value">
                    <input type="radio" name="data-filter-type" value="0" checked>${lfn:message('sys-modeling-base:listview.unified.set')}
                    <input type="radio" name="data-filter-type" value="1">${lfn:message('sys-modeling-base:calendar.independent.set')}
                </div>
            </div>
            <div class="data-filter-content-unify data-filter" name="unify" style="display: none">

            </div>
            <div class="data-filter-content-alone data-filter" name="alone" style="display: none">
                <div class="data-filter-div data-filter-pc" name="pc"></div>
                <div class="data-filter-div data-filter-mobile" name="mobile"></div>
            </div>

        </div>
    </div>
    <!--查询条件模板-->
    <div class="common-data-filter-temp" style="display: none">
        <div class="common-auth-type common-data-filter-whereType">
            <ul>
                <li class="active" value="0">${lfn:message('sys-modeling-base:modeling.custom.query')}</li>
                <li value="1">${lfn:message('sys-modeling-base:modeling.builtIn.query')}</li>
            </ul>
        </div>
        <div class="data-filter-content">
            <div class="common-data-filter-type">
                <input type="radio" name="data-filter-whereType" class="data-filter-whereType" value="0" checked>${lfn:message('sys-modeling-base:calendar.meet.all.conditions')}
                <input type="radio" name="data-filter-whereType" class="data-filter-whereType" value="1">${lfn:message('sys-modeling-base:relation.meet.any.conditions')}
            </div>
            <table class='where-block-table tb_simple model-edit-view-oper-content-table'
                   data-table-type='where' name='custom_query'>
            </table>
        </div>
        <div class="data-filter-content-sys">
            <div class="common-data-filter-type">
                <input type="radio" name="sys-filter-whereType" class="sys-filter-whereType" value="0" checked>${lfn:message('sys-modeling-base:calendar.meet.all.conditions')}
                <input type="radio" name="sys-filter-whereType" class="sys-filter-whereType" value="1">${lfn:message('sys-modeling-base:relation.meet.any.conditions')}
            </div>
            <table class='where-block-table tb_simple model-edit-view-oper-content-table'
                   data-table-type='where' name='sys_query'>
            </table>
        </div>
        <div class="model-data-create">
            <div>${lfn:message('sys-modeling-base:button.add')}</div>
        </div>
    </div>
    <html:hidden property="fdId"></html:hidden>
    <html:hidden property="modelMainId"></html:hidden>
    <html:hidden property="docCreateTime"></html:hidden>
    <html:hidden property="docCreatorId"></html:hidden>
    <html:hidden property="authSettingType"/>
    <html:hidden property="fdConfig"></html:hidden>
    <html:hidden property="method_GET"/>
</html:form>
<script type="text/javascript">
    var _validation = $KMSSValidation();
    var listviewOption = {
        param: {
        },
        fdAuthEnabledHit:'<bean:message bundle="sys-modeling-base" key="modelingAppListview.fdAuthEnabled.hit"/>',
        isEnableFlow: {
            isFlowBoolean: "${isFlowBoolean}",
            isFlow: "${isFlow}"
        },
        modelingCalendarForm: {
            fdModelName : '${modelingCalendarForm.modelMainName}',
            fdName :'${modelingCalendarForm.fdName}',
            fdConfig :'${modelingCalendarForm.fdConfig}',
            pcAuthReaderIds:'${modelingCalendarForm.pcAuthReaderIds}',
            mobileAuthReaderIds:'${modelingCalendarForm.mobileAuthReaderIds}',
            pcAuthReaderNames:'${modelingCalendarForm.pcAuthReaderNames}',
            mobileAuthReaderNames:'${modelingCalendarForm.mobileAuthReaderNames}',
        },
        dialogs : {
            sys_modeling_operation_selectListviewOperation : {
                modelName : 'com.landray.kmss.sys.modeling.base.model.SysModelingOperation',
                sourceUrl : '/sys/modeling/base/sysModelingOperationData.do?method=selectListviewOperation&fdAppModelId=${param.fdModelId }'
            }
        },
        <c:if test="${baseInfo!=null}">baseInfo:${baseInfo}</c:if>
    };
    window.colorChooserHintInfo = {
        cancelText: '取消',
        chooseText: '确定'
    };
    Com_IncludeFile("Sortable.min.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
    Com_IncludeFile("calendarTable.js", Com_Parameter.ContextPath + "sys/modeling/base/views/business/res/preview/", "js", true);
    Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
        + 'sys/modeling/base/resources/js/', 'js', true);
    function returnListPage() {
        var url = Com_Parameter.ContextPath + 'sys/modeling/base/views/business/calendar/calendar_index.jsp?fdModelId=${modelingCalendarForm.modelMainId}';
        var iframe = window.parent.document.getElementById("trigger_iframe");
        $(iframe).attr("src", url);
        return false;
    }


    seajs.use(["sys/modeling/base/views/business/res/modelingCalendar", "lui/dialog", "lui/jquery", 'lui/topic']
        , function (modelingCalendar, dialog, $, topic) {
            //窗口大小自适应-------------------------------
            function onResizeFitWindow() {
                var height = $('body').height();
                if ("${param.isInDialog}") {
                    $('.model-edit-view-title:eq(0)').hide()
                } else {
                    height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
                }

                $("body", parent.document).find('#trigger_iframe').height(height);
                $(".model-edit-right-wrap").height(height - 100);
                // $(".model-edit-left-wrap .model-edit-view-content").height(height - 60);
                $("body", parent.document).css("overflow", "hidden");
            }

            $(window).resize(function () {
                onResizeFitWindow();
            });
            onResizeFitWindow();
            //预览加载完毕事件
            topic.subscribe('preview_load_finish', function (ctx) {
                onResizeFitWindow();
            });


            //窗口大小自适应-------------------------------end

            window.onclose = function () {
                $dialog.hide(null);
            };
            window.dosubmit = function (type) {
                var common = window.modelingCalendar.getKeyData();
                var config = {};
                if(common.hasOwnProperty("authSettingType")){
                    $("input[name='authSettingType']").val(common.authSettingType);
                }
                config["fdCommon"] = common;
                config["fdPcCfg"] = LUI("pcViewContainer").views[0].getKeyData();
                config["fdMobileCfg"] = LUI("mobileViewContainer").views[0].getKeyData();

                var validateResult = window.modelingCollection_beforeSubmitValidate();
                if (!validateResult) {
                    $("input[name='fdConfig']").val(JSON.stringify(config));
                    Com_Submit(document.modelingCalendarForm, type);
                }else {
                    dialog.alert(validateResult)
                }
            };

            window.modelingCollection_beforeSubmitValidate = function(){
                var $KMSSValidation = $GetKMSSDefaultValidation();
                var fdName = $("input[name='fdName']",document)[0];
                var result = $KMSSValidation.validateElement(fdName);
                var pc_fdCalendarShowField = $("input[name='fd_pc_fdCalendarShowField']",document)[0];
                var pc_fdDateField = $("input[name='fd_pc_fdDateField']",document)[0];
                var pc_fdCalendarShowFieldtResult = $KMSSValidation.validateElement(pc_fdCalendarShowField);
                var pc_fdDateFieldResult = $KMSSValidation.validateElement(pc_fdDateField);
                var mobile_fdCalendarShowField = $("input[name='fd_mobile_fdCalendarShowField']",document)[0];
                var mobile_fdDateField = $("input[name='fd_mobile_fdDateField']",document)[0];
                var mobile_fdCalendarShowFieldtResult = $KMSSValidation.validateElement(mobile_fdCalendarShowField);
                var mobile_fdDateFieldResult = $KMSSValidation.validateElement(mobile_fdDateField);
                var maxLength = $KMSSValidation.getValidator("maxLength(36)");
                if (!result){
                    if(maxLength.test($("input[name='fdName']").val(),fdName) == false){
                        $(".validation-container").remove();
                        //超长提示框
                        $(".collection-view-name").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                            "<table class=\"validation-table\"><tbody><tr><td>" +
                            "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                            "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">${lfn:message('sys-modeling-base:modeling.model.fdName')}</span>" +
                            "${lfn:message('sys-modeling-base:listview.enter.up.12.Chinese')}</td></tr></tbody></table></div></div>"));
                        return "${lfn:message('sys-modeling-base:calendar.validate')}";
                    }else{
                        $(".validation-container").remove();
                        //必填提示框
                        $(".collection-view-name").append("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                            "<table class=\"validation-table\"><tbody><tr><td>" +
                            "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                            "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">${lfn:message('sys-modeling-base:modeling.model.fdName')}</span>" +
                            "${lfn:message('sys-modeling-base:kmReviewMain.notNull')}</td></tr></tbody></table></div></div>");
                        return "${lfn:message('sys-modeling-base:calendar.validate')}";
                    }
                }else{
                    if(pc_fdCalendarShowFieldtResult== false ||  pc_fdDateFieldResult == false){
                        return "${lfn:message('sys-modeling-base:listview.pc.verification.fail')}";
                    }
                    if(mobile_fdCalendarShowFieldtResult== false ||  mobile_fdDateFieldResult == false){
                        return "${lfn:message('sys-modeling-base:listview.mobile.verification.fail')}";
                    }
                }
            }
            window.getValidateHtml = function (subject, type) {
                var html = '<div class="validation-advice" id="advice-_validate_' + type + '" >'
                    + '<table class="validation-table"><tbody><tr>'
                    + '<td><div class="lui_icon_s lui_icon_s_icon_validator"></div></td>'
                    + '<td class="validation-advice-msg"><span class="validation-advice-title">' + subject + '</span> ${lfn:message('sys-modeling-base:kmReviewMain.notNull')}</td>'
                    + '</tr></tbody></table></div>';
                return html;
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

            window.changeContentView =function(type) {
                window.calendarViewType = type;
                $(".modeling-pam-top-center li").removeClass("active");
                $(".modeling-pam-top-center ul").find("li").each(function () {
                    if($(this).attr("value") === type){
                        $(this).addClass("active");
                    }
                });
                if (type === "pc") {
                    $("#modeling-pam-content-pc").show();
                    $("#modeling-pam-content-mobile").hide();
                } else {
                    $("#modeling-pam-content-pc").hide();
                    $("#modeling-pam-content-mobile").show();
                }
                changeRightContentView("common");
                var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true)-115;
                $(".model-edit-right-wrap").css("height", bodyHeight);
                $("#view_preview").css("height", bodyHeight);
                $("#view_preview_mobile").css("height", bodyHeight);
            }

            window.changeRightContentView = function(type){
                $(".model-edit-view-content-common").css("display","block");
                $(".model-edit-view-title li").removeClass("active");
                $(".model-edit-view-title ul").find("li").each(function () {
                    if($(this).attr("value") === type){
                        $(this).addClass("active");
                    }
                });
                var modeligWhereBlock = {};
                if(window.modelingCalendar){
                    modeligWhereBlock = window.modelingCalendar.getKeyData();
                }
                if(type === "common"){
                    var modelingCalendar_cfg = {
                        contentContainer:$(".modeling-pam-content"),
                        contentContent:$("#editContent_common"),
                        whereContentTemp:$(".common-data-filter-temp"),
                        collectionViewType:window.calendarViewType,
                        fdType:listviewOption.modelingCalendarForm.fdType,
                        fdConfig:listviewOption.modelingCalendarForm.fdConfig,
                        modeligWhereBlock:modeligWhereBlock.fdWhereBlock,
                        xformId: "${xformId}",
                        flowInfo:${flowInfo},
                        modelMainId: "${modelingCalendarForm.modelMainId}",
                        widgets: ${widgets},
                        fdConfig: $("[name='fdConfig']").val()
                    };
                    window.modelingCalendar = new modelingCalendar.ModelingCalendar(modelingCalendar_cfg);
                    window.modelingCalendar.startup();
                    $("#editContent_pc_design").hide();
                    $("#editContent_mobile_design").hide();
                }else {
                    $(".model-edit-view-content-common").css("display","none");
                    if(window.calendarViewType === "pc") {
                        $("#editContent_pc_design").show();
                        $("#editContent_mobile_design").hide();
                    } else {
                        $("#editContent_pc_design").hide();
                        $("#editContent_mobile_design").show();
                    }
                    topic.publish("switchRightContentView", {key: window.calendarViewType});
                }
            }
            changeRightContentView("common");

            window.switchSelectPosition = function (obj, direct) {
                Com_EventStopPropagation();
                var context;
                if (calendarViewType === "pc") {
                    context = $("#modeling-pam-content-pc");
                } else {
                    context = $("#modeling-pam-content-mobile");
                }
                $("[data-lui-position]", context).removeClass("active");
                var position = $(obj).attr("data-lui-position");
                if(!position){
                    position = $(obj).closest("td").attr("data-lui-position");
                }
                $("[data-lui-position='" + position + "']", context).addClass("active");
                if (direct == 'left' && position) {//左边
                    if (position == "fdName") {
                        var viewId = $(obj).attr("view-id");
                        //右边切换
                        LUI("viewContainer").switchView(viewId);
                    } else if (position.indexOf("fdOrderBy") != -1) {
                        changeRightContentView("design");
                        $("[data-lui-position='fdOrderBy']", context).addClass("active");
                        $("#fdOrderBy_more").hide();
                        $("div[data-lui-position='fdOrderBy_more']", context).removeClass("active");
                    } else if (position.indexOf("fdDisplay") != -1) {
                        changeRightContentView("design");
                        //$("[data-lui-position='fdDisplay']", context).addClass("active");
                    } else if (position.indexOf("fdCondition") != -1) {
                        changeRightContentView("design");
                        $("[data-lui-position='fdCondition']", context).addClass("active");
                    }else if (position.indexOf("fdOperation") != -1) {
                        changeRightContentView("design");
                        $("[data-lui-position='fdOperation']", context).addClass("active");
                    }else if (position.indexOf("tableTitle") != -1) {
                        changeRightContentView("design");
                        $("[data-lui-position='tableTitle']", context).addClass("active");
                    }
                } else if(direct == 'right' && position) {//右边
                    if (position.indexOf("fdOrderBy") != -1) {
                        $("[data-lui-position='fdOrderBy']", context).addClass("active");
                        $("#fdOrderBy_more", context).hide();
                        if ($("[data-lui-position='" + position + "']", context).parents("div[data-lui-position='fdOrderBy_more']").eq(0).length <= 0) {
                            $("div[data-lui-position='fdOrderBy_more']", context).removeClass("active");
                        }
                    } else if (position.indexOf("fdDisplay") != -1) {
                        $("[data-lui-position='fdDisplay']", context).addClass("active");
                    } else if (position.indexOf("fdOperation") != -1) {
                        $("[data-lui-position='fdOperation']", context).addClass("active");
                    }else if (position.indexOf("tableTitle") != -1) {
                        $("[data-lui-position='tableTitle']", context).addClass("active");
                    }
                }
                //进行滚轮处理
                if (direct == 'left' && position) {
                    var panel = $(".model-edit-right", context).find(".model-edit-right-wrap");
                    var target = $(".model-edit-right", context).find("[data-lui-position='" + position + "']").eq(0);
                    if (panel && target && target.offset() && panel.offset()) {
                        var scrollTop = target.offset().top - panel.offset().top + panel.scrollTop() - 50;
                        panel.scrollTop(scrollTop)
                    }
                }
                return false;
            }

            //切换事件
            topic.subscribe('switchSelectPosition', function (data) {
                console.log("switchSelectPosition");
                if (data.key === calendarViewType) {
                    switchSelectPosition(data.dom, 'right');
                }
            });

            //改变标志，避免拖动事件和点击事件冲突
            window.changeFlag = function (oper) {
                if (oper == 'down') {
                    isDown = true;
                } else if (oper == 'move') {
                    if (isDown) {
                        flag = false;
                    }
                } else if (oper == "up") {
                    isDown = false;
                }
            }

            //数据过滤——删除行
            window.delTr = function (dom, type) {
                var tb = $(dom).closest("table")[0];
                var $tr = $(dom).closest("tr");
                var curIndex = $tr.index();
                if (type == 'where') {
                    window.modelingCalendar.delWhereTr($(dom));
                }
                $tr.remove();
            }

            // 移动 -1：上移       1：下移
            window.moveTr = function(direct, dom, type) {
                var tb = $(dom).closest("table")[0];
                var $tr = $(dom).closest("tr");
                var curIndex = $tr.index();
                var lastIndex = tb.rows.length - 1;
                var targetIndex = curIndex;
                if (direct == 1) {
                    if (curIndex >= lastIndex) {
                        alert("已经到底了！");
                        return;
                    }
                    $tr.next().after($tr);
                    targetIndex = curIndex + 1;
                } else {
                    if (curIndex < 1) {
                        alert("已经移动至最顶！");
                        return;
                    }
                    $tr.prev().before($tr);
                    targetIndex = curIndex - 1;
                }
                if (curIndex != targetIndex) {
                    window.modelingCalendar.moveWgt($(dom),type, curIndex, targetIndex);
                }
            }

            //选择框切换数据后事件
            topic.channel("modeling").subscribe("field.change", function (data) {
                var selectDom = data.dom;
                //更新标题
                var $parent = $(selectDom).parents("div.select_union").eq(0);
                var text = "";
                var fieldId = $parent.find("select").eq(0).val();
                var fieldText = $parent.find("select").eq(0).find("option[value='" + fieldId + "']").text();
                text = fieldText;
                fieldId = $parent.find("select").eq(1).val();
                fieldText = $parent.find("select").eq(1).find("option[value='" + fieldId + "']").text();
                if (fieldText) {
                    text += "|" + fieldText;
                }
                $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text(text);
                console.log("field.change");
                //刷新预览
                topic.publish("preview.refresh");
            })

            // 数据过滤——类型切换事件，更新头部标题
            topic.channel("modeling").subscribe("whereType.change", function (data) {
                var selectDom = data.dom;
                var text = "";
                // 0（自定义查询项）|1（内置查询项）
                if (data.value === "0") {
                    text = data.wgt.fieldWgt.getFieldText();
                } else if (data.value === "1") {
                    text = $(selectDom).closest(".list-content").find("[data-bind-type-value='1']").find("select option:selected").text();
                }
                $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text(text);
            });

            topic.subscribe('mobile_view_load_finish', function (data) {
                var previewWgt = LUI("view_preview_mobile");
                var value = LUI("mobileViewContainer").getKeyData();
                previewWgt.setSourceData(value, 'data');
                previewWgt.reRender();
                $(".model-edit-left .model-body-content-phone-wrap").on('click', function () {
                    $("[data-lui-position]").removeClass("active");
                    return false;
                })
            });

            //预览更新事件
            topic.subscribe('preview.refresh', function (data) {
                //刷新预览
                if (data && data.key === window.calendarViewType) {
                    try {
                        var previewWgt;
                        var containerName;
                        if (calendarViewType === "pc") {
                            previewWgt = LUI("view_preview");
                            containerName = "pcViewContainer";
                        } else {
                            previewWgt = LUI("view_preview_mobile");
                            containerName = "mobileViewContainer";
                        }
                        var value = LUI(containerName).getKeyData();
                        previewWgt.setSourceData(value, 'data');
                        previewWgt.reRender();
                    } catch (e) {
                        console.log(e);
                    }
                }
            });

            LUI.ready(function(){
                changeContentView('pc');
                var titleName = listviewOption.modelingCalendarForm.fdName;
                if(titleName){
                    $(".listviewName").text(titleName);
                    $(".listviewName").attr("title",titleName);
                }
                $("input[name='fdName']").blur(function(){
                    $(".validation-container").remove();
                });
                var fdConfig = listviewOption.modelingCalendarForm.fdConfig;
                if(!fdConfig){
                    $(".common-auth-value").find("input[type='checkbox']").trigger("click");
                }
            });

        })

</script>
