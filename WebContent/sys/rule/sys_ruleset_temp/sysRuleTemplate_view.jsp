<%@page import="com.landray.kmss.sys.rule.util.SysRuleTemplateUtil"%>
<%@page import="com.landray.kmss.util.AutoHashMap"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.landray.kmss.sys.rule.forms.SysRuleTemplateForm"%>
<%@page import="com.landray.kmss.sys.rule.model.SysRuleTemplate"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.rule.forms.ISysRuleTemplateForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="sysRuleTemplateForms" value="${templateForm.sysRuleTemplateForms}" />
<%
	Object _form = request.getAttribute(request.getParameter("formName"));
	if((_form instanceof ISysRuleTemplateForm) ) {
		//处理message
		String messageKey = (String)request.getParameter("messageKey");
		if(StringUtil.isNotNull(messageKey)){
			String[] messageKeys = messageKey.split(";");
			request.setAttribute("messageKeys", messageKeys);
		}
		//处理key值
		String fdKey = (String)request.getParameter("fdKey");
		if(StringUtil.isNotNull(fdKey)){
			String[] keys = fdKey.split(";");
			request.setAttribute("fdKeys", keys);
		}
		//处理model
		String modelName = SysRuleTemplateUtil.getMainModelName(
				(String)request.getParameter("templateModelName"),
				(String)request.getParameter("fdKey"));
		if(StringUtil.isNull(modelName)){
			modelName = (String)request.getParameter("modelName");
		}
		pageContext.setAttribute("modelName",modelName);
%>
<c:if test="${param.useLabel != 'false'}">
<tr id="sysRule_tab" LKS_LabelName="${lfn:message('sys-rule:sysRuleTemplate.title.1')}" style="display:none" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
	<td>
		<script type="text/javascript" src="<c:url value='/sys/rule/resources/js/common.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/sys/rule/resources/js/rule_template.js'/>"></script>
		<script type="text/javascript">
			Com_IncludeFile("doclist.js|data.js");
		</script>
		<c:forEach items="${fdKeys }" var="key" varStatus="keyStatus">
			<c:set var="key" value="${key}"></c:set>
			<%
				//处理获取当前对应的form
				AutoHashMap map = (AutoHashMap)pageContext.getAttribute("sysRuleTemplateForms");
				String key = (String)pageContext.getAttribute("key");
				request.setAttribute("sysRuleTemplateForm", map.get(key));
			%>
			<div><!-- 动态列表表格，组件，ID根据DocList_Info -->
				<c:if test="${not empty param.messageKey }">
					<c:set var="index" value="${keyStatus.index }"></c:set>
					<%
						//获取messagekey
						Integer index = (Integer)pageContext.getAttribute("index");
						String[] messageKeys = (String[])request.getAttribute("messageKeys");
						request.setAttribute("messageKey", messageKeys[index]);
					%>
					<div style="text-align: center;line-height:30px">${messageKey }</div>
				</c:if>
				<input type="hidden" name="sysRuleTemplateForms.${key }.fdId" value='${sysRuleTemplateForm.fdId }'>
				<input type="hidden" name="sysRuleTemplateForms.${key }.quoteInfo" value="<c:out value='${sysRuleTemplateForm.quoteInfo }'></c:out>">
				<table id="ruleSetMap_${key }" class="tb_normal ruleSetMap" width="100%">
					<tr class="tr_normal_title">
						<td width="50px;" align="center">
							<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
						</td>
						<td width="200px;"><bean:message key="sysRuleTemplate.col.title.1" bundle="sys-rule"/></td>
						<td width="200px"><bean:message key="sysRuleTemplate.col.title.2" bundle="sys-rule"/></td>
						<td width="200px"><bean:message key="sysRuleTemplate.col.title.3" bundle="sys-rule"/></td>
					</tr>
					<%-- 内容行 --%>
					<c:forEach items="${sysRuleTemplateForm.sysRuleTemplateEntrys}" var="sysRuleTemplateEntry" varStatus="vstatus">
					<tr KMSS_IsContentRow="1">
						<td align="center">
							${vstatus.index + 1}
						</td>
						<td>
							<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].fdId" value="${sysRuleTemplateEntry.fdId }">
							<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].fdName" value="${sysRuleTemplateEntry.fdName }">
							<xform:text showStatus="view" property="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].fdName" style="width:90%" value="${sysRuleTemplateEntry.fdName }"></xform:text>
						</td>
						<td>
							<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].sysRuleSetDocId" value="${sysRuleTemplateEntry.sysRuleSetDocId }">
							<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].sysRuleSetDocName" value="${sysRuleTemplateEntry.sysRuleSetDocName }">
							<xform:text property="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].sysRuleSetDocName" showStatus="view" value="${sysRuleTemplateEntry.sysRuleSetDocName }"></xform:text>
						</td>
						<td>
							<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].content" value="<c:out value='${sysRuleTemplateEntry.content }'></c:out>">
							<table class="tb_normal mapContent" width="100%">
								<tr class="tr_normal_title">
									<td width="50%"><bean:message key="sysRuleTemplate.col.title.4" bundle="sys-rule"/></td>
									<td width="50%"><bean:message key="sysRuleTemplate.col.title.5" bundle="sys-rule"/></td>
								</tr>
								<tr class="pivotRow">
									<td>
										<input type="hidden" name="mapId">
										<input type="hidden" name="ruleSetParamId">
										<input type="text" name="ruleSetParamName" style="border:none" readonly="readonly"/>
									</td>
									<td>
										<%-- <xform:select property="xformField" showStatus="view" style="width:90%">
										</xform:select> --%>
										<input type="hidden" name="xformFieldId">
										<input type="text" name="xformFieldName" style="border:none" readonly="readonly"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
					</c:forEach>
				</table>
				<script type="text/javascript">
					DocList_Info.push("ruleSetMap_${key}");
				</script>
			</div>
		</c:forEach>
		<script type="text/javascript">
			//初始化
			Com_AddEventListener(window,"load",function(){
				//预存sysRuleTemplateId
				var modelNames = '${modelName}';
				var fdKeys = '${param.fdKey}';
				sysRuleTemplate.modelNames = modelNames.split(";");
				sysRuleTemplate.fdKeys = fdKeys.split(";");
				for(var i=0; i<sysRuleTemplate.fdKeys.length; i++){
					var sysRuleTemplateId = $("[name='sysRuleTemplateForms."+sysRuleTemplate.fdKeys[i]+".fdId']").val();
					sysRuleTemplate.ids.push(sysRuleTemplateId);
					sysRuleTemplate.keyToId[sysRuleTemplate.fdKeys[i]] = sysRuleTemplateId;
				}
				//初始化内容
				sysRuleTemplate.init("view");
			})
		</script>
	</td>
</tr>
</c:if>
<%
	}
%>