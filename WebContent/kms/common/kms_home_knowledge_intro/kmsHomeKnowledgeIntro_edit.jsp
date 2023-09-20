<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${ kmsHomeKnowledgeIntroForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('kms-common:table.kmsHomeKnowledgeIntro') } - ${lfn:message('button.add') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${kmsHomeKnowledgeIntroForm.fdName} - ${ lfn:message('button.edit') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar layout="sys.ui.toolbar.float">
			<c:if test="${kmsHomeKnowledgeIntroForm.method_GET=='edit'}">
				<ui:button text="${lfn:message('button.update') }"
					onclick="Com_Submit(document.kmsHomeKnowledgeIntroForm, 'update');"/>
			</c:if>
			<c:if test="${kmsHomeKnowledgeIntroForm.method_GET=='add'}">
				<ui:button text="${lfn:message('button.save') }"
						onclick="Com_Submit(document.kmsHomeKnowledgeIntroForm, 'save');"/>
				<ui:button text="${lfn:message('button.saveadd') }" 
					onclick="Com_Submit(document.kmsHomeKnowledgeIntroForm, 'saveadd')"/>
			</c:if>
			<ui:button text="${lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
			<ui:combin ref="menu.path.simplecategory">
				<ui:varParams 
					moduleTitle="${ lfn:message('kms-common:table.kmsHomeKnowledgeIntro') }" 
					modulePath="/kms/common/" 
					modelName="com.landray.kmss.kms.common.model.KmsHomeKnowledgeIntroCategory" 
					autoFetch="false"
					target="_blank"
					categoryId="${kmsHomeKnowledgeIntroForm.docCategoryId}" />
			</ui:combin>
	</template:replace>	
	<template:replace name="content"> 
		<div class="lui_form_content_frame" style="padding-top:20px">
		<html:form action="/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do">
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.fdName"/>
					</td>
					<td width="85%" colspan="3">
						<xform:text property="fdName" style="width:85%" validators="length"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.docCategory"/>
					</td>
					<td width="85%" colspan="3">
						<xform:dialog style="width:85%" validators="required" required="true"
							dialogJs="cateSelect();" propertyId="docCategoryId" propertyName="docCategoryName"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.fdPhase"/>
					</td>
					<td width="85%" colspan="3">
						<xform:text property="fdPhase" style="width:85%"  />
					</td>
				</tr>
				<tr>
					<td class="td_normal_title"><bean:message bundle="kms-common" key="kmsHomeKnowledgeMapping.docSubject"/></td>
					<td colspan="3">
						<xform:text property="fdTopName" style="width:85%" validators="length"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title"><bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.url"/></td>
					<td colspan="3">
						<c:if test="${kmsHomeKnowledgeIntroForm.method_GET == 'add'}">
							<xform:text property="fdTopUrl" style="width:85%" value="http://" 
							    htmlElementProperties='validate="validUrl(fdTopUrl)"' />
						</c:if>
						<c:if test="${kmsHomeKnowledgeIntroForm.method_GET == 'edit'}">
							<xform:text property="fdTopUrl" style="width:85%;" 
							    htmlElementProperties='validate="validUrl(fdTopUrl)"'/>
						</c:if>
					</td>
				</tr>
				<%-- 
				<tr>
					<td class="td_normal_title">内容预览</td>
					<td colspan="3">
						<xform:textarea property="fdTopContent" style="width:85%"></xform:textarea>
					</td>
				</tr>--%>
				<tr>
					<td class="td_normal_title"><bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.img"/></td>
					<td colspan="3">
						<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
					    	<c:param name="fdKey" value="kmsHomeKnowlegeIntr_fdKey"/>
					    	<c:param name="fdMulti" value="false"/>
					    	<c:param name="fdAttType" value="pic"/>
					    	<c:param name="fdImgHtmlProperty" value="height=150"/>
						</c:import>
					</td>
				</tr>
				<c:if test="${kmsHomeKnowledgeIntroForm.method_GET=='edit'}">
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.docCreator"/>
					</td><td width="35%">
						<xform:text property="docCreatorName" showStatus="view"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.docCreateTime"/>
					</td><td width="35%">
						<xform:text property="docCreateTime" style="width:85%" showStatus="view"/>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.docAlteror"/>
					</td><td width="35%">
						<xform:text property="docAlterorName" showStatus="view"/>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message bundle="kms-common" key="kmsHomeKnowledgeIntro.docAlterTime"/>
					</td><td width="35%">
						<xform:datetime property="docAlterTime" showStatus="view" />
					</td>
				</tr>
				</c:if>
			</table>
			<html:hidden property="fdId" />
			<html:hidden property="method_GET" />
			<script>
				Com_IncludeFile("dialog.js");
				var validObj = $KMSSValidation();
				validObj.addValidator('validUrl(url)', '链接规范：https://www.baidu.com(必须带有http、https等完整链接地址)',
				    function(v, e, o) { // 参数说明 v:Dom对象的值，e：Dom对象，o：参数对象
				       var urlInput = document.getElementsByName(o['url'])[0]; // o['url']可以获取传递的参数，这里获取为fdTopUrl
				       var url = urlInput.value;
				       if(!url){
				    	   return true; // 允许为空  
				       }
				       if(url && (url.toLowerCase().indexOf('https://') == 0
				    		   || url.toLowerCase().indexOf('http://') == 0)) {
				           return true; // 校验通过
				       }
				       return false;
				    }
				);

				function cateSelect(canClose) {
					var canClose = canClose != "undefined" ? canClose : true;
					var cfg = {
						modelName : "com.landray.kmss.kms.common.model.KmsHomeKnowledgeIntroCategory",
						idField : 'docCategoryId',
						nameField : 'docCategoryName',
						canClose : canClose,
						mulSelect : false,
						notNull : true,
						action : function(val) {
							//这里不需要手动赋值，会自动赋到相应的组件上
							/* if (val && val.id) {
								var url = "${LUI_ContextPath}/kms/common/kms_home_knowledge_intro/kmsHomeKnowledgeIntro.do?method=add&docCategoryId=" + val.id;
								window.open(url, '_self');
							} */
						},
					}
					seajs.use([ 'sys/ui/js/dialog' ], function(dialog) {
						dialog.simpleCategory(cfg);
					});
				}
			</script>
			</html:form>
			</div>
	</template:replace>
</template:include>