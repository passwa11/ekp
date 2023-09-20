<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<%@page import="com.landray.kmss.sys.organization.model.SysOrganizationConfig" %>
<%
	SysOrganizationConfig sysOrganizationConfig = new SysOrganizationConfig();
	String isLoginSpecialChar = sysOrganizationConfig.getIsLoginSpecialChar();
%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-ratify" key="hrRatifyEntry.addOrgPerson"/>
	</template:replace>
	<template:replace name="head">
        <style type="text/css">
        	.lui_form_body {
        		background-color:#fff !important;
        	}
            .lui-destroy {
				padding:10px;
				width: 184px;
				height: 55px;
				line-height: 55px;
				text-align: center;
				color: #f56b6b;
				font-size: 28px;
				font-weight: normal;
				font-family: "Microsoft Yahei";
				text-transform: uppercase;
				background-image: url('./icon-haswrite.png');
				background-repeat: no-repeat;
				background-position: 0 0;
				background-size: 100% auto;
				display: block;
				white-space: nowrap;
				overflow: hidden;
				text-overflow: ellipsis;
				transform: rotate(-15deg);
				-webkit-transform: rotate(-15deg);
				-moz-transform: rotate(-15deg);
				-o-transform: rotate(-15deg);
			}	
        </style>
    </template:replace>
	<template:replace name="content">
		<html:form action="/hr/ratify/hr_ratify_entry_dr/hrRatifyEntryDR.do">
			<c:choose>
				<c:when test="${empty hrRatifyEntryDRForm.fdHasWrite or hrRatifyEntryDRForm.fdHasWrite eq 'null' }">
					<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
						<div data-dojo-type="mui/panel/AccordionPanel" class="editPanel">
							<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'账号写入',icon:'mui-ul'">
								<table class="muiSimple headTb" cellpadding="0" cellspacing="0" >
									<html:hidden property="fdId"/>
									<html:hidden property="fdEntryId"/>
									<html:hidden property="mobile" value="mobile"/>
									<tr>
									    <td class="muiTitle" >
									   		<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdLoginName"/>
										</td>
									    <td>
								    		<xform:text property="fdLoginName" showStatus="edit" required="true" validators="uniqueLoginName invalidLoginName normalLoginName" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdLoginName') }" mobile="true" />
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
											<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdPassword"/>
										</td>
										<td>
											<xform:text property="fdPassword" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdPassword') }" mobile="true" />
										</td>
									</tr>
									<tr>
										<td class="muiTitle" >
											<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEntryDept"/>
										</td>
										<td>
										    <xform:address isLoadDataDict="false" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryDept')}" propertyId="fdEntryDeptId" propertyName="fdEntryDeptName" orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT' mobile="true" />
										</td>
									</tr>
									<tr>
										<td class="muiTitle" >
											<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEntryPosts"/>
										</td>
										<td class="muiTitle" >
										 	<xform:address isLoadDataDict="false" showStatus="edit" required="true" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryPosts')}" propertyId="fdEntryPostIds" propertyName="fdEntryPostNames" orgType='ORG_TYPE_POST' mobile="true" mulSelect="false" />
										</td>
									</tr>
									<tr>
										<td class="muiTitle">
											<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdNo"/>
										</td>
										<td>
										   	<% if (sysOrganizationConfig.isNoRequired()) { %>
												<xform:text property="fdNo" validators="uniqueNo" required="true" showStatus="edit" mobile="true"></xform:text>
											<% } else { %>
												<xform:text property="fdNo" showStatus="edit" mobile="true"></xform:text>
											<% } %>
										</td>
									</tr>
									<tr>
										<td class="muiTitle" >
											<bean:message bundle="hr-ratify" key="hrRatifyEntry.fdEntryDate"/>
										</td>
										<td>
									        <xform:datetime property="fdEntryDate" required="true" showStatus="edit" dateTimeType="date" subject="${lfn:message('hr-ratify:hrRatifyEntry.fdEntryDate')}" mobile="true" />
										</td>
									</tr>
								</table>
							</div>
						</div>
						<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
						  	<li data-dojo-type="mui/tabbar/TabBarButton" onclick="submitForm();"><bean:message  key="button.submit"/></li>
						</ul>
					</div>
					<script type="text/javascript">
						require(["mui/form/ajax-form!hrRatifyEntryDRForm"]);
						require(['dojo/ready','dijit/registry','dojo/topic','dojo/query','dojo/dom-style','dojo/dom-class',"dojo/_base/lang","mui/dialog/Tip","dojo/request","mui/device/adapter","mui/util",'dojo/date/locale','mui/dialog/Confirm'],
							function(ready,registry,topic,query,domStyle,domClass,lang,Tip,req,adapter,util,locale,Confirm){
								var validorObj = null;
								ready(function(){
									validorObj = registry.byId('scrollView');
									var LoginNameValidators = {};
									var isLoginSpecialChar = <%=isLoginSpecialChar%>;
							    	var errorMsg ="<bean:message key='sysOrgPerson.error.loginName.abnormal' bundle='sys-organization'/>";
							    	<%  if("true".equals(isLoginSpecialChar)){%>
							    		errorMsg = "只能包含部分特殊字符 @ # $ % ^ & ( ) - + = { } : ; \ ' ? / < > , . \" [ ] | _ 空格";
							    	<% } %>
							    	validorObj._validation.addValidator("uniqueLoginName","<bean:message key='sysOrgPerson.error.loginName.mustUnique' bundle='sys-organization' />",function (value) {
					     				var fdId = query('[name="fdEntryId"]')[0].value;
					     				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&loginName=" + value+"&fdId="+fdId+"&checkType=unique&date="+new Date());
					     				var result = _CheckUnique(url);
					     				if (!result) 
					     					return false;
					     				return true;
					     			});
							    	validorObj._validation.addValidator("invalidLoginName","<bean:message key='sysOrgPerson.error.newLoginNameSameOldName' bundle='sys-organization' />",function(value) {
					     				if (LoginNameValidators["lgName"] && (LoginNameValidators["lgName"]==value)){
					     					return true;
					     				}
										LoginNameValidators["lgName"]=null;
										var fdId = query('[name="fdEntryId"]')[0].value;
										var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrRatifyEntryService&loginName=" + value+"&fdId="+fdId+"&checkType=invalid&date="+new Date());
										var result = _CheckUnique(url);
										var flag = true;
										if (!result){ 
											Confirm("<bean:message key='sysOrgPerson.newLoginName.ConfirmMsg' bundle='sys-organization' />",null,function(check){
												if(!check) {
													flag = false;
												}else{
													LoginNameValidators["lgName"]=value;
													flag = true;
												}
											}, false, function() {
												flag = false;
											});
										}
										return flag;	
					     			});
							    	validorObj._validation.addValidator("normalLoginName",errorMsg,function(value){
					     				var pattern;
				     					
					   					<% if("true".equals(isLoginSpecialChar)){%>
					   						pattern=new RegExp("^[A-Za-z0-9_@#$%^&()={}:;\'?/<>,.\"\\[\\]|\\-\\+ ]+$");
					   					<% }else{ %>
					   						pattern=new RegExp("^[A-Za-z0-9_]+$");
					   					<% }%>
					     					
					     				if(pattern.test(value)){
					     					return true;
					     				}else{
					     					return false;
					     				}
					     			});
							    	validorObj._validation.addValidator("uniqueNo","<bean:message key='organization.error.fdNo.mustUnique' bundle='sys-organization' />",function(v, e, o) {
					      				if (v.length < 1)
					      					return true;
					      				var fdId = query('[name="fdId"]')[0].value,
					      					fdNo = query('[name="fdNo"]')[0].value;;
					      				var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=sysOrgElementService&fdOrgType=8"
					      						+ "&fdId=" + fdId + "&fdNo=" + fdNo + "&_=" + new Date());
					      				return _CheckUnique(url);
					      			});
								});
								//校验登录名是否与系统中失效的登录名一致
								window._CheckUnique = function(url) {
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
								};
								window.submitForm = function() {
									if(!validorObj.validate()){
										return;
									}
									var url = '${LUI_ContextPath}/hr/ratify/hr_ratify_entry/hrRatifyEntry.do?method=checkAddOrg&fdEntryId=${hrRatifyEntryDRForm.fdEntryId}';
									req.get(url, {handleAs : 'json',headers: {"accept": "application/json"}})
									.response.then(function(datas) {
										//console.log(datas);
										if(datas.status=='200'){
											if(!datas.data){
												Com_Submit(document.hrRatifyEntryDRForm, 'saveOrgPerson');
											}else{
												Tip.fail({
													text:'<bean:message key="hrRatifyEntry.fdHasWrite.tip" bundle="hr-ratify" />' 
												});
												history.back(-1);
											}
										}
									});	
							   	};
						});	
					</script>
				</c:when>
				<c:otherwise>
					<center>
				        <div class="lui-destroy">
					    	<span>${ lfn:message('hr-ratify:hrRatifyEntry.fdHasWrite.tip') }</span>
					    </div>
				    </center>
				</c:otherwise>
			</c:choose>	
		</html:form>
	</template:replace>
</template:include>
