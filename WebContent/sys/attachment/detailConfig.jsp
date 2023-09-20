<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil,java.lang.String" %>
<%
	String url = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
    pageContext.setAttribute("tipUrl", url);
%>
<template:include ref="config.profile.edit" >
	<template:replace name="content">
		<div>
			<table >
			<tr >
			<%
				String currentLocaleCountry = SysLangUtil.getCurrentLocaleCountry();
			    if(currentLocaleCountry != null && currentLocaleCountry.equals("US"))
			    {
			%>
			<td >
					1.Find files in the local WPS installation directory “office6/cfgs/oem.ini”
                       </br>
					2.Check that the following configuration code exists or, if not, copy the following to the file to save
					</br>
					[Support]</br>
					JsApiPlugin = true</br>
					[Server]</br>
					JSPluginsServer = ${pageScope.tipUrl}/sys/attachment/sys_att_main/wps/oaassist/jsplugins.xml
					
				</td>
			<%
			    } else{
			%>
				<td >
					1.找到本地WPS安装目录下的文件“office6/cfgs/oem.ini”
                       </br>
					2.检查以下配置代码是否存在，如不存在，则将以下复制到该文件中保存即可
					</br>
					[Support]</br>
					JsApiPlugin = true</br>
					[Server]</br>
					JSPluginsServer = ${pageScope.tipUrl}/sys/attachment/sys_att_main/wps/oaassist/jsplugins.xml
					
				</td>
			<%
		      }
			%>
			</tr>
			</table>
		</div>
		<center style="margin-top: 10px;"> 
			<ui:button text="${lfn:message('button.ok') }" height="35" width="80" onclick="$dialog.hide(null);"> </ui:button>
         </center> 
	</template:replace>
</template:include>