<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no" showQrcode="false">
	<template:replace name="head">
		<script>
			var dialogWin = window.top;
			Com_IncludeFile("doclist.js|dialog.js");
			seajs.use([ "sys/mportal/sys_mportal_card/css/edit.css" ]);
		</script>
		<script src="${LUI_ContextPath}/sys/ui/js/var.js"></script>
	</template:replace>
	<template:replace name="title">
		<c:choose>
			<c:when test="${ sysMportalCardForm.method_GET == 'add' }">
				<bean:message key="button.add"/> - <bean:message bundle="sys-mportal" key="table.sysMportalCard"/>
		</c:when>
			<c:otherwise>
				<c:out value="${sysMportalCardForm.fdName}" /> - <bean:message key="button.edit"/>
		</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav">
			<ui:menu-item text="${lfn:message('home.home')}" href="/index.jsp"
				icon="lui_icon_s_home" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item1')}" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item2')}" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item3')}" />
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item4')}" />
		</ui:menu>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<c:choose>
				<c:when test="${ sysMportalCardForm.method_GET == 'add' }">
					<ui:button text="${lfn:message('button.submit')}" order="2"
						onclick="if(validate())submit('save')">
					</ui:button>
				</c:when>
				<c:when test="${sysMportalCardForm.method_GET == 'edit' }">
					<ui:button text="${lfn:message('button.submit')}" order="2"
						onclick="if(validate())submit('update');">
					</ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${lfn:message('button.close')}" order="5"
				onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>

	<template:replace name="content">
		<html:form action="/sys/mportal/sys_mportal_card/sysMportalCard.do">
			<div class="lui_content_form_frame" style="padding: 20px 0px">
				<table class="tb_normal" width=95%>
					<tr>
					    <!-- 名称  -->
						<td class="td_normal_title" width=15%><bean:message bundle="sys-mportal" key="sysMportalCard.fdName" /></td>
						<td width="35%"><xform:text property="fdName" required="true"  validators="required" style="width:85%"/></td>
						<!-- 排序号  -->
						<td class="td_normal_title" width=15%><bean:message bundle="sys-mportal" key="sysMportalCard.fdOrder" /></td>
						<td width="35%"><xform:text property="fdOrder" style="width:98%" /></td>
					</tr>
					<tr>
					<%-- 模块分类 --%>
					<td class="td_normal_title" width=15%>
						<bean:message key="sysMportalCard.fdModuleCate" bundle="sys-mportal"/>
					</td>
					<td width="85%" colspan="3">
						<html:hidden property="fdModuleCateId"/>
						<html:text styleClass="inputsgl" readonly="true" property="fdModuleCateName"/><span class="txtstrong">*</span>
							<a href="#" onclick="Dialog_Tree(false, 
								 'fdModuleCateId', 
								 'fdModuleCateName', 
								 ',', 
								 'sysMportalModuleCateTreeService', 
								 '<bean:message key="sysMportalCard.fdModuleCate" bundle="sys-mportal"/>',
								 null, null,null, null, null,
								 '<bean:message key="table.sysMportalModuleCate" bundle="sys-mportal"/>')"><bean:message key="button.select"/> </a>
						</td>
					</tr>
					<tr>
					    <!-- 是否启用  -->
						<td class="td_normal_title" width=15%><bean:message bundle="sys-mportal" key="sysMportalCard.fdEnabled" /></td>
						<td width="35%">
						    <xform:radio property="fdEnabled">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						 <!-- 多页签  -->
						<td class="td_normal_title" width=15%><bean:message bundle="sys-mportal" key="sysMportalCard.isMulTab"/></td>
						<td width="35%">
						    <xform:radio property="fdType" onValueChange="mulValueChange">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</td>
						
						<div style="display: none">
						    <xform:radio property="fdIsPushed">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</div>
					</tr>
					<tr>
					   
					</tr>
					
					<tr id="tabpanel" <c:if test="${ sysMportalCardForm.fdType == false}">style="display: none"</c:if> >
						<td colspan="4">
							<div>
								<table style="width: 100%">
									<tr>
										<td><input class="lui_form_button" type="button"
											style="cursor: pointer;" title="${lfn:message('sys-mportal:sysMportalCard.addTab')}" 
											value="${lfn:message('sys-mportal:sysMportalCard.addTab')}"
											onclick="addTabPanelPortlet()">
											<div id="card_mul_config"></div></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
					<tr id="panel"
						<c:if test="${ sysMportalCardForm.fdType == true}">style="display: none"</c:if>>
						<td colspan="4">
							<table class="tb_normal card_table" width=95%>
								<tr>
									<td class="td_normal_title" width=15%>
										<bean:message bundle="sys-mportal" key="sysMportalCard.fdContent"/>
									</td>
									<td width="85%" colspan="3">
										<div>
											<input type="text" name="fdPortletName" style="width: 80%" class="inputsgl" readonly="readonly"> 
											<input type="hidden" name="fdPortletId"> 
											<a href="javascript:;" class="com_btn_link" onclick="selectPortlet(this);"> 
												<bean:message key="button.select"/>
											</a>
											<div name="descriptionContent" style="display:none;padding-left: 4px;margin-top: 10px;">
											   <span name="descriptionTitle" >${lfn:message('sys-mportal:sysMportalPortal.fdDescription') }:</span>
											   <span name="description" ></span>
											</div>
											<div class="lui_mportlet_options"></div>
											<div class="lui_mportlet_operations"></div>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="sys-mportal" key="sysMportalCard.authEditors"/>
						</td>
						<td colspan="3">
							<xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" textarea="true"/>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%><bean:message bundle="sys-mportal" key="sysMportalCard.docCreator" /></td>
						<td width="35%"><xform:text property="docCreatorName" showStatus="view" /></td>
						<td class="td_normal_title" width=15%><bean:message bundle="sys-mportal" key="sysMportalCard.docCreateTime" /></td>
						<td width="35%"><xform:text property="docCreateTime" showStatus="view" /></td>
					</tr>
				</table>
			</div>
			<html:hidden property="docCreatorId" />
			<html:hidden property="docCreateTime" />
			<html:hidden property="fdId" />
			<html:hidden property="fdPortletConfig" />
			<html:hidden property="method_GET" />
		</html:form>

		<script>
			var nameLengthError = '${lfn:message("sys-mportal:sysMportalCard.name.length.error")}';
		</script>
		<script src="${LUI_ContextPath}/sys/mportal/sys_mportal_card/js/edit.js"></script>
		<script>
			$KMSSValidation(document.forms[0]);
			if ('${sysMportalCardForm.method_GET}' == 'edit') {
				editInit();
			}
			function validate(){
				var moduleCateName=$('input[name="fdModuleCateName"]').val();
				if(moduleCateName == ""){
					alert('<bean:message bundle="sys-mportal" key="sysMportalCard.fdModuleCate"/><bean:message bundle="third-pda" key="validate.notNull"/>');
					return false;
				}
				return true;
			}
		</script>
	</template:replace>
</template:include>