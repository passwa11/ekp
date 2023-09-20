<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
	Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|data.js");
</script>
<tr LKS_LabelName="${lfn:message('tic-core-common:ticCoreTransSett.mappingRelation')}">
	<td>
		<table id="TABLE_DocList" class="tb_normal" width="100%">
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.number"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.name"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdCategory"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdAppType"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdExplain"/></td>
			</tr>
			<!--基准行-->
			<tr KMSS_IsReferRow="1" style="display: none">
				<td KMSS_IsRowIndex="1"></td>
				<td>
				
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdId"/>
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdInvokeType"  value="3"/><!--0表示表单事件-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdSendType"  value="0"/>
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdUse"/> <!--是否启用-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdQuartzTime"/> <!--时间撮-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdQuartzId" value="${ticCoreSyncJobForm.fdId}"/> <!-- 定时任务id -->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdQuartzName" value="${ticCoreSyncJobForm.fdSubject}"/>  <!-- 定时任务名称 -->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdMappConfig"  /> 
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdCompDbcpId" value="${fdFuncInfoForm.fdCompDbcpId}"/><!--数据源ID-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdCompDbcpName" value="${fdFuncInfoForm.fdCompDbcpName}"/><!--数据源Name-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdSyncTableXpath" value="${fdFuncInfoForm.fdSyncTableXpath}"/><!--同步表xpath-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdSyncType" value="${fdFuncInfoForm.fdSyncType}"/><!--同步方式-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdTimeColumn" value="${fdFuncInfoForm.fdTimeColumn}"/><!--时间戳列-->
					<input type="hidden" name="fdFuncInfoForms[!{index}].fdDelCondition" value="${fdFuncInfoForm.fdDelCondition}"/><!--删除条件-->
					
					<xform:dialog required="true" propertyId="fdFuncInfoForms[!{index}].fdFuncBaseId" propertyName="fdFuncInfoForms[!{index}].fdFuncBaseName" showStatus="view"
							subject="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}"  style="width:35%;float:left">
					</xform:dialog>
				</td>
				<td>
				<input type="text" name="fdFuncInfoForms[!{index}].fdFuncBaseCate" value="${fdFuncInfoForm.fdFuncBaseCate}" class="inputread"  readOnly>
				</td>
				<td>
				<input type="text" name="fdFuncInfoForms[!{index}].fdFuncBaseType" value="${fdFuncInfoForm.fdFuncBaseType}" class="inputread"  readOnly>
				</td>
				<td>
				<input type="text" name="fdFuncInfoForms[!{index}].fdFuncMark" value="${fdFuncInfoForm.fdFuncMark}" >
				</td>
						<td><img src="${KMSS_Parameter_StylePath}icons/edit.gif"
							title="${lfn:message('tic-core-common:ticCoreCommon.check')}"
							onclick="editFormEventFunction(this.parentNode.parentNode.rowIndex-1);"
							style="cursor: pointer">
				</td>
			</tr>

			<!--内容行-->
			<c:forEach items="${ticCoreSyncJobForm.fdFuncInfoForms}"
				var="fdFuncInfoForms" varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td>${vstatus.index+1}</td>
					<td>			
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdId"  value="${fdFuncInfoForm.fdId}"/>
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdInvokeType" value="${fdFuncInfoForm.fdInvokeType}"/><!--0表示表单事件-->
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdSendType"  value="${fdFuncInfoForm.fdSendType}"/>
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdUse" value="${fdFuncInfoForm.fdUse}"/> <!--是否启用-->
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdQuartzTime" value="${fdFuncInfoForm.fdQuartzTime}"/> <!--时间撮-->
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdQuartzId" value="${ticCoreSyncJobForm.fdId}"/> <!-- 定时任务id -->
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdQuartzName" value="${ticCoreSyncJobForm.fdSubject}"/>  <!-- 定时任务名称 -->
							
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdCompDbcpId" value="${fdFuncInfoForm.fdCompDbcpId}"/><!--数据源ID-->
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdCompDbcpName" value="${fdFuncInfoForm.fdCompDbcpName}"/><!--数据源Name-->
							<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdSyncTableXpath" value="${fdFuncInfoForm.fdSyncTableXpath}"/><!--同步表xpath-->
						   <input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdMappConfig" value='${fdFuncInfoForm.fdMappConfig}' />			
						   <input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdTimeColumn" value="${fdFuncInfoForm.fdTimeColumn}"/><!--时间戳列-->
						   <input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdDelCondition" value="${fdFuncInfoForm.fdDelCondition}"/><!--删除条件-->
							<xform:dialog required="true" propertyId="fdFuncInfoForms[${vstatus.index}].fdFuncBaseId" propertyName="fdFuncInfoForms[${vstatus.index}].fdFuncBaseName" showStatus="view"
									subject="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}"  style="width:35%;float:left">
		                     </xform:dialog>	
					</td>
					<td>
						<input type="text" name="fdFuncInfoForms[${vstatus.index}].fdFuncBaseCate" value="${fdFuncInfoForm.fdFuncBaseCate}" class="inputread"  readOnly>
						</td>
						<td>
						<input type="text" name="fdFuncInfoForms[${vstatus.index}].fdFuncBaseType" value="${fdFuncInfoForm.fdFuncBaseType}" class="inputread"  readOnly>
						</td>
						<td>
						<input type="text" name="fdFuncInfoForms[${vstatus.index}].fdFuncMark" value="${fdFuncInfoForm.fdFuncMark}" >
						</td>
					<td><img
						src="${KMSS_Parameter_StylePath}icons/userinfo_icon.gif"
						title="${lfn:message('tic-core-common:ticCoreCommon.check')}" onclick="editFormEventFunction('${vstatus.index}');"
						style="cursor: pointer"> 
					</td>
				</tr>
			</c:forEach>

		</table></td>
