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

public class FsscFeeWbsNumberControl implements ISysFormTemplateControl, FilterAction,ISysFormTemplateDetailsTableControl{

	@Override
	public boolean parse(Node node, Lexer lexer, StringBuilder jsp, List<ISysFormTemplateControl> controls)
			throws Exception {
		if(node instanceof TagNode){
			TagNode tagNode = (TagNode) node;
			if(isType("wbsNumber",tagNode)){
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
			String name = idPrefix+props.getString("id")+"_name";
			StringBuilder tempJsp = new StringBuilder();
			jsp.append(ENTER).append(getElementTab()).append("<xformflag flagtype='wbs' flagid='"+id+"' _xform_type='wbs' property='extendDataFormInfo.value("+id+")' id='_xform_extendDataFormInfo.value("+id+")'>");
			tempJsp.append(ENTER).append(getElementTab()).append("<xform:dialog propertyId=\"extendDataFormInfo.value(");
			tempJsp.append(id).append(")\" ").append("propertyName=\"extendDataFormInfo.value(");
			tempJsp.append(name).append(")\" ");
			if(props.containsKey("required")){
				tempJsp.append("required=\"true\" ");
			}
			if(props.containsKey("width")){
				tempJsp.append("style=\"width:").append(props.get("width")).append("\" ");
			}else{
				tempJsp.append("style=\"width:85%\" ");
			}
			if(props.containsKey("readOnly")){
				tempJsp.append("showStatus=\"readOnly\" ");
			}
			setTitleAndSubject(tempJsp, props.getString("subject"));
			setAttribute(tempJsp, "dialogJs", "Designer_DialogSelect(false,'eop_basedata_wbs_fdWbs','extendDataFormInfo.value("+id+")','extendDataFormInfo.value("+name+")',null,{fdCompanyId:'"+props.getString("companyId")+"',fdProjectId:'"+props.getString("projectId")+"'});");
			tempJsp.append(">");
			tempJsp.append(ENTER).append(getElementTab()).append(getElementTab());
			tempJsp.append(ENTER).append(getElementTab()).append("</xform:dialog>")
					.append(ENTER);
			//引入对话框组件
			tempJsp.append("<script>Com_IncludeFile(\"dialog.js\",Com_Parameter.ContextPath+\"fssc/fee/fssc_fee_xform/extendJs/\",\"js\",true);</script>");
			tempJsp.append("<script>Com_IncludeFile(\"event.js\",Com_Parameter.ContextPath+\"fssc/fee/fssc_fee_xform/extendJs/\",\"js\",true);</script>").append(ENTER);
			tempJsp.append("<script>").append(ENTER).append("window.Designer_Control_WbsNumber_List=window.Designer_Control_WbsNumber_List||[];").append(ENTER);
			tempJsp.append("window.Designer_Control_WbsNumber_List.push('"+id+"')").append(ENTER);
			tempJsp.append("window.Designer_Control_Company_Event_List['"+props.getString("companyId")+"'].push(Designer_Control_WbsNumber_Load);").append(ENTER).append("</script>");
			jsp.append(tempJsp.toString());
			jsp.append("<script>Designer_Control_ReplaceTag('"+id+"')</script></xformflag>");
		}
	}

	@Override
	public boolean parseDetailsTable(Node node, Lexer lexer, StringBuilder templateJsp, final String idPrefix,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("wbsNumber", tagNode)) {
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
