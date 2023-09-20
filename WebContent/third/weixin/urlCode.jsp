<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="body">
	<br>
 		<center>
 		<table class="tb_normal" width=95%>
 			<tr>
 				<td colspan="3" class="tb_normal">
	 				<center>
	 					<b><bean:message key="url.code.path" bundle="third-weixin"/></b>
	 				</center>
 				</td>
 			</tr>
 			<tr>
 				<td width="45%">
 					<bean:message key="url.code.source" bundle="third-weixin"/><textarea name="source" style="width: 100%;height: 120px"></textarea>
 				</td>
 				<td width="5%" align="center">
 					<ui:button text="转换" onclick="urlCode();" style="vertical-align: top;"></ui:button>
 				</td>
 				<td width="45%">
 					<bean:message key="url.code.dest" bundle="third-weixin"/><textarea name="dest" style="width: 100%;height: 120px"></textarea>
 				</td>
 			</tr>
 			<tr>
 				<td colspan="3">
 					<bean:message key="url.code.note" bundle="third-weixin"/>
 				</td>
 			</tr>
 		</table>
 		</center>
 		<script>
	 		function urlCode(){
	 			var source = $("textarea[name='source']").val();
	 			if(source==null||source==""){
	 				alert('<bean:message key="url.code.no" bundle="third-weixin"/>');
	 				return false;
	 			}
	 			if(source.indexOf("http")!=0){
	 				alert('<bean:message key="url.code.no1" bundle="third-weixin"/>');
	 				return false;
	 			}
				var url = '<c:url value="/third/wx/weixinSynchroOrgCheck.do?method=urlCode&type=wx&source=" />'+source;
				$.ajax({
				   type: "POST",
				   url: url,
				   async:false,
				   dataType: "json",
				   success: function(data){
						if(data.status=="1"){
							$("textarea[name='dest']").val(data.msg);
						}else{
							alert(data.msg);
						}
				   }
				});
			}
 		</script>
	</template:replace>
</template:include>