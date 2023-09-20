<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="<c:url value="/sys/ui/js/chart/echarts/echarts4.2.1.js"/>?s_cache=${LUI_Cache}"></script>


<div>
	<div class="lui_hr_platform_block_3 lui_hr_platform_component lui_hr_platform_block_echarts_container">
                <div class="lui_hr_platform_block_echarts_wrap lui_hr_platform_block_echarts_wrap_4">
                    <!-- 员工学历分析 - 柱状图 -->
                    <div class="lui_hr_platform_block_echarts_item">
                        <div id="hr_echart_2" >
                       	</div>
                    </div>
                </div>
            </div>
</div>
<script>


$(function () {
	  // 员工学历分析 - 柱形图 Starts
	  var myChart2 = echarts.init(document.getElementById('hr_echart_2'));

	  var option2 = {
	    color: ['#FFAA8A', '#69A0FD', '#2270F1'],
	    title: {
	      text: "${lfn:message('hr.staff:porlet.echarts.entryAndleave')}",
	      left: 20,
	      top: 20,
	      "textStyle": {
	        "fontSize": 14,
	        "fontWeight": 'normal'
	      }
	    },
	    dataset: {
	      source: [{
	    	'': '',
	    	'在职': ${data.status.onthejob},
	        '离职': ${data.status.leave},
	        '入职': ${data.status.entry} 
	       
	      }]
	    },
	    legend: {
	      itemWidth: 10, //图例的宽度
	      itemHeight: 10, //图例的高度
	      top: 50,
	      left: 20,
	      orient: 'horizontal',
	      icon: 'rect',
	    },
	    tooltip: {
	      trigger: 'axis',
	      axisPointer: { // 坐标轴指示器，坐标轴触发有效
	        type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
	      }
	    },
	    grid: {
	      top: 90,
	      bottom: 20
	    },
	    xAxis: {
	      type: 'category',
	      axisLine: {
	        lineStyle: {
	          color: '#fff'
	        }
	      }
	    },
	    yAxis: {
	      type: 'value',
	      splitLine: false,
	      axisTick: {
	        show: false
	      },
	      axisLabel: {
	        color: '#999'
	      },
	      axisLine: {
	        onZero: false,
	        lineStyle: {
	          color: '#d8d8d8'
	        }
	      }
	    },
	    series: [{
	        type: 'bar',
	        barWidth: 40,
	      }, {
	        type: 'bar',
	        barWidth: 40,
	      },
	      {
	        type: 'bar',
	        barWidth: 40,
	      }
	    ]
	  };
	  myChart2.setOption(option2);

	})
</script>