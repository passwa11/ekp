<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<%
	String formName = request.getParameter("formName");
	IExtendForm form = (IExtendForm) request.getAttribute(formName);
	String url = "/kms/common/kms_common_push/kmsCommonDatapush.do?method=getPushModels&modelName=${modelName}&fdId=${fdId}";
	url = StringUtil.replace(url, "${modelName}", form.getModelClass().getName());
	url = StringUtil.replace(url, "${fdId}", form.getFdId());
	request.setAttribute("url", url);
%>


<script>
	var _dialog;
	seajs.use([ 'lui/dialog' ], function(dialog) {

		_dialog = dialog;
	});

	function datapush(modelName, fdId, cateModelName) {

		if ("com.landray.kmss.kms.wiki.model.KmsWikiMain" == modelName) {
			var kmsCommonPushAction = encodeURIComponent('${param.kmsCommonPushAction}');
			url = '/kms/common/kms_common_push/kms_common_push_wiki.jsp?fdModelId='
					+ fdId
					+ '&modelName='
					+ modelName
					+ "&kmsCommonPushAction=" + kmsCommonPushAction;
			seajs.use([ 'lui/dialog' ], function(dialog) {

				dialog.iframe(url,
						"${lfn:message('kms-common:kmsCommonDataPush.title')}",
						function() {

						}, {
							"width" : 700,
							"height" : 500
						});
			});

		} else if ("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge" == modelName) {
			seajs
					.use(
							[ 'sys/ui/js/dialog' ],
							function(dialog) {

								var create_url = '${param.kmsCommonPushAction}&fdTemplateId=!{id}&fdCategoryId=!{id}&modelName='
										+ modelName + '&fdModelId=' + fdId;
								dialog.simpleCategoryForNewFile(cateModelName,
										create_url, false, function(rtn) {

											// 无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
											if (!true && !rtn)
												window.close();
										}, null, LUI.$(
												'input[name=docCategoryId]')
												.val(), "_black", true, {
											'fdTemplateType' : '1,3'
										});
							});
		} // 原子知识管理
		else if ("com.landray.kmss.kms.kem.model.KmsKemMain" == modelName) {
			seajs
					.use(
							[ 'sys/ui/js/dialog' ],
							function(dialog) {

								var create_url = '${param.kmsCommonPushAction}&docCategoryId=!{id}&fdCategoryId=!{id}&modelName='
										+ modelName + '&fdModelId=' + fdId;
								dialog.simpleCategoryForNewFile(cateModelName,
										create_url, false, function(rtn) {

											// 无分类状态下（一般于门户快捷操作）创建文档，取消操作同时关闭当前窗口
											if (!true && !rtn)
												window.close();
										}, null, LUI.$(
												'input[name=docCategoryId]')
												.val(), "_black", true, {
											//'fdTemplateType' : '1,3'
										});
							});
		} else {
			var url = '${kmsCommonPushAction}&fdId=' + fdId + '&modelName='
					+ modelName;
			seajs.use([ 'lui/dialog' ], function(dialog) {

				dialog.iframe(url,
						"${lfn:message('kms-common:kmsCommonDataPush.title')}",
						function() {

						}, {
							"width" : 700,
							"height" : 500
						});
			});
		}

	}
</script>