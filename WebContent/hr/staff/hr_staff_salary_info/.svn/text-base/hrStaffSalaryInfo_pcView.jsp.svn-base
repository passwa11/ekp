<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<template:replace name="head">
		<style>
			/* 工资单相关 Starts
					   ========================================================================== */
			body {
				margin: 0;
				padding: 0;
			}
			
			.lui-login-wrapper {
				text-align: center;
				padding-top: 172px;
				padding-bottom: 172px;
			}
			
			.lui-login-wrapper .lui-login-title {
				font-size: 22px;
				color: #333333;
				letter-spacing: 0;
				margin-bottom: 36px;
			}
			
			.lui-login-wrapper .lui-login-form {
				margin: 0 auto;
				width: 335px;
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field {
				margin-bottom: 20px;	
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field > * {
				width: 100%;
				height: 44px;
				box-sizing: border-box;
				border-radius: 4px;
				outline: none;
				font-size: 15px;
				letter-spacing: -0.36px;
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field input {
				border: 1px solid #E5E5E5;
				padding: 0 8px;
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field input:focus {
				border-color: #4285F4;
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field button {
				cursor: pointer;
				border: 1px solid #4285F4;
				background-color: #4285F4;
				color: white;
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field button:active {
				background: #3174e3;
				border-color: #3174e3;
			}
			
			.lui-payroll-wrapper {
				margin: 0 auto;
				padding: 16px 0;
				width: 410px;
				line-height: 1.42857143;
			}
			
			.lui-payroll-panel {
				border-radius: 8px;
				color: #333;
				border-radius: 8px;
				border: 1px solid #d5d5d5;
				background-color: #fff;
			}
			
			.lui-payroll-panel-heading {
				margin-top: -1px;
				margin-bottom: 15px;
				border-bottom: 1px dashed #d5d5d5;
				border-top: 10px solid #4285f4;
				border-top-left-radius: 8px;
				border-top-right-radius: 8px;
				position: relative;
			}
			
			.lui-payroll-panel-heading-title {
				margin: 0;
				padding: 25px 10px;
				text-align: center;
				color: #4285f4;
				font-size: 26px;
				font-weight: normal;
				display: block;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}
			
			.lui-payroll-list {
				margin: 0;
				padding: 0 30px;
				list-style: none;
			}
			
			.lui-payroll-list>li {
				padding: 10px 20px;
				border-bottom: 1px solid #d5d5d5;
			}
			
			.lui-payroll-list>li:after {
				content: '';
				display: table;
				visibility: hidden;
				clear: both;
			}
			
			.lui-payroll-list>li .title {
				min-width: 85px;
				text-align: left;
				display: inline-block;
			}
			
			.lui-payroll-list>li .content.warning {
				color: #ea4335;
			}
			
			.lui-payroll-depict {
				margin: 0;
				padding: 10px 30px 20px 50px;
			}
			
			.lui-payroll-marked {
				padding: 20px 0;
				text-align: center;
			}
			
			.lui-payroll-marked>.title {
				margin: 0 0 8px;
				color: #333;
				font-size: 18px;
				font-weight: normal;
			}
			
			.lui-payroll-marked>.txt {
				margin: 0;
				color: #666;
				font-size: 13px;
			}
			
			.lui-payroll-panel-dot {
				position: absolute;
				width: 20px;
				height: 20px;
				border-radius: 10px;
				box-sizing: border-box;
				bottom: -10px;
				border: 1px solid #d5d5d5;
				background-color: white;
			}
			
			.lui-payroll-panel-dot::after {
				content: '';
				position: absolute;
				background-color: white;
			    top: -1px;
			    bottom: -1px;
			}
			
			.lui-payroll-panel-dot[data-pos="left"] {
				left: -10px;
			}
			.lui-payroll-panel-dot[data-pos="left"]::after {
				left: -1px;
			    right: 10px;
			}
			.lui-payroll-panel-dot[data-pos="right"] {
				right: -10px;
			}
			
			.lui-payroll-panel-dot[data-pos="right"]::after {
				left: 10px;
			    right: -1px;
			}
			
			#btnSubmit {
				width: 300px;
				height: 38px;
				line-height: 38px;
				background: #4285F4;
				border: 1px solid #4285F4;
				border-radius: 4px;
				font-family: PingFangSC-Regular;
				font-size: 14px;
				color: #FFFFFF;
				letter-spacing: -0.34px;
				text-align: center;
				display: inline-block;
				cursor: pointer;
			}
			
			#btnSubmit.invalid {
				background-color: #E5E5E5 !important;
				border-color: #E5E5E5 !important;
				color: #666666 !important;
			}
			
			#btnSubmit:active {
				background: #3174e3;
				border-color: #3174e3;
			}
				
			
			/* 工资单相关 Ends
					   ========================================================================== */
		</style>
	</template:replace>

	<%--标题--%>
	<template:replace name="title">

	</template:replace>

	<%--操作栏--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow()"></ui:button>
		</ui:toolbar>
	</template:replace>

	<%--路径--%>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId">  
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('hr-staff:hrStaffSalaryInfo.salary') }" href="" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>

	<%--内容区--%>
	<template:replace name="content">
	
		<!-- 登陆验证 Starts -->
		<div class="lui-login-wrapper" id="loginFormWrapper">
			<div class="lui-login-title"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.validate.person'/></div>
			<div class="lui-login-form" id="loginForm">
				<div class="lui-login-form-field">
					<input type="password" placeholder="<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.input.password'/>" name="fdPassword"/>
				</div>
				<div class="lui-login-form-field">
					<button type="button" id="btnLogin"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.submit'/></button>
				</div>
			</div>		
		</div>
		<!-- 登陆验证 Ends -->
	
		<!-- 工资单区域 Starts -->
		<div class="lui-payroll-wrapper" id="payRollWrapper" style="display: none;">
			<div class="lui-payroll-panel">
				<div class="lui-payroll-panel-heading">
					<h2 class="lui-payroll-panel-heading-title" id="salaryTitle"></h2>
					<div class="lui-payroll-panel-dot" data-pos="left"></div>
					<div class="lui-payroll-panel-dot" data-pos="right"></div>
				</div>
				<ul class="lui-payroll-list" id="salaryContent">

				</ul>
				<p class="lui-payroll-depict" id="remark"></p>
			</div>
			<div class="lui-payroll-marked">
				<div id="btnSubmit" style="margin-bottom: 20px;"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.cofirm'/></div>
				<h4 class="title"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.thanks'/></h4>
				<p class="txt"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.notice'/></p>
			</div>
		</div>
		<!-- 工资单区域 Ends -->
		
		<script>
			
			seajs.use(['lui/jquery'], function($) {
				
				//登录
				$('#btnLogin').click(function() {

					var fdPassword = $('[name="fdPassword"]').val();
					if(!fdPassword) {
						alert("<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.input.password.error'/>");
						return;
					}
					
					//登录请求逻辑
					$.post('<c:url value="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=salaryLogin&fdId=${param.fdId}"/>', {
						fdPassword: fdPassword
					},function(res) {
						//验证成功
						if(res.isLogin){

							$.each(res, function(i, item) {
								//循环获取数据
								if("isLogin"==i){
								}else if("salaryTitle"==i){
									$('#salaryTitle').text(item);
								}else if("备注"==i){
									$('#remark').text("备注:"+item);
								}else if("isDone"==i){
									if(item)
										setBtnDisabled();
								}else{
									var htm = '<li><span class="title">'+ i +':</span> <span class="content" >'+ item +'</span></li>';
									$('#salaryContent').append(htm);
								}
							});
							
							$('#payRollWrapper').show();
							$('#loginFormWrapper').hide();
						}else{
							alert("<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.validate.error'/>");
						}
					},'json')
				});
				
				window.setBtnDisabled = function() {
					$('#btnSubmit').addClass('invalid');
					$('#btnSubmit').text("<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.cofirmed'/>");
				}
				
				//工资单确认按钮
				$('#btnSubmit').click(function() {
					
					if($('#btnSubmit').hasClass('invalid'))
						return;
					
					$.get('<c:url value="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=setSalaryTodoDone&fdId=${param.fdId}"/>',function(res) {
						//验证成功
						if(res.isSet){
							setBtnDisabled();
							//window.location.href = '<c:url value="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=success"/>';
							
						}else{
							if(res.errorCode==1)
								alert("<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.not.self.salary'/>");
							
							else if(res.errorCode==2)
								alert("<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.todoDone.exception'/>");
						}
					},'json')
				});
			});
		
		</script>
	</template:replace>
</template:include>