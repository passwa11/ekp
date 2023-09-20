<%@ page language="java" contentType="text/html; charset=UTF-8"
 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		个人云证书校验
	</template:replace>
	<template:replace name="head">
		<link charset="utf-8" rel="stylesheet" href="<%=request.getContextPath()%>/elec/core/authentication/mobile/css/custom.css">
		<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery.js"></script>
		<script type="text/javascript">
		//用户是否设置有对应的验证信息
		function isHasSetting(){
			return true;
		}
		
		function buildSubmitData(){
			var data = {};
			data.fdCertId = document.getElementsByName("fdCertId")[0].value;
			data.pin = document.getElementById("verify-pin").value;
			return data;
		}

		function validateResult( msg){
			$("#msg_block").text(msg);
		}

		function dataValidate(){
			var value = document.getElementsByName("fdCertId")[0].value;
			//alert("name:"+value);
			if(!value || value==''){
				$("#msg_block").text("请选择一个证书进行验证");
				return false;
			}
			value = document.getElementById("verify-pin").value;
			if(!value || value==''){
				$("#msg_block").text("请输入PIN码");
				return false;
			}
			return true;
		}
		
		</script>
	</template:replace>
	<template:replace name="content">
		<div class="identity_mobile">
		    <div class="muiVerify-container">
				<h3 class="muiVerify-title">个人证书校验</h3>
				<div class="muiVerify-head">
					选择一个证书：<br/>
					<input type="hidden" name="fdCertId" value="" disabled="true" />
					<div id="_xform_fdCertId" _xform_type="dialog">
						<div data-dojo-type="mui/form/CommonDialog"
							data-dojo-props="idField:'fdCertId',
												nameField:'fdCertName',
												isMul:false,
												modelName:'com.landray.kmss.elec.ca.model.ElecCaInfo',
												dataURL:'/elec/ca/elec_ca_info/elecCaInfo.do?method=importListData&c.eq.fdModelId=${KMSS_Parameter_CurrentUserId}&c.eq.fdModelName=com.landray.kmss.sys.organization.model.SysOrgPerson&forward=importListDataMobile',
												subject:'',
												required:'',
												curIds:'',
												curNames:'',
												displayProp:'fdSerialNumber',
												afterSelect:''">
						</div>
					</div>
					<br/>
					请输入证书PIN码：
					<div class="muiVerify-form">
						<input type="password" id="verify-pin" placeholder="">
					</div>
					<p class="muiVerify-error" id="msg_block"></p>
				</div>
				<p class="muiVerify-tips">说明：输入验证码并点击确认则表示，您已同意并承认此行为为您本人意愿</p>
			</div>
		</div>
	</template:replace>
</template:include>
