<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/eop/basedata/fssc.tld" prefix="fssc"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ page import="com.landray.kmss.sys.config.dict.SysDictModel" %>
<%@ page import="com.landray.kmss.sys.config.dict.SysDataDict" %>
<%@ page import="com.landray.kmss.util.ModelUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.eop.basedata.util.EopBasedataAuthUtil"%>

<%
	String fdCompanyId=request.getParameter("fdCompanyId");
	request.setAttribute("staffManagerList", EopBasedataAuthUtil.getFinanceStaffAndManagerList(fdCompanyId));
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
		<input type="button" value="${lfn:message('eop-basedata:button.showList')}"
				onclick="showList();" />
		<c:if test="${param.can ne false}">
		<kmss:auth
			requestURL="${modelUrl }?method=add&fdCompanyId=${param.companyId}">
			<input type="button" value="<bean:message key="button.add"/>"
				onclick="add();" />
		</kmss:auth>
		</c:if>
		<kmss:auth
			requestURL="${modelUrl }?method=edit&fdCompanyId=${param.companyId}">
			<input type="button" value="<bean:message key="button.edit"/>"
				onclick="edit();" />
		</kmss:auth>
		<c:if test="${empty param.unuse and not empty param.companyId}">
			<c:if test="${param.can ne false}">
				<kmss:auth
						requestURL="${modelUrl }?method=deleteall&fdCompanyId=${param.companyId}">
					<input type="button" value="<bean:message key="button.delete"/>"
						   onclick="deleteAccounts();" />
				</kmss:auth>
			</c:if>
			<kmss:authShow roles="ROLE_EOPBASEDATA_IMPORT" extendOrgElements="${staffManagerList}">
				<input type="button" value="<bean:message key="button.import"/>"
					   onclick="importData();" />
			</kmss:authShow>
			<kmss:authShow roles="ROLE_EOPBASEDATA_IMPORT" extendOrgElements="${staffManagerList}">
				<input type="button" value="<bean:message key="eop-basedata:button.exportTemplate"/>"
					   onclick="downloadTemplate();" />
			</kmss:authShow>
			<kmss:authShow roles="ROLE_EOPBASEDATA_IMPORT" extendOrgElements="${staffManagerList}">
				<input type="button" value="<bean:message key="button.export"/>"
					   onclick="exportData();" />
			</kmss:authShow>
		</c:if>
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
		//Com_Parameter.XMLDebug = true;
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
			var fdCompanyId = Com_GetUrlParameter(location.href, "companyId");
			n1.authType = "01";
			n2 = n1.AppendBeanData("eopBasedataTreeService&modelName=${param.modelName}&parentId=!{value}&fdCompanyId="
					+ fdCompanyId);
			LKSTree.Show();
		}

		function add() {
			var url = "<c:url value='${modelUrl }'/>?method=add";
			var fdCompanyId = Com_GetUrlParameter(location.href, "companyId");
			url = Com_SetUrlParameter(url, "fdCompanyId", fdCompanyId);
			var checkedNode = LKSTree.GetCheckedNode();
			if (checkedNode.length > 0) {
				if (checkedNode[0].nodeType == "CATEGORY_SON") {
					alert("<bean:message key='error.illegalCreateCategory' bundle='sys-category'/>");
					return false;
				} else {
					if (LKSTree.GetCheckedNode().length == 1) {
						var selectedId = checkedNode[0].value;
						url = Com_SetUrlParameter(url, "parentId", selectedId);
					} else {
						alert("<bean:message key='error.select.message.add' bundle='sys-category'/>");
						return false;
					}
				}
			}
			Com_OpenWindow(url);
		}

		function edit() {
			var url = "<c:url value='${modelUrl }' />?method=edit";
			if (List_CheckSelect()) {
				if (LKSTree.GetCheckedNode().length == 1) {
					var selectedId = LKSTree.GetCheckedNode()[0].value;
					url = Com_SetUrlParameter(url, "fdId", selectedId);
					var fdCompanyId = Com_GetUrlParameter(location.href,
							"companyId");
					url = Com_SetUrlParameter(url, "fdCompanyId", fdCompanyId);
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

		function deleteAccounts() {
			if (!List_ConfirmDel())
				return;
			var selList = LKSTree.GetCheckedNode();
			for (var i = selList.length - 1; i >= 0; i--) {
				var input = document.createElement("INPUT");
				input.type = "text";
				input.style.display = "none";
				input.name = "List_Selected";
				input.value = selList[i].value;
				document.forms[0].appendChild(input);
			}
			Com_Submit(document.forms[0], 'deleteall');
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

		function exportData(){
			window.open(Com_Parameter.ContextPath+"eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=exportData&modelName=${param.modelName}&fdCompanyId=${param.companyId}");
		}
		//导入
		window.importData = function(){
			seajs.use(['lui/dialog','lang!eop-basedata'],function(dialog,lang){
				dialog.iframe(
						'/eop/basedata/resource/jsp/eopBasedataImport.jsp?modelName=${param.modelName}',
						lang['message.import.title'],
						function(){
						},{height:600,width:800}
				);
			});

		}
		//下载模板
		window.downloadTemplate = function(){
			window.open(Com_Parameter.ContextPath+'eop/basedata/eop_basedata_business/eopBasedataBusiness.do?method=downloadTemplate&modelName=${param.modelName}');
		}

		function copy() {
			var url = "<c:url value='${modelUrl }' />?method=copy";
			if (List_CheckSelect()) {
				var nodes = LKSTree.GetCheckedNode();
				var ids = [];
				for(var i=0;i<nodes.length;i++){
					ids.push(nodes[i].value);
				}
				seajs.use(['lui/dialog','lang!eop-basedata'],function(dialog,lang){
					if(ids.length==0){
						dialog.alert(listOption.lang.noSelect);
						return;
					}
					var load = dialog.loading();
					dialog.iframe(
							'/eop/basedata/resource/jsp/eopBasedataSelectCompany.jsp',
							lang["message.table.label.selectCompany"],
							function(data){
								if(!data){
									return;
								}
								$.ajax({
									url:url,
									data:{ids:ids.join(";"),fdCompanyIds:data.fdCompanyIds,modelName:'${param.modelName}'},
									dataType:'json',
									type:'POST',
									async:false,
									success:function(rtn){
										dialog.result(rtn);
										window.location.reload();
									},
									error:function(){
										dialog.failure('操作失败');
										load.hide();
									}
								});
							},{width:600,height:250}
					);
				})
			}
		}
		
			

		function showList(){
			var s_path=encodeURIComponent(encodeURIComponent($("#path").html()));
			var url = Com_Parameter.ContextPath.substring(0,Com_Parameter.ContextPath.length-1)+"${modelPath}/index.jsp?s_path="+s_path+"&companyId=${param.companyId}";
			<fssc:switchOff property="fdShowType">
			url+="&fdCompanyId=${param.companyId}";
			</fssc:switchOff>
			window.location.href = url;
		}
	</script>
	<input type='hidden' id='fdIds'/>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
