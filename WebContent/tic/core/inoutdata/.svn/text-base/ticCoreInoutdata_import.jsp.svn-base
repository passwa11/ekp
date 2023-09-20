<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script type="text/javascript">
var LKSTree;
window.onload = function() {
	generateTree();
};
function generateTree(){
	LKSTree = new TreeView("LKSTree", '<bean:message bundle="tic-core-inoutdata" key="ticCoreInoutdata.import.title"/>', document.getElementById("treeDiv"));
	var n1, n2, n3;
	LKSTree.isShowCheckBox=true;
	LKSTree.isMultSel=true;
	LKSTree.isAutoSelectChildren = true;	<%-- 选择父节点是否自动选中子节点 --%>
	n1 = LKSTree.treeRoot;
	n1.value = " ";
	var json=eval(${filePaths});
	for(var i=0; i<json.length; i++) {
		n2 = n1.AppendChild(json[i].text);
		n2.value = " ";
		for(var j=0; j<json[i].children.length; j++) {
			n3=n2.AppendChild(json[i].children[j].text);
			n3.value=json[i].children[j].value;
		}
	}
	LKSTree.Show();
}
function submitForm(){
	var form = document.getElementsByName('ticCoreInoutdataForm')[0];
	if(List_CheckSelect()){
		form.submit();
	}
}
function List_CheckSelect(){
	var selList = LKSTree.GetCheckedNode();
	for(var i=selList.length-1;i>=0;i--){
		var input = document.createElement("INPUT");
		input.type="text";
		input.style.display="none";
		input.name="Inoutdata_List_Selected";	
		input.value = selList[i].value;
		document.ticCoreInoutdataForm.appendChild(input);	
	}
	if(selList.length>0){
		return true;
	}
	alert('<bean:message key="page.noSelect"/>');
	return false;
}

</script>
<form action="<c:url value="/tic/core/inoutdata/ticCoreInoutdata.do?method=startImport&fdAppType=${JsParam.fdAppType}" />" name="ticCoreInoutdataForm" method="POST">
	
	<div id="optBarDiv">
		<input type=button value="<bean:message bundle="tic-core-inoutdata" key="ticCoreInoutdata.import" />"
			onclick="submitForm();">
	</div>
	
	<table class="tb_noborder">
		<tr>
			<td width="10pt"></td>
			<td>
			<div id=treeDiv class="treediv"></div>
			</td>
		</tr>
	</table>
	
	<table class="tb_noborder">
		<tr>
			<td width="10pt"></td>
			<td>
				<label><input type="checkbox" name="isUpdate" value="true" checked="checked" /><bean:message bundle="tic-core-inoutdata" key="ticCoreInoutdata.import.isUpdate" /></label>
				<bean:message bundle="tic-core-inoutdata" key="ticCoreInoutdata.import.isUpdate.info" />
			</td>
		</tr>
		<tr>
			<td width="10pt"></td>
			<td>
				<label><input type="checkbox" name="isImportRequired" value="true" /><bean:message bundle="tic-core-inoutdata" key="ticCoreInoutdata.import.isImportRequired" /></label>
				<bean:message bundle="tic-core-inoutdata" key="ticCoreInoutdata.import.isImportRequired.info" />
			</td>
		</tr>
	</table>
	
</form>

<%@ include file="/resource/jsp/edit_down.jsp"%>
