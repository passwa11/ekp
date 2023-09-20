<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

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
        <script type="text/javascript">
            var formInitData = {

            };
            var messageInfo = {

            };

            var initData = {
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_balance/", 'js', true);
            Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            Com_IncludeFile("fsscExpenseBalance_edit.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_balance/", 'js', true);
            seajs.use(['lui/dialog','lang!fssc-expense'],function(dialog,lang){
            	Com_Parameter.event.submit.push(function(){
            		if($("[name=docStatus]").val()=='10'){
            			return true;
            		}
                	var JM=0,DM=0;
                	$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(){
                		if($(this).find("[name$=fdType]").val()=='1'){
                			JM = numAdd(JM,$(this).find("[name$=fdMoney]").val()*1);
                		}
                		if($(this).find("[name$=fdType]").val()=='2'){
                			DM = numAdd(DM,$(this).find("[name$=fdMoney]").val()*1);
                		}
                	});
                	if(JM!=DM){
                		dialog.alert(lang['tips.balance.moneyNotEqual']);
                	}
                	return JM==DM;
                })
            })
            <fssc:switchOn property="fdRebudget">
            Com_Parameter.event.submit.push(function(){
            	var params = [];
            	$("#TABLE_DocList_fdDetailList_Form tr:gt(0)").each(function(i){
            		params.push({
            			index:i,
            			fdCompanyId:$("[name$=fdCompanyId]").val(),
            			fdPersonId:$(this).find("[name$=fdPersonId]").val(),
            			fdDeptId:$(this).find("[name$=fdDeptId]").val(),
            			fdExpenseItemId:$(this).find("[name$=fdExpenseItemId]").val(),
            			fdCostCenterId:$(this).find("[name$=fdCostCenterId]").val(),
            			fdPersonId:$(this).find("[name$=fdPersonId]").val(),
            			fdProjectId:$(this).find("[name$=fdProjectId]").val(),
            			fdCurrencyId:$("[name=fdCurrencyId]").val(),
            			fdBudgetMoney:$(this).find("[name$=fdBudgetMoney]").val()
            		});
            	});
            	$.ajax({
            		url:'${LUI_ContextPath}/fssc/expense/fssc_expense_balance/fsscExpenseBalance.do?method=getBudgetData',
            		data:{data:JSON.stringify(params)},
            		async:false,
            		success:function(rtn){
            			rtn = JSON.parse(rtn);
           				for(var i in rtn){
           					$("[name='fdDetailList_Form["+i+"].fdBudgetInfo']").val(JSON.stringify(rtn[i]||'[]').replace(/\"/g,"'"));
           				}
            		}
            	});
            	return true;
            });
            </fssc:switchOn>
        </script>
    </template:replace>

    <template:replace name="title">
        <c:choose>
            <c:when test="${fsscExpenseBalanceForm.method_GET == 'add' }">
                <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-expense:table.fsscExpenseBalance') }" />
            </c:when>
            <c:otherwise>
                <c:out value="${fsscExpenseBalanceForm.docSubject} - " />
                <c:out value="${ lfn:message('fssc-expense:table.fsscExpenseBalance') }" />
            </c:otherwise>
        </c:choose>
    </template:replace>
    <template:replace name="toolbar">
        <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            <c:choose>
                <c:when test="${ fsscExpenseBalanceForm.method_GET == 'edit' }">
                    <c:if test="${ fsscExpenseBalanceForm.docStatus=='10' || fsscExpenseBalanceForm.docStatus=='11' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" onclick="submitForm('10','update',true);" />
                    </c:if>
                    <c:if test="${ fsscExpenseBalanceForm.docStatus=='10' || fsscExpenseBalanceForm.docStatus=='11' || fsscExpenseBalanceForm.docStatus=='20' }">
                        <ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
                    </c:if>
                </c:when>
                <c:when test="${ fsscExpenseBalanceForm.method_GET == 'add' }">
                    <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save',true);" />
                    <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="submitForm('20','save');" />
                </c:when>
            </c:choose>

            <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
        </ui:toolbar>
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
            <ui:menu-item text="${ lfn:message('fssc-expense:table.fsscExpenseBalance') }" />
             <ui:menu-item text="${docTemplateName }"  />
        </ui:menu>
    </template:replace>
    <template:replace name="content">
        <c:if test="${param.approveType ne 'right'}">
            <form action="${LUI_ContextPath }/fssc/expense/fssc_expense_balance/fsscExpenseBalance.do" name="fsscExpenseBalanceForm"  method="post">
			</c:if>

            <ui:tabpage expand="false" var-navwidth="90%" collapsed="true" id="reviewTabPage">
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
                <ui:content title="${ lfn:message('fssc-expense:py.JiBenXinXi') }" expand="true">
                    <div class='lui_form_title_frame'>
                        <div class='lui_form_subject'>
                           	<c:if test="${empty fsscExpenseBalanceForm.docSubject }">
                            		${ docTemplate.fdName}
                            	</c:if>
                            	<c:if test="${not empty fsscExpenseBalanceForm.docSubject }">
                            		${ fsscExpenseBalanceForm.docSubject}
                            	</c:if>
                        </div>
                        <div class='lui_form_baseinfo'>

                        </div>
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
                                ${lfn:message('fssc-expense:fsscExpenseBalance.docCreator')}
                            </td>
                            <td width="16.6%">
                                <%-- 经办人--%>
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscExpenseBalanceForm.docCreatorId}" personName="${fsscExpenseBalanceForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.docCreatorDept')}
                            </td>
                            <td width="16.6%">
                                <%-- 经办人部门--%>
                                <div id="_xform_docCreatorDeptId" _xform_type="address">
                                    <xform:address propertyId="docCreatorDeptId" propertyName="docCreatorDeptName" orgType="ORG_TYPE_ORGORDEPT" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.docCreateTime')}
                            </td>
                            <td width="16.6%">
                                <%-- 创建日期--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}
                            </td>
                            <td width="16.6%">
                                <%-- 费用公司--%>
                                <div id="_xform_fdCompanyId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalance.fdCompany')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',null,{fdModelName:'com.landray.kmss.fssc.expense.model.FsscExpenseMain'},clearDetailWhenCompanyChanged);
                                    </xform:dialog>
                                    <html:hidden property="fdCompanyIdOld" value="${fsscExpenseBalanceForm.fdCompanyId }" />
									<html:hidden property="fdCompanyNameOld" value="${fsscExpenseBalanceForm.fdCompanyName }" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}
                            </td>
                            <td width="16.6%">
                                <%-- 成本中心--%>
                                <div id="_xform_fdCostCenterId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCostCenterId" propertyName="fdCostCenterName" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalance.fdCostCenter')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_cost_center_selectCostCenter','fdCostCenterId','fdCostCenterName',null,{fdCompanyId:$('[name=fdCompanyId]').val()});
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.fdAttNum')}
                            </td>
                            <td width="16.6%">
                                <%-- 附件(张)--%>
                                <div id="_xform_fdAttNum" _xform_type="text">
                                    <xform:text property="fdAttNum" showStatus="edit" validators=" digits min(0)" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.fdVoucherType')}
                            </td>
                            <td width="16.6%">
                                <%-- 凭证类型--%>
                                <div id="_xform_fdVoucherTypeId" _xform_type="radio">
                                    <xform:dialog propertyId="fdVoucherTypeId" propertyName="fdVoucherTypeName" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalance.fdVoucherType')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_voucher_type_selectVoucherType','fdVoucherTypeId','fdVoucherTypeName',null,{fdCompanyId:$('[name=fdCompanyId]').val()});
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.fdCurrency')}
                            </td>
                            <td width="16.6%">
                                <%-- 币种--%>
                                <div id="_xform_fdCurrencyId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdCurrencyId" propertyName="fdCurrencyName" showStatus="readOnly" required="true" subject="${lfn:message('fssc-expense:fsscExpenseBalance.fdCurrency')}" style="width:95%;">
                                        dialogSelect(false,'eop_basedata_currency_fdCurrency','fdCurrencyId','fdCurrencyName');
                                    </xform:dialog>
                                </div>
                            </td>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.fdMonth')}
                            </td>
                            <td width="16.6%">
                                <%-- 月份--%>
                                <div id="_xform_fdMonth" _xform_type="text">
                                    <xform:text property="fdMonth" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-expense:fsscExpenseBalance.fdSubject')}
                            </td>
                            <td colspan="5" width="83.0%">
                                <%-- 凭证抬头文本--%>
                                <div id="_xform_fdSubject" _xform_type="text">
                                    <xform:text property="fdSubject" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
				            <td class="td_normal_title" width="16.6%">
				                  ${lfn:message('fssc-expense:fsscExpenseBalance.attachment')}</td>
				            <td colspan="5" width="83.0%">
						        <%-- 附件--%>
						  <c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
						          <c:param name="fdKey" value="attBalance" />
						          <c:param name="formBeanName" value="fsscExpenseBalanceForm" />
						          <c:param name="fdMulti" value="true" />
						  </c:import>
				           </td>
			            </tr>
                        <tr>
                            <td colspan="6" width="100%">
                            	<c:import url="/fssc/expense/fssc_expense_balance_detail/fsscExpenseBalanceDetail_edit.jsp"/>
                            </td>
                        </tr>
                    </table>
                </ui:content>
                <c:if test="${param.approveType ne 'right'}">
                <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                    <c:param name="formName" value="fsscExpenseBalanceForm" />
                    <c:param name="fdKey" value="fsscExpenseBalance" />
                    <c:param name="isExpand" value="true" />
                </c:import>
                 <%--权限 --%>
			     <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                <c:param name="formName" value="fsscExpenseBalanceForm" />
	                <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalance" />
	             </c:import>
				</c:if>
            </ui:tabpage>
            <c:if test="${param.approveType eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
			<%--流程--%>
			<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp"
				charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseBalanceForm" />
				<c:param name="fdKey" value="fsscExpenseBalance" />
				<c:param name="showHistoryOpers" value="true" />
				<c:param name="isExpand" value="true" />
				<c:param name="approveType" value="right" />
			</c:import>
			</ui:tabpanel>
			</c:if>
            <html:hidden property="fdId" />
            <html:hidden property="docStatus" />
            <html:hidden property="docTemplateId" />
            <html:hidden property="fdBudgetRate" />
            <html:hidden property="method_GET" />
            <script>
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
					<c:param name="formName" value="fsscExpenseBalanceForm" />
					<c:param name="fdKey" value="fsscExpenseBalance" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
					<c:param name="needInitLbpm" value="true" />
				</c:import>
				<c:import url="/fssc/expense/fssc_expense_balance/fsscExpenseBalance_baseInfo_right.jsp"></c:import>
				<!-- 关联机制 -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscExpenseBalanceForm" />
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
				<c:param name="formName" value="fsscExpenseBalanceForm" />
			</c:import>
		</template:replace>
	</c:if>
