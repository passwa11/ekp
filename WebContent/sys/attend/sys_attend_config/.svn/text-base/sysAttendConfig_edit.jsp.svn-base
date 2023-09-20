<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="content">
<style type="text/css">
	.sysAttendConfig-item { 
		display: flex;
	    display: -webkit-flex;
	    align-items: center;
	    margin: 5px 0;
	    padding-left: 10px;
    }
    .sysAttendConfig-item.fdClientLimit,.sysAttendConfig-item.fdDeviceLimit{display:inline;}
    .fdClientLimit div[data-lui-type="lui/switch!Switch"]{display:inline;}
    .fdDeviceLimit div[data-lui-type="lui/switch!Switch"]{display:inline;}
</style>
<html:form action="/sys/attend/sys_attend_config/sysAttendConfig.do">
<html:hidden property="fdId"/>
<div style="margin-top:15px">
<p class="configtitle"><bean:message key="sysAttend.tree.config.title" bundle="sys-attend" /></p>
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sysAttend.tree.config.fdExcTarget"  bundle="sys-attend"/>
		</td><td colspan=3>
			<xform:address propertyId="fdExcTargetIds" propertyName="fdExcTargetNames" 
					subject="${ lfn:message('sys-attend:sysAttendCategory.fdTargets') }"
					mulSelect="true" orgType="ORG_TYPE_ALL" textarea="false" style="width:95%" />
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('sys-attend:sysAttend.tree.config.speedAttend') }
		</td>
		<td colspan=3>
			<table>
				<tr>
					<td>
						<div class='sysAttendConfig-item'>
							<ui:switch property="fdSpeedAttend" 
								enabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdSpeedAttend') }"
								disabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdSpeedAttend.disable') }"
								onValueChange="changeSpeedConfig()">
							</ui:switch>
						</div>
					</td>
				</tr>
				<tr id="fdSpeedTime" style="display: none">
					<td>
						<div class='sysAttendConfig-item'>
							${ lfn:message('sys-attend:sysAttend.tree.config.fdSpeedStartTime') }
							<xform:datetime onValueChange="onTimeChange" property="fdSpeedStartTime" dateTimeType="time" value="${sysAttendConfigForm.fdSpeedStartTime ==null? '8:00' :sysAttendConfigForm.fdSpeedStartTime}"
								validators="required" subject="${ lfn:message('sys-attend:sysAttend.tree.config.validate.fdSpeedStartTime') }"></xform:datetime>
							${ lfn:message('sys-attend:sysAttend.tree.config.fdSpeedEndTime') }
							<xform:datetime property="fdSpeedEndTime" dateTimeType="time" value="${sysAttendConfigForm.fdSpeedEndTime ==null? '10:00' :sysAttendConfigForm.fdSpeedEndTime}"
								validators="required afterSpeedStartTime" subject="${ lfn:message('sys-attend:sysAttend.tree.config.validate.fdSpeedEndTime') }"></xform:datetime>
							<span class="txtstrong">*</span>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<div id="fdSpeedDescription" style="color: red;display: none;padding-left: 10px;">
							<bean:message key="sysAttend.tree.config.speedAttend.description"  bundle="sys-attend"/><br>
							<bean:message key="sysAttend.tree.config.speedAttend.description.txt"  bundle="sys-attend"/>
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('sys-attend:sysAttend.tree.config.pushReport') }
		</td>
		<td colspan=3>
			<div class='sysAttendConfig-item'>
				<ui:switch property="fdPushLeader" 
					enabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdPushLeader') }"
					disabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdPushLeader.disable') }"
					onValueChange="changePushConig()">
				</ui:switch>
			</div>
			<div id="fdPushDateTime" class='sysAttendConfig-item' style="display: none">
				${ lfn:message('sys-attend:sysAttend.tree.config.fdPushDate') }
				<xform:select property="fdPushDate" value="${sysAttendConfigForm.fdPushDate == null? '1' :sysAttendConfigForm.fdPushDate}"
					validators="required" subject="${ lfn:message('sys-attend:sysAttend.tree.config.validate.fdPushDate') }">
					<c:forEach var="date" begin="1" end="5">
						<xform:simpleDataSource value="${date }"></xform:simpleDataSource>
					</c:forEach>
				</xform:select>
				${ lfn:message('sys-attend:sysAttend.tree.config.fdPushTime') }
				<xform:datetime property="fdPushTime" dateTimeType="time" value="${sysAttendConfigForm.fdPushTime ==null? '10:00' :sysAttendConfigForm.fdPushTime}"
					validators="required" subject="${ lfn:message('sys-attend:sysAttend.tree.config.validate.fdPushTime') }"></xform:datetime>
				<span class="txtstrong">*</span>
			</div>
		</td>
	</tr>
	
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('sys-attend:sysAttendConfig.fdOffType') }
		</td>
		<td>
			<html:hidden property="fdOffType"></html:hidden>
			<div style="margin-top: 3px;">
				${ lfn:message('sys-attend:sysAttend.tree.config.leaveType0') }<a href="${LUI_ContextPath }/sys/profile/index.jsp#maintenance/admin/" target="_blank" style="color: #4285f4;">${ lfn:message('sys-attend:sysAttend.tree.config.leaveType1') }</a>
				${ lfn:message('sys-attend:sysAttend.tree.config.leaveType2') }<a href="${LUI_ContextPath }/sys/profile/index.jsp?type=leaveRule#lbpm/timeArea" target="_blank" style="color: #4285f4;">${ lfn:message('sys-attend:sysAttend.tree.config.leaveType3') }</a>${ lfn:message('sys-attend:sysAttend.tree.config.leaveType4') }
			</div>
		</td>
	</tr>
	<c:set var="fdClientLimitStyle" value="style='display:none;'"></c:set>
	<c:if test="${isEnableKKConfig eq true }">
		<c:set var="fdClientLimitStyle" value=""></c:set>
	</c:if>
	<tr id="fdClient_row" ${fdClientLimitStyle }>
		<td class="td_normal_title" width=15%>
			${ lfn:message('sys-attend:sysAttend.tree.config.fdClient') }
		</td>
		<td colspan=3>
			<div class='sysAttendConfig-item fdClientLimit'>
				<ui:switch property="fdClientLimit" 
					enabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdClient.limit') }"
					disabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdClient.unlimit') }"
					onValueChange="onClientLimitChange()">
				</ui:switch>
			</div>
			<div id="fdClientLimit_app" class='sysAttendConfig-item' style="display: none;">
				<xform:radio property="fdClient" value="kk">
					<c:if test="${isEnableKKConfig eq true}">
						<xform:simpleDataSource value="kk" bundle="sys-attend" textKey="sysAttend.tree.config.fdClient.kk" />
					</c:if>
				</xform:radio>
			</div>
		</td>
	</tr>
	<tr id="fdDevice_row" style="display:none;">
		<td class="td_normal_title" width=15%>
			${ lfn:message('sys-attend:sysAttend.tree.config.fdDevice') }
		</td>
		<td colspan=3>
			<div class='sysAttendConfig-item fdDeviceLimit'>
				<ui:switch property="fdDeviceLimit" 
					enabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdDevice.limit') }"
					disabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdDevice.unlimit') }"
					onValueChange="onDeviceLimitChange()">
				</ui:switch>
			</div>
			<div id="fdDeviceLimit_app" class='sysAttendConfig-item' style="display: none;">
				<xform:select property="fdDeviceCount" showStatus="edit" showPleaseSelect="false">
					<xform:simpleDataSource value="1">1</xform:simpleDataSource>
					<xform:simpleDataSource value="2">2</xform:simpleDataSource>
					<xform:simpleDataSource value="3">3</xform:simpleDataSource>
					<xform:simpleDataSource value="4">4</xform:simpleDataSource>
				</xform:select>
				${ lfn:message('sys-attend:sysAttend.tree.config.fdDevice.count') }
				<c:if test="${isEnableKKConfig eq true}">
					<span class="lui_device_exc">${ lfn:message('sys-attend:sysAttend.tree.config.device.exc') }:
						<xform:radio property="fdDeviceExcMode" showStatus="edit">
							<xform:simpleDataSource value="camera" bundle="sys-attend" textKey="sysAttend.tree.config.device.exc.camera"></xform:simpleDataSource>
							<xform:simpleDataSource value="face" bundle="sys-attend" textKey="sysAttend.tree.config.device.exc.face"></xform:simpleDataSource>
						</xform:radio>
					</span>
				</c:if>
			</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('sys-attend:sysAttend.tree.config.sameDeviceLimit') }
		</td>
		<td>
			<div class='sysAttendConfig-item'>
				<ui:switch property="fdSameDeviceLimit" 
					enabledText="${ lfn:message('sys-attend:sysAttend.tree.config.sameDeviceLimit.yes') }"
					disabledText="${ lfn:message('sys-attend:sysAttend.tree.config.sameDeviceLimit.no') }">
				</ui:switch>
			</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('sys-attend:sysAttend.tree.config.expires') }
		</td>
		<td colspan=3>
			<div class='sysAttendConfig-item'>
				<%-- 默认不开启 --%>
				<c:set var="isRemain" value="${sysAttendConfigForm.fdIsRemain eq null ? false : sysAttendConfigForm.fdIsRemain }"></c:set>
				<%-- 默认3个月 --%>
				<c:set var="remainMonth" value="${sysAttendConfigForm.fdIsRemain eq null ? '3' : sysAttendConfigForm.fdRemainMonth }"></c:set>
				<ui:switch property="fdIsRemain" 
					enabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdIsRemain.yes') }"
					disabledText="${ lfn:message('sys-attend:sysAttend.tree.config.fdIsRemain.no') }"
					onValueChange="changeIsRemain()"
					checked="${isRemain}">
				</ui:switch>
			</div>
			<div id='remainBlock' class='sysAttendConfig-item' style="${isRemain ? '' : 'display:none'}">
				${ lfn:message('sys-attend:sysAttend.tree.config.before') }<xform:text property="fdRemainMonth" showStatus="edit" style="width: 50px;text-align: center;" validators="${isRemain ? 'required min(1) max(12) digits' : '' }" value="${remainMonth }"></xform:text>${ lfn:message('sys-attend:sysAttend.tree.config.records') }
			</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('sys-attend:sysAttend.tree.config.trip') }
		</td>
		<td>
			<div class='sysAttendConfig-item'>
				<ui:switch property="fdTrip" 
					enabledText="${ lfn:message('sys-attend:sysAttend.tree.config.trip.natural') }"
					disabledText="${ lfn:message('sys-attend:sysAttend.tree.config.trip.workdate') }">
				</ui:switch>
			</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${lfn:message('sys-attend:sysAttend.tree.config.fdShouldDays') }
		</td>
		<td>
			<div class='sysAttendConfig-item'>
				<ui:switch property="fdShouldDayCfg" 
					enabledText="${lfn:message('sys-attend:sysAttend.tree.config.fdShouldDays.yes') }"
					disabledText="${lfn:message('sys-attend:sysAttend.tree.config.fdShouldDays.no') }">
				</ui:switch>
			</div>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
				${lfn:message('sys-attend:sysAttendSignLog.config.tohis') }
		</td>
		<td>
			<div class='sysAttendConfig-item'>
				<xform:text  validators="required number digits min(1)"
							 property="fdSignLogToHisDay"
							showStatus="edit"
							>
				</xform:text>
				${lfn:message('sys-attend:sysAttendSignLog.config.tohis.desc') }
			</div>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
				${lfn:message('sys-attend:sysAttendSignLog.config.delete') }
		</td>
		<td>
			<div class='sysAttendConfig-item'>
				<xform:text  validators="required number digits min(1)"
							 property="fdSignLogToDeleteDay"
							 showStatus="edit"
				>
				</xform:text>
					${lfn:message('sys-attend:sysAttendSignLog.config.delete.desc') }
			</div>
		</td>
	</tr>

</table>
<div style="margin-bottom: 10px;margin-top:25px">
	   <ui:button text="${lfn:message('button.save')}" suspend="bottom" height="35" width="120" onclick="submint();" order="1" ></ui:button>
</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>
<script type="text/javascript">

	function submint() {
		var fdClientLimit = $('input[name="fdClientLimit"]').val();
		if(fdClientLimit!='true'){
			$('input[name="fdDeviceLimit"]').val('false');
		}
		Com_Submit(document.sysAttendConfigForm, 'update');
	}

	window.changeSpeedConfig = function() {
		var fdSpeedAttend = $(':hidden[name="fdSpeedAttend"]').val(),
			fdSpeedStartTime = $(':text[name="fdSpeedStartTime"]'),
			fdSpeedEndTime = $(':text[name="fdSpeedEndTime"]'),
			fdSpeedTime = $('#fdSpeedTime'),
			fdSpeedDescription = $('#fdSpeedDescription');
		if (fdSpeedAttend == 'true') {
			fdSpeedStartTime.removeAttr('disabled');
			fdSpeedEndTime.removeAttr('disabled');
			fdSpeedTime.show();
			fdSpeedDescription.show();
		} else {
			fdSpeedStartTime.attr('disabled','disabled');
			fdSpeedEndTime.attr('disabled','disabled');
			fdSpeedTime.hide();
			fdSpeedDescription.hide();
		}
	}
	
	window.changePushConig = function() {
		var fdPushLeader = $(':hidden[name="fdPushLeader"]').val(),
			fdPushDate = $('select[name="fdPushDate"]'),
			fdPushTime = $(':text[name="fdPushTime"]'),
			fdPushDateTime = $('#fdPushDateTime');
		if (fdPushLeader == 'true') {
			fdPushDate.removeAttr('disabled');
			fdPushTime.removeAttr('disabled');
			fdPushDateTime.show();
		} else {
			fdPushDate.attr('disabled','disabled');
			fdPushTime.attr('disabled','disabled');
			fdPushDateTime.hide();
		}
	}
	
	var configValidation = $KMSSValidation();
	
	configValidation.addValidator('afterSpeedStartTime', "${ lfn:message('sys-attend:sysAttend.tree.config.validate.time.afterSpeedStartTime') }", function(v,e,o){
		var SpeedStartTime = $('input[name="fdSpeedStartTime"]:enabled').val();
		if(SpeedStartTime && v) {
			return SpeedStartTime <= v;
		} else {
			return true;
		}
	});
	
	window.onTimeChange = function(value,element){
		configValidation.validateElement(element);
		if(element.name=='fdSpeedStartTime'){
			configValidation.validateElement($('[name="fdSpeedEndTime"]')[0]);
		}
	};
	window.onClientLimitChange = function(){
		var fdClientLimit = $('input[name="fdClientLimit"]').val();
		if(fdClientLimit=='true'){
			$('#fdClientLimit_app').css('display','inline');
			$('#fdDevice_row').show();
		}else{
			$('#fdClientLimit_app').hide();
			$('#fdDevice_row').hide();
		}
	};
	window.onDeviceLimitChange = function(){
		var fdDeviceLimit = $('input[name="fdDeviceLimit"]').val();
		if(fdDeviceLimit=='true'){
			$('#fdDeviceLimit_app').css('display','inline');
		}else{
			$('#fdDeviceLimit_app').hide();
		}
	};
	window.clientLimitInit = function(){
		var isEnableKKConfig = "${isEnableKKConfig}";
		var isEnableDingConfig = "${isEnableDingConfig}";
		if(isEnableKKConfig =='true' || isEnableDingConfig =='true'){
			onClientLimitChange();
			onDeviceLimitChange();
		}
	};
	
	window.changeIsRemain = function(){
		var isRemain  = $('[name="fdIsRemain"]').val();
		var remainMonth = $('[name="fdRemainMonth"]');
		if(isRemain == 'true') {
			$('#remainBlock').show();
			if(!remainMonth.val()){
				remainMonth.val('3');
			}
			configValidation.addElements(remainMonth[0], 'required min(1) max(12) digits');
		} else {
			$('#remainBlock').hide();
			configValidation.removeElements(remainMonth[0]);
		}
	};
	
	LUI.ready(function(){
		changeSpeedConfig();
		changePushConig();
		clientLimitInit();
	});
</script>
</template:replace>
</template:include>

<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>