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
    if("${fsscMobileInvoiceTitleForm.fdName}" != "") {
        window.document.title = "${fsscMobileInvoiceTitleForm.fdName} - ${ lfn:message('fssc-mobile:table.fsscMobileInvoiceTitle') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/mobile/fssc_mobile_invoice_title/fsscMobileInvoiceTitle.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscMobileInvoiceTitle.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/mobile/fssc_mobile_invoice_title/fsscMobileInvoiceTitle.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscMobileInvoiceTitle.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-mobile:table.fsscMobileInvoiceTitle') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdName')}
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
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdTaxNo')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 税号--%>
                    <div id="_xform_fdTaxNo" _xform_type="text">
                        <xform:text property="fdTaxNo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdAddress')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 单位地址--%>
                    <div id="_xform_fdAddress" _xform_type="text">
                        <xform:text property="fdAddress" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdPhone')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 电话号码--%>
                    <div id="_xform_fdPhone" _xform_type="text">
                        <xform:text property="fdPhone" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdBankName')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 开户银行--%>
                    <div id="_xform_fdBankName" _xform_type="text">
                        <xform:text property="fdBankName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdBankAccount')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 银行账户--%>
                    <div id="_xform_fdBankAccount" _xform_type="text">
                        <xform:text property="fdBankAccount" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdUserList')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 可使用者--%>
                    <div id="_xform_fdUserListIds" _xform_type="address">
                        <xform:address propertyId="fdUserListIds" propertyName="fdUserListNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.fdEditorList')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 可维护者--%>
                    <div id="_xform_fdEditorListIds" _xform_type="address">
                        <xform:address propertyId="fdEditorListIds" propertyName="fdEditorListNames" mulSelect="true" orgType="ORG_TYPE_ALLORG" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.docCreator')}
                </td>
                <td width="35%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${fsscMobileInvoiceTitleForm.docCreatorId}" personName="${fsscMobileInvoiceTitleForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-mobile:fsscMobileInvoiceTitle.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
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
        basePath: '/fssc/mobile/fssc_mobile_invoice_title/fsscMobileInvoiceTitle.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
