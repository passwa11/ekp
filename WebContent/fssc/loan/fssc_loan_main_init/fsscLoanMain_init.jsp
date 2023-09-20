<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
function validateForm() {
	if (document.fsscLoanMainForm.uploadFile.value == "") {
		seajs.use(['lui/jquery','lui/dialog'], function($, dialog) {
			dialog.alert("${lfn:message('fssc-loan:fsscLoanMain.init.excel.null')}")
		});
		return false;
	}
	document.getElementById("loadingImg").style.display = "";
	return true;
}

</script>
<html:form action="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=saveImport" 
	enctype="multipart/form-data" onsubmit="return validateForm(this);">
<div id="optBarDiv">
		<kmss:auth requestURL="/fssc/loan/fssc_loan_main/fsscLoanMain.do?method=saveImport">
			<input type="submit" value="<bean:message key="button.submit"/>">
		</kmss:auth>
		<input type="button" value="<bean:message bundle="fssc-loan" key="fsscLoanMain.init.downloadTemplate"/>" 
			onclick="Com_OpenWindow('<c:url value="/fssc/loan/fssc_loan_main_init/fsscLoanMain_init.xls" />','_blank');">
</div>

<p class="txttitle"><bean:message key="py.loanInit" bundle="fssc-loan"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="fssc-loan" key="fsscLoanMain.init.excel.null"/>
		</td><td width=85% colspan="3">
			<input name="uploadFile" type="file" style="width:90%;height: 25px;" class="upload" > 
			<input type="hidden" name="service" value="${param.service}">
			<img src="${KMSS_Parameter_ResPath}style/common/images/loading.gif" border="0" id="loadingImg" align="bottom" style="display: none;"/>
		</td>
	</tr>
	<tr>
		<td colspan="2" >
			<bean:message bundle="fssc-loan" key="fsscLoanMain.init.remind" />
		</td>
	</tr>
</table>
<br/>
</center>
<html:hidden property="method_GET"/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
