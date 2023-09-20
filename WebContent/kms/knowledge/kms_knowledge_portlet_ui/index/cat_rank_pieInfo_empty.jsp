<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{
    tooltip: {
        trigger: 'axis'
    },
    grid: {
        right: '10px',
        left: '40px'
    },
    toolbox: {
        show: false
    },
	xAxis: [
				{
					type: 'category',
					splitLine: {
						show:false
					},
					axisTick: {
						show: false
					},
                    axisLine: {
                        lineStyle: {
                            color: '#999999',
                            width: 1, //这里是为了突出显示加上的  
                        }
                    },
					data:['${lfn:message('kms-knowledge:kms.knowledge.list.new.null')}']
				}
	],
    yAxis : [
        {
            type: 'value',
            name: '${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.num')}',
            position: 'left',
			splitLine :{    //网格线
				lineStyle:{
					color: '#eeeeee',
					type:'dashed'    //设置网格线类型 dotted：虚线   solid:实线
				},
				show:true //隐藏或显示
			},
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#999999'
                }
            },
            axisLabel: {
                formatter: '{value}',
                textStyle: {
                    color: '#999999'
                }
            }
        }
    ],
        series: [
    {
        name:"${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.num')}",
        type:'bar',
        yAxisIndex: 0,
        itemStyle: {
            normal: {
                color: function(params) {
                    //注意，如果颜色太少的话，后面颜色不会自动循环，最好多定义几个颜色
                    var colorList = ['#2C9DFF','#3ca6fc', '#4babfa', '#59b0f7', '#6bb8f7','#7dc2fa', '#8cc9fa', '#9bcffa', '#a8d5fa', '#73EAFF'];
                    if (params.dataIndex >= colorList.length) {  //给大于颜色数量的柱体添加循环颜色的判断
                        index = params.dataIndex - colorList.length;
                    }
                    return colorList[params.dataIndex]
                }
            }
        },
        data:[0]
    }
]
}