<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="content">
<c:if test="${empty requestScope.sysRelationMainPrefix}">
	<%-- 系统模板中使用 --%>
	<form name="sysRelationMainForm" action="<c:url value='/sys/relation/sys_relation_main/sysRelationMain.do'/>" method="post" onkeydown="if(event.keyCode==13){return false;}">
</c:if>
<script type="text/javascript">
Com_IncludeFile("docutil.js|dialog.js|doclist.js", null, "js");
</script>
	<link rel="stylesheet" type="text/css"
			href="${LUI_ContextPath}/sys/relation/sys_relation_main/style/rela_view.css" />
<center>

<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						
					</ui:toolbar>
				</div>
			</div>
				<!-- mini分页 -->
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
			<!-- 操作按钮 -->
			<div style="float:right">
			
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar id="Btntoolbar">
							
							<!-- 提交-->
						<c:if test="${empty requestScope.sysRelationMainPrefix}">
							<ui:button text="${lfn:message('button.save')}" onclick="Com_Submit(document.sysRelationMainForm,'update','fdId');">
							</ui:button>
						</c:if>
						<c:if test="${not empty requestScope.sysRelationMainPrefix}">
							<ui:button class="btnopt"  text="${lfn:message('sys-relation:button.insertFromTemplate')}" onclick="importFromTemplate();">
						</ui:button>
						
						<ui:button class="btnopt" text="${lfn:message('sys-relation:button.preview')}" onclick="preview()">
						</ui:button>
						
			
						</c:if>
					</ui:toolbar>
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
<table id="sysRelationZoneTop" class="tb_normal" style="display:none;border:0;width:100%" >
	<tr>
	<td style="width: 3%">
			<input type='checkbox' name='DocList_Selected' />
		</td>
		<td width="5%" align="center">
				${vstatus.index+1}
				
		</td>
	<td align="right" style="border:0;">
		<input type="hidden" name="${requestScope.sysRelationMainPrefix}fdId" value="<c:out value='${sysRelationMainForm.fdId}' />" />
		<input type="hidden" name="${requestScope.sysRelationMainPrefix}fdKey" value="<c:out value='${fdKey}' />"/>
		<input type="hidden" name="${requestScope.sysRelationMainPrefix}fdModelName" value="<c:out value='${sysRelationMainForm.fdModelName}' />"/>
		<input type="hidden" name="${requestScope.sysRelationMainPrefix}fdModelId" value="<c:out value='${sysRelationMainForm.fdModelId}' />"/>
		<input type="hidden" name="${requestScope.sysRelationMainPrefix}fdParameter" value="<c:out value='${sysRelationMainForm.fdParameter}' />"/>
		<c:if test="${not empty requestScope.sysRelationMainPrefix}">
			&nbsp;&nbsp;
			<input type=button class="btnopt" value='<bean:message bundle="sys-relation" key="button.insertFromTemplate" />' 
				onclick="importFromTemplate();">&nbsp;&nbsp;
			<input type=button class="btnopt" value="<bean:message bundle="sys-relation" key="button.preview" />" onclick="preview()">
		</c:if>
	</td>
	</tr>
