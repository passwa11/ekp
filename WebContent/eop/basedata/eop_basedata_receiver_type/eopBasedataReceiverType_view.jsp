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
    if("${eopBasedataReceiverTypeForm.fdName}" != "") {
        window.document.title = "${eopBasedataReceiverTypeForm.fdName} - ${ lfn:message('eop-basedata:table.eopBasedataReceiverType') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/eop/basedata/eop_basedata_receiver_type/eopBasedataReceiverType.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataReceiverType.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/eop/basedata/eop_basedata_receiver_type/eopBasedataReceiverType.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataReceiverType.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataReceiverType') }</p>
    <div style="width:95%;margin:0 auto;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdCompanyList')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdCompanyId" _xform_type="dialog">
                        <xform:dialog propertyId="fdCompanyListIds" propertyName="fdCompanyListNames" subject="${lfn:message('eop-basedata:eopBasedataReceiverType.fdCompanyList')}" showStatus="view" style="width:95%;">
                            dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyListIds','fdCompanyListNames');
                        </xform:dialog>
                    </div>
                    </a>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdName')}
                </td>
                <td width="35.0%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdCode')}
                </td>
                <td width="35.0%">
                    <%-- 编码--%>
                    <div id="_xform_fdCode" _xform_type="text">
                        <xform:text property="fdCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdAccounts')}
                </td>
                <td width="35.0%">
                    <%-- 会计科目--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:dialog propertyId="fdAccountsId" propertyName="fdAccountsName" subject="${lfn:message('eop-basedata:eopBasedataReceiverType.fdAccounts')}" required="true" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_accounts_com_fdAccount','fdAccountsId','fdAccountsName');
                        </xform:dialog>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdType')}
                </td>
                <td width="35.0%">
                    <div id="_xform_fdType" _xform_type="radio">
                        <xform:radio property="fdType" showStatus="view">
                            <xform:enumsDataSource enumsType="eop_basedata_receiver_type" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.fdIsAvailable')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdIsAvailable" _xform_type="radio">
                        <xform:radio property="fdIsAvailable" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataReceiverType.docCreator')}
                </td>
                <td width="35%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${eopBasedataReceiverTypeForm.docCreatorId}" personName="${eopBasedataReceiverTypeForm.docCreatorName}" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
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
