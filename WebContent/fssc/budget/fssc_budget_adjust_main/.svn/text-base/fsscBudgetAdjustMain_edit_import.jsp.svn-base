<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/fssc/budget/budget.tld" prefix="budget"%>
    <template:replace name="head">
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
        <link href="${LUI_ContextPath}/fssc/common/resource/css/common.css"  rel="stylesheet" />
        <script type="text/javascript">
            var formInitData = {
				"fdSchemePeriod":"${fdSchemePeriod}",
				"adjustType":"${adjustType}"
            };
            var messageInfo = {

            };
            //右侧审批模式下，隐藏底部栏
            if('${param.approveModel}'=='right'){
            	LUI.ready(function(){
    				setTimeout(function(){
    					$(".lui_tabpage_frame").prop("style","display:none;");
    				},100)
    			})
            }
            Com_IncludeFile("security.js|doclist.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
            Com_IncludeFile("fsscBudgetAdjust.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_adjust_main/", 'js', true);
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_adjust_main/", 'js', true);
        </script>
    </template:replace>
    <c:if test="${fsscBudgetAdjustMainForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscBudgetAdjustMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-budget:table.fsscBudgetAdjustMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscBudgetAdjustMainForm.docSubject} - " />
                    <c:out value="${ lfn:message('fssc-budget:table.fsscBudgetAdjustMain') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:if test="${param.approveModel eq 'right'}">
                   <c:if test="${ fsscBudgetAdjustMainForm.method_GET == 'edit' }">
                       <c:if test="${ fsscBudgetAdjustMainForm.docStatus=='10' || fsscBudgetAdjustMainForm.docStatus=='11' }">
                           <ui:button text="${ lfn:message('button.savedraft') }" onclick="submitForm('10','update',true);" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                       </c:if>
                       <c:if test="${ fsscBudgetAdjustMainForm.docStatus=='10' || fsscBudgetAdjustMainForm.docStatus=='11' || fsscBudgetAdjustMainForm.docStatus=='20' }">
                           <ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                       </c:if>
                   </c:if>
                   <c:if test="${ fsscBudgetAdjustMainForm.method_GET == 'add' }">
                       <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save',true);" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                       <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="submitForm('20','save');" styleClass="lui_widget_btn_primary" isForcedAddClass="true" />
                   </c:if>
			</c:if>
            <c:if test="${param.approveModel ne 'right'}">
                   <c:if test="${ fsscBudgetAdjustMainForm.method_GET == 'edit' }">
                       <c:if test="${ fsscBudgetAdjustMainForm.docStatus=='10' || fsscBudgetAdjustMainForm.docStatus=='11' }">
                           <ui:button text="${ lfn:message('button.savedraft') }" onclick="submitForm('10','update',true);" />
                       </c:if>
                       <c:if test="${ fsscBudgetAdjustMainForm.docStatus=='10' || fsscBudgetAdjustMainForm.docStatus=='11' || fsscBudgetAdjustMainForm.docStatus=='20' }">
                           <ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
                       </c:if>
                   </c:if>
                   <c:if test="${ fsscBudgetAdjustMainForm.method_GET == 'add' }">
                       <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save',true);" />
                       <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="submitForm('20','save');" />
                   </c:if>
			</c:if>
                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-budget:table.fsscBudgetAdjustMain') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
			<c:if test="${param.approveModel ne 'right'}">
	            <form action="${LUI_ContextPath }/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do"  name="fsscBudgetAdjustMainForm" method="post">
			</c:if>
                <ui:tabpage expand="false" var-navwidth="90%">
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docSubject')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <div id="_xform_docSubject" _xform_type="text">
                                        <c:if test="${docTemplate.fdSubjectType=='1' }">
                                            <xform:text property="docSubject" showStatus="edit" style="width:95%;" />
                                        </c:if>
                                        <c:if test="${docTemplate.fdSubjectType=='2' }">
                                            <span style="color: #888;">${lfn:message('fssc-budget:py.BianHaoShengCheng') }</span>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                            <c:set var="containCompany" value="false"></c:set>
	                    	<budget:budgetScheme fdSchemeId="${HtmlParam['i.fdBudgetScheme']}" type="dimension" value="2">
	                    		<c:set var="containCompany" value="true"></c:set>
	                    	</budget:budgetScheme>
	                    	<%-- 维度包含公司 --%>
                    		<c:if test="${containCompany}">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCompany')}
                                </td>
                                <td width="35%">
                                    <div id="_xform_fdCompanyId" _xform_type="dialog">
                                        <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" subject="${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCompany') }" showStatus="edit" style="width:95%;">
                                            dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',afterSelectCompany);
                                        </xform:dialog>
                                        <xform:text property="fdOldCompanyId" showStatus="noShow" value="${fsscBudgetAdjustMainForm.fdCompanyId}"></xform:text>
                                    </div>
                                </td>
                                 <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCurrency')}
                                </td>
                                <td width="35%">
                                    <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                        <xform:text property="fdCurrencyName" showStatus="readOnly" style="color:#333;"></xform:text>
                                        <xform:text property="fdCurrencyId" showStatus="noShow"></xform:text>
                                    </div>
                                </td>
                            </tr>
                            </c:if>
                            <%-- 维度不包含公司 --%>
	                    	<c:if test="${!containCompany}">
	                    		<tr>
	                                 <td class="td_normal_title" width="15%">
	                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdCurrency')}
	                                </td>
	                                <td width="85%" colspan="3">
	                                    <div id="_xform_fdCurrencyId" _xform_type="dialog">
	                                        <xform:text property="fdCurrencyName" showStatus="readOnly" style="color:#333;"></xform:text>
	                                        <xform:text property="fdCurrencyId" showStatus="noShow"></xform:text>
	                                    </div>
	                                </td>
	                            </tr>
	                    	</c:if>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docTemplate')}
                                </td>
                                <td width="35%">
                                    <div id="_xform_docTemplateId" _xform_type="dialog">
                                        <xform:dialog propertyId="docTemplateId" propertyName="docTemplateName" showStatus="view" style="width:95%;">
                                        </xform:dialog>
                                        <xform:text property="fdSchemeType" value="${adjustType}" showStatus="noShow"></xform:text>
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdBudgetScheme')}
                                </td>
                                <td width="35%">
                                    <div id="_xform_docTemplateId" _xform_type="dialog">
                                        <c:if test="${empty param['i.fdBudgetScheme'] }">
                                            <xform:dialog propertyId="fdBudgetSchemeId" propertyName="fdBudgetSchemeName" subject="${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdBudgetScheme') }"  required="true" showStatus="edit" style="width:95%;">
                                                dialogSelect(false,'fssc_base_fdBudgetScheme','fdBudgetSchemeId','fdBudgetSchemeName',afterSelectBudgetScheme);
                                            </xform:dialog>
                                        </c:if>
                                        <c:if test="${not empty param['i.fdBudgetScheme'] }">
                                            <xform:dialog propertyId="fdBudgetSchemeId" propertyName="fdBudgetSchemeName" showStatus="view" style="width:95%;">
                                            </xform:dialog>
                                            <xform:text property="fdBudgetSchemeId" showStatus="noShow"></xform:text>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" width="100%">
                                    <c:import url="/fssc/budget/fssc_budget_adjust_detail/fsscBudgetAdjustDetail_edit.jsp" charEncoding="UTF-8"></c:import>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.fdDesc')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <div id="_xform_fdDesc" _xform_type="textarea">
                                        <xform:textarea property="fdDesc" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.attBudget')}
                                </td>
                                <td colspan="3" width="85.0%">
                                    <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
                                        <c:param name="fdKey" value="attBudget" />
                                        <c:param name="formBeanName" value="fsscBudgetAdjustMainForm" />
                                        <c:param name="fdMulti" value="true" />
                                    </c:import>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreator')}
                                </td>
                                <td width="35%">
                                    <div id="_xform_docCreatorId" _xform_type="address">
                                        <ui:person personId="${fsscBudgetAdjustMainForm.docCreatorId}" personName="${fsscBudgetAdjustMainForm.docCreatorName}" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-budget:fsscBudgetAdjustMain.docCreateTime')}
                                </td>
                                <td width="35%">
                                    <div id="_xform_docCreateTime" _xform_type="datetime">
                                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                     <c:if test="${param.approveModel ne 'right'}">
	                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
	                        <c:param name="formName" value="fsscBudgetAdjustMainForm" />
	                        <c:param name="fdKey" value="fsscBudgetAdjustMain" />
	                        <c:param name="isExpand" value="true" />
	                    </c:import>
	                    <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                        <c:param name="formName" value="fsscBudgetAdjustMainForm" />
	                        <c:param name="moduleModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
	                    </c:import>
					</c:if>
					<c:if test="${param.approveModel eq 'right'}">
		                 <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
							<%--流程--%>
							<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="fsscBudgetAdjustMainForm" />
								<c:param name="fdKey" value="fsscBudgetAdjustMain" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
							</c:import>
							<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
		                        <c:param name="formName" value="fsscBudgetAdjustMainForm" />
		                        <c:param name="moduleModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
		                    </c:import>
						</ui:tabpanel>
	                 </c:if>

                </ui:tabpage>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <input type="hidden" name="IsDraft" />
                <input value="${fsscBudgetAdjustMainForm.docTemplateId}" type="hidden" name="docTemplateId" />
                                <input value="${fsscBudgetAdjustMainForm.docTemplateName}" type="hidden" name="docTemplateName" />
                
                <html:hidden property="method_GET" />
            <c:if test="${param.approveModel ne 'right'}">
            	</form>
            </c:if>
            <script src="${LUI_ContextPath }/eop/basedata/resource/js/importDetail.js"></script>
            <script>Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);</script>
        </template:replace>
		<c:if test="${param.approveModel eq 'right'}">
			<template:replace name="barRight">
				<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
					<%--流程--%>
					<c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscBudgetAdjustMainForm" />
						<c:param name="fdKey" value="fsscBudgetAdjustMain" />
						<c:param name="isExpand" value="true" />
						<c:param name="approveType" value="right" />
						<c:param name="approvePosition" value="right" />
						<c:param name="needInitLbpm" value="true" />
					</c:import>
					<!-- 基本信息-->
				<c:import url="/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain_viewBaseInfoContent.jsp" charEncoding="UTF-8">
				</c:import>
				</ui:tabpanel>
			</template:replace>
		</c:if>
    </c:if>
