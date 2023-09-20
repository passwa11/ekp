<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld"
	prefix="portal"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
	seajs.use([ 'theme!list', 'theme!portal', 'theme!zone' ]);
</script>
<style type="text/css">
	.lui_zone_contact {
    height: 22px;
    display: none;
}
</style>
<title><template:block name="title" /></title>

<template:block name="head" />
</head>
<body class="lui_list_body">
	<c:set var="frameWidth" scope="page" value="${(empty param.width) ? '90%' : (param.width)}" />
	<c:if test="${empty param.j_iframe }">
		<portal:header var-width="${frameWidth}" />
	</c:if>
	
	<table style="width:90%;margin:0px auto;" class="lui_main_container">
		<tr>
			<td valign="top">
				<div class="lui_list_body_frame">
					<div id="queryListView" style="width: 100%">
						<template:block name="path" />
					<!-- 员工黄页 个人信息 banner Starts -->
					<div class="lui_sys_zone_page_iframe">
						<div class="sys_zone_page_info_wrap">
						<div class="sys-zone-card">
							<div class="sys_zone_page_info_content" style="width:${ frameWidth }; min-width:1142px;">
								<!--头像-->
								<div class="sys_zone_page_info_photo">
									<template:block name="photo" />
																	
								</div>
								<!--简介-->
								<div class="sys_zone_page_info_brief">
									<template:block name="infoCard" />
								<!-- 信息墙 -->
									
									<template:block name="infoWall" />
	
									<!--<template:block name="name" /> -->
								
									<!-- 个人信息列表 -->
									<!-- <div class="sys_zone_page_infolist">
										<div class="field">
											<template:block name="field" />
										</div>
										<template:block name="infolist" />
									</div> 
	
									
								</div>
										<!-- 个性签名 -->
									
										<template:block name="signature" /> 
	

	
	
								<!--个人信息墙 中间 Ends-->
							</div>
							</div>
						</div>
						
					 </div>
						<!--员工个人信息墙 Ends-->
						<!--导航栏 Starts-->
						<div class="lui_zone_nav_box_new com_bgcolor_d">
							<div class="lui_zone_nav_bar_new " id="showNavBarUl" style=" min-width:980px;max-width:${ fdPageMaxWidth }; margin:0px auto;">
								
							    <template:block name="navBar" />
							   
							</div>
						</div>
						<!--导航栏 Ends-->
						<!--主体区域 Starts-->
						<div class="lui_zone_mbody" style="width:${ frameWidth }; min-width:980px;max-width:${ fdPageMaxWidth }; margin:0px auto;">
							<!--左侧区域 Starts-->
							<div class="lui_zone_mbody_l">
								<div class='lui_zone_mbody_l_inner'>
							    	<template:block name="bodyL" />
							    </div>
							</div>
							<!--左侧区域 Ends-->
							<!--右侧区域 Starts-->
							<div class="lui_zone_mbody_r">
								<div class="lui_zone_slide_wrap">
								    
								</div>
							</div>
							<!--右侧区域 Ends-->
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
	<portal:footer var-width="${frameWidth}" />
	<ui:top id="top"></ui:top>
	<c:import url="/sys/praise/sysPraiseInfo_common_btn.jsp"></c:import>
	<script>
		LUI.ready(function(){
			seajs.use('lui/qrcode',function(qrcode){
				qrcode.QrcodeToTop();
			});

		});

		domain.register("fireEvent",function(data){
			if("resize" == data.name  ) {
				var irameId = data.target || "iframe_body";
				document.getElementById(irameId).style.height = data.data.height + "px";
			}
		});
	</script>
	<template:block name="bodyR" />
</body>
</html>
