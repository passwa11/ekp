<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>

<style>
.subTable {
	border:none ! important;
}
.subTable td,.subTable tr {
	border:none ! important;
}
.subTable a{
	cursor: pointer;
	font-size: 12px;
} 
</style>

<table id="TABLE_PRINT" class="tb_normal subTable" width=100% align="center" frame=void>
	<tr ischecked="true">
		<td>
			<input type="hidden" name="MyfdId" value="<c:out value="${sysPrintTemplateForm.fdId}" />" />
			<input type="hidden" name="MyfdName" value="<c:out value="${sysPrintTemplateForm.fdName}" />" />
			<a style="font-weight:bold;color:#47b5e6;" onclick="SubPrint_Click(this);"><c:out value="${sysPrintTemplateForm.fdName}" /></a>
			<input type="hidden" name="MyfdTmpXml" value="<c:out value="${sysPrintTemplateForm.fdTmpXml}" />" />
		</td>
	</tr>
	<c:forEach items="${sysPrintTemplateForm.fdSubTemplates}"  var="item" varStatus="vstatus">
		<tr KMSS_IsContentRow="1" ischecked="false"> 
			<td>
				<input type="hidden" name="MyfdId" value="<c:out value="${item.fdId}" />" />
				<input type="hidden" name="MyfdName" value="<c:out value="${item.fdName}" />" />
				<a style="font-weight:bold;" onclick="SubPrint_Click(this);"><c:out value='${item.fdName}'/></a>
				<input type="hidden" name="MyfdTmpXml" value="<c:out value="${item.fdTmpXml}" />" />
			</td>
		</tr>
	</c:forEach>
</table>

<script>
$(function(){
	if(typeof xform_subform_fdMode === "undefined"){
		xform_subform_fdMode = "1";
	}
	var fdPrintMode = "${sysPrintTemplateForm.fdPrintMode}";
	if((xform_subform_fdMode=="<%=XFormConstant.TEMPLATE_SUBFORM%>" || xform_subform_fdMode=="<%=XFormConstant.TEMPLATE_DEFINE%>") && fdPrintMode!='2'){
		$("#DIV_SubPrint_View").show();
		$("#DIV_SubPrintTep_View").css("width","87%");
		$("#DIV_SubPrintTep_View").css("float","right");
	}
});

function SubPrint_Click(dom){
	//切换前选中的模板
	var tr = $("#TABLE_PRINT").find("tr[ischecked='true']");
	$("#TABLE_PRINT").find("tr").each(function(){
		if($(this)[0]!=$(dom).parents("tr[ischecked]")[0]){
			$(this).attr("ischecked","false");
			$(this).find("a").css("color","");
		}else{
			$(this).find("a").css("color","#47b5e6");
			$(this).attr("ischecked","true");
		}
	});
	//切换后选中的模板
	var tr2 = $("#TABLE_PRINT").find("tr[ischecked='true']");
	var html = tr2.find("input[name='MyfdTmpXml']").val();
	//设置html
	$('#sysPrintdesignPanelTd').html(html);
	var myHeight = $("#DIV_SubPrintTep_View").outerHeight(false);
	$("#DIV_SubPrint_View").css("height",myHeight-2);
	//刷新版本
	var div = $("#sysPrintTemplateChangeHistoryDiv");
	if(!div.is(":hidden") && tr[0]!=tr2[0]) {
		sysPrintEditionListReLoad(div);
	}
}

function SubPrint_OnLabelSwitch_${JsParam.fdKey}(tableName,index){
	var trs = document.getElementById(tableName).rows;
	if(trs[index].id =="sysPrint_tab"){
		setTimeout(function (){
			var myHeight = $("#DIV_SubPrintTep_View").outerHeight(false);
			$("#DIV_SubPrint_View").css("height",myHeight-2);
		},0);
	}
}

function Print_getSubPrintViewInfo_${JsParam.fdKey}() {
	var subObj = [];
	if (xform_subform_fdMode == '<%=XFormConstant.TEMPLATE_SUBFORM%>') {
		$("#TABLE_PRINT").find("tr[ischecked]").each(function(i) {
			var subformObj = {};
			if(i==0){
				subformObj.id='default'
				subformObj.name='默认打印模板';
			}else{
				subformObj.id=$(this).find("input[name$='fdId']").val();
				subformObj.name=$(this).find("input[name$='fdName']").val();
			}
			subObj.push(subformObj);
		});
	}
	return subObj;
}
</script>