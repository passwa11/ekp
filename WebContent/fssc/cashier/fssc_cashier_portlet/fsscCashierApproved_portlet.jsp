<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<title>
    ${ lfn:message('fssc-pres:portlet.doc.view.flat') }
</title>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/fssc/common/resource/css/custom.css">
</head>
<style>
    #approveDavg{
        width: 96px;
        height: 12px;
        font-family: PingFangSC-Regular;
        font-size: 12px;
        color: rgba(0,0,0,0.65);
        line-height: 12px;
        margin-right:10px;
        font-weight: 400;
    }

    #approveDavg{
        margin-right: 10px;
    }
</style>
<div class="lui_fssc_financial_card_portlet_container">
    <div class="lui_fssc_financial_card">
        <div class="lui_fssc_financial_card_items_header">
            <i class="lui_fssc_financial_card_icons financial_primary">
                <span class="lui_fssc_financial_card_icon"></span>
            </i>
            <span class="lui_fssc_financial_card_title">${lfn:message('fssc-cashier:fssCashierApproved.title')}</span>
            <span class="lui_fssc_financial_card_detail">
                                <div class="lui_fssc_financial_card_detail_wrapper">
                                    <i class="lui_fssc_financial_card_detail_icon">
                                    </i>
                                    <div class="lui_fssc_financial_card_tips">
                                        <span>${lfn:message('fssc-cashier:fssCashierApproved.average.throughput')}</span>
                                    </div>
                                </div>
                            </span>
        </div>
        <div class="lui_fssc_financial_card_content">
            <span id="approveDsum"></span><span id="approveDsumId"></span>
        </div>
        <div id="main" style="width: 170px; height: 56px; -webkit-tap-highlight-color: transparent; user-select: none;" _echarts_instance_="ec_1630915448456"><div style="position: relative; width: 170px; height: 56px; padding: 0px; margin: 0px; border-width: 0px; cursor: default;"><canvas data-zr-dom-id="zr_0" width="255" height="135" style="position: absolute; left: 0px; top: 0px; width: 170px; height: 90px; user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0); padding: 0px; margin: 0px; border-width: 0px;"></canvas></div></div>
        <div class="lui_fssc_financial_card_pending">
            <span><a id="approveDavg">${lfn:message('fssc-cashier:fssCashierrApproved.average.throughputByDay')}</a><span id="approveDavgId"></span></span>
        </div>
    </div>
</div>
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var cMyChart = echarts.init(document.getElementById('main'));
    $(function(){
        $.get('${LUI_ContextPath}/fssc/cashier/fssc_cashier_portlet/fsscCashierPortle.do?method=getApprovedPortletDataB', function (data) {
            $("#approveDsum").html(data.cApproved);
            $("#approveDavgId").html(data.cApprovedDay);
            cMyChart.setOption({
                grid:{
                    y:40,
                    y2:40,
                    borderWidth:1
                },
                xAxis: {
                    data: data.cEcharts.x,
                    show: false,
                },
                yAxis: {
                    show: false,
                },
                series: [{
                    type: 'bar',
                    barWidth: 10,
                    data: data.cEcharts.y,
                    itemStyle: {
                        color: '#4285F4',
                        borderRadius: [5, 5, 0, 0],
                    },

                }],
                tooltip : {
                    trigger: 'axis',
                    formatter:'{b}月:{c}单',
                }
            })
        }, 'json')
    })
</script>