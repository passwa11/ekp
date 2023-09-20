package com.landray.kmss.fssc.fee.xform;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.ENTER;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.filterNode;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getElementTab;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.isType;

import java.util.List;

import net.sf.json.JSONObject;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.ParserException;

import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateControl;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateDetailsTableControl;
import com.landray.kmss.sys.xform.base.service.controls.FilterAction;
import com.landray.kmss.sys.xform.base.service.controls.LoopAction;
import com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils;
import com.landray.kmss.util.ResourceUtil;

public class FsscFeeCtripSSOControl implements ISysFormTemplateControl, FilterAction,ISysFormTemplateDetailsTableControl{

	@Override
	public boolean parse(Node node, Lexer lexer, StringBuilder jsp, List<ISysFormTemplateControl> controls)
			throws Exception {
		if(node instanceof TagNode){
			TagNode tagNode = (TagNode) node;
			if(isType("ctripSSO",tagNode)){
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
			if(FsscCommonUtil.checkHasModule("/fssc/ctrip/")){
				JSONObject props = JSONObject.fromObject(input.getAttribute("props").replaceAll("\'", "\""));
				StringBuilder tempJsp = new StringBuilder();
				tempJsp.append(ENTER).append(getElementTab()).append("<xform:viewShow>");
				tempJsp.append(ENTER).append("<ui:button text=\""+ResourceUtil.getString("control.ctripSSO.button.title", "fssc-fee") +"\" name=\"ctripSSOButton\" onclick=\"bookticketOfPc(this);\" />");
				tempJsp.append(ENTER).append("<script>");
				tempJsp.append(ENTER).append("window.bookticketOfPc = function(obj){");
				tempJsp.append(ENTER).append("var fdCompanyId = '${fsscFeeMainForm.getExtendDataFormInfo().getValue('"+props.getString("companyId")+"')}';");
				tempJsp.append(ENTER).append("var docNumber = '${fsscFeeMainForm.getExtendDataFormInfo().getValue('"+props.getString("docNumberId")+"')}';");
				tempJsp.append(ENTER).append("var fdDetailNo = $(obj).parent().parent().find('td').eq(0).html();");
				tempJsp.append(ENTER).append("if(!(fdDetailNo*1)){fdDetailNo='1'}");
				tempJsp.append(ENTER).append("console.log(fdCompanyId+'@@'+docNumber+'@@'+fdDetailNo+'@@'+'"+props.getString("docNumberId")+"');");
				tempJsp.append(ENTER).append("window.open('<c:url value=\"/fssc/ctrip/fssc_ctrip_app_message/fsscCtripAppMessage.do\" />?method=bookPlaneTicketOfPc&fdCompanyId='+fdCompanyId+'&docNumber='+docNumber+'&fdDetailNo='+fdDetailNo);");
				tempJsp.append(ENTER).append("}");
				tempJsp.append(ENTER).append("</script>");
				tempJsp.append(ENTER).append("</xform:viewShow>");
				jsp.append(tempJsp.toString());
			}
		}
	}

	@Override
	public boolean parseDetailsTable(Node node, Lexer lexer, StringBuilder templateJsp, final String idPrefix,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("ctripSSO", tagNode)) {
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
