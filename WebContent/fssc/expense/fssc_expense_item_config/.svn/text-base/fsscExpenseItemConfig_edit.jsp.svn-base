<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_item_config/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/expense/fssc_expense_item_config/fsscExpenseItemConfig.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscExpenseItemConfigForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscExpenseItemConfigForm, 'update');">
            </c:when>
            <c:when test="${fsscExpenseItemConfigForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscExpenseItemConfigForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-expense:table.fsscExpenseItemConfig') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCompany')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 所属公司--%>
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCompany')}" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',changeCompany);
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCategory')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 申请模板--%>
                        <div id="_xform_fdTemplateId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCategoryId" propertyName="fdCategoryName" required="true" subject="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdCategory')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'fssc_expense_category_getCategory','fdCategoryId','fdCategoryName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <kmss:ifModuleExist path="/fssc/budget">
                    <fssc:switchOn property="fdIsBudget" defaultValue="1">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdIsNeedBudget')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 是否必须有预算--%>
                        <div id="_xform_fdIsNeedBudget"  _xform_type="radio">
                            <xform:radio property="fdIsNeedBudget" htmlElementProperties="id='fdIsNeedBudget'" required="true" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                </fssc:switchOn>
                </kmss:ifModuleExist>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-expense:fsscExpenseItemConfig.fdItemList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 对应费用类型--%>
                        <div id="_xform_fdItemListIds" _xform_type="dialog">
                            <xform:dialog textarea="true" propertyId="fdItemListIds" propertyName="fdItemListNames" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseItemConfig.fdItemList')}" style="width:95%;">
                                FSSC_SelectExpenseItem();
                            </xform:dialog>
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
       	window.FSSC_SelectExpenseItem = function(){
       		var fdCompanyId = $("[name=fdCompanyId]").val();
           	if(!fdCompanyId){
           		dialog.alert("请选择记账公司");
           		return;
           	}
           	dialogSelect(true,'eop_basedata_expense_item_fdParent','fdItemListIds','fdItemListNames',null,{fdCompanyId:$("[name=fdCompanyId]").val(),fdNotId:'fdNotId'});
       	}
       	window.changeCompany = function(){
       		$("[name=fdItemListIds]").val("");
       		$("[name=fdItemListNames]").val("");
       		window.buildMultiSelector($("[name=fdItemListIds]")[0].parentNode);
       	}
        Com_IncludeFile("quickSelect.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
