<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<title>
    ${ lfn:message('fssc-pres:portlet.doc.view.flat') }
</title>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/fssc/common/resource/css/custom.css">
</head>
<style>
    #totalUnpaiMoneyAvg{
        width: 96px;
        height: 12px;
        font-family: PingFangSC-Regular;
        font-size: 12px;
        color: rgba(0,0,0,0.65);
        line-height: 12px;
        margin-right:10px;
        font-weight: 400;
    }
    #yearon{
        width: 36px;
        height: 12px;
        font-family: PingFangSC-Regular;
        font-size: 12px;
        color: rgba(0,0,0,0.65);
        line-height: 12px;
        font-weight: 400;
    }
</style>
<div class="lui_fssc_financial_card_portlet_container">
    <div class="lui_fssc_financial_card">
        <div class="lui_fssc_financial_card_items_header">
            <i class="lui_fssc_financial_card_icons financial_primary">
                <span class="lui_fssc_financial_card_icon"></span>
            </i>
            <span class="lui_fssc_financial_card_title">${lfn:message('fssc-cashier:portlet.doc.view.flat')}</span>
            <span class="lui_fssc_financial_card_detail">
                                <div class="lui_fssc_financial_card_detail_wrapper">
                                    <i class="lui_fssc_financial_card_detail_icon"></i>
                                    <div class="lui_fssc_financial_card_tips">
                                        <span>${lfn:message('fssc-cashier:portlet.doc.view.tip')}</span>
                                    </div>
                                </div>
                            </span>
        </div>
        <div class="lui_fssc_financial_card_content">
            <span id="totalMonye"></span>
        </div>
        <div class="lui_fssc_financial_card_items_process " id="payment_up_down">
            <span id="yearon">&nbsp;&nbsp;<i id="theYearOnYearGrowthRate">&nbsp;&nbsp;&nbsp;</i></span>
        </div>
        <div class="lui_fssc_financial_card_pending">
            <span><a id="totalUnpaiMoneyAvg">${lfn:message('fssc-cashier:portlet.doc.view.avgDay')}</a></span>
        </div>
    </div>
</div>
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
<script type="text/javascript">
    $(function(){
        $.ajax({
            url : "${LUI_ContextPath}/fssc/cashier/fssc_cashier_portlet/fsscCashierPortle.do?method=getCashierPortletData",
            type:'POST',
            async:false,
            dataType:'json',
            success: function(json) {

                $("#totalMonye").append("￥"+json.totalUnpaidMoney);
                $("#totalUnpaiMoneyAvg").after("￥"+json.totalUnpaiMoneyAvg);
            }
        });
    })
</script>