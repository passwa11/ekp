<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<script src="./resource/weui_switch.js"></script>
<script type="text/javascript">
Com_IncludeFile("common.js|doclist.js|dialog.js");
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

function submitForm(method){
	   var fdMainScore = document.getElementsByName("fdMainScore")[0].value;
	   var fdResScore = document.getElementsByName("fdResScore")[0].value;
	   var fdPinkScore = document.getElementsByName("fdPinkScore")[0].value;
	   var re = /^[0-9]*[0-9]*$/ ; 
	   if(!re.test(fdMainScore)){
		   alert('<bean:message bundle="km-forum" key="kmForumCategory.fdMainScore.desc"/>');
		   return;
	   }   
	   if(!re.test(fdResScore)){
		   alert('<bean:message bundle="km-forum" key="kmForumCategory.fdResScore.desc"/>');
		   return;
	   }   
	   if(!re.test(fdPinkScore)){
		   alert('<bean:message bundle="km-forum" key="kmForumCategory.fdPinkScore.desc"/>');
		   return;
	   }   
	   Com_Submit(document.kmForumCategoryForm,method);
	   }
	   $(document).ready(function(){
		  var flag = '${kmForumCategoryForm.fdTimeliness}';
		  if(flag=='true'){
			  $("#fdTimelinessDateTr").show();
		  }
	   });
</script>
<p class="txttitle"><bean:message bundle="km-forum" key="kmForumCategory.fdId"/><bean:message key="button.edit"/></p>

<html:form action="/km/forum/km_forum_cate/kmForumCategory.do" onsubmit="return validateKmForumCategoryForm(this);">
<div id="optBarDiv">
	<c:if test="${kmForumCategoryForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="submitForm('update');">
	</c:if>
	<c:if test="${kmForumCategoryForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="submitForm('save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="submitForm('saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<center>
<table class="tb_normal" width=95%>
		<html:hidden property="fdId"/>
		<html:hidden property="fdAnonymous"/>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdParentId"/>
		</td><td colspan=3>
			<html:hidden property="fdParentId" />
			<input name="fdParentName" subject='<bean:message bundle="km-forum" key="kmForumCategory.fdParentId"/>' readonly="readonly"
			class="inputsgl" type="text" validate="required" style="width:50%;" value="${kmForumCategoryForm.fdParentName}">
			
			<span class="txtstrong">*</span>&nbsp;<a href="#" onclick="openCategoryWindow();"><bean:message key="dialog.selectOther" /></a>
		   </td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdName"/>
		</td><td colspan=3>
		<%-- 
			<html:text property="fdName" size="50"/><span class="txtstrong">*</span>
		--%>	
			<xform:text property="fdName" required="true" style="width:80%"></xform:text>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdDescription"/>
		</td><td colspan=3>
			<html:textarea property="fdDescription" style="width:95%"/>
		</td>
	</tr>
	<tr>
		<td width="15%" class="td_normal_title">		
			<bean:message  bundle="km-forum" key="kmForumCategory.forumManagers"/>
		</td>
		<td colspan=3>
			<xform:address mulSelect="true" propertyId="fdManagerIds" propertyName="fdManagerNames" orgType="ORG_TYPE_PERSON" style="width:90%" ></xform:address>
		</td>
	</tr>
	<tr>
		<td colspan=4><bean:message  bundle="km-forum" key="kmForumCategory.forumManager.msg"/></td>
	</tr>	
	
	<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="km-forum" key="kmForumCategory.fdTimeliness" /></td>
		<td colspan="3">
			<html:hidden property="fdTimeliness" /> 
			<label class="weui_switch">
				<span class="weui_switch_bd">
					<input type="checkbox" ${'true' eq kmForumCategoryForm.fdTimeliness ? 'checked' : '' } />
					<span></span>
					<small></small>
				</span>
				<span id="fdTimelinessText"></span>
			</label>
			<script type="text/javascript">
				function setText(status) {
					if(status) {
						$("#fdTimelinessText").text('<bean:message bundle="km-forum" key="kmForumCategory.fdTimeliness.true" />');
						$("#fdTimelinessDateTr").show();
					} else {
						$("#fdTimelinessText").text('<bean:message bundle="km-forum" key="kmForumCategory.fdTimeliness.false" />');
						$("#fdTimelinessDateTr").hide();
					}
				}
				$(".weui_switch :checkbox").on("click", function() {
					var status = $(this).is(':checked');
					$("input[name=fdTimeliness]").val(status);
					setText(status);
				});
				if('${kmForumCategoryForm.fdTimeliness}'=='false'){
					setText(false);
				}else{
					setText(true);
				}
			</script>
		</td>
	</tr>
	<tr style="display: none;" id="fdTimelinessDateTr">
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.setTime"/>
		</td>
		<td colspan="3">
			<xform:radio property="fdTimelinessDate">
				<xform:simpleDataSource value="91"><bean:message  bundle="km-forum" key="kmForumCategory.setTime.topic1"/></xform:simpleDataSource>
				<xform:simpleDataSource value="182"><bean:message  bundle="km-forum" key="kmForumCategory.setTime.topic2"/></xform:simpleDataSource>
				<xform:simpleDataSource value="365"><bean:message  bundle="km-forum" key="kmForumCategory.setTime.topic3"/></xform:simpleDataSource>
				<xform:simpleDataSource value="730"><bean:message  bundle="km-forum" key="kmForumCategory.setTime.topic4"/></xform:simpleDataSource>
			</xform:radio>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdMainScore"/>
		</td><td>
			<html:text property="fdMainScore" size="5"/><span class="txtstrong">*</span>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdResScore"/>
		</td><td>
			<html:text property="fdResScore" size="5"/><span class="txtstrong">*</span>
		</td>
	</tr>

	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdPinkScore"/>
		</td><td>
			<html:text property="fdPinkScore" size="5"/><span class="txtstrong">*</span>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.fdDisplayOrder"/>
		</td><td>
			<html:text property="fdOrder" size="5"/>
		</td>
	</tr>	
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.docCreatorId"/>
		</td><td>
			<html:hidden property="docCreatorId"/>
			<html:text property="docCreatorName" readonly="true"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.docCreateTime"/>
		</td><td>
			<html:text property="docCreateTime" readonly="true"/>
		</td>
	</tr>
	
	<c:if test="${kmForumCategoryForm.method_GET=='edit'}">
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.docAlterId"/>
		</td><td>
			<html:text property="docAlterName" readonly="true"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="km-forum" key="kmForumCategory.docAlterTime"/>
		</td><td>
			<html:text property="docAlterTime" readonly="true"/>
		</td>
	</tr>
	</c:if>

</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<script language="JavaScript">Com_IncludeFile("calendar.js");</script>
<html:javascript formName="kmForumCategoryForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>