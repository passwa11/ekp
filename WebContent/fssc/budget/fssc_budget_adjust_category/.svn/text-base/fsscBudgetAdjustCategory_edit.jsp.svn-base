<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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
    Com_IncludeFile("config_edit.js", "${LUI_ContextPath}/fssc/budget/resource/js/", 'js', true);
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/budget/fssc_budget_adjust_category/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/budget/fssc_budget_adjust_category/fsscBudgetAdjustCategory.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscBudgetAdjustCategoryForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="Com_Submit(document.fsscBudgetAdjustCategoryForm, 'update');">
            </c:when>
            <c:when test="${fsscBudgetAdjustCategoryForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="Com_Submit(document.fsscBudgetAdjustCategoryForm, 'save');">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-budget:table.fsscBudgetAdjustCategory') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-budget:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdParent')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdParentId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                        Dialog_SimpleCategory('com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}');
                                    </xform:dialog>
                                </div>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdAdjustType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdAdjustType" _xform_type="select">
                                    <xform:radio property="fdAdjustType" htmlElementProperties="id='fdAdjustType'" showStatus="edit">
                                        <xform:enumsDataSource enumsType="fssc_budget_adjust_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdSubjectType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <%-- 主题生成方式--%>
                                <div id="_xform_fdSubjectType" _xform_type="radio">
                                    <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" onValueChange="changeSubjectType" showStatus="edit">
                                        <xform:enumsDataSource enumsType="fssc_budget_subject_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr style="display:none;" id="fdSubjectRule">
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdSubjectRule')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdSubjectRule" _xform_type="text">
                                    <xform:dialog propertyName="fdSubjectRuleText" propertyId="fdSubjectRule" idValue="${fsscBudgetAdjustCategoryForm.fdSubjectRule }" nameValue="${fsscBudgetAdjustCategoryForm.fdSubjectRuleText }"  subject="${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdSubjectRule')}"    style="width:95%;">
                                        selectFormula('fdSubjectRule','fdSubjectRuleText');
                                    </xform:dialog>
                                    <span class="txtstrong">*</span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdOrder')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.docCreator')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscBudgetAdjustCategoryForm.docCreatorId}" personName="${fsscBudgetAdjustCategoryForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.docCreateTime')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreateTime" _xform_type="datetime">
                                    <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                            	<label>
                            	<input type="checkbox" name="authNotReaderFlag" value="true"
                                       onclick="checkNotReaderFlag(this);" <c:if test="${fsscBudgetAdjustCategoryForm.authNotReaderFlag eq 'true'}">checked</c:if>>
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
                                ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                            <%-- 可维护者--%>
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscBudgetAdjustCategoryForm" />
                <c:param name="fdKey" value="fsscBudgetAdjustMain" />
                <c:param name="messageKey" value="fssc-budget:py.LiuChengDingYi" />
            </c:import>

            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscBudgetAdjustCategoryForm" />
                <c:param name="modelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
                <c:param name="messageKey" value="fssc-budget:py.BianHaoGuiZe" />
            </c:import>

            <tr LKS_LabelName="${ lfn:message('fssc-budget:py.MoRenQuanXian') }">
                <td>
                    <table class="tb_normal" width=100%>
                        <c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="fsscBudgetAdjustCategoryForm" />
                            <c:param name="moduleModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory" />
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
        function checkNotReaderFlag(el) {
        	if(el.checked){
        		$("[id='_xform_authReaderIds']").hide()
        	}else{
        		$("[id='_xform_authReaderIds']").show()
        	}
    	}
        Com_IncludeFile("formula.js");
        Com_AddEventListener(window,'load',function(){
            if("${fsscBudgetAdjustCategoryForm.fdSubjectType}"=='2'){
                $("#fdSubjectRule").show();
            }
        });
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
                Formula_GetVarInfoByModelName('com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain'),'String');
        }
    </script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
