<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/eop/basedata//resource/jsp/jshead.jsp" %>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/fee/fssc_fee_expense_item/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/fee/fssc_fee_expense_item/fsscFeeExpenseItem.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscFeeExpenseItemForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscFeeExpenseItemForm, 'update');">
            </c:when>
            <c:when test="${fsscFeeExpenseItemForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscFeeExpenseItemForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-fee:table.fsscFeeExpenseItem') }</p>
    <center>

        <div style="width:95%;">
            <table class="tb_normal" width="100%">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeExpenseItem.fdCompany')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 所属公司--%>
                        <div id="_xform_fdCompanyId" _xform_type="dialog">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" showStatus="edit" required="true" subject="${lfn:message('fssc-fee:fsscFeeExpenseItem.fdCompany')}" style="width:95%;">
                                dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeExpenseItem.fdTemplate')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 申请模板--%>
                        <div id="_xform_fdTemplateId" _xform_type="dialog">
                            <xform:dialog propertyId="fdTemplateId" propertyName="fdTemplateName" required="true" subject="${lfn:message('fssc-fee:fsscFeeExpenseItem.fdTemplate')}" showStatus="edit" style="width:95%;">
                                dialogSelect(false,'fssc_fee_template_getTemplate','fdTemplateId','fdTemplateName');
                            </xform:dialog>
                        </div>
                    </td>
                </tr>
                <kmss:ifModuleExist path="/fssc/budget">
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeExpenseItem.fdIsNeedBudget')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 是否必须有预算--%>
                        <div id="_xform_fdIsNeedBudget" _xform_type="radio">
                            <xform:radio property="fdIsNeedBudget" htmlElementProperties="id='fdIsNeedBudget'" showStatus="edit">
                                <xform:enumsDataSource enumsType="common_yesno" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
                </kmss:ifModuleExist>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-fee:fsscFeeExpenseItem.fdItemList')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 对应费用类型--%>
                        <div id="_xform_fdItemListIds" _xform_type="dialog">
                            <xform:dialog textarea="true" propertyId="fdItemListIds" propertyName="fdItemListNames" showStatus="edit" required="true" subject="${lfn:message('fssc-fee:fsscFeeExpenseItem.fdItemList')}" style="width:95%;">
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
           		seajs.use(['lui/dialog'],function(dialog){
           			dialog.alert("请选择记账公司");
           		})
           		return;
           	}
           	dialogSelect(true,'eop_basedata_expense_item_fdParent','fdItemListIds','fdItemListNames',null,{fdCompanyId:$("[name=fdCompanyId]").val(),fdNotId:''});
       	}
       	Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
