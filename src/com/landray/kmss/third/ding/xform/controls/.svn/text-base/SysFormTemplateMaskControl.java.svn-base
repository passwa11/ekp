package com.landray.kmss.third.ding.xform.controls;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.ENTER;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getElementTab;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.isType;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.ParserException;

import com.landray.kmss.sys.xform.base.service.ISysFormTemplateControl;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateDetailsTableControl;
import com.landray.kmss.sys.xform.base.service.controls.LoopAction;
import com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils;

@SuppressWarnings("all")
public class SysFormTemplateMaskControl implements ISysFormTemplateControl,ISysFormTemplateDetailsTableControl{

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(SysFormTemplateMaskControl.class);
	private static final Node TagNode = null;

	public boolean parse(Node node, Lexer lexer, StringBuilder jsp,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("mask", tagNode)) {
				doParse(tagNode, lexer, jsp, controls, false, null);
				return true;
			}
		}
		return false;
	}

	private void doParse(TagNode tagNode, Lexer lexer, StringBuilder jsp,
			List<ISysFormTemplateControl> controls, final boolean isDetail,
			final String tableId) throws Exception {
		String tagName = tagNode.getTagName();
		start(tagNode, jsp);
		Node node = TagNodeUtils.loopDoParse(tagName, tagNode, lexer, jsp,
				new LoopAction() {
					public boolean action(Node tagNode, Lexer lexer,
							StringBuilder jsp,
							List<ISysFormTemplateControl> controls)
							throws Exception {
						for (ISysFormTemplateControl control : controls) {
							if (isDetail) {
								if (control instanceof ISysFormTemplateDetailsTableControl) {
									ISysFormTemplateDetailsTableControl c = (ISysFormTemplateDetailsTableControl) control;
									if (c.parseDetailsTable(tagNode, lexer,
											jsp, tableId, controls)) {
										return true;
									}
								}
							} else {
								if (control
										.parse(tagNode, lexer, jsp, controls)) {
									return true;
								}
							}
						}
						return false;
					}
				}, controls);
		end(node, jsp);
	}

	private final void end(Node node, StringBuilder jsp) throws ParserException {
		log.debug("mask控件结束！");
		TagNode tagNode = (TagNode) node;
		jsp.append(ENTER).append(getElementTab()).append("</div>");
		jsp.append(ENTER).append(TagNodeUtils.buildControlWrapHTMLEnd());
	}

	private final void start(Node node, StringBuilder jsp)
			throws ParserException {
		log.debug("mask控件开始...");
		if(node instanceof TagNode){
			TagNode tagNode = (TagNode) node;
			String id = tagNode.getAttribute("id");
			jsp.append(ENTER).append(getElementTab())
					.append(TagNodeUtils.buildControlWrapHTMLBegin(id,
							"mask",
							"mask"));
			TagNodeUtils.removeDesignAttrs(tagNode);
			jsp.append(getElementTab()).append(tagNode.toHtml());
		}
	}

	public boolean parseDetailsTable(Node node, Lexer lexer,
			StringBuilder templateJsp, String idPrefix,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("mask", tagNode)) {
				TagNodeUtils.setDetailsTableId(idPrefix, tagNode);
				doParse(tagNode, lexer, templateJsp, controls, true, idPrefix);
				return true;
			}
		}
		return false;
	}
}
