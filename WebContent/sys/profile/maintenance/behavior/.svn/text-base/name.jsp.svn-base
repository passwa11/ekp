<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.named" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			var data = {
					module : '<c:out value="${param.module}" />',
					path : '<c:out value="${param.path}" />',
					method : '<c:out value="${param.method}" />',
					name : '<c:out value="${param.name}" />'
				};
				function changeSelect(value){
					$('input[name="type"]').each(function(){
						for(var tr = $('input[name="'+this.value+'"]')[0]; tr!=null && tr.tagName!='TR'; tr=tr.parentNode){
						}
						if(this.checked){
							$(tr).show();
						}else{
							$(tr).hide();
						}
					});
				}
				function load(){
					if(data.path!=''){
						var model = data.path;
						var index = model.lastIndexOf('/');
						if(index>-1){
							model = model.substring(index+1);
						}
						index = model.lastIndexOf('.');
						if(index>-1){
							model = model.substring(0, index);
						}
						if(model.length>1){
							model = model.substring(0,1).toUpperCase()+model.substring(1);
							$('input[name="model"]').val(model);
						}
					}
					data.path = data.module + data.path + (data.method==''?'':'?method='+data.method);
					$('input[name="name"]').val(data.name);
					$('input[name="module"]').val(data.module);
					$('input[name="path"]').val(data.path);
					$('input[name="_method"]').val(data.method);
					changeSelect('module');
				}
	
			$(function(){
				load();
				menu_focus("1__name");
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.named" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <form action="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=name" method="post">
					<table class="tb_normal" style="width:800px;">
						<tr>
							<td style="width:100px;"><bean:message key="sys.profile.behavior.named.type" bundle="sys-profile-behavior" /></td>
							<td>
								<label><input name="type" type="radio" onclick="changeSelect(value);" value="module" checked><bean:message key="sys.profile.behavior.named.module" bundle="sys-profile-behavior" /></label>
								<label><input name="type" type="radio" onclick="changeSelect(value);" value="model"><bean:message key="sys.profile.behavior.named.model" bundle="sys-profile-behavior" /></label>
								<label><input name="type" type="radio" onclick="changeSelect(value);" value="path"><bean:message key="sys.profile.behavior.named.path" bundle="sys-profile-behavior" /></label>
								<label><input name="type" type="radio" onclick="changeSelect(value);" value="_method"><bean:message key="sys.profile.behavior.named.method" bundle="sys-profile-behavior" /></label>
							</td>
						</tr>
						<tr>
							<td><bean:message key="sys.profile.behavior.named.name" bundle="sys-profile-behavior" /></td>
							<td><input type="text" name="name" style="width:95%; line-height:22px;"></td>
						</tr>
						<tr>
							<td><bean:message key="sys.profile.behavior.named.module" bundle="sys-profile-behavior" /></td>
							<td><input type="text" name="module" style="width:95%; line-height:22px;"></td>
						</tr>
						<tr>
							<td><bean:message key="sys.profile.behavior.named.model" bundle="sys-profile-behavior" /></td>
							<td><input type="text" name="model" style="width:95%; line-height:22px;"></td>
						</tr>
						<tr>
							<td><bean:message key="sys.profile.behavior.named.path" bundle="sys-profile-behavior" /></td>
							<td><input type="text" name="path" style="width:95%; line-height:22px;"></td>
						</tr>
						<tr>
							<td><bean:message key="sys.profile.behavior.named.method" bundle="sys-profile-behavior" /></td>
							<td><input type="text" name="_method" style="width:95%; line-height:22px;"></td>
						</tr>
						<tr>
							<td colspan="2" style="text-align: center;">
								<input type="submit" value="${ lfn:message('sys-profile-behavior:sys.profile.behavior.submit') }" style="padding:8px 30px;">
							</td>
						</tr>
					</table>
				</form>
            </div>
          </div>
        </div>
	</template:replace>
</template:include>