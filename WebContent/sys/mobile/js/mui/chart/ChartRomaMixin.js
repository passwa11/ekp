define(['dojo/_base/declare',
        "dojo/dom-geometry",
        "dojo/dom-style",
        "dojo/window"
        ],function(declare,domGeometry,domStyle,win){
	return declare('mui.chart.chartRomaMixin',null,{
		
		// 默认颜色序列,覆盖Echarts3默认的,与PC端保持一致
		colors : ['#E01F54','#001852','#f5e8c8','#b8d2c7','#c6b38e',
                  '#a4d8c2','#f3d999','#d3758f','#dcc392','#2e4783',
                  '#82b6e9','#ff6347','#a092f1','#0a915d','#eaf889',
                  '#6699FF','#ff6666','#3cb371','#d5b158','#38b6b6'
              ],
		concatDefaultOptions : function(options){
			this.defaultColor(options);
			this.defaultXAxis(options);
		},
		
		// x轴默认设置(适配移动端)
		defaultXAxis : function(options){
			var xAxis = options.xAxis;
			if(xAxis instanceof Array){
				for(var i = 0;i < xAxis.length;i++){
					if(!options.xAxis[i].axisLabel) 
						options.xAxis[i].axisLabel={};
					// x轴显示全
					// 数据量太多的时候，不能全部都显示出来 by zhugr 2018-08-08
					// options.xAxis[i].axisLabel.interval = 0;
					// x轴默认倾斜角度
					if(!options.xAxis[i].axisLabel.rotate){
						options.xAxis[i].axisLabel.rotate = 30;
					}
				}
			}
		},
		
		// 默认颜色序列设置(与PC端保持一致)
		defaultColor : function(options){
			if(!options.color){
				options.color = this.colors;
			}
		},
		
		registerTheme:function(echarts){
			 var colorPalette = this.colors;

             var theme = {
                 color: colorPalette,

                 visualMap: {
                     color:['#e01f54','#e7dbc3'],
                     textStyle: {
                         color: '#333'
                     }
                 },

                 candlestick: {
                     itemStyle: {
                         normal: {
                             color: '#e01f54',
                             color0: '#001852',
                             lineStyle: {
                                 width: 1,
                                 color: '#f5e8c8',
                                 color0: '#b8d2c7'
                             }
                         }
                     }
                 },

                 graph: {
                     color: colorPalette
                 },

                 gauge : {
                     axisLine: {
                         lineStyle: {
                             color: [[0.2, '#E01F54'],[0.8, '#b8d2c7'],[1, '#001852']],
                             width: 8
                         }
                     }
                 }
             };

             echarts.registerTheme('roma', theme);
		}
		
	});
	
});