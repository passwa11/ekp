<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<html:form action="/tic/soap/connector/tic_soap_category/ticSoapCategory.do">
	<div id="optBarDiv"><%-- 新建 --%> <input type="button"
		value="<bean:message key="button.save"/>" onclick="window.frames['iframeName'].saveAdd();" />
	<%-- 编辑 --%> <input type="button"
		value="<bean:message key="button.edit"/>" onclick="editCategory();" />
	<%-- 删除 --%> <input type="button"
		value="<bean:message key="button.delete"/>"
		onclick="deleteCategories();" /> <%-- 刷新 --%> <input type="button"
		value="<bean:message key="button.refresh"/>" onclick="location.reload();">
	</div>
	 <div style="width: 100%">
	 <iframe id='iframeSearch' name="iframeName" width=100%
		height=120px frameborder=0 scrolling=no marginheight="0"> 
	 </iframe></div>
	<table class="tb_noborder">
		<tr>
			<td width="10pt"></td>
			<td>
			<div id=treeDiv class="treediv"></div>
			</td>
		</tr>
	</table>
	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
	<script>	
window.onload = generateTree;
var LKSTree;
//Com_Parameter.XMLDebug = true;

//打开页面展开分类树
 function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message key="table.ticSoapCategory" bundle="tic-soap-connector"/>", document.getElementById("treeDiv"));
	LKSTree.isShowCheckBox=true;
	LKSTree.isMultSel=true;
	LKSTree.isAutoSelectChildren = false;
	var n1, n2;
	n1 = LKSTree.treeRoot;
	n2 = n1.AppendBeanData("ticSoapCategoryTreeService&parentId=!{value}");
	//TODO 数据显示不出来
	LKSTree.Show();
	  var  iframeObj=document.getElementById("iframeSearch");//搜索结果页面
 	var url="<c:url value='/tic/soap/connector/tic_soap_category/ticSoapCategory.do?method=add'/>"; 
	iframeObj.src=url;
}
 
//Com_Parameter.XMLDebug = true;
//新建
    function addCategory(){
	var url = "<c:url value="/tic/soap/connector/tic_soap_category/ticSoapCategory.do" />?method=add";
	var modelName = Com_GetUrlParameter(location.href,"modelName");
	url = Com_SetUrlParameter(url,"fdModelName",modelName);
	var checkedNode = LKSTree.GetCheckedNode();
	if(checkedNode.length>0){
	   if(checkedNode.length>1)
	   {
	   alert("<bean:message key="error.messageone" bundle="tic-soap-connector"/>");
	   return false;
	   }
		if(checkedNode[0].nodeType=="CATEGORY_SON"){
			alert("<bean:message key="error.illegalCreateCategory" bundle="sys-category"/>");
			return false;
		}else{
			var selectedId = checkedNode[0].value;
			url = Com_SetUrlParameter(url, "parentId", selectedId);
		}
	}
	var  iframeObj=document.getElementById("iframeSearch");
    iframeObj.src=url;  
	//Com_OpenWindow(url);
}
//编辑
function editCategory(){
	var url = "<c:url value="/tic/soap/connector/tic_soap_category/ticSoapCategory.do" />?method=edit&mainModelName=${param.mainModelName}";
	var checkedNode = LKSTree.GetCheckedNode();
	if(List_CheckSelect()){
	if(checkedNode.length>1)
	   {
	   alert("<bean:message key="error.messageone" bundle="tic-soap-connector"/>");
	   return false;
	   }
		var selectedId = LKSTree.GetCheckedNode()[0].value;
		url = Com_SetUrlParameter(url, "fdId", selectedId);
		var  iframeObj=document.getElementById("iframeSearch");
        iframeObj.src=url; 
		}
	 
}
function viewCategory(id) {
	if(id==null) return false;
	var node = Tree_GetNodeByID(this.treeRoot,id);
	if(node!=null && node.value!=null) {
		var url = "<c:url value="/tic/soap/connector/tic_soap_category/ticSoapCategory.do" />?method=view&fdId="+node.value;
		Com_OpenWindow(url);
	}
}
// 删除
function deleteCategories(){
	if(!List_ConfirmDel())return;
	var selList = LKSTree.GetCheckedNode();
	for(var i=selList.length-1;i>=0;i--){
		var input = document.createElement("INPUT");
		input.type="text";
		input.style.display="none";
		input.name="List_Selected_Node";	
		input.value = selList[i].value;
		document.ticSoapCategoryForm.appendChild(input);	
		
	}
	Com_Submit(document.ticSoapCategoryForm, 'deleteall');	
}
//选择
function List_CheckSelect(){
	var obj = document.getElementsByName("List_Selected");
	if(LKSTree.GetCheckedNode().length>0){
		return true;
	}
	alert("<bean:message key="page.noSelect"/>");
	return false;
}
function List_ConfirmDel(){
	return List_CheckSelect() && confirm("<bean:message key="page.comfirmDelete"/>");
}

 
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>