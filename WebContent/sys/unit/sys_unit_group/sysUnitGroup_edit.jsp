<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit"  sidebar="auto">
	
	<template:replace name="head">
		 <script type="text/javascript">
			 
		 </script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<c:if test="${sysUnitGroupForm.method_GET=='edit'}">
			 <ui:button text="${ lfn:message('button.update') }" order="2" onclick="Com_Submit(document.sysUnitGroupForm, 'update');">
			 </ui:button>
			</c:if>
			<c:if test="${sysUnitGroupForm.method_GET=='add'}">
				<ui:button text="${ lfn:message('button.submit') }" order="1" onclick="Com_Submit(document.sysUnitGroupForm, 'save');">
			    </ui:button>
			</c:if>
			<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
			 </ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/unit/sys_unit_group/sysUnitGroup.do">
			<script type="text/javascript">
				Com_IncludeFile("sysUnitDialog.js", Com_Parameter.ContextPath+ "sys/unit/resource/js/", "js", true);
				Com_IncludeFile('styles.css|jquery.ui.widget.js|jquery.marcopolo.js|jquery.manifest.js','js/jquery-plugin/manifest/');
			</script>
			<p class="txttitle">
				<c:out value="机构组编辑"></c:out>
			</p>

			<center>
				<html:hidden property="fdId"/>
				<html:hidden property="method_GET"/>
				
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
							<xform:datetime property="docCreateTime" dateTimeType="datetime" style="width:85%" showStatus="readOnly"></xform:datetime>
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-unit" key="sysUnitGroup.fdIsAvailable"/>
						</td>
						<td width=35% >
							<sunbor:enums property="fdIsAvailable" enumsType="common_yesno" elementType="radio" />
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-unit" key="sysUnitGroup.docCreator"/>
						</td>
						<td width=85%  colspan="3">
							<xform:address propertyName="docCreatorName" propertyId="docCreatorId" showStatus="readOnly"></xform:address>
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-unit" key="sysUnitGroup.kmImissiveUnits"/>
						</td>
						<td width=85% colspan="3">
							<xform:dialog propertyId="kmImissiveUnitIds" propertyName="kmImissiveUnitNames" style="width:95%" showStatus="edit" textarea="true"  useNewStyle="true">
						     	Dialog_UnitTreeList(
						     		true, 'kmImissiveUnitIds', 'kmImissiveUnitNames', ';', 'kmImissiveUnitCategoryTreeService&parentId=!{value}',
							     	'<bean:message key="kmImissiveUnit.fdCategoryId" bundle="sys-unit"/>', 'kmImissiveUnitListWithAuthService&parentId=!{value}&type=allUnit',
							     	mainCalBackFn,'kmImissiveUnitListWithAuthService&fdKeyWord=!{keyword}&type=allUnit'
							     );
						    </xform:dialog>
						    <script>
								var ids= document.getElementsByName("kmImissiveUnitIds")[0].value;
							   	var names = document.getElementsByName("kmImissiveUnitNames")[0].value;
								initNewDialog("kmImissiveUnitIds","kmImissiveUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,"","",null);
							   	if(ids != ""){
							   		resetNewDialog("kmImissiveUnitIds","kmImissiveUnitNames",";","kmImissiveUnitListWithAuthService&newSearch=true&type=allUnit",true,ids,names,null);
							   	}
						    </script>
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
								showStatus="edit"
								useNewStyle="true"
								textarea="true"
								style="width:95%" />
							</br>
						    <!-- （为空则所有人可使用） -->
							<bean:message bundle="sys-unit" key="sysUnitGroup.authReaders.describe"/>
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
								showStatus="edit"
								useNewStyle="true"
								textarea="true"
								style="width:95%" />
							</br>
							<bean:message bundle="sys-unit" key="sysUnitGroup.authEditors.describe"/>
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
			<script language="JavaScript">
			      $KMSSValidation(document.forms['sysUnitGroupForm']);
			</script>
			<script type="text/javascript">
				function mainCalBackFn(value){
					console.log("sysUnitGroup select unit: ", value);
				}
			</script>
		</html:form>
	</template:replace>
</template:include>