<%@ page import="com.landray.kmss.sys.xform.util.LangUtil" %>
<!doctype html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit"/>
    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css"/>
    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/style/switch.css"/>
    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css"/>
    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/appList.css"/>
    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/dialog.css"/>
    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/profile/appfselect_jquery/css/template.css"/>
    <script type="text/javascript">
        seajs.use(['theme!profile']);
        Com_IncludeFile("plugin.js");
        Com_IncludeFile('dialog.js');

    </script>
    <style type="text/css">
        /*空页面*/
        .app_empty_panel_table > td {
            padding-top: 0 !important;
        }

        .app_empty_panel_table .td_normal_title {
            font-size: 14px;
            vertical-align: top;
        }

        .app_empty_panel_table .app_empty_panel_table_td {
            padding-left: 20px;
            padding-bottom: 20px;
        }

        .lui_modeling_main .app_empty {
            position: absolute;
            top: 50%;
            left: 50%;
            margin-left: -250px;
            width: 500px;
            height: 400px;
            margin-top: -200px;
        }

        .app_empty .app_empty_img div {
            width: 164px;
            height: 127px;
            margin: 0 auto;
            background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/empty.png) no-repeat center;
        }

        .app_empty .app_empty_img p {
            font-size: 28px;
            color: #333333;
            text-align: center;
            margin: 28px 0px;
        }

        .app_empty .app_empty_img_editor div {
            width: 480px;
            height: 240px;
            background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/empty_page.png) no-repeat center;
        }

        .app_empty .app_empty_img_editor p {
            font-size: 14px;
            margin: 20px 0px;
        }

        .appMenu_main_icon {
            width: 52px;
            height: 52px;
            background: #FFFFFF;
            box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.10);
            border-radius: 8px;
            display: inline-block;
            text-align: center;
            line-height: 52px;
            border: 1px solid #DFE3E9;
        }

        .appMenu_main_icon i {
            font-size: 30px;
            padding-left: 0px !important;
        }

        .select_icon_btn {
            width: 90px;
            display: inline-block;
            height: 30px;
            border: 1px solid #DDDDDD;
            border-radius: 2px;
            border-radius: 2px;
            bottom: 4px;
            position: relative;
            margin-left: 15px;
            font-size: 14px;
            text-align: center;
            line-height: 30px;
        }

        .lui_custom_list_box_content_col_btn a {
            margin: 0 10px;
        }
        .head_import .hideCreate .lui_profile_block_grid_item_add{
            display: none;
        }
        .head_import .lui_widget_btn .lui_widget_btn_icon {
             vertical-align: middle;
        }
        .head_import .lui_toolbar_btn_def .lui_widget_btn_icon {
            padding: 0px;
        }
        .head_import .lui_widget_btn .lui_widget_btn_icon {
             padding: 0px;
        }
        .lui_widget_btn .modeling_application_icon.lui_icon_s{
            vertical-align:bottom;
        }
        html {
            overflow-y: hidden;
        }
        .appliactionBoxText{
            display: inline-flex;
            display:-webkit-inline-flex;
            align-items: center;
            justify-content: space-between;
            height:27px;
            border: 1px solid #DDDDDD;
            background-color: #fff;
            border-radius: 2px;
            position: relative;
            padding:0 8px;
            float: left;
            margin-top: 11px;
        }
        .appliactionBoxText>span{
            font-size: 12px;
        }
        .appliactionBoxText input{
            font-size: 12px;
            color: #999999;
        }
        .appliactionBoxText>.appliactionBoxIconText{
            float: right;
            width: 20px;
            height: 20px;
            background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/icon_sortInvertedOrder_desc.png) no-repeat center;
        }
        .appliactionBoxListText{
            display: none;
            width: 100%;
            position: absolute;
            top: 100%;
            right:-1px;
            background-color: #fff;
            border: 1px solid #DDDDDD;
            box-shadow: 0 1px 3px 0 rgba(0,0,0,0.15);
            border-radius: 2px;
            z-index: 11;
            box-sizing: content-box;
            transform: translateY(1px);
        }
        .appliactionBoxListText>ul>li{
            display: -webkit-flex;
            align-items: center;
            height: 30px;
            height:30px;
            font-size: 12px;
            padding:0 6px;
            background-color: #fff;
            cursor: pointer;
        }
        .appliactionBoxListText>ul>li:hover{
            background-color: rgba(66,133,244,0.10);
        }

        .headViewTypeBoxText{
            display: inline-flex;
            display:-webkit-inline-flex;
            align-items: center;
            justify-content: space-between;
            height:29px;
            border: 1px solid #DDDDDD;
            background-color: #fff;
            border-radius: 2px;
            position: relative;
            padding:0 8px;
            float: left;
            margin-top: 10px;
        }
        .headViewTypeBoxText>span{
            font-size: 14px;
        }
        .headViewTypeBoxText input{
            font-size: 14px;
            color: #999999;
        }
        .headViewTypeBoxText>.headViewTypeIcon{
            float: left;
            width: 20px;
            height: 20px;
            background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/icon_comm_gongGeView.svg) no-repeat center;
        }
        .headViewTypeBoxText>.headViewTypeBoxIconText{
            float: right;
            width: 20px;
            height: 20px;
            background: url(${KMSS_Parameter_ContextPath}sys/modeling/base/resources/images/icon_commo_arrowDown.svg) no-repeat center;
        }
        .headViewTypeBoxListText{
            display: none;
            width: 100%;
            position: absolute;
            top: 100%;
            right:-1px;
            background-color: #fff;
            border: 1px solid #DDDDDD;
            box-shadow: 0 1px 3px 0 rgba(0,0,0,0.15);
            border-radius: 2px;
            z-index: 11;
            box-sizing: content-box;
            transform: translateY(1px);
        }
        .headViewTypeBoxListText>ul>li{
            display: -webkit-flex;
            align-items: center;
            height: 30px;
            height:30px;
            font-size: 14px;
            padding:0 6px;
            background-color: #fff;
            cursor: pointer;
        }
        .headViewTypeBoxListText>ul>li:hover{
            background-color: rgba(66,133,244,0.10);
        }
    </style>
