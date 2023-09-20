<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict,com.landray.kmss.sys.config.dict.SysDictModel" %>
<template:include ref="default.view" showQrcode="false">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.edit') }" onclick="editDoc();"></ui:button>
			<ui:button text="${ lfn:message('button.delete') }" onclick="deleteDoc();"></ui:button>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}dbcenter/echarts/application/navTree/css/treeShow.css?s_cache=${LUI_Cache}" />
		<center>
			<table class="tb_normal" width=95%> 
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="treeShow.name"/>
					</td>
					<td width="85%" colspan="3">
						<xform:text property="fdName" required="true"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="dbcenter-echarts-application" key="treeShow.isEnable"/>
					</td>
					<td width="35%" >
						<xform:radio property="fdIsEnable">
							<xform:enumsDataSource enumsType="common_yesno" />
						</xform:radio>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message key="model.fdOrder"/>
					</td>
					<td width="35%" >
						<xform:text property="fdOrder" validators="number"/>
					</td>
				</tr>
			</table>
			<div class="tableList">
				<div class="tableList-header">
					<bean:message bundle="dbcenter-echarts-application" key="treeShow.containChart"/>
				</div>
				<div clas="tableList-content">
					<table class="tb_normal" width="100%" id="TABLE_DocList">
						<tr align="center" class="tr_normal_title">
							<%--序号--%> 
							<td width="5%"><bean:message key="page.serial"/></td>
							<%-- 图表导航名称 --%>
							<td width="65%"><bean:message bundle="dbcenter-echarts-application" key="treeShow.item.name"/></td>
							<td width="30%"><bean:message bundle="dbcenter-echarts-application" key="treeShow.item.category"/></td>
						</tr>
						<tr KMSS_IsReferRow="1" style="display: none" align="center">
							<td KMSS_IsRowIndex="1" width="5%" align="center"></td>
							<td align="center">
								<xform:dialog required="true" subject="${lfn:message('dbcenter-echarts-application:treeShow.item.name') }" propertyId="fdNavTreeShowList[!{index}].fdNavTreeId" style="width:30%"
									propertyName="fdNavTreeShowList[!{index}].fdNavTitleTxt" dialogJs="NavTree_Dialog('fdNavTreeShowList[!{index}].fdNavTreeId','fdNavTreeShowList[!{index}].fdNavTitleTxt',this);">
								</xform:dialog>
								<xform:text property="fdNavTreeShowList[!{index}].fdNavTreeModelName" showStatus="noShow"/>
							</td>
							<td align="center">
								<input type="text" readOnly name="fdNavTreeShowList[!{index}].fdDbEchartsCategoryName" style="border:0px;text-align:center;"/>
							</td>
						</tr>
						<c:forEach items="${dbEchartsNavTreeShowForm.fdNavTreeShowList}"  var="dbEchartsNavTreeShowItemForm" varStatus="vstatus">
							<tr KMSS_IsContentRow="1" align="center">
								<td width="5%">${vstatus.index+1}
								</td>
								<td>
									<xform:text showStatus="view" property="fdNavTreeShowList[${vstatus.index}].fdNavTitleTxt" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.showValue') }"></xform:text>
								</td>
								<td>
									<xform:text showStatus="view" property="fdNavTreeShowList[${vstatus.index}].fdDbEchartsCategoryName" subject="${lfn:message('sys-xform-maindata:sysFormMainDataCustom.realValue') }"></xform:text>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		<br>
		</center>
	<script>
		seajs.use(["lui/dialog"],function(dialog){
			window.deleteDoc = function(delUrl){
				dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
					var url = "<c:url value='/dbcenter/echarts/application/dbEchartsNavTreeShow.do?method=delete&fdId=${param.fdId}' />";
					if(isOk){
						Com_OpenWindow(url,'_self');
					}
				});
			};
			
		});
		
		function editDoc(){
			Com_OpenWindow("<c:url value='/dbcenter/echarts/application/dbEchartsNavTreeShow.do?method=edit&fdId=${param.fdId}'/>",'_self');
		}
		
	</script>
	</template:replace>

</template:include>