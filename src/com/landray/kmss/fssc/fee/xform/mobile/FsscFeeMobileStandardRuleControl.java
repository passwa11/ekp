package com.landray.kmss.fssc.fee.xform.mobile;

import java.util.Locale;

import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;

import com.landray.kmss.sys.xform.base.service.parse.JSPBuilder;
import com.landray.kmss.sys.xform.base.service.parse.ParseAnnotation;
import com.landray.kmss.sys.xform.base.service.parse.ParseContext;
import com.landray.kmss.sys.xform.base.service.parse.ParseElement;
import com.landray.kmss.sys.xform.base.service.parse.ParseHandler;
import com.landray.kmss.sys.xform.base.service.parse.ParseUtils;
import com.landray.kmss.util.ResourceUtil;

import net.sf.json.JSONObject;
@ParseAnnotation(acceptType = "standardRule", removeDesignAttrs = false)
public class FsscFeeMobileStandardRuleControl implements ParseHandler{

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
			JSONObject props = JSONObject.fromObject(input.getAttribute("props").replaceAll("\'", "\""));
			String id = props.getString("id");
			String tb = props.getString("fdMatchTable");
			jsp.html("<c:if test=\"${fsscFeeMainForm.extendDataFormInfo.formData['"+tb+"'][vstatus.index]['"+id+"_standard_status']=='0'}\">"+ResourceUtil.getString("py.standard.0","fssc-fee",Locale.getDefault())+"</c:if>");
			jsp.html("<c:if test=\"${fsscFeeMainForm.extendDataFormInfo.formData['"+tb+"'][vstatus.index]['"+id+"_standard_status']=='1'}\">"+ResourceUtil.getString("py.standard.1","fssc-fee",Locale.getDefault())+"</c:if>");
			jsp.html("<c:if test=\"${fsscFeeMainForm.extendDataFormInfo.formData['"+tb+"'][vstatus.index]['"+id+"_standard_status']=='2'}\">"+ResourceUtil.getString("py.standard.2","fssc-fee",Locale.getDefault())+"</c:if>");
		}
	}
}
