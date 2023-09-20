<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.list">
	<template:replace name="title">${ lfn:message('sys-organization:sysOrgElement.orgChart') }</template:replace>
	<template:replace name="content">
		<link rel="stylesheet" type="text/css" href="${ KMSS_Parameter_ContextPath}sys/organization/resource/css/orgChart.css?s_cache=${LUI_Cache}">
		<link rel="stylesheet" type="text/css" href="${ KMSS_Parameter_ContextPath}sys/organization/resource/css/dropDownList.css?s_cache=${LUI_Cache}">
		<script type="text/javascript" src="${LUI_ContextPath}/sys/organization/resource/js/spryMap-2.js?s_cache=${LUI_Cache}"></script>
		<script type="text/javascript" src="${LUI_ContextPath}/sys/organization/resource/js/jquery.orgchart.js?s_cache=${LUI_Cache}"></script>
        <script type="text/javascript" src="${LUI_ContextPath}/hr/organization/resource/js/orgChart.js?s_cache=${LUI_Cache}"></script>
         
         <div align="center" style=" font-family:微软雅黑; font-weight:bold; font-size:12px; height:55px; line-height:55px;">
             <img src="${LUI_ContextPath}/sys/organization/resource/image/showBig.png" style=" cursor:pointer;vertical-align: middle;" onclick="ShowBig()">
             <img src="${LUI_ContextPath}/sys/organization/resource/image/showSmall.png" style=" cursor:pointer;vertical-align: middle;" onclick="ShowSmall()">
            		<bean:message bundle="sys-organization" key="sysOrgElement.dropDown"/>：
             <select id="select_Arrow" onchange="selectChange(this)" class="dropDownList" style="width:100px;"></select>
         </div>
         <div style="display: inline-block; float: right; overflow: hidden; margin-right: 30px; margin-top: -40px;">
         	<!-- 导出图片 -->
			<ui:button text="${ lfn:message('sys-organization:button.export.image') }" onclick="outputImage();" />
			<!-- 导出PDF -->
			<ui:button text="${ lfn:message('button.export.pdf') }" onclick="outputPDF();" />
         </div>
		<div id="worldMap"> 
			<div id="left" style="display: none;">
				<ul id="organisation"></ul>
			</div> 
			<div id="main"></div> 
		</div>

		<style type="text/css">
			::-webkit-scrollbar {
				height: 0px;
			}
		</style>
		<script>
		window.allExpandText = '<bean:message bundle="sys-organization" key="sysOrgElement.dropDown.all"/>';
		var notsupportIE = "${lfn:message('sys-organization:organization.export.notsupport.ie')}";
		var notsupportFirefox = "${lfn:message('sys-organization:organization.export.notsupport.firefox')}";
		var notsupportSafari = "${lfn:message('sys-organization:organization.export.notsupport.safari')}";
		var fileName = "${ lfn:message('sys-organization:organization.export.filename') }";
		fileName = fileName.replace(/\s+/g,"");
		
		function outputPDF() {
			seajs.use(['lui/dialog'],function(dialog) {
				window.export_load = dialog.loading();
			});
			seajs.use(['lui/jquery','sys/organization/resource/js/export.orgchart.js'],function($,exp) {
				exp.exportPdf(document.getElementById('main'),fileName+'.pdf');
			});
		}
		
		function outputImage() {
			seajs.use(['lui/dialog'],function(dialog) {
				window.export_load = dialog.loading();
			});
			seajs.use(['lui/jquery','sys/organization/resource/js/export.orgchart.js'],function($,exp) {
				exp.exportImage(document.getElementById('main'),fileName+".png");
			});
		}
		
		$(function(){
			window.root_FdId = '${param.parent}';  // 根节点组织机构ID 
			var fdId = window.root_FdId;   // 组织机构ID
			var sceneId = "page_init";     // 场景标识 （页面初始化）
			GetOrgChartAjax( fdId, sceneId, null );
		});
		</script>
	</template:replace>
</template:include>