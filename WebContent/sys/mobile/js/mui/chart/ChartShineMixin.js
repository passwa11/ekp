define(['dojo/_base/declare',
        "dojo/dom-geometry",
        "dojo/dom-style",
        "dojo/window"
        ],function(declare,domGeometry,domStyle,win){
	return declare('mui.chart.chartShineMixin',null,{
		
		//默认颜色序列,覆盖Echarts3默认的,与PC端保持一致
		colors : [
                  '#c12e34','#e6b600','#0098d9','#2b821d',
                  '#005eaa','#339ca8','#cda819','#32a487'
              ],
		
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
			var colorPalette = this.colors;

			                var theme = {

			                    color: colorPalette,

			                    title: {
			                        textStyle: {
			                            fontWeight: 'normal'
			                        }
			                    },

			                    visualMap: {
			                        color:['#1790cf','#a2d4e6']
			                    },

			                    toolbox: {
			                        iconStyle: {
			                            normal: {
			                                borderColor: '#06467c'
			                            }
			                        }
			                    },

			                    tooltip: {
			                        backgroundColor: 'rgba(0,0,0,0.6)'
			                    },

			                    dataZoom: {
			                        dataBackgroundColor: '#dedede',
			                        fillerColor: 'rgba(154,217,247,0.2)',
			                        handleColor: '#005eaa'
			                    },

			                    timeline: {
			                        lineStyle: {
			                            color: '#005eaa'
			                        },
			                        controlStyle: {
			                            normal: {
			                                color: '#005eaa',
			                                borderColor: '#005eaa'
			                            }
			                        }
			                    },

			                    candlestick: {
			                        itemStyle: {
			                            normal: {
			                                color: '#c12e34',
			                                color0: '#2b821d',
			                                lineStyle: {
			                                    width: 1,
			                                    color: '#c12e34',
			                                    color0: '#2b821d'
			                                }
			                            }
			                        }
			                    },

			                    graph: {
			                        color: colorPalette
			                    },

			                    map: {
			                        label: {
			                            normal: {
			                                textStyle: {
			                                    color: '#c12e34'
			                                }
			                            },
			                            emphasis: {
			                                textStyle: {
			                                    color: '#c12e34'
			                                }
			                            }
			                        },
			                        itemStyle: {
			                            normal: {
			                                borderColor: '#eee',
			                                areaColor: '#ddd'
			                            },
			                            emphasis: {
			                                areaColor: '#e6b600'
			                            }
			                        }
			                    },

			                    gauge: {
			                        axisLine: {
			                            show: true,
			                            lineStyle: {
			                                color: [[0.2, '#2b821d'],[0.8, '#005eaa'],[1, '#c12e34']],
			                                width: 5
			                            }
			                        },
			                        axisTick: {
			                            splitNumber: 10,
			                            length:8,
			                            lineStyle: {
			                                color: 'auto'
			                            }
			                        },
			                        axisLabel: {
			                            textStyle: {
			                                color: 'auto'
			                            }
			                        },
			                        splitLine: {
			                            length: 12,
			                            lineStyle: {
			                                color: 'auto'
			                            }
			                        },
			                        pointer: {
			                            length: '90%',
			                            width: 3,
			                            color: 'auto'
			                        },
			                        title: {
			                            textStyle: {
			                                color: '#333'
			                            }
			                        },
			                        detail: {
			                            textStyle: {
			                                color: 'auto'
			                            }
			                        }
			                    }
			                };
			                echarts.registerTheme('shine', theme);
		}
		
	});
	
});