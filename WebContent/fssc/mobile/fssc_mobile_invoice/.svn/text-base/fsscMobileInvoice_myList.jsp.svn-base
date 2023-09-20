<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${lfn:message('fssc-mobile:fsscMobileInvoice.my') }</title>
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/applicationFormList.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }" />
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }" >
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css">
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/rem.js"></script>
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/common.js"></script>
</head>
<body>
    <div class="ld-tab-list">
        <div class="ld-tab-list-tab">
            <div class="active">${lfn:message('fssc-mobile:fsscMobileInvoice.unuse') }</div>
            <div>${lfn:message('fssc-mobile:fsscMobileInvoice.used') }</div>
        </div>
        <div class="ld-tab-list-main">
            <div class="ld-tab-list-swiper">
                <div class="swiper-wrapper">
                    <div class="swiper-slide noSwiping">
                        <ul>
                         <c:forEach items="${unuseInoviceList}" var="list">
                            <li class="ld-tab-list-main-list" onclick="viewInvoiceInfo('${list.id}')">
                                <div style="border:none;">
                                    <div style="width:60%;word-break: break-all;">
				                    	${list.type }
				                    </div>
				                     <div style="width:40%;text-align:right;">
					                     <c:if test="${not empty list.money }">
					                     ￥<kmss:showNumber value="${list.money}" pattern="0.00"/>
					                     </c:if>
				                    </div>
                                </div>
                                <div>
                                    	<div style="width:40%;float:left;">${list.code}</div>
                                    	<div style="width:40%;text-align:right;float:right;">
				                      		${list.number }
				                      	</div>
                                </div>
                            </li>
                            <div class="ld-line20px"></div>
                            </c:forEach>
                        </ul>
                    </div>
                    <div class="swiper-slide noSwiping">
                        <ul>
                         <c:forEach items="${usedInoviceList}" var="list">
                            <li class="ld-tab-list-main-list" onclick="viewInvoiceInfo('${list.id}')">
                                <div style="border:none;">
                                    <div style="width:60%;word-break: break-all;">
				                    	${list.type }
				                    </div>
				                     <div style="width:40%;text-align:right;">
				                        <c:if test="${not empty list.money }">
					                     ￥<kmss:showNumber value="${list.money}" pattern="0.00"/>
					                     </c:if>
				                    </div>
                                </div>
                                <div>
                                    	<div style="width:40%;float:left;">${list.code}</div>
                                    	<div style="width:40%;text-align:right;float:right;">
				                      		${list.number }
				                      	</div>
                                </div>
                            </li>
                            <div class="ld-line20px"></div>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="backHome oneBtn" onclick="backToHome()"></div>
</body>

<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/swiper.min.js"></script>
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
    });
    function viewInvoiceInfo(id){
    		window.location.href= Com_Parameter.ContextPath+'fssc/mobile/fssc_mobile_invoice/fsscMobileInvoice.do?method=view&viewOnly=true&fdId='+id
    }
</script>
