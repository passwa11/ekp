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
					<bean:message key="lbpmTemplate.skipRule.function" bundle="sys-lbpmservice-support"/>
				</div>
				<div class="helptext">
					<bean:message key="lbpmTemplate.skipRule.description" bundle="sys-lbpmservice-support"/>
				</div>
				
				<div class="headline">
					<bean:message key="lbpmTemplate.skipRule.defaultRule" bundle="sys-lbpmservice-support"/>
				</div>
				<div class="helptext">
					1、<bean:message key="lbpmTemplate.skipRule.rule1" bundle="sys-lbpmservice-support"/><br/>
					2、<bean:message key="lbpmTemplate.skipRule.rule2" bundle="sys-lbpmservice-support"/><br/>
					3、<bean:message key="lbpmTemplate.skipRule.rule3" bundle="sys-lbpmservice-support"/><br/>
					4、<bean:message key="lbpmTemplate.skipRule.rule4" bundle="sys-lbpmservice-support"/><br/>
					5、<bean:message key="lbpmTemplate.skipRule.rule5" bundle="sys-lbpmservice-support"/><br/>
					6、<bean:message key="lbpmTemplate.skipRule.rule6" bundle="sys-lbpmservice-support"/><br/>
					7、<bean:message key="lbpmTemplate.skipRule.rule7" bundle="sys-lbpmservice-support"/><br/>
				</div>
				
				<div class="headline">
					<bean:message key="lbpmTemplate.skipRule.IntelligentComputingRule" bundle="sys-lbpmservice-support"/>
				</div>
				<div class="helptext">
					<bean:message key="lbpmTemplate.skipRule.IntelligentComputingRuleDesc" bundle="sys-lbpmservice-support"/>
				</div>
				
				<div class="headline">
					<bean:message key="lbpmTemplate.skipRule.scene" bundle="sys-lbpmservice-support"/>
				</div>
				<div class="helptext">
					1、<bean:message key="lbpmTemplate.skipRule.scene1" bundle="sys-lbpmservice-support"/><br/>
					2、<bean:message key="lbpmTemplate.skipRule.scene2" bundle="sys-lbpmservice-support"/><br/>
					3、<bean:message key="lbpmTemplate.skipRule.scene3" bundle="sys-lbpmservice-support"/><br/>
					4、<bean:message key="lbpmTemplate.skipRule.scene4" bundle="sys-lbpmservice-support"/><br/>
					5、<bean:message key="lbpmTemplate.skipRule.scene5" bundle="sys-lbpmservice-support"/><br/>
					6、<bean:message key="lbpmTemplate.skipRule.scene6" bundle="sys-lbpmservice-support"/><br/>
					7、<bean:message key="lbpmTemplate.skipRule.scene7" bundle="sys-lbpmservice-support"/><br/>
					8、<bean:message key="lbpmTemplate.skipRule.scene8" bundle="sys-lbpmservice-support"/><br/>
					9、<bean:message key="lbpmTemplate.skipRule.scene9" bundle="sys-lbpmservice-support"/><br/>
					10、<bean:message key="lbpmTemplate.skipRule.scene10" bundle="sys-lbpmservice-support"/><br/>
					11、<bean:message key="lbpmTemplate.skipRule.scene11" bundle="sys-lbpmservice-support"/><br/>
					12、<bean:message key="lbpmTemplate.skipRule.scene12" bundle="sys-lbpmservice-support"/><br/>
					<br/>
					<bean:message key="lbpmTemplate.skipRule.SymbolicRepresentation1" bundle="sys-lbpmservice-support"/><br/>
					<bean:message key="lbpmTemplate.skipRule.SymbolicRepresentation2" bundle="sys-lbpmservice-support"/><br/>
					<bean:message key="lbpmTemplate.skipRule.SymbolicRepresentation3" bundle="sys-lbpmservice-support"/><br/>
					<bean:message key="lbpmTemplate.skipRule.SymbolicRepresentation4" bundle="sys-lbpmservice-support"/><br/>
					<bean:message key="lbpmTemplate.skipRule.SymbolicRepresentation5" bundle="sys-lbpmservice-support"/><br/>
				</div>
				
			</div>
		</div>
		<div style="height:5px;"></div>
	</template:replace>
</template:include>