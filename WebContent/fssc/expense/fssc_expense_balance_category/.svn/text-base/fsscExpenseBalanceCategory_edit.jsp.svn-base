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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_balance_category/", 'js', true);
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/expense/resource/js/", 'js', true);
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
    Com_AddEventListener(window,'load',function(){
    	if("${fsscExpenseBalanceCategoryForm.fdSubjectType}"=='2'){
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
				Formula_GetVarInfoByModelName('com.landray.kmss.fssc.expense.model.FsscExpenseBalance'),'String');
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

<html:form action="/fssc/expense/fssc_expense_balance_category/fsscExpenseBalanceCategory.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscExpenseBalanceCategoryForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscExpenseBalanceCategoryForm, 'update');">
            </c:when>
            <c:when test="${fsscExpenseBalanceCategoryForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscExpenseBalanceCategoryForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-expense:table.fsscExpenseBalanceCategory') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-expense:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseBalanceCategory.fdParent')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 父节点--%>
                                <div id="_xform_fdParentId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                        Dialog_SimpleCategory('com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory','fdParentId','fdParentName',false);
                                    </xform:dialog>
                                </div>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseBalanceCategory.fdName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 名称--%>
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
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
                                	<xform:dialog propertyName="fdSubjectRuleText" propertyId="fdSubjectRule" idValue="${fsscExpenseBalanceCategoryForm.fdSubjectRule }" nameValue="${fsscExpenseBalanceCategoryForm.fdSubjectRuleText }"   style="width:95%;">
                                		selectFormula('fdSubjectRule','fdSubjectRuleText');
                                	</xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseBalanceCategory.fdOrder')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 排序号--%>
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseBalanceCategory.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <label>
                            	<input type="checkbox" name="authNotReaderFlag" value="true"
                                       onclick="checkNotReaderFlag(this);" <c:if test="${fsscExpenseBalanceCategoryForm.authNotReaderFlag eq 'true'}">checked</c:if>>
                                <bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" />
                                </label>
                                <%-- 可使用者--%>
                                <div id="_xform_authReaderIds" _xform_type="address">
                                    <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseBalanceCategory.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 可维护者--%>
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseBalanceCategory.docCreator')}
                            </td>
                            <td width="35%">
                                <%-- 创建人--%>
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscExpenseBalanceCategoryForm.docCreatorId}" personName="${fsscExpenseBalanceCategoryForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseBalanceCategory.docCreateTime')}
                            </td>
                            <td width="35%">
                                <%-- 创建时间--%>
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseBalanceCategoryForm" />
                <c:param name="fdKey" value="fsscExpenseBalance" />
                <c:param name="messageKey" value="fssc-expense:py.LiuChengDingYi" />
            </c:import>

            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseBalanceCategoryForm" />
                <c:param name="modelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalance" />
                <c:param name="messageKey" value="fssc-expense:py.BianHaoGuiZe" />
            </c:import>
            <tr LKS_LabelName="<bean:message bundle='fssc-expense' key='py.GuanLianXinXi' />">
                <c:set var="mainModelForm" value="${fsscExpenseBalanceCategoryForm}" scope="request" />
                <c:set var="currModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory" scope="request" />
                <td>
                    <%@ include file="/sys/relation/include/sysRelationMain_edit.jsp" %>
                </td>
            </tr>
             <tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
                <td>
                    <table class="tb_normal" width=100%>
                        <c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="fsscExpenseBalanceCategoryForm" />
                            <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseBalanceCategory" />
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
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
