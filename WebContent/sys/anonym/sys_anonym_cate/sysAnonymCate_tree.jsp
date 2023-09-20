<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.anonym.util.SysAnonymUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<style>
	.lui_tree_operation {
	   width:100%;
	   height:38px;
	   border-bottom:1px solid #ccc!important;
	   margin-bottom: 10px;
	   padding-top: 5px;
	   background-color: #eee;
	}
</style>
<template:include ref="config.edit" sidebar="no">
	<template:replace name="toolbar">
		<div  class="lui_tree_operation">
		   <ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="7">
			   <ui:button text="${lfn:message('button.add')}"  onclick="addCategory();" order="2" ></ui:button>
			   <ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="3" ></ui:button>
			   <ui:button text="${lfn:message('button.delete')}"  onclick="deleteCategories();" order="4" ></ui:button>
			   <ui:button text="${lfn:message('button.copy')}"  onclick="copyCategory();" order="5" ></ui:button>
			   <ui:button text="${lfn:message('button.refresh')}"  onclick="location.reload();" order="7" ></ui:button>
				<c:import url="/sys/authorization/sys_cate_area_change/cate_area_change_button_new.jsp" charEncoding="UTF-8">
					<c:param name="modelName" value="${param.modelName}"/>
				</c:import>
		   </ui:toolbar>
		</div>
		<ui:fixed elem=".lui_tree_operation"></ui:fixed>
	</template:replace>
<template:replace name="content">
	<html:form action="/sys/anonym/sys_anonym_cate/sysAnonymCate.do">
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
	<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
	<script>
			seajs.use(['lui/jquery','lui/topic','lui/toolbar', 'lui/dialog'], function($,topic,toolbar, dialog) {
				window.getCookie = function(){   
					var arr,reg = new RegExp("(^| )isopen=([^;]*)(;|$)");   
					if (arr = document.cookie.match(reg)) {
						return unescape(arr[2]);   
					} else {
						return null;   
					}
				};
					
			    LUI.ready(function(){
				   var mark = getCookie();
					 if(mark == 'open'){
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
				
				window.addCategory = function(){
					var url = s_contextPath + "/sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=add";
					var modelName = Com_GetUrlParameter(location.href,"modelName");
					url = Com_SetUrlParameter(url,"fdModelName",modelName);
					var checkedNode = LKSTree.GetCheckedNode();
					if (checkedNode.length > 0) {
						if (checkedNode[0].nodeType=="CATEGORY_SON"){
							dialog.alert('<bean:message key="error.illegalCreateCategory" bundle="sys-anonym"/>');
							return false;
						} else {
							if (LKSTree.GetCheckedNode().length==1) {
								var selectedId = checkedNode[0].value;
								url = Com_SetUrlParameter(url, "parentId", selectedId);
							} else {
								dialog.alert('<bean:message key="error.select.message.add" bundle="sys-anonym"/>');
								return false;
							}
						}
					}
					Com_OpenWindow(url);
				}
				
				window.editCategory = function(){
					var url = s_contextPath + "/sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=edit&mainModelName=${JsParam.mainModelName}";
					if (List_CheckSelect()) {
						if (LKSTree.GetCheckedNode().length == 1) {
							var selectedId = LKSTree.GetCheckedNode()[0].value;
							url = Com_SetUrlParameter(url, "fdId", selectedId);
							Com_OpenWindow(url);
						} else {
							dialog.alert("<bean:message key="error.select.message.edit" bundle="sys-anonym"/>");
							return false;
						}
					}
				}
				
				window.deleteCategories = function(){
					if(List_CheckSelect()){
						dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value){
							if(value==true){
								var selList = LKSTree.GetCheckedNode();
								for(var i = selList.length - 1;i >= 0;i--){
									var input = document.createElement("INPUT");
									input.type="text";
									input.style.display="none";
									input.name="List_Selected";	
									input.value = selList[i].value;
									document.sysAnonymCateForm.appendChild(input);	
								}
								Com_Submit(document.sysAnonymCateForm, 'deleteall');	
							}
						});
					}
				}

				window.List_CheckSelect = function(){
					var obj = document.getElementsByName("List_Selected");
					if(LKSTree.GetCheckedNode().length > 0){
						return true;
					}
					dialog.alert('<bean:message key="page.noSelect"/>');
					return false;
				} 
				
				window.copyCategory = function(){
					var url = s_contextPath + "/sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=copy&fdModelName=${JsParam.modelName}";
					if (List_CheckSelect()){
						if (LKSTree.GetCheckedNode().length == 1){
							var selectedId = LKSTree.GetCheckedNode()[0].value;
							url = Com_SetUrlParameter(url, "fdCopyId", selectedId);
							Com_OpenWindow(url);
						} else {
							dialog.alert('<bean:message key="error.select.message.edit" bundle="sys-anonym"/>');
							return false;
						}
					}
				}
				
			});
	
			var s_contextPath = Com_Parameter.ContextPath;
			if(s_contextPath.length > 0){
				s_contextPath = s_contextPath.substring(0, s_contextPath.length - 1);
			}
			
			function Category_OnLoad() { 
				generateTree();
			}
			
			window.onload = Category_OnLoad;
			
			var LKSTree;
			function generateTree() {
				LKSTree = new TreeView('LKSTree', '<bean:message key="table.sysAnonymCate" bundle="sys-anonym"/>', document.getElementById("treeDiv"));
				LKSTree.isShowCheckBox=true;
				LKSTree.isMultSel=true;
				LKSTree.isAutoSelectChildren = false;
				LKSTree.DblClickNode = viewCategory;
				var n1, n2;
				n1 = LKSTree.treeRoot;	
				n2 = n1.AppendBeanData("sysAnonymCateService&parentId=!{value}&modelName=${param.modelName}&showType=edit");
				LKSTree.Show();
			}

			
			function viewCategory(id) {
				if(id == null) {
					return false;
				}
				var node = Tree_GetNodeByID(this.treeRoot, id);
				if(node!= null && node.value!=null) {
					var url = s_contextPath + "/sys/anonym/sys_anonym_cate/sysAnonymCate.do?method=view&fdId="+node.value;
					Com_OpenWindow(url);
				}
			}

			Tree_IncludeCSSFile();
		</script>
	</template:replace>
</template:include>
