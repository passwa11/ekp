<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/hr/ratify/fieldlayout/common/param_parser.jsp"%>
<span class="xform_fieldlayout" style="<%=parse.getStyle()%>">
	<xform:radio property="fdPositiveFlag" showStatus="view">
		<xform:enumsDataSource enumsType="common_yesno" />
	</xform:radio>
</span>