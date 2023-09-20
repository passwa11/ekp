<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="${LUI_ContextPath}/kms/lservice/index/admin/echart/resource/echarts.min.js" type="text/javascript" charset="utf-8"></script> 
<script>
function generateBarChart(arr) {
	var	learnOption = {
		    series: [{
		        type: 'gauge',
		        startAngle: -90,
		        endAngle: 270,
		        color:'#4285F4',
		        radius:'60%',
		        pointer: {
		            show: false
		        },
		        progress: {
		            show: true,
		            overlap: false,
		            roundCap: true,
		            clip: false,
		            itemStyle: {
		                borderWidth: 1
		            }
		        },
		        axisLine: {

		            lineStyle: {
		                width: 5
		            }
		        },
		        splitLine: {
		            show: false,
		            distance: 0,
		            length: 10
		        },
		        axisTick: {
		            show: false
		        },
		        axisLabel: {
		            show: false,
		            distance: 50
		        },
		        data: [{
		            value: arr.learnActiRate,
		            name: '已完成',
		            title: {
		                offsetCenter: ['1%', '35%']
		            },
		            detail: {
		                offsetCenter: ['0%', '-20%']
		            }
		        }
		        ],
		        title: {
		            fontSize: 12,
		            color:'#999999'
		        },
		        detail: {
		            width: 50,
		            height: 14,
		            fontSize: 28,
		            color: '#4285F4',
		            borderColor: 'auto',
		            borderRadius: 20,
		            borderWidth: 0,
		            formatter: '{value}%'
		        }
		    }]
		};
	var	examOption = {
		    series: [{
		        type: 'gauge',
		        startAngle: -90,
		        endAngle: 270,
		        color:'#4285F4',
		        radius:'60%',
		        pointer: {
		            show: false
		        },
		        progress: {
		            show: true,
		            overlap: false,
		            roundCap: true,
		            clip: false,
		            itemStyle: {
		                borderWidth: 1
		            }
		        },
		        axisLine: {

		            lineStyle: {
		                width: 5
		            }
		        },
		        splitLine: {
		            show: false,
		            distance: 0,
		            length: 10
		        },
		        axisTick: {
		            show: false
		        },
		        axisLabel: {
		            show: false,
		            distance: 50
		        },
		        data: [{
		            value: arr.examFinishRate,
		            name: '已完成',
		            title: {
		                offsetCenter: ['1%', '35%']
		            },
		            detail: {
		                offsetCenter: ['0%', '-20%']
		            }
		        }
		        ],
		        title: {
		            fontSize: 12,
		            color:'#999999'
		        },
		        detail: {
		            width: 50,
		            height: 14,
		            fontSize: 28,
		            color: '#4285F4',
		            borderColor: 'auto',
		            borderRadius: 20,
		            borderWidth: 0,
		            formatter: '{value}%'
		        }
		    }]
		};
	var	trainOption = {
		    series: [{
		        type: 'gauge',
		        startAngle: -90,
		        endAngle: 270,
		        color:'#4285F4',
		        radius:'60%',
		        pointer: {
		            show: false
		        },
		        progress: {
		            show: true,
		            overlap: false,
		            roundCap: true,
		            clip: false,
		            itemStyle: {
		                borderWidth: 1
		            }
		        },
		        axisLine: {

		            lineStyle: {
		                width: 5
		            }
		        },
		        splitLine: {
		            show: false,
		            distance: 0,
		            length: 10
		        },
		        axisTick: {
		            show: false
		        },
		        axisLabel: {
		            show: false,
		            distance: 50
		        },
		        data: [{
		            value: arr.trainPlanRate,
		            name: '已完成',
		            title: {
		                offsetCenter: ['1%', '35%']
		            },
		            detail: {
		                offsetCenter: ['0%', '-20%']
		            }
		        }
		        ],
		        title: {
		            fontSize: 12,
		            color:'#999999'
		        },
		        detail: {
		            width: 50,
		            height: 14,
		            fontSize: 28,
		            color: '#4285F4',
		            borderColor: 'auto',
		            borderRadius: 20,
		            borderWidth: 0,
		            formatter: '{value}%'
		        }
		    }]
		};
	var	lmapOption = {
		    series: [{
		        type: 'gauge',
		        startAngle: -90,
		        endAngle: 270,
		        color:'#4285F4',
		        radius:'60%',
		        pointer: {
		            show: false
		        },
		        progress: {
		            show: true,
		            overlap: false,
		            roundCap: true,
		            clip: false,
		            itemStyle: {
		                borderWidth: 1
		            }
		        },
		        axisLine: {

		            lineStyle: {
		                width: 5
		            }
		        },
		        splitLine: {
		            show: false,
		            distance: 0,
		            length: 10
		        },
		        axisTick: {
		            show: false
		        },
		        axisLabel: {
		            show: false,
		            distance: 50
		        },
		        data: [{
		            value: arr.lmapFinishRate,
		            name: '已完成',
		            title: {
		                offsetCenter: ['1%', '35%']
		            },
		            detail: {
		                offsetCenter: ['0%', '-20%']
		            }
		        }
		        ],
		        title: {
		            fontSize: 12,
		            color:'#999999'
		        },
		        detail: {
		            width: 50,
		            height: 14,
		            fontSize: 28,
		            color: '#4285F4',
		            borderColor: 'auto',
		            borderRadius: 20,
		            borderWidth: 0,
		            formatter: '{value}%'
		        }
		    }]
		};
	var learnFlag =arr.learnFlag; // 学习任务
	if(learnFlag){
		var learnChart = echarts.init(document.getElementById('appBarlearn'));
		learnChart.setOption(learnOption, true);
	}
	var examFlag =arr.examFlag; // 考试任务
	if(examFlag) {
		var examChart = echarts.init(document.getElementById('appBarexam'));
		examChart.setOption(examOption, true);
	}
	var trainFlag = arr.trainFlag; // 培训任务
	if(trainFlag) {
		var trainChart = echarts.init(document.getElementById('appBartrain'));
		trainChart.setOption(trainOption, true);
	}
	var lmapFlag = arr.lmapFlag; // 学习地图
	if(lmapFlag) {
		var lmapChart = echarts.init(document.getElementById('appBarlmap'));
		lmapChart.setOption(lmapOption, true);
	}
     document.getElementById('chart_main').setAttribute('style','display:flex;')
};

seajs.use(['lui/jquery'], function($){
	$(function(){
		$.ajax({
			url: '${LUI_ContextPath}/kms/lservice/kmsLservicePortletAction.do?method=getPieInfo',
			type: 'POST',
			dataType: 'json',
			async: false,
			success: function(data) {
				generateBarChart(data);
			}			
		});
	});
})
</script>