<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super />

	<script>
			Com_IncludeFile('echarts4.2.1.js','${LUI_ContextPath}/sys/ui/js/chart/echarts/','js',true);
	</script>
	
	<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>?s_cache=${LUI_Cache}"></script>
	<script type="text/javascript" src="<c:url value="/sys/ui/js/analysis/transition.js"/>?s_cache=${LUI_Cache}"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value="/sys/lbpmmonitor/sys_lbpm_monitor_analysis/css/analysis.css"/>" media="screen" />
	
	</template:replace>
	<template:replace name="body">
	
	
	
	<list:criteria channel="channel_common_approve"  expand="true" multi="false">
			   <list:cri-ref ref="criterion.sys.calendar" key="fdCreateTime"
		title="${lfn:message('sys-lbpmperson:lbpmperson.flow.docAuthorTime') }" />
	</list:criteria>
	
	
	
  	
    <!-- 选项卡头部 Starts -->
    <div class="lui-flowMonitor-tabHead">
      <ul class="lui-flowMonitor-tabs">
        <li class="active">
        	<span class="txtlistpath">
				<a href="javascript:backUrl();"><bean:message key="sysLbpmMonitor.analysis.back" bundle="sys-lbpmmonitor" /> > <bean:message key="sysLbpmMonitor.analysis.createMaxProcess" bundle="sys-lbpmmonitor" /></a>
			</span>
        </li>
      </ul>
    </div>
    <!-- 选项卡头部 Ends -->
    <!-- 选项卡内容 Starts -->
    <div class="lui-flowMonitor-tab-content lui-flowMonitor-tab-content-detail">
      <div class="lui-flowMonitor-tab-pane fade active in" >
        <ul class="lui-flowMonitor-statistics-board">
          <li style="width:25%">
            <div class="magnet">
              <div class="magnet-icon icon-color-1 icon-13"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.totalCreateProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="total_process_count"></p>
            </div>
          </li>
          <li style="width:25%">
            <div class="magnet">
              <div class="magnet-icon icon-color-2 icon-14"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.totalCompletedProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="completed_process_count"></p>
            </div>
          </li>
          <li style="width:25%">
            <div class="magnet">
              <div class="magnet-icon icon-color-2 icon-14"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.totalAbandonedProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="abandoned_process_count"></p>
            </div>
          </li>
          <li style="width:25%">
            <div class="magnet">
              <div class="magnet-icon icon-color-3 icon-15"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.totalRunProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="run_process_count"></p>
            </div>
          </li>
        </ul>
        <ul class="lui-flowMonitor-chartList">
          <li>
            <div class="chart-item"  id="create_process" style="display: block; height: 420px; text-align: center; font-size: 36px;">
            </div>
          </li>
        </ul>
      </div>
    </div>

    <!-- 选项卡内容 Ends -->
	   <script type="text/javascript">
	   function backUrl(){
		   var url='<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do?method=getMonitorInfo" />';
		   window.location.href=encodeURI(url + "&s_path=${param.s_path}&s_css=default");
	   }
	   
	   setCookie("returnTab","tab-1");//保存选项卡值，返回的时候能跳到相关选项卡
	   seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($ ,dialog, topic) {
		   
		   var fdCreateTimeStart1="";
		   var fdCreateTimeEnd1="";
		    
		   var url = '<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do" />';
		   
		 
			 var createProcessItem = echarts.init(document.getElementById("create_process"));
			  // 显示标题，图例和空的坐标轴
			 createProcessItem.setOption({
			 	title: {
			 		text: '<bean:message key="sysLbpmMonitor.analysis.CreateProcessRanking" bundle="sys-lbpmmonitor" />',
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
			        data: ['<bean:message key="sysLbpmMonitor.analysis.ProcessDraft" bundle="sys-lbpmmonitor" />','<bean:message key="sysLbpmMonitor.analysis.ProcessPending" bundle="sys-lbpmmonitor" />','<bean:message key="sysLbpmMonitor.analysis.ProcessAbandoned" bundle="sys-lbpmmonitor" />','<bean:message key="sysLbpmMonitor.analysis.ProcessCompleted" bundle="sys-lbpmmonitor" />']
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
			 		 axisTick : {show: true},
			         data :[],
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
			 		 name:'<bean:message key="sysLbpmMonitor.analysis.CreateProcessRankingNumber" bundle="sys-lbpmmonitor" />',
			         type:'bar',
			         barWidth: 20,
			         data:[]
			 	}]
			 });
			  
			 createProcessItem.showLoading(); 
			
			 createProcessItem.on('click', function (param) { 
				   
					//指定图表的配置项和数据
			       var option = createProcessItem.getOption();
					
					//获取自定义参数Id值
					var fdTempletModelId=option.series[param.seriesIndex].rawdata[param.dataIndex];
					var seriesIndex=param.seriesIndex;
					console.log("seriesIndex"+seriesIndex);
					var status='10';
					if(seriesIndex==0){//草稿状态
						status='10';
					}
					if(seriesIndex==1){//待审状态
						status='20';				
					}
					if(seriesIndex==2){//废弃状态
						status='00';
					}
					if(seriesIndex==3){//已结束状态
						status='30';
					}
					console.log("status"+status);
			       console.log("sdfsd"+fdTempletModelId);
			       console.log("fdCreateTimeStart:"+fdCreateTimeStart1);
			       console.log("fdCreateTimeEnd1:"+fdCreateTimeEnd1);
			       
			       dialog.iframe('/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_list.jsp?fdTempletModelId=' + fdTempletModelId+"&status="+status+"&starTime="+fdCreateTimeStart1+"&endTime="+fdCreateTimeEnd1,
				 			"流程清单", null, {
						width : 960,
						height : 400
					});
			 });
			 
			 
			 postAction(url,null,null);
		   
			// 监听筛选控件
			topic.channel("channel_common_approve").subscribe('criteria.changed',
					criteriaChange);
			
			 function criteriaChange(data){
				    fdCreateTimeStart1="";
				    fdCreateTimeEnd1="";
				    console.log(data);
				    
				    if(data.criterions.length!=0){
				    	fdCreateTimeStart1=data.criterions[0].value[0];
			    		fdCreateTimeEnd1=data.criterions[0].value[1];
				    }
				        
				    $("#total_process_count").html("0");
		 			$("#completed_process_count").html("0");
		 			$("#run_process_count").html("0");
		 			$("#abandoned_process_count").html("0");
		 			
				    postAction(url,fdCreateTimeStart1,fdCreateTimeEnd1);
			 }
			 
			 function postAction(url,fdCreateTimeStart1,fdCreateTimeEnd1){
							 var param = {
								method : "getCreateProcessList",
								fdCreateTimeStart:fdCreateTimeStart1,
								fdCreateTimeEnd:fdCreateTimeEnd1
							};
						 	
						 	var xData = [];
						 	var yData = [];
						 	var zData=[];
						 	
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
						 			$("#total_process_count").html(parseInt(datamap[i].totalCount));
							 		$("#completed_process_count").html(parseInt(datamap[i].completeTotalCount));
							 		$("#run_process_count").html(parseInt(datamap[i].runTotalCount));
							 		$("#abandoned_process_count").html(parseInt(datamap[i].abandonedTotalCount));
							 		
						 		}
						 		yData.unshift(datamap[i].fdName);
						 	}
						 	
						 	for(var i=0;i<datamap.length;i++){
						 		xData.unshift(parseInt(datamap[i].processCount));
						 		    zData.unshift(datamap[i].fdTemplateModelId);
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
						 									 + '<td><bean:message key="sysLbpmMonitor.analysis.CreateTemplateRankingNumber" bundle="sys-lbpmmonitor" /></td>';
						 									 
						 									for (var z = 0, l = series.length; z < l; z++) {
						 										table=table + '<td>' + series[z].name + '</td>';
						 			 						}
						 									
						 									table=table + '</tr>';
						 						
						 						console.log(axisData);
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
						         series: [ 
						        	 { 
						             name: '<bean:message key="sysLbpmMonitor.analysis.ProcessDraft" bundle="sys-lbpmmonitor" />',
						             value:88,
						             type: 'bar',
						             stack: '<bean:message key="sysLbpmMonitor.analysis.ProcessTotal" bundle="sys-lbpmmonitor" />',
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
						             data: xCreateCountData,
						             rawdata:zData
						         },
						         {
						             name: '<bean:message key="sysLbpmMonitor.analysis.ProcessPending" bundle="sys-lbpmmonitor" />',
						             color: ['#4175FF'],
						             type: 'bar',
						             stack: '<bean:message key="sysLbpmMonitor.analysis.ProcessTotal" bundle="sys-lbpmmonitor" />',
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
						             data: xActivatedData,
						             rawdata:zData
						         },
						         {
						             name: '<bean:message key="sysLbpmMonitor.analysis.ProcessAbandoned" bundle="sys-lbpmmonitor" />',
						             color: ['#C2C2C2'],
						             type: 'bar',
						             stack: '<bean:message key="sysLbpmMonitor.analysis.ProcessTotal" bundle="sys-lbpmmonitor" />',
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
						             data: xAbandonedData,
						             rawdata:zData
						         },
						         {
						             name: '<bean:message key="sysLbpmMonitor.analysis.ProcessCompleted" bundle="sys-lbpmmonitor" />',
						             type: 'bar',
						             color: ['#FFD37D'],
						             stack: '<bean:message key="sysLbpmMonitor.analysis.ProcessTotal" bundle="sys-lbpmmonitor" />',
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
						             data: xCompletedData,
						             rawdata:zData
						         }]
						     });
						 	
						 },"json");
			 }
			 
			 
	   });
	   

	   </script>
	  
	</template:replace>
</template:include>
