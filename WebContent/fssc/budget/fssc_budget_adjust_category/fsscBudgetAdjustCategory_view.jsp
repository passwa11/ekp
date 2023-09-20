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

    <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_category/fsscBudgetAdjustCategory.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscBudgetAdjustCategory.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/budget/fssc_budget_adjust_category/fsscBudgetAdjustCategory.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscBudgetAdjustCategory.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
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
                            <a href="${KMSS_Parameter_ContextPath}fssc/budget/fssc_budget_adjust_category/fsscBudgetAdjustCategory.do?method=view&fdId=${fsscBudgetAdjustCategoryForm.fdParentId}" target="blank" class="com_btn_link">
                                <c:out value="${fsscBudgetAdjustCategoryForm.fdParentName}" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdName')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdName" _xform_type="text">
                                <xform:text property="fdName" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdAdjustType')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdAdjustType" _xform_type="select">
                                <xform:radio property="fdAdjustType" htmlElementProperties="id='fdAdjustType'" showStatus="view">
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
                            <div id="_xform_fdSubjectType" _xform_type="radio">
                                <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" showStatus="view">
                                    <xform:enumsDataSource enumsType="fssc_expense_subject_type" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <tr id="fdSubjectRule" style="display:none;">
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdSubjectRule')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdSubjectRule" _xform_type="text">
                                <xform:text property="fdSubjectRuleText" showStatus="view" value="${fsscBudgetAdjustCategoryForm.fdSubjectRuleText }" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-budget:fsscBudgetAdjustCategory.fdOrder')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdOrder" _xform_type="text">
                                <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
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
                        	<c:if test="${fsscBudgetAdjustCategoryForm.authNotReaderFlag eq 'true'}">
                            <label>
						    <bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" /></label>
						    </c:if>
						    <c:if test="${fsscBudgetAdjustCategoryForm.authNotReaderFlag ne 'true'}">
                            <div id="_xform_authReaderIds" _xform_type="address">
                                <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            	<span><bean:message bundle="fssc-budget" key="msg.description.main.tempReader.allUse" /></span>
                            </div>
                            </c:if>
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
        <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscBudgetAdjustCategoryForm" />
            <c:param name="fdKey" value="fsscBudgetAdjustMain" />
            <c:param name="messageKey" value="fssc-budget:py.LiuChengDingYi" />
        </c:import>

        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscBudgetAdjustCategoryForm" />
            <c:param name="modelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain" />
            <c:param name="messageKey" value="fssc-budget:py.BianHaoGuiZe" />
        </c:import>

        <tr LKS_LabelName="${ lfn:message('fssc-budget:py.MoRenQuanXian') }">
            <td>
                <table class="tb_normal" width=100%>
                    <c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscBudgetAdjustCategoryForm" />
                        <c:param name="moduleModelName" value="com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory" />
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
        basePath: '/fssc/budget/fssc_budget_adjust_category/fsscBudgetAdjustCategory.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
