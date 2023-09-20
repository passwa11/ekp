<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.DateUtil,com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.attend.model.SysAttendCategory"%>
<%
	ISysAttendCategoryService sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
	String categoryId = request.getParameter("categoryId");
	if(StringUtil.isNotNull(categoryId)) {
		SysAttendCategory category = (SysAttendCategory) sysAttendCategoryService.findByPrimaryKey(categoryId);
		if(category != null) {
			// 日期
			if(!category.getFdTimes().isEmpty()) {
				request.setAttribute("fdTime", DateUtil.convertDateToString(category.getFdTimes().get(0).getFdTime(), DateUtil.TYPE_DATE, null));
			}
			// 签到时间
			if(!category.getFdRule().isEmpty()) {
				request.setAttribute("fdInTime", DateUtil.convertDateToString(category.getFdRule().get(0).getFdInTime(), DateUtil.TYPE_TIME, null));
			}
			// 开始时间
			if(category.getFdStartTime()!=null) {
				request.setAttribute("fdStartTime", DateUtil.convertDateToString(category.getFdStartTime(), DateUtil.TYPE_TIME, null));
			}
			// 结束时间
			if(category.getFdEndTime()!=null) {
				request.setAttribute("fdEndTime", DateUtil.convertDateToString(category.getFdEndTime(), DateUtil.TYPE_TIME, null));
			}
			// 签到组名
			request.setAttribute("fdName", category.getFdName());
			// 是否允许EKP外部人员签到
			request.setAttribute("fdUnlimitOuter", category.getFdUnlimitOuter() == Boolean.TRUE);
		}
	}
%>
<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/import/css/view.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content">
		<section class="mui_meetingSign_header">
			<h1 class="mui_meetingSign_header_title">${fdName}</h1>
			<ul class="mui_meetingSign_header_board">
				<li>
					<div class="time">${fdStartTime}</div>
					<div class="txt">开始时间</div>
				</li>
				<li>
					<div class="tip">
						<span>${fdInTime}</span>后为迟到
					</div>
					<div class="date">${fdTime}</div>
				</li>
				<li>
					<div class="time">${fdEndTime}</div>
					<div class="txt">结束时间</div>
				</li>
			</ul>
		</section>
		<section class="mui_meetingSign_body">
			<ul class="mui_meetingSign_chooseLogin">
				<li><a id="innerBlock" class="chooseLogin_item" href="javascript:void(0)"
						onclick="window.open('${LUI_ContextPath}/sys/attend/mobile/import/sign_inner.jsp?categoryId=${JsParam.categoryId}','_self')">
						<h2 class="chooseLogin_title">公司内人员签到</h2>
						<p class="chooseLogin_summary">属于本公司内部人员，已有内部系统帐号，请登录后签到</p> <i
						class="mui mui-forward"></i>
				</a></li>
				<c:if test="${fdUnlimitOuter }">
				<li><a id="outerBlock" class="chooseLogin_item" href="javascript:void(0)">
						<h2 class="chooseLogin_title">公司外人员签到</h2>
						<p class="chooseLogin_summary">非本公司内部人员，没有内部系统帐号，需要填写个人信息</p> <i
						class="mui mui-forward"></i>
				</a></li>
				</c:if>
			</ul>
		</section>
		
		<script type="text/javascript">
			require(['dojo/topic','mui/dialog/Tip',"dojo/query","dojo/ready","dojo/on", "dojo/touch","dojo/request",'dojo/io-query','mui/util'],
					function(topic,Tip,query,ready,on,touch,request,ioq,util){
				<c:if test="${fdUnlimitOuter }">
				ready(function(){
					var categoryId = '${JsParam.categoryId}';
					var outerName = getCookie("outerName");//从cookie取
					var outerPhone = getCookie("outerPhone");
					var outerBlock = query('#outerBlock');
					var defaultUrl = util.formatUrl('/resource/sys/attend/sysAttendAnym.do?method=register&categoryId=${JsParam.categoryId}');
					if(categoryId && outerName && outerPhone) {
						var url = util.formatUrl('/resource/sys/attend/sysAttendOutPerson.do?method=getUserId');
						var options = {
							handleAs : 'json',
							method : 'POST',
							data : ioq.objectToQuery({
								name : outerName,
								phoneNum : outerPhone
							})
						};
						request(url, options).then(function(result){
							if(result && result.userId){
								// 已注册则跳到外部人员签到页面
								on(outerBlock[0], touch.press, function(e){
									location.href = util.formatUrl('/resource/sys/attend/sysAttendAnym.do?method=signOuter') 
										+ '&categoryId=' + categoryId + '&userId=' + result.userId;
								});
							} else {
								// 未注册则跳到注册页面
								on(outerBlock[0], touch.press, function(e){
									location.href = defaultUrl;
								});
							}
						}, function(e){
							cosole.error(e);
						});
					} else {
						on(outerBlock[0], touch.press, function(e){
							location.href = defaultUrl;
						});
					}
				});
				
				var getCookie = function(name){
					var arr=document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
					if(arr!=null)
						return decodeURIComponent(arr[2]);
					return null;
				}
				</c:if>
			});
		</script>
	</template:replace>
</template:include>
