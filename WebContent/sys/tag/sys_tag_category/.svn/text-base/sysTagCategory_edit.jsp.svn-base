<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% response.addHeader("X-UA-Compatible", "IE=8"); %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script>
Com_IncludeFile("dialog.js");
</script>
<html:form action="/sys/tag/sys_tag_category/sysTagCategory.do" onsubmit="return validateSysTagCategoryForm(this);">
<div id="optBarDiv">
	<c:if test="${sysTagCategoryForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.sysTagCategoryForm, 'update');">
	</c:if>
	<c:if test="${sysTagCategoryForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="Com_Submit(document.sysTagCategoryForm, 'save');">
		<input type=button value="<bean:message key="button.saveadd"/>"
			onclick="Com_Submit(document.sysTagCategoryForm, 'saveadd');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message  bundle="sys-tag" key="table.sysTagCategory"/></p>

<center>
<table class="tb_normal" width=95%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdName"/>
		</td><td width=35%>
			<xform:text property="fdName" style="width:85%" required="true"></xform:text>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdOrder"/>
		</td><td width=35%>
			<html:text property="fdOrder"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdIsSpecial"/>
		</td><td width=35%>
			<xform:radio property="fdIsSpecial"/>
		</td>
			<td class="td_normal_title" width=15%>
				<bean:message  bundle="sys-tag" key="sysTagCategory.fdTagQuoteTimes"/>
			</td><td width=35%>
				<html:hidden property="fdTagQuoteTimes"/>
				${sysTagCategoryForm.fdTagQuoteTimes}
				<span class="txtstrong">(<bean:message  bundle="sys-tag" key="sysTagCategory.fdTagQuoteTimes.describe"/>)</span>
			</td>	
		
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message  bundle="sys-tag" key="sysTagCategory.fdManagerId"/>
		</td><td colspan=3>
		<html:hidden  property="authEditorIds"/>
		<xform:address mulSelect="true" textarea="true" required="true" propertyName="authEditorNames" propertyId="authEditorIds"></xform:address>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<script>
//提交校验
Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function() {
	//提交前，校验类别名称唯一性
	if(checkCategoryName()){
		
		 return true ;
	}	
}

function checkCategoryName(){
	var fdName=document.getElementsByName("fdName")[0].value ;
	var fdId='${sysTagCategoryForm.fdId}';
	var result = true;
	if(fdName != null && fdName != ""){
		seajs.use([ 'lui/dialog', 'lui/jquery' ], function(dialog) {
			$.ajax( {
				url : Com_Parameter.ContextPath + "sys/tag/sys_tag_category/sysTagCategory.do?method=checkCategoryName",
				type : 'post',
				data:{
					fdId:fdId,
					fdName:fdName
				},
				async : false,//同步请求
				dataType : 'json',
				success : function(data) {
					if(data.code != 200){
						result = false;
						dialog.alert(data.msg);
					}
				}
			});
		});
	}
	return result;
}
	$KMSSValidation();
</script>
</html:form>
<html:javascript formName="sysTagCategoryForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>