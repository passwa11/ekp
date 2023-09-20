<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<head>
	 <style>
        /* 新增发票模态框 */
         .ld-invoice-modal{
		        height:100%;
		        width:100%;
		        position: fixed;
		        top:0;
		        left:0;
		        background: rgba(0,0,0,.5);
		        z-index:999;
	        }
	        .ld-invoice-modal-invoice{
		        position:absolute;
		        top:50%;
		        transform:translateY(-50%);
		        width:100%;
		        padding:0 0.3rem;
		        box-sizing: border-box;
	        }
	        .ld-invoice-modal-invoice  img {
	           width:100%;
	           object-fit: contain;
	        }
	        .ld-invoice-modal i{
	            height: 0.6rem;
	            width:0.6rem;
	            position: absolute;
	            right:0.3rem;
	            top:0.72rem;
	            background:url('${LUI_ContextPath}/fssc/mobile/resource/images/close.png') no-repeat center center;
	            background-size: contain;
	        }
    </style>
</head>
<body style="overflow-scrolling:touch;overflow:auto;width:100%;height:100%;">
    <div class="ld-remember-invoiceInfo">
         <div class="ld-remember-invoiceInfo-title">
             <h3>${lfn:message('fssc-mobile:table.invoices')}</h3>
             <i></i>
         </div>
         <ul id ="invoiceListId">
               <c:forEach var="invoice" items="${invoices }" varStatus="vstatus">
             		<li  onclick="editInvoice(this)">
	                 <div class="ld-remember-invoiceInfo-top">
	                     <div>
	                         <img src="../resource/images/specialTicket.png" alt="">
	                         <span class="fdInvoiceType">${invoice.fdInvoiceType}</span>
	                         <span class="ld-remember-invoiceInfo-top-satuts ld-remember-invoiceInfo-top-satuts-${empty invoice.fdCheckStatus?'0':invoice.fdCheckStatus }">
	                         	<c:choose>
	                         		<c:when test="${empty invoice.fdCheckStatus}">
	                         			${lfn:message('fssc-mobile:invoice.satuts.0')}
	                         		</c:when>
	                         		<c:otherwise>
	                         			<sunbor:enumsShow enumsType="fssc_mobile_invoice_status" value="${invoice.fdCheckStatus}"></sunbor:enumsShow>
	                         		</c:otherwise>
	                         	</c:choose>
	                         </span>
							 <%--发票抬头校验是否通过--%>
							 <c:if test="${not empty invoice.fdIsCurrent}">
							 <span id="isCurrent${vstatus.index}" class="ld-remember-invoiceInfo-top-satuts ld-remember-invoiceInfo-top-satuts-${invoice.fdIsCurrent}" style="margin-left:0.2rem;font-size:0.2rem;width:1rem;">
								 ${lfn:message('fssc-mobile:invoice.is.current')}
							 </span>
							 </c:if>
							 <c:if test="${empty invoice.fdIsCurrent}">
							 <span id="isCurrent${vstatus.index}" style="margin-left:0.2rem;font-size:0.2rem;">

							 </span>
							 </c:if>
	                     </div>
	                     <i onclick="deleteInvoice()"></i>
	                 </div>
	                 <div class="ld-remember-invoiceInfo-bottom">
	                 	<input name='row-invoice-index${vstatus.index }' value="${vstatus.index }" type="hidden">
	                    <span class="fdInvoiceNumber">${invoice.fdInvoiceNumber}</span>
	                    <span class="fdTotalAmount">
	                    	<c:if test="${not empty invoice.fdJshj }">
	                    		<kmss:showNumber value="${invoice.fdJshj}" pattern="0.00"/>
	                    	</c:if>
	                    </span>
	                 </div>
	                 <div  style="display: none;" class="invoice_detail">
	                      <input name='fdInvoiceList_form[${vstatus.index }].fdTotalAmount' validate="required" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdTotal')}"  value="<kmss:showNumber value="${invoice.fdJshj}" pattern="0.00"/>">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdInvoiceCode' value="${invoice.fdInvoiceCode}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdInvoiceNumber' validate="required" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceNumber')}" value="${invoice.fdInvoiceNumber}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdCheckCode' value="${invoice.fdCheckCode}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdInvoiceDate' value="${invoice.fdInvoiceDate}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdPurchaserName' value="${invoice.fdPurchaserName}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdPurchaserTaxNo' value="${invoice.fdPurchaserTaxNo}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdSalesName' value="${invoice.fdSalesName}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdTotalTax' value="<kmss:showNumber value="${invoice.fdTotalTax}" pattern="0.00"/>">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdAttId' value="${invoice.fdAttId}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdId' value="${invoice.fdId}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdInvoiceTypeId' value="${invoice.fdInvoiceTypeId}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdInvoiceType' validate="required" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceType')}" value="${invoice.fdInvoiceType}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdCheckStatus' value="${invoice.fdCheckStatus}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdState' value="${invoice.fdState}">
						  <input name='fdInvoiceList_form[${vstatus.index }].fdIsCurrent' value="${invoice.fdIsCurrent}">
	                 </div>
	             </li>
             	</c:forEach>
         </ul>
         <div class="ld-remember-invoiceInfo-btn" onclick="addInvoiceByHand()">
             <i></i><span>${lfn:message('fssc-mobile:fsscMobileNote.addDetail')}</span>
         </div>
     </div>

   	<div class="ld-invoice-detail-body">
        <div class="ld-invoice-detail">
        	<div class="ld-invoice-img" onclick="viewMoreInfo(this);">
	           <img id="detail_img" src="${LUI_ContextPath}" alt="">
	       </div>
            <div class="ld-invoice-detail-info" style="width:100%;overflow:auto;">
               <div class="ld-invoice-detail-info-main" style="width:100%;">
                <div class="ld-invoice-detail-info-top">
                  	<div class="ld-remember-valBox" style="height:50px;">
                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceType')}</span>
                    	<input type="text" name="fdInvoiceType" id="fdInvoiceType" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceType')}" style="position:relative;font-size:14px;" value="" onclick="selectInvoiceTypeItem()" readonly="readonly">
                    	<span style="color:#d02300;">*</span>
                    	<i class="ld-remember-arrow"></i>
                    	<input type="text" name="fdInvoiceTypeId" id="fdInvoiceTypeId" hidden="true">
                	</div>
                    <div style="height:50px;">
                    	<input type="hidden" name="row-invoice-index" value="0"/>
                    	<span>${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceCode')}</span>
                    	<input type="text" name="fdInvoiceCode" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceCode')}" style="font-size:14px;" value="">
						<span style="color:#d02300;display:none;" class="isVat">*</span>
					</div>
	                <div style="height:50px;position:relative;">
	                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceNumber')}</span>                   
	                    	<input type="text"  name="fdInvoiceNumber" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceNumber')}" style="font-size:14px;" value="">
	                    	<span style="color:#d02300;position:absolute;right:15px;top:15px;">*</span>
	                </div>
	                <div style="height:50px;">
	                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdCheckCode')}</span>
	                    <input type="text"  name="fdCheckCode" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdCheckCode')}" style="font-size:14px;"  value="">
	                </div>
	                <div class="ld-remember-valBox" style="height:50px;">
	                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdInvoiceDate')}</span>
	                    <input type="text" id="fdInvoiceDate" name="fdInvoiceDate" style="font-size:14px;" readonly="readonly" onclick="selectTime('fdInvoiceDate','fdInvoiceDate');"   value="" >
	                    <span style="color:#d02300;">*</span>
	                    <i class="ld-remember-arrow" style="height:9px;"></i>
	                </div>
                </div>
                
                <div class="ld-invoice-detail-info-bottom">
                <div style="height:50px;position:relative;">
                        <span>${lfn:message('fssc-mobile:fsscMobileNote.fdTotal')}</span>
                    	<input type="text" name="fdJshj" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdTotal')}" style="font-size:14px;"  value="">
                    	<span style="color:#d02300;position:absolute;right:15px;top:15px;">*</span>
                </div>
                <div style="height:50px;">
                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdTax')}</span>
                    <input type="text"  name="fdTotalTax" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdTax')}" style="font-size:14px;"  value="">
					<span style="color:#d02300;display:none;" class="isVat">*</span>
                </div>
                <div style="height:50px;">
                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdPurchName')}</span>
                    <input type="text"   name="fdPurchaserName" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdPurchName')}" style="font-size:14px;"  value="">
                </div>
                <div style="height:50px;">
                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdPurchTaxNo')}</span>
                    <input type="text"  name="fdPurchaserTaxNo" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdPurchTaxNo')}" style="font-size:14px;" value="">
                </div>
                <div style="height:50px;">
                    <span>${lfn:message('fssc-mobile:fsscMobileNote.fdSaleTaxName')}</span>
                    <input type="text"  name="fdSalesName" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdSaleTaxName')}" style="font-size:14px;"  value="" >
                </div>
                </div>
                <input name="currentInvoiceIndex" value="" type="hidden"/>
                <input name="fdCheckStatus" id="fdCheckStatus" value="" type="hidden"/>
                <input name="fdState" value=""  id="fdState" type="hidden"/>
                <input name="fdIsCurrent" value=""  id="fdIsCurrent" type="hidden"/>
                <input name="editFlag" value="" type="hidden"/>
            </div>
        </div>
      	<div class="ld-footer ld-invoice-detail-footer">
      		<div class="ld-footer-whiteBg" onclick="backToMainView();">${lfn:message('button.cancel')}</div>
			<!-- 存在合力中税模块，且开启提交人验真，且是费控解决方案版 -->
			<fssc:checkVersion version="true">
			<kmss:ifModuleExist path="/fssc/baiwang">
				<c:if test="${fdCreatorCheck}">
					<div class="ld-footer-blueBg" onclick="checkInvoice();" style="margin-left:0.1rem;margin-right:0.1rem;">${lfn:message('fssc-mobile:button.checkInvoice')}</div>
				</c:if>
			</kmss:ifModuleExist>
			</fssc:checkVersion>
            <div class="ld-footer-blueBg" onclick="saveInvoice();">${lfn:message('button.save')}</div>
        </div>
      </div>
    </div>
    <div class="ld-invoice-modal" style="display: none;" onclick="closeImg();">
        <div class="ld-invoice-modal-invoice">
            <img id="more_info" src="" alt="">
        </div>
    </div>
    <!-- 处理中 -->   
     <div class="ld-main" id="ld-main-upload" style="display: none;">
        <div class="ld-mask" style="z-index:100;">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:py.processing')}</span>
            </div>
        </div>
    </div>
<script>
$(document).ready(function(){
	var outterHeight=document.documentElement.clientHeight-$(".ld-invoice-detail-footer").height();
	var innerheight=outterHeight+150;
	$(".ld-invoice-detail-info").css("height",outterHeight+"px");
	$(".ld-invoice-detail-info-main").css("height",innerheight+"px");
})
</script>
  </body>  
