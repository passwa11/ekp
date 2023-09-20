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
<link charset="utf-8" rel="stylesheet" href="<%=request.getContextPath()%>/sys/authentication/identity/css/custom.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript">

function buildSubmitData(){
	var data = {};
	data.mobileCode = document.getElementsByName("mobileCode")[0].value;
	return data;
}

function validateResult( msg){
	//debugger;
	$("#errMsg").text(msg);
}

function dataValidate(){
	if(document.getElementsByName("mobileCode").length==0){
		return false;
	}
	var value = document.getElementsByName("mobileCode")[0].value;
	if(!value || value==''){
		$("#errMsg").text("请输入验证码");
		return false;
	}
	return true;
}

/* function sendMobileCode(){
	$.ajax({
		url: Com_Parameter.ContextPath + "/sys/authentication/indentity/sysValidationCode.do?method=sendMobileValidationCode",
		type: "GET",
		//data: config,
		dataType:"json",
		async: false,
		success: function(result){
			//alert(result);
			
		}}
	);
}
 */

function sendMobileCode(type){
	var datas;
	$("#button_sendCode").attr("disabled","disabled");
	$("#button_sendCode").addClass("btn-disabled");
	$("input[name='mobileCode']").removeAttr("disabled");
	$.ajax({
		type : "POST",
		dataType : 'json',
		url : "${KMSS_Parameter_ContextPath}sys/authentication/indentity/sysValidationCode.do?method=sendMobileValidationCode"
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
		$("#button_sendCode").val(s + "秒后重新发送");
		t = setTimeout('times()', 1000);
		if (s <= 0) {
			s = 60;
			clearTimeout(t);
			$("#button_sendCode").val("获取验证码");
			$("#button_sendCode").attr("disabled", "");
			$("#button_sendCode").removeAttr("disabled");
			$("#button_sendCode").removeClass("btn-disabled");
		}
	}

	function returnFailMsg(flag) {
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
	}
</script>	
</head>
<body>
<c:if test="${ hasMobileNo == true}">
  <div class="content" style="height: 210px">
        <h3>短信验证码验证</h3>
        <!-- 短信验证码验证 Starts -->
        <div class="lui_verify_identity_container">
            <div class="lui_verify_identity_head">
                <p class="lui_verify_identity_desc">当前已验证手机号码：${mobileNoShow}</p>
                <div class="lui_verify_identity_form">
                  <label for="verify-phone">验证码：</label>
                  <input type="text" id="verify-phone" style="width: 140px;height:28px" disabled='disabled' name='mobileCode' validate="required maxLength(6)"
                        placeholder="请输入验证码">
                  <input type="button" class="com_bgcolor_d lui_verify_identity_form2" style="width:110px"
	                                      value="发送验证码"  id="button_sendCode" onclick="sendMobileCode(1);" />
                   <span style="padding-left: 5px;"><font color="red" size="3pt" id="msg_block">${errMsg}</font></span>
                </div>
                 <div ><font color="red" size="3pt" id="errMsg">${errMsg}</font></div>       
                        	
            </div>
            <br><br><br><br>
            <p class="lui_verify_identity_tipsStype">说明：输入手机验证码并点击确认则表示，您已同意并承认此行为为您本人意愿</p>
        </div>
  </div>
</c:if>

<c:if test="${ hasMobileNo == false}">
  <div class="error">
       <!-- 未绑定手机号码 Ends -->
        <h3>未绑定手机号码</h3>
        <!-- 未绑定手机号码 Starts -->
        <div class="lui_verify_identity_container">
            <!-- 操作提示 -->
            <div class="lui_verify_identity_msg">
                <div class="lui_verify_identity_icon_area">
                    <div class="lui_verify_identity_icon icon_warning"></div>
                </div>
                <div class="lui_verify_identity_text_area">抱歉，您尚无法执行此操作</div>
                <p class="lui_verify_identity_opr_area">您尚未绑定手机号码，请绑定手机号码后再完成此操作</p>
            </div>
        </div>
  </div>
  <script type="text/javascript">
  	parent.changeIdentityButton("我知道了");
  </script>
</c:if>
</body>
</html>
