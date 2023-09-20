<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.config.util.LicenseUtil,com.landray.kmss.util.*" %>
<%@ page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService" %>
<%@ page import="com.landray.kmss.sys.authorization.util.TripartiteAdminUtil" %>

<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	String importTips = null;
	// 获取可导入人员数量
	int licenseOrgPerson = StringUtil.getIntFromString(LicenseUtil.get("license-org-person"), 9999999);
	if(licenseOrgPerson == 9999999) {
		importTips = ResourceUtil.getString("sys.profile.orgImport.person.tip1", "sys-profile");
	} else {
		// 获取已存在人员数量
		int personCount = ((ISysOrgCoreService) SpringBeanUtil.getBean("sysOrgCoreService")).getCountByRegistered(false, true);
		importTips = ResourceUtil.getString("sys.profile.orgImport.person.tip2", "sys-profile", null, new Object[]{licenseOrgPerson, (licenseOrgPerson - personCount)});
	}
%>

<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/profile/org/io/import.css"/>
		<script type="text/javascript">
			Com_IncludeFile("data.js");
		</script>
	</template:replace>
	<template:replace name="content">
		<form name="templet" action="${LUI_ContextPath}/sys/profile/org/orgImport.do?method=downloadTemplet" method="post">
		</form>

		<div class="profile_orgIO_wrapper">
			<h2 class="profile_orgIO_tips"><%=importTips%></h2>
			<div class="profile_orgIO_content">
				<p class="title title01">
					<bean:message bundle="sys-profile" key="sys.profile.orgImport.download.info1" />
					<a href="javascript:void(0)" class="profile_orgIO_link" onclick="downloadTemplet();"><bean:message bundle="sys-profile" key="sys.profile.orgImport.download.info2" /></a>，
					<bean:message bundle="sys-profile" key="sys.profile.orgImport.download.info3" />
				</p>
				<form id="importForm" name="importForm" action="${LUI_ContextPath}/sys/profile/org/orgImport.do?method=importData" method="post" enctype="multipart/form-data">
					<input type="hidden" name="type" value="orgdept"/>
					<!--上传文件 开始-->
					<div class="profile_orgIO_fileUpload_item">
						<div class="fileUpload">
							<div class="file_wrap">
								<input class="input_file" id="input_file" type="file" name="file" accept=".xls,.xlsx" onchange="fileChange(this);"/>
								<label class="profile_orgIO_uploadBtn" for="input_file">
									<bean:message bundle="sys-profile" key="sys.profile.orgImport.upload" />
								</label>
								<span class="profile_orgIO_fileTitle"><bean:message bundle="sys-profile" key="sys.profile.orgImport.select.file" /></span>
							</div>
						</div>
						<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { // 以下内容非三员时显示 %>
						<p class="profile_orgIO_opt">
							<label class="profile_label"><input type="checkbox" name="isReplaceName"/><span><bean:message bundle="sys-profile" key="sys.profile.orgImport.replace" /></span></label>
						</p>
						<% } else { %>
						<p style="display: none;">
							<input type="checkbox" name="isReplaceName" checked="checked"/>
						</p>
						<% } %>
					</div>
					<!--上传文件 结束-->
					<p class="title title02">
					<% if(!TripartiteAdminUtil.IS_ENABLED_TRIPARTITE_ADMIN) { // 以下内容非三员时显示 %>
						<bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.info1" />
						<a href="javascript:void(0)" class="profile_orgIO_link" onclick="advancedImport('orgImport');"><bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.info2" /></a>
						<bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.info3" />
						<a href="javascript:void(0)" class="profile_orgIO_link" onclick="advancedImport('deptImport');"><bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.info4" /></a>
						<bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.info3" />
						<a href="javascript:void(0)" class="profile_orgIO_link" onclick="advancedImport('personImport');"><bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.info5" /></a>
						<bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.info6" />
					<% } else if(TripartiteAdminUtil.isSysadmin()) { %>
					<bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.sysadmin" />
					<% } else if(TripartiteAdminUtil.isSecurity()) { %>
					<bean:message bundle="sys-profile" key="sys.profile.orgImport.tip.security" />
					<% } %>
					</p>
					<div class="profile_orgIO_btn_wrap">
						<a href="javascript:void(0)" class="profile_orgIO_btn btn_def" onclick="importOrgdept();"><bean:message bundle="sys-profile" key="sys.profile.orgImport.import" /></a>
						<a href="javascript:void(0)" class="profile_orgIO_btn btn_gray" onclick="reset();"><bean:message key="button.cancel" /></a>
					</div>
				</form>
				<p class="profile_orgIO_result">
					${resultMsg}
				</p>
			</div>
		</div>

	 	<script type="text/javascript">
		 	seajs.use([ 'lui/jquery', 'lui/dialog' ], function($, dialog) {
				window.importOrgdept = function() {
					var file = $('#importForm input[name=file]');
					if (file.val() == "") {
						dialog.alert('<bean:message bundle="sys-profile" key="sys.profile.orgImport.empty.file" />');
					} else {
						$("#importForm").submit();
						// 开启进度条
						window.progress = dialog.progress(false);
						window._progress();
					}
				}
				window._progress = function () {
					var data = new KMSSData();
					data.UseCache = false;
					data.AddBeanData("sysOrgImportXMLDataService");
					var rtn = data.GetHashMapArray()[0];
					
					if(window.progress) {
						var currentCount = parseInt(rtn.currentCount || 0);
						var allCount = parseInt(rtn.totalCount || 0);
						if(rtn.importState == 1 || currentCount > allCount) {//#119814
							window.progress.hide();
						}
						// 设置进度值
						if(allCount == -1 || allCount == 0) {
							window.progress.setProgress(0);
						} else {
							window.progress.setProgressText('<bean:message bundle="sys-profile" key="sys.profile.orgImport.progress" />' + currentCount + '/' + allCount);
							window.progress.setProgress(currentCount, allCount);
						}
					}
					if(rtn.importState != 1) {
						setTimeout("window._progress()", 1000);
					}
				} 
			});

			function reset() {
				document.forms[0].reset();
				$(".profile_orgIO_result").html('');
				$(".profile_orgIO_fileTitle").text('<bean:message bundle="sys-profile" key="sys.profile.orgImport.select.file" />');
			}
		
			function fileChange(file) {
				var index = file.value.lastIndexOf("\\");
				var fileName = file.value.substring(index + 1);
				$(".profile_orgIO_fileTitle").text(fileName);
			}

			function downloadTemplet() {
				document.templet.submit();
			}

			function advancedImport(type) {
				window.top.location.href = "${LUI_ContextPath}/sys/profile/index.jsp#org/baseSetting/" + type;
				window.top.location.reload();
			}
		</script>
	</template:replace>
</template:include>
