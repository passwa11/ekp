<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:replace name="head">
	<style type="text/css">
		.lui_paragraph_title {
			font-size: 15px;
			color: #15a4fa;
			padding: 15px 0px 5px 0px;
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
            	fdIsCloseFee:'${fsscExpenseMainForm.fdIsCloseFee}',
            	docStatus:'${fsscExpenseMainForm.docStatus}',
            };
            var messageInfo = {

            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("doclist.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_main/", 'js', true);
            Com_IncludeFile("fsscExpenseMain_submitEvent.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_main/", 'js', true);
       		Com_IncludeFile("fsscExpenseMain_edit.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_main/", 'js', true);
       		Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
        </script>
        <script>Com_IncludeFile('detailsTableFreeze2.css', "${LUI_ContextPath}/fssc/expense/resource/css/", 'css', true);</script>
		<script>Com_IncludeFile('detailsTableFreeze2.js', "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);</script>
        <kmss:ifModuleExist path="/fssc/budget">
        	<script>Com_IncludeFile("submit_common_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);</script>
        </kmss:ifModuleExist>
        <link rel="stylesheet" href="../resource/layui/css/layui.css"  media="all">
        <script src="../resource/layui/layui.js" charset="utf-8"></script>

</template:replace>

<template:replace name="title">
	<c:choose>
		<c:when test="${fsscExpenseMainForm.method_GET == 'add' }">
			<c:out
				value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-expense:table.fsscExpenseMain') }" />
		</c:when>
		<c:otherwise>
			<c:out value="${fsscExpenseMainForm.docSubject} - " />
			<c:out value="${ lfn:message('fssc-expense:table.fsscExpenseMain') }" />
		</c:otherwise>
	</c:choose>
</template:replace>
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
		<c:choose>
			<c:when test="${ fsscExpenseMainForm.method_GET == 'edit' }">
				<c:if
					test="${ fsscExpenseMainForm.docStatus=='10' || fsscExpenseMainForm.docStatus=='11' }">
					<ui:button text="${ lfn:message('button.savedraft') }"
						onclick="submitForm('10','update',true);" />
				</c:if>
				<c:if
					test="${ fsscExpenseMainForm.docStatus=='10' || fsscExpenseMainForm.docStatus=='11' || fsscExpenseMainForm.docStatus=='20' }">
					<ui:button text="${ lfn:message('button.submit') }"
						onclick="submitForm('20','update');" />
				</c:if>
			</c:when>
			<c:when test="${ fsscExpenseMainForm.method_GET == 'add' }">
				<ui:button text="${ lfn:message('button.savedraft') }" order="2"
					onclick="submitForm('10','save',true);" />
				<ui:button text="${ lfn:message('button.submit') }" order="2"
					onclick="submitForm('20','save');" />
			</c:when>
		</c:choose>

		<ui:button text="${ lfn:message('button.close') }" order="5"
			onclick="Com_CloseWindow();" />
	</ui:toolbar>
</template:replace>
<template:replace name="path">
	<ui:menu layout="sys.ui.menu.nav">
		<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
		<ui:menu-item text="${ lfn:message('fssc-expense:table.fsscExpenseMain') }" />
	    <ui:menu-item text="${docTemplateName }"  />
	</ui:menu>
</template:replace>
<template:replace name="content">
<c:import url="/sys/recycle/import/redirect.jsp">
	<c:param name="formBeanName" value="fsscExpenseMainForm"></c:param>
</c:import>
<c:if test="${param.approveType ne 'right' }">
	<form action="${LUI_ContextPath }/fssc/expense/fssc_expense_main/fsscExpenseMain.do" name="fsscExpenseMainForm" method="post">
</c:if>
	<ui:tabpage expand="false" collapsed="true" id="reviewTabPage"
		var-navwidth="90%">
		<c:if test="${param.approveType eq 'right'}">
		<script>
					LUI.ready(function(){
						setTimeout(function(){
							var reviewTabPage = LUI("reviewTabPage");
							if(reviewTabPage!=null){
								reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
								reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
							}
						},100)
					})
				</script>
		</c:if>
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<c:if test="${empty fsscExpenseMainForm.docSubject }">
                		${ docTemplate.fdName}
                	</c:if>
                	<c:if test="${not empty fsscExpenseMainForm.docSubject }">
                		${ fsscExpenseMainForm.docSubject}
                	</c:if>
                </div>
			<div class='lui_form_baseinfo'></div>
		</div>
		<table class="tb_normal" width="100%">
			<tr>
				<td class="td_normal_title" width="16.6%">
					${lfn:message('fssc-expense:fsscExpenseMain.docSubject')}</td>
				<td colspan="5">
					<div id="_xform_docSubject" _xform_type="address">
						<c:if test="${docTemplate.fdSubjectType=='1' }">
							<xform:text property="docSubject" style="width:95%"></xform:text>
						</c:if>
						<c:if test="${docTemplate.fdSubjectType=='2' }">
							<span style="color: #888;">${lfn:message('fssc-expense:py.BianHaoShengCheng') }</span>
						</c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="16.6%">
					${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}</td>
				<td width="16.6%">
					<div id="_xform_fdClaimantId" _xform_type="address">
						<!-- 启用提单转授权 -->
						<c:if test="${fdIsAuthorize=='true'}">
							<xform:dialog propertyId="fdClaimantId" propertyName="fdClaimantName" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}" style="width:95%;">
	                            dialogSelect(false,'fssc_expense_selectAuthorize','fdClaimantId','fdClaimantName',null,null,clearInfoWhenCliamntChanged);
	                        </xform:dialog>
						</c:if>
						<!-- 未启用提单转授权 -->
						<c:if test="${fdIsAuthorize=='false'}">
							<xform:address propertyId="fdClaimantId" propertyName="fdClaimantName" onValueChange="clearInfoWhenCliamntChanged" orgType="ORG_TYPE_PERSON" showStatus="readonly" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdClaimant')}" style="width:95%;" />
						</c:if>
					</div>
				</td>
				<td class="td_normal_title" width="16.6%">
					${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}</td>
				<td width="16.6%">
					<div id="_xform_fdCompanyId" _xform_type="dialog">
						<xform:dialog propertyId="fdCompanyId"
							propertyName="fdCompanyName" showStatus="edit" required="true"
							subject="${lfn:message('fssc-expense:fsscExpenseMain.fdCompany')}"
							style="width:95%;">
                                            dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',null,{fdPersonId:$('[name=fdClaimantId]').val(),fdModelName:'com.landray.kmss.fssc.expense.model.FsscExpenseMain'},clearDetailWhenCompanyChanged);
                                        </xform:dialog>
						<html:hidden property="fdCompanyIdOld"
							value="${fsscExpenseMainForm.fdCompanyId }" />
						<html:hidden property="fdCompanyNameOld"
							value="${fsscExpenseMainForm.fdCompanyName }" />
					</div>
				</td>
				<%-- <td class="td_normal_title" width="16.6%">
					${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}</td>
				<td width="16.6%">
					<div id="_xform_fdCostCenterId" _xform_type="dialog">
						<xform:dialog propertyId="fdCostCenterId"
							propertyName="fdCostCenterName" showStatus="edit" required="true"
							subject="${lfn:message('fssc-expense:fsscExpenseMain.fdCostCenter')}"
							style="width:95%;">
                                            selectCostCenterMain()
                                        </xform:dialog>
					</div>
				</td> --%>
				<td class="td_normal_title" width="16.6%" style="display: none">
					${lfn:message('fssc-expense:fsscExpenseMain.fdExpenseDept')}</td>
				<td width="16.6%" style="display: none">
					<div id="_xform_fdExpenseDeptId" _xform_type="text">
	                  <xform:text property="fdExpenseDeptName" style="width:85%" showStatus="readOnly"></xform:text>
	                  <xform:text property="fdExpenseDeptId" style="width:85%" showStatus="noShow"></xform:text>
	                        
					</div>
				</td>
			</tr>
			<c:if test="${docTemplate.fdIsFee=='true' }">
				<tr>
					<td class="td_normal_title" width="16.6%">
						${lfn:message('fssc-expense:fsscExpenseMain.fdFeeNames')}</td>
					<td colspan="5" width="83.0%" >
						<div id="_xform_fdContent" _xform_type="textarea">
							<xform:dialog propertyName="fdFeeNames" propertyId="fdFeeIds"
								style="width:85%;height:50px;" required="true"
								textarea="true"
								subject="${lfn:message('fssc-expense:fsscExpenseMain.fdFeeNames')}">
                                        	dialogSelect(true,'fssc_expense_main_selectFee','fdFeeIds','fdFeeNames',null,{'docTemplateId':$('[name=docTemplateId]').val(),'fdPersonId':$('[name=fdClaimantId]').val()});
                            </xform:dialog>
                            <div id="closeFeeMain" style="margin-top:12px;float:right;margin-left:-10px;">
						&nbsp &nbsp
							<xform:checkbox property="fdIsCloseFee" value="${fsscExpenseMainForm.fdIsCloseFee}" onValueChange="checkFeeRelation">
								<xform:simpleDataSource value="1">${lfn:message('fssc-expense:fsscExpenseMain.fdIsCloseFee')}</xform:simpleDataSource>
							</xform:checkbox>
						</div>
						</div>
					</td>
				</tr>
			</c:if>
			<c:if test="${docTemplate.fdIsProapp=='true' }">
				<tr>
					<td class="td_normal_title" width="16.6%">
						${lfn:message('fssc-expense:fsscExpenseMain.fdProappName')}</td>
					<td colspan="5" width="83.0%">
						<div id="_xform_fdContent" _xform_type="textarea">
							<xform:dialog propertyName="fdProappName" propertyId="fdProappId" style="width:85%;" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdProappName')}">
                                 dialogSelect(false,'fssc_expense_main_selectProapp','fdProappId','fdProappName',null,{'fdCompanyId':$('[name=fdCompanyId]').val()},FSSC_AfterProappChanged);
                            </xform:dialog>
						</div>
					</td>
				</tr>
			</c:if>
			<c:if test="${docTemplate.fdIsAmortize=='true' }">
				<tr>
					<td class="td_normal_title" width="16.6%">
						${lfn:message('fssc-expense:fsscExpenseMain.fdIsAmortize')}</td>
					<td colspan="5" width="83.0%">
						<div id="_xform_fdContent" _xform_type="textarea">
							<xform:radio property="fdIsAmortize"
								onValueChange="FSSC_ChangeIsAmortize" required="true">
								<xform:enumsDataSource enumsType="common_yesno" />
							</xform:radio>
						</div>
					</td>
				</tr>
			</c:if>
			<c:if test="${docTemplate.fdIsProject=='true'&&(docTemplate.fdIsProjectShare=='false' or empty docTemplate.fdIsProjectShare) }">
				<tr>
					<td class="td_normal_title" width="16.6%">
						${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}
					</td>
					<td colspan="5" width="83.0%">
						<div id="_xform_fdContent" _xform_type="textarea">
							<xform:dialog propertyName="fdProjectName" propertyId="fdProjectId" style="width:85%;" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdProject')}">
                                 selectProjectMain()
                            </xform:dialog>
						</div>
					</td>
				</tr>
			</c:if>
			<c:if test="${fn:indexOf(docTemplate.fdExtendFields,'8')>-1 }">
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-expense:fsscExpenseMain.fdProjectAccounting')}
                </td>
                <td colspan="5" width="83.0%">
                    <div id="_xform_fdContent" _xform_type="textarea">
                        <xform:dialog propertyName="fdProjectAccountingName" propertyId="fdProjectAccountingId" style="width:85%;" required="true" subject="${lfn:message('fssc-expense:fsscExpenseMain.fdProjectAccounting')}">
                        	selectCostProject()
                        </xform:dialog>
                    </div>
                </td>
            </tr>
            </c:if>
		</table>
		<c:if test="${param.approveType ne 'right'}">
			<c:if test="${fsscExpenseMainForm.docUseXform == 'true' || empty fsscExpenseMainForm.docUseXform}">
				<c:import url="/sys/xform/include/sysForm_edit.jsp"
						  charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseMainForm" />
					<c:param name="fdKey" value="fsscExpenseMain" />
					<c:param name="useTab" value="false" />
				</c:import>
			</c:if>
		</c:if>
				<table class="tb_normal" width="100%">
<tr>
				<td class="td_normal_title" width="16.6%">
					${lfn:message('fssc-expense:fsscExpenseMain.fdContent')}</td>
				<td colspan="5" width="83.0%">
					<div id="_xform_fdContent" _xform_type="textarea">
						<xform:textarea property="fdContent" required="true" showStatus="edit" style="width:95%;height:50px;" />
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('fssc-expense:fsscExpenseMain.attachment')}
				</td>
				<td colspan="5" width="83.0%">
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="invoice" />
						<c:param name="fdModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
					</c:import>
				</td>
			</tr>
			</table>
		<c:if test="${docTemplate.fdExpenseType eq '2' and docTemplate.fdIsTravelAlone eq 'true'}">
			<c:import url="/fssc/expense/fssc_expense_travel_detail/fsscExpenseTravelDetail_edit_include.jsp"></c:import>
		</c:if>

		<ui:content title="${lfn:message('fssc-expense:fsscExpenseMain.fdpanel.detail')}" expand="true" toggle="false">
			<ui:tabpanel id="tabpanel" layout="sys.ui.tabpanel.simple"
				scroll="true" suckTop="false" channel="">
				<c:import url="/fssc/expense/fssc_expense_detail/fsscExpenseDetail_edit_include.jsp"></c:import>
<%-- 				<c:import url="/fssc/expense/fssc_expense_invoice_detail/fsscExpenseInvoiceDetail_edit_include.jsp" ></c:import>
 --%>				<kmss:ifModuleExist path="/fssc/loan">
<%-- 					<c:import url="/fssc/expense/fssc_expense_offset_detail/fsscExpenseOffsetDetail_edit_include.jsp"></c:import>
 --%>				</kmss:ifModuleExist>
<%-- 				<c:import url="/fssc/expense/fssc_expense_accounts_detail/fsscExpenseAccountsDetail_edit_include.jsp"></c:import>
 --%>				<kmss:ifModuleExist path="/fssc/didi">
				<c:import url="/fssc/expense/fssc_expense_didi_detail/fsscExpenseDidiDetail_edit_include.jsp"></c:import>
				</kmss:ifModuleExist>
				<kmss:ifModuleExist path="/fssc/ccard">
					<c:import url="/fssc/expense/fssc_expense_tran_data/fsscExpenseTranData_edit_include.jsp"></c:import>
				</kmss:ifModuleExist>
			</ui:tabpanel>
		</ui:content>
		<c:if test="${param.approveType ne 'right'}">
			<%--<c:if test="${fsscExpenseMainForm.docUseXform == 'true' || empty fsscExpenseMainForm.docUseXform}">
				<ui:content title="${lfn:message('fssc-expense:py.BiaoDanNeiRong')}" expand="true">
					<c:import url="/sys/xform/include/sysForm_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="fsscExpenseMainForm" />
						<c:param name="fdKey" value="fsscExpenseMain" />
						<c:param name="useTab" value="false" />
					</c:import>
				</ui:content>
			</c:if>--%>
			<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_provision_include.jsp"></c:import>
			<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_baseInfo.jsp"></c:import>
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseMainForm" />
				<c:param name="fdKey" value="fsscExpenseMain" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
			</c:import>
	     <%--权限 --%>
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	           <c:param name="formName" value="fsscExpenseMainForm" />
	           <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
	    </c:import>
		</c:if>
	</ui:tabpage>
	<c:if test="${param.approveType eq 'right'}">
		<ui:tabpanel suckTop="false" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
			<c:if test="${fsscExpenseMainForm.docUseXform == 'true' || empty fsscExpenseMainForm.docUseXform}">
				<ui:content title="${lfn:message('fssc-expense:py.BiaoDanNeiRong')}" expand="true">
					<c:import url="/sys/xform/include/sysForm_edit.jsp"
						charEncoding="UTF-8">
						<c:param name="formName" value="fsscExpenseMainForm" />
						<c:param name="fdKey" value="fsscExpenseMain" />
						<c:param name="useTab" value="false" />
					</c:import>
				</ui:content>
			</c:if>
			<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_provision_include.jsp"></c:import>
			<c:import url="/fssc/expense/fssc_expense_main/fsscExpenseMain_baseInfo.jsp"></c:import>
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseMainForm" />
				<c:param name="fdKey" value="fsscExpenseMain" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
			</c:import>
			<%--权限 --%>
		<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	           <c:param name="formName" value="fsscExpenseMainForm" />
	           <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
	    </c:import>
		</ui:tabpanel>
	</c:if>
	<html:hidden property="fdId" />
	<html:hidden property="docStatus" />
	<html:hidden property="docTemplateId" />
	<html:hidden property="method_GET" />
	<html:hidden property="fdExpenseType" value="${docTemplate.fdExpenseType }" />
	<html:hidden property="fdAllocType" value="${docTemplate.fdAllocType }" />
	<html:hidden property="fdBudgetShowType" value="${docTemplate.fdBudgetShowType }" />
	<html:hidden property="fdLedgerInvoiceId" />
	<html:hidden property="fdIsTravelAlone" value="${docTemplate.fdIsTravelAlone }"/>
	<html:hidden property="fdIsProjectShare" value="${docTemplate.fdIsProjectShare }"/>
	<html:hidden property="fdDeduFlag" value="${fdDeduFlag}"/>
	<html:hidden property="fdExtendField" value="${docTemplate.fdExtendFields}"/>
	<html:hidden property="fdIsTransfer" value="${fdIsTransfer}"/>
	<fssc:checkVersion version="true">
		<html:hidden property="checkVersion" value="true" />
	</fssc:checkVersion>
	<input name="feeLedgerObj" value='${feeLedgerObj}'  type="hidden"  />
	<input name="budgetObj" value='${budgetObj}'  type="hidden"  />
	<!-- 待审编辑主文档 -->
	<c:if test="${fsscExpenseMainForm.method_GET=='edit' and fsscExpenseMainForm.docStatus=='20'}">
		<input type="hidden" name="edit_examine" value="true"/>
	</c:if>
   	<script>
   		Com_IncludeFile("fsscExpenseDetail_edit.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_main/", 'js', true);
   		Com_IncludeFile("quickSelect.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
   	</script>
   	<c:if test="${param.approveType ne 'right' }">
   	</form>
   	</c:if>
</template:replace>
<c:if test="${param.approveType eq 'right' }">
	<template:replace name="barRight">
		<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseMainForm" />
				<c:param name="fdKey" value="fsscExpenseMain" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
				<c:param name="approvePosition" value="right" />
				<c:param name="needInitLbpm" value="true" />
			</c:import>
			<!-- 关联机制 -->
			<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseMainForm" />
				<c:param name="approveType" value="right" />
				<c:param name="needTitle" value="true" />
			</c:import>
		</ui:tabpanel>
	</template:replace>
</c:if>
<c:if test="${param.approveType ne 'right'}">
	<template:replace name="nav">
		<%--关联机制--%>
		<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
			<c:param name="formName" value="fsscExpenseMainForm" />
		</c:import>
	</template:replace>
</c:if>