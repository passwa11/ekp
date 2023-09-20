<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@page import="com.landray.kmss.sys.ftsearch.util.ResultModelName" %>
<%@page import="com.landray.kmss.util.StringUtil" %>
<html>
<head>
<script type="text/javascript">
	function _submitForm(){
		Com_Submit(document.displayAreaForm, "save");
	};
</script>

<div id="optBarDiv">
	<input type="button" class="btnopt" value="<bean:message key="button.save"/>" onclick="_submitForm();" />
</div>
</head>
<body>
	<html:form action="/sys/ftsearch/expand/customDisplayAreaConfig.do?method=save">
		<p class="txttitle">
			<bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.personSearch.show.module.config"/>
		</p>
		<center>
			<table class="tb_normal" width=100% id="displayAreaForm">
				<tr>
					<td class="td_normal_title" width="20%"><bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.personSearch.module.config"/></td>
					<td>
						<xform:checkbox  property="value(kmss.ftsearch.person.config.module)" showStatus="edit" dataType="String" value="${displayAreaForm.map['kmss.ftsearch.person.config.module']}" >
							<%
								IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.ftsearch.personNameSearch","*","personSearchs");
								if(extensions != null){
									for(IExtension extension : extensions){
										String module = Plugin.getParamValueString(extension,"module");
										String value = ResultModelName.getModelName((String) Plugin.getParamValue(extension, "module"));
										String langSplit = "\u001A";
										if(value != null && value.indexOf(langSplit) > -1){
											String lang = null;
											String langLocale = com.landray.kmss.util.ResourceUtil.getLocaleStringByUser(request);
											if(langLocale ==null || langLocale.trim().length() == 0){
												lang = com.landray.kmss.sys.language.utils.SysLangUtil.getOfficialLang();
											}else{
												if(langLocale.split("-").length > 1){
													lang = langLocale.split("-")[1];
												}
											}
											if(lang != null && lang.trim().length() > 0){
												for(String ml : value.split(langSplit)){
													if(ml != null && ml.trim().length() > 0 && ml.indexOf("-") > -1){
														String[] mlSplit = ml.split("-");
														if(mlSplit.length > 1){
															if(lang.equalsIgnoreCase(mlSplit[0])){
																value = mlSplit[1];
																break;
															}
														}
													}
												}
											}
										}
										String outSystem=(String) Plugin.getParamValue(extension, "outSystem");
										if(StringUtil.isNotNull(outSystem)){
											module=outSystem+"@"+module;
										}
										request.setAttribute("id",module);
										request.setAttribute("value",value);
							%>
								<xform:simpleDataSource value="${id}">${value}</xform:simpleDataSource>
							<%}}%>
						</xform:checkbox>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%"><bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.personSearch.show.num"/></td>
					<td>
						<xform:select property="value(kmss.ftsearch.person.config.num)" showStatus="edit" value="${displayAreaForm.map['kmss.ftsearch.person.config.num']}" showPleaseSelect="false">
							<xform:simpleDataSource value="1">1</xform:simpleDataSource>
							<xform:simpleDataSource value="2">2</xform:simpleDataSource>
							<xform:simpleDataSource value="3">3</xform:simpleDataSource>
							<xform:simpleDataSource value="4">4</xform:simpleDataSource>
							<xform:simpleDataSource value="5">5</xform:simpleDataSource>
							<xform:simpleDataSource value="6">6</xform:simpleDataSource>
							<xform:simpleDataSource value="7">7</xform:simpleDataSource>
							<xform:simpleDataSource value="8">8</xform:simpleDataSource>
							<xform:simpleDataSource value="9">9</xform:simpleDataSource>
							<xform:simpleDataSource value="10">10</xform:simpleDataSource>
						</xform:select>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%"><bean:message bundle="sys-ftsearch-expand" key="sysFtsearch.personSearch.show.relevance"/></td>
					<td>
						<xform:select property="value(kmss.ftsearch.person.config.relevance)" showStatus="edit" value="${displayAreaForm.map['kmss.ftsearch.person.config.relevance']}" showPleaseSelect="false">
							<xform:simpleDataSource value="true">true</xform:simpleDataSource>
							<xform:simpleDataSource value="false">false</xform:simpleDataSource>
						</xform:select>
			            <div style="color: gray;"><bean:message key="sysFtsearch.personSearch.show.relevance.tip" bundle="sys-ftsearch-expand"/></div>
					</td>
				</tr>
			</table>
		</center>
	</html:form>
</body>
</html>
