<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple" spa="true">
	<template:replace name="head">
	<style>
		#stopOrgForm .orgname{
			font-size: 14px;
			color: #666666;
			padding-right:14px;
			width:20%;
		}
		#stopOrgForm .orgnamevalue{
			font-size: 14px;
			color: #333;
		}
		#stopOrgForm textarea{
			background: #FFFFFF;
			border: 1px solid #DDDDDD;
			border-radius: 2px;
		}
		.stopOrg{
			margin:20px 20px;
		}
		#stopOrgForm textarea{
			background: #FFFFFF;
			border: 1px solid #DDDDDD;
			border-radius: 2px;
		 	padding:10px 12px;
		}
		.stoporgbtn{
			background: #F0F0F0;
			border-radius: 10px;
			padding:2px 11px;
			cursor:pointer;
			height:20px;
			line-height:21px;
		}
		.stoporgbtn:hover i{
			display:inline-block;
			width:13px;
			height:13px;
			vertical-align: middle;
			background:url('../../resource/images/icon_cancel.png') no-repeat;
		}
		.orgnamevalue .stoporgbtn{
			float:left;
			margin:4px;
					
		}	
	</style>
	</template:replace>
    <template:replace name="body">
	<script type="text/javascript">
		seajs.use(['theme!form']);
		Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
		Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|data.js", null, "js");
	</script>    
    <form name="stopOrg">
		<div class="stopOrg">
			<table id="stopOrgForm">
				<tr style="line-height:40px;">
					<td style="width:20%;"><span class="orgname">${lfn:message("hr-organization:hrOrganizationElement.fdName")}</span></td>
					<td><span class="orgnamevalue"></span></td>
				</tr>
				<tr >
					<td>
					${lfn:message('hr-organization:hr.organization.info.stopOrg.memo')}
					</td>
					<td>
						<textarea id="hrorgDemo" validate="required" 
						placeholder="${lfn:message('hr-organization:hr.organization.info.tip.6')}" rows="8" cols="40"></textarea>
					</td>
				</tr>
			</table>
	</form>		
		</div>
		<script src="${LUI_ContextPath}/hr/organization/resource/js/tree/stopOrg.js"></script>
    </template:replace>
</template:include>