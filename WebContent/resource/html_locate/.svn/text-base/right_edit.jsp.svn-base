<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="rightForm" value="${requestScope[param.formName]}" />
<ui:content title="${ lfn:message('sys-right:right.moduleName') }">
	<table class="tb_normal" width=100%>
		<tr>
			<td class="td_normal_title" width="15%">
				<bean:message bundle="km-archives" key="kmArchivesMain.authFileReaders" />
			</td>
			<td width="85%">
				<xform:address textarea="true" mulSelect="true" propertyId="authFileReaderIds" propertyName="authFileReaderNames" style="width:97%;height:90px;" ></xform:address>
			</td>
		</tr>
	</table>
</ui:content>
