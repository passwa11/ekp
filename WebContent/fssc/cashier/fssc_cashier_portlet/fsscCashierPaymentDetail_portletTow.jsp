<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<title>
    ${ lfn:message('fssc-pres:portlet.doc.view.flat') }
</title>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/fssc/common/resource/css/custom.css">
</head>
<style>
    #j{
        margin-left:5px;
        margin-right: 10px;
    }

    #avg{
        width: 96px;
        height: 12px;
        font-family: PingFangSC-Regular;
        font-size: 12px;
        color: rgba(0,0,0,0.65);
        line-height: 12px;
        margin-right:10px;
        font-weight: 400;
    }
</style>
<div class="lui_fssc_financial_card_portlet_container">
    <div class="lui_fssc_financial_card">
        <div class="lui_fssc_financial_card_items_header">
            <i class="lui_fssc_financial_card_icons financial_primary">
                <span class="lui_fssc_financial_card_icon"></span>
            </i>
            <span class="lui_fssc_financial_card_title">${lfn:message('fssc-cashier:portlet.doc.view.flatTow')}</span>
            <span class="lui_fssc_financial_card_detail">
                                <div class="lui_fssc_financial_card_detail_wrapper">
                                    <i class="lui_fssc_financial_card_detail_icon">
                                    </i>
                                    <div class="lui_fssc_financial_card_tips">
                                        <span>${lfn:message('fssc-cashier:portlet.doc.view.payMentAvgDay.tip')}</span>
                                    </div>
                                </div>
                            </span>
        </div>
        <div class="lui_fssc_financial_card_content">
            <span id="sum"></span>
        </div>

        <div id="main1" style="width: 170px; height: 56px; -webkit-tap-highlight-color: transparent; user-select: none;" _echarts_instance_="ec_1630565488069"><div style="position: relative; width: 170px; height: 56px; padding: 0px; margin: 0px; border-width: 0px; cursor: default;"><canvas data-zr-dom-id="zr_0" width="255" height="135" style="position: absolute; left: 0px; top: 0px; width: 170px; height: 90px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); padding: 0px; margin: 0px; border-width: 0px;"></canvas></div></div>
        <div class="lui_fssc_financial_card_pending">
            <span><a id="avg">${lfn:message('fssc-cashier:portlet.doc.view.payMentAvgDay')}</a><span id="todaySumId"></span></span>
        </div>
    </div>
</div>
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('main1'));
    $(function(){
        $.get('${LUI_ContextPath}/fssc/cashier/fssc_cashier_portlet/fsscCashierPortle.do?method=getPayMentPortletData', function (data) {
            $("#sum").html("￥"+data.sum);
            $("#todaySumId").html("￥"+data.todaySum);
            myChart1.setOption({
                grid:{
                    y:40,
                    y2:40,
                    borderWidth:1
                },
                xAxis: {
                    type: 'category',
                    data: data.monthe,
                    show: false,
                },
                yAxis: {
                    type: 'value',
                    show: false,
                },
                series: [{
                    data: data.money,
                    type: 'line',
                    smooth: true,
                    showSymbol: false,
                    itemStyle: {
                        color: '#4285f4'
                    },
                    areaStyle: {
                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                            offset: 0,
                            color: 'rgba(66,133,244,0.8)'
                        }, {
                            offset: 1,
                            color: 'rgba(255,255,255,0.1)'
                        }])
                    },
                }],
                tooltip : {
                    trigger: 'axis',
                    formatter:'{b}月:{c}元',
                    confine: true
                }
            })
        }, 'json')
    })
</script>