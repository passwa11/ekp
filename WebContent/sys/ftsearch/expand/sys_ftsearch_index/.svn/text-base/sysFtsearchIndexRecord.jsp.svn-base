<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>

var count = null;
init();

function init(){
	seajs.use(['lui/jquery'], function($) {
		findCurrentIndexRecord(0);
    });
}

function findCurrentIndexRecordByTime(time){
	stopCycleRefresh();
	if(time == null || time <= 0){
		findCurrentIndexRecord();
	} else if(time > 0){
		count = setInterval(function() {
			findCurrentIndexRecord();
		}, time*1000);
	}
	
}

function findCurrentIndexRecord(){
	seajs.use(['lui/jquery'], function($) {
		var url = '${LUI_ContextPath}/sys/ftsearch/expand/sysFtsearchIndexRecord.do?method=findCurrentIndexRecord'
		$.ajax({
			url : url,
			method : "post",
	       	dataType : "json",
	       	success : function(data){
	       			var result = data.result;
	      			if(result != undefined && result != null && result.length > 0){
	      				$("#runningRecordTable").html("");
	      				$.each(result,function(index,obj){
	      					addRecordIndexing(obj);
	      				});
	          		}
	          		else{
	          			clearRunningTable();
	          		}
	       	}
		});
	});
}

function stopCycleRefresh(){
	if(count != null){
		clearInterval(count);
		count = null;
	}
}

function addRecordIndexing(record){
	var recordHtml1 = '<tr><td class="td_normal_title" width=20%>';
	var recordHtml2 = '</td><td width="80%">';
	var recordHtml3 = '</td></tr>';
	var recordHtml = recordHtml1+record.threadName+recordHtml2+record.fdId+recordHtml3;
	$("#runningRecordTable").append(recordHtml);
}

function clearRunningTable(){
	var recordHtml1 = '<tr><td class="td_normal_title" width=20%>';
	var recordHtml2 = '</td><td width="80%">';
	var recordHtml3 = '</td></tr>';
	var recordHtml = recordHtml1+"${lfn:message('sys-ftsearch-expand:sysFtsearch.index.running.record')}"+recordHtml2+"${lfn:message('sys-ftsearch-expand:sysFtsearch.index.running.no.record')}"+recordHtml3;
	$("#runningRecordTable").html(recordHtml);
}

</script>
<div id="optBarDiv">
	<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.refresh.button"/>" onclick="findCurrentIndexRecordByTime(0);">
	<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.refresh.one.second.button"/>" onclick="findCurrentIndexRecordByTime(1);">
	<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.refresh.five.second.button"/>" onclick="findCurrentIndexRecordByTime(5);">
	<input type="button" value="<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.refresh.stop.button"/>" onclick="stopCycleRefresh();">
</div>

<p class="txttitle"><bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.index.running.record"/></p>

<center>
<table id="runningRecordTable" class="tb_normal" width=95%>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>