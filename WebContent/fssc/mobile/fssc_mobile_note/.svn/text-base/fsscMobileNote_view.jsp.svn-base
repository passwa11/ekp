<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
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
     <title>${lfn:message('fssc-mobile:fsscMobileNote.remember')}</title>
    <script type="text/javascript">
       var costCenterArr = <%= request.getAttribute("costCenterArr")%>;
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
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdCostCenter')}</span>
                    <div class="ld-remember-valBox" >
                        <input type="text" id ="fdCostCenterName" value="${ fsscMobileNoteForm.fdCostCenterName}" readonly="readonly"> 
                        <xform:text property="fdCostCenterName" showStatus="noShow"/>
                        <xform:text property="fdCostCenterId" showStatus="noShow"/>
                        <i class="ld-remember-arrow"></i>
                    </div>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdHappenDate')}</span>
                    <div class="ld-remember-valBox">
                       <input type="text"  id="fdHappenDate" value="${ fsscMobileNoteForm.fdHappenDate}" readonly="readonly"> 
                         <xform:text property="fdHappenDate" showStatus="noShow"></xform:text>
                        <i class="ld-remember-arrow"></i>
                    </div>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdSumPlace')}</span>
                    <div class="ld-remember-valBox" onclick="selectCity()">
                         <xform:text property="fdEndPlace" style=" color: #333;"    showStatus="readOnly"></xform:text>
                         <xform:text property="fdEndAreaId"  showStatus="noShow" ></xform:text> 
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
                         <input type="text"  class="ld-consumerType" id ="fdExpenseItemName" value="${ fsscMobileNoteForm.fdExpenseItemName}" readonly="readonly"> 
                        <i class="ld-remember-arrow"></i>
                       <xform:text property="fdExpenseItemId" showStatus="noShow" ></xform:text> 
                        <xform:text property="fdExpenseItemName" showStatus="noShow" ></xform:text> 
                    </div>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdMoney')}</span>
                    <div class="ld-remember-valBox" >
                    	<xform:text property="fdMoney"  style=" color: #333;" showStatus="edit"  ></xform:text>
                    	<i class="ld-remember-arrow"></i>
                    </div>
                </div>
                <div>
                    <span class="ld-remember-label">${lfn:message('fssc-mobile:fsscMobileNote.fdSubject')}</span>
                   <!--  <input type="text" placeholder="请输入事由"> -->
                    <xform:text property="fdSubject"  style=" color: #333;" showStatus="edit"  ></xform:text>
                </div>
            </div>
            <!-- 发票明细 -->
            
            <kmss:ifModuleExist path="/fssc/ledger">
             	<c:import url="/fssc/mobile/fssc_mobile_invoice/fsscMobileInvoice_info.jsp">
             		<c:param name="fdModelId" value="${fsscMobileNoteForm.fdId }"></c:param>
             		<c:param name="fdModelName" value="com.landray.kmss.fssc.mobile.model.FsscMobileNote"></c:param>
             	</c:import>
            </kmss:ifModuleExist>

            <!-- 附件 -->
            <div class="ld-remember-attach">
                <div class="ld-remember-attach-title">
                    <h3>${lfn:message('fssc-mobile:py.attachment')}</h3>
                    <i></i>
                </div>
                	<c:forEach items="${attachments}" var="list">
	                 <ul>
	                    <li >
	                        <div class="ld-remember-attact-info" onclick="showAtt('${list.value}','${list.title}');">
	                            <img src="../resource/images/pdf.png" alt="">
	                            <span>${list.title}</span>
	                        </div>
	                        <span onclick="deleteAtt('${list.value}');"></span>
	                    </li>
	                	</ul>
	                </c:forEach>
                <div class="ld-remember-attach-btn">
                    <i></i><span>${lfn:message('fssc-mobile:fsscExpenseMain.addAttach')}</span>
                </div>
            </div>
        </div>
    </div>
    <div class="ld-rememberOne-footer">
          <c:if test="${fsscMobileNoteForm.method_GET =='view'}">
        	<div class="ld-remember-footer-save"   onclick="Com_Submit(document.fsscMobileNoteForm, 'update')">${lfn:message('fssc-mobile:button.save')}</div>
        </c:if>
    </div>
    
    <!-- 城市选择器 -->
   <c:import url="/fssc/mobile/fssc_mobile_note/fsscMobileNote_city.jsp"></c:import>
  
    <div id="showLetter" class="showLetter"><span>A</span></div>
    <!-- 消费类型选择 -->
    <div class="ld-cost-mask" id="ld-cost-mask">
        <div class="ld-cost-modal">
            <div class="ld-cost-modal-head">
                <div onclick="colseSelectExpenseItem()">${lfn:message('fssc-mobile:button.cancel')}</div>
                <div onclick="openSelectExpenseItem()">${lfn:message('fssc-mobile:button.finish')}</div>
            </div>
            <div class="ld-cost-modal-search">
                <input type="text" placeholder="搜索" id="searchItem" value="" >
                <i onclick="selectExpenseItem();"></i>
            </div>
            <div class="ld-cost-modal-main">
                <div class="ld-cost-modal-main-left">
                   <!-- 一级费用类型 -->
                </div>
                
                <div class="ld-cost-modal-main-right">
                    <div class="cost-swiper-container">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide ld-cost-modal-main-right-item">
                               <ul class="ld-cost-modal-main-right-second">
                                  <!--二级费用类型  --> 
                               </ul>
                              <!--   <p></p>
                               <ul  class="ld-cost-modal-main-right-third">
                                   	 三级费用类型
                                </ul> -->
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
    
<input hidden="true" name="fdModelName" value="com.landray.kmss.fssc.mobile.model.FsscMobileNote" />
<input hidden="true" name="fdModelId" value="${fsscMobileNoteForm.fdId }" />
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
</template:include>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/jquery.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/swiper.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/picker.min.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/orderbyPingyin.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/fssc_mobile_note/fsscMobileNote_edit.js"></script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp" %>

