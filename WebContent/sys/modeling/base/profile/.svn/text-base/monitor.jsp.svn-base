<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%
    if (!UserUtil.checkRole("ROLE_MODELING_MONITOR")) {
        request.getRequestDispatcher("/resource/jsp/e403.jsp").forward(request, response);
    }
%>
<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('sys-modeling-base:module.sys.modeling') }"></c:out>
    </template:replace>
    <template:replace name="head">
        <link type="text/css" rel="stylesheet"
              href="${ LUI_ContextPath }/sys/modeling/base/resources/css/monitor.css?s_cache=${LUI_Cache}"/>
        <style>
            .lui_list_body_frame br {
                 display: block !important;
            }
        </style>
        <script type="text/javascript">
            seajs.use(['theme!form']);
            Com_IncludeFile("echarts4.2.1.js", "${LUI_ContextPath}/sys/ui/js/chart/echarts/", null, true);
        </script>
    </template:replace>
    <template:replace name="nav">

    </template:replace>
    <template:replace name="content">
        <div class="monitorContainer">
            <div class="monitorTitle">
                    ${lfn:message("sys-modeling-base:sys.profile.modeling.monitor")}
            </div>
            <div class="monitorStatistics">
                    <%--应用统计--%>
                <div class="monitorStatisticsApp">
                    <div class="monitorStatisticsAppTitle">
                        <div>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.statisticsApps")}</div>
                    </div>
                    <ul class="monitorStatisticsAppList">
                        <li class="monitorStatisticsAppTotal">
                            <a href="javascript:void(0)">
                                <p class="appTotal">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.totalApps")}</span>
                            </a>
                        </li>
                        <li class="monitorStatisticsAppPublish">
                            <a href="javascript:void(0)">
                                <p class="publish">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.publishedApps")}</span>
                            </a>
                        </li>
                        <li class="monitorStatisticsAppNotPublish">
                            <a href="javascript:void(0)">
                                <p class="notPublish">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.unPublishedApps")}</span>
                            </a>
                        </li>
                        <li class="monitorStatisticsAppSelfBuild">
                            <a href="javascript:void(0)">
                                <p class="selfBuild">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.selfBuiltApps")}</span>
                            </a>
                        </li>
                        <li class="monitorStatisticsAppInstall">
                            <a href="javascript:void(0)">
                                <p class="install">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.installedApps")}</span>
                            </a>
                        </li>
                    </ul>
                </div>
                    <%--表单总览--%>
                <div class="monitorStatisticsForm">
                    <div class="monitorStatisticsFormTitle">
                        <div>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.formOverview")}</div>
                    </div>
                    <ul class="monitorStatisticsFormList">
                        <li>
                            <a href="javascript:void(0)">
                                <p class="formTotal">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.totalForms")}</span>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:void(0)">
                                <p class="enableFlow">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.processForms")}</span>
                            </a>
                        </li>
                        <li>
                            <a href="javascript:void(0)">
                                <p class="simple">00</p>
                                <span>${lfn:message("sys-modeling-base:sys.profile.modeling.homePage.noProcessForms")}</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="monitorChart">
                    <%--执行队列--%>
                <div id="monitorChartTriggerExecQueue" class="monitorChartTriggerExecQueue">
                    <div id="monitorChartTriggerExecQueueContent" class="monitorChartTriggerExecQueueContent"></div>
                    <div id="monitorChartTriggerExecQueueFoot" class="monitorChartTriggerExecQueueFoot"></div>
                </div>
                    <%--触发事件执行时间--%>
                <div class="monitorChartTriggerExecTime">
                    <div class="monitorChartButtons">
                        <div  class="triggerExecTimeSelect " style="border: 0">
                            <div class="triggerExecTimeSelectInput">
                                <div class="triggerExecTimeSelectInputText" value="week">
                                        ${lfn:message("sys-modeling-base:listview.nearly.a.week")}
                                </div>
                                <div class="triggerExecTimeSelectIcon" >
                                </div>
                            </div>
                            <div class="triggerExecTimeSelectList">
                                <div><span value="week" onclick="execTimeSelectSpanOnclick(this,'week')" >${lfn:message("sys-modeling-base:listview.nearly.a.week")}</span></div>
                                <div><span value="month" onclick="execTimeSelectSpanOnclick(this,'month')" >${lfn:message("sys-modeling-base:listview.nearly.a.month")}</span></div>
                                <div><span value="threeMonth" onclick="execTimeSelectSpanOnclick(this,'threeMonth')" >${lfn:message("sys-modeling-base:listview.nearly.3.month")}</span></div>
                                <div><span value="halfYear" onclick="execTimeSelectSpanOnclick(this,'halfYear')" >${lfn:message("sys-modeling-base:listview.nearly.half.a.year")}</span></div>
                                <div><span value="year" onclick="execTimeSelectSpanOnclick(this,'year')" >${lfn:message("sys-modeling-base:listview.nearly.a.year")}</span></div>
                            </div>
                        </div>
                        <div class="triggerExecTimeAverage active" style="border: 1px solid #4285F4;" onclick="triggerExecTimeAverage(this)">${lfn:message("sys-modeling-base:modelingBehaviorLog.triggerExecTimeAverage")}</div>
                        <div class="triggerExecTimeTotal" onclick="triggerExecTimeTotal(this)">${lfn:message("sys-modeling-base:modelingBehaviorLog.triggerExecTimeTotal")}</div>
                    </div>
                    <div id="monitorChartTriggerExecTimeContent"  class="monitorChartTriggerExecTimeContent"></div>
                    <div id="monitorChartTriggerExecTimeFoot" class="monitorChartTriggerExecTimeFoot"></div>
                </div>
            </div>

                <%--日志--%>
            <div class="monitorLog">
                <ui:tabpanel>
                    <ui:content title='${lfn:message("sys-modeling-base:table.modelingOperLog")}'>
                        <ui:iframe id="operlogIframe" src="${LUI_ContextPath}/sys/modeling/base/profile/log/operationLog.jsp"></ui:iframe>
                    </ui:content>
                    <ui:content title='${lfn:message("sys-modeling-base:modeling.form.FormModificationLog")}'>
                        <ui:iframe id="modificationlogIframe" src="${LUI_ContextPath}/sys/modeling/base/profile/log/formModifiedLog.jsp"></ui:iframe>
                    </ui:content>
                    <ui:content title='${lfn:message("sys-modeling-base:table.modelingBehaviorLog")}'>
                        <ui:iframe id="behaviorlogIframe" src="${LUI_ContextPath}/sys/modeling/base/profile/log/behaviorLog.jsp"></ui:iframe>
                    </ui:content>
                    <ui:content title='${lfn:message("sys-modeling-base:table.modelingInterfaceLog")}'>
                        <ui:iframe id="interfacelogIframe" src="${LUI_ContextPath}/sys/modeling/base/profile/log/interfaceLog.jsp"></ui:iframe>
                    </ui:content>
                </ui:tabpanel>
            </div>
        </div>
        <div class="xAxisTip"></div>
        <script>
            var execQueueChart, execTimeChart;
            function initTriggerExecQueue() {
                var url = Com_Parameter.ContextPath + "sys/modeling/base/behaviorLog.do?method=getBehaviorCountTotal";
                $.ajax({
                    url: url,
                    type: "get",
                    dataType : 'json',
                    success: function (data) {
                        buildExecQueue(data)
                    }
                });
            }

            function buildExecQueue(data) {
                var dom = document.getElementById("monitorChartTriggerExecQueueContent");
                var myChart = echarts.init(dom);
                execQueueChart = myChart;
                var option;
                option = {
                    title: {
                        text: '${lfn:message("sys-modeling-base:modelingBehaviorLog.execQueue")}',
                        textStyle:{
                            color : '#333333' ,
                            fontWeight : 500,
                            fontSize : 16 ,
                            lineHeight : 24 ,
                        },
                        padding: [12,16,12,16]
                    },
                    tooltip:{
                        trigger: 'axis',
                        formatter(params) {
                            var marker = '<div class="tooltipExecTimeSpan"><span ></span></div>'
                            var str =  "<div class='tooltipName'>" + params[0].axisValueLabel + "</div>";
                            str = str + "<div class='tooltipExecTime'>" + marker;
                            str = str + "<div class='tooltipExecTimeContent'> ${lfn:message("sys-modeling-base:modelingBehaviorLog.execQueue")}";
                            str = str + ":  " +  params[0].data + "</div></div>";
                            str = str + "<br />";
                            return str;
                        },
                        backgroundColor : '#FFFFFF',
                        borderColor : '#333',
                        textStyle: {
                            color : '#333333'
                        },
                        extraCssText: 'box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);'
                    } ,
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    color:[
                        "#4285F4"
                    ],
                    label: {
                        formatter: function (value, index) {
                            // 格式化成时分/日
                            var date = new Date(value.value);
                            var minutes = date.getMinutes();
                            var minutesStr = '';
                            if(minutes < 10){
                                minutesStr = '0'+minutes;
                            }else{
                                minutesStr = minutes;
                            }
                            var texts = [date.getHours(),minutesStr];
                            return texts.join(':');
                        }
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        // 使用函数模板，函数参数分别为刻度数值（类目），刻度的索引
                        axisLabel: {
                            formatter: function (value, index) {
                                // 格式化成时分
                                var date = new Date(value);
                                var minutes = date.getMinutes();
                                var minutesStr = '';
                                if(minutes < 10){
                                    minutesStr = '0'+minutes;
                                }else{
                                    minutesStr = minutes;
                                }
                                var texts = [date.getHours(),minutesStr];
                                return texts.join(':');
                            }
                        },
                        // data: ['2009/6/12 2:05:00', '2009/6/12 2:10:00', '2009/6/12 2:15:00', '2009/6/12 2:20:00', '2009/6/12 2:25:00', '2009/6/12 2:30:00', '2009/6/12 2:35:00']
                        data: data.xAxis,
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        }
                    },
                    yAxis: {
                        type: 'value',
                        axisLine: {
                            show: false
                        },
                        splitLine: {
                            lineStyle: {
                                type: "dashed"
                            }
                        },
                        axisTick: {
                            show: false
                        }
                    },
                    series: [
                        {
                            name: '${lfn:message("sys-modeling-base:modelingBehaviorLog.execQueue")}',
                            type: 'line',
                            stack: 'Total',
                            areaStyle: {
                                color:{
                                    type: 'linear',
                                    x: 0,
                                    y: 0,
                                    x2: 0,
                                    y2: 1,
                                    colorStops: [{
                                        offset: 0, color: 'rgba(236,243,254,1)' // 0% 处的颜色
                                    }, {
                                        offset: 1, color: 'rgba(236,243,254,0)' // 100% 处的颜色
                                    }],
                                    global: false // 缺省为 false
                                }
                            },
                            // data: [120, 132, 101, 134, 90, 230, 210]
                            data: data.series,
                        },
                    ]
                };
                if (option && typeof option === 'object') {
                    myChart.setOption(option);
                }
            }

            function initTriggerExecTime(mathType,timeSelectType) {
                var url = Com_Parameter.ContextPath + "sys/modeling/base/behaviorLog.do?method=getBehaviorConsumeTotal";
                if(mathType){
                    url = url + "&mathType=" + mathType;
                }
                if(timeSelectType){
                    url = url + "&timeSelectType=" + timeSelectType;
                }
                $.ajax({
                    url: url,
                    type: "get",
                    dataType : 'json',
                    success: function (data) {
                        buildExecTime(data)
                    }
                });
            }

            function buildExecTime(data) {
                var dom = document.getElementById("monitorChartTriggerExecTimeContent");
                var myChart = echarts.init(dom);
                execTimeChart = myChart;
                var option;
                var date = new Date();
                var hour = date.getHours();
                var minutes = date.getMinutes();
                var dayTime = 60 * 60 * 24 * 1000;
                //00:10分前查询的是前一天的数据
                if( 0 == hour && minutes < 10){
                    date.setTime(date.getTime() - dayTime * 2 ) ;
                }else {
                    date.setTime(date.getTime() - dayTime) ;
                }
                var month = date.getMonth() + 1;
                var day = date.getDate();
                var monthStr = month;
                var dayStr = day;
                if(month<10){
                    monthStr = "0" + monthStr;
                }
                if(day < 10 ){
                    dayStr = "0" + dayStr;
                }
                var limitTip = "(${lfn:message("sys-modeling-base:modelingBehaviorLog.statisticsAsOf")}"+monthStr+"-"+dayStr+" 24:00)"
                $('.monitorChartTriggerExecTimeFoot').text(limitTip);
                option = {
                    title: {
                        text: '${lfn:message("sys-modeling-base:modelingBehaviorLog.triggerExecTime")}',
                        padding: [12,16,12,16],
                        textStyle:{
                            color : '#333333' ,
                            fontWeight : 500,
                            fontSize : 16 ,
                            lineHeight : 24 ,
                            rich: {
                                a: {
                                    color: '#999999',
                                    lineHeight: 10,
                                    padding:10,
                                },
                            }
                        },
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    color: [
                        'rgba(66,133,244,1)'
                    ],
                    xAxis: {
                        type: 'value',
                        axisLabel: {
                            formatter: function (value, index) {
                                return value + 'ms';
                            }
                        },
                        boundaryGap: [0, 0.01],
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        splitLine: {
                            lineStyle: {
                                type: "dashed"
                            }
                        }
                    },
                    yAxis: {
                        type: 'category',
                        // data: ['项目1', '项目2', '项目3', '项目4', '项目5', '项目6', '项目7', '项目8', '项目9', '项目10']
                        data: data.yAxis,
                        axisLine: {
                            show: true,
                            lineStyle: {
                                color: "#999999"
                            }
                        },
                        axisLabel: {
                            formatter: function (value) {
                                let valueTxt = ''
                                if (value.length > 8) {
                                    valueTxt = value.substring(0, 8) + '...'
                                } else {
                                    valueTxt = value
                                }
                                return valueTxt
                            }
                        },
                        triggerEvent: true,
                        axisTick: {
                            show: false
                        }
                    },
                    tooltip:{
                        formatter(params) {
                            var marker = '<div class="tooltipExecTimeSpan"><span ></span></div>'
                            var str =  "<div class='tooltipName'>" + params.name + "</div>";
                            str = str + "<div class='tooltipExecTime'>" + marker;
                            str = str + "<div class='tooltipExecTimeContent'> ${lfn:message("sys-modeling-base:modelingBehaviorLog.execTime")}";
                            if(isExecTimeTotalFlag){
                                str = str + "(" + "${lfn:message("sys-modeling-base:modelingBehaviorLog.triggerExecTimeTotal")}"+")";
                            }else{
                                str = str + "(" + "${lfn:message("sys-modeling-base:modelingBehaviorLog.triggerExecTimeAverage")}"+")";
                            }
                            str = str + ":  " +  params.data + "ms</div></div>";
                            str = str + "<br />";
                            str = str + "<div class='tooltipExecTime'>" + marker;
                            str = str + "<div class='tooltipExecTimeContent'> ${lfn:message("sys-modeling-base:modelingBehaviorLog.triggerExecTimeCount")}:  " +  data.seriesCount[params.seriesIndex] + "</div></div>";
                            return str;
                        },
                        backgroundColor : '#FFFFFF',
                        borderColor : '#333',
                        textStyle: {
                            color : '#333333'
                        },
                        extraCssText: 'box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);'
                    } ,
                    series: [
                        {
                            name: '${lfn:message("sys-modeling-base:modelingBehaviorLog.execTime")}',
                            type: 'bar',
                            // data: [111,123,432,453,533,322,232,233,342,634]
                            data: data.seriesTime
                        }
                    ]
                };

                if (option && typeof option === 'object') {
                    myChart.setOption(option);
                    myChart.on('mouseover', function (params) {
                        if (params.componentType === 'yAxis') {
                            var offsetX = params.event.event.offsetX + $(".monitorChartTriggerExecTimeContent").offset().left;
                            var offsetY = params.event.event.offsetY + $(".monitorChartTriggerExecTimeContent").offset().top;
                            var xAxisTip = document.querySelector('.xAxisTip')
                            xAxisTip.innerText = params.value.split('#')[0]
                            xAxisTip.style.left = offsetX + 'px'
                            xAxisTip.style.top = offsetY + 10 + 'px'
                            xAxisTip.style.display = 'block'
                        }
                    })
                    myChart.on('mousemove', function (params) {
                        if (params.componentType === 'yAxis') {
                            var offsetX = params.event.event.offsetX + $(".monitorChartTriggerExecTimeContent").offset().left;
                            var offsetY = params.event.event.offsetY + $(".monitorChartTriggerExecTimeContent").offset().top;
                            var xAxisTip = document.querySelector('.xAxisTip')
                            xAxisTip.innerText = params.value.split('#')[0]
                            xAxisTip.style.left = offsetX + 30 + 'px'
                            xAxisTip.style.top = offsetY + 10 + 'px'
                            xAxisTip.style.display = 'block'
                        }
                    })
                    myChart.on('mouseout', function (params) {
                        var xAxisTip = document.querySelector('.xAxisTip')
                        xAxisTip.style.display = 'none'
                    })
                }
            }

            var isExecTimeTotalFlag = false;
            seajs.use(['lui/dialog', 'lui/topic','lui/jquery'], function (dialog, topic,$) {
                window.onresize = () => {
                    if(execTimeChart){
                        execTimeChart.resize();
                    }
                    if(execQueueChart){
                        execQueueChart.resize();
                    }
                }
                window.execTimeSelectSpanOnclick = function (e,timeSelectType){
                    $(".triggerExecTimeSelectInputText").text($(e).text());
                    $(".triggerExecTimeSelectInputText").val(timeSelectType);
                    triggerExecTimeSelectInputOnclick();
                    var mathType ="total";
                    if($('.triggerExecTimeAverage').hasClass('active')){
                        mathType = "average";
                    }
                    initTriggerExecTime(mathType,timeSelectType);
                }

                $('.triggerExecTimeSelectInput').mouseover(function () {
                    $(".triggerExecTimeSelectIcon").css("-webkit-transform","rotate(180deg)");
                    $(".triggerExecTimeSelectIcon").css("transform","rotate(180deg)");
                    $(".triggerExecTimeSelectList").css("display","block");
                })

                $('.triggerExecTimeSelectInput').mouseout(function () {
                    $(".triggerExecTimeSelectIcon").css("-webkit-transform","rotate(0deg)");
                    $(".triggerExecTimeSelectIcon").css("transform","rotate(0deg)");
                    $(".triggerExecTimeSelectList").css("display","none");
                })

                $('.triggerExecTimeSelectList').mouseover(function () {
                    $(".triggerExecTimeSelectIcon").css("-webkit-transform","rotate(180deg)");
                    $(".triggerExecTimeSelectIcon").css("transform","rotate(180deg)");
                    $(".triggerExecTimeSelectList").css("display","block");
                })

                $('.triggerExecTimeSelectList').mouseout(function () {
                    $(".triggerExecTimeSelectIcon").css("-webkit-transform","rotate(0deg)");
                    $(".triggerExecTimeSelectIcon").css("transform","rotate(0deg)");
                    $(".triggerExecTimeSelectList").css("display","none");
                })

                window.triggerExecTimeSelectInputOnclick = function(e){

                    if("none" ==  $(".triggerExecTimeSelectList").css("display")){
                        $(".triggerExecTimeSelectIcon").css("-webkit-transform","rotate(180deg)");
                        $(".triggerExecTimeSelectIcon").css("transform","rotate(180deg)");
                        $(".triggerExecTimeSelectList").css("display","block");
                    }else{
                        $(".triggerExecTimeSelectIcon").css("-webkit-transform","rotate(0deg)");
                        $(".triggerExecTimeSelectIcon").css("transform","rotate(0deg)");
                        $(".triggerExecTimeSelectList").css("display","none");
                    }

                }

                window.triggerExecTimeAverage = function(e){
                    if($(e).hasClass('active')){
                        return;
                    }
                    isExecTimeTotalFlag = false;
                    $('.triggerExecTimeTotal').removeClass('active');
                    $('.triggerExecTimeTotal').css('border','1px solid #DDDDDD');
                    $(e).css('border','1px solid #4285F4');

                    //获取时间范围
                    var timeSelectType = $("#triggerExecTimeSelectInputText").val();
                    initTriggerExecTime("average",timeSelectType);
                }

                window.triggerExecTimeTotal = function(e){
                    if($(e).hasClass('active')){
                        return;
                    }
                    isExecTimeTotalFlag = true;
                    $('.triggerExecTimeAverage').removeClass('active');
                    $('.triggerExecTimeAverage').css('border','1px solid #DDDDDD');
                    $(e).css('border','1px solid #4285F4');
                    //获取时间范围
                    var timeSelectType =  $(".triggerExecTimeSelectInputText").val();
                    initTriggerExecTime("total",timeSelectType);
                }

                function initStaticsAppInfo(){
                    var url = Com_Parameter.ContextPath + "sys/modeling/base/monitor.do?method=getAppCounts";
                    $.ajax({
                        url: url,
                        type: "post",
                        data: {},
                        success: function (rtn) {
                            if (rtn) {
                                var itemObj = $(".monitorStatisticsAppList");
                                itemObj.find(".publish").html(formateNum(rtn['publishApps'] || 0));
                                itemObj.find(".notPublish").html(formateNum(rtn['notPublishApps'] || 0));
                                itemObj.find(".selfBuild").html(formateNum(rtn['myApps'] || 0));
                                itemObj.find(".install").html(formateNum(rtn['installedApps'] || 0));
                                itemObj.find(".appTotal").html(formateNum(rtn['allApps'] || 0));
                            } else {

                            }
                        }
                    });
                }

                function initStaticsFormInfo(){
                    var url = Com_Parameter.ContextPath + "sys/modeling/base/monitor.do?method=getFormsCount";
                    $.ajax({
                        url: url,
                        type: "post",
                        data: {},
                        success: function (rtn) {
                            if (rtn) {
                                var itemObj = $(".monitorStatisticsFormList");
                                itemObj.find(".formTotal").html(formateNum(rtn['allForms'] || 0));
                                itemObj.find(".enableFlow").html(formateNum(rtn['flowForms'] || 0));
                                itemObj.find(".simple").html(formateNum(rtn['simpleForms'] || 0));
                            } else {

                            }
                        }
                    });
                }

                $(document).ready(function (){
                    initStaticsAppInfo();
                    initStaticsFormInfo();

                    //初始化图表
                    initTriggerExecQueue();
                    initTriggerExecTime();
                });
                function formateNum(val){
                    return val >= 10 ? val : ('0' + val);
                }

            });
        </script>
    </template:replace>
</template:include>
