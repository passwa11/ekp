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
	<kmss:auth requestURL="/third/ding/third_ding_notifylog/thirdDingNotifylog.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
		<input type="button"
			value="<bean:message key="button.delete"/>"
			onclick="if(!confirmDelete())return;Com_OpenWindow('thirdDingNotifylog.do?method=delete&fdId=${param.fdId}','_self');">
	</kmss:auth>
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<p class="txttitle"><bean:message bundle="third-ding" key="table.thirdDingNotifylog"/></p>

<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ding" key="thirdDingNotifylog.docSubject"/>
		</td><td width="35%" colspan="3">
			<xform:text property="docSubject" style="width:85%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ding" key="thirdDingNotifylog.fdSendTime" />
		</td>
		<td width="35%">
			<xform:datetime property="fdSendTime" style="width:85%" dateTimeType="datetime"/>
		</td>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ding" key="thirdDingNotifylog.fdRtnTime"/>
		</td><td width="35%">
			<xform:datetime property="fdRtnTime" style="width:85%" showStatus="view"  dateTimeType="datetime"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ding" key="thirdDingNotifylog.fdNotifyData"/>
		</td>
		<td colspan="3" width="35%">
			<div style="width: 100%; word-break:break-all;">
				<%-- <xform:textarea property="fdNotifyData" style="width:55%" /> --%>
				${thirdDingNotifylogForm.fdNotifyData }
			</div>
		</td>
		</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="third-ding" key="thirdDingNotifylog.fdRtnMsg"/>
		</td><td width="35%" colspan="3">
			<xform:textarea property="fdRtnMsg" style="width:85%" />
		</td>
	</tr>
	
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>