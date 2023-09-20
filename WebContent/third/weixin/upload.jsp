<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
		<html:form enctype="multipart/form-data" style="padding:5px;" action="/third/weixin/domain.do?method=upload" method="post" onsubmit="return check();">  
 		<!-- <input type="hidden" name="method" value="upload" /> -->
 		<br><br>
 		<center>
 		<table class="tb_noborder" width=85%>
 			<tr>
 				<td>
 					<table class="tb_normal" width=100%>
						<tr>
							<td class="td_normal_title" width="15%">文件上传</td>
							<td width="35%">
								<div style="float: left;"><input type="file" style="width: 350px;" name="file" accept="text/plain"/></div>
								<div style="float: right;"><input type="submit" value='<bean:message key="common.fileUpLoad.upload" />' style="width: 115px;" /></div>
							</td>
						</tr>
					</table>
 				</td>
 			</tr>
 			<tr>
 				<td>
 					<div style="color: red;">
			 			${ errorMessage }
			 		</div>
 				</td>
 			</tr>
 		</table>
 		</center>
		</html:form>
		<script>
			function check(){
				var fn = $("input[name='file']").val();
				if(fn==''){
					alert("请选择要上传的文件（txt）");
					return false;
				}else{
					return true;
				}
			}
		</script>
	</template:replace>
</template:include>