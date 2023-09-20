<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
		<template:replace name="head">
			<%@ include file="/sys/ui/jsp/jshead.jsp"%>	
			<script>
					Com_IncludeFile("jquery.js|json2.js");
			</script>
			<script src='<c:url value="/sys/xform/designer/underscore.js"/>'></script>
			<script src='<c:url value="/sys/xform/designer/UU_processBar.js"/>'></script>
		</template:replace>

<template:replace name="body">	
<div class="txttitle">
<bean:message bundle="sys-xform" key="sysMutliLang.upload.title"/>
</div>

<script type="text/template" id="template">
<@ _.each(result,function(value,index){@>
<tr>
	<td>
		<@=index+1@>
	</td>
	<td>
		<@ if (value["fileName"].indexOf('_') == 64){@>
			<@=value["fileName"].substr(65)@>
		<@}else{@>
			<@=value['fileName']@>
		<@}@>
	</td>

	<td>
		<@ if (value['url'] && value['url'].indexOf('error:') == -1){@>
			<a href='<@=Com_Parameter.ContextPath + value['url']@>' target="_blank">点击进入文档</a>
		<@}else{@>
			<@=value['url']@>
		<@}@>
	</td>
	<td>
		<@=value["cause"]@>
	</td>
</tr>
<@})@>
</script>

<form action="${KMSS_Parameter_ContextPath}sys/mutillang/import.do?method=excelImport" method="post" enctype="multipart/form-data" >
	<center>
		<div id="canvas"></div>
		<table id ="container" class="tb_normal" width=95%>				
			<tr>
				<td>序号</td>
				<td>文件名</td>
				<td>模板链接</td>
				<td>状态</td>
			</tr>
		</table>
	</center>
</form>

<script>

_.templateSettings = {
        interpolate: /\<\@\=(.+?)\@\>/gim,
        evaluate: /\<\@([\s\S]+?)\@\>/gim,
        escape: /\<\@\-(.+?)\@\>/gim
    };
    
//模板
var template = _.template($("#template").html());    

var fileNames = eval('${fileNames}');

var eventSync = [];

var fileNamesParseLog = [];

//下标
var fileNamesFlag = 0;

var timer;

var j  = 0; 

var flag=false;

function isParseEnd(){	
	for(var i = 0 ; i< eventSync.length ; i++){
		if( eventSync[i] == false){
			return false;
		}
	}
	return true;
}

function parseExcel(){
	
	
	//如果之前没有解析完, 500 毫秒以后再来执行
	if(!isParseEnd()){
		setTimeout(parseExcel, 500);
		return;
	}
	//清空锁
	eventSync = [];
	
	//意味着以及执行完成了
	if(fileNamesFlag > fileNames.length - 1){
	
		var temp = {};
		temp.result = fileNamesParseLog;
		
		if($("tbody","#container").length > 0 ){
			$("tbody","#container").append(template(temp));
		}else{
			$("#container").append(template(temp));
		}
		if(flag){
			window.progress.hide();
			clearTimeout(timer);
		}
		return ;
	}
	
	var i  = 0; 
	seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
		
		window.progress = dialog.progress(false);
		progressAlert = function(){
			
			if(window.progress) {
				window.progress.setProgress(j,fileNames.length);
			}
				 timer=setTimeout("progressAlert()", 10);
		}
		
		if(fileNamesFlag > fileNames.length - 1){
			
			
			flag=true;
		}
		
		
		progressAlert();
		
	});
	for(i = fileNamesFlag; i < fileNamesFlag + 10 && i < fileNames.length; i++){
		//加锁
		eventSync[i%10] = false;
		
		ajaxPares(fileNames[i],i%10,function(index){
			eventSync[index] = true;
		});
	}
	fileNamesFlag = i;
		parseExcel();
	
}



function ajaxPares(fileName, index ,func){
	$.ajax({
		type: "POST",
		data:{'fileName':fileName},
		url:Com_Parameter.ContextPath+"sys/mutillang/import.do?method=parseImport",
		success:function(result){
			// 兼容异常数据
			if(result == null || result == ''){
				func(index);
				return;
			}
			var obj = eval("("+result+")");
			fileNamesParseLog.push(obj);
			func(index);
			j=fileNamesParseLog.length;
			
		},
		error:function(result){
			fileNamesParseLog.push({
				"fileName":fileName,
				"cause":"网络出现断开，请重新导入"
			});
			func(index);
			j=fileNamesParseLog.length;
		}
	});
}
parseExcel();
	

</script>
</template:replace>
</template:include>
