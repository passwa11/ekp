<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
		<kmss:auth requestURL="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=edit&fdId=${param.fdId}" requestMethod="GET">
			<input type="button"
				value="<bean:message key="button.edit"/>"
				onclick="Com_OpenWindow('lbpmVoteWeight.do?method=edit&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
		<kmss:auth requestURL="/sys/lbpmext/voteweight/lbpm_voteweight/lbpmVoteWeight.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
			<input type="button" 
				value="<bean:message key="button.delete"/>"
				onclick="if(!confirmDelete())return;Com_OpenWindow('lbpmVoteWeight.do?method=delete&fdId=${JsParam.fdId}','_self');">
		</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<kmss:windowTitle 
	moduleKey="sys-lbpmext-voteweight:table.lbpmVoteWeight"/>
<p class="txttitle"><bean:message  bundle="sys-lbpmext-voteweight" key="table.lbpmVoteWeight"/></p>
<center>
<html:hidden name="lbpmVoteWeightForm" property="fdId"/>
<table id="Label_Tabel" width="95%">
	<tr>
		<td>
			<table class="tb_normal" width=100%>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdVoter"/>
					</td>
					<td width=35%>
						${lbpmVoteWeightForm.fdVoterName}
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdVoteWeight"/>
					</td>
					<td width=35%>
						${lbpmVoteWeightForm.fdVoteWeight}
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.lbpmVoteWeightScope"/>
					</td><td width=85% colspan=3>
						<textarea style="width:95%" readonly>${lbpmVoteWeightForm.fdScopeFormVoteWeightCateShowtexts}</textarea>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%> 
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdCreator"/>
					</td><td width=35%>
						${lbpmVoteWeightForm.fdCreatorName} 
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-voteweight" key="lbpmVoteWeight.fdCreateTime"/>
					</td><td width=35%>
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
			</table>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>