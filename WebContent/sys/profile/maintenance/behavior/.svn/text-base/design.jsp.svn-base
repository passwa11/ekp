<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.design" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="head">
		<style>
			#mainTable {width:900px;}
			#mainTable td {vertical-align: middle; line-height:30px;}
		</style>
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			function submitForm(){
				var value = $('input[name="file"]').val();
				if($.trim(value)==''){
					alert('<bean:message key="sys.profile.behavior.design.nofile" bundle="sys-profile-behavior" />');
					return;
				}
				document.forms[0].submit();
			}
	
			$(function(){
				menu_focus("1__design");
				<c:if test="${'true' == param.state}">
				alert('<bean:message key="sys.profile.behavior.design.success" bundle="sys-profile-behavior" />');
				</c:if>
				<c:if test="${'false' == param.state}">
				alert(unescape("${param.errorMsg}"));
				</c:if>
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.design" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <form action="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=uploadDesign" method="post" enctype="multipart/form-data">
					<center>
						<span style="color: red; font-weight: bold;"><c:out value="${message}" /></span>
						<table class="tb_normal" style="width:900px;" id="mainTable">
							<tr class="tr_normal_title">
								<td><bean:message key="sys.profile.behavior.design.info1" bundle="sys-profile-behavior" /></td>
							</tr>
							<tr>
								<td style="text-align: center;">
									<input type="file" name="file" style="padding:5px 0px;" accept="text/plain">
									<input type="button" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.submit') }" style="padding:5px 20px; cursor: pointer;" onclick="submitForm();">
								</td>
							</tr>
							<tr>
								<td style="">
									<bean:message key="sys.profile.behavior.design.info2" bundle="sys-profile-behavior" />
									<img src="${LUI_ContextPath}/sys/profile/maintenance/behavior/images/module.jpg">
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