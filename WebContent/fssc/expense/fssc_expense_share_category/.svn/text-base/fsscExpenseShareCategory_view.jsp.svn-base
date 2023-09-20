<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
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

    <kmss:auth requestURL="/fssc/expense/fssc_expense_share_category/fsscExpenseShareCategory.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscExpenseShareCategory.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/expense/fssc_expense_share_category/fsscExpenseShareCategory.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscExpenseShareCategory.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
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
                                    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" showStatus="view" style="width:95%;">
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
                                    <xform:text property="fdName" showStatus="view" style="width:95%;" />
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
                                        <xform:radio property="fdShareType" showStatus="view">
                                            <xform:enumsDataSource enumsType="fssc_expense_share_type" />
                                        </xform:radio>
                                    </div>
                                </td>
                            </c:if>
                            <c:if test="${not empty fdIsContainsPayment }">
                                <td width="35.0%">
                                    <div id="_xform_fdShareType" _xform_type="radio">
                                        <xform:radio property="fdShareType" showStatus="view">
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
                                    <xform:dialog propertyName="fdCateNames" propertyId="fdCateIds" style="width:95%">
                                        dialogSelect(true,'fssc_expense_category_getCategory','fdCateIds','fdCateNames')
                                    </xform:dialog>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.authReaders')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_authReaderIds" _xform_type="address">
                                    <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.authEditors')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_authEditorIds" _xform_type="address">
                                    <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-expense:fsscExpenseShareCategory.fdOrder')}
                            </td>
                            <td colspan="3" width="85.0%">
                                <div id="_xform_fdOrder" _xform_type="text">
                                    <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
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
        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscExpenseShareCategoryForm" />
            <c:param name="modelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareMain" />
            <c:param name="messageKey" value="fssc-expense:py.BianHaoGuiZe" />
        </c:import>
		<c:import url="/sys/workflow/include/sysWfTemplate_view.jsp" charEncoding="UTF-8">
	        <c:param name="formName" value="fsscExpenseShareCategoryForm" />
	        <c:param name="fdKey" value="fsscExpenseShareMain" />
	    </c:import>
	     <tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
                <td>
                    <table class="tb_normal" width=100%>
                        <c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
                            <c:param name="formName" value="fsscExpenseShareCategoryForm" />
                            <c:param name="moduleModelName" value="com.landray.kmss.fssc.expense.model.FsscExpenseShareCategory" />
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
        basePath: '/fssc/expense/fssc_expense_share_category/fsscExpenseShareCategory.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
