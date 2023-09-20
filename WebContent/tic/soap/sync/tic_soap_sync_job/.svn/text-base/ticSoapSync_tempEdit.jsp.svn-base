<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tr LKS_LabelName="<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.mappingRelation"/>">
			<td>
			<table id="TABLE_DocList" class="tb_normal" width="100%">
	<tr class="td_normal_title" >
	    <td rowspan="100" align="center"><bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.syncJob"/></td>
		<td><bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.number"/></td>
		<td>${lfn:message('tic-soap-connector:erpEkpSoap.BAPI')}<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.name"/></td>
		<td><bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.fdUseExplain"/></td>
		<td>
			<img
			src="${KMSS_Parameter_StylePath}icons/add.gif" alt="<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.add"/>"
			onclick="add_row('TABLE_DocList')" style="cursor: pointer">
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1">
		</td>
		<td>
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdId"/>
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdSoapMainId" value=""/>
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdInvokeType"  value="0"/><!--0表示表单事件-->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdLastDate"/><!--传入参数json字符串-->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdSoapXml"/>
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdUse"/> <!--是否启用-->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdQuartzTime"/> <!--时间撮-->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdQuartzId" value="${ticSoapSyncJobForm.fdId}"/> <!-- 定时任务id -->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdQuartzName" value="${ticSoapSyncJobForm.fdSubject}"/>  <!-- 定时任务名称 -->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdLastDate" value="${fdSoapInfoForm.fdLastDate}"/><!--最后执行时间-->
		
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdCompDbcpId" value="${fdSoapInfoForm.fdCompDbcpId}"/><!--数据源ID-->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdCompDbcpName" value="${fdSoapInfoForm.fdCompDbcpName}"/><!--数据源Name-->
		<input type="hidden" name="fdSoapInfoForms[!{index}].fdSyncTableXpath" value="${fdSoapInfoForm.fdSyncTableXpath}"/><!--同步表xpath-->
		
		<input type="text" name="fdSoapInfoForms[!{index}].fdSoapMainName" value="" readOnly class="inputread" >
		
		</td>
		<td><input type="text" name="fdSoapInfoForms[!{index}].fdFuncMark" value=""  class="inputsgl" ></td>
		<td>
			<img
			src="${KMSS_Parameter_StylePath}icons/edit.gif" alt="<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.edit"/>"
			onclick="editFormEventFunction(this.parentNode.parentNode.rowIndex-1);" style="cursor: pointer">
				<img
			src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.delete"/>"
			onclick="DocList_DeleteRow();" style="cursor: pointer">
		</td>
	</tr>
	
	<!--内容行-->
	<c:forEach items="${ticSoapSyncJobForm.fdSoapInfoForms}" var="fdSoapInfoForm" varStatus="vstatus">
	<tr KMSS_IsContentRow="1">
		<td>${vstatus.index+1}</td>
		<td>
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdUse" value="${fdSoapInfoForm.fdUse}"/> <!--是否启用-->
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdQuartzTime" value="${fdSoapInfoForm.fdQuartzTime}"/> <!--时间-->
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdQuartzId" value="${ticSoapSyncJobForm.fdId}"/> <!-- 定时任务id -->
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdQuartzName" value="${ticSoapSyncJobForm.fdSubject}"/> <!-- 定时任务名称 -->
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdId" value="${fdSoapInfoForm.fdId}"/>
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdSoapMainId" value="${fdSoapInfoForm.fdSoapMainId}"/>
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdInvokeType"  value="${fdSoapInfoForm.fdInvokeType}"/>
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdSoapXml" value='${fdSoapInfoForm.fdSoapXmlView}'/><!--传入参数json字符串-->
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdLastDate" value="${fdSoapInfoForm.fdLastDate}"/><!--最后执行时间-->
		
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdCompDbcpId" value="${fdSoapInfoForm.fdCompDbcpId}"/><!--数据源ID-->
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdCompDbcpName" value="${fdSoapInfoForm.fdCompDbcpName}"/><!--数据源Name-->
		<input type="hidden" name="fdSoapInfoForms[${vstatus.index}].fdSyncTableXpath" value="${fdSoapInfoForm.fdSyncTableXpath}"/><!--同步表xpath-->
		
		<input type="text" name="fdSoapInfoForms[${vstatus.index}].fdSoapMainName" value="${fdSoapInfoForm.fdSoapMainName}" readOnly class="inputread">
		</td>
		<td><input type="text" name="fdSoapInfoForms[${vstatus.index}].fdFuncMark" value="${fdSoapInfoForm.fdFuncMark}" class="inputsgl" ></td>
		<td>
			<img src="${KMSS_Parameter_StylePath}icons/edit.gif" alt="<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.edit"/>"
			onclick="editFormEventFunction(this.parentNode.parentNode.rowIndex-1);" style="cursor: pointer">
		    <img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="<bean:message bundle="tic-soap-sync" key="ticSoapSyncJob.lang.delete"/>"
			onclick="DocList_DeleteRow();" style="cursor: pointer">
		</td>
	</tr>
	</c:forEach>
	
</table>
</td>
<script type="text/javascript" >

var dialogObject = {};

