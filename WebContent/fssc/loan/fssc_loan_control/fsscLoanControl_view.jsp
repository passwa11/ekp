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
<script type="text/javascript">
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/loan/fssc_loan_control/fsscLoanControl.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscLoanControl.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/loan/fssc_loan_control/fsscLoanControl.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscLoanControl.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-loan:table.fsscLoanControl') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-loan:fsscLoanControl.fdOrgs')}
                </td>
                <td width="85%">
                    <%-- 控制范围--%>
                    <div id="_xform_fdOrgIds" _xform_type="address">
                        <xform:address propertyId="fdOrgIds" propertyName="fdOrgNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-loan:fsscLoanControl.fdValidity')}
                </td>
                <td width="85%">
                    <%-- 控制方式--%>
                    <div id="_xform_fdValidity" _xform_type="radio">
                        <xform:radio property="fdValidity" htmlElementProperties="id='fdValidity'" showStatus="view">
                            <xform:enumsDataSource enumsType="fssc_loan_fd_validity" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
			<c:if test="${'1' eq fsscLoanControlForm.fdValidity}">
	            <tr>
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('fssc-loan:fsscLoanControl.fdForbid')}
	                </td>
	                <td width="85%">
	                    <%-- 控制方案--%>
	                    <div id="_xform_fdForbid" _xform_type="radio">
	                        <xform:radio property="fdForbid" htmlElementProperties="id='fdForbid'" showStatus="view">
	                            <xform:enumsDataSource enumsType="fssc_loan_fd_forbid" />
	                        </xform:radio>
	                    </div>
	                </td>
	            </tr>
	            <tr>
	                <td class="td_normal_title" width="15%">
	                        ${lfn:message('fssc-loan:fsscLoanControl.fdLoanCategory')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <div id="_xform_fdLoanCategoryIds" _xform_type="dialog">
	                        <xform:dialog propertyId="fdLoanCategoryIds" propertyName="fdLoanCategoryNames" showStatus="view" style="width:95%;">
	                            dialogSelect(true,'fssc_loan_category_selectLowestLevelList','fdLoanCategoryIds','fdLoanCategoryNames');
	                        </xform:dialog>
	                    </div>
	                </td>
	            </tr>
	            <tr class="controlTr">
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('fssc-loan:fsscLoanControl.fdControlClear')}
	                </td>
	                <td width="85%">
						<c:if test="${'true' eq  fsscLoanControlForm.fdControlClear}">
							<input type="checkbox" name="fdControlClear" value="${fsscLoanControlForm.fdControlClear}" checked="checked" disabled="disabled"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message1"/>
							<xform:text property="fdMoney"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message2"/>
						</c:if>
						<c:if test="${'false' eq  fsscLoanControlForm.fdControlClear}">
							<input type="checkbox" name="fdControlClear" value="${fsscLoanControlForm.fdControlClear}" disabled="disabled"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message1"/>
							<xform:text property="fdMoney"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message2"/>
						</c:if><br />
						
						<c:if test="${'true' eq  fsscLoanControlForm.fdControlExpense}">
							<input type="checkbox" name="fdControlExpense" value="${fsscLoanControlForm.fdControlExpense}" checked="checked" disabled="disabled"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message3"/>
						</c:if>
						<c:if test="${'false' eq  fsscLoanControlForm.fdControlExpense}">
							<input type="checkbox" name="fdControlExpense" value="${fsscLoanControlForm.fdControlExpense}" disabled="disabled"/><bean:message bundle="fssc-loan" key="fsscLoanControl.type.message3"/>
						</c:if>
	                </td>
	            </tr>
	        </c:if>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-loan:fsscLoanControl.docCreator')}
                </td>
                <td width="85%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${fsscLoanControlForm.docCreatorId}" personName="${fsscLoanControlForm.docCreatorName}" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-loan:fsscLoanControl.docCreateTime')}
                </td>
                <td width="85%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
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
        basePath: '/fssc/loan/fssc_loan_control/fsscLoanControl.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
