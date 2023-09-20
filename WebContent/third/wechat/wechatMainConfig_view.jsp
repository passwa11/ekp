<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<html:form action="/third/wechat/wechatMainConfig.do" onsubmit="return validateWechatMainConfigForm(this);">
<div id="optBarDiv">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="Com_Submit(document.wechatMainConfigForm, 'update');">
</div>
<center>
<table class="tb_normal" width=95%>
	
	<tr>
		    <td class="td_normal_title" width=15%>License：</td>
				<td >
					<xform:text property="lwechat_license" style="width:100%"></xform:text>
				</td>
				<td class="td_normal_title" width=15%>${lfn:message('third-wechat:wechatMainConfig.qy.file')}</td>
				<td >
					<xform:text property="lwechat_qyDownloadUrl" style="width:100%"></xform:text>
				</td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%>${lfn:message('third-wechat:wechatMainConfig.url')}</td>
			<td width=25%>
				<xform:text property="lwechat_wyUrl" style="width:100%"></xform:text>
			</td>
			<td class="td_normal_title" width=15%>${lfn:message('third-wechat:wechatMainConfig.qy')}</td>
			<td>
			    <xform:text property="lwechat_qyUrl" style="width:100%"></xform:text>
			</td>
		</tr>
		
		<tr>
			<td class="td_normal_title" width=15%>${lfn:message('third-wechat:wechatMainConfig.notify.enable')}</td>
			<td width=25%> <xform:radio property="lwechat_wyEnable" showStatus="edit" >
					<xform:simpleDataSource value="1">${lfn:message('third-wechat:wechatMainConfig.notify1')}</xform:simpleDataSource>
					<xform:simpleDataSource value="0">${lfn:message('third-wechat:wechatMainConfig.notify0')}</xform:simpleDataSource>
				  </xform:radio>
			</td>
			<td class="td_normal_title" width=15% >${lfn:message('third-wechat:wechatMainConfig.qy.notify.enabel')}</td>
			<td><xform:radio property="lwechat_qyEnable" showStatus="edit" >
				<xform:simpleDataSource value="1">${lfn:message('third-wechat:wechatMainConfig.notify1')}</xform:simpleDataSource>
				<xform:simpleDataSource value="0">${lfn:message('third-wechat:wechatMainConfig.notify0')}</xform:simpleDataSource>
				</xform:radio>
			</td>
		</tr>
		
		<tr>
			<td class="td_normal_title" width=15%>${lfn:message('third-wechat:wechatMainConfig.we')}</td>
			<td >
				<xform:text property="lwechat_wyNotifyUrl" style="width:100%"></xform:text>
			</td>
			<td class="td_normal_title" width=15%>${lfn:message('third-wechat:wechatMainConfig.qy.we')}</td>
			<td >
				<xform:text property="lwechat_qyNotifyUrl" style="width:100%"></xform:text>
			</td>
		</tr>
</table>
</center>
<html:hidden property="method_GET"/>
</html:form>

<html:javascript formName="wechatMainConfigForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>

<%@ include file="/resource/jsp/edit_down.jsp"%>
