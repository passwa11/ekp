package com.landray.kmss.third.ding.xform.controls.parse;

import org.htmlparser.Node;
import org.htmlparser.nodes.TagNode;

public class DingParseUtil {

	public static boolean isAttType(String attName, String name, Node node) {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			return (name.equals(tagNode.getAttribute(attName)));
		}
		return false;
	}

	public static boolean isClassRow(String name, Node node) {
		return isAttType("class", name, node);
	}

	public static boolean isDetailstableRow(Node node) {
		return isClassRow("ding_detailstable_row", node);
	}

	public static boolean isDetailstableTemplateRow(Node node) {
		return isAttType("type", "templateRow", node);
	}
}
