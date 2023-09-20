<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<tr>
	<td class="td_normal_title" style="width: 15%"><bean:message bundle="sys-zone" key="zoneAddress.itemType" /></td>
	<td colspan="3">
		<xform:radio onValueChange="onItemTypeChange" property="fdItemType" showStatus="readOnly">
			<xform:simpleDataSource value="private">
				<bean:message bundle="sys-zone" key="zoneAddress.itemType.private" />
			</xform:simpleDataSource>
		</xform:radio>
		<xform:radio onValueChange="onItemTypeChange" property="fdItemType" showStatus="readOnly">
			<xform:simpleDataSource value="public">
				<bean:message bundle="sys-zone" key="zoneAddress.itemType.public" />
			</xform:simpleDataSource>
		</xform:radio>
	</td>
</tr>