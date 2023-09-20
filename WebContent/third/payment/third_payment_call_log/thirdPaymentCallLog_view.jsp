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
    if("${thirdPaymentCallLogForm.fdModelName}" != "") {
        window.document.title = "${thirdPaymentCallLogForm.fdModelName} - ${ lfn:message('third-payment:table.thirdPaymentCallLog') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">
    <kmss:auth requestURL="/third/payment/third_payment_call_log/thirdPaymentCallLog.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdPaymentCallLog.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-payment:table.thirdPaymentCallLog') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdModelName')}
                </td>
                <td width="35%">
                    <%-- 主文档类型--%>
                    <div id="_xform_fdModelName" _xform_type="text">
                        <xform:text property="fdModelName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdModelId')}
                </td>
                <td width="35%">
                    <%-- 主文档ID--%>
                    <div id="_xform_fdModelId" _xform_type="text">
                        <xform:text property="fdModelId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdKey')}
                </td>
                <td width="35%">
                    <%-- 主文档关键字--%>
                    <div id="_xform_fdKey" _xform_type="text">
                        <xform:text property="fdKey" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdOrderNo')}
                </td>
                <td width="35%">
                    <%-- 订单号--%>
                    <div id="_xform_fdOrderNo" _xform_type="text">
                        <xform:text property="fdOrderNo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdCallMethod')}
                </td>
                <td width="35%">
                    <%-- 调用接口--%>
                    <div id="_xform_fdCallMethod" _xform_type="text">
                        <xform:text property="fdCallMethod" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdCallResult')}
                </td>
                <td width="35%">
                    <%-- 请求结果--%>
                    <div id="_xform_fdCallResult" _xform_type="select">
                        <xform:select property="fdCallResult" htmlElementProperties="id='fdCallResult'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_payment_call_result" />
                        </xform:select>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdOrderDesc')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 订单描述--%>
                    <div id="_xform_fdOrderDesc" _xform_type="text">
                        <xform:text property="fdOrderDesc" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdPaymentService')}
                </td>
                <td width="35%">
                    <%-- 支付服务--%>
                    <div id="_xform_fdPaymentService" _xform_type="select">
                        <xform:select property="fdPaymentService" htmlElementProperties="id='fdPaymentService'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_payment_service" />
                        </xform:select>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdPayType')}
                </td>
                <td width="35%">
                    <%-- 支付方式--%>
                    <div id="_xform_fdPayType" _xform_type="select">
                        <xform:select property="fdPayType" htmlElementProperties="id='fdPayType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_payment_type" />
                        </xform:select>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdTotalMoney')}
                </td>
                <td width="35%">
                    <%-- 订单总金额--%>
                    <div id="_xform_fdTotalMoney" _xform_type="text">
                        <xform:text property="fdTotalMoney" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.docCreateTime')}
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
                    ${lfn:message('third-payment:thirdPaymentCallLog.docCreator')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${thirdPaymentCallLogForm.docCreatorId}" personName="${thirdPaymentCallLogForm.docCreatorName}" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdReqData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 请求报文--%>
                    <div id="_xform_fdReqData" _xform_type="rtf">
                        <xform:rtf property="fdReqData" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdResData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 响应报文--%>
                    <div id="_xform_fdResData" _xform_type="rtf">
                        <xform:rtf property="fdResData" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-payment:thirdPaymentCallLog.fdErrorMsg')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 错误信息--%>
                    <div id="_xform_fdErrorMsg" _xform_type="rtf">
                        <xform:rtf property="fdErrorMsg" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdPaymentCallLogForm.fdId}" />
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
        basePath: '/third/payment/third_payment_call_log/thirdPaymentCallLog.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>