 function initRejectNodeProcess(url){
	//待办最多的流程
	 var rejectNodeItem = echarts.init(document.getElementById("rejectNode"));
	  // 显示标题，图例和空的坐标轴
	 rejectNodeItem.setOption({
	 	color: ['#3398DB'],
	 	title: {
	 		text:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.nodeRejectMax"),
	 		x: 'left',
	 		textStyle:{
	 			color:'#333',
	 			fontSize:16
	 		},
	 		padding:[10,10,40,10]
	 	},
	 	tooltip : {
	 		trigger: 'axis',
	 		axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	 			type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	 		}
	 	},
	    grid: {
	    	left:10, 
	    	bottom:10,
	        containLabel: true
	    },
	 	toolbox: {
	 		show: true,
	 		feature: {
	 			dataView: {},
	            saveAsImage: {},
	        	restore: {}
	        }
	 	},
	 	yAxis : [
	 		{
	 			 type : 'value',
	 			axisLine:{
	 				symbol:['none', 'arrow'],
	 				symbolSize:[10, 12],
	 				symbolOffset:[0, 0],
	 				lineStyle:{
	 					color:'#C4C6CF'
	 				}
	 			},
		 		splitLine:{
		 			lineStyle:{
		 				color:'#EBECF0',
		 				width:0.9
		 			}
		 		}
	 		}
	 	],
	 	xAxis : {
	 		type : 'category',
	         data :[],
	         axisTick: {
	             alignWithLabel: true
	         },
	         splitNumber:10,//动态设置分隔间断
	 		axisLabel:{
	 			fontFamily:'MicrosoftYaHei',
	 			color:'#000000',
	 			rotate:45,
	 			formatter: function (value, index) {
	 				if(value!=null&&value!=""){
	 					var startIndex=value.indexOf("(");
	 					var lastIndex=value.indexOf(")");
	 					if(startIndex!=-1&&lastIndex!=-1){
	 						value=value.substring(0,startIndex);
	 					}
	 				}
	 				
	 				if(value!=null&&value.length>8){
	 					value=value.substring(0,8)+"...";
	 				}
	 			    return value;
	 			}
	 		},
	 		axisLine:{
 				symbol:['none', 'arrow'],
 				symbolSize:[10, 12],
 				symbolOffset:[0, 0],
 				lineStyle:{
 					color:'#C4C6CF'
 				}
 			},
	 		splitLine:{
	 			lineStyle:{
	 				color:'#EBECF0',
	 				width:0.9
	 			}
	 		}
	 	},
	 	series: [{
	 		name:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.nodeRejectNumberTimes"),
	         type:'bar',
	         barWidth: 20,
	         data:[]
	 	}]
	 });
	 rejectNodeItem.showLoading(); 
	
	 var param = {
	 	method : "getNodeRejectCountList"
	 	};
	 	
	 	var xData = [];
	 	var yData = [];
	 	
	    $.post(url, param, function(data,status){
	 		//每次接收一个数据
	    	
	 	//请求成功
	 	var datamap = data.data;
	 	
	 	
	 	
	 	for(var i=0;i<datamap.length;i++){
	 		if(i==0){
	 			$("#node_max_rejectNode").html(datamap[i].fdName);
	 		}
	 		xData.unshift(datamap[i].fdName);
	 	}
	 	
	 	for(var i=0;i<datamap.length;i++){
	 		yData.unshift(parseInt(datamap[i].processCount));
	 	}
	 	
	 	rejectNodeItem.hideLoading(); 
	 	
	 	rejectNodeItem.setOption({        //加载数据图表
	 		xAxis: {
	             data: xData
	         },
	         toolbox: {
	 			feature: {
	 				dataView: {readOnly: true,
	 					optionToContent: function(opt) {
	 						
	 						var DEFAULT_VERSION = 8.0;  
	 					    var ua = navigator.userAgent.toLowerCase();  
	 					    var isIE = ua.indexOf("msie")>-1;  
	 					    var safariVersion;  
	 					    if(isIE){  
	 					    	safariVersion =  ua.match(/msie ([\d.]+)/)[1];  
	 					    }  
	 					    if(safariVersion <= DEFAULT_VERSION ){  
	 					    	var div$One=$("#rejectNode").children("div").get(0);
	 							 $(div$One).hide();
	 					    }; 
	 						    
	 						var axisData = opt.xAxis[0].data;
	 						var series = opt.series;
	 						var table = '<table class="lui_data_form_two" style="width:100%;text-align:center;background-color:#FFFFFF" border><tbody><tr>'
	 									 + '<td>'+Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.rejectMaxNodeName")+'</td>'
	 									 + '<td>' + series[0].name + '</td>'
	 									 + '</tr>';
	 						for (var i = 0, l = axisData.length; i < l; i++) {
	 							table += '<tr>'
	 									 + '<td>' + axisData[i] + '</td>'
	 									 + '<td>' +  (series[0].data[i]==null ? "":series[0].data[i]) + '</td>'
	 									 + '</tr>';
	 						}
	 						table += '</tbody></table>';
	 						
	 					    
	 						return table;
	 					}
	 				}
	 			}
	 		},
	         series: [{
	             // 根据名字对应到相应的系列
	             data: yData
	         }]
	     });
	 	
	 },"json");
	    
	    return rejectNodeItem;
 }