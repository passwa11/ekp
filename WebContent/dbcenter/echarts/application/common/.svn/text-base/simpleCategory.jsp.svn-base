<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
</head>
<body> 
	<script type="text/javascript">
		Com_IncludeFile("data.js");
		Com_IncludeFile("treeview.js");
	</script>
	<script type="text/javascript">
		var dialogRtnValue;
		var dialogObject=null;
		if(window.showModalDialog){
			dialogObject = window.dialogArguments;
		}else{
			dialogObject = window.opener.Com_Parameter.Dialog;
		}
	</script>
	<script type="text/javascript">
		var LKSTree;
		Tree_IncludeCSSFile();
		var curItem = dialogObject.data;
		
		// 生成树
		function generateTree() {
			var para = new Array;
			var href = location.href;
			para[0] = Com_GetUrlParameter(href, "url");
			LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-category" key="sysCategoryMain.modelName"/>", document.getElementById("treeDiv"));
			
			LKSTree.ClickNode = Dialog_ClickNode;
			
			var n1 = LKSTree.treeRoot;
			n1.AppendSimpleCategoryData(curItem.templateModelName, para);
			LKSTree.Show();

		}
		
		function Dialog_ClickNode(node){
			if(typeof(node)=="number")
				node = Tree_GetNodeByID(this.treeRoot, node);
			if(node == null)
				return;
			if(node.parameter==null){
				this.ExpandNode(node);
				return;
			}
			var bean = "dbEchartsApplicationService&parentId=!{value}&chartMode=" + curItem.value + "&&modeType=${param.modeType}";
			if(node.parameter.IsKMSSData!=true){
				node.parameter = new KMSSData().AddBeanData(Com_ReplaceParameter(bean, node));
			}
			try{
				document.getElementById("optiframe").contentWindow.setOptData(node.parameter);
				// 设置隐藏域，当前ID
				//$("#currentId").val(node.value);
			}catch(e){
				return;
			}
			this.SetCurrentNode(node);
		}
		
		function Com_DialogReturn(rn){
			if (rn != null) {
				dialogRtnValue = rn;
				dialogRtnValue.item = curItem;
			}
			close();
		}
		
		function beforeClose(){
			//window.returnValue = dialogRtnValue;
			var callback = dialogObject.AfterShow;
			if (callback) {
				callback(dialogRtnValue);
			}
		}
		
		Com_AddEventListener(window, "beforeunload", beforeClose);
		
		Com_AddEventListener(window, "load", function(){
			var height = "";
			if(window.innerHeight){
				height = window.innerHeight-3;
			}else{
				var winHeight = Math.max(document.documentElement.clientHeight, document.body.clientHeight);
				height = Math.max(winHeight, document.body.scrollHeight)-3;
			}
			// 预防出现上下滚动条，再减去20
			height = height - 20;
			document.getElementById("optiframe").setAttribute("height",height);
			document.getElementById("treeDiv").style.height = (height - 10) + "px";
		});
	</script>
	<table width="100%" height="100%" style="height:99%; border-collapse:collapse;border: 0px #303030 solid;">
		<tr>
			<td valign="top" width="30%">
				<div id=treeDiv class="treediv" style="overflow:scroll;max-width:200px;"></div>		
			</td>
			<td valign="top">
				<iframe id="optiframe" width=100% height=100% frameborder=0 scrolling="no" src='${KMSS_Parameter_ContextPath}dbcenter/echarts/application/common/simpleCategory_sgl.jsp?modelName=${param.modelName}'></iframe>	
			</td>
		</tr>
	</table>
	<script>generateTree();</script>
</body>
</html>