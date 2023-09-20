<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
<script type="text/javascript" src="<c:url value="/sys/ui/js/chart/echarts/echarts4.2.1.js"/>?s_cache=${LUI_Cache}"></script>


<div>
	<div class="lui_hr_platform_block_3 lui_hr_platform_component lui_hr_platform_block_echarts_container">
                <div class="lui_hr_platform_block_echarts_wrap lui_hr_platform_block_echarts_wrap_4">
                    <!-- 在职员工性别分布 - 人物图 -->
                    <div class="lui_hr_platform_block_echarts_item" id="main">
                        <div id="hr_echart_1">
                        </div>
                    </div>
                </div>
            </div>
</div>
<script>


$(function () {
	var M = ${data.sex.M};
	var F = ${data.sex.F};
	var unknown = ${data.sex.unknown};
	// 基于准备好的dom，初始化echarts实例
	  // 在职员工性别分布 - 异形图
	  var myChart1 = echarts.init(document.getElementById('hr_echart_1'));
	
	  // 指定图表的配置项和数据
	  var symbols = [
	    'path://M41.5999714,75.1413427 L41.5999714,87.7170615 C41.5999714,92.8445526 37.4363244,96.999998 32.3001563,96.999998 L19.6937273,96.999998 C14.5609397,97.003354 10.3972642,92.847239 10.3939116,87.7170615 L10.3939116,75.1413388 L9.29981461,75.1413388 C4.16702806,75.1446955 0.0033525739,70.9885798 2.30058591e-09,65.8584023 L0,52.0676348 C-3.62329413e-10,39.327885 10.3331385,29 23.0854471,29 L28.9145529,29 C41.6607832,29 52,39.327885 52,52.07371 L52,65.8583988 C52,70.9858899 47.836353,75.1413353 42.7001848,75.1413353 L41.6000095,75.1413353 L41.5999714,75.1413427 Z M10,14.5 C10,6.49187031 16.9395874,2.68415642e-15 25.5,0 C34.060422,0 41,6.49187975 41,14.5 C41,22.5081297 34.0604126,28.9999988 25.5,29 C16.9395874,29 10.0000013,22.5081202 10,14.4999978 Z',
	    'path://M45.1955763,81.5447051 L40.7079696,91.5011303 C39.2039488,94.8503865 35.8657564,97 32.1790808,97 L22.8309287,97 C19.1487713,97.0000514 15.8095757,94.8471609 14.3020395,91.5011303 L9.82054665,81.5447051 L9.34977592,81.5447051 C4.18693645,81.5455962 0.000898120243,77.3776786 1.40355573e-07,72.2353905 C-0.000137582055,71.4437871 0.10107924,70.6553979 0.301200745,69.8893031 L6.49458713,46.1887485 C9.50793011,34.632865 21.3560582,27.6980969 32.9580539,30.6994871 C40.5787984,32.670905 46.5299691,38.5983843 48.5092505,46.1887503 L54.696523,69.889305 C55.9965212,74.8659014 52.9999145,79.94992 48.0034207,81.244712 C47.2342598,81.4440365 46.4427221,81.5448501 45.6479526,81.5447131 L45.1894097,81.5447131 L45.1955763,81.5447051 Z M12,15 C12,6.715725 18.7157344,2.66453431e-15 27,0 C35.284275,0 42,6.71573438 42,15 C42,23.284275 35.2842656,29.9999987 27,30 C18.715725,30 12.0000013,23.2842656 12,14.9999977 Z',
	    'path://M41.5999714,75.1413427 L41.5999714,87.7170615 C41.5999714,92.8445526 37.4363244,96.999998 32.3001563,96.999998 L19.6937273,96.999998 C14.5609397,97.003354 10.3972642,92.847239 10.3939116,87.7170615 L10.3939116,75.1413388 L9.29981461,75.1413388 C4.16702806,75.1446955 0.0033525739,70.9885798 2.30058591e-09,65.8584023 L0,52.0676348 C-3.62329413e-10,39.327885 10.3331385,29 23.0854471,29 L28.9145529,29 C41.6607832,29 52,39.327885 52,52.07371 L52,65.8583988 C52,70.9858899 47.836353,75.1413353 42.7001848,75.1413353 L41.6000095,75.1413353 L41.5999714,75.1413427 Z M10,14.5 C10,6.49187031 16.9395874,2.68415642e-15 25.5,0 C34.060422,0 41,6.49187975 41,14.5 C41,22.5081297 34.0604126,28.9999988 25.5,29 C16.9395874,29 10.0000013,22.5081202 10,14.4999978 Z',
	  ];

	  var bodyMax = 100; //指定图形界限的值
	  var labelSetting = {
	    normal: {
	      show: true,
	      position: 'bottom',
	      offset: [0, 0],
	      formatter: function (param) {
	        return '\n' + param.name + '\n\n' + (param.value / bodyMax * 100).toFixed(0) + '%';
	      },
	      textStyle: {
	        fontSize: 12,
	        fontFamily: 'PingFangSC',
	        color: '#666'
	      }
	    }
	  };

	  var option1 = {
	    color: ['#2270F1', '#F17474', '#2270F1'],
	    title: {
	      "text": "${lfn:message('hr.staff:porlet.echarts.gender')}",
	      left: 20,
	      top: 20,
	      "textStyle": {
	        "fontSize": 14,
	        "fontWeight": 'normal'
	      }
	    },
	    tooltip: {
	      show: false, //鼠标放上去显示悬浮数据
	    },
	    legend: {
	      data: ['男', '女', '未知'],
	      itemGap: 30
	    },
	    grid: {
	      top: '25%',
	      containLabel: false
	    },
	    xAxis: {
	      data: ['男', '女', '未知'],
	      axisTick: {
	        show: false
	      },
	      axisLine: {
	        show: false
	      },
	      axisLabel: {
	        show: false
	      }
	    },
	    yAxis: {
	      max: 100,
	      splitLine: {
	        show: false
	      },
	      axisTick: {
	        // 刻度线
	        show: false
	      },
	      axisLine: {
	        // 轴线
	        show: false
	      },
	      axisLabel: {
	        // 轴坐标文字
	        show: false
	      }
	    },
	    series: [{
	        name: '',
	        type: 'pictorialBar',
	        symbolClip: true,
	        symbolBoundingData: bodyMax,
	        label: labelSetting,
	        center: ['50%', '50%', '50%'],
	        data: [{
	            value: M,
	            symbol: symbols[0],
	            itemStyle: {
	              normal: {
	                color: '#2270F1' //单独控制颜色
	              }
	            },
	          },
	          {
	            value: F,
	            symbol: symbols[1],
	            itemStyle: {
	              normal: {
	                color: '#F17474' //单独控制颜色
	              }
	            },
	          },
	          {
	        	  value: unknown,
		          symbol: symbols[2],
		          itemStyle: {
		             normal: {
		                color: '#2270F1' //单独控制颜色
		             }
		          }
	          }
	        ],
	        z: 10
	      },
	      {
	        // 设置背景底色，不同的情况用这个
	        name: 'full',
	        type: 'pictorialBar', //异型柱状图 图片、SVG PathData
	        symbolBoundingData: bodyMax,
	        animationDuration: 0,
	        itemStyle: {
	          normal: {
	            color: '#fff' //设置全部颜色，统一设置
	          }
	        },
	        z: 10,
	        data: [{
	            itemStyle: {
	              normal: {
	                color: 'rgba(34,112,241,0.40)' //单独控制颜色
	              }
	            },
	            value: 100,
	            symbol: symbols[0]
	          },
	          {
	            itemStyle: {
	              normal: {
	                color: 'rgba(241,116,116,0.40)' //单独控制颜色
	              }
	            },
	            value: 100,
	            symbol: symbols[1]
	          },
	          {
	        	  itemStyle: {
	              normal: {
	                color: 'rgba(34,112,241,0.40)' //单独控制颜色
	               }
	              },
	              value: 100,
	              symbol: symbols[2] 
	          }
	        ]
	      }
	    ]
	  }
	  // 使用刚指定的配置项和数据显示图表。
	  myChart1.setOption(option1);
	  // 性别分布 Ends

	})
</script>