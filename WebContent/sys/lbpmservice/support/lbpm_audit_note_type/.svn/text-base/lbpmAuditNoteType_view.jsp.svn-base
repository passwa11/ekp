<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.view"  sidebar="auto">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				 <ui:button text="${ lfn:message('button.edit') }" order="2" onclick="editType();">
				 </ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				 <ui:button text="${ lfn:message('button.delete') }" order="2" onclick="deleteType();">
				 </ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
			 </ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<p class="txttitle">
			<bean:message  bundle="sys-lbpmservice-support" key="table.lbpmAuditNoteType"/>
		</p>
		<center>
			<table class="tb_normal" width=100%>
				<html:hidden name="lbpmAuditNoteTypeForm" property="fdId"/>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNoteType.fdName"/>
					</td>
					<td width=35% colspan="3">
						<c:out value="${lbpmAuditNoteTypeForm.fdName}" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNoteType.fdOrder"/>
					</td>
					<td width=35%>
						<c:out value="${lbpmAuditNoteTypeForm.fdOrder}" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNoteType.fdIsAvailable"/>
					</td>
					<td width=35%>
						<sunbor:enumsShow value="${lbpmAuditNoteTypeForm.fdIsAvailable}" enumsType="common_yesno" />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15% >
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNoteType.docCreator"/>
					</td>
					<td width=35%>
						<c:out value="${lbpmAuditNoteTypeForm.docCreatorName}" />
					</td>
					<td class="td_normal_title" width=15% >
						<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNoteType.docCreateTime"/>
					</td>
					<td width=35%>
						<c:out value="${lbpmAuditNoteTypeForm.docCreateTime}" />
					</td>
				</tr>
			</table>
		</center>
		<script>
			seajs.use(['sys/ui/js/dialog'], function(dialog) {
				window.dialog=dialog;
			});
			function deleteType(){
				dialog.confirm("${lfn:message('page.comfirmDelete')}",function(flag){
			    	if(flag==true){
			    		Com_OpenWindow('/sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=delete&fdId=${param.fdId}','_self');
			    	}else{
			    		return false;
				    }
			    }, "warn");
			};
			function editType() {
				Com_OpenWindow('/sys/lbpmservice/support/lbpm_audit_note_type/lbpmAuditNoteType.do?method=edit&fdId=${param.fdId}','_self');
			}
		</script>
	</template:replace>
</template:include>