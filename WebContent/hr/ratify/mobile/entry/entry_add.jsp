<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>
<template:include ref="mobile.edit" compatibleMode="true" newMui="true">
	<template:replace name="title">
		<bean:message bundle="hr-ratify" key="mobile.hrStaffEntry.create" />
	</template:replace>
	
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/css/formreset.css"></link>
		<style>
			#scrollView{
				background:#fff;
			}
		</style>
	</template:replace>
	
	<template:replace name="content">
		<html:form styleId="formReset" action="/hr/staff/hr_staff_entry/hrStaffEntry.do">
			<html:hidden property="fdId" value="${hrStaffEntryForm.fdId}"/>
			<html:hidden property="fdStatus" value="${hrStaffEntryForm.fdStatus}"/>
			<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
				<div data-dojo-type="mui/panel/AccordionPanel">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'必填项'">
						<div class="muiFormContent">
							 <table class="muiSimple" cellpadding="0" cellspacing="0">
                                 <tr>
                                     <td class="muiTitle">
                                         ${lfn:message('hr-ratify:mobile.hrStaffEntry.fdName')}
                                     </td>
                                     <td class="newInput">
                                         <div id="_xform_fdName" _xform_type="text">
                                             <xform:text property="fdName" showStatus="edit" mobile="true" style="width:95%;" />
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="muiTitle">
                                         ${lfn:message('hr-ratify:mobile.hrStaffEntry.fdMobileNo')}
                                     </td>
                                     <td class="newInput">
                                         <div id="_xform_fdName" _xform_type="text">
                                             <xform:text property="fdMobileNo" validators="uniqueMobileNo phoneNumber" showStatus="edit" mobile="true" style="width:95%;" />
                                         </div>
                                     </td>
                                 </tr>
                                 <tr>
                                     <td class="muiTitle" style="width:40%;">
                                         ${lfn:message('hr-ratify:mobile.hrStaffEntry.fdPlanEntryTime')}
                                     </td>
                                     <td class="newDate">
                                         <div id="_xform_fdName" _xform_type="text">
                                             <xform:datetime property="fdPlanEntryTime" showStatus="edit" mobile="true" style="width:95%;" required="true"/>
                                         </div>
                                     </td>
                                 </tr>
								<tr>
                                     <td class="muiTitle">
                                         ${lfn:message('hr-ratify:mobile.hrStaffEntry.dept')}
                                     </td>
                                     <td class="newAddress">
                                         <div id="_xform_fdName" _xform_type="text">
                                             <xform:address propertyId="fdPlanEntryDeptId" propertyName="fdPlanEntryDeptName" mobile="true" required="true" orgType="ORG_TYPE_DEPT" showStatus="edit" style="width:95%;" />
                                         </div>
                                     </td>
                                 </tr>
							</table>
						</div>
					</div>
				</div>
				<div data-dojo-type="mui/panel/AccordionPanel">
					<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'非必填项'">
						<div class="muiFormContent">
							 <table class="muiSimple" cellpadding="0" cellspacing="0">
                                 <tr>
                                     <td class="muiTitle">
                                         ${lfn:message('hr-ratify:mobile.hrStaffEntry.post')}
                                     </td>
                                     <td class="newAddress">
                                         <div id="_xform_fdName" _xform_type="text">
                                             <xform:address propertyId="fdOrgPostIds" propertyName="fdOrgPostNames" mulSelect="true" mobile="true" orgType="ORG_TYPE_POST" showStatus="edit" style="width:95%;" />
                                         </div>
                                     </td>
                                 </tr>
							</table>
						</div>
					</div>
				</div>
				<div data-dojo-type="mui/tabbar/TabBar" fixed="bottom"  data-dojo-props='fill:"grid"'>
					
					<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext" onclick="commitMethod('saveadd');"
					 		data-dojo-props='colSize:2,icon1:"",align:"center"'>
						${lfn:message('button.saveadd')}
					</li>
					<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext"
				 		data-dojo-props='colSize:2,onClick:function(){commitMethod("save");},icon1:"",align:"center",moveTo:"carInfoView",transition:"slide"'>
						${lfn:message('button.save')}
					</li>
				</div>
			</div>
		</html:form>
	</template:replace>


</template:include>

<script>
require(['dojo/request','dojo/ready','dojo/date/locale',
         'dojo/query','dojo/topic','dijit/registry','mui/dialog/Confirm','mui/dialog/Tip','mui/device/adapter','mui/util','dojo/_base/lang', 'dojo/dom-class','mui/dialog/Confirm'],
			function(request,ready,locale,query,topic,registry,Confirm,Tip,adapter,util,lang,domClass,Confirm){
	
		//校验对象
		var validorObj = null;
	
		ready(function(){
			require([ "mui/form/ajax-form!hrRatifyEntryForm"]);
			validorObj = registry.byId('scrollView');
			// 验证手机号是否已被注册
			validorObj._validation.addValidator("uniqueMobileNo", "<bean:message key='sysOrgPerson.error.newMoblieNoSameOldName' bundle='sys-organization' />",
				function(v, e, o) {
					var value = query('[name="fdMobileNo"]')[0].value;
					if(startsWith(value, "+86")) {
						// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
						value = value.slice(3, value.length)
					}
					if(startsWith(value, "+")) {
						value = value.replace("+", "x")
					}
					var fdId = document.getElementsByName("fdId")[0].value;
					var url = encodeURI(Com_Parameter.ResPath + "jsp/ajax.jsp?&serviceName=hrStaffEntryService&mobileNo="
							+ value + "&fdId=" + fdId + "&checkType=unique&date=" + new Date());
					var result = _CheckUnique(url);
					if (!result) 
						return false;
					return true;
				});
			//验证手机号是否合法
			validorObj._validation.addValidator("phone", '请输入有效的手机号',
				function(v, e, o) {
					return this.getValidator('isEmpty').test(v) || /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[189])\d{8}$/.test(v);
				});
		});
		
		// 增加一个字符串的startsWith方法
		function startsWith(value, prefix) {
			return value.slice(0, prefix.length) === prefix;
		}
			
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
	
		window.validate = function() {
			validatorObj = registry.byId("scrollView");
			var rs = validatorObj.validate();
			return rs;
		};
		
		
		window.commitMethod = function(method){
			if(!validorObj.validate()){
				return;
			}
			Com_Submit(document.hrStaffEntryForm, method);
			if(method == 'saveadd'){
				setInterval(() => {
					window.location='${LUI_ContextPath}/hr/staff/hr_staff_entry/hrStaffEntry.do?method=addEntryMobile';
				}, 2000);
			}
		};
		
		
});

</script>