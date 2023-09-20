<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.StringUtil,com.landray.kmss.util.SpringBeanUtil,
                com.landray.kmss.sys.authentication.identity.model.SysAuthenEntity,
                com.landray.kmss.sys.authentication.identity.service.ISysAuthenEntityService"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
	boolean hasMobileNo = false;
	SysAuthenEntity sysAuthenEntity = ((ISysAuthenEntityService)SpringBeanUtil.getBean("sysAuthenEntityService")).saveAndGetEntity();
	if(sysAuthenEntity!=null && StringUtil.isNotNull(sysAuthenEntity.getFdMobileNo())){
		String mobileNo =sysAuthenEntity.getFdMobileNo();
		String mobileNoShow = mobileNo.substring(0,3)+"****"+mobileNo.substring(7);
		request.setAttribute("mobileNoShow", mobileNoShow);
		hasMobileNo = true;
	}
	request.setAttribute("hasMobileNo", hasMobileNo);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link charset="utf-8" rel="stylesheet" href="<%=request.getContextPath()%>/sys/authentication/identity/css/mobile/custom.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript">

function buildSubmitData(){
	var data = {};
	data.mobileCode = document.getElementById("verify-phone").value;
	return data;
}

function validateResult( msg){
	$("#msg_block").text(msg);
}

function dataValidate(){
	var value = document.getElementById("verify-phone").value;
	if(!value || value==''){
		$("#msg_block").text("请输入验证码");
		return false;
	}
	return true;
}

function sendMobileCode(type){
	var datas;
	$("#button_sendCode").attr("disabled","disabled");
	$("#button_sendCode").attr("onclick","");
	$("#button_sendCode").addClass("btn-disabled");
	$("#verify-phone").removeAttr("disabled");
	$.ajax({
		type : "POST",
		dataType : 'json',
		url :  "${KMSS_Parameter_ContextPath}sys/authentication/indentity/sysValidationCode.do?method=sendMobileValidationCode"
							+ "&r=" + Math.random(),
					data : {"type":"1"},
					success : function(data) {
						if (data.result == true) {
							$("#msg_block").text("短信已发出");
						} else {
							$("#msg_block").html(data.errMsg);
						}
					},
					error : function(data) {
						alert(data);
						$("#msg_block").html(data.errMsg);
					}
				});
		times();

	}
	var s = 60, t;
	function times() {
		s--;
		$("#button_sendCode").text(s + "秒后重新发送");
		t = setTimeout('times()', 1000);
		if (s <= 0) {
			s = 60;
			clearTimeout(t);
			$("#button_sendCode").text("获取验证码");
			$("#button_sendCode").attr("disabled", "");
			$("#button_sendCode").attr("onclick", "sendMobileCode(1);");
			$("#button_sendCode").removeAttr("disabled");
			$("#button_sendCode").removeClass("btn-disabled");
		}
	}

	/* function returnFailMsg(flag) {
		if (flag == "true") {
			seajs
					.use(
							[ 'lui/dialog' ],
							function(dialog) {
								dialog
										.iframe(
												"/sys/organization/sys_org_retrieve_password/retrievePasswordFail.jsp",
												null, null, {
													width : 240,
													height : 120
												})
							});
		}
	} */
	
	function submitMobileCode(){
		var config = getQueryVariable("config");
	}
	//获取url参数
	function getQueryVariable(variable)
	{
	       var query = window.location.search.substring(1);
	       var vars = query.split("&");
	       for (var i=0;i<vars.length;i++) {
	               var pair = vars[i].split("=");
	               if(pair[0] == variable){return pair[1];}
	       }
	       return(false);
	}
</script>	
</head>
<body>
<div class="identity_mobile">
<c:if test="${ hasMobileNo == true}">
	<script type="text/javascript">
		//用户是否设置有对应的验证信息
		function isHasSetting(){
			return true;
		}
	</script>
  <!-- 短信验证码验证 Starts -->
	    <div class="muiVerify-container">
	        <h3 class="muiVerify-title">短信验证码校验</h3>
	        <div class="muiVerify-head">
	            <p class="muiVerify-desc">当前已验证手机号码：${mobileNoShow}</p>
	            <div class="muiVerify-form">
	            	<input type="text" id="verify-phone" placeholder="请输入验证码" disabled="disabled">
	            	<span class="com_bgcolor_d muiVerify-btn" id="button_sendCode" onclick="sendMobileCode(1);">获取验证码</span>
	            	
	            </div>
	            <p class="muiVerify-error" id="msg_block"></p>
	            <p class="muiVerify-tips">说明：输入手机验证码并点击确认则表示，您已同意并承认此行为为您本人意愿</p>
	        </div>
	
	       <!--  <div class="muiVerify-footer">
	            <span class="muiVerify-btn btn-cancel">取消</span>
	            <span class="muiVerify-btn btn-confirm" onclick="submitMobileCode();">确定</span>
	        </div> -->
	    </div>
	    <!-- 短信验证码验证 Ends -->
</c:if>
<c:if test="${ hasMobileNo == false}">
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
	            <p class="muiVerify-opr_area">您尚未绑定手机号码，请在绑定手机号码后再完成此操作</p>
	        </div>
	       <!--  <div class="muiVerify-ft">
	            <span class="muiVerify-btn">我知道了</span>
	        </div> -->
	    </div>
	    <!-- 未设置验证密码 Ends -->
  <!-- <script type="text/javascript">
		var iknow = document.getElementsByClassName("muiVerify-btn");
		iknow[0].onclick = function(){
			window.close();
		}
	</script> -->
</c:if>
</div>
</body>
</html>
