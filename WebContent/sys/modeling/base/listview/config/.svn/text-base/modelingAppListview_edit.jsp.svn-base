<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.modeling.base.util.ListviewEnumUtil"%>
<%@page import="com.landray.kmss.sys.modeling.base.forms.ModelingAppListviewForm"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<script type="text/javascript">
Com_IncludeFile("select.js");
Com_IncludeFile("dialog.js");
Com_IncludeFile("form.js");
Com_IncludeFile("formula.js");
Com_IncludeFile("doclist.js");
Com_IncludeFile("view.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
Com_IncludeFile("dialog.css","${LUI_ContextPath}/sys/modeling/base/resources/css/","css",true);
function submitForm(method){
	Listview_Submit(document.modelingAppListviewForm, method);
}
function packageData(callBack){
	Listview_PackageData(document.modelingAppListviewForm,"update",callBack);
}
</script>
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/query.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/showFilters.css?s_cache=${LUI_Cache}" />
<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css?s_cache=${LUI_Cache}" />
<style>
body{
	overflow: hidden;
}
.fdViewFlag:first-child{
	margin-left:0;
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
.inputselectsgl input{
	padding-left:8px!important;
}
.model-edit-view-oper-content-item div.operation>div.inputselectsgl{
	height: 28px!important;
}
.model-edit-view-oper-content-item div.operation>div.inputselectsgl input{
	height: 28px!important;
}
.liHidden{
	display:none;
}

@media (-webkit-min-device-pixel-ratio: 1.5) {
    .tb_simple .inputselectsgl {
    border: 1.5px solid #dfdfdf;
    }
    
	.model-edit-right .inputselectsgl.active {
    border: 1.5px solid #4285f4;
    }
    
    .model-edit-view-oper {
    border: 1.5px solid #DFE3E9;
    }
    
	.model-edit-view-oper.active {
	border: 1.5px solid #4285f4;
	}
}
</style>
<div class="model-body-content" id="editContent_pc">
	<!-- 这里放入你的组件 starts -->
	<div class="model-edit">
		<div class="model-edit-left">
			<div data-lui-type="sys/modeling/base/resources/js/preview/preview!Preview" id="view_preview" style="display: none">
		        <div data-lui-type="sys/modeling/base/resources/js/preview/previewSource!PreviewSource"></div>
		        <div data-lui-type="lui/view/render!Template" style="display:none;">
		            <script type="text/config">
 						{
							src : '/sys/modeling/base/resources/js/preview/listview_pc.html#'
						}
                	</script>
		        </div>
		    </div>
		</div>
		<div class="model-edit-right">
			<div class="model-edit-right-wrap">
				<div class="model-edit-view-title">
					<p>${lfn:message('sys-modeling-base:listview.view.configuration') }</p>
					<c:if test="${modelingAppListviewForm.method_GET=='edit' || modelingAppListviewForm.method_GET=='editTemplate'}">
						 <div onclick="submitForm('update');"><bean:message key="button.update"/></div>
					</c:if>
					<c:if test="${modelingAppListviewForm.method_GET=='add'}">
						<div onclick="submitForm('save');"><bean:message key="button.save"/></div>
					</c:if>
				</div>
				<div class="model-edit-view-content">
					<div class="model-edit-view-content-wrap">
						<html:form action="/sys/modeling/base/modelingAppListview.do" onsubmit="return validateModelingAppListviewForm(this);">
							<div style="height: 100%;box-sizing: border-box;">
							<center>
							<html:hidden property="fdId"/>
							<html:hidden property="fdModelName"/>
							<table class="tb_simple model-view-panel-table" width="100%">
								<tr lui-pam-hidden="${param.isPcAndMobile}">
									<td class="td_normal_title">
										<bean:message  bundle="sys-modeling-base" key="modelingAppListview.fdName"/>
									</td>
								</tr>
								<tr lui-pam-hidden="${param.isPcAndMobile}">
									<td class="model-view-panel-table-td">
										<div id="_xform_fdName" _xform_type="text">
							                <input name="fdName" subject="${lfn:message('sys-modeling-base:listview.view.name') }" class="inputsgl" value="${modelingAppListviewForm.fdName}" type="text" validate="required maxLength(200)" style="width:97%;"><span class="txtstrong">*</span>
							            </div>
									</td>
								</tr>
								<tr style="display: none">
									<td class="td_normal_title">
										<bean:message bundle="sys-modeling-base" key="modelingAppListview.fdModel"/>
									</td>
								</tr>
								<tr style="display:none;">
									<td class="model-view-panel-table-td">
										<xform:select onValueChange="modelChange" value="${param.fdModelId }" property="fdModelId" required="true" subject="${lfn:message('sys-modeling-base:modelingAppListview.fdModel') }">
											<xform:beanDataSource serviceBean="modelingAppModelService" whereBlock="modelingAppModel.fdApplication='${fdAppId }'"></xform:beanDataSource>
										</xform:select>
									</td>
								</tr>
								<%-- 业务操作 --%>
								<tr>
									<td class="td_normal_title" data-lui-position='fdOperation'>
										<bean:message  bundle="sys-modeling-base" key="modelingAppListview.listOperation"/>
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td">
										<div class="model-opt-panel-table-base model-panel-table-base" style="margin-bottom:0px;" id="_xform_listOperationIds" _xform_type="dialog">
											<c:set var="listOperationNames" value="${modelingAppListviewForm.listOperationNames }"></c:set>
											<c:set var="listOperationIds" value="${modelingAppListviewForm.listOperationIds }"></c:set>
											<%
												//解析names
												String listOperationNames = (String)pageContext.getAttribute("listOperationNames");
												String listOperationIds = (String)pageContext.getAttribute("listOperationIds");
												if(StringUtil.isNotNull(listOperationIds)){
													String[] listOperationNameArr = listOperationNames.split(";");
													String[] listOperationIdArr = listOperationIds.split(";");
													pageContext.setAttribute("listOperationNameArr", listOperationNameArr);
													pageContext.setAttribute("listOperationIdArr", listOperationIdArr);
												}
											%>
											<table id="operationTable" class="tb_simple model-edit-view-oper-content-table" width="100%">
												<%-- 基准行，KMSS_IsReferRow = 1 --%>
												<tr  style="display:none;" KMSS_IsReferRow="1" class="operationTr">
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
																		onclick="xform_main_data_beforeDelOperationRow();updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
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
															<div class="model-edit-view-oper-content listview-content">
																<div class='model-edit-view-oper-content-item first-item last-item'>
																	<div class='item-title'>${ lfn:message('sys-modeling-base:modelingAppListview.operationButton') }</div>
																	<div class='item-content'>
																		<!-- <p onclick="xform_main_data_operationDialog()"  class="highLight listOperationName label">&nbsp;</p> -->
																		<%-- 操作--%>
									                                    <div class='operation' id="_xform_listOpers_Form[!{index}].fdOperationId" _xform_type="dialog" style="width:100%">
									                                        <xform:dialog style="width:100%;vertical-align: middle;" subject="${ lfn:message('sys-modeling-base:modelingAppListview.operationButton') }" propertyId="listOperationIdArr[!{index}]" propertyName="listOperationNameArr[!{index}]" showStatus="edit" validators=" required">
									                                            xform_main_data_operationDialog()
									                                        </xform:dialog>
									                                    </div>
																	</div>
																</div>
																<!-- <input type="hidden" name="listOperationIdArr[!{index}]">
																<input type="hidden" name="listOperationNameArr[!{index}]"> -->
															</div>
														</div>
													</td>
												</tr>
												<%-- 内容行 --%>
												<c:forEach items="${listOperationNameArr}" var="listOperationName" varStatus="vstatus">
												<tr KMSS_IsContentRow="1" class="operationTr">
													<td>
														<div class="model-edit-view-oper" data-lui-position='fdOperation-${vstatus.index}' onclick="switchSelectPosition(this,'right')">
															<div class="model-edit-view-oper-head">
																<div class="model-edit-view-oper-head-title">
																	<div onclick="changeToOpenOrClose(this)">
																		<i class="open"></i>
																	</div>
																	<span>${listOperationName }</span>
																</div>
																<div class="model-edit-view-oper-head-item " style="padding-top:0px;">
																	<div class="del"
																		onclick="xform_main_data_beforeDelOperationRow();updateRowAttr(0);DocList_DeleteRow();updateRowAttr();">
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
															<div class="model-edit-view-oper-content listview-content">
																<div class='model-edit-view-oper-content-item first-item last-item'>
																	<div class='item-title'>${lfn:message('sys-modeling-base:modelingAppListview.operationButton') }</div>
																	<div class='item-content'>
																		<%-- 操作--%>
									                                    <div class='operation' id="_xform_listOpers_Form[!{index}].fdOperationId" _xform_type="dialog" style="width:100%">
									                                        <xform:dialog style="width:100%;vertical-align: middle;" propertyId="listOperationIdArr[${vstatus.index}]" propertyName="listOperationNameArr[${vstatus.index}]" idValue="${listOperationIdArr[vstatus.index] }" nameValue="${listOperationName }" showStatus="edit" validators=" required">
									                                            xform_main_data_operationDialog()
									                                        </xform:dialog>
									                                    </div>
																	</div>
																</div>
															</div>
														</div>
													</td>
												</tr>
												</c:forEach>
											</table>
											<input name="listOperationIds" type="hidden" value="${modelingAppListviewForm.listOperationIds }">
											<input name="listOperationNames" type="hidden" value="${modelingAppListviewForm.listOperationNames }">
											<input name="listOperationIds_last" type="hidden" value="${modelingAppListviewForm.listOperationIds }">
											<input name="listOperationNames_last" type="hidden" value="${modelingAppListviewForm.listOperationNames }">
											<input name="fdOperOrder" type="hidden" value="${modelingAppListviewForm.fdOperOrder }">
											<input name="fdOperNameOrder" type="hidden" value="${modelingAppListviewForm.fdOperNameOrder }">
										</div>
										<div class="model-data-create" onclick="xform_main_data_addOperation('operationTable');">
											<div>${lfn:message('sys-modeling-base:button.add') }</div>
										</div>
									</td>
								</tr>
								<%-- 显示项 --%>
								<tr>
									<td class="td_normal_title" data-lui-position='fdDisplay'>
										<bean:message  bundle="sys-modeling-base" key="modelingAppListview.fdDisplay"/>
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td height28">
										<input type="hidden" name="fdDisplay"/>
										<xform:dialog propertyName="fdDisplayText" htmlElementProperties="placeholder='${lfn:message('sys-modeling-base:modeling.page.choose') }' data-lui-position='fdDisplay'" propertyId="fd_display" dialogJs="selectDisplay(this);"></xform:dialog>
									</td>
								</tr>
								<tr id="fdDisplayCssSetTr">
									<td class="td_normal_title td_display_add">
										<span class="displaycss td_normal_title" data-lui-position='fdDisplayCss'>${lfn:message('sys-modeling-base:modelingAppListview.fdDisplayCssSet') }</span>
										<span class="displaycss_create model_select_selected" style="float:right;padding-bottom: 8px;">${lfn:message('sys-modeling-base:button.add') }
											<div class="displaycss_create_pop">
	                                        </div>
										</span>
										
									</td>
								</tr>
								
								<%-- 搜索项 --%>
								<tr>
									<td class="td_normal_title" data-lui-position='fdCondition'>
										<bean:message  bundle="sys-modeling-base" key="modelingAppListview.fdCondition"/>
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td height28">
										<input type="hidden" name="fdCondition"/>
										<xform:dialog propertyName="fdConditionText" htmlElementProperties="placeholder='请选择' data-lui-position='fdCondition'" propertyId="fd_condition" dialogJs="selectCondition(this);"></xform:dialog>
									</td>
								</tr>
								<%-- 排序项 --%>
								<tr>
									<td class="td_normal_title" data-lui-position='fdOrderBy'>
										<bean:message bundle="sys-modeling-base" key="modelingAppListview.fdOrderBy"/>
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td">
										<input type="hidden" name="fdOrderBy"/>
										<div class="model-panel-table-base" style="margin-bottom:0">
											<table id="xform_main_data_orderbyTable" class="tb_simple model-edit-view-oper-content-table" style="width:100%;">
											</table>
										</div>
										<div class="model-data-create" onclick="xform_main_data_addOrderbyItem();">
											<div>${lfn:message('sys-modeling-base:button.add') }</div>
										</div>
									</td>
								</tr>
								<%-- 预定义查询 --%>
								<tr>
									<td class="td_normal_title" data-lui-position='fdWhereBlock'>
										<bean:message bundle="sys-modeling-base" key="modelingAppListview.fdWhereBlock"/>
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td">
										<div class="model-query">
									        <div class="model-query-wrap">
									            <div class="model-query-tab">
									                <ul class="clearfix">
									                    <li class="active"><span>${lfn:message('sys-modeling-base:modeling.custom.query') }</span></li>
									                    <li><span>${lfn:message('sys-modeling-base:modeling.builtIn.query') }</span></li>
									                </ul>
									            </div>
									            <div class="model-query-content">
									                <!-- 自定义查询 -->
									                <div class="model-query-content-cust model-query-cont">
									                    <div class="model-query-content-rule clearfix">
									                        <p>${lfn:message('sys-modeling-base:listview.query.rules') }</p>
									                        <div>
									                           <label>
									                           		<input type="radio" value="0" name="fdWhereType" 
									                           			<c:if test="${modelingAppListviewForm.fdWhereType eq '0' or empty modelingAppListviewForm.fdWhereType }">checked</c:if>/>
									                           			${lfn:message('sys-modeling-base:relation.meet.all.conditions') }
									                           </label>
															   <label>
															   		<input type="radio" value="1" name="fdWhereType" 
															   			<c:if test="${modelingAppListviewForm.fdWhereType eq '1'}">checked</c:if> />
															   			${lfn:message('sys-modeling-base:relation.meet.any.conditions') }
															   </label>
									                        </div>
									                    </div>
									                    <div class="model-query-content-add">
									                    	<input type="hidden" name="fdWhereBlock"/>
															<div class="model-panel-table-base" style="margin-bottom:0">
																<table id="xform_main_data_whereTable" class="tb_simple model-edit-view-oper-content-table" style="width:100%;">
																</table>
															</div>
									                    </div>
									                    <div class="model-data-create" onclick="xform_main_data_addWhereItem(null, this, '0');">
																<div>${lfn:message('sys-modeling-base:button.add') }</div>
														</div>
									                </div>
									                <!-- 内置查询 -->
									                <div class="model-query-content-sys model-query-cont">
									                    <div class="model-query-content-rule clearfix">
									                        <p>${lfn:message('sys-modeling-base:listview.query.rules') }</p>
															<div>
																<label style="margin-left:20px;">
																	<input type="radio" value="0" name="fdInWhereType"
																		   <c:if test="${modelingAppListviewForm.fdInWhereType eq '0' or empty modelingAppListviewForm.fdInWhereType }">checked</c:if>/>
																	${lfn:message('sys-modeling-base:relation.meet.all.conditions') }
																</label>
																<label style="margin-left:20px;">
																	<input type="radio" value="1" name="fdInWhereType"
																		   <c:if test="${modelingAppListviewForm.fdInWhereType eq '1'}">checked</c:if> />
																	${lfn:message('sys-modeling-base:relation.meet.any.conditions') }
																</label>
															</div>
									                    </div>
									                    <div class="model-query-content-add">
															<div class="model-panel-table-base" style="margin-bottom:0">
																<table id="xform_main_data_whereTable2" class="tb_simple model-edit-view-oper-content-table" style="width:100%;">
																</table>
															</div>
									                    </div>
									                    <div class="model-data-create" onclick="xform_main_data_addWhereItem(null,this,'1');">
																<div>${lfn:message('sys-modeling-base:button.add') }</div>
														</div>
									                </div>
									            </div>
									        </div>
									    </div>
								    </td>
								</tr>
								
								<!-- 列表业务穿透 -->
								<tr>
									<td class="td_normal_title">
										${lfn:message('sys-modeling-base:sysModelingRelation.fdIsThrough') }
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td">
							            <input type="hidden" name="fdViewFlag" value="${modelingAppListviewForm.fdViewFlag }">
							            <div class="view_flag_radio" style="display: inline-block;">
							            	<div class="view_flag_radio_yes" style="display: inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdViewFlag',1,'fdViewId')"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1') }</div>
							            	<div class="view_flag_radio_no view_flag_last" style="display:inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdViewFlag',0,'fdViewId')"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0') }</div>
							            </div>
								        <span style="margin-right:20px"></span>
							            <xform:select property="fdViewId" showPleaseSelect="false" style="width: 60%">
							            	<xform:simpleDataSource value="">${lfn:message('sys-modeling-base:listview.default.view') }</xform:simpleDataSource>
							                <xform:beanDataSource serviceBean="modelingAppViewService" selectBlock="fdId,fdName" whereBlock="fdModel.fdId='${param.fdModelId }' and fdMobile=0">
							                </xform:beanDataSource>
							            </xform:select>
									</td>
								</tr>
								<!-- 可使用者 -->

								<tr lui-pam-hidden="${param.isPcAndMobile}">
									<td class="td_normal_title">
										<bean:message bundle="sys-modeling-base" key="modelingAppListview.authSearchReaders"/>
									</td>
								</tr>
								<tr lui-pam-hidden="${param.isPcAndMobile}">
									<td class="model-view-panel-table-td height28">
										<xform:dialog propertyName="authSearchReaderNames" htmlElementProperties="placeholder='${lfn:message('sys-modeling-base:listview.select.fdAuthReaders') }' data-lui-position='authSearchReaderIds'" propertyId="authSearchReaderIds" dialogJs="Dialog_Address(true, 'authSearchReaderIds','authSearchReaderNames', ';', ORG_TYPE_ALL);"></xform:dialog>
										<%-- 为空则所有用户都可使用 --%>
										<bean:message bundle="sys-modeling-base" key="modelingAppListview.allUsersCanUse"/>
									</td>
								</tr>
								<!-- 开启权限过滤 -->
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="sys-modeling-base" key="modelingAppListview.fdAuthEnabled"/>
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td">
										<input type="hidden" name="fdAuthEnabled" value="${modelingAppListviewForm.fdAuthEnabled }">
										<div class="view_flag_radio" style="display: inline-block;">
											<div class="view_flag_radio_yes" style="display: inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdAuthEnabled',1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.1') }</div>
											<div class="view_flag_radio_no view_flag_last" style="display:inline-block;cursor: pointer;"  onclick="changeFlag(this,'fdAuthEnabled',0)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:enums.viewtab_fdIsOpen.0') }</div>
										</div>
										<div><span style="color: red;"><bean:message bundle="sys-modeling-base" key="modelingAppListview.fdAuthEnabled.hit"/></span></div>
									</td>
								</tr>
								<tr>
									<td data-lui-position="fdPagination" class="td_normal_title"  onclick='switchSelectPosition(this,"right")'>
										<div>${lfn:message('sys-modeling-base:listview.pager.configuration') }</div>
										<div id="paginationPosition"></div>
									</td>
								</tr>
								<tr>
									<td class="model-view-panel-table-td">
										<input type="hidden" name="fdPageSetting" value="${modelingAppListviewForm.fdPageSetting }">
										<div class="view_flag_radio" style="display: inline-block;">
											<div id="defalutPagination" class="view_flag_radio_yes" style="display: inline-block;cursor: pointer;"  onclick="changePageSetting(this,'fdPageSetting',1)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:listview.default.pagination') }</div>
											<div id="simplePagination" class="view_flag_radio_no view_flag_last" style="display:inline-block;cursor: pointer;"  onclick="changePageSetting(this,'fdPageSetting',2)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:listview.simple.pagination') }</div>
											<div id="changePagination" class="view_flag_radio_last view_flag_last" style="display:inline-block;cursor: pointer;"  onclick="changePageSetting(this,'fdPageSetting',3)"><i class="view_flag_no"></i>${lfn:message('sys-modeling-base:listview.allow.switc.tabs') }</div>
										</div>
										<ui:popup style="background:white;overflow-x:hidden;" triggerObject="#defalutPagination" triggerEvent="mouseover" align="down-right" borderWidth="5" positionObject="#paginationPosition">
											<img alt="" src="${LUI_ContextPath}/sys/modeling/base/resources/images/listview/paging_setting_default.png"/>
										</ui:popup>
										<ui:popup style="background:white;overflow-x:hidden;" triggerObject="#simplePagination" triggerEvent="mouseover" align="down-right" borderWidth="5" positionObject="#paginationPosition">
											<img alt="" src="${LUI_ContextPath}/sys/modeling/base/resources/images/listview/paging_setting_simple.png"/>
										</ui:popup>
										<ui:popup style="background:white;overflow-x:hidden;" triggerObject="#changePagination" triggerEvent="mouseover" align="down-right" borderWidth="5" positionObject="#paginationPosition">
											<img alt="" src="${LUI_ContextPath}/sys/modeling/base/resources/images/listview/paging_setting_can_change.png"/>
										</ui:popup>
									</td>
								</tr>
							</table>
							</center>
							</div>
							<input type="hidden" name="fdDisplayCssSet" />
							<html:hidden property="method_GET"/>
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
	ModelingAppListviewForm modelingAppListviewForm = (ModelingAppListviewForm) request.getAttribute("modelingAppListviewForm");
	if(modelingAppListviewForm != null){
		String fdWhereBlock = modelingAppListviewForm.getFdWhereBlock();
		if (StringUtil.isNotNull(fdWhereBlock)) {
			//将json的value中的"替换为\"
			fdWhereBlock = fdWhereBlock.replaceAll("\\\\\"","\\\\\\\\\"");
			pageContext.setAttribute("fdWhereBlock",fdWhereBlock);
		}
	}
%>
<script>
	DocList_Info.push('operationTable');
	var _main_data_insystem_enumCollection =
<%=enumString%>
	;
	var listviewOption = {
		param : {
			fdId : "${param.fdId}",
			fdAppId : "${fdAppId}",
			fdModelId:"${param.fdModelId }",
			method:"${method_GET}",
			contextPath:Com_Parameter.ContextPath,
			isInDialog: '${param.isInDialog}',
			isPcAndMobile:'${param.isPcAndMobile}'
		},
		modelingAppListviewForm : {
			fdModelName : '${modelingAppListviewForm.fdModelName}',
			modelDict : '${modelDict}',
			fdWhereBlock : '${fdWhereBlock}',
			fdDisplay : '${modelingAppListviewForm.fdDisplay}',
			fdDisplayCssSet : '${modelingAppListviewForm.fdDisplayCssSet}',
			fdCondition : '${modelingAppListviewForm.fdCondition}',
			fdOrderBy : '${modelingAppListviewForm.fdOrderBy}',
			fdEnableFlow : '${modelingAppListviewForm.fdEnableFlow}',
			allField : '${allField}'
		},
		baseInfo:{
			modelDict:'${modelDict}'
		},
		dialogs : {
			sys_modeling_operation_selectListviewOperation : {
				modelName : 'com.landray.kmss.sys.modeling.base.model.SysModelingOperation',
				sourceUrl : '/sys/modeling/base/sysModelingOperationData.do?method=selectListviewOperation&fdAppModelId=${param.fdModelId }'
			}
		},
		lang : {
			alreadyToDown : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.alreadyToDown')}",
			alreadyToUp : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.alreadyToUp')}",
			fdKeyWaring : "${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.fdKeyWaring')}",
			chooseModuleFirst : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.chooseModuleFirst')}",
			docCategory : "${lfn:message('sys-xform-maindata:sysFormJdbcDataSet.docCategory')}",
			searchPreview : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.searchPreview')}",
			systemData : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.systemData')}",
			lookLog : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.lookLog')}",
			fdOrderBy : "${lfn:message('sys-modeling-base:modelingAppListview.fdOrderBy')}",
			fdWhereBlock : "${lfn:message('sys-modeling-base:modelingAppListview.fdWhereBlock')}",
			cantHaveRepeatItem : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.cantHaveRepeatItem')}",
			returnValCantBeNull : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.returnValCantBeNull')}",
			deleteIt : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.delete')}",
			up : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.up')}",
			down : "${lfn:message('sys-xform-maindata:sysFormMainDataInsystem.down')}",
			asc : "${lfn:message('sys-modeling-base:modelingAppListview.fdOrderType.asc')}",
			desc : "${lfn:message('sys-modeling-base:modelingAppListview.fdOrderType.desc')}",
			selectDisplay : "${lfn:message('button.select')}${lfn:message('sys-modeling-base:modelingAppListview.fdDisplay')}",
			field : "${lfn:message('button.select')}${lfn:message('sys-modeling-base:relation.field')}",
			fdOutSort : "${lfn:message('button.select')}${lfn:message('sys-modeling-base:modelingPortletCfg.fdOutSort')}",
			selectCondition : "${lfn:message('button.select')}${lfn:message('sys-modeling-base:modelingAppListview.fdCondition')}",
			fdOperator : "${lfn:message('button.select')}${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator')}",
			fdValue : "${lfn:message('button.select')}${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue')}"
		}
	};
	window.top.xformId = '${xformId}';
	Com_IncludeFile("listview_edit_script.js", Com_Parameter.ContextPath
			+ 'sys/modeling/base/resources/js/', 'js', true);
	Com_IncludeFile("listview_context.js", Com_Parameter.ContextPath
			+ 'sys/modeling/base/resources/js/', 'js', true);
	Com_IncludeFile("listview_edit.js", Com_Parameter.ContextPath
			+ 'sys/modeling/base/resources/js/', 'js', true);
	Com_IncludeFile("view_common.js", Com_Parameter.ContextPath
			+ 'sys/modeling/base/resources/js/', 'js', true);
	Com_IncludeFile('calendar.js');
	Com_IncludeFile("selectPanel.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", "js", true);
	//修改部分样式
	$("[name='fdDisplayText']").css("height","100%");
	$("[name='fdConditionText']").css("height","100%");
	$("[name='fdViewId']").css("height","100%");
	//初始化
	var lastSelectPostionObj;
    var lastSelectPostionDirect;
    var lastSelectPosition;
  	//隐藏表格最后一行的向下移动按钮
	$(document).on('detaillist-init',function(e){
		var table = e.target;
		$(table).find(">tbody>tr").last().find("div.down").css("display","none");
	});
	Com_AddEventListener(window,"load",function(){
		//是否穿透
		var fdViewFlag = $("[name='fdViewFlag']");
		var value = fdViewFlag.val();
		if(value === 0 || value === "0" || value === "false"){
			fdViewFlag.nextAll().find(".view_flag_radio_no i").addClass("view_flag_yes");
			fdViewFlag.nextAll().find(".view_flag_radio_yes i").removeClass("view_flag_yes");
			$("[name='fdViewId']").hide();
		}else{
			fdViewFlag.nextAll().find(".view_flag_radio_no i").removeClass("view_flag_yes");
			fdViewFlag.nextAll().find(".view_flag_radio_yes i").addClass("view_flag_yes");
		}
		//分页器设置
		var fdPageSetting = $("[name='fdPageSetting']");
		value = fdPageSetting.val();
		if(value === 3 || value === "3"){
			fdPageSetting.nextAll().find(".view_flag_radio_last i").addClass("view_flag_yes");
			fdPageSetting.nextAll().find(".view_flag_radio_yes i").removeClass("view_flag_yes");
			fdPageSetting.nextAll().find(".view_flag_radio_no i").removeClass("view_flag_yes");
		}else if(value === 2 || value === "2"){
			fdPageSetting.nextAll().find(".view_flag_radio_yes i").removeClass("view_flag_yes");
			fdPageSetting.nextAll().find(".view_flag_radio_last i").removeClass("view_flag_yes");
			fdPageSetting.nextAll().find(".view_flag_radio_no i").addClass("view_flag_yes");
		}else{
			fdPageSetting.nextAll().find(".view_flag_radio_yes i").addClass("view_flag_yes");
			fdPageSetting.nextAll().find(".view_flag_radio_no i").removeClass("view_flag_yes");
			fdPageSetting.nextAll().find(".view_flag_radio_last i").removeClass("view_flag_yes");
		}
		//开启权限过滤
		var fdAuthEnabled = $("[name='fdAuthEnabled']");
		value = fdAuthEnabled.val();
		if(value === 0 || value === "0" || value === "false"){
			fdAuthEnabled.nextAll().find(".view_flag_radio_no i").addClass("view_flag_yes");
			fdAuthEnabled.nextAll().find(".view_flag_radio_yes i").removeClass("view_flag_yes");
		}else{
			fdAuthEnabled.nextAll().find(".view_flag_radio_no i").removeClass("view_flag_yes");
			fdAuthEnabled.nextAll().find(".view_flag_radio_yes i").addClass("view_flag_yes");
		}
		var num = setInterval(function(){
			//初始化编辑页面预览
			if(LUI("view_preview")){
				clearInterval(num);
				LUI("view_preview").setSourceData(getListViewData);
				LUI("view_preview").reRender();
				//恢复选择的位置
				if(lastSelectPostionObj && lastSelectPostionDirect){
					switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect,lastSelectPosition);
				}
			}
		}, 200);
	});
	//--------------公式定义器
	var isFormulaFieldListBuilded = false;
	var formulaFieldList=[];
	function onClick_Formula_Dialog(idField,nameField,returnType){
		//#117231 列表视图查询条件屏蔽变量
		var formulaUrl = formulaDialogUrl = Com_Parameter.ContextPath + "sys/formula/dialog_edit.jsp";
		var formulaParam = {modelingListviewHideVar:true};
		Formula_Dialog(idField,nameField, '', 'String',null,null,null,formulaParam,formulaUrl);
		//列表视图无法解析公式定义器中的变量，撤回
	}
	//----------------公式定义器end
	seajs.use([ 'lui/topic', 'lui/jquery',
				'sys/modeling/base/listview/config/js/displayCssSet',
				"sys/modeling/base/formlog/res/mark/listViewFMMark"]
			,function(topic,$,displayCssSet,listViewFMMark){
		
		//初始化显示样式组件
		var displayCssSet_cfg = {
			operationEle:$("#fdDisplayCssSetTr"),
			contentEle:$("#fdDisplayCssSetContent"),
			storeData: listviewOption.modelingAppListviewForm.fdDisplayCssSet,
			allField: listviewOption.modelingAppListviewForm.allField
		};
		window.displayCssSetIns = new displayCssSet.DisplayCssSet(displayCssSet_cfg);
		
		//显示项下拉框数据
		var texts = getListViewData()[0].fdDisplays;
		if(listviewOption.modelingAppListviewForm.fdDisplay != ""){
			var modelDictData = $.parseJSON(listviewOption.modelingAppListviewForm.modelDict);
			var allFieldData = $.parseJSON(listviewOption.modelingAppListviewForm.allField);
			var fields = $.parseJSON(listviewOption.modelingAppListviewForm.fdDisplay);
			var data = {};
			var text = [];
			for(var i = 0;i < texts.length;i++){
				text.push(texts[i].text);
			}
			data.selected = doAdaptorOldData(modelDictData,allFieldData,fields);
			data.text = text;
			//显示项样式改变事件
			topic.publish("modeling.selectDisplay.change",{'thisObj':null,'data':data});
		}
		//兼容旧数据，补全多选框的枚举数据
		function doAdaptorOldData(modelDictData,allFieldData,fields){
			if(!fields || !allFieldData || !modelDictData){
				return;
			}
			var objArr = [];
			for(var n = 0;n < fields.length;n++){
				for(var m = 0;m < allFieldData.length;m++){
					if(fields[n].field == allFieldData[m].field.split(".")[0]){
						objArr.push(allFieldData[m]); 
					}
				}
			}
			for(var i = 0;i < objArr.length;i++){
				for(var j = 0;j < modelDictData.length;j++){
					if(modelDictData[j].field == objArr[i].field.split(".")[0] && modelDictData[j].hasOwnProperty("enumValues")){
						objArr[i].enumValues = modelDictData[j].enumValues;
					}
				}
			}
			return objArr;
		}
		
		//窗口大小自适应
		function onResizeFitWindow() {
			var height = $('body').height();
			if (listviewOption.param.isInDialog) {
				$('.model-edit-view-title:eq(0)').hide()
			} else {
				height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 25;
			}

			if (listviewOption.param.isPcAndMobile) {
				//Pc+移动
				height = $(parent.document).find(".modeling-pam-content-frame").eq(0).outerHeight(true);
				$(".model-edit-right-wrap .model-edit-view-content").height(height-80);
				$(".model-edit-left-wrap .model-edit-view-content").height(height-60);
				//
				$("[lui-pam-hidden='true']").hide();
				$(".model-edit-view-title").hide();
				$(".model-edit-left").css({"padding-top":0})
				$(".model-edit-right").css({"padding-top":0,"border-top":"1px solid #DDDDDD","border-bottom":"1px solid #DDDDDD"})
			}else{
				$("body", parent.document).find('#trigger_iframe').height(height);
				$(".model-edit-right-wrap .model-edit-view-content").height(height - 80);
				$(".model-edit-left-wrap .model-edit-view-content").height(height - 60);
				$("body", parent.document).css("overflow", "hidden");
			}

		}

		$(window).resize(function() {
			onResizeFitWindow();
		});

		//预览加载完毕事件
    	topic.subscribe('preview_load_finish', function (ctx) {
			onResizeFitWindow();
		});

    	//预览更新事件
		topic.subscribe('preview.refresh',function(){
			try{
				//刷新预览
				LUI("view_preview").setSourceData(getListViewData);
				LUI("view_preview").reRender();
				//恢复选择的位置
				if(lastSelectPostionObj && lastSelectPostionDirect){
					switchSelectPosition(lastSelectPostionObj,lastSelectPostionDirect,lastSelectPosition);
				}
			}catch(err){
			}
		});
		//表单映射
	    var listViewFMMark_pc = new listViewFMMark.ListViewFMMark({fdId:"${modelingAppListviewForm.fdId}"});
		listViewFMMark_pc.startup();

	});
	//查询条件“内置条件”和“自定义条件”切换
	$(".model-query-tab li").on("click", function () {
	       $(this).siblings().removeClass("active");
	       $(this).addClass("active");
	       var index = $(this).index();
	       $(".model-query-cont").each(function (i, ele) {
	           if (i == index) {
	               $(this).siblings().css('display', 'none');
	               $(this).css('display', 'block');
	           }
	       })
	});

</script>
<!-- 预定义查询相关 end -->

<html:javascript formName="modelingAppListviewForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>