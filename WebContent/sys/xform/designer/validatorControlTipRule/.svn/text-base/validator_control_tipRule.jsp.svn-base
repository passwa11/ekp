<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple">
	<template:replace name="head">
		<template:super/>
		<style>
			body{background-color: #F9F9F9;}
			.headline {font-size:14px; font-weight: bold; color: #333; border-bottom: 1px #CCC solid; padding:10px 10px 5px; margin-bottom: 2px;}
			.helptext {line-height:30px; padding: 0px 10px; background-color: #F9F9F9; margin-bottom:2px;}
			.helptitle {line-height:30px; padding: 0px 10px; background-color: #F9F9F9; margin-bottom:2px; color:#333;}
			.textcolor span {display:inline-block; min-width:200px; line-height:30px; margin-left:20px;}
			.container_frame {display:inline-block; border:1px yellow dashed; padding:2px; width:480px; margin:10px; vertical-align: top;}
		</style>
	</template:replace>
	<template:replace name="body">
		<ui:top />
		<div>
			<div style="padding:5px 10px; background-color: #FFFFFF;">
				<div class="headline">
					<bean:message key="Designer_Lang.validatorControlRule" bundle="sys-xform-base"/>
				</div>
				<div class="helptext">
					<bean:message key="Designer_Lang.validatorControlRuleDes" bundle="sys-xform-base"/>
				</div>
				
				<div class="headline">
					<bean:message key="Designer_Lang.validatorControlRuleScene" bundle="sys-xform-base"/>
				</div>
				<div class="helptext" style="padding:5px;">
					<bean:message key="Designer_Lang.validatorControlRuleExam" bundle="sys-xform-base"/></br>
					<img src="${LUI_ContextPath}/sys/xform/designer/validatorControlTipRule/validatorExample1.png" style="margin-top:-2px;"></img>
				</div>
				
			</div>
		</div>
		<div style="height:5px;"></div>
	</template:replace>
</template:include>