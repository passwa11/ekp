<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.category.interfaces.CategoryUtil" %>
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
			background-color: #f6f6f6;
			color: #101F2C;
		}
	</style>
</template:replace>
<template:replace name="toolbar">
<div  class="lui_tree_operation">
   <ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="3">
	   <ui:button text="${lfn:message('button.add')}"  onclick="addCategory();" order="2" ></ui:button>
	   <ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="3" ></ui:button>
	   <ui:button text="${lfn:message('button.delete')}"  onclick="deleteCategories();" order="4" ></ui:button>
	   <%-- <ui:button text="${lfn:message('button.copy')}"  onclick="copyCategory();" order="5" ></ui:button>
	   <c:if test="${param.modelName!=null && param.mainModelName!=null}">
		<kmss:auth requestURL="/sys/right/cchange_cate_right/cchange_cate_right.jsp?tmpModelName=${param.modelName}&mainModelName=${param.mainModelName}" requestMethod="GET">
			<ui:button text="${lfn:message('sys-right:right.button.changeRightBatch')}"  onclick="updateCateRight();" order="6" ></ui:button>
		</kmss:auth>
		</c:if>
	   <ui:button text="${lfn:message('button.refresh')}"  onclick="location.reload();" order="7" ></ui:button>
	   <c:import url="/sys/workflow/import/sysWfTemplate_auditorBtn.jsp" charEncoding="UTF-8">
			<c:param name="fdModelName" value="${param.modelName}"/>
	       <c:param name="byCategory" value="true"/>
		</c:import>
		<c:import url="/sys/authorization/sys_cate_area_change/cate_area_change_button_new.jsp"
			charEncoding="UTF-8">
			<c:param name="modelName" value="${param.modelName}"/>
			<c:param name="mainModelName" value="${param.mainModelName}"/>
			<c:param name="docFkName" value="${param.docFkName}"/>
		</c:import> --%>
   </ui:toolbar>
</div>
<ui:fixed elem=".lui_tree_operation"></ui:fixed>
</template:replace>
<template:replace name="content">
<html:form action="/sys/category/sys_category_main/sysCategoryMain.do">
	<table class="tb_noborder"  style="width:100%;">
		<tr>
			<td width="10pt"></td>
			<td>
				<div id=treeDiv class="treediv" style="margin-bottom:50px"></div>
			</td>
		</tr>
	</table>
	<input type='hidden' id='fdIds'>
</html:form>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
	<script type="text/javascript">Com_IncludeFile("treeview_ding.js");</script>
	<script>
	seajs.use(['lui/jquery','lui/topic','lui/toolbar','lui/dialog'], function($,topic,toolbar,dialog) {
		
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
			var form = document.forms[0];
			var url = Com_Parameter.ContextPath + "sys/datainit/sys_datainit_main/sysDatainitMain.do?method=export&formName="+form.name;
			form.action = url;
			form.submit();
		}
		window.editCategory = function(){
			var url = s_contextPath + "/sys/category/sys_category_main/sysCategoryMain.do?method=edit&mainModelName=${JsParam.mainModelName}";
			if(List_CheckSelect()){
				if(LKSTree.GetCheckedNode().length==1){
					var selectedId = LKSTree.GetCheckedNode()[0].value;
					url = Com_SetUrlParameter(url, "fdId", selectedId);
					Com_OpenWindow(url);
				}else{
					dialog.alert("<bean:message key="error.select.message.edit" bundle="sys-category"/>");
					return false;
				}
			}
		}
		
		window.addCategory = function(){
			var url = s_contextPath + "/sys/category/sys_category_main/sysCategoryMain.do?method=add";
			var modelName = Com_GetUrlParameter(location.href,"modelName");
			url = Com_SetUrlParameter(url,"fdModelName",modelName);
			var checkedNode = LKSTree.GetCheckedNode();
			if(checkedNode.length>0){
				if(checkedNode[0].nodeType=="CATEGORY_SON"){
					dialog.alert("<bean:message key="error.illegalCreateCategory" bundle="sys-category"/>");
					return false;
				}else{
					if(LKSTree.GetCheckedNode().length==1){
						var selectedId = checkedNode[0].value;
						url = Com_SetUrlParameter(url, "parentId", selectedId);
					}else{
						dialog.alert("<bean:message key="error.select.message.add" bundle="sys-category"/>");
						return false;
					}
				}
			}
			Com_OpenWindow(url);
		}
		
		window.updateCateRight = function(){
			if(!List_CheckSelect()){
				return;
			}
			var selList = LKSTree.GetCheckedNode();
			var c = 0,s="" ;
			for(var i=selList.length-1;i>=0;i--){
				if(c>0){
					s+=",";
				}
				c++;
				s += selList[i].value;
			}
			document.getElementById('fdIds').value = s;
			var url=s_contextPath + "/sys/right/cchange_cate_right/cchange_cate_right.jsp";
			url+="?method=cChangeCateRight&tmpModelName=${JsParam.modelName}&mainModelName=${JsParam.mainModelName}";
			url+="&templateName=${JsParam.templateName}&categoryName=${JsParam.categoryName}&authReaderNoteFlag=${JsParam.authReaderNoteFlag}";
			//Com_OpenWindow(url);
			window.open(url);
		}
		window.viewCategory = function(id) {
			if(id==null) return false;
			var node = Tree_GetNodeByID(this.treeRoot,id);
			if(node!=null && node.value!=null) {
				if(node.isDingCategory){
					dialog.alert("<bean:message key="error.select.message.ding" bundle="sys-category"/>");
				}else{
					var url = s_contextPath + "/sys/category/sys_category_main/sysCategoryMain.do?method=view&fdId="+node.value;
					Com_OpenWindow(url);
				}
			}
		}

		window.deleteCategories = function(){
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
							document.sysCategoryMainForm.appendChild(input);	
						}
						Com_Submit(document.sysCategoryMainForm, 'deleteall');	
					}
				});
			}
		}
		window.List_CheckSelect = function(){
			var obj = document.getElementsByName("List_Selected");
			if(LKSTree.GetCheckedNode().length>0){
				return true;
			}
			dialog.alert("<bean:message key="page.noSelect"/>");
			return false;
		} 


		window.copyCategory = function(){
			var url = s_contextPath + "/sys/category/sys_category_main/sysCategoryMain.do?method=copy&fdModelName=${JsParam.modelName}";
			if(List_CheckSelect()){
				if(LKSTree.GetCheckedNode().length==1){
					var selectedId = LKSTree.GetCheckedNode()[0].value;
					url = Com_SetUrlParameter(url, "fdCopyId", selectedId);
					Com_OpenWindow(url);
				}else{
					dialog.alert("<bean:message key="error.select.message.edit" bundle="sys-category"/>");
					return false;
				}
			}
		}
		
	});
	
	var s_contextPath=Com_Parameter.ContextPath;
	if(s_contextPath.length>0){
		s_contextPath = s_contextPath.substring(0,s_contextPath.length-1);
	}
	<%
	    String modelName = request.getParameter("modelName");
	    if (StringUtil.isNotNull(modelName)
			&& CategoryUtil.isAdminRole(modelName)) {
	%>
	function Category_OnLoad() { 
		generateTree();
	}
	<%  } else {%>
	function NodeFunc_FetchChildrenByXML(){
		var nodesValue = new KMSSData().AddXMLData(Com_ReplaceParameter(this.XMLDataInfo.beanURL, this)).GetHashMapArray();
		for(var i=0; i<nodesValue.length; i++){
			if(Category_CheckAuth(nodesValue[i]))
				this.FetchChildrenUseXMLNode(nodesValue[i]);
		}
	}
	function Category_CheckAuth(nodeValue){
		if(nodeValue["isShowCheckBox"]!="0"){
			return true;
		}
		var value = nodeValue["value"];
		for(var i=0; i<Category_AuthIds.length; i++){
			if(Category_AuthIds[i]["v"]==value){
				return true;
			}
		}
		return false;
	}
	var Category_AuthIds;
	function Category_OnLoad() { // 过滤没权限或者没有模板的分类节点
		Category_AuthIds = new KMSSData().AddBeanData("sysCategoryAuthListService&modelName=${JsParam.modelName}").GetHashMapArray();
		generateTree();
	}
	<% } %>
window.onload = Category_OnLoad;
var LKSTree;
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message key="tree.sysCategoryMain.title" bundle="sys-category"/>", document.getElementById("treeDiv"));
	LKSTree.isShowCheckBox=true;
	LKSTree.isMultSel=true;
	LKSTree.isAutoSelectChildren = false;
	LKSTree.DblClickNode = viewCategory;
	var n1, n2;
	n1 = LKSTree.treeRoot;	
	var modelName = Com_GetUrlParameter(location.href,"modelName");
	n1.authType = "01"
	n2 = n1.AppendCategoryData(modelName,null,0,0);
	LKSTree.Show();
}
Tree_IncludeCSSFile();
</script>
</template:replace>
</template:include>
