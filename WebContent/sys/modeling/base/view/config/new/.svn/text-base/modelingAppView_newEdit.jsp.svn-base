<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.modeling.base.util.ListviewEnumUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/newViewPreview.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/businessTag.css" />
<link type="text/css" rel="stylesheet"
	  href="${LUI_ContextPath}/sys/modeling/base/resources/css/collection.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/newViewEdit.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/newViewMobilePreview.css?s_cache=${LUI_Cache}" />
<script>
	Com_IncludeFile("doclist.js");
	Com_IncludeFile("view.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
	Com_IncludeFile("dialog.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
	Com_IncludeFile("jquery.dragsort.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
</script>
<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
<div class="model-body-content-view" id="editContent">
	<li class="modeling-pam-top">
		<div calss="modeling-pam-top-left">
			<div class="modeling-pam-back">
				<div onclick="returnListPage('new')">
					<i></i>
					<p>${lfn:message('sys-modeling-base:sys.profile.modeling.homePage.back') }</p>
				</div>
			</div>
			<div class="listviewName" title="${modelingAppViewForm.fdName}">
				<p style="display: inline-block;"<%-- ondblclick="modifyFdName(this)"--%>>
					<c:if test="${empty modelingAppViewForm.fdName}">
						${lfn:message('sys-modeling-base:view.untitled.view') }
					</c:if>
					${modelingAppViewForm.fdName}
				</p>
			</div>
		</div>
		<div class="modeling-pam-top-center">
			<ul>
				<li value="pc" class="active pc" onclick="showPcOrMobile('pc')" style="pointer-events:none">${lfn:message('sys-modeling-base:sysform.PC') }</li>
				<li value="mobile" class="mobile" onclick="showPcOrMobile('mobile')" style="pointer-events:none">${lfn:message('sys-modeling-base:sysform.mobile') }</li>
			</ul>
		</div>
		<div class="modeling-pam-top-right">
			<ul>
				<c:if test="${modelingAppViewForm.method_GET=='edit' || modelingAppViewForm.method_GET=='editTemplate'}">
					<li onclick="if(validateDetail()){submit_custom('update');}" class="active"><bean:message key="button.update"/></li>
				</c:if>
				<c:if test="${modelingAppViewForm.method_GET=='add'}">
					<li onclick="if(validateDetail()){submit_custom('save');}" class="active"><bean:message key="button.save"/></li>
				</c:if>
			</ul>
		</div>
</div>
<div class="modeling-pam-content">
	<div class="view-content">
		<!-- 这里放入你的组件 starts -->
		<div class="model-edit view-model-edit">
			<div class="model-edit-left model-edit-left-view">
				<div class="preview-pc">
					<div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview" style="display: none">
						<div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
						<div data-lui-type="lui/view/render!Template" style="display:none;">
							<script type="text/config">
							{
								src : '/sys/modeling/base/view/config/new/view_pc.html#'
							}
						</script>
						</div>
					</div>
				</div>
				<div class="preview-mobile" style="display: none;">
					<div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview_mobile" style="display: none">
						<div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
						<div data-lui-type="lui/view/render!Template" style="display:none;">
							<script type="text/config">
 						{
							src : '/sys/modeling/base/view/config/new/view_mobile.html#'
						}
                	</script>
						</div>
					</div>
				</div>
			</div>
			<!----右边---->
			<div class="model-edit-right model-edit-right-view" style="display: none">
				<div class="model-edit-right-view-content">
					<div class="model-edit-view-title">
						<ul>
							<li class="setting active common" value="common" onclick="changeRightView('common')"><span>${lfn:message('sys-modeling-base:listview.basic.set') }</span></li>
							<li value="design" class="design" onclick="changeRightView('design')"><span>${lfn:message('sys-modeling-base:listview.view.set') }</span></li>
						</ul>
					</div>
					<div class="model-edit-right-wrap model-edit-design">
						<div class="model-edit-view-content model-edit-view-content-right">
							<div class="model-edit-view-content-wrap">
								<html:form action="/sys/modeling/base/modelingAppView.do" style="height:100%">
									<center>
										<div class="view-edit-common">
											<table class="tb_simple model-view-panel-table" width="100%">
												<tr lui-pam-hidden="${param.isPcAndMobile}" class="view-fdName">
													<td class="td_normal_title">
															${lfn:message('sys-modeling-base:modelingAppView.fdName')}
													</td>
												</tr>
												<tr lui-pam-hidden="${param.isPcAndMobile}" class="view-fdName">
													<td colspan="3" width="85%" class="model-view-panel-table-td">
															<%-- 名称--%>
														<div id="_xform_fdName" _xform_type="text" style="width: 98%">
															<xform:text property="fdName" showStatus="edit" style="width:98%;height:28px!important;" />
														</div>
													</td>
												</tr>
												<tr lui-pam-hidden="${param.isPcAndMobile}" class="view-fdName">
													<td colspan="3" width="85%" class="model-view-panel-table-td">
														<div id="_xform_fdIsDefault" class="model-view-is-default" _xform_type="text" style="width: 98%">
															<div>${lfn:message('sys-modeling-base:listview.set.default.view') }</div>
															<div class="modeling-viewcover-tip">
																<span>${lfn:message('sys-modeling-base:listview.set.default.view.tips') }</span>
															</div>
															<div class="modeling-view-default">
																<ui:switch property="fdIsDefault" checkVal="true" unCheckVal="false" text=""></ui:switch>
															</div>
														</div>
													</td>
												</tr>
											</table>
										</div>
										<div style="width:100%;" class="view-edit-right-pc">
											<table class="tb_simple model-view-panel-table" width="98%">
												<tr style="display:none;" class="view-model view-design">
													<td class="td_normal_title">
															${lfn:message('sys-modeling-base:modelingAppView.fdModel')}
													</td>
												</tr>
												<tr style="display:none;" class="view-model view-design">
													<td colspan="3" width="85%" class="model-view-panel-table-td">
															<%-- 业务模块--%>
														<div id="_xform_fdModelId" _xform_type="select">
															<xform:select property="fdModelId" value="${param.fdModelId }" onValueChange="modelChange" htmlElementProperties="id='fdModelId_pc'" showStatus="edit">
																<xform:beanDataSource serviceBean="modelingAppModelService" selectBlock="fdId,fdName" whereBlock="modelingAppModel.fdApplication='${fdAppId }'"/>
															</xform:select>
														</div>
													</td>
												</tr>
												<!-- 业务操作 -->
												<tr class="view-design">
													<td class="td_normal_title" data-lui-position='fdOperation'>
														<span>${lfn:message('sys-modeling-base:modelingAppView.listOpers')}</span>
														<span class="model-data-create model-data-operate"
															  onclick="addRowOpers('TABLE_DocList_listOpers_Form','pcListOpersFrom','_xform_listOpers_pcForm','listOpers_pc','pc');">
																${lfn:message('sys-modeling-base:button.add') }</span>
													</td>
												</tr>
												<tr class="view-design">
													<td colspan="3" width="98%" class="model-view-panel-table-td">
														<div class="model-panel-table-base" style="margin-bottom:0px;">
															<table id="TABLE_DocList_listOpers_Form" class="tb_simple model-edit-view-oper-content-table" width="100%">
																	<%-- 基准行，KMSS_IsReferRow = 1 --%>
																<tr  style="display:none;" KMSS_IsReferRow="1" class="docListTr">
																	<td>
																		<div class="model-edit-view-oper" data-lui-position='fdOperation-!{index}' onclick="switchSelectPosition(this,'right')">
																			<div class="model-edit-view-oper-head">
																				<div class="model-edit-view-oper-head-title">
																					<div onclick="changeToOpenOrClose(this)">
																						<i class="open"></i>
																					</div>
																					<span>${lfn:message('sys-modeling-base:listview.action.item') }<span class='title-index'>!{index}</span></span>
																				</div>
																				<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																					<div class="del"
																						 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																						<i></i>
																					</div>
																					<div class="sortableIcon"><i></i></div>
																				</div>
																			</div>
																			<div class="model-edit-view-oper-content">
																				<ul>
																					<li class='model-edit-view-oper-content-item first-item'>
																						<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</div>
																						<div class='item-content'>
																								<%-- 操作--%>
																							<input type="hidden" name="listOpers_pc[!{index}].fdId" value="" disabled="true" />
																							<input type="hidden" name="listOpers_pc[!{index}].fdIsNewPc" value="1"/>
																							<div id="_xform_listOpers_Form[!{index}].fdOperationId" _xform_type="dialog" style="width:98%;display: flex;">
																								<xform:dialog style="width:98%;vertical-align: middle;"
																											  propertyId="listOpers_pc[!{index}].fdOperationId"
																											  propertyName="listOpers_pc[!{index}].fdOperationName"
																											  subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')} "
																											  showStatus="edit" validators=" required">
																									operationSelectViewOperation(null,false,true)
																								</xform:dialog>
																								<span class="txtstrong">*</span>
																							</div>
																							<div id="listOpers_pc[!{index}].tips">
																							</div>
																						</div>
																					</li>
																					<li class='model-edit-view-oper-content-item'>
																							<%-- 判断方式--%>
																						<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}</div>
																						<div class='item-content'>
																							<div id="_xform_listOpers_Form[!{index}].fdJudgeType" _xform_type="dialog" style="width:98%">
																								<div class="common-auth-type common-data-filter-whereType">
																									<input type="hidden" name="listOpers_pc[!{index}].fdJudgeType" value="">
																									<ul>
																										<li class="fdJudgeType_!{index} active" value="0" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_pc[!{index}].fdJudgeType'),true);">${lfn:message('sys-modeling-base:enums.app_viewopers_judgetype.0') }</li>
																										<li class="fdJudgeType_!{index}" value="1" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_pc[!{index}].fdJudgeType'),true);">${lfn:message('sys-modeling-base:enums.app_viewopers_judgetype.1') }</li>
																									</ul>
																								</div>
																							</div>
																						</div>
																					</li>

																						<%-- 公式定义 begin--%>
																					<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-formula-!{index}" style="display: none">
																						<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdFormula')}</div>
																						<div class='item-content'>
																							<div class="inputselectsgl" style="width: 98%; vertical-align: middle;"
																								 onclick="onClick_Formula_Dialog('listOpers_pc[!{index}].fdFormula_express','listOpers_pc[!{index}].fdFormula_text', 'Boolean');">
																								<div class="input">
																									<input name="listOpers_pc[!{index}].fdFormula_express" type="hidden" value="">
																									<input name="listOpers_pc[!{index}].fdFormula_text" type="text" value="">
																									<input name="listOpers_pc[!{index}].fdFormula" type="hidden" value="">
																								</div>
																								<div class="selectitem"></div>
																							</div>
																						</div>
																					</li>
																						<%-- 公式定义 end--%>

																						<%-- 条件判断 begin--%>
																					<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-oper-field-!{index}">
																						<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																							 id="_xform_listOpers_pcForm[!{index}].fdField" _xform_type="text" style="width:98%" class="pcListOpersFrom">
																						</div>
																						<div>
																							<input name="listOpers_pc[!{index}].fdJudgeConfig" type="hidden" value="<c:out value=""/>">
																						</div>
																					</li>
																						<%-- 条件判断 end--%>
																				</ul>
																			</div>
																		</div>
																	</td>
																</tr>
																<c:forEach items="${modelingAppViewForm.listOpers_pc}" var="listOpers_FormItem" varStatus="vstatus">
																	<tr KMSS_IsContentRow="1" class="docListTr">
																		<td>
																			<div class="model-edit-view-oper" data-lui-position='fdOperation-${vstatus.index}' index = "${vstatus.index}" onclick="switchSelectPosition(this,'right')">
																				<div class="model-edit-view-oper-head">
																					<div class="model-edit-view-oper-head-title">
																						<div onclick="changeToOpenOrClose(this)">
																							<i class="open"></i>
																						</div>
																						<span>${listOpers_FormItem.fdOperationName}</span>
																					</div>
																					<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																						<div class="del"
																							 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																							<i></i>
																						</div>
																						<div class="sortableIcon"><i></i></div>
																					</div>
																				</div>
																				<div class="model-edit-view-oper-content">
																					<ul>
																						<li class='model-edit-view-oper-content-item first-item'>
																							<div class='item-title'>
																									${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</div>
																							<div class='item-content'>
																									<%-- 操作--%>
																								<input type="hidden" name="listOpers_pc[${vstatus.index}].fdId" value="${listOpers_FormItem.fdId}" />
																								<input type="hidden" name="listOpers_pc[${vstatus.index}].fdIsNewPc" value="1" />
																								<div id="_xform_listOpers_Form[${vstatus.index}].fdOperationId" _xform_type="dialog" style="width: 98%;display: flex;">
																									<xform:dialog style="vertical-align: middle;width:98%"
																												  propertyId="listOpers_pc[${vstatus.index}].fdOperationId"
																												  propertyName="listOpers_pc[${vstatus.index}].fdOperationName"
																												  validators=" required"
																												  subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}">
																										operationSelectViewOperation(null,false,true)
																									</xform:dialog>
																									<span class="txtstrong">*</span>
																								</div>
																								<div id="listOpers_pc[${vstatus.index}].tips">
																									<%-- 操作按钮提示需开启机制--%>
																									<c:forEach items="${sysModelingOperationList}" var="sysModelingOperation" varStatus="vstatus_">
																										<c:if test="${(listOpers_FormItem.fdOperationId eq sysModelingOperation.fdId)
																										             and ((sysModelingOperation.fdDefType eq '8') or (sysModelingOperation.fdDefType eq '9')
																										             or (sysModelingOperation.fdDefType eq '10') or (sysModelingOperation.fdDefType eq '11')
																										             or (sysModelingOperation.fdDefType eq null and sysModelingOperation.fdOperationScenario eq '1')
																										             ) }">
																											<div class="operateTips">${lfn:message('sys-modeling-base:modeling.model.mechanism.tips')}</div>
																										</c:if>
																									</c:forEach>
																								</div>
																							</div>
																						</li>
																						<li class='model-edit-view-oper-content-item'>
																								<%-- 判断方式--%>
																							<div class='item-title'>
																									${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}
																							</div>
																							<div class='item-content'>
																								<div id="_xform_listOpers_Form[${vstatus.index}].fdJudgeType" _xform_type="dialog" style="width:98%">
																									<div class="common-auth-type common-data-filter-whereType">
																										<input type="hidden" name="listOpers_pc[${vstatus.index}].fdJudgeType" value="<c:out value="${listOpers_FormItem.fdJudgeType}"/>">
																										<ul>
																											<li class="fdJudgeType_${vstatus.index} active" value="0" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_pc[${vstatus.index}].fdJudgeType'),true);">${lfn:message('sys-modeling-base:enums.app_viewopers_judgetype.0') }</li>
																											<li class="fdJudgeType_${vstatus.index}" value="1" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_pc[${vstatus.index}].fdJudgeType'),true);">${lfn:message('sys-modeling-base:enums.app_viewopers_judgetype.1') }</li>
																										</ul>
																									</div>
																								</div>
																							</div>
																						</li>
																							<%-- 公式定义 begin--%>
																						<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-formula-${vstatus.index}" <c:if test="${!(listOpers_FormItem.fdJudgeType eq '1')}"> style="display: none" </c:if> >
																							<div class='item-title'>
																									${lfn:message('sys-modeling-base:modelingAppViewopers.fdFormula')}
																							</div>
																							<div class='item-content'>
																								<div class="inputselectsgl" style="width: 98%; vertical-align: middle;"
																									 onclick="onClick_Formula_Dialog('listOpers_pc[${vstatus.index}].fdFormula_express','listOpers_pc[${vstatus.index}].fdFormula_text', 'Boolean');">
																									<div class="input">
																										<input name="listOpers_pc[${vstatus.index}].fdFormula_text" type="text" value="<c:out value="${listOpers_FormItem.fdFormula_text}"/>">
																										<input name="listOpers_pc[${vstatus.index}].fdFormula_express" type="hidden" value="<c:out value="${listOpers_FormItem.fdFormula_express}"/>">
																										<input name="listOpers_pc[${vstatus.index}].fdFormula" type="hidden" value="<c:out value="${listOpers_FormItem.fdFormula}"/>">
																									</div>
																									<div class="selectitem"></div>
																								</div>
																							</div>
																						</li>
																							<%-- 公式定义 end--%>

																							<%-- 条件判断 begin--%>
																						<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-oper-field-${vstatus.index}" <c:if test="${listOpers_FormItem.fdJudgeType eq '1'}"> style="display: none" </c:if>  >
																							<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																								 id="_xform_listOpers_pcForm[${vstatus.index}].fdField" _xform_type="text" style="width:98%" class="pcListOpersFrom">
																								<script type="text/config">
																									{
																									    xformId: "${xformId}",
																										container: $("#_xform_listOpers_pcForm\\[${vstatus.index}\\]\\.fdField"),
																										storeData: $.parseJSON('${listOpers_FormItem.fdJudgeConfig}'||'{}'),
																										resultInputPrefix: "listOpers_pc",
																										resultInputSuffix: ".fdJudgeConfig",
																										channel: 'pc',
																										modelDict: '${modelDict}'
																									}
																								</script>
																							</div>
																							<div>
																								<input name="listOpers_pc[${vstatus.index}].fdJudgeConfig" type="hidden" value="<c:out value="${listOpers_FormItem.fdJudgeConfig}"/>">
																							</div>

																						</li>
																							<%-- 条件判断 end--%>
																					</ul>
																				</div>
																			</div>
																		</td>
																	</tr>
																</c:forEach>
															</table>
														</div>
														<input type="hidden" name="listOpers_Flag" value="1">
														<script>
															DocList_Info.push('TABLE_DocList_listOpers_Form');
														</script>
													</td>
												</tr>
												<!-- 业务标签 -->
												<tr class="view-design">
													<td class="td_normal_title" data-lui-position='fdTag'>
														<span>${lfn:message('sys-modeling-base:modelingAppView.listTabs')}</span>
														<span class="model-data-create model-data-operate" onclick="addRowTabs('TABLE_DocList_listTabs_Form',false,'pcListTabsFrom','_xform_listTabs_pcForm','listTabs_pc','pc');">
																${lfn:message('sys-modeling-base:button.add') }</span>
													</td>
												</tr>
												<tr class="business_tr view-design">
													<td colspan="3" width="100%" class="model-view-panel-table-td">
														<table id="TABLE_DocList_listTabs_Form" class="tb_simple model-edit-view-oper-content-table" width="100%" style="position:relative;">
																<%-- 基准行，KMSS_IsReferRow = 1 --%>
															<tr style="display:none;" KMSS_IsReferRow="1" class="docListTr pcListTabsFrom">
																<td>
																	<div class="model-edit-view-oper" data-lui-position='fdTag-!{index}' onclick="switchSelectPosition(this,'right')">
																		<div class="model-edit-view-oper-head">
																			<input name="listTabs_pc[!{index}].fdOrder"  type='hidden'/>
																			<input name="listTabs_pc[!{index}].fdTabType" value="0" type='hidden'/>
																			<div class="model-edit-view-oper-head-title">
																				<span>${lfn:message('sys-modeling-base:view.label') }<span class='title-index'>!{index}</span></span>
																				<div onclick="changeToOpenOrClose(this)">
																					<i class="open"></i>
																				</div>
																			</div>
																			<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																				<div class="del"
																					 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																					<i></i>
																				</div>
																			</div>
																		</div>
																		<div class="model-edit-view-oper-content">
																			<ul>
																				<li class='model-edit-view-oper-content-item first-item'>
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}</div>
																					<div class='item-content'>
																							<%-- 标签名称--%>
																						<input type="hidden" name="listTabs_pc[!{index}].fdId" value="" disabled="true" />
																						<input type="hidden" name="listTabs_pc[!{index}].fdIsNewPc" value="1"/>
																						<div id="_xform_listTabs_Form[!{index}].fdName" _xform_type="text" style="width:98%">
																							<xform:text property="listTabs_pc[!{index}].fdName" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}" required="true" validators="maxLength(50)" style="width:98%;height:28px!important;" onValueChange="tagNameUpdate"/>
																						</div>
																					</div>
																				</li>
																					<%-- 默认展开 --%>
																				<li class='model-edit-view-oper-content-item'>
																					<ui:switch property="listTabs_pc[!{index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}"></ui:switch>
																				</li>
																				<input type="hidden" name="listTabs_pc[!{index}].fdIsShow" />
																				<li class='model-edit-view-oper-content-item'>
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdType')}</div>
																					<div class='item-content'>
																							<%-- 展开样式--%>
																						<div id="_xform_listTabs_Form[!{index}].fdType" _xform_type="select" style="width: 98%">
																							<div class="view_flag_radio" style="display: inline-block;">
																								<input type="hidden" name="listTabs_pc[!{index}].fdType" value>
																								<div class="view_flag_radio_yes" index="1" style="display: inline-block;cursor: pointer;"  onclick="changeTargetForm(this,1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.1') }</div>
																								<div class="view_flag_radio_no view_flag_last" index="0" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,0)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.0') }</div>
																								<div class="view_flag_radio_no view_flag_last" index="2" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,2)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:view.link') }</div>
																							</div>
																						</div>
																					</div>
																				</li>
																					<%--目标表单 --%>
																				<li class='model-edit-view-oper-content-item' id='tabSourceLi_index_!{index}'>
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.targetForm') }</div>
																					<div class='item-content'>
																						<input name='listTabs_pc[!{index}].fdApplicationId' type='hidden'/>
																						<input name='listTabs_pc[!{index}].fdModelId' type='hidden'/>
																						<div class="item-content-element-choose" onclick="chooseTabSource(this,'${fdAppId }',false,true);">
																							<p class="item-content-element-label"></p>
																							<i></i>
																						</div>
																					</div>
																				</li>
																					<%--展示数据 --%>
																				<li class='model-edit-view-oper-content-item last-item' style="height: auto">
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }</div>
																					<div class='item-content'>
																							<%-- 业务模块--%>
																						<div id="_xform_listTabs_Form[!{index}].fdRelationMainField" _xform_type="select" style="display :none;">
																								<%-- 关联主模块字段--%>
																							<xform:select property="listTabs_pc[!{index}].fdRelationMainField" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdRelationMainField') }" style="width:97%">
																								<xform:beanDataSource serviceBean="modelingAppListviewModelDictService" selectBlock="fdId,fdName" whereBlock="fdAppModelId='${param.fdModelId }'"/>
																							</xform:select>
																							<span class="txtstrong">*</span>
																						</div>
																							<%-- 列表视图--%>
																						<div id="_xform_listTabs_Form[!{index}].fdListview">
																							<xform:select property="listTabs_pc[!{index}].fdListviewId" required="true" onValueChange="onFdListviewChange" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }" style="width:98%">
																							</xform:select>
																							<input name='listTabs_pc[!{index}].fdListviewIncParams' type='hidden'/>
																							<input name='listTabs_pc[!{index}].fdCollectionViewId' type='hidden'/>
																							<div id="_xform_listTabs_Form[!{index}].fdListviewIncParams">
																							</div>
																						</div>
																							<%-- 链接--%>
																						<div id="_xform_listTabs_Form[!{index}].fdLink">
																							<xform:text property="listTabs_pc[!{index}].fdLinkParams" style="width: 96%;height: 28px!important;" className="inputsgl"/>
																							<span class="txtstrong">*</span>
																							<div style="color: #999999;font-size: 12px;">${lfn:message('sys-modeling-base:modeling.system.link.only') }:/sys/modeling/base/profile/nav/index.jsp</div>
																						</div>
																					</div>
																				</li>

																					<%-- 业务标签配置 显示范围 begin--%>
																				<li class='model-edit-view-oper-content-item' height: auto >
																						<%-- 显示范围--%>
																					<div class="model-edit-view-oper-tab-config-item">
																						<div class="item-title">
																								${lfn:message('sys-modeling-base:modelingAppViewtab.showScope')}
																						</div>
																						<div class="tab-data-filter-value">
																							<label>
																								<input type="radio" name="data-pc-tab-!{index}" value="0" checked="true"
																									   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_pc[!{index}].fdShowType'),'pc')"/>
																									${lfn:message('sys-modeling-base:modelingAppViewtab.alwaysShow')}
																							</label>
																							<label style="visibility: hidden ;margin-right: 20px"></label>
																							<label>
																								<input type="radio" name="data-pc-tab-!{index}" value="1"
																									   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_pc[!{index}].fdShowType'),'pc')"/>
																									${lfn:message('sys-modeling-base:modelingAppViewtab.conditionsShow')}
																							</label>
																						</div>
																						<input name="listTabs_pc[!{index}].fdShowType"  type="hidden" value="0">
																					</div>
																				</li>
																				<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-tabs-field-pc-!{index}" style="display: none" >
																						<%-- 设置按钮--%>
																					<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																						 id="_xform_listTabs_pcForm[!{index}].fdField" _xform_type="text" style="width:98%">
																					</div>
																					<div>
																						<input name="listTabs_pc[!{index}].fdConfig" type="hidden" value='' />
																					</div>
																				</li>
																					<%-- 业务标签配置  显示范围  end--%>
																			</ul>
																		</div>
																	</div>
																</td>
															</tr>
															<c:forEach items="${modelingAppViewForm.listTabs_pc}" var="listTabs_FormItem" varStatus="vstatus">
																<c:choose>
																	<c:when test="${listTabs_FormItem.fdTabType ne 0}">
																		<tr KMSS_IsContentRow="1" class="docListTr model-businessTag-wrap pcListTabsFrom" style="display:block;">
																			<td class="model-businessTag-content">
																				<div class="model-edit-view-oper" data-lui-position='fdTag-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
																					<input name="listTabs_pc[${vstatus.index}].fdOrder" value="${listTabs_FormItem.fdOrder}" type='hidden'/>
																					<input name="listTabs_pc[${vstatus.index}].fdTabType" value="${listTabs_FormItem.fdTabType}" type='hidden'/>
																					<input name="listTabs_pc[${vstatus.index}].fdIsNewPc" value="1" type='hidden'/>
																					<input type="hidden" name="listTabs_pc[${vstatus.index}].fdApplicationId" value="${listTabs_FormItem.fdApplicationId}" data-model-name='${listTabs_FormItem.fdApplicationName}'/>
																					<input type="hidden" name="listTabs_pc[${vstatus.index}].fdModelId" value="${listTabs_FormItem.fdModelId}" data-model-name='${listTabs_FormItem.fdModelName}'/>
																					<input type="hidden" name="listTabs_pc[${vstatus.index}].fdName" value="${listTabs_FormItem.fdName}" />
																					<div class="model-businessTag-content-power buildin model-edit-view-oper-head">
																						<div class="content-power-left" style="min-width: 107px;">
																							<p>${listTabs_FormItem.fdName}</p>
																							<span>${lfn:message('sys-modeling-base:modelingAppViewtab.buildIn')}</span>
																						</div>
																						<div class="content-power-right">
																							<ui:switch property="listTabs_pc[${vstatus.index}].fdIsShow" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:view.show')}：" onValueChange="IsOpen(this,'fdIsOpen')"></ui:switch>
																							<ui:switch property="listTabs_pc[${vstatus.index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}："></ui:switch>
																						</div>
																					</div>
																				</div>
																			</td>
																		</tr>
																	</c:when>
																	<c:otherwise>
																		<tr KMSS_IsContentRow="1" class="docListTr pcListTabsFrom">
																			<td>
																				<div class="model-edit-view-oper" data-lui-position='fdTag-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
																					<div class="model-edit-view-oper-head">
																						<input name="listTabs_pc[${vstatus.index}].fdOrder" value="${listTabs_FormItem.fdOrder}" type='hidden'/>
																						<input name="listTabs_pc[${vstatus.index}].fdTabType" value="${listTabs_FormItem.fdTabType}" type='hidden'/>
																						<div class="model-edit-view-oper-head-title">
																							<div class="content-power-left">
																								<span>${listTabs_FormItem.fdName}</span>
																							</div>
																							<div onclick="changeToOpenOrClose(this)">
																								<i class="open"></i>
																							</div>
																						</div>
																						<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																							<div class="del"
																								 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																								<i></i>
																							</div>
																						</div>
																					</div>
																					<div class="model-edit-view-oper-content">
																						<ul>
																							<li class='model-edit-view-oper-content-item first-item'>
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}</div>
																								<div class='item-content'>
																										<%-- 名称--%>
																									<input type="hidden" name="listTabs_pc[${vstatus.index}].fdId" value="${listTabs_FormItem.fdId}" />
																									<input type="hidden" name="listTabs_pc[${vstatus.index}].fdIsNewPc" value="1" />
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdName" _xform_type="text" style="width:98%">
																										<xform:text property="listTabs_pc[${vstatus.index}].fdName" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}" required="true" validators="maxLength(50)" style="width:98%;height:28px!important;"  onValueChange="tagNameUpdate"/>
																									</div>
																								</div>
																							</li>
																								<%-- 默认展开 --%>
																							<li class='model-edit-view-oper-content-item'>
																								<ui:switch property="listTabs_pc[${vstatus.index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}"></ui:switch>
																							</li>
																							<li class='model-edit-view-oper-content-item'>
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdType')}</div>
																								<div class='item-content'>
																										<%-- 类型--%>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdType" _xform_type="select" style="width:98%">
																										<div class="view_flag_radio" style="display: inline-block;">
																											<input type="hidden" name="listTabs_pc[${vstatus.index}].fdType" value="${listTabs_FormItem.fdType}">
																											<div class="view_flag_radio_yes" index="1" style="display: inline-block;cursor: pointer;"  onclick="changeTargetForm(this,1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.1') }</div>
																											<div class="view_flag_radio_no view_flag_last" index="0" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,0)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.0') }</div>
																											<div class="view_flag_radio_no view_flag_last" index="2" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,2)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:view.link') }</div>
																										</div>
																									</div>
																								</div>
																							</li>
																							<c:set var="isShowTabSourceLi" value="${listTabs_FormItem.fdType != 2}"></c:set>
																							<li class='model-edit-view-oper-content-item' id='tabSourceLi_index_${vstatus.index}' style="display :${isShowTabSourceLi ? 'block':'none'};">
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.targetForm') }</div>
																								<div class='item-content'>
																									<input type="hidden" name="listTabs_pc[${vstatus.index}].fdApplicationId" value="${listTabs_FormItem.fdApplicationId}" data-model-name='${listTabs_FormItem.fdApplicationName}'/>
																									<input type="hidden" name="listTabs_pc[${vstatus.index}].fdModelId" value="${listTabs_FormItem.fdModelId}" data-model-name='${listTabs_FormItem.fdModelName}'/>
																									<div class="item-content-element-choose" onclick="chooseTabSource(this,'${fdAppId }',false,true);">
																										<p class="item-content-element-label"></p>
																										<i></i>
																									</div>
																								</div>
																							</li>
																							<li class='model-edit-view-oper-content-item last-item' style="height: auto">
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }</div>
																								<div class='item-content'>
																										<%-- 业务模块--%>
																									<c:set var="isModelId" value="${listTabs_FormItem.fdType == 0}"></c:set>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdRelationMainField" _xform_type="select" style="display :${isModelId ? 'block':'none'};">
																											<%-- 关联主模块字段--%>
																										<xform:select property="listTabs_pc[${vstatus.index}].fdRelationMainField" validators="${isModelId ? 'required':''}" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdRelationMainField') }" style="width:97%">
																											<xform:beanDataSource serviceBean="modelingAppListviewModelDictService" selectBlock="fdId,fdName" whereBlock="fdAppModelId='${param.fdModelId }'"/>
																										</xform:select>
																										<span class="txtstrong">*</span>
																									</div>
																										<%-- 列表视图--%>
																									<c:set var="isListview" value="${listTabs_FormItem.fdType == 1}"></c:set>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdListview" style="display :${isListview ? 'block':'none'};">
																										<!--为兼容新版列表视图，改成下面的形式-->
																										<div data-lui-type="sys/modeling/base/view/config/new/tabSelect!TabSelect" class="tab-select">
																											<script type="text/config">
																							{"modelId":"${listTabs_FormItem.fdModelId}",
																							"defaultValue":"${listTabs_FormItem.fdListviewId}",
																							"index":"${vstatus.index}",
																							"isMobile":"false",
																							"isNew":"true"}
																						</script>
																										</div>
																										<span class="txtstrong">*</span>
																										<input name='listTabs_pc[${vstatus.index}].fdListviewIncParams' type='hidden' value="${listTabs_FormItem.fdListviewIncParams }"/>
																										<input name='listTabs_pc[${vstatus.index}].fdCollectionViewId' type='hidden' value="${listTabs_FormItem.fdCollectionViewId }"/>
																										<div id="_xform_listTabs_Form[${vstatus.index}].fdListviewIncParams">
																										</div>
																									</div>
																										<%-- 链接--%>
																									<c:set var="isLink" value="${listTabs_FormItem.fdType == 2}"></c:set>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdLink" style="display :${isLink ? 'block':'none'};">
																										<xform:text property="listTabs_pc[${vstatus.index}].fdLinkParams"  validators="${isLink ? 'urlCustomize urlLength':''}" style="width: 96%;height: 28px!important;" className="inputsgl"/>
																										<span class="txtstrong">*</span>
																										<div style="color: #999999;font-size: 12px;">${lfn:message('sys-modeling-base:modeling.system.link.only') }/sys/modeling/base/profile/nav/index.jsp</div>
																									</div>
																								</div>
																							</li>
																								<%-- 业务标签配置 显示范围 begin--%>
																							<li class='model-edit-view-oper-content-item' height: auto >
																									<%-- 显示范围--%>
																								<div class="model-edit-view-oper-tab-config-item">
																									<div class="item-title">
																											${lfn:message('sys-modeling-base:modelingAppViewtab.showScope')}
																									</div>
																									<div class="tab-data-filter-value">
																										<label>
																											<input type="radio" name="data-pc-tab-${vstatus.index}" value="0" checked="true"
																												   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_pc[${vstatus.index}].fdShowType'),'pc')"/>
																												${lfn:message('sys-modeling-base:modelingAppViewtab.alwaysShow')}
																										</label>
																										<label style="visibility: hidden ;margin-right: 20px"></label>
																										<label>
																											<input type="radio" name="data-pc-tab-${vstatus.index}" value="1" <c:if test="${(listTabs_FormItem.fdShowType eq '1')}"> checked="true" </c:if>
																												   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_pc[${vstatus.index}].fdShowType'),'pc')"/>
																												${lfn:message('sys-modeling-base:modelingAppViewtab.conditionsShow')}
																										</label>
																									</div>
																									<input name="listTabs_pc[${vstatus.index}].fdShowType"  type="hidden" value="${listTabs_FormItem.fdShowType}">
																								</div>
																							</li>
																							<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-tabs-field-pc-${vstatus.index}" <c:if test="${!(listTabs_FormItem.fdShowType eq '1')}">style="display: none"</c:if>>
																									<%-- 设置按钮--%>
																								<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																									 id="_xform_listTabs_pcForm[${vstatus.index}].fdField" _xform_type="text" style="width:98%">
																									<script type="text/config">
																										{
																											xformId: "${xformId}",
																											container: $("#_xform_listTabs_pcForm\\[${vstatus.index}\\]\\.fdField"),
																											storeData: $.parseJSON('${listTabs_FormItem.fdConfig}'||'{}'),
																											resultInputPrefix: "listTabs_pc",
																											resultInputSuffix: ".fdConfig",
																											channel: 'pc',
																											modelDict: '${modelDict}'
																										}
																									</script>
																								</div>
																								<div>
																									<input name="listTabs_pc[${vstatus.index}].fdConfig" type="hidden" value="<c:out value="${listTabs_FormItem.fdConfig}"/>">
																								</div>
																							</li>
																								<%-- 业务标签配置  显示范围  end--%>
																						</ul>
																					</div>
																				</div>
																			</td>
																		</tr>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</table>
														<input type="hidden" name="listTabs_Flag" value="1">
														<script>
															DocList_Info.push('TABLE_DocList_listTabs_Form');
														</script>
													</td>
												</tr>
											</table>
										</div>
										<!------------------------移动--------------------------------------移动---------------------->
										<div style="width:100%;display: none" class="view-edit-right-mobile">
											<table class="tb_simple model-view-panel-table" width="100%">
												<!-- 业务标签 -->
												<tr class="view-design">
													<td class="td_normal_title" data-lui-position='fdTag'>
														<span>${lfn:message('sys-modeling-base:modelingAppView.listTabs')}</span>
														<span class="model-data-create model-data-operate"
															  onclick="addRowTabs('TABLE_DocList_listTabs_Form_Mobile',true,'mobileListTabsFrom','_xform_listTabs_mobileForm','listTabs_mobile','mobile');">
																${lfn:message('sys-modeling-base:button.add') }</span>
													</td>
												</tr>
												<tr class="business_tr view-design">
													<td colspan="3" width="100%" class="model-view-panel-table-td">
														<table id="TABLE_DocList_listTabs_Form_Mobile" class="tb_simple model-edit-view-oper-content-table" width="98%" style="position:relative;">
																<%-- 基准行，KMSS_IsReferRow = 1 --%>
															<tr style="display:none;" KMSS_IsReferRow="1" class="docListTr mobileListTabsFrom">
																<td>
																	<div class="model-edit-view-oper" data-lui-position='fdTag-!{index}' onclick="switchSelectPosition(this,'right')">
																		<div class="model-edit-view-oper-head">
																			<input name="listTabs_mobile[!{index}].fdOrder"  type='hidden'/>
																			<input name="listTabs_mobile[!{index}].fdTabType" value="0" type='hidden'/>
																			<div class="model-edit-view-oper-head-title">
																				<span>${lfn:message('sys-modeling-base:view.label') }<span class='title-index'>!{index}</span></span>
																				<div onclick="changeToOpenOrClose(this)">
																					<i class="open"></i>
																				</div>
																			</div>
																			<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																				<div class="del"
																					 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																					<i></i>
																				</div>
																			</div>
																		</div>
																		<div class="model-edit-view-oper-content">
																			<ul>
																				<li class='model-edit-view-oper-content-item first-item'>
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}</div>
																					<div class='item-content'>
																							<%-- 标签名称--%>
																						<input type="hidden" name="listTabs_mobile[!{index}].fdId" value="" disabled="true" />
																						<input type="hidden" name="listTabs_mobile[!{index}].fdIsNewPc" value="0"/>
																						<div id="_xform_listTabs_Form[!{index}].fdName" _xform_type="text" style="width:98%">
																							<xform:text property="listTabs_mobile[!{index}].fdName" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}" required="true" validators="maxLength(18)" style="width:98%;height:28px!important;" onValueChange="tagNameUpdate"/>
																						</div>
																					</div>
																				</li>
																					<%-- 默认展开 --%>
																				<li class='model-edit-view-oper-content-item'>
																					<ui:switch property="listTabs_mobile[!{index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}"></ui:switch>
																				</li>
																				<input type="hidden" name="listTabs_mobile[!{index}].fdIsShow" />
																				<li class='model-edit-view-oper-content-item'>
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdType')}</div>
																					<div class='item-content'>
																							<%-- 展开样式--%>
																						<div id="_xform_listTabs_Form[!{index}].fdType" _xform_type="select" style="width:98%">
																							<div class="view_flag_radio" style="display: inline-block;">
																								<input type="hidden" name="listTabs_mobile[!{index}].fdType" value>
																								<div class="view_flag_radio_yes" index="1" style="display: inline-block;cursor: pointer;"  onclick="changeTargetForm(this,1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.1') }</div>
																									<%--																					<div class="view_flag_radio_no view_flag_last" index="0" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,0)"><i class="view_flag_no"></i>表单</div>--%>
																								<div class="view_flag_radio_no view_flag_last" index="2" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,2)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:view.link') }</div>
																							</div>
																						</div>
																					</div>
																				</li>
																					<%--目标表单 --%>
																				<li class='model-edit-view-oper-content-item' id='tabSourceLi_index_!{index}'>
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.targetForm') }</div>
																					<div class='item-content'>
																						<input name='listTabs_mobile[!{index}].fdApplicationId' type='hidden'/>
																						<input name='listTabs_mobile[!{index}].fdModelId' type='hidden'/>
																						<div class="item-content-element-choose" onclick="chooseTabSource(this,'${fdAppId }',true,true);">
																							<p class="item-content-element-label"></p>
																							<i></i>
																						</div>
																					</div>
																				</li>
																					<%--展示数据 --%>
																				<li class='model-edit-view-oper-content-item last-item' style="height: auto">
																					<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }</div>
																					<div class='item-content'>
																							<%-- 业务模块--%>
																						<div id="_xform_listTabs_Form[!{index}].fdRelationMainField" _xform_type="select" style="display :none;">
																								<%-- 关联主模块字段--%>
																							<xform:select property="listTabs_mobile[!{index}].fdRelationMainField" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdRelationMainField') }" style="width:97%">
																								<xform:beanDataSource serviceBean="modelingAppListviewModelDictService" selectBlock="fdId,fdName" whereBlock="fdAppModelId='${param.fdModelId }'"/>
																							</xform:select>
																							<span class="txtstrong">*</span>
																						</div>
																							<%-- 列表视图--%>
																						<div id="_xform_listTabs_Form[!{index}].fdListview">
																							<xform:select property="listTabs_mobile[!{index}].fdMobileListViewId" required="true" onValueChange="onFdMobileListviewChange" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }" style="width:98%">
																							</xform:select>
																							<input name='listTabs_mobile[!{index}].fdMobileTabId' type='hidden' value="${listTabs_FormItem.fdMobileTabId }"/>
																							<div id="_xform_listTabs_Form[!{index}].fdTabViews"> </div>
																							<input name='listTabs_mobile[!{index}].fdListviewIncParams' type='hidden'/>
																							<div id="_xform_listTabs_Form[!{index}].fdListviewIncParams"> </div>
																						</div>
																							<%-- 链接--%>
																						<div id="_xform_listTabs_Form[!{index}].fdLink">
																							<xform:text property="listTabs_mobile[!{index}].fdLinkParams"  style="width: 96%;height: 28px!important;" className="inputsgl"/>
																							<span class="txtstrong">*</span>
																							<div style="color: #999999;font-size: 12px;">${lfn:message('sys-modeling-base:modeling.system.link.only') }/sys/modeling/base/profile/nav/index.jsp</div>
																						</div>
																					</div>
																				</li>
																					<%-- 业务标签配置 显示范围 begin--%>
																				<li class='model-edit-view-oper-content-item' height: auto >
																					<div class="model-edit-view-oper-tab-config-item">
																							<%-- 显示范围--%>
																						<div class="item-title">
																								${lfn:message('sys-modeling-base:modelingAppViewtab.showScope')}
																						</div>
																						<div class="tab-data-filter-value">
																							<label>
																								<input type="radio" name="data-mobile-tab-!{index}" value="0" checked="true"
																									   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_mobile[!{index}].fdShowType'),'mobile')"/>
																									${lfn:message('sys-modeling-base:modelingAppViewtab.alwaysShow')}
																							</label>
																							<label style="visibility: hidden ;margin-right: 20px"></label>
																							<label>
																								<input type="radio" name="data-mobile-tab-!{index}" value="1"
																									   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_mobile[!{index}].fdShowType'),'mobile')"/>
																									${lfn:message('sys-modeling-base:modelingAppViewtab.conditionsShow')}
																							</label>
																						</div>
																						<input name="listTabs_mobile[!{index}].fdShowType"  type="hidden" value="0">
																					</div>
																				</li>
																				<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-tabs-field-mobile-!{index}" style="display: none">
																						<%-- 设置按钮--%>
																					<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																						 id="_xform_listTabs_mobileForm[!{index}].fdField" _xform_type="text" style="width:98%">
																					</div>
																					<div>
																						<input name="listTabs_mobile[!{index}].fdConfig" type="hidden" value='' />
																					</div>
																				</li>
																					<%-- 业务标签配置  显示范围  end--%>
																			</ul>
																		</div>
																	</div>
																</td>
															</tr>
															<c:forEach items="${modelingAppViewForm.listTabs_mobile}" var="listTabs_FormItem" varStatus="vstatus">
																<c:choose>
																	<c:when test="${listTabs_FormItem.fdTabType ne 0}">
																		<tr KMSS_IsContentRow="1" class="docListTr model-businessTag-wrap mobile-businessTag mobileListTabsFrom " style="display:none;">
																			<td class="model-businessTag-content">
																				<div class="model-edit-view-oper" data-lui-position='fdTag-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
																					<input name="listTabs_mobile[${vstatus.index}].fdIsNewPc" value="0" type='hidden'/>
																					<input name="listTabs_mobile[${vstatus.index}].fdOrder" value="${listTabs_FormItem.fdOrder}" type='hidden'/>
																					<input name="listTabs_mobile[${vstatus.index}].fdTabType" value="${listTabs_FormItem.fdTabType}" type='hidden'/>
																					<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdApplicationId" value="${listTabs_FormItem.fdApplicationId}" data-model-name='${listTabs_FormItem.fdApplicationName}'/>
																					<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdModelId" value="${listTabs_FormItem.fdModelId}" data-model-name='${listTabs_FormItem.fdModelName}'/>
																					<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdName" value="${listTabs_FormItem.fdName}" />
																					<div class="model-businessTag-content-power buildin model-edit-view-oper-head">
																						<div class="content-power-left" style="min-width: 107px;">
																							<p>${listTabs_FormItem.fdName}</p>
																							<span>${lfn:message('sys-modeling-base:modelingAppViewtab.buildIn')}</span>
																						</div>
																						<div class="content-power-right">
																							<ui:switch property="listTabs_mobile[${vstatus.index}].fdIsShow" checkVal="1" unCheckVal="0" text="显示：" onValueChange="IsOpen(this,'fdIsOpen')"></ui:switch>
																								<%--<ui:switch property="listTabs_mobile[${vstatus.index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}："></ui:switch>--%>
																						</div>
																					</div>
																				</div>
																			</td>
																		</tr>
																	</c:when>
																	<c:otherwise>
																		<tr KMSS_IsContentRow="1" class="docListTr mobileListTabsFrom">
																			<td>
																				<div class="model-edit-view-oper" data-lui-position='fdTag-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
																					<div class="model-edit-view-oper-head">
																						<input name="listTabs_mobile[${vstatus.index}].fdOrder" value="${listTabs_FormItem.fdOrder}" type='hidden'/>
																						<input name="listTabs_mobile[${vstatus.index}].fdTabType" value="${listTabs_FormItem.fdTabType}" type='hidden'/>
																						<div class="model-edit-view-oper-head-title">
																							<div class="content-power-left">
																								<span>${listTabs_FormItem.fdName}</span>
																							</div>
																							<div onclick="changeToOpenOrClose(this)">
																								<i class="open"></i>
																							</div>
																						</div>
																						<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																							<div class="del"
																								 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																								<i></i>
																							</div>
																						</div>
																					</div>
																					<div class="model-edit-view-oper-content">
																						<ul>
																							<li class='model-edit-view-oper-content-item first-item'>
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}</div>
																								<div class='item-content'>
																										<%-- 名称--%>
																									<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdId" value="${listTabs_FormItem.fdId}" />
																									<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdIsNewPc" value="0" />
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdName" _xform_type="text" style="width:98%">
																										<xform:text property="listTabs_mobile[${vstatus.index}].fdName" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}" required="true" validators="maxLength(18)" style="width:98%;height:28px!important;"  onValueChange="tagNameUpdate"/>
																									</div>
																								</div>
																							</li>
																								<%-- 默认展开 --%>
																								<%--<li class='model-edit-view-oper-content-item'>
                                                                                                    <ui:switch property="listTabs_mobile[${vstatus.index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}"></ui:switch>
                                                                                                </li>--%>
																							<li class='model-edit-view-oper-content-item'>
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdType')}</div>
																								<div class='item-content'>
																										<%-- 类型--%>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdType" _xform_type="select" style="width:98%">
																										<div class="view_flag_radio" style="display: inline-block;">
																											<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdType" value="${listTabs_FormItem.fdType}">
																											<div class="view_flag_radio_yes" index="1" style="display: inline-block;cursor: pointer;"  onclick="changeTargetForm(this,1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.1') }</div>
																												<%--																								<div class="view_flag_radio_no view_flag_last" index="0" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,0)"><i class="view_flag_no"></i>表单</div>--%>
																											<div class="view_flag_radio_no view_flag_last" index="2" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,2)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:view.link') }</div>
																										</div>
																									</div>
																								</div>
																							</li>
																							<c:set var="isShowTabSourceLi" value="${listTabs_FormItem.fdType != 2}"></c:set>
																							<li class='model-edit-view-oper-content-item' id='tabSourceLi_index_${vstatus.index}' style="display :${isShowTabSourceLi ? 'block':'none'};">
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.targetForm') }</div>
																								<div class='item-content'>
																									<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdApplicationId" value="${listTabs_FormItem.fdApplicationId}" data-model-name='${listTabs_FormItem.fdApplicationName}'/>
																									<input type="hidden" name="listTabs_mobile[${vstatus.index}].fdModelId" value="${listTabs_FormItem.fdModelId}" data-model-name='${listTabs_FormItem.fdModelName}'/>
																									<div class="item-content-element-choose" onclick="chooseTabSource(this,'${fdAppId }',true,true);">
																										<p class="item-content-element-label"></p>
																										<i></i>
																									</div>
																								</div>
																							</li>
																							<li class='model-edit-view-oper-content-item last-item' style="height: auto">
																								<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }</div>
																								<div class='item-content'>
																										<%-- 业务模块--%>
																									<c:set var="isModelId" value="${listTabs_FormItem.fdType == 0}"></c:set>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdRelationMainField" _xform_type="select" style="display :${isModelId ? 'block':'none'};">
																											<%-- 关联主模块字段--%>
																										<xform:select property="listTabs_mobile[${vstatus.index}].fdRelationMainField" validators="${isModelId ? 'required':''}" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdRelationMainField') }" style="width:97%">
																											<xform:beanDataSource serviceBean="modelingAppListviewModelDictService" selectBlock="fdId,fdName" whereBlock="fdAppModelId='${param.fdModelId }'"/>
																										</xform:select>
																										<span class="txtstrong">*</span>
																									</div>
																										<%-- 列表视图--%>
																									<c:set var="isListview" value="${listTabs_FormItem.fdType == 1}"></c:set>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdListview" style="display :${isListview ? 'block':'none'};">
																											<%--<xform:select property="listTabs_mobile[${vstatus.index}].fdMobileListViewId" onValueChange="onFdMobileListviewChange" validators="${isListview ? 'required':''}" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdListview') }" style="width:98%">
                                                                                                                &lt;%&ndash; <xform:beanDataSource serviceBean="modelingAppMobileListViewService" selectBlock="fdId,fdName" whereBlock="modelingAppMobileListView.fdModel='${listTabs_FormItem.fdModelId }'"/>&ndash;%&gt;
                                                                                                                <!--为兼容新版列表视图的入参，现修改成下方的方式-->
                                                                                                                <xform:customizeDataSource className="com.landray.kmss.sys.modeling.base.views.collection.service.spring.ModelingAppCollectionViewServiceImp"/>
                                                                                                            </xform:select>--%>
																										<!--为兼容新版列表视图，改成下面的形式-->
																										<div data-lui-type="sys/modeling/base/view/config/new/tabSelect!TabSelect" class="tab-select">
																											<script type="text/config">
																									{"modelId":"${listTabs_FormItem.fdModelId}",
																									"defaultValue":"${listTabs_FormItem.fdListviewId}",
																									"index":"${vstatus.index}",
																									"isMobile":"true",
																									"isNew":"true"}
																								</script>
																										</div>
																										<span class="txtstrong">*</span>
																										<input name='listTabs_mobile[${vstatus.index}].fdMobileTabId' type='hidden' value="${listTabs_FormItem.fdMobileTabId }"/>
																										<div id="_xform_listTabs_Form[${vstatus.index}].fdTabViews"> </div>
																										<input name='listTabs_mobile[${vstatus.index}].fdListviewIncParams' type='hidden' value="${listTabs_FormItem.fdListviewIncParams }"/>
																										<div id="_xform_listTabs_Form[${vstatus.index}].fdListviewIncParams">
																										</div>
																									</div>
																										<%-- 链接--%>
																									<c:set var="isLink" value="${listTabs_FormItem.fdType == 2}"></c:set>
																									<div id="_xform_listTabs_Form[${vstatus.index}].fdLink" style="display :${isLink ? 'block':'none'};">
																										<xform:text property="listTabs_mobile[${vstatus.index}].fdLinkParams"  validators="${isLink ? 'urlCustomize urlLength':''}" style="width: 96%;height: 28px!important;" className="inputsgl"/>
																										<span class="txtstrong">*</span>
																										<div style="color: #999999;font-size: 12px;">${lfn:message('sys-modeling-base:modeling.system.link.only') }:/sys/modeling/base/profile/nav/index.jsp</div>
																									</div>
																								</div>
																							</li>

																								<%-- 业务标签配置 显示范围 begin--%>
																							<li class='model-edit-view-oper-content-item' height: auto >
																								<div class="model-edit-view-oper-tab-config-item">
																										<%-- 显示范围--%>
																									<div class="item-title">
																											${lfn:message('sys-modeling-base:modelingAppViewtab.showScope')}
																									</div>
																									<div class="tab-data-filter-value">
																										<label>
																											<input type="radio" name="data-mobile-tab-${vstatus.index}" value="0" checked="true"
																												   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_mobile[${vstatus.index}].fdShowType'),'mobile')"/>${lfn:message('sys-modeling-base:modelingAppViewtab.alwaysShow')}
																										</label>
																										<label style="visibility: hidden ;margin-right: 20px"></label>
																										<label>
																											<input type="radio" name="data-mobile-tab-${vstatus.index}" value="1" <c:if test="${(listTabs_FormItem.fdShowType eq '1')}"> checked="true" </c:if>
																												   onclick="onFdShowTypeChange(this.value, document.getElementsByName('listTabs_mobile[${vstatus.index}].fdShowType'),'mobile')"/>${lfn:message('sys-modeling-base:modelingAppViewtab.conditionsShow')}
																										</label>
																									</div>
																									<input name="listTabs_mobile[${vstatus.index}].fdShowType"  type="hidden" value= "${listTabs_FormItem.fdShowType}">
																								</div>
																							</li>
																							<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-tabs-field-mobile-${vstatus.index}" <c:if test="${!(listTabs_FormItem.fdShowType eq '1')}"> style="display: none" </c:if>>
																									<%-- 设置按钮--%>
																								<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																									 id="_xform_listTabs_mobileForm[${vstatus.index}].fdField" _xform_type="text" style="width:98%" >
																									<script type="text/config">
																										{
																											xformId: "${xformId}",
																											container: $("#_xform_listTabs_mobileForm\\[${vstatus.index}\\]\\.fdField"),
																											storeData: $.parseJSON('${listTabs_FormItem.fdConfig}'||'{}'),
																											resultInputPrefix: "listTabs_mobile",
																											resultInputSuffix: ".fdConfig",
																											channel: 'mobile',
																											modelDict: '${modelDict}'
																										}
																									</script>
																								</div>
																								<div>
																									<input name="listTabs_mobile[${vstatus.index}].fdConfig" type="hidden" value="<c:out value="${listTabs_FormItem.fdConfig}"/>">
																								</div>
																							</li>
																								<%-- 业务标签配置  显示范围  end--%>
																						</ul>
																					</div>
																				</div>
																			</td>
																		</tr>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</table>
														<input type="hidden" name="listTabs_Flag" value="1">
														<script>
															DocList_Info.push('TABLE_DocList_listTabs_Form_Mobile');
														</script>
													</td>
												</tr>

												<!-- 业务操作 -->
												<tr class="view-design">
													<td class="td_normal_title" data-lui-position='fdOperation'>
														<span>${lfn:message('sys-modeling-base:modelingAppView.listOpers')}</span>
														<span class="model-data-create model-data-operate"
															  onclick="addRowOpers('TABLE_DocList_listOpers_Form_Mobile','mobileListOpersFrom','_xform_listOpers_mobileForm','listOpers_mobile', 'mobile');">
																${lfn:message('sys-modeling-base:button.add') }</span>
													</td>
												</tr>
												<tr class="view-design">
													<td colspan="3" width="100%" class="model-view-panel-table-td">
														<div class="model-panel-table-base" style="margin-bottom:0px;">
															<table id="TABLE_DocList_listOpers_Form_Mobile" class="tb_simple model-edit-view-oper-content-table" width="98%">
																	<%-- 基准行，KMSS_IsReferRow = 1 --%>
																<tr  style="display:none;" KMSS_IsReferRow="1" class="docListTr">
																	<td>
																		<div class="model-edit-view-oper" data-lui-position='fdOperation-!{index}' onclick="switchSelectPosition(this,'right')">
																			<div class="model-edit-view-oper-head">
																				<div class="model-edit-view-oper-head-title">
																					<div onclick="changeToOpenOrClose(this)">
																						<i class="open"></i>
																					</div>
																					<span>${lfn:message('sys-modeling-base:listview.action.item') }<span class='title-index'>!{index}</span></span>
																				</div>
																				<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																					<div class="del"
																						 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																						<i></i>
																					</div>
																					<div class="sortableIcon"><i></i></div>
																				</div>
																			</div>
																			<div class="model-edit-view-oper-content">
																				<ul>
																					<li class='model-edit-view-oper-content-item first-item'>
																						<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</div>
																						<div class='item-content'>
																								<%-- 操作--%>
																							<input type="hidden" name="listOpers_mobile[!{index}].fdId" value="" disabled="true" />
																							<input type="hidden" name="listOpers_mobile[!{index}].fdIsNewPc" value="0"/>
																							<div id="_xform_listOpers_Form[!{index}].fdOperationId" _xform_type="dialog" style="width:98%">
																								<xform:dialog style="width:98%;vertical-align: middle;" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')} " propertyId="listOpers_mobile[!{index}].fdOperationId" propertyName="listOpers_mobile[!{index}].fdOperationName" showStatus="edit" validators=" required">
																									operationSelectViewOperation(null, true,true)
																								</xform:dialog>
																							</div>
																						</div>
																					</li>
																					<li class='model-edit-view-oper-content-item'>
																						<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}</div>
																						<div class='item-content'>
																							<div id="_xform_listOpers_Form[!{index}].fdJudgeType" _xform_type="dialog" style="width:98%">
																								<div class="common-auth-type common-data-filter-whereType">
																									<input type="hidden" name="listOpers_mobile[!{index}].fdJudgeType" value="">
																									<ul>
																										<li class="fdJudgeType_!{index} active" value="0" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_mobile[!{index}].fdJudgeType'),true);">条件判断</li>
																										<li class="fdJudgeType_!{index}" value="1" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_mobile[!{index}].fdJudgeType'),true);">公式定义</li>
																									</ul>
																								</div>
																							</div>
																						</div>
																					</li>

																						<%-- 公式定义 begin--%>
																					<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-formula-!{index}" style="display: none">
																						<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdFormula')}</div>
																						<div class='item-content'>
																							<div class="inputselectsgl" style="width: 98%; vertical-align: middle;"
																								 onclick="onClick_Formula_Dialog('listOpers_mobile[!{index}].fdFormula_express','listOpers_mobile[!{index}].fdFormula_text', 'Boolean');">
																								<div class="input">
																									<input name="listOpers_mobile[!{index}].fdFormula_express" type="hidden" value="">
																									<input name="listOpers_mobile[!{index}].fdFormula_text" type="text" value="">
																									<input name="listOpers_mobile[!{index}].fdFormula" type="hidden" value="">
																								</div>
																								<div class="selectitem"></div>
																							</div>
																						</div>
																					</li>
																						<%-- 公式定义 end--%>
																						<%-- 条件判断 begin--%>
																					<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-oper-field-!{index}">
																						<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																							 id="_xform_listOpers_mobileForm[!{index}].fdField" _xform_type="text" style="width:98%" class="mobileListOpersFrom">
																						</div>
																						<div>
																							<input name="listOpers_mobile[!{index}].fdJudgeConfig" type="hidden" value="<c:out value=""/>">
																						</div>
																					</li>
																						<%-- 条件判断 end--%>
																				</ul>
																			</div>
																		</div>
																	</td>
																</tr>
																<c:forEach items="${modelingAppViewForm.listOpers_mobile}" var="listOpers_FormItem" varStatus="vstatus">
																	<tr KMSS_IsContentRow="1" class="docListTr">
																		<td>
																			<div class="model-edit-view-oper" data-lui-position='fdOperation-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
																				<div class="model-edit-view-oper-head">
																					<div class="model-edit-view-oper-head-title">
																						<div onclick="changeToOpenOrClose(this)">
																							<i class="open"></i>
																						</div>
																						<span>${listOpers_FormItem.fdOperationName}</span>
																					</div>
																					<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																						<div class="del"
																							 onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																							<i></i>
																						</div>
																						<div class="sortableIcon"><i></i></div>
																					</div>
																				</div>
																				<div class="model-edit-view-oper-content" >
																					<ul>
																						<li class='model-edit-view-oper-content-item first-item'>
																							<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</div>
																							<div class='item-content'>
																									<%-- 操作--%>
																								<input type="hidden" name="listOpers_mobile[${vstatus.index}].fdId" value="${listOpers_FormItem.fdId}" />
																								<input type="hidden" name="listOpers_mobile[${vstatus.index}].fdIsNewPc" value="0" />
																								<div id="_xform_listOpers_Form[${vstatus.index}].fdOperationId" _xform_type="dialog" style="width: 98%;">
																									<xform:dialog style="vertical-align: middle;width:98%" propertyId="listOpers_mobile[${vstatus.index}].fdOperationId" propertyName="listOpers_mobile[${vstatus.index}].fdOperationName" validators=" required" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}">
																										operationSelectViewOperation(null, true,true)
																									</xform:dialog>
																								</div>
																							</div>
																						</li>
																						<li class='model-edit-view-oper-content-item'>
																							<div class='item-title'>
																									${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}
																							</div>
																							<div class='item-content'>
																								<div id="_xform_listOpers_Form[${vstatus.index}].fdJudgeType" _xform_type="dialog" style="width:98%">
																									<div class="common-auth-type common-data-filter-whereType">
																										<input type="hidden" name="listOpers_mobile[${vstatus.index}].fdJudgeType" value="<c:out value="${listOpers_FormItem.fdJudgeType}"/>">
																										<ul>
																											<li class="fdJudgeType_${vstatus.index} active" value="0" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_mobile[${vstatus.index}].fdJudgeType'),true);">${lfn:message('sys-modeling-base:enums.app_viewopers_judgetype.0') }</li>
																											<li class="fdJudgeType_${vstatus.index}" value="1" onclick="onFdJudgeTypeChange(this.value, document.getElementsByName('listOpers_mobile[${vstatus.index}].fdJudgeType'),true);">${lfn:message('sys-modeling-base:enums.app_viewopers_judgetype.1') }</li>
																										</ul>
																									</div>
																								</div>
																							</div>
																						</li>
																							<%-- 公式定义 begin--%>
																						<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-formula-${vstatus.index}" <c:if test="${!(listOpers_FormItem.fdJudgeType eq '1')}"> style="display: none" </c:if> >
																							<div class='item-title'>
																									${lfn:message('sys-modeling-base:modelingAppViewopers.fdFormula')}
																							</div>
																							<div class='item-content'>
																								<div class="inputselectsgl" style="width: 98%; vertical-align: middle;"
																									 onclick="onClick_Formula_Dialog('listOpers_mobile[${vstatus.index}].fdFormula_express','listOpers_mobile[${vstatus.index}].fdFormula_text', 'Boolean');">
																									<div class="input">
																										<input name="listOpers_mobile[${vstatus.index}].fdFormula_text" type="text" value="<c:out value="${listOpers_FormItem.fdFormula_text}"/>">
																										<input name="listOpers_mobile[${vstatus.index}].fdFormula_express" type="hidden" value="<c:out value="${listOpers_FormItem.fdFormula_express}"/>">
																										<input name="listOpers_mobile[${vstatus.index}].fdFormula" type="hidden" value="<c:out value="${listOpers_FormItem.fdFormula}"/>">
																									</div>
																									<div class="selectitem"></div>
																								</div>
																							</div>
																						</li>
																							<%-- 公式定义 end--%>

																							<%-- 条件判断 begin--%>
																						<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-oper-field-${vstatus.index}" <c:if test="${listOpers_FormItem.fdJudgeType eq '1'}"> style="display: none" </c:if>  >
																							<div data-lui-type="sys/modeling/base/resources/js/judgeTypeSettingButton!JudgeTypeSettingButton"
																								 id="_xform_listOpers_mobileForm[${vstatus.index}].fdField" _xform_type="text" style="width:98%" class="mobileListOpersFrom">
																								<script type="text/config">
																									{
																									    xformId: "${xformId}",
																										container: $("#_xform_listOpers_mobileForm\\[${vstatus.index}\\]\\.fdField"),
																										storeData: $.parseJSON('${listOpers_FormItem.fdJudgeConfig}'||'{}'),
																										resultInputPrefix: "listOpers_mobile",
																										resultInputSuffix: ".fdJudgeConfig",
																										channel: 'mobile',
																										modelDict: '${modelDict}'
																									}
																								</script>
																							</div>
																							<div>
																								<input name="listOpers_mobile[${vstatus.index}].fdJudgeConfig" type="hidden" value="<c:out value="${listOpers_FormItem.fdJudgeConfig}"/>">
																							</div>

																						</li>
																							<%-- 条件判断 end--%>
																					</ul>
																				</div>
																			</div>
																		</td>
																	</tr>
																</c:forEach>
															</table>
														</div>
														<input type="hidden" name="listOpers_Flag" value="1">
														<script>
															DocList_Info.push('TABLE_DocList_listOpers_Form_Mobile');
														</script>
													</td>
												</tr>
											</table>
										</div>
									</center>
									<html:hidden property="fdId" />
									<html:hidden property="method_GET" />
									<html:hidden property="fdIsNew" value="true" />
									<html:hidden property="fdName" value="${modelingAppViewForm.fdName}" />
								</html:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<%
	JSONObject enumJSON = ListviewEnumUtil.getAllEnum();
	String enumString = enumJSON.toString();
%>
<script type="text/javascript">
	var _main_data_insystem_enumCollection = <%=enumString%>;
	var _validation = $KMSSValidation();

	if("${modelingAppViewForm.method_GET}" == 'edit') {
		window.document.title = "${ lfn:message('button.edit') } - ${ lfn:message('sys-modeling-base:table.modelingAppView') }";
	}
	if("${modelingAppViewForm.method_GET}" == 'add') {
		window.document.title = "${ lfn:message('button.add') } - ${ lfn:message('sys-modeling-base:table.modelingAppView') }";
	}
	var param = {
		fdModelId : '${param.fdModelId}',
		fdAppId: '${fdAppId }',
		contextPath:Com_Parameter.ContextPath,
		isInDialog: '${param.isInDialog}',
		isPcAndMobile:'${param.isPcAndMobile}',
		isFirstInit: true
	};
	var formInitData = {
	};
	var messageInfo = {
		'listTabs': "${lfn:escapeJs(lfn:message('sys-modeling-base:table.modelingAppViewtab'))}"
	};
	var initData = {
		contextPath: '${LUI_ContextPath}',
		modelDict : '${modelDict}',
		modelName : '${modelName}'
	};
	var validation = $KMSSValidation();
	validation.addValidator('urlCustomize','<span class="validation-advice-title">${lfn:message("sys-modeling-base:modeling.link.address") }</span> ${lfn:message("sys-modeling-base:view.enter.valid.url") }',function(v) {
		var pattern = /^\/[a-zA-Z0-9\w-./?%&=]+/;
		return !validation.getValidator('isEmpty').test(v) && pattern.test(v);}
	);
	validation.addValidator('urlLength','<span class="validation-advice-title">${lfn:message("sys-modeling-base:modeling.link.address") }</span> ${lfn:message("sys-modeling-base:view.URL.address.cannot.exceed.1000") }',function(v) {
				if(v){
					return !validation.getValidator('isEmpty').test(v) && (v.length <1000);
				}else{
					return false;
				}
			}
	);
	Com_IncludeFile("security.js");
	Com_IncludeFile("domain.js");
	Com_IncludeFile("form.js");
	Com_IncludeFile("formula.js");
	Com_IncludeFile("form_option.js", "${LUI_ContextPath}/sys/modeling/base/view/config/", 'js', true);
	Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
	Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
	Com_IncludeFile("view_edit.js",Com_Parameter.ContextPath+'sys/modeling/base/resources/js/','js',true);
	Com_IncludeFile("view_common.js",Com_Parameter.ContextPath+'sys/modeling/base/resources/js/','js',true);
	Com_IncludeFile('calendar.js');

	//--------------公式定义器
	var isFormulaFieldListBuilded = false;
	var formulaFieldList=[];
	function onClick_Formula_Dialog(idField,nameField,returnType){
		var xformId ="${xformId}" ;
		if (!isFormulaFieldListBuilded) {
			var sysObj = Formula_GetVarInfoByModelName("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
			if (sysObj != null) {
				//通过xformId获取appmodel 从而获取有无流程标识
				var getIsFlowUrl = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingBehavior.do?method=findModelIsFlowByXformId&xformId=" + xformId;
				$.ajax({
					url: getIsFlowUrl,
					type: "get",
					async: false,
					success: function (data) {
						if(!data){
							for (var i = 0; i <sysObj.length ; i++) {
								var sysItem  = sysObj[i];
								if(sysItem && sysItem.name=="fdProcessEndTime"){
									//无流程表单去掉流程结束时间
									sysObj.splice(i,1);
								}
							}
						}else{
							//#166825
							var fdHandlerObj = [];
							fdHandlerObj.name="LbpmExpecterLog_fdHandler";
							fdHandlerObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog_fdHandler')}";
							fdHandlerObj.type="com.landray.kmss.sys.organization.model.SysOrgPerson";
							sysObj.push(fdHandlerObj);
							var fdNodeObj = [];
							fdNodeObj.name="LbpmExpecterLog_fdNode";
							fdNodeObj.label="${lfn:message('sys-modeling-base:modeling.lbpm.expecterLog.fdNode')}";
							fdNodeObj.type="String";
							sysObj.push(fdNodeObj);
						}
					}
				});
			}
			var extObj = null;
			extObj = new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId=" + xformId).GetHashMapArray();
			formulaFieldList = formulaFieldList.concat(sysObj);
			if (extObj != null) {
				formulaFieldList = formulaFieldList.concat(extObj);
			}
			isFormulaFieldListBuilded =true;
		}
		Formula_Dialog(idField,nameField, formulaFieldList || "", returnType || "Object", null, null, null, null, null);
	}
	//----------------公式定义器end
	var lastSelectPostionObj;
	var lastSelectPostionDirect;
	//隐藏表格最后一行的向下移动按钮
	$(document).on('detaillist-init',function(e){
		var table = e.target;
		$(table).find(">tbody>tr").last().find("div.down").css("display","none");
	});
	//---------------------封装数据-pcandmobile使用
	function packageData(callBack){
		if (validateDetail()) {
			submit_packageData()
			callBack()
		}
	}
	seajs.use([ 'lui/topic', 'lui/jquery',"sys/modeling/base/formlog/res/mark/viewFMMark",'lui/dialog','sys/modeling/base/resources/js/judgeTypeSettingButton']
			,function(topic,$,viewFMMark,dialog,settingbutton){
				window.modelingViewNewEdit_load = dialog.loading();
				//窗口大小自适应
				function onResizeFitWindow() {
					var height = $('body').height();
					if (param.isInDialog) {
						$('.model-edit-view-title:eq(0)').hide()
					} else {
						height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
					}
					if (param.isPcAndMobile) {
						//Pc+移动
						height = $(parent.document).find(".modeling-pam-content-frame").eq(0).outerHeight(true);
						$(".model-edit-right-wrap .model-edit-view-content").height(height-80);
						$(".model-edit-left-wrap .model-edit-view-content").height(height-60);
						$(".preview-mobile .model-body-content-wrap").height(height - 60);
						//
						$("[lui-pam-hidden='true']").hide();
						$(".model-edit-view-title").hide();
						$(".model-edit-left").css({"padding-top":0})
						$(".model-edit-right").css({"padding-top":0,"border-top":"1px solid #DDDDDD","border-bottom":"1px solid #DDDDDD"})
					}else{
						$("body", parent.document).find('#trigger_iframe').height(height);
						$(".model-edit-right-wrap .model-edit-view-content").height(height - 80);
						$(".model-edit-left-wrap .model-edit-view-content").height(height - 60);
						$(".preview-mobile .model-body-content-wrap").height(height - 60);
						$("body", parent.document).css("overflow", "hidden");
					}
				}

				function updateOpenRuleSettingXY(){
					var $openRuleSetting = $(".open_rule_setting");
					var $openRuleSettingToRight = $(".open_rule_setting_to_right");
					if(0<$openRuleSetting.length){
						var $parentButton = $openRuleSetting.parent();
						var x = $parentButton.offset().top;
						var y = $parentButton.offset().left;
						$openRuleSetting.css({
							"top": x - 200,
							"left": y - 548
						});
						$openRuleSettingToRight.css({
							"top": x + 8,
							"left": y - 10
						});
					}
				}



				$(window).resize(function() {
					onResizeFitWindow();
					//updateOpenRuleSettingXY();
				});


				//预览加载完毕事件
				topic.subscribe('preview_load_finish', function (ctx) {
					onResizeFitWindow();
					//#132181:业务标签（传阅记录和沉淀记录）和业务操作之间的联动
					var tabDataShow = showTabByOper("true");
					showOrHideTab(tabDataShow);
					var tabDataShow = showTabByOper("false");
					showOrHideTab(tabDataShow);
				});

				//预览更新事件
				topic.subscribe('preview.refresh',function(){
					try{
						//刷新预览
						LUI("view_preview").setSourceData(getNewViewData);
						LUI("view_preview").reRender();
						//恢复选择的位置
						if(lastSelectPostionObj && lastSelectPostionDirect){
							switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect);
						}
						//刷新预览
						LUI("view_preview_mobile").setSourceData(getNewViewData4m);
						LUI("view_preview_mobile").reRender();
						//恢复选择的位置
						if(lastSelectPostionObj && lastSelectPostionDirect){
							switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect);
						}
					}catch(err){}
				})
				//表单映射
				var viewFMMark = new viewFMMark.ViewFMMark({fdId:"${param.fdId}"});
				viewFMMark.startup();

				//切换PC和移动
				window.showPcOrMobile = function(type){
					//切换前先校验当前配置是否正确
					if(false == param.isFirstInit && !validation.validate()){
						return;
					}
					param.isFirstInit = false;
					if(type === "pc"){
						$(".modeling-pam-top-center li").filter(".pc").addClass("active");
						$(".modeling-pam-top-center li").filter(".mobile").removeClass("active");
						$(".view-edit-right-pc").css("display","block");
						$(".view-edit-right-mobile").css("display","none");
						$(".preview-pc").css("display","block");
						$(".preview-mobile").css("display","none");
						changeRightView("design");
					}else if(type === "mobile"){
						$(".modeling-pam-top-center li").filter(".mobile").addClass("active");
						$(".modeling-pam-top-center li").filter(".pc").removeClass("active");
						$(".view-edit-right-mobile").css("display","block");
						$(".view-edit-right-pc").css("display","none");
						$(".preview-mobile").css("display","block");
						$(".preview-pc").css("display","none");
						changeRightView("design");
					}
					//将右边内容显示
					$(".model-edit-right-view").css("display","block");
				}

				//关闭条件判断设置窗口
				window.closeStatisticsSetting = function (){
					var $openRuleSetting = $(".open_rule_setting");
					$openRuleSetting.each(function () {
						if( "none" !=  $(this).css("display")){
							$(this).css("display","none")
						}
					})

					var $openRuleSettingToRight = $(".open_rule_setting_to_right");
					$openRuleSettingToRight.each(function () {
						if( "none" !=  $(this).css("display")){
							$(this).css("display","none")
						}
					})
				}

				window.triggleSelectdatetime = function (event, dom, type, name) {
					var input = $(dom).find("input[name='" + name + "']");
					if (type == "DateTime") {
						selectDateTime(event, input);
					} else if (type == "Date") {
						selectDate(event, input);
					} else {
						selectTime(event, input);
					}
				}

				//新增业务操作，作获取设置按钮对象位置信息，以便绘画设置按钮
				//参数：optTb-模板名称，className定位类名，xform定位表单名，resultInputPrefix结果输入框名称前缀,channel渠道端
				window.addRowOpers = function(optTb, className,xform,resultInputPrefix,channel){
					DocList_AddRow_Custom_Opers(optTb);
					var listOpersFromDivs = $("."+className) ;
					var index = listOpersFromDivs.length -1 ;
					var params= {
						id: xform+"["+ index +"].fdField",
						xformId: "${xformId}",
						container: $("#"+xform+"\\["+ index +"\\]\\.fdField"),
						storeData: {},
						resultInputPrefix: resultInputPrefix,
						resultInputSuffix: ".fdJudgeConfig",
						channel: channel,
						modelDict: this.initData.modelDict
					};
					var newAddSettingbutton= new settingbutton.JudgeTypeSettingButton(params);
					newAddSettingbutton.startup();
					newAddSettingbutton.draw();
				}

				//新增业务标签，作获取设置按钮对象位置信息，以便绘画设置按钮
				//参数：optTb-模板名称，isMobile-是否模板（true：是，false：否），className定位类名，xform定位表单名，resultInputPrefix结果输入框名称前缀,channel渠道端
				window.addRowTabs = function(optTb,isMobile,className,xform,resultInputPrefix,channel){
					DocList_AddRow_Custom_Tabs(optTb,isMobile);
					var listOpersFromDivs = $("."+className) ;
					var index = listOpersFromDivs.length -1 ;
					var params= {
						id: xform+"["+ index +"].fdField",
						xformId: "${xformId}",
						container: $("#"+xform+"\\["+ index +"\\]\\.fdField"),
						storeData: {},
						resultInputPrefix: resultInputPrefix,
						resultInputSuffix: ".fdConfig",
						channel: channel,
						modelDict: this.initData.modelDict
					};
					var newAddSettingbutton= new settingbutton.JudgeTypeSettingButton(params);
					newAddSettingbutton.startup();
					newAddSettingbutton.draw();
				}

			});
	Com_AddEventListener(window,'load',function(){
		//移除头部导航的禁止点击样式，防止页面还没加载完就点击报错
		$(".modeling-pam-top-center ul li").removeAttr("style");
		//名称没填默认
		var fdName = "${modelingAppViewForm.fdName}" || "${lfn:message('sys-modeling-base:view.untitled.view') }";
		$("[name='fdName']").val(fdName);
		//触发pc
		showPcOrMobile("pc");
		var num = setInterval(function(){
			//初始化编辑页面预览
			if(LUI("view_preview")){
				clearInterval(num);
				LUI("view_preview").setSourceData(getNewViewData);
				LUI("view_preview").reRender();
				//恢复选择的位置
				if(lastSelectPostionObj && lastSelectPostionDirect){
					switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect);
				}
			}
			if(LUI("view_preview_mobile")){
				clearInterval(num);
				LUI("view_preview_mobile").setSourceData(getNewViewData4m);
				LUI("view_preview_mobile").reRender();
				//恢复选择的位置
				if(lastSelectPostionObj && lastSelectPostionDirect){
					switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect);
				}
			}
		}, 200);

		//业务标签展示类型
		$(".view_flag_radio").find("[name*='fdType']").each(function(){
			var radioObj = $(this).parents(".view_flag_radio")[0];
			if($(this).val() == 0){
				$(radioObj).find("[index='0'] i").addClass("view_flag_yes");
				$(radioObj).find("[index='1'] i").removeClass("view_flag_yes");
				$(radioObj).find("[index='2'] i").removeClass("view_flag_yes");
				changeTargetForm(this,0);
			}else if($(this).val() == 1){
				$(radioObj).find("[index='1'] i").addClass("view_flag_yes");
				$(radioObj).find("[index='0'] i").removeClass("view_flag_yes");
				$(radioObj).find("[index='2'] i").removeClass("view_flag_yes");
				changeTargetForm(this,1);
			}else if($(this).val() == 2){
				//修改标题
				var name = $(this).attr('name');
				var index = name.substring(name.indexOf('[')+1,name.indexOf(']'));
				var fdLinkDiv = $(this).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdLink");
				var itemTitle = fdLinkDiv.parent('.item-content').parent(".model-edit-view-oper-content-item").find('.item-title');
				itemTitle.text("${lfn:message('sys-modeling-base:modeling.link.address') }");

				$(radioObj).find("[index='2'] i").addClass("view_flag_yes");
				$(radioObj).find("[index='0'] i").removeClass("view_flag_yes");
				$(radioObj).find("[index='1'] i").removeClass("view_flag_yes");
				changeTargetForm(this,2);
			}
		})
		//业务标签显示关闭，展开不可选择
		$("[name*='fdIsShow']").each(function(){
			if($(this).val() == '0'){
				$(this).closest(".lui-component").next().find("input[type='checkbox']").attr("disabled","disabled");
			}
		})

		//新版查看视图增加业务操作 判断条件回显
		$('input[name*="\\]\\.fdJudgeType"]').each(function(){
			var name = $(this).attr("name");
			var index = name.substring(name.indexOf("[") + 1, name.indexOf("]"));
			//获取需要显示li
			var liActive = $(this).parent().find(".fdJudgeType_"+index).filter("[value='"+$(this).val()+"']");
			//激活显示li
			liActive.addClass("active");
			liActive.siblings().removeClass("active");
		});
		modelingViewNewEdit_load.hide();
	});
	function IsOpen(e,name){
		var value = e.checked;
		//关闭显示，展开也关闭,且不可打开
		if(value == false || value == "false"){
			$(e).closest(".lui-component").next().find("input[type='checkbox']").removeAttr("checked");
			$(e).closest(".lui-component").next().find("input[type='hidden']").val("0");
			$(e).closest(".lui-component").next().find("input[type='checkbox']").attr("disabled","disabled");
		}else{
			$(e).closest(".lui-component").next().find("input[type='checkbox']").removeAttr("disabled");
		}
	}
	// 业务标签拖动排序
	$("#TABLE_DocList_listTabs_Form").dragsort({
		dragSelector: "tr",
		//#164982 【日常缺陷】【低代码平台】权限-业务标签的显示、默认展开开关来回展开关闭，显示排序会变化
		dragSelectorExclude: ".model-edit-view-oper-content ul , .weui_switch_bd",
		dragEnd: dragEnd,
		dragBetween: true,
		placeHolderTemplate: "<tr></tr>"
	});

	$("#TABLE_DocList_listTabs_Form_Mobile").dragsort({
		dragSelector: "tr",
		dragSelectorExclude: ".model-edit-view-oper-content ul",
		dragEnd: dragEnd4m,
		dragBetween: true,
		placeHolderTemplate: "<tr></tr>"
	});

	// 业务操作拖动排序
	$("#TABLE_DocList_listOpers_Form").dragsort({
		dragSelector: "tr",
		dragSelectorExclude: ".model-edit-view-oper-content ul",
		dragEnd: dragOperEnd,
		dragBetween: true,
		placeHolderTemplate: "<tr></tr>"
	});

	$("#TABLE_DocList_listOpers_Form_Mobile").dragsort({
		dragSelector: "tr",
		dragSelectorExclude: ".model-edit-view-oper-content ul",
		dragEnd: dragOperEnd4m,
		dragBetween: true,
		placeHolderTemplate: "<tr></tr>"
	});

	function dragEnd() {
		$("#TABLE_DocList_listTabs_Form tr").each(function () {
			DocListFunc_RefreshIndex(DocList_TableInfo["TABLE_DocList_listTabs_Form"],$(this).index());
		});
	}
	function dragEnd4m() {
		$("#TABLE_DocList_listTabs_Form_Mobile tr").each(function () {
			DocListFunc_RefreshIndex(DocList_TableInfo["TABLE_DocList_listTabs_Form_Mobile"],$(this).index());
		});
	}
	function dragOperEnd() {
		$("#TABLE_DocList_listOpers_Form tr").each(function () {
			DocListFunc_RefreshIndex(DocList_TableInfo["TABLE_DocList_listOpers_Form"],$(this).index());
		});
	}
	function dragOperEnd4m() {
		$("#TABLE_DocList_listOpers_Form_Mobile tr").each(function () {
			DocListFunc_RefreshIndex(DocList_TableInfo["TABLE_DocList_listOpers_Form_Mobile"],$(this).index());
		});
	}
	//业务标签悬浮图标
	$(".buildin").on("mouseenter", function () {
		$(this).addClass("drag");
	}).on("mouseleave", function () {
		$(this).removeClass("drag");
	});
	//目标表单切换
	function changeTargetForm(obj,value){
		var radioObj = $(obj).parents(".view_flag_radio")[0];
		var curVal = $(radioObj).find("[name*='fdType']").val();
		var curName = $(radioObj).find("[name*='fdType']").attr("name");
		$(radioObj).find("[name*='fdType']").val(value);
		if(curVal === value){
			return;
		}
		if(value == 0){
			$(radioObj).find("[index='0'] i").addClass("view_flag_yes");
			$(radioObj).find("[index='1'] i").removeClass("view_flag_yes");
			$(radioObj).find("[index='2'] i").removeClass("view_flag_yes");
		}else if(value == 1){
			$(radioObj).find("[index='1'] i").addClass("view_flag_yes");
			$(radioObj).find("[index='0'] i").removeClass("view_flag_yes");
			$(radioObj).find("[index='2'] i").removeClass("view_flag_yes");
		}else if(value == 2){
			$(radioObj).find("[index='2'] i").addClass("view_flag_yes");
			$(radioObj).find("[index='0'] i").removeClass("view_flag_yes");
			$(radioObj).find("[index='1'] i").removeClass("view_flag_yes");
		}
		$(".view_flag_no").parent("div").removeAttr("style");
		$(".view_flag_no").parent("div").css({
			"display": "inline-block",
			"cursor": "pointer"});

		$(".view_flag_radio_yes").css({
			"width": "100px",
			"height": "26px",
			"background": "#F2F2F2",
			"border-radius": "1px",
			"box-shadow": "0px 0px 0px 0px rgb(0 0 0 / 0%)"
		});
		$(".view_flag_yes").parent("div").css({
			"display": "inline-block",
			"cursor": "pointer",
			"width": "100px",
			"height": "26px",
			"background": "#FFFFFF",
			"box-shadow": "0px 2px 4px 0px rgb(0 0 0 / 10%)",
			"border-radius": "1px",
			"margin-top": "3px",
			"margin-left": "3px"
		});
		fdTypeChange(value,document.getElementsByName(curName));
	}

	/**
	 * 基础设置和视图设置的切换
	 * @param type
	 */
	function changeRightView(type) {
		if(type === "common"){
			$(".model-edit-view-title li").filter(".common").addClass("active");
			$(".model-edit-view-title li").filter(".design").removeClass("active");
			$(".view-fdName").removeAttr("style")
			$(".view-design").css("display","none");
			$(".view-edit-common").css("display","block");
		}else if(type === "design"){
			$(".model-edit-view-title li").filter(".design").addClass("active");
			$(".model-edit-view-title li").filter(".common").removeClass("active");
			$(".view-fdName").css("display","none");
			$(".view-design").css("display","");
			$(".view-model").css("display","none");
			$(".view-edit-common").css("display","none");
		}
	}

	//展现内置标签---访问统计
	$(".mobile-businessTag").each(function(){
		var tabName = $(this).find("[name*='fdName']").val();
		if(tabName === "访问统计"){
			$(this).css("display","block");
		}
	})

</script>
