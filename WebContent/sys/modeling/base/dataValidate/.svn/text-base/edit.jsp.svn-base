<%@ page import="com.landray.kmss.sys.modeling.base.forms.ModelingAppDataValidateForm" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<%
	ModelingAppDataValidateForm modelingAppDataValidateForm = (ModelingAppDataValidateForm) request.getAttribute("modelingAppDataValidateForm");
	if(modelingAppDataValidateForm != null){
		String fdConfig = modelingAppDataValidateForm.getFdCfg();
		if (StringUtil.isNotNull(fdConfig)) {
			//将json的value中的"替换为\"
			fdConfig = fdConfig.replaceAll("\\\\\"","\\\\\\\\\"");
			pageContext.setAttribute("fdConfig",fdConfig);
		}
	}
%>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<link type="text/css" rel="stylesheet"
              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/dataValidate.css?s_cache=${LUI_Cache}"/>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/modeling/base/modelingAppDataValidate.do">
			<div class="data-validate-main">
				<div class="data-validate-content">
					<table class="tb_simple" width="100%">
						<tr>
							<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:modeling.model.fdName')}</td>
							<td>
								<xform:xtext property="fdName" required="true" subject="${lfn:message('sys-modeling-base:modeling.model.fdName')}" style="width:96%;box-sizing:border-box"></xform:xtext>
							</td>
						</tr>
						<tr>
								<%-- 描述 --%>
							<td class="td_normal_title" width=15% style="vertical-align: top !important;">${lfn:message('sys-modeling-base:modeling.model.fdDesc')}</td>
							<td>
								<xform:textarea required="false" property="fdDesc" style="width:95%;" placeholder="${lfn:message('sys-modeling-base:modelingAppSpace.textPleaseEnter')}"/>
							</td>
						</tr>
						<tr>
							<%-- 校验规则 --%>
							<td class="td_normal_title" width=15%>${lfn:message('sys-modeling-base:modeling.model.fdValidateRule')}</td>
							<td class="validate_type">
								<xform:radio property="fdValidateRule" showStatus="edit"
											 value="${empty modelingAppDataValidateForm.fdValidateRule ? 0 : modelingAppDataValidateForm.fdValidateRule}" onValueChange="ruleTypeChange">
									<xform:simpleDataSource value="0">${lfn:message('sys-modeling-base:modeling.dataValidate.DataUnique')}</xform:simpleDataSource>
									<xform:simpleDataSource value="1">${lfn:message('sys-modeling-base:modeling.dataValidate.CustomValidation')}</xform:simpleDataSource>
								</xform:radio>
								<div class="modeling-viewcover-tip">
									<span>
                                    ${lfn:message('sys-modeling-base:modeling.dataValidate.CustomTip')}${lfn:message('sys-modeling-base:modeling.dataValidate.CustomTips')}
                                </span></div>
							</td>
						</tr>
						<%-- 唯一性校验 --%>
						<tr class="data-alone">
							<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:modeling.dataValidate.DataItem')}</td>
							<td>
								<div data-lui-type="sys/modeling/base/dataValidate/js/shuttleBox!TextShowWgt" class="data-txt" style="display:none;width:96%;box-sizing:border-box">
								</div>
							</td>
						</tr>
						<tr class="data-alone">
							<td colspan="2">
								<div data-lui-type="sys/modeling/base/dataValidate/js/shuttleBox!ShuttleBox" class="shuttleBox"
									style="display:none;" id="shuttleBox">
									<c:if test="${modelingAppDataValidateForm.fdValidateRule eq 1 }">
										<!--选择自定义校验规则时-->
										<ui:source type="Static">
											{
											datas : {
											allDatas : ${fieldInfos },
											storedDatas : JSON.parse('{}')
											}
											}
										</ui:source>
									</c:if>
									<c:if test="${modelingAppDataValidateForm.fdValidateRule ne 1 }">
										<ui:source type="Static">
											{
											datas : {
											allDatas : ${fieldInfos },
											storedDatas : JSON.parse('${modelingAppDataValidateForm.fdCfg }' || '{}')
											}
											}
										</ui:source>
									</c:if>
						            <div data-lui-type="lui/view/render!Template" style="display:none;">
						                <script type="text/config">
 											{
												src : '/sys/modeling/base/dataValidate/js/shuttleBoxRender.html#'
											}

                						</script>
						            </div>
						        </div>
						        <html:hidden property="fdCfg"/>
							</td>
						</tr>
							<%-- 自定义校验 --%>
						<tr class="data-other data-other-judgeType">
							<td class="td_normal_title" width="15%">${lfn:message('sys-modeling-base:modeling.dataValidate.JudgeType')}</td>
							<td class="validate_type judge_type">
								<input type="radio" name="judgeType" value="0"><span>${lfn:message('sys-modeling-base:modeling.dataValidate.MainTable')}</span>
								<input type="radio" name="judgeType" value="1"><span>${lfn:message('sys-modeling-base:behavior.detail.table')}</span>
							</td>
						</tr>
						<!--主表-->
						<tr class="data-other data-other-main">
							<td class="td_normal_title" width="15%" style="vertical-align: top !important;">${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}</td>
							<td class="data-validate-where">
								<table class="pre_model_where_tmp_html" width="100%">
									<tr class="pre_model_where">
										<td>
											<div style="color:#999999;margin-bottom: 5px;">${lfn:message('sys-modeling-base:modeling.dataValidate.WhereTips')}</div>
											<div style="margin-top: 5px;margin-bottom: 10px;" class="preModelWhereTypediv">
												<label class="PreModelWhereTypeinput1"><input type="radio" value="0" name="mainWhereType" checked="checked"/>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
												<label class="PreModelWhereTypeinput2"><input type="radio" value="1" name="mainWhereType" />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
											</div>
											<div class="model-mask-panel-table-base view_field_pre_model_where_div">
												<table class="tb_normal field_table view_field_pre_model_where_table" width="100%">
													<thead>
													<tr>
														<td width="30%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
														<td width="10%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}</td>
														<td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
														<td width="35%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
														<td width="10%">
															${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
														</td>
													</tr>
													</thead>
												</table>
												<div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">
													<div>${lfn:message('sys-modeling-base:enums.oper_log_method.add')}</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!--明细表-->
						<tr class="data-other-detail" style="display: none">
							<td class="td_normal_title" width="15%" >${lfn:message('sys-modeling-base:sysModelingRelation.fdTargetDetail')}</td>
							<td>
								<select name="detailId"></select>
							</td>
						</tr>
						<tr class="data-other-detail">
							<td class="td_normal_title" width="15%" style="vertical-align: top !important;">${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}</td>
							<td class="data-validate-where">
								<table class="detail_where_tmp_html" width="100%">
									<tr class="pre_model_where">
										<td>
											<div style="color:#999999;margin-bottom: 5px;">${lfn:message('sys-modeling-base:modeling.dataValidate.WhereTips')}</div>
											<div style="margin-top: 5px;margin-bottom: 10px;display: none" class="preModelWhereTypediv">
												<label class="PreModelWhereTypeinput1"><input type="radio" value="0" name="detailWhereType" checked="checked"/>${lfn:message('sys-modeling-base:relation.meet.all.conditions')}</label>
												<label class="PreModelWhereTypeinput2"><input type="radio" value="1" name="detailWhereType" />${lfn:message('sys-modeling-base:relation.meet.any.conditions')}</label>
											</div>
											<div class="model-mask-panel-table-base view_field_pre_model_where_div">
												<table class="tb_normal field_table view_field_detail_where_table" width="100%">
													<thead>
													<tr>
														<td width="30%">${lfn:message('sys-modeling-base:behavior.field.name')}</td>
														<td width="10%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}</td>
														<td width="15%">${lfn:message('sys-modeling-base:relation.value.type')}</td>
														<td width="35%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</td>
														<td width="10%" style="display: none">
																${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}
														</td>
													</tr>
													</thead>
												</table>
												<div class="model-mask-panel-table-detail-create table_opera" style="display: none">
													<div>${lfn:message('sys-modeling-base:enums.oper_log_method.add')}</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<%-- 错误提示信息 --%>
						<tr>
							<td class="td_normal_title" width="15%" style="vertical-align: top !important;">${lfn:message('sys-modeling-base:modeling.dataValidate.PromptInformation')}</td>
							<td>
								<xform:textarea property="fdMsg" subject="${lfn:message('sys-modeling-base:modeling.dataValidate.PromptInformation')}" style="width:99%;resize:none;" 
									htmlElementProperties="placeholder='${lfn:message(\'sys-modeling-base:modeling.dataValidate.ReturnModify\')}' maxlength='200' onkeyup='showLength(this,200)'"></xform:textarea>
								<div style="float:right;bottom:-50px;width:45px;height:12px;right:23px;font-size:12px;">
									<span id="fdMsgLength">0</span><span style="color:#BDCADA">/200</span>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="data-validate-bottom">
					<c:if test="${modelingAppDataValidateForm.method_GET=='edit'}">
						 <ui:button text="${ lfn:message('button.ok') }" width="80" height="30" onclick="submitForm('update');"/>
					</c:if>
					<c:if test="${modelingAppDataValidateForm.method_GET=='add'}">
						<ui:button text="${ lfn:message('button.ok') }" width="80" height="30" onclick="submitForm('save');"/>
					</c:if>
    				<ui:button text="${ lfn:message('button.cancel') }" width="80" height="30" styleClass="lui_toolbar_btn_gray" onclick="Com_CloseWindow();" />
				</div>
			</div>
			<html:hidden property="fdModelId"/>
		</html:form>
		<script>
			$KMSSValidation();
			
			seajs.use(["lui/dialog","sys/modeling/base/dataValidate/js/customValidate"],function(dialog,customValidate){
				
				window.submitForm = function(method){
					// 设置配置项
					var shuttleBoxWgt = LUI("shuttleBox");
					var fdValidateRule = $("[name='fdValidateRule']:checked").val();
					if(fdValidateRule == "0"){
						if(shuttleBoxWgt.isEmpty()){
							dialog.alert("${lfn:message('sys-modeling-base:modeling.dataValidate.ChooseDataItem')}");
							return false;
						}
					}
					var shuttleBoxWgtData = shuttleBoxWgt.getKeyData();
					if(fdValidateRule == "1"){
						var customData = window.customValidate.getKeyData();
						shuttleBoxWgtData.judgeType = customData.judgeType;
						shuttleBoxWgtData.detailId = customData.detailId;
						shuttleBoxWgtData.mainWhereType = customData.mainWhereType;
						shuttleBoxWgtData.detailWhereType = customData.detailWhereType;
						shuttleBoxWgtData.where = customData.where;
					}
					$("[name='fdCfg']").val(JSON.stringify(shuttleBoxWgtData));
					
					// 设置提示信息
					var fdMsg = $("[name='fdMsg']").val().trim();
					if(!fdMsg){
						fdMsg = $("[name='fdMsg']").attr("placeholder");
						$("[name='fdMsg']").val(fdMsg);
					}
					
					Com_Submit(document.modelingAppDataValidateForm,method);
				}
				
				window.showLength = function(obj,maxlength){
					var num=maxlength-obj.value.length;
				    if(num<0){
				        num=0;
				    }
				    $("#fdMsgLength").text(num);
				}

				window.ruleTypeChange = function (e) {
					if($(e).selector == "1"){
						$(".data-alone").hide();
						$(".data-other-judgeType").show();
						if($("[name='judgeType']:checked").val() == "1"){
							$(".data-other-detail").show();
							$(".data-other-main").hide();
						}else{
							$(".data-other-detail").hide();
							$(".data-other-main").show();
						}
					}else{
						$(".data-other").hide();
						$(".data-other-detail").hide();
						$(".data-alone").show();
					}
				}

				var customValidate_cfg = {
					contentContainer:$(".pre_model_where_tmp_html"),
					modelId : "${fdModelId}" || "${modelingAppDataValidateForm.fdModelId}",
					fdCfg: '${fdConfig}'
				};
				window.customValidate = new customValidate.CustomValidate(customValidate_cfg);
				window.customValidate.startup();
				window.customValidate.draw();
			});
			
			Com_AddEventListener(window,"load",function(){
				var msg = "${modelingAppDataValidateForm.fdMsg}";
				if(!msg){
					fdMsg = $("[name='fdMsg']").attr("placeholder");
					$("[name='fdMsg']").val(fdMsg);
				}
				var num = 200-msg.length;
				$("#fdMsgLength").text(num);
				var rule =  "${modelingAppDataValidateForm.fdValidateRule}";
				if(rule == "1"){
					$(".data-alone").hide();
					$(".data-other-judgeType").show();
					if($("[name='judgeType']:checked").val() == "1"){
						$(".data-other-detail").show();
						$(".data-other-main").hide();
					}else{
						$(".data-other-detail").hide();
						$(".data-other-main").show();
					}
				}else{
					$(".data-other").hide();
					$(".data-other-detail").hide();
					$(".data-alone").show();
				}
			})
			
		</script>
	</template:replace>
</template:include>