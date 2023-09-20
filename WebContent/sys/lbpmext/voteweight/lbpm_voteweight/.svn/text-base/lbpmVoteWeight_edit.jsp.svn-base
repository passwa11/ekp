<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight_script.jsp"%>
<html:form action="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do" onsubmit="return validateLbpmVoteWeightForm(this);">
<div id="optBarDiv"> 
	<c:if test="${lbpmVoteWeightForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="validateSubmitForm('update');">
	</c:if>
	<c:if test="${lbpmVoteWeightForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="validateSubmitForm('save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<kmss:windowTitle 
	moduleKey="sys-lbpmext-voteweight:table.lbpmVoteWeight"/>

<p class="txttitle"><bean:message  bundle="sys-lbpmext-voteweight" key="table.lbpmVoteWeight"/></p>

<center>
<html:hidden property="fdId"/>
<table id="Label_Tabel" width="95%">
	<tr>
		<td>
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdVoter"/>
					</td>
					<td width=35%>
						<xform:address propertyId="fdVoterId" propertyName="fdVoterName" orgType="ORG_TYPE_PERSON | ORG_TYPE_POST"
							showStatus="edit" style="width:86%" required="true">
						</xform:address>
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdVoteWeight"/>
					</td>
					<td width=35%>
						<xform:select property="fdVoteWeight" showPleaseSelect="false" showStatus="edit">
						    <xform:simpleDataSource value="1">1</xform:simpleDataSource>
						    <xform:simpleDataSource value="2">2</xform:simpleDataSource>
						    <xform:simpleDataSource value="3">3</xform:simpleDataSource>
						    <xform:simpleDataSource value="4">4</xform:simpleDataSource>
						    <xform:simpleDataSource value="5">5</xform:simpleDataSource>
						    <xform:simpleDataSource value="6">6</xform:simpleDataSource>
						    <xform:simpleDataSource value="7">7</xform:simpleDataSource>
						    <xform:simpleDataSource value="8">8</xform:simpleDataSource>
						    <xform:simpleDataSource value="9">9</xform:simpleDataSource>
						    <xform:simpleDataSource value="10">10</xform:simpleDataSource>
						</xform:select>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.lbpmVoteWeightScope"/>
					</td>
					<td width=85% colspan=3>
						<html:hidden property="fdScopeFormVoteWeightCateIds"/>
						<html:hidden property="fdScopeFormVoteWeightCateNames"/>
						<html:hidden property="fdScopeFormModelNames"/>
						<html:hidden property="fdScopeFormModuleNames"/>
						<html:hidden property="fdScopeFormTemplateIds"/>
						<html:hidden property="fdScopeFormTemplateNames"/>
						<html:hidden property="scopeTempValues"/>
						<textarea style="width:90%" readonly name="fdScopeFormVoteWeightCateShowtexts">${lbpmVoteWeightForm.fdScopeFormVoteWeightCateShowtexts}</textarea>
						<br>
						<a href="#"
							onclick="importVoteWeightCateDialog();">
							<bean:message key="dialog.selectOther" /></a><bean:message key="lbpmVoteWeight.lbpmVoteWeightScope.note" bundle="sys-lbpmext-voteweight"/>
					</td>
				</tr>
				<c:if test="${lbpmVoteWeightForm.method_GET=='edit'}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdCreator"/>
						</td>
						<td width=35%>
							<html:hidden property="fdCreatorName"/>
							${lbpmVoteWeightForm.fdCreatorName}
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdCreateTime"/>
						</td>
						<td width=35%>
							<html:hidden property="fdCreateTime"/>
							${lbpmVoteWeightForm.fdCreateTime} 
						</td>
					</tr>
					<c:if test="${not empty lbpmVoteWeightForm.docAlterorName}">
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.docAlteror"/>
						</td>
						<td width=35%>
							<html:hidden property="docAlterorName"/>
							${lbpmVoteWeightForm.docAlterorName}
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.docAlterTime"/>
						</td>
						<td width=35%>
							<html:hidden property="docAlterTime"/>
							${lbpmVoteWeightForm.docAlterTime} 
						</td>
					</tr>
					</c:if>
				</c:if>
				<tr>
					<td colspan="4" style="color:red">
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.rule.1"/><br/>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.rule.2"/><br/>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.rule.3"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>
<html:javascript formName="lbpmVoteWeightForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<%@ include file="/resource/jsp/edit_down.jsp"%>