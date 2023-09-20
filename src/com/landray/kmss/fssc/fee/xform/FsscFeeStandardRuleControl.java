package com.landray.kmss.fssc.fee.xform;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.ENTER;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.filterNode;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getElementTab;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.isType;

import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;

import com.landray.kmss.sys.xform.base.service.ISysFormTemplateControl;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateDetailsTableControl;
import com.landray.kmss.sys.xform.base.service.controls.FilterAction;
import com.landray.kmss.sys.xform.base.service.controls.LoopAction;
import com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

public class FsscFeeStandardRuleControl  implements ISysFormTemplateControl, FilterAction,ISysFormTemplateDetailsTableControl{

	@Override
	public boolean parse(Node node, Lexer lexer, StringBuilder jsp, List<ISysFormTemplateControl> controls)
			throws Exception {
		if(node instanceof TagNode){
			TagNode tagNode = (TagNode) node;
			if(isType("standardRule",tagNode)){
				filterNode(tagNode, lexer, jsp, this);
				return true;
			}
		}
		return false;
	}
	
	private void doParse(Node node, final StringBuilder jsp, String idPrefix)
			throws ParserException {
		if (node instanceof InputTag) {
			InputTag input = (InputTag) node;
			if(StringUtil.isNotNull(idPrefix)){
				idPrefix+=".!{index}.";
			}
			String propss = input.getAttribute("props");
			if(StringUtil.isNull(propss)){
				return;
			}
			JSONObject props = JSONObject.fromObject(propss.replaceAll("\'", "\""));
			String id = idPrefix+props.getString("id");
			String fdStandardStatus = id+"_standard_status";
			String fdStandardInfo = id+"_standard_info";
			jsp.append(ENTER).append(getElementTab()).append("<xform:text showStatus=\"noShow\" property=\"extendDataFormInfo.value("+fdStandardStatus+")\"/>");
			jsp.append(ENTER).append(getElementTab()).append("<xform:text showStatus=\"noShow\" property=\"extendDataFormInfo.value("+fdStandardInfo+")\"/>");
			String showId = "standard_status";
			if("2".equals(props.get("fdMatchType"))){
				showId+="_!{index}";
			}
			jsp.append("<div id='"+showId+"' class='budget_container'></div>");
			jsp.append("<script>Com_IncludeFile(\"Number.js\", \"${LUI_ContextPath}/fssc/common/resource/js/\", 'js', true);</script>");
			jsp.append("<script>Com_IncludeFile(\"event.js\",Com_Parameter.ContextPath+\"fssc/fee/fssc_fee_xform/extendJs/\",\"js\",true);</script>").append(ENTER);
			jsp.append("<script>").append(ENTER).append("if(!window.Designer_Control_Standard_Rule_Props)window.Designer_Control_Standard_Rule_Props=JSON.parse(\"").append(input.getAttribute("props")).append("\".replace(/\'/g,'\"'))").append("</script>").append(ENTER);
			jsp.append("<link rel='stylesheet' href='${LUI_ContextPath}/fssc/fee/fssc_fee_xform/css/budgetRule.css'/>");
		}
	}

	@Override
	public boolean parseDetailsTable(Node node, Lexer lexer, StringBuilder templateJsp, final String idPrefix,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("standardRule", tagNode)) {
				TagNodeUtils.loopForDetailsTable(this, tagNode, lexer,
						templateJsp, idPrefix, controls, new LoopAction() {
							@Override
                            public boolean action(Node aTagNode, Lexer lexer,
                                                  StringBuilder jsp,
                                                  List<ISysFormTemplateControl> controls)
									throws Exception {
								// 保持与 parse中方法的判断一致
								if (aTagNode instanceof InputTag) {
									doParse(aTagNode, jsp, idPrefix);
									return true;
								}
								return false;
							}
						});
				return true;
			}
		}
		return false;
	}

	@Override
	public void start(Node node, StringBuilder jsp) throws ParserException {
		doParse(node, jsp,"");
		
	}

	@Override
	public void filter(Node node, StringBuilder jsp) throws ParserException {
		doParse(node, jsp,"");
		
	}

	@Override
	public void end(Node node, StringBuilder jsp) throws ParserException {
		doParse(node, jsp,"");
	}

}
