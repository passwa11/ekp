<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="/resource/jsp/edit_top.jsp"%>

<script type="text/javascript">Com_IncludeFile("treeview.js|jquery.js");</script>
<script language="JavaScript">

	window.onload = function start(){
		generateTree();
	};
	var LKSTree;
	<%--打开页面展开分类树--%>
	function generateTree(){
		LKSTree = new TreeView(
			"LKSTree",
			"${moduleDownloadInfo}",
			document.getElementById("TreeDiv")
		);
		LKSTree.isShowCheckBox=true;  			<%-- 是否显示单选/复选框 --%>
		LKSTree.isMultSel=true;					<%-- 是否多选 --%>
		LKSTree.isAutoSelectChildren = true;	<%-- 选择父节点是否自动选中子节点 --%>
		var n1, n2;
		n1 = LKSTree.treeRoot;					<%-- 根节点 --%>
		n1.value = " ";
		<%--通过JavaBean的数据方式批量添加子结点,这里的bean填写前面的bean --%>
		// 总的bean
		var url = "ticCoreInoutdataBean&parentId=!{value}";
		n1.AppendBeanData(url + "&moduleType=${HtmlParam.fdAppType}");
		LKSTree.EnableRightMenu();
		LKSTree.Show();
	}
	
	// 打包下载
	function exportZip(){
		if (List_CheckSelect()) {
			var form = document.getElementsByName('ticCoreInoutdataForm')[0];
			form.submit();
		} 
	}

	// 判断选中
	function List_CheckSelect(){
		removeAllChild(document.ticCoreInoutdataForm);
		var selList = LKSTree.GetCheckedNode();
		for(var j=selList.length-1;j>=0;j--){
			var input = document.createElement("INPUT");
			input.type="text";
			input.style.display="none";
			input.name="Inoutdata_List_Selected";	
			input.value = selList[j].value;
			document.ticCoreInoutdataForm.appendChild(input);	
		}
		if(selList.length > 0){
			return true;
		}
		alert("<bean:message key="page.noSelect"/>");
		return false;
	}
	
	/**
	 * 删除带有INPUT的第一个子节点
	 */
	function removeAllChild(elementObj) {
		var childs = elementObj.childNodes;
		for (var i = 0; i < childs.length; i++) {
			var child = childs[i];
			if ("INPUT" == child.tagName) {
				elementObj.removeChild(child);
			}
		}
	}
	
</script>




<form
	action="<c:url value="/tic/core/inoutdata/ticCoreInoutdata.do?method=exportZip&fdAppType=${HtmlParam.fdAppType}" />" name="ticCoreInoutdataForm"  method="POST">
	<div id="optBarDiv">
		<input type=button value="${lfn:message('home.help')}"
			onclick="Com_OpenWindow(Com_Parameter.ContextPath+'tic/core/inoutdata/help/ExportAndImportHelp.html','_blank');"/>
		<input type="button" value="<bean:message bundle="tic-core-inoutdata" key="imExport.dataExport" />"
			onclick="exportZip();">
	</div>
	
	<p class="txttitle">
		<bean:message bundle="tic-core-inoutdata"
			key="imExport.dataExport" />
	</p>
	
	<center>
		<table class="tb_normal" width=95%>
			<!-- 组件 -->
			<tr>
				<td class="td_normal_title" width=15%>
					${moduleDownloadInfo}
				</td><td colspan="3" width="85%">
					<table class="tb_noborder">
						<tr>
							<td width="10pt"></td>
							<td>
								<div id=TreeDiv class="treediv"></div>
							</td>
						</tr>
					</table>
				</td>
			</tr>

		</table>
	</center>
	
</form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
