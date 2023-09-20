package com.landray.kmss.km.signature.actions;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.filterNode;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getElementTab;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.isType;

import java.util.Date;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Div;
import org.htmlparser.util.ParserException;

import com.landray.kmss.sys.xform.base.service.ISysFormTemplateControl;
import com.landray.kmss.sys.xform.base.service.controls.FilterAction;
import com.landray.kmss.util.IDGenerator;

public class KmexReferenceController implements ISysFormTemplateControl,
		FilterAction {
	private static final String TYPE = "reference";
	private String fieldId;
	private String myDoc;
	private String status;

	@Override
    public boolean parse(Node node, Lexer lexer, StringBuilder jsp,
                         List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType(TYPE, tagNode)) {
				filterNode(tagNode, lexer, jsp, this);
				return true;
			}
		}
		return false;
	}

	private void doParse(Node node, final StringBuilder jsp)
			throws ParserException {
		if (node instanceof Div) {
			String et = getElementTab();
			Div div = (Div) node;
			String sigtext = div.getAttribute("sigtext");
			fieldId = div.getAttribute("id");
			String style = div.getAttribute("style");
			jsp.append("<c:import url='/km/signature/sig_from_template/sig_from_jsp.jsp' charEncoding='UTF-8'><c:param name='FieldName' value='abc"
					+ IDGenerator.generateID(new Date()) + "' /> </c:import>");
		}
	}

	@Override
    public void end(Node node, StringBuilder jsp) throws ParserException {
		doParse(node, jsp);
	}

	@Override
    public void start(Node node, StringBuilder jsp) throws ParserException {
		doParse(node, jsp);
	}

	protected boolean updateDocRight() {
		return false;
	}

	@Override
    public void filter(Node arg0, StringBuilder arg1) throws ParserException {

	}
}
