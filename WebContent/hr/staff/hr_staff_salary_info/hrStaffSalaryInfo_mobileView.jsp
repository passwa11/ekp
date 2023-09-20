<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld"
	prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" sidebar="no">
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
				padding-top: 4rem;
			}
			
			.lui-login-wrapper .lui-form-title {
				font-size: 22px;
				color: #333333;
				letter-spacing: 0;
				margin-bottom: 36px;
			}
			
			.lui-login-wrapper .lui-login-form {
				padding: 0 2rem;
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field {
				margin-bottom: 2rem;	
			}
			
			.lui-login-wrapper .lui-login-form .lui-login-form-field > * {
				width: 100%;
				height: 4.4rem;
				box-sizing: border-box;
				border-radius: 4px;
				outline: none;
				font-size: 1.5rem;
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
				border-color: #3174e3;
				background-color: #3174e3;
			}
			
			.lui-payroll-wrapper {
				padding: 1.2rem;
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
				margin-bottom: 1.5rem;
				border-bottom: 1px dashed #d5d5d5;
				border-top: 1rem solid #4285f4;
				border-top-left-radius: 8px;
				border-top-right-radius: 8px;
				position: relative;
			}
			
			.lui-payroll-panel-heading-title {
				margin: 0;
				padding: 2.5rem 1rem;
				text-align: center;
				color: #4285f4;
				font-size: 2.6rem;
				font-weight: normal;
				display: block;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
			}
			
			.lui-payroll-list {
				margin: 0;
				padding: 0 3rem;
				list-style: none;
			}
			
			.lui-payroll-list>li {
				padding: 1rem 2rem;
				border-bottom: 1px solid #d5d5d5;
			}
			
			.lui-payroll-list>li:after {
				content: '';
				display: table;
				visibility: hidden;
				clear: both;
			}
			
			.lui-payroll-list>li .title {
				text-align: left;
				display: inline-block;
			}
			
			.lui-payroll-list>li .content.warning {
				color: #ea4335;
			}
			
			.lui-payroll-depict {
				margin: 0;
				padding: 1rem 3rem 2rem 5rem;
			}
			
			.lui-payroll-marked {
				padding: 2rem 0;
				text-align: center;
			}
			
			.lui-payroll-marked>.title {
				margin: 0 0 .8rem;
				color: #333;
				font-size: 1.8rem;
				font-weight: normal;
			}
			
			.lui-payroll-marked>.txt {
				margin: 0;
				color: #666;
				font-size: 1.3rem;
			}
			
			.lui-payroll-panel-dot {
				position: absolute;
				width: 2rem;
				height: 2rem;
				border-radius: 1rem;
				box-sizing: border-box;
				bottom: -1rem;
				border: 1px solid #d5d5d5;
				background-color: #f7f7f7;
			}
			
			.lui-payroll-panel-dot::after {
				content: '';
				position: absolute;
				background-color: #f7f7f7;
			    top: -1px;
			    bottom: -1px;
			}
			
			.lui-payroll-panel-dot[data-pos="left"] {
				left: -1rem;
			}
			.lui-payroll-panel-dot[data-pos="left"]::after {
				left: -1px;
			    right: 1rem;
			}
			.lui-payroll-panel-dot[data-pos="right"] {
				right: -1rem;
			}
			
			.lui-payroll-panel-dot[data-pos="right"]::after {
				left: 1rem;
			    right: -1px;
			}
			
			#tabbar.invalid,
			#tabbar.invalid #btnSubmit {
				background-color: #E5E5E5 !important;
				color: #666666 !important;
			}
			
			/* 工资单相关 Ends
					   ========================================================================== */
		</style>
	</template:replace>

	<%--标题--%>
	<template:replace name="title">

	</template:replace>

	<%--内容区--%>
	<template:replace name="content">
		<div id="scrollView" data-dojo-type="mui/view/DocScrollableView">
			
			<!-- 登陆验证 Starts -->
			<div class="lui-login-wrapper" id="loginFormWrapper">
				<div class="lui-form-title"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.validate.person'/></div>
				<div class="lui-login-form">
					<div class="lui-login-form-field">
						<input type="password" placeholder="<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.input.password'/>" name="fdPassword"/>
					</div>
					<div class="lui-login-form-field">
						<button id="btnLogin" type="button"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.submit'/></button>
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
					<h4 class="title"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.thanks'/></h4>
					<p class="txt"><bean:message bundle='hr-staff' key='hrStaffSalaryInfo.notice'/></p>
				</div>
			</div>
			<!-- 工资单区域 Ends -->
			<ul data-dojo-type="mui/tabbar/TabBar" id="tabbar" fixed="bottom" data-dojo-props='fill:"grid"' style="display: none">
				<li id="btnSubmit" data-dojo-type="mui/tabbar/TabBarButton" onclick="window.handleSubmit();"
					data-dojo-props="colSize:2">
					确认
				</li>
			</ul>
		</div>
		
		<script>
		
			require(['dojo/query', 'dojo/dom-style', 'dojo/html', 'dijit/registry', 'dojo/request', 'dojo/on', 'mui/dialog/Tip', 
			         'dojo/ready', 'dojo/_base/array', 'dojo/dom-construct', 'dojo/dom-class'], 
					function(query, domStyle, html, registry, request, on, Tip, ready, array, domCtr, domClass) {

				ready(function() {
						
					var loginFormWrapper = query('#loginFormWrapper')[0];
					var payRollWrapper = query('#payRollWrapper')[0];
					var tabbar = query('#tabbar')[0];
					var btnSubmit = query('#btnSubmit')[0];
					
					var pwdIpt = query('[name="fdPassword"]')[0];
					var loginBtn = query('#btnLogin')[0];
					
					var salaryTitle = query('#salaryTitle')[0];
					var remark = query('#remark')[0];
					
					//验证按钮逻辑
					on(loginBtn, 'click', function() {
						var fdPassword = pwdIpt.value;
						if(!fdPassword) {
							Tip.fail({
								text: "<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.input.password.error'/>"
							});
							return;
						}
						
						var salaryContent = query('#salaryContent')[0];
						request('<c:url value="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=salaryLogin&fdId=${param.fdId}"/>', {
							data: {
								fdPassword: fdPassword	
							},
							method: 'post',
							handleAs: 'json'
						}).then(function(res) {
							if(res.isLogin){
								for(var i in res) {
									if("isLogin"==i){
									}else if("salaryTitle"==i){
										html.set(salaryTitle, res[i]);
									}else if("备注"==i){
										html.set(remark, "备注:"+res[i]);
									}else if("isDone"==i){
										if(res[i]){
											setBtnDisabled();
										}
										domStyle.set(tabbar, 'display', 'block');
										registry.byNode(tabbar).resize();											
											//$('#btnSubmit').hide();
									}else{
										domCtr.create('li', {
											innerHTML: '<span class="title">' + i + '：</span> <span class="content" id="otherPay">' + res[i] + '</span>'
										}, salaryContent);
									}
								}
								domStyle.set(loginFormWrapper, 'display', 'none');
								domStyle.set(payRollWrapper, 'display', 'block');
							}else{
								alert("<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.validate.error'/>");
							}
						});
					});
					
					window.setBtnDisabled = function() {
						domClass.add(tabbar, 'invalid');
						html.set(btnSubmit, "<bean:message bundle='hr-staff' key='hrStaffSalaryInfo.cofirmed'/>");
					}
					
					//确认按钮逻辑
					window.handleSubmit = function() {
						
						if(domClass.contains(tabbar, 'invalid')) {
							return;
						}
						
						request('<c:url value="/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=setSalaryTodoDone&fdId=${param.fdId}"/>', {
							method: 'get',
							handleAs: 'json'
						}).then(function(res) {

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
						});
					}
				});
				
			});
		
		</script>
		
	</template:replace>
</template:include>