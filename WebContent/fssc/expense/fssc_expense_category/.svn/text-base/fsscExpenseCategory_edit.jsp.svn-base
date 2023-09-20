<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
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
    Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/expense/fssc_expense_category/", 'js', true);
</script>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<html:form action="/fssc/expense/fssc_expense_category/fsscExpenseCategory.do">
    <div id="optBarDiv">
        <c:choose>
            <c:when test="${fsscExpenseCategoryForm.method_GET=='edit'}">
                <input type="button" value="${ lfn:message('button.update') }" onclick="submitForm('update')">
            </c:when>
            <c:when test="${fsscExpenseCategoryForm.method_GET=='add'}">
                <input type="button" value="${ lfn:message('button.save') }" onclick="submitForm('save')">
            </c:when>
        </c:choose>
        <input type="button" value="${ lfn:message('button.close') }" onclick="Com_CloseWindow();">
    </div>
    <p class="txttitle">${ lfn:message('fssc-expense:table.fsscExpenseCategory') }</p>
    <center>

        <table class="tb_normal" id="Label_Tabel" width="95%">
            <tr LKS_LabelName="${ lfn:message('fssc-expense:py.JiBenXinXi') }">
                <td>
                    <table class="tb_normal" width="100%">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdParent')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdParentId" _xform_type="dialog">
                                    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="edit" style="width:95%;">
                                        Dialog_SimpleCategory('com.landray.kmss.fssc.expense.model.FsscExpenseCategory','fdParentId','fdParentName',false,null,'01',null,false,'${JsParam.fdId}');
                                    </xform:dialog>
                                </div>
                                </a>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdName" _xform_type="text">
                                    <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdCode')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdCode" _xform_type="text">
                                    <xform:text property="fdCode" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdAllocType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdAllocType" _xform_type="radio">
                                    <xform:radio property="fdAllocType" htmlElementProperties="id='fdAllocType'" showStatus="edit">
                                        <xform:enumsDataSource enumsType="fssc_expense_alloc_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdExpenseType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdExpenseType" _xform_type="radio">
                                    <xform:radio property="fdExpenseType" showStatus="edit" onValueChange="changeExpenseType">
                                        <xform:enumsDataSource enumsType="fssc_expense_cate_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr class="fdIsTravelAlone" style="display:${fsscExpenseCategoryForm.fdExpenseType=='2'?'':'none'};">
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsTravelAlone')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdExpenseType" _xform_type="radio">
                                    <xform:radio property="fdIsTravelAlone"  showStatus="edit">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <kmss:ifModuleExist path="/fssc/budget">
                            <fssc:switchOn property="fdIsBudget" defaultValue="1">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdBudgetShowType')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdBudgetShowType" _xform_type="radio">
                                    <xform:radio property="fdBudgetShowType" showStatus="edit">
                                        <xform:enumsDataSource enumsType="fssc_expense_category_fd_budget_show_type" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                            </fssc:switchOn>
                        </kmss:ifModuleExist>
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
                                	<xform:dialog propertyName="fdSubjectRuleText" propertyId="fdSubjectRule" idValue="${fsscExpenseCategoryForm.fdSubjectRule }" nameValue="${fsscExpenseCategoryForm.fdSubjectRuleText }"      style="width:95%;">
                                		selectFormula('fdSubjectRule','fdSubjectRuleText');
                                	</xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsProject')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsProject" _xform_type="radio">
                                    <xform:radio property="fdIsProject" htmlElementProperties="id='fdIsProject'" showStatus="edit" onValueChange="changeIsProject">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr class="fdIsProjectShare"">
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsProjectShare')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsProjectShare" _xform_type="radio">
                                    <xform:radio property="fdIsProjectShare" htmlElementProperties="id='fdIsProject'" isLoadDataDict="false" required="false" showStatus="edit">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                    <span class="txtstrong">*</span>
                                </div>
                            </td>
                        </tr>
                        <fssc:checkVersion version="true">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsAmortize')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsAmortize" _xform_type="radio">
                                    <xform:radio property="fdIsAmortize" showStatus="edit">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        </fssc:checkVersion>
                        <kmss:ifModuleExist path="/fssc/fee">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsFee')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsAmortize" _xform_type="radio">
                                    <xform:radio property="fdIsFee" showStatus="edit" onValueChange="changeIsFee" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsFee')}">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr style="display:${fsscExpenseCategoryForm.fdIsFee=='true'?'':'none'};" id="fdFeeTemplate">
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdFeeTemplateName')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdFeeTemplate" _xform_type="text">
                                	<xform:dialog propertyName="fdFeeTemplateName" propertyId="fdFeeTemplateId" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdFeeTemplateName')}" style="width:95%;">
                                		selectFeeTemplate();
                                	</xform:dialog>
                                </div>
                            </td>
                        </tr>
                        </kmss:ifModuleExist>
                        <fssc:checkVersion version="true">
                        <kmss:ifModuleExist path="/fssc/proapp">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsProapp')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsProapp" _xform_type="radio">
                                    <xform:radio onValueChange="changeIsProapp" property="fdIsProapp" showStatus="edit" required="true" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsProapp')}">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        </kmss:ifModuleExist>
                         </fssc:checkVersion>
                         <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsForeign')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsAmortize" _xform_type="radio">
                                    <xform:radio property="fdIsForeign" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsForeign')}">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        <kmss:ifModuleExist path="/fssc/mobile/">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsMobile')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsMobile" _xform_type="radio">
                                    <xform:radio property="fdIsMobile" showStatus="edit" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsMobile')}">
                                        <xform:enumsDataSource enumsType="common_yesno" />
                                    </xform:radio>
                                </div>
                            </td>
                        </tr>
                        </kmss:ifModuleExist>
						<fssc:configEnabled property="fdFinancialSystem" value="SAP">
                        <c:set var="SapEnabled" value="true"/>
                        </fssc:configEnabled>
                        <c:if test="${empty SapEnabled }">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdExtendFields')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsProject" _xform_type="radio">
                                    <xform:checkbox property="fdExtendFields" showStatus="edit" required="false" isLoadDataDict="false">
                                        <xform:enumsDataSource enumsType="fssc_expense_category_fd_extend_fields" />
                                    </xform:checkbox>
                                </div>
                            </td>
                        </tr>
                        </c:if>
                        <c:if test="${not empty SapEnabled }">
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdExtendFields')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdIsProject" _xform_type="radio">
                                    <xform:checkbox property="fdExtendFields" showStatus="edit" required="false" isLoadDataDict="false">
                                        <xform:enumsDataSource enumsType="fssc_expense_category_fd_extend_fields_contains_sap" />
                                    </xform:checkbox>
                                </div>
                            </td>
                        </tr>
                        </c:if>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                            	<label>
                            	<input type="checkbox" name="authNotReaderFlag" value="true"
                                       onclick="checkNotReaderFlag(this);" <c:if test="${fsscExpenseCategoryForm.authNotReaderFlag eq 'true'}">checked</c:if>>
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
                                ${lfn:message('fssc-expense:fsscExpenseCategory.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="edit" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.fdOrder')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="edit" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.docCreator')}
                            </td>
                            <td width="35%">
                                <div id="_xform_docCreatorId" _xform_type="address">
                                    <ui:person personId="${fsscExpenseCategoryForm.docCreatorId}" personName="${fsscExpenseCategoryForm.docCreatorName}" />
                                </div>
                            </td>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseCategory.docCreateTime')}
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
            <tr LKS_LabelName="${lfn:message('fssc-expense:py.BiaoDanMoBan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscExpenseCategoryForm" />
                        <c:param name="fdKey" value="fsscExpenseMain" />
                        <c:param name="fdMainModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
                        <c:param name="useLabel" value="false" />
                    </c:import>
                    <table id="rtfView" class="tb_normal" width="100%" style="border-top:0;">
                        <tr>
                            <td colspan="4" style="border-top:0;">
                                <html:hidden property="docUseXform" />
                                <kmss:editor property="docXform" toolbarSet="Default" height="1000" />
                            </td>
                        </tr>
                    </table>
                    <script language="JavaScript">
                        function XForm_Mode_Listener(key, value) {
                            var rtfView = document.getElementById('rtfView');
                            var $docUseXform = $("input[type='hidden'][name='docUseXform']");
                            var docUseXform = $docUseXform[0];
                            var display;
                            if(value == '1') {
                                display = '';
                                docUseXform.value = (false);
                            } else {
                                display = 'none';
                                docUseXform.value = (true);
                            }
                            rtfView.style.display = display;
                        }
                    </script>
                </td>
            </tr>
			
            <c:import url="/sys/number/include/sysNumberMappTemplate_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseCategoryForm" />
                <c:param name="modelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
                <c:param name="messageKey" value="fssc-expense:py.BianHaoGuiZe" />
            </c:import>
			<c:import url="/sys/workflow/include/sysWfTemplate_edit.jsp" charEncoding="UTF-8">
		        <c:param name="formName" value="fsscExpenseCategoryForm" />
		        <c:param name="fdKey" value="fsscExpenseMain" />
		    </c:import>
            <kmss:ifModuleExist path="/eop/arch">
            <c:import url="/sys/archives/include/sysArchivesFileSetting_edit.jsp" charEncoding="UTF-8">
                <c:param name="formName" value="fsscExpenseCategoryForm" />
                <c:param name="fdKey" value="fsscExpenseMain" />
                <c:param name="modelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
                <c:param name="templateService" value="fsscExpenseCategoryService" />
                <c:param name="moduleUrl" value="fssc/expense" />
            </c:import>
        </kmss:ifModuleExist>
		    <tr LKS_LabelName="<bean:message bundle='fssc-expense' key='py.GuanLianXinXi' />">
                <c:set var="mainModelForm" value="${fsscExpenseCategoryForm}" scope="request" />
                <c:set var="currModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseCategory" scope="request" />
                <td>
                    <%@ include file="/sys/relation/include/sysRelationMain_edit.jsp" %>
                </td>
            </tr>
		    <tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
		     	<td>
					<table class="tb_normal" width=100%>
						<c:import url="/sys/right/tmp_right_edit.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="fsscExpenseCategoryForm" />
							<c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseCategory" />
						</c:import>
				 	</table>
			    </td>
			</tr>
        </table>
    </center>
    <html:hidden property="fdId" />
    <html:hidden property="method_GET" />
    <fssc:checkVersion version="false">
    <script>
        Com_AddEventListener(window,'load',function(){
            $("input[name='_fdExtendFields'][value='6']").parent().hide();
        });
    </script>
    </fssc:checkVersion>
    <script>
        $KMSSValidation();
        Com_IncludeFile("formula.js");
        Com_AddEventListener(window,'load',function(){
	        	if("${fsscExpenseCategoryForm.fdSubjectType}"=='2'){
	        		$("#fdSubjectRule").show();
	        	}
	        	var el = $("[name=authNotReaderFlag]")[0]
	        	if(el.checked){
	        		$("[id='_xform_authReaderIds']").hide()
	        	}else{
	        		$("[id='_xform_authReaderIds']").show()
	        	}
	        	changeExpenseType();
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
	    				Formula_GetVarInfoByModelName('com.landray.kmss.fssc.expense.model.FsscExpenseMain'),'String');
	    	}
        function checkNotReaderFlag(el) {
	        	if(el.checked){
	        		$("[id='_xform_authReaderIds']").hide()
	        	}else{
	        		$("[id='_xform_authReaderIds']").show()
	        	}
        }
        function submitForm(method) {
	        	if(typeof XForm_BeforeSubmitForm != 'undefined' && XForm_BeforeSubmitForm instanceof Function){
	        		XForm_BeforeSubmitForm(function(){
	        			Com_Submit(document.forms[0],method);
	        		});
	        	}else{
	        		Com_Submit(document.forms[0],method);
            }
        }
        function selectFeeTemplate(){
        		dialogSelect(false,'fssc_expense_category_selectFeeTemplate','fdFeeTemplateId','fdFeeTemplateName');
        }
        function changeIsFee(){
	        	if($("[name=fdIsFee]:checked").val()=='true'){
	        		var fdIsProapp = $("[name=fdIsProapp]:checked").val();
	        		if(fdIsProapp=='true'){
	        			alert("${lfn:message('fssc-expense:tips.fee.enable.error')}");
	        			$("[name=fdIsFee][value=false]").prop("checked",true);
	        			return;
	        		}
	        		$("#fdFeeTemplate").show();
	        	}else{
	        		$("[name=fdFeeTemplateId]").val("")
	        		$("[name=fdFeeTemplateName]").val("")
	        		$("#fdFeeTemplate").hide();
	        	}
        }
        function changeIsProapp(){
	        	if($("[name=fdIsProapp]:checked").val()=='true'){
	        		var fdIsProapp = $("[name=fdIsFee]:checked").val();
	        		if(fdIsProapp=='true'){
	        			alert("${lfn:message('fssc-expense:tips.proapp.enable.error')}");
	        			$("[name=fdIsProapp][value=false]").prop("checked",true);
	        			return;
	        		}
	        	}
	    }
        function changeExpenseType(){
	        	var type = $("[name=fdExpenseType]:checked").val();
	        	if(type=='2'){
	        		$(".fdIsTravelAlone").show();
	        		$("[name=fdIsTravelAlone]").attr("validate","required");
	        	}else{
	        		$(".fdIsTravelAlone").hide();
	        		$("[name=fdIsTravelAlone]").attr("validate","");
	        	}
        }
        function changeIsProject(){
        	var fdIsProject = $("[name=fdIsProject]:checked").val();
        	if('true'==fdIsProject){
        		$(".fdIsProjectShare").show();
        		$("[name=fdIsProjectShare]").attr("validate","required");
        		
        	}else{
        		$(".fdIsProjectShare").hide();
        		$("[name=fdIsProjectShare]").attr("validate","");
        	}
        }
        Com_AddEventListener(window,'load',changeIsProject);
    </script>
    <c:import url="/fssc/common/resource/jsp/designer.jsp" charEncoding="UTF-8">
    	<c:param name="fdKey">fsscExpenseMain</c:param>
    </c:import>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>
