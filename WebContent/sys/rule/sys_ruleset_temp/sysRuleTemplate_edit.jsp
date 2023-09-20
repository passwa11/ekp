<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.rule.util.SysRuleTemplateUtil"%>
<%@page import="com.landray.kmss.util.AutoHashMap"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.landray.kmss.sys.rule.forms.SysRuleTemplateForm"%>
<%@page import="com.landray.kmss.sys.rule.model.SysRuleTemplate"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.sys.rule.forms.ISysRuleTemplateForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/rule/taglib/rule.tld" prefix="rule"%>
<%@page import="com.landray.kmss.sys.rule.taglib.RuleTextTag"%>
<%
	pageContext.setAttribute("isLangSuport", RuleTextTag.isLangSuportEnabled());
%>
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
	<tr LKS_LabelName="${lfn:message('sys-rule:sysRuleTemplate.title.1')}" style="display:none" id="sysRule_tab" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
			<script type="text/javascript">
				Com_IncludeFile("doclist.js|data.js");
				Com_IncludeFile('buttons.css','${LUI_ContextPath}'+'/sys/rule/resources/css/',null,true);
			</script>
			<script type="text/javascript" src="<c:url value='/sys/rule/resources/js/common.js'/>"></script>
			<script type="text/javascript" src="<c:url value='/sys/rule/resources/js/rule_template.js'/>"></script>
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
							String value = messageKeys[index];
							if(StringUtil.isNotNull(messageKeys[index]) && messageKeys[index].indexOf(":") != -1){
								String temp = ResourceUtil.getString(messageKeys[index]);
								if(StringUtil.isNotNull(temp)){
									value = temp;
								}
							}
							request.setAttribute("messageKey", value);
						%>
						<div style="text-align: center;line-height:30px">${messageKey }</div>
					</c:if>
					<input type="hidden" name="sysRuleTemplateForms.${key }.fdKey" value='${key}'>
					<input type="hidden" name="sysRuleTemplateForms.${key }.fdId" value='${sysRuleTemplateForm.fdId}'>
					<input type="hidden" name="sysRuleTemplateForms.${key }.quoteInfo" value="<c:out value='${sysRuleTemplateForm.quoteInfo }'></c:out>">
					<table id="ruleSetMap_${key }" class="tb_normal ruleSetMap" width="100%">
						<tr class="tr_normal_title">
							<td width="5%" align="center">
								<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
							</td>
							<td width="25%"><bean:message key="sysRuleTemplate.col.title.1" bundle="sys-rule"/></td>
							<td width="20%"><bean:message key="sysRuleTemplate.col.title.2" bundle="sys-rule"/></td>
							<td width="35%"><bean:message key="sysRuleTemplate.col.title.3" bundle="sys-rule"/></td>
							<td width="15%">
								<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="sysRuleTemplate.addRow('ruleSetMap_${key}','${key }');" style="cursor:pointer">
							</td>
						</tr>
						<%-- 基准行，KMSS_IsReferRow = 1 --%>
						<tr style="display:none;" KMSS_IsReferRow="1">
							<!-- 序号列，KMSS_IsRowIndex = 1 -->
							<td KMSS_IsRowIndex="1" align="center">
								!{index}
							</td>
							<td>
								<%-- <xform:text property="sysRuleTemplateForm.sysRuleTemplateEntrys[!{index}].fdName" required="true" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.1') }" style="width:90%" onValueChange="sysRuleTemplate.changeName"></xform:text> --%>
								<c:if test="${!isLangSuport }">
									<input class="inputsgl" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[!{index}].fdName" validate="required" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.1') }" style="width:90%" onblur="sysRuleTemplate.changeName(this.value,this,!{index});"/>
								</c:if>
								<c:if test="${isLangSuport }">
									<rule:text property="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[!{index}].fdName" validators="required maxLength(200)" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.1') }" style="width:75%"  htmlElementProperties="onblur=sysRuleTemplate.changeName(this.value,this,!{index})"></rule:text>
								</c:if>
								<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[!{index}].fdNameLangJson">
							</td>
							<td>
								<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[!{index}].sysRuleSetDocId">
								<xform:text property="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[!{index}].sysRuleSetDocName" showStatus="readOnly" required="true" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.2') }"></xform:text>
								<a href="#" class="btn_text rel_btn" onclick="sysRuleTemplate.selectRuleSet('${key }')">
									<bean:message key="dialog.selectOrg"/>
								</a>&nbsp;
								<a href="#" class="btn_text rel_btn" onclick="sysRuleTemplate.createRuleSet('${key }')">
									<bean:message key="button.add" bundle="sys-rule"/>
								</a>
							</td>
							<td>
								<input type="hidden" name='sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[!{index}].content'>
								<table class="tb_normal mapContent" width="100%">
									<tr class="tr_normal_title">
										<td width="50%"><bean:message key="sysRuleTemplate.col.title.4" bundle="sys-rule"/></td>
										<td width="50%"><bean:message key="sysRuleTemplate.col.title.5" bundle="sys-rule"/></td>
									</tr>
									<tr class="pivotRow" style="display:none">
										<td>
											<input type="hidden" name="mapId">
											<input type="hidden" name="ruleSetParamId">
											<xform:text property="ruleSetParamName" showStatus="readOnly" style="width:90%;border:0;color:black;">
											</xform:text>
										</td>
										<td>
											<xform:select property="xformField" style="width:90%" htmlElementProperties="onclick=sysRuleTemplate.recordFieldBeforeUpdateMap(this) onchange=sysRuleTemplate.updateMap(this.value,this,'${key }')">
											</xform:select>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<input type="hidden" class='lastSysRuleTemplateEntryId'>
								<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[!{index}].fdId">
								<div style="text-align:center">
									<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="sysRuleTemplate.addRow('ruleSetMap_${key}','${key }');" style="cursor:pointer">&nbsp;&nbsp;
									<img src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="if(sysRuleTemplate.updateBeforeDel('${key }')){DocList_DeleteRow(this.parentNode.parentNode.parentNode);}" style="cursor:pointer">
								</div>
							</td>
						</tr>
						<%-- 内容行 --%>
						<c:forEach items="${sysRuleTemplateForm.sysRuleTemplateEntrys}" var="sysRuleTemplateEntry" varStatus="vstatus">
						<tr KMSS_IsContentRow="1">
							<td align="center">
								${vstatus.index + 1}
							</td>
							<td>
								<%-- <xform:text property="sysRuleTemplateForm.sysRuleTemplateEntrys[${vstatus.index }].fdName" required="true" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.1') }" style="width:90%" value="${sysRuleTemplateEntry.fdName }" onValueChange="sysRuleTemplate.changeName"></xform:text> --%>
								<c:if test="${!isLangSuport }">
									<input class="inputsgl" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].fdName" validate = "required" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.1') }" style="width:90%" onblur="sysRuleTemplate.changeName(this.value,this,${vstatus.index })" value="${sysRuleTemplateEntry.fdName }"/>
								</c:if>
								<c:if test="${isLangSuport }">
									<rule:text property="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].fdName" validators="required" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.1') }" style="width:75%" langs='${sysRuleTemplateEntry.fdNameLangJson}' htmlElementProperties="onblur=sysRuleTemplate.changeName(this.value,this,${vstatus.index })"></rule:text>
								</c:if>
								<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].fdNameLangJson" value="<c:out value='${sysRuleTemplateEntry.fdNameLangJson }'></c:out>">
							</td>
							<td>
								<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].sysRuleSetDocId" value="${sysRuleTemplateEntry.sysRuleSetDocId }">
								<xform:text property="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].sysRuleSetDocName" showStatus="readOnly" required="true" subject="${lfn:message('sys-rule:sysRuleTemplate.col.title.2') }" value="${sysRuleTemplateEntry.sysRuleSetDocName }"></xform:text>
								<a href="#" class="btn_text rel_btn"  onclick="sysRuleTemplate.selectRuleSet('${key }')">
									<bean:message key="dialog.selectOrg"/>
								</a>&nbsp;
								<a href="#" class="btn_text rel_btn"  onclick="sysRuleTemplate.createRuleSet('${key }')">
									<bean:message key="button.add" bundle="sys-rule"/>
								</a>
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
											<xform:text property="ruleSetParamName" showStatus="readOnly" style="width:90%;border:0;color:black">
											</xform:text>
										</td>
										<td>
											<xform:select property="xformField" style="width:90%" htmlElementProperties="onclick=sysRuleTemplate.recordFieldBeforeUpdateMap(this) onchange=sysRuleTemplate.updateMap(this.value,this,'${key }')">
											</xform:select>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<input type="hidden" class='lastSysRuleTemplateEntryId' value="${sysRuleTemplateEntry.fdId }">
								<input type="hidden" name="sysRuleTemplateForms.${key }.sysRuleTemplateEntrys[${vstatus.index }].fdId" value="${sysRuleTemplateEntry.fdId }">
								<div style="text-align:center">
									<img src="<c:url value="/resource/style/default/icons/add.gif"/>" alt="add" onclick="sysRuleTemplate.addRow('ruleSetMap_${sysRuleTemplateForm.fdKey }','${key }');" style="cursor:pointer">&nbsp;&nbsp;
									<img src="<c:url value="/resource/style/default/icons/delete.gif"/>" alt="del" onclick="if(sysRuleTemplate.updateBeforeDel('${key }')){DocList_DeleteRow(this.parentNode.parentNode.parentNode);}" style="cursor:pointer">
								</div>
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
				var langJsonStr = <%=RuleTextTag.getLangsJsonStr()%>;
				var isLangSuport = <%=RuleTextTag.isLangSuportEnabled()%>;
				//初始化
				Com_AddEventListener(window,"load",function(){
					//预存sysRuleTemplateId
					var modelNames = '${modelName}';
					var fdKeys = '${param.fdKey}';
					sysRuleTemplate.modelNames = modelNames.split(";");
					sysRuleTemplate.fdKeys = fdKeys.split(";");
					for(var i=0; i<sysRuleTemplate.fdKeys.length; i++){
						if(sysRuleTemplate.fdKeys[i]){
							var sysRuleTemplateId = $("[name='sysRuleTemplateForms."+sysRuleTemplate.fdKeys[i]+".fdId']").val();
							if(!sysRuleTemplateId || sysRuleTemplateId==""){
								var unid = sysRuleTemplate.getUnid();
								sysRuleTemplateId = unid;
								$("[name^='sysRuleTemplateForms."+sysRuleTemplate.fdKeys[i]+".fdId']").val(unid);
							}
							sysRuleTemplate.ids.push(sysRuleTemplateId);
							sysRuleTemplate.keyToId[sysRuleTemplate.fdKeys[i]] = sysRuleTemplateId;
						}
					}
					//初始化内容
					sysRuleTemplate.init("edit");
					//注册切换页签的事件
					var table = document.getElementById("sysRule_tab").parentNode;
					while((table!=null) && (table.tagName!="TABLE")){
						table = table.parentNode;
					}
					if(table!=null){
						//页签切换调用的函数
						Doc_AddLabelSwitchEvent(table, "Rule_OnLabelSwitch_key");
					}
					//提交处理和校验
					Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
						//保存多语言名称
						if(isLangSuport){
							for(var j=0; j<sysRuleTemplate.fdKeys.length; j++){
								var fdNameObjs = $("[name ^= 'sysRuleTemplateForms."+sysRuleTemplate.fdKeys[j]+".sysRuleTemplateEntrys'][name $= 'fdName']");
								for(var i=0; i<fdNameObjs.length; i++){
									var fdNameObj = fdNameObjs[i];
									var name = $(fdNameObj).attr("name");
									handleMultiLangSave(name,"sysRuleTemplateForms."+sysRuleTemplate.fdKeys[j]+".sysRuleTemplateEntrys["+i+"].fdNameLangJson");
								}
							}
						}
						//删除无效数据（比如，设置完后但是后续节点或者控件被删除数据应该不保留）
						//sysRuleTemplate.delInvalidData();
						//引用内容
						sysRuleTemplate.writeData();
						return true;
					}
				})
				//切换页签
				window.Rule_OnLabelSwitch_key = function(tableName,index){
					sysRuleTemplate.reloadMap(tableName, index);
				}
				
				//保存多语言
				function handleMultiLangSave(eleName,targetName){
					var elLang=[];
					var fdValue = document.getElementsByName(eleName)[0].value;
					fdValue = _format(fdValue);
					var officialElName= eleName+"_"+langJsonStr["official"]["value"];
					document.getElementsByName(officialElName)[0].value=fdValue;
					var lang={};
					lang["lang"]=langJsonStr["official"]["value"];
					lang["value"]=fdValue;
					elLang.push(lang);
					for(var i=0;i<langJsonStr["support"].length;i++){
						var elName = eleName+"_"+langJsonStr["support"][i]["value"];
						if(elName==officialElName){
							continue;
						}
						lang={};
						lang["lang"]=langJsonStr["support"][i]["value"];
						lang["value"]=_format(document.getElementsByName(elName)[0].value);
						elLang.push(lang);
					}
					var fdNameLangJson = JSON.stringify(elLang);
					document.getElementsByName(targetName)[0].value = fdNameLangJson;
				}
				
				function _format(value){
					value=value||"";
					value = value.replace(/\r/g, "&#xD;");
					value = value.replace(/\n/g, "&#xA;");
					return value;
				}
			</script>
		</td>
	</tr>
</c:if>
<%
	}
%>