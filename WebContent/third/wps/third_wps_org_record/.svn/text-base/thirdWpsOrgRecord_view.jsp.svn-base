<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%> 

<template:include ref="default.view" >
	<template:replace name="head">
		<style>
			 pre {
			    display: block;
			    font-family: inherit;
			    white-space: pre-wrap;
			    white-space: -moz-pre-wrap;
			    white-space: -o-pre-wrap;
			    word-wrap: break-word;
			    padding: 0;
			    margin: 0;
			    font-size: 15px;
			    line-height: inherit;
			    color: inherit;
			    background-color: transparent;
			    border: none;
			    border-radius: 0;
			    text-align: left;
			  }
			 .string { color: green; }
			 .number { color: darkorange; }
			 .boolean { color: blue; }
			 .null { color: magenta; }
			 .key { color: red; }
		 </style>
	</template:replace>
	<template:replace name="title">
		WPS私有云组织架构同步结果
	</template:replace>
	
		<template:replace name="toolbar">
			<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="4">
				<ui:button text="${lfn:message('button.close')}" order="3" onclick="Com_CloseWindow();">
				</ui:button>
			</ui:toolbar>
		</template:replace>

	<template:replace name="content" >
		<center>		
		<table class="tb_normal" width=100% style="margin-top:10px;">	
				<c:if test="${syncResult.result == null || syncResult.result == ''}">
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.syncResult")}</td>
						<td colspan="3">${lfn:message("third-wps:thirdWpsOrgRecord.dialog.syncRemind")}</td>
					</tr>	
				</c:if>						
				<c:if test="${syncResult.result != null && syncResult.result=='ERROR'}">
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.syncResult")}</td>
						<td colspan="3">${lfn:message("third-wps:thirdWpsOrgRecord.message.error")}</td>
					</tr>
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.errorMsg")}</td>
						<td colspan="3">${syncResult.errorMsg}</td>
					</tr>
				</c:if>
				<c:if test="${syncResult.result != null && syncResult.result=='SUCCESS'}">
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.syncResult")}</td>
						<td colspan="3">${lfn:message("third-wps:thirdWpsOrgRecord.message.success")}</td>
					</tr>
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.currentDeptNum")}</td>
						<td colspan="3">${syncResult.currentDeptNum}</td>
					</tr>	
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.currentMemberNum")}</td>
						<td colspan="3">${syncResult.currentMemberNum}</td>
					</tr>				
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.needUpdateDeptNum")}</td>
						<td>${syncResult.needUpdateDeptNum}</td>
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.updateDeptNum")}</td>
						<td>${syncResult.updateDeptNum}</td>
					</tr>			
				
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.needSyncDeptNum")}</td>
						<td>${syncResult.needSyncDeptNum}</td>
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.syncDeptNum")}</td>
						<td>${syncResult.syncDeptNum}</td>
					</tr>
						
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.needUpdateMemberNum")}</td>
						<td>${syncResult.needUpdateNum}</td>
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.updateMemberNum")}</td>
						<td>${syncResult.updateNum}</td>
					</tr>
				
			
					<tr class="tb_normal">
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.needSyncMemberNum")}</td>
						<td>${syncResult.needSyncNum}</td>
						<td class="td_normal_title" width=25%>${lfn:message("third-wps:thirdWpsOrgRecord.message.syncMemberNum")}</td>
						<td>${syncResult.syncNum}</td>
					</tr>
				</c:if>
		</table>
		<c:if test="${thirdWpsOrgRecordForm.fdResult != null}">
			<div id="geoJsonDiv">
				<div style="text-align: left;font-weight: bold; margin-top: 10px;">
					部门/员工同步失败信息:
				</div>
				<pre id="geoJsonTxt"></pre>	
			</div>
		</c:if>	
		<script>
			window.onload = function(){
				var jsonStr = '${thirdWpsOrgRecordForm.fdResult}';	
				try{
					 var json = JSON.parse(jsonStr);					
					 var newJson = {};
					 var showFailedInfo = false;
					 if(json.failedMember && json.failedMember.length > 0){
						 newJson.failedMember = json.failedMember;
						 showFailedInfo = true;
					 }
					 if(json.failedDept && json.failedDept.length > 0){
						 newJson.failedDept = json.failedDept;
						 showFailedInfo = true;
					 }		
					 if(showFailedInfo){
						 newJson = window.formatJson(newJson);
						 newJson = window.beautifulJson(newJson);			
						 $('#geoJsonTxt').html(newJson);
					 }else{
						 $('#geoJsonDiv').hide();
					 }	
				}catch(e){	
					$('#geoJsonDiv').hide();
				}
			}			
			window.formatJson = function (json) {
		         var formatted = '',     //转换后的json字符串
		            padIdx = 0,         //换行后是否增减PADDING的标识
		             PADDING = '    ';   //4个空格符
		         /**
		          * 将对象转化为string
		          */
		         if (typeof json !== 'string') {
		             json = JSON.stringify(json);
		         }
		         /** 
		          *利用正则类似将{'name':'ccy','age':18,'info':['address':'wuhan','interest':'playCards']}
		          *---> \r\n{\r\n'name':'ccy',\r\n'age':18,\r\n
		          *'info':\r\n[\r\n'address':'wuhan',\r\n'interest':'playCards'\r\n]\r\n}\r\n
		          */
		         json = json.replace(/([\{\}])/g, '\r\n$1\r\n')
		                     .replace(/([\[\]])/g, '\r\n$1\r\n')
		                     .replace(/(\,)/g, '$1\r\n')
		                     .replace(/(\r\n\r\n)/g, '\r\n')
		                     .replace(/\r\n\,/g, ',');
		         /** 
		          * 根据split生成数据进行遍历，一行行判断是否增减PADDING
		          */
		        (json.split('\r\n')).forEach(function (node, index) {
		             var indent = 0,padding = '';
		             if (node.match(/\{$/) || node.match(/\[$/)){
		            	 indent = 1;
		             }  else if (node.match(/\}/) || node.match(/\]/))  {
		            	 padIdx = padIdx !== 0 ? --padIdx : padIdx;
		             }else{
		            	 indent = 0; 
		             } 
		             for (var i = 0; i < padIdx; i++)    padding += PADDING;
		             formatted += padding + node + '\r\n';
		             padIdx += indent;
	         	});
		        return formatted;
		    };
		    
		    window.beautifulJson = function(json){
		    	 json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
				 return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
						 var cls = 'number';					
						 	if (/^"/.test(match)) {
						 			if (/:$/.test(match)) {
						 				cls = 'key';
						 			} else {
						 				cls = 'string';
						 			}
						 	} else if (/true|false/.test(match)) {
						 		cls = 'boolean';
						 	} else if (/null/.test(match)) {
						 		cls = 'null';
						 	}
						 	return '<span class="' + cls + '">' + match + '</span>';
					 });	
		    }
	
		</script>
	</template:replace>
</template:include>
