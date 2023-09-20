<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.edit" sidebar="no">
	<template:replace name="toolbar">
	<div style="width:100%;height:38px;border-bottom:1px solid #ccc!important;margin-bottom: 10px;margin-top: 5px">
	   <ui:toolbar id="toolbar" style="float:right;margin-right:40px" count="4">
		   <ui:button text="${lfn:message('button.add')}"  onclick="addAuthArea();" order="2" ></ui:button>
		   <ui:button text="${lfn:message('button.edit')}"  onclick="editAuthArea();" order="3" ></ui:button>
		   <ui:button text="${lfn:message('button.refresh')}"  onclick="history.go(0);" order="4" ></ui:button>
	   </ui:toolbar>
	</div>
	</template:replace>
	<template:replace name="content">
		<html:form action="/sys/authorization/sys_auth_area/sysAuthArea.do">
			<table class="tb_noborder" style="width:100%;">
				<tr>
					<td width="10pt"></td>
					<td>
						<div id=treeDiv class="treediv" style="margin-bottom:50px"></div>
					</td>
				</tr>
			</table>
			
			<script type="text/javascript">Com_IncludeFile("treeview.js");Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");</script>
			<script type="text/javascript">
				window.onload = generateTree;
				var LKSTree;
				function generateTree() {
					LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-authorization" key="table.sysAuthArea" />", document.getElementById("treeDiv"));
					LKSTree.isShowCheckBox=true;
					LKSTree.isMultSel=false;
					var n1, n2;
					n1 = LKSTree.treeRoot;	
					var modelName = Com_GetUrlParameter(location.href,"modelName");
					n1.authType = "01";
					n2 = n1.AppendBeanData("sysAuthAreaTreeService&parentId=!{value}&modelName="+modelName);
					LKSTree.Show();
				}
				
				function addAuthArea(){
					var url = "<c:url value="/sys/authorization/sys_auth_area/sysAuthArea.do" />?method=add";
					var modelName = Com_GetUrlParameter(location.href,"modelName");
					url = Com_SetUrlParameter(url,"fdModelName",modelName);
					var checkedNode = LKSTree.GetCheckedNode();
					if(checkedNode != null){
						var selectedId = checkedNode.value;
						url = Com_SetUrlParameter(url, "parentId", selectedId);
					}
					Com_OpenWindow(url);
				}
				
				function editAuthArea(){
					var url = "<c:url value="/sys/authorization/sys_auth_area/sysAuthArea.do" />?method=edit&mainModelName=${JsParam.mainModelName}";
					if(List_CheckSelect()){
						var selectedId = LKSTree.GetCheckedNode().value;
						url = Com_SetUrlParameter(url, "fdId", selectedId);
						Com_OpenWindow(url);
					}
				}
				
				function viewAuthArea(id) {
					if(id==null) return false;
					var node = Tree_GetNodeByID(this.treeRoot,id);
					if(node!=null && node.value!=null) {
						var url = "<c:url value="/sys/authorization/sys_auth_area/sysAuthArea.do" />?method=view&fdId="+node.value;
						Com_OpenWindow(url);
					}
				}
				
				function deleteAuthAreas(){
					if(!List_ConfirmDel())return;
					var selList = LKSTree.GetCheckedNode();
					var input = document.createElement("INPUT");
					input.type="text";
					input.style.display="none";
					input.name="List_Selected";	
					input.value = selList[i].value;
					document.sysAuthAreaForm.appendChild(input);	
					Com_Submit(document.sysAuthAreaForm, 'deleteall');	
				}
				
				function List_CheckSelect(){
					var obj = document.getElementsByName("List_Selected"); 
					if(LKSTree.GetCheckedNode() != null){
						return true;
					}
					alert("<bean:message key="page.noSelect"/>");
					return false;
				}
				function List_ConfirmDel(){
					return List_CheckSelect() && confirm("<bean:message key="page.comfirmDelete"/>");
				}
			</script>
			<script>
			LUI.ready(function(){
				seajs.use(['lui/jquery','lui/topic','lui/toolbar'], function($,topic,toolbar) {
					window.getCookie = function(){   
						var arr,reg=new RegExp("(^| )isopen=([^;]*)(;|$)");   
						if(arr=document.cookie.match(reg)) return unescape(arr[2]);   
						else return null;   
					};
						
					var mark=getCookie();
					if(mark=='open'){
						var dataInitBtn = toolbar.buildButton({id:'dataInit',order:'1',text:'${lfn:message("sys-datainit:sysDatainitMain.data.export")}',click:'Datainit_Submit()'});
					    LUI('toolbar').addButton(dataInitBtn);
					}
					 
					window.Datainit_Submit = function(){
						if(!List_CheckSelect())
							return;
						var form = document.forms[0];
						var url = Com_Parameter.ContextPath + "sys/datainit/sys_datainit_main/sysDatainitMain.do?method=export&formName="+form.name;
						form.action = url;
						form.submit();
					}
				});
			});
			</script>
		</html:form>
	</template:replace>
</template:include>
