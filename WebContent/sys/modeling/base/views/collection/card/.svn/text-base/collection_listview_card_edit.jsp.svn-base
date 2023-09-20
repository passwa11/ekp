<%@ page import="com.landray.kmss.sys.modeling.base.views.collection.forms.ModelingAppCollectionViewForm" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%
    ModelingAppCollectionViewForm modelingAppCollectionViewForm = (ModelingAppCollectionViewForm) request.getAttribute("modelingAppCollectionViewForm");
    if(modelingAppCollectionViewForm != null){
        String fdConfig = modelingAppCollectionViewForm.getFdConfig();
        if (StringUtil.isNotNull(fdConfig)) {
            //将json的value中的"替换为\"
            fdConfig = fdConfig.replaceAll("\\\\\"","\\\\\\\\\"");
            pageContext.setAttribute("fdConfig",fdConfig);
        }
    }
%>
<template:include ref="config.edit" sidebar="no">
    <template:replace name="head">
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/collectionListViewPreview.css?s_cache=${LUI_Cache}" />
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/collectionCardListView.css?s_cache=${LUI_Cache}" />
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/card/cardMobileConfig.css?s_cache=${LUI_Cache}" />
        <link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/card/cardConfig.css?s_cache=${LUI_Cache}" />
        <script>
            Com_IncludeFile("doclist.js");
            Com_IncludeFile("Sortable.min.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
            Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
                + 'sys/modeling/base/resources/js/', 'js', true);
        </script>
    </template:replace>
    <template:replace name="content">
        <html:form action="/sys/modeling/base/modelingAppCollectionView.do">
        <c:import url="/sys/modeling/base/views/collection/collection_listview_edit_top.jsp" charEncoding="UTF-8">
            <c:param name="fdModelId" value="${modelingAppCollectionViewForm.fdModelId}"/>
            <c:param name="method" value="eidt"/>
        </c:import>
        <html:hidden property="fdId"/>
        <html:hidden property="fdModelId"/>
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
                                            src : '/sys/modeling/base/views/collection/card/preview/listview_pc_card.html#'
                                        }
                                         </script>
                                    </div>
                                </div>
                            </div>
                            <!--右边内容区-->
                            <div class="modeling-pam-content-pc-right">
                                <div class="model-edit-right">
                                    <div class="model-edit-right-wrap" style="overflow: auto;height:560px">
                                        <div class="model-edit-view-title">
                                            <ul>
                                                <li class="setting active" value="common" onclick="changeRightContentView('common')"><span>${lfn:message('sys-modeling-base:listview.basic.set')}</span></li>
                                                <li value="design" onclick="changeRightContentView('design')"><span>${lfn:message('sys-modeling-base:listview.view.set')}</span></li>
                                            </ul>
                                        </div>
                                        <div class="model-edit-view-content">
                                            <!--基础设置-->
                                            <div class="model-edit-view-content-common" id="editContent_pc_common"></div>
                                            <!--视图设置-->
                                            <div class="model-edit-view-content-design card-view" id="editContent_pc_design">
                                                <div data-lui-type="sys/modeling/base/mobile/resources/js/viewContainer!ViewContainer"
                                                     id="pcViewContainer" style="display:none;margin-top:10px">
                                                    <script type="text/config">
                                                        {
                                                            storeData : $.parseJSON('${fdConfig}' || '{}'),
                                                            headerClass : 'panel-tab-header',
                                                            mainClass : 'panel-tab-main',
                                                            mode : "pc",
                                                            fdType:'${modelingAppCollectionViewForm.fdType}',
                                                            isMultiTab: false,
                                                            sysModelingOperationJson: $.parseJSON('${sysModelingOperationJson}' || '{}')
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
                                         src : '/sys/modeling/base/views/collection/card/preview/listview_mobile_card.html#'
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
                                        <div class="model-edit-view-content-design card-view" id="editContent_mobile_design">
                                            <div data-lui-type="sys/modeling/base/mobile/resources/js/viewContainer!ViewContainer"
                                                 id="mobileViewContainer" style="display:none;margin-top:10px">
                                                <script type="text/config">
                                                     {
                                                        storeData : $.parseJSON('${fdConfig}' || '{}'),
                                                        headerClass : 'panel-tab-header',
                                                        mainClass : 'panel-tab-main',
                                                        mode : "mobile",
                                                        fdType:'${modelingAppCollectionViewForm.fdType}',
                                                        isMultiTab: false
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
            <div class="model-edit-view-content-common card-view" id="editContent_common" style="display: none">
                <div class="model-edit-view-content-common-name">
                    <div class="common_title">
                        <bean:message  bundle="sys-modeling-base" key="modelingAppListview.fdName"/>
                    </div>
                    <div class="name-content">
                        <div _xform_type="text" class="collection-view-name">
                            <input name="fdName" subject="${lfn:message('sys-modeling-base:listview.view.name')}" class="name-content-text" value="${modelingAppCollectionViewForm.fdName}" type="text"
                                   validate="required maxLength(36)" style="width:97%;">
                            <span class="txtstrong">*</span>
                        </div>
                    </div>
                </div>
                <!--视图类型-->
                <div class="model-edit-view-content-common-type">
                    <div class="common_title">
                            ${lfn:message('sys-modeling-base:listview.view.type')}
                    </div>
                    <div class="type-content">
                        <input type="hidden" name="fdType" value="">
                        <div class="type-container-text">

                        </div>
                    </div>
                    <div class="type-selectBoxs" style="display: none;">
                        <ul>
                            <li value="1">${lfn:message('sys-modeling-base:listview.cardView')}</li>
                        </ul>
                    </div>
                </div>
                <!--可访问者-->
                <div id="fdOrgElementTr">
                    <div class="auth-common-reader-head">
                        <div class="common_title">
                                ${lfn:message('sys-modeling-base:modelingAppListview.authSearchReaders')}
                        </div>
                        <div class="common-auth-type common-auth-reader">
                            <input type="radio" name="auth-filter" value="0" checked>${lfn:message('sys-modeling-base:listview.unified.set')}
                            <input type="radio" name="auth-filter" value="1">${lfn:message('sys-modeling-base:listview.independent.set')}
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
                            <span>${lfn:message('sys-modeling-base:listview.cardView')}${lfn:message('sys-modeling-base:modelingAppListview.fdAuthEnabled.hit')}</span>
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
                                ${lfn:message('sys-modeling-base:listview.data.filter')}
                        </div>
                        <div class="common-data-filter-value">
                            <input type="radio" name="data-filter-type" value="0" checked>${lfn:message('sys-modeling-base:listview.unified.set')}
                            <input type="radio" name="data-filter-type" value="1">${lfn:message('sys-modeling-base:listview.independent.set')}
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
                        <input type="radio" name="data-filter-whereType" class="data-filter-whereType" value="0" checked>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}
                        <input type="radio" name="data-filter-whereType" class="data-filter-whereType" value="1">${lfn:message('sys-modeling-base:relation.meet.any.conditions')}
                    </div>
                    <table class='where-block-table tb_simple model-edit-view-oper-content-table'
                           data-table-type='where' name='custom_query'>
                    </table>
                </div>
                <div class="data-filter-content-sys">
                    <div class="common-data-filter-type">
                        <input type="radio" name="sys-filter-whereType" class="sys-filter-whereType" value="0" checked>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}
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
            <html:hidden property="authSettingType"/>
            <html:hidden property="fdConfig"/>
        </html:form>

        <script type="text/javascript">
            var _validation = $KMSSValidation();
            var listviewOption = {
                param: {
                    fdId: "${param.fdId}",
                    fdAppId: "${fdAppId}",
                    fdModelId: "${param.fdModelId }",
                    contextPath: Com_Parameter.ContextPath,
                    isInDialog: "${param.isInDialog}",
                    isPcAndMobile: '${param.isPcAndMobile}'
                },
                fdAuthEnabledHit:'<bean:message bundle="sys-modeling-base" key="modelingAppListview.fdAuthEnabled.hit"/>',
                isEnableFlow: {
                    isFlowBoolean: "${isFlowBoolean}",
                    isFlow: "${isFlow}"
                },
                modelingAppCollectionViewForm: {
                    fdModelName : '${modelingAppCollectionViewForm.fdModelName}',
                    fdName :'${modelingAppCollectionViewForm.fdName}',
                    fdType :'${modelingAppCollectionViewForm.fdType}',
                    fdConfig :'${fdConfig}',
                    pcAuthReaderIds:'${modelingAppCollectionViewForm.pcAuthReaderIds}',
                    mobileAuthReaderIds:'${modelingAppCollectionViewForm.mobileAuthReaderIds}',
                    pcAuthReaderNames:'${modelingAppCollectionViewForm.pcAuthReaderNames}',
                    mobileAuthReaderNames:'${modelingAppCollectionViewForm.mobileAuthReaderNames}',
                },
                dialogs : {
                    sys_modeling_operation_selectListviewOperation : {
                        modelName : 'com.landray.kmss.sys.modeling.base.model.SysModelingOperation',
                        sourceUrl : '/sys/modeling/base/sysModelingOperationData.do?method=selectListviewOperation&fdAppModelId=${param.fdModelId }'
                    }
                },
                <c:if test="${baseInfo!=null}">baseInfo:${baseInfo},</c:if>
                <c:if test="${attachments!=null}">attachments:${attachments}</c:if>
            };

            seajs.use([ 'lui/topic', 'lui/jquery','lui/dialog','sys/modeling/base/resources/js/views/collection/listViewEditCommon']
                ,function(topic,$,dialog,listViewEditCommon){

                    function updateOpenRuleSettingXY(){
                        var $openRuleSetting = $(".open_rule_setting");
                        var $openRuleSettingToRight = $(".open_rule_setting_to_right");
                        if(0<$openRuleSetting.length){
                            var $parentButton = $openRuleSetting.parent();
                            var x = $parentButton.offset().top;
                            var y = $parentButton.offset().left;
                            $openRuleSetting.css({
                                "top": x - 200,
                                "left": y - 548
                            });
                            $openRuleSettingToRight.css({
                                "top": x + 8,
                                "left": y - 10
                            });
                        }
                    }

                    // 点击其他地方关闭下拉列表
                    $(window).click(function () {
                        closeStatisticsSetting();
                    });

                    window.closeStatisticsSetting = function (){
                        var $openRuleSetting = $(".open_rule_setting");
                        $openRuleSetting.each(function () {
                            if( "none" !=  $(this).css("display")){
                                $(this).css("display","none")
                            }
                        })

                        var $openRuleSettingToRight = $(".open_rule_setting_to_right");
                        $openRuleSettingToRight.each(function () {
                            if( "none" !=  $(this).css("display")){
                                $(this).css("display","none")
                            }
                        })
                    }

                    $(window).resize(function() {
                        updateOpenRuleSettingXY();
                    })

                    window.statisticsMore = function (event) {
                        console.log( $(event))
                        $(event).css("display","none");
                        $('.model-phone-statistics-less').css("display","inline-block");
                        $(event).parent('.model-body-content-phone-top-statistics').find('.model-body-content-phone-statistics').removeClass('statistics-more');
                    }
                    window.statisticsLess = function (event) {
                        console.log( $(event))
                        $(event).css("display","none");
                        $('.model-phone-statistics-more').css("display","inline-block");
                        $(event).parent('.model-body-content-phone-top-statistics').find('.model-body-content-phone-statistics').addClass('statistics-more');
                    }
                    //window.listViewEditCommon = listViewEditCommon;
                    window.changeContentView = function (type) {
                        window.collectionViewType = type;
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
                            topic.publish("preview.refresh", {"key": "mobile"});
                        }
                        changeRightContentView("common");
                        var bodyHeight = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true)-90;
                        //#152411 链接直接打开这个页面获取不到父页面高度而被挤压，
                        if (bodyHeight<0){
                            bodyHeight =document.body.clientHeight;
                        }
                        $(".model-edit-right-wrap").css("height", bodyHeight);
                        $("#view_preview").css("height", bodyHeight);
                        $("#view_preview_mobile").css("height", bodyHeight + 20);

                        //绑定右边内容区滚动事件
                        $('.model-edit-right-wrap').unbind('scroll').bind('scroll', function(){
                            var scrollTop = $(this).scrollTop();
                            var $openRuleSetting = $(".open_rule_setting");
                            if(0<$openRuleSetting.length){
                                $openRuleSetting.each(function () {
                                    var firstScrollTop =  $(this).attr("scrollTop");
                                    var top = $(this).attr("top");
                                    $(this).css({
                                        "top": top - scrollTop + Number(firstScrollTop),
                                    });
                                })
                            }
                            var $openRuleSettingToRight = $(".open_rule_setting_to_right");
                            if(0<$openRuleSettingToRight.length){
                                $openRuleSettingToRight.each(function () {
                                    var firstScrollTop =  $(this).attr("scrollTop");
                                    var top = $(this).attr("top");
                                    $(this).css({
                                        "top": top - scrollTop + Number(firstScrollTop),
                                    });
                                })
                            }
                        })
                    }

                    window.changeRightContentView = function(type){
                        $(".model-edit-view-content-common").css("display","block");
                        $(".model-edit-view-title li").removeClass("active");
                        $(".model-edit-view-title ul").find("li").each(function () {
                            if($(this).attr("value") === type){
                                $(this).addClass("active");
                            }
                        });
                        if(type === "common"){
                            var listViewEditCommon_cfg = {
                                contentContainer:$(".modeling-pam-content"),
                                contentContent:$("#editContent_common"),
                                whereContentTemp:$(".common-data-filter-temp"),
                                collectionViewType:window.collectionViewType,
                                fdType:listviewOption.modelingAppCollectionViewForm.fdType,
                                fdConfig:listviewOption.modelingAppCollectionViewForm.fdConfig
                            };
                            window.listViewEditCommon = new listViewEditCommon.ListViewEditCommon(listViewEditCommon_cfg);
                            window.listViewEditCommon.startup();
                            $("#editContent_pc_design").hide();
                            $("#editContent_mobile_design").hide();
                        }else {
                            $(".model-edit-view-content-common").css("display","none");
                            if(collectionViewType === "pc") {
                                $("#editContent_pc_design").show();
                                $("#editContent_mobile_design").hide();
                            } else {
                                $("#editContent_pc_design").hide();
                                $("#editContent_mobile_design").show();
                            }
                            topic.publish("switchRightContentView", {key: collectionViewType});
                        }
                    }

                    window.switchSelectPosition = function (obj, direct) {
                        Com_EventStopPropagation();
                        var context;
                        if (collectionViewType === "pc") {
                            context = $("#modeling-pam-content-pc");
                        } else {
                            context = $("#modeling-pam-content-mobile");
                        }
                        $("[data-lui-position]", context).removeClass("active");
                        var position = $(obj).attr("data-lui-position");
                        $("[data-lui-position='" + position + "']", context).addClass("active");
                        if (direct == 'left') {//左边
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
                                $("[data-lui-position='fdDisplay']", context).addClass("active");
                            } else if (position.indexOf("fdCondition") != -1) {
                                changeRightContentView("design");
                                $("[data-lui-position='fdCondition']", context).addClass("active");
                            } else if (position.indexOf("fdStatistics") != -1) {
                                changeRightContentView("design");
                                $("[data-lui-position='fdStatistics']", context).addClass("active");
                            } else if (position.indexOf("fdOperation") != -1) {
                                changeRightContentView("design");
                                $("[data-lui-position='fdOperation']", context).addClass("active");
                            } else if (position.indexOf("fdStatistics") != -1) {
                                changeRightContentView("design");
                                $("[data-lui-position='fdStatistics']", context).addClass("active");
                            }
                        } else {//右边
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
                            } else if (position.indexOf("fdStatistics") != -1) {
                                $("[data-lui-position='fdStatistics']", context).addClass("active");
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

                    window.getValidateHtml = function (subject, type) {
                        var html = '<div class="validation-advice" id="advice-_validate_' + type + '" _reminder="true">'
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

                    window.collectionDoSubmit = function (method) {
                        var common = window.listViewEditCommon.getKeyData();
                        var config = {};
                        if(common.hasOwnProperty("authSettingType")){
                            $("input[name='authSettingType']").val(common.authSettingType);
                        }
                        config["fdCommon"] = common;
                        config["fdPcCfg"] = LUI("pcViewContainer").views[0].getKeyData();
                        config["fdMobileCfg"] = LUI("mobileViewContainer").views[0].getKeyData();
                        $("input[name='fdConfig']").val(JSON.stringify(config));
                        if (modelingCollection_beforeSubmitValidate()) {
                            Com_Submit(document.modelingAppCollectionViewForm, method);
                        }
                    }
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
                           window.listViewEditCommon.delWhereTr($(dom));
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
                                alert("！${lfn:message('sys-modeling-base:listview.is.the.end')}");
                                return;
                            }
                            $tr.next().after($tr);
                            targetIndex = curIndex + 1;
                        } else {
                            if (curIndex < 1) {
                                alert("${lfn:message('sys-modeling-base:listview.moved.to.top')}");
                                return;
                            }
                            $tr.prev().before($tr);
                            targetIndex = curIndex - 1;
                        }
                        if (curIndex != targetIndex) {
                            window.listViewEditCommon.moveWgt($(dom),type, curIndex, targetIndex);
                        }
                    }

                    window.modelingCollection_beforeSubmitValidate = function(){
                        var $KMSSValidation = $GetKMSSDefaultValidation();
                        var fdAduitNote = $("input[name='fdName']",document)[0];
                        var pcSubject = $("input[name='fd_pc_fdSubject']",document)[0];
                        var mobileSubject = $("input[name='fd_mobile_fdSubject']",document)[0];
                        var result = $KMSSValidation.validateElement(fdAduitNote);
                        var mobileSubjectResult = $KMSSValidation.validateElement(mobileSubject);
                        var pcSubjectResult = $KMSSValidation.validateElement(pcSubject);
                        var maxLength = $KMSSValidation.getValidator("maxLength(36)");
                        if (!result){
                            if(maxLength.test($("input[name='fdName']").val(),fdAduitNote) == false){
                                $(".validation-container").remove();
                                //超长提示框
                                $(".collection-view-name").append($("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                                    "<table class=\"validation-table\"><tbody><tr><td>" +
                                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">${lfn:message('sys-modeling-base:modelingAppListview.fdName')}</span>" +
                                    "${lfn:message('sys-modeling-base:listview.enter.up.12.Chinese')}</td></tr></tbody></table></div></div>"));
                            }else{
                                $(".validation-container").remove();
                                //必填提示框
                                $(".collection-view-name").append("<div class='validation-container'><div class=\"validation-advice\" _reminder=\"true\">" +
                                    "<table class=\"validation-table\"><tbody><tr><td>" +
                                    "<div class=\"lui_icon_s lui_icon_s_icon_validator\"></div></td>" +
                                    "<td class=\"validation-advice-msg\"><span class=\"validation-advice-title\">${lfn:message('sys-modeling-base:modelingAppListview.fdName')}</span>" +
                                    "${lfn:message('sys-modeling-base:kmReviewMain.notNull')}</td></tr></tbody></table></div></div>");
                            }
                            return false;
                        }else{
                            var errorMsg = '';
                            if(mobileSubjectResult == false){

                                errorMsg ="${lfn:message('sys-modeling-base:listview.mobile.verification.fail')}</br></br>";
                            }
                            var pcValidateResult = LUI("pcViewContainer").views[0].validate();
                            if(pcSubjectResult == false || !pcValidateResult){

                                errorMsg = errorMsg ? (errorMsg+'${lfn:message('sys-modeling-base:listview.pc.verification.fail')}</br>'):('${lfn:message('sys-modeling-base:listview.pc.verification.fail')}</br>');
                            }
                            var mobileValidateResult = LUI("mobileViewContainer").views[0].validate();
                            if (!mobileValidateResult) {
                                dialog.alert('${lfn:message('sys-modeling-base:listview.mobile.verification.fail')}');
                                return false;
                            }
                            if(errorMsg){
                                dialog.alert(errorMsg);
                                return false;
                            }
                            return true;
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
                                    '<td class="validation-advice-msg"><span class="validation-advice-title"></span> ${lfn:message("sys-modeling-base:listview.must.valid.number")}.</td>' +
                                    '</tr>' +
                                    '</table></div>';
                                $whereRule.append(html)
                                $whereRule.css("height","70px");
                            }
                            return false;
                        }
                    }

                    //切换事件
                    topic.subscribe('switchSelectPosition', function (data) {
                        if (data.key === collectionViewType) {
                            switchSelectPosition(data.dom, 'right');
                        }
                    });

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
                        if (data && data.key === window.collectionViewType) {
                            try {
                                var previewWgt;
                                var containerName;
                                if (collectionViewType === "pc") {
                                    previewWgt = LUI("view_preview");
                                    containerName = "pcViewContainer";
                                } else {
                                    previewWgt = LUI("view_preview_mobile");
                                    containerName = "mobileViewContainer";
                                }
                                var value = LUI(containerName).getKeyData();
                                previewWgt.setSourceData(value, 'data');
                                previewWgt.reRender();
                                if (collectionViewType === "mobile") {
                                    if ($(".model-phone-opt-wrap").find(".model-phone-opt-item").length > 3) {
                                        $(".model-phone-opt-item").css("width", "25%");
                                    }
                                }
                            } catch (e) {
                                console.log(e);
                            }
                        }
                    });
                });

            LUI.ready(function(){
                changeContentView('pc');
                var titleName = listviewOption.modelingAppCollectionViewForm.fdName;
                if(titleName){
                    $(".listviewName").text(titleName);
                    $(".listviewName").attr("title",titleName);
                }
                $("input[name='fdName']").blur(function(){
                    $(".validation-container").remove();
                });
                var fdConfig = listviewOption.modelingAppCollectionViewForm.fdConfig;
                if(!fdConfig){
                    $(".common-auth-value").find("input[type='checkbox']").trigger("click");
                }
            });
        </script>
    </template:replace>
</template:include>
