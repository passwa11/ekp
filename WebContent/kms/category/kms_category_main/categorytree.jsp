<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ page import="com.landray.kmss.sys.category.interfaces.CategoryUtil" %>
<style type="text/css">
.lui_label_btn {
	font-size: 12px;
	line-height: 18px;
	color: #4285F4;
	background: #e1ecff;
	cursor: pointer;
	border: 1px solid #dde9ff;
	border-radius: 2px;
	padding: 0 5px;
}
</style>
<div id=treeDiv class="treediv"></div>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script>
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
		Category_AuthIds = new KMSSData().AddBeanData("sysCategoryAuthListService&modelName=${JsParam.modelName}&getTemplate=2&getReadCate=true").GetHashMapArray();
		generateTree();
	}
	<% } %>
	window.onload = Category_OnLoad;
	var LKSTree;
	Tree_IncludeCSSFile();
	
	var data;
	var a=1;
	var fdParentIds =[];
	var fdIdArray = [];
	
	function generateTree()
	{
		var para = new Array;
		var href = location.href;
		para[0] = Com_GetUrlParameter(href, "url")+"?method=checkCategoryTagView&fdId=!{value}";
		para[1] = Com_GetUrlParameter(href, "target");
		para[2] = Com_GetUrlParameter(href, "winStyle");
		LKSTree = new TreeView("LKSTree", "<bean:message bundle="sys-category" key="sysCategoryMain.modelName"/>", document.getElementById("treeDiv"));
		
		var n1 = LKSTree.treeRoot;
		n1.AppendSimpleCategoryData(Com_GetUrlParameter(href, "modelName"),para);
		
		LKSTree.Show();
		
		LKSTree.OnNodePostExpand = function(node){
			for (var i = 0; i < fdIdArray.length; i++) {
				var node = Tree_GetNodeByValue(LKSTree.treeRoot,fdIdArray[i]);
				if(node!= null){
					$("#treeDiv table tr td a").each(function(){
						if($(this).text()==node.text){
							$(this).append("&nbsp;&nbsp;<a class='lui_label_btn'>待较准</a>");
						}
					}); 
				}
				
			}
			for (var i = 0; i < fdParentIds.length; i++) {
				var node = Tree_GetNodeByValue(LKSTree.treeRoot,fdParentIds[i]);
				if(node!=null){
					$("#TVN_"+node.id).css('color', '#4285F4');
				}
			}
		}
		
		//165c75398956b1d1393c8354a06bc6f5   一级分类
		//165c7dcb00ee91a15f3e24e405ebd9f8 12121
		
		// 获取回调URL，如果有设置此数据，则会在右则打开回调页面
		var callback_url = Com_GetUrlParameter(location.href, "callback_url");
		if(callback_url)
			Com_OpenWindow(callback_url);
		
		//seajs.use(["lui/jquery",'lui/dialog'],function($,dialog){
			$.ajax({
				url: "${KMSS_Parameter_ContextPath}kms/category/kms_category_main/kmsCategoryMain.do",
				type: 'get',
				data:{'method':'getCategoryParentIds'},
				dataType: 'json',
				success: function(data) {
					data=data;
					if(data.flag){
						fdParentIds=data.data[0].fdParentIds.split(";");
						fdIdArray =data.data[0].fdIds.split(";");
						for (var i = 0; i < fdIdArray.length; i++) {
							var node = Tree_GetNodeByValue(LKSTree.treeRoot,fdIdArray[i]);
							if(node!= null){
								$("#treeDiv table tr td a").each(function(){
									if($(this).text()==node.text){
										$(this).append("&nbsp;&nbsp;<a class='lui_label_btn'>待较准</a>");
									}
								}); 
							}
							
						}
						for (var i = 0; i < fdParentIds.length; i++) {
							var node = Tree_GetNodeByValue(LKSTree.treeRoot,fdParentIds[i]);
							if(node!=null){
								$("#TVN_"+node.id).css('color', '#4285F4');
							}
						}
					}
				}
			});
		//});
		
		//var json={"status":0,"data":[{"fdCategoryId":"16660afe9b36452ad47c18a4a2b9c19b"},{"fdCategoryId":"16660bcbc72e2165a656c284292beb7e"},{"fdCategoryId":"165c75398956b1d1393c8354a06bc6f5"},{"fdCategoryId":"165c7da4c4d91a6e73c4fb14e78a38e3"}]};
		
		
		
	}
	
	//var aaa= Tree_GetNodeByID(LKSTree.treeRoot,'165c75398956b1d1393c8354a06bc6f5');
	//var bbb= Tree_GetNodeByValue(LKSTree.treeRoot,'一级分类');
	//alert(bbb.value);
	
	
	
	
	
</script>
<%@ include file="/resource/jsp/view_down.jsp" %>