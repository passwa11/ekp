<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,
                com.landray.kmss.sys.authentication.identity.model.SysAuthenEntity,
                com.landray.kmss.sys.authentication.identity.service.ISysAuthenEntityService"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<%
	boolean hasSettingePwd = false;
	SysAuthenEntity sysAuthenEntity = ((ISysAuthenEntityService)SpringBeanUtil.getBean("sysAuthenEntityService")).saveAndGetEntity();
	if(sysAuthenEntity!=null && StringUtil.isNotNull(sysAuthenEntity.getFdVerifyPwd())){
		hasSettingePwd = true;
	}
	request.setAttribute("hasSettingePwd", hasSettingePwd);
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link charset="utf-8" rel="stylesheet" href="<%=request.getContextPath()%>/sys/authentication/identity/css/mobile/custom.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript">

function buildSubmitData(){
	var data = {};
	data.password = document.getElementById("verify-phone").value;
	return data;
}

function validateResult( msg){
	$("#msg_block").text(msg);
}

function dataValidate(){
	var value = document.getElementById("verify-phone").value;
	if(!value || value==''){
		$("#msg_block").text("请输入您的身份验证密码");
		return false;
	}
	return true;
}

</script>	
</head>
<body>
<div class="identity_mobile">
<c:if test="${ hasSettingePwd == true}">
	<script type="text/javascript">
		//用户是否设置有对应的验证信息
		function isHasSetting(){
			return true;
		}
	</script>
  <!-- 短信验证码验证 Starts -->
    <div class="muiVerify-container">
		<h3 class="muiVerify-title">身份验证密码校验</h3>
		<div class="muiVerify-head">
			<p class="muiVerify-desc">请输入你的身份校验密码</p>
			<div class="muiVerify-form">
				<input type="password" id="verify-phone" placeholder="请输入身份校验密码">
			</div>
			<p class="muiVerify-error" id="msg_block"></p>
		</div>
		<p class="muiVerify-tips">说明：输入验证码并点击确认则表示，您已同意并承认此行为为您本人意愿</p>
		<!-- <div class="muiVerify-footer">
			<span class="muiVerify-btn btn-cancel">取消</span>
			<span class="muiVerify-btn btn-confirm">确定</span>
		</div> -->
	</div>
        <!-- 短信验证码验证 Ends -->
 </c:if>
<c:if test="${ hasSettingePwd == false}">  
	<script type="text/javascript">
		//用户是否设置有对应的验证信息
		function isHasSetting(){
			return false;
		}
	</script>
  <div class="muiVerify-mask"></div>
	    <!-- 未设置验证密码 Starts -->
	    <div class="muiVerify-alert">
	        <!-- 操作提示 -->
	        <div class="muiVerify-msg">
	            <div class="muiVerify-icon_area">
	                <div class="muiVerify-icon icon_warning"></div>
	            </div>
	            <div class="muiVerify-text_area">抱歉，您尚无法执行此操作</div>
	            <p class="muiVerify-opr_area">您尚未设置身份验证密码，请在设置验证密码后再完成此操作</p>
	        </div>
	        <!-- <div class="muiVerify-ft">
	            <span class="muiVerify-btn">我知道了</span>
	        </div> -->
	    </div>
	    <!-- 未设置验证密码 Ends -->
</c:if>
</div>
</body>
</html>
