<%@page import="com.landray.kmss.framework.hibernate.extend.SqlPartitionConfig"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple">
	<template:replace name="body">
	<c:import url="dataPanel.jsp" charEncoding="UTF-8"></c:import>
	<list:listview id="lbpmHistoryWorkitemTable">
		<ui:source type="AjaxJson">
				{url:'/sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=findList&fdModelId=${JsParam.fdModelId}&docStatus=${JsParam.docStatus}'}
		</ui:source>
		<list:colTable isDefault="true" layout="sys.ui.listview.columntable">
			<list:col-auto props="fdNode.fdFactNodeName;fdHandler;fdStartDate;operationDate;operationType;operations"></list:col-auto> 
		</list:colTable>
		<list:paging></list:paging>
		<ui:event topic="list.loaded">
			initialPage();
		</ui:event>
	</list:listview>
<style type="text/css">
	.content{
	    display:none;
	    width:170px;
	    height:20px;
	    border-radius:10px;
	    padding:4px 6px 4px 6px;
	    position:absolute;
	    right:-2px;
	    top:-40px;
	    background-color: #D8D8D8;
		color:#4285f4;
	}
</style>
<script src='<%=request.getContextPath() %>/resource/js/jquery.js'/></script>

	<script type="text/javascript">
	Com_IncludeFile("data.js");
	function countDown(op,milliseconds,id){
		var stopTime1=new Date(milliseconds);//结束时间
		var newDay=new Date();//当前时间
		
		 var shenyu1=stopTime1.getTime()-newDay.getTime();//倒计时毫秒数
		if(shenyu1<0){
			
			$(op).hide("slow");
			learTimeoutList(countStatusDownList);
			$($(op).parents("a")[0]).attr('href',statusHrefClick[id]);
			return;
		}
		
		var today=new Date(),//当前时间
			h=today.getHours(),
			m=today.getMinutes(),
			s=today.getSeconds();
		  var stopTime=new Date(milliseconds),//结束时间
			stopH=stopTime.getHours(),
			stopM=stopTime.getMinutes(),
			stopS=stopTime.getSeconds();
		  var shenyu=stopTime.getTime()-today.getTime(),//倒计时毫秒数
			shengyuD=parseInt(shenyu/(60*60*24*1000)),//转换为天
			D=parseInt(shenyu)-parseInt(shengyuD*60*60*24*1000),//除去天的毫秒数
			shengyuH=parseInt(D/(60*60*1000)),//除去天的毫秒数转换成小时
			H=D-shengyuH*60*60*1000,//除去天、小时的毫秒数
			shengyuM=parseInt(H/(60*1000)),//除去天的毫秒数转换成分钟
			M=H-shengyuM*60*1000;//除去天、小时、分的毫秒数
			S=parseInt((shenyu-shengyuD*60*60*24*1000-shengyuH*60*60*1000-shengyuM*60*1000)/1000)//除去天、小时、分的毫秒数转化为秒
			
			if(shengyuD>0){
				$(op).html(shengyuD+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.day")+shengyuH+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.hour")+shengyuM+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.minute")+S+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.remindedAgain")+"<br>");
			}else{
				$(op).html(shengyuH+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.hour")+shengyuM+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.minute")+S+Data_GetResourceString("sys-lbpmservice-support:lbpmPressLog.remindedAgain")+"<br>");
			}
		
			var countStatusDownHander =setTimeout(function () { countDown(op,milliseconds,id); },1000);
			countStatusDownList.push(countStatusDownHander);	
	}

	/**
	 * 清理定时任务
	 * @param list
	 * @returns
	 */
	function learTimeoutList (list) {
	    Array.isArray(list) && list.forEach(function(v){
			clearTimeout(v);
		});
	}

	var countStatusDownList = [];
	
	var processStatus={
			isExec:false//是否执行过统计数据获取函数
	};
	function initialPage(){
		try {
			var arguObj = document.getElementById("lbpmHistoryWorkitemTable");
			var lbpmHistoryWorkitemHead=document.getElementById("lbpmHistoryWorkitemHead");
			if(window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
				var height = arguObj.offsetHeight + lbpmHistoryWorkitemHead.offsetHeight + 0;
				if(height>0)
					window.frameElement.style.height = height + "px";
			}
			if(processStatus.isExec==false){
				processStatusData();
				processStatus.isExec=true;
			}
		} catch(e) {
		}
	}
	
	var statusHrefClick={};
	
	//催办
	window.mouseoverCountDown=function(id,that){
		
		if(statusHrefClick==null||statusHrefClick[id]==null){
			statusHrefClick[id]=$(that).attr("href");
		}
		$.ajax({
			url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=pressJson",
			async: false,
			data: {workItemId:id},
			type: "POST",
			dataType: 'json',
			success: function (data) {
				if(data.pressTimes>0){
					$(that).attr('href',"javascript:;");
					countDown($(that).children(".content")[0],data.pressTimes,id);
					$($(that).children(".content")[0]).show("slow");	
				}else{
					$($(that).children(".content")[0]).hide("slow");
					learTimeoutList(countStatusDownList);
					$(that).attr('href',statusHrefClick[id]);
				}
			},
			error: function (er) {
				if(console){
					console.log(er);
				}			
			}
		});	
	}
	
	window.mouseoutCountDown=function(id,that){
		$($(that).children(".content")[0]).hide("slow");
		learTimeoutList(countStatusDownList);
	}

	

	//催办
	window.press=function(id,op){
		statusHrefClick[id]=$(op).attr("href");
		$.ajax({
			url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=press",
			async: false,
			data: {workItemId:id},
			type: "POST",
			dataType: 'json',
			success: function (data) {
				if(data.type){
					seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			    		dialog.success("${ lfn:message('sys-lbpmservice-support:lbpm.process.status.pressSuccess') }","","","","",{autoCloseTimeout:2,topWin:window});
			    		});
					processStatusData();//刷新统计数据
				}
				else{
					seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
						var msg="${ lfn:message('sys-lbpmservice-support:lbpm.process.status.pressfailure') }";
						if(data.msg){
							msg = data.msg;
						}
			    		dialog.failure(msg,"","","","",{autoCloseTimeout:2,topWin:window});
			    		});
				}
				
			
				
			},
			error: function (er) {
				console.log(er);
			}
		});					
	}
	//获取流程状态统计数据
	function processStatusData(){
		$.ajax({
			url: Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=processStatus",
			async: false,
			data: {fdModelId:'${JsParam.fdModelId}'},
			type: "POST",
			dataType: 'json',
			success: function (data) {
				if(data.type){
					var card_nums=$(".card_num");
					for(var i=0;i<card_nums.length;i++){
						var vId=$(card_nums[i]).attr("id");
						if(data.data[vId]){
							$("#"+vId).html(data.data[vId]);
						}
					}
				}
			},
			error: function (er) {
					console.log(er);
			}
		});
	}
	//列表筛选
	function filterList(type){
		//type:0未查看、1已查看、2未提交、3已提交
		if(LUI("lbpmHistoryWorkitemTable")){
			LUI("lbpmHistoryWorkitemTable").source.setUrl('/sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=findList&fdModelId=${JsParam.fdModelId}&type='+type);
			LUI("lbpmHistoryWorkitemTable").source.get();
		}
	}
	Com_AddEventListener(window, "load", initialPage);
	
	
	
	
	
	</script>
	</template:replace>
</template:include>