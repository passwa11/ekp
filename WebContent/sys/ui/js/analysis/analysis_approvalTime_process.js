 function initApprovalTimeProcess(url){
	//待办最多的流程
	 var approvalTimeProcessItem = echarts.init(document.getElementById("approvalTime_process"));
	  // 显示标题，图例和空的坐标轴
	 approvalTimeProcessItem.setOption({
	 	color: ['#3398DB'],
	 	title: {
	 		text: Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.avgApprovalTime"),
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
		 		},
		 		axisLabel:{formatter:'{value} h'}
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
	 		name:Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.avgConsumingTimeTemplate"),
	         type:'bar',
	         barWidth: 20,
	         data:[]
	 	}]
	 });
	 approvalTimeProcessItem.showLoading(); 
	
	   var param = {
	 	method : "getApprovalTimeProcessList"
	 	};
	 	
	 	var xData = [];
	 	var yData = [];
	 	
	    $.post(url, param, function(data,status){
	 		//每次接收一个数据

	 	//请求成功
	 	var datamap = data.data;
	 	
	 	for(var i=0;i<datamap.length;i++){
	 		if(i==0){
	 			$("#approvalTime_max_process").html(datamap[i].fdName);
	 		}
	 		yData.unshift(datamap[i].fdName);
	 	}
	 	
	 	for(var i=0;i<datamap.length;i++){
	 		xData.unshift(parseInt(datamap[i].processCount));
	 	}
	 	
	 	approvalTimeProcessItem.hideLoading(); 
	 	
	 	approvalTimeProcessItem.setOption({        //加载数据图表
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
	 					    	var div$One=$("#approvalTime_process").children("div").get(0);
	 							 $(div$One).hide();
	 					    }; 
	 						    
	 						var axisData = opt.yAxis[0].data;
	 						var series = opt.series;
	 						var table = '<table class="lui_data_form_two" style="width:100%;text-align:center;background-color:#FFFFFF" border><tbody><tr>'
	 									 + '<td>'+Data_GetResourceString("sys-lbpmmonitor:sysLbpmMonitor.analysis.avgConsumingTimeTemplateName")+'</td>'
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
	             data: xData
	         }]
	     });
	 	
	 	
	 },"json");
	    
	    return approvalTimeProcessItem;
	    
 }