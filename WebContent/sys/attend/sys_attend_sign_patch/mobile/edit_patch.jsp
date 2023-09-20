<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="title">
		${ lfn:message('sys-attend:sysAttendSignPatch.addPatch') }
	</template:replace>
	
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/mobile/resource/css/attend.css?s_cache=${MUI_Cache}"></link>
	</template:replace>
	<template:replace name="content"> 
		<html:form action="/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do">
		
			<div data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView" class="muiSignExc gray">
				<html:hidden property="fdId" />
				<html:hidden property="method_GET" />
				<html:hidden property="fdCateName" />
				<html:hidden property="fdPatchPersonId" />
				<html:hidden property="fdPatchPersonName" />
				<html:hidden property="fdPatchTime" />
				<html:hidden property="cateId" value="${param.cateId }" />
				<html:hidden property="signedIds" value="${signedIds }" />
				
				<div class="muiFormContent" style="background:#fff;padding: 1.5rem 0 0 1rem;">
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td class="muiTitle">
								${ lfn:message('sys-attend:sysAttendCategory.custom') }
							</td>
							<td colspan="2">
								${sysAttendSignPatchForm.fdCateName }
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignPerson') }
							</td>
							<td>
								<xform:address propertyId="fdPatchDetail[0].fdSignPersonId" propertyName="fdPatchDetail[0].fdSignPersonName" orgType="ORG_TYPE_PERSON" required="true" mulSelect="false" subject="${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignPerson') }" style="" validators="attendPerson signedPerson" mobile="true"></xform:address>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignTime') }
							</td>
							<td>
								<xform:datetime property="fdPatchDetail[0].fdSignTime" required="true" subject="${ lfn:message('sys-attend:sysAttendSignPatchDetail.fdSignTime') }" style="" validators="signTime" mobile="true"></xform:datetime>
							</td>
						</tr>
					</table>
				</div>
				<div class="muiSignInBtnGroup muiSignInBtnGroupInline muiSignExcBtn">
			    	<button type="button" onclick="commitMethod()" class="muiSignInBtn muiSignInBtnLg muiSignInBtnPrimary">${ lfn:message('sys-attend:sysAttendCategory.sysAttendSign.topic1') }</button>
			   </div>
			   <ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom">
					<li data-dojo-type="mui/back/BackButton" edit="true" data-dojo-props="icon1:''"></li>
					<li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'',label:'${ lfn:message('sys-attend:sysAttendCategory.sysAttendSign.topic2') }'">
						<div data-dojo-type="mui/back/HomeButton"></div>
					</li>
				</ul>
			</div>
			
		</html:form>
		
		<script type="text/javascript">
			require(["mui/form/ajax-form!sysAttendSignPatchForm"]);
			require(['dojo/topic','mui/dialog/Tip',"dojo/dom-class","dojo/query","dojo/dom-style","dijit/registry", "dojo/ready", "dojo/request","dojo/io-query","mui/util"],
				function(topic,Tip,domClass,query,domStyle,registry,ready,request,ioq,util){
				ready(function(){
					var validorObj=registry.byId('scrollView');
					validorObj._validation.addValidator('attendPerson', "${lfn:message('sys-attend:sysAttendCategory.sysAttendSign.topic3') }", function(value, elem){
						if(!value){
							return true;
						}
						var targetIds = '${targetIds}' || '';
						if(targetIds == 'all'){
							return true;
						} else {
							return targetIds.indexOf(value) > -1;
						}
					});
					
					validorObj._validation.addValidator('signedPerson', "${lfn:message('sys-attend:sysAttendCategory.sysAttendSign.topic4') }", function(value, elem){
						if(!value){
							return true;
						}
						var signedIds = query('input[name="signedIds"]')[0].value;
						if(signedIds){
							return signedIds.indexOf(value) == -1;
						}
						return true;
					});
					
					validorObj._validation.addValidator('signTime', "${lfn:message('sys-attend:sysAttendCategory.sysAttendSign.topic5') }", function(value){
						var startTime = '${startTime}'
						var endTime = '${endTime}'
						if(value && startTime && endTime) {
							var date = Com_GetDate(value, 'datetime',Com_Parameter.DateTime_format);
							startObj = Com_GetDate(startTime, 'datetime', Com_Parameter.DateTime_format);
							endObj = Com_GetDate(endTime, 'datetime', Com_Parameter.DateTime_format);
							if(date && startObj && endObj) {
								return date.getTime() >= startObj.getTime() && date.getTime() <= endObj.getTime();
							}
						}
						return true;
					});
				});
				
				window.commitMethod=function(){
					var validorObj=registry.byId('scrollView');
					if(!validorObj.validate()){
						return false;
					}
					checkValidation(function(){
						Com_Submit(document.sysAttendSignPatchForm, 'save'); 
					});
				};
				
				// 防止同一个人补签多次
				var checkValidation = function(callback){
					var signInputs = query('input[name="fdPatchDetail[0].fdSignPersonId"]');
					var validorObj=registry.byId('scrollView');
					var signIds = [];
					for(var i=0; i<signInputs.length; i++){
						signIds.push(signInputs[i].value);
					}
					var proc = Tip.processing();
					proc.show();
					request(util.formatUrl('/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=checkValidation'), 
					{
						handleAs : 'json',
						method : 'POST',
						data : ioq.objectToQuery({'signId': signIds, 'cateId' : '${param.cateId }'})
					}).then(function(data){
						proc.hide();
						if(data) {
							if(data.status == '1'){
								callback && callback();
							} else {
								var signedIds = query('input[name="signedIds"]')[0];
								signedIds.value = data.signedIds;
								validorObj.validate();
							}
						}
					},function(e){
						proc.hide();
						console.error(e);
					});
				}
			});
		</script>
	</template:replace>
</template:include>