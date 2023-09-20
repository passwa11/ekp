<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
	<template:replace name="content">
		<h2 align="center" style="margin: 10px 0">
			<span class="profile_config_title"><bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept"/></span>
		</h2>
		
		<center>
			<form action="#">
				<table class="tb_normal" width="95%">
					<tr>
						<td width="15%" class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.fromPerson"/>
						</td>
						<td width="85%">
							<xform:address propertyId="fdIds" propertyName="fdNames" mulSelect="true" showStatus="edit" 
								orgType="${HtmlParam.orgType}" style="width:95%" textarea="true" isExternal="false">
							</xform:address>
							<span class="txtstrong">*</span>
						</td>
					</tr>
					<tr>
						<td width="15%" class="td_normal_title">
							<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.toDept"/>
						</td>
						<td width="85%">
							<xform:address propertyId="deptId" propertyName="deptName" showStatus="edit" 
								orgType="ORG_TYPE_ORGORDEPT" style="width:95%" onValueChange="deptChange" isExternal="false">
							</xform:address>
							<span class="txtstrong">*</span>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<c:choose>
								<c:when test="${'ORG_TYPE_PERSON' eq HtmlParam.orgType}">
								<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.person.desc"/>
								</c:when>
								<c:when test="${'ORG_TYPE_POST' eq HtmlParam.orgType}">
								<bean:message bundle="sys-organization" key="sysOrgPerson.quickChangeDept.post.desc"/>
								</c:when>
							</c:choose>
						</td>
					</tr>
				</table>
			</form>
		</center>
		
	 	<script type="text/javascript">
		 	window.onload = function(){
	 			var fdIds ;
	 			setTimeout(function(){
	 				var values="";
	 				var __win;
	 				if(window.opener){
	 					__win = window.opener;
	 				}else if($dialog && $dialog.config.opener){
	 					__win = $dialog.config.opener;
	 				}else if(window.parent){
	 					__win = window.parent;
	 				}
	 				if(!__win){
	 					values = '${JsParam.fdIds}';
	 				} else {
	 					var	select = __win.document.getElementsByName("List_Selected");
	 					for(var i=0;i<select.length;i++) {
	 						if(select[i].checked){
	 							values+=select[i].value;
	 							values+=";";
	 						}
	 					}
	 				}
	 				document.getElementsByName("fdIds")[0].value=values; 
	 				fdIds=values; 
	 				$.ajax({
	 					url : '<c:url value="/sys/organization/sys_org_person/sysOrgPerson.do"/>?method=changeDeptEdit',
	 					type : 'POST',
	 					data : $.param({"fdIds":fdIds}, true),
	 					dataType : 'json',
	 					success: function(data) {
	 						if(data) {
	 							Address_QuickSelection("fdIds","fdNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,data,null,null,"","false");
	 						} 
	 					}
	 			   });
	 			},1);
	 		};
	 		function deptChange(data) {
	 			$dialog.deptId = data[0];
	 		}
	 	</script>
	</template:replace>
</template:include>
