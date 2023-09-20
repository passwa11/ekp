<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<table class="tb_normal" width=100%>
	<tr>
		<td class="td_normal_title" width="15%">
			邮箱地址
		</td>
		<td width="35%">
			<xform:text property="fdEmail"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			手机号码
		</td>
		<td width="35%">
			<xform:text property="fdMobileNo" validators="phoneNumber"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			紧急联系人
		</td>
		<td width="35%">
			<xform:text property="fdEmergencyContact"></xform:text>
		</td>
		<td class="td_normal_title" width="15%">
			紧急联系人电话
		</td>
		<td width="35%">
			<xform:text property="fdEmergencyContactPhone" validators="phoneNumber"></xform:text>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width="15%">
			其他联系方式
		</td>
		<td colspan="3">
			<xform:text property="fdOtherContact"></xform:text>
		</td>
	</tr>
</table>