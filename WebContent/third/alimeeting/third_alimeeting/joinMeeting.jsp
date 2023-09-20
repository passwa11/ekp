<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/third/alimeeting/resource/css/aliyunMeeting.css" />
		
		<script type="text/javascript">
			seajs.use(['lui/dialog'], function (dialog) {
				window.dialog = dialog;
			});
			
			var initData = {
				fdMeetingId : "${param.fdMeetingId}",
				fdCurUserId : "${KMSS_Parameter_CurrentUserId}",
				contextPath : "${LUI_ContextPath}"
            };
		
			function joinMeeting() {
				
				// 必填校验提示处理
				var codeEmptyTip = $(".codeEmptyTip");
				if (codeEmptyTip.length > 0) {
					codeEmptyTip.remove();
				}
				
				// 获取会议口令
				var codeInputDom = $("#meeting-number-input");
				var fdMeetingCode = codeInputDom.val();
				
				// 口令不能为空
				if (!fdMeetingCode) {
					codeInputDom.after('<div class="codeEmptyTip">请先输入会议口令</div>');
				} else {
					
					// 查询会议口令，校验当前用户是否有权限进入视频会议
					$.ajax({
						url: "${KMSS_Parameter_ContextPath}/third/alimeeting/third_alimeeting/aliMeetingAction.do?method=joinAliyunMeeting",
						type: "GET",   
						async: false,    //用同步方式 
						data:{
							fdMeetingId : initData.fdMeetingId,   // EKP会议ID
							fdMeetingCode : fdMeetingCode		  // 阿里云会议口令
						},
						success: function(result) {
							var resultObj = eval('(' + result + ')');
							
							console.log(resultObj);
							
							if (resultObj.success) {
								
								var storage = window.sessionStorage;
								
								// 当前用户登录ID，用于配置阿里云视频会议SDK所需的PageConfig对象
								resultObj.meetingInfo.paasUserId = initData.fdCurUserId;
								
								// 上下文，阿里云视频会议SDK设置路由时需要用到
								resultObj.meetingInfo.contextPath = initData.contextPath;
								
								// 把阿里API返回的数据设在sessionStorage对象中
								storage.setItem("meetingInfo", JSON.stringify(resultObj.meetingInfo));
								
								// 跳转到阿里云视频会议页面
								window.location.href = '<c:url value="/third/alimeeting/third_alimeeting/videoMeeting.html"></c:url>';
							} else {
								
								if ("404" == resultObj.errorCode) {  // 口令不正确
									
									dialog.alert({
										html : "口令不正确，请确认当前会议与输入口令是否匹配",
										title : "入会提示"
									});
								
								} else if ("20050" == resultObj.errorCode) {  // 用户不是该会议参会人员
									
									dialog.alert({
										html : '您无权限进入该视频会议，请联系相关人员！',
										title : "入会提示"
									});
								
								}
							}
						}
					});
				}
			}
		</script>
	</template:replace>
	
	<template:replace name="body">
	    <div id="enter-content-view">
	    	<div class="enter-content-box">
	    		<div id="enter-content-title">加入会议</div>
		        <div id="meeting-number">会议口令</div>
		        <input type="text" id="meeting-number-input" placeholder="请输入会议口令"/>
		        <div id="error-tip" style="visibility: hidden;">错误</div>
		        <button id="join-meeting" onclick="joinMeeting()">立即加入</button>
		        <div id="broswer-tip" style="display: none;">目前只支持Chrome浏览器（版本>=70）</div>
	    	</div>
	    </div>
	    
	</template:replace>
</template:include>