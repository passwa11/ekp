<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@ page import="com.landray.kmss.common.forms.ExtendForm" %>
<%@ page import="com.landray.kmss.sys.language.utils.SysLangUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeUtil" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeConfig" %>
<%@ page import="com.landray.kmss.sys.property.custom.DynamicAttributeField" %>
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
			for(int i=0; i<_list.size(); i++) {
				DynamicAttributeField field = _list.get(i);
				String fieldType = field.getFieldType();
				String isMulti = field.getIsMulti();
				String value = "";
				if("com.landray.kmss.sys.organization.model.SysOrgElement".equals(fieldType)){
					String mName = modelName;
					mName = mName.replace(".", "_");
					if("true".equals(isMulti)){
						mName += "_Names";
					} else {
						mName += "_Name";
					}
					Object obj = extendForm.getCustomPropMap().get(field.getFieldName() + "_" + mName );
					if(obj != null) {
						value = obj.toString();
					}
				} else {
					Object obj = extendForm.getCustomPropMap().get(field.getFieldName());
					if(obj != null) {
						value = obj.toString();
					}
				}
				pageContext.setAttribute("value", value);
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
			<tr>
			<%
				}
			%>
				<td width=15% class="td_normal_title">
					<%=StringEscapeUtils.escapeHtml(field.getFieldTextByCurrentLang())%>
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
					if(!field.getFieldEnums().isEmpty()) { // 枚举类型
					%>
						<%=StringEscapeUtils.escapeHtml(field.getFieldEnum(value))%>
					<%
					} else { // 其它类型显示文本域
					%>
						<%=StringEscapeUtils.escapeHtml(value)%>
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
