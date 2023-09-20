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
    if("${fsscAlitripMessageForm.fdName}" != "") {
        window.document.title = "${fsscAlitripMessageForm.fdName} - ${ lfn:message('fssc-alitrip:table.fsscAlitripMessage') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscAlitripMessage.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscAlitripMessage.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-alitrip:table.fsscAlitripMessage') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdName')}
                </td>
                <td colspan="3" width="49.8%">
                    <%-- 名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdOrder')}
                </td>
                <td width="16.6%">
                    <%-- 排序号--%>
                    <div id="_xform_fdOrder" _xform_type="text">
                        <xform:text property="fdOrder" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppKey')}
                </td>
                <td width="16.6%">
                    <%-- Appkey--%>
                    <div id="_xform_fdAppKey" _xform_type="text">
                        <xform:text property="fdAppKey" showStatus="view" style="width:95%;" />
                        <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppKey.tips')}</span>
                    </div>
                </td>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdAppSecret')}
                </td>
                <td colspan="3" width="49.8%">
                    <%-- Appsecret--%>
                    <div id="_xform_fdAppSecret" _xform_type="text">
                        <xform:text property="fdAppSecret" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.corpid')}
                </td>
                <td width="16.6%">
                    <%-- Corpid--%>
                    <div id="_xform_corpid" _xform_type="text">
                        <xform:text property="corpid" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.corpname')}
                </td>
                <td colspan="3" width="49.8%">
                    <%-- CorpName--%>
                    <div id="_xform_corpname" _xform_type="text">
                        <xform:text property="corpname" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                    <td class="td_normal_title" width="16.6%">
                        ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdCompanyCode')}
                    </td>
                    <td width="16.6%" colspan="5">
                        <%-- 对应公司代码--%>
                        <div id="_xform_fdCompanyName" _xform_type="text">
                            <xform:dialog propertyId="fdCompanyId" propertyName="fdCompanyName"  subject="${lfn:message('fssc-alitrip:fsscAlitripMessage.fdCompanyName')}" style="width:95%;">
                                            dialogSelect(true,'eop_basedata_company_fdCompany','fdCompanyId','fdCompanyName',null);
                                        </xform:dialog>
                        </div>
                    </td>
                </tr>
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdSynCostCenter')}
                </td>
                <td colspan="5" width="83.0%">
                    <%-- 是否同步成本中心--%>
                    <div id="_xform_fdSynCostCenter" _xform_type="radio">
                        <xform:radio property="fdSynCostCenter" htmlElementProperties="id='fdSynCostCenter'" showStatus="view">
                            <xform:enumsDataSource enumsType="fssc_alitrip_syn_cost_center" />
                        </xform:radio>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdSynCenterSql')}
                </td>
                <td colspan="5" width="83.0%">
                    <%-- 同步获取成本中心sql--%>
                    <div id="_xform_fdSynCenterSql" _xform_type="text">
                        <xform:text property="fdSynCenterSql" showStatus="view" style="width:95%;" />
                        <br><span class="com_help">${lfn:message('fssc-alitrip:fsscAlitripMessage.fdSynCenterSql.tips')}</span>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.fdDesc')}
                </td>
                <td colspan="5" width="83.0%">
                    <%-- 描述--%>
                    <div id="_xform_fdDesc" _xform_type="textarea">
                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.docCreator')}
                </td>
                <td width="16.6%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${fsscAlitripMessageForm.docCreatorId}" personName="${fsscAlitripMessageForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="16.6%">
                    ${lfn:message('fssc-alitrip:fsscAlitripMessage.docCreateTime')}
                </td>
                <td colspan="3" width="49.8%">
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
        basePath: '/fssc/alitrip/fssc_alitrip_message/fsscAlitripMessage.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>
