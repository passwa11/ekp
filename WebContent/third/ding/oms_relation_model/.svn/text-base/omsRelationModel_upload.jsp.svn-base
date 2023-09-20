<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<ui:button text="${ lfn:message('button.submit') }" onclick="loadExcel();"></ui:button>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="content">
	
<html:form action="/third/ding/oms_relation_model/omsRelationModel.do?method=loadExcel" enctype="multipart/form-data">
 
<p class="txttitle"><bean:message bundle="third-ding" key="omsRelationModel.file.batch.upload"/></p>

<center>
<table class="tb_normal" width=95%> 
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ding" key="omsRelationModel.file.upload"/>
		</td><td width="35%" colspan="3">
			<html:file property="file" style="width:35%" styleClass="lui_form_button" />
		</td>
	</tr>
</table>
<iframe name="file_frame" style="display:none;"></iframe>
<br><div id="einfo" style="width: 95%;text-align: left;"></div>
</center>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();
</script>
<script>
//上传excel文件
function loadExcel(){
	var fileName = document.getElementsByName("file")[0];
	if(fileName&&fileName.value==""){
		alert("未选择需要导入的文件，请选择文件");
		return false;
	}
	if(fileName&&fileName.value!=""&&fileName.value.lastIndexOf("xls")!=(fileName.value.length-3)){
		alert("请选择xls文件");
		return false;
	}
	var form=document.getElementsByName("omsRelationModelForm")[0];
	//修改action的值为excel上传地址
	form.target="file_frame";
	form.submit();
	$("#einfo").empty();
}
//提交出错
function uploadSucess(){
	alert("${lfn:message('third-ding:omsRelationModel.file.upload.sucess')}");
}
//提交出错
function loadError(){
	alert("${lfn:message('third-ding:omsRelationModel.tip.upload.error')}");
}
//excel文件上传完毕添加明细、还原action值
function callback(result){
	var data = eval(result);
	var error = "";
	if(data.length>0){
		for(var i=0;i<data.length;i++){
			error += data[i].msg+"<br>";
		}
	}
	$("#einfo").html(error);
}
</script>
</html:form>

	</template:replace>
</template:include>