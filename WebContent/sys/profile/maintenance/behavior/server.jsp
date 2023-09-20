<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/maintenance/behavior/template/behavior.jsp">
	<template:replace name="title">
		<bean:message key="sys.profile.behavior.server" bundle="sys-profile-behavior" />
	</template:replace>
	<template:replace name="head">
		<style>
			#serverConfig {width:900px;}
			#serverConfig td {text-align: center; vertical-align: middle;}
			#serverConfig .input {border: 0px; border-bottom: 1px solid #b4b4b4;}
			#serverConfig select, #serverConfig .input {height: 20px; color: #1b83d8;  padding-left:4px; font-size:12px;  font-family: Microsoft YaHei, Geneva, "sans-serif", SimSun;}
		</style>
	</template:replace>
	<template:replace name="script">
		<script type="text/javascript">
			function addRow(){
				var tb = $("#serverConfig")[0];
				var length = tb.rows.length;
				var tr = tb.insertRow(length-1);
				$(tr.insertCell(-1)).html('<input type="radio" name="defaultServer"'+(length==3?' checked':'')+'>');
				$(tr.insertCell(-1)).html('<input class="input" name="name" value="" style="width:90%">');
				$(tr.insertCell(-1)).html('<input class="input" name="key" value="" style="width:90%">');
				$(tr.insertCell(-1)).html('<select name="type"><option value="java">java</option><option value="domino">domino</option><option value="other"><bean:message key="sys.profile.behavior.other" bundle="sys-profile-behavior" /></option></select>');
				$(tr.insertCell(-1)).html('<a href="#" onclick="deleteRow(this);return false;"><bean:message key="sys.profile.behavior.delete" bundle="sys-profile-behavior" /></a>');
			}
			function deleteRow(dom){
				for(var tr=dom; tr!=null && tr.tagName!='TR'; tr=tr.parentNode){}
				if(tr!=null){
					var tb = $("#serverConfig")[0];
					for(var i=0; i<tb.rows.length; i++){
						if(tb.rows[i]==tr){
							tb.deleteRow(i);
							return;
						}
					}
				}
			}
			function submitForm(){
				var input = $('input[name="key"]');
				if(input.length==0){
					alert('<bean:message key="sys.profile.behavior.server.error1" bundle="sys-profile-behavior" />');
					return;
				}
				if(checkNull(input, '<bean:message key="sys.profile.behavior.server.url" bundle="sys-profile-behavior" />')){
					return;
				}
				if(checkNull('input[name="name"]', '<bean:message key="sys.profile.behavior.server.name" bundle="sys-profile-behavior" />')){
					return;
				}
				var defaultServer = $('input[name="defaultServer"]');
				var found = false;
				for(var i=0; i<defaultServer.length; i++){
					if(defaultServer[i].checked){
						defaultServer[i].value = input[i].value;
						found = true;
						break;
					}
				}
				if(!found){
					alert('<bean:message key="sys.profile.behavior.server.error2" bundle="sys-profile-behavior" />');
					return;
				}
				document.forms[0].submit();
			}
			function checkNull(field, name){
				field = $(field);
				for(var i=0; i<field.length; i++){
					if($.trim(field[i].value)==''){
						alert(name+'<bean:message key="sys.profile.behavior.server.error3" bundle="sys-profile-behavior" />');
						return true;
					}
				}
				return false;
			}
	
			$(function(){
				menu_focus("1__server");
			});
		</script>
	</template:replace>
	<template:replace name="body">
		<div class="beh-card-wrap beh-col-12">
          <div class="beh-card">
            <div class="beh-card-heading">
              <h4><bean:message key="sys.profile.behavior.server" bundle="sys-profile-behavior" /></h4>
            </div>
            <div class="beh-card-body">
	            <form action="${LUI_ContextPath}/sys/profile/maintenance/behavior/behaviorSetting.do?method=server" method="post">
					<center>
						<span style="color: red; font-weight: bold;"><c:out value="${message}" /></span>
						<table class="tb_normal" style="width:900px;" id="serverConfig">
							<tr class="tr_normal_title">
								<td colspan="5"><bean:message key="sys.profile.behavior.server.configure" bundle="sys-profile-behavior" /></td>
							</tr>
							<tr>
								<td style="width:50px;"><bean:message key="sys.profile.behavior.server.default" bundle="sys-profile-behavior" /></td>
								<td style="width:150px;"><bean:message key="sys.profile.behavior.server.name" bundle="sys-profile-behavior" /><br><bean:message key="sys.profile.behavior.server.name.info" bundle="sys-profile-behavior" /></td>
								<td style=""><bean:message key="sys.profile.behavior.server.url" bundle="sys-profile-behavior" /><br><bean:message key="sys.profile.behavior.server.url.info" bundle="sys-profile-behavior" /></td>
								<td style="width:100px;"><bean:message key="sys.profile.behavior.server.type" bundle="sys-profile-behavior" /></td>
								<td style="width:100px;"><a href="#" onclick="addRow();return false;">${ lfn:message('sys-profile-behavior:sys.profile.behavior.add') }</a></td>
							</tr>
							<c:forEach items="${servers}" var="server">
								<tr>
									<td><input type="radio" name="defaultServer" ${server.defaultServer ? "checked":"" }></td>
									<td><input class="input" name="name" value="<c:out value="${server.name}"/>" style="width:90%"></td>
									<td><input class="input" name="key" value="<c:out value="${server.key}"/>" style="width:90%"></td>
									<td>
										<select name="type" value="<c:out value="${server.type}"/>">
											<option value="java" ${server.type=="java" ? "selected":"" }>java</option>
											<option value="domino" ${server.type=="domino" ? "selected":"" }>domino</option>
											<option value="other" ${server.type=="other" ? "selected":"" }>${ lfn:message('sys-profile-behavior:sys.profile.behavior.other') }</option>
										</select>
									</td>
									<td><a href="#" onclick="deleteRow(this);return false;">${ lfn:message('sys-profile-behavior:sys.profile.behavior.delete') }</a></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="5" style="">
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