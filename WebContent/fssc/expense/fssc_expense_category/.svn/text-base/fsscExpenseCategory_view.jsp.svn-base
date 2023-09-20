<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/resource/jsp/view_top.jsp" %>
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
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/expense/fssc_expense_category/fsscExpenseCategory.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscExpenseCategory.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/expense/fssc_expense_category/fsscExpenseCategory.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscExpenseCategory.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
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
                            <a href="${KMSS_Parameter_ContextPath}fssc/expense/fssc_expense_category/fsscExpenseCategory.do?method=view&fdId=${fsscExpenseCategoryForm.fdParentId}" target="blank" class="com_btn_link">
                                <c:out value="${fsscExpenseCategoryForm.fdParentName}" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdName')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdName" _xform_type="text">
                                <xform:text property="fdName" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdCode')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdCode" _xform_type="text">
                                <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdAllocType')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdAllocType" _xform_type="radio">
                                <xform:radio property="fdAllocType" htmlElementProperties="id='fdAllocType'" showStatus="view">
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
                                <xform:radio property="fdExpenseType" htmlElementProperties="id='fdExpenseType'" showStatus="view">
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
                                <xform:radio property="fdIsTravelAlone" >
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
                                <xform:radio property="fdBudgetShowType">
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
                                <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" showStatus="view">
                                    <xform:enumsDataSource enumsType="fssc_expense_subject_type" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <tr id="fdSubjectRule" style="display:none;">
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdSubjectRule')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdSubjectRule" _xform_type="text">
                                <xform:text property="fdSubjectRuleText" showStatus="view" value="${fsscExpenseCategoryForm.fdSubjectRuleText }" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsProject')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdIsProject" _xform_type="radio">
                                <xform:radio property="fdIsProject" htmlElementProperties="id='fdIsProject'" showStatus="view">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <tr style="display:${fsscExpenseCategoryForm.fdIsProject=='true'?'':'none'}">
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsProjectShare')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdIsProjectShare" _xform_type="radio">
                                <xform:radio property="fdIsProjectShare" htmlElementProperties="id='fdIsProject'" isLoadDataDict="false" required="false" showStatus="view">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
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
                                <xform:radio property="fdIsAmortize">
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
                                <xform:radio property="fdIsFee" showStatus="view" onValueChange="changeIsFee" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsFee')}">
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
                            	<xform:dialog propertyName="fdFeeTemplateName" propertyId="fdFeeTemplateId" style="width:95%;">
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
                                <xform:radio property="fdIsProapp" showStatus="view" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsProapp')}">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    </kmss:ifModuleExist>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsForeign')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdIsAmortize" _xform_type="radio">
                                <xform:radio property="fdIsForeign" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsForeign')}">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    </fssc:checkVersion>
                    
                    <fssc:checkVersion version="true">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdIsForeign')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdIsAmortize" _xform_type="radio">
                                <xform:radio property="fdIsForeign" showStatus="view" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsForeign')}">
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
                                <xform:radio property="fdIsMobile" showStatus="view" subject="${lfn:message('fssc-expense:fsscExpenseCategory.fdIsMobile')}">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    </kmss:ifModuleExist>
                   </fssc:checkVersion>
                    <fssc:configEnabled property="fdFinancialSystem" value="SAP">
                    <c:set var="SapEnabled" value="true"/>
                    </fssc:configEnabled>
                    <c:if test="${empty SapEnabled }">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdExtendFields')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdExtendFields" _xform_type="radio">
                                <xform:checkbox property="fdExtendFields" showStatus="view" required="false" isLoadDataDict="false">
                                    <xform:enumsDataSource enumsType="fssc_expense_category_fd_extend_fields" />
                                </xform:checkbox>
                            </div>
                        </td>
                    </tr>
                    </c:if>
                    <c:if test="${SapEnabled=='true' }">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdExtendFields')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdExtendFields" _xform_type="radio">
                                <xform:checkbox property="fdExtendFields" showStatus="view" required="false" isLoadDataDict="false">
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
                        	<c:if test="${fsscExpenseCategoryForm.authNotReaderFlag eq 'true'}">
                            <label>
						    <bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" /></label>
						    </c:if>
						    <c:if test="${fsscExpenseCategoryForm.authNotReaderFlag ne 'true'}">
                            <div id="_xform_authReaderIds" _xform_type="address">
                                <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            	<span><bean:message bundle="fssc-expense" key="msg.description.main.tempReader.allUse" /></span>
                            </div>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.authEditors')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_authEditorIds" _xform_type="address">
                                <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-expense:fsscExpenseCategory.fdOrder')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdOrder" _xform_type="text">
                                <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
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
        <c:if test="${fsscExpenseCategoryForm.docUseXform == 'false'}">
            <tr LKS_LabelName="${lfn:message('fssc-expense:py.BiaoDanMoBan')}">
                <td>
                    <table class="tb_normal" width=100%>
                        <tr>
                            <td width="15%" class="td_normal_title" valign="top">
                                ${lfn:message('sys-xform:sysFormTemplate.fdMode')}
                            </td>
                            <td width="85%">
                                ${lfn:message('sys-xform:sysFormTemplate.fdTemplateType.nouse')}
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <xform:rtf property="docXform" toolbarSet="Default" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </c:if>
        <c:if test="${fsscExpenseCategoryForm.docUseXform == 'true' || empty fsscExpenseCategoryForm.docUseXform}">
            <tr LKS_LabelName="${lfn:message('fssc-expense:py.BiaoDanMoBan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscExpenseCategoryForm" />
                        <c:param name="fdKey" value="fsscExpenseMain" />
                        <c:param name="fdMainModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
                        <c:param name="useLabel" value="false" />
                    </c:import>
                </td>
            </tr>
        </c:if>

        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscExpenseCategoryForm" />
            <c:param name="modelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseMain" />
            <c:param name="messageKey" value="fssc-expense:py.BianHaoGuiZe" />
        </c:import>
        <c:import url="/sys/workflow/include/sysWfTemplate_view.jsp" charEncoding="UTF-8">
		        <c:param name="formName" value="fsscExpenseCategoryForm" />
		        <c:param name="fdKey" value="fsscExpenseMain" />
		    </c:import>
        <tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
	     	<td>
				<table class="tb_normal" width=100%>
					<c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscExpenseCategoryForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseCategory" />
					</c:import>
			 	</table>
		    </td>
		</tr>

    </table>
</center>
<script>
    var formInitData = {

    };

    function confirmDelete(msg) {
        return confirm('${ lfn:message("page.comfirmDelete") }');
    }

    function openWindowViaDynamicForm(popurl, params, target) {
        var form = document.createElement('form');
        if(form) {
            try {
                target = !target ? '_blank' : target;
                form.style = "display:none;";
                form.method = 'post';
                form.action = popurl;
                form.target = target;
                if(params) {
                    for(var key in params) {
                        var
                        v = params[key];
                        var vt = typeof
                        v;
                        var hdn = document.createElement('input');
                        hdn.type = 'hidden';
                        hdn.name = key;
                        if(vt == 'string' || vt == 'boolean' || vt == 'number') {
                            hdn.value =
                            v +'';
                        } else {
                            if($.isArray(
                                v)) {
                                hdn.value =
                                v.join(';');
                            } else {
                                hdn.value = toString(
                                    v);
                            }
                        }
                        form.appendChild(hdn);
                    }
                }
                document.body.appendChild(form);
                form.submit();
            } finally {
                document.body.removeChild(form);
            }
        }
    }

    function doCustomOpt(fdId, optCode) {
        if(!fdId || !optCode) {
            return;
        }

        if(viewOption.customOpts && viewOption.customOpts[optCode]) {
            var param = {
                "List_Selected_Count": 1
            };
            var argsObject = viewOption.customOpts[optCode];
            if(argsObject.popup == 'true') {
                var popurl = viewOption.contextPath + argsObject.popupUrl + '&fdId=' + fdId;
                for(var arg in argsObject) {
                    param[arg] = argsObject[arg];
                }
                openWindowViaDynamicForm(popurl, param, '_self');
                return;
            }
            var optAction = viewOption.contextPath + viewOption.basePath + '?method=' + optCode + '&fdId=' + fdId;
            Com_OpenWindow(optAction, '_self');
        }
    }
    window.doCustomOpt = doCustomOpt;
    var viewOption = {
        contextPath: '${LUI_ContextPath}',
        basePath: '/fssc/expense/fssc_expense_category/fsscExpenseCategory.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_AddEventListener(window,'load',function(){
    	if("${fsscExpenseCategoryForm.fdSubjectType}"=='2'){
    		$("#fdSubjectRule").show();
    	}
    });
</script>
<fssc:checkVersion version="false">
    <script>
        Com_AddEventListener(window,'load',function(){
            $("#_xform_fdExtendFields").find("label").eq(5).hide();
        });
    </script>
</fssc:checkVersion>
<%@ include file="/resource/jsp/view_down.jsp" %>
