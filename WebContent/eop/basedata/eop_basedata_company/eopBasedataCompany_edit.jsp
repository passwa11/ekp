<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<style type="text/css">
.lui_paragraph_title {
	font-size: 12px;
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
<script type="text/javascript">
	var formInitData = {

	};
	var messageInfo = {
		"eopBasedataCompany.fdSystemParam.U8" : "${lfn:message('eop-basedata:eopBasedataCompany.fdSystemParam.U8')}",
		"eopBasedataCompany.fdSystemParam.K3" : "${lfn:message('eop-basedata:eopBasedataCompany.fdSystemParam.K3')}"
	};
	Com_IncludeFile("security.js");
	Com_IncludeFile("domain.js");
	Com_IncludeFile("form.js");
	Com_IncludeFile("config_fssc_edit.js",
			"${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
	Com_IncludeFile("form_option.js",
			"${LUI_ContextPath}/eop/basedata/eop_basedata_company/", 'js', true);
	Com_IncludeFile("fsscCompany.js",
			"${LUI_ContextPath}/eop/basedata/eop_basedata_company/", 'js', true);
	var jsEntry = null;
	var jsEntryPath = 'eop/basedata/eop_basedata_company/js/edit.js';
	var luiReady = function() {
		seajs.use([ jsEntryPath ], function(je) {
			je.init({
				ctxPath : "${LUI_ContextPath}"
			});
			jsEntry = je;
		})
	}
	LUI.ready(luiReady);
</script>

<%
pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
%>

<html:form
	action="/eop/basedata/eop_basedata_company/eopBasedataCompany.do">
	<div id="optBarDiv">
		<c:choose>
			<c:when test="${eopBasedataCompanyForm.method_GET=='edit'}">
				<input type="button" value="${ lfn:message('button.update') }"
					onclick="Com_Submit(document.eopBasedataCompanyForm, 'update');">
			</c:when>
			<c:when test="${eopBasedataCompanyForm.method_GET=='add'}">
				<input type="button" value="${ lfn:message('button.save') }"
					onclick="Com_Submit(document.eopBasedataCompanyForm, 'save');">
				<input type="button" value="${ lfn:message('button.saveadd') }"
					onclick="Com_Submit(document.eopBasedataCompanyForm, 'saveadd');">
			</c:when>
		</c:choose>
		<input type="button" value="${ lfn:message('button.close') }"
			onclick="Com_CloseWindow();">
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
									showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_group_fdGroup','fdGroupId','fdGroupName');
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
							<xform:text property="fdName" showStatus="edit"
								style="width:95%;" />
						</div>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdIsAvailable')}</td>
					<td width="35%">
						<%-- 是否有效--%>
						<div id="_xform_fdIsAvailable" _xform_type="radio">
							<xform:radio property="fdIsAvailable"
								htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
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
							<xform:text property="fdIden" showStatus="edit" required="true"
								style="width:95%;" />
						</div>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.legalRepresentative')}
					</td>
					<td width="35%">
						<%-- 法定代表人--%>
						<div id="_xform_legalRepresentative" _xform_type="text">
							<xform:text property="legalRepresentative" showStatus="edit"
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
							<xform:text property="regAddress" showStatus="edit"
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
								propertyName="fdAccountCurrencyName" showStatus="edit"
								required="true"
								subject="${lfn:message('eop-basedata:eopBasedataCompany.fdAccountCurrency')}"
								style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdAccountCurrencyId','fdAccountCurrencyName');
                            </xform:dialog>
						</div>
					</td>
					<td class="td_normal_title" width="15%">
						${lfn:message('eop-basedata:eopBasedataCompany.fdEkpOrg')}</td>
					<td width="35.0%">
						<div id="_xform_fdEkpOrgIds" _xform_type="address">
							<xform:address propertyId="fdEkpOrgIds"
								propertyName="fdEkpOrgNames" mulSelect="true"
								orgType="ORG_TYPE_ORGORDEPT" showStatus="edit" required="true"
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
								<c:if test="${empty eopBasedataCompanyForm.fdCode}">
									<xform:text property="fdCode" showStatus="edit" required="true"
										style="width:95%;" />
								</c:if>
								<c:if test="${not empty eopBasedataCompanyForm.fdCode}">
									<xform:text property="fdCode" showStatus="view"
										style="width:95%;" />
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.fdType')}</td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdType" _xform_type="radio">
								<xform:radio property="fdType" showStatus="edit">
									<xform:enumsDataSource enumsType="eop_basedata_company_type" />
								</xform:radio>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.fdJoinSystem')}</td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdJoinSystem" _xform_type="text">
								<xform:radio property="fdJoinSystem"
									value="${eopBasedataCompanyForm.fdJoinSystem}"
									onValueChange="changeSystem">
									<c:forEach items="${financialSystemList}" var="system">
										<xform:simpleDataSource value="${system}"></xform:simpleDataSource>
									</c:forEach>
								</xform:radio>
							</div>
						</td>
					</tr>
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
								<br />
								<span style="color: red;">${lfn:message('eop-basedata:eopBasedataCompany.fdESlnName.message')}</span>
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
								<br />
								<span style="color: red;">${lfn:message('eop-basedata:eopBasedataCompany.fdEDcName.message')}</span>
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
								<br />
								<span style="color: red;">${lfn:message('eop-basedata:eopBasedataCompany.fdELanguage.message')}</span>
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
								<br />
								<span style="color: red;">${lfn:message('eop-basedata:eopBasedataCompany.fdEDbType.message')}</span>
							</div>
						</td>
					</tr>
					<tr style="display: none;" class="Eas">
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.fdEAuthPattern')}
						</td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdEAuthPattern" _xform_type="text">
								<xform:text property="fdEAuthPattern" style="width:95%;"
									validators="maxLength(50)"></xform:text>
								<br />
								<span style="color: red;">${lfn:message('eop-basedata:eopBasedataCompany.fdEAuthPattern.message')}</span>
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
								<xform:text property="fdEImportVoucherWsdlUrl"
									style="width:95%;" validators="maxLength(200)"></xform:text>
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



					<tr style="display: none;" id="systemParam">
						<td class="td_normal_title" width="15%" id="systemTitle"></td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdJoinSystem" _xform_type="text">
								<xform:text property="fdSystemParam" style="width:95%;"></xform:text>
							</div>
						</td>
					</tr>
					<kmss:ifModuleExist path="/fssc/inhand">
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.fdCabinetType')}
						</td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdCabinetType" _xform_type="radio">
								<xform:radio property="fdCabinetType" showStatus="edit">
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
									propertyName="fdBudgetCurrencyName" showStatus="edit"
									required="true"
									subject="${lfn:message('eop-basedata:eopBasedataCompany.fdBudgetCurrency')}"
									style="width:95%;">
                                dialogSelect(false,'eop_basedata_currency_fdCurrency','fdBudgetCurrencyId','fdBudgetCurrencyName');
                            </xform:dialog>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.fdDutyParagraph')}
						</td>
						<td colspan="3" width="85.0%">
							<div id="_xform_fdDutyParagraph" _xform_type="dialog">
								<xform:text property="fdDutyParagraph" showStatus="edit"
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
									orgType="ORG_TYPE_ALLORG" showStatus="edit" required="true"
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
									orgType="ORG_TYPE_ALLORG" showStatus="edit" required="true"
									subject="${lfn:message('eop-basedata:eopBasedataCompany.fdFinancialManager')}"
									textarea="true" style="width:95%;" />
							</div>
						</td>
					</tr>
				</table>
			</kmss:ifModuleExist>
				<div class="lui_paragraph_title">${ lfn:message('eop-basedata:py.HeTongZhuTiXinXi') }
				</div>
				<table class="tb_normal" width="100%">
					<tr>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.legalRepresentativeIden')}</td>
						<td width="35%">
							<div id="_xform_legalRepresentativeIden" _xform_type="text">
								<xform:text property="legalRepresentativeIden"
									validators="lrIdenValidator" showStatus="edit"
									style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.contactor')}</td>
						<td width="35%">
							<div id="_xform_contactorId" _xform_type="address">
								<xform:address propertyId="contactorId"
									propertyName="contactorName" orgType="ORG_TYPE_PERSON"
									showStatus="edit" style="width:95%;"
									onValueChange="jsEntry.onContactorChoosed" />
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
								<xform:text property="contactorMobileNo" showStatus="edit"
									style="width:95%;" />
							</div>
						</td>
						<td class="td_normal_title" width="15%">
							${lfn:message('eop-basedata:eopBasedataCompany.contactorEmail')}</td>
						<td width="35%">
							<%-- 联系人邮箱--%>
							<div id="_xform_contactorEmail" _xform_type="text">
								<xform:text property="contactorEmail" showStatus="edit"
									style="width:95%;" />
							</div>
						</td>
					</tr>
				</table>
		</div>
	</center>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
	<script>
		$KMSSValidation();
		Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath
				+ "eop/basedata/resource/js/", 'js', true);
	</script>
	<fssc:checkVersion version="false">
		<script>
			seajs.use([ 'lui/dialog', 'lang!eop-basedata' ], function(dialog,
					lang) {
				Com_Parameter.event["submit"].push(function() {
					var rtn = true;
					var account = $("input[name='fdAccountCurrencyId']").val();
					var budget = $("input[name='fdBudgetCurrencyId']").val();
					if (account && budget && account != budget) {
						dialog.alert(lang['message.currency.same']);
						rtn = false;
					}
					return rtn;
				});
			});
		</script>
	</fssc:checkVersion>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
