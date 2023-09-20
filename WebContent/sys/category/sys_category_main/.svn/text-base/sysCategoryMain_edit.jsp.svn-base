<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils"%>
<%@ page import="com.landray.kmss.sys.category.forms.SysCategoryMainForm"%>
<%
String fdModelName = "";
Object obj = request.getAttribute("sysCategoryMainForm");
if(obj != null) {
	SysCategoryMainForm form = (SysCategoryMainForm)obj;
	fdModelName = form.getFdModelName();	
}
%>
<script language="JavaScript">
	function submitForm(method) {
	    // 判断描述字符长度
	    var newvalue = document.getElementsByName("fdDesc")[0].value.replace(/[^\x00-\xff]/g, "***");
		if(newvalue.length > 1500) {
			var msg = '<bean:message key="errors.maxLength"/>'.replace("{0}", '<bean:message bundle="sys-category" key="sysCategoryMain.fdDesc"/>').replace("{1}", 1500);
			alert(msg);
			return;
		}
		
		if("${param.fdCopyId}" != ""){
			Com_Submit(document.sysCategoryMainForm, "savecopy");
		}else{
			Com_Submit(document.sysCategoryMainForm, method);
		}
	}
</script>
<html:form action="/sys/category/sys_category_main/sysCategoryMain.do"
	onsubmit="return validateSysCategoryMainForm(this);">
	<div id="optBarDiv">
		<c:if
			test="${sysCategoryMainForm.method_GET=='edit'}">
			<input type=button value="<bean:message key="button.update"/>"
				onclick="submitForm('update');" />
		</c:if> 
		<c:if test="${sysCategoryMainForm.method_GET=='add'}">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="submitForm('save');">
			<input type=button value="<bean:message key="button.saveadd"/>"
				onclick="submitForm('saveadd');">
		</c:if> 
		<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_CloseWindow();">
	</div>

	<p class="txttitle"><bean:message bundle="sys-category"
		key="table.sysCategoryMain" /></p>

	<center>
	<table class="tb_normal" width=95%>
		<html:hidden property="fdId" />
		<html:hidden property="fdModelName" />
		<c:set var="selectEmpty" value="true" />
		<kmss:auth
			requestURL="/sys/category/sys_category_main/sysCategoryMain.do?method=add&fdModelName=${param.fdModelName}"
			requestMethod="Get">
			<c:set var="selectEmpty" value="false" />
		</kmss:auth>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategoryMain.fdParentName" /></td>
			<td colspan="3"><html:hidden property="fdParentId" /> <html:text
				property="fdParentName" readonly="true" styleClass="inputsgl"
				style="width:85%" /> 
				<% if(SysAuthAreaUtils.isAreaEnabled(fdModelName)){ %>
				<a href="#"
				onclick="Dialog_Category('<bean:write name="sysCategoryMainForm" property="fdModelName"/>','fdParentId','fdParentName',false,'01',1,document.getElementsByName('authAreaId')[0].value,null,${selectEmpty},'${sysCategoryMainForm.fdId}');">
			    <bean:message key="dialog.selectOther" /> </a>
			    <% } else { %>
				<a href="#"
				onclick="Dialog_Category('<bean:write name="sysCategoryMainForm" property="fdModelName"/>','fdParentId','fdParentName',false,'01',1,null,null,${selectEmpty},'${sysCategoryMainForm.fdId}');">
			    <bean:message key="dialog.selectOther" /> </a>			
			    <% } %>
			
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategoryMain.fdName" /></td>
			<td colspan="3">
			<%-- 
			<html:text property="fdName" style="width:85%" /><span
				class="txtstrong">*</span>
			--%>	
				<xform:text property="fdName" style="width:85%" required="true" validators="maxLength(200)"></xform:text>
				</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategoryMain.fdDesc" /></td>
			<td colspan="3"><html:textarea property="fdDesc" style="width:85%" /></td>
		</tr>
		
		<% if(SysAuthAreaUtils.isAreaEnabled(fdModelName)){ %>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategoryMain.fdOrgTreeName" /></td>
			<td width=35%>
				<%--
				<html:hidden property="fdOrgTreeId" /> <html:text
				property="fdOrgTreeName" readonly="true"
				styleClass="inputsgl"/>
				<a href="#" onclick="Dialog_OrgTree('fdOrgTreeId', 'fdOrgTreeName', false,'01');">
				<bean:message key="dialog.selectOther"/></a>
				<span class="txtstrong">*</span>
				--%>
				<html:hidden property="authAreaId" />
				<html:hidden property="authAreaName"/>
				<c:out value="${sysCategoryMainForm.authAreaName}" />
			</td>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdOrder" /></td>
			<td width=35%><xform:text property="fdOrder" validators="digits" /></td>
		</tr>		
		<% } else { %>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdOrder" /></td>
			<td colspan="3"><xform:text property="fdOrder" validators="digits" /></td>
		</tr>			
		<% } %>
		
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.tempEditorName" /></td>
			<td colspan="3">
			<xform:address textarea="true" mulSelect="true" propertyId="authEditorIds" propertyName="authEditorNames" orgType="ORG_TYPE_ALL" style="width:90%" ></xform:address>
			<div class="description_txt">
			<bean:message	bundle="sys-category" key="description.main.tempEditor" />
			</div>
			</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.tempReaderName" /></td>
			<td colspan="3">
			<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" style="width:90%" ></xform:address>
			<div class="description_txt">
			<bean:message	bundle="sys-category" key="description.main.tempReader" />
			</div>
			</td>
		</tr>
		<tr style="display:none">
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategory.fdIsinheritMaintainer" /></td>
			<td width=35%>
			<sunbor:enums property="fdIsinheritMaintainer" enumsType="common_yesno" elementType="radio" />
			</td>
			<td class="td_normal_title" width=15%><bean:message
				bundle="sys-category" key="sysCategory.fdIsinheritUser" /></td>
			<td width=35%>
				<sunbor:enums property="fdIsinheritUser" enumsType="common_yesno" elementType="radio" />
			</td>			
		</tr>
		<c:if test="${sysCategoryMainForm.method_GET!='add'}">
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreator" /></td>
			<td width=35%><bean:write name="sysCategoryMainForm" property="docCreatorName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdCreateTime" /></td>
			<td width=35%><bean:write name="sysCategoryMainForm" property="docCreateTime"/></td>			
		</tr>
		<c:if test="${sysCategoryMainForm.docAlterorName!=''}">
		<tr>
			<td class="td_normal_title" width=15%><bean:message
				key="model.docAlteror" /></td>
			<td width=35%><bean:write name="sysCategoryMainForm" property="docAlterorName"/></td>
			<td class="td_normal_title" width=15%><bean:message
				key="model.fdAlterTime" /></td>
			<td width=35%><bean:write name="sysCategoryMainForm" property="docAlterTime"/></td>
		</tr>
		</c:if>
		</c:if>
	</table>
	</center>
	<html:hidden property="method_GET" />
</html:form>
<script>
Com_IncludeFile("dialog_ding.js");
</script>
<script>
	function checkParentId(){
		var formObj = document.sysCategoryMainForm;
		if(formObj.fdParentId.value!="" && formObj.fdParentId.value==formObj.fdId.value){
			alert("<bean:message bundle="sys-category" key="error.illegalSelected" />");
			return false;
		}else
			return true;	
	}
	//var oldOrgTree = document.getElementsByName("fdOrgTreeId")[0].value;
	//var oldParentCate = document.getElementsByName("fdParentId")[0].value;
	function checkParentOrg() {
		var fieldObj = document.getElementsByName("fdOrgTreeId")[0];
		var cateParentObj = document.getElementsByName("fdParentId")[0];
		if(fieldObj.value!=oldOrgTree || cateParentObj.value!=oldParentCate){
			var cateObj = document.getElementsByName("fdId")[0];			
			var beanName = "sysCategoryTreeService&checkOrg="+(fieldObj.value==oldOrgTree?"2":"1")+"&categoryId=" + cateObj.value + "&orgId=" + fieldObj.value;
			if(cateParentObj.value!=oldParentCate) beanName+="&parentId="+cateParentObj.value;
			var data = new KMSSData();
			data.AddBeanData(beanName);
			data = data.GetHashMapArray();
			var errStr = "";
			var parentErr = false;
			for(var i=0;i<data.length;i++){
				errStr += "\n\r" + data[i]["text"] + "("+data[i]["title"] + ")";
				if(data[i]["title"]=="error parent") parentErr = true;
			}
			if(errStr!=""){
				if(parentErr){
					alert("<bean:message bundle="sys-category" key="sysCategoryMain.errorParent"/>");
					return false;
				}else{
					errStr += "\n\r";
					var message = "<bean:message bundle="sys-category" key="sysCategoryMain.errorModify" arg0="[info]" />";
					message = message.replace("[info]",errStr);
					if(confirm(message)){
						var data = new KMSSData();
						data.AddBeanData(beanName+"&correct=1");
						data = data.GetHashMapArray();				
					}else return false;
				}
			}
		}
		return true;
	}
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = checkParentId;
	//Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = checkParentOrg;
</script>
<html:javascript formName="sysCategoryMainForm" cdata="false"
	dynamicJavascript="true" staticJavascript="false" />
<%@ include file="/resource/jsp/edit_down.jsp"%>
