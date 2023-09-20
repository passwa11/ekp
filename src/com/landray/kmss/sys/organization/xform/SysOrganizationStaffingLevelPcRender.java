package com.landray.kmss.sys.organization.xform;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.xform.AbstractFieldElementTag;
import com.landray.kmss.web.taglib.xform.pc.AbstractFieldElementPcRender;

public class SysOrganizationStaffingLevelPcRender extends AbstractFieldElementPcRender {

	private SysOrganizationStaffingLevelTag SysOrganizationStaffingLevel = null;

	public SysOrganizationStaffingLevelPcRender(PageContext pageContext,
			AbstractFieldElementTag fieldTag) {
		super(pageContext, fieldTag);
		this.SysOrganizationStaffingLevel = (SysOrganizationStaffingLevelTag) fieldTag;
	}

	private String getDialogJs2() {
		StringBuffer sb = new StringBuffer();
		sb.append("__openStaffingLevelWindow").append("('").append(
				SysOrganizationStaffingLevel.getPropertyId()).append("','").append(
				SysOrganizationStaffingLevel.getPropertyName()).append("','").append("','").append("treeTitle")
				.append("','").append("winTitle").append("'");
		if (SysOrganizationStaffingLevel.getOnValueChange() != null) {
			
			sb.append(",").append("function(){").append(
					SysOrganizationStaffingLevel.getOnValueChange()).append("}");
		}
		sb.append(");");
		return sb.toString();
	}

	private String getDialogJs() {
		StringBuffer sb = new StringBuffer();
		sb.append("selectStaffingLevel").append("('").append(
				SysOrganizationStaffingLevel.getPropertyId()).append("','").append(
				SysOrganizationStaffingLevel.getPropertyName()).append("'");
		if (SysOrganizationStaffingLevel.getOnValueChange() != null) {

			sb.append(",").append("function(){").append(
					SysOrganizationStaffingLevel.getOnValueChange()).append("}");
		}
		sb.append(");");
		return sb.toString();
	}

	@Override
	protected void prepareOnValueChange(StringBuffer results)
			throws JspException {
		return;
	}

	@Override
	protected void renderElementInViewMode(StringBuffer results)
			throws JspException {
		String cxpath = ((HttpServletRequest) pageContext.getRequest())
				.getContextPath();
		List<String> idList = acquireValueList(SysOrganizationStaffingLevel.getPropertyId());
		List<String> nameList = null;
		if (idList.size() > 0) {
			results.append("<script>Com_IncludeFile('staffingLevel.js','"
					+ getContentPath()
					+ "/sys/organization/resource/js/',null,true);</script>");
			nameList = acquireValueList(SysOrganizationStaffingLevel.getPropertyName());
			for (int i = 0; i < idList.size(); i++) {
				results.append("<a class=\"com_author\" " + "href=\"" + cxpath
						+ "/sys/organization/base/kmStaffingLevelBase.do?method=view&fdId="
						+ idList.get(i) + "\" target=\"_blank\">");
				results.append(nameList.get(i));
				results.append("</a>");

			}
			return;
		}
		results.append(acquireValue(SysOrganizationStaffingLevel.getPropertyName(), SysOrganizationStaffingLevel
				.getNameValue()));
	}

	@Override
	protected void renderElementInEditingMode(StringBuffer results)
			throws JspException {
		// addHiddenPropertyeIdHtml(results);

		results.append("<script>Com_IncludeFile('staffingLevel.js','"
				+ getContentPath()
				+ "/sys/organization/resource/js/',null,true);</script>");

		results.append("<script>Com_IncludeFile('dialog.js');</script>");
		addReadOnlyInputPropertyeNameHtml(results, true, true);
		prepareRequired(results);
		// addSelectButtonHtml(results);
	}

	@Override
	protected void renderElementInHiddenMode(StringBuffer results)
			throws JspException {
		addHiddenPropertyeIdHtml(results);
		addHiddenPropertyeNameHtml(results);
	}

	@Override
	protected void renderElementInReadOnlyMode(StringBuffer results)
			throws JspException {

		addReadOnlyInputPropertyeNameHtml(results, false, false);

	}

