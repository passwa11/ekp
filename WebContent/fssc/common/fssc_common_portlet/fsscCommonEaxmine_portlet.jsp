<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
    <script type="text/javascript" src="${LUI_ContextPath}/fssc/common/resource/js/Number.js?s_cache=${LUI_Cache}"></script>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="lui_fssc_financial_card_portlet_container">
    <div class="lui_fssc_financial_card">
        <div class="lui_fssc_financial_card_items_header">
            <i class="lui_fssc_financial_card_icons financial_primary">
                <span class="lui_fssc_financial_card_icon"></span>
            </i>
            <span class="lui_fssc_financial_card_title">${lfn:message('fssc-common:portlet.bill.examine')}</span>
            <span class="lui_fssc_financial_card_detail">
                                <div class="lui_fssc_financial_card_detail_wrapper">
                                    <i class="lui_fssc_financial_card_detail_icon">
                                    </i>
                                    <div class="lui_fssc_financial_card_tips">
                                        <span>${lfn:message('fssc-common:portlet.bill.examine')}</span>
                                    </div>
                                </div>

                            </span>
        </div>
        <div class="lui_fssc_financial_card_content">
            <span id="approveNum" style="margin-left:0.5rem;"></span>
        </div>
        <div id="yearOnyear" class="lui_fssc_financial_card_items_process">
            <span>${lfn:message('fssc-common:portlet.bill.examine.year.on.year')}<i></i><span id="percent"></span></span>
        </div>
        <div class="lui_fssc_financial_card_pending">
            ${lfn:message('fssc-common:portlet.bill.examine.current.month')}
            <span id="approvedCurrent" style="margin-left:0.5rem;"></span>
        </div>
    </div>
</div>
    <script type="text/javascript">
        $(document).ready(function(){
            $.ajax({
                url:'${LUI_ContextPath}/fssc/common/fssc_common_portlet/fsscCommonPortlet.do?method=getExamineQuantity',
                async:false,
                success:function(rtn){
                    rtn = JSON.parse(rtn);
                    $("#approveNum").eq(0).html(rtn["approve"]);
                    $("#approvedCurrent").eq(0).html(rtn["approvedCurrent"]);
                    if(rtn["approve"]-rtn["approvePre"]>0){  //本年未审批大于前一年的我未审批，同比增长
                        if(rtn["approvePre"]==0){//往年未审单据为0,增长100%
                            $("#percent").html("100");
                        }else{
                            $("#percent").html(divPoint((rtn["approve"]-rtn["approvePre"])*100,rtn["approvePre"]));
                        }
                        $("#yearOnyear").addClass("lui_fssc_financial_card_items_process process_up");
                    }else if(rtn["approve"]-rtn["approvePre"]<0){ //本月我已审小于前一年的我已审，同比减少
                        $("#yearOnyear").addClass("lui_fssc_financial_card_items_process process_down");
                        $("#percent").html(divPoint((rtn["approvePre"]-rtn["approve"])*100,rtn["approvePre"]));
                    }else{
                        $("#percent").html("持平");
                        $("#percent").attr("style","margin-left:0.3rem;");
                    }
                }
            });
        });
    </script>
</body>