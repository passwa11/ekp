<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<title>
    ${ lfn:message('fssc-pres:portlet.doc.view.flat') }
</title>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/fssc/common/resource/css/custom.css">
</head>
<style>
    #approveByMonth{
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
            <span class="lui_fssc_financial_card_title">${lfn:message('fssc-cashier:fssCashierApprove.title')}</span>
            <span class="lui_fssc_financial_card_detail">
                                <div class="lui_fssc_financial_card_detail_wrapper">
                                    <i class="lui_fssc_financial_card_detail_icon"></i>
                                    <div class="lui_fssc_financial_card_tips">
                                    <span>${lfn:message('fssc-cashier:fssCashierApprove.tip')}</span>
                                </div>
                                </div>
                            </span>
            </div>
            <div class="lui_fssc_financial_card_content">
                <span id="totalApprove"></span>
            </div>
            <div class="lui_fssc_financial_card_items_process" id="cashier_up_down">
                <span id="yearon">${lfn:message('fssc-cashier:fssCashierApprove.comparison')}<i id="approveAvg"></i></span>
            </div>
            <div class="lui_fssc_financial_card_pending">
                <span><a id="approveByMonth">${lfn:message('fssc-cashier:fssCashierApprove.throughput')}</a></span>
            </div>
        </div>
    </div>
</div>
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>
<script type="text/javascript">
    $(function(){
        $.ajax({
            url : "${LUI_ContextPath}/fssc/cashier/fssc_cashier_portlet/fsscCashierPortle.do?method=getCapprovePortletData",
            type:'POST',
            async:false,
            dataType:'json',
            success: function(json) {
                if(json.AvgType === 'reduction'){
                    $("#cashier_up_down").addClass("process_down");
                    $("#approveAvg").after(json.cApproveAvg);
                }else if(json.AvgType === 'increase'){
                    $("#cashier_up_down").addClass("process_up");
                    $("#approveAvg").after(json.cApproveAvg);
                }else{
                    $("#approveAvg").after('&nbsp;'+json.cApproveAvg);
                }
                $("#totalApprove").append(json.cApprove);
                $("#approveByMonth").after(json.cApproveByMonth);
            }
        });
    })
</script>