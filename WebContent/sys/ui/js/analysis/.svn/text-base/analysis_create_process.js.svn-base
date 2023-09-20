 function initCreateProcess(url){
	//待办最多的流程
	 var createProcessItem = echarts.init(document.getElementById("create_process"));
	  // 显示标题，图例和空的坐标轴
	 createProcessItem.setOption({
	 	title: {
	 		text:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.CreateProcessRanking"),
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
	 	},//草稿  #9FD6FF  驳回#66A8FF  待审 #4175FF   废弃 #C2C2C2  已结束 #FFD37D
	 	legend: {
	 		x: 'center',
	 		top:'7%',
	        data: [Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessDraft"),Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessPending"),Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessAbandoned"),Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessCompleted")]
	    },
	    grid: {
	    	left:6, 
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
	 		min:0,
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
	 		 name:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.CreateProcessRankingNumber"),
	         type:'bar',
	         barWidth: 20,
	         data:[]
	 	}]
	 });
	 createProcessItem.showLoading(); 
	
	 var param = {
	 	method : "getCreateProcessList"
	 	};
	 	
	 	var xData = [];
	 	var yData = [];
	 	
	 	var xCreateCountData = [];
	 	
	 	var xActivatedData = [];
	 	
	 	var xAbandonedData = [];
	 	
	 	var xCompletedData = [];
	 	
	    $.post(url, param, function(data,status){
	 		//每次接收一个数据

	 	//请求成功
	 	var datamap = data.data;
	 	
	 	for(var i=0;i<datamap.length;i++){
	 		if(i==0){
	 			$("#create_max_process").html(datamap[i].fdName);
	 		}
	 		yData.unshift(datamap[i].fdName);
	 	}
	 	
	 	for(var i=0;i<datamap.length;i++){
	 		xData.unshift(parseInt(datamap[i].processCount));
	 		xCreateCountData.unshift(parseInt(datamap[i].createCount));
	 		xActivatedData.unshift(parseInt(datamap[i].activatedCount));
	 		xAbandonedData.unshift(parseInt(datamap[i].abandonedCount));
	 		xCompletedData.unshift(parseInt(datamap[i].completedCount));
	 	}
	 	
	 	createProcessItem.hideLoading(); 
	 	
	 	createProcessItem.setOption({        //加载数据图表
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
	 					    	var div$One=$("#create_process").children("div").get(0);
	 							 $(div$One).hide();
	 					    }; 
	 						    
	 						var axisData = opt.yAxis[0].data;
	 						var series = opt.series;
	 						var table = '<table class="lui_data_form_two" style="width:100%;text-align:center;background-color:#FFFFFF" border><tbody><tr>'
	 									 + '<td>'+Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.CreateTemplateRankingNumber")+'</td>';
	 									 
	 									for (var z = 0, l = series.length; z < l; z++) {
	 										table=table + '<td>' + series[z].name + '</td>';
	 			 						}
	 									
	 									table=table + '</tr>';
	 						
	 						
	 						for (var i = 0, l = axisData.length; i < l; i++) {
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
	 		},//草稿  #9FD6FF  驳回#66A8FF  待审 #4175FF   废弃 #C2C2C2  已结束 #FFD37D
	         series: [ { 
	             name: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessDraft"),
	             type: 'bar',
	             stack: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessTotal"),
	             color: ['#9FD6FF'],
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
	             data: xCreateCountData
	         },
	         {
	             name:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessPending"),
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
	             name:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessAbandoned"),
	             color: ['#C2C2C2'],
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
	             data: xAbandonedData
	         },
	         {
	             name: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessCompleted"),
	             type: 'bar',
	             color: ['#FFD37D'],
	             stack:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.ProcessTotal"),
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
	    
	    return createProcessItem;
 }