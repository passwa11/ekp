<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@ page import="com.landray.kmss.common.forms.ExtendForm" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeConfig" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeField" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeFieldEnum" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="java.util.Map,java.util.Iterator,java.util.List,java.util.ArrayList" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%
	        			Object form = TagUtils.getFormBean(request);
	        			if(form instanceof ExtendForm) {
	        				ExtendForm extendForm = (ExtendForm)form;
	        				String modelName = extendForm.getModelClass().getName();
	        				Map<String, String> dynamicAttrMap = extendForm.getCustomPropMap();
	        				DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
	        				if(dynamicConfig != null) {
	        					// 复制一份出来操作，否则会删除原有的数据
	        					List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
	        					for(int i=0; i<_list.size(); i++){
	        						DynamicAttributeField field = _list.get(i);
	        						String fieldType = field.getFieldType();
	        						String __fieldName = null;
	        						String __fieldId = null;
		        					String value = null;
		        					if("com.landray.kmss.sys.organization.model.SysOrgElement".equals(fieldType)){
		        						String mName = modelName;
		        						mName = mName.replace(".", "_");
		        						String isMulti = field.getIsMulti();
		        						pageContext.setAttribute("isMulti", isMulti);
		        						if("true".equals(isMulti)){
		        							__fieldName = "customPropMap(" + field.getFieldName() + "_" + mName + "_Names" + ")";
		        							__fieldId = "customPropMap(" + field.getFieldName() + "_" + mName + "_Ids" + ")";
		        						}else{
		        							__fieldName= "customPropMap(" + field.getFieldName() + "_" + mName + "_Name" + ")";
		        							__fieldId = "customPropMap(" + field.getFieldName() + "_" + mName + "_Id" + ")";
		        						}
		        					}else{
		        						Object obj = extendForm.getCustomPropMap().get(field.getFieldName());
		        						if(obj != null) {
		        							value = obj.toString();
		        						}
		        						__fieldName= "customPropMap(" + field.getFieldName() + ")";
		        						value=field.getDefaultValues();
		        						pageContext.setAttribute("value", value);
		        					}
		        					pageContext.setAttribute("__fieldId", __fieldId);
	        						pageContext.setAttribute("__fieldName", __fieldName);
	        						pageContext.setAttribute("__fieldText", field.getFieldTextByCurrentLang());
	        						if("true".equals(field.getRequired())) {
	        							pageContext.setAttribute("__fieldRequired", "true");
	        						}else{
	        							pageContext.setAttribute("__fieldRequired", "false");
	        						}

	        		%>
	        						<div class="ppc_c_list inputstring">
	        							<div class="ppc_c_list_head">
	        							<%=field.getFieldTextByCurrentLang() %>
	        							</div>
	        							<div class="ppc_c_list_body">
	        								<%
					if("radio".equals(field.getDisplayType())) { // 单选按钮
					%>
						<xform:radio required="${__fieldRequired }" property="${__fieldName}" subject="${lfn:escapeHtml(__fieldText)}" showStatus="${param.oper=='readonly'?'readOnly':'edit' }" validators="${validate}" value="<%=value%>" mobile="true">
						<%
						for(DynamicAttributeFieldEnum _enum : field.getFieldEnums()) {
							%>
							<xform:simpleDataSource value="<%=_enum.getValue()%>"><%=StringEscapeUtils.escapeHtml(_enum.getTextByByCurrentLang())%></xform:simpleDataSource>
							<%
						}
						%>
						 </xform:radio>
					<%
					} else if("checkbox".equals(field.getDisplayType())) { // 复选框
					%>
						<xform:checkbox required="${__fieldRequired }" property="${__fieldName}" subject="${lfn:escapeHtml(__fieldText)}" validators="${validate}" showStatus="${param.oper=='readonly'?'readOnly':'edit' }" value="<%=value%>" mobile="true">
						<%
						for(DynamicAttributeFieldEnum _enum : field.getFieldEnums()) {
							%>
							<xform:simpleDataSource value="<%=_enum.getValue()%>"><%=StringEscapeUtils.escapeHtml(_enum.getTextByByCurrentLang())%></xform:simpleDataSource>
							<%
						}
						%>
						 </xform:checkbox>
					<%
					} else if("select".equals(field.getDisplayType())) { // 下拉列表
					%>
						<xform:select required="${__fieldRequired }" property="${__fieldName}" subject="${lfn:escapeHtml(__fieldText)}" validators="${validate}" showStatus="${param.oper=='readonly'?'readOnly':'edit' }" value="<%=value%>" mobile="true">
						<%
						for(DynamicAttributeFieldEnum _enum : field.getFieldEnums()) {
							%>
							<xform:simpleDataSource  value="<%=_enum.getValue()%>"><%=StringEscapeUtils.escapeHtml(_enum.getTextByByCurrentLang())%></xform:simpleDataSource>
							<%
						}
						%>
						 </xform:select>
					<%
					} else if("java.util.Date".equals(field.getFieldType())) { // 日期，显示日期控件
					%>
						<xform:datetime required="${__fieldRequired }" property="${__fieldName}" subject="${__fieldText}" validators="${validate}" showStatus="${param.oper=='readonly'?'readOnly':'edit' }" dateTimeType="<%=field.getDisplayType()%>" value="<%=value%>" mobile="true"></xform:datetime>
					<%
					} else if("com.landray.kmss.sys.organization.model.SysOrgElement".equals(field.getFieldType())) { // 组织架构
					%>
						<xform:address required="${__fieldRequired }" propertyName="${__fieldName}" propertyId="${__fieldId}" mulSelect="${isMulti}" orgType="${fn:replace(displayType, ';' , '|')}" showStatus="${param.oper=='readonly'?'readOnly':'edit' }" idValue="<%=value%>" nameValue="${_defaultNames}" mobile="true"></xform:address>
					<%
					} else if("textarea".equals(field.getDisplayType())) { // 多行文本框
					%>
						<xform:textarea required="${__fieldRequired }" property="${__fieldName}" subject="${__fieldText}" showStatus="${param.oper=='readonly'?'readOnly':'edit' }" value="<%=value%>" mobile="true"></xform:textarea>
					<%
					} else { // 其它类型显示文本域
					%>
						<xform:text  required="${__fieldRequired }" className="" property="${__fieldName}" subject="${__fieldText}"  showStatus="${param.oper=='readonly'?'readOnly':'edit' }" mobile="true" value="<%=value%>"></xform:text>
					<%
					}
					%>
					
	        							</div>
	        						</div>
	        		<%				
	        					}
	        				}
	        			}
	        		%>
