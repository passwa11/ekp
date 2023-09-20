<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js", null, "js");
Com_IncludeFile("jquery.js");
Com_Parameter.event["submit"].push(function() {
	var fdName = $("input[name='fdName']").val();
	if (fdName == "") {
		alert("类别名称 不能为空");
		return false;
	}
	return true;
});
</script>

<p class="txttitle"><bean:message bundle="tic-core-common"
		key="table.ticCoreBusiCate" /></p>

<html:form action="/tic/business/connector/tic_business_category/ticBusinessCategory.do">
		<c:import url="/sys/simplecategory/sys_simple_category/sysCategoryMain_edit_button.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="ticBusinessCategoryForm" />
		</c:import>
<center>
<table class="Label_Tabel" width="95%">
	<c:import url="/sys/simplecategory/include/sysCategoryMain_edit.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="ticBusinessCategoryForm" />
		<c:param name="requestURL" value="tic/business/connector/tic_business_category/ticBusinessCategory.do?method=add" />
		<c:param name="fdModelName" value="${param.fdModelName}" />
	</c:import>
</table>
</center>  
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
	//隐藏分类中可维护者、可使用者
    $(function(){
    	$(".Label_Tabel table tr:nth-child(5)").hide();
    	$(".Label_Tabel table tr:nth-child(6)").hide();
    });
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>