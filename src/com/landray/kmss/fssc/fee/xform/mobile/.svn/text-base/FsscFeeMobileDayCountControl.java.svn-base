package com.landray.kmss.fssc.fee.xform.mobile;

import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;

import com.landray.kmss.sys.xform.base.service.parse.JSPBuilder;
import com.landray.kmss.sys.xform.base.service.parse.ParseAnnotation;
import com.landray.kmss.sys.xform.base.service.parse.ParseContext;
import com.landray.kmss.sys.xform.base.service.parse.ParseElement;
import com.landray.kmss.sys.xform.base.service.parse.ParseHandler;
import com.landray.kmss.sys.xform.base.service.parse.ParseUtils;

import net.sf.json.JSONObject;
@ParseAnnotation(acceptType = "dayCount", removeDesignAttrs = false)
public class FsscFeeMobileDayCountControl implements ParseHandler{

	@Override
    public boolean parse(ParseElement elem, ParseContext context)
			throws Exception {
		JSPBuilder builder = context.newJSPBuilder();
		for (ParseElement e : elem) {
			if (e.isBegin()) {
				continue;
			}
			if (e.isEnd()) {
				continue;
			}
			if (e.getNode() instanceof InputTag) {
				doParse((InputTag) e.getNode(), builder);
				continue;
			}
		}
		return true;
	}
	
	private void doParse(InputTag node, final JSPBuilder jsp)
			throws ParserException {
		if (node instanceof InputTag) {
			InputTag input = (InputTag) node;
			String type = input.getAttribute("type");
			//隐藏字段不处理
			if("hidden".equals(type)){
				return;
			}
			JSONObject props = JSONObject.fromObject(input.getAttribute("props").replaceAll("\'", "\""));
			String id = props.getString("id");
			jsp.beginTag("xform:text");
			jsp.subject(props.getString("subject"));
			jsp.attr("property","extendDataFormInfo.value("+id+")");
			if(props.containsKey("required")){
				jsp.required(node);
			}
			if(props.containsKey("readOnly")){
				jsp.attr("showStatus","readOnly");
			}
			jsp.closeTag();
			jsp.endTag("xform:text");
		}
	}
}
