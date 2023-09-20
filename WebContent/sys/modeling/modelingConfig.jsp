<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<table class="tb_normal" width=100%>

	<tr>
		<td class="td_normal_title" width="15%">建模日志存放路径</td>
		<td>
			<xform:text property="value(modeling.log.path)"
				subject="建模日志存放路径" required="false" style="width:85%" showStatus="edit" /><br/>
			<span class="message">建模日志默认存放在应用服务器bin目录下的modeling目录,如Tomcat服务器地址可能是:C:/Java/apache-tomcat-7.0.0/bin/modeling</span>
		</td>
	</tr>
	
</table>