function add_row(index)
{
	var length=document.getElementById(index).rows.length;
	//if(length<2){
		DocList_AddRow();
	//}
}


//得到函数对应xml格式数据信息
<%-- 
function getXml(){
	var fdSoapMainId=$("#fdSoapMainId").val();
	if(fdSoapMainId==null||fdSoapMainId=="")return;
	var data = new KMSSData();
	data.SendToBean("ticSoapMappingFuncXmlService&fdSoapMainId="+fdSoapMainId,resetTable);
}
--%>
//编辑函数信息
function editFormEventFunction(index){
	dialogObject={
		"fdUse":$('input[name="fdSoapInfoForms['+index+'].fdUse"]').val(),
		"fdLastDate":$('input[name="fdSoapInfoForms['+index+'].fdLastDate"]').val(),
		"fdQuartzId":$('input[name="fdSoapInfoForms['+index+'].fdQuartzId"]').val(),
		"fdQuartzName":$('input[name="fdSoapInfoForms['+index+'].fdQuartzName"]').val(),
		"fdQuartzTime":$('input[name="fdSoapInfoForms['+index+'].fdQuartzTime"]').val(),
		"fdFuncMark":$('input[name="fdSoapInfoForms['+index+'].fdFuncMark"]').val(),
		"fdSoapMainName":$('input[name="fdSoapInfoForms['+index+'].fdSoapMainName"]').val(),
		"fdSoapMainId":$('input[name="fdSoapInfoForms['+index+'].fdSoapMainId"]').val(),
		"fdInvokeType":$('input[name="fdSoapInfoForms['+index+'].fdInvokeType"]').val(),
		"fdSoapXml":$('input[name="fdSoapInfoForms['+index+'].fdSoapXml"]').val(),
		"fdCompDbcpId":$('input[name="fdSoapInfoForms['+index+'].fdCompDbcpId"]').val(),
		"fdCompDbcpName":$('input[name="fdSoapInfoForms['+index+'].fdCompDbcpName"]').val(),
		"fdSyncTableXpath":$('input[name="fdSoapInfoForms['+index+'].fdSyncTableXpath"]').val()
	};
	if(dialogObject.fdSoapMainId!=null||dialogObject.fdSoapMainId!=""){
		if(dialogObject.fdSoapXml==null||dialogObject.fdSoapXml==""){
			var data = new KMSSData();
			data.SendToBean("ticSoapMappingFuncXmlService&fdSoapMainId="+dialogObject.fdSoapMainId,function (rtnData){
				if(rtnData.GetHashMapArray().length==0)return;
				dialogObject.fdSoapXml=rtnData.GetHashMapArray()[0]["funcXml"];
			});
		}
	};
	dialogObject["_index"] = index;
	dialogObject.cfg_model="edit";//控制阅读
	
	//window.showModalDialog("../tic_soap_sync_temp_func/ticSoapSyncTempFunc_edit.jsp?fdInvokeType=4", funcObject,"dialogWidth=1000px;dialogHeight=600px");
	
	var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=1000px,height=600px,screenX=10px,screenY=10px,top=100px,left=100px";
	window.open("../tic_soap_sync_temp_func/ticSoapSyncTempFunc_edit.jsp?fdInvokeType=4","",winStyle);
	
	
}

function editFormEventFunction_callback(){
	//重置字段值
	resetFormEventField(dialogObject,dialogObject["_index"]);
}

//编辑函数后重置字段值
function resetFormEventField(funcObject,index){
	$('input[name="fdSoapInfoForms['+index+'].fdUse"]').val(funcObject.fdUse),
	$('input[name="fdSoapInfoForms['+index+'].fdLastDate"]').val(funcObject.fdLastDate), 
	$('input[name="fdSoapInfoForms['+index+'].fdQuartzId"]').val(funcObject.fdQuartzId),
	$('input[name="fdSoapInfoForms['+index+'].fdFuncMark"]').val(funcObject.fdFuncMark),
	$('input[name="fdSoapInfoForms['+index+'].fdQuartzTime"]').val(funcObject.fdQuartzTime),
	$('input[name="fdSoapInfoForms['+index+'].fdFuncMark"]').val(funcObject.fdFuncMark),
	$('input[name="fdSoapInfoForms['+index+'].fdSoapMainName"]').val(funcObject.fdSoapMainName),
	$('input[name="fdSoapInfoForms['+index+'].fdSoapMainId"]').val(funcObject.fdSoapMainId),
	$('input[name="fdSoapInfoForms['+index+'].fdInvokeType"]').val(funcObject.fdInvokeType),
	$('input[name="fdSoapInfoForms['+index+'].fdSoapXml"]').val(funcObject.fdSoapXml);
	$('input[name="fdSoapInfoForms['+index+'].fdCompDbcpId"]').val(funcObject.fdCompDbcpId);
	$('input[name="fdSoapInfoForms['+index+'].fdCompDbcpName"]').val(funcObject.fdCompDbcpName);
	$('input[name="fdSoapInfoForms['+index+'].fdSyncTableXpath"]').val(funcObject.fdSyncTableXpath);
}
</script>
			
		
	
