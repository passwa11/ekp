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

    <kmss:auth requestURL="/third/ding/third_ding_error/thirdDingError.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdDingError.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/ding/third_ding_error/thirdDingError.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingError.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-ding:table.thirdDingError') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%" style="table-layout:fixed">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdName')}
                </td>
                <td width="35.0%">
                    <%-- 功能名称--%>
                    <div id="_xform_fdName" _xform_type="text">
                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdCount')}
                </td>
                <td width="35.0%">
                    <%-- 执行次数--%>
                    <div id="_xform_fdCount" _xform_type="text">
                        <xform:text property="fdCount" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdModelId')}
                </td>
                <td width="35%">
                    <%-- 业务Id--%>
                    <div id="_xform_fdModelId" _xform_type="text">
                        <xform:text property="fdModelId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdModelName')}
                </td>
                <td width="35%">
                    <%-- 业务模型名--%>
                    <div id="_xform_fdModelName" _xform_type="text">
                        <xform:text property="fdModelName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdContent')}
                </td>
                <td colspan="3" width="85%" style="word-wrap: break-word;">
                    <%-- 消息内容--%>
                    	${ thirdDingErrorForm.fdContent}
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdErrorMsg')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 错误消息--%>
                    <div id="_xform_fdErrorMsg" _xform_type="text">
                        <xform:text property="fdErrorMsg" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdHandleError')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 错误消息--%>
                    <div id="_xform_fdHandleError" _xform_type="text">
                        <xform:text property="fdHandleError" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdServiceName')}
                </td>
                <td width="35%">
                    <%-- 执行服务--%>
                    <div id="_xform_fdServiceName" _xform_type="text">
                        <xform:text property="fdServiceName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdServiceMethod')}
                </td>
                <td width="35%">
                    <%-- 执行方法--%>
                    <div id="_xform_fdServiceMethod" _xform_type="text">
                        <xform:text property="fdServiceMethod" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdMethodParam')}
                </td>
                <td width="35%">
                    <%-- 执行参数--%>
                    <div id="_xform_fdMethodParam" _xform_type="text">
                        <xform:text property="fdMethodParam" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-ding:thirdDingError.fdCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_fdCreateTime" _xform_type="datetime">
                        <xform:datetime property="fdCreateTime" showStatus="view" style="width:95%;" />
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
        basePath: '/third/ding/third_ding_error/thirdDingError.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>