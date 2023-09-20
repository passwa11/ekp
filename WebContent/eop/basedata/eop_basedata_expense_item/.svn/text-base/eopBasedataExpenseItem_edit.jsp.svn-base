<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
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
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("config_fssc_edit.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/eop/basedata/eop_basedata_expense_item/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItem.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${eopBasedataExpenseItemForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.eopBasedataExpenseItemForm, 'update');">
            </c:when>
            <c:when test="${eopBasedataExpenseItemForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.eopBasedataExpenseItemForm, 'save');">
                <input type="button" value="${ lfn:message('button.saveadd') }" onclick="Com_Submit(document.eopBasedataExpenseItemForm, 'saveadd');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataExpenseItem') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdCompanyList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataExpenseItem.fdCompanyList')}" showStatus="edit" style="width:95%;">
                            dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames',changeCompany);
                        </xform:dialog>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdParent')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdParentId" _xform_type="dialog">
                            <xform:dialog propertyId="fdParentId" propertyName="fdParentName" subject="${lfn:message('eop-basedata:eopBasedataExpenseItem.fdParent')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'eop_basedata_expense_item_fdParent','fdParentId','fdParentName',null,{fdCompanyId:$('[name=fdCompanyListIds]').val(),multi:'true'});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdName')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdCode')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdCode" _xform_type="text">
                            <c:if test="${empty eopBasedataExpenseItemForm.fdCode}">
                        		<xform:text property="fdCode" showStatus="edit" required="true" style="width:95%;" />
                        	</c:if>
                        	<c:if test="${not empty eopBasedataExpenseItemForm.fdCode}">
                        		<xform:text property="fdCode" showStatus="readOnly" required="true"  style="width:95%;color:#333;" />
                        	</c:if>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdBudgetItems')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdBudgetItemIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdBudgetItemIds" propertyName="fdBudgetItemNames" subject="${lfn:message('eop-basedata:eopBasedataExpenseItem.fdBudgetItems')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_budget_item_com_fdParent','fdBudgetItemIds','fdBudgetItemNames',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdAccounts')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdAccountIds" _xform_type="dialog">
                            <xform:dialog propertyId="fdAccountIds" propertyName="fdAccountNames" subject="${lfn:message('eop-basedata:eopBasedataExpenseItem.fdAccounts')}" showStatus="edit" style="width:95%;">
                                dialogSelect(true,'eop_basedata_accounts_com_fdAccount','fdAccountIds','fdAccountNames',null,{fdCompanyId:$('[name=fdCompanyListIds]').val()});
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdTripType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdTripType" _xform_type="select">
                            <xform:select property="fdTripType" htmlElementProperties="id='fdTripType'" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_trip_type" />
                            </xform:select>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                            ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdDayCalType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdDayCalType" _xform_type="select">
                            <xform:select property="fdDayCalType" htmlElementProperties="id='fdDayCalType'" showStatus="edit">
                                <xform:enumsDataSource enumsType="eop_basedata_day_cal_type" />
                            </xform:select>
                            <span style="color:red;">${lfn:message('eop-basedata:message.common.fdDayCalType.tips')}</span>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdIsAvailable')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdIsAvailable" _xform_type="radio">
                            <xform:radio property="fdIsAvailable" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdOrder')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <div id="_xform_fdOrder" _xform_type="text">
                            <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.fdCategory')}
                    </td>
                    <td colspan="3" width="85.0%">
                        ${fdStandardCategoryName }
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.docCreator')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreatorId" _xform_type="address">
                            <ui:person personId="${eopBasedataExpenseItemForm.docCreatorId}" personName="${eopBasedataExpenseItemForm.docCreatorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.docCreateTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docCreateTime" _xform_type="datetime">
                            <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.docAlteror')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterorId" _xform_type="address">
                            <ui:person personId="${eopBasedataExpenseItemForm.docAlterorId}" personName="${eopBasedataExpenseItemForm.docAlterorName}" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataExpenseItem.docAlterTime')}
                    </td>
                    <td width="35%">
                        <div id="_xform_docAlterTime" _xform_type="datetime">
                            <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
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
        function changeCompany(){
        	$("[name=fdAccountIds],[name=fdAccountNames],[name=fdBudgetItemIds],[name=fdBudgetItemNames],[name=fdParentId],[name=fdParentName]").val("")
            //清空显示值
            var len = $("span[data-idfield='fdBudgetItemIds']").length;
            for(var i=0;i<len;i++){
                deleteItem($("span[data-idfield='fdBudgetItemIds']").eq(0));
            }
            len = $("span[data-idfield='fdAccountIds']").length;
            for(var i=0;i<len;i++){
                deleteItem($("span[data-idfield='fdAccountIds']").eq(0));
            }
        }
        Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
