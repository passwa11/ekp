<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.edit" compatibleMode="true">

	<template:replace name="title">
		<bean:message bundle="km-forum" key="kmForumTopic.introduce.button" />
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="introduce.css"/>
	</template:replace>

	<template:replace name="content">
	<xform:config orient="vertical">
		<html:form action="/km/forum/km_forum/kmForumTopic.do" >
			<div>
				<div data-dojo-type="mui/view/DocScrollableView"
							data-dojo-mixins="mui/form/_ValidateMixin" id="scrollView">
					<div data-dojo-type="mui/panel/AccordionPanel">
						<div class="muiFormContent">
							<html:hidden property="fdId"/>
							<html:hidden property="method_GET"/>
							<table class="muiSimple" cellpadding="0" cellspacing="0">
								<tr>
									<td >
										<div style="min-height:10rem;padding-left: 1rem;">
										<c:out value="${kmForumTopicForm.docSubject }"></c:out>
										<html:hidden property="docSubject"/>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<kmss:editNotifyType property="fdNotifyType" showType="select" multi="true" mobile="true" title="${lfn:message('km-forum:kmForumTopic.introduce.fdNotifyType') }" required="true"></kmss:editNotifyType>
									</td>
								</tr>
								<tr>
									<td>
										<xform:address textarea="true" showStatus="edit" mobile="true" subject="${lfn:message('km-forum:kmForumTopic.introduce.fdTargetIds') }" propertyId="fdTargetIds" propertyName="fdTargetNames" orgType="ORG_TYPE_ALL" mulSelect="true" required="true"></xform:address>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>	
				  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
					  	data-dojo-props='colSize:2,href:"javascript:submitForm();",transition:"slide"'>
					  		<bean:message  key="button.submit"/>
					 </li>
				</ul>
			</div>
		</html:form>
		</xform:config>
	</template:replace>


</template:include>
<script>
require(['dojo/query','dojo/topic','dijit/registry','dojo/ready','dojo/dom-style','dojo/dom-geometry','mui/dialog/Tip','dojo/date/locale','mui/device/adapter','dojo/request','dojo/dom-attr'],
		function(query,topic,registry,ready,domStyle,domGeometry,Tip,locale,adapter,request,domAttr){
	
	window.submitForm = function(){
		var validateFlag = true;
		var fdNotifyType = query("[name='fdNotifyType']")[0];
		if(fdNotifyType.value == null || fdNotifyType.value == ""){
			Tip.fail({
				text:'<bean:message key="kmForumTopic.introduce.message.error.fdNotifyType" bundle="km-forum" />' 
			});
			validateFlag = false;
			return;
		 }
		var fdTargetIds = query("[name='fdTargetIds']")[0];
		if(fdTargetIds.value == "" ){
			Tip.fail({
				text:'<bean:message key="kmForumTopic.introduce.message.error.fdTargetIds" bundle="km-forum" />' 
			});
			validateFlag = false;
			return;
		}
		if(validateFlag){
			var url ="${LUI_ContextPath}/km/forum/km_forum/kmForumTopic.do?method=updateIntroduce";
			var fdId = '${JsParam.fdId}';
			var data={fdId:fdId,fdTargetIds:fdTargetIds.value,fdNotifyType:fdNotifyType.value};
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: false
			}).then(function(data){
				if(data==true){
					Tip.success({
						text:'<bean:message key="return.optSuccess" />',
						callback: function(){
							window.location='${LUI_ContextPath}/km/forum/mobile/kmForumPost.do?method=view&fdTopicId='+fdId;
						}
					});
					// setTimeout(function (){
					// 	window.history.go(-1);
					// }, 1500);
				}else{
					Tip.fail({
						text:'<bean:message key="return.optFailure" />' 
					});
				}
			},function(data){
				Tip.fail({
					text:'<bean:message key="return.optFailure" />' 
				});
			});
		}
	}
	
});
</script>