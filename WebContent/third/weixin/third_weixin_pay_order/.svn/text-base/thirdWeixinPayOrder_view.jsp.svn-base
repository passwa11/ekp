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
    if("${thirdWeixinPayOrderForm.fdBody}" != "") {
        window.document.title = "${thirdWeixinPayOrderForm.fdBody} - ${ lfn:message('third-weixin:table.thirdWeixinPayOrder') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">
    <kmss:auth requestURL="/third/weixin/third_weixin_pay_order/thirdWeixinPayOrder.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinPayOrder.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinPayOrder') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdAppId')}
                </td>
                <td width="35%">
                    <%-- 公众账号ID--%>
                    <div id="_xform_fdAppId" _xform_type="text">
                        <xform:text property="fdAppId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdMchId')}
                </td>
                <td width="35%">
                    <%-- 商户号--%>
                    <div id="_xform_fdMchId" _xform_type="text">
                        <xform:text property="fdMchId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr style="display:none;">
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdDeviceInfo')}
                </td>
                <td width="35%">
                    <%-- 设备号--%>
                    <div id="_xform_fdDeviceInfo" _xform_type="text">
                        <xform:text property="fdDeviceInfo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdNonceStr')}
                </td>
                <td width="35%">
                    <%-- 随机字符串--%>
                    <div id="_xform_fdNonceStr" _xform_type="text">
                        <xform:text property="fdNonceStr" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr style="display: none">
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdSign')}
                </td>
                <td width="35%">
                    <%-- 签名--%>
                    <div id="_xform_fdSign" _xform_type="text">
                        <xform:text property="fdSign" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdSignType')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdBody')}
                </td>
                <td width="35%">
                    <%-- 商品描述--%>
                    <div id="_xform_fdBody" _xform_type="text">
                        <xform:text property="fdBody" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2" width="50.0%">
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdDetail')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdAttach')}
                </td>
                <td width="35%">
                    <%-- 附加数据--%>
                    <div id="_xform_fdAttach" _xform_type="text">
                        <xform:text property="fdAttach" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdFeeType')}
                </td>
                <td width="35%">
                    <%-- 标价币种--%>
                    <div id="_xform_fdFeeType" _xform_type="text">
                        <xform:text property="fdFeeType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTotalFee')}
                </td>
                <td width="35%">
                    <%-- 标价金额--%>
                    <div id="_xform_fdTotalFee" _xform_type="text">
                        <xform:text property="fdTotalFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdSpbillCreateIp')}
                </td>
                <td width="35%">
                    <%-- 终端IP--%>
                    <div id="_xform_fdSpbillCreateIp" _xform_type="text">
                        <xform:text property="fdSpbillCreateIp" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTimeStart')}
                </td>
                <td width="35%">
                    <%-- 交易起始时间--%>
                    <div id="_xform_fdTimeStart" _xform_type="text">
                        <xform:text property="fdTimeStart" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTimeExpire')}
                </td>
                <td width="35%">
                    <%-- 交易结束时间--%>
                    <div id="_xform_fdTimeExpire" _xform_type="text">
                        <xform:text property="fdTimeExpire" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdGoodsTag')}
                </td>
                <td width="35%">
                    <%-- 订单优惠标记--%>
                    <div id="_xform_fdGoodsTag" _xform_type="text">
                        <xform:text property="fdGoodsTag" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTradeType')}
                </td>
                <td width="35%">
                    <%-- 交易类型--%>
                    <div id="_xform_fdTradeType" _xform_type="select">
                        <xform:select property="fdTradeType" htmlElementProperties="id='fdTradeType'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_weixin_trade_type" />
                        </xform:select>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdProductId')}
                </td>
                <td width="35%">
                    <%-- 商品ID--%>
                    <div id="_xform_fdProductId" _xform_type="text">
                        <xform:text property="fdProductId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdLimitPay')}
                </td>
                <td width="35%">
                    <%-- 指定支付方式--%>
                    <div id="_xform_fdLimitPay" _xform_type="text">
                        <xform:text property="fdLimitPay" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdOpenid')}
                </td>
                <td width="35%">
                    <%-- 用户标识--%>
                    <div id="_xform_fdOpenid" _xform_type="text">
                        <xform:text property="fdOpenid" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdProfitSharing')}
                </td>
                <td width="35%">
                    <%-- 电子发票入口开放标识--%>
                    <div id="_xform_fdProfitSharing" _xform_type="text">
                        <xform:text property="fdProfitSharing" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdSceneInfo')}
                </td>
                <td width="35%">
                    <%-- 场景信息--%>
                    <div id="_xform_fdSceneInfo" _xform_type="text">
                        <xform:text property="fdSceneInfo" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTradeTypeReturn')}
                </td>
                <td width="35%">
                    <%-- 交易类型-响应--%>
                    <div id="_xform_fdTradeTypeReturn" _xform_type="select">
                        <xform:select property="fdTradeTypeReturn" htmlElementProperties="id='fdTradeTypeReturn'" showStatus="view">
                            <xform:enumsDataSource enumsType="third_weixin_trade_type" />
                        </xform:select>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdPrepayId')}
                </td>
                <td width="35%">
                    <%-- 预支付交易会话标识--%>
                    <div id="_xform_fdPrepayId" _xform_type="text">
                        <xform:text property="fdPrepayId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCodeUrl')}
                </td>
                <td width="35%">
                    <%-- 二维码链接--%>
                    <div id="_xform_fdCodeUrl" _xform_type="text">
                        <xform:text property="fdCodeUrl" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdIsSubscribe')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTradeState')}
                </td>
                <td width="35%">
                    <%-- 交易状态--%>
                    <div id="_xform_fdTradeState" _xform_type="text">
                        <xform:text property="fdTradeState" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdBankType')}
                </td>
                <td width="35%">
                    <%-- 付款银行--%>
                    <div id="_xform_fdBankType" _xform_type="text">
                        <xform:text property="fdBankType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTotalFeeReturn')}
                </td>
                <td width="35%">
                    <%-- 标价金额-响应--%>
                    <div id="_xform_fdTotalFeeReturn" _xform_type="text">
                        <xform:text property="fdTotalFeeReturn" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdSettlementTotalFee')}
                </td>
                <td width="35%">
                    <%-- 应结订单金额--%>
                    <div id="_xform_fdSettlementTotalFee" _xform_type="text">
                        <xform:text property="fdSettlementTotalFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdFeeTypeReturn')}
                </td>
                <td width="35%">
                    <%-- 标价币种-响应--%>
                    <div id="_xform_fdFeeTypeReturn" _xform_type="text">
                        <xform:text property="fdFeeTypeReturn" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCashFee')}
                </td>
                <td width="35%">
                    <%-- 现金支付金额--%>
                    <div id="_xform_fdCashFee" _xform_type="text">
                        <xform:text property="fdCashFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCashFeeType')}
                </td>
                <td width="35%">
                    <%-- 现金支付币种--%>
                    <div id="_xform_fdCashFeeType" _xform_type="text">
                        <xform:text property="fdCashFeeType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCouponFee')}
                </td>
                <td width="35%">
                    <%-- 代金券金额--%>
                    <div id="_xform_fdCouponFee" _xform_type="text">
                        <xform:text property="fdCouponFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCouponCount')}
                </td>
                <td width="35%">
                    <%-- 代金券使用数量--%>
                    <div id="_xform_fdCouponCount" _xform_type="text">
                        <xform:text property="fdCouponCount" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCouponType')}
                </td>
                <td width="35%">
                    <%-- 代金券类型--%>
                    <div id="_xform_fdCouponType" _xform_type="text">
                        <xform:text property="fdCouponType" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCouponId')}
                </td>
                <td width="35%">
                    <%-- 代金券ID--%>
                    <div id="_xform_fdCouponId" _xform_type="text">
                        <xform:text property="fdCouponId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdCouponFeeOne')}
                </td>
                <td width="35%">
                    <%-- 单个代金券支付金额--%>
                    <div id="_xform_fdCouponFeeOne" _xform_type="text">
                        <xform:text property="fdCouponFeeOne" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTransactionId')}
                </td>
                <td width="35%">
                    <%-- 微信支付订单号--%>
                    <div id="_xform_fdTransactionId" _xform_type="text">
                        <xform:text property="fdTransactionId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTimeEnd')}
                </td>
                <td width="35%">
                    <%-- 支付完成时间--%>
                    <div id="_xform_fdTimeEnd" _xform_type="text">
                        <xform:text property="fdTimeEnd" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2" width="50.0%">
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdTradeStateDesc')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 交易状态描述--%>
                    <div id="_xform_fdTradeStateDesc" _xform_type="rtf">
                        <xform:rtf property="fdTradeStateDesc" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.docAlterTime')}
                </td>
                <td width="35%">
                    <%-- 更新时间--%>
                    <div id="_xform_docAlterTime" _xform_type="datetime">
                        <xform:datetime property="docAlterTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.docCreator')}
                </td>
                <td width="35%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${thirdWeixinPayOrderForm.docCreatorId}" personName="${thirdWeixinPayOrderForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdModelName')}
                </td>
                <td width="35%">
                    <%-- 主文档类名--%>
                    <div id="_xform_fdModelName" _xform_type="text">
                        <xform:text property="fdModelName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdModelId')}
                </td>
                <td width="35%">
                    <%-- 主文档ID--%>
                    <div id="_xform_fdModelId" _xform_type="text">
                        <xform:text property="fdModelId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayOrder.fdKey')}
                </td>
                <td width="35%">
                    <%-- 主文档关键字--%>
                    <div id="_xform_fdKey" _xform_type="text">
                        <xform:text property="fdKey" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinPayOrderForm.fdId}" />
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
        basePath: '/third/weixin/third_weixin_pay_order/thirdWeixinPayOrder.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>