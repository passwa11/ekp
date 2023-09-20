package com.landray.kmss.fssc.fee.xform;

import com.landray.kmss.sys.xform.base.service.ISysFormTemplateControl;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateDetailsTableControl;
import com.landray.kmss.sys.xform.base.service.controls.FilterAction;
import com.landray.kmss.sys.xform.base.service.controls.LoopAction;
import com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;

import java.util.List;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.*;

/**
 * 本币金额
 */
public class FsscFeeAccountMoneyControl implements ISysFormTemplateControl, FilterAction,ISysFormTemplateDetailsTableControl{

	@Override
	public boolean parse(Node node, Lexer lexer, StringBuilder jsp, List<ISysFormTemplateControl> controls)
			throws Exception {
		if(node instanceof TagNode){
			TagNode tagNode = (TagNode) node;
			if(isType("accountMoney",tagNode)){
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
			String type = input.getAttribute("type");
			//隐藏字段不处理
			if("hidden".equals(type)){
				return;
			}
			if(StringUtil.isNotNull(idPrefix)){
				idPrefix+=".!{index}.";
			}
			JSONObject props = JSONObject.fromObject(input.getAttribute("props").replaceAll("\'", "\""));
			String id = idPrefix+props.getString("id");
			jsp.append(ENTER).append("<xformflag flagtype='accountMoney' _xform_type='accountMoney' flagid='"+id+"' property='extendDataFormInfo.value("+id+")' id='_xform_extendDataFormInfo.value("+id+")'>");
			jsp.append(ENTER).append(getElementTab()).append("<xform:text property=\"extendDataFormInfo.value(");
			jsp.append(id).append(")\" ");
			jsp.append("required=\"true\" ");
			jsp.append("showStatus=\"readOnly\" ");
			if(props.containsKey("width")){
				jsp.append("style=\"width:").append(props.get("width")).append("\" ");
			}else{
				jsp.append("style=\"width:85%\" ");
			}
			jsp.append("/>");
			jsp.append("<script>");
			jsp.append(ENTER).append("if(!window.Designer_Control_AccountMoney_Props)window.Designer_Control_AccountMoney_Props=JSON.parse(\"").append(input.getAttribute("props")).append("\".replace(/\'/g,'\"'));").append(ENTER);
			jsp.append("Com_IncludeFile(\"Number.js\", \"${LUI_ContextPath}/fssc/common/resource/js/\", 'js', true);").append(ENTER);
			jsp.append("Com_IncludeFile(\"event.js\",Com_Parameter.ContextPath+\"fssc/fee/fssc_fee_xform/extendJs/\",\"js\",true);").append(ENTER);
			jsp.append("</script></xformflag>").append(ENTER);
		}
	}

	@Override
	public boolean parseDetailsTable(Node node, Lexer lexer, StringBuilder templateJsp, final String idPrefix,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("accountMoney", tagNode)) {
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
