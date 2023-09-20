<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
	LKSTree = new TreeView(
		"LKSTree",
		'<bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.root"/>',
		document.getElementById("treeDiv")
	);
	var n1 = LKSTree.treeRoot;
	<kmss:authShow roles="ROLE_SYSXFORM_SETTING">
	n1.AppendURLChild(
		'<bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.list"/>',
		'<c:url value="/sys/xform/maindata/jdbc_data_set/xFormJdbcDataSet_ui_include.jsp"/>');	
		
	n1.AppendURLChild(
		'<bean:message bundle="sys-xform-maindata" key="tree.relation.jdbc.catg"/>',
		'<c:url value="/sys/xform/maindata/jdbc_data_set_category/xFormJdbcDataSetCategory_tree.jsp"/>');
	</kmss:authShow>

	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp" %>
<script>
//新建
	window.addDoc = function(url) {
		window.open(url);
	};
</script>