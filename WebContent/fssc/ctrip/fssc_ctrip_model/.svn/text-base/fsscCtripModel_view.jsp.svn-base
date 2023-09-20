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
    if("${fsscCtripModelForm.fdName}" != "") {
        window.document.title = "${fsscCtripModelForm.fdName} - ${ lfn:message('fssc-ctrip:table.fsscCtripModel') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_model/fsscCtripModel.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('fsscCtripModel.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_model/fsscCtripModel.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('fsscCtripModel.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('fssc-ctrip:table.fsscCtripModel') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdName')}
                    </td>
                    <td width="85%" colspan="3">
                        <%-- 模块名--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdName" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                </tr>
                <tr>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdCateKey')}
                    </td>
                    <td width="35%">
                        <%-- 分类在主表的字段名--%>
                        <div id="_xform_fdName" _xform_type="text">
                            <xform:text property="fdCateKey" showStatus="view" style="width:95%;" />
                        </div>
                    </td>
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('fssc-ctrip:fsscCtripModel.fdKey')}
                    </td>
                    <td width="35%">
                        <%-- 模版对应的key--%>
                        <div id="_xform_fdKey" _xform_type="text">
                            <xform:text property="fdKey" showStatus="view" style="width:95%;" />
                            <br><span class="com_help">${lfn:message('fssc-ctrip:fsscCtripModel.fdKey.tips')}</span>
                        </div>
                    </td>
                </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-ctrip:fsscCtripModel.fdModelName')}
                </td>
                <td width="35%">
                    <%-- 模块modelName--%>
                    <div id="_xform_fdModelName" _xform_type="text">
                        <xform:text property="fdModelName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-ctrip:fsscCtripModel.fdCategoryName')}
                </td>
                <td width="35%">
                    <%-- 对应分类或者模版的model--%>
                    <div id="_xform_fdCategoryName" _xform_type="text">
                        <xform:text property="fdCategoryName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-ctrip:fsscCtripModel.docCreator')}
                </td>
                <td width="35%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${fsscCtripModelForm.docCreatorId}" personName="${fsscCtripModelForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-ctrip:fsscCtripModel.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-ctrip:fsscCtripModel.docAlteror')}
                </td>
                <td width="35%">
                    <%-- 修改人--%>
                    <div id="_xform_docAlterorId" _xform_type="address">
                        <ui:person personId="${fsscCtripModelForm.docAlterorId}" personName="${fsscCtripModelForm.docAlterorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('fssc-ctrip:fsscCtripModel.docAlterTime')}
                </td>
                <td width="35%">
                    <%-- 更新时间--%>
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${fsscCtripModelForm.fdId}" />
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
        basePath: '/fssc/ctrip/fssc_ctrip_model/fsscCtripModel.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>