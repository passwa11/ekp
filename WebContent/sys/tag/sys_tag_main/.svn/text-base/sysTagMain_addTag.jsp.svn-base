<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<c:set var="sysTagMainForm" value="${tag_MainForm.sysTagMainForm}" />
<c:set var="tag_modelName" value="${tag_MainForm.modelClass.name}" />
<template:include ref="default.simple">

	<template:replace name="head">
		<template:super />
		<script>
			seajs.use([ 'theme!form' ]);
		</script>
		<script type="text/javascript">
			Com_IncludeFile("data.js|dialog.js");
			Com_IncludeFile("tag.js",
					"${LUI_ContextPath}/sys/tag/resource/js/", "js", true);
		</script>

		<script type="text/javascript">
			var tag_params = {
				"model" : "edit",
				"fdQueryConditionValue" : "${HtmlParam.fdQueryCondition}",
				"tag_msg1" : "<bean:message bundle='sys-tag' key='sysTagMain.message.1'/>",
				"tag_msg2" : "<bean:message bundle='sys-tag' key='sysTagMain.message.2'/>",
				"tree_title" : "<bean:message key='sysTagTag.tree' bundle='sys-tag'/>",
			};
			if (window.tag_opt == null) {
				window.tag_opt = new TagOpt('${tag_modelName}',
						'${sysTagMainForm.fdModelId}',
						'${sysTagMainForm.fdKey}', tag_params);
			}
			Com_AddEventListener(window, 'load', function() {
				window.tag_opt.onload();
			});
			
			function TagformSubmit() {
				seajs
						.use(
								[ 'lui/jquery', 'lui/dialog', 'lui/util/env' ],
								function($, dialog, env) {
									if(!$('[name="sysTagMainForm.fdTagNames"]').val()){
										dialog.failure("${lfn:message('sys-tag:sysTagCategory.addTags.msg.tags')}");
										return;
									}
									$
											.ajax({
												url : env.fn
														.formatUrl("/sys/tag/sys_tag_main/sysTagMain.do?method=saveTag&fdModelId=${tag_MainForm.fdId}&fdModelName=${tag_modelName}"),
												async : false,
												cache : false,
												data : $("#tagsForm")
														.serialize(),
												type : "POST",
												dataType : "json",
												success : function(data) {
													dialog
															.success("${lfn:message('return.optSuccess')}");
													//新填写标签addTagNames
													var addTagNames =$.trim(document
															.getElementsByName("sysTagMainForm.fdTagNames")[0].value);
													//原有标签orgTagNames
													var orgTagNames = $.trim(document
															.getElementsByName('___fdTagNames___')[0].value);
													
													var adds = addTagNames.split(/[;；]/);
													
													var orgs = orgTagNames.split(/;|；/);
													
													var new_tags=[];
													
													for(var i=0;i<adds.length;i++){
														//将新标签做判空和除重复处理   后放入new_tags中
														if($.trim(adds[i])!=""&&$.inArray($.trim(adds[i]), new_tags) ==-1){
															new_tags.push(adds[i]);
														}
														
													}
													
													for (var i = 0; i < new_tags.length; i++) {
														//将new_tags(新标签)加入旧标签中(变长)
														var t = new_tags[i];
														if (!t){//再次判空
															continue;
														}
														if ($.inArray(t, orgs) < 0) {
															if(orgTagNames){
																orgTagNames += ';';
															}
															orgTagNames += t ;//旧标签尾部顺延增长
														}
													}
													var top = Com_Parameter.top || window.top;
													if(top.tag_opt != null){
														top.tag_opt.params.fdTagNames = orgTagNames;
														top.tag_opt.onload();
													}else{
														top.location.reload();
														top.tag_opt.onload();
													}
													window.$dialog.hide();
													top.location.reload();
												},
												error : function(e) {
													
													if(e.responseJSON && e.responseJSON.message[0] && e.responseJSON.message[0].msg) {
														if(e.responseJSON.message[0].msg.indexOf("非法值") > 0) {
															dialog
																.failure(e.responseJSON.message[0].msg);
															return
														}
													}
													dialog
														.failure("${lfn:message('return.optFailure')}");
												}
											});
								});
			}
			var __top = Com_Parameter.top || window.top;
			__top.addTagSign = 1;
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="lui_tag_dialogbox">
			<form id="tagsForm" onsubmit="return false;">
			
				<input type="hidden" name="sysTagMainForm.fdId" value="${sysTagMainForm.fdId}" />
				<input type="hidden" name="sysTagMainForm.fdKey" value="${sysTagMainForm.fdKey}" /> 
				<input type="hidden" name="sysTagMainForm.fdModelName" value="${tag_modelName}" /> 
				<input type="hidden" name="sysTagMainForm.fdModelId"/>
				<input type="hidden" name="sysTagMainForm.fdQueryCondition" value="${sysTagMainForm.fdQueryCondition}" />
				<input type="hidden" name="sysTagMainForm.fdTagIds"/>
				<input type="hidden" name="___fdTagNames___" value="${fn:escapeXml(sysTagMainForm.fdTagNames) }"/>
				
				<div class="lui_tag_inputbox inputselectsgl">
					<div class="input">
						<input type="text" name="sysTagMainForm.fdTagNames" value="${lfn:escapeHtml(sysTagMainForm.fdTagNames)}">
					</div>
					<div class="selectitem" id="tag_selectItem"></div>
				</div>
				<div class="tag_prompt_area" id="id_application_div">
					<div id="id_application_div_div">
						<p>
							<bean:message bundle="sys-tag" key="sysTagMain.message.0" />
						</p>
						<p id="hot_id">
							<bean:message bundle="sys-tag" key="sysTagMain.message.1" />
						</p>
						<p id="used_id">
							<bean:message bundle="sys-tag" key="sysTagMain.message.2" />
						</p>
					</div>
				</div>
				<div class="lui_tag_dialogbox_footer">
					<ui:button text="${lfn:message('button.save') }" order="2"
						onclick="TagformSubmit();"></ui:button>
				</div>
			</form>

		</div>
	</template:replace>
</template:include>