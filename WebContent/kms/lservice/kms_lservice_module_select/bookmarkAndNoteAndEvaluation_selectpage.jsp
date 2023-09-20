<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="title">${lfn:message('kms-lservice:mportal.select.selectType')}</template:replace>
	<template:replace name="body">
	<table class="tb_normal" style="width:90%;margin-top:25px;">
		<kmss:ifModuleExist path="/sys/bookmark">
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-lservice:mportal.select.showBookmark')}
				</td>
				<td width="35%">
					<xform:radio showStatus="edit" property="showBookmark" value="${param.showBookmark}">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
			</tr>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/kms/common">
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-lservice:mportal.select.showNote')}
				</td>
				<td width="85%" colspan="3">
					<xform:radio showStatus="edit" property="showNote" value="${param.showNote}">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
			</tr>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/sys/evaluation">
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('kms-lservice:mportal.select.showEvaluation')}
				</td>
				<td width="85%" colspan="3">
					<xform:radio showStatus="edit" property="showEvaluation"  value="${param.showEvaluation}">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
			</tr>
		</kmss:ifModuleExist>
	</table>
	
	<script>
		function submitSelected() {
			var showBookmark = LUI.$("input[name='showBookmark']:checked").val();
			var showNote = LUI.$("input[name='showNote']:checked").val();
			var showEvaluation = LUI.$("input[name='showEvaluation']:checked").val();
		
			if(showBookmark == "false"){
				showBookmark = false;
			}
			
			if(showNote == "false"){
				showNote = false;
			}
			
			if(showEvaluation == "false"){
				showEvaluation = false;
			}
			
			if(!showBookmark && !showNote && !showEvaluation){
				seajs.use("lui/dialog",function(dialog){
					dialog.alert("${lfn:message('kms-lservice:mportal.select.atLeastSelectOne')}");
				})
				return;
			}
			var fdId = "";
			var fdName = "";
			
			if(showBookmark && showBookmark != "false"){
				fdId = "bookmark";
				fdName += "${lfn:message('kms-lservice:mobile.msg.bookmark')}";
			}
			if(showNote && showNote != "false"){
				if(fdId){
					fdId += ",note";
				}else{
					fdId += "note";
				}		
				if(fdName){
					fdName += "," + "${lfn:message('kms-lservice:mobile.msg.note')}";
				}else{
					fdName += "${lfn:message('kms-lservice:mobile.msg.note')}";
				}				
			}			
			if(showEvaluation && showEvaluation != "false"){
				if(fdId){
					fdId += ",evaluation";
				}else{
					fdId += "evaluation";
				}		
				if(fdName){
					fdName += "," + "${lfn:message('kms-lservice:mobile.msg.evaluation')}";
				}else{
					fdName += "${lfn:message('kms-lservice:mobile.msg.evaluation')}";
				}				
			}
			
			window.$dialog.hide({
				"fdId":fdId, 
				"fdName":fdName
			});
		}
	</script>
				
	<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom: 5px;right: 5px;" class="lui_dialog_common_buttons clearfloat">
		<ui:button onclick="submitSelected();" text="${lfn:message('kms-lservice:mportal.select.ensure')}" />
	</div>
	
	</template:replace>
</template:include>
