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

    <kmss:auth requestURL="/fssc/loan/fssc_loan_re_category/fsscLoanReCategory.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscLoanReCategory.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/loan/fssc_loan_re_category/fsscLoanReCategory.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscLoanReCategory.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-loan:table.fsscLoanReCategory') }</p>
<center>

    <table class="tb_normal" id="Label_Tabel" width="95%">
        <tr LKS_LabelName="${ lfn:message('fssc-loan:py.JiBenXinXi') }">
            <td>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.fdParent')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 父节点--%>
                            <a href="${KMSS_Parameter_ContextPath}fssc/loan/fssc_loan_re_category/fsscLoanReCategory.do?method=view&fdId=${fsscLoanReCategoryForm.fdParentId}" target="blank" class="com_btn_link">
                                <c:out value="${fsscLoanReCategoryForm.fdParentName}" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.fdName')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 名称--%>
                            <div id="_xform_fdName" _xform_type="text">
                                <xform:text property="fdName" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.fdSubjectType')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 主题生成方式--%>
                            <div id="_xform_fdSubjectType" _xform_type="radio">
                                <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" showStatus="view">
                                    <xform:enumsDataSource enumsType="fssc_loan_subject_type" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.fdSubjectRule')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 主题生成规则--%>
                            <div id="_xform_fdSubjectRule" _xform_type="text">
                                <xform:text property="fdSubjectRule" showStatus="view" value="${fsscLoanReCategoryForm.fdSubjectRule }" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.fdLoanCategory')}
                        </td>
                        <td width="35%">
                            <%-- 借款类别--%>
                            <div id="_xform_fdLoanCategoryId" _xform_type="dialog">
                                <xform:dialog propertyId="fdLoanCategoryId" propertyName="fdLoanCategoryName" showStatus="view" style="width:95%;">
                                    dialogSelect(false,'fssc_loan_category_fsscLoanCategory','fdLoanCategoryId','fdLoanCategoryName',null,{'fdType':'lastStage'});
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.authReaders')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 可使用者--%>
                            <div id="_xform_authReaderIds" _xform_type="address">
                                <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.authEditors')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 可维护者--%>
                            <div id="_xform_authEditorIds" _xform_type="address">
                                <xform:address propertyId="authEditorIds" propertyName="authEditorNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.docCreator')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 创建人--%>
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${fsscLoanReCategoryForm.docCreatorId}" personName="${fsscLoanReCategoryForm.docCreatorName}" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanReCategory.docCreateTime')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 创建时间--%>
                            <div id="_xform_docCreateTime" _xform_type="datetime">
                                <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscLoanReCategoryForm" />
            <c:param name="fdKey" value="fsscLoanRepayment" />
            <c:param name="messageKey" value="fssc-loan:py.LiuChengDingYi" />
        </c:import>

        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscLoanReCategoryForm" />
            <c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanRepayment" />
            <c:param name="messageKey" value="fssc-loan:py.BianHaoGuiZe" />
        </c:import>
		<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
	     	<td>
				<table class="tb_normal" width=100%>
					<c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanReCategoryForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanReCategory" />
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
        basePath: '/fssc/loan/fssc_loan_re_category/fsscLoanReCategory.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
