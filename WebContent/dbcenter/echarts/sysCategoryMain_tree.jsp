<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.simplecategory.interfaces.SimpleCategoryUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<template:include ref="config.edit" sidebar="no">
<template:replace name="head">
	<style>
		.lui_tree_operation{
			width:100%;
			height:38px;
			border-bottom:1px solid #ccc!important;
			margin-bottom: 10px;
			padding-top: 5px;
			background-color: #eee;
		}
	</style>
</template:replace>
<template:replace name="toolbar">
<div class="lui_tree_operation">
   <ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="7">
	   <ui:button text="${lfn:message('button.add')}"  onclick="addCategory();" order="1" ></ui:button>
	   <ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="2" ></ui:button>
	   <ui:button text="${lfn:message('button.delete')}"  onclick="deleteCategories();" order="3" ></ui:button>
	   <ui:button text="${lfn:message('button.copy')}"  onclick="copyCategory();" order="4" ></ui:button>
    <c:if test="${1!=param.rightFlag}"> <%-- 这里控制有些类别不需要权限控制，传入rightFlag=1，不需要权限 --%>
	   <ui:button text="${lfn:message('dbcenter-echarts:updateCateRight.echart')}"  onclick="updateCateRight();" order="5" ></ui:button>
	   <ui:button text="${lfn:message('dbcenter-echarts:updateCateRight.dbEchartsCustom')}"  onclick="updateCateRight('com.landray.kmss.dbcenter.echarts.model.DbEchartsCustom');" order="5" ></ui:button>
	   <ui:button text="${lfn:message('dbcenter-echarts:updateCateRight.table')}"  onclick="updateCateRight('com.landray.kmss.dbcenter.echarts.model.DbEchartsTable');" order="5" ></ui:button>
	   <ui:button text="${lfn:message('dbcenter-echarts:updateCateRight.chartSet')}"  onclick="updateCateRight('com.landray.kmss.dbcenter.echarts.model.DbEchartsChartSet');" order="5" ></ui:button>
    </c:if>
	   <ui:button text="${lfn:message('button.refresh')}"  onclick="location.reload();" order="6" ></ui:button>
	   <c:import url="/sys/workflow/import/sysWfTemplate_auditorBtn.jsp" charEncoding="UTF-8">
			<c:param name="fdModelName" value="${param.modelName}"/>
			<c:param name="exportExcel" value="${param.exportExcel}"/>
			<c:param name="isCategory" value="true"/>
		</c:import>
		<c:import url="/sys/authorization/sys_cate_area_change/cate_area_change_button_ui.jsp"
			charEncoding="UTF-8">
			<c:param name="modelName" value="${param.modelName}"/>
			<c:param name="mainModelName" value="${param.mainModelName}"/>
			<c:param name="docFkName" value="${param.docFkName}"/>
		</c:import>
   </ui:toolbar>
</div>
<ui:fixed elem=".lui_tree_operation"></ui:fixed>
</template:replace>
<template:replace name="content">
<html:form action="${HtmlParam.actionUrl}">
	<table class="tb_noborder" style="width:100%;">
		<tr>
			<td width="10pt"></td>
			<td>
				<div id=treeDiv class="treediv"></div>
			</td>
		</tr>
	</table>
</html:form>

<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>

	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
	<script>
seajs.use(['lui/jquery','lui/topic','lui/toolbar'], function($,topic,toolbar) {
		
		window.getCookie = function(){   
			var arr,reg=new RegExp("(^| )isopen=([^;]*)(;|$)");   
			if(arr=document.cookie.match(reg)) return unescape(arr[2]);   
			else return null;   
		};
			
		 LUI.ready(function(){
		   var mark=getCookie();
			 if(mark=='open'){
				 var dataInitBtn = toolbar.buildButton({id:'dataInit',order:'1',text:'${lfn:message("sys-datainit:sysDatainitMain.data.export")}',click:'Datainit_Submit()'});
				 LUI('toolbar').addButton(dataInitBtn);
		   }
		  		 
		}); 
		
		window.Datainit_Submit = function(){
			if(!List_CheckSelect())
				return;
			var form = document.${JsParam.formName};
			var url = Com_Parameter.ContextPath + "sys/datainit/sys_datainit_main/sysDatainitMain.do?method=export&formName="+form.name;
			form.action = url;
			form.submit();
		}
	});
	
	var s_contextPath=Com_Parameter.ContextPath;
	if(s_contextPath.length>0){
		s_contextPath = s_contextPath.substring(0,s_contextPath.length-1);
	}
	<%
	    String modelName = request.getParameter("modelName");
	    if (StringUtil.isNotNull(modelName)
			&& SimpleCategoryUtil.isAdmin(modelName)) {
	%>
	function SimpleCategory_OnLoad() { 
		generateTree();
	}
	<%  } else {%>
	function NodeFunc_FetchChildrenByXML(){
		var nodesValue = new KMSSData().AddXMLData(Com_ReplaceParameter(this.XMLDataInfo.beanURL, this)).GetHashMapArray();
		for(var i=0; i<nodesValue.length; i++){
			if(SimpleCategory_CheckAuth(nodesValue[i]))
				this.FetchChildrenUseXMLNode(nodesValue[i]);
		}
	}
	function SimpleCategory_CheckAuth(nodeValue){
		if(nodeValue["isShowCheckBox"]!="0"){
			return true;
		}
		var value = nodeValue["value"];
		for(var i=0; i<SimpleCategory_AuthIds.length; i++){
			if(SimpleCategory_AuthIds[i]["v"]==value){
				return true;
			}
		}
		return false;
	}
	var SimpleCategory_AuthIds;
	function SimpleCategory_OnLoad() { // 过滤没权限或者没有模板的分类节点
		SimpleCategory_AuthIds = new KMSSData().AddBeanData("sysSimpleCategoryAuthList&modelName=${JsParam.modelName}&authType=01").GetHashMapArray();
		generateTree();
	}
	<% } %>
window.onload = SimpleCategory_OnLoad;
var LKSTree;
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	// tree.jsp中传入treeTitle的名称，如果没有就使用默认的名称
	var treeTitle = "${JsParam.treeTitle}";
	if (!treeTitle){
		treeTitle = "<bean:message key="tree.sysSimpleCategory.title" bundle="sys-simplecategory"/>";
		}
	LKSTree = new TreeView("LKSTree", treeTitle, document.getElementById("treeDiv"));
	LKSTree.isShowCheckBox=true;
	LKSTree.isMultSel=true;
	LKSTree.isAutoSelectChildren = false;
	<c:if test="${param.dbClickView=='true'}">
	LKSTree.DblClickNode = viewCategory;
	</c:if>
	var n1, n2;
	n1 = LKSTree.treeRoot;	
	var modelName = "${JsParam.modelName}";
	n1.authType = "01"
	n2 = n1.AppendSimpleCategoryData(modelName);
	LKSTree.Show();
}

function addCategory(){
	var url = s_contextPath + "${JsParam.actionUrl}?method=add";
	var modelName ="${JsParam.modelName}";
	url = Com_SetUrlParameter(url,"fdModelName",modelName);
	var checkedNode = LKSTree.GetCheckedNode();
	if(checkedNode.length>0){
		if(LKSTree.GetCheckedNode().length==1){
			var selectedId = checkedNode[0].value;
			url = Com_SetUrlParameter(url, "parentId", selectedId);
		}else{
			dialog.alert("<bean:message key="error.select.message.add" bundle="sys-simplecategory"/>");
			return false;
		}
	}
	Com_OpenWindow(url);
}

function editCategory(){
	var url = s_contextPath + "${JsParam.actionUrl}?method=edit";
	var modelName = "${JsParam.modelName}";
	url = Com_SetUrlParameter(url,"fdModelName",modelName);
	if(List_CheckSelect()){
		if(LKSTree.GetCheckedNode().length==1){
			var selectedId = LKSTree.GetCheckedNode()[0].value;
			url = Com_SetUrlParameter(url, "fdId", selectedId);
			Com_OpenWindow(url);
		}else{
			dialog.alert("<bean:message key="error.select.message.edit" bundle="sys-simplecategory"/>");
			return false;
		}
	}
}

function viewCategory(id) {
	if(id==null) return false;
	var node = Tree_GetNodeByID(this.treeRoot,id);
	if(node!=null && node.value!=null) {
		var url = "<c:url value="${HtmlParam.actionUrl}" />?method=view&fdModelName=${JsParam.modelName}&fdId="+node.value;
		Com_OpenWindow(url);
	}
	
}

function deleteCategories(){
	if(List_CheckSelect()){
		dialog.confirm("<bean:message key="page.comfirmDelete"/>",function(value){
			if(value==true){
				var selList = LKSTree.GetCheckedNode();
				for(var i=selList.length-1;i>=0;i--){
					var input = document.createElement("INPUT");
					input.type="text";
					input.style.display="none";
					input.name="List_Selected";	
					input.value = selList[i].value;
					document.forms["${JsParam.formName}"].appendChild(input);	
				}
				Com_Submit(document.${JsParam.formName}, 'deleteall');
			}
		});
	}
}

function updateCateRight(mainModelName){
	if(!List_CheckSelect()){
		return;
	}
	var selList = LKSTree.GetCheckedNode();
	var c = 0,s="" ;
	for(var i=selList.length-1;i>=0;i--){
		if(c>0){
			s+=";";
		}
		c++;
		s += selList[i].value;
	}
	if(!mainModelName){
		mainModelName = '${JsParam.mainModelName}';
	}
	var url= s_contextPath + "/sys/right/rightCateChange.do";
	url+="?method=cateRightEdit&cateModelName=${JsParam.modelName}&modelName="+mainModelName;
	<c:if test="${not empty param.authReaderNoteFlag}">
	url+="&authReaderNoteFlag=${JsParam.authReaderNoteFlag}";
	</c:if>
	url+="&fdIds="+s+"&docFkName=${JsParam.docFkName}";
	Com_OpenWindow(url);
}

function List_CheckSelect(){
	var obj = document.getElementsByName("List_Selected");
	if(LKSTree.GetCheckedNode().length>0){
		return true;
	}
	dialog.alert("<bean:message key="page.noSelect"/>");
	return false;
}


function copyCategory(){
	var url = s_contextPath + "${JsParam.actionUrl}?method=copy";
	var modelName = "${JsParam.modelName}";
	url = Com_SetUrlParameter(url,"fdModelName",modelName);
	if(List_CheckSelect()){
		if(LKSTree.GetCheckedNode().length==1){
			var selectedId = LKSTree.GetCheckedNode()[0].value;
			url = Com_SetUrlParameter(url, "fdCopyId", selectedId);
			Com_OpenWindow(url);
		}else{
			dialog.alert("<bean:message key="error.select.message.copy" bundle="sys-simplecategory"/>");
			return false;
		}
	}
}
Tree_IncludeCSSFile();
</script>
</template:replace>
</template:include>
