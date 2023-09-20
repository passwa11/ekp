<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/fssc/ctrip/resource/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>

<script type="text/javascript">
    var formInitData = {

    };
    var messageInfo = {

    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_filed_mapping/", 'js', true);
    Com_IncludeFile("fsscCtripFiledMapping_edit.js", "${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_filed_mapping/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">
	<c:choose>
		<c:when test="${fsscCtripFiledMappingForm.method_GET=='edit'}">
			<input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscCtripFiledMappingForm, 'update');">
		</c:when>
		<c:when test="${fsscCtripFiledMappingForm.method_GET=='add'}">
			<input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscCtripFiledMappingForm, 'save');">
			<input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.fsscCtripFiledMappingForm, 'saveadd');">
		</c:when>
	</c:choose>
	<input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
</div>

<html:form action="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do">
 
<p class="txttitle">${lfn:message('fssc-ctrip:table.fsscCtripFiledMapping')}</p>

<center>
	<div style="width:95%;">
		<table class="tb_normal" id="Label_Tabel" width=95%>
			<tr LKS_LabelName="${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdTripOwenor')}">
				<td>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdModelName')}
							</td><td width="35%">
								<xform:dialog propertyId="fdModelId" propertyName="fdModelName" required="true" showStatus="edit" style="width:85%;">
									dialogSelect(false,'fssc_ctrip_model_fdmodel','fdModelId','fdModelName',FS_changgeModel);
								</xform:dialog>
								<input name="fdMainModelName" type="hidden" value="${fdMainModelName}" />
								<input name="fdTemplateModelName" type="hidden" value="${fdTemplateModelName}" />
								<input name="fdKey" type="hidden" value="${fdKey}" />
							</td>
						</tr>
						<tr>
	                    <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-ctrip:fsscCtripFiledMapping.docTemplateName')}
	                    </td>
	                    <td width="85%">
	                        <%-- 所属分类/模板--%>
	                        <div id="_xform_fdCateId" style="display: none;" _xform_type="dialog">
	                            <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" required="true" showStatus="edit" style="width:85%;">
	                                dialogSelect(false,'fssc_ctrip_select_fdTempate','docTemplateId','docTemplateName',null,{'fdModelName':$('[name=fdTemplateModelName]').val()});
	                            </xform:dialog>
	                        </div>
	                    </td>
	                	</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdApprovalNumberText')}
							</td><td width="35%">
								<xform:text property="fdApprovalNumber" showStatus="noShow" style="width:85%" />
								<xform:text property="fdApprovalNumberText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdApprovalNumber','fdApprovalNumberText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdDetailNoText')}
							</td><td width="35%">
								<xform:text property="fdDetailNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdDetailNoText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdDetailNo','fdDetailNoText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message7')}</span>
								</div>
							</td>
						</tr>
						<tr>
								<td class="td_normal_title" width=15%>
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdCompanyIdText')}
								</td><td width="35%">
									<xform:text property="fdCompanyId" showStatus="noShow" style="width:85%" />
									<xform:text property="fdCompanyIdText" showStatus="readOnly" style="width:85%" />
									<a href="#" onclick="selectFormula('fdCompanyId','fdCompanyIdText');">
										${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
									</a>
									<div>
										<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message1')}</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=15%>
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdCostCenter1Text')}
								</td><td width="35%">
									<xform:text property="fdCostCenter1" showStatus="noShow" style="width:85%" />
									<xform:text property="fdCostCenter1Text" showStatus="readOnly" style="width:85%" />
									<a href="#" onclick="selectFormula('fdCostCenter1','fdCostCenter1Text');">
										${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
									</a>
									<div>
										<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message2')}</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=15%>
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdCostCenter2Text')}
								</td><td width="35%">
									<xform:text property="fdCostCenter2" showStatus="noShow" style="width:85%" />
									<xform:text property="fdCostCenter2Text" showStatus="readOnly" style="width:85%" />
									<a href="#" onclick="selectFormula('fdCostCenter2','fdCostCenter2Text');">
										${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
									</a>
									<div>
										<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message3')}</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=15%>
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdCostCenter3Text')}
								</td><td width="35%">
									<xform:text property="fdCostCenter3" showStatus="noShow" style="width:85%" />
									<xform:text property="fdCostCenter3Text" showStatus="readOnly" style="width:85%" />
									<a href="#" onclick="selectFormula('fdCostCenter3','fdCostCenter3Text');">
										${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
									</a>
									<div>
										<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message3')}</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=15%>
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdDefineFlag1Text')}
								</td><td width="35%">
									<xform:text property="fdDefineFlag1" showStatus="noShow" style="width:85%" />
									<xform:text property="fdDefineFlag1Text" showStatus="readOnly" style="width:85%" />
									<a href="#" onclick="selectFormula('fdDefineFlag1','fdDefineFlag1Text');">
										${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
									</a>
									<div>
										<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message4')}</span>
									</div>
								</td>
							</tr>
							<tr>
								<td class="td_normal_title" width=15%>
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdDefineFlag2Text')}
								</td><td width="35%">
									<xform:text property="fdDefineFlag2" showStatus="noShow" style="width:85%" />
									<xform:text property="fdDefineFlag2Text" showStatus="readOnly" style="width:85%" />
									<a href="#" onclick="selectFormula('fdDefineFlag2','fdDefineFlag2Text');">
										${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
									</a>
									<div>
										<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message5')}</span>
									</div>
								</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr LKS_LabelName="${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdTripmessage')}">
				<td>
					<table class="tb_normal" width=100%> 
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdRule')}
							</td><td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdRule" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdRuleText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdRule','fdFlightDetail_Form[0].fdRuleText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdRule.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdAirLineText')}
							</td><td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdAirLine" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdAirLineText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdAirLine','fdFlightDetail_Form[0].fdAirLineText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message8')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdCurrencyText')}
							</td><td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdCurrency" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdCurrencyText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdCurrency','fdFlightDetail_Form[0].fdCurrencyText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message9')}
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdFlightWayText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdFlightWay" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdFlightWayText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdFlightWay','fdFlightDetail_Form[0].fdFlightWayText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message10')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdDepartDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdDepartDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdDepartDateBeginText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdDepartDateBegin','fdFlightDetail_Form[0].fdDepartDateBeginText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdDepartDateNoText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdDepartDateNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdDepartDateNoText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdDepartDateNo','fdFlightDetail_Form[0].fdDepartDateNoText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdDepartDateNoText.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdDiscountText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdDiscount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdDiscountText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdDiscount','fdFlightDetail_Form[0].fdDiscountText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdDepartCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdDepartCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdDepartCityCodesText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdDepartCityCodes','fdFlightDetail_Form[0].fdDepartCityCodesText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdArrivalCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdArrivalCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdArrivalCityCodesText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdArrivalCityCodes','fdFlightDetail_Form[0].fdArrivalCityCodesText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdPassengerListText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdPassengerList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdPassengerListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdPassengerList','fdFlightDetail_Form[0].fdPassengerListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdPassengerListText.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdPriceText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdPrice','fdFlightDetail_Form[0].fdPriceText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdProductType','fdFlightDetail_Form[0].fdProductTypeText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdProductType.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdReturnDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdReturnDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdReturnDateBeginText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdReturnDateBegin','fdFlightDetail_Form[0].fdReturnDateBeginText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdReturnDateBegin.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdReturnDateNoText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdReturnDateEnd" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdReturnDateEndText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdReturnDateEnd','fdFlightDetail_Form[0].fdReturnDateEndText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdReturnDateEnd.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdSeatClassText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdSeatClass" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdSeatClassText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdFlightDetail_Form[0].fdSeatClass','fdFlightDetail_Form[0].fdSeatClassText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdSeatClass.help')}</span>
								</div>
							</td>
						</tr>
					</table>
					<input name="fdFlightDetail_Flag" value="1" type="hidden" />
				</td>
			</tr>
			<tr LKS_LabelName="${lfn:message('fssc-ctrip:table.fsscCtripHotelMapping')}">
				<td>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdRule')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdRule" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdRuleText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdRule','fdHotelDetail_Form[0].fdRuleText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdRule.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdProductType','fdHotelDetail_Form[0].fdProductTypeText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdProductTypeText.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckInDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateBeginText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdCheckInDateBegin','fdHotelDetail_Form[0].fdCheckInDateBeginText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckInDateNoText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateNoText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdCheckInDateNo','fdHotelDetail_Form[0].fdCheckInDateNoText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckOutDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateBeginText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdCheckOutDateBegin','fdHotelDetail_Form[0].fdCheckOutDateBeginText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckOutDateNoText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateNoText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdCheckOutDateNo','fdHotelDetail_Form[0].fdCheckOutDateNoText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdPassengerListText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdPassengerList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdPassengerListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdPassengerList','fdHotelDetail_Form[0].fdPassengerListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdPassengerListText.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckInCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckInCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckInCityCodesText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdCheckInCityCodes','fdHotelDetail_Form[0].fdCheckInCityCodesText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMaxPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMaxPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMaxPriceText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdMaxPrice','fdHotelDetail_Form[0].fdMaxPriceText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMinPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMinPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMinPriceText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdMinPrice','fdHotelDetail_Form[0].fdMinPriceText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCurrencyText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCurrency" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCurrencyText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdCurrency','fdHotelDetail_Form[0].fdCurrencyText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message9')}
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMaxStarRatingText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMaxStarRating" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMaxStarRatingText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdMaxStarRating','fdHotelDetail_Form[0].fdMaxStarRatingText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMinStarRatingText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMinStarRating" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMinStarRatingText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdMinStarRating','fdHotelDetail_Form[0].fdMinStarRatingText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdAveragePriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdAveragePrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdAveragePriceText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdAveragePrice','fdHotelDetail_Form[0].fdAveragePriceText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<%-- <tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdSkipFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdSkipFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdSkipFieldsText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdSkipFields','fdHotelDetail_Form[0].fdSkipFieldsText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdTotalRoomNightCountText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdTotalRoomNightCount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdTotalRoomNightCountText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdTotalRoomNightCount','fdHotelDetail_Form[0].fdTotalRoomNightCountText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr> --%>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdRoomNightPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdRoomNightPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdRoomNightPriceText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdRoomNightPrice','fdHotelDetail_Form[0].fdRoomNightPriceText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<%-- <tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdPreVerifyFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdPreVerifyFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdPreVerifyFieldsText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdHotelDetail_Form[0].fdPreVerifyFields','fdHotelDetail_Form[0].fdPreVerifyFieldsText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr> --%>
					</table>
					<input type="hidden" name="fdHotelDetail_Flag" value="1">
				</td>
			</tr>
			<tr LKS_LabelName="${lfn:message('fssc-ctrip:table.fsscCtripTrainMapping')}">
				<td>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdRule')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdRule" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdRuleText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdRule','fdTrainDetail_Form[0].fdRuleText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdRule.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdProductType','fdTrainDetail_Form[0].fdProductTypeText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdTrainProductType.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdTripTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdTripType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdTripTypeText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdTripType','fdTrainDetail_Form[0].fdTripTypeText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdTrainTripTypeText.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdDepartDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateBeginText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdDepartDateBegin','fdTrainDetail_Form[0].fdDepartDateBeginText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdDepartDateEndText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateEnd" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateEndText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdDepartDateEnd','fdTrainDetail_Form[0].fdDepartDateEndText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdReturnDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdReturnDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdReturnDateBeginText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdReturnDateBegin','fdTrainDetail_Form[0].fdReturnDateBeginText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdReturnDateBegin.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdReturnDateEndText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdReturnDateEnd" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdReturnDateEndText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdReturnDateEnd','fdTrainDetail_Form[0].fdReturnDateEndText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdReturnDateEnd.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdPassengerListText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdPassengerList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdPassengerListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdPassengerList','fdTrainDetail_Form[0].fdPassengerListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdPassengerListText.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdFromCitiesListText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdFromCitiesList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdFromCitiesListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdFromCitiesList','fdTrainDetail_Form[0].fdFromCitiesListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdToCitiesListText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdToCitiesList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdToCitiesListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdToCitiesList','fdTrainDetail_Form[0].fdToCitiesListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdArrivalCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdArrivalCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdArrivalCityCodesText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdArrivalCityCodes','fdTrainDetail_Form[0].fdArrivalCityCodesText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdDepartCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdDepartCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdDepartCityCodesText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdDepartCityCodes','fdTrainDetail_Form[0].fdDepartCityCodesText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdPriceText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdPrice','fdTrainDetail_Form[0].fdPriceText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdCurrencyText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdCurrency" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdCurrencyText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdCurrency','fdTrainDetail_Form[0].fdCurrencyText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message9')}
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdSeatTypeListText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdSeatTypeList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdSeatTypeListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdSeatTypeList','fdTrainDetail_Form[0].fdSeatTypeListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdSeatTypeListText.help')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdSkipFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdSkipFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdSkipFieldsText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdSkipFields','fdTrainDetail_Form[0].fdSkipFieldsText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdTravelerCountText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdTravelerCount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdTravelerCountText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdTravelerCount','fdTrainDetail_Form[0].fdTravelerCountText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdPreVerifyFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdPreVerifyFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdPreVerifyFieldsText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdTrainDetail_Form[0].fdPreVerifyFields','fdTrainDetail_Form[0].fdPreVerifyFieldsText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
					</table>
					<input type="hidden" name="fdTrainDetail_Flag" value="1">
				</td>
			</tr>
			<%--<tr LKS_LabelName="${lfn:message('fssc-ctrip:table.fsscCtripCarQuickMapping')}">
				<td>
					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdRule')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdRule" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdRuleText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdRule','fdCarQuickDetail_Form[0].fdRuleText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdProductType','fdCarQuickDetail_Form[0].fdProductTypeText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdPassengerListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdPassengerList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdPassengerListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdPassengerList','fdCarQuickDetail_Form[0].fdPassengerListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdCitiesText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdCities" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdCitiesText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdCities','fdCarQuickDetail_Form[0].fdCitiesText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdCompanyAddressListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdCompanyAddressList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdCompanyAddressListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdCompanyAddressList','fdCarQuickDetail_Form[0].fdCompanyAddressListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdArrivalAddressListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalAddressList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalAddressListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdArrivalAddressList','fdCarQuickDetail_Form[0].fdArrivalAddressListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdBeginUseDateText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdBeginUseDate" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdBeginUseDateText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdBeginUseDate','fdCarQuickDetail_Form[0].fdBeginUseDateText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdEndUseDateText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdEndUseDate" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdEndUseDateText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdEndUseDate','fdCarQuickDetail_Form[0].fdEndUseDateText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdUseTimeListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdUseTimeList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdUseTimeListText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdUseTimeList','fdCarQuickDetail_Form[0].fdUseTimeListText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdCurrencyText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdCurrency" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdCurrencyText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdCurrency','fdCarQuickDetail_Form[0].fdCurrencyText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message9')}
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdPriceText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdPrice','fdCarQuickDetail_Form[0].fdPriceText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdVehicleGroupText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdVehicleGroup" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdVehicleGroupText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdVehicleGroup','fdCarQuickDetail_Form[0].fdVehicleGroupText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdEffectivenessAmountText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdEffectivenessAmount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdEffectivenessAmountText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdEffectivenessAmount','fdCarQuickDetail_Form[0].fdEffectivenessAmountText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdSkipFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdSkipFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdSkipFieldsText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdSkipFields','fdCarQuickDetail_Form[0].fdSkipFieldsText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdArrivalCitiesText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalCities" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalCitiesText" showStatus="readOnly" style="width:85%" />
								<a href="#" onclick="selectFormula('fdCarQuickDetail_Form[0].fdArrivalCities','fdCarQuickDetail_Form[0].fdArrivalCitiesText');">
									${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdFormula')}
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>--%>
			<input type="hidden" name="fdCarQuickDetail_Flag" value="1">
		</table>
	</div>
</center>
<html:hidden property="fdId" />
<html:hidden property="docCreateTime" />
<html:hidden property="docAlterTime" />
<html:hidden property="fdLastModifiedTime" />
<html:hidden property="method_GET" />
<script>
    Com_IncludeFile("formula.js");
	Com_IncludeFile("calendar.js");
	Com_IncludeFile("formula.js");
	Com_IncludeFile("docutil.js|security.js|dialog.js|data.js");
	$KMSSValidation();
	function XForm_Util_UnitArray(array, sysArray, extArray) {
		<%-- // 合并 --%>
		array = array.concat(sysArray);
		if (extArray != null) {
			array = array.concat(extArray);
		}
		<%-- // 结果 --%>
		return array;
	}
	
	function selectFormula(id,name){
		var tempId = $("input[name='docTemplateId']").val();
		var fdMainModelName=$("input[name='fdMainModelName']").val();
		if(!tempId){
            seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
                dialog.alert("${lfn:message('fssc-ctrip:fsscCtripFiledMapping.checkFeeFirst')}");
            });
			return ;
		}else{
            Formula_Dialog(id, name, Formula_GetVarInfoByModelName_New(fdMainModelName,tempId),'String');
		}
	}
	function Formula_GetVarInfoByModelName_New(modelName,tempId){
		var obj = [];
		var sysObj = new KMSSData().AddBeanData("sysFormulaDictVarTree&authCurrent=true&modelName="+modelName).GetHashMapArray();
		var fdKey = $("input[name='fdKey']").val();
		var fdModelCate = $("input[name='fdTemplateModelName']").val();
		var extObj = new KMSSData().AddBeanData("fsscCtripDictExtendModelService&authCurrent=true&tempType=template&tempId="+tempId+"&fdKey="+fdKey+"&fdModelName="+fdModelCate).GetHashMapArray();
		return XForm_Util_UnitArray(obj, sysObj, extObj);
	}
</script>
</html:form>

<%@ include file="/resource/jsp/edit_down.jsp" %>
