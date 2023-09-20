<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<tr>
	<td width="15%" class="td_normal_title" valign="top">
		<bean:message bundle="sys-doc" key="sysDocBaseInfo.fdDescription" />
	</td>
	<td width="85%" colspan="3">
		<xform:textarea property="fdDescription" style="width:97.5%;height:80px" validators="senWordsValidator(kmDocKnowledgeForm)"/>
	</td> 
</tr>
<tr>
	<td width="15%" class="td_normal_title" valign="top">
		<bean:message bundle="sys-doc" key="sysDocBaseInfo.docContent" />
	</td>
	<td width="85%" colspan="3">
		<xform:rtf property="docContent" toolbarSet="Default" width="98%" validators="senWordsValidator(kmDocKnowledgeForm)"/>
	</td>
</tr>
<tr>
	<td width="15%" class="td_normal_title" valign="top">
		<bean:message bundle="sys-doc" key="sysDocBaseInfo.docAttachments" />
	</td>
	<td width="85%" colspan="3">
		<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp"
			charEncoding="UTF-8">
			<c:param name="fdKey" value="attachment" />
			<c:param name="uploadAfterSelect" value="true" />
		</c:import>
	</td>
</tr>
