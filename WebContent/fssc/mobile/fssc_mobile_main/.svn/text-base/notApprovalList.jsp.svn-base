<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%
    session.setAttribute("S_PADFlag","1");
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>待我审列表</title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/applicationFormList.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }" />
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }" >
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/dropload.min.js"></script>
</head>
<body>
<div class="ld-tab-list">
    <div class="ld-tab-list-tab">
        <div onclick="listApproval();">待我审的</div>
        <div onclick="listApproved();">我已审的</div>
        <div onclick="listAll();">全部文档</div>
    </div>
    <div class="ld-tab-list-main">
        <div class="ld-tab-list-swiper">
            <div>
                <div class="swiper-slide noSwiping">
                    <ul id="list_data">
                        <c:forEach items="${array}" var="list">
                            <li class="ld-tab-list-main-list" onclick="viewFlow('${list.fdId}','${list.fdKey}','${list.url}','${list.templateId}')">
                                <div style="border:none;">
                                    <div style="width:60%;word-break: break-all;">
                                        <c:if test="${fn:length(list.docSubject)>40 }">${fn:substring(list.docSubject,0,37)}...</c:if>
                                        <c:if test="${fn:length(list.docSubject)<=40 }">${list.docSubject}</c:if>

                                    </div>
                                    <div style="width:40%;text-align:right;">
                                            ${list.fdNo}
                                    </div>
                                </div>
                                <div>
                                    <div style="width:40%;float:left;">${list.createTime}</div>
                                    <div style="width:40%;text-align:right;float:right;">
                                        <span class="ld-list-status ld-list-status-${list.clazz}">${list.status}</span>
                                    </div>
                                </div>
                            </li>
                            <div class="ld-line20px"></div>
                        </c:forEach>
                        <input name="pageno" value="${queryPage.pageno}" type="hidden"/>
                        <input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
                        <input name="totalrows" value="${queryPage.totalrows}" type="hidden"/>
                        <input name="type" value="${type}" type="hidden"/>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- 加载中 -->
    <div class="ld-main" id="ld-main-loading" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.loading')}</span>
            </div>
        </div>
    </div>
</div>
<div class="backHome oneBtn" onclick="backToHome()"></div>
</body>

<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/swiper.min.js"></script>
<script>
    //待我审的
    function listApproval(){
        window.open(Com_Parameter.ContextPath+ "fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getNewApprovalList", '_self');
    }
    //我已审的
    function listApproved(){
        window.open(Com_Parameter.ContextPath+ "fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getApprovedList", '_self');
    }
    //全部文档
    function listAll(){
        window.open(Com_Parameter.ContextPath+ "fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getAllList", '_self');
    }
    //#content为某个div的id
    var isContinue=false;  //是否继续加载，默认为false，不继续加载
    var dropload = $('.ld-tab-list').dropload({
        //scrollArea很关键，要不然加载更多不起作用
        scrollArea : window,
        distance:100,
        domUp : {
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh">↓${lfn:message("fssc-mobile:fssc.mobile.list.down.reflesh")}</div>',
            domUpdate  : '<div class="dropload-update">↑${lfn:message("fssc-mobile:fssc.mobile.list.up.reflesh")}</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>${lfn:message("fssc-mobile:fssc.mobile.list.loading")}</div>'
        },
        domDown : {
            domClass   : 'dropload-down',
            domRefresh : '<div class="dropload-refresh">↑${lfn:message("fssc-mobile:fssc.mobile.list.load.more")}</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>${lfn:message("fssc-mobile:fssc.mobile.list.loading")}</div>',
            domNoData  : '<div class="dropload-noData">${lfn:message("fssc-mobile:fssc.mobile.list.no.data")}</div>'
        },
        loadUpFn : function(me){
            //下拉刷新需要调用的函数
            window.location.reload(true);			//重置下拉刷新
            me.resetload();
        },
        loadDownFn : function(me){
            var totalrows=$("[name='totalrows']").val();
            var rowsize=$("[name='rowsize']").val();
            var pageno=$("[name='pageno']").val();
            if((totalrows-rowsize*pageno)>0){
                isContinue=true;
                $(".dropload-down").show();
            }else{
                isContinue=false;  //不需要再加载
                $(".dropload-down").hide();
            }
            if(isContinue){
                var type=$("[name='type']").val();
                if(!pageno){
                    pageno=1;
                }
                pageno=pageno*1+1;
                $("#ld-main-loading").attr("style","display: block;");
                //上拉加载更多需要调用的函数
                $.ajax({
                    url: '${LUI_ContextPath}/fssc/mobile/fssc_mobile_link/fsscMobileLink.do?method=getMoreData&pageno='+pageno+'&rowsize=${queryPage.rowsize}&type='+type,
                    type: 'post',
                    error: function(data){
                        console.log("error"+data);
                    },
                    success: function(data){
                        //移除前一次查询的参数
                        $("[name='pageno']").remove();
                        $("[name='rowsize']").remove();
                        $("#list_data").append($(data));
                        $("#ld-main-loading").attr("style","display: none;");
                    }
                });
                $(".dropload-down").remove();
                setTimeout(function(){			            // 每次数据加载完，必须重置
                    me.resetload();
                },1000);
            }
        }
    });
</script>
<script>
    function backToHome(){
        window.location.href='${LUI_ContextPath}/fssc/mobile/index.jsp'
    }
    var ldTabListSwiper=new Swiper('.ld-tab-list-swiper',{
        noSwiping :true,
        autoHeight: true,
        noSwipingClass : 'noSwiping'
    })
    $(".ld-tab-list-tab div").on('touchstart mousedown', function (e) {
        e.preventDefault()
        $(".ld-tab-list-tab .active").removeClass('active')
        $(this).addClass('active')
        ldTabListSwiper.slideTo($(this).index())
        $(".ld-tab-list-tab .active").click();
    });
    function viewFlow(fdId, fdKey, url, templateId) {
        if (fdKey == "fsscExpenseMain") {   //报销
            window.location.href = Com_Parameter.ContextPath + 'fssc/expense/fssc_expense_mobile/fsscExpenseMobile.do?method=view&fdId=' +fdId;
        } else if (fdKey == "fsscFeeMain") {    //事前申请
            window.location.href = Com_Parameter.ContextPath + 'fssc/fee/fssc_fee_mobile/fsscFeeMobile.do?method=view&fdId=' +fdId+"&i.docTemplate=" +templateId;
        } else if (fdKey == "fsscLoanMain") {   //借款
            window.location.href = Com_Parameter.ContextPath + 'fssc/loan/fssc_loan_mobile/fsscLoanMobile.do?method=view&fdId=' +fdId;
        } else if (fdKey == "fsscLoanRepayment") {  //还款
            window.location.href = Com_Parameter.ContextPath + 'fssc/loan/fssc_loan_repay_mobile/fsscLoanRepayMobile.do?method=view&fdId=' +fdId;
        } else {
            window.location.href = Com_Parameter.ContextPath + url;
        }
    }
</script>