</table>
<%-- 尽量不要在id为sysRelationZone的table中增删行 --%>
<table id="sysRelationZone" class="tb_normal" style="width:100%">
	<tr align="center">
		<td class="td_normal_title" style="width: 3%"></td>
		 
		<%--序号--%> 
		<td class="td_normal_title" style="width: 5%">
			<bean:message key="page.serial"/>
		</td>
		<td class="td_normal_title" width="30%">
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdName" />
		</td>
		<td class="td_normal_title" width="30%">
			<bean:message bundle="sys-relation" key="sysRelationEntry.fdType" />
		</td>
		<td class="td_normal_title" width="25%">
			<bean:message bundle="sys-relation" key="button.operation" />
		</td>
	</tr>
	<c:set var="entryPrefixIndex" value="${requestScope.sysRelationMainPrefix}sysRelationEntryFormList[!{index}]." />
	<tr KMSS_IsReferRow="1" style="display:none">

		<td style="width: 3%">
			<input type='checkbox' name='DocList_Selected' />
		</td>
		<td width="5%" align="center" KMSS_IsRowIndex="1">!{index}</td>
		<td>
		
			<input name="${entryPrefixIndex}fdModuleName" class="inputsgl" style="width:100%;text-align: center;" />
			<span class="txtstrong">*</span>
			<input type="hidden" name="${entryPrefixIndex}fdId" />
			<input type="hidden" name="${entryPrefixIndex}fdType" />
			<input type="hidden" name="${entryPrefixIndex}fdModuleModelName" />
			<input type="hidden" name="${entryPrefixIndex}fdOrderBy" />
			<input type="hidden" name="${entryPrefixIndex}fdOrderByName" />
			<input type="hidden" name="${entryPrefixIndex}fdPageSize" />
			<input type="hidden" name="${entryPrefixIndex}fdRelationProperty" />
			<input type="hidden" name="${entryPrefixIndex}fdParameter" />
			<%-- 说明：为了避免动态表格刷新，条件的字段提交的时候在拼装  --%>
			<input type="hidden" name="${entryPrefixIndex}fdKeyWord" />
			<input type="hidden" name="${entryPrefixIndex}docCreatorId" />
			<input type="hidden" name="${entryPrefixIndex}docCreatorName" />
			<input type="hidden" name="${entryPrefixIndex}fdFromCreateTime" />
			<input type="hidden" name="${entryPrefixIndex}fdToCreateTime" />
			<input type="hidden" name="${entryPrefixIndex}fdSearchScope" />
			<input type="hidden" name="${entryPrefixIndex}fdOtherUrl" />
		</td>
		<td align="center">
			&nbsp;
		</td>
		<td align="center">
			<a href="#" onclick="editRelationEntry();" style="text-decoration: none"><img src="${KMSS_Parameter_StylePath}icons/edit.gif" border="0" /></a>
			<a href="#" onclick="deleteRelationEntry();" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0" /></a>
			<!--<a href="#" onclick="DocList_MoveRow(-1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/up.gif" border="0" /></a>
			<a href="#" onclick="DocList_MoveRow(1);" style="text-decoration: none">&nbsp;<img src="${KMSS_Parameter_StylePath}icons/down.gif" border="0" /></a>
		--></td>
	</tr>
	<c:forEach items="${sysRelationMainForm.sysRelationEntryFormList}" varStatus="vstatus" var="sysRelationEntryForm">
		<c:set var="entryPrefix" value="${requestScope.sysRelationMainPrefix}sysRelationEntryFormList[${vstatus.index}]." />
		<tr KMSS_IsContentRow="1">
		<td style="width: 3%">
			<input type='checkbox' name='DocList_Selected' />
		</td>
		<td width="5%" align="center">${vstatus.index+1}</td>
			<td>
				<input type="text" name="${entryPrefix}fdModuleName" value="<c:out value='${sysRelationEntryForm.fdModuleName}' />" class="inputsgl" style="width: 100%;text-align: center;" />
				<span class="txtstrong">*</span>
				<input type="hidden" name="${entryPrefix}fdId" value="<c:out value='${sysRelationEntryForm.fdId}' />" />
				<input type="hidden" name="${entryPrefix}fdType" value="<c:out value='${sysRelationEntryForm.fdType}' />" />
				<input type="hidden" name="${entryPrefix}fdModuleModelName" value="<c:out value='${sysRelationEntryForm.fdModuleModelName}' />" />
				<input type="hidden" name="${entryPrefix}fdOrderBy" value="<c:out value='${sysRelationEntryForm.fdOrderBy}' />" />
				<input type="hidden" name="${entryPrefix}fdOrderByName" value="<c:out value='${sysRelationEntryForm.fdOrderByName}' />" />
				<input type="hidden" name="${entryPrefix}fdPageSize" value="<c:out value='${sysRelationEntryForm.fdPageSize}' />" />
				<input type="hidden" name="${entryPrefix}fdRelationProperty" value="<c:out value='${sysRelationEntryForm.fdRelationProperty}' />" />
				<input type="hidden" name="${entryPrefix}fdParameter" value="<c:out value='${sysRelationEntryForm.fdParameter}' />" />
				<%-- 说明：为了避免动态表格刷新，条件的字段提交的时候在拼装  --%>
				<input type="hidden" name="${entryPrefix}fdKeyWord" value="<c:out value='${sysRelationEntryForm.fdKeyWord}' />" />
				<input type="hidden" name="${entryPrefix}docCreatorId" value="<c:out value='${sysRelationEntryForm.docCreatorId}' />" />
				<input type="hidden" name="${entryPrefix}docCreatorName" value="<c:out value='${sysRelationEntryForm.docCreatorName}' />" />
				<input type="hidden" name="${entryPrefix}fdFromCreateTime" value="<c:out value='${sysRelationEntryForm.fdFromCreateTime}' />" />
				<input type="hidden" name="${entryPrefix}fdToCreateTime" value="<c:out value='${sysRelationEntryForm.fdToCreateTime}' />" />
				<input type="hidden" name="${entryPrefix}fdSearchScope" value="<c:out value='${sysRelationEntryForm.fdSearchScope}' />" />
				<input type="hidden" name="${entryPrefix}fdOtherUrl" value="<c:out value='${sysRelationEntryForm.fdOtherUrl}' />" />
			</td>
			<td align="center">
				<sunbor:enumsShow enumsType="sysRelationEntry_fdType" value="${sysRelationEntryForm.fdType}"></sunbor:enumsShow>
			</td>
			<td align="center">
				<a href="#" onclick="editRelationEntry();"><img src="${KMSS_Parameter_StylePath}icons/edit.gif" border="0" /></a>
				<a href="#" onclick="deleteRelationEntry();"><img src="${KMSS_Parameter_StylePath}icons/delete.gif" border="0" /></a>
				
			</td>
		</tr>
	</c:forEach>
	
</table>
<table id="sysRelationZoneFooterbutton" class="tb_normal" style="border-top: 0px; width:100%">
<tr type="optRow" class="tr_normal_opt" invalidrow="true">
		<td colspan="9">
			<a href="javascript:void(0);" onclick="addRelationEntry();" title="${ lfn:message('sys-relation:sysRelationMain.helptips12') }">
				<img src="${LUI_ContextPath}/resource/style/default/icons/icon_add.png" border="0" />
			</a>
			&nbsp;
			<a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(-1);" title="${ lfn:message('sys-relation:sysRelationMain.helptips13') }">
				<img src="${LUI_ContextPath}/resource/style/default/icons/icon_up.png" border="0" />
			</a>
			&nbsp;
			<a href="javascript:void(0);" onclick="DocList_MoveRowBySelect(1);" title="${ lfn:message('sys-relation:sysRelationMain.helptips14') }">
				<img src="${LUI_ContextPath}/resource/style/default/icons/icon_down.png" border="0" />
			</a>
			&nbsp;
		</td>
	</tr>
</table>
<table id="sysRelationZoneFooter" class="tb_normal" style="border-top: 0px; width:100%">
	<tr>
		<td style="border-top: 0px;"><font color="red"><bean:message bundle="sys-relation" key="sysRelationEntry.fdNote" /></font></td>
	</tr>
</table>
</center>
<script>
function DocList_MoveRowBySelect(direct){

		var optTR = "tr.tr_normal_opt";
		var optTB = " table#sysRelationZone.tb_normal";
		var docList_Selected = $("input[name='DocList_Selected']:checked",optTB);;
		if(docList_Selected.size() == 0){
			alert(Data_GetResourceString('page.noSelect'));
			return;
		}
		if(direct == -1){
			for(var i=0;i<docList_Selected.size();i++){
				var _optTR = docList_Selected.eq(i).closest('tr');
				if(_optTR && _optTR.size()>0){
					var result = DocList_MoveRow(direct,_optTR[0]);
					if(result == 'top'){
						break;
					}
				}
			}
		}
		if(direct == 1){
			for(var i=docList_Selected.size() - 1;i>=0;i--){
				var _optTR = docList_Selected.eq(i).closest('tr');
				if(_optTR && _optTR.size()>0){
					result = DocList_MoveRow(direct,_optTR[0]);
					if(result == 'bottom'){
						break;
					}
				}
			}
		}
}
</script>
<%@ include file="sysRelationMain_edit_script.jsp"%>
<c:if test="${empty requestScope.sysRelationMainPrefix}">
	<%-- 系统模板中使用 --%>
	</form>
</c:if>
</template:replace>

</template:include>