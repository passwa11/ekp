package com.landray.kmss.fssc.fee.xform;

import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.ENTER;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.filterNode;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.getElementTab;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.isType;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.setAttribute;
import static com.landray.kmss.sys.xform.base.service.controls.TagNodeUtils.setTitleAndSubject;

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

public class FsscFeeVehicleBerthControl  implements ISysFormTemplateControl, FilterAction,ISysFormTemplateDetailsTableControl{

	@Override
	public boolean parse(Node node, Lexer lexer, StringBuilder jsp, List<ISysFormTemplateControl> controls)
			throws Exception {
		if(node instanceof TagNode){
			TagNode tagNode = (TagNode) node;
			if(isType("vehicleBerth",tagNode)){
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
			String fdVehicleId = id+"_vehicle_id";
			String fdVehicleName = id+"_vehicle_name";
			jsp.append(ENTER).append(getElementTab()).append("<xformflag flagtype='vehicle' flagid='"+id+"' _xform_type='vehicle' property='extendDataFormInfo.value("+id+")' id='_xform_extendDataFormInfo.value("+id+")'>");
			jsp.append(ENTER).append(getElementTab()).append("<xform:text showStatus=\"noShow\" property=\"extendDataFormInfo.value("+id+"_berth_id)\"/>");
			jsp.append(ENTER).append(getElementTab()).append("<xform:text showStatus=\"noShow\" property=\"extendDataFormInfo.value("+fdVehicleId+")\"/>");
			jsp.append(ENTER).append(getElementTab()).append("<xform:text showStatus=\"noShow\" property=\"extendDataFormInfo.value("+fdVehicleName+")\"/>");
			jsp.append(ENTER).append(getElementTab()).append("<xform:dialog propertyId=\"extendDataFormInfo.value(");
			jsp.append(id).append(")\" ").append("propertyName=\"extendDataFormInfo.value(");
			jsp.append(name).append(")\" ");
			if(props.containsKey("required")){
				jsp.append("required=\"true\" ");
			}
			if(props.containsKey("width")){
				jsp.append("style=\"width:").append(props.get("width")).append("\" ");
			}else{
				jsp.append("style=\"width:85%\" ");
			}
			if(props.containsKey("readOnly")){
				jsp.append("showStatus=\"readOnly\" ");
			}
			setTitleAndSubject(jsp, props.getString("subject"));
			setAttribute(jsp, "dialogJs", "Designer_DialogSelect(false,'eop_basedata_berth_fdParent','extendDataFormInfo.value("+id+")','extendDataFormInfo.value("+name+")',function(rtn,id){Designer_Control_Vehicle_Berth_Changed(rtn,id,'"+fdVehicleId+"','"+fdVehicleName+"')},{fdCompanyId:'"+props.getString("companyId")+"'});");
			jsp.append(">");
			jsp.append(ENTER).append(getElementTab()).append(getElementTab());
			jsp.append(ENTER).append(getElementTab()).append("</xform:dialog>")
					.append(ENTER);
			//引入对话框组件
			jsp.append("<script>Com_IncludeFile(\"dialog.js\",Com_Parameter.ContextPath+\"fssc/fee/fssc_fee_xform/extendJs/\",\"js\",true);</script>");
			jsp.append("<script>Com_IncludeFile(\"event.js\",Com_Parameter.ContextPath+\"fssc/fee/fssc_fee_xform/extendJs/\",\"js\",true);</script>").append(ENTER);
			jsp.append("<script>").append(ENTER).append("window.Designer_Control_Vehicle_Berth_List=window.Designer_Control_Vehicle_Berth_List||[];").append(ENTER);
			jsp.append("window.Designer_Control_Vehicle_Berth_List.push('"+id+"')").append(ENTER);
			jsp.append("window.Designer_Control_Company_Event_List['"+props.getString("companyId")+"'].push(Designer_Control_Vehicle_Berth_Load);").append(ENTER).append("</script>");
			jsp.append("<script>Designer_Control_ReplaceTag('"+id+"')</script></xformflag>");
		}
	}

	@Override
	public boolean parseDetailsTable(Node node, Lexer lexer, StringBuilder templateJsp, final String idPrefix,
			List<ISysFormTemplateControl> controls) throws Exception {
		if (node instanceof TagNode) {
			TagNode tagNode = (TagNode) node;
			if (isType("vehicleBerth", tagNode)) {
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
