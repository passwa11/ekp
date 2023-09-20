<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/print.css"/>" />
<style type="text/css">
	.tb_normal TD{
		border:1px #000 solid;
	}
	.tb_normal{
		border:1px #000 solid;
	}
	.tb_noborder{
	border:0px;
	}
	.tb_noborder TD{
	border:0px;
	}
	table td {
		color: #000;
	}
	.tb_normal > tbody > tr > td{
		border: 1px #000 solid !important;
	}
</style>
<table class="tb_normal" style="width:100%">
	<tr>
		<td class="td_normal_title" colspan="6">${lfn:message('fssc-expense:py.BiaoDanNeiRong')}</td>
	</tr>
	<tr>
		<td colspan="6">
			<c:import url="/sys/xform/include/sysForm_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscExpenseMainForm" />
				<c:param name="fdKey" value="fsscExpenseMain" />
				<c:param name="useTab" value="false" />
			</c:import>
		</td>
	</tr>
</table>
<script type="text/javascript">
Com_IncludeFile("jquery.js");
Com_IncludeFile("Number.js", "${LUI_ContextPath}/fssc/common/resource/js/", 'js', true);
</script>
<%@ include file="/resource/jsp/view_down.jsp"%>