</head>
<body class="lui_profile_listview_body">

<div class="lui_modeling">

  <div class="lui_modeling_aside">

    	<div class = "oprationHelp" onclick="goToHelp()">${lfn:message('sys-modeling-base:sys.profile.modeling.operationGuide')}</div>

        <div data-lui-type="sys/modeling/base/resources/js/appList/menuAside!MenuAside" style="display:none;"
             id="menuAside">

            <ui:source type="AjaxJson">
                { url : '/sys/modeling/base/modelingApplication.do?method=getAppCounts'}
            </ui:source>
            <div data-lui-type="lui/view/render!Template" style="display:none;">
                <script type="text/config">
 						{
							src : '/sys/modeling/base/resources/js/appList/aside.html#'
						}
                </script>
            </div>

        </div>

    </div>

    <div class="lui_modeling_main" id="defaultMain">
        <div class="lui_modeling_main_head">
            <div class="lui_profile_listview_searchWrap" style="float:left;margin:0 0 0 5px;">
                <input type="text" class="lui_profile_search_input"
                       placeholder="${lfn:message('sys-modeling-base:modeling.profile.search')}"
                       onkeyup='searchApp(event,this);'>
                <i class="lui_profile_listview_icon lui_icon_s_icon_search"  style="cursor: pointer" onclick="searchApp_icon(this)"></i>
            </div>
            <div class="head_select" style="float:left;margin:0 0 0 5px;">
                <select class="categoryList" multiple="multiple">
                </select>
            </div>
            <div class="head_select" style="float:left;margin:0 0 0 5px;">
                <select onchange="switchStatus(event)">
                    <option value="all">${lfn:message('sys-modeling-base:sys.profile.modeling.allStatus')}</option>
                    <option value="draft">${lfn:message('sys-modeling-base:status.draft')}</option>
                    <option value="fdPublish">${lfn:message('sys-modeling-base:status.published')}</option>
                    <option value="unFdPublish">${lfn:message('sys-modeling-base:status.toPublish')}</option>
                    <option value="fdValid">${lfn:message('sys-modeling-base:status.terminated')}</option>
                    <option value="disable">${lfn:message('sys-modeling-base:modelingLicense.activate.disable')}</option>
                </select>
            </div>
            <div style="float:left;margin:0 0 0 5px;">
                <div class="appliactionBoxText">
                    <i class="appliactionBoxIconText"></i>
                    <span>${lfn:message('sys-modeling-base:modelingApp.lication.form.order.desc')}</span>
                    <input type="hidden" value="desc">
                    <div class="appliactionBoxListText" style="display: none;">
                        <ul>
                            <li data-select="desc">${lfn:message('sys-modeling-base:modelingApp.lication.form.order.desc')}</li>
                            <li data-select="asc">${lfn:message('sys-modeling-base:modelingApp.lication.form.order.asc')}</li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="lui_app_License">
                <span class="app_License"></span>
                <span class="app_License_tips">
                    <span>${lfn:message('sys-modeling-base:modelingLicense.license.tips')}</span>
                </span>
            </div>
            <div id="import_activateApps" class="head_import">
                <kmss:auth requestURL="/sys/modeling/base/modelingApplication.do?method=activateApps" requestMethod="GET">
                    <div><ui:button onclick="activateApps();" text="${lfn:message('sys-modeling-base:modelingLicense.batch.activation')}"/></div>
                    <div class="model-mask-panel-sta"></div>
                </kmss:auth>
            </div>
            <div id="head_import_btn" class="head_import">
                <kmss:auth requestURL="/sys/modeling/base/modelingDatainitMain.do?method=import" requestMethod="GET">
                    <div><ui:button onclick="appImport();" text="${lfn:message('sys-modeling-base:sys.profile.modeling.importApp')}"/></div>
                    <div class="model-mask-panel-sta"></div>
                </kmss:auth>
            </div>
            <div id="head_newapp_table" class="head_import" style="display: none">
                    <div><ui:button onclick="tableNewappAdd();" text="${lfn:message('sys-modeling-base:modeling.creat.newapp')}"/></div>
            </div>
            <div id="head_viewType" class="head_import">
                <div class="headViewTypeBoxText">
                    <i class="headViewTypeIcon"></i>
                    <span>${lfn:message('sys-modeling-base:listview.card')}</span>
                    <input type="hidden" value="1">
                    <i class="headViewTypeBoxIconText"></i>
                    <div class="headViewTypeBoxListText" style="display: none;">
                        <ul>
                            <li data-select="1"><i class="headViewTypeBoxIconTextggView"></i>${lfn:message('sys-modeling-base:listview.card')}</li>
                            <li data-select="2"><i class="headViewTypeBoxIconTextListView"></i>${lfn:message('sys-modeling-base:modelingAppSpace.dataList')}</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="lui_modeling_main_content">
            <div data-lui-type="sys/modeling/base/resources/js/appList/listView!ListView" style="display:none;"
                 id="appList">
                <div data-lui-type="sys/modeling/base/resources/js/appList/listViewSource!ListViewSource"></div>
                <div data-lui-type="lui/view/render!Template" style="display:none;">
                    <script type="text/config">
                            {
                                src : '/sys/modeling/base/resources/js/appList/listViewRender.html#'
                            }
                    </script>
                </div>
            </div>
        </div>
    </div>
    <!-- 所有应用的空页面 -->
    <div class="lui_modeling_main" id="allAppsMain" style="display: none">
        <div class="app_empty">
            <div class="app_empty_img">
                <div></div>
                <p>${lfn:message('sys-modeling-base:sys.profile.modeling.welcomeMsg')}</p>
            </div>
            <div>
                <form>
                    <kmss:auth requestURL="/sys/modeling/base/modelingApplication.do?method=saveBaseInfoByAjax" requestMethod="GET">
                    <center>
                        <table class="tb_simple app_empty_panel_table" width=95%>
                            <tr>
                                <td class="td_normal_title" width=15% style="line-height:30px">
                                    ${lfn:message('sys-modeling-base:modeling.app.name')}
                                </td>
                                <td width=85% class="app_empty_panel_table_td">

                                    <input class="inputsgl" placeholder="${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.appPlaceholder')}"
                                           style="padding-left:2px;width: 96%;height:30px;line-height:30px;border: 1px solid #b4b4b4;color: #1b83d8;white-space: nowrap;"
                                           name="fdAppName"
                                           subject="${lfn:message('sys-modeling-base:modeling.app.name')}" type="text"
                                           validate="required"/><span class="txtstrong">*</span>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width=15% style="line-height:52px">
                                    ${lfn:message('sys-modeling-base:modeling.app.icon')}
                                </td>
                                <td width=85% class="app_empty_panel_table_td">
                                    <div class="appMenu_main_icon"><i class="iconfont_nav"></i></div>
                                    <span class="txtstrong">*</span>
                                    <a href="javascript:void(0);" class="select_icon_btn" onclick="selectIcon();">
                                        <!-- <i class="iconfont_nav" style="color:#999;font-size:40px;"></i> -->
                                        ${lfn:message('sys-modeling-base:modeling.form.ChangeIcon')}
                                    </a>
                                    <input name="fdIcon" type="hidden" value="iconfont_nav"/>
                                </td>
                            </tr>
                            <!--分类 -->
                            <tr>
                                <td class="td_normal_title" width=15%>
                                        ${lfn:message('sys-modeling-base:modeling.app.category')}
                                </td>
                                <td width="85%" class="model-view-panel-table-td">
                                    <div class="inputselectsgl" onclick="categoryDialog();" style="margin-left: 10px;padding-left:2px;width: 93%;height:30px;line-height:30px;border: 1px solid #b4b4b4;color: #1b83d8;white-space: nowrap;">
                                        <input name="fdCategoryId" value="" type="hidden">
                                        <div class="input">
                                            <input subject="${lfn:message('sys-modeling-base:modeling.app.category')}" name="fdCategoryName" value="" type="text" readonly="" style="border:0px;">
                                        </div>
                                        <div class="selectitem"></div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width=15%>
                                        ${lfn:message('sys-modeling-base:modeling.app.desrc')}
                                </td>
                                <td width="85%" class="model-view-panel-table-td">
                                    <textarea name="fdAppDesc" subject="${lfn:message('sys-modeling-base:modeling.app.desrc')}" style="margin-left: 10px;width:94%;height: 80px;border: 1px solid #b4b4b4;"></textarea>
                                </td>
                            </tr>
                        </table>
                    </center>
                    </kmss:auth>

                    <div class="">
                        <center>
                            <div class="lui_custom_list_box_content_col_btn" style="text-align: center;width: 100%">
                                <kmss:auth requestURL="/sys/modeling/base/modelingApplication.do?method=saveBaseInfoByAjax" requestMethod="GET">
                                    <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)"
                                       onclick="modeling_submit();">${lfn:message('sys-modeling-base:modelingApplication.createNow')}</a>
                                </kmss:auth>
                                <kmss:auth requestURL="/sys/modeling/base/modelingDatainitMain.do?method=import" requestMethod="GET">
                                    <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)"
                                       onclick="appImport();">${lfn:message('sys-modeling-base:sys.profile.modeling.importApp')}</a>
                                </kmss:auth>
                            </div>
                        </center>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!--  我维护的应用的空页面 -->
    <div class="lui_modeling_main" id="editorAppsMain" style="display: none">
        <div class="app_empty">
            <div class="app_empty_img app_empty_img_editor">
                <div></div>
                <p>${lfn:message('sys-modeling-base:modelingApplication.dontHavemaintainedAPP')}</p>
            </div>
        </div>
    </div>
    <!--  我维护的应用的空页面 -->
    <div class="lui_modeling_main" id="installedAppsMain" style="display: none">
        <div class="app_empty">
            <div class="app_empty_img app_empty_img_editor">
                <div></div>
                <p>${lfn:message('sys-modeling-base:modelingApplication.dontHaveInstalledAPP')}</p>
            </div>
        </div>
    </div>
