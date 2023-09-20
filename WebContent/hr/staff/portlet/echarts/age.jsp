<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="<c:url value="/sys/ui/js/chart/echarts/echarts4.2.1.js"/>?s_cache=${LUI_Cache}"></script>


<div>
	<div class="lui_hr_platform_block_3 lui_hr_platform_component lui_hr_platform_block_echarts_container">
                <div class="lui_hr_platform_block_echarts_wrap lui_hr_platform_block_echarts_wrap_4">
                    <!-- 员工年龄分析 - 饼图 -->
                    <div class="lui_hr_platform_block_echarts_item">
                        <div id="hr_echart_3"></div>
                        
                        </div>
                   
                </div>
            </div>
</div>
<script>


$(function () {
	// 基于准备好的dom，初始化echarts实例
	  // 员工年龄分析 - 饼图 Starts
	  var myChart3 = echarts.init(document.getElementById('hr_echart_3'));

	  var option3 = {

	    title: {
	      text: '${lfn:message("hr.staff:porlet.echarts.age")}',
	      x: 'left',
	      left: 20,
	      top: 20,
	      "textStyle": {
	        "fontSize": 14,
	        "fontWeight": 'normal'
	      }
	    },
	    color: [
	      '#FFAA8A',
	      '#2270F1',
	      '#6672FF',
	      '#4E90FE',
	      '#69A0FD',
	      '#F17474'
	    ],
	    tooltip: {
	      trigger: 'item',
	      formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	      top: '40',
	      left: 'left',
	      itemWidth: 10, //图例的宽度
	      itemHeight: 10, //图例的高度
	      itemGap: 20,
	      top: 50,
	      left: 20,
	      orient: 'horizontal',
	      icon: 'rect',
	    },
	    series: [{
	      name: '${lfn:message("hr.staff:porlet.echarts.age")}',
	      type: 'pie',
	      radius: '55%',
	      center: ['50%', '65%'],
	      label: {
	        show: true,
	        formatter: '{b}: {d}%'
	      },
	      data: [
	        {
	          value: '${data.age.age30}',
	          name: '30以下'
	        },
	        {
	          value: '${data.age.age40}',
	          name: '30-40'
	        },
	        {
	          value: '${data.age.age50}',
	          name: '40-50'
	        },
	        {
	          value: '${data.age.age60}',
	          name: '50-60'
	        },
	        {
	          value: '${data.age.ageA}',
	          name: '60以上'
	        },
	      ],
	      itemStyle: {
	        emphasis: {
	          shadowBlur: 10,
	          shadowOffsetX: 0,
	          shadowColor: 'rgba(0, 0, 0, 0.5)'
	        }
	      }
	    }]
	  };
	  myChart3.setOption(option3);
	  // 员工年龄分析 Ends


	  // 窗口缩放
	  var chartContainer = document.getElementById('main');
	  var resizeChartContainer = function () {
	    chartContainer.style.height = window.innerWidth*400/312+'px';
	  };
	  window.onresize = function () {
	    myChart3.resize();
	  };

	})
</script>