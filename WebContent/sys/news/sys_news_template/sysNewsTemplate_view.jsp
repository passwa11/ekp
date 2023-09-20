<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script>
function confirmSetAuth(){
	var setAuth = confirm("<bean:message key="sysNews.updateAuth.confirm" bundle="sys-news"/>");
	return setAuth;
}
</script>
<%--参数--%>
<c:set var="formName" value="sysNewsTemplateForm" />
<c:set var="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
<%--标题--%>		
<p class="txttitle"><bean:message bundle="sys-news" key="table.sysNewsTemplate" /></p>
<%--按钮--%>
<div id="optBarDiv">
	<kmss:auth
		requestURL="/sys/news/sys_news_template/sysNewsTemplate.do?method=edit&fdId=${param.fdId}"
		requestMethod="GET">
		<input type="button" value="<bean:message key="sysNews.updateAuth" bundle="sys-news"/>"
			onclick="if(!confirmSetAuth())return;Com_OpenWindow('<c:url value="/sys/news/sys_news_main/sysNewsMain.do?method=setAuth&tmpId=${JsParam.fdId}"/>','_self');">
		<input type="button" value="<bean:message key="button.edit"/>"
			onclick="Com_OpenWindow('sysNewsTemplate.do?method=edit&fdId=${JsParam.fdId}&fdModelName=${fdModelName}','_self');">
	</kmss:auth>
	<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_CloseWindow();">
</div>
<center> 
<table id="Label_Tabel" width=95%>
	 <%--模板信息---%>		
	<c:import url="/sys/simplecategory/include/sysCategoryMain_view.jsp"
			charEncoding="UTF-8">
			<c:param name="formName" value="${formName}" />
			<c:param name="fdModelName" value="${fdModelName}" /> 
			<c:param name="fdKey" value="newsMainDoc" />
		</c:import>
	 <%--基本信息---%>
	 <tr
		LKS_LabelName="<bean:message bundle='sys-news' key='news.template.baseInfo'/>">
		<td>
		<table class="tb_normal" width=100%>
			<%-- 文档内容 --%>
			<tr>
				<td class="td_normal_title" width=15%><bean:message
					bundle="sys-news" key="sysNewsTemplate.docContent" /></td>
				<td colspan=3>${sysNewsTemplateForm.docContent}</td>
			</tr>
			<%-- 关键字 去掉
			<tr>
				<td class="td_normal_title" width=15%><bean:message
					bundle="sys-news" key="sysNews.docKeyword" /></td>
				<td width=83%><bean:write name="sysNewsTemplateForm"
					property="docKeywordNames" /></td>
			</tr>--%>
		   <!-- 标签机制 -->
				<c:import url="/sys/tag/include/sysTagTemplate_view.jsp"
					charEncoding="UTF-8">
					<c:param name="formName" value="sysNewsTemplateForm" />
					<c:param name="fdKey" value="newsMainDoc" /> 
				</c:import>
			<!-- 标签机制 -->
			<%-- 新闻重要度 --%>
			<tr>
				<td class="td_normal_title" width=15%><bean:message
					bundle="sys-news" key="sysNewsTemplate.fdImportance" /></td>
				<td colspan=3>
					<sunbor:enumsShow value="${sysNewsTemplateForm.fdImportance}" enumsType="sysNewsMain_fdImportance" />
				</td>
			</tr>
			<%-- 新闻可阅读者 --%>
			<tr>
				<td class="td_normal_title" width=15%><bean:message
					bundle="sys-news" key="table.sysNewsMainReader" /></td>
				<td width=83%><bean:write name="sysNewsTemplateForm"
					property="authTmpReaderNames" /></td>
			</tr>
			<%-- 新闻可编辑者 --%>
			<tr>
				<td class="td_normal_title" width=15%><bean:message
					bundle="sys-news" key="table.sysNewsMainEditor" /></td>
				<td width=83%><bean:write name="sysNewsTemplateForm"
					property="authTmpEditorNames" /></td>
			</tr>
		</table>
		</td>
	</tr> 
	<%--流程机制 --%>
	<c:import url="/sys/workflow/include/sysWfTemplate_view.jsp"
		charEncoding="UTF-8">
		<c:param name="formName" value="sysNewsTemplateForm" />
		<c:param name="fdKey" value="newsMainDoc" />
	</c:import>
	<!-- 规则机制 -->
	<c:import url="/sys/rule/sys_ruleset_temp/sysRuleTemplate_view.jsp" charEncoding="UTF-8">
		<c:param name="formName" value="sysNewsTemplateForm" />
		<c:param name="fdKey" value="newsMainDoc" />
		<c:param name="templateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate"></c:param>
	</c:import>
	 <%-- 提醒中心 --%>
	 <kmss:ifModuleExist path="/sys/remind/">
		 <c:import url="/sys/remind/include/sysRemindTemplate_view.jsp" charEncoding="UTF-8">
			 <%-- 模板Form名称 --%>
			 <c:param name="formName" value="sysNewsTemplateForm" />
			 <%-- KEY --%>
			 <c:param name="fdKey" value="newsMainDoc" />
			 <%-- 模板全名称 --%>
			 <c:param name="templateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
			 <%-- 主文档全名称 --%>
			 <c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
			 <%-- 主文档模板属性 --%>
			 <c:param name="templateProperty" value="fdTemplate" />
			 <%-- 模块路径 --%>
			 <c:param name="moduleUrl" value="sys/news" />
			 <c:param name="enable" value="${enableModule.enableSysRemind eq 'false' ? 'false' : 'true'}"></c:param>
		 </c:import>
	 </kmss:ifModuleExist>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>
