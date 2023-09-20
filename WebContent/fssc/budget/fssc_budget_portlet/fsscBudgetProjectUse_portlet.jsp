<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<head>
    <script type="text/javascript" src="${LUI_ContextPath}/fssc/common/resource/js/Number.js?s_cache=${LUI_Cache}"></script>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/common/resource/css/custom.css">
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div class="lui_fssc_iframe"  style="width:100%;">
    <div class="lui_fssc_financial_card_portlet">
        <div class="lui_fssc_credit">
            <div class="lui_fssc_credit_header">
                <span>${lfn:message("fssc-budget:fssc.budget.portlet.project.use")}</span>
            </div>
            <div class="lui_fssc_credit_detail">
                <div class="lui_fssc_credit_detail_left" style="width:100%;">
                    <table class="lui_fssc_credit_detail_talbe">
                        <tbody>
                        <tr>
                            <th style="text-align:center;"><span>${lfn:message("page.serial")}</span></th>
                            <th><span>${lfn:message("fssc-budget:fsscBudgetDetail.fdProject.fdName")}</span></th>
                            <th><span>${lfn:message("fssc-budget:fssc.budget.project.used.percent")}</span></th>
                        </tr>
                        </tbody>
                    </table>
                    <div id="noDataTips" style="line-height:30px; height:30px;text-align: center;display:none;">${lfn:message("return.noRecord.reason2")}</div>
                </div>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        getProjectAcountInfo();
    });
    function getProjectAcountInfo(){
        var fdCostCenterId=$("#budget_project_cost_center").val()||'';
        $.ajax({
            url:'${LUI_ContextPath}/fssc/budget/fssc_budget_portlet/fsscBudgetPortlet.do?method=getProjectAcountInfo',
            async:false,
            success:function(rtn){
                rtn = JSON.parse(rtn);
                var projectArrInfo=rtn.projectArr;
                if(projectArrInfo&&projectArrInfo.length>0){
                    //debugger;
                    $("#noDataTips").hide();
                    $(".lui_fssc_credit_detail_talbe:not(.credit_use)").show();
                    $(".lui_fssc_credit_detail_talbe:not(.credit_use)  tr:gt(0) ").remove();
                    var trHtml="";
                    for(var i=0;i<projectArrInfo.length;i++){
                        var projectname=projectArrInfo[i][0];
                        if(projectname&&projectname.length>15){
                            projectname=projectname.substring(0,15)+"...";
                        }
                        trHtml+='<tr><td style="text-align:center;"><span>'+(i+1)+'</span> </td><td><span title="'+projectArrInfo[i][0]+'">'+projectname+'</span> </td><td><span class="lui_fssc_progress_wrapper">';
                        var percent=0;
                        if(projectArrInfo[i][1]>0){  //总额不为0
                            percent=projectArrInfo[i][2]/projectArrInfo[i][1]*100;
                        }
                        if(percent<=60){ //0-40蓝色
                            trHtml+='<span class="lui_fssc_progress_inner lui_progress_primary" style="width:'+formatFloat(percent,2)+'%;">';
                        }else if(percent>60&&percent<=80){//40-60绿色
                            trHtml+='<span class="lui_fssc_progress_inner lui_progress_passing" style="width:'+formatFloat(percent,2)+'%;">';
                        }else{
                            trHtml+='<span class="lui_fssc_progress_inner lui_progress_warning" style="width:'+formatFloat(percent,2)+'%;">';
                        }
                        trHtml+='</span></span><i class="lui_fssc_progress_num">'+formatFloat(percent,2)+'%</i></td>';
                    }
                    $(".lui_fssc_credit_detail_talbe:not(.credit_use)").append(trHtml);
                }else{
                    $("#noDataTips").show();
                    $(".lui_fssc_credit_detail_talbe:not(.credit_use)").hide();
                }
            }
        });
    }
</script>
</body>