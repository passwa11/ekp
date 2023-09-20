<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/view_top.jsp"%>
<div class="txttitle">
<bean:message bundle="sys-xform" key="sysMutliLang.upload.title"/>
</div>
<script>

function checkFile(subject){
	if (/(zip|xls)$/im.test(subject)) {
		return true;
	} else {
		return false;
	}
}

function submitForm(){
	if(document.getElementsByName("initfile")[0].value.length>0){
		if(checkFile(document.getElementsByName("initfile")[0].value)){
			document.forms[0].submit();
			$(document.forms[0]).hide();
			$('#loading').show();
			return;
		}else{
			alert("${lfn:message('sys-xform:sysFormTemplate.lang.fileMustBeExcelOrZip')}");
		}
	}else{
		alert("${lfn:message('sys-xform:sysFormTemplate.lang.dataSourceNotNull')}");	
	}
}
</script>
<form action="${KMSS_Parameter_ContextPath}sys/mutillang/import.do?method=excelImport" method="post" enctype="multipart/form-data" >
	<center>
		<table class="tb_normal" width=95%>				
			<tr>
				<td class="td_normal_title" width=20%>
					<bean:message bundle="sys-datainit" key="sysDatainitMain.select.upload"/>
				</td><td width=60%>
					<span id="_formFilesSpan">
						<input type="file" class="upload" name="initfile" width=100%>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input class="btnopt" type=button value="<bean:message bundle="sys-xform" key="sysMutliLang.upload.button"/>" onclick="submitForm();">
					</span>
				</td>
			</tr>
		</table>
	</center>
</form>
<div align="center" style="display: none;" id="loading">
	<img src="../../../resource/style/common/images/loading.gif" border="0" />
	<bean:message bundle="sys-datainit" key="sysDatainitMain.import.loading"/>
</div>
<%@ include file="/resource/jsp/view_down.jsp"%>