</div>
<%--<span class="end_time_tips">${lfn:message('sys-modeling-base:modelingLicense.endTime.cannot.use.tips')}</span>--%>
<script>
    $KMSSValidation();
    var licenseInfo = {};
    getLicense();
    /**
     *分类选择
     */
    function categoryDialog() {
        Dialog_List(false, 'fdCategoryId', 'fdCategoryName', null, "modelingAppCategoryService");
    }

    window.goToHelp = function () {
        //Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/base/form/form.jsp?fdId=${appId}","_self");
        Com_OpenWindow(Com_Parameter.ContextPath + "sys/modeling/help/docs/index.jsp#/pages/page-1/start", "_blank");
    }
    function goMall(){
        var url=Com_Parameter.ContextPath+'sys/profile/index.jsp#modeling/applicationMall';
        window.top.location.href=url;
        window.top.location.reload();
    }

    function appSwitch() {
        var val = LUI("switch").element.find("input[name='fdValid']").val();
        LUI("appList").setCriteria("eq.fdValid", val);
        LUI("appList").reRender();
    }

    function switchStatus(event) {
        var ele = event.target;
        var v = $(ele).val();
        LUI("appList").clearCriteria();
        if (v === "unFdPublish") {//待发布
            LUI("appList").setCriteria("eq.fdPublish", false);
            LUI("appList").setCriteria("eq.fdValid", true);
            LUI("appList").setCriteria("ne.fdVersion.fdStatus", "dra");
        } else if (v === "fdPublish") {//已发布
            LUI("appList").setCriteria("eq.fdPublish", true);
            LUI("appList").setCriteria("eq.fdValid", true);
        } else if (v === "fdValid") {//已停用
            LUI("appList").setCriteria("eq.fdValid", false);
            LUI("appList").setCriteria("app.statu", "stopapp");
        } else if (v === "disable") {//已禁用
            LUI("appList").setCriteria("eq.fdValid", false);
            LUI("appList").setCriteria("app.statu", "disable");
        }else if(v === 'draft'){//草稿
        	 LUI("appList").setCriteria("eq.fdPublish", false);
             LUI("appList").setCriteria("eq.fdValid", true);
             LUI("appList").setCriteria("eq.fdVersion.fdStatus", "dra");
        }
        //分类是否有选择
        checkFSelectVal();
        //排序
        var orderType = $(".appliactionBoxText input").val();
        LUI("appList").setCriteria("fdOrder", orderType);
        var viewType = $(".headViewTypeBoxText input").val();
        LUI("appList").setCriteria("fdViewType", viewType);
        LUI("appList").reRender();
    }
    function searchApp_icon(dom) {
        var val = encodeURIComponent($(".lui_profile_search_input").val());
        if (val === "") {
            LUI("appList").setCriteria("like.fdAppName", val);
            checkFSelectVal();
            //排序
            var orderType = $(".appliactionBoxText input").val();
            LUI("appList").setCriteria("fdOrder", orderType);
            LUI("appList").reRender();
            $("#appList").removeClass("hideCreate");
        } else  {
            LUI("appList").setCriteria("like.fdAppName", val);
            //排序
            var orderType = $(".appliactionBoxText input").val();
            LUI("appList").setCriteria("fdOrder", orderType);
            checkFSelectVal();
            LUI("appList").reRender();
            $("#appList").addClass("hideCreate");
        }
    }
    function searchApp(event, dom) {
        var val = encodeURIComponent($(dom).val());
        if (val === "") {
            //#152840 当输入框为空的时候回车或者返回多次执行刷新页面
            if($("#appList").hasClass("hideCreate")){
                LUI("appList").setCriteria("like.fdAppName", val);
                checkFSelectVal();
                //排序
                var orderType = $(".appliactionBoxText input").val();
                LUI("appList").setCriteria("fdOrder", orderType);
                LUI("appList").reRender();
                $("#appList").removeClass("hideCreate");
            }
        } else if (event && event.keyCode == '13') {
            LUI("appList").setCriteria("like.fdAppName", val);
            checkFSelectVal();
            //排序
            var orderType = $(".appliactionBoxText input").val();
            LUI("appList").setCriteria("fdOrder", orderType);
            LUI("appList").reRender();
            $("#appList").addClass("hideCreate");
            //165958 避免用户反复狂点回车导致查询崩掉，像百度搜索一样，按回车后失焦
            $(dom).blur();
        }

    }
    //下拉框中确认按钮点击事件
    function searchAppByCate(){
        var selectVal="";
        $(".fs-option.selected").each(function(i, el) {
            var selected = $(el).attr('data-value');
            selectVal=selectVal+selected+";"
        });
        if(selectVal){
            selectVal = selectVal.substring(0, selectVal.length - 1);
        }
        LUI("appList").setCriteria("fdCategory.fdId", selectVal);
        LUI("appList").reRender();
        $('.fs-dropdown').addClass('hidden');
        //手动清空搜索框内容。
        $('.fs-search input').val("");
        $('.fs-search input').keyup();
    }
    //清空下拉复选框
    function clearAppByCate(){
        //判断是否有选择分类，如果没有分类时只隐藏选项，若有则清空所选内容重新加载
        if($(".fs-option.selected").length>0){
             $(".fs-option.selected").each(function(i, el) {
                 $(el).removeClass('selected');
             });
             $(".fs-label").html("${lfn:message('sys-modeling-main:modeling.allCategory')}");
             searchAppByCate();
        }else{
            $('.fs-dropdown').addClass('hidden');
            //手动清空搜索框内容。
            $('.fs-search input').val("");
            $('.fs-search input').keyup();
        }
    }
    function checkFSelectVal(){
        //分类是否有选择
        var selectVal="";
        $(".fs-option.selected").each(function(i, el) {
            var selected = $(el).attr('data-value');
            selectVal=selectVal+selected+";"
        });
        if(selectVal){
            selectVal = selectVal.substring(0, selectVal.length - 1);
            LUI("appList").setCriteria("fdCategory.fdId", selectVal);
            $('.fs-dropdown').addClass('hidden');
        }
    }

    function switchOrder(orderType) {
        LUI("appList").setCriteria("fdOrder", orderType);
        //分类是否有选择
        checkFSelectVal();
        LUI("appList").reRender();
    }
    function  switchViewType(viewType){
        LUI("appList").setCriteria("fdViewType", viewType);
        //分类是否有选择
        checkFSelectVal();
        LUI("appList").reRender();
    }

    function modeling_submit() {
        // Com_Submit(document.modelingApplicationForm, 'createApp');
        var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=saveBaseInfoByAjax";
        $.ajax({
            url: url,
            type: "post",
            data: $('form').serialize(),
            success: function (rtn) {
                if (rtn.status === '00') {
                    //刷新当前窗口
                    var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=appIndex&fdId=" + rtn.data.id;
                    top.open(url, "_blank");
                    LUI("appList").reRender();
                    LUI("menuAside").reRender();
                    $("#allAppsMain").hide();
                    $("#defaultMain").show();
                    //$dialog.___params["formWindow"].open(url,"_blank");
                    //$dialog.hide({type:'success'});
                } else {
                    seajs.use(["lui/jquery", "lui/dialog"], function ($, dialog) {
                        dialog.failure(rtn.errmsg);
                    });
                }
            }
        });
    }

    function getLicense() {
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=getLicenses";
            $.ajax({
                url: url,
                type: "post",
                data: $('form').serialize(),
                success: function (rtn) {
                    licenseInfo = rtn;
                    if (rtn.licenseMode === 'runner' && rtn.licenseCount != 0 ) {
                        $(".app_License").html("${lfn:message('sys-modeling-base:modelingLicense.activate.self.build')}${lfn:message('sys-modeling-base:modelingLicense.license')}"+rtn.licenseCount+
                            "${lfn:message('sys-modeling-base:modelingLicense.license.used')}"+
                            rtn.selfBuildApps+"${lfn:message('sys-modeling-base:modelingLicense.license.remain')}"+
                            rtn.remainBuild+"${lfn:message('sys-modeling-base:modelingLicense.license.num')}");
                    } else {
                        $(".lui_app_License").hide();
                    }
                }
            });
    }

    seajs.use(['lui/dialog', 'lui/topic'], function (dialog, topic) {

        topic.channel("modelingAppList").subscribe('app.lincense', function () {
            getLicense();
        });
        window.appImport = function (dialogType) {
            var url = '/sys/modeling/base/modelingDatainitMain.do?method=importPage';
            dialog.iframe(url, listOption.lang.importDialogTitle, function (data) {
                // LUI("appList").reRender();
                if(data == "noReload"){
                    return;
                }
                LUI("menuAside").reRender();
                checkImportStatus();
            }, {
                width: 540 + 5,
                height: (dialogType === 'status' ? 398 : 232)
            });
        }
        window.tableNewappAdd = function(){
            if (licenseInfo.licenseMode === 'runner' && licenseInfo.licenseCount === 0 ){
                var tip1 = "${lfn:message('sys-modeling-base:modelingLicense.license.cannot.add.tip1')}";
                var tip2 = "${lfn:message('sys-modeling-base:modelingLicense.license.cannot.add.tip2')}";
                var html = "<p style='text-align: left;font-weight:bold;'>"+tip1+"</p><p style='text-align: left;'>"+tip2+"</p>";
                var param = {"title":"${lfn:message('sys-modeling-base:modelingLicense.license.tipTitle')}","html":html}
                dialog.alert(param)
                return;
            }else {
                var url = "/sys/modeling/base/modelingApplication.do?method=add";
                dialog.iframe(url, listOption.lang.createApp, function (rt) {
                    if (rt && rt.type === "success") {
                        topic.channel("modelingAppList").publish("app.update");
                        topic.channel("modelingAppList").publish("app.lincense");
                        topic.channel("modelingAppList").publish("window.reload");
                    }
                }, {width: 540, height: 317, params: {formWindow: window}});
            }
        }
        window.activateApps = function (dialogType) {
            var url = '/sys/modeling/base/profile/dialog_activate_apps.jsp';
            dialog.iframe(url, "${lfn:message('sys-modeling-base:modelingLicense.batch.activation')}", function (flag) {
                if (flag){
                    topic.channel("modelingAppList").publish("app.update");
                    topic.channel("modelingAppList").publish("app.lincense");
                }
            }, {
                width: 540 + 5,
                height: 400
            });
        }

        window.selectIcon = function () {
            var url = "/sys/modeling/base/resources/iconfont.jsp?type=module";
            dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.app.selectIcon')}", changeIcon, {
                width: 750,
                height: 500
            })
        }


        function changeIcon(className) {
            if (className) {
                $("i.iconfont_nav").removeClass().addClass(className);
                $("input[name='fdIcon']").val(className.split(" ")[1]);
            }
        }
        topic.channel("modelingAppList").subscribe('window.reload', function () {
            //因为卡片和列表视图需求，屏蔽页面全量刷新。
            //location.reload();
        });

    });
    var listOption = {
        lang: {
            importDialogTitle: '${lfn:message("button.import")}',
            exportDialogTitle: '${lfn:message("button.export")}',
            draft: '${lfn:message('sys-modeling-base:status.draft')}',
            published: '${lfn:message('sys-modeling-base:status.published')}',
            toPublish: '${lfn:message('sys-modeling-base:status.toPublish')}',
            terminated: '${lfn:message('sys-modeling-base:status.terminated')}',
            createApp: '${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.createApp')}',
            putAway: '${lfn:message('sys-modeling-base:modelingApplication.putAway')}',
            unfold: '${lfn:message('sys-modeling-base:modelingApplication.unfold')}',
            DeleteTips: '${lfn:message('sys-modeling-base:modeling.form.DeleteTips')}',
            OperateSuccess: '${lfn:message('sys-modeling-base:modeling.baseinfo.OperateSuccess')}',
            OperateTips: '${lfn:message('sys-modeling-base:modeling.form.OperateTips')}',
            DeleteAssociatedModule: '${lfn:message('sys-modeling-base:modeling.form.DeleteAssociatedModule')}',
            export: '${lfn:message('sys-modeling-base:enums.operation.def.7')}',
            fdApplication: '${lfn:message('sys-modeling-base:table.modelingApplication')}',
            fdVersion: '${lfn:message('sys-modeling-base:modelingApplication.fdVersion')}',
            pubApp: '${lfn:message('sys-modeling-base:modelingApplication.pubApp')}',
            unpublish: '${lfn:message('sys-modeling-base:modelingApplication.unpublish')}',
            enableApp: '${lfn:message('sys-modeling-base:modelingApplication.enableApp')}',
            disableApp: '${lfn:message('sys-modeling-base:modelingApplication.disableApp')}',
            deleteApp: '${lfn:message('sys-modeling-base:modelingApplication.DeleteApp')}',
            deleteData: '${lfn:message('sys-modeling-base:modelingApplication.deleteData')}',
            pleaserWord:"${lfn:message('sys-modeling-main:modeling.please.choose')}",
            allCategory:"${lfn:message('sys-modeling-main:modeling.allCategory')}",
            total: '${lfn:message('sys-modeling-base:modelingTreeView.total')}',
            search:"${lfn:message('sys-modeling-main:sysModeling.btn.search')}",
            searchnull:"${lfn:message('sys-modeling-main:modelingCalendar.on.content')}",
            buttonOkText:"${lfn:message('sys-modeling-main:modeling.ok')}",
            buttonCancelText:"${lfn:message('sys-modeling-main:modeling.cancel')}"
        },
        temp: {
            relatedDatas: ''
        }
    }

    //引入覆盖的样式
    function _create(url) {
        var head = top.document.getElementsByTagName('head')[0],
            css = top.document.createElement('link');
        css.type = 'text/css';
        css.rel = 'stylesheet';
        css.href = url;
        head.appendChild(css);
    }
    // lang:{curLang:xxx,lang:{}}
    /********* 当前系统的语种 start **********/
    function getSysLangInfo(){
        var defaultLang = '<%=LangUtil.Default_Lang%>';
        var _currentLang = '<%=UserUtil.getKMSSUser().getLocale().toString()%>'.replace("_","-");
        if(_currentLang == 'null'){
            _currentLang = defaultLang;
        }
        return _currentLang;
    }
    _create('${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css');
    $(document).ready(function() {
        //获取所有分类构建下拉选项option
        var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=getCategory";
        var optionStr="<option value='noCate'>${lfn:message('sys-modeling-base:modelingApplication.category.none')}</option>";
        $.ajax({
            url: url,
            type: "post",
            async:false,
            success: function (data) {
                $.each(data, function (index, val) {
                    optionStr += "<option value='"+val['id']+"'>"+val['name']+"</option>";
                });
            }
        });
        $('.categoryList').append(optionStr);
        $('.categoryList').fappSelect();
        //排序下拉选项
        $('.appliactionBoxText').click(function(e){
            e?e.stopPropagation():event.cancelBubble = true;
            if($(this).find('.appliactionBoxListText').css('display') == 'none'){
                $('.appliactionBoxListText').hide();
                $(this).find('.appliactionBoxListText').show();
            }else{
                $(this).find('.appliactionBoxListText').hide();
            }
        });
        $('.appliactionBoxListText>ul>li').click(function(e){
            e?e.stopPropagation():event.cancelBubble = true;
            $(this).parents('.appliactionBoxText').children('span').text($(this).text());
            var dataSelect = $(this).attr("data-select");
            $(this).parents('.appliactionBoxText').children('input').val(dataSelect);
            $(this).parents('.appliactionBoxText').find('.appliactionBoxListText').hide();
            var url = "";
            if (dataSelect == "desc"){
                url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_sortInvertedOrder_desc.png";
            }else{
                url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_sortInvertedOrder_asc.png";
            }
            var backgroundValue="url('"+url+"') no-repeat center"
            $('.appliactionBoxText .appliactionBoxIconText').css("background",backgroundValue);
            switchOrder(dataSelect);
        });
        // 下拉列表点击外部或者按下ESC后列表隐藏
        $(document).click(function(){
            $('.appliactionBoxListText').hide();
            $(this).find('.watermark_configuration_list_pop').hide();
            $(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
        }).keyup(function(e){
            var key =  e.which || e.keyCode;;
            if(key == 27){
                $('.appliactionBoxListText').hide();
                $(this).find('.watermark_configuration_list_pop').hide();
            }
        });
        //视图类型下拉
        $('.headViewTypeBoxText').click(function(e){
            e?e.stopPropagation():event.cancelBubble = true;
            if($(this).find('.headViewTypeBoxListText').css('display') == 'none'){
                $('.headViewTypeBoxListText').hide();
                $(this).find('.headViewTypeBoxListText').show();
            }else{
                $(this).find('.headViewTypeBoxListText').hide();
            }
        });
        $('.headViewTypeBoxListText>ul>li').click(function(e){
            e?e.stopPropagation():event.cancelBubble = true;
            $(this).parents('.headViewTypeBoxText').children('span').text($(this).text());
            var dataSelect = $(this).attr("data-select");
            $(this).parents('.headViewTypeBoxText').children('input').val(dataSelect);
            //改变不同视图的样式
            var url = "";
            if (dataSelect == "1"){
                url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_comm_gongGeView.svg";
            }else{
                url = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/icon_comm_menuList.svg";
            }
            var backgroundValue="url('"+url+"') no-repeat center"
            $('.headViewTypeBoxText .headViewTypeIcon').css("background",backgroundValue);
            $(this).parents('.headViewTypeBoxText').find('.headViewTypeBoxListText').hide();
            switchViewType(dataSelect);
        });
        // 下拉列表点击外部或者按下ESC后列表隐藏
        $(document).click(function(){
            $('.headViewTypeBoxListText').hide();
            $(this).find('.watermark_configuration_list_pop').hide();
            $(".watermark_configuration_alpha_bar,.watermark_configuration_scale_box,.watermark_configuration_alpha_bar").addClass("hide");
        }).keyup(function(e){
            var key =  e.which || e.keyCode;;
            if(key == 27){
                $('.headViewTypeBoxListText').hide();
                $(this).find('.watermark_configuration_list_pop').hide();
            }
        });
    });
    Com_IncludeFile("importStatus.js", Com_Parameter.ContextPath + 'sys/modeling/main/resources/js/datainit/', 'js', true);
    Com_IncludeFile("template.js", Com_Parameter.ContextPath + 'sys/modeling/base/profile/appfselect_jquery/js/', 'js', true);
</script>
</body>
</html>
