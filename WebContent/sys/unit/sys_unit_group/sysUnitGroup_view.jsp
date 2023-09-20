<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view"  sidebar="auto">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<kmss:auth requestURL="/sys/unit/sys_unit_group/sysUnitGroup.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				 <ui:button text="${ lfn:message('button.edit') }" order="2" onclick="Com_OpenWindow('sysUnitGroup.do?method=edit&fdId=${param.fdId}','_self');">
				 </ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/unit/sys_unit_group/sysUnitGroup.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				 <ui:button text="${ lfn:message('button.delete') }" order="2" onclick="Delete();">
				 </ui:button>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
			 </ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<script>
			seajs.use(['sys/ui/js/dialog'], function(dialog) {
				window.dialog=dialog;
			});
			function Delete(){
				dialog.confirm("${lfn:message('page.comfirmDelete')}",function(flag){
			    	if(flag==true){
			    		Com_OpenWindow('sysUnitGroup.do?method=delete&fdId=${param.fdId}','_self');
			    	}else{
			    		return false;
				    }
			    },"warn");
			};
		</script>
		<p class="txttitle">
			<bean:message  bundle="sys-unit" key="table.sysUnitGroup"/>
		</p>
		<center>
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.fdName"/>
					</td>
					<td width="85%" colspan="3">
					    <xform:text property="fdName" required="true" style="width:85%"></xform:text>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.fdOrder"/>
					</td>
					<td width="35%"  colspan="1">
						<xform:text property="fdOrder" style="width:85%" validators="digits" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.fdDept"/>
					</td>
					<td width="35%" >
						<xform:address propertyName="fdDeptName" propertyId="fdDeptId" orgType="ORG_TYPE_DEPT" style="width:85%" />
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.docCreateTime"/>
					</td>
					<td width=35% >
						<xform:datetime property="docCreateTime" dateTimeType="datetime" style="width:85%" showStatus="view"></xform:datetime>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.fdIsAvailable"/>
					</td>
					<td width=35% >
						<c:choose>
							<c:when test="${sysUnitGroupForm.fdIsAvailable eq 'true'}">
								<c:out value="有效"></c:out>
							</c:when>
							<c:otherwise>
								<c:out value="无效"></c:out>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.docCreator"/>
					</td>
					<td width=85%  colspan="3">
						<xform:address propertyName="docCreatorName" propertyId="docCreatorId" showStatus="view"></xform:address>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.kmImissiveUnits"/>
					</td>
					<td width=85% colspan="3">
						<xform:dialog propertyId="kmImissiveUnitIds" propertyName="kmImissiveUnitNames" style="width:95%" showStatus="view" textarea="true"  useNewStyle="true">
					     	Dialog_UnitTreeList(
					     		true, 'kmImissiveUnitIds', 'kmImissiveUnitNames', ';', 'kmImissiveUnitCategoryTreeService&parentId=!{value}',
						     	'<bean:message key="kmImissiveUnit.fdCategoryId" bundle="sys-unit"/>', 'kmImissiveUnitListWithAuthService&parentId=!{value}&type=allUnit',
						     	mainCalBackFn,'kmImissiveUnitListWithAuthService&fdKeyWord=!{keyword}&type=allUnit'
						     );
					    </xform:dialog>
					</td>
				</tr>
				
				<!-- 可使用者 -->
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.authReaders"/>
					</td>
					<td  width=85% colspan="3">
						<xform:address
							propertyName="authReaderNames"
							propertyId="authReaderIds"
							orgType="ORG_TYPE_ALL"
							mulSelect="true"
							showStatus="view"
							useNewStyle="true"
							textarea="true"
							style="width:95%" />
				   </td>
				</tr>
				
				<!-- 可维护者 -->
				<tr style="display: none">
					<td class="td_normal_title" width=15%>
						<bean:message bundle="sys-unit" key="sysUnitGroup.authEditors"/>
					</td>
					<td width=85% colspan="3">
						<xform:address 
							propertyName="authEditorNames"
							propertyId="authEditorIds"
							orgType="ORG_TYPE_ALL"
							mulSelect="true"
							showStatus="view"
							useNewStyle="true"
							textarea="true"
							style="width:95%" />
					</td>
				</tr>
				
				<tr>
					<td width=15% class="td_normal_title">
						<bean:message bundle="sys-unit" key="sysUnitGroup.desc"/>
					</td><td colspan="3">
						<xform:textarea property="fdDesc" style="width:95%"></xform:textarea>
					</td>
				</tr>
			</table>
		</center>
	</template:replace>
</template:include>