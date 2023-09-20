<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
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
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_share_category/", 'js', true);
    Com_AddEventListener(window,'load',function(){
    	if("${fsscExpenseShareCategoryForm.fdSubjectType}"=='2'){
    		$("#fdSubjectRule").show();
    	}
    	var el = $("[name=authNotReaderFlag]")[0]
    	if(el.checked){
    		$("[id='_xform_authReaderIds']").hide()
    	}else{
    		$("[id='_xform_authReaderIds']").show()
    	}
    })
    function changeSubjectType(){
    	var subjectType = $("[name=fdSubjectType]:checked").val();
    	if(subjectType=="1"){
    		$("[name=fdSubjectRuleText],[name=fdSubjectRule]").val("");
    		$("[name=fdSubjectRuleText]").attr("validate","");
    		$("#fdSubjectRule").hide();
    	}else{
    		$("[name=fdSubjectRuleText]").attr("validate","required");
    		$("#fdSubjectRule").show();
    	}
    }
    function selectFormula(id,name){
		Formula_Dialog(id, name, 
				Formula_GetVarInfoByModelName('com.landray.kmss.fssc.expense.model.FsscExpenseShareMain'),'String');
	}
    function checkNotReaderFlag(el) {
    	if(el.checked){
    		$("[id='_xform_authReaderIds']").hide()
    	}else{
    		$("[id='_xform_authReaderIds']").show()
    	}
}
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/expense/fssc_expense_share_category/fsscExpenseShareCategory.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscExpenseShareCategoryForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscExpenseShareCategoryForm, 'update');">
            </c:when>
            <c:when test="${fsscExpenseShareCategoryForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscExpenseShareCategoryForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-expense:table.fsscExpenseShareCategory') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-expense:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.fdParent')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdParentId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                        Dialog_SimpleCategory('com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}');
                                    </xform:dialog>
                                </div>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.fdName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-expense:fsscExpenseShareCategory.fdShareType')}
                            </td>
                            <kmss:ifModuleExist path="/fssc/payment/">
                                <c:set var="fdIsContainsPayment" value="true"/>
                            </kmss:ifModuleExist>
                            <c:if test="${empty fdIsContainsPayment }">
                                <td width="35.0%">
                                    <div id="_xform_fdShareType" _xform_type="radio">
                                        <xform:radio property="fdShareType" showStatus="edit" required="true" isLoadDataDict="false">
                                            <xform:enumsDataSource enumsType="fssc_expense_share_type" />
                                        </xform:radio>
                                    </div>
                                </td>
                            </c:if>
                            <c:if test="${not empty fdIsContainsPayment }">
                                <td width="35.0%">
                                    <div id="_xform_fdShareType" _xform_type="radio">
                                        <xform:radio property="fdShareType" showStatus="edit" required="true" onValueChange="changeShareType" isLoadDataDict="false">
                                            <xform:enumsDataSource enumsType="fssc_expense_share_type_contains_payment" />
                                        </xform:radio>
                                    </div>
                                </td>
                            </c:if>
                            <td class="td_normal_title" width="15%">
                                    ${lfn:message('fssc-expense:fsscExpenseShareCategory.fdCateNames')}
                            </td>
                            <td width="35.0%">
                                <div id="_xform_fdCateIds" _xform_type="text">
                                    <xform:dialog propertyName="fdCateNames" propertyId="fdCateIds" subject="${lfn:message('fssc-expense:fsscExpenseShareCategory.fdCateNames')}" style="width:95%">
                                        dialogSelect(true,'fssc_expense_category_getCategory','fdCateIds','fdCateNames',null,{fdShareType:$('[name=fdShareType]:checked').val()})
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdSubjectType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdSubjectType" _xform_type="radio">
                                    <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" onValueChange="changeSubjectType" showStatus="edit">
                                        <xform:enumsDataSource enumsType="fssc_expense_subject_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                         <tr style="display:none;" id="fdSubjectRule">
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdSubjectRule')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdSubjectRule" _xform_type="text">
                                	<xform:dialog propertyName="fdSubjectRuleText" propertyId="fdSubjectRule" idValue="${fsscExpenseShareCategoryForm.fdSubjectRule }" nameValue="${fsscExpenseShareCategoryForm.fdSubjectRuleText }"   style="width:95%;">
                                		selectFormula('fdSubjectRule','fdSubjectRuleText');
                                	</xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <label>
                            	<input type="checkbox" name="authNotReaderFlag" value="true"
                                       onclick="checkNotReaderFlag(this);" <c:if test="${fsscExpenseShareCategoryForm.authNotReaderFlag eq 'true'}">checked</c:if>>
                                <bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
                                </label>
                                <div id="_xform_authReaderIds" _xform_type="address">
                                    <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.fdOrder')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.docCreator')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscExpenseShareCategoryForm.docCreatorId}" personName="${fsscExpenseShareCategoryForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.docCreateTime')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseShareCategoryForm" />
                <c:param name="modelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
                <c:param name="messageKey" value="fssc-expense:py.BianHaoGuiZe" />
            </c:import>
			<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp" charEncoding="UTF-8">
		        <c:param name="formName" value="fsscExpenseShareCategoryForm" />
		        <c:param name="fdKey" value="fsscExpenseShareMain" />
		    </c:import>
		    <tr LKS_LabelName="<bean:message bundle='fssc-expense' key='py.GuanLianXinXi' />">
                <c:set var="mainModelForm" value="${fsscExpenseShareCategoryForm}" scope="request" />
                <c:set var="currModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory" scope="request" />
                <td>
                    <%@ include file="/sys/relation/include/sysRelationMain_edit.jsp" %>
                </td>
            </tr>
             <tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
                <td>
                    <table class="tb_normal" width=100%>
                        <c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="fsscExpenseShareCategoryForm" />
                            <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory" />
                        </c:import>
                    </table>
                </td>
            </tr>
        </table>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <script>
        $KMSSValidation();
        Com_IncludeFile("formula.js");
        function changeShareType(v, e){
            $("[name='fdCateIds']").val("");
            $("[name='fdCateNames']").val("");
        }
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
