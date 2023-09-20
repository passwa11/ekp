<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js|optbar.js");
</script>
<kmss:windowTitle moduleKey="sys-lbpmservice-support:table.lbpmTemplate" subjectKey="sys-lbpmservice-support:lbpmTemplate.updatePrivileger.subject" />
<html:form action="/sys/lbpmservice/support/lbpm_template/lbpmPrivileger.do?fdModelName=${HtmlParam.fdModelName}&fdKey=${HtmlParam.fdKey}">
<div id="optBarDiv">
	<input type=button value="<bean:message key="button.update"/>"
			onclick="if(!confirmUpdatePrivileger())return;Com_Submit(document.lbpmTemplateForm, 'doUpdatePrivileger');">
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<p class="txttitle"><bean:message key="lbpmTemplate.updatePrivileger.subject" bundle="sys-lbpmservice-support"/></p>
<center>
<p>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=10%>
			<bean:message key="lbpmTemplate.updatePrivileger.oprType.title" bundle="sys-lbpmservice-support"/>
		</td>
		<td width=90%>
			<label><input type="radio" value="1" name="oprType" checked onclick='oprOnclickFunc(this);' ><bean:message key="lbpmTemplate.updatePrivileger.oprType.1" bundle="sys-lbpmservice-support"/> </label>
			<label><input type="radio" value="2" name="oprType" onclick='oprOnclickFunc(this);' ><bean:message key="lbpmTemplate.updatePrivileger.oprType.2" bundle="sys-lbpmservice-support"/> </label>
			<label><input type="radio" value="3" name="oprType" onclick='oprOnclickFunc(this);' ><bean:message key="lbpmTemplate.updatePrivileger.oprType.3" bundle="sys-lbpmservice-support"/> </label>
		</td>
	</tr>	

	<tr>
		<td class="td_normal_title" width=10%>
			<bean:message key="lbpmTemplate.updatePrivileger.select.title" bundle="sys-lbpmservice-support"/>
		</td>
		<td width=90%><label>
			<input type="checkbox" name="template" checked value="true">
			<bean:message key="lbpmTemplate.updatePrivileger.select.template" bundle="sys-lbpmservice-support"/></label>
			<label>
			<input type="checkbox" name="doc" value="true">
			<bean:message key="lbpmTemplate.updatePrivileger.select.doc" bundle="sys-lbpmservice-support"/></label>
		</td>
	</tr>	

	<tr>
		<td class="td_normal_title"  width=10% id="orgTdId">
			<bean:message key="lbpmTemplate.updatePrivileger.targetPrivileger" bundle="sys-lbpmservice-support"/>
		</td>
		<td width=90%>			
			<input type="hidden" name="orgIds">
			<textarea name="orgNames" readonly="readonly" style="width:90%" class="inputmul"></textarea>
			<a href="#" onclick="Dialog_Address(true, 'orgIds','orgNames', ';', ORG_TYPE_POSTORPERSON, null, null, null, true);">
				<bean:message key="dialog.selectOrg"/>
			</a>
			</span>
		</td>
	</tr>

</table>
</p>
<c:if test="${not empty templateList}">
<%-- 业务模板、简单分类、全局分类 批量修改特权人 --%>
<div>

<table class="tb_normal" width=95%>
	<tr class="tr_normal_title">
		<td width="10pt">
			<input type="checkbox" name="List_Tongle" checked onclick="tongleSelect();">
		</td>
		<td width="40pt">
			<bean:message key="page.serial"/>
		</td>
		<td>
			<bean:message key="lbpmTemplate.updateAuditor.template" bundle="sys-lbpmservice-support"/>
		</td>
		<td>
			<bean:message key="lbpmTemplate.updateAuditor.common" bundle="sys-lbpmservice-support"/>
		</td>
		<td width="120pt" class="td_normal_title">
			<bean:message key="lbpmTemplate.fdType" bundle="sys-lbpmservice-support"/>
		</td>
	</tr>
	<c:forEach items="${templateList}" var="template" varStatus="vstatus">
		<tr>
			<td>
				<input type="checkbox" name="List_Selected" checked value="${template.id}" />
			</td>
			<td>
				${vstatus.index+1}
			</td>
			<td>
				<c:out value="${template.modelSubject}" />
			</td>
			<td>
				<c:out value="${template.comTempName}" />
			</td>
			<td>
				<c:if test="${template.type == '1'}">
					<bean:message key="lbpmTemplate.fdType.default" bundle="sys-lbpmservice-support"/>
				</c:if>
				<c:if test="${template.type == '2' }">
					<bean:message key="lbpmTemplate.fdType.other" bundle="sys-lbpmservice-support"/>
				</c:if>
				<c:if test="${template.type == '3' }">
					<bean:message key="lbpmTemplate.fdType.define" bundle="sys-lbpmservice-support"/>
				</c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</div>
</c:if>
<c:if test="${not empty templateIds}">
<%-- 通用流程模板 替换处理人 --%>
<c:forEach items="${templateIds}" var="templateId">
<input type="checkbox" name="List_Selected" checked value="${templateId}" style="display:none" />
</c:forEach>
</c:if>

</center>
<script>
$KMSSValidation();
function tongleSelect() {
	var List_Tongle = document.getElementsByName('List_Tongle')[0];
	var List_Selected = document.getElementsByName('List_Selected');
	for (var i = 0; i < List_Selected.length; i ++) {
		List_Selected[i].checked = List_Tongle.checked;
	}
}
function confirmUpdatePrivileger() {
	var pno = document.getElementsByName("orgIds");
	if(pno[0].value==""){
		alert('<bean:message key="lbpmTemplate.updatePrivileger.privileger.required" bundle="sys-lbpmservice-support"/>');
		return false;
	}

	var obj = document.getElementsByName("List_Selected");
	for(var i=0, n=obj.length; i<n; i++) {
		if(obj[i].checked) {
			return true;
		}
	}
	alert('<bean:message key="page.noSelect"/>');
	return false;
}
function oprOnclickFunc(el){
	var opTypeValue = el.value;
	var tt = '<bean:message key="lbpmTemplate.updatePrivileger.targetPrivileger" bundle="sys-lbpmservice-support"/>';
	var td='<bean:message key="lbpmTemplate.updatePrivileger.srcPrivileger" bundle="sys-lbpmservice-support"/>';
	if(opTypeValue=="1" || opTypeValue=="3"){
		document.getElementById("orgTdId").innerHTML=tt;
	}else{
		document.getElementById("orgTdId").innerHTML=td;
	}
}
</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>