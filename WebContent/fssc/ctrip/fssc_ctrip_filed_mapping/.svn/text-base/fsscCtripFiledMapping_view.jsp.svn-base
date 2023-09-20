<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp" %>
<style type="text/css">

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

</style>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
		<div id="optBarDiv">
			<kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripFiledMapping.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
			</kmss:auth>
			<kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_filed_mapping/fsscCtripFiledMapping.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscCtripFiledMapping.do?method=delete&fdId=${param.fdId}','_self');" />
			</kmss:auth>
			<input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
		</div>
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
								<xform:dialog propertyId="fdModelId" propertyName="fdModelName" required="true" style="width:85%;">
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
	                        <div id="_xform_fdCateId" _xform_type="dialog">
	                            <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" required="true" style="width:85%;">
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
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFiledMapping.fdDetailNoText')}
							</td><td width="35%">
								<xform:text property="fdDetailNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdDetailNoText" showStatus="readOnly" style="width:85%" />
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
							<td colspan="2">
								<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message6')}</span>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdRule')}
							</td><td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdRule" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdRuleText" showStatus="readOnly" style="width:85%" />
								<div>
									<span style="color: red;">${lfn:message('fssc-ctrip:fsscCtripFiledMapping.message8')}</span>
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdAirLineText')}
							</td><td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdAirLine" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdAirLineText" showStatus="readOnly" style="width:85%" />
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
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdDepartDateNoText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdDepartDateNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdDepartDateNoText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdDiscountText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdDiscount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdDiscountText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdDepartCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdDepartCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdDepartCityCodesText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdArrivalCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdArrivalCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdArrivalCityCodesText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdPassengerListText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdPassengerList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdPassengerListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdPriceText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripFlightMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdFlightDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdFlightDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
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
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckInDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateBeginText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckInDateNoText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckInDateNoText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckOutDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateBeginText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckOutDateNoText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateNo" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckOutDateNoText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdPassengerListText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdPassengerList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdPassengerListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCheckInCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCheckInCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCheckInCityCodesText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMaxPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMaxPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMaxPriceText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMinPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMinPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMinPriceText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdCurrencyText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdCurrency" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdCurrencyText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMaxStarRatingText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMaxStarRating" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMaxStarRatingText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdMinStarRatingText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdMinStarRating" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdMinStarRatingText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdAveragePriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdAveragePrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdAveragePriceText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdSkipFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdSkipFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdSkipFieldsText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdTotalRoomNightCountText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdTotalRoomNightCount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdTotalRoomNightCountText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdRoomNightPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdRoomNightPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdRoomNightPriceText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripHotelMapping.fdPreVerifyFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdHotelDetail_Form[0].fdPreVerifyFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdHotelDetail_Form[0].fdPreVerifyFieldsText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
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
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdTripTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdTripType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdTripTypeText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdDepartDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateBeginText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdDepartDateEndText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateEnd" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdDepartDateEndText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdReturnDateBeginText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdReturnDateBegin" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdReturnDateBeginText" showStatus="readOnly" style="width:85%" />
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
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdFromCitiesListText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdFromCitiesList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdFromCitiesListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdToCitiesListText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdToCitiesList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdToCitiesListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdArrivalCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdArrivalCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdArrivalCityCodesText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdDepartCityCodesText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdDepartCityCodes" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdDepartCityCodesText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdPriceText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdCurrencyText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdCurrency" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdCurrencyText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdSeatTypeListText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdSeatTypeList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdSeatTypeListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdSkipFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdSkipFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdSkipFieldsText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdTravelerCountText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdTravelerCount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdTravelerCountText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripTrainMapping.fdPreVerifyFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdTrainDetail_Form[0].fdPreVerifyFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdTrainDetail_Form[0].fdPreVerifyFieldsText" showStatus="readOnly" style="width:85%" />
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
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdProductTypeText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdProductType" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdProductTypeText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdPassengerListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdPassengerList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdPassengerListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdCitiesText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdCities" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdCitiesText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdCompanyAddressListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdCompanyAddressList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdCompanyAddressListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdArrivalAddressListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalAddressList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalAddressListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdBeginUseDateText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdBeginUseDate" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdBeginUseDateText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdEndUseDateText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdEndUseDate" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdEndUseDateText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdUseTimeListText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdUseTimeList" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdUseTimeListText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdCurrencyText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdCurrency" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdCurrencyText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdPriceText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdPrice" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdPriceText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdVehicleGroupText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdVehicleGroup" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdVehicleGroupText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdEffectivenessAmountText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdEffectivenessAmount" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdEffectivenessAmountText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdSkipFieldsText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdSkipFields" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdSkipFieldsText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
						<tr>
							<td class="td_normal_title" width=15%>
								${lfn:message('fssc-ctrip:fsscCtripCarQuickMapping.fdArrivalCitiesText')}
							</td>
							<td width="35%">
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalCities" showStatus="noShow" style="width:85%" />
								<xform:text property="fdCarQuickDetail_Form[0].fdArrivalCitiesText" showStatus="readOnly" style="width:85%" />
							</td>
						</tr>
					</table>
					<input type="hidden" name="fdCarQuickDetail_Flag" value="1">
				</td>
			</tr>--%>
		</table>
	</div>
</center>

<%@ include file="/resource/jsp/view_down.jsp" %>
