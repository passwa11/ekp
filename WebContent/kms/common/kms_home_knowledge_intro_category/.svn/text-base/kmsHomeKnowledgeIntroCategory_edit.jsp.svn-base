<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ kmsHomeKnowledgeIntroCategoryForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" 
							   onclick="Com_Submit(document.kmsHomeKnowledgeIntroCategoryForm, 'update');">
					</ui:button>
				</c:when>
				<c:when test="${ kmsHomeKnowledgeIntroCategoryForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" 
							   onclick="Com_Submit(document.kmsHomeKnowledgeIntroCategoryForm, 'save');">
					</ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" 
							   onclick="Com_Submit(document.kmsHomeKnowledgeIntroCategoryForm, 'saveadd');">
					</ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
			<html:form action="/kms/common/kms_home_knowledge_intro_category/kmsHomeKnowledgeIntroCategory.do">
			<p class="txttitle">
			${lfn:message('kms-common:table.kmsHomeKnowledgeIntroCategory') }
			</p>
			<center>
				<table class="tb_normal" width=95%> 
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntroCategory.fdParent"/>
						</td><td width="85%" colspan="3">
							<xform:dialog propertyId="fdParentId" propertyName="fdParentName"  style="width:95%"
									dialogJs="cateSelect();">
							</xform:dialog>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntroCategory.fdName"/>
						</td><td width="85%" colspan="3">
							<xform:text property="fdName" style="width:90%"  validators="maxLength(200) checkName" />
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntroCategory.fdOrder"/>
						</td><td width="85%" colspan="3">
							<xform:text property="fdOrder" style="width:25%" />
						</td>
					</tr>
					<tr>
					<td class="td_normal_title" width=15%><bean:message bundle="sys-simplecategory"
							key="sysSimpleCategory.parentMaintainer" /></td>
						<td colspan="3" id="parentMaintainerId">${parentMaintainer}</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntroCategory.authEditors"/>
						</td><td width="85%" colspan="3">
							<xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" textarea="true" style="width:95%" />
							<div class="description_txt">
								<bean:message	bundle="sys-simplecategory" key="description.main.tempEditor" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntroCategory.authReaders"/>
						</td>
						<td width="85%" colspan="3">
							<input type="checkbox" name="authNotReaderFlag" value="${kmsHomeKnowledgeIntroCategoryForm.authNotReaderFlag}" 
			      				   onclick="Cate_CheckNotReaderFlag(this);" 
							<c:if test="${kmsHomeKnowledgeIntroCategoryForm.authNotReaderFlag eq 'true'}">checked</c:if>>
							<bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
							<div id="Cate_AllUserId">
								<xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" textarea="true" style="width:95%" />
							</div>
							<div id="Cate_AllUserNote">
								<bean:message bundle="sys-simplecategory" key="description.main.tempReader.allUse" />
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntroCategory.docCreator"/>
						</td><td width="35%">
							<xform:address propertyId="docCreatorId" propertyName="docCreatorName" 
										   orgType="ORG_TYPE_PERSON" showStatus="view" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntroCategory.docCreateTime"/>
						</td><td width="35%">
							<xform:datetime property="docCreateTime" showStatus="view" />
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<script>
				Com_IncludeFile("dialog.js");
				Com_IncludeFile("jquery.js|dialog.js|validation.js|plugin.js|validation.jsp ");
				
				
				function Cate_Win_Onload(){
					Cate_CheckNotReaderFlag(document.getElementsByName("authNotReaderFlag")[0]);
				}
				
				Com_AddEventListener(window, "load", Cate_Win_Onload);
				
				//校验类别名称是否重复
				var _validation = $KMSSValidation();
				_validation.addValidator('checkName','类别名称已存在,请重新命名',
						function(v,e,o){
					var bool = true;
					if(v=='${kmsHomeKnowledgeIntroCategoryForm.fdName}'){
						return bool;
					}
					$.ajax({async:false
						,url:'<c:url value="/kms/common/kms_home_knowledge_intro_category/kmsHomeKnowledgeIntroCategory.do?method=valiName"/>',data:{fdName:v}
						,success:function(data){
							if(data=='false'){
								bool = false;		
							}
						}
					});
					return bool;
				});
				
				function Cate_CheckNotReaderFlag(el){
					document.getElementById("Cate_AllUserId").style.display = el.checked ? "none" : "";
					document.getElementById("Cate_AllUserNote").style.display = el.checked ? "none" : "";
					el.value=el.checked;
				};
				
				function cateSelect() {
					seajs.use(['lui/dialog'],function(dialog){
						dialog.simpleCategory('${param.fdModelName}','fdParentId','fdParentName',false,Cate_getParentMaintainer,null,true,null,false);
					})
				}
				
				
				function Cate_getParentMaintainer(){
					var url = 
						"/kms/common/kms_home_knowledge_intro_category/kmsHomeKnowledgeIntroCategory.do?method=getParentMaintainer&"
					var parameters ="parentId="+document.getElementsByName("fdParentId")[0].value;
					var s_url = Com_Parameter.ContextPath + url;
					$.ajax({
							url: s_url,
							type: "GET",
							data: parameters,
							dataType:"text",
							async: false,
							success: function(text){
								if(text)
									$(document.getElementById("parentMaintainerId")).text(text);
								else 
									$(document.getElementById("parentMaintainerId")).text("");
							}});
				}
			
				function check(){
					var parentId = $("input[name='fdParentId']").val();
					if(parentId != ""&&parentId == '${kmsHomeKnowledgeIntroCategoryForm.fdId}'){
						alert("<bean:message bundle="sys-simplecategory" key="error.illegalSelected" />");
						return false;
					}else{
						return true;
					}
				}
				Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = check;
			</script>
			</html:form>
	</template:replace>
</template:include>