	/**
	 * 名字输入框 textarea
	 * 
	 * @param results
	 * @throws JspException
	 */
	protected void addReadOnlyTextareaPropertyeNameHtml(StringBuffer results,
			boolean needValidation, boolean inEditing) throws JspException {
		if (StringUtil.isNull(SysOrganizationStaffingLevel.getStyle())) {
			SysOrganizationStaffingLevel.setStyle("width:100%");
		}
		results.append("<div class='inputselectmul' ");
		if (inEditing) {
			results.append(" onclick=\"" + getDialogJs() + "\" ");
		}
		prepareStyle(results);
		results.append(">");
		addHiddenPropertyeIdHtml(results);
		results.append("<div class=\"textarea\"><textarea");
		prepareAdditionalAttributes(results);
		prepareAttribute(results, "name", SysOrganizationStaffingLevel.getPropertyName());
		prepareAttribute(results, "class", SysOrganizationStaffingLevel.getClassName());
		if (needValidation) {
			prepareValidation(results);
		}
		prepareOtherAttributes(results);
		results.append(" readOnly>");
		results.append(acquireValue(SysOrganizationStaffingLevel.getPropertyName(), SysOrganizationStaffingLevel
				.getNameValue()));
		results.append("</textarea></div>");
		results.append("<div ");
		prepareAttribute(results, "class", "selectitem");
		results.append(" ></div>");
		results.append("</div>");
	}

	/**
	 * 名字输入框 input:text
	 * 
	 * @param results
	 * @throws JspException
	 */
	protected void addReadOnlyInputPropertyeNameHtml(StringBuffer results,
			boolean needValidation, boolean inEditing) throws JspException {
		if (StringUtil.isNull(SysOrganizationStaffingLevel.getStyle())) {
			SysOrganizationStaffingLevel.setStyle("width:100%");
		}
		results.append("<div class='"
				+ (inEditing ? "inputselectsgl" : "inputselectsglreadonly")
				+ "' ");
		if (inEditing) {
			results.append("onclick=\"" + getDialogJs() + "\"");
		}
		prepareStyle(results);
		results.append(">");
		addHiddenPropertyeIdHtml(results);
		results.append("<div class=\"input\"><input");
		prepareAdditionalAttributes(results);
		prepareAttribute(results, "name", SysOrganizationStaffingLevel.getPropertyName());
		prepareValue(results, SysOrganizationStaffingLevel.getPropertyName(), SysOrganizationStaffingLevel
				.getNameValue());
		prepareType(results, "text");
		if (needValidation) {
			prepareValidation(results);
		}
		results.append(" readOnly /></div>");
		results.append("<div ");
		prepareAttribute(results, "class", "selectitem");
		results.append(" ></div>");
		results.append("</div>");
	}

	/**
	 * 名字隐藏框
	 * 
	 * @param results
	 * @throws JspException
	 */
	protected void addHiddenPropertyeNameHtml(StringBuffer results)
			throws JspException {
		results.append("<input");
		prepareAttribute(results, "name", SysOrganizationStaffingLevel.getPropertyName());
		prepareValue(results, SysOrganizationStaffingLevel.getPropertyName(), SysOrganizationStaffingLevel
				.getValue());
		prepareType(results, "hidden");
		results.append(" />");
	}

	/**
	 * ID隐藏框
	 * 
	 * @param results
	 * @throws JspException
	 */
	protected void addHiddenPropertyeIdHtml(StringBuffer results)
			throws JspException {
		results.append("<input");
		prepareAttribute(results, "name", SysOrganizationStaffingLevel.getPropertyId());
		prepareValue(results, SysOrganizationStaffingLevel.getPropertyId(), SysOrganizationStaffingLevel
				.getIdValue());
		prepareType(results, "hidden");
		results.append(" />");
	}

	public String getContentPath() {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		String contentPath = request.getContextPath();
		if (contentPath.endsWith("/")) {
			contentPath = contentPath.substring(0, contentPath.length() - 1);
		}
		return contentPath;
	}

}
