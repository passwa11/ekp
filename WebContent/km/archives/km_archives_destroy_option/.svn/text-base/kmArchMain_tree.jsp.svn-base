<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/tree_top.jsp" %>
//Com_Parameter.XMLDebug = true;
function generateTree()
{
        
	LKSTree = new TreeView("LKSTree", '<bean:message key="module.km.archives" bundle="km-archives" />',
	 document.getElementById("treeDiv"));
	LKSTree.isAutoSelectChildren = true;
	var n1, n2;
	n1 = LKSTree.treeRoot;	

	var parameter= new Array;
		parameter[0] = '<c:url value="/km/archives/km_archives_destroy/kmArchDestroyOption.do?method=checkArchList&categoryId=!{value}"/>';
		parameter[1] = 'chacked';
		parameter[2] = Com_Parameter.Style;
	
	n1.AppendSimpleCategoryData(
    	"com.landray.kmss.km.archives.model.KmArchivesCategory",
    	 parameter
    );
	
	n1.isExpanded = true; 	
	
	LKSTree.EnableRightMenu();
	
	
	
	
	
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp"%>
