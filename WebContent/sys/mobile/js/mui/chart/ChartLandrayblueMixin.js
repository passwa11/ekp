define(['dojo/_base/declare',
        "dojo/dom-geometry",
        "dojo/dom-style",
        "dojo/window"
        ],function(declare,domGeometry,domStyle,win){
	return declare('mui.chart.chartLandrayblueMixin',null,{
		
		//默认颜色序列,覆盖Echarts3默认的,与PC端保持一致
		colors : [
		            "#166eff",
		            "#69a0fd",
		            "#9ec2ff",
		            "#c4daff",
		            "#c8cbd2",
		            "#b0b7c4",
		            "#98a4b9",
		            "#778398",
		            "#ffc0a9",
		            "#ff9898"
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
			echarts.registerTheme('landrayblue', {
		        "color": this.colors,
		        "backgroundColor": "rgba(0, 0, 0, 0)",
		        "textStyle": {},
		        "title": {
		            "textStyle": {
		                "color": "#333"
		            },
		            "subtextStyle": {
		                "color": "#999999"
		            }
		        },
		        "line": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": "0"
		                }
		            },
		            "lineStyle": {
		                "normal": {
		                    "width": "3"
		                }
		            },
		            "symbolSize": "3",
		            "symbol": "circle",
		            "smooth": false
		        },
		        "radar": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": "0"
		                }
		            },
		            "lineStyle": {
		                "normal": {
		                    "width": "3"
		                }
		            },
		            "symbolSize": "3",
		            "symbol": "circle",
		            "smooth": false
		        },
		        "bar": {
		            "itemStyle": {
		                "normal": {
		                    "barBorderWidth": 0,
		                    "barBorderColor": "#ccc"
		                },
		                "emphasis": {
		                    "barBorderWidth": 0,
		                    "barBorderColor": "#ccc"
		                }
		            }
		        },
		        "pie": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            }
		        },
		        "scatter": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            }
		        },
		        "boxplot": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            }
		        },
		        "parallel": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            }
		        },
		        "sankey": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            }
		        },
		        "funnel": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            }
		        },
		        "gauge": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                },
		                "emphasis": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            }
		        },
		        "candlestick": {
		            "itemStyle": {
		                "normal": {
		                    "color": "#69a0fd",
		                    "color0": "#ffc0a9",
		                    "borderColor": "#166eff",
		                    "borderColor0": "#ff9898",
		                    "borderWidth": 1
		                }
		            }
		        },
		        "graph": {
		            "itemStyle": {
		                "normal": {
		                    "borderWidth": 0,
		                    "borderColor": "#ccc"
		                }
		            },
		            "lineStyle": {
		                "normal": {
		                    "width": 1,
		                    "color": "#aaa"
		                }
		            },
		            "symbolSize": "3",
		            "symbol": "circle",
		            "smooth": false,
		            "color": this.colors,
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
		                    "areaColor": "#eee",
		                    "borderColor": "#4e88e6",
		                    "borderWidth": 0.5
		                },
		                "emphasis": {
		                    "areaColor": "#d1e2ff",
		                    "borderColor": "#4e90fe",
		                    "borderWidth": "1"
		                }
		            },
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#333333"
		                    }
		                },
		                "emphasis": {
		                    "textStyle": {
		                        "color": "#4e90fe"
		                    }
		                }
		            }
		        },
		        "geo": {
		            "itemStyle": {
		                "normal": {
		                    "areaColor": "#eee",
		                    "borderColor": "#4e88e6",
		                    "borderWidth": 0.5
		                },
		                "emphasis": {
		                    "areaColor": "#d1e2ff",
		                    "borderColor": "#4e90fe",
		                    "borderWidth": "1"
		                }
		            },
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#333333"
		                    }
		                },
		                "emphasis": {
		                    "textStyle": {
		                        "color": "#4e90fe"
		                    }
		                }
		            }
		        },
		        "categoryAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#dcdcdc"
		                }
		            },
		            "axisTick": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#dcdcdc"
		                }
		            },
		            "axisLabel": {
		                "show": true,
		                "textStyle": {
		                    "color": "#999999"
		                }
		            },
		            "splitLine": {
		                "show": false,
		                "lineStyle": {
		                    "color": [
		                        "#ccc"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.3)",
		                        "rgba(200,200,200,0.3)"
		                    ]
		                }
		            }
		        },
		        "valueAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#dcdcdc"
		                }
		            },
		            "axisTick": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#dcdcdc"
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
		                        "#f2f2f2"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.3)",
		                        "rgba(200,200,200,0.3)"
		                    ]
		                }
		            }
		        },
		        "logAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#333"
		                }
		            },
		            "axisTick": {
		                "show": true,
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
		                        "#ccc"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.3)",
		                        "rgba(200,200,200,0.3)"
		                    ]
		                }
		            }
		        },
		        "timeAxis": {
		            "axisLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#333"
		                }
		            },
		            "axisTick": {
		                "show": true,
		                "lineStyle": {
		                    "color": "#333"
		                }
		            },
		            "axisLabel": {
		                "show": true,
		                "textStyle": {
		                    "color": "#333"
		                }
		            },
		            "splitLine": {
		                "show": true,
		                "lineStyle": {
		                    "color": [
		                        "#ccc"
		                    ]
		                }
		            },
		            "splitArea": {
		                "show": false,
		                "areaStyle": {
		                    "color": [
		                        "rgba(250,250,250,0.3)",
		                        "rgba(200,200,200,0.3)"
		                    ]
		                }
		            }
		        },
		        "toolbox": {
		            "iconStyle": {
		                "normal": {
		                    "borderColor": "#999"
		                },
		                "emphasis": {
		                    "borderColor": "#333333"
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
		                    "color": "#e9e9e9",
		                    "width": "1"
		                },
		                "crossStyle": {
		                    "color": "#e9e9e9",
		                    "width": "1"
		                }
		            }
		        },
		        "timeline": {
		            "lineStyle": {
		                "color": "#999999",
		                "width": 1
		            },
		            "itemStyle": {
		                "normal": {
		                    "color": "#999999",
		                    "borderWidth": 1
		                },
		                "emphasis": {
		                    "color": "#69a0fd"
		                }
		            },
		            "controlStyle": {
		                "normal": {
		                    "color": "#999999",
		                    "borderColor": "#999999",
		                    "borderWidth": 0.5
		                },
		                "emphasis": {
		                    "color": "#999999",
		                    "borderColor": "#999999",
		                    "borderWidth": 0.5
		                }
		            },
		            "checkpointStyle": {
		                "color": "#69a0fd",
		                "borderColor": "#166eff"
		            },
		            "label": {
		                "normal": {
		                    "textStyle": {
		                        "color": "#999999"
		                    }
		                },
		                "emphasis": {
		                    "textStyle": {
		                        "color": "#999999"
		                    }
		                }
		            }
		        },
		        "visualMap": {
		            "color": [
		                "#166eff",
		                "#69a0fd",
		                "#9ec2ff",
		                "#c4daff",
		                "#ffc0a9",
		                "#ff9898"
		            ]
		        },
		        "dataZoom": {
		            "backgroundColor": "rgba(47,69,84,0)",
		            "dataBackgroundColor": "rgba(47,69,84,0.3)",
		            "fillerColor": "rgba(167,183,204,0.4)",
		            "handleColor": "#a7b7cc",
		            "handleSize": "100%",
		            "textStyle": {
		                "color": "#333"
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