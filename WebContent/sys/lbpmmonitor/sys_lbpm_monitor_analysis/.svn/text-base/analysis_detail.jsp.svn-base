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
	
	<list:criteria channel="channel_common_approve"  expand="false" multi="false">
			    <list:cri-criterion
			title="${lfn:message('sys-lbpmperson:lbpmperson.flow.order.module')}"
			key="fdModelName" multi="false" expand="true">
				<list:box-select>
					<list:item-select>
						<ui:source type="AjaxJson">
								{url:'/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=getModule'}
						</ui:source>
					</list:item-select>
				</list:box-select>
		</list:cri-criterion>
		<list:cri-auto modelName="com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess" title="${lfn:message('sys-lbpmmonitor:sysLbpmMonitor.order.timeRange')}" property="fdCreateTime" /> 

	</list:criteria>
	
  	
    <!-- 选项卡头部 Starts -->
    <div class="lui-flowMonitor-tabHead">
      <ul class="lui-flowMonitor-tabs">
        <li class="active">
		 	<span class="txtlistpath">
		 		<a href="javascript:backUrl();"><bean:message key="sysLbpmMonitor.analysis.back" bundle="sys-lbpmmonitor" /> > <bean:message key="sysLbpmMonitor.analysis.UpcomingMaxProcess" bundle="sys-lbpmmonitor" /></a>
		 	</span>
        </li>
      </ul>
    </div>
    <!-- 选项卡头部 Ends -->
    <!-- 选项卡内容 Starts -->
    <div class="lui-flowMonitor-tab-content lui-flowMonitor-tab-content-detail">
      <div class="lui-flowMonitor-tab-pane fade active in">
        <ul class="lui-flowMonitor-statistics-board">
          <li>
            <div class="magnet">
              <div class="magnet-icon icon-color-1 icon-13"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.tatalPeopleCount" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="peopleCount"></p>
            </div>
          </li>
          <li>
            <div class="magnet">
              <div class="magnet-icon icon-color-2 icon-14"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.tatalUpcomingProcessCount" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="totalProcessCount"></p>
            </div>
          </li>
          <li>
            <div class="magnet">
              <div class="magnet-icon icon-color-3 icon-15"></div>
              <h4 class="magnet-title"><bean:message key="sysLbpmMonitor.analysis.UpcomingMaxProcess" bundle="sys-lbpmmonitor" /></h4>
              <p class="magnet-subhead" id="max_fdName"></p>
            </div>
          </li>
        </ul>
        <ul class="lui-flowMonitor-chartList">
          <li>
            <div class="chart-item" id="upcoming_process" style="display: block; width:80%; height: 420px; text-align: center; font-size: 36px;">
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
		    
		   var url = '<c:url value="/sys/lbpmmonitor/sys_lbpmmonitor_flow/SysLbpmMonitorFlow.do" />';
		   var createTime="";
		   
		   //待办最多的流程
			 var upcomingProcessItem = echarts.init(document.getElementById("upcoming_process"));
			  // 显示标题，图例和空的坐标轴
			 upcomingProcessItem.setOption({
			 	color: ['#3398DB'],
			 	title: {
			 		text: '<bean:message key="sysLbpmMonitor.analysis.UpcomingMaxProcess" bundle="sys-lbpmmonitor" />',
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
				 			rotate:45,
				 			formatter: function (value, index) {
				 				if(value!=null&&value.length>8){
				 					value=value.substring(0,8)+"...";
				 				}
				 			    return value;
				 			}
				 		}
			 	},
			 	series: [{
			 		name:'<bean:message key="sysLbpmMonitor.analysis.UpcomingMaxProcessCount" bundle="sys-lbpmmonitor" />',
			         type:'bar',
			         barWidth: 20,
			         data:[]
			 	}]
			 });
			 upcomingProcessItem.showLoading(); 
			
			
			 upcomingProcessItem.on('click', function (param) { 
					// 指定图表的配置项和数据
			       var option = upcomingProcessItem.getOption();
					
					//获取自定义参数Id值
					var fdTempletModelId=option.series[param.seriesIndex].rawdata[param.dataIndex];
					
			       console.log("sdfsd"+fdTempletModelId);
			       console.log(param);
			       
			       dialog.iframe('/sys/lbpmmonitor/sys_lbpm_monitor_analysis/analysis_list.jsp?fdTempletModelId=' + fdTempletModelId+"&fdCreateTime="+ createTime+"&status=20&excuteType=upcoming",
				 			"流程清单", null, {
						width : 960,
						height : 400
					});
			 });
			 
			 
			 postAction(url,"");
		   
			// 监听筛选控件
			topic.channel("channel_common_approve").subscribe('criteria.changed',
					criteriaChange);
			
			 function criteriaChange(data){
				    console.log(data);
				    var modelName="";
				    createTime = "";
				    if(data.criterions.length!=0){
				    	for(var i=0;i<data.criterions[0].value.length;i++){
				    		if("fdModelName" === data.criterions[0].key){
				    			modelName+=data.criterions[0].value+";";
					    	}else if("fdCreateTime" === data.criterions[0].key){
						 		createTime=data.criterions[0].value+";";
					    	}
					 	}
				    	
				    }
				    if(data.criterions.length>1){
				    	for(var i=0;i<data.criterions[1].value.length;i++){
				    		if("fdModelName" === data.criterions[1].key){
				    			modelName+=data.criterions[1].value+";";
					    	}else if("fdCreateTime" === data.criterions[1].key){
						 		createTime=data.criterions[1].value+";";
					    	}
					 	}
				    	
				    }
				 	
				    $("#totalProcessCount").html("0");
		 			$("#peopleCount").html("0");
		 			$("#max_fdName").html("");
		 			
		 			postAction(url,modelName,createTime);
			 }
			 
			 function postAction(url,modelName,createTime){
				 var param = {
					method : "getUpcomingProcessList",
					fdModelName:modelName,
					fdCreateTime:createTime
				 };
				 
				 var xData = [];
				 var yData = [];
				 var zData=[];
				 
				 $.post(url, param, function(data,status){
				 		//每次接收一个数据
					
				 	//请求成功
				 	var datamap = data.data;
				 	
				 	for(var i=0;i<datamap.length;i++){
				 		if(i==0){
				 			$("#totalProcessCount").html(datamap[i].totalProcessCount);
				 			$("#peopleCount").html(datamap[i].peopleCount);
				 			$("#max_fdName").html(datamap[i].fdName);
				 		}
				 		
				 		zData.unshift(datamap[i].fdTemplateModelId);
				 		xData.unshift(datamap[i].fdName);
				 	}
				 	
				 	console.log(xData);
				 	
				 	for(var i=0;i<datamap.length;i++){
				 		yData.unshift(parseInt(datamap[i].processCount));
				 	}
				 	
				 	upcomingProcessItem.hideLoading(); 
				 	
				 	upcomingProcessItem.setOption({        //加载数据图表
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
				 							var div$One=$("#upcoming_process").children("div").get(0);
				 							 $(div$One).hide();
				 						}; 
				 							
				 						var axisData = opt.xAxis[0].data;
				 						var series = opt.series;
				 						var table = '<table class="lui_data_form_two" style="width:100%;text-align:center;background-color:#FFFFFF" border><tbody><tr>'
				 									 + '<td><bean:message key="sysLbpmMonitor.analysis.UpcomingMaxTemplate" bundle="sys-lbpmmonitor" /></td>'
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
				 			 data: yData,
				 			 rawdata:zData
				 		 }]
				 	 });
				 	
				 },"json");
				 	
			 }
			 
			 
	   });
	   

	   </script>
	  
	</template:replace>
</template:include>
