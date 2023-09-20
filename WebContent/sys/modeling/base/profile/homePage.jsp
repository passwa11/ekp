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
          href="${ LUI_ContextPath }/sys/modeling/base/profile/homePage/css/workbench.css?s_cache=${LUI_Cache}"/>
</head>
<body class="lui_profile_listview_body">

<div class="workbenchContainer">
    <div class="workbenchBanner">
        <div class="workbenchBannerContentTitle">
            ${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.sixTemp')}
        </div>
        <div class="workbenchBannerContentButton">
            <a href="http://mall.landray.com.cn/core01/build/index.html#/ProcessManagement/model/sceneExperience" target="_blank">
                ${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.experience')}
            </a>
        </div>
        <ul class="workbenchBannerList">
            <div class="workbenchBannerListDiv"></div>
            <li>
                <span class="workbenchBannerListOrder">
                    1
                </span>
                <p class="workbenchBannerListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.demandCom')}</p>
            </li>
            <li>
                <span class="workbenchBannerListOrder">
                    2
                </span>
                <p class="workbenchBannerListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.creatBaseData')}</p>
            </li>
            <li>
                <span class="workbenchBannerListOrder">
                    3
                </span>
                <p class="workbenchBannerListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.businessConfig')}</p>
            </li>
            <li>
                <span class="workbenchBannerListOrder">
                    4
                </span>
                <p class="workbenchBannerListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.chartSet')}</p>
            </li>
            <li>
                <span class="workbenchBannerListOrder">
                    5
                </span>
                <p class="workbenchBannerListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.homePageConfig')}</p>
            </li>
            <li>
                <span class="workbenchBannerListOrder">
                    6
                </span>
                <p class="workbenchBannerListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.appRelease')}</p>
            </li>
        </ul>
        <div class="workbenchBannerContent">
        </div>
    </div>
    <div class="workbenchEntrance clearfix">
        <div class="workbenchShortcut">
            <div class="workbenchEntranceTitle">
                ${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.shortcut')}
            </div>
            <ul class="workbenchShortcutList">
                <li>
                    <a href="javascript:void(0)" onclick="createApp();">
                        <img src="homePage/images/shorcut_04.png">
                        <div class="workbenchShortcutListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.app.manager')}</div>
                        <div class="workbenchShortcutListInfo">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.app.manager.info')}</div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="openApplicationMall();">
                        <img src="homePage/images/shorcut_02.png">
                        <div class="workbenchShortcutListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.app.store')}</div>
                        <div class="workbenchShortcutListInfo">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.app.store.info')}</div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="openMonitor();">
                        <img src="homePage/images/shorcut_03.png">
                        <div class="workbenchShortcutListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.app.monitor')}</div>
                        <div class="workbenchShortcutListInfo">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.app.monitor.info')}</div>
                    </a>
                </li>
                <li>
                    <a href="javascript:void(0)" onclick="openMaintenance();">
                        <img src="homePage/images/shorcut_01.png">
                        <div class="workbenchShortcutListTitle">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.maintenance.admin')}</div>
                        <div class="workbenchShortcutListInfo">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.maintenance.admin.info')}</div>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="workbenchBusinessRelation">
        <div class="workbenchEntranceTitle">
            ${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.relationship')}
        </div>
        <div class="businessRelationContent">
            <div class = "searchContent">
                <div id = "searchTypeButton" onclick="reBackApp();">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back')}</div>
                <div class="workbenchSearch">
                    <input type="text" id="searchInput" class="appInput" autocomplete="off" onkeyup="searchMenu(this.value, event);">
                    <span onclick="searchApp();"></span>
                    <div class="location_input_selection" style="display: none;max-height:200px;overflow-y:auto;">
                        <ul id="selection_menus">
                        </ul>
                    </div>
                </div>
            </div>
            <div class="businessRelationContentBg" >
                <div id="app_relation_echart"></div>
            </div>
            <div class="modelTitle"></div>
            <div class="businessRelationContentLegend">
                <ul>
                    <li class="businessRelationContentLegendItem">
                        <div>
                            <img src="homePage/images/legend_01.png">
                        </div>
                        <span class="appLegend">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.appChecked')}</span>
                        <span class="modelLegend">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.modelChecked')}</span>
                    </li>
                    <li class="businessRelationContentLegendItem">
                        <div>
                            <img src="homePage/images/legend_02.png">
                        </div>
                        <span class="appLegend">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.appRelated')}</span>
                        <span class="modelLegend">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.modelRelated')}</span>
                    </li>
                    <li class="businessRelationContentLegendItem">
                        <div>
                            <img src="homePage/images/legend_03.png">
                        </div>
                        <span class="appLegend">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.appOther')}</span>
                        <span class="modelLegend">${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.modelOther')}</span>
                    </li>
                    <li class="businessRelationContentLegendItem">
                        <div class="relationIcon">
                            <img src="homePage/images/legend_04.png">
                        </div>
                        <span>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.relateTwoWay')}</span>
                    </li>
                    <li class="businessRelationContentLegendItem">
                        <div class="relationIcon">
                            <img src="homePage/images/legend_05.png">
                        </div>
                        <span>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.relateOneWay')}</span>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="${LUI_ContextPath}/sys/modeling/base/profile/homePage/js/search.js?s_cache=${LUI_Cache}"></script>
<script>

    Com_IncludeFile("echarts4.2.1.js", "${LUI_ContextPath}/sys/ui/js/chart/echarts/", null, true);

    var menuDatas = [];
    var searchNodataMsg = "${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.searchNodataMsg')}";
    var optionsAppData = [];
    var optionsModelData =[];
    var chart1 = null;
    var searchType = "1";
    var globCategory = [{name:'other'},{name:'checked'},{name:'related'}];
    var globAppLinks ={};
    var globModelLinks ={};
    var appLinks = [];
    var licenseInfo = {};
    function searchApp(id){
        var value = $('#searchInput').val();
        if(id){
            value = id;
        }
        __searchApp(value);
    }
    function __searchApp(value) {
        if(!value){
            return;
        }
        var optionsData = [];
        var optionsLinks = {};
        if(searchType == "1"){
            optionsData = optionsAppData;
            optionsLinks = globAppLinks;
        }else if(searchType == "2"){
            optionsData = optionsModelData;
            optionsLinks = globModelLinks;
        }
        var dataIndex = 0;
        var flag = false;
        for(var i =0;i<optionsData.length;i++){
            var item = optionsData[i];
            if(value == item.id || value == item.name){
                dataIndex = i;
                if(item.category != globCategory[1].name){
                    flag = true;
                }
                break;
            }
        }
        if(chart1 !=null && flag){
            var series = chart1.getOption().series;
            var source = (series[0].data)[dataIndex].id;
            var copyData = [];
            for(var i = 0;i<series[0].data.length;i++){
                var item = series[0].data[i];
                item.category = globCategory[0].name;
                var target = item.id;
                if(dataIndex == i){
                    item.category = globCategory[1].name;
                }else if((optionsLinks[source] && optionsLinks[source].indexOf(target) > -1) || (optionsLinks[target] && optionsLinks[target].indexOf(source) > -1)){
                    item.category = globCategory[2].name;
                }
                copyData.push(item);
            }
            series[0].data = copyData;
            chart1.setOption({series:series});
            if(searchType == "1"){
                optionsAppData = series[0].data;
            }else if(searchType == "2"){
                optionsModelData = series[0].data;
            }

        }
    }
    function reBackApp(){
        if(chart1 != null && searchType == "2"){
            var series = chart1.getOption().series;
            series[0].data = optionsAppData;
            series[0].links = appLinks;
            chart1.setOption({
                series:series,
                title: {show: false, text: ''}
            });
            searchType = "1";
            menuDatas = optionsAppData;
            $("#selection_menus").empty();
            initHtml();
        }
    }
    function initHtml(){
        if(searchType == "1"){
            $("#searchTypeButton").hide();
            $("#searchInput").attr("placeholder","${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.appPlaceholder')}");
            $(".modelTitle").hide();
            $(".appLegend").show();
            $(".modelLegend").hide();
            if(!$("#searchInput").hasClass("appInput")){
                $("#searchInput").addClass("appInput");
            }
        }else if(searchType == "2"){
            $("#searchTypeButton").show();
            $(".modelTitle").show();
            $("#searchInput").attr("placeholder","${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.modelPlaceholder')}");
            $(".appLegend").hide();
            $(".modelLegend").show();
            if($("#searchInput").hasClass("appInput")){
                $("#searchInput").removeClass("appInput");
            }
        }
        $("#searchInput").val('');
    }
    function getLicense() {
        var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=getLicenses";
        $.ajax({
            url: url,
            type: "post",
            async:false,
            success: function (rtn) {
                licenseInfo = rtn;
                console.log("licenseInfo1",licenseInfo);
            }
        });
    }

    seajs.use(['lui/dialog', 'lui/topic','lui/jquery'], function (dialog, topic,$) {
        //计算显示线宽度
        function calcWorkbenchBannerList(){
            var firstLiWidth = $(".workbenchBannerList>li:nth-child(2)").width();
            var lastLiWidth = $(".workbenchBannerList>li:last-child").width();
            var divWidth = $(".workbenchBannerListDiv").width();
            divWidth = divWidth - firstLiWidth/2 - lastLiWidth/2;
            $(".workbenchBannerListDiv").width(divWidth).css("left",firstLiWidth/2);
            $(".workbenchBannerList").css("visibility","visible");
            $(".workbenchBannerListDiv").css("visibility","visible");
        }
        calcWorkbenchBannerList();
        window.createApp= function(){
            getLicense();
            if (licenseInfo.licenseMode === 'runner' && licenseInfo.licenseCount === 0 ){
                var tip1 = "${lfn:message('sys-modeling-base:modelingLicense.license.cannot.add.tip1')}";
                var tip2 = "${lfn:message('sys-modeling-base:modelingLicense.license.cannot.add.tip2')}";
                var html = "<p style='text-align: left;font-weight:bold;'>"+tip1+"</p><p style='text-align: left;'>"+tip2+"</p>";
                var param = {"title":"${lfn:message('sys-modeling-base:modelingLicense.license.tipTitle')}","html":html}
                dialog.alert(param)
                return;
            }else {
                var url = "/sys/modeling/base/modelingApplication.do?method=add";
                dialog.iframe(url, "${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.createApp')}", function (data){
                    if(data && data.type == 'success'){
                        initStaticsInfo();
                        initRelations();
                    }
                }, {width: 540, height: 320, params: {formWindow: window}});
            }
        }
        function initStaticsInfo(){
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=getAppCounts";
            $.ajax({
                url: url,
                type: "post",
                data: {},
                success: function (rtn) {
                    if (rtn) {
                        //console.log(rtn);
                        var itemObj = $(".workbenchCountList");
                        itemObj.find(".publish").html(formateNum(rtn['publishApps'] || 0));
                        itemObj.find(".draft").html(formateNum(rtn['draftApps'] || 0));
                        itemObj.find(".stop").html(formateNum(rtn['stopApps'] || 0));
                        itemObj.find(".total").html(formateNum(rtn['allApps'] || 0));
                    } else {

                    }
                }
            });
        }
        var echartsOptions = {};
        function initRelations(){
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=getAppRelationByAjax";
            $.ajax({
                url: url,
                type: "post",
                data: {},
                success: function (rtn) {
                    if (rtn) {
                        //console.log(rtn);
                        optionsAppData = rtn.apps;
                        menuDatas = rtn.apps;
                        globAppLinks = {};
                        if(rtn.links){
                            appLinks = rtn.links;
                            for(var i = 0;i<rtn.links.length;i++){
                                var item = rtn.links[i];
                                var source = item.source;
                                var target = item.target;
                                if(globAppLinks[source]){
                                    globAppLinks[source] += ";" + target;
                                }else{
                                    globAppLinks[source] = target;
                                }
                            }
                        }
                        echartsOptions = {
                            data: rtn.apps,
                            links: rtn.links,
                            categories: globCategory
                        };
                        _drawEcharts(echartsOptions);
                    } else {

                    }
                }
            });
        }
        $(document).ready(function (){
            initStaticsInfo();
            initRelations();
            initHtml();
        });
        function formateNum(val){
            return val >= 10 ? val : ('0' + val);
        }
        var showUpdate = 0;
        function _drawEcharts(data) {
            var options = {
                //限制图表缩放范围
                scaleLimit:{
                min:1,
                max:3,
                },
                //title: { text: "", left: 10 },
                tooltip: {
                    formatter: function(x) {
                        var rtn =[];
                        if(x.data.name.length > 16){
                            var name = x.data.name;
                            var k = name.length/16;
                            for(var i = 0;i < k;i++){
                                var start = i*16;
                                var end = (i+1)*16 > name.length ? name.length : (i+1)*16;
                                var temp = name.slice(start,end);
                                rtn.push(temp);
                            }
                        }
                        return rtn.join('<br/>');
                    }
                },
                color: ['#5DDBA5','#4285F4','#AABFFF'],
                toolbox: {
                    show: false,
                    feature: {
                        saveAsImage: {
                            show: true,
                            excludeComponents: ['toolbox'],
                            pixelRatio: 2
                        }
                    },
                    right: 60
                },
                series: [{
                    type: 'graph',
                    layout: 'force',
                    roam: true,
                    focusNodeAdjacency: false,
                    legendHoverLink:false,
                    draggable: true,
                    force: {
                        repulsion: [200, 220],
                        edgeLength: [120, 200]
                    },
                    lineStyle: {
                        normal: {
                            cap:'round',
                            width: 1,
                            color: 'target'
                        },
                        emphasis:{
                            cap:'round',
                            width: 1,
                            color: '#4285F4'
                        }
                    },
                    edgeLabel: {
                        normal: {
                            show: true,
                            formatter: function(x) {
                                var rtn =[];
                                if(x.data.name.length > 6){
                                    var name = x.data.name;
                                    var k = name.length/6 + 1;
                                    k = k > 3 ? 3 : k;
                                    for(var i = 0;i <= k;i++){
                                        var start = i*6;
                                        var end = (i+1)*6 > name.length ? name.length : (i+1)*6;
                                        if(i == 3){
                                            end = end - start > 2 ? end - 2 : end;
                                        }
                                        var temp = name.slice(start,end);
                                        if(i == 3){
                                            temp = temp + '...';
                                        }
                                        rtn.push(temp);
                                    }
                                }else{
                                    rtn.push(x.data.name);
                                }
                                return rtn.join('\n');
                            }
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: function(x) {
                                var rtn =[];
                                var richType = 'a';
                                if(x.data.category == globCategory[1].name){
                                    richType = 'b';
                                }
                                if(x.data.name.length > 6){
                                    var name = x.data.name;
                                    var k = name.length/6;
                                    k = k > 3 ? 3 : k;
                                    for(var i = 0;i <= k;i++){
                                        var start = i*6;
                                        var end = (i+1)*6 > name.length ? name.length : (i+1)*6;
                                        if(i == 3){
                                            end = end - start > 2 ? end -2 : end;
                                        }
                                        var temp = name.slice(start,end);
                                        if(i == 3){
                                            temp = temp + '...';
                                        }
                                        rtn.push('{' + richType + '|' + temp + '}');
                                    }
                                }else{
                                    rtn.push('{' + richType + '|' + x.data.name + '}');
                                }
                                return rtn.join('\n');
                            },
                            color: '#FFFFFF',
                            fontSize: 12,
                            rich: {
                                a: {
                                    color: '#FFFFFF',
                                    lineHeight: 17,
                                    fontSize: 12,
                                    fontWeight:500,
                                },
                                b:{
                                    color: '#FFFFFF',
                                    lineHeight: 19,
                                    fontSize: 14,
                                    fontWeight:500,
                                    padding:5,
                                }
                            }

                        }
                    },
                    symbolSize: 80,
                    data: data.data,
                    links: data.links,
                    categories: data.categories
                }]
            }
            chart1 = echarts.init(document.getElementById('app_relation_echart'));
            chart1.setOption(options);
            chart1.on('click', function(param) {
                var eventType = "";
                if(param) {
                    eventType = param.type;
                }
                if(eventType == "click" && param.dataType=="node"){
                    var now = new Date().getTime();
                    if (now - showUpdate > 500) {
                        showUpdate = now;
                        var id = param.data.id;
                        __searchApp(id);
                    }
                }

            });
            chart1.on('dblclick', function(param) {
                if(param && param.dataType == 'node'){
                    changeChart(param.name,param.data.id);
                }
            });
        }
        function changeChart(name,id){
            if(searchType == "1" && chart1 != null){
                var series = chart1.getOption().series;
                var subtext = '';
                var domNum = 100;
                var k = name.length/domNum;
                k = k > 3 ? 3 : k;
                for(var i = 0;i <= k;i++){
                    var start = i*domNum;
                    var end = (i+1)*domNum > name.length ? name.length : (i+1)*domNum;
                    var temp = name.slice(start,end);
                    subtext = subtext + temp + '\n';
                }
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=getModelRelationByAjax";
                $.ajax({
                    url: url,
                    type: "post",
                    dataType:'json',
                    data: {
                        appId: id
                    },
                    success: function (rtn) {
                        if (rtn) {
                            optionsModelData = rtn.apps;
                            menuDatas = rtn.apps;
                            globModelLinks = {};
                            if(rtn.links){
                                for(var i = 0;i<rtn.links.length;i++){
                                    var item = rtn.links[i];
                                    var source = item.source;
                                    var target = item.target;
                                    if(globModelLinks[source]){
                                        globModelLinks[source] += ";" + target;
                                    }else{
                                        globModelLinks[source] = target;
                                    }
                                }
                            }
                            series[0].data = rtn.apps;
                            series[0].links = rtn.links;
                            chart1.setOption({
                                series:series
                            });
                            searchType = "2";
                            $(".modelTitle").html(subtext);
                            $(".modelTitle").attr('title',subtext);
                            $("#selection_menus").empty();
                            initHtml();
                        } else {

                        }
                    }
                });
            }
        }
        window.addEventListener("resize", function() {
            chart1.resize();
        });
        window.openApplicationMall = function(){
            var url = Com_Parameter.ContextPath + "sys/profile/index.jsp#modeling/applicationMall";
            top.open(url,'_blank');
        }
        window.openMonitor = function(){
            var url = Com_Parameter.ContextPath + "sys/profile/index.jsp#modeling/monitor";
            top.open(url,'_blank');
        }
        window.openMaintenance = function(){
            var url = Com_Parameter.ContextPath + "sys/profile/index.jsp#modeling/maintenance";
            top.open(url,'_blank');
        }
    });
</script>
</html>
