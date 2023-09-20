<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.kms.knowledge.model.KmsKnowledgeFileStoreConfig"%>
<%
	KmsKnowledgeFileStoreConfig fileStoreConfig = new KmsKnowledgeFileStoreConfig();
	String providerName = fileStoreConfig.getFileStoreProvider();
	pageContext.setAttribute("_providerName",providerName);
	pageContext.setAttribute("prefix",KmsKnowledgeFileStoreConfig.FILE_STORE_PROVIDER);
	pageContext.setAttribute("timeWaitInterval",KmsKnowledgeFileStoreConfig.FILE_STORE_TIME_WAIT_INTERVAL);

%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title">${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.title')}</span>
		</h2>

		<html:form action="/kms/knowledge/kms_knowledge_file_store_config/kmsKnowledgeFileStoreConfig.do">
			<center>
				<table class="tb_normal" width=95%>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.module')}
						</td>
						<td colspan="3">
							<xform:radio
									property="value(${prefix}.module)"
									subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.module')}"
									value="kms-multidoc"
									showStatus="edit">
								<xform:simpleDataSource value="kms-multidoc">
									${lfn:message('kms-multidoc:module.kms.multidoc')}
								</xform:simpleDataSource>
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.type')}
						</td>
						<td colspan="3">
							<xform:radio
									property="value(${prefix})"
									subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.type')}"
									showStatus="edit">
								<xform:simpleDataSource value="ftp">
									${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp')}
								</xform:simpleDataSource>
							</xform:radio>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.timeWaitInterval')}
						</td>
						<td colspan="3">
							<xform:text subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.timeWaitInterval')}" validators="digits min(2)" property="value(${timeWaitInterval})"></xform:text>
							<span style="color: red;">${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.timeWaitInterval.desc')}</span>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.host')}
						</td>
						<td colspan="3">
							ftp://<xform:text subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.host')}" required="true" property="value(${prefix}.ftp.host)" validators="required"></xform:text>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.port')}
						</td>
						<td colspan="3">
							<xform:text subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.port')}" required="true" property="value(kms.knowledge.filestore.provider.ftp.port)" validators="required digits"></xform:text>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.userName')}
						</td>
						<td colspan="3">
							<xform:text subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.userName')}" required="true" property="value(kms.knowledge.filestore.provider.ftp.userName)" validators="required"></xform:text>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.password')}
						</td>
						<td colspan="3">
							<xform:text subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.password')}" required="true" property="value(kms.knowledge.filestore.provider.ftp.password)" validators="required"></xform:text>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="20%">
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.basePath')}
						</td>
						<td colspan="3">
							<xform:text subject="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.ftp.basePath')}" property="value(kms.knowledge.filestore.provider.ftp.basePath)"></xform:text>
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.rootPath.describe1')}
							<span style="color:red">${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.rootPath.describe.ftp')}</span>
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.rootPath.describe3')}
							<span style="color:red">${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.rootPath.describe.path')}</span>
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.rootPath.describe5')}
							<span style="color:red">${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.rootPath.describe.split')}</span>
								${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.property.rootPath.describe7')}
						</td>
					</tr>
				</table>
			</center>
			<html:hidden property="method_GET" />
			<input type="hidden" name="modelName" value="com.landray.kmss.kms.knowledge.model.KmsKnowledgeFileStoreConfig" />

			<center style="margin-top: 10px;">
				<!-- 保存 -->
				<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysAppConfigForm, 'update');"></ui:button>
				<ui:button text="${lfn:message('kms-knowledge:kmsKnowledgeFileStoreExcelImport.provider.checkServerIsAvailable')}" height="35" width="120" onclick="checkServiceAvailable()"></ui:button>
			</center>
		</html:form>

		<script type="text/javascript">
			seajs.use(["lui/dialog","kms/knowledge/kms_knowledge_file_store/resource/js/commonAjaxSubmit"],function (dialog,commonAjaxSubmit){
				window.alert = dialog;
				window.dialog = dialog;
				window.__doAjax = commonAjaxSubmit.doAjax;
			})

			var validation = $KMSSValidation();
			$(document).ready(function(){
				var provider = $("input[name='value(kms.knowledge.filestore.provider)']:checked").val();
				if(!provider){
					$($("input[name='value(kms.knowledge.filestore.provider)']")[0]).click();
				}
			});
			window.checkServiceAvailable = function(){
				if(!validation.validate()){
					return;
				}
				var provider = $("input[name='value(kms.knowledge.filestore.provider)']:checked").val();
				var host = $("input[name='value(kms.knowledge.filestore.provider.ftp.host)']").val();
				var port = $("input[name='value(kms.knowledge.filestore.provider.ftp.port)']").val();
				var userName = $("input[name='value(kms.knowledge.filestore.provider.ftp.userName)']").val();
				var password = $("input[name='value(kms.knowledge.filestore.provider.ftp.password)']").val();
				var basePath = $("input[name='value(kms.knowledge.filestore.provider.ftp.basePath)']").val();
				var data = {
					provider: provider,
					host: host,
					port: port,
					userName: userName,
					password: password,
					basePath: basePath
				}
				window.__doAjax("/kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do?method=checkServiceIsAvailable",
						data,true);
			}
			window.downloadTemplate = function (){
				window.open(Com_Parameter.ContextPath + "kms/knowledge/kms_knowledge_file_store/kmsKnowledgeFileStoreImportExport.do?method=exportTemplate");
			}
		</script>
	</template:replace>
</template:include>
