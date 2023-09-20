<%@ page language="java" pageEncoding="UTF-8" import="com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit">
	<template:replace name="title">
		 ${lfn:message('sys-zone:sysZonePersonInfo.fdPersonResume.upload') } -  <c:out value="${name}"/>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" >
			<ui:menu-item text="${lfn:message('home.home')}" href="/index.jsp" icon="lui_icon_s_home" />
			<ui:menu-item text="${lfn:message('sys-zone:module.sys.zone') }" />
			<ui:menu-item text="${lfn:message('sys-zone:sysZonePersonInfo.fdPersonResume.upload') }" />
			<ui:menu-item text="${name}" />
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div style="margin-top: 10px;"> 
			<html:form action="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do" styleId="sysZonePersonInfoForm">
			  <ui:panel layout="sys.ui.panel.light" scroll="false" toggle="false">
				 <ui:content title="${lfn:message('sys-zone:sysZonePersonInfo.fdPersonResume')}">
			 		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
			          <c:param name="fdKey" value="personResume"/>
			          <c:param name="fdMulti" value="false" />
			          <c:param name="enabledFileType" value="*.doc;*.docx;*.ppt;*.pptx;*.pdf" />
			          <c:param name="fdModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
			          <c:param name="fdModelId" value="${fdId}"/>
			        </c:import> 
			        <br>
			        <table class="tb_normal" width="100%">
			        	<tbody>
			        	<tr>
			        		<td class="td_normal_title" width="15%">
			        		附件权限
			        		</td>
			        		<td  width="85%">
			        		可下载者
			        		<input type="checkbox" name="authAttNodownload" id="authAttNodownload" value="1" onclick="refreshDisplay(this,'names')">在阅读状态下所有人不可下载
			        		<div id="names">
			        			<xform:address textarea="true" showStatus="edit" mulSelect="true" propertyName="authAttDownloadNames" propertyId="authAttDownloadIds"></xform:address>
								<span class="com_help">（为空则所有用户可下载） </span>
							</div>
							<div class="div_img">
								支持的附件类型：
								<span style="position: relative;top: 6px;"><img src="${LUI_ContextPath}/sys/right/resource/images/Word.png" height="20" width="20" title="MS Word "></span>
								<span style="position: relative;top: 6px;"><img src="${LUI_ContextPath}/sys/right/resource/images/Excel.png" height="20" width="20" title="MS Excel "></span>
								<span style="position: relative;top: 6px;"><img src="${LUI_ContextPath}/sys/right/resource/images/PowerPoint.png" height="20" width="20" title="MS PowerPoint "></span>
								<span style="position: relative;top: 6px;"><img src="${LUI_ContextPath}/sys/right/resource/images/Project.png" height="20" width="20" title="MS Project "></span>
								<span style="position: relative;top: 6px;"><img src="${LUI_ContextPath}/sys/right/resource/images/Visio.png" height="20" width="20" title="MS Visio"></span>
							</div>
							</td>
						</tr>
						</tbody>
					</table>
				</ui:content>
			  </ui:panel>
			</html:form>
		</div>
		<div style="text-align: center;padding:10px;" id='saveResume'>
			<ui:button text="${lfn:message('button.save') }" onclick="saveResume();"></ui:button>
		</div>
		<script>
		
		(function() {
			var fileId = '';
			var attName = '';
			var isdel = '0';
			attachmentObject_personResume.on("uploadSuccess", function() {
				//清空附件机制生成的信息
				var fileList =  attachmentObject_personResume.fileList;
				fileId = fileList[fileList.length - 1].fileKey;
				attName = fileList[fileList.length - 1].fileName;
				$("#saveResume").show();
			});
			$(document).on("alterName", function() {
				var fileList =  attachmentObject_personResume.fileList;
				fileId = fileList[fileList.length - 1].fileKey;
				attName = fileList[fileList.length - 1].fileName; 
			}); 
			attachmentObject_personResume.on("editDelete", function(file) {
				fileId = '';
				attName = '';
				isdel = '1';
				$("#saveResume").show();
			});
			/*
			*保存简历
			*/
			window.saveResume = function() { 
				seajs.use(['lui/jquery', 'lui/dialog','lui/util/env'], function($, dialog, env) {
					<c:if test="${personResume == null}">
					if(attName==''){
						dialog.failure("${ lfn:message('sys-zone:sysZonePersonInfo.uploadResume') }");
						return;
					}
					</c:if>
					var load  = dialog.loading("${ lfn:message('sys-zone:sysZonePersonInfo.resumeSaveLoading') }");
					$.ajax({
						type:"post",
						url:"${LUI_ContextPath}/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=saveOtherResume",		
						data:{
								personId: "${fdId}",
								fileName: attName,
							 	fileId: fileId,
							 	authAttDownloadIds:$("input[name='authAttDownloadIds']")[0].value,
							 	authAttNodownload:$("input[name='authAttNodownload']")[0].checked,
							 	isdel:isdel
						},
						success:function(data) {
							load.hide();
							$("#saveResume").hide();
							dialog.success("${ lfn:message('sys-zone:sysZonePersonInfo.saveSuccess') }");
						},
						error:function() {
							load.hide();
							dialog.failure("${ lfn:message('sys-zone:sysZonePersonInfo.saveFailure') }");
						}
					});
				});
			}
		})()
		
		window.onload = function(){
			var divObj = document.getElementById('names');
			var checkObj = document.getElementById('authAttNodownload');
			if(${sysZonePersonInfoForm.authAttNodownload}){
				divObj.style.display="none";
				checkObj.checked="true";
			}else{
				divObj.style.display="";
			}
		}

			function refreshDisplay(obj,divName){
				var divObj = document.getElementById(divName);
				divObj.style.display=(obj.checked?"none":"");
			}
		</script>
	</template:replace>
</template:include>