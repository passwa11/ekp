<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
var _validator = $KMSSValidation();
Com_IncludeFile("common.js|doclist.js|dialog.js|calendar.js");
function openCategoryWindow(){
	dialog_Category_Tree(null, 'fdParentId', 'fdParentName', ',', 'kmForumCategoryTeeService&categoryId=!{value}&isCategory=true', '<bean:message key="dialog.tree.title" bundle="km-forum"/>', null, null, '${kmForumCategoryForm.fdId}', null, null, '<bean:message key="dialog.title" bundle="km-forum"/>');
}
function dialog_Category_Tree(mulSelect, idField, nameField, splitStr, treeBean, treeTitle, treeAutoSelChilde, action, exceptValue, isMulField, notNull, winTitle){
	var dialog = new KMSSDialog(mulSelect, false);
	var node = dialog.CreateTree(treeTitle);
	node.AppendBeanData(treeBean, null, null, null, exceptValue);
	dialog.tree.isAutoSelectChildren = treeAutoSelChilde;
	dialog.winTitle = winTitle==null?treeTitle:winTitle;
	dialog.BindingField(idField, nameField, splitStr);
	dialog.SetAfterShow(action);
	dialog.Show();
}
function onSubmitClick(){
	var fdParentObj = $('input[name="fdParentName"]');
	var fdParentIdObj = $('input[name="fdParentId"]');
	if(!_validator.validateElement(fdParentObj[0])){
		return false;
	}
	if(fdParentIdObj.val()==null||fdParentIdObj.val()==''){
		alert("请选择正确的板块！");
		fdParentObj.val('');
		return false;
	}
	Com_Submit(document.kmForumCategoryForm, 'changeDirectory');
}
</script>
<p class="txttitle"><bean:message bundle="km-forum" key="kmForumCategory.changeDirectory" /></p>

<html:form action="/km/forum/km_forum_cate/kmForumCategory.do">
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="onSubmitClick()">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="km-forum" key="kmForumCategory.changeTo" />
		</td><td colspan=3>
			<html:hidden property="fdParentId" />
			<xform:text property="fdParentName" showStatus="edit" style="width:30%" required="true"/>
			<a href="#" onclick="openCategoryWindow();"><bean:message key="dialog.selectOther" /></a>
			
		   </td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<script type="text/javascript">
    $(document).ready(function () {
    	$('input[name="fdParentName"]').attr("readonly","readonly");
    })
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>