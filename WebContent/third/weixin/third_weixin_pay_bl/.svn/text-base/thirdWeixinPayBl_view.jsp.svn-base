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
    if("${thirdWeixinPayBlForm.fdBody}" != "") {
        window.document.title = "${thirdWeixinPayBlForm.fdBody} - ${ lfn:message('third-weixin:table.thirdWeixinPayBl') }";
    }
    Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
</script>
<div id="optBarDiv">

    <kmss:auth requestURL="/third/weixin/third_weixin_pay_bl/thirdWeixinPayBl.do?method=edit&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.edit')}" onclick="Com_OpenWindow('thirdWeixinPayBl.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
    </kmss:auth>
    <kmss:auth requestURL="/third/weixin/third_weixin_pay_bl/thirdWeixinPayBl.do?method=delete&fdId=${param.fdId}">
        <input type="button" value="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('thirdWeixinPayBl.do?method=delete&fdId=${param.fdId}','_self');" />
    </kmss:auth>
    <input type="button" value="${lfn:message('button.close')}" onclick="Com_CloseWindow();" />
</div>
<p class="txttitle">${ lfn:message('third-weixin:table.thirdWeixinPayBl') }</p>
<center>

    <div style="width:95%;">
        <table class="tb_normal" width="100%">
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.docCreateTime')}
                </td>
                <td width="35%">
                    <%-- 创建时间--%>
                    <div id="_xform_docCreateTime" _xform_type="datetime">
                        <xform:datetime property="docCreateTime" showStatus="view" dateTimeType="datetime" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdBody')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdDetail')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdAttach')}
                </td>
                <td width="35%">
                    <%-- 附加数据--%>
                    <div id="_xform_fdAttach" _xform_type="text">
                        <xform:text property="fdAttach" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdFeeType')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdTotalFee')}
                </td>
                <td width="35%">
                    <%-- 标价金额--%>
                    <div id="_xform_fdTotalFee" _xform_type="text">
                        <xform:text property="fdTotalFee" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdTimeStart')}
                </td>
                <td width="35%">
                    <%-- 交易起始时间--%>
                    <div id="_xform_fdTimeStart" _xform_type="text">
                        <xform:text property="fdTimeStart" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdTimeExpire')}
                </td>
                <td width="35%">
                    <%-- 交易结束时间--%>
                    <div id="_xform_fdTimeExpire" _xform_type="text">
                        <xform:text property="fdTimeExpire" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdGoodsTag')}
                </td>
                <td width="35%">
                    <%-- 订单优惠标记--%>
                    <div id="_xform_fdGoodsTag" _xform_type="text">
                        <xform:text property="fdGoodsTag" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdTradeType')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdProductId')}
                </td>
                <td width="35%">
                    <%-- 商品ID--%>
                    <div id="_xform_fdProductId" _xform_type="text">
                        <xform:text property="fdProductId" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdLimitPay')}
                </td>
                <td width="35%">
                    <%-- 指定支付方式--%>
                    <div id="_xform_fdLimitPay" _xform_type="text">
                        <xform:text property="fdLimitPay" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdTransactionId')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdModelName')}
                </td>
                <td width="35%">
                    <%-- 主文档类名--%>
                    <div id="_xform_fdModelName" _xform_type="text">
                        <xform:text property="fdModelName" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdModelId')}
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
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdKey')}
                </td>
                <td width="35%">
                    <%-- 主文档关键字--%>
                    <div id="_xform_fdKey" _xform_type="text">
                        <xform:text property="fdKey" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2" width="50.0%">
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdOtherData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 其它信息--%>
                    <div id="_xform_fdOtherData" _xform_type="rtf">
                        <xform:rtf property="fdOtherData" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdReturnData')}
                </td>
                <td colspan="3" width="85.0%">
                    <%-- 响应信息--%>
                    <div id="_xform_fdReturnData" _xform_type="rtf">
                        <xform:rtf property="fdReturnData" showStatus="view" width="95%" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.docCreator')}
                </td>
                <td width="35%">
                    <%-- 创建人--%>
                    <div id="_xform_docCreatorId" _xform_type="address">
                        <ui:person personId="${thirdWeixinPayBlForm.docCreatorId}" personName="${thirdWeixinPayBlForm.docCreatorName}" />
                    </div>
                </td>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdPayPerson')}
                </td>
                <td width="35%">
                    <%-- 支付人--%>
                    <div id="_xform_fdPayPersonId" _xform_type="address">
                        <xform:address propertyId="fdPayPersonId" propertyName="fdPayPersonName" orgType="ORG_TYPE_PERSON" showStatus="view" style="width:95%;" />
                    </div>
                </td>
            </tr>
            <tr>
                <td class="td_normal_title" width="15%">
                    ${lfn:message('third-weixin:thirdWeixinPayBl.fdAppid')}
                </td>
                <td width="35%">
                    <%-- 小程序ID--%>
                    <div id="_xform_fdAppid" _xform_type="text">
                        <xform:text property="fdAppid" showStatus="view" style="width:95%;" />
                    </div>
                </td>
                <td colspan="2">
                </td>
            </tr>
        </table>
    </div>
    <input type="hidden" name="fdId" value="${thirdWeixinPayBlForm.fdId}" />
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
        basePath: '/third/weixin/third_weixin_pay_bl/thirdWeixinPayBl.do',
        customOpts: {

            ____fork__: 0
        }
    };
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>