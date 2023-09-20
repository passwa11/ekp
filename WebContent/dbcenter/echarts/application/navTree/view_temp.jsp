<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict,com.landray.kmss.sys.config.dict.SysDictModel" %>
<template:include ref="default.view" showQrcode="false">
<c:set var="navTreeForm" value="${requestScope[param.formName]}" />
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.edit') }" onclick="editDoc();"></ui:button>
			<ui:button text="${ lfn:message('button.delete') }" onclick="deleteDoc();"></ui:button>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<script>
			Com_IncludeFile('inputs.js',Com_Parameter.ContextPath+'dbcenter/echarts/application/common/','js',true);
			Com_IncludeFile('data.js');
		</script>
		<center>
			<%
				String tempModelName = request.getParameter("tempModelName");
				SysDictModel dictModel = SysDataDict.getInstance().getModel(tempModelName);
				String viewUrl = dictModel.getUrl();
				viewUrl = viewUrl.substring(1, viewUrl.indexOf("?"));
			%>
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdNavTitleTxt"/>
					</td>
					<td width="85%" colspan="3">
						<xform:text property="fdNavTitleTxt"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdEchartsTemplate"/>
					</td>
					<td width="35%">
						<xform:dialog propertyId="fdTemplateId" propertyName="fdTemplateName" >
						</xform:dialog>
						<div style="display:inline-block;color:#4285f4;">
							<a href="javascript:void(0);" onclick="DbCenter_Application_NavTree_ViewTemp();">
								<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.view"/>
							</a>
						</div>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdEchartsCategory"/>
					</td>
					<td width=35%>
						<xform:text property="fdEchartsCategoryName"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.fdConfig"/>
					</td>
					<td width="85%" colspan="3">
						<div class="navTree-inputs">
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docCreator"/>
					</td>
					<td width="35%">
						<html:text property="docCreatorName" readonly="true"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docCreateTime"/>
					</td>
					<td width="35%">
						<html:text property="docCreateTime" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docAlteror"/>
					</td>
					<td width="35%">
						<bean:write name="navTreeForm" property="docAlterorName" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="dbEchartsNavTree.docAlterTime"/>
					</td>
					<td width="35%">
						<bean:write name="navTreeForm" property="docAlterTime" />
					</td>
				</tr>	
			</table>
			<html:hidden property="fdConfig" />
		</center>
	<script>
		seajs.use(["lui/dialog"],function(dialog){
			window.deleteDoc = function(delUrl){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
					var url = "<c:url value='${param.actionUrl }?method=delete&fdId=${param.fdId}' />";
					if(isOk){
						Com_OpenWindow(url,'_self');
					}	
				});
				return;
			};
			
			window.DbCenter_Application_NavTree_ViewTemp = function(){
				var fdTemplateId = "${navTreeForm.fdTemplateId}";
				var url = Com_Parameter.ContextPath + "<%=viewUrl%>?method=view&fdId=" + fdTemplateId;
				Com_OpenWindow(url);
			}
		});
		
		function editDoc(){
			Com_OpenWindow("<c:url value='${param.actionUrl }?method=edit&fdId=${param.fdId}'/>",'_self');
		}
		
		function DbCenter_Application_NavTree_Init(){
			var dbEchartsAppInputs = new DbEchartsAppInputs($(".navTree-inputs"));
			// 初始化入参
			var fdConfig = $.trim($('[name="fdConfig"]').val());
			var inputs = fdConfig == ''?{}:LUI.toJSON(fdConfig);
			dbEchartsAppInputs.buildInputInView(inputs);
		}

		Com_AddEventListener(window,'load',DbCenter_Application_NavTree_Init);
	</script>
	</template:replace>

</template:include>