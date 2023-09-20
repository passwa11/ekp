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
    if("${eopBasedataPayWayForm.fdName}" != "") {
        window.document.title = "${eopBasedataPayWayForm.fdName} - ${ lfn:message('eop-basedata:table.eopBasedataPayWay') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/eop/basedata/eop_basedata_pay_way/eopBasedataPayWay.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataPayWay.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/eop/basedata/eop_basedata_pay_way/eopBasedataPayWay.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataPayWay.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataPayWay') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <kmss:ifModuleExist path="/fssc/common">
            <tr>
                 <td class="td_normal_title" width="15%">
                     ${lfn:message('eop-basedata:eopBasedataPayWay.fdCompanyList')}
                 </td>
                 <td colspan="3" width="85.0%">
                     <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataInnerOrder.fdCompanyList')}" showStatus="view" style="width:95%;">
                         dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames');
                     </xform:dialog>
                 </td>
            </tr>
            </kmss:ifModuleExist>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.fdName')}
                </td>
                <td width="35%">
                    <%-- 付款方式名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                        ${lfn:message('eop-basedata:eopBasedataPayWay.fdCode')}
                </td>
                <td width="35%">
                        <%-- 付款方式编码--%>
                    <div id="_xform_fdCode" _xform_type="text">
                        <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
            	<%--是否默认付款方式--%>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.fdIsDefault')}
                </td>
                <td width="35%">
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsDefault" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
           		<%--是否涉及转账--%>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.fdIsTransfer')}
                </td>
                <td width="35%">
                    <div id="_xform_fdIsTransfer" _xform_type="radio">
                        <xform:radio property="fdIsTransfer" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                        &nbsp;&nbsp;&nbsp;<span class="com_help">${lfn:message('eop-basedata:fssc.base.payWay.fdIstransfer.tips')} </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.fdAccount')}
                </td>
                <td width="35%">
                    <div id="_xform_fdAccountId" _xform_type="dialog">
                        <xform:dialog propertyId="fdAccountId" propertyName="fdAccountName" subject="${lfn:message('eop-basedata:eopBasedataPayWay.fdAccount')}" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdAccountId','fdAccountName');
                        </xform:dialog>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.fdDefaultPayBank')}
                </td>
                <td width="35%">
                    <div id="_xform_fdDefaultPayBankId" _xform_type="dialog">
                        <xform:dialog propertyId="fdDefaultPayBankId" propertyName="fdDefaultPayBankName" subject="${lfn:message('eop-basedata:eopBasedataPayWay.fdDefaultPayBank')}" showStatus="view" style="width:95%;">
                           dialogSelect(false,'eop_basedata_pay_bank_fdBank','fdDefaultPayBankId','fdDefaultPayBankName',FSSC_AfterBankSelected);
                        </xform:dialog>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.fdOrder')}
                </td>
                <td width="35%">
                    <%-- 排序号--%>
                    <div id="_xform_fdOrder" _xform_type="text">
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.fdStatus')}
                </td>
                <td width="35%">
                    <%-- 状态--%>
                    <div id="_xform_fdStatus" _xform_type="radio">
                        <xform:radio property="fdStatus" htmlElementProperties="id='fdStatus'" showStatus="view">
                            <xform:enumsDataSource enumsType="eop_basedata_mate_status" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.docCreator')}
                </td>
                <td width="35%">
                    <%-- 最近更新人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${eopBasedataPayWayForm.docCreatorId}" personName="${eopBasedataPayWayForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataPayWay.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 最近更新时间--%>
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
        basePath: '/eop/basedata/eop_basedata_pay_way/eopBasedataPayWay.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
