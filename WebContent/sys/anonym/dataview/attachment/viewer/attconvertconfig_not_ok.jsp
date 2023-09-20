<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
<template:replace name="body">
<link rel="stylesheet" type="text/css" href="${fullContextPath}/sys/attachment/sys_att_main/resource/css/nodata.css">
			<div class="lui_imissive_noData_L_container">
		        <div class="lui_imissive_noData_L_wrapper">
		            <div class="lui_imissive_noData_img"></div>
		            <p>${not empty msg?msg:'目前暂无附件，请到发文稿纸上传相关附件或从附件列表选择附件预览哦！'}</p>
		        </div>
		    </div>
	</template:replace>
</template:include>	


