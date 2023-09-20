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
    if("${thirdWeixinPayCbForm.fdMchId}" != "") {
        window.document.title = "${thirdWeixinPayCbForm.fdMchId} - ${ lfn:message('third-weixin:table.thirdWeixinPayCb') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/third/weixin/third_weixin_pay_cb/thirdWeixinPayCb.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinPayCb.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinPayCb') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdMchId')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdDeviceInfo')}
                </td>
                <td width="35%">
                    <%-- 设备号--%>
                    <div id="_xform_fdDeviceInfo" _xform_type="text">
                        <xform:text property="fdDeviceInfo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdNonceStr')}
                </td>
                <td width="35%">
                    <%-- 随机字符串--%>
                    <div id="_xform_fdNonceStr" _xform_type="text">
                        <xform:text property="fdNonceStr" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdSign')}
                </td>
                <td width="35%">
                    <%-- 签名--%>
                    <div id="_xform_fdSign" _xform_type="text">
                        <xform:text property="fdSign" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdSignType')}
                </td>
                <td width="35%">
                    <%-- 签名类型--%>
                    <div id="_xform_fdSignType" _xform_type="text">
                        <xform:text property="fdSignType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdTradeType')}
                </td>
                <td width="35%">
                    <%-- 交易类型--%>
                    <div id="_xform_fdTradeType" _xform_type="select">
                        <xform:select property="fdTradeType" htmlElementProperties="id='fdTradeType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_weixin_trade_type" />
                        </xform:select>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdOpenid')}
                </td>
                <td width="35%">
                    <%-- 用户标识--%>
                    <div id="_xform_fdOpenid" _xform_type="text">
                        <xform:text property="fdOpenid" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdIsSubscribe')}
                </td>
                <td width="35%">
                    <%-- 是否关注公众账号--%>
                    <div id="_xform_fdIsSubscribe" _xform_type="radio">
                        <xform:radio property="fdIsSubscribe" htmlElementProperties="id='fdIsSubscribe'" showStatus="view">
                            <xform:enumsDataSource enumsType="common_yesno" />
                        </xform:radio>
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdBankType')}
                </td>
                <td width="35%">
                    <%-- 付款银行--%>
                    <div id="_xform_fdBankType" _xform_type="text">
                        <xform:text property="fdBankType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdSettlementTotalFee')}
                </td>
                <td width="35%">
                    <%-- 应结订单金额--%>
                    <div id="_xform_fdSettlementTotalFee" _xform_type="text">
                        <xform:text property="fdSettlementTotalFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdCashFee')}
                </td>
                <td width="35%">
                    <%-- 现金支付金额--%>
                    <div id="_xform_fdCashFee" _xform_type="text">
                        <xform:text property="fdCashFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdCashFeeType')}
                </td>
                <td width="35%">
                    <%-- 现金支付币种--%>
                    <div id="_xform_fdCashFeeType" _xform_type="text">
                        <xform:text property="fdCashFeeType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdCouponFee')}
                </td>
                <td width="35%">
                    <%-- 代金券金额--%>
                    <div id="_xform_fdCouponFee" _xform_type="text">
                        <xform:text property="fdCouponFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdCouponCount')}
                </td>
                <td width="35%">
                    <%-- 代金券使用数量--%>
                    <div id="_xform_fdCouponCount" _xform_type="text">
                        <xform:text property="fdCouponCount" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdCouponType')}
                </td>
                <td width="35%">
                    <%-- 代金券类型--%>
                    <div id="_xform_fdCouponType" _xform_type="text">
                        <xform:text property="fdCouponType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdCouponId')}
                </td>
                <td width="35%">
                    <%-- 代金券ID--%>
                    <div id="_xform_fdCouponId" _xform_type="text">
                        <xform:text property="fdCouponId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdCouponFeeOne')}
                </td>
                <td width="35%">
                    <%-- 单个代金券支付金额--%>
                    <div id="_xform_fdCouponFeeOne" _xform_type="text">
                        <xform:text property="fdCouponFeeOne" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdTransactionId')}
                </td>
                <td width="35%">
                    <%-- 微信支付订单号--%>
                    <div id="_xform_fdTransactionId" _xform_type="text">
                        <xform:text property="fdTransactionId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdTimeEnd')}
                </td>
                <td width="35%">
                    <%-- 支付完成时间--%>
                    <div id="_xform_fdTimeEnd" _xform_type="text">
                        <xform:text property="fdTimeEnd" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdAppid')}
                </td>
                <td width="35%">
                    <%-- 小程序ID--%>
                    <div id="_xform_fdAppid" _xform_type="text">
                        <xform:text property="fdAppid" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdResultCode')}
                </td>
                <td width="35%">
                    <%-- 业务结果--%>
                    <div id="_xform_fdResultCode" _xform_type="text">
                        <xform:text property="fdResultCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdErrCode')}
                </td>
                <td width="35%">
                    <%-- 错误代码--%>
                    <div id="_xform_fdErrCode" _xform_type="text">
                        <xform:text property="fdErrCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdErrCodeDes')}
                </td>
                <td width="35%">
                    <%-- 错误代码描述--%>
                    <div id="_xform_fdErrCodeDes" _xform_type="text">
                        <xform:text property="fdErrCodeDes" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdTotalFee')}
                </td>
                <td width="35%">
                    <%-- 订单金额--%>
                    <div id="_xform_fdTotalFee" _xform_type="text">
                        <xform:text property="fdTotalFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdOutTradeNo')}
                </td>
                <td width="35%">
                    <%-- 商户订单号--%>
                    <div id="_xform_fdOutTradeNo" _xform_type="text">
                        <xform:text property="fdOutTradeNo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdAttach')}
                </td>
                <td width="35%">
                    <%-- 商家数据包--%>
                    <div id="_xform_fdAttach" _xform_type="text">
                        <xform:text property="fdAttach" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdReturnCode')}
                </td>
                <td width="35%">
                    <%-- 返回状态码--%>
                    <div id="_xform_fdReturnCode" _xform_type="text">
                        <xform:text property="fdReturnCode" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdReturnMsg')}
                </td>
                <td width="35%">
                    <%-- 返回信息--%>
                    <div id="_xform_fdReturnMsg" _xform_type="text">
                        <xform:text property="fdReturnMsg" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2" width="50.0%">
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdReqData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 请求报文--%>
                    <div id="_xform_fdReqData" _xform_type="rtf">
                        <c:out value="${thirdWeixinPayCbForm.fdReqData}" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayCb.fdResData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 响应报文--%>
                    <div id="_xform_fdResData" _xform_type="rtf">
                        <c:out value="${thirdWeixinPayCbForm.fdResData}" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinPayCbForm.fdId}" />
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
        basePath: '/third/weixin/third_weixin_pay_cb/thirdWeixinPayCb.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>