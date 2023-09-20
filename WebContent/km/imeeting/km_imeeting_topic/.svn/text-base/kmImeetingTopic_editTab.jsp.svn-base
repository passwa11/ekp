<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:replace name="content"> 
<script>
    Com_IncludeFile("dialog.js|jquery.js");
    Com_IncludeFile("sysUnitDialog.js", Com_Parameter.ContextPath+ "sys/unit/resource/js/", "js", true);
</script>
<script type="text/javascript">

$(document).ready(function(){
	resetNewDialog("fdAttendUnitIds","fdAttendUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"","",null);
	resetNewDialog("fdListenUnitIds","fdListenUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"","",null);
	if("${ kmImeetingTopicForm.method_GET}" != 'add'){
		resetNewDialog("fdAttendUnitIds","fdAttendUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"${kmImeetingTopicForm.fdAttendUnitIds}","${kmImeetingTopicForm.fdAttendUnitNames}",null);
		resetNewDialog("fdListenUnitIds","fdListenUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"${kmImeetingTopicForm.fdListenUnitIds}","${kmImeetingTopicForm.fdListenUnitNames}",null);
	}
});

function commitMethod(commitType, saveDraft){
	var formObj = document.kmImeetingTopicForm;
	var docStatus = document.getElementsByName("docStatus")[0];
	if(saveDraft=="true"){
		docStatus.value="10";
	}else{
		docStatus.value="20";
	}
	if('save'==commitType){
		Com_Submit(formObj, commitType,'fdId');
    }else{
    	Com_Submit(formObj, commitType);
    }
}
function confirmChgCate(modeName,url,canClose){
	seajs.use(['sys/ui/js/dialog'],	function(dialog) {
		dialog.confirm("${lfn:message('km-doc:kmDoc.changeCate.confirmMsg')}",function(flag){
		if(flag==true){
			window.changeDocCate(modeName,url,canClose);
		}},"warn");
	});
};
function changeDocCate(modeName,url,canClose,flag) {
	if(modeName==null || modeName=='' || url==null || url=='')
		return;
	seajs.use(['sys/ui/js/dialog'],	function(dialog) {
		dialog.simpleCategoryForNewFile(modeName,url,false,function(rtn) {
			// 门户无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
			if (!rtn && flag == "portlet")
				window.close();
		},null,null,"_self",canClose);
	});
};

function rilegou(rtn,obj){
	if(rtn[0]!="" && rtn[1] != ""){
		var fdMaterialStaffValues=[];
		var fdOrgId = rtn[0];
		var fdOrgName = rtn[1];
		fdMaterialStaffObj={
			id:fdOrgId,
			name:fdOrgName
		};
		fdMaterialStaffValues.push(fdMaterialStaffObj);
		var fdMaterialStaffAddress = Address_GetAddressObj("fdMaterialStaffName");
		fdMaterialStaffAddress.reset(";",ORG_TYPE_PERSON,false,fdMaterialStaffValues);
		
		 var url="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=getDept"; 
		 $.ajax({   
		     url:url,  
		     data:{fdOrgId:fdOrgId},   
		     async:false,    //用同步方式 
		     success:function(data){
		    	 var results =  eval("("+data+")");
		    	var values=[];
			    if(results['deptId'] && results['deptName']){
			    	var obj={
			    		id:	results['deptId'],
			    		name:results['deptName']
			    	};
			    	values.push(obj);
				}
				var address = Address_GetAddressObj("fdChargeUnitName");
				address.reset(";",ORG_TYPE_ORGORDEPT,false,values);
			}    
	    });
	}
}

</script>
<c:if test="${param.approveModel ne 'right'}">
	<form name="kmImeetingTopicForm" method="post" action ="${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_topic/kmImeetingTopic.do">
</c:if>
	<div class="lui_form_content_frame" style="padding-top:20px">
		<table class="tb_simple" width=100%>
			<html:hidden property="fdId" />
			<html:hidden property="fdModelId" />
			<html:hidden property="fdModelName" />
			<html:hidden property="docStatus" />
			<html:hidden property="docCreateTime"/>
			<html:hidden property="method_GET" />
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.docSubject"/>
				</td>
				<td colspan="3">
					<xform:text isLoadDataDict="false" validators="maxLength(200) senWordsValidator(kmImeetingTopicForm)" 
						property="docSubject" required="true" subject="${lfn:message('sys-doc:sysDocBaseInfo.docSubject')}" className="inputsgl" style="width:97%;"/>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdTopicCategory"/>
				</td>
				<td  width="35%">	
					<html:hidden property="fdTopicCategoryId" /> 
					<bean:write	name="kmImeetingTopicForm" property="fdTopicCategoryName" />
					<c:if test="${kmImeetingTopicForm.method_GET=='add'}">
						&nbsp;&nbsp;
						<a href="javascript:confirmChgCate('com.landray.kmss.km.imeeting.model.KmImeetingTopicCategory','/km/imeeting/km_imeeting_topic/kmImeetingTopic.do?method=add&fdTemplateId=!{id}&fdModelId=${JsParam.fdModelId}&fdModelName=${JsParam.fdModelName}',true);" class="com_btn_link">
							<bean:message bundle="km-imeeting" key="kmImeetingTopic.modify.issue.classification" />
						</a>
					</c:if>
					
				</td>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdNo"/>
				</td>
				<td  width="35%">
					<c:if test="${kmImeetingTopicForm.docStatus==10 || kmImeetingTopicForm.docStatus==null || kmImeetingTopicForm.docStatus=='' }">
					   <bean:message bundle="km-imeeting" key="tips.automatically.generated.after.submission" />
					</c:if>
					<c:if test="${kmImeetingTopicForm.fdNo!='' && kmImeetingTopicForm.fdNo!=null && kmImeetingTopicForm.docStatus!=10 }">
                   	 	${ kmImeetingTopicForm.fdNo}
                	</c:if>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdReporter"/>
				</td>
				<td  width="35%">
					<xform:address isLoadDataDict="false"  required="true" style="width:94%" propertyId="fdReporterId" propertyName="fdReporterName" orgType='ORG_TYPE_PERSON' onValueChange="rilegou"></xform:address>
				</td>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdChargeUnit"/>
				</td>
				<td  width="35%">
					<xform:address isLoadDataDict="false" required="true" subject="承办部门" style="width:94%" propertyId="fdChargeUnitId" propertyName="fdChargeUnitName" orgType='ORG_TYPE_ORGORDEPT'></xform:address>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdMaterialStaff"/>
				</td>
				<td  width="35%">
					<xform:address isLoadDataDict="false" required="true" style="width:94%" propertyId="fdMaterialStaffId" propertyName="fdMaterialStaffName" orgType='ORG_TYPE_PERSON'></xform:address>
				</td>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdSourceSubject"/>
				</td>
				<td  width="35%">
					<c:choose>
						<c:when test="${ kmImeetingTopicForm.method_GET == 'add' }">
							<c:if test="${empty kmImeetingTopic.fdSourceSubject }">
								<xform:text isLoadDataDict="false" validators="maxLength(200)" property="fdSourceSubject"  className="inputsgl" style="width:93%;"/>
							</c:if>
							<c:if test="${not empty kmImeetingTopic.fdSourceSubject }">
								<xform:text isLoadDataDict="false" validators="maxLength(200)" showStatus="readOnly" property="fdSourceSubject"  className="inputsgl" style="width:93%;"/>
							</c:if>
						</c:when>
						<c:otherwise>
							<xform:text isLoadDataDict="false" validators="maxLength(200)" property="fdSourceSubject"  className="inputsgl" style="width:93%;"/>
						</c:otherwise>
					</c:choose>	
					
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdAttendUnit"/>
				</td>
				<td  width="85%" colspan="3">
					 <xform:dialog propertyId="fdAttendUnitIds" propertyName="fdAttendUnitNames" style="width:97%" showStatus="edit" textarea="true"  useNewStyle="true">
				       Dialog_UnitTreeList(true, 'fdAttendUnitIds', 'fdAttendUnitNames', ';', 'kmImissiveUnitCategoryTreeService&parentId=!{value}', '单位选择', 'kmImissiveUnitListWithAuthService&parentId=!{value}&type=allUnit',null,'kmImissiveUnitListWithAuthService&fdKeyWord=!{keyword}&type=allUnit');
				    </xform:dialog>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdListenUnit"/>
				</td>
				<td  width="85%" colspan="3">
					 <xform:dialog propertyId="fdListenUnitIds" propertyName="fdListenUnitNames" style="width:97%" showStatus="edit" textarea="true"  useNewStyle="true">
				       Dialog_UnitTreeList(true, 'fdListenUnitIds', 'fdListenUnitNames', ';', 'kmImissiveUnitCategoryTreeService&parentId=!{value}', '单位选择', 'kmImissiveUnitListWithAuthService&parentId=!{value}&type=allUnit',null,'kmImissiveUnitListWithAuthService&fdKeyWord=!{keyword}&type=allUnit');
				    </xform:dialog>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.issue.text" />
				</td>
				<td width="85%" colspan="3">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="mainonline"/>
						<c:param  name="fdMulti" value="false" />
						<c:param name="uploadAfterSelect" value="true" />  
						<c:param name="enabledFileType" value="doc,docx,xls,xlsx,ppt,pptx,xlc,mppx,xlcx,wps,et,vsd,rtf,pdf" />
						<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId }" />
						<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
					</c:import>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.issue.material" />
				</td>
				<td width="85%" colspan="3">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="attachment" />
						<c:param name="uploadAfterSelect" value="true" />  
						<c:param name="enabledFileType" value="doc,docx,xls,xlsx,ppt,pptx,xlc,mppx,xlcx,wps,et,vsd,rtf,pdf" />
						<c:param name="fdModelId" value="${kmImeetingTopicForm.fdId }" />
						<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
					</c:import>
				</td>
			</tr>	
			<tr>
				<td width="15%" class="td_normal_title">
					<bean:message bundle="km-imeeting" key="kmImeetingTopic.fdContent"/>
				</td>
				<td  width="85%" colspan="3">
					<xform:textarea property="fdContent" style="width:97.5%;height:80px" validators="senWordsValidator(kmImeetingTopicForm)"/>
				</td>
			</tr>
		</table>
	</div>
	<c:choose>	
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'
				var-supportExpand="true" var-expand="true">
				<%--权限机制 --%>
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingTopicForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
				</c:import>
				<c:if test="${kmImeetingTopicForm.docStatus != '30'}">
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingTopicForm" />
					<c:param name="fdKey" value="mainTopic" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
				</c:import>
				</c:if>
			</ui:tabpanel>	
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false">
				
				<%--流程机制 --%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingTopicForm" />
					<c:param name="fdKey" value="mainTopic" />
				</c:import>
				<%--权限机制 --%>
				<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="kmImeetingTopicForm" />
					<c:param name="moduleModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingTopic" />
				</c:import>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>
<c:if test="${param.approveModel ne 'right'}">
</form>
</c:if>
<script language="JavaScript">
	var validation = $KMSSValidation(document.forms['kmImeetingTopicForm']);
</script>
</template:replace>
<c:if test="${param.approveModel eq 'right'}">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<c:choose>
				<c:when test="${kmImeetingTopicForm.docStatus>='30' || kmImeetingTopicForm.docStatus=='00'}">
					<!-- 基本信息-->
					<c:import url="/km/imeeting/km_imeeting_topic/kmImeetingTopic_viewBaseInfo.jsp" charEncoding="UTF-8">
					</c:import>
				</c:when>
				<c:otherwise>
					<%--流程--%>
					<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="kmImeetingTopicForm" />
						<c:param name="fdKey" value="mainTopic" />
						<c:param name="showHistoryOpers" value="true" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
					</c:import>
				</c:otherwise>
			</c:choose>
		</ui:tabpanel>
	</template:replace>
</c:if>