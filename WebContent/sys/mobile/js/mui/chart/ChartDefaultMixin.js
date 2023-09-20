define(['dojo/_base/declare',
        "dojo/dom-geometry",
        "dojo/dom-style",
        "dojo/window"
        ],function(declare,domGeometry,domStyle,win){
	return declare('mui.chart.chartDefaultMixin',null,{
		
		//默认颜色序列,覆盖Echarts3默认的,与PC端保持一致
		//colors : ["#166eff", "#69a0fd", "#9ec2ff", "#c4daff", "#c8cbd2", "#b0b7c4", "#98a4b9", "#778398", "#ffc0a9", "#ff9898" ],
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
//			echarts.registerTheme('default', {
//		        color: this.colors,
//		        //backgroundColor: '#fef8ef',
//		        graph: {
//		            color: this.colors
//		        }
//	    		});
			
			echarts.registerTheme('default',{graph: {
		            //color: this.colors
		        }});
		}
	});
	
});