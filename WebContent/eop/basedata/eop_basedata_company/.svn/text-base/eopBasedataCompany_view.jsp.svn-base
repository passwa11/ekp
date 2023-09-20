<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<style type="text/css">
.lui_paragraph_title {
	font-size: 15px;
	color: #15a4fa;
	padding: 5px 0px;
	text-align: left;
	border-bottom: 1px solid #eee;
	margin-bottom: 6px;
}

.lui_paragraph_title span {
	display: inline-block;
	margin: -2px 5px 0px 0px;
}

.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
	border: 0px;
	color: #868686
}
</style>
<div id="optBarDiv">

	<kmss:auth
		requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=edit&fdId=${param.fdId}">
		<input type="button" value="${lfn:message('button.edit')}"
			onclick="Com_OpenWindow('eopBasedataCompany.do?method=edit&fdId=${param.fdId}','_self');"
			order="2" />
	</kmss:auth>
	<kmss:auth
		requestURL="/eop/basedata/eop_basedata_company/eopBasedataCompany.do?method=delete&fdId=${param.fdId}">
		<input type="button" value="${lfn:message('button.delete')}"
			onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataCompany.do?method=delete&fdId=${param.fdId}','_self');" />
	</kmss:auth>
	<input type="button" value="${lfn:message('button.close')}"
		onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataCompany') }</p>
<center>
	<div style="width: 95%;">
		<div class="lui_paragraph_title">${ lfn:message('eop-basedata:py.JiBenXinXi') }
		</div>
		<table class="tb_normal" width="100%">
			<fssc:switchOn property="fdCompanyGroup">
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdGroup')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdGroupId" _xform_type="dialog">
							<xform:dialog propertyId="fdGroupId" propertyName="fdGroupName"
								subject="${lfn:message('eop-basedata:eopBasedataCompany.fdGroup')}"
								showStatus="view" style="width:95%;">
							</xform:dialog>
						</div>
					</td>
				</tr>
			</fssc:switchOn>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.fdName')}</td>
				<td width="35%">
					<%-- 主体名称--%>
					<div id="_xform_fdName" _xform_type="text">
						<xform:text property="fdName" showStatus="view" style="width:95%;" />
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.fdIsAvailable')}</td>
				<td width="35%">
					<%-- 是否有效--%>
					<div id="_xform_fdIsAvailable" _xform_type="radio">
						<xform:radio property="fdIsAvailable"
							htmlElementProperties="id='fdIsAvailable'" showStatus="view">
							<xform:enumsDataSource enumsType="common_yesno" />
						</xform:radio>
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.fdIden')}</td>
				<td width="35%">
					<%-- 统一信用代码--%>
					<div id="_xform_fdIden" _xform_type="text">
						<xform:text property="fdIden" showStatus="view" style="width:95%;" />
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.legalRepresentative')}
				</td>
				<td width="35%">
					<%-- 法定代表人--%>
					<div id="_xform_legalRepresentative" _xform_type="text">
						<xform:text property="legalRepresentative" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.regAddress')}</td>
				<td colspan="3" width="85.0%">
					<%-- 注册地址--%>
					<div id="_xform_regAddress" _xform_type="text">
						<xform:text property="regAddress" showStatus="view"
							style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.fdAccountCurrency')}
				</td>
				<td width="35.0%">
					<div id="_xform_fdAccountCurrencyId" _xform_type="dialog">
						<xform:dialog propertyId="fdAccountCurrencyId"
							propertyName="fdAccountCurrencyName" showStatus="view"
							required="true"
							subject="${lfn:message('eop-basedata:eopBasedataCompany.fdAccountCurrency')}"
							style="width:95%;">
						</xform:dialog>
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.fdEkpOrg')}</td>
				<td width="35.0%">
					<div id="_xform_fdEkpOrgIds" _xform_type="address">
						<xform:address propertyId="fdEkpOrgIds"
							propertyName="fdEkpOrgNames" mulSelect="true"
							orgType="ORG_TYPE_ORGORDEPT" showStatus="view" required="true"
							subject="${lfn:message('eop-basedata:eopBasedataCompany.fdEkpOrg')}"
							textarea="true" style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.docCreator')}</td>
				<td width="35%">
					<%-- 创建人--%>
					<div id="_xform_docCreatorId" _xform_type="address">
						<ui:person personId="${eopBasedataCompanyForm.docCreatorId}"
							personName="${eopBasedataCompanyForm.docCreatorName}" />
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.docCreateTime')}</td>
				<td width="35%">
					<%-- 创建时间--%>
					<div id="_xform_docCreateTime" _xform_type="datetime">
						<xform:datetime property="docCreateTime" showStatus="view"
							dateTimeType="datetime" style="width:95%;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.docAlteror')}</td>
				<td width="35%">
					<div id="_xform_docAlterorId" _xform_type="address">
						<ui:person personId="${eopBasedataCompanyForm.docAlterorId}"
							personName="${eopBasedataCompanyForm.docAlterorName}" />
					</div>
				</td>
				<td class="td_normal_title" width="15%">
					${lfn:message('eop-basedata:eopBasedataCompany.docAlterTime')}</td>
				<td width="35%">
					<div id="_xform_docAlterTime" _xform_type="datetime">
						<xform:datetime property="docAlterTime" showStatus="view"
							dateTimeType="datetime" style="width:95%;" />
					</div>
				</td>
			</tr>
		</table>

		<kmss:ifModuleExist path="/fssc/common">
			<div class="lui_paragraph_title">${ lfn:message('eop-basedata:py.CaiWuGongSiXinXi') }
			</div>
			<table class="tb_normal" width="100%">
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdCode')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdCode" _xform_type="text">
							<xform:text property="fdCode" showStatus="view" required="true"
								style="width:95%;" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdType')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdType" _xform_type="radio">
							<xform:radio property="fdType" showStatus="view">
								<xform:enumsDataSource enumsType="eop_basedata_company_type" />
							</xform:radio>
						</div>
					</td>
				</tr>
				<c:if test="${not empty  eopBasedataCompanyForm.fdJoinSystem}">
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.fdJoinSystem')}</td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdJoinSystem" _xform_type="text">
								<xform:text property="fdJoinSystem" showStatus="view"
									style="width:95%;" />
							</div>
						</td>
					</tr>
				</c:if>
				<tr style="display: none;" class="U8">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdUEightUrl')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdUEightUrl" _xform_type="text">
							<xform:text property="fdUEightUrl" style="width:95%;"
								validators="maxLength(200)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdKUrl')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdKUrl" _xform_type="text">
							<xform:text property="fdKUrl" style="width:95%;"
								validators="maxLength(200)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdKUserName')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdKUserName" _xform_type="text">
							<xform:text property="fdKUserName" style="width:95%;"
								validators="maxLength(100)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdKPassWord')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdKPassWord" _xform_type="text">
							<xform:text property="fdKPassWord" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdEUserName')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdEUserName" _xform_type="text">
							<xform:text property="fdEUserName" style="width:95%;"
								validators="maxLength(100)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdEPassWord')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdEPassWord" _xform_type="text">
							<xform:text property="fdEPassWord" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdESlnName')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdESlnName" _xform_type="text">
							<xform:text property="fdESlnName" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdEDcName')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdEDcName" _xform_type="text">
							<xform:text property="fdEDcName" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdELanguage')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdELanguage" _xform_type="text">
							<xform:text property="fdELanguage" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdEDbType')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdEDbType" _xform_type="text">
							<xform:text property="fdEDbType" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdEAuthPattern')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdEAuthPattern" _xform_type="text">
							<xform:text property="fdEAuthPattern" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdELoginWsdlUrl')}
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdELoginWsdlUrl" _xform_type="text">
							<xform:text property="fdELoginWsdlUrl" style="width:95%;"
								validators="maxLength(200)"></xform:text>
							?wsdl
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="Eas">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdEImportVoucherWsdlUrl')}
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdEImportVoucherWsdlUrl" _xform_type="text">
							<xform:text property="fdEImportVoucherWsdlUrl" style="width:95%;"
								validators="maxLength(200)"></xform:text>
							?wsdl
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3Cloud">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdK3cUrl')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdK3cUrl" _xform_type="text">
							<xform:text property="fdK3cUrl" style="width:95%;"
								validators="maxLength(200)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3Cloud">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdK3cId')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdK3cId" _xform_type="text">
							<xform:text property="fdK3cId" style="width:95%;"
								validators="maxLength(100)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3Cloud">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdK3cPersonName')}
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdK3cPersonName" _xform_type="text">
							<xform:text property="fdK3cPersonName" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3Cloud">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdK3cPassword')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdK3cPassword" _xform_type="text">
							<xform:text property="fdK3cPassword" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<tr style="display: none;" class="K3Cloud">
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdK3cIcid')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdK3cIcid" _xform_type="text">
							<xform:text property="fdK3cIcid" style="width:95%;"
								validators="maxLength(50)"></xform:text>
						</div>
					</td>
				</tr>
				<c:if test="${not empty  eopBasedataCompanyForm.fdSystemParam}">
					<tr id="systemParam">
						<td class="td_normal_title" width="15%" id="systemTitle"></td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdJoinSystem" _xform_type="text">
								<xform:text property="fdSystemParam" style="width:95%;"></xform:text>
							</div>
						</td>
					</tr>
				</c:if>
				<kmss:ifModuleExist path="/fssc/inhand">
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdCabinetType')}
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdCabinetType" _xform_type="radio">
							<xform:radio property="fdCabinetType" showStatus="view">
								<xform:enumsDataSource enumsType="eop_basedata_cabinet_type" />
							</xform:radio>
						</div>
					</td>
				</tr>
				</kmss:ifModuleExist>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdBudgetCurrency')}
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdBudgetCurrencyId" _xform_type="dialog">
							<xform:dialog propertyId="fdBudgetCurrencyId"
								propertyName="fdBudgetCurrencyName" showStatus="view"
								required="true"
								subject="${lfn:message('eop-basedata:eopBasedataCompany.fdBudgetCurrency')}"
								style="width:95%;">
							</xform:dialog>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdDutyParagraph')}</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdDutyParagraph" _xform_type="dialog">
							<xform:text property="fdDutyParagraph" showStatus="view"
								style="width:95%;" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdFinancialStaff')}
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdFinancialStaffIds" _xform_type="address">
							<xform:address propertyId="fdFinancialStaffIds"
								propertyName="fdFinancialStaffNames" mulSelect="true"
								orgType="ORG_TYPE_ALL" showStatus="view" required="true"
								subject="${lfn:message('eop-basedata:eopBasedataCompany.fdFinancialStaff')}"
								textarea="true" style="width:95%;" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdFinancialManager')}
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_fdFinancialManagerIds" _xform_type="address">
							<xform:address propertyId="fdFinancialManagerIds"
								propertyName="fdFinancialManagerNames" mulSelect="true"
								orgType="ORG_TYPE_ALL" showStatus="view" required="true"
								subject="${lfn:message('eop-basedata:eopBasedataCompany.fdFinancialManager')}"
								textarea="true" style="width:95%;" />
						</div>
					</td>
				</tr>
			</table>
		</kmss:ifModuleExist>
		<kmss:ifModuleExist path="/km/agreement">
			<div class="lui_paragraph_title">${ lfn:message('eop-basedata:py.HeTongZhuTiXinXi') }
			</div>
			<table class="tb_normal" width="100%">
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.legalRepresentativeIden')}</td>
					<td width="35%">
						<div id="_xform_legalRepresentativeIden" _xform_type="text">
							<xform:text property="legalRepresentativeIden"
								validators="lrIdenValidator" showStatus="view"
								style="width:95%;" />
						</div>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.contactor')}</td>
					<td width="35%">
						<div id="_xform_contactorId" _xform_type="address">
							<xform:address propertyId="contactorId"
								propertyName="contactorName" orgType="ORG_TYPE_PERSON"
								showStatus="view" style="width:95%;" />
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.contactorMobileNo')}
					</td>
					<td width="35%">
						<%-- 联系人手机号--%>
						<div id="_xform_contactorMobileNo" _xform_type="text">
							<xform:text property="contactorMobileNo" showStatus="view"
								style="width:95%;" />
						</div>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.contactorEmail')}</td>
					<td width="35%">
						<%-- 联系人邮箱--%>
						<div id="_xform_contactorEmail" _xform_type="text">
							<xform:text property="contactorEmail" showStatus="view"
								style="width:95%;" />
						</div>
					</td>
				</tr>
			</table>
		</kmss:ifModuleExist>
	</div>
</center>
<script>
	$(document)
			.ready(
					function() {
						var fdJoinSystem = "${eopBasedataCompanyForm.fdJoinSystem}";
						if (fdJoinSystem == 'U8') {
							$(".U8").show();
							$("#systemTitle")
									.html(
											"${lfn:message('eop-basedata:eopBasedataCompany.fdSystemParam.U8')}");
						} else if (fdJoinSystem == 'K3') {
							$(".K3").show();
							$("#systemTitle")
									.html(
											"${lfn:message('eop-basedata:eopBasedataCompany.fdSystemParam.K3')}");
						} else if (fdJoinSystem == 'EAS') {
							$(".Eas").show();
						} else if (fdJoinSystem == 'K3Cloud') {
							$(".K3Cloud").show();
						}
					});
	var formInitData = {

	};

	function confirmDelete(msg) {
		return confirm('${ lfn:message("page.comfirmDelete") }');
	}

	function openWindowViaDynamicForm(popurl, params, target) {
		var form = document.createElement('form');
		if (form) {
			try {
				target = !target ? '_blank' : target;
				form.style = "display:none;";
				form.method = 'post';
				form.action = popurl;
				form.target = target;
				if (params) {
					for ( var key in params) {
						var v = params[key];
						var vt = typeof v;
						var hdn = document.createElement('input');
						hdn.type = 'hidden';
						hdn.name = key;
						if (vt == 'string' || vt == 'boolean' || vt == 'number') {
							hdn.value = v + '';
						} else {
							if ($.isArray(v)) {
								hdn.value = v.join(';');
							} else {
								hdn.value = toString(v);
							}
						}
						form.appendChild(hdn);
					}
				}
				document.body.appendChild(form);
				form.submit();
			} finally {
				document.body.removeChild(form);
			}
		}
	}

	function doCustomOpt(fdId, optCode) {
		if (!fdId || !optCode) {
			return;
		}

		if (viewOption.customOpts && viewOption.customOpts[optCode]) {
			var param = {
				"List_Selected_Count" : 1
			};
			var argsObject = viewOption.customOpts[optCode];
			if (argsObject.popup == 'true') {
				var popurl = viewOption.contextPath + argsObject.popupUrl
						+ '&fdId=' + fdId;
				for ( var arg in argsObject) {
					param[arg] = argsObject[arg];
				}
				openWindowViaDynamicForm(popurl, param, '_self');
				return;
			}
			var optAction = viewOption.contextPath + viewOption.basePath
					+ '?method=' + optCode + '&fdId=' + fdId;
			Com_OpenWindow(optAction, '_self');
		}
	}
	window.doCustomOpt = doCustomOpt;
	var viewOption = {
		contextPath : '${LUI_ContextPath}',
		basePath : '/eop/basedata/eop_basedata_company/eopBasedataCompany.do',
		customOpts : {

			____fork__ : 0
		}
	};
	Com_IncludeFile("security.js");
	Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>
