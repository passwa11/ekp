<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<%-- 右边框内容 --%>
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<bean:message bundle="sys-xform" key="sysFormDbGet.sqlParam.value" />
		</h2>
		
		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">
			<center>
				<div style="margin:auto auto 60px;">
					<table class="tb_normal" width=95%>
						<tr>
							<td class="td_normal_title" width=15%>
								<bean:message bundle="sys-xform" key="associated.document.status" />
							</td>
							<td width=85%>
								<xform:checkbox property="value(defaultRelevanceDocStatus)" showStatus="edit" notChangedValues="30">
									<xform:enumsDataSource enumsType="sysForm_status" />
								</xform:checkbox>
								</br>
								<bean:message bundle="sys-xform" key="sysFormDefaultSwitch.relevance.docStatus.des" />
							</td>
						</tr>
						<!-- 移动端样式 -->
						<tr>
							<td class="td_normal_title" width=15%><bean:message bundle="sys-xform-base" key="Designer_Lang.mobileControlStyle" /></td>
							<td width="85%">
								<ui:switch id="showMobileStyle" property="value(showMobileStyle)" disabledText="${ lfn:message('sys-xform-base:Designer_Lang.mobileControlStyleClose') }" enabledText="${ lfn:message('sys-xform-base:Designer_Lang.mobileControlStyleOpen') }"></ui:switch>
								<bean:message bundle="sys-xform-base" key="Designer_Lang.mobileControlStyleDesc" />
								<a class="com_btn_link" href="javascript:void(0);" onclick="forwardToUpdateJsp();"><bean:message bundle="sys-xform-base" key="Designer_Lang.mobileControlStyleUpdate" /></a>
							</td>
						</tr>
						<!-- 移动端桌面端模式 -->
						<tr>
							<td class="td_normal_title" width=15%><bean:message bundle="sys-xform-base" key="Designer_Lang.isNewDesktopLayout" /></td>
							<td width="85%">
								<ui:switch id="isNewDesktopLayout" property="value(isNewDesktopLayout)" disabledText="${ lfn:message('sys-xform-base:Designer_Lang.isNewDesktopLayoutClose') }" enabledText="${ lfn:message('sys-xform-base:Designer_Lang.isNewDesktopLayoutOpen') }"></ui:switch>
								<bean:message bundle="sys-xform-base" key="Designer_Lang.isNewDesktopLayoutDesc" />
							</td>
						</tr>
						<!-- 表单内容存储形式 -->
						<tr>
							<td class="td_normal_title" width=15%><bean:message bundle="sys-xform-base" key="Designer_Lang.formDataPersistenceFormat" /></td>
							<td width="85%">
								<ui:switch id="isFormDataPersistenceFormatJson" property="value(isFormDataPersistenceFormatJson)" disabledText="${ lfn:message('sys-xform-base:Designer_Lang.formDataPersistenceFormatClose') }" enabledText="${ lfn:message('sys-xform-base:Designer_Lang.formDataPersistenceFormatOpen') }"></ui:switch>
								<bean:message bundle="sys-xform-base" key="Designer_Lang.formDataPersistenceFormatDesc" />
							</td>
						</tr>
					</table>
				</div>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.sys.xform.base.model.SysFormDefaultSwitch" />
			
			<center style="margin-top: 10px;">
			<!-- 保存 -->
			<ui:button text="${lfn:message('button.save')}" style="position: fixed;bottom:0px;left: 15px;width:95%;background: #fff;" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');" order="1" ></ui:button>
			</center>
		</html:form>
		<script>
		
			function sysFormDefaultSwitchInit(){
				/************************关联文档状态默认值处理 start *****************************/
				var $publicDom = $("[name='_value(defaultRelevanceDocStatus)'][value='30']");
				// 如果没有选中，证明是首次进来
				if(!$publicDom.is(':checked')){
					$publicDom.attr("checked","checked");
					$("[name='value(defaultRelevanceDocStatus)']").val("30");
				}
				/************************关联文档状态默认值处理 end *****************************/
			}
			
			function forwardToUpdateJsp(){
				Com_OpenWindow('<c:url value="/sys/xform/base/sysFormTemplateToReUpdate.jsp"/>');
			}
			
			Com_AddEventListener(window,'load',sysFormDefaultSwitchInit);
		</script>
	</template:replace>
</template:include>