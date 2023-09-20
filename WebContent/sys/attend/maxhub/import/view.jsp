<%@page import="com.landray.kmss.sys.attend.model.SysAttendCategoryTime"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attend.model.SysAttendCategory"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/maxhub/js/lib/swiper/swiper.min.css?s_cache=${MUI_Cache}"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/maxhub/import/resource/css/view.css?s_cache=${MUI_Cache}"></link>

<script>
	window.__fdModelId4sysAttend__ = '${JsParam.fdModelId}';
</script>

<div id="sysAttendView_${JsParam.fdModelId}" class="sysAttendView ${JsParam.className}">

	<%
		// 获取签到组
		String fdAppId = request.getParameter("fdModelId");
		ISysAttendCategoryService categoryService = ((ISysAttendCategoryService)SpringBeanUtil.getBean("sysAttendCategoryService"));
		List<SysAttendCategory> cateList = categoryService.findCategorysByAppId(fdAppId);
		
		if(cateList.size() > 0){
			request.setAttribute("_cate", cateList.get(cateList.size() - 1));
			
			SysAttendCategory _cate = cateList.get(cateList.size() - 1);
		
			String startTime = DateUtil.convertDateToString(_cate.getFdStartTime(), DateUtil.TYPE_TIME, null);
			String endTime = DateUtil.convertDateToString(_cate.getFdEndTime(), DateUtil.TYPE_TIME, null);
			
			pageContext.setAttribute("__fdStartTime", startTime);
			pageContext.setAttribute("__fdEndTime", endTime);
			pageContext.setAttribute("__hasCate", true);
			pageContext.setAttribute("__cateId", _cate.getFdId());
		}
	%>
	
	<script>
		window.__fdStartTime4sysAttend__ = '${__fdStartTime}';
		window.__fdEndTime4sysAttend__ = '${__fdEndTime}';
		window.__hasCate4sysAttend__ = '${__hasCate}';
		window.__cateId4sysAttend__ = '${__cateId}';
	</script>

	<div class="sysAttendView_content <c:if test="${__hasCate != true }">mhui-hidden</c:if>" id="sysAttendContentView">
		<div class="sysAttendView_limit" style="margin-top: 1.625rem;">
			<div class="sysAttendView_limit_sec sysAttendView_limit_start">
				<span id="sysAttendStartTime">${__fdStartTime }</span>
				<div>签到开始</div>
			</div>
			<div class="sysAttendView_limit_line sysAttendView_limit_">
				<span id="sysAttendLateTime">-</span>
			</div>
			<div class="sysAttendView_limit_sec sysAttendView_limit_end">
				<span id="sysAttendEndTime">${__fdEndTime }</span>
				<div>签到结束</div>
			</div>
		</div>
		<div class="sysAttendView_cards" style="margin-top: 1.5rem;">
		
			<div class="sysAttendView_card sysAttendView_qrCode" style="width: 31.25rem; height: 34.6875rem;">
				<div class="sysAttendView_card_head" style="border: none;">
					扫一扫签到
				</div>
				<div class="sysAttendView_card_content">
					<div style="text-align: center;">
						<div id="sysAttendQRCode">
							<div id="sysAttendQRCodeMask">
								<span id="sysAttendQRCodeStatus"></span>
							</div>
							<div id="sysAttendQRCodeMain"></div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="sysAttendView_card sysAttendView_status" style="width: 38rem; height: 34.6875rem;">
				<div class="sysAttendView_card_head">
					应签到<span id="sysAttendTotal"> - </span>人
				</div>
				<div class="sysAttendView_card_content">
					<div class="sysAttendView_attendInfo">
						<div data-dojo-type="dijit/_WidgetBase"
							data-dojo-mixins="sys/attend/maxhub/import/resource/js/SignInCountBtnMixin"
							data-dojo-props="targetId:'sysAttendPersonList'"
							class="sysAttendView_count" style="border-color: #4285F4;">
							<span id="sysAttendSignIn">0</span>
							<div>已签到</div>
						</div>
						<div data-dojo-type="dijit/_WidgetBase"
							data-dojo-mixins="sys/attend/maxhub/import/resource/js/SignInCountBtnMixin"
							data-dojo-props="targetId:'sysUnAttendPersonList'"  
							class="sysAttendView_count" style="border-color: #EA4335;">
							<span id="sysAttendUnSign">0</span>
							<div>未签到</div>
						</div>							
					</div>
					
					<div class="sysAttendView_person">
						<span id="sysAttendPersonArrow"></span>
						<ul id="sysAttendPersonList"
							class="sysAttendView_personList active"
							data-dojo-type="mhui/list/ItemListBase"
							data-dojo-mixins="mhui/list/SwiperItemListMixin, sys/attend/maxhub/import/resource/js/AttendPersonListMixin"
							data-dojo-props="lazy:true,navigable:true,filter:'attend'">
						</ul>
						<ul id="sysUnAttendPersonList"
							class="sysAttendView_personList"
							data-dojo-type="mhui/list/ItemListBase"
							data-dojo-mixins="mhui/list/SwiperItemListMixin, sys/attend/maxhub/import/resource/js/AttendPersonListMixin"
							data-dojo-props="lazy:true,navigable:true,filter:'unattend'">
						</ul>
					</div>
					
				</div>
			</div>
		
		</div>
		
	</div>
	
	<div class="sysAttendView_noData <c:if test="${__hasCate == true }">mhui-hidden</c:if>" id="sysAttendNoDataView">
		<div data-dojo-type="mhui/message/MessageBox"
			data-dojo-props="icon:'mhui-icon-signin',message:'会议未发起签到',buttons:[{text:'刷新',icon:'mui mui-reflash',onClick:'getSysAttendCategory'}]">
		</div>
		
		<c:set var="_signInBtnFunc" value=""></c:set>
		<c:if test="${JsParam.signInBtnFunc != null}">
			<c:set var="_signInBtnFunc" value="${JsParam.signInBtnFunc }"></c:set>
		</c:if>
		
		<c:if test="${JsParam.showSignInBtn == true}">
			<div id="btnLaunchSignIn" 
				data-dojo-type="mhui/button/Button"
				data-dojo-props="text:'发起签到',type:'primary',size:'lg',onClick:'${_signInBtnFunc }'">
			</div>
		</c:if>
	</div>
	
	<script type="text/javascript" src="${LUI_ContextPath}/sys/attend/maxhub/import/resource/js/view.js"></script>
	
</div>