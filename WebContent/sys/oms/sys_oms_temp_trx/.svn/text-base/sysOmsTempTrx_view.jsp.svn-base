<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ include file="/sys/log/resource/import/jshead.jsp"%>
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
    .fail{
    	color: red;
    }
</style>
<script type="text/javascript">
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">
    <kmss:auth requestURL="/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=downloadLog&type=detail&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('sys-oms:sysOmsTempTrx.fdLogDetail.download')}" onclick="Com_OpenWindow('sysOmsTempTrx.do?method=downloadLog&type=detail&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <kmss:auth requestURL="/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do?method=downloadLog&type=error&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('sys-oms:sysOmsTempTrx.fdLogError.download')}" onclick="Com_OpenWindow('sysOmsTempTrx.do?method=downloadLog&type=error&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <%-- 开始时间 --%>
                <td class="td_normal_title" width="15%">
                   开始时间
                </td>
                <td width="35%">
                    <div id="_xform_beginTime" _xform_type="datetime">
                                    <xform:text property="beginTime" showStatus="view" />
                                </div>
                </td>
                <%-- 结束时间 --%>
                <td class="td_normal_title" width="15%">
                   结束时间
                </td>
                <td width="35%">
                    <div id="_xform_endTime" _xform_type="datetime">
                                    <xform:text property="endTime" showStatus="view" />
                                </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    错误日志
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 错误日志--%>
                    <div class="pre_hide">
                   		<pre>${sysOmsTempTrxForm.fdLogError }</pre>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    详细日志
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 详细日志--%>
                    <div class="pre_hide">
                    	<pre>${sysOmsTempTrxForm.fdLogDetail }</pre>
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
        basePath: '/sys/oms/sys_oms_temp_trx/sysOmsTempTrx.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    
    $(function(){
		//处理高度较高，需要隐藏的div
		seajs.use(['sys/log/resource/js/autoHide'], function(autoHide) {
			autoHide.init(200);
		});
    });
</script>
<!-- 引入CSS -->
<link type="text/css" rel="styleSheet"  href="${LUI_ContextPath}/sys/log/resource/css/autoHide.css" />
<!-- 引入JS -->
<script type="text/javascript" src="${LUI_ContextPath}/sys/log/resource/js/autoHide.js"></script>
<%@ include file="/resource/jsp/view_down.jsp" %>