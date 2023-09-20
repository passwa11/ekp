<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%@ taglib uri="/WEB-INF/KmssConfig/sys/modeling/modeling.tld" prefix="modeling"%>
<template:include ref="default.simple">
    <%-- 右侧内容区域 --%>
    <template:replace name="body">
        <style id="show_operation_line">
            .lui_list_operation_line{
                display: none;
            }
        </style>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/assembly/css/gantt/index.css?s_cache=${LUI_Cache}">
        <!-- 筛选器 -->
        <%@ include file="/sys/modeling/base/views/business/show/criteria.jsp" %>
        <!-- 操作栏 -->
        <div class="model-gantt-tab">
            <div class="model-gantt-tab-left" >
                <!-- 排序 -->
                <c:if test="${not empty viewOrderInfo.columns}">
                <div class="lui_list_operation_sort_btn" >
                    <%@ include file="/sys/modeling/base/listview/ui/order.jsp"  %>
                </div>
                </c:if>
                <!-- 分页 -->
                <div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top" >
                    </list:paging>
                </div>

            </div>
            <div class="model-gantt-tab-right">
                <div class="model-gantt-tab-come-back" onclick="comeBackToday()">
                    <div></div>
                    <a>&nbsp;${ lfn:message('sys-modeling-main:modelingGantt.back.to.today') }</a>
                </div>
                <ui:toolbar id="toolbar" style="float:right; margin-right:8px" count="3">
                    <%-- 业务操作按钮 --%>
                    <%@ include file="/sys/modeling/base/view/ui/ganttviewopers.jsp"%>
                </ui:toolbar>
                <div class="model-gantt-tab-change">
                    <ul class="model-gantt-tab-change-select">
                        <li>${ lfn:message('sys-modeling-main:modelingRes.day') }</li>
                        <li>${ lfn:message('sys-modeling-main:modelingRes.month') }</li>
                        <li>${ lfn:message('sys-modeling-main:modelingCalendar.season') }</li>
                        <li>${ lfn:message('sys-modeling-main:modelingCalendar.year') }</li>
                    </ul>
                </div>
                <div class="model-gantt-tab-color" onclick="colorOnclick(this)"></div>
            </div>
        </div>
        <!-- 内容列表 -->
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                { url : '/sys/modeling/main/business.do?method=indexData&fdAppModelId=${param.modelId}&modelId=${param.modelId}&businessId=${param.businessId}&type=gantt&incFdId=${JsParam.incFdId }&treeViewId=${JsParam.treeViewId }&targetModelId=${JsParam.targetModelId }'}
            </ui:source>
            <list:colTable isDefault="true" layout="sys.ui.listview.columntable">
                <%--<list:col-checkbox></list:col-checkbox>
                <list:col-serial></list:col-serial>
                <list:col-auto props="${fdDisplay }"></list:col-auto>--%>
            </list:colTable>
        </list:listview>
        <div data-lui-type="sys/modeling/base/views/business/show/gantt!Gantt" style="display:none;"
             id="ganttContent">
            <ui:source type="AjaxJson">
                { url : '/sys/modeling/main/business.do?method=indexData&modelId=${param.modelId}&businessId=${param.businessId}&type=gantt&incFdId=${JsParam.incFdId }&treeViewId=${JsParam.treeViewId }&targetModelId=${JsParam.targetModelId }&hide=1'}
            </ui:source>
            <div data-lui-type="lui/view/render!Template">
                <script type="text/config">
                {src : '/sys/modeling/base/views/business/show/ganttRender.html#'}

                </script>
            </div>
            <ui:event event="load" args="evt">
                seajs.use(['sys/modeling/assembly/js/gantt.js', 'lui/jquery', 'lui/dialog', 'lui/topic'],
                function (modelingGantt,$, dialog, topic) {
                $('#listview').css('display','none');
                //屏蔽分页器前面的竖线
                if(!gantt_data.isPageViewFirst){
                $('#show_operation_line').remove();
                }
                window.init = function(gantt_data) {
                var cfg = {
                timeData: gantt_data.table.timeData,
                missionData: gantt_data.table.missionData,
                modelId: '${param.modelId}',
                fdEnableFlow: gantt_data.fdEnableFlow,
                fdModelName: gantt_data.fdModelName,
                fdViewFlag : gantt_data.fdViewFlag,
                fdViewId : gantt_data.fdViewId,
                businessId: '${param.businessId}'
                };
                window.modelingGantt = new modelingGantt.ModelingGantt(cfg);
                window.modelingGantt.startup();
                }
                init(gantt_data);

                });
            </ui:event>
        </div>
        <!-- 分页 -->
        <list:paging layout="${pageLayout}"/>
        <script>
            var gantt_data = {
                table: {
                    colorData: ${result.table.colorData},
                    projectData: ${result.table.projectData},
                    timeData: ${result.table.timeData},
                    missionData: ${result.table.missionData},
                },
                criterionData : {
                    sorts: [],
                    query: [],
                    page:[],
                    criterions: [],
                    lock: false,
                    customCriteria: {},//自定义事件
                    cacheEvt: []// 缓存事件，防止重复初始化
                },
                colorListIndex: '${result.colorListIndex}',
                fdEnableFlow: ${fdEnableFlow},
                drawerSettingData: [],
                fdModelName : '${fdModelName}',
                fdViewFlag : '${fdViewFlag}',
                fdViewId : '${fdViewId}',
                isOpenMilepost: false,
                isPageViewFirst: ${ empty viewOrderInfo.columns}
            };
            var listOption = {
                fdAppModelId : '${param.modelId}',
                isFlow : '${fdEnableFlow}',
            }
            Com_IncludeFile("index.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
            Com_IncludeFile("listview_export.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'],
                function ($, dialog, topic) {
                    // 监听新建更新等成功后刷新
                    topic.subscribe('successReloadPage', function() {
                        topic.publish("list.refresh");
                        var PAGING_CHANGED = 'paging.changed';
                        var evt = '';
                        topic.publish(PAGING_CHANGED,evt);
                    });

                    window.colorOnclick =  function(thisObj) {
                        var url = '/sys/modeling/assembly/pages/color.jsp';
                        dialog.iframe(url, "${ lfn:message('sys-modeling-main:modelingCalendar.color') }", function (data) {
                            if (data) {
                                if("submit" == data){
                                    //保存自定义颜色回调，发布事件刷新页面
                                    var evt = '';
                                    topic.publish("customColor.submit",evt);
                                    return;
                                }else{
                                    gantt_data.table.projectData = data;
                                    return;
                                }

                            }
                        }, {
                            width: 540,
                            height: 600,
                            params: {
                                colorData: gantt_data.table.colorData,
                                projectData: gantt_data.table.projectData,
                                colorListIndex:  gantt_data.colorListIndex,
                                modelId: '${param.modelId}',
                                businessId: '${param.businessId}'
                            },
                        });
                    };

                    window.drawerAddOnclick =  function(fdModelRecordId , fdConfig) {
                        // #138050 防止多次点击导致多次打开
                        if(gantt_data.isOpenMilepost){
                            return;
                        }else{
                            gantt_data.isOpenMilepost = true;
                        }
                        var startTime = '';
                        var endTime = '';
                        var missionContent = gantt_data.table.missionData.missionContent;
                        for (var i = 0; i < missionContent.length; i++) {
                            if(fdModelRecordId == missionContent[i].fdId){
                                startTime = missionContent[i].lineContent.start;
                                endTime = missionContent[i].lineContent.end;
                                break;
                            }
                        }
                        var url = '/sys/modeling/assembly/pages/milepost.jsp';
                        var title = '';
                        if(fdConfig){
                            title = "${ lfn:message('sys-modeling-main:modelingCalendar.edit.milestone') }";
                        }else{
                            title = "${ lfn:message('sys-modeling-main:modelingCalendar.new.milestone') }";
                        }
                        dialog.iframe(url, title, function (data) {
                            gantt_data.isOpenMilepost = false;
                            if (data) {
                                if("submit" == data){
                                    //发布事件刷新页面
                                    var evt = '';
                                    topic.publish("customColor.submit",evt);
                                }
                            }
                        }, {
                            width: 540,
                            height: 440,
                            params: {
                                fdModelRecordId: fdModelRecordId,
                                startTime: startTime,
                                endTime: endTime,
                                modelId: '${param.modelId}',
                                businessId: '${param.businessId}',
                                fdConfig: fdConfig
                            },
                        });
                    };

                    window.deleteMilepost=  function(fdId) {
                        //发生ajax请求，保存里程碑
                        var url = '${LUI_ContextPath}/sys/modeling/main/business.do?method=deleteMilepost&modelId=${param.modelId}'
                            +'&businessId=${param.businessId}&type=gantt&fdId='+fdId;
                        $.ajax({
                            type:"post",
                            url:url,
                            async:false ,    //用同步方式
                            dataType: "json",
                            success:function(data){
                                //发布事件刷新页面
                                var evt = '';
                                topic.publish("customColor.submit",evt);
                            }
                        });

                    };

                    window.comeBackToday =  function(thisObj){
                        var dataArr = gantt_data.table.timeData.dataArr;
                        //判断当前处于哪个视图,日、月、季、年
                        //判断当前日期是否处于视图时间轴范围，若不处于则返回提示
                        switch (gantt_data.table.timeData.defaultValue) {
                            case "day":
                                comeBackToday_day(dataArr);
                                break;
                            case "month":
                                comeBackToday_month(dataArr);
                                break;
                            case "quarter":
                                comeBackToday_quarter(dataArr);
                                break;
                            case "year":
                                comeBackToday_year(dataArr);
                                break;
                        }
                    }

                    //地址本
                    window.setAddressCriteriaVal = function(item,criteriaObj){
                        //默认值
                        var defaultValue = JSON.parse(item.config.defaultValue);
                        var model = item.children[1].criterionSelectElement.selectedValues.model;
                        var values = [];
                        if(defaultValue != null){
                            if(defaultValue.length > 1){
                                //触发多选
                                if(item.config.canMulti){
                                    LUI(item.children[1].cid).setMulti(true);
                                }
                            }
                            for(var i=0; i<defaultValue.length; i++){
                                values.push(defaultValue[i].value);
                                var isExit = false;
                                for(var j=0; j<model.length; j++){
                                    if(model[j].value == defaultValue[i].value){
                                        isExit = true;
                                    }
                                }
                                if(!isExit){
                                    if(item.config.conditionType == "person")
                                        model.push(defaultValue[i]);
                                }
                            }
                        }
                        item.children[1].criterionSelectElement.selectedValues.updateModel(model);
                        item.children[1].criterionSelectElement.selectedValues.addAll(values);
                    }
                    //枚举、文档状态（有流程）
                    window.setEnumCriteriaVal = function(item,criteriaObj){
                        //默认值
                        if(item.selectBox.criterionSelectElement.config.defaultValue != ""){
                            var defaultValue = JSON.parse(item.selectBox.criterionSelectElement.config.defaultValue);
                            var values = [];
                            if(defaultValue != null){
                                if(defaultValue.length > 1){
                                    //触发多选
                                    if(item.config.canMulti){
                                        LUI(item.selectBox.cid).setMulti(true);
                                    }
                                }
                                for(var i = 0 ;i < defaultValue.length; i++){
                                    values.push(defaultValue[i].value);
                                }
                            }
                            item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                        }
                    }
                    //数字，金额
                    window.setNumberCriteriaVal = function (item,criteriaObj){
                        //默认值
                        var defaultValue = JSON.parse(item.config.defaultValue);
                        var values = [];
                        var isnull = false;
                        if(defaultValue != null){
                            for (var j = 0;j < defaultValue.length;j++){
                                var n = Number(defaultValue[j].value);
                                if (isNaN(n)){
                                    //不是数字
                                    isnull = false;
                                    break;
                                }
                                values.push(defaultValue[j].value);
                                if(defaultValue[j].value != ""){
                                    isnull = true;
                                }
                            }
                        }
                        if(isnull){
                            item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                        }
                    }
                    //时间
                    window.setTimeCriteriaVal = function(item,criteriaObj){
                        //默认值
                        var defaultValue = JSON.parse(item.config.defaultValue);
                        var isnull = false;
                        for(var i = 0;i < defaultValue.length;i++ ){
                            var regexs = /^(([0-2][0-3])|([0-1][0-9])):[0-5][0-9]$/;
                            if(defaultValue[i] != ""){
                                isnull = true;
                                if(!regexs.test(defaultValue[i])){
                                    return
                                }
                            }
                        }
                        if(isnull){
                            item.selectBox.criterionSelectElement.selectedValues.addAll(defaultValue);
                        }
                    }
                    //文本
                    window.setTextCriteriaVal = function(item,criteriaObj){
                        var values = [];
                        //默认值
                        if(item.config.defaultValue){
                            var defaultValue = item.config.defaultValue;
                            values.push(defaultValue);
                            item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                        }
                    }
                    //日期6
                    window.setDateCriteriaVal = function(item,calendarObj){
                        var allDates = item.selectBox.criterionSelectElement.allDates;
                        if(item.config.defaultValue){
                            setDateCriteriaValModify(allDates,item,item.config.defaultValue);
                        }
                    }
                    //修改日期类型组件values
                    window.setDateCriteriaValModify = function(allDates,item,type){
                        if(type.indexOf("[") == -1){
                            var values = [];
                            var allDatasArr = allDates[type];
                            if(allDatasArr){
                                for(var i = 0;i < allDatasArr.length;i++){
                                    var obj = {};
                                    obj.text = allDatasArr[i];
                                    obj.value = allDatasArr[i];
                                    values.push(obj);
                                }
                                item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                            }
                        }
                    }
                    //筛选器设置默认值
                    var num = setInterval(function () {
                        var criteria = $(".criteria")[0];
                        if (!criteria) {
                            return;
                        }
                        var uid = $(criteria).attr("data-lui-cid");
                        //打开筛选
                        var criteriaObj = LUI(uid);
                        if (!criteriaObj.isDrawed || !criteriaObj.moreAction) {
                            return;
                        }
                        clearInterval(num);
                        criteriaObj.expandCriterions(criteriaObj.expand);
                        //初始化筛选器的值
                        setTimeout(function(){
                            var criteriaArr = criteriaObj.criterions;
                            for (var i = 0; i < criteriaArr.length; i++) {
                                var item = criteriaArr[i];
                                var bsnsType = item.selectBox.criterionSelectElement.config.conditionBusinessType || "";
                                if(item.config.conditionType == "DateTime" || item.config.conditionType == "Date" ){
                                    var calendar = $(".criterion-calendar")[0];
                                    if(calendar){
                                        //日期
                                        uid = $(calendar).attr("data-lui-cid");
                                        var calendarObj = LUI(uid);
                                        setDateCriteriaVal(item,calendarObj);
                                    }
                                }else if(item.config.conditionType == "Time"){
                                    //时间
                                    setTimeCriteriaVal(item,calendarObj);
                                }else if(item.config.conditionType == "person" || item.config.conditionType == "post" ||item.config.conditionType == "dept"){
                                    //地址本
                                    setAddressCriteriaVal(item,criteriaObj);
                                }else if(item.config.conditionType == "number"){
                                    //数字，金额
                                    setNumberCriteriaVal(item,criteriaObj);
                                }else if(bsnsType == "select" || bsnsType == "inputCheckbox" || bsnsType == "inputRadio" || item.config.key === "docStatus"){
                                    //（枚举）多选，单选，下拉，文档状态
                                    setEnumCriteriaVal(item,criteriaObj);
                                }else{
                                    //文本
                                    setTextCriteriaVal(item,criteriaObj);
                                }
                            }
                        },500);
                    }, 200);

                    function comeBackToday_day(dataArr) {
                        var now = new Date;
                        var nowTime = now.getTime();
                        var startYear, startMonth, startDay;
                        var endYear, endMonth, endDay;
                        var title = dataArr[0].content[0].title;
                        startYear = title.substring(0,4);
                        startMonth = title.substring(5,7);
                        startDay = dataArr[0].content[0].subTitle[0].dateNum;

                        var lastContentLen = dataArr[0].content.length-1;
                        title =  dataArr[0].content[lastContentLen].title;
                        endYear = title.substring(0,4);
                        endMonth = title.substring(5,7);
                        var lastSubTitleLen = dataArr[0].content[lastContentLen].subTitle.length - 1;
                        endDay = dataArr[0].content[lastContentLen].subTitle[lastSubTitleLen].dateNum;
                        //判断当前日期是否在区间内
                        var startTime = (new Date(startYear+"/"+startMonth+"/"+startDay +" 00:00:00")).getTime();
                        var endTime =  (new Date(endYear+"/"+endMonth+"/"+endDay+" 00:00:00")).getTime();
                        if(startTime < nowTime && nowTime < endTime){
                            //在区间内，计算与开始时间的时间差
                            var dayCount = parseInt((nowTime - startTime)/(60*60*24*1000));
                            if(!$('.model-gantt-table-today').hasClass('active')){
                                $('.model-gantt-table-today').addClass('active');
                            }
                            $('.model-gantt-table-todayY').css("left",dayCount*35+16);

                            $('.model-gantt-table-content-header-subtitle').find('p').eq(dayCount)
                                .css({
                                    "border-radius":"50%",
                                    "background-color":"#4285F4",
                                    "margin": "4px",
                                    "width": "26px",
                                    "height": "26px",
                                    "line-height": "26px"});
                            if(dayCount>16){
                                dayCount = dayCount-16;
                            }else{
                                dayCount = 0;
                            }
                            $('.model-gantt-table').scrollLeft(dayCount*35);

                        }else{
                            dialog.alert("${ lfn:message('sys-modeling-main:modelingCalendar.current.date.not.timeline') }")
                        }
                    };

                    function comeBackToday_month(dataArr) {
                        var now = new Date;
                        var nowTime = now.getTime();
                        var startYear, startMonth;
                        var endYear, endMonth;
                        var title = dataArr[1].content[0].title;
                        startYear = title.substring(0,4);
                        startMonth = dataArr[1].content[0].subTitle[0];

                        var lastContentLen = dataArr[1].content.length-1;
                        title =  dataArr[1].content[lastContentLen].title;
                        endYear = title.substring(0,4);
                        var lastSubTitleLen = dataArr[1].content[lastContentLen].subTitle.length - 1;
                        endMonth = dataArr[1].content[lastContentLen].subTitle[lastSubTitleLen];
                        //判断当前日期是否在区间内
                        if(endMonth==12){
                            endYear++;
                            endMonth=1;
                        }

                        var startTime = (new Date(startYear+"/"+startMonth+"/1" +" 00:00:00")).getTime();
                        var endTime =  (new Date(endYear+"/"+endMonth+"/1"+" 00:00:00")).getTime();
                        if(startTime < nowTime && nowTime < endTime){
                            //在区间内，计算与开始时间的时间差
                            var nowYear = now.getFullYear();
                            var nowMonth = now.getMonth() + 1;
                            var monthCount = (nowYear - startYear)*12 + (nowMonth - startMonth);
                            if(!$('.model-gantt-table-today').hasClass('active')){
                                $('.model-gantt-table-today').addClass('active');
                            }
                            $('.model-gantt-table-todayY').css("left",monthCount*35+16);
                            $('.model-gantt-table-content-header-subtitle').find('p').eq(monthCount)
                                .css({
                                    "border-radius":"50%",
                                    "background-color":"#4285F4",
                                    "margin": "4px",
                                    "width": "26px",
                                    "height": "26px",
                                    "line-height": "26px"});
                            if(monthCount>16){
                                monthCount = monthCount-16;
                            }else{
                                monthCount = 0;
                            }
                            $('.model-gantt-table').scrollLeft(monthCount*35);

                        }else{
                            dialog.alert("${ lfn:message('sys-modeling-main:modelingCalendar.current.date.not.timeline') }")
                        }
                    };

                    function comeBackToday_quarter(dataArr) {
                        var now = new Date;
                        var nowTime = now.getTime();
                        var startYear, startMonth, startQuarter;
                        var endYear, endMonth, endQuarter;
                        var title = dataArr[2].content[0].title;
                        startYear = title.substring(0,4);
                        startQuarter = dataArr[2].content[0].subTitle[0];

                        var lastContentLen = dataArr[2].content.length-1;
                        title =  dataArr[2].content[lastContentLen].title;
                        endYear = title.substring(0,4);
                        var lastSubTitleLen = dataArr[2].content[lastContentLen].subTitle.length - 1;
                        endQuarter = dataArr[2].content[lastContentLen].subTitle[lastSubTitleLen];

                        switch (startQuarter) {
                            case '一季度':
                                startMonth = 1;
                                break;
                            case '二季度':
                                startMonth = 4;
                                break;
                            case '三季度':
                                startMonth = 7;
                                break;
                            case '四季度':
                                startMonth = 10;
                                break;
                        }
                        switch (endQuarter) {
                            case '一季度':
                                endMonth = 4;
                                break;
                            case '二季度':
                                endMonth = 7;
                                break;
                            case '三季度':
                                endMonth = 10;
                                break;
                            case '四季度':
                                endYear++;
                                endMonth=1;
                                break;
                        }
                        //判断当前日期是否在区间内
                        var startTime = (new Date(startYear+"/"+startMonth+"/1" +" 00:00:00")).getTime();
                        var endTime =  (new Date(endYear+"/"+endMonth+"/1"+" 00:00:00")).getTime();
                        if(startTime < nowTime && nowTime < endTime){
                            //在区间内，计算与开始时间的时间差
                            var nowYear = now.getFullYear();
                            var nowMonth = now.getMonth() + 1;
                            var monthCount = (nowYear - startYear)*12 + (nowMonth - startMonth);
                            var quarterCount = parseInt(monthCount/3);
                            if(!$('.model-gantt-table-today').hasClass('active')){
                                $('.model-gantt-table-today').addClass('active');
                            }
                            $('.model-gantt-table-todayY').css("left",quarterCount*70+33);
                            $('.model-gantt-table-content-header-subtitle').find('p').eq(quarterCount)
                                .css({
                                    "border-radius":"70%",
                                    "background-color":"#4285F4",
                                    "margin": "4px",
                                    "width": "60px",
                                    "height": "26px",
                                    "line-height": "26px"});
                            if(quarterCount>8){
                                quarterCount = quarterCount-8;
                            }else{
                                quarterCount = 0;
                            }
                            $('.model-gantt-table').scrollLeft(quarterCount*70);

                        }else{
                            dialog.alert("${ lfn:message('sys-modeling-main:modelingCalendar.current.date.not.timeline') }")
                        }
                    };

                    function comeBackToday_year(dataArr) {
                        var now = new Date;
                        var nowTime = now.getTime();
                        var startYear, startMonth, startUpDown;
                        var endYear, endMonth, endUpDown;
                        var title = dataArr[3].content[0].title;
                        startYear = title.substring(0,4);
                        startUpDown = dataArr[3].content[0].subTitle[0];

                        var lastContentLen = dataArr[3].content.length-1;
                        title =  dataArr[3].content[lastContentLen].title;
                        endYear = title.substring(0,4);
                        var lastSubTitleLen = dataArr[3].content[lastContentLen].subTitle.length - 1;
                        endUpDown = dataArr[3].content[lastContentLen].subTitle[lastSubTitleLen];

                        switch (startUpDown) {
                            case '上':
                                startMonth = 1;
                                break;
                            case '下':
                                startMonth = 7;
                                break;
                        }
                        switch (endUpDown) {
                            case '上':
                                endMonth = 7;
                                break;
                            case '下':
                                endYear++;
                                endMonth = 1;
                                break;
                        }
                        //判断当前日期是否在区间内
                        var startTime = (new Date(startYear+"/"+startMonth+"/1" +" 00:00:00")).getTime();
                        var endTime =  (new Date(endYear+"/"+endMonth+"/1"+" 00:00:00")).getTime();
                        if(startTime < nowTime && nowTime < endTime){
                            //在区间内，计算与开始时间的时间差
                            var nowYear = now.getFullYear();
                            var nowMonth = now.getMonth() + 1;
                            var monthCount = (nowYear - startYear)*12 + (nowMonth - startMonth);
                            var yearCount = parseInt(monthCount/6);
                            if(!$('.model-gantt-table-today').hasClass('active')){
                                $('.model-gantt-table-today').addClass('active');
                            }
                            $('.model-gantt-table-todayY').css("left",yearCount*70+33);
                            $('.model-gantt-table-content-header-subtitle').find('p').eq(yearCount)
                                .css({
                                    "border-radius":"70%",
                                    "background-color":"#4285F4",
                                    "margin": "4px",
                                    "width": "60px",
                                    "height": "26px",
                                    "line-height": "26px"});
                            if(yearCount>8){
                                yearCount = yearCount-8;
                            }else{
                                yearCount = 0;
                            }
                            $('.model-gantt-table').scrollLeft(yearCount*70);

                        }else{
                            dialog.alert("${ lfn:message('sys-modeling-main:modelingCalendar.current.date.not.timeline') }")
                        }
                    }
                })
        </script>

    </template:replace>
</template:include>