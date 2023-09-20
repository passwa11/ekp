<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
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

    <kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('eopBasedataCardInfo.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('eopBasedataCardInfo.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('eop-basedata:table.eopBasedataCardInfo') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCorNum')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdCorNum" _xform_type="text">
                        <xform:text property="fdCorNum" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCorChiName')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdCorChiName" _xform_type="text">
                        <xform:text property="fdCorChiName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCompany')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdCompanyId" _xform_type="dialog">
                        <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName" subject="${lfn:message('eop-basedata:eopBasedataCardInfo.fdCompany')}" showStatus="view" style="width:95%;">
                            dialogSelect(false,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName');
                        </xform:dialog>
                    </div>
                    </a>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdActNum')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdActNum" _xform_type="text">
                        <xform:text property="fdActNum" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdAcctNbr')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdAcctNbr" _xform_type="text">
                        <xform:text property="fdAcctNbr" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCardNumber')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdCardNumber" _xform_type="text">
                        <xform:text property="fdCardNumber"  showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolder')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdHolderChiName" _xform_type="address">
                        <xform:address required="true" propertyId="fdHolderId" propertyName="fdHolderName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolderChiName')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdHolderChiName" _xform_type="text">
                        <xform:text property="fdHolderChiName" showStatus="view" style="width:95%;"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdHolderEngName')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdHolderEngName" _xform_type="text">
                        <xform:text property="fdHolderEngName" showStatus="view" style="width:95%;"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdEmpNumber')}
                </td>
                <td colspan="3" width="85.0%">
                    <div id="_xform_fdEmpNumber" _xform_type="text">
                        <xform:text property="fdEmpNumber" showStatus="view" style="width:95%;"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdIsAvailable')}
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
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdActivationCode')}
                </td>
                <td width="35%">
                    <div id="_xform_fdActivationCode">
                        <xform:select property="fdActivationCode" showStatus="view">
                            <xform:enumsDataSource enumsType="eop_basedata_cardInfo_activationCode" />
                        </xform:select>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdActivationDate')}
                </td>
                <td width="35%">
                    <div id="_xform_fdActivationDate" _xform_type="datetime">
                        <xform:datetime property="fdActivationDate" showStatus="view" style="width:95%;" dateTimeType="date"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCirculationFlag')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCirculationFlag">
                        <xform:select property="fdCirculationFlag" showStatus="view">
                            <xform:enumsDataSource enumsType="eop_basedata_cardInfo_circulationFlag" />
                        </xform:select>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.fdCancelDate')}
                </td>
                <td width="35%">
                    <div id="_xform_fdCancelDate" _xform_type="datetime">
                        <xform:datetime property="fdCancelDate" showStatus="view" style="width:95%;" dateTimeType="date"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.docCreator')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${eopBasedataCardInfoForm.docCreatorId}" personName="${eopBasedataCardInfoForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.docCreateTime')}
                </td>
                <td width="35%">
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.docAlteror')}
                </td>
                <td width="35%">
                    <div id="_xform_docAlterorId" _xform_type="address">
                        <ui:person personId="${eopBasedataCardInfoForm.docAlterorId}" personName="${eopBasedataCardInfoForm.docAlterorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('eop-basedata:eopBasedataCardInfo.docAlterTime')}
                </td>
                <td width="35%">
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" style="width:95%;" />
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
        basePath: '/eop/basedata/eop_basedata_card_info/eopBasedataCardInfo.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
