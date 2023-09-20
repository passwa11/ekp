<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/common.css?s_cache=${LUI_Cache }" />
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/reset.css?s_cache=${LUI_Cache }" >
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/footer.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/swiper.min.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/costDetail.css?s_cache=${LUI_Cache }">
    <script >
	    var formInitData={
	   		 'LUI_ContextPath':'${LUI_ContextPath}',
	    }
    	Com_IncludeFile("kk-1.3.10.min.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
    	Com_IncludeFile("rem.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);  	
    </script>
    <title>${lfn:message('fssc-mobile:module.fssc.mobile')}</title>
</head>
<body>

    <div class="ld-feesCharged">
        <div class="ld-feesCharged-home-main">
            <!-- 待审核 -->
            <div class="ld-feesCharged-home-main-top">
                <div class="ld-feesCharged-home-main-notSubmit">
                    <div class="ld-feesCharged-home-main-notSubmit-top" onclick="listApproval();" >
                        <span style="color:white;">${lfn:message('fssc-mobile:lbpm.approve')}</span><span style="color:white;" class="notSubmitNum" id="count">0</span><span style="color:white;">${lfn:message('fssc-mobile:lbpm.approve.count')}</span>
                        <i></i>
                    </div>
                    <fssc:checkVersion version="true">
                    	<!-- 未报费用 -->
	                    <div class="ld-feesCharged-home-main-notSubmit-bottom" onclick="notExpenseData();">
	                        <p>${lfn:message('fssc-mobile:fssc.mobile.index.notExpense.money')}</p>
	                        <p id="fdMoney">0.00</p>
	                    </div>
                    </fssc:checkVersion>
                    <fssc:checkVersion version="false">
                    	<!-- 已报费用，报销待审和发布的总金额 -->
	                    <div class="ld-feesCharged-home-main-notSubmit-bottom">
	                        <p>${lfn:message('fssc-mobile:fssc.mobile.index.expense.money')}</p>
	                        <p id="fdExpensedMoney">0.00</p>
	                    </div>
                    </fssc:checkVersion>
                </div>
            </div>
            <!-- 常用按钮 -->
           <div class="ld-feesCharged-home-btns">
	            <div class="ld-feesCharged-commonUsedLink"  style="display:none;">
                    <h3>${lfn:message('fssc-mobile:fssc.mobile.index.link')}</h3>
                    <!-- 不大于8个则直接显示swiper，否则显示下面的swiper-container -->
	                    <div class="swiper-container">
	                        <div class="swiper-wrapper" id="personal">
	                         	
	                        </div>
	                        <div class="swiper-pagination"></div>
	                     </div>
	           </div>
	           <div class="ld-line" id="person_line" style="display:none;"></div>
              <!-- 应用导航-->
               <div class="ld-feesCharged-shortcut" style="display:none;">
                   <h3>${lfn:message('fssc-mobile:fssc.mobile.index.app')}</h3>
                     <div class="swiper-container">
                   	   <div class="swiper-wrapper" id="app">
                  		
                      </div>
                   </div>
               </div>
              <div class="ld-line" id="app_line" style="display:none;"></div>
              <!-- 第三方应用 -->
              <div class="ld-feesCharged-otheruse" style="display:none;">
                  <h3>${lfn:message('fssc-mobile:fssc.mobile.index.third')}</h3>
                  <div class="swiper-container">
                     <div class="swiper-wrapper" id="third">
                      	
                     </div>
                </div>
              </div>
            </div>
        </div>
        <div class="ld-feesCharged-mine ld-feesCharged-hidden">
        		<!-- 个人信息开始 -->
        		<div class="ld-feesCharged-mine-header">
        			<div class="ld-feesCharged-mine-header-icon">
        				<img src="${LUI_ContextPath }/sys/person/image.jsp?personId=${KMSS_Parameter_CurrentUserId}">
        			</div>
        			<div class="ld-feesCharged-mine-header-info">
        				<h3></h3>
        				<p></p>
        			</div>
        		</div>
        		<div class="ld-feesCharged-mine-cell" onclick="editMobileLink();">
        			<div class="ld-feesCharged-mine-cell-left">
        				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/ifsdmobile021.png"/>
        				<span>${lfn:message('fssc-mobile:fssc.mobile.index.link.config')}</span>
        			</div>
        			<div class="ld-feesCharged-mine-cell-right">
        				<span></span>
        			</div>
        		</div>
        		<div class="ld-feesCharged-mine-cell" onclick="listData();">
        			<div class="ld-feesCharged-mine-cell-left">
        				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/authorize.png"/>
        				<span>${lfn:message('eop-basedata:table.eopBasedataAuthorize')}</span>
        			</div>
        			<div class="ld-feesCharged-mine-cell-right">
        				<span></span>
        			</div>
        		</div>
        		<%-- <div class="ld-feesCharged-mine-cell" onclick="listInvoiceTitle();">
        			<div class="ld-feesCharged-mine-cell-left">
        				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/invoice_tittle.png" style="background-size:80%;"/>
        				<span>我的发票</span>
        			</div>
        			<div class="ld-feesCharged-mine-cell-right">
        				<span></span>
        			</div>
        		</div> --%>
        		<fssc:checkVersion version="true">
        		<div class="ld-feesCharged-mine-cell" onclick="window.open('${LUI_ContextPath}/fssc/mobile/fssc_mobile_invoice/fsscMobileInvoice.do?method=listMyInvoice','_self');">
        			<div class="ld-feesCharged-mine-cell-left">
        				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/my_invoice.png" style="width:1rem;height:1rem;"/>
        				<span>${lfn:message('fssc-mobile:fsscMobileInvoice.my') }</span>
        			</div>
        			<div class="ld-feesCharged-mine-cell-right">
        				<span></span>
        			</div>
        		</div>
        		</fssc:checkVersion>
        		<div class="ld-feesCharged-mine-cell" onclick="window.open('${LUI_ContextPath}/eop/basedata/eop_basedata_account/eopBasedataAccount.do?method=listMobile','_self');">
        			<div class="ld-feesCharged-mine-cell-left">
        				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/bank.png" style="background-size:80%;"/>
        				<span>${lfn:message('fssc-mobile:fsscMobileAccount.my') }</span>
        			</div>
        			<div class="ld-feesCharged-mine-cell-right">
        				<span></span>
        			</div>
        		</div> 
        		<div class="ld-feesCharged-mine-cell" onclick="logout();">
        			<div class="ld-feesCharged-mine-cell-left">
        				<img src="${LUI_ContextPath }/fssc/mobile/resource/images/icon/logout.png"/>
        				<span>${lfn:message('fssc-mobile:fssc.mobile.index.logout')}</span>
        			</div>
        			<div class="ld-feesCharged-mine-cell-right">
        				<span></span>
        			</div>
        		</div>
        </div>
        <div class="ld-feesCharged-footer">
            <div class="ld-freesCharged-footer-conent">
                <div class="ld-footer-home active">
                    <i></i>
                    <span>${lfn:message('fssc-mobile:fssc.mobile.index.title')}</span>
                </div>
                <fssc:checkVersion version="true">
                <div class="ld-footer-center">
                    <span onclick="rememberOne()">${lfn:message('fssc-mobile:fsscMobileNote.remember')}</span>
                </div>
                </fssc:checkVersion>
                <div class="ld-footer-mine">
                    <i></i>
                    <span>${lfn:message('fssc-mobile:fssc.mobile.index.mine')}</span>
                </div>
            </div>
        </div>
    </div>
    
    <div class="ld-remember-modal">
        <div class="ld-remember-modal-main">
           <ul>
               <li id="manual" style="display:none;">
                   <div onclick="rememberNoteByHand();">
                      <span class="ld-manual-invoice"></span>
                       <div class="ld-remember-modal-text" >
                           <p>${lfn:message('fssc-mobile:addByHand')}</p>
                           <p>${lfn:message('fssc-mobile:addByHand.tips')}</p>
                       </div>
                   </div>
                   <i></i>
               </li>
                <li id="qrcode" style="display:none;">
                   <div  onclick="scanQrCode();">
                       <span class="ld-saomiao-invoice"></span>
                       <div class="ld-remember-modal-text">
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.scanCode')}</p>
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.scanCode.tips')}</p>
                       </div>
                   </div>
                    <i></i>
               </li>
                <li id="photo" style="display:none;">
                   <div id="getPhoto" >	
                        <span class="ld-picture-invoice"></span>
                        <div class="ld-remember-modal-text" >
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.photo')}</p>
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.photo.tips')}</p>
                        </div>
                   </div>
                   <i></i> 
               </li>
               
               <kmss:ifModuleExist path="/fssc/wxcard">
               <li id="weixin">
                   <div onclick="chooseByWeixin();">
                       <span class="ld-saomiao-invoice"></span>
                       <div class="ld-remember-modal-text">
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.wxcard')}</p>
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.tips')}</p>
                       </div>
                   </div>
                   <i></i>
               </li>
               </kmss:ifModuleExist>
               
               <kmss:ifModuleExist path="/fssc/didi">
               <li id="didi">
                   <div onclick="chooseDidiTravel();">
                       <span class="ld-select-travel"></span>
                       <div class="ld-remember-modal-text">
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.didi')}</p>
                           <p>${lfn:message('fssc-mobile:fssc.mobile.index.didi.tips')}</p>
                       </div>
                   </div>
                   <i></i>
               </li>
               </kmss:ifModuleExist>
           </ul>
           <div class="ld-remember-modal-cancel">${lfn:message('button.cancel')}</div>
        </div>
    </div>
    <!-- 上传中 -->   
     <div class="ld-main" id="ld-main-upload" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.uploading')}</span>
            </div>
        </div>
    </div>
    <!-- 识别中 -->  
    <div class="ld-main" id="ld-main" style="display: none;">
        <div class="ld-mask">
            <div class="ld-progress-modal">
                <img src="${LUI_ContextPath}/fssc/mobile/resource/images/loading.png" alt="">
                <span>${lfn:message('fssc-mobile:fssc.mobile.list.scaning')}</span>
            </div>
        </div>
    </div>
    
        <!-- 选择相机 -->
    <div class="ld-turnUpCamera" id="ld-turnUpCamera" style="display: none" >
      <div class="ld-turnUpCamera-modal">
        <div class="ld-turnUpCamera-modal-content">
            <div class="ld-turnUpCamera-modal-content-list">
                <div onclick="getPhoto('camera')">
                  <span>${lfn:message('fssc-mobile:fssc.mobile.index.photo.select')}</span>
                  <img src="${LUI_ContextPath}/fssc/mobile/resource/images/turnUpCamera.png" alt="">
                </div>
                <div onclick="getPhoto('album')">
                  <span>${lfn:message('fssc-mobile:fssc.mobile.index.photo.pic')}</span>
                  <img src="${LUI_ContextPath}/fssc/mobile/resource/images/gallery.png" alt="">
                </div>
            </div>
            <div class="ld-turnUpCamera-modal-content-btn">
             	 ${lfn:message('button.cancel')}
            </div>
        </div>
      </div>
    </div>
    
    <div class="didi-order-list">
    	<div class="ld-costDetail">
		        <div class="ld-notSubmit-main">
		            <div class="ld-notSubmit-list">
		                <ul>
		                </ul>
		            </div>
		        </div>
		        
		        <div class="ld-didi-check" style="display: none">
		      		<div class="ld-costDetail-footer">
		                <div class="ld-checkBox">
		                    <input type="checkbox" id="selectAll" id="ld-selectAll" value="0"/>
		                    <label class="checkbox-label" for="ld-selectAll">${lfn:message('fssc-mobile:button.selectAll')}</label>
		                </div> 
		                <div class="ld-costDetail-btn-cancel"  onclick="cancelSelectDidi()">${lfn:message('button.cancel')}</div>
		                <div class="ld-costDetail-btn"  onclick="confirmSelectDidi()">${lfn:message('button.ok')}</div>
		       		 </div>
		        	</div>
		        	
		    </div>
    </div>
    <fssc:checkVersion version="true">
    	<input type="hidden" value="true" name="version" />
    </fssc:checkVersion>
</body>

<script  type="text/javascript">
   Com_IncludeFile("zepto.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
   Com_IncludeFile("swiper.min.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
   Com_IncludeFile("dyselect.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
   Com_IncludeFile("index.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_main/", 'js', true);
</script>
<script>
var commonUseLinkSwiper = new Swiper('.swiper-container', {
    slidesPerView: 4,
    slidesPerColumn: 2,
    slidesPerColumnFill: "row",
    pagination: '.swiper-pagination',
    observer:true,//修改swiper自己或子元素时，自动初始化swiper 
    observeParents:true//修改swiper的父元素时，自动初始化swiper 
});
Com_IncludeFile("main.js", "${LUI_ContextPath}/fssc/mobile/resource/js/", 'js', true);
</script>
<script src="//g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js"></script>
<script src="//res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
