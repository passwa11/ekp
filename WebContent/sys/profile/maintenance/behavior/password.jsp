<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.password" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="head">
		<style>
			#_password {width:900px;}
			#_password td {text-align: center; vertical-align: middle;}
			#_password .input {border: 0px; border-bottom: 1px solid #b4b4b4;}
			#_password select, #_password .input {height: 20px; color: #1b83d8;  padding-left:4px; font-size:12px;  font-family: Microsoft YaHei, Geneva, "sans-serif", SimSun;}
		</style>
	</template:replace>
	<template:replace name="script">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
		<script type="text/javascript">
			Com_IncludeFile("security.js");
			function submitForm(){
			    var __oldPwd = document.forms[0].oldPwd.value;
			    var __newPwd = document.forms[0].newPwd.value;
			    var __reNewPwd = document.forms[0].reNewPwd.value;
			    
			    if($.trim(__oldPwd)=='' || $.trim(__newPwd)=='' || $.trim(__reNewPwd)=='') {
			    	alert('<bean:message key="sys.profile.behavior.password.err1" bundle="sys-profile-behavior" />');
			    	return;
			    }
			    
			    if($.trim(__newPwd) != $.trim(__reNewPwd)) {
			    	alert('<bean:message key="sys.profile.behavior.password.err2" bundle="sys-profile-behavior" />');
			    	return;
			    }
			    
				// 加密处理
			    document.forms[0].oldPwd.value = desEncrypt(document.forms[0].oldPwd.value);
			    document.forms[0].newPwd.value = desEncrypt(document.forms[0].newPwd.value);
			    document.forms[0].reNewPwd.value = desEncrypt(document.forms[0].reNewPwd.value);
			    
				$.ajax({
					url : '${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=changePassword',
					dataType : 'json',
					data : $("form").serialize(),
					success : function(result){
						if(result){
							if(result.state) {
								alert('<bean:message key="sys.profile.behavior.password.success" bundle="sys-profile-behavior" />');
							}else{
								alert(result.errorMsg);
							}
						} else {
							alert('<bean:message key="sys.profile.behavior.password.fail" bundle="sys-profile-behavior" />');
						}
						document.forms[0].reset();
					}
				});
			}
	
			$(function(){
				menu_focus("1__password");
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.password" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	           <form action="" method="post">
					<center>
						<span style="color: red; font-weight: bold;"><c:out value="${message}" /></span>
						<table class="tb_normal" style="width:80%;" id="_password">
							<tr>
								<td style="width:20%;"><bean:message key="sys.profile.behavior.password.oldPwd" bundle="sys-profile-behavior" /></td>
								<td><input type="password" class="input" name="oldPwd" style="width:90%"></td>
							</tr>
							<tr>
								<td style="width:20%;"><bean:message key="sys.profile.behavior.password.newPwd" bundle="sys-profile-behavior" /></td>
								<td><input type="password" class="input" name="newPwd" style="width:90%"></td>
							</tr>
							<tr>
								<td style="width:20%;"><bean:message key="sys.profile.behavior.password.reNewPwd" bundle="sys-profile-behavior" /></td>
								<td><input type="password" class="input" name="reNewPwd" style="width:90%"></td>
							</tr>
							<tr>
								<td colspan="2" style="">
									<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.submit') }" style="padding:5px 20px; cursor: pointer;" onclick="submitForm();">
								</td>
							</tr>
						</table>
					</center>
				</form>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>