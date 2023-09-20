<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.sys.organization.service.ISysOrgElementService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<template:include ref="mobile.simple" compatibleMode="true">
	<%
		String qCodeUrl = StringUtil.formatUrl("/hr/ratify/mobile/entry/invite_qr_code/index.html");
		request.setAttribute("qCodeUrl", qCodeUrl);
		String fdHierarchyId = UserUtil.getUser().getFdHierarchyId();
		String[] hierarchIds = fdHierarchyId.split("x");
		if(hierarchIds.length > 1){
			String fdId = hierarchIds[1];
			ISysOrgElementService sysOrgElementService = (ISysOrgElementService)SpringBeanUtil.getBean("sysOrgElementService");
			SysOrgElement orgElement  = (SysOrgElement)sysOrgElementService.findByPrimaryKey(fdId);
			request.setAttribute("orgName", orgElement.getFdName());	
		}else{
			request.setAttribute("orgName", UserUtil.getUser().getFdName());
		}
	%> 
	<template:replace name="title">
		<bean:message bundle="hr-ratify" key="mobile.entry.scan.qr" />
	</template:replace>
	
	<template:replace name="head">
			<link rel="stylesheet" type="text/css" href="../resource/css/scan.css"></link>
			<link rel="stylesheet" type="text/css" href="../resource/css/dabFont/dabFont.css"></link>
	</template:replace>
	
	<template:replace name="content">
	
	<%-- 	<h3>${orgName }</h3> --%>
<%-- 		<p><bean:message bundle="hr-ratify" key="mobile.entry.scan.qr.tip1" /></p>
	
		<p><bean:message bundle="hr-ratify" key="mobile.entry.scan.qr.tip2" /></p> --%>
		
	<div class="enter-scan">
        <div class="bottom-bg-img">
            <img src="../resource/images/element.png" alt="">
        </div>
        <h1 class="landray-title">
            	${orgName }
        </h1>
        <div class="landray-tips">
            	新增待入职员工后，才能提供入职扫码哦
        </div>
        <div class="scan-cont">
            <div class="erweima-image">
                	<div id="scanQR"></div>
            </div>
<!--             <div class="save-image-btn">
                 	保存图片
            </div> -->
        </div>
    </div>
	</template:replace>

</template:include>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}resource/js/jquery.js"></script>
<script type="text/javascript" src="../resource/js/jquery.qrcode.min.js"></script>
<script type="text/javascript" src="../resource/js/rem.js"></script>
<script type="text/javascript">
 	$(document).ready(function () {
 		//获取二维码
        $("#scanQR").qrcode({
        	render: "table", //table方式
        	width: 200, //宽度
        	height:200, //高度
        	text: '${qCodeUrl}'//任意内容
        });
 	});
	</script>