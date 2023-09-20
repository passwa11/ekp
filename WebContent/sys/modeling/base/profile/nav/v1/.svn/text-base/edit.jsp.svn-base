<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/viewList.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/profile/nav/v1/css/businessNav.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css" rel="stylesheet">
<template:include ref="config.edit" sidebar="no">
    <template:replace name="content">
        <script>
            Com_IncludeFile("doclist.js");
            Com_IncludeFile("Sortable.min.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
            Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
                + 'sys/modeling/base/resources/js/', 'js', true);
        </script>
        <html:form action="/sys/modeling/base/modelingAppNav.do" style="background-color:#fff;">
            <div id="v1Nav" class="model-businessNav">
                    <%-- 头部--%>
                <div class="model-businessNav-top">
                        <%--左侧 返回和设置--%>
                    <div class="businessNav-top-title">
                        <span class="return" onclick="returnListPage()" title="${modelingAppNavForm.docSubject}">${modelingAppNavForm.docSubject}</span>
                    </div>
                    <i class="base_msg" onclick="editNav()"></i>
                        <%--右侧 保存和返回首页 --%>
                    <div class="businessNav-top-btn">
                        <div class="save" onclick="dosubmit('update')">${lfn:message("sys-modeling-base:modeling.save")}</div>
                        <div class="go_homePage" onclick="redirectIndex()" >${lfn:message("sys-modeling-base:modeling.visit.homepage")}</div>
                    </div>
                </div>
                    <%--主题功能 组件化主体--%>
                <div class="model-businessNav-main">
                        <%-- 数据选取--%>
                    <div class="businessNav-main-source">
                        <div class="main-source-tabs">
                            <p class="tab_form"><span>${lfn:message("sys-modeling-base:table.modelingAppModel")}</span></p>
                            <p class="tab_chart"><span>${lfn:message("sys-modeling-base:modeling.chart")}</span></p>
                            <p class="tab_space"><span>${lfn:message("sys-modeling-base:table.modelingAppSpace")}</span></p>
                        </div>
                        <div class="main-source-container">
                            <div class="main-source-title">
                                <div class="title-name">
                                    <span class="name-select-list-selected">${lfn:message("sys-modeling-base:modeling.app.name")}</span>
                                    <div class="name-select">
                                            <%--  应用搜索--%>
                                        <div class="name-select-search">
                                            <i></i>
                                            <input type="text" class="name-select-search-val" placeholder="${lfn:message("sys-modeling-base:button.searchList")}">
                                        </div>
                                            <%--应用下拉框--%>
                                        <ul class="name-select-list"></ul>
                                    </div>
                                </div>
                                    <%-- 表单搜索--%>
                                <div class="title-search" style="display: none">
                                    <input type="text" class="title-search-val" placeholder="${lfn:message("sys-modeling-base:modeling.enter.key.words")}">
                                    <span class="title-search-btn"></span>
                                </div>
                            </div>
                                <%--数据二级列表--%>
                            <div class="main-source-content">
                                <div class="source-content-supper"></div>
                                <div class="source-content-extend"></div>
                            </div>
                        </div>
                    </div>
                    <div class="businessNav-main-opera">
                        <div class="main-opera-container">
                            <div class="main-opera-title">
                                <!-- expend 表示所有业务导航内容处于展开状态 -->
                                <div class="opera-title-name expend">${lfn:message("sys-modeling-base:table.modelingAppNav")}</div>
                                <div class="opera-title-showSetting">
                                    <p>${lfn:message("sys-modeling-base:modeling.display.set")}</p>
                                    <div class="showSetting-select">
                                        <div class="showSetting-select-search">
                                            <i></i>
                                            <input type="text" class="showSetting-select-search-val" placeholder="${lfn:message("sys-modeling-base:button.searchList")}">
                                        </div>
                                        <p class="showSetting-select-title">${lfn:message("sys-modeling-base:modeling.client.default")}</p>
                                        <ul class="showSetting-select-list">
                                        </ul>
                                        <div class="showSetting-select-all">
                                            <i class="selectedAll"></i>
                                            <span>${lfn:message("sys-modeling-base:modeling.common.selectAll")}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="main-opera-content">
                                <div class="opera-content-main" id="navRightContent">
                                    <div class="addFirLevel"><i></i>${lfn:message("sys-modeling-base:button.add")}</div>
                                </div>
                                <!-- 新建业务导航节点页 starts -->
                                <div class="whitePage">
                                    <img src="resources/images/nav/blank-space@2x.png" alt="">
                                    <p>${lfn:message("sys-modeling-base:modeling.click.new.business.node")}</p>
                                    <div class="newNode">${lfn:message("sys-modeling-base:modeling.button.new")} </div>
                                </div>
                                <!-- 新建业务导航节点页 ends -->
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 已添加到右侧导航标注弹窗 -->

                <div class="hidden_param">
                    <html:hidden property="fdApplicationId"/>
                    <html:hidden property="fdId"/>
                    <html:hidden property="fdNavContent"/>
                    <html:hidden property="docSubject"/>
                    <html:hidden property="fdOrder"/>
                    <html:hidden property="fdNavVersion"/>
                    <html:hidden property="authReaderIds"/>
                    <html:hidden property="authReaderNames"/>
                </div>
            </div>
        </html:form>
        <script type="text/javascript">
            var modeling_validation = $KMSSValidation();
            var applicationName = '${applicationName}';
            var fdAppId = '${param.fdAppId }';
            var fdNavId = '${modelingAppNavForm.fdId}';
            var __edit = "${modelingAppNavForm.method_GET}";
            var lang = {
                table: '${lfn:message("sys-modeling-base:table.modelingAppModel")}',
                chart: '${lfn:message("sys-modeling-base:modeling.chart")}',
                noSelect: '${lfn:message("sys-modeling-base:table.modelingAppModel")}',
                customData: '${lfn:message("sys-modeling-base:modeling.custom.data")}',
                dbEchartsChartSet: '${lfn:message("sys-modeling-base:table.dbEchartsChartSet")}',
                dbEchartsTable: '${lfn:message("sys-modeling-base:table.dbEchartsTable")}',
                dbEchartsChart: '${lfn:message("sys-modeling-base:table.dbEchartsChart")}',
                modelingAppListview: '${lfn:message("sys-modeling-base:table.modelingAppListview")}',
                modelingCollectionView: '${lfn:message("sys-modeling-base:table.modelingCollectionView")}',
                modelingGantt: '${lfn:message("sys-modeling-base:table.modelingGantt")}',
                modelingResourcePanel: '${lfn:message("sys-modeling-base:table.modelingResourcePanel")}',
                modelingMindMap: '${lfn:message("sys-modeling-base:table.modelingMindMap")}',
                modelingCalendar: '${lfn:message("sys-modeling-base:table.modelingCalendar")}',
                modelingTreeView: '${lfn:message("sys-modeling-base:table.modelingTreeView")}',
                addAll: '${lfn:message("sys-modeling-base:modeling.add.all")}',
                buttonAdd: '${lfn:message("sys-modeling-base:modelingTransport.button.add")}',
                addRightNav: '${lfn:message("sys-modeling-base:modeling.added.right.navigation")}',
                selectFirstNav: '${lfn:message("sys-modeling-base:modeling.select.first.nav")}',
                enter: '${lfn:message("sys-modeling-base:modeling.please.enter")}',
                maxCharacters: '${lfn:message("sys-modeling-base:modeling.max.8.characters")}',
                fillNavTtile: '${lfn:message("sys-modeling-base:modeling.fill.nav.title")}',
                contentPath: '${lfn:message("sys-modeling-base:modeling.content.path")}',
                exlink: '${lfn:message("sys-modeling-base:modeling.add.exlink")}',
                fillTtile: '${lfn:message("sys-modeling-base:modeling.fill.title")}',
                pcTitle: '${lfn:message("sys-modeling-base:table.modelingAppSpace")}',
                noPcTitle :'${lfn:message("sys-modeling-base:modelingAppSpace.no.pc")}'
            }
            seajs.use(["sys/modeling/base/profile/nav/v1/nav", "lui/dialog", "lui/jquery"]
                , function (nav, dialog, $) {
                    //-------------------- 组件初始化
                    function init() {
                        window.navInst = new nav.Nav({
                            formParam: {
                                "fdAppId": fdAppId,
                                "fdNavId": fdNavId
                            },
                            navContent: $("[name=fdNavContent]").val(),
                            container: $("#v1Nav")
                        });
                    }

                    init();
                    //----  ---------------- 系统方法
                    window.dosubmit = function (type) {
                        if(!navInst.validate()){
                            console.error("${lfn:message("sys-modeling-base:modeling.check.failed")}");
                            return;
                        }
                        var navContent=navInst.getKeyData();
                        $("[name=fdNavContent]").val(JSON.stringify(navContent))
                        Com_Submit(document.modelingAppNavForm, type);
                        var tabTitle = window.parent.document.getElementById("space-title");
                        $(tabTitle).css("display","block");
                    };
                    window.returnListPage = function () {
                        var url = Com_Parameter.ContextPath + "sys/modeling/base/profile/nav/index_body.jsp?fdAppId=" + fdAppId;
                        var iframe = window.parent.document.getElementById("trigger_iframe");
                        var tabTitle = window.parent.document.getElementById("space-title");
                        $(tabTitle).css("display","block");
                        $(iframe).attr("src", url);
                    }
                    window.editNav = function () {
                        var url = "/sys/modeling/base/modelingAppNav.do?method=edit&type=createNav&fdAppId=" + fdAppId + "&fdId=" + fdNavId;
                        dialog.iframe(url, "${lfn:message("sys-modeling-base:modeling.app.baseinfo")}", function (rt) {
                            if (rt && rt.type && rt.type == "success") {//成功时把数据添加到页面，总体进行保存
                                var data = rt.data;
                                $("[name='docSubject']").val(data.docSubject || "");
                                $("[name='fdOrder']").val(data.fdOrder || "");
                                $("[name='authReaderIds']").val(data.authReaderIds || "");
                                $("[name='authReaderNames']").val(data.authReaderNames || "");
                                $(".return").text(data.docSubject)
                            }
                        }, {width: 550, height:400});
                    }
                    window.redirectIndex = function () {
                        var host = location.host;
                        var fdUrl = Com_Parameter.ContextPath
                            + "sys/modeling/main/index.jsp?fdAppId=" + fdAppId + "&fdNavId=" + fdNavId ;
                        window.open(fdUrl,"_blank");
                    }

                });


        </script>

    </template:replace>
</template:include>

