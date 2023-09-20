<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|data.js");
</script>
<tr LKS_LabelName="${lfn:message('tic-soap-sync:ticSoapSyncJob.lang.mappingRelation')}">
	<td>
		<table id="TABLE_DocList" class="tb_normal" width="100%">
			<tr class="td_normal_title">
				<td rowspan="100" align="center">${lfn:message('tic-soap-sync:ticSoapSyncJob.lang.syncJob')}</td>
				<td>${lfn:message('tic-soap-sync:ticSoapSyncJob.lang.number')}</td>
				<td>${lfn:message('tic-soap-sync:ticSoapSyncJob.BAPIName')}</td>
				<td>${lfn:message('tic-soap-sync:ticSoapSyncJob.fdUseExplain')}</td>
				<td></td>
			</tr>
			<!--基准行-->
			<tr KMSS_IsReferRow="1" style="display: none">
				<td KMSS_IsRowIndex="1"></td>
				<td>
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdId" /> 
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdSoapMainId" value="" /> 
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdInvokeType" value="3" /> <!--0表示表单事件--> 
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdLastDate" />
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdSoapXml" /> 
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdUse" /> <!--是否启用--> 
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdQuartzTime" /> <!--时间撮-->
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdQuartzId" value="${ticSoapSyncJobForm.fdId}" /> <!-- 定时任务id --> 
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdQuartzName" value="${ticSoapSyncJobForm.fdSubject}" /> <!-- 定时任务名称 -->
					
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdCompDbcpId" value="${fdSoapInfoForm.fdCompDbcpId}"/><!--数据源ID-->
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdCompDbcpName" value="${fdSoapInfoForm.fdCompDbcpName}"/><!--数据源Name-->
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdSyncTableXpath" value="${fdSoapInfoForm.fdSyncTableXpath}"/><!--同步表-->
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdSyncType" value="${fdSoapInfoForm.fdSyncType}"/><!--同步方式-->
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdTimeColumn" value="${fdSoapInfoForm.fdTimeColumn}"/><!--时间戳列-->
					<input type="hidden" name="fdSoapInfoForms[!{index}].fdDelCondition" value="${fdSoapInfoForm.fdDelCondition}"/><!--删除条件-->
		
					 
					<input type="text" name="fdSoapInfoForms[!{index}].fdSoapMainName" value="default" readOnly class="inputread">
				</td>
				<td>
				<input type="text"
					name="fdSoapInfoForms[!{index}].fdFuncMark" value="${lfn:message('tic-soap-sync:ticSoapSyncJob.use')}" readOnly
					class="inputread">
				</td>
				<td><img src="${KMSS_Parameter_StylePath}icons/edit.gif"
					title="${lfn:message('tic-soap-sync:ticSoapSyncJob.check')}"
					onclick="editFormEventFunction(this.parentNode.parentNode.rowIndex-1);"
					style="cursor: pointer">
				</td>
			</tr>

			<!--内容行-->
			<c:forEach items="${ticSoapSyncJobForm.fdSoapInfoForms}"
				var="fdSoapInfoForm" varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td>${vstatus.index+1}</td>
					<td> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdUse" value="${fdSoapInfoForm.fdUse}" /> <!--是否启用--> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdQuartzTime" value="${fdSoapInfoForm.fdQuartzTime}" /> <!--时间--> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdQuartzId" value="${ticSoapSyncJobForm.fdId}" /> <!-- 定时任务id --> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdQuartzName" value="${ticSoapSyncJobForm.fdSubject}" /> <!-- 定时任务名称 --> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdId" value="${fdSoapInfoForm.fdId}" /> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdSoapMainId" value="${fdSoapInfoForm.fdSoapMainId}" /> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdInvokeType" value="${fdSoapInfoForm.fdInvokeType}" /> 
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdSoapXml" value='${fdSoapInfoForm.fdSoapXmlView}' />
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdLastDate" value="${fdSoapInfoForm.fdLastDate}" />
						
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdCompDbcpId" value="${fdSoapInfoForm.fdCompDbcpId}"/><!--数据源ID-->
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdCompDbcpName" value="${fdSoapInfoForm.fdCompDbcpName}"/><!--数据源Name-->
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdSyncTableXpath" value="${fdSoapInfoForm.fdSyncTableXpath}"/><!--同步表-->
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdSyncType" value="${fdSoapInfoForm.fdSyncType}"/><!--同步方式-->
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdTimeColumn" value="${fdSoapInfoForm.fdTimeColumn}"/><!--时间戳列-->
						<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdDelCondition" value="${fdSoapInfoForm.fdDelCondition}"/><!--删除条件-->
		
						<input type="text" name="fdSoapInfoForms[${vstatus.index}].fdSoapMainName" value="${fdSoapInfoForm.fdSoapMainName}" readOnly class="inputread">
					</td>
					<td><input type="text"
						name="fdSoapInfoForms[${vstatus.index}].fdFuncMark"
						value="${fdSoapInfoForm.fdFuncMark}" readOnly class="inputread">
					</td>
					<td><img
						src="${KMSS_Parameter_StylePath}icons/userinfo_icon.gif"
						title="${lfn:message('tic-soap-sync:ticSoapSyncJob.check')}" onclick="editFormEventFunction('${vstatus.index}');"
						style="cursor: pointer"> 
					</td>
				</tr>
			</c:forEach>

		</table></td>
<script>