<script>

var dialogObject = {};
	console.log("${ticCoreSyncJobForm.fdFuncInfoForms}");
	//编辑函数信息
	function editFormEventFunction(index) {
		dialogObject = {
			"fdUse" : $('input[name="fdFuncInfoForms[' + index + '].fdUse"]').val(),
			"fdQuartzId" : $('input[name="fdFuncInfoForms[' + index+ '].fdQuartzId"]').val(),
			"fdQuartzName" : $('input[name="fdFuncInfoForms[' + index+ '].fdQuartzName"]').val(),
			"fdQuartzTime" : $('input[name="fdFuncInfoForms[' + index+ '].fdQuartzTime"]').val(),
			"fdFuncMark" : $('input[name="fdFuncInfoForms[' + index+ '].fdFuncMark"]').val(),
			"fdFuncBaseId" : $('input[name="fdFuncInfoForms[' + index+ '].fdFuncBaseId"]').val(),
			"fdInvokeType" : $('input[name="fdFuncInfoForms[' + index+ '].fdInvokeType"]').val(),
			"fdMappConfig" : $('input[name="fdFuncInfoForms[' + index + '].fdMappConfig"]').val(),
			"fdCompDbcpId":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpId"]').val(),
			"fdCompDbcpName":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpName"]').val(),
			"fdSyncTableXpath":$('input[name="fdFuncInfoForms['+index+'].fdSyncTableXpath"]').val(),
			"fdSyncType":$('input[name="fdFuncInfoForms['+index+'].fdSyncType"]').val(),
			"fdTimeColumn":$('input[name="fdFuncInfoForms['+index+'].fdTimeColumn"]').val(),
			"fdDelCondition":$('input[name="fdFuncInfoForms['+index+'].fdDelCondition"]').val()
		};
		dialogObject.cfg_model="view";//控制阅读
		if ((dialogObject.fdFuncBaseId != null || dialogObject.fdFuncBaseId != "")
				&& (dialogObject.fdMappConfig == null || dialogObject.fdMappConfig == "")) {
			var data = new KMSSData();
			data.SendToBean("ticSoapMappingFuncXmlService&fdFuncBaseId="+ dialogObject.fdFuncBaseId,
				function(rtnData) {
					if (rtnData.GetHashMapArray().length == 0)
						return;
					dialogObject.fdMappConfig = rtnData.GetHashMapArray()[0]["funcXml"];
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
		$('input[name="fdFuncInfoForms[' + index + '].fdUse"]').val(dialogObject.fdUse),
		$('input[name="fdFuncInfoForms[' + index + '].fdLastDate"]').val(dialogObject.fdLastDate), 
		$('input[name="fdFuncInfoForms[' + index + '].fdQuartzId"]').val(dialogObject.fdQuartzId), 
		$('input[name="fdFuncInfoForms[' + index + '].fdFuncMark"]').val(dialogObject.fdFuncMark), 
		$('input[name="fdFuncInfoForms[' + index + '].fdQuartzTime"]').val(dialogObject.fdQuartzTime), 
		$('input[name="fdFuncInfoForms[' + index + '].fdFuncMark"]').val(dialogObject.fdFuncMark), 
		$('input[name="fdFuncInfoForms[' + index + '].fdFuncBaseId"]').val(dialogObject.fdFuncBaseId), 
		$('input[name="fdFuncInfoForms[' + index + '].fdInvokeType"]').val(dialogObject.fdInvokeType), 
		$('input[name="fdFuncInfoForms[' + index + '].fdMappConfig"]').val(dialogObject.fdMappConfig);
		$('input[name="fdFuncInfoForms[' + index + '].fdCompDbcpId"]').val(dialogObject.fdCompDbcpId);
		$('input[name="fdFuncInfoForms[' + index + '].fdCompDbcpName"]').val(dialogObject.fdCompDbcpName);
		$('input[name="fdFuncInfoForms[' + index + '].fdSyncTableXpath"]').val(dialogObject.fdSyncTableXpath);
		$('input[name="fdFuncInfoForms[' + index + '].fdSyncType"]').val(dialogObject.fdSyncType);
		$('input[name="fdFuncInfoForms[' + index + '].fdTimeColumn"]').val(dialogObject.fdTimeColumn);
		$('input[name="fdFuncInfoForms[' + index + '].fdDelCondition"]').val(dialogObject.fdDelCondition);
	}
</script>
