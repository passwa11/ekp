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
<link charset="utf-8" rel="stylesheet" href="<%=request.getContextPath()%>/sys/authentication/identity/css/custom.css">
 <script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
<script type="text/javascript">

function buildSubmitData(){
	var data = {};
	data.password = document.getElementsByName("password")[0].value;
	return data;
}

function validateResult( msg){
	$("#errMsg").text(msg);
}

function dataValidate(){
	if(document.getElementsByName("password").length==0){
		return false;
	}
	var value = document.getElementsByName("password")[0].value;
	if(!value || value==''){
		$("#errMsg").text("请输入您的身份验证密码");
		return false;
	}
	return true;
}

</script>	
</head>
<body>
<c:if test="${ hasSettingePwd == true}">
  <div class="content">
       <h3>密码验证</h3>
        <!-- 密码验证 Starts -->
        <div class="lui_verify_identity_container">
            <div class="lui_verify_identity_head">
                <div class="lui_verify_identity_form"><label for="verify-id">身份校验密码:</label><input type="password" id="verify-id" name="password"
                        placeholder="请输入身份校验密码" style="width:150px;height: 25px;margin-left: 10px"></div>
            </div>
           <div ><font color="red" size="3pt" id="errMsg">${errMsg}</font></div>     
            <p class="lui_verify_identity_tips">说明：输入身份校验密码并点击确认则表示，您已同意并承认此行为为您本人意愿</p>
        </div>
  </div>
 </c:if>
<c:if test="${ hasSettingePwd == false}">  
  <div class="error">
        <h3>未设置验证密码</h3>
        <div class="lui_verify_identity_container">
            <!-- 操作提示 -->
            <div class="lui_verify_identity_msg">
                <div class="lui_verify_identity_icon_area">
                    <div class="lui_verify_identity_icon icon_warning"></div>
                </div>
                <div class="lui_verify_identity_text_area">抱歉，您尚无法执行此操作</div>
                <p class="lui_verify_identity_opr_area">您尚未设置身份验证密码，请在设置验证密码后再完成此操作</p>
            </div>
        </div>
  </div>
  <script type="text/javascript">
  	parent.changeIdentityButton("我知道了");
  </script>
</c:if>
</body>
</html>
