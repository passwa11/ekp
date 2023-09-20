 function initTimeOutProcess(url){
	//待办最多的流程
	 var timeOutProcessItem = echarts.init(document.getElementById("timeOut_process"));
	  // 显示标题，图例和空的坐标轴
	 timeOutProcessItem.setOption({
	 	title: {
	 		text:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.dealMaxProcess"),
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
	 	legend: {
	 		x: 'center',
	 		top:'7%',
	        data: [Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessRuning"),Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessCompleted")]
	    },
	 	toolbox: {
	 		show: true,
	 		feature: {
	 			dataView: {},
	            saveAsImage: {},
	        	restore: {}
	        }
	 	},
	 	xAxis : [
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
	 	yAxis : {
		 		 type : 'category',
		         data :[],
		         axisTick: {
		             alignWithLabel: true
		         },
	            splitNumber:10,//动态设置分隔间断
		 		axisLine:{
		 			symbol:['none', 'arrow'],
		 			symbolSize:[10, 12],
		 			symbolOffset:[0, 0],
	 				lineStyle:{
	 					color:'#C4C6CF'
	 				}
		 		},
		 		axisLabel:{
		 			fontFamily:'MicrosoftYaHei',
		 			color:'#000000',
		 			margin:15,
		 			formatter: function (value, index) {
		 				if(value!=null&&value.length>8){
		 					value=value.substring(0,8)+"...";
		 				}
		 			    return value;
		 			}
		 		}
	 	},
	 	series: [{
	 		 name:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.dealTimeOutProcessNum"),
	         type:'bar',
	         barWidth: 20,
	         data:[]
	 	}]
	 });
	 timeOutProcessItem.showLoading(); 
	
	 var param = {
	 	method : "getTimeOutProcessList"
	 	};
	 	
	 	var xData = [];
	 	var yData = [];
	 	
	 	var xActivatedData = [];
	 	var xCompletedData = [];
	 	
	    $.post(url, param, function(data,status){
	 		//每次接收一个数据

	 	//请求成功
	 	var datamap = data.data;
	 	
	 	for(var i=0;i<datamap.length;i++){
	 		if(i==0){
	 			$("#timeOut_max_process").html(datamap[i].fdName);
	 		}
	 		xData.unshift(parseInt(datamap[i].processCount));
	 		yData.unshift(datamap[i].fdName);
	 		
	 		xActivatedData.unshift(parseInt(datamap[i].activatedCount));
	 		xCompletedData.unshift(parseInt(datamap[i].completedCount));
	 	}
	 	
	 	timeOutProcessItem.hideLoading(); 
	 	
	 	timeOutProcessItem.setOption({        //加载数据图表
	 		 yAxis: {
	             data: yData
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
	 					    	var div$One=$("#timeOut_process").children("div").get(0);
	 							 $(div$One).hide();
	 					    }; 
	 						    
	 						var axisData = opt.yAxis[0].data;
	 						var series = opt.series;
	 						
	 						var table = '<table class="lui_data_form_two" style="width:100%;text-align:center;background-color:#FFFFFF" border><tbody><tr>'
								 + '<td>'+Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.dealTimeOutTemplateRanking")+'</td>';
								 
							for (var z = 0, l = series.length; z < l; z++) {
								table=table + '<td>' + series[z].name + '</td>';
	 						}
							
							table=table + '</tr>';
					
					
							for (var i = 0, x = axisData.length; i < x; i++) {
								table += '<tr>'
										 + '<td>' + axisData[i] + '</td>';
										 
									for (var j = 0, x = series.length; j < x; j++) {
										table=table + '<td>' +  (series[j].data[i]==null ? "":series[j].data[i]) + '</td>';
			 						}
									
									table=table+ '</tr>';
							}
					
	 						table += '</tbody></table>';
	 						
	 					    
	 						return table;
	 					}
	 				}
	 			}
	 		},
	         // 运行中 #4175FF     已结束 #FFD37D
	         series: [ 
	         {
	             name: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessRuning"),
	             color: ['#4175FF'],
	             type: 'bar',
	             stack: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessTotal"),
	             label: {
	                 normal: {
	                     show: true,
	                     position: 'insideRight',
	                     formatter: function (params) {
                             if (params.value > 0) {
                                 return params.value;
                             } else {
                                 return '';
                             }
	                     }
	                 }
	             },
	             data: xActivatedData
	         },
	         {
	             name: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessCompleted"),
	             type: 'bar',
	             color: ['#C2C2C2'],
	             stack: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessTotal"),
	             label: {
	                 normal: {
	                     show: true,
	                     position: 'insideRight',
	                     formatter: function (params) {
                             if (params.value > 0) {
                                 return params.value;
                             } else {
                                 return '';
                             }
	                     }
	                 }
	             },
	             data: xCompletedData
	         }]
	     });
	 	
	 },"json");
	    
	    return timeOutProcessItem;
 }