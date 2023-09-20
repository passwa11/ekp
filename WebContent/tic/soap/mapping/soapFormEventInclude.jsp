<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.tic.core.mapping.plugins.TicCoreMappingIntegrationPlugins"%>
<%@page import="com.landray.kmss.tic.core.mapping.constant.Constant"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.tic.core.mapping.plugins.IBaseTicCoreMappingIntegration"%>
<%@page import="com.landray.kmss.tic.core.mapping.model.TicCoreMappingFunc"%>
<%@page import="java.util.List"%><script type="text/javascript">
Com_IncludeFile("dialog.js|doclist.js|jquery.js");
</script>
<!-- 避免重复引入,需要优化 -->
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/js/invoke.js"></script>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/js/ERP_data.js"></script>
<%
//取得对应扩展点信息
Map<String, String> pluginInfo_soapui=TicCoreMappingIntegrationPlugins.getConfigByType(Constant.FD_TYPE_SOAP);
if(pluginInfo_soapui!=null&&!pluginInfo_soapui.isEmpty()){
	//取得代码分离bean
	String beanName=pluginInfo_soapui.get(TicCoreMappingIntegrationPlugins.ekpIntegrationBean);
	Object bean =SpringBeanUtil.getBean(beanName);
	if(bean instanceof IBaseTicCoreMappingIntegration){
		List<TicCoreMappingFunc>  funcs=((IBaseTicCoreMappingIntegration)bean).getFormEventIncludeList(request);
		if(funcs!=null&&!funcs.isEmpty()){
			//System.out.println("查找3"+fdTemplateId);
		%>
		<script type="text/javascript">
			//以resource文件夹为准的相对路径..存在风险,避免重复引入脚本\
			commJs();
		</script>
		<%-- ------------${KMSS_Parameter_ContextPath}--------------- --%>
		<script src="${KMSS_Parameter_ContextPath}tic/soap/mapping/soapEkpFormEvent.js"></script>
		<% 
			for(int i=0;i<funcs.size();i++){	
				%>
			<%--c:import url="<%=funcs.get(i).getFormEventJspFilePath()%>" charEncoding="UTF-8">
			</c:import>
			--%>
			<%
				String jsp = funcs.get(i).getFdJspSegmentActual().replaceAll("&lt;", "<").replaceAll("&gt;", ">");
			%>
			<c:out value="<%=jsp%>" escapeXml="false"></c:out>
				<%
			}
		}
		
	}
}
%>


