define(['dojo/_base/declare',
        "dojo/dom-geometry",
        "dojo/dom-style",
        "dojo/window"
        ],function(declare,domGeometry,domStyle,win){
	return declare('mui.chart.chartDarkMixin',null,{
		
		//默认颜色序列,覆盖Echarts3默认的,与PC端保持一致
		colors : ['#dd6b66','#759aa0','#e69d87','#8dc1a9','#ea7e53','#eedd78','#73a373','#73b9bc','#7289ab', '#91ca8c','#f49f42'],
		
		concatDefaultOptions : function(options){
			this.defaultColor(options);
			this.defaultXAxis(options);
		},
		
		//x轴默认设置(适配移动端)
		defaultXAxis : function(options){
			var xAxis = options.xAxis;
			if(xAxis instanceof Array){
				for(var i = 0;i < xAxis.length;i++){
					if(!options.xAxis[i].axisLabel) 
						options.xAxis[i].axisLabel={};
					//x轴显示全 
					// 数据量太多的时候，不能全部都显示出来 by zhugr 2018-08-08
					//options.xAxis[i].axisLabel.interval = 0;
					//x轴默认倾斜角度
					if(!options.xAxis[i].axisLabel.rotate){
						options.xAxis[i].axisLabel.rotate = 30;
					}
				}
			}
		},
		
		//默认颜色序列设置(与PC端保持一致)
		defaultColor : function(options){
			if(!options.color){
				options.color = this.colors;
			}
		},
		
		registerTheme:function(echarts){
			var contrastColor = '#eee';
		    var axisCommon = function () {
		        return {
		            axisLine: {
		                lineStyle: {
		                    color: contrastColor
		                }
		            },
		            axisTick: {
		                lineStyle: {
		                    color: contrastColor
		                }
		            },
		            axisLabel: {
		                textStyle: {
		                    color: contrastColor
		                }
		            },
		            splitLine: {
		                lineStyle: {
		                    type: 'dashed',
		                    color: '#aaa'
		                }
		            },
		            splitArea: {
		                areaStyle: {
		                    color: contrastColor
		                }
		            }
		        };
		    };

		    var colorPalette = this.colors;
		    var theme = {
		        color: colorPalette,
		        backgroundColor: '#333',
		        tooltip: {
		            axisPointer: {
		                lineStyle: {
		                    color: contrastColor
		                },
		                crossStyle: {
		                    color: contrastColor
		                }
		            }
		        },
		        legend: {
		            textStyle: {
		                color: contrastColor
		            }
		        },
		        textStyle: {
		            color: contrastColor
		        },
		        title: {
		            textStyle: {
		                color: contrastColor
		            }
		        },
		        toolbox: {
		            iconStyle: {
		                normal: {
		                    borderColor: contrastColor
		                }
		            }
		        },
		        dataZoom: {
		            textStyle: {
		                color: contrastColor
		            }
		        },
		        timeline: {
		            lineStyle: {
		                color: contrastColor
		            },
		            itemStyle: {
		                normal: {
		                    color: colorPalette[1]
		                }
		            },
		            label: {
		                normal: {
		                    textStyle: {
		                        color: contrastColor
		                    }
		                }
		            },
		            controlStyle: {
		                normal: {
		                    color: contrastColor,
		                    borderColor: contrastColor
		                }
		            }
		        },
		        timeAxis: axisCommon(),
		        logAxis: axisCommon(),
		        valueAxis: axisCommon(),
		        categoryAxis: axisCommon(),

		        line: {
		            symbol: 'circle'
		        },
		        graph: {
		            color: colorPalette
		        },
		        gauge: {
		            title: {
		                textStyle: {
		                    color: contrastColor
		                }
		            }
		        },
		        candlestick: {
		            itemStyle: {
		                normal: {
		                    color: '#FD1050',
		                    color0: '#0CF49B',
		                    borderColor: '#FD1050',
		                    borderColor0: '#0CF49B'
		                }
		            }
		        }
		    };
		    theme.categoryAxis.splitLine.show = false;
		    echarts.registerTheme('dark', theme);
		}
		
	});
	
});