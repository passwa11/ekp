package com.landray.kmss.sys.attend.xform;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.ENTER;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.filterNode;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getElementTab;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getFieldName;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.isType;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.setAttribute;

import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.ParserException;

import com.landray.kmss.sys.xform.base.service.ISysFormTemplateControl;
import com.landray.kmss.sys.xform.base.service.controls.FilterAction;
import com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils;
import com.landray.kmss.util.StringUtil;

public class SysAttendTemplateMapControl implements ISysFormTemplateControl,
		FilterAction {

	@Override
	public boolean parse(Node node, Lexer lexer, StringBuilder jsp,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("map", tagNode)) {
				filterNode(tagNode, lexer, jsp, this);
				return true;
			}
		}
		return false;
	}

	@Override
	public void start(Node node, StringBuilder jsp) throws ParserException {
		TagNode tagNode = (TagNode) node;
		jsp.append(
				"<%@ taglib uri=\"/WEB-INF/KmssConfig/sys/attend/map.tld\" prefix=\"map\" %>");
		// 编辑状态 start
		jsp.append(ENTER).append(getElementTab())
				.append(TagNodeUtils.buildControlWrapHTMLBegin(
						tagNode.getAttribute("id"), "map",
						"map"));
		jsp.append(ENTER).append("<map:location ");
		setAttribute(jsp, "propertyName", getFieldName(tagNode));
		setAttribute(jsp, "propertyCoordinate",
				getFieldName(tagNode.getAttribute("id") + "Coordinate"));
		setAttribute(jsp, "propertyProvince",
				getFieldName(tagNode.getAttribute("id") + "Province"));
		setAttribute(jsp, "propertyCity",
				getFieldName(tagNode.getAttribute("id") + "City"));
		setAttribute(jsp, "propertyDistrict",
				getFieldName(tagNode.getAttribute("id") + "District"));
		/** #56902 start **/
		setAttribute(jsp, "propertyDetail",
				getFieldName(tagNode.getAttribute("id") + "Detail"));
		/** #56902 end **/
		jsp.append(" style='width:" + tagNode.getAttribute("_width") + ";'");
		// 只读
		String readOnly = tagNode.getAttribute("_readOnly");
		if (StringUtil.isNotNull(readOnly)
				&& "true".equalsIgnoreCase(readOnly)) {
			jsp.append(" showStatus='readOnly' ");
		}
		// 地点选择范围
		String radius = tagNode.getAttribute("radius");
		if (StringUtil.isNotNull(radius)) {
			jsp.append(" radius='"+ radius +"' ");
		}
		// 显示值
		String isModify = tagNode.getAttribute("ismodify");
		if (StringUtil.isNotNull(isModify)) {
			jsp.append(" isModify='"+ isModify +"' ");
		}
		// 显示值
		String defaultvalue = tagNode.getAttribute("defaultvalue");
		if (StringUtil.isNotNull(defaultvalue)) {
			jsp.append(" defaultValue='" + defaultvalue + "' ");
		}
		// 必填校验
		String required = tagNode.getAttribute("_required");
		if (StringUtil.isNotNull(required)
				&& "true".equalsIgnoreCase(required)) {
			jsp.append(" validators='required' ");
			jsp.append(" required='true' ");
		}
		setAttribute(jsp, "subject",
				"${xform:subject('" + tagNode.getAttribute("id")
						+ "','label')}");
		jsp.append("></map:location>");
		jsp.append(ENTER).append(TagNodeUtils.buildControlWrapHTMLEnd());
		// end
	}

	@Override
	public void filter(Node node, StringBuilder jsp) throws ParserException {
		// TODO Auto-generated method stub

	}

	@Override
	public void end(Node node, StringBuilder jsp) throws ParserException {

	}



}
