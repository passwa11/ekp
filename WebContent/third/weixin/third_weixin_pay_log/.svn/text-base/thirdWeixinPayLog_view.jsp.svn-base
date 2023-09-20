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
    if("${thirdWeixinPayLogForm.fdUrl}" != "") {
        window.document.title = "${thirdWeixinPayLogForm.fdUrl} - ${ lfn:message('third-weixin:table.thirdWeixinPayLog') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/third/weixin/third_weixin_pay_log/thirdWeixinPayLog.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinPayLog.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinPayLog') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdReqData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 请求报文--%>
                    <div id="_xform_fdReqData" _xform_type="rtf">
                        <c:out value="${thirdWeixinPayLogForm.fdReqData}" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdResData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 响应报文--%>
                    <div id="_xform_fdResData" _xform_type="rtf">
                        <c:out value="${thirdWeixinPayLogForm.fdResData}" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdReqDate')}
                </td>
                <td width="35%">
                    <%-- 请求时间--%>
                    <div id="_xform_fdReqDate" _xform_type="datetime">
                        <xform:datetime property="fdReqDate" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdResDate')}
                </td>
                <td width="35%">
                    <%-- 响应时间--%>
                    <div id="_xform_fdResDate" _xform_type="datetime">
                        <xform:datetime property="fdResDate" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdResult')}
                </td>
                <td width="35%">
                    <%-- 结果--%>
                    <div id="_xform_fdResult" _xform_type="select">
                        <xform:select property="fdResult" htmlElementProperties="id='fdResult'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_weixin_req_result" />
                        </xform:select>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdUrl')}
                </td>
                <td width="35%">
                    <%-- 请求地址--%>
                    <div id="_xform_fdUrl" _xform_type="text">
                        <xform:text property="fdUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdErrMsg')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 错误信息--%>
                    <div id="_xform_fdErrMsg" _xform_type="rtf">
                        <xform:rtf property="fdErrMsg" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdExpireTime')}
                </td>
                <td width="35%">
                    <%-- 请求耗时--%>
                    <div id="_xform_fdExpireTime" _xform_type="text">
                        <xform:text property="fdExpireTime" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdBody')}
                </td>
                <td width="35%">
                    <%-- 商品描述--%>
                    <div id="_xform_fdBody" _xform_type="text">
                        <xform:text property="fdBody" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdDetail')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 商品详情--%>
                    <div id="_xform_fdDetail" _xform_type="rtf">
                        <xform:rtf property="fdDetail" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdOutTradeNo')}
                </td>
                <td width="35%">
                    <%-- 商户订单号--%>
                    <div id="_xform_fdOutTradeNo" _xform_type="text">
                        <xform:text property="fdOutTradeNo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdPrepayId')}
                </td>
                <td width="35%">
                    <%-- 预支付交易会话标识--%>
                    <div id="_xform_fdPrepayId" _xform_type="text">
                        <xform:text property="fdPrepayId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdCodeUrl')}
                </td>
                <td width="35%">
                    <%-- 二维码链接--%>
                    <div id="_xform_fdCodeUrl" _xform_type="text">
                        <xform:text property="fdCodeUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdMchId')}
                </td>
                <td width="35%">
                    <%-- 商户号--%>
                    <div id="_xform_fdMchId" _xform_type="text">
                        <xform:text property="fdMchId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayLog.fdAppId')}
                </td>
                <td width="35%">
                    <%-- 公众账号ID--%>
                    <div id="_xform_fdAppId" _xform_type="text">
                        <xform:text property="fdAppId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2">
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinPayLogForm.fdId}" />
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
        basePath: '/third/weixin/third_weixin_pay_log/thirdWeixinPayLog.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>