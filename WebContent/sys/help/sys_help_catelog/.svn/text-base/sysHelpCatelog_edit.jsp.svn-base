<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super />
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/help/sys_help_catelog/css/catelog.css?s_cache=${LUI_Cache}">
		<%@ include file="/sys/help/sys_help_catelog/sysHelpCatelog_edit_js.jsp"%>  
		<script >
			Com_IncludeFile("validation.js|plugin.js|eventbus.js|xform.js", null, "js");
			seajs.use(['theme!form']);
		</script>
		<style>
			.tb_simple tbody tr td {
				padding: 8px 0px;
			}
		</style>
	</template:replace>
	<template:replace name="body">
		<div style="position: fixed;width: 100%;z-index: 10;display:none" id="table_doclist_bar">
		<div style="padding: 0px 15px;">
			<table class="tb_simple" width="100%" id="TABLE_DocList2" tbdraggable="true" >
				 <tr class="lui_sys_help_catelog_edit_tr_head" type="titleRow">
                      <td width="5%" style="padding-top:5px;padding-bottom: 5px;">
                      	<input type="checkbox" id="fdCatelogList_seclectAll2" onclick="changeSelectAll(this);" /></td>
                      <td width="6%">${lfn:message('sys-help:sysHelpCatelog.fdOrder') }</td>
                      <td width="25%">${lfn:message('sys-help:sysHelpCatelog.docSubject') }</td>
                      <td width="25%">${lfn:message('sys-help:sysHelpCatelog.fdParentDir') }</td>
                      <td>
						<span class="lui_sys_help_catelog_add" id="add_Row1" title="${lfn:message('button.insert') }" style="margin-left: 50px" onclick="add_Row();"></span>
                      	<span class="lui_sys_help_catelog_del" title="${lfn:message('button.delete') }" onclick="delete_Row();"></span>
                      	<span class="lui_sys_help_catelog_up" title="${lfn:message('button.moveup') }" onclick="move_Row(-1);"></span>
                      	<span class="lui_sys_help_catelog_down" title="${lfn:message('button.movedown') }" onclick="move_Row(1);"></span>
                      </td>
                 </tr>
			</table>
			</div>
		</div>
		<div style="padding:15px;">
		  	<div class="lui_form_subject">
		  		${lfn:message('sys-help:sysHelpCatelog.editCatelog') }
				<input type='hidden' name="fdCheckFlag" id="fdCheckFlag" value="true"/>
		  	</div>
			<html:form action="/sys/help/sys_help_main/sysHelpMain.do" >
				<input type='hidden' name="fdId" value="${param.fdId}"/>
				<table class="tb_simple" width="100%" id="TABLE_DocList" tbdraggable="true" >
					 <tr class="lui_sys_help_catelog_edit_tr_head" type="titleRow">
	                      <td width="5%" style="padding-top:5px;padding-bottom: 5px;">
	                      	<input type="checkbox" id="fdCatelogList_seclectAll" onclick="changeSelectAll(this);" /></td>
	                      <td width="6%">${lfn:message('sys-help:sysHelpCatelog.fdOrder') }</td>
	                      <td width="25%">${lfn:message('sys-help:sysHelpCatelog.docSubject') }</td>
	                      <td width="25%">${lfn:message('sys-help:sysHelpCatelog.fdParentDir') }</td>
	                      <td>
	                      	<span class="lui_sys_help_catelog_add" id="add_Row2" title="${lfn:message('button.insert') }" style="margin-left: 50px" onclick="add_Row();"></span>
	                      	<span class="lui_sys_help_catelog_del" title="${lfn:message('button.delete') }" onclick="delete_Row();"></span>
	                      	<span class="lui_sys_help_catelog_up" title="${lfn:message('button.moveup') }" onclick="move_Row(-1);"></span>
	                      	<span class="lui_sys_help_catelog_down" title="${lfn:message('button.movedown') }" onclick="move_Row(1);"></span>
	                      </td>
	                 </tr>
	                 <tr KMSS_IsReferRow="1" style="display:none;" class="lui_sys_help_catelogEdit_tr">
	                     <td valign="top" >
		                     <input type="checkbox" name="fdCatelogList_seclect" onclick="changeSelect(this);" />
		                     <input type='hidden' name ='sysHelpCatelogList[!{index}].fdId'/>
		                     <input type='hidden' name ='sysHelpCatelogList[!{index}].fdOrder'/>
		                     <input type='hidden' name ='sysHelpCatelogList[!{index}].docContent'/>
		                     <input type='hidden' name ='sysHelpCatelogList[!{index}].fdParentId'/>
		                     <input type='hidden' name ='sysHelpCatelogList[!{index}].fdParentDir'/>
		                     <input type='hidden' name ='sysHelpCatelogList[!{index}].fdLevel'/>
		                     <input type='hidden' name ='sysHelpCatelogList[!{index}].docContent'/>
	                 	</td>
	                 	<td KMSS_IsRowIndex="1" valign="top"></td>
	                 	<td valign="top">
	                 		<input type='text' name='sysHelpCatelogList[!{index}].docSubject' style="width:95%"  class="inputsgl" 
	                 			   subject="${lfn:message('sys-help:sysHelpCatelog.docSubject')}"
								   validate="fillParentDirName required maxLength(200)"/>
						</td>
						<td valign="top">
							<input type='text' name='sysHelpCatelogList[!{index}].fdParentDirName' style="width:95%" class="inputsgl" readOnly="readOnly"/>
						</td>
					</tr>
				</table>
			<html:hidden property="method_GET"/>
			</html:form>
	<script>
	    $KMSSValidation(document.forms['sysHelpMainForm']).addValidators(catelog_edit_val);
	</script>
			<script>
				seajs.use(["lui/jquery"], function($) {
					$(document.forms[0]).on("keydown", function(e) {
						 if (e.which == 13) {
						        return false;
						    }
					});
				});
			</script>
		</div>
	</template:replace>
</template:include>

