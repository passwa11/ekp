define(['dojo/_base/declare',
        "dojo/dom-geometry",
        "dojo/dom-style",
        "dojo/window"
        ],function(declare,domGeometry,domStyle,win){
	return declare('mui.chart.chartWesterosMixin',null,{
		
		//默认颜色序列,覆盖Echarts3默认的,与PC端保持一致
		colors : [
		            "#4285f4",
		            "#e57abc",
		            "#f3c14a",
		            "#69e27c",
		            "#a294e3",
		            "#f29393"
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
			echarts.registerTheme('westeros', {
		        "color": this.colors,
		        "backgroundColor": "rgba(0,0,0,0)",
		        "textStyle": {},
		        "title": {
		            "textStyle": {
		                "color": "#666666"
		            },
		            "subtextStyle": {
		                "color": "#f2f2f2"
		            }
		        },
		        "line": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": "2"
		                }
		            },
		            "lineStyle": {
		                "normal": {
		                    "width": "2"
		                }
		            },
		            "symbolSize": "6",
		            "symbol": "emptyCircle",
		            "smooth": true
		        },
		        "radar": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": "2"
		                }
		            },
		            "lineStyle": {
		                "normal": {
		                    "width": "2"
		                }
		            },
		            "symbolSize": "6",
		            "symbol": "emptyCircle",
		            "smooth": true
		        },
		        "bar": {
		            "itemStyle": {
		                "normal": {
		                    "barBorderWidth": 0,
		                    "barBorderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "barBorderWidth": 0,
		                    "barBorderColor": "#ffffff"
		                }
		            }
		        },
		        "pie": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            }
		        },
		        "scatter": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            }
		        },
		        "boxplot": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            }
		        },
		        "parallel": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            }
		        },
		        "sankey": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            }
		        },
		        "funnel": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            }
		        },
		        "gauge": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            }
		        },
		        "candlestick": {
		            "itemStyle": {
		                "normal": {
		                    "color": "#f79fc2",
		                    "color0": "transparent",
		                    "borderColor": "#f04086",
		                    "borderColor0": "#2cd3e1",
		                    "borderWidth": "2"
		                }
		            }
		        },
		        "graph": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ffffff"
		                }
		            },
		            "lineStyle": {
		                "normal": {
		                    "width": 1,
		                    "color": "#aaaaaa"
		                }
		            },
		            "symbolSize": "6",
		            "symbol": "emptyCircle",
		            "smooth": true,
		            "color": [
		                "#4285f4",
		                "#e57abc",
		                "#f3c14a",
		                "#69e27c",
		                "#a294e3",
		                "#f29393"
		            ],
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#ffffff"
		                    }
		                }
		            }
		        },
		        "map": {
		            "itemStyle": {
		                "normal": {
		                    "areaColor": "#f3f3f3",
		                    "borderColor": "#516b91",
		                    "borderWidth": 0.5
		                },
		                "emphasis": {
		                    "areaColor": "rgba(165,231,240,1)",
		                    "borderColor": "#516b91",
		                    "borderWidth": 1
		                }
		            },
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#000000"
		                    }
		                },
		                "emphasis": {
		                    "textStyle": {
		                        "color": "rgb(81,107,145)"
		                    }
		                }
		            }
		        },
		        "geo": {
		            "itemStyle": {
		                "normal": {
		                    "areaColor": "#f3f3f3",
		                    "borderColor": "#516b91",
		                    "borderWidth": 0.5
		                },
		                "emphasis": {
		                    "areaColor": "rgba(165,231,240,1)",
		                    "borderColor": "#516b91",
		                    "borderWidth": 1
		                }
		            },
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#000000"
		                    }
		                },
		                "emphasis": {
		                    "textStyle": {
		                        "color": "rgb(81,107,145)"
		                    }
		                }
		            }
		        },
		        "categoryAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#cccccc"
		                }
		            },
		            "axisTick": {
		                "show": false,
		                "lineStyle": {
		                    "color": "#333"
		                }
		            },
		            "axisLabel": {
		                "show": true,
		                "textStyle": {
		                    "color": "#999999"
		                }
		            },
		            "splitLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": [
		                        "#eeeeee"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.05)",
		                        "rgba(200,200,200,0.02)"
		                    ]
		                }
		            }
		        },
		        "valueAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#cccccc"
		                }
		            },
		            "axisTick": {
		                "show": false,
		                "lineStyle": {
		                    "color": "#333"
		                }
		            },
		            "axisLabel": {
		                "show": true,
		                "textStyle": {
		                    "color": "#999999"
		                }
		            },
		            "splitLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": [
		                        "#eeeeee"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.05)",
		                        "rgba(200,200,200,0.02)"
		                    ]
		                }
		            }
		        },
		        "logAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#cccccc"
		                }
		            },
		            "axisTick": {
		                "show": false,
		                "lineStyle": {
		                    "color": "#333"
		                }
		            },
		            "axisLabel": {
		                "show": true,
		                "textStyle": {
		                    "color": "#999999"
		                }
		            },
		            "splitLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": [
		                        "#eeeeee"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.05)",
		                        "rgba(200,200,200,0.02)"
		                    ]
		                }
		            }
		        },
		        "timeAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#cccccc"
		                }
		            },
		            "axisTick": {
		                "show": false,
		                "lineStyle": {
		                    "color": "#333"
		                }
		            },
		            "axisLabel": {
		                "show": true,
		                "textStyle": {
		                    "color": "#999999"
		                }
		            },
		            "splitLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": [
		                        "#eeeeee"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.05)",
		                        "rgba(200,200,200,0.02)"
		                    ]
		                }
		            }
		        },
		        "toolbox": {
		            "iconStyle": {
		                "normal": {
		                    "borderColor": "#999999"
		                },
		                "emphasis": {
		                    "borderColor": "#666666"
		                }
		            }
		        },
		        "legend": {
		            "textStyle": {
		                "color": "#999999"
		            }
		        },
		        "tooltip": {
		            "axisPointer": {
		                "lineStyle": {
		                    "color": "#cccccc",
		                    "width": 1
		                },
		                "crossStyle": {
		                    "color": "#cccccc",
		                    "width": 1
		                }
		            }
		        },
		        "timeline": {
		            "lineStyle": {
		                "color": "#8fd3e8",
		                "width": 1
		            },
		            "itemStyle": {
		                "normal": {
		                    "color": "#8fd3e8",
		                    "borderWidth": 1
		                },
		                "emphasis": {
		                    "color": "#8fd3e8"
		                }
		            },
		            "controlStyle": {
		                "normal": {
		                    "color": "#8fd3e8",
		                    "borderColor": "#8fd3e8",
		                    "borderWidth": 0.5
		                },
		                "emphasis": {
		                    "color": "#8fd3e8",
		                    "borderColor": "#8fd3e8",
		                    "borderWidth": 0.5
		                }
		            },
		            "checkpointStyle": {
		                "color": "#8fd3e8",
		                "borderColor": "rgba(181,173,194,0.37)"
		            },
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#8fd3e8"
		                    }
		                },
		                "emphasis": {
		                    "textStyle": {
		                        "color": "#8fd3e8"
		                    }
		                }
		            }
		        },
		        "visualMap": {
		            "color": [
		                "#516b91",
		                "#59c4e6",
		                "#a5e7f0"
		            ]
		        },
		        "dataZoom": {
		            "backgroundColor": "rgba(0,0,0,0)",
		            "dataBackgroundColor": "rgba(255,255,255,0.3)",
		            "fillerColor": "rgba(167,183,204,0.4)",
		            "handleColor": "#a7b7cc",
		            "handleSize": "100%",
		            "textStyle": {
		                "color": "#333333"
		            }
		        },
		        "markPoint": {
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#ffffff"
		                    }
		                },
		                "emphasis": {
		                    "textStyle": {
		                        "color": "#ffffff"
		                    }
		                }
		            }
		        }
		    });
		}
		
	});
	
});