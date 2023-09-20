<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.util.ModelUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>

<%
	String modelName = request.getParameter("modelName");
	SysDictModel model = SysDataDict.getInstance().getModel(modelName);
	String modelUrl = model.getUrl().split("\\?")[0];
	request.setAttribute("modelUrl", modelUrl);
	request.setAttribute("modelPath", modelUrl.substring(0,modelUrl.lastIndexOf("/")));
	String[] messageKey = model.getMessageKey().split(":");
	request.setAttribute("displayName", ResourceUtil.getString(messageKey[1], messageKey[0]));
%>
<html:form action="${modelUrl }" method="post" enctype="multipart/form-data">
	<div id="optBarDiv">
		<input type="button" value="${lfn:message('eop-basedata:eopBasedata.button.showList')}"
				onclick="showList();" />
		<c:if test="${param.can ne false}">
		<kmss:auth
			requestURL="${modelUrl }?method=add">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="add();" />
		</kmss:auth>
		</c:if>
		<kmss:auth
			requestURL="${modelUrl }?method=edit">
			<input type="button" value="<bean:message key="button.edit"/>"
				onclick="edit();" />
		</kmss:auth>
		<input type="button" value="<bean:message key="button.refresh"/>"
			onclick="history.go(0);">
	</div>
	<table class="tb_noborder">
		<tr>
			<td colspan="20"><span style="color: #0080FF;float: left;margin:5px 10px;" id="path"><bean:message
						key="page.curPath" /><%=java.net.URLDecoder.decode(request.getParameter("s_path"), "utf-8")%></span></td>
		</tr>
		<tr>
			<td width="10pt"></td>
			<td>
				<div id=treeDiv class="treediv"></div>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		Com_IncludeFile("treeview.js");
	</script>
	<script>
		window.onload = generateTree;
		var LKSTree;
		function generateTree() {
			LKSTree = new TreeView(
					"LKSTree",
					"${displayName}",
					document.getElementById("treeDiv"));
			LKSTree.isShowCheckBox = true;
			LKSTree.isMultSel = true;
			LKSTree.isAutoSelectChildren = false;
			LKSTree.DblClickNode = view;
			var n1, n2;
			n1 = LKSTree.treeRoot;
			n1.authType = "01";
			n2 = n1.AppendBeanData("eopBasedataPublicTreeService&modelName=${param.modelName}&parentId=!{value}");
			
			LKSTree.Show();
		}

		function add() {
			if(checkNode()){
				alert("<bean:message key='error.select.node.message.add' bundle='eop-basedata'/>");
				return false;
			}
			var url = "<c:url value='${modelUrl }'/>?method=add";
			var checkedNode = LKSTree.GetCheckedNode();
			if (checkedNode.length > 0) {
				if (checkedNode[0].nodeType == "CATEGORY_SON") {
					alert("<bean:message key='error.illegalCreateCategory' bundle='sys-category'/>");
					return false;
				} else {
					if (LKSTree.GetCheckedNode().length == 1) {
						var selectedId = checkedNode[0].value;
						url = Com_SetUrlParameter(url, "parentId", selectedId);
						var selectedId2 = LKSTree.GetCheckedNode()[0].value;
						url = Com_SetUrlParameter(url, "nowId", selectedId2);
					} else {
						alert("<bean:message key='error.select.message.add' bundle='sys-category'/>");
						return false;
					}
				}
			}
			Com_OpenWindow(url);
		}

		function edit() {
			if(checkNode()){
				alert("<bean:message key='error.select.node.message.edit' bundle='eop-basedata'/>");
				return false;
			}
			var url = "<c:url value='${modelUrl }' />?method=edit";
			if (List_CheckSelect()) {
				if (LKSTree.GetCheckedNode().length == 1) {
					var selectedId = LKSTree.GetCheckedNode()[0].value;
					url = Com_SetUrlParameter(url, "fdId", selectedId);
					Com_OpenWindow(url);
				} else {
					alert("<bean:message key='error.select.message.edit' bundle='sys-category'/>");
					return false;
				}
			}
		}

		function view(id) {
			if (id == null)
				return false;
			var node = Tree_GetNodeByID(this.treeRoot, id);
			if (node != null && node.value != null) {
				var url = "<c:url value='${modelUrl }' />?method=view&fdId="
						+ node.value;
				Com_OpenWindow(url);
			}

		}

		function checkNode(){
			var modelNameFlag1=${param.modelName == 'com.landray.kmss.eop.basedata.model.EopBasedataMaterial'};
			var modelNameFlag2;
			//发起ajax请求，如果为节点名为物料类别名，返回false
			//不能存在节点数据，表示是没选择节点，直接编辑或新增操作
			var nowNode = LKSTree.GetCheckedNode()[0];
			if(nowNode == undefined){
				return false;
			}
			var nowId = LKSTree.GetCheckedNode()[0].value;
			$.ajax({
		        url: '${LUI_ContextPath}' + '/eop/basedata/eop_basedata_mate_cate/eopBasedataMateCate.do' + '?method=isExist',
		        data: {nowId:nowId},
		        dataType: 'json',
		        async:false,    //用同步方式 
		        type: 'POST',
		        success: function(data) {
		             if(data.isExist){
		            	 modelNameFlag2 = true;
		             }else{
		            	 modelNameFlag2 = false; 
		             }
		        }
		    });  
			var vl = LKSTree.GetCheckedNode();
			var parent=LKSTree.GetCheckedNode()[0].parent;
			var parent1=parent.parent;
			if(modelNameFlag1 && modelNameFlag2){
				return true;
			}else{
				return false;
			}
			
		}

		function List_CheckSelect() {
			var obj = document.getElementsByName("List_Selected");
			if (LKSTree.GetCheckedNode().length > 0) {
				return true;
			}
			alert("<bean:message key='page.noSelect'/>");
			return false;
		}
		function List_ConfirmDel() {
			return List_CheckSelect()
					&& confirm("<bean:message key='page.comfirmDelete'/>");
		}
		
			

		function showList(){
			var s_path=encodeURIComponent(encodeURIComponent($("#path").html()));
			var url = Com_Parameter.ContextPath.substring(0,Com_Parameter.ContextPath.length-1)+"${modelPath}/index.jsp?s_path="+s_path;
			window.location.href = url;
		}
	</script>
	<input type='hidden' id='fdIds'>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
