<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/rememberOne.css?s_cache=${LUI_Cache }" >
    <link rel="stylesheet"href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
    <style type="text/css">
	   .currentCityType div{
	    line-height: 0.12rem;
	    width: 100%;
	    text-align: center;
		}
    </style>
    <script type="text/javascript">
		var currentUserId = "${KMSS_Parameter_CurrentUserId}";
	</script>
    <title>${lfn:message('fssc-mobile:fsscMobileNote.remember')}</title>
    <script>
    var message = {
    		"tips.invoice.same":"${lfn:message('fssc-mobile:tips.invoice.same')}",
    		"invoice.satuts.0":"${lfn:message('fssc-mobile:invoice.satuts.0')}",
    		"invoice.satuts.1":"${lfn:message('fssc-mobile:invoice.satuts.1')}",
    		"invoice.fdDeductible.0":"${lfn:message('fssc-mobile:invoice.fdDeductible.0')}",
    		"invoice.fdDeductible.1":"${lfn:message('fssc-mobile:invoice.fdDeductible.1')}",
    		"enums.invoice_type.10100":"${lfn:message('fssc-mobile:enums.invoice_type.10100')}",
    		"enums.invoice_type.10101":"${lfn:message('fssc-mobile:enums.invoice_type.10101')}",
    		"enums.invoice_type.10102":"${lfn:message('fssc-mobile:enums.invoice_type.10102')}",
    		"enums.invoice_type.10103":"${lfn:message('fssc-mobile:enums.invoice_type.10103')}",
    		"enums.invoice_type.10104":"${lfn:message('fssc-mobile:enums.invoice_type.10104')}",
    		"enums.invoice_type.10105":"${lfn:message('fssc-mobile:enums.invoice_type.10105')}",
    		"enums.invoice_type.10200":"${lfn:message('fssc-mobile:enums.invoice_type.10200')}",
    		"enums.invoice_type.10400":"${lfn:message('fssc-mobile:enums.invoice_type.10400')}",
    		"enums.invoice_type.10500":"${lfn:message('fssc-mobile:enums.invoice_type.10500')}",
    		"enums.invoice_type.10503":"${lfn:message('fssc-mobile:enums.invoice_type.10503')}",
    		"enums.invoice_type.10505":"${lfn:message('fssc-mobile:enums.invoice_type.10505')}",
    		"enums.invoice_type.10507":"${lfn:message('fssc-mobile:enums.invoice_type.10507')}",
    		"enums.invoice_type.10900":"${lfn:message('fssc-mobile:enums.invoice_type.10900')}",
    		"enums.invoice_type.00000":"${lfn:message('fssc-mobile:enums.invoice_type.00000')}",
    		"enums.invoice_type.20100":"${lfn:message('fssc-mobile:enums.invoice_type.20100')}",
    		"enums.invoice_type.20105":"${lfn:message('fssc-mobile:enums.invoice_type.20105')}",
            "enums.invoice_type.30100":"${lfn:message('fssc-mobile:enums.invoice_type.30100')}",
            "enums.invoice_type.30101":"${lfn:message('fssc-mobile:enums.invoice_type.30101')}",
    		"errors.required":"${lfn:message('errors.required')}",
        	"errors.dollar":"${lfn:message('errors.dollar')}",
    };
    </script>
    <script>
	Com_IncludeFile("bmap.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
    </script>
</head>
<html:form action="/fssc/mobile/fssc_mobile_note/fsscMobileNote.do" >
<template:include ref="default.edit" >
    <div class="ld-rememberOne">
        <div class="ld-rememberOne-main">
            <div class="ld-remember-specialInfo ld-remember-info-item">
                <div>
                    <h3 class="ld-remember-title">${lfn:message('fssc-mobile:py.JiBenXinXi')}</h3><b></b>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdCompany')}</span>
                    <div class="ld-remember-valBox" >
                        <input type="text" name ="fdCompanyName" style="font-size:14px;" validate="required" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdCompany')}" value="${ fsscMobileNoteForm.fdCompanyName}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-mobile:fsscMobileNote.fdCompany')}" onclick="selectObject('fdCompanyId','fdCompanyName','fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCompany','afterSelectCompany');" readonly="readonly">   
                        <xform:text property="fdCompanyId" showStatus="noShow"/>
                        <span style="margin-left:2px;color:#d02300;">*</span>
                        <i class="ld-remember-arrow"></i>
                    </div>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdCostCenter')}</span>
                    <div class="ld-remember-valBox" >
                        <input type="text" name ="fdCostCenterName" style="font-size:14px;" validate="required" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdCostCenter')}" value="${ fsscMobileNoteForm.fdCostCenterName}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-mobile:fsscMobileNote.fdCostCenter')}" onclick="selectObject('fdCostCenterId','fdCostCenterName','fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCostCenter');" readonly="readonly">   
                        <xform:text property="fdCostCenterId" showStatus="noShow" />
                        <span style="margin-left:2px;color:#d02300;">*</span>
                        <i class="ld-remember-arrow"></i>
                    </div>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdHappenDate')}</span>
                    <div class="ld-remember-valBox">
                       <input type="text"  id="fdHappenDate" value="${ fsscMobileNoteForm.fdHappenDate}" style="font-size:14px;"  onclick="selectTime('fdHappenDate','fdHappenDate');"   readonly="readonly"> 
                         <xform:text property="fdHappenDate" showStatus="noShow"></xform:text>
                        <i class="ld-remember-arrow"></i>
                    </div>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdSumPlace')}</span>
                    <div class="ld-remember-valBox" onclick="selectCity()">
                         <input type="text" id ="fdEndPlace" name="fdEndPlace" validate="required" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdSumPlace')}"   value="${ fsscMobileNoteForm.fdEndPlace}" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-mobile:fsscMobileNote.fdSumPlace')}" readonly="readonly">  
                         <xform:text property="fdEndAreaId"  showStatus="noShow" ></xform:text> 
                        <span style="margin-left:2px;color:#d02300;">*</span>
                        <i class="ld-remember-arrow"></i>
                    </div>
                </div>
            </div>
            <div class="ld-remember-consumerInfo ld-remember-info-item">
                <div>
                    <h3 class="ld-remember-title">${lfn:message('fssc-mobile:fsscMobileNote.information')}</h3><b></b>
                </div>
                <div>
                    <span class="ld-remember-label" >${lfn:message('fssc-mobile:fsscMobileNote.fdExpenseItem')}</span>
                    <div class="ld-remember-valBox" onclick="selectExpenseItem()">
                        <input type="text"  class="ld-consumerType" id ="fdExpenseItemName"  placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.select')}${lfn:message('fssc-mobile:fsscMobileNote.fdExpenseItem')}" value="${fsscMobileNoteForm.fdExpenseItemName }" readonly="readonly">  
                        <span style="margin-left:2px;color:#d02300;">*</span>
                        <i class="ld-remember-arrow"></i>
                       	<input name="fdExpenseItemId" type="hidden" validate="required" value="${fsscMobileNoteForm.fdExpenseItemId}" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdExpenseItem')}" /> 
                        <xform:text property="fdExpenseItemName" showStatus="noShow" ></xform:text> 
                    </div>
                </div>
                <div class="ld-remember-valBox">
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdMoney')}</span>
                    <div class="ld-remember-valBox" > 
                    	<c:if test="${not empty fsscMobileNoteForm.fdMoney}">
                    		<input name="fdMoney" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdMoney')}" validate="required currency-dollar"  autocomplete="off" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdMoney')}" value="<kmss:showNumber value="${fsscMobileNoteForm.fdMoney }" pattern="##0.00"></kmss:showNumber>" />
                    	</c:if>
                    	<c:if test="${empty fsscMobileNoteForm.fdMoney}">
                    		<input name="fdMoney" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdMoney')}" validate="required currency-dollar"  autocomplete="off" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdMoney')}" value=""/>
                    	</c:if>
                   		<span style="margin-left:2px;color:#d02300;">*</span>
                   </div> 
                </div>
                <div class="ld-remember-valBox">
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdSubject')}</span>
                    <input name="fdSubject" subject="${lfn:message('fssc-mobile:fsscMobileNote.fdSubject')}" validate="required"  autocomplete="off" placeholder="${lfn:message('fssc-mobile:fssc.mobile.placeholder.input')}${lfn:message('fssc-mobile:fsscMobileNote.fdSubject')}" value="${fsscMobileNoteForm.fdSubject }"/>
                	<span style="margin-left:2px;color:#d02300;">*</span>
                </div>
            </div>
            <!-- 发票明细编辑 -->

             <c:import url="/fssc/mobile/fssc_mobile_note/fsscMobileNote_invoice.jsp" charEncoding="UTF-8">
             </c:import> 
             
            <!-- 附件 -->
            <div class="ld-remember-attach">
                <div class="ld-remember-attach-title">
                    <h3>${lfn:message('fssc-mobile:py.attachment')}</h3>
                    <i></i>
                </div>
	                <ul id="fdAttachListId">
	                 <c:forEach items="${attachments}" var="list" varStatus="vstatus">
	                    <li >
	                        <div class="ld-remember-attact-info" onclick="showAtt('${list.value}','${list.title}');" varStatus="vstatus">
	                            <img src="" alt="" data-file="${list.title}">
	                            <span>${list.title}</span>
	                        </div>
	                        <span onclick="deleteAtt('${list.value}');"></span>
	                        <input hidden='true' name="fdAttList_form[${vstatus.index}].fdId" value="${list.value}">
	                        <input hidden='true' name="fdAttList_form[${vstatus.index}].fdName"  value="${list.title}">
	                    </li>
	                  </c:forEach>
	                </ul>
                <div class="ld-remember-attach-btn">
                    <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addAttach')}</span>
                </div>
            </div>
        </div>
    </div>
    
    <div class="ld-rememberOne-footer">
          <c:if test="${fsscMobileNoteForm.method_GET =='edit'}">
        	<div class="ld-remember-footer-save"   onclick="Com_Submit(document.fsscMobileNoteForm, 'update')">${lfn:message('fssc-mobile:button.save')}</div>
        </c:if>
        <c:if test="${fsscMobileNoteForm.method_GET =='add'}">
        	<div class="ld-remember-footer-save"   onclick="Com_Submit(document.fsscMobileNoteForm, 'save')">${lfn:message('fssc-mobile:button.save')}</div>
        </c:if>
    </div>
    
    <!-- 城市选择器 -->
   <c:import url="/fssc/mobile/fssc_mobile_note/fsscMobileNote_city.jsp"></c:import>
  
    <div id="showLetter" class="showLetter"><span>A</span></div>
    <!-- 消费类型选择 -->
    <div class="ld-cost-mask" id="ld-cost-mask">
        <div class="ld-cost-modal">
            <div class="ld-cost-modal-head">
                <div onclick="colseSelectExpenseItem()">${lfn:message('button.cancel')}</div>
                <div onclick="openSelectExpenseItem()">${lfn:message('button.ok')}</div>
            </div>
            <div class="ld-cost-modal-search">
                <input type="search" placeholder="搜索" id="searchItem" value="" >
                <i onclick="selectExpenseItem();"></i>
            </div>
            <div class="ld-cost-modal-main">
                <div class="ld-cost-modal-main-left">
                    <%-- 一级费用类型--%>
                </div>
                
                <div class="ld-cost-modal-main-right">
                    <div class="cost-swiper-container swiper-container-vertical">
                        <div class="swiper-wrapper" style="transform: translate3d(0px, 0px, 0px); transition-duration: 0ms;">
                            <div class="swiper-slide ld-cost-modal-main-right-item swiper-slide-active" style="height: 442px;">
                                <ul id="second_item">
                                    <%-- 二级费用类型--%>
                                </ul>
                                <p id="third_name"></p>
                                <ul id="third_item">
                                    <%-- 三级费用类型--%>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
	<!-- 上传中 -->   
     <div class="ld-main" id="ld-main-upload" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="../resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.uploading')}</span>
            </div>
        </div>
    </div>
     <div class="ld-main" id="ld-main-scan" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="../resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.scaning')}</span>
            </div>
        </div>
    </div>
    <fssc:ifModuleExists path="/fssc/iqubic/;/fssc/ocr/">
    	<script>
    		window.needOcr = true;
    	</script>
	</fssc:ifModuleExists>
<input hidden="true" name="fdModelName" value="com.landray.kmss.fssc.mobile.model.FsscMobileNote" />
<input hidden="true" name="fdModelId" value="${fsscMobileNoteForm.fdId }" />
<input hidden="true" name="invoiceParam"  />
<input hidden="true" name="attaParam"  />
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
</template:include>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/jquery.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/swiper.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/orderbyPingyin.js"></script>
<script src="${LUI_ContextPath}/fssc/common/resource/js/Number.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote_edit.js"></script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>

