<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.modeling.base.util.ListviewEnumUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/businessTag.css" />
<style type="text/css">
		 html{
		 	height:100%;
		 }
		 body{
		 	height:100%;
		 	overflow: hidden;
		 }
    	.lui_paragraph_title{
    		font-size: 15px;
    		color: #15a4fa;
        	padding: 15px 0px 5px 0px;
    	}
    	.lui_paragraph_title span{
    		display: inline-block;
    		margin: -2px 5px 0px 0px;
    	}
    	.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
      		border: 0px;
      		color: #868686
    	}
    	.lui_custom_list_boxs{
	    	border-top: 1px solid #d5d5d5;
			position:fixed;
			bottom:0;
			width:100%;
			background-color: #fff;
			z-index:1000;
			height:63px;
	    }
	    table.model-view-panel-table>tbody>tr>td.td_normal_title{
	    	text-align: left;
			padding-left: 20px;
			padding-right: 20px;
	    }
	    .model-view-panel-table .model-view-panel-table-td{
			padding-left: 20px!important;
			padding-right: 20px!important;
			padding-bottom: 15px!important;
		}
	    .view-model-edit{
	    	min-width:930px;
	    }
	    div.model-body-wrap .model-body-content-desc{
			padding-bottom:0;
			margin-top:14px;
			height: auto;
   			line-height: normal;
		}
		div.model-body-wrap .model-body-content-desc i{
			margin-top:2px;
		}

		 .model-phone-opt{
			 height: 38px;
			 overflow: hidden;
		 }
		 .model-body-wrap .model-body-content-phone-view {
			 height: 525px;
		 }
		.txtstrong{
			float:right;
		}
</style>
<script>
	Com_IncludeFile("doclist.js");
	Com_IncludeFile("view.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
	Com_IncludeFile("dialog.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
</script>
    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>
<div class="model-body-content" id="editContent">
	<!-- 这里放入你的组件 starts -->
	<div class="model-edit view-model-edit">
		<div class="model-edit-left model-edit-left-view">
			<div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview" style="display: none">
		        <div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
		        <div data-lui-type="lui/view/render!Template" style="display:none;">
		            <script type="text/config">
 						{
							src : '/sys/modeling/base/resources/js/preview/view_mobile.html#'
						}
                	</script>
		        </div>
		    </div>
		</div>
		<div class="model-edit-right model-edit-right-view">
			<div class="model-edit-right-wrap">
				<div class="model-edit-view-title">
					<p>视图配置</p>
					<c:if test="${modelingAppViewForm.method_GET=='edit' || modelingAppListviewForm.method_GET=='editTemplate'}">
						 <div onclick="viewSubmit('update')"><bean:message key="button.update"/></div>
					</c:if>
					<c:if test="${modelingAppViewForm.method_GET=='add'}">
						<div onclick="viewSubmit('save')"><bean:message key="button.save"/></div>
					</c:if>
				</div>
				<div class="model-edit-view-content">
					<div class="model-edit-view-content-wrap">
						<html:form action="/sys/modeling/base/modelingAppView.do" style="height:100%">
					    <center>
					        <div style="width:95%;">
					            <table class="tb_simple model-view-panel-table" width="100%">
					                <tr lui-pam-hidden="${param.isPcAndMobile}">
										<td class="td_normal_title">
											${lfn:message('sys-modeling-base:modelingAppView.fdName')}
										</td>
									</tr>
					                <tr lui-pam-hidden="${param.isPcAndMobile}">
					                    <td colspan="3" width="85%" class="model-view-panel-table-td">
					                        <%-- 名称--%>
					                        <div id="_xform_fdName" _xform_type="text" style="width: 100%">
					                            <xform:text property="fdName" showStatus="edit" style="width:96%;height:28px!important;" />
					                        </div>
					                    </td>
					                </tr>
									<tr lui-pam-hidden="${param.isPcAndMobile}">
										<td colspan="3" width="85%" class="model-view-panel-table-td">
											<div id="_xform_fdIsDefault" class="model-view-is-default" _xform_type="text" style="width: 100%">
												<div>设置为默认视图</div>
												<div class="modeling-viewcover-tip">
													<span>设置为默认视图之后，应用中跳转默认视图的地方会跳转该视图，每个表单仅支持配置一个默认视图。</span>
												</div>
												<ui:switch property="fdIsDefault" checkVal="true" unCheckVal="false" text=""></ui:switch>
											</div>
										</td>
									</tr>
					                <tr style="display:none;">
										<td class="td_normal_title">
											${lfn:message('sys-modeling-base:modelingAppView.fdModel')}
										</td>
									</tr>
					                <tr style="display:none;">
					                    <td colspan="3" width="85%" class="model-view-panel-table-td">
					                        <%-- 业务模块--%>
					                        <div id="_xform_fdModelId" _xform_type="select">
					                            <xform:select property="fdModelId" value="${param.fdModelId }" onValueChange="modelChange" htmlElementProperties="id='fdModelId'" showStatus="edit">
					                                <xform:beanDataSource serviceBean="modelingAppModelService" selectBlock="fdId,fdName" whereBlock="modelingAppModel.fdApplication='${fdAppId }'"/>
					                            </xform:select>
					                        </div>
					                    </td>
					                </tr>
									<!-- 业务操作 -->
					                <tr>
										<td class="td_normal_title" data-lui-position='fdOperation'>
											 ${lfn:message('sys-modeling-base:modelingAppView.listOpers')}
										</td>
									</tr>
					                <tr>
					                    <td colspan="3" width="100%" class="model-view-panel-table-td">
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
																	<span>操作项<span class='title-index'>!{index}</span></span>
																</div>
																<div class="model-edit-view-oper-head-item" style="padding-top:0px;">
																	<div class="del"
																		onclick="updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
																		<i></i>
																	</div>
																	<div style="display:none" class="down"
																		onclick="DocList_MoveRow(1);updateRowAttr(1);">
																		<i></i>
																	</div>
																	<div class="up"
																		onclick="DocList_MoveRow(-1);updateRowAttr(-1);">
																		<i></i>
																	</div>
																</div>
															</div>
															<div class="model-edit-view-oper-content">
																<ul>
																	<li class='model-edit-view-oper-content-item first-item'>
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</div>
																		<div class='item-content'>
																			<%-- 操作--%>
										                                    <input type="hidden" name="listOpers_Form[!{index}].fdId" value="" disabled="true" />
										                                    <div id="_xform_listOpers_Form[!{index}].fdOperationId" _xform_type="dialog" style="width:100%">
										                                        <xform:dialog style="width:100%;vertical-align: middle;" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')} " propertyId="listOpers_Form[!{index}].fdOperationId" propertyName="listOpers_Form[!{index}].fdOperationName" showStatus="edit" validators=" required">
										                                            operationSelectViewOperation(null, true)
										                                        </xform:dialog>
										                                    </div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item'>
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}</div>
																		<div class='item-content'>
																			<div id="_xform_listOpers_Form[!{index}].fdJudgeType" _xform_type="dialog" style="width:100%">
																				<xform:radio property="listOpers_Form[!{index}].fdJudgeType"
																							 showStatus="edit"
																							 required="true" onValueChange="onFdJudgeTypeChange"
																							 value="0" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}">
																					<xform:enumsDataSource enumsType="sys_modeling_app_viewopers_judgetype" />
																				</xform:radio>
																			</div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-formula-!{index}" style="display: none">
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdFormula')}</div>
																		<div class='item-content'>
																			<div class="inputselectsgl" style="width: 100%; vertical-align: middle;"
																				 onclick="onClick_Formula_Dialog('listOpers_Form[!{index}].fdFormula_express','listOpers_Form[!{index}].fdFormula_text', 'Boolean');">
																				<div class="input">
																					<input name="listOpers_Form[!{index}].fdFormula_express" type="hidden" value="">
																					<input name="listOpers_Form[!{index}].fdFormula_text" type="text" value="">
																					<input name="listOpers_Form[!{index}].fdFormula" type="hidden" value="">
																				</div>
																				<div class="selectitem"></div>
																			</div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-field-!{index}">
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdField')}</div>
																		<div class='item-content'>
																				<%-- 条件字段--%>
																			<div id="_xform_listOpers_Form[!{index}].fdField" _xform_type="text" style="width:100%">
																				<xform:select onValueChange="onFdFieldChange" style="width:100%;" property="listOpers_Form[!{index}].fdField" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdField')}" showPleaseSelect="false">
																					<xform:simpleDataSource value="">无</xform:simpleDataSource>
																					<xform:beanDataSource serviceBean="modelingAppListviewModelDictService" selectBlock="fdId,fdName" whereBlock="fdAppModelId='${param.fdModelId }'"/>
																				</xform:select>
																			</div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item'  data-lui-mark="view-oper-field-!{index}" style="display: none">
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}</div>
																		<div class='item-content'>
																				<%-- 运算符--%>
																			<div id="_xform_listOpers_Form[!{index}].fdOperator" _xform_type="text" style="display: none;width:100%">
																				<xform:select onValueChange="onOperatorChange" style="width:100%;" property="listOpers_Form[!{index}].fdOperator" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}" showPleaseSelect="false">
																					<xform:simpleDataSource value="equals">${lfn:message('sys-modeling-base:modelingAppListview.enum.equal') }</xform:simpleDataSource>
																					<xform:simpleDataSource value="contain">${lfn:message('sys-modeling-base:modelingAppListview.enum.contain') }</xform:simpleDataSource>
																					<xform:simpleDataSource value="notContain">${lfn:message('sys-modeling-base:modelingAppListview.enum.notContain') }</xform:simpleDataSource>
																				</xform:select>
																			</div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-oper-field-!{index}" style="display:none;">
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</div>
																		<div class='item-content'>
																				<%-- 值--%>
																			<div style="vertical-align:top;display: inline-block;width:30%">
																				<xform:select property="listOpers_Form[!{index}].fdValue" style="display: none;width:100%" onValueChange="onFdValueChange" htmlElementProperties="id='listOpers_Form[!{index}].fdValue'" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}" showPleaseSelect="false">
																					<xform:enumsDataSource enumsType="sys_modeling_app_viewopers_value" />
																				</xform:select>
																			</div>
																				<%-- 输入值--%>
																			<input type='hidden' name="listOpers_Form[!{index}].fdInputValue"/>
																			<div class="input_radio height28" id="_xform_listOpers_Form[!{index}].fdInputValue" style="width:67%;display: none;" _xform_type="text" style="display: inline-block;">
																				<xform:text property="_listOpers_Form[!{index}].fdInputValue" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdInputValue')}" validators=" maxLength(200)" style="width:100%;" />
																			</div>
																		</div>
																	</li>
																</ul>
															</div>
														</div>
													</td>
												</tr>
												<c:forEach items="${modelingAppViewForm.listOpers_Form}" var="listOpers_FormItem" varStatus="vstatus">
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
																	<div class="down"
																		onclick="DocList_MoveRow(1);updateRowAttr(1);">
																		<i></i>
																	</div>
																	<div class="up"
																		onclick="DocList_MoveRow(-1);updateRowAttr(-1);">
																		<i></i>
																	</div>
																</div>
															</div>
															<div class="model-edit-view-oper-content">
																<ul>
																	<li class='model-edit-view-oper-content-item first-item'>
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}</div>
																		<div class='item-content'>
																			<%-- 操作--%>
									                                        <input type="hidden" name="listOpers_Form[${vstatus.index}].fdId" value="${listOpers_FormItem.fdId}" />
									                                        <div id="_xform_listOpers_Form[${vstatus.index}].fdOperationId" _xform_type="dialog" style="width: 99%;">
									                                            <xform:dialog style="vertical-align: middle;width:100%" propertyId="listOpers_Form[${vstatus.index}].fdOperationId" propertyName="listOpers_Form[${vstatus.index}].fdOperationName" validators=" required" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation')}">
									                                                operationSelectViewOperation(null, true)
									                                            </xform:dialog>
									                                        </div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item'>
																		<div class='item-title'>
																				${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}
																		</div>
																		<div class='item-content'>
																			<div id="_xform_listOpers_Form[${vstatus.index}].fdJudgeType" _xform_type="dialog" style="width:100%">
																				<xform:radio property="listOpers_Form[${vstatus.index}].fdJudgeType" showStatus="edit" required="true" onValueChange="onFdJudgeTypeChange"
																							 subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdJudgeType')}">
																					<xform:enumsDataSource enumsType="sys_modeling_app_viewopers_judgetype" />
																				</xform:radio>
																			</div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-formula-${vstatus.index}" <c:if test="${!(listOpers_FormItem.fdJudgeType eq '1')}"> style="display: none" </c:if> >
																		<div class='item-title'>
																				${lfn:message('sys-modeling-base:modelingAppViewopers.fdFormula')}
																		</div>
																		<div class='item-content'>
																			<div class="inputselectsgl" style="width: 100%; vertical-align: middle;"
																				 onclick="onClick_Formula_Dialog('listOpers_Form[${vstatus.index}].fdFormula_express','listOpers_Form[${vstatus.index}].fdFormula_text', 'Boolean');">
																				<div class="input">
																					<input name="listOpers_Form[${vstatus.index}].fdFormula_text" type="text" value="<c:out value="${listOpers_FormItem.fdFormula_text}"/>">
																					<input name="listOpers_Form[${vstatus.index}].fdFormula_express" type="hidden" value="<c:out value="${listOpers_FormItem.fdFormula_express}"/>">
																					<input name="listOpers_Form[${vstatus.index}].fdFormula" type="hidden" value="<c:out value="${listOpers_FormItem.fdFormula}"/>">
																				</div>
																				<div class="selectitem"></div>
																			</div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-field-${vstatus.index}"  <c:if test="${listOpers_FormItem.fdJudgeType eq '1'}"> style="display: none" </c:if>  >
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdField')}</div>
																		<div class='item-content'>
																				<%-- 条件字段--%>
																			<div id="_xform_listOpers_Form[${vstatus.index}].fdField" _xform_type="text" style="width:100%">
																				<xform:select style="width:100%" onValueChange="onFdFieldChange" property="listOpers_Form[${vstatus.index}].fdField" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdField')}" showPleaseSelect="false">
																					<xform:simpleDataSource value="">${lfn:message('sys-modeling-base:behavior.null') }</xform:simpleDataSource>
																					<xform:beanDataSource serviceBean="modelingAppListviewModelDictService" selectBlock="fdId,fdName" whereBlock="fdAppModelId='${param.fdModelId }'"/>
																				</xform:select>
																			</div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item' data-lui-mark="view-oper-field-${vstatus.index}" <c:if test="${listOpers_FormItem.fdJudgeType eq '1'}"> style="display: none" </c:if>  style="display: none">
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}</div>
																		<div class='item-content'>
																			<%-- 运算符--%>
									                                        <div id="_xform_listOpers_Form[${vstatus.index}].fdOperator" _xform_type="text" style="width:100%;display: ${listOpers_FormItem.fdField == '' ? 'none' : 'display'}">
									                                            <xform:select style="width:100%" onValueChange="onOperatorChange" property="listOpers_Form[${vstatus.index}].fdOperator" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}" showPleaseSelect="false">
									                                            	<xform:simpleDataSource value="equal">${lfn:message('sys-modeling-base:modelingAppListview.enum.equal') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="contain">${lfn:message('sys-modeling-base:modelingAppListview.enum.contain') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="notContain">${lfn:message('sys-modeling-base:modelingAppListview.enum.notContain') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="less">${lfn:message('sys-modeling-base:modelingAppListview.enum.less') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="lessEqual">${lfn:message('sys-modeling-base:modelingAppListview.enum.lessEqual') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="more">${lfn:message('sys-modeling-base:modelingAppListview.enum.more') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="moreEqual">${lfn:message('sys-modeling-base:modelingAppListview.enum.moreEqual') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="less2">${lfn:message('sys-modeling-base:modelingAppListview.enum.less2') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="more2">${lfn:message('sys-modeling-base:modelingAppListview.enum.more2') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="lessEqual2">${lfn:message('sys-modeling-base:modelingAppListview.enum.lessEqual2') }</xform:simpleDataSource>
									                                            	<xform:simpleDataSource value="moreEqual2">${lfn:message('sys-modeling-base:modelingAppListview.enum.moreEqual2') }</xform:simpleDataSource>
									                                            </xform:select>
									                                        </div>
																		</div>
																	</li>
																	<li class='model-edit-view-oper-content-item last-item' data-lui-mark="view-oper-field-${vstatus.index}" <c:if test="${listOpers_FormItem.fdJudgeType eq '1'}"> style="display: none" </c:if>  style="display: none">
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}</div>
																		<div class='item-content'>
																				<%-- 值--%>
																			<div style="vertical-align:top;display: inline-block;width:30%">
																				<xform:select property="listOpers_Form[${vstatus.index}].fdValue" style="width:100%;display: ${listOpers_FormItem.fdField == '' ? 'none' : 'display'}" onValueChange="onFdValueChange" htmlElementProperties="id='listOpers_Form[${vstatus.index}].fdValue'" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}" showPleaseSelect="false">
																					<xform:enumsDataSource enumsType="sys_modeling_app_viewopers_value" />
																				</xform:select>
																			</div>
																				<%-- 输入值--%>
																			<input type='hidden' name="listOpers_Form[${vstatus.index}].fdInputValue" value='${listOpers_FormItem.fdInputValue }'/>
																			<div class="input_radio height28" id="_xform_listOpers_Form[${vstatus.index}].fdInputValue" _xform_type="text" style="width: 67%; display: ${listOpers_FormItem.fdField != '' && listOpers_FormItem.fdValue == 'fix' ? 'inline-block' : 'none'}">
																				<xform:text property="_listOpers_Form[${vstatus.index}].fdInputValue" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewopers.fdInputValue')}" validators=" maxLength(200)" style="width: 100%"/>
																			</div>
																		</div>
																	</li>
																</ul>
															</div>
														</div>
													</td>
												</tr>
												</c:forEach>
					                        </table>
					                        </div>
					                        <div class="model-data-create" onclick="DocList_AddRow_Custom_Opers('TABLE_DocList_listOpers_Form');">
												<div>${lfn:message('sys-modeling-base:button.add')}</div>
											</div>
					                        <input type="hidden" name="listOpers_Flag" value="1">
					                        <script>
					                            DocList_Info.push('TABLE_DocList_listOpers_Form');
					                        </script>
					                    </td>
					                </tr>

									<!-- 业务标签 -->
									<tr>
										<td class="td_normal_title" data-lui-position='fdTag'>
												${lfn:message('sys-modeling-base:modelingAppView.listTabs')}
										</td>
									</tr>
									<tr class="business_tr">
										<td colspan="3" width="100%" class="model-view-panel-table-td">
											<table id="TABLE_DocList_listTabs_Form" class="tb_simple model-edit-view-oper-content-table" width="100%" style="position:relative;">
													<%-- 基准行，KMSS_IsReferRow = 1 --%>
												<tr style="display:none;" KMSS_IsReferRow="1" class="docListTr">
													<td>
														<div class="model-edit-view-oper" data-lui-position='fdTag-!{index}' onclick="switchSelectPosition(this,'right')">
															<div class="model-edit-view-oper-head">
																<input name="listTabs_Form[!{index}].fdOrder"  type='hidden'/>
																<input name="listTabs_Form[!{index}].fdTabType" value="0" type='hidden'/>
																<div class="model-edit-view-oper-head-title">
																	<span>${lfn:message('sys-modeling-base:view.label')}<span class='title-index'>!{index}</span></span>
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
																			<input type="hidden" name="listTabs_Form[!{index}].fdId" value="" disabled="true" />
																			<div id="_xform_listTabs_Form[!{index}].fdName" _xform_type="text" style="width:100%">
																				<xform:text property="listTabs_Form[!{index}].fdName" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}" required="true" validators="maxLength(18)" style="width:95%;height:28px!important;" onValueChange="tagNameUpdate"/>
																			</div>
																		</div>
																	</li>
																		<%-- 默认展开 --%>
																	<li class='model-edit-view-oper-content-item' style="display:none;">
																		<ui:switch property="listTabs_Form[!{index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}"></ui:switch>
																	</li>
																	<input type="hidden" name="listTabs_Form[!{index}].fdIsShow" value="1" />
																	<li class='model-edit-view-oper-content-item'>
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdType')}</div>
																		<div class='item-content'>
																				<%-- 展开样式--%>
																			<div id="_xform_listTabs_Form[!{index}].fdType" _xform_type="select" style="width: 100%">
																				<div class="view_flag_radio" style="display: inline-block;">
																					<input type="hidden" name="listTabs_Form[!{index}].fdType" value="1">
																					<div class="view_flag_radio_yes" index="1" style="display: inline-block;cursor: pointer;"><i class="view_flag_no view_flag_yes"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.1') }</div>
																					<div class="view_flag_radio_no view_flag_last" index="2" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,2)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:view.link') }</div>
																				</div>
																			</div>
																		</div>
																	</li>
																		<%--目标表单 --%>
																	<li class='model-edit-view-oper-content-item' id='tabSourceLi_index_!{index}'>
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.targetForm') }</div>
																		<div class='item-content'>
																			<input name='listTabs_Form[!{index}].fdApplicationId' type='hidden'/>
																			<input name='listTabs_Form[!{index}].fdModelId' type='hidden'/>
																			<div class="item-content-element-choose" onclick="chooseTabSource(this,'${fdAppId }',true);">
																				<p class="item-content-element-label"></p>
																				<i></i>
																			</div>
																		</div>
																	</li>
																		<%--展示数据 --%>
																	<li class='model-edit-view-oper-content-item last-item' style="height: auto">
																		<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }</div>
																		<div class='item-content'>
																				<%-- 业务模块，无用--%>
																				<div id="_xform_listTabs_Form[!{index}].fdRelationMainField" _xform_type="select" style="display :none;">
																						<%-- 关联主模块字段--%>
																					<xform:select property="listTabs_Form[!{index}].fdRelationMainField" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdRelationMainField') }" style="width:95%">
																						<xform:beanDataSource serviceBean="modelingAppListviewModelDictService" selectBlock="fdId,fdName" whereBlock="fdAppModelId='${param.fdModelId }'"/>
																					</xform:select>
																					<span class="txtstrong">*</span>
																				</div>
																				<%-- 列表视图--%>
																			<div id="_xform_listTabs_Form[!{index}].fdListview">
																				<xform:select property="listTabs_Form[!{index}].fdMobileListViewId" required="true" onValueChange="onFdMobileListviewChange" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }" style="width:95%">
																				</xform:select>
																				<input name='listTabs_Form[!{index}].fdMobileTabId' type='hidden' value="${listTabs_FormItem.fdMobileTabId }"/>
																				<div id="_xform_listTabs_Form[!{index}].fdTabViews"> </div>
																				<input name='listTabs_Form[!{index}].fdListviewIncParams' type='hidden'/>
																				<div id="_xform_listTabs_Form[!{index}].fdListviewIncParams">
																				</div>
																			</div>
																			<%-- 链接--%>
																			<div id="_xform_listTabs_Form[!{index}].fdLink">
																				<xform:text property="listTabs_Form[!{index}].fdLinkParams"  style="width: 97%;height: 28px!important;" className="inputsgl"/>
																				<span class="txtstrong">*</span>
																				<div style="color: #999999;font-size: 12px;">${lfn:message('sys-modeling-base:modeling.system.link.only') }/sys/modeling/base/profile/nav/index.jsp</div>
																			</div>
																		</div>
																	</li>
																</ul>
															</div>
														</div>
													</td>
												</tr>
                                                    <c:forEach items="${modelingAppViewForm.listTabs_Form}" var="listTabs_FormItem" varStatus="vstatus">
                                                        <c:choose>
                                                            <c:when test="${listTabs_FormItem.fdTabType ne 0}">
                                                                <tr KMSS_IsContentRow="1" class="docListTr model-businessTag-wrap" style="display:none;">
                                                                    <td class="model-businessTag-content">
                                                                        <div class="model-edit-view-oper" data-lui-position='fdTag-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
                                                                            <input name="listTabs_Form[${vstatus.index}].fdOrder" value="${listTabs_FormItem.fdOrder}" type='hidden'/>
                                                                            <input name="listTabs_Form[${vstatus.index}].fdTabType" value="${listTabs_FormItem.fdTabType}" type='hidden'/>
                                                                            <input type="hidden" name="listTabs_Form[${vstatus.index}].fdApplicationId" value="${listTabs_FormItem.fdApplicationId}" data-model-name='${listTabs_FormItem.fdApplicationName}'/>
                                                                            <input type="hidden" name="listTabs_Form[${vstatus.index}].fdModelId" value="${listTabs_FormItem.fdModelId}" data-model-name='${listTabs_FormItem.fdModelName}'/>
                                                                            <input type="hidden" name="listTabs_Form[${vstatus.index}].fdName" value="${listTabs_FormItem.fdName}" />
                                                                            <div class="model-businessTag-content-power buildin model-edit-view-oper-head">
                                                                                <div class="content-power-left" style="min-width: 107px;">
                                                                                    <p>${listTabs_FormItem.fdName}</p>
                                                                                    <span>${lfn:message('sys-modeling-base:modelingAppViewtab.buildIn')}</span>
                                                                                </div>
                                                                                <div class="content-power-right">
                                                                                    <ui:switch property="listTabs_Form[${vstatus.index}].fdIsShow" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:view.show')}：" onValueChange="IsOpen(this,'fdIsOpen')"></ui:switch>
<%--																						<ui:switch property="listTabs_Form[${vstatus.index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}："></ui:switch>--%>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <tr KMSS_IsContentRow="1" class="docListTr">
                                                                    <td>
                                                                        <div class="model-edit-view-oper" data-lui-position='fdTag-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
                                                                            <div class="model-edit-view-oper-head">
                                                                                <input name="listTabs_Form[${vstatus.index}].fdOrder" value="${listTabs_FormItem.fdOrder}" type='hidden'/>
                                                                                <input name="listTabs_Form[${vstatus.index}].fdTabType" value="${listTabs_FormItem.fdTabType}" type='hidden'/>
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
                                                                                            <input type="hidden" name="listTabs_Form[${vstatus.index}].fdId" value="${listTabs_FormItem.fdId}" />
                                                                                            <div id="_xform_listTabs_Form[${vstatus.index}].fdName" _xform_type="text" style="width:100%">
                                                                                                <xform:text property="listTabs_Form[${vstatus.index}].fdName" showStatus="edit" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdName')}" required="true" validators="maxLength(18)" style="width:95%;height:28px!important;"  onValueChange="tagNameUpdate"/>
                                                                                            </div>
                                                                                        </div>
                                                                                    </li>
                                                                                        <%-- 默认展开 --%>
                                                                                    <li class='model-edit-view-oper-content-item' style="display:none;">
                                                                                        <ui:switch property="listTabs_Form[${vstatus.index}].fdIsOpen" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:modelingAppViewtab.fdIsOpen')}"></ui:switch>
                                                                                    </li>
                                                                                    <li class='model-edit-view-oper-content-item'>
                                                                                        <div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.fdType')}</div>
                                                                                        <div class='item-content'>
                                                                                                <%-- 类型--%>
                                                                                            <div id="_xform_listTabs_Form[${vstatus.index}].fdType" _xform_type="select" style="width:100%">
                                                                                                <div class="view_flag_radio" style="display: inline-block;">
                                                                                                    <input type="hidden" name="listTabs_Form[${vstatus.index}].fdType" value="${listTabs_FormItem.fdType}">
                                                                                                    <div class="view_flag_radio_yes" index="1" style="display: inline-block;cursor: pointer;"  onclick="changeTargetForm(this,1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_type.1') }</div>
																									<div class="view_flag_radio_no view_flag_last" index="2" style="display:inline-block;cursor: pointer;"  onclick="changeTargetForm(this,2)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:view.link') }</div>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </li>
																					<c:set var="isShowTabSourceLi" value="${listTabs_FormItem.fdType != 2}"></c:set>
                                                                                    <li class='model-edit-view-oper-content-item' id='tabSourceLi_index_${vstatus.index}' style="display :${isShowTabSourceLi ? 'block':'none'};">
                                                                                        <div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.targetForm') }</div>
                                                                                        <div class='item-content'>
                                                                                            <input type="hidden" name="listTabs_Form[${vstatus.index}].fdApplicationId" value="${listTabs_FormItem.fdApplicationId}" data-model-name='${listTabs_FormItem.fdApplicationName}' data-model-type="mobile"/>
                                                                                            <input type="hidden" name="listTabs_Form[${vstatus.index}].fdModelId" value="${listTabs_FormItem.fdModelId}" data-model-name='${listTabs_FormItem.fdModelName}' data-model-type="mobile"/>
                                                                                            <div class="item-content-element-choose" onclick="chooseTabSource(this,'${fdAppId }',true);">
                                                                                                <p class="item-content-element-label"></p>
                                                                                                <i></i>
                                                                                            </div>
                                                                                        </div>
                                                                                    </li>
                                                                                    <li class='model-edit-view-oper-content-item last-item' style="height: auto">
                                                                                        <div class='item-title'>${lfn:message('sys-modeling-base:modelingAppViewtab.showData') }</div>
                                                                                        <div class='item-content'>
                                                                                                <%-- 业务模块，无用--%>
                                                                                                <c:set var="isModelId" value="${listTabs_FormItem.fdType == 0}"></c:set>
                                                                                                <div id="_xform_listTabs_Form[${vstatus.index}].fdRelationMainField" _xform_type="select" style="display :${isModelId ? 'block':'none'};">
                                                                                                        <%-- 关联主模块字段--%>
                                                                                                    <xform:select property="listTabs_Form[${vstatus.index}].fdRelationMainField" validators="${isModelId ? 'required':''}" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdRelationMainField') }" style="width:95%">
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
																										"isMobile":"true"}
																									</script>
																									<span class="txtstrong">*</span>
																								</div>
                                                                                                <%--<xform:select property="listTabs_Form[${vstatus.index}].fdMobileListViewId" onValueChange="onFdMobileListviewChange" validators="${isListview ? 'required':''}" subject="${lfn:message('sys-modeling-base:modelingAppViewtab.fdListview') }" style="width:95%">
                                                                                                   <xform:beanDataSource serviceBean="modelingAppMobileListViewService" selectBlock="fdId,fdName" whereBlock="modelingAppMobileListView.fdModel='${listTabs_FormItem.fdModelId }'"/>
                                                                                                </xform:select>--%>
                                                                                                <input name='listTabs_Form[${vstatus.index}].fdMobileTabId' type='hidden' value="${listTabs_FormItem.fdMobileTabId }"/>
                                                                                                <div id="_xform_listTabs_Form[${vstatus.index}].fdTabViews"> </div>
                                                                                                <input name='listTabs_Form[${vstatus.index}].fdListviewIncParams' type='hidden' value="${listTabs_FormItem.fdListviewIncParams }"/>
                                                                                                <div id="_xform_listTabs_Form[${vstatus.index}].fdListviewIncParams">
                                                                                                </div>
                                                                                            </div>
																								<%-- 链接--%>
																							<c:set var="isLink" value="${listTabs_FormItem.fdType == 2}"></c:set>
																							<div id="_xform_listTabs_Form[${vstatus.index}].fdLink" style="display :${isLink ? 'block':'none'};">
																								<xform:text property="listTabs_Form[${vstatus.index}].fdLinkParams"  validators="${isLink ? 'urlCustomize urlLength':''}" style="width: 97%;height: 28px!important;" className="inputsgl"/>
																								<span class="txtstrong">*</span>
																								<div style="color: #999999;font-size: 12px;">${lfn:message('sys-modeling-base:modeling.system.link.only') }/sys/modeling/base/profile/nav/index.jsp</div>
																							</div>
                                                                                        </div>
                                                                                    </li>
                                                                                </ul>
                                                                            </div>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
											</table>
											<div class="model-data-create" onclick="DocList_AddRow_Custom_Tabs('TABLE_DocList_listTabs_Form');">
												<div>${lfn:message('sys-modeling-base:button.add') }</div>
											</div>
											<input type="hidden" name="listTabs_Flag" value="1">
											<script>
												DocList_Info.push('TABLE_DocList_listTabs_Form');
											</script>
										</td>
									</tr>

									<!-- 入参查询条件 -->
					            </table>
					        </div>
					    </center>
					    </div>
					    <html:hidden property="fdId" />
					    <html:hidden property="fdMobile" />
					    <html:hidden property="method_GET" />
					</html:form>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 预定义查询相关 start -->
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
		isPcAndMobile:'${param.isPcAndMobile}'
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
	Com_IncludeFile("jquery.dragsort.js", Com_Parameter.ContextPath+'sys/modeling/base/resources/js/', 'js', true);
       
       var lastSelectPostionObj;
       var lastSelectPostionDirect;
     	//隐藏表格最后一行的向下移动按钮
   	$(document).on('detaillist-init',function(e){
   		var table = e.target;
   		$(table).find(">tbody>tr").last().find("div.down").css("display","none");
   	});
	//---------------------封装数据-pcandmobile使用
	function packageData(callBack){
			if (validateDetail() && validateLabel() && validateTabNameOverSize()) {
			submit_packageData()
			callBack()
		}
	}
	function viewSubmit(flag){
		if(validateDetail() && validateTabNameOverSize()){
			submit_custom(flag);
		}
	}
       seajs.use([ 'lui/topic', 'lui/jquery',"sys/modeling/base/formlog/res/mark/viewFMMark"]
			   ,function(topic,$,viewFMMark){
		   function onResizeFitWindow() {
			   var height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
			   if (param.isInDialog) {
				   $('.model-body-content-desc:eq(0)').hide();
				   height = $(".lui_dialog_content", $(parent.parent.document)).height();
			   }
			   if (param.isPcAndMobile) {
				   //Pc+移动
				   height = $(parent.document).find(".modeling-pam-content-frame").eq(0).outerHeight(true);
				   $(".model-edit-right-wrap .model-edit-view-content").height(height-80);
				   $(".model-edit-left-wrap .model-edit-view-content").height(height-60);
				   $('.model-body-content-desc:eq(0)').hide();
				   $("[lui-pam-hidden='true']").hide();
				   $(".model-edit-view-title").hide();
				   $(".model-edit-left").addClass("model-edit-left-over");
				   $(".model-edit-left").css({"padding-top":0})
				   $(".model-edit-right").css({"padding-top":0,"border-top":"1px solid #DDDDDD","border-bottom":"1px solid #DDDDDD"})
			   }else{
				   $("body", parent.document).find('#trigger_iframe').height(height);
				   $(".model-edit-right-wrap .model-edit-view-content").height(height - 80);
				   $(".model-edit-left .model-body-content-wrap").height(height - 60);
				   $("body", parent.document).css("overflow", "hidden");
			   }

		   }

		   $(window).resize(function () {
			   onResizeFitWindow();
		   });

		   //预览加载完毕事件
		   topic.subscribe('preview_load_finish', function (ctx) {
			   onResizeFitWindow();

			   $(".model-edit-left div.model-body-content-phone").eq(0).on('click', function () {
				   $("[data-lui-position]").removeClass("active");
				   return false;
			   })

			   //#132181:业务标签（传阅记录和沉淀记录）和业务操作之间的联动
			   var tabDataShow = showTabByOper();
			   showOrHideTab(tabDataShow);
		   });

		   //预览更新事件
		   topic.subscribe('preview.refresh', function () {
			   try {
				   //刷新预览
				   LUI("view_preview").setSourceData(getViewData);
				   LUI("view_preview").reRender();
				   //恢复选择的位置
				   if (lastSelectPostionObj && lastSelectPostionDirect) {
					   switchSelectPosition(lastSelectPostionObj, lastSelectPostionDirect);
				   }
			   } catch (err) {
			   }
		   })
				   //表单映射
				   var viewFMMark_mobile = new viewFMMark.ViewFMMark({fdId:"${param.fdId}",isMobile:true});
				   viewFMMark_mobile.startup();
   	});
       Com_AddEventListener(window,'load',function(){
    	 var num = setInterval(function(){
   			//初始化编辑页面预览
   			if(LUI("view_preview")){
   				clearInterval(num);
   				LUI("view_preview").setSourceData(getViewData);
   				LUI("view_preview").reRender();
   				//恢复选择的位置
   				if(lastSelectPostionObj && lastSelectPostionDirect){
   					switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect);
   				}
   			}
   		}, 200);
            //内置标签的宽度
           var buildInWidth = $(".model-businessTag-content").closest("tr").width();
           $(".model-businessTag-content").width(buildInWidth);

		   //业务标签展示类型
		   $(".view_flag_radio").find("[name*='fdType']").each(function(){
			   var radioObj = $(this).parents(".view_flag_radio")[0];
			   if($(this).val() == 0){
				   $(radioObj).find("[index='0'] i").addClass("view_flag_yes");
				   $(radioObj).find("[index='1'] i").removeClass("view_flag_yes");
				   $(radioObj).find("[index='2'] i").removeClass("view_flag_yes");
			   }else if($(this).val() == 1){
				   $(radioObj).find("[index='1'] i").addClass("view_flag_yes");
				   $(radioObj).find("[index='0'] i").removeClass("view_flag_yes");
				   $(radioObj).find("[index='2'] i").removeClass("view_flag_yes");
			   }else if($(this).val() == 2){
				   //修改标题
				   var name = $(this).attr('name');
				   var index = name.substring(name.indexOf('[')+1,name.indexOf(']'));
				   var fdLinkDiv = $(this).closest(".model-edit-view-oper-content").find("#_xform_listTabs_Form\\[" + index + "\\]\\.fdLink");
				   var itemTitle = fdLinkDiv.parent('.item-content').parent(".model-edit-view-oper-content-item").find('.item-title');
				   itemTitle.text("${lfn:message('sys-modeling-base:listview.use.new.version') }");

				   $(radioObj).find("[index='2'] i").addClass("view_flag_yes");
				   $(radioObj).find("[index='0'] i").removeClass("view_flag_yes");
				   $(radioObj).find("[index='1'] i").removeClass("view_flag_yes");
			   }
		   })

    	 //展现内置标签---访问统计
		   $(".model-businessTag-wrap").each(function(){
				var tabName = $(this).find("[name*='fdName']").val();
			   if(tabName === "访问统计"){
			   	$(this).css("display","block");
			   }
		   })
		   //业务操作选择传阅，业务标签显示传阅记录
		   var tabDataShow = showTabByOper();
		   showOrHideTab(tabDataShow);

		   // 业务标签拖动排序
		   $("#TABLE_DocList_listTabs_Form").dragsort({
			   dragSelector: "tr",
			   dragSelectorExclude: ".model-edit-view-oper-content ul",
			   dragEnd: dragEnd,
			   dragBetween: true,
			   placeHolderTemplate: "<tr></tr>"
		   });
		   function dragEnd() {
			   $("#TABLE_DocList_listTabs_Form tr").each(function () {
				   DocListFunc_RefreshIndex(DocList_TableInfo["TABLE_DocList_listTabs_Form"],$(this).index());
			   });

		   }
		   //业务标签悬浮图标
		   $(".buildin").on("mouseenter", function () {
			   $(this).addClass("drag");
		   }).on("mouseleave", function () {
			   $(this).removeClass("drag");
		   });
       })

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
		fdTypeChange(value,document.getElementsByName(curName));
	}
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
</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>