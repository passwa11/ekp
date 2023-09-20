<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tr LKS_LabelName="<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.mappingRelation"/>">
			<td>
			<table id="TABLE_DocList" class="tb_normal" width="100%">
	<tr class="td_normal_title" >
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.number"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.name"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdCategory"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdAppType"/></td>
		<td><bean:message bundle="tic-core-sync" key="ticCoreSyncJob.fdExplain"/></td>
		<td>
			<img
			src="${KMSS_Parameter_StylePath}icons/add.gif" alt="<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.add"/>"
			onclick="add_row('TABLE_DocList')" style="cursor: pointer">
		</td>
	</tr>
	<!--基准行-->
	<tr KMSS_IsReferRow="1" style="display:none">
		<td KMSS_IsRowIndex="1">
		</td>
		<td>
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdId"/>
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdInvokeType"  value="0"/><!--0表示表单事件-->
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdSendType"  value="0"/>
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdUse"/> <!--是否启用-->
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdQuartzTime"/> <!--时间撮-->
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdQuartzId" value="${ticCoreSyncJobForm.fdId}"/> <!-- 定时任务id -->
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdQuartzName" value="${ticCoreSyncJobForm.fdSubject}"/>  <!-- 定时任务名称 -->
		
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdCompDbcpId" value="${fdFuncInfoForm.fdCompDbcpId}"/><!--数据源ID-->
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdCompDbcpName" value="${fdFuncInfoForm.fdCompDbcpName}"/><!--数据源Name-->
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdSyncTableXpath" value="${fdFuncInfoForm.fdSyncTableXpath}"/><!--同步表xpath-->
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdMappConfig"/>
		
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdInParam" />
		<input type="hidden" name="fdFuncInfoForms[!{index}].fdSyncType" value='${fdFuncInfoForm.fdSyncType}'/>
		
		<xform:dialog required="true" propertyId="fdFuncInfoForms[!{index}].fdFuncBaseId" propertyName="fdFuncInfoForms[!{index}].fdFuncBaseName" showStatus="edit"
				subject="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}" dialogJs="selectFunction(!{index})" style="float:left">
		</xform:dialog>
		</td>

		<td>
		<input type="text" name="fdFuncInfoForms[!{index}].fdFuncBaseCate" value="${fdFuncInfoForm.fdFuncBaseCate}" class="inputread"  readOnly>
		</td>
		<td>
		<input type="text" name="fdFuncInfoForms[!{index}].fdFuncBaseType" value="${fdFuncInfoForm.fdFuncBaseType}" class="inputread"  readOnly>
		</td>
		<td>
		<input type="text" name="fdFuncInfoForms[!{index}].fdFuncMark" value="${fdFuncInfoForm.fdFuncMark}"  class="inputsgl" >
		</td>
		<td>
			<img
			src="${KMSS_Parameter_StylePath}icons/edit.gif" alt="<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.edit"/>"
			onclick="editFormEventFunction(this.parentNode.parentNode.rowIndex-1);" style="cursor: pointer">
				<img
			src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.delete"/>"
			onclick="DocList_DeleteRow();" style="cursor: pointer">
		</td>
	</tr>
	
	<!--内容行-->
	<c:forEach items="${ticCoreSyncJobForm.fdFuncInfoForms}" var="fdFuncInfoForm" varStatus="vstatus">
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
		<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdMappConfig" value="${fdFuncInfoForm.fdMappConfigView}"/>
		<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdInParam" value="${fdFuncInfoForm.fdInParam}"/>
		
		<input type="hidden" name="fdFuncInfoForms[${vstatus.index}].fdSyncType" value='${fdFuncInfoForm.fdSyncTypeHtml}'/>
		
		<xform:dialog required="true" propertyId="fdFuncInfoForms[${vstatus.index}].fdFuncBaseId" propertyName="fdFuncInfoForms[${vstatus.index}].fdFuncBaseName" showStatus="edit"
				subject="${lfn:message('tic-core-common:ticCoreTransSett.fdFunction')}" dialogJs="selectFunction(${vstatus.index})" style="float:left">
		</xform:dialog>
		</td>

		<td>
		<input type="text" name="fdFuncInfoForms[${vstatus.index}].fdFuncBaseCate" value="${fdFuncInfoForm.fdFuncBaseCate}" class="inputread"  readOnly>
		</td>
		<td>
		<input type="text" name="fdFuncInfoForms[${vstatus.index}].fdFuncBaseType" value="${fdFuncInfoForm.fdFuncBaseType}" class="inputread"  readOnly>
		</td>
		<td>
		<input type="text" name="fdFuncInfoForms[${vstatus.index}].fdFuncMark" value="${fdFuncInfoForm.fdFuncMark}"  class="inputsgl">
		</td>
		<td>
			<img
			src="${KMSS_Parameter_StylePath}icons/edit.gif" alt="<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.edit"/>"
			onclick="editFormEventFunction(this.parentNode.parentNode.rowIndex-1);" style="cursor: pointer">
				<img
			src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="<bean:message bundle="tic-core-sync" key="ticCoreSyncJob.lang.delete"/>"
			onclick="DocList_DeleteRow();" style="cursor: pointer">
		</td>
	</tr>
	</c:forEach>
	
</table>
</td>
<script>
var oldFuncId = "";

function selectFunction(index) {
	oldFuncId = $('input[name="fdFuncInfoForms['+index+'].fdFuncBaseId"]').val();
	
	Dialog_TreeList(false,"fdFuncInfoForms["+index+"].fdFuncBaseId","fdFuncInfoForms["+index+"].fdFuncBaseName",";",
		           "ticCoreFindFunctionService&selectId=!{value}&type=cate&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}",
		           "业务分类",
		           "ticCoreFindFunctionService&selectId=!{value}&type=func&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}&noTrans=true&index="+index,
		           after_dialogSelect,
		           "ticCoreFindFunctionService&type=search&keyword=!{keyword}&fdAppType=${param.fdAppType}&fdEnviromentId=${param.fdEnviromentId}&noTrans=true",
		           null,null,null,
		           "选择函数");
}

function after_dialogSelect(rtn){
	if (!rtn) {
		return;
	}
	var data = rtn.GetHashMapArray();
	if (data && data.length > 0) {
		var funcType = data[0]["type"];
		var cate = data[0]["cate"];
		var index = data[0]["index"];
		var newFuncId = data[0]["id"];
		
		$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseCate"]').val(cate);
		if(funcType=="1"){
			  $('input[name="fdFuncInfoForms['+index+'].fdFuncBaseType"]').val("SAP");
		}else if(funcType=="3"){
			 $('input[name="fdFuncInfoForms['+index+'].fdFuncBaseType"]').val("SOAP");
		}else if(funcType=="4"){
			$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseType"]').val("JDBC");
		}else if(funcType=="5"){
			$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseType"]').val("REST");
		}
		
		if(newFuncId!=oldFuncId){
			$('input[name="fdFuncInfoForms['+index+'].fdSyncTableXpath"]').val("");
			$('input[name="fdFuncInfoForms['+index+'].fdMappConfig"]').val("");
			
		}
	}

}
var dialogObject = {};

function add_row(index)
{
		DocList_AddRow();
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
	var type=$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseType"]').val();
	if(!type)
		return ;
	if(type=="SOAP"){
		dialogObject={
				"fdUse":$('input[name="fdFuncInfoForms['+index+'].fdUse"]').val(),
				"fdQuartzId":$('input[name="fdFuncInfoForms['+index+'].fdQuartzId"]').val(),
				"fdQuartzName":$('input[name="fdFuncInfoForms['+index+'].fdQuartzName"]').val(),
				"fdQuartzTime":$('input[name="fdFuncInfoForms['+index+'].fdQuartzTime"]').val(),
				"fdFuncMark":$('input[name="fdFuncInfoForms['+index+'].fdFuncMark"]').val(),
				"fdFuncBaseName":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseName"]').val(),
				"fdFuncBaseId":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseId"]').val(),
				"fdInvokeType":$('input[name="fdFuncInfoForms['+index+'].fdInvokeType"]').val(),
				"fdMappConfig":$('input[name="fdFuncInfoForms['+index+'].fdMappConfig"]').val(),
				"fdCompDbcpId":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpId"]').val(),
				"fdCompDbcpName":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpName"]').val(),
				"fdSyncTableXpath":$('input[name="fdFuncInfoForms['+index+'].fdSyncTableXpath"]').val()
			};
			if(dialogObject.fdFuncBaseId!=null||dialogObject.fdFuncBaseId!=""){
				if(dialogObject.fdMappConfig==null||dialogObject.fdMappConfig==""){
					var data = new KMSSData();
					data.SendToBean("ticSoapMappingFuncXmlService&fdSoapMainId="+dialogObject.fdFuncBaseId,function (rtnData){
						if(rtnData.GetHashMapArray().length==0)return;
						dialogObject.fdMappConfig=rtnData.GetHashMapArray()[0]["funcXml"];
					});
				}
			};
			dialogObject["_index"] = index;
			dialogObject["fdEnviromentId"] = '${param.fdEnviromentId}';
			dialogObject.cfg_model="edit";//控制阅读
			var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=1000px,height=600px,screenX=10px,screenY=10px,top=100px,left=100px";
			window.open(Com_Parameter.ContextPath+"tic/soap/sync/tic_soap_sync_temp_func/ticSoapSyncTempFunc_edit.jsp?fdInvokeType=4","",winStyle);
	}else if(type=="SAP"){
		dialogObject={
				"fdSendType":$('input[name="fdFuncInfoForms['+index+'].fdSendType"]').val(),
				"fdUse":$('input[name="fdFuncInfoForms['+index+'].fdUse"]').val(),
				"fdQuartzId":$('input[name="fdFuncInfoForms['+index+'].fdQuartzId"]').val(),
				"fdQuartzName":$('input[name="fdFuncInfoForms['+index+'].fdQuartzName"]').val(),
				"fdQuartzTime":$('input[name="fdFuncInfoForms['+index+'].fdQuartzTime"]').val(),
				"fdFuncMark":$('input[name="fdFuncInfoForms['+index+'].fdFuncMark"]').val(),
				"fdFuncBaseName":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseName"]').val(),
				"fdFuncBaseId":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseId"]').val(),
				"fdInvokeType":$('input[name="fdFuncInfoForms['+index+'].fdInvokeType"]').val(),
				"fdMappConfig":$('input[name="fdFuncInfoForms['+index+'].fdMappConfig"]').val(),//json字符串
				"fdCompDbcpId":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpId"]').val(),
				"fdCompDbcpName":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpName"]').val(),
				"fdSyncTableXpath":$('input[name="fdFuncInfoForms['+index+'].fdSyncTableXpath"]').val()
				};
		dialogObject["_index"] = index;
		dialogObject["fdEnviromentId"] = '${param.fdEnviromentId}';
	if(dialogObject.fdFuncBaseId!=null||dialogObject.fdFuncBaseId!=""){
		if(dialogObject.fdMappConfig==null||dialogObject.fdMappConfig==""){
		var data = new KMSSData();
		data.SendToBean("ticSapMappingFuncXmlService&fdRfcSettingId="+dialogObject.fdFuncBaseId,function (rtnData){
			if(rtnData.GetHashMapArray().length==0)return;
			dialogObject.fdMappConfig=rtnData.GetHashMapArray()[0]["funcXml"];
		});
	}
	};
	dialogObject.cfg_model="edit";//控制阅读
		var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=1000px,height=600px,screenX=10px,screenY=10px,top=100px,left=100px";
		window.open(Com_Parameter.ContextPath+"tic/sap/sync/tic_sap_sync_temp_func/ticSapSyncTempFunc_edit.jsp?fdInvokeType=4","",winStyle);
	}else if(type=="JDBC"){
		dialogObject={
				"fdUse":$('input[name="fdFuncInfoForms['+index+'].fdUse"]').val(),
				"fdSyncType":$('input[name="fdFuncInfoForms['+index+'].fdSyncType"]').val(),
				"fdQuartzId":$('input[name="fdFuncInfoForms['+index+'].fdQuartzId"]').val(),
				"fdQuartzName":$('input[name="fdFuncInfoForms['+index+'].fdQuartzName"]').val(),
				"fdQuartzTime":$('input[name="fdFuncInfoForms['+index+'].fdQuartzTime"]').val(),
				"fdCompDbcpId":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpId"]').val(),
				"fdFuncMark":$('input[name="fdFuncInfoForms['+index+'].fdFuncMark"]').val(),
				"fdFuncBaseName":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseName"]').val(),
				"fdFuncBaseId":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseId"]').val(),
				"fdMappConfig":$('input[name="fdFuncInfoForms['+index+'].fdMappConfig"]').val(),//json字符串
				"fdInParam":$('input[name="fdFuncInfoForms['+index+'].fdInParam"]').val()//json字符串
				};
		dialogObject["_index"] = index;
		dialogObject["fdEnviromentId"] = '${param.fdEnviromentId}';
		var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=1000px,height=600px,screenX=10px,screenY=10px,top=100px,left=100px";
		window.open(Com_Parameter.ContextPath+"tic/jdbc/tic_jdbc_mapp_manage/ticJdbcMappManage_edit.jsp","",winStyle);
	}else if(type=="REST"){
		dialogObject={
				"fdUse":$('input[name="fdFuncInfoForms['+index+'].fdUse"]').val(),
				"fdQuartzId":$('input[name="fdFuncInfoForms['+index+'].fdQuartzId"]').val(),
				"fdQuartzName":$('input[name="fdFuncInfoForms['+index+'].fdQuartzName"]').val(),
				"fdQuartzTime":$('input[name="fdFuncInfoForms['+index+'].fdQuartzTime"]').val(),
				"fdFuncMark":$('input[name="fdFuncInfoForms['+index+'].fdFuncMark"]').val(),
				"fdFuncBaseName":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseName"]').val(),
				"fdFuncBaseId":$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseId"]').val(),
				"fdInvokeType":$('input[name="fdFuncInfoForms['+index+'].fdInvokeType"]').val(),
				"fdMappConfig":$('input[name="fdFuncInfoForms['+index+'].fdMappConfig"]').val(),
				"fdCompDbcpId":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpId"]').val(),
				"fdCompDbcpName":$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpName"]').val(),
				"fdSyncTableXpath":$('input[name="fdFuncInfoForms['+index+'].fdSyncTableXpath"]').val()
			};
		dialogObject["fdEnviromentId"] = '${param.fdEnviromentId}';
			if(dialogObject.fdFuncBaseId!=null||dialogObject.fdFuncBaseId!=""){
				if(dialogObject.fdMappConfig==null||dialogObject.fdMappConfig==""){
					var data = new KMSSData();
					data.SendToBean("ticRestMappingFuncXmlService&fdRestMainId="+dialogObject.fdFuncBaseId,function (rtnData){
						if(rtnData.GetHashMapArray().length==0)return;
						dialogObject.fdMappConfig=rtnData.GetHashMapArray()[0]["fdReqParam"];
					});
				}
			};
			dialogObject["_index"] = index;
			dialogObject.cfg_model="edit";//控制阅读
			var winStyle= "resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no,width=1000px,height=600px,screenX=10px,screenY=10px,top=100px,left=100px";
			window.open(Com_Parameter.ContextPath+"tic/rest/connector/sync/ticRestSyncTempFunc_edit.jsp?fdInvokeType=4","",winStyle);
	}
}

function editFormEventFunction_callback(){
	//重置字段值
	resetFormEventField(dialogObject,dialogObject["_index"]);
}

//编辑函数后重置字段值
function resetFormEventField(funcObject,index){
	$('input[name="fdFuncInfoForms['+index+'].fdUse"]').val(funcObject.fdUse),
	$('input[name="fdFuncInfoForms['+index+'].fdQuartzId"]').val(funcObject.fdQuartzId),
	$('input[name="fdFuncInfoForms['+index+'].fdFuncMark"]').val(funcObject.fdFuncMark),
	$('input[name="fdFuncInfoForms['+index+'].fdQuartzTime"]').val(funcObject.fdQuartzTime),
	$('input[name="fdFuncInfoForms['+index+'].fdFuncMark"]').val(funcObject.fdFuncMark),
	$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseName"]').val(funcObject.fdFuncBaseName),
	$('input[name="fdFuncInfoForms['+index+'].fdFuncBaseId"]').val(funcObject.fdFuncBaseId),
	$('input[name="fdFuncInfoForms['+index+'].fdInvokeType"]').val(funcObject.fdInvokeType),
	$('input[name="fdFuncInfoForms['+index+'].fdMappConfig"]').val(funcObject.fdMappConfig);
	$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpId"]').val(funcObject.fdCompDbcpId);
	$('input[name="fdFuncInfoForms['+index+'].fdCompDbcpName"]').val(funcObject.fdCompDbcpName);
	$('input[name="fdFuncInfoForms['+index+'].fdSyncTableXpath"]').val(funcObject.fdSyncTableXpath);
	$('input[name="fdFuncInfoForms['+index+'].fdSyncType"]').val(funcObject.fdSyncType);
	$('input[name="fdFuncInfoForms['+index+'].fdInParam"]').val(funcObject.fdInParam);
	console.log(funcObject);
}
</script>
			
		
	