var dialogObject = {};
	
	//编辑函数信息
	function editFormEventFunction(index) {
		dialogObject = {
			"fdUse" : $('input[name="fdSoapInfoForms[' + index + '].fdUse"]').val(),
			"fdLastDate" : $('input[name="fdSoapInfoForms[' + index + '].fdLastDate"]').val(),
			"fdQuartzId" : $('input[name="fdSoapInfoForms[' + index+ '].fdQuartzId"]').val(),
			"fdQuartzName" : $('input[name="fdSoapInfoForms[' + index+ '].fdQuartzName"]').val(),
			"fdQuartzTime" : $('input[name="fdSoapInfoForms[' + index+ '].fdQuartzTime"]').val(),
			"fdFuncMark" : $('input[name="fdSoapInfoForms[' + index+ '].fdFuncMark"]').val(),
			"fdSoapMainName" : $('input[name="fdSoapInfoForms[' + index+ '].fdSoapMainName"]').val(),
			"fdSoapMainId" : $('input[name="fdSoapInfoForms[' + index+ '].fdSoapMainId"]').val(),
			"fdInvokeType" : $('input[name="fdSoapInfoForms[' + index+ '].fdInvokeType"]').val(),
			"fdSoapXml" : $('input[name="fdSoapInfoForms[' + index + '].fdSoapXml"]').val(),
			"fdCompDbcpId":$('input[name="fdSoapInfoForms['+index+'].fdCompDbcpId"]').val(),
			"fdCompDbcpName":$('input[name="fdSoapInfoForms['+index+'].fdCompDbcpName"]').val(),
			"fdSyncTableXpath":$('input[name="fdSoapInfoForms['+index+'].fdSyncTableXpath"]').val(),
			"fdSyncType":$('input[name="fdSoapInfoForms['+index+'].fdSyncType"]').val(),
			"fdTimeColumn":$('input[name="fdSoapInfoForms['+index+'].fdTimeColumn"]').val(),
			"fdDelCondition":$('input[name="fdSoapInfoForms['+index+'].fdDelCondition"]').val()
		};
		dialogObject.cfg_model="view";//控制阅读
		if ((dialogObject.fdSoapMainId != null || dialogObject.fdSoapMainId != "")
				&& (dialogObject.fdSoapXml == null || dialogObject.fdSoapXml == "")) {
			var data = new KMSSData();
			data.SendToBean("ticSoapMappingFuncXmlService&fdSoapMainId="+ dialogObject.fdSoapMainId,
				function(rtnData) {
					if (rtnData.GetHashMapArray().length == 0)
						return;
					dialogObject.fdSoapXml = rtnData.GetHashMapArray()[0]["funcXml"];
					// window.showModalDialog(
					//		"../tic_soap_sync_temp_func/ticSoapSyncTempFunc_view.jsp?fdInvokeType=4",
					//		dialogObject,
					//		"dialogWidth=1000px;dialogHeight=600px");
					//resetFormEventField(dialogObject, index); 
					var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=1000px,height=600px,screenX=10px,screenY=10px,top=100px,left=100px";
					window.open("../tic_soap_sync_temp_func/ticSoapSyncTempFunc_view.jsp?fdInvokeType=4","",winStyle);
				}
			);
		} else {
			// window.showModalDialog("../tic_soap_sync_temp_func/ticSoapSyncTempFunc_view.jsp?fdInvokeType=4",
			//		dialogObject,
			//		"dialogWidth=1000px;dialogHeight=600px");
			//resetFormEventField(dialogObject, index);
			var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=1000px,height=600px,screenX=10px,screenY=10px,top=100px,left=100px";
			window.open("../tic_soap_sync_temp_func/ticSoapSyncTempFunc_view.jsp?fdInvokeType=4","",winStyle);
		}
	}
	
	
	function editFormEventFunction_callback(){
		//重置字段值
		resetFormEventField(dialogObject,dialogObject["_index"]);
	}
	
	
	//编辑函数后重置字段值
	function resetFormEventField(dialogObject, index) {
		$('input[name="fdSoapInfoForms[' + index + '].fdUse"]').val(dialogObject.fdUse),
		$('input[name="fdSoapInfoForms[' + index + '].fdLastDate"]').val(dialogObject.fdLastDate), 
		$('input[name="fdSoapInfoForms[' + index + '].fdQuartzId"]').val(dialogObject.fdQuartzId), 
		$('input[name="fdSoapInfoForms[' + index + '].fdFuncMark"]').val(dialogObject.fdFuncMark), 
		$('input[name="fdSoapInfoForms[' + index + '].fdQuartzTime"]').val(dialogObject.fdQuartzTime), 
		$('input[name="fdSoapInfoForms[' + index + '].fdFuncMark"]').val(dialogObject.fdFuncMark), 
		$('input[name="fdSoapInfoForms[' + index + '].fdSoapMainName"]').val(dialogObject.fdSoapMainName), 
		$('input[name="fdSoapInfoForms[' + index + '].fdSoapMainId"]').val(dialogObject.fdSoapMainId), 
		$('input[name="fdSoapInfoForms[' + index + '].fdInvokeType"]').val(dialogObject.fdInvokeType), 
		$('input[name="fdSoapInfoForms[' + index + '].fdSoapXml"]').val(dialogObject.fdSoapXml);
		$('input[name="fdSoapInfoForms[' + index + '].fdCompDbcpId"]').val(dialogObject.fdCompDbcpId);
		$('input[name="fdSoapInfoForms[' + index + '].fdCompDbcpName"]').val(dialogObject.fdCompDbcpName);
		$('input[name="fdSoapInfoForms[' + index + '].fdSyncTableXpath"]').val(dialogObject.fdSyncTableXpath);
		$('input[name="fdSoapInfoForms[' + index + '].fdSyncType"]').val(dialogObject.fdSyncType);
		$('input[name="fdSoapInfoForms[' + index + '].fdTimeColumn"]').val(dialogObject.fdTimeColumn);
		$('input[name="fdSoapInfoForms[' + index + '].fdDelCondition"]').val(dialogObject.fdDelCondition);
	}
</script>
