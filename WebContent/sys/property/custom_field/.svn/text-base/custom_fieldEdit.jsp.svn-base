<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
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
		Map<String, String> customPropMap = extendForm.getCustomPropMap();
		DynamicAttributeConfig dynamicConfig = DynamicAttributeUtil.getDynamicAttributeConfig(modelName);
		if(dynamicConfig != null) {
			// 复制一份出来操作，否则会删除原有的数据
			List<DynamicAttributeField> _list = dynamicConfig.getEnabledFields();
			for(int i=0; i<_list.size(); i++) {
				DynamicAttributeField field = _list.get(i);
				String fieldType = field.getFieldType();
				String __fieldName = null;
				String __fieldId = null;
				String _defaultValues = null;
				String _defaultIds = null;
				String _defaultNames = null;
				if("com.landray.kmss.sys.organization.model.SysOrgElement".equals(fieldType)){
					String mName = modelName;
					mName = mName.replace(".", "_");
					String isMulti = field.getIsMulti();
					pageContext.setAttribute("isMulti", isMulti);
					if("true".equals(isMulti)){
						__fieldName = field.getFieldName() + "_" + mName + "_Names";
						__fieldId = field.getFieldName() + "_" + mName + "_Ids";
					}else{
						__fieldName= field.getFieldName() + "_" + mName + "_Name";
						__fieldId = field.getFieldName() + "_" + mName + "_Id";
					}
					
					String oriValue = customPropMap.get(__fieldId);
					if (StringUtil.isNotNull(oriValue)) {
						_defaultIds = customPropMap.get(__fieldId);
						_defaultNames = customPropMap.get(__fieldName);
					} else {
						_defaultIds = field.getDefaultIds();
						_defaultNames = field.getDefaultNames();
					}
					
					__fieldName= "customPropMap(" + __fieldName + ")";
					__fieldId = "customPropMap(" + __fieldId + ")";
					
					pageContext.setAttribute("_defaultIds", _defaultIds);
					pageContext.setAttribute("_defaultNames", _defaultNames);
				}else{
					__fieldName= "customPropMap(" + field.getFieldName() + ")";
					//本身有值时不用默认值
					String oriValue = customPropMap.get(field.getFieldName());
					_defaultValues = StringUtil.isNotNull(oriValue) ? oriValue:field.getDefaultValues();
					pageContext.setAttribute("_defaultValues", _defaultValues);
				}
				
				String __fieldText = field.getFieldTextByCurrentLang();
				String validate = "";
				if("true".equals(field.getRequired())) {
					validate += " required";
					pageContext.setAttribute("required", "true");
				} else {
					pageContext.setAttribute("required", "");
				}
				if(field.getFieldType().contains("Integer")) {
					validate += " digits";
				}
				if(field.getFieldType().contains("Double")) {
					validate += " number scaleLength("+field.getScale()+")";
				}
				if(field.hasLength()) {
					validate += " maxLength(" + field.getFieldLength() + ")";
				}
				
				pageContext.setAttribute("__fieldName", __fieldName);
				pageContext.setAttribute("__fieldId", __fieldId);
				pageContext.setAttribute("displayType", field.getDisplayType());
				pageContext.setAttribute("validate", validate);
				pageContext.setAttribute("__fieldText", StringEscapeUtils.escapeHtml(__fieldText));
			%>
			<%
				if(i != 0 && i % 2 == 0) {
			%>
			</tr>
			<%
				}
			%>
			<%
				if(i % 2 == 0) {
			%>
			<tr class="lui-custom-Prop">
			<%
				}
			%>
				<td width=15% class="td_normal_title">
					<%=StringEscapeUtils.escapeHtml(__fieldText)%>
				</td>
				<%
					if(i % 2 == 0 && i == _list.size() -1) {
				%>
				<td width=85% colspan="3">
				<%
					} else {
				%>
				<td width=35%>
				<%
					}
				%>
				<%
					if("radio".equals(field.getDisplayType())) { // 单选按钮
					%>
						<xform:radio property="${__fieldName}" subject="${lfn:escapeHtml(__fieldText)}" validators="${validate}" showStatus="edit" value="${_defaultValues}">
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
						<xform:checkbox property="${__fieldName}" subject="${lfn:escapeHtml(__fieldText)}" validators="${validate}" showStatus="edit" value="${_defaultValues}">
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
						<xform:select property="${__fieldName}" subject="${lfn:escapeHtml(__fieldText)}" validators="${validate}" showStatus="edit" value="${_defaultValues}">
						<%
						for(DynamicAttributeFieldEnum _enum : field.getFieldEnums()) {
							%>
							<xform:simpleDataSource value="<%=_enum.getValue()%>"><%=StringEscapeUtils.escapeHtml(_enum.getTextByByCurrentLang())%></xform:simpleDataSource>
							<%
						}
						%>
						 </xform:select>
					<%
					} else if("java.util.Date".equals(field.getFieldType())) { // 日期，显示日期控件
					%>
						<xform:datetime property="${__fieldName}" subject="${__fieldText}" validators="${validate}" showStatus="edit" dateTimeType="<%=field.getDisplayType()%>" value="${_defaultValues}"></xform:datetime>
					<%
					} else if("com.landray.kmss.sys.organization.model.SysOrgElement".equals(field.getFieldType())) { // 组织架构
					%>
						<xform:address propertyName="${__fieldName}" propertyId="${__fieldId}" mulSelect="${isMulti}" required="${required}" orgType="${fn:replace(displayType, ';' , '|')}" showStatus="edit" idValue="${_defaultIds}" nameValue="${_defaultNames}" subject="<%=StringEscapeUtils.escapeHtml(__fieldText)%>"></xform:address>
					<%
					} else if("textarea".equals(field.getDisplayType())) { // 多行文本框
					%>
						<xform:textarea property="${__fieldName}" subject="${__fieldText}" validators="${validate}"  showStatus="edit" style="width:96%" value="${_defaultValues}"></xform:textarea>
					<%
					} else { // 其它类型显示文本域
					%>
						<xform:text property="${__fieldName}" subject="${__fieldText}" validators="${validate}"  showStatus="edit" style="width:80%" value="${_defaultValues}"></xform:text>
					<%
					}
					%>
					
					<%
					if("true".equals(field.getRequired()) && !"com.landray.kmss.sys.organization.model.SysOrgElement".equals(field.getFieldType())) { // 必填标识
					%>
						<span class="txtstrong">*</span>
					<%
					}
					%>
				</td>
				<%
					if(i == _list.size() -1 ) {
				%>
				</tr>
				<%
				}
				%>
			<%
			}
		}
	}
%>
