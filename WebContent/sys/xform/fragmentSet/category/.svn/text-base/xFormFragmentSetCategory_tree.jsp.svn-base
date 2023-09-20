<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="head">
		<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
		<script>	
			window.onload = generateTree;
			var LKSTree;
			
			//打开页面展开分类树
			function generateTree() {
				LKSTree = new TreeView("LKSTree", "<bean:message key="sysFormJdbcDataSetCategory.categoryConfig"  bundle="sys-xform-maindata"/>", document.getElementById("treeDiv"));
				LKSTree.isShowCheckBox=true;
				LKSTree.isMultSel=true;
				LKSTree.isAutoSelectChildren = false;
				var n1, n2;
				n1 = LKSTree.treeRoot;
				n2 = n1.AppendBeanData("sysFormJdbcDataSetCategoryTreeService&parentId=!{value}");//选取树数据的bean
				//TODO 数据显示不出来
				LKSTree.Show();
				var  iframeObj=document.getElementById("iframeSearch");//搜索结果页面
			 	var url="<c:url value='/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do?method=add'/>"; 
				iframeObj.src=url;
			}
			//新建
		    function addCategory() {
			var url = "<c:url value='/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do' />?method=add";
			var modelName = Com_GetUrlParameter(location.href,"modelName");
			url = Com_SetUrlParameter(url,"fdModelName",modelName);
			var checkedNode = LKSTree.GetCheckedNode();
			if(checkedNode.length>0){
			   if(checkedNode.length>1) {
				   alert("<bean:message key="error.messageone" bundle="sys-xform-maindata"/>");
				   return false;
			   }
				if(checkedNode[0].nodeType=="CATEGORY_SON"){
					alert("<bean:message key="error.illegalCreateCategory" bundle="sys-xform-maindata"/>");
					return false;
				}else{
					var selectedId = checkedNode[0].value;
					url = Com_SetUrlParameter(url, "parentId", selectedId);
				}
			}
			var  iframeObj=document.getElementById("iframeSearch");
		    iframeObj.src=url;  
			}
			//编辑
			function editCategory() {
				var url = "<c:url value='/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do' />?method=edit&mainModelName=${JsParam.mainModelName}";
				var checkedNode = LKSTree.GetCheckedNode();
				if(List_CheckSelect()){
					if(checkedNode.length>1) {
				   		alert("<bean:message key="error.messageone" bundle="sys-xform-maindata"/>");
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
					var url = "<c:url value='/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do' />?method=view&fdId="+node.value;
					Com_OpenWindow(url);
				}
			}
			// 删除
			function deleteCategories(){
				var selList = LKSTree.GetCheckedNode();
				for(var i=selList.length-1;i>=0;i--){
					var input = document.createElement("INPUT");
					input.type="text";
					input.style.display="none";
					input.name="List_Selected_Node";	
					input.value = selList[i].value;
					document.sysFormJdbcDataSetCategoryForm.appendChild(input);	
					
				}
				Com_Submit(document.sysFormJdbcDataSetCategoryForm, 'deleteall');	
			}
			//选择
			function List_CheckSelect(){
				var obj = document.getElementsByName("List_Selected");
				if(LKSTree.GetCheckedNode().length>0){
					return true;
				}
					seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
						dialog.alert('<bean:message key="page.noSelect"/>', function() {
						});
				   return false;
				});
			}
			function List_ConfirmDel(){
				if(List_CheckSelect()){
				seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
					dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(flag, d) {
						if (flag) {
							deleteCategories();
						} 
					});
				});
			}
				
			}
		</script>
	</template:replace>
	<template:replace name="toolbar">
		<div style="width:100%;height:38px;border-bottom:1px solid #ccc!important;margin-bottom: 10px;margin-top: 5px">
		   <ui:toolbar id="toolbar" style="float:right;margin-right:15px" count="9">
		   		<%-- 新建 --%>
			   <ui:button text="${lfn:message('button.save')}" onclick="window.frames['iframeName'].saveAdd();" order="1" ></ui:button>
			   <%-- 编辑 --%>
			   <ui:button text="${lfn:message('button.edit')}"  onclick="editCategory();" order="2" ></ui:button>
			   <%-- 删除 --%>
			   <ui:button text="${lfn:message('button.delete')}"  onclick="List_ConfirmDel();" order="3" ></ui:button>
			   <%-- 刷新 --%>
			   <ui:button text="${lfn:message('button.refresh')}"  onclick="location.reload();" order="4" ></ui:button>
		   </ui:toolbar>
		</div>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory.do">
			<div style="width: 100%">
				 <iframe id='iframeSearch' name="iframeName" width="100%" height="120px" frameborder="0" scrolling="no" marginheight="0"> 
				 </iframe>
			 </div>
			 
			<table class="tb_noborder" align="left">
				<tr>
					<td width="10pt"></td>
					<td>
					<div id="treeDiv" class="treediv"></div>
					</td>
				</tr>
			</table>
		</html:form>
	</template:replace>
</template:include>
