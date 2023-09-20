<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig" %>
<%
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet" href="../resource/css/lib/common.css" />
		<link rel="stylesheet" href="../resource/css/lib/form.css">
		<link rel="stylesheet" href="../resource/css/hr.css">
	</template:replace>
	<template:replace name="content" >
		<c:choose>
			<c:when test="${hrStaffEntryForm.fdStatus ne '2' }">
				<script type="text/javascript">Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js|dialog.js|form.js", null, "js");</script>
				<html:form action="/hr/staff/hr_staff_entry/hrStaffEntry.do" styleId="checkForm" styleClass="hr_staff_entry_form">
					<div class="hr_staff_entry_wrap">
						<html:hidden property="fdId"/>
						<html:hidden property="method_GET"/>
						<div class="lui_hr_tb_simple_wrap">
							<table class="tb_simple lui_hr_tb_simple">
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdName"/>
									</td>
									<td>
										${hrStaffEntryForm.fdName }
										<html:hidden property="fdName"/>
									</td>
								</tr>
									<!-- 是否开通账号 -->
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdIsOpenOrg"/>
									</td>
									<td>
										<ui:switch property="fdIsOpenOrg" onValueChange="showLinkTr(this)" checked="true"></ui:switch>
									</td>
								</tr>
								<!-- 是否关联组织架构账号 -->
								<tr id="fdIsLinkOrgTr">
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdIsLinkOrg"/>
									</td>
									<td>
										<ui:switch property="fdIsLinkOrg" onValueChange="showTr(this)" checked="false"></ui:switch>
									</td>
								</tr>
								<!-- 请选择系统账号 -->
								<tr id="fdOrgPersonTr" style="display: none;">
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdOrgPerson.title"/>
									</td>
									<td>
										<div _xform_type="address">
											<xform:address 
											propertyName="fdOrgPersonName" 
											propertyId="fdOrgPersonId" 
											showStatus="edit" 
											orgType="ORG_TYPE_PERSON"
											mulSelect="false"
											onValueChange="getPersonInfo"></xform:address>
											<span style="display: none;" class="txtstrong" id="isRequiredFlag">*</span>
										</div>
									</td>
								</tr>
								<!-- 账号 -->
								<tr class="fdStaffNoTr">
									<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.accountNumber"/></td>
									<td width="50%">
										<xform:text property="fdLoginName" showStatus="edit" required="true" subject="${ lfn:message('hr-staff:hrStaffPersonInfo.accountNumber') }" ></xform:text>
									</td>
								</tr>
								<!-- 密码-->
								<tr class="fdStaffNoTr">
									<td class="td_normal_title"><bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdPassword"/></td>
									<td width="50%">
										<xform:text property="fdNewPassword" showStatus="edit" required="true"  subject="${ lfn:message('hr-staff:hrStaffPersonInfo.fdPassword') }"></xform:text>
									</td>
								</tr>
								<!-- 工号 -->
								<tr>
									<td  class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdStaffNo"/>
									</td>
									<td>
										<xform:text property="fdStaffNo" showStatus="edit" validators="uniqueStaffNo" required="true" subject="${ lfn:message('hr-staff:hrStaffEntry.fdStaffNo') }"></xform:text>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdMobileNo"/>
									</td>
									<td>
										<xform:text property="fdMobileNo" showStatus="edit" validators="phoneNumber uniqueMobileNo" required="true"></xform:text>
										<input type="hidden" name="fdMobile" value="${hrStaffEntryForm.fdMobileNo}">
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdPlanEntryDept"/>
									</td>
									<td>
										<xform:address propertyName="fdPlanEntryDeptName" propertyId="fdPlanEntryDeptId" showStatus="edit" style="width:70%" required="true" orgType="ORG_TYPE_DEPT"></xform:address>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdOrgPosts"/>
									</td>
									<td> 
										<xform:address  mulSelect="true"  propertyName="fdOrgPostNames" propertyId="fdOrgPostIds" showStatus="edit" orgType="ORG_TYPE_POST" style="width:70%"></xform:address>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<bean:message bundle="hr-staff" key="hrStaffEntry.fdPlanEntryTime"/>
									</td>
									<td>
										<xform:datetime property="fdPlanEntryTime" showStatus="edit"></xform:datetime>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
								<!-- 	入职时间 -->
									<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdEntryTime"/>
									</td>
									<td>
										<xform:datetime property="fdEntryTime" showStatus="edit" dateTimeType="date"  required="true"  subject="${ lfn:message('hr-staff:hrStaffPersonInfo.fdEntryTime') }"></xform:datetime>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
									<!-- 员工状态 -->
										<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdStatus"/>
									</td>
									<td>
										<xform:select property="fdPersonStatus" showStatus="edit" showPleaseSelect="false">
											<xform:enumsDataSource enumsType="hrStaffPersonInfo_fdStatus"></xform:enumsDataSource>
										</xform:select>
									</td>
								</tr>
								<tr>
									<td class="td_normal_title">
										<!-- 工作性质 -->
										<bean:message bundle="hr-staff" key="hrStaffPersonInfo.fdNatureWork"/>
									</td>
									<td>
										<xform:select property="fdNatureWork" showStatus="edit">
											<xform:beanDataSource serviceBean="hrStaffPersonInfoSetNewService" selectBlock="fdName" whereBlock="fdType='fdNatureWork'" orderBy="fdOrder"></xform:beanDataSource>
										</xform:select>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<%-- <div class="lui_hr_footer_btnGroup">
						<ui:button text="${lfn:message('button.cancel') }" onclick="$dialog.hide(null);" styleClass="lui_toolbar_btn_gray"></ui:button>
						<ui:button text="${lfn:message('button.ok') }" onclick="clickOk();"></ui:button>
					</div> --%>
				</html:form>
			<script>
			
				var validation=$KMSSValidation();//校验框架
				var isLoginSpecialChar = <%=isLoginSpecialChar%>;
				var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
				<%  if("true".equals(isLoginSpecialChar)){%>
					errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
				<% } %>
			   	var LoginNameValidators = {
			   			'uniqueLoginName' : {
			   				error : "<bean:message key='sysOrgPerson.error.loginName.mustUnique' bundle='sys-organization' />",
			   				test : function (value) {
			   						var fdId = document.getElementsByName("fdId")[0].value;
			   						var result = loginNameCheckUnique("hrStaffPersonInfoService",value,fdId,"unique");
			   						if (!result) 
			   							return false;
			   						return true;
			   				      }
			   			},
			   			'invalidLoginName': {
			   				error : "<bean:message key='sysOrgPerson.error.newLoginNameSameOldName' bundle='sys-organization' />",
			   				test  : function(value) {
			   					    if (LoginNameValidators["lgName"] && (LoginNameValidators["lgName"]==value)){
			   						    return true;
			   					    }
			   					    LoginNameValidators["lgName"]=null;
			   						var fdId = document.getElementsByName("fdId")[0].value;
			   						var result = loginNameCheckUnique("hrStaffPersonInfoService",value,fdId,"invalid");
			   						console.log("invalidLoginName"+!result);
			   						if (!result){ 
			   							if(window.confirm("<bean:message key='sysOrgPerson.newLoginName.ConfirmMsg' bundle='sys-organization' />")){
			   								LoginNameValidators["lgName"]=value;
			   								return true;
			   							}else{
			   							  	return false;
			   							}
			   						}
			   						return true;	
			   			    }
			   			},			
			   			
			   			'normalLoginName':{
			   			
			   				error:errorMsg,
			   				test:function(value){
			   					
			   					var pattern;
			   					
			   					<% if("true".equals(isLoginSpecialChar)){%>
			   						pattern=new RegExp("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$");
			   					<% }else{ %>
			   						pattern=new RegExp("^[A-Za-z0-9_]+$");
			   					<% }%>
			   					console.log("normalLoginName "+pattern.test(value))
			   					if(pattern.test(value)){
			   						return true;
			   					}else{
			   						return false;
			   					}
			   				}
			   			}
			   	};
			   	validation.addValidators(LoginNameValidators);
			    //校验登录名是否与系统中失效的登录名一致
			   	function loginNameCheckUnique(bean, loginName,fdId,checkType) {
			   		var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=" 
			   				+ bean + "&loginName=" + loginName+"&fdId="+fdId+"&checkType="+checkType+"&date="+new Date());
			   		return _CheckUnique(url);
			   	}
				// 增加一个字符串的startsWith方法
				function startsWith(value, prefix) {
					return value.slice(0, prefix.length) === prefix;
				}
				
				// 校验手机号是否正确
				validation.addValidator(
					'phone',
					"<bean:message key='hrStaffPersonInfo.phone.err' bundle='hr-staff' />",
					function(v, e, o) {
						if (v == "") {
							return true;
						}
						// 国内手机号可以有+86，但是后面必须是11位数字
						// 国际手机号必须要以+区号开头，后面可以是6~11位数据
						if(startsWith(v, "+")) {
							if(startsWith(v, "+86")) {
								return /^(\+86)(\d{11})$/.test(v);
							} else {
								return /^(\+\d{1,5})(\d{6,11})$/.test(v);
							}
						} else {
							// 没有带+号开头，默认是国内手机号
							return /^(\d{11})$/.test(v);
						}
				});
				// 验证工号是是否唯一的
				var StaffNoValidators = {
						"uniqueStaffNo" : {
							error : "<bean:message key='hrStaffPersonInfo.staffNo.unique.err' bundle='hr-staff' />",
							test : function (value) {
								var fdId = '${JsParam.fdId}';
								var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffEntryService&staffNo="
										+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
								var result = _CheckUnique(url);
								console.log("工号"+!result);
								if (!result) 
									return false;
								return true;
						      }
						}
				};
				// 验证手机号是否已被注册
				var MobileNoValidators = {
						"uniqueMobileNo" : {
							error : "<bean:message key='sysOrgPerson.error.newMoblieNoSameOldName' bundle='sys-organization' />",
							test : function (value) {
								if(startsWith(value, "+86")) {
									// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
									value = value.slice(3, value.length)
								}
								if(startsWith(value, "+")) {
									value = value.replace("+", "x")
								}
								var fdId = document.getElementsByName("fdId")[0].value;
								var fdOrgPersonId = $("[name='fdOrgPersonId']").val();
								var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffEntryService&mobileNo="
										+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date()+"&isEntry=true"+"&fdOrgPersonId="+fdOrgPersonId);
								var result = _CheckUnique(url);

								if (!result) 
									return false;
								return true;
						      }
						}
				};
				validation.addValidators(MobileNoValidators);
				validation.addValidators(StaffNoValidators);
				// 检查是否唯一
				function _CheckUnique(url) {
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
				seajs.use(['lui/dialog', 'lui/jquery', 'lui/topic'],function(dialog,$,topic) {
				window.getPersonInfo = function(){
						var fdOrgPersonId = $("[name='fdOrgPersonId']").val();
						var fdMobileNo = $("[name='fdMobileNo']").val();
						//选了关联系统账号人员
						if(fdOrgPersonId){
								var url = '<c:url value="/hr/staff/hr_staff_entry/hrStaffEntry.do?method=getPersonInfo"/>';
								$.ajax({
									url :url,
									type: 'POST',
									dataType: 'json',
									data : {
										fdOrgPersonId : fdOrgPersonId
									},
									success : function(data){
										if(data.status ==1){
											if(fdMobileNo!= data.mobileNo){
												$("[name='fdMobileNo']").val(data.mobileNo);
											}else{
												validation.validate();
											}
										}
									}
								})
							}
						}
					var __isSubmit = false;
					window.clickOk=function(){

						if(__isSubmit){
							return;
						}else{
							if(validation.validate()){
									__isSubmit = true;
									submit();
							}
						}
					};
					window.onload = function(){
						$("input[name='fdLoginName']").attr("validate","required uniqueLoginName invalidLoginName normalLoginName");
						$("input[data-propertyname='fdLoginName']").attr("validate","required uniqueLoginName invalidLoginName normalLoginName");
					}
					window.showLinkTr = function(obj){
						var fdIsLinkOrgTr = $('#fdIsLinkOrgTr');
						var fdOrgPersonTr = $('#fdOrgPersonTr');
						var fdStaffNoTr = $('.fdStaffNoTr');
						var fdLoginName = $("input[name='fdLoginName']")[0];
						var fdNewPassword = $("input[name='fdNewPassword']")[0];
						if(obj.checked){
							fdIsLinkOrgTr.show();
							var fdIsLinkOrg = $('[name="fdIsLinkOrg"]').val();
							if(fdIsLinkOrg == 'true'){
								fdOrgPersonTr.show();
								fdStaffNoTr.hide();
							}else{
								fdOrgPersonTr.hide();
								fdStaffNoTr.show();
							}
							$("input[name='fdLoginName']").attr("validate","required uniqueLoginName invalidLoginName normalLoginName");
							$("input[data-propertyname='fdLoginName']").attr("validate","required uniqueLoginName invalidLoginName normalLoginName");
							validation.resetElementsValidate(fdNewPassword);
						}else{
							fdIsLinkOrgTr.hide();
							fdOrgPersonTr.hide();
							$form("fdOrgPersonName").val('');
							$form("fdOrgPersonId").val('');
							fdStaffNoTr.hide();
							$form("fdOrgPersonId").val("");
							$form("fdOrgPersonName").val("");
							$("input[name='fdLoginName']").val("");
							$("input[name='fdNewPassword']").val("");
							$("input[name='fdLoginName']").attr("validate","");
							$("input[data-propertyname='fdLoginName']").attr("validate","");
							validation.removeElements(fdNewPassword,'required');
						}
					};
					<!--是否关联组织架构账号开关-->
					window.showTr = function(obj){
						var fdOrgPersonTr = $('#fdOrgPersonTr');
						var fdStaffNoTr = $('.fdStaffNoTr');
						var fdLoginName = $("input[name='fdLoginName']")[0];
						var fdNewPassword = $("input[name='fdNewPassword']")[0];
						if(obj.checked){//开启
							fdOrgPersonTr.show();
							fdStaffNoTr.hide();
							$("input[name='fdOrgPersonName']").attr("validate","required");
							$("input[data-propertyname='fdOrgPersonName']").attr("validate","required");
							$("input[name='fdLoginName']").attr("validate","");
							$("input[data-propertyname='fdLoginName']").attr("validate","");
							validation.removeElements(fdNewPassword,'required');
							$("input[name='fdOrgPersonName']").attr("validate","required");
							$('#isRequiredFlag').show();
						}else{//关闭
							fdOrgPersonTr.hide();
							$form("fdOrgPersonName").val('');
							$form("fdOrgPersonId").val('');
							fdStaffNoTr.show();
							$("input[name='fdOrgPersonName']").attr("validate","");
							$("input[data-propertyname='fdOrgPersonName']").attr("validate","");
							$("input[name='fdLoginName']").attr("validate","required uniqueLoginName invalidLoginName normalLoginName");
							$("input[data-propertyname='fdLoginName']").attr("validate","required uniqueLoginName invalidLoginName normalLoginName");
							validation.resetElementsValidate(fdNewPassword);
							$("input[name='fdOrgPersonName']").attr("validate","");
							$("[name='fdMobileNo']").val('${hrStaffEntryForm.fdMobileNo}');
							$('#isRequiredFlag').hide();
						}
					};
					function submit(){
						var url = '<c:url value="/hr/staff/hr_staff_entry/hrStaffEntry.do?method=check"/>';
						window.del_load = dialog.loading();
						$.ajax({
							url : url,
							type : 'POST',
							data : $("#checkForm").serialize(),
							dataType : 'json',
							error : function(data) {
								__isSubmit = false;
								if(window.del_load != null) {
									window.del_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: function(data) {
								__isSubmit = false;
								if(window.del_load != null){
									window.del_load.hide(); 
									topic.publish("list.refresh");
								}
								dialog.result(data);
								setTimeout(function(){
									$dialog.hide('success');
								},1500);
							}
					   });
					}
				});
			</script>
			</c:when>
			<c:otherwise>
				<center>
			        <div class="lui-destroy" style="margin-top:150px;">
				    	<span>该员工已确认到岗</span>
				    </div>
			    </center>
			</c:otherwise>
		</c:choose>
	</template:replace>
</template:include>