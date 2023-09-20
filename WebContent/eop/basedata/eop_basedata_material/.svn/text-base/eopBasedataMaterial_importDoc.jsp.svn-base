<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="java.util.*"%>
<script type="text/javascript">
	Com_IncludeFile("docutil.js|calendar.js|dialog.js|optbar.js");
	function checkFile(){
		var file = document.getElementsByName("file");
		if(file[0].value==null || file[0].value.length==0){
			alert('请选择文件');
			return false ;
		}
		return true ;
	}
</script>
<style>
	.esp-export-main #_xform_fdUserType {
		display: inline-block;
	}
	.esp-export-main {
		text-align: center;
	}
	.esp-export-button :hover{
		cursor:pointer;
	}
	.esp-export-button input {
		background-color: #4285f4;
		padding: 5px 10px;
		outline: none;
		border: 1px solid #4285f4;
		color: #fff;
		border-radius: 2px;
		margin-top: 20px;
		margin-right: 10px;
	}
	.esp-export-upload :hover {
		cursor:pointer;
	}
</style>

<html:form action="/eop/basedata/eop_basedata_material/eopBasedataMaterial.do" enctype="multipart/form-data">
	<div class="esp-export-main">
	<div class="esp-export-form">
		<div class="esp-export-upload"><html:file property="file" /></div>
		<html:hidden property="method_GET" />
	</div>
		<div class="esp-export-button">
			<input
					type=button
					value="下载模板"
					onclick="Com_OpenWindow('<c:url value="/eop/basedata/eop_basedata_material/MaterialImportTemplate.xls" />');">
			<input
					type=button
					value="上传"
					onclick="Com_Submit(document.eopBasedataMaterialForm, 'saveExcel');">
		</div>
	</div>

</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
