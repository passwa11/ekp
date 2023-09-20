<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
    <script type="text/javascript" src="${LUI_ContextPath}/fssc/common/resource/js/Number.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts.js?s_cache=${LUI_Cache}"></script>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="lui_fssc_financial_card_portlet_container">
    <div class="lui_fssc_financial_card">
        <div class="lui_fssc_financial_card_items_header">
            <i class="lui_fssc_financial_card_icons financial_primary">
                <span class="lui_fssc_financial_card_icon"></span>
            </i>
            <span class="lui_fssc_financial_card_title">${lfn:message('fssc-common:portlet.bill.approved')}</span>
            <span class="lui_fssc_financial_card_detail">
                                <div class="lui_fssc_financial_card_detail_wrapper">
                                    <i class="lui_fssc_financial_card_detail_icon">
                                    </i>
                                    <div class="lui_fssc_financial_card_tips">
                                        <span>${lfn:message('fssc-common:portlet.bill.approved')}</span>
                                    </div>
                                </div>

                            </span>
        </div>
        <div class="lui_fssc_financial_card_content">
            <span id="approveedNum" style="margin-left:0.5rem;"></span>
        </div>
        <div id="approved_main" style="width: 170px; height: 56px;"></div>
        <div class="lui_fssc_financial_card_pending">
            ${lfn:message('fssc-common:portlet.bill.approved.current.day')}
            <span id="avageNum" style="margin-left:0.5rem;"></span>
        </div>
    </div>
</div>
    <script type="text/javascript">
        $(document).ready(function(){
            $.ajax({
                url:'${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=getApprovedQuantity',
                async:false,
                success:function(rtn){
                    rtn = JSON.parse(rtn);
                    $("#approveedNum").eq(0).html(rtn["approvedTotal"]);
                    $("#avageNum").eq(0).html(rtn["avageNum"]);
                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('approved_main'));
                    var x_data=[],y_data=[];
                    for (var prop in rtn["monthData"]) {
                        if (rtn["monthData"].hasOwnProperty(prop)) {
                            x_data.push(prop);
                            y_data.push(rtn["monthData"][prop]);
                        }
                    }
                    // 指定图表的配置项和数据
                    var option = {
                        tooltip: {
                            trigger: 'axis',
                            formatter:'{b}月:{c}单'
                        },
                        color: ['#50C45E'],
                        title: {
                            text: ''
                        },
                        xAxis: {
                            data: x_data,
                            show:false
                        },
                        yAxis: {
                            show:false
                        },
                        series: [{
                            type: 'line',
                            data: y_data
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        });
    </script>
</body>