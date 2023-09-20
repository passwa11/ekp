<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<style type="text/css">
			body{ background-color: #f4fcfe; }
			.personInfoBox{width:800px; margin: 0 auto; text-align: center;}
			.personInfoBox .weui_switch {
				display: inline-block;
				position: relative;
			}

			.personInfoBox table {
			    text-align: left;
			    width: 100%;
			    margin: 0 auto;
			}
			.personInfoBox table tr td {
				padding: 8px 5px;
				height: 42px;
				vertical-align: middle;
				line-height: 20px;
			}
			.personInfoBox table tr td.td_title {
		    width: 25%;
		    font-size: 14px;
		    text-align: right;
		    padding-right: 10px;
			}
			.personInfoBox table tr td > .inputsgl{
			  border: 1px solid #e8e8e8;
		    background-color: #fff;
		    line-height: 20px;
		    padding: 10px;
		    position: relative;
			}
			.personInfoBox table tr td > .txtstrong{
				color: #fa6835;
				font-size: 20px;
				margin-left: 5px;
		    line-height: 1;
		    position: relative;
		    top: 6px;
			}

			.personInfoBox .btnW{
				margin-top: 20px;
				padding:10px 0;
				text-align: center;
			}
			.personInfoBox .btnW a {
				display: inline-block;
				cursor: pointer;
				margin: 0px 10px;
				padding: 10px 15px;
				min-width: 100px;
				text-align: center;
				line-height: 20px;
				font-size: 14px;
				color: #20b3ff;
				border-radius: 5px;
				border: 1px solid #20b3ff;
				background-color: transparent;
				transition: all .3s ease-in-out;
			}
			.personInfoBox .btnW a:hover{
				color: #fff;
				background-color: #20b3ff;
			}
			.personInfoBox .btnW a.btn_disable {
				color: #333;
				border-color: #ccd1d9;
				background-color: #ccd1d9;
				opacity: .65;
				cursor: not-allowed;
			}
			.personInfoBox .btnW .btn_cancel {
				color: #333;
				border-color: #b0b0b0;
				background-color: #b0b0b0;
				opacity: .65;
			}
			.personInfoBox .error{
				padding-left: 28px;
				color: #f00;
				font-size: 14px;
				margin-bottom: 15px;
				text-align: center;
				display:none;
			}
			.personInfoBox .mobileerror{
				margin-left: 4px;
				font-size: 0;
				*white-space: -1px;
				vertical-align: middle;
				display: inline-block;
			}
			.personInfoBox .icon {
				width: 20px;
		    height: 20px;
		    display: inline-block;
			}
			.personInfoBox .textTip{
				color: #fa6835;
			  font-size: 12px;
			  padding-left: 4px;
			  word-break: break-all;
			  word-wrap: break-word;
			}
			.personInfoBox .redIcon { background-image: url(../../common/changePwd/images/changePwd-icon-warning.png);}
			.personInfoBox .icon span {
				display: inline-block;
		    width: 20px;
		    height: 20px;
		    background-repeat: no-repeat;
		    background-position: 50%;
		    vertical-align: middle;
		    position: relative;
		    top: -4px;
			}
			.on_fucus{display:none;}
		</style>
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/common/changePwd/css/person.css?v=1.0"/>
		<script type="text/javascript" src="${KMSS_Parameter_ContextPath }resource/js/jquery.js"></script>
	</template:replace>
	<template:replace name="title">
		<bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step2"/>
	</template:replace>
	<template:replace name="body">
	  <div class="lui-changePwd-header">
	    <h1 class="lui-changePwd-header-title"><img src="${KMSS_Parameter_ContextPath }sys/common/changePwd/images/changePwd-header-heading.png" alt=""></h1>
	  </div>
		<div class="lui-changePwd-content">
		 	<!-- 进度栏 Starts -->
		    <ul class="lui-changePwd-step-nav">
		      <li class="finish" ><span class="num">1</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step1"/></li>
		      <li class="active"><span class="num">2</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step2"/></li>
		      <li><span class="num">3</span><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.step3"/></li>
		    </ul>
		    <!-- 进度栏 Ends -->
		    <div class="lui-changePwd-form-box">
		    	<div class="personInfoBox" style="">
					<form id="change_person_form" action="<%=request.getContextPath()%>/sys/organization/sys_org_person/chgPersonInfo.do?method=updatePerson" method="post" onsubmit="return false;">
						<table>
							<tr id="err_tip" style="display:none;">
								<td colspan="2"><div class="error"></div></td>
							</tr>
							<tr>
								<td class="td_title"><label><bean:message bundle="sys-organization" key="sysOrgPerson.fdName"/></label></td>
								<td><span><%=UserUtil.getKMSSUser().getUserName() %></span></td>
							</tr>
							<tr>
								<td class="td_title"><label><bean:message bundle="sys-organization" key="sysOrgPerson.fdSex"/></label></td>
								<td>
									<%
										String sex = "F".equals(UserUtil.getKMSSUser().getPerson().getFdSex())?"true":"false";
										pageContext.setAttribute("_fdSex",sex);
									%>
								<ui:switch property="fdSex" showText="false" checked="${_fdSex}"></ui:switch>
								</td>
							</tr>
							<tr>
								<td class="td_title"><label><bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo"/></label></td>
								<td>
									<xform:text style="width:550px;" property="mobilPhone" className="inputsgl" showStatus="edit" value="<%=UserUtil.getKMSSUser().getPerson().getFdMobileNo() %>" htmlElementProperties="placeholder='${ lfn:message('sys-organization:sysOrgPerson.moblieNo.tips') }'"></xform:text>
									<div class="mobileerror">
										<span class="icon"><span class=""></span></span>
										<span class="textTip"></span>
									</div>

								</td>
							</tr>
						</table>

						<div class="btnW"><a class="btn_submit" id="btn_submit"><bean:message bundle="sys-organization" key="sysOrgPerson.changePerson.btn1"/></a></div>
					</form>
				</div>
		    </div>

		</div>

		<script type="text/javascript">
			var isSubmit = false;
			function onPersonSubmit(){
				if(isSubmit)return;
				if(!checkMobile()){
					$("input[name=mobilPhone]").focus();
					return;
				}
				isSubmit =true;
				var form = $("#change_person_form");
				$.ajax({
					type:"POST",
					url:form.attr("action"),
					data:form.serialize(),
					dataType:'json',
					success:function(result) {
						if(result.msg=='true'){
							<%
								String path = request.getContextPath();
								String basePath = "";
								if(80 == request.getServerPort() || 443 == request.getServerPort())
									basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
								else
									basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
							%>
							//todo:url后续优化
							location.href = "<%=basePath%>"+"sys/organization/sys_org_person/chgPersonInfo.do?method=changePerson&setting=person_image";
						}else{
							isSubmit = false;
							$('.personInfoBox .error').show().html(result.message);
						}
					},
					error:function(result) {
						isSubmit = false;
						$('#err_tip').show();
						$('#err_tip .error').html(result.message);
					}
				});
			};

			$(function(){
				$("#btn_submit").click(onPersonSubmit);
				$("input[name=mobilPhone]").blur(checkMobile);
			});

			function setMobileErrorTip(desc){
				$('.mobileerror .icon span').addClass('redIcon');
				$('.mobileerror .textTip').html(desc);
			}
			function clearMobileErrorTip(){
				$('.mobileerror .icon span').removeClass('redIcon');
				$('.mobileerror .textTip').html("");
			}
			function checkMobile(){
				var value = $("input[name=mobilPhone]").val();
				if(!value){
					clearMobileErrorTip();
					return true;
				}
				if(!isMobile(value)){
					setMobileErrorTip("${ lfn:message('sys-organization:sysOrgPerson.changePerson.fdMobile.desc') }");
					return false;
				}else{
					if(startsWith(value, "+86")) {
						// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
						value = value.slice(3, value.length)
					}
					if(startsWith(value, "+")) {
						value = value.replace("+", "x")
					}
					var fdId = "<%=UserUtil.getKMSSUser().getPerson().getFdId()%>";
					var result = mobileNoCheckUnique("sysOrgPersonService",value,fdId,"unique");
					if (!result){
						setMobileErrorTip("${ lfn:message('sys-organization:sysOrgPerson.error.newMoblieNoSameOldName') }");
						return false;
					}
					clearMobileErrorTip();
					return true;
				}
			}
			
			function mobileNoCheckUnique(bean, mobileNo,fdId,checkType) {
				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
						+ bean + "&mobileNo=" + mobileNo+"&fdId="+fdId+"&checkType="+checkType+"&date="+new Date());
				var xmlHttpRequest;
				if (window.XMLHttpRequest) { // Non-IE browsers
					xmlHttpRequest = new XMLHttpRequest();
				} else if (window.ActiveXObject) { // IE
					try {
						xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP");
					} catch (othermicrosoft) {
						try {
							xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
						} catch (failed) {
							xmlHttpRequest = false;
						}
					}
				}
				if (xmlHttpRequest) {
					xmlHttpRequest.open("GET", url, false);
					xmlHttpRequest.send();
					var result = xmlHttpRequest.responseText.replace(/\s/g, "").replace(/;/g, "\n");
					if (result != "") {
						return false;
					}
				}
				return true;
			}
			function isMobile(value){
				// 国内手机号可以有+86，但是后面必须是11位数字
				// 国际手机号必须要以+区号开头，后面可以是6~11位数据
				if(startsWith(value, "+")) {
					if(startsWith(value, "+86")) {
						return /^(\+86)(\d{11})$/.test(value);
					} else {
						return /^(\+\d{1,5})(\d{6,11})$/.test(value);
					}
				} else {
					// 没有带+号开头，默认是国内手机号
					return /^(\d{11})$/.test(value);
				}
			}
			// 增加一个字符串的startsWith方法
			function startsWith(value, prefix) {
				return value.slice(0, prefix.length) === prefix;
			}
		</script>
	</template:replace>
</template:include>
