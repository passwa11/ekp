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
	Com_IncludeFile("doclist.js");
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscFeeTemplate.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/fee/fssc_fee_template/fsscFeeTemplate.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscFeeTemplate.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-fee:table.fsscFeeTemplate') }</p>
<center>

    <table class="tb_normal" id="Label_Tabel" width="95%">
        <tr LKS_LabelName="${ lfn:message('fssc-fee:py.JiBenXinXi') }">
            <td>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.docCategory')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 所属分类--%>
                            <div id="_xform_docCategoryId" _xform_type="dialog">
                                <xform:dialog propertyId="docCategoryId" propertyName="docCategoryName" showStatus="view" style="width:95%;">
                                    Dialog_Category('com.landray.kmss.fssc.fee.model.FsscFeeTemplate','docCategoryId','docCategoryName');
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.fdName')}
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
                            ${lfn:message('fssc-fee:fsscFeeTemplate.fdOrder')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 排序号--%>
                            <div id="_xform_fdOrder" _xform_type="text">
                                <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.fdSubjectType')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdSubjectType" _xform_type="radio">
                                <xform:radio property="fdSubjectType" htmlElementProperties="id='fdSubjectType'" showStatus="view">
                                    <xform:enumsDataSource enumsType="fssc_fee_subject_type" />
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
                                <xform:text property="fdSubjectRuleText" showStatus="view" value="${fsscFeeTemplateForm.fdSubjectRuleText }" style="width:95%;" />
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.fdForbid')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdForbid" _xform_type="radio">
                                <xform:radio property="fdForbid">
                                    <xform:enumsDataSource enumsType="fssc_fee_fd_forbid" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    <fssc:checkVersion version="true">
                    <kmss:ifModuleExist path="/fssc/mobile/">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.fdIsMobile')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <div id="_xform_fdIsMobile" _xform_type="radio">
                                <xform:radio property="fdIsMobile">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                    </kmss:ifModuleExist>
                    </fssc:checkVersion>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.authReaders')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 可使用者--%>
                                <c:if test="${fsscFeeTemplateForm.authNotReaderFlag eq 'true'}">
                                    <label>
                                        <bean:message bundle="sys-simplecategory" key="description.main.tempReader.notUse" /></label>
                                </c:if>
                                <c:if test="${fsscFeeTemplateForm.authNotReaderFlag ne 'true'}">
                            <div id="_xform_authReaderIds" _xform_type="address">
                                <xform:address propertyId="authReaderIds" propertyName="authReaderNames" mulSelect="true" orgType="ORG_TYPE_ALL" showStatus="view" textarea="true" style="width:95%;" />
                            </div>
                                </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.authEditors')}
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
                            ${lfn:message('fssc-fee:fsscFeeTemplate.docCreator')}
                        </td>
                        <td width="35%">
                            <%-- 创建人--%>
                            <div id="_xform_docCreatorId" _xform_type="address">
                                <ui:person personId="${fsscFeeTemplateForm.docCreatorId}" personName="${fsscFeeTemplateForm.docCreatorName}" />
                            </div>
                        </td>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('fssc-fee:fsscFeeTemplate.docCreateTime')}
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

        <c:if test="${fsscFeeTemplateForm.docUseXform == 'false'}">
            <tr LKS_LabelName="${lfn:message('fssc-fee:py.webBiaoDan')}">
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
        <c:if test="${fsscFeeTemplateForm.docUseXform == 'true' || empty fsscFeeTemplateForm.docUseXform}">
            <tr LKS_LabelName="${lfn:message('fssc-fee:py.webBiaoDan')}" style="display:none">
                <td>
                    <c:import url="/sys/xform/include/sysFormTemplate_view.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscFeeTemplateForm" />
                        <c:param name="fdKey" value="fsscFeeMain" />
                        <c:param name="fdMainModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
                        <c:param name="useLabel" value="false" />
                    </c:import>
                </td>
            </tr>
        </c:if>
        <kmss:ifModuleExist path="/fssc/mobile/">
		<tr LKS_LabelName="<bean:message bundle='fssc-fee' key='py.YiDongBiaoDan' />">
	        <td>
	            <%@ include file="/fssc/fee/fssc_fee_mobile_config/fsscFeeMobileConfig_view_include.jsp" %>
	        </td>
	    </tr>
	    </kmss:ifModuleExist>
		<c:import url="/sys/lbpmservice/include/sysLbpmTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscFeeTemplateForm" />
            <c:param name="fdKey" value="fsscFeeMain" />
            <c:param name="messageKey" value="fssc-fee:py.LiuChengDingYi" />
        </c:import>
        <c:import url="/sys/number/include/sysNumberMappTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscFeeTemplateForm" />
            <c:param name="modelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
            <c:param name="messageKey" value="fssc-fee:py.BianHaoGuiZe" />
        </c:import>
        <c:import url="/sys/print/include/sysPrintTemplate_view.jsp" charEncoding="UTF-8">
            <c:param name="formName" value="fsscFeeTemplateForm" />
            <c:param name="fdKey" value="fsscFeeMain" />
            <c:param name="modelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
            <c:param name="templateModelName" value="FsscFeeTemplate" />
            <c:param name="useLabel" value="true" />
            <c:param name="messageKey" value="fssc-fee:py.DaYinMoBan" />
        </c:import>
		<tr LKS_LabelName="<bean:message bundle="sys-right" key="right.moduleName" />">
	     	<td>
				<table class="tb_normal" width=100%>
					<c:import url="/sys/right/tmp_right_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="fsscFeeTemplateForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeTemplate" />
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
        basePath: '/fssc/fee/fssc_fee_template/fsscFeeTemplate.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("dialog.js");
    Com_AddEventListener(window,'load',function(){
    	if("${fsscFeeTemplateForm.fdSubjectType}"=='2'){
    		$("#fdSubjectRule").show();
    	}
    });
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
