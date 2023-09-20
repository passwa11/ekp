package com.landray.kmss.third.ding.xform.controls;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.ENTER;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getElementTab;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.setProperty;

import java.util.List;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.ParserException;

import com.landray.kmss.sys.xform.base.service.ISysFormTemplateControl;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateDetailsTableControl;
import com.landray.kmss.sys.xform.base.service.controls.LoopAction;
import com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils;
import com.landray.kmss.third.ding.xform.controls.parse.DingParseUtil;

public class ThirdDingDetailsTableControl
		implements ISysFormTemplateControl {

	private static final Pattern indexNamePattern = Pattern
			.compile("\\!\\{index\\}");

	private static final Pattern noNamePattern = Pattern.compile("\\{1\\}");

	@Override
	public boolean parse(Node node, Lexer lexer, StringBuilder jsp,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (TagNodeUtils.isType("dingDetailsTable", tagNode)) {
				doParse(tagNode, lexer, jsp, controls);
				return true;
			}
		}
		return false;
	}

	private void doParse(TagNode tagNode, Lexer lexer, StringBuilder jsp,
			List<ISysFormTemplateControl> controls) throws Exception {
		String tagName = tagNode.getTagName();
		final String tableId = tagNode.getAttribute("id");
		start(tagNode, jsp);
		tagNode = (TagNode) lexer.nextNode();
		Node node = loopDoParse(tagName, tagNode, lexer, jsp,
				new LoopAction() {
					@Override
					public boolean action(Node tagNode, Lexer lexer,
							StringBuilder jsp,
							List<ISysFormTemplateControl> controls)
							throws Exception {
						// 找到模板行处理
						if (DingParseUtil.isDetailstableRow(tagNode)) {
							tagNode = doParseTemplateRow((TagNode) tagNode,
									lexer, tableId, controls, jsp);
							return true;
						}
						return false;
					}

					private Node doParseTemplateRow(TagNode templateRowNode,
							Lexer lexer, final String tableId,
							List<ISysFormTemplateControl> controls,
							StringBuilder jsp) throws Exception {
						StringBuilder contentJsp = new StringBuilder();
						String startTag = templateRowNode.toHtml();
						String _templateStartTag = templateRowNode.toHtml();
						TagNode nextNode = (TagNode) lexer.nextNode();
						Node endNode = loopDoParse(templateRowNode.getTagName(),
								nextNode, lexer, contentJsp,
								new LoopAction() {
									@Override
									public boolean action(Node tagNode,
											Lexer lexer,
											StringBuilder jsp,
											List<ISysFormTemplateControl> controls)
											throws Exception {
										for (ISysFormTemplateControl control : controls) {
											if (control instanceof ISysFormTemplateDetailsTableControl) {
												ISysFormTemplateDetailsTableControl c = (ISysFormTemplateDetailsTableControl) control;
												if (c.parseDetailsTable(tagNode,
														lexer, jsp,
														tableId, controls)) {
													return true;
												}
											}
										}
										return false;
									}
								}, controls);
						String endTag = endNode.toHtml();
						// 补全隐藏的行ID
						fillHiddenIdToContentJsp(contentJsp);
						jsp.append(_templateStartTag + contentJsp.toString()
								+ endTag);
						// 添加view页面的foreach循环
						jsp.append(getViewForEachHtml(
								startTag.replaceAll("(?i)KMSS_IsReferRow",
										"KMSS_IsContentRow")
										+ contentJsp.toString()
										+ endTag));
						return endNode;
					}

					private void
							fillHiddenIdToContentJsp(StringBuilder contentJsp) {
						StringBuilder hiddenId = new StringBuilder();
						hiddenId.append(ENTER).append(getElementTab()).append(
								"<%-- 明细表行ID --%>").append(ENTER).append(
										getElementTab());
						hiddenId.append("<xform:text showStatus=\"noShow\" ");
						TagNodeUtils.setAttribute(hiddenId, "property",
								TagNodeUtils
										.getFieldName(tableId
												+ ".${vstatus.index}.fdId"));
						hiddenId.append(" />").append(ENTER);
						String hiddenIdStr = hiddenId.toString();

						String templateJspUpper = contentJsp.toString()
								.toUpperCase();
						int myIndex = templateJspUpper.indexOf("</TD>");

						contentJsp.insert(myIndex, hiddenIdStr.replace(
								"${vstatus.index}", "!{index}"));
					}

					private String
							getViewForEachHtml(String templateJspStr) {
						StringBuilder rs = new StringBuilder();
						// 添加contentJsp的循环
						String forEachBegin = "<c:forEach items=\"${_xformForm.extendDataFormInfo.formData."
								+ tableId
								+ "}\" var=\"_xformEachBean\" varStatus=\"vstatus\">";
						String forEachEnd = "</c:forEach>";
						templateJspStr = indexNamePattern
								.matcher(templateJspStr)
								.replaceAll("\\$\\{vstatus.index\\}");
						templateJspStr = noNamePattern.matcher(templateJspStr)
								.replaceAll("\\$\\{vstatus.index + 1\\}");
						rs.append(ENTER).append(forEachBegin);
						rs.append(templateJspStr);
						rs.append(ENTER).append(forEachEnd);
						return rs.toString();
					}
				}, controls);
		end(node, jsp);
	}

	/**
	 * 不使用TagNodeUtils.loopDoParse方法，在没有匹配到控件元素时，原样输出
	 * 
	 * @param tagName
	 * @param tagNode
	 * @param lexer
	 * @param jsp
	 * @param action
	 * @param controls
	 * @return
	 * @throws Exception
	 */
	public Node loopDoParse(String tagName, Node tagNode,
			Lexer lexer, StringBuilder jsp, LoopAction action,
			List<ISysFormTemplateControl> controls) throws Exception {
		tagName = tagName.toUpperCase();
		String start = "<" + tagName;
		String end = "</" + tagName;

		int index = 1;
		boolean deal = false;
		Node node = tagNode;
		do {
			deal = action.action(node, lexer, jsp, controls);
			if (deal) {
				deal = false;
			} else {
				String html = node.toHtml().toUpperCase();
				if (html.startsWith(start)) {
					index++;
				} else if (html.startsWith(end)) {
					index--;
				}
				if (index > 0) {
					// 在没有匹配到控件元素时，原样输出
					jsp.append(node.toHtml());
				}
			}
			if (index == 0) {
				return node;
			}
		} while (null != (node = lexer.nextNode()));
		return node;
	}

	public void start(Node node, StringBuilder jsp) throws ParserException {
		TagNode tagNode = (TagNode) node;
		jsp.append(ENTER).append(getElementTab())
				.append("<xform:XFormLUIWidget");
		setProperty(jsp, tagNode);
		TagNodeUtils.setAttribute(jsp, "luiType",
				"third/ding/third_ding_xform/control/dingDetailsTableRun!DingDetailsTableRun");
		jsp.append(">");

		String label = tagNode.getAttribute("label");
		jsp.append(ENTER).append(
				"<xform:LUIVar name='subject' value='" + label + "' />");

		String id = "TABLE_DL_" + tagNode.getAttribute("id");
		jsp.append(ENTER).append(
				"<script>Com_IncludeFile('doclist.js');</script>");
		jsp.append("<script>DocList_Info.push('").append(id).append(
				"');</script>");
		TagNodeUtils.removeDesignAttrs((TagNode) node);
	}


	public void end(Node node, StringBuilder jsp) throws ParserException {
		jsp.append("</xform:XFormLUIWidget>").append(ENTER);
	}

}
