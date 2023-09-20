<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
	function confirmDelete(msg){
	var del = confirm("<bean:message key="page.comfirmDelete"/>");
	return del;
}
</script>
<div id="optBarDiv">
	<kmss:auth requestURL="/third/im/kk/kkNotifyLog.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('kkNotifyLog.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="third-im-kk" key="table.kkNotifyLog"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-im-kk" key="kkNotifyLog.fdSubject"/>
		</td><td width="35%" colspan="3">
			<xform:text property="fdSubject" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-im-kk" key="kkNotifyLog.sendTime"/>
		</td><td width="35%">
			<xform:datetime property="sendTime" style="width:85%" />
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-im-kk" key="kkNotifyLog.rtnTime"/>
		</td><td width="35%">
			<xform:datetime property="rtnTime" style="width:85%" />
		</td>
	</tr>
	<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="third-im-kk" key="kkNotifyLog.kkNotifyData"/>
		</td><td colspan="3" width="35%">
			<xform:textarea property="kkNotifyData" style="width:85%" />
		</td>
		</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-im-kk" key="kkNotifyLog.kkRtnMsg"/>
		</td><td width="35%" colspan="3">
			<xform:textarea property="kkRtnMsg" style="width:85%" />
		</td>
	</tr>
		<tr>
	<td class="td_normal_title" width=15%>
			<bean:message bundle="third-im-kk" key="kkNotifyLog.fdParams"/>
		</td><td width="35%" colspan="3">
			<xform:textarea property="fdParams" style="width:85%" />
					
		</td>
	</tr>
	
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>