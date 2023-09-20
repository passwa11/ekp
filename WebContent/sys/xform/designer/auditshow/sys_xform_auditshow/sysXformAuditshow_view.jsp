<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.view">
<template:replace name="title">
<bean:message bundle="sys-xform-base" key="table.sysXformAuditshow"/>
</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
<script>
$(document).ready(function(){
	var h = window.screen.height;
	$("#contentDiv").css("height",(0.5*h)+"px");
	$("#previewDiv").css("height",(0.5*h)+"px");
	var domElement = document.getElementById('previewDiv');
	var area = document.getElementById('contentDiv');
	var map={"msg":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.msg2"/>",
			"person":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.person2"/>",
			"dept":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.dept2"/>",
			"post":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.post2"/>",
			"width":'',
			"handwrite":'<img height="30px" width="40px" src="../../designer/style/img/auditshow/handwrite.png">',
			"attachment":'<img src="../../designer/style/img/auditshow/attachment.png">',
			"time":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.time2"/>",
			"date":"<bean:message bundle="sys-xform-base" key="sysXformAuditshow.date2"/>",
			"picPath":'<img src="../../designer/style/img/auditshow/defaultSig2.png">',
			"operation":'<label><bean:message bundle="sys-xform-base" key="sysXformAuditshow.operation2"/></label>'};
	var text = area.innerText.replace(/\\$\{(\w+)\}/g,function($1,$2){
		return map[$2];
	});
	domElement.innerHTML = text;
});

function confirmDelete(msg){
	var del = confirm("<bean:message bundle="sys-xform-base" key="sysXformAuditshow.isEnabled_msg"/>");
	return del;
}
</script>
			<kmss:auth requestURL="/sys/xform/auditshow/sys_xform_auditshow/sysXformAuditshow.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${ lfn:message('button.edit') }" onclick="Com_OpenWindow('sysXformAuditshow.do?method=edit&fdId=${param.fdId}','_self');">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/sys/xform/auditshow/sys_xform_auditshow/sysXformAuditshow.do?method=disable&fdId=${param.fdId}" requestMethod="GET">
				<c:choose>
					<c:when test="${ sysXformAuditshowForm.fdIsenabled == '1' }">
						<ui:button text="${lfn:message('sys-xform-base:sysXformAuditshow.disable')}" onclick="if(!confirmDelete())return;Com_OpenWindow('sysXformAuditshow.do?method=disable&fdId=${param.fdId}','_self');">
						</ui:button>
					</c:when>
					<c:when test="${ sysXformAuditshowForm.fdIsenabled == '0' }">	
						<ui:button text="${lfn:message('sys-xform-base:sysXformAuditshow.enabled')}" onclick="if(!confirmDelete())return;Com_OpenWindow('sysXformAuditshow.do?method=disable&fdId=${param.fdId}','_self');">
						</ui:button>
					</c:when>
				</c:choose>
			</kmss:auth>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
<br/>	
<p class="txttitle"><bean:message bundle="sys-xform-base" key="table.sysXformAuditshow"/></p>

<center>
<table class="tb_normal" width=70%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-base" key="sysXformAuditshow.fdName"/>
		</td><td width="85%" colspan="2">
			<xform:text property="fdName" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-base" key="sysXformAuditshow.fdOrder"/>
		</td><td width="85%" colspan="2">
			<xform:text property="fdOrder" style="width:85%"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-xform-base" key="sysXformAuditshow.fdContent"/>
		</td><td width="50%">
			<div id="contentDiv" style="width:100%;word-break:break-all;font-size:20px;overflow:auto">
				<xform:textarea property="fdContent" />
			</div>
		</td>
		<td width="35%">
			<div id="previewDiv" style="width:100%;word-break:break-all;font-size:20px;overflow:auto">
			</div>
		</td>
	</tr>
</table>

</center>
	</template:replace>
</template:include>