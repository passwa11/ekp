<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="mobile.list">
	<template:replace name="title">
		邀请成员
	</template:replace>
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/organization/mobile/css/person/qrcode.css">
    </template:replace>
	<template:replace name="content">
		<c:if test="${empty qrCodeerror}">
			<div class="muiSysOrgPersonQrcodeContainer">
				<div class="muiSysOrgPersonQrcodeTitle">
					<div class="muiSysOrgPersonQrcodeLabel">邀请你加入</div>
					<div class="muiSysOrgPersonQrcodeDept">${sysOrgElementName}</div>
				</div>
				<div class="muiSysOrgPersonQrcodeMessage">钉钉扫一扫，加入我们的组织</div>
				<div class="muiSysOrgPersonQrcodeBox">
					<div class="muiSysOrgPersonQrcodeContent">
						<img src="${LUI_ContextPath}/sys/ui/sys_ui_qrcode/sysUiQrcode.do?method=getQrcode&contents=${dingdingUrl}"></img>
					</div>
					<div class="muiSysOrgPersonQrcodeBoxFlash fontmuis muis-org-flash"></div>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty qrCodeerror}">
			<div class="muiSysOrgPersonQrcodeContainer">
				<div class="muiSysOrgPersonQrcodeTitle">
					<div class="muiSysOrgPersonQrcodeDept">${qrCodeerror}</div>
				</div>
			</div>
		</c:if>
	</template:replace>
</template:include>
