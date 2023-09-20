<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="
	com.landray.kmss.util.KmssMessageWriter,
	com.landray.kmss.util.KmssReturnPage" %>
<%
	KmssMessageWriter msgWriter = null;
	if(request.getAttribute("KMSS_RETURNPAGE")!=null){
		msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
	}else{
		msgWriter = new KmssMessageWriter(request, null);
	}
	if(request.getHeader("accept")!=null){
		if(request.getHeader("accept").indexOf("application/json") >=0){
			out.write(msgWriter.DrawJsonMessage(false).toString());
			return;
		}
	}
	response.setHeader("lui-status","true");
%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<template:include ref="default.edit" sidebar="auto">
	<template:replace name="title">
		<c:out value="${modelingAppSimpleMainForm.fdModelName}"></c:out>
	</template:replace>
	<template:replace name="head">
		<script>
			var validation = $KMSSValidation();
			var modelingAttachments = {};
			var externalConfig={
				"fileLimitCount":${fileLimitCount},
				"singleFileSize":${singleFileSize},
				"fileMaxSize":${fileMaxSize},
				"fileEnabledType":'${fileEnabledType}',
			}
			var attachmentCount = {
				fileSizeCount : 0,
				fileCount:0
			}
			seajs.use(['lui/jquery','lui/dialog','sys/modeling/main/resources/js/modelingAttachment'], function($,dialog,modelingAttachment) {
				//地址本
				$("[flagtype='xform_address']").empty();
				//高级地址本
				$("[flagtype='xform_new_address']").empty();
				//公式加载
				$("[flagtype='formula_calculation']").empty();
				//附件
				$("[flagtype='xform_relation_attachment']").hide();
				//图片
				$("[flagtype='xform_docimg']").empty();
				//动态下拉框
				$("[flagtype='xform_relation_select']").empty();
				//动态单选
				$("[flagtype='xform_relation_radio']").empty();
				//动态多选
				$("[flagtype='xform_relation_checkbox']").empty();
				//选择框
				$("[flagtype='xform_relation_choose']").empty();
				//业务关联
				$("[flagtype='placeholder']").empty();
				//数据填充
				//大数据呈现
				$("[flagtype='massdata']").empty();
				//复合控件
				$("[flagtype='composite']").empty();
				//jsp片段
				//关联文档
				$("[flagtype='relevance']").empty();
				//图表控件dbechart
				$("[flagtype='dbechart']").empty();
				//片段集
				$("[flagtype='xform_fragmentSet']").empty();
				//excel导入
				$("[fd_type='standardTable']").each(function (index) {
					if(0 < index){
						$(this).empty();
					}
				});


				LUI.ready(function (){
					$("[flagtype='xform_relation_attachment']").each(function (i){
						var $attachment = $(this);
						//附件字段id
						var flagid = $attachment.attr("flagid");
						var attachmentObject = window["attachmentObject_"+flagid];
						$attachment.hide();
						window.setTimeout(function (){
							$attachment.empty();
							if(attachmentObject){
								var cfg = {
									container:$attachment,
									fdMulti:attachmentObject.fdMulti,
									required:attachmentObject.required,
									enabledFileType:attachmentObject.enabledFileType,
									fdKey:flagid
								};
								window["attach_"+flagid] = new modelingAttachment.ModelingAttachment(cfg);
								window["attach_"+flagid].startup();
								modelingAttachments[flagid] = window["attach_"+flagid];
								attachmentObject = null;
								$attachment.show();
							}
						},310);

					})
				})

				/*
				 *	数据唯一性校验
				 *
				 */
				window.Modeling_DataUniqueValidateShare = function(){
					var isUnique = true;
					var url = Com_Parameter.ContextPath + "sys/modeling/main/externalShare.do?method=dataValidate";
					$.ajax({
						url: url,
						type: "post",
						data: $('form').serialize(),
						async : false,
						success: function (rtn) {
							if (!rtn.status) {
								dialog.alert(rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }");
								isUnique = false;
							}else if (rtn.status === '01') {
								dialog.alert(rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }");
								isUnique = false;
							}
						},
						error : function(rtn){
							dialog.alert(rtn.errmsg || "${lfn:message('sys-modeling-main:modeling.data.uniqueness.verification.tips') }");
							isUnique = false;
						}
					});

					return isUnique;
				}
				window.submitForm=function(method){
					if (validation.validate() && Modeling_DataUniqueValidateShare()){
						var formObj = document.modelingAppSimpleMainForm;
						var i;
						var url = Com_Parameter.ContextPath + "sys/modeling/main/externalShare.do?method=save&fdAppModelId=${modelingAppSimpleMainForm.fdModelId}";
						if(method!=null)
							url = Com_SetUrlParameter(url, "method", method);
						var seq = parseInt(Com_GetUrlParameter(url, "s_seq"));
						seq = isNaN(seq)?1:seq+1;
						url = Com_SetUrlParameter(url, "s_seq", seq);
						formObj.action = url;
						var btns = document.getElementsByTagName("INPUT");
						for(i=0; i<btns.length; i++)
							if(btns[i].type=="button" || btns[i].type=="image")
								btns[i].disabled = true;
						btns = document.getElementsByTagName("A");
						for(i=0; i<btns.length; i++){
							btns[i].disabled = true;
							btns[i].removeAttribute("href");
							btns[i].onclick = null;
						}
						var formData = new FormData(formObj);
						for(var flagId in modelingAttachments){
							var modelingAttach = modelingAttachments[flagId];
							if(!modelingAttach.validate()){
								return false;
							}
							var fileList = modelingAttach.getKeyData();
							for(var name in fileList){
								var fielItem = fileList[name];
								formData.append(flagId, fielItem.file);
							}
						}
						$.ajax({
							//几个参数需要注意一下
							type: "POST",//方法类型
							dataType: "json",//预期服务器返回的数据类型
							url: url,
							data: formData,
							cache: false,
							contentType: false,
							processData: false,
							success: function (result) {
								if (result["status"]  === "1") {
									showSuccessPage();
								}else{
									dialog.failure(result["msg"]);
								}
							},
							error : function() {
								alert("${lfn:message('sys-modeling-main:modeling.findFlow.error') }");
							}
						});
					}
				}
			});

			function showSuccessPage(){
				var children = $("body").children();
				if(children && children.length > 0){
					for (var i = 0; i < children.length; i++) {
						var $dom = $(children[i]);
						$(children[i]).css("display","none")
					}
				}
				$(".prompt_frame").css("display","");
			}

			$(function(){
				$("#lui_validate_message").css("display","none");
				initDetailOperationAuth($("input[name='detailOperationAuthConfig']").val(), false, "${param.method}");
			})
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar var-navwidth="90%" id="toolbar" layout="sys.ui.toolbar.float" count="6">
			<c:if test="${modelingAppSimpleMainForm.method_GET=='add'}">
				<ui:button text="${saveButtonText}" order="1" onclick="submitForm('save');"></ui:button>
			</c:if>
			<ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow()"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
		<link type="text/css" rel="stylesheet"
			  href="${LUI_ContextPath}/sys/modeling/main/resources/css/externalShare.css?s_cache=${LUI_Cache}"/>
		<html:hidden property="detailOperationAuthConfig" value="${detailOperationAuthConfig}" />
		<html:form action="/sys/modeling/main/externalShare.do" enctype="multipart/form-data" method="post">
			<html:hidden property="listviewId" value="${param.listviewId}"/>
			<html:hidden property="fdId" value="${modelingAppSimpleMainForm.fdId}"/>
			<html:hidden property="fdModelId" />
			<html:hidden property="docCreateTime"/>
			<html:hidden property="docCreatorId"/>
			<html:hidden property="fdTreeNodeData" value="${modelingAppModelMainForm.fdTreeNodeData}"/>
			<br/>
			<%-- 表单 --%>
			<div id="sysModelingXform">
				<c:import url="/sys/xform/include/sysForm_edit.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppSimpleMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="messageKey" value="sys-modeling-main:modelingAppBaseModel.reviewContent" />
					<c:param name="useTab" value="false" />
				</c:import>
			</div>
			<br/>
			<script>
				Com_IncludeFile('view_common.js','${LUI_ContextPath}/sys/modeling/main/resources/js/','js',true);
				Com_IncludeFile("detailOperationAuth.js", "${LUI_ContextPath}/sys/modeling/main/resources/js/", 'js', true);
			</script>
		</html:form>

	</template:replace>
</template:include>
<div class="prompt_frame" style="display: none">
	<div class="prompt_centerL">
		<div class="prompt_centerR">
			<div class="prompt_centerC">
				<div class="prompt_container clearfloat">
					<div class="prompt_content_success"></div>
					<div class="prompt_content_right">
						<div class="prompt_content_inside">
							<bean:message key="return.title" />
							<%=msgWriter.DrawTitle(false)%>
							<%=msgWriter.DrawMessages()%>
							<span id="_timeArea" class="prompt_content_timer"></span>
						</div>
						<div class="prompt_buttons clearfloat">
							<%=msgWriter.DrawButton()%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>