<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="default.simple" sidebar="auto">
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/> - <bean:message key="global.init.export.data"/>
	</template:replace>
	<template:replace name="head">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/sys/organization/resource/css/matrixData.css">
	</template:replace>
	<template:replace name="body">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
    	 	<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();"/>
		</ui:toolbar>
		
		<div style="width: 95%; margin: 100px auto;">
			<p class="txttitle">
				<bean:message bundle="sys-organization" key="table.sysOrgMatrix"/> - <bean:message key="global.init.export.data"/> - ${lfn:escapeHtml(sysOrgMatrixForm.fdName)}
			</p>
			
			<!-- 矩阵导入 Starts  -->
	        <div class="lui_maxtrix_import_wrap lui_maxtrix_import_disable">
	            <!-- 图标 -->
	            <span class="lui_maxtrix_import_icon icon_add">
	                <i></i>
	            </span>
	            <!-- 新建 -->
	            <p class="lui_maxtrix_import_txt"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.selectFile"/></p>
	            <p class="import_desc">
	            	<span><bean:message bundle="sys-organization" key="sysOrgMatrix.import.note"/></span>
	            	<a href="javascript:downloadTemplate();" class="lui_text_primary">
	            		<bean:message bundle="sys-organization" key="sysOrgMatrix.template.download"/>
	            	</a>
	            </p>
	            <p class="import_btnGroup">
	            	<a class="com_bgcolor_d" href="javascript:upload(false);"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.append"/></a>
	            	<a class="com_bgcolor_d" href="javascript:upload(true);"><bean:message bundle="sys-organization" key="sysOrgMatrix.import.update"/></a>
	            </p>
	        </div>
	        <!-- 矩阵导入 Ends  -->
			
			<html:form action="/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=saveMatrixData&fdId=${sysOrgMatrixForm.fdId}" enctype="multipart/form-data">
				<input type="file" name="file" accept=".xls,.xlsx" style="display: none;" onchange="fileChange(this);"/>
				<input type="hidden" name="append" value="true">
			</html:form>
		</div>
		<!-- 模板下载 -->
		<form id="downloadTemplateForm" action="${LUI_ContextPath}/sys/organization/sys_org_matrix/sysOrgMatrix.do?method=downloadTemplate&fdId=${sysOrgMatrixForm.fdId}" method="post"></form>
		<script language="JavaScript">
			Com_IncludeFile("data.js");
			var flag = true, temp = 1;
			seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
				// 模板下载
				window.downloadTemplate = function() {
					$("#downloadTemplateForm").submit();
				};
				
				// 文件上传
				window.upload = function(replace) {
					// 判断是否禁用状态
					if($(".lui_maxtrix_import_wrap").hasClass("lui_maxtrix_import_disable")) {
						// 禁用状态，不可点击
						return false;
					}
					if(replace) {
						dialog.confirm("<bean:message bundle="sys-organization" key="sysOrgMatrix.import.confirm"/>", function(val) {
							if(val) {
								$("[name=append]").val("fales");
								doUpload();
							}
						});
					} else {
						doUpload();
					}
				}
				
				// 文件上传
				window.doUpload = function() {
					dialog.loading();
					var file = document.getElementsByName("file");
					if(file[0].value == null || file[0].value.length == 0) {
						dialog.alert("<bean:message bundle='sys-organization' key='sysOrgMatrix.import.file.empty'/>");
						return false;
					} else {
						document.forms['sysOrgMatrixForm'].submit();
					}
				}
				
				$(function() {
					// 点击选择文件
					$(".lui_maxtrix_import_icon").on("click", function() {
						$("[name=file]").click();
					});
				});
			});
			
			function fileChange(file) {
				if(file.files.length > 0) {
					$(".lui_maxtrix_import_wrap").removeClass("lui_maxtrix_import_disable");
					$(".lui_maxtrix_import_txt").text(file.files[0].name + " (" + renderSize(file.files[0].size) + ")");
					$(".lui_maxtrix_import_icon").removeClass("icon_add");
					$(".lui_maxtrix_import_icon").addClass("icon_excel");
					$(".lui_maxtrix_import_icon i").text("<bean:message bundle='sys-organization' key='sysOrgMatrix.import.replace'/>");
				} else {
					$(".lui_maxtrix_import_wrap").addClass("lui_maxtrix_import_disable");
					$(".lui_maxtrix_import_txt").text("<bean:message bundle='sys-organization' key='sysOrgMatrix.import.selectFile'/>");
					$(".lui_maxtrix_import_icon").removeClass("icon_excel");
					$(".lui_maxtrix_import_icon").addClass("icon_add");
					$(".lui_maxtrix_import_icon i").text("");
				}
			}

			function renderSize(value) {
				if (null == value || value == '') {
					return "0 Bytes";
				}
				var unitArr = new Array("Bytes", "KB", "MB", "GB");
				var index = 0;
				var srcsize = parseFloat(value);
				index = Math.floor(Math.log(srcsize) / Math.log(1024));
				var size = srcsize / Math.pow(1024, index);
				size = size.toFixed(2);//保留的小数位数
				return size + unitArr[index];
			}
		</script>
	</template:replace>
	
</template:include>
