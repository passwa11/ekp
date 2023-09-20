<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<script type="text/javascript" src="<c:url value="/resource/js/jquery.js"/>"></script>
		<script>
		seajs.use(['theme!form']);
		Com_IncludeFile("security.js",null,"js");
		</script>
		<style type="text/css">
.tb_normal>tbody>tr{border:none}
.tb_normal>tbody>tr>td{border:none}
</style>
	</template:replace>
   <template:replace name="body">
   	<script type="text/javascript">
	   	Com_IncludeFile("validation.js|plugin.js|validation.jsp ");
		window.onload = function(){
	   		$("#password").focus();
	   	}
	</script>
	
   	<%--新建标签--%>
   	<div style="display: block; width: 100%;">
   		<table class="tb_normal" width=90% id="calendar.integrate.ecalendar" style="border: none;">
			<tr>
			<td colspan="2">
				<font style="font-weight:bold;" color="red">
				<bean:message
														bundle="sys-zone" key="sysZonePersonInfo.password.validate.tip" />
														</font>
			</td>
			</tr>
			<tr>
				<td class="td_normal_title" width="40px;" style="border: none;"><bean:message
														bundle="sys-zone" key="sysZonePersonInfo.password" /></td>
				<td>
					<input type="password" id="password" name="password" validate="required validatePassword"></input>
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input type="button" id="button" class="btnopt" value="<bean:message
														bundle="sys-zone" key="sysZonePersonInfo.password.confirm" />" onclick="validate();">
				</td>
				
			</tr>
			
		</table> 
	</div>
	<script>
		
		var result = false;
			
		$("#password").on("blur",function(event){
			if(event.relatedTarget == $("#button")[0]){
				validate();
			}
		})
			
		function validate(){
			$("#password").blur();
			if(result==true)
				window.$dialog.hide("true");
		}
		   	
		var $KMSSValidation = $KMSSValidation();
		$KMSSValidation.addValidator(
				'validatePassword',
				'<bean:message bundle="sys-zone" key="sysZonePersonInfo.password.error" />',
				function(v, e, o) {
					var password = v;
					password=desEncrypt(password);
					$.ajax({
						type: 'POST',
						url: '<c:url value="/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=validatePassword" />',
						dataType: 'json',
						data: {
							password: password
						},
						async: false,
						success: function(data) {
							if(data==true){
							    result = true;
							}else if(data==false){
							    result = false;
							}else{
							    result = false;
							}
						},
						error: function(data){
						    seajs.use('lui/dialog',function(dialog){
						        dialog.alert(data);
						    });
						}
					});
					if(result == true){
						return true;
					}else{
						return false;
					}
				});
	</script>
   </template:replace>
</template:include>
