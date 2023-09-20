<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="toolbar">
	</template:replace>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="5">
			<ui:button text="${lfn:message('button.save')}" onclick="editSaveWpsCenter();" order="1" />
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
		</ui:toolbar>

	<template:replace name="body">
		<script>Com_IncludeFile("jquery.js");</script>
		<script>Com_IncludeFile("polyfill.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/js/","js",true);</script>
		<script>Com_IncludeFile("web-office-sdk-v1.1.16.umd.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
		<script>Com_IncludeFile("wps_center_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/wps/center/js/","js",true);</script>
		<div id="WPSCenterOffice_${sysAttMainForm.fdKey}" class="wps-container">
		</div>
		<script>
			var wpscenterObj;

			var _centerTimer;
			var _retryCnt = 0;
			function setWpsCenterPageCommand() {
				clearTimeout(_centerTimer);
				try{
					_retryCnt ++;
					hiddenMoreFileMenu();
					hideDocumentMap();
					forceRevision();
					window.centerLoading_tip.hide();
					if(window.console) {
						console.log('wps文档中台页面按钮已成功初始化');
					}
				}catch(e){
					if(window.console) {
						console.log('wps文档中台未加载完毕，等待2秒后重新设置页面按钮，' + e.message);
					}
					if (_retryCnt > 1) {
						if(window.console) {
							console.log('超过重试次数，中台版本可能不支持前端接口' + e.message);
						}
						window.centerLoading_tip.hide();
					} else {
						_centerTimer =setTimeout("setWpsCenterPageCommand()", 2000);
					}
				}
			}

			$(document).ready(function(){
				var fdAttMainId ='${sysAttMainForm.fdId}';
		  		var fdKey = '${sysAttMainForm.fdKey}';
		  		var fdModelId = '${sysAttMainForm.fdModelId}';
		  		var fdModelName = '${sysAttMainForm.fdModelName}';
				wpscenterObj = new WPSCenterOffice_AttachmentObject(fdAttMainId,fdKey,fdModelId,fdModelName,"write",false);
				wpscenterObj.load();
				$("#office-iframe").height(window.innerHeight);
				//合同模块编辑页菜单控制
				if ('com.landray.kmss.km.agreement.model.KmAgreementApply' == wpscenterObj.fdModelName
						&& 'mainOnline' == wpscenterObj.fdKey) {
					seajs.use(['lui/dialog'],function(dialog){
						window.centerLoading_tip = dialog.loading();
					});
					setWpsCenterPageCommand();
				}

			});

			function editSaveWpsCenter(){
				seajs.use(['lui/dialog'], function(dialog) {
					const promise=wpscenterObj.submit();
					var def = $.Deferred();
					promise.then(function (result) {
						if (!result) {
							def.resolve(false);
						}else{
							def.resolve(true);
						}
					});
					def.then(function (result) {
						if (result) {
							dialog.alert("${lfn:message('return.optSuccess')}");
							//如果是从合同模块进入此页面，判断父页面是否已经关闭
							if ('com.landray.kmss.km.agreement.model.KmAgreementApply' == wpscenterObj.fdModelName
									&& 'mainOnline' == wpscenterObj.fdKey) {
								//通知父页面
								if (window.opener != null && window.opener.refreshKmAgreementApplyMain) {
									window.opener.refreshKmAgreementApplyMain();
								}
							}
						}else{
							dialog.alert("${lfn:message('return.optFailure')}");
						}
					});

				});

			}

			/**
			 * 屏蔽更多菜单
			 */
			function hiddenMoreFileMenu() {
				wpscenterObj.wpsObj.Application.CommandBars('MoreMenus').Visible = false;
			}

			/**
			 * 关闭导航
			 */
			function hideDocumentMap() {
				wpscenterObj.wpsObj.WpsApplication().ActiveDocument.ActiveWindow.DocumentMap = false;
			}

			/**
			 * 强制留痕，并且隐藏留痕操作
			 */
			function forceRevision() {
				wpscenterObj.wpsObj.setCommandBars([
					// 可以配置多个组件
					{
						cmbId: 'ReviewTrackChanges',
						attributes: {
							visible: false, // 隐藏组件
							enable: false, // 禁用组件， 组件显示但不响应点击事件
						}
					},
					{
						cmbId: 'TrackChanges',
						attributes: {
							visible: false, // 隐藏组件
							enable: false, // 禁用组件， 组件显示但不响应点击事件
						}
					},
					{
						cmbId: 'RevisionSetting',
						attributes: {
							visible: false, // 隐藏组件
							enable: false, // 禁用组件， 组件显示但不响应点击事件
						}
					}
				]);
				wpscenterObj.wpsObj.WpsApplication().ActiveDocument.Revisions.SwitchRevisionBtn(false);
			}

		</script>
	</template:replace>	
</template:include>	