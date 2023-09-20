<%@ page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>

<%
	request.setAttribute("userId", UserUtil.getKMSSUser().getPerson().getFdId());
	request.setAttribute("userName", UserUtil.getKMSSUser().getUserName());
	request.setAttribute("_fdSex", "F".equals(UserUtil.getKMSSUser().getPerson().getFdSex())?"true":"false");
	request.setAttribute("mobileNo", UserUtil.getKMSSUser().getPerson().getFdMobileNo());
%>

<template:include ref="mobile.view" compatibleMode="true">
	<template:replace name="title">
		${ lfn:message('sys-organization:sysOrgPerson.changePerson.welcome') }
	</template:replace>
	
	<template:replace name="head">
		<link type="text/css" rel="Stylesheet" href="${LUI_ContextPath}/sys/common/changePwd/mobile/resource/person.css?s_cache=${MUI_Cache}" />
	</template:replace>
	
	<template:replace name="content">
		<div class="pwdbox">
			<div class="personInfoBox">
				<h1>${ lfn:message('sys-organization:sysOrgPerson.changePerson.step2') }</h1>
				<form id="change_person_form" action="${LUI_ContextPath}/sys/organization/sys_org_person/chgPersonInfo.do?method=updatePerson" method="post" onsubmit="return false;">
					<div class="lui_changepwd_panel person_info">
						<div class="content">
							<table>
								<tr class="tr-opt">
									<td class="td_title"><label><bean:message bundle="sys-organization" key="sysOrgPerson.fdName"/></label></td>
									<td><span>${userName}</span></td>
								</tr>
								<tr class="tr-opt">
									<td class="td_title"><label><bean:message bundle="sys-organization" key="sysOrgPerson.fdSex"/></label></td>
									<td>
										<xform:radio property="fdSex" mobile="true" mobileRenderType="normal" value="${_fdSex}" showStatus="edit">
											<xform:simpleDataSource value="false">${lfn:message('sys-organization:sysOrgPerson.fdSex.M') }</xform:simpleDataSource>
											<xform:simpleDataSource value="true">${lfn:message('sys-organization:sysOrgPerson.fdSex.F') }</xform:simpleDataSource>
										</xform:radio>
									</td>
								</tr>
								<tr class="tr-opt">
									<td class="td_title"><label><bean:message bundle="sys-organization" key="sysOrgPerson.fdMobileNo"/></label></td>
									<td>
										<xform:text property="mobilPhone" className="inputsgl" showStatus="edit" value="${mobileNo}" htmlElementProperties="placeholder='${ lfn:message('sys-organization:sysOrgPerson.mobilPhone.tip') }'"></xform:text>
									</td>
								</tr>
								<tr class="newPwdTip">
									<td></td>
									<td>
										<div class="info-words">
				                            <p class="textTip"></p>
										</div>
									</td>
								</tr>
							</table>
							<div class="btnW"><a class="btn_submit_1" id="btn_submit"><bean:message key="button.submit"/></a></div>
						</div>
					</div>
				</form>
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
							location.href = "${LUI_ContextPath}/third/pda/index.jsp";
						}else{
							isSubmit = false;
							setMobileErrorTip(result.message);
						}
					},
					error:function(result) {
						isSubmit = false;
						setMobileErrorTip(result.message);
					}
				});
			};

			$(function(){
				$("#btn_submit").click(onPersonSubmit);
				$("input[name=mobilPhone]").blur(checkMobile);
			});

			function setMobileErrorTip(desc){
				$('.info-words .textTip').html(desc);
				$('.newPwdTip').addClass("warning");
				$('.newPwdTip').prev().addClass("warning");
			}
			function clearMobileErrorTip(){
				$('.info-words .textTip').html("");
				$('.newPwdTip').removeClass("warning");
				$('.newPwdTip').prev().removeClass("warning");
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
					var fdId = "${userId}";
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