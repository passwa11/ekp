<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
    <script type="text/javascript" src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="${LUI_ContextPath}/fssc/common/resource/js/Number.js?s_cache=${LUI_Cache}"></script>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="lui_fssc_iframe">
    <div class="lui_fssc_iframe_left">
        <div class="lui_fssc_financial_card_portlet">
            <div class="lui_fssc_budget">
                <div class="lui_fssc_budget_header">
                    <span>${lfn:message("fssc-budget:fssc.budget.portlet.use")}</span>
                    <span><select id="budget_cost_center" onchange="getCostCenterAcountInfo();"></select></span>
                </div>
                <div class="lui_fssc_budget_detail">
                    <div class="lui_fssc_budget_detail_left">
                        <div id="budget_total"  style="width: 300px; height: 300px;"></div>
                    </div>
                    <div class="lui_fssc_budget_detail_right">
                        <div id="budget_detail"  style="width: 700px; height: 300px;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        getCostCenterAcountInfo();
    });
    function getCostCenterAcountInfo(){
        var fdCostCenterId=$("#budget_cost_center").val()||'';
        $.ajax({
            url:'${LUI_ContextPath}/fssc/budget/fssc_budget_portlet/fsscBudgetPortlet.do?method=getCostCenterAcountInfo&fdCostCenterId='+fdCostCenterId,
            async:false,
            success:function(rtn){
                rtn = JSON.parse(rtn);
                if(rtn.costCenterInfo){
                    var selectHtml="";
                    for (var i=0;i<rtn["costCenterInfo"].length;i++) {
                        var cost_center=rtn["costCenterInfo"][i];
                        for(var id in cost_center){//遍历json对象的每个key/value对,p为key
                            selectHtml+='<option value="'+id+'">'+cost_center[id]+'</option>';
                        }
                    }
                    if(selectHtml){
                        $("#budget_cost_center").html(selectHtml);
                    }
                }else{
                    if(!$("#budget_cost_center").val()||''){//重新选择有成本中心ID，不做隐藏
                        $("#budget_cost_center").hide();
                    }
                }
                // 基于准备好的dom，初始化echarts实例
                var budgetTotalChart = echarts.init(document.getElementById('budget_total'));  //部门预算使用管理
                var budgetDetail = echarts.init(document.getElementById('budget_detail'));
                var optionBudgetTotal = {
                    tooltip: {
                        trigger: 'item'
                    },
                    color:['#E6EBF8','#4286F4'],
                    title: {
                        text:  parseInt(rtn['totalAcount']/10000)+'万',  //图形标题，配置在中间对应效果图的80%
                        subtext:'部门预算总额',
                        left: "center",
                        top: "40%",
                        textStyle: {
                            color: "rgb(72,72,72)",
                            fontSize: 36,
                            align: "center"
                        },
                        subtextStyle: {
                            color: "rgb(72,72,72)",
                            fontSize: 14,
                            align: "center"
                        },
                    },
                    series: [
                        {
                            name: '部门预算使用情况',
                            type: 'pie',
                            radius: ['60%', '70%'],
                            avoidLabelOverlap: false,
                            label: {
                                show: false,
                                position: 'center'
                            },
                            labelLine: {
                                show: false
                            },
                            data: [
                                {
                                    value: rtn["usedAcount"],
                                    name: '预算使用',
                                    label: {
                                        normal: {
                                            show: false
                                        }
                                    }
                                },
                                {
                                    value: rtn["canUse"],
                                    name: '剩余预算',
                                    label: {
                                        normal: {
                                            show: false

                                        }
                                    }
                                }
                            ],
                            detail: {
                                fontSize: 14,
                                color: '#666666',
                                formatter: '部门预算总额'
                            }
                        }
                    ]
                }
                var x_data=[],y_data=[];
                if(rtn["itemData"]){
                    for (var i=0;i< rtn["itemData"].length;i++) {
                        x_data.push(rtn["itemData"][i][0]);
                        y_data.push(rtn["itemData"][i][1]);
                    }
                }
                var optionBudgetDetail = {
                    tooltip: {
                        trigger: 'axis',
                        formatter:'{b}:{c}元'
                    },
                    xAxis: {
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            interval: 0,
                            // rotate:40
                        },
                        type: 'category',

                        data: x_data
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: y_data,

                        type: 'bar',
                        showBackground: true,
                        backgroundStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                                offset: 0,
                                color: '#B8D1FB'
                            },
                                {
                                    offset: 1,
                                    color: '#ffffff'
                                }
                            ]),
                            borderRadius: [8, 8, 0, 0],
                        },
                        itemStyle: {
                            color: '#4285F4',
                            borderRadius: [8, 8, 0, 0],
                            width: 8,
                        },
                    }],
                    barWidth: 8,
                }
                // 使用刚指定的配置项和数据显示图表。
                budgetTotalChart.setOption(optionBudgetTotal);
                budgetDetail.setOption(optionBudgetDetail);

            }
        });
    }
</script>
</body>