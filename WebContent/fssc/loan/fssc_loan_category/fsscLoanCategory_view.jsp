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
<script type="text/javascript">
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/loan/fssc_loan_category/fsscLoanCategory.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscLoanCategory.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/loan/fssc_loan_category/fsscLoanCategory.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscLoanCategory.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-loan:table.fsscLoanCategory') }</p>
<center>

    <table class="tb_normal" id="Label_Tabel" width="95%">
        <tr LKS_LabelName="${ lfn:message('fssc-loan:py.JiBenXinXi') }">
            <td>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanCategory.fdParent')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 父节点--%>
                            <a href="${KMSS_Parameter_ContextPath}fssc/loan/fssc_loan_category/fsscLoanCategory.do?method=view&fdId=${fsscLoanCategoryForm.fdParentId}" target="blank" class="com_btn_link">
                                <c:out value="${fsscLoanCategoryForm.fdParentName}" />
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanCategory.fdName')}
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
                            ${lfn:message('fssc-loan:fsscLoanCategory.fdSubjectType')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 主题生成方式--%>
                            <div id="_xform_fdSubjectType" _xform_type="text">
                                <xform:text property="fdSubjectType" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanCategory.fdSubjectRule')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 主题规则--%>
                            <div id="_xform_fdSubjectRule" _xform_type="text">
                                <xform:text property="fdSubjectRule" showStatus="view" value="${fsscLoanCategoryForm.fdSubjectRule }" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <kmss:ifModuleExist path="/fssc/fee">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanCategory.fdIsRequiredFee')}
                        </td>
                        <td width="85.0%">
                            <%-- 是否关联事前申请--%>
                            <div id="_xform_fdIsRequiredFee" _xform_type="radio">
                            	<sunbor:enumsShow enumsType="common_yesno" value="${fsscLoanCategoryForm.fdIsRequiredFee}"></sunbor:enumsShow>
                            </div>
                        </td>
                    </tr>
                    </kmss:ifModuleExist>
                     <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanCategory.fdIsProject')}
                        </td>
                        <td width="85.0%">
                            <%-- 是否项目费用--%>
                            <div id="_xform_fdIsProject" _xform_type="radio">
                            	<sunbor:enumsShow enumsType="common_yesno" value="${fsscLoanCategoryForm.fdIsProject}"></sunbor:enumsShow>
                            </div>
                        </td>
                    </tr>
                    <fssc:configEnabled value="SAP" property="fdFinancialSystem">
                      	<c:set var="SapEnabled" value="true"/>
                    </fssc:configEnabled>
                    <c:if test="${empty SapEnabled }">
                    	<tr>
                          <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdExtendFields')}
                            </td>
                            <td width="85.0%">
                                <%-- 可选字段--%>
                                <div id="_xform_fdExtendFields" _xform_type="radio">
                                	<xform:radio property="fdExtendFields" required="true">
                                		<xform:enumsDataSource enumsType="fssc_category_extend_fields"></xform:enumsDataSource>
                                	</xform:radio>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                    <c:if test="${not empty SapEnabled }">
                    	<tr>
                          <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdExtendFields')}
                            </td>
                            <td width="85.0%">
                                <%-- 可选字段--%>
                                <div id="_xform_fdExtendFields" _xform_type="radio">
                                	<xform:radio property="fdExtendFields" required="true">
                                		<xform:enumsDataSource enumsType="fssc_category_extend_fields_contains_sap"></xform:enumsDataSource>
                                	</xform:radio>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                         <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdIsContRepayDay')}
                            </td>
                            <td width="85.0%">
                                <%-- 控制天数--%>
                                <div id="_xform_fdIsContRepayDay" _xform_type="text">
                                	<xform:text property="fdIsContRepayDay"  />
                                	<span>day</span>
                                </div>
                            </td>
                        </tr>
                         <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdIsErasable')}
                            </td>
                            <td width="85.0%">
                                <%-- --%>
                                <div id="_xform_fdIsErasable" _xform_type="radio">
                                	<xform:radio property="fdIsErasable" >
                                		<xform:enumsDataSource enumsType="common_yesno"></xform:enumsDataSource>
                                	</xform:radio>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="td_normal_title" width="15%">
                                ${lfn:message('fssc-loan:fsscLoanCategory.fdLoanTotalType')}
                            </td>
                            <td width="85.0%">
                                <%-- 借款统计维度--%>
                                <div id="_xform_fdLoanTotalType" _xform_type="radio">
                                	<xform:radio property="fdLoanTotalType" required="true">
                                		<xform:enumsDataSource enumsType="fssc_loan_total_type"></xform:enumsDataSource>
                                	</xform:radio>
                                </div>
                            </td>
                        </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanCategory.authReaders')}
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
                            ${lfn:message('fssc-loan:fsscLoanCategory.authEditors')}
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
                            ${lfn:message('fssc-loan:fsscLoanCategory.docCreator')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 创建人--%>
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${fsscLoanCategoryForm.docCreatorId}" personName="${fsscLoanCategoryForm.docCreatorName}" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-loan:fsscLoanCategory.docCreateTime')}
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
        <c:if test="${fsscLoanCategoryForm.docUseXform == 'false'}">
            <tr LKS_LabelName="${lfn:message('fssc-loan:py.BiaoDanMoBan')}">
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
        <c:if test="${fsscLoanCategoryForm.docUseXform == 'true' || empty fsscLoanCategoryForm.docUseXform}">
            <tr LKS_LabelName="${lfn:message('fssc-loan:py.BiaoDanMoBan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscLoanCategoryForm" />
                        <c:param name="fdKey" value="fsscLoanMain" />
                        <c:param name="fdMainModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
                        <c:param name="useLabel" value="false" />
                    </c:import>
                </td>
            </tr>
        </c:if>
        <c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscLoanCategoryForm" />
            <c:param name="fdKey" value="fsscLoanMain" />
            <c:param name="messageKey" value="fssc-loan:py.LiuChengDingYi" />
        </c:import>

        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscLoanCategoryForm" />
            <c:param name="modelName" value="com.landray.kmss.fssc.loan.model.FsscLoanMain" />
            <c:param name="messageKey" value="fssc-loan:py.BianHaoGuiZe" />
        </c:import>
		<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
	     	<td>
				<table class="tb_normal" width=100%>
					<c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscLoanCategoryForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.fssc.loan.model.FsscLoanCategory" />
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
        basePath: '/fssc/loan/fssc_loan_category/fsscLoanCategory.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
