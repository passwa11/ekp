<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view" compatibleMode="true">
    <template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/lbpmext/authorize/mobile/resource/css/lbpmext.css" />
	</template:replace>
	<template:replace name="title">
		<c:out value="${lfn:message('sys-lbpmext-authorize:lbpmAuthorize.mobile.detail.title')}"></c:out>
	</template:replace>
	<template:replace name="content">
		<div id="scrollView"  data-dojo-type="mui/view/DocScrollableView" class="gray">
			<div data-dojo-type="mui/panel/AccordionPanel">
				<div data-dojo-type="mui/panel/Content" data-dojo-props="title:'<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.info.title" />',icon:'mui-ul'">
					<div class="muiFormContent">
						<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td class="muiTitle">
							  <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizeType"/>
							</td>
							<td>
							<sunbor:enumsShow value="${lbpmAuthorizeForm.fdAuthorizeType}" enumsType="lbpmAuthorize_authorizeType" />
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
							   <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizer"/>
							</td>
							<td>
							   <xform:text property="fdAuthorizerName" mobile="true"></xform:text>
							 </td>
						</tr>
						<tr>
							<td class="muiTitle">
						    	<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeItem.fdAuthorizeOrgId"/>
							</td>
							<td>
							    <xform:text property="fdLbpmAuthorizeItemNames" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
							   <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizedPerson"/>
							</td>
							<td>
								<xform:text property="fdAuthorizedPersonName" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
							   <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdDrafterDeptConstraints"/>
							</td>
							<td>
								<xform:text property="fdDrafterDeptConstraintNames" mobile="true"></xform:text>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
							   <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.lbpmAuthorizeScope"/>
							</td>
							<td>
							  <xform:text property="fdScopeFormAuthorizeCateShowtexts" mobile="true"></xform:text>
							</td>
						</tr>
						<c:if test="${lbpmAuthorizeForm.fdAuthorizeType != 1}">
							<tr>
								<td class="muiTitle">
								   <div name="aaa">
								     <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle"/>
								   </div>
								</td>
								<td>
									 <div name="bbb">
									    <xform:datetime property="fdStartTime" dateTimeType="datetime" mobile="true">
										</xform:datetime>
										<xform:datetime property="fdEndTime" dateTimeType="datetime" mobile="true">
										</xform:datetime>
									 </div>
								</td>
							</tr>
						</c:if>
						<c:if test="${lbpmAuthorizeForm.fdAuthorizeType == 2}">
						<tr>
							<td class="muiTitle">
							  <bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdExpireRecover"/>
							</td>
							<td>
								<c:if test="${lbpmAuthorizeForm.fdExpireRecover=='true'}">
									<div class="muiLbpmextSwitch" data-dojo-type="mui/form/switch/NewSwitch"
									     data-dojo-mixins="sys/lbpmext/authorize/mobile/js/SwitchMixin" 
									     data-dojo-props="mode:'view',leftLabel:'',rightLabel:'',value:'on',property:'fdExpireRecover'">
									</div>
								</c:if>
								<c:if test="${lbpmAuthorizeForm.fdExpireRecover!='true'}">
									<div class="muiLbpmextSwitch" data-dojo-type="mui/form/switch/NewSwitch"
										 data-dojo-mixins="sys/lbpmext/authorize/mobile/js/SwitchMixin" 
										 data-dojo-props="mode:'view',leftLabel:'',rightLabel:'',value:'off',property:'fdExpireRecover'">
									</div>									
								</c:if>	
							</td>
						</tr>
						</c:if>
					</table>
					</div>
				</div>
				<div class="muiAuthorizeOptBar">
					<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
						<div class="muiAuthorizeEditOpt" onclick='editCalendar();'>
							<i class="mui mui-calendarEdit"></i><bean:message key="button.edit"/>
						</div>
					</kmss:auth>
					<c:if test="${_gtValue eq true}">
						<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=stop&fdId=${param.fdId}" requestMethod="GET">
							<c:if test="${lbpmAuthorizeForm.fdAuthorizeType ne '1'}">
								<div class="muiAuthorizeStopOpt" onclick='_dealCalendar("stop");'>
									<i class="mui mui-stop"></i><bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.btn.stop.title"/>
								</div>
							</c:if>
						</kmss:auth>
					</c:if>
					<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
						<div class="muiAuthorizeDeleteOpt" onclick='_dealCalendar("delete");'> 
							<i class="mui mui-drafter_refuse_abandon"></i><bean:message key="button.delete"/>
						</div>
					</kmss:auth>
				</div>
			</div>
			<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" >
			  <li data-dojo-type="mui/back/BackButton"></li>
			   <li data-dojo-type="mui/tabbar/TabBarButtonGroup" data-dojo-props="icon1:'mui mui-more'">
			    	<div data-dojo-type="mui/back/HomeButton"></div>
			    </li>
			</ul>
		</div>
	</template:replace>
</template:include>
<script>
//编辑授权
window.editCalendar=function(){
	location.href='${LUI_ContextPath}/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=edit&fdId=${param.fdId}';
};
require(['dojo/ready','dojo/query', "mui/dialog/Dialog", "dojo/dom-construct", "dojo/_base/lang","dojo/request","mui/dialog/Tip"],
		function(ready,query,Dialog,domConstruct,lang,request,Tip){
	window._dealCalendar = function(dealType){
		var summary = "";
		var method = dealType;
		if(dealType=="delete"){
			summary = '<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.delete.authtip"/>';
		}else if(dealType=="stop"){
			summary = '<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.stop.authtip"/>';
		}
		var contentNode = domConstruct.create('div', {
			className : 'muiBackDialogElement',
			innerHTML : '<div>' + summary + '<div>'
		});
		if(method=='delete'){
			var canDel=false;
			var url="${LUI_ContextPath}/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=findAuthInstanceCount&fdId=${param.fdId}";
			request.get(url, {handleAs : '',sync:true}).response.then(function(datas) {
				if(datas.data=='0'){
					canDel=true;
				}else{
					Tip.tip({text:"<bean:message  bundle='sys-lbpmext-authorize' key='lbpmAuthorizeItem.notdel.msg'/>"});
				}
			});
			if(!canDel){
				return ;
			}
		}
		var muiBackDialogShow = query(".muiBackDialogShow");
		if(muiBackDialogShow.length == 0){
			Dialog.element({
				'title' : '<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.mobile.tip"/>',
				'showClass' : 'muiBackDialogShow',
				'element' : contentNode,
				'scrollable' : false,
				'parseable': false,
				'buttons' : [ {
					title : '<bean:message key="button.cancel"/>',
					fn : function(dialog) {
						dialog.hide();
					}
				} ,{
					title : '<bean:message key="button.ok"/>',
					fn : lang.hitch(this,function(dialog) {
						var url='${LUI_ContextPath}/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method='+method+'&fdId=${param.fdId}'
						request.get(url, {handleAs : 'json',headers: {"accept": "application/json"}}).response.then(function(datas) {
							if(datas.status=='200'){
								dialog.hide();
								location.href='${LUI_ContextPath}/sys/lbpmext/authorize/mobile/index.jsp';
							}
						});
					})
				}]
			});
		}
	};	
	
});
</script>
