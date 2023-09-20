<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="config.profile.edit" sidebar="no">

	<template:replace name="content">

		<html:form action="/sys/appconfig/sys_appconfig/sysAppConfig.do">

			<center style="margin-top: 20px;">

				<table class="tb_normal" width=95%>

					<tr>
						<td class="td_normal_title" width=30%>阿里云EndPoint</td>
						<td><xform:text property="value(endPoint)" required="true"
								style="width:95%" /></td>
					</tr>

					<tr>
						<td class="td_normal_title" width=15%>阿里云Bucket</td>
						<td><xform:text property="value(bucket)" required="true"
								style="width:95%" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>阿里云accessKeyId</td>
						<td><xform:text property="value(accessKeyId)" required="true"
								style="width:95%" /></td>
					</tr>
					<tr>
						<td class="td_normal_title" width=15%>阿里云accessKeySecret</td>
						<td><xform:text property="value(accessKeySecret)"
								htmlElementProperties="type=password" required="true"
								style="width:95%" /></td>
					</tr>

				</table>

			</center>

		</html:form>
	</template:replace>
</template:include>
