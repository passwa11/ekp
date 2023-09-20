Com_IncludeFile("treeview.js|jquery.js");

/**
 * 
 * @param treeview
 * @returns
 */
function Modeling_Nav_DrawNodeInnerHTML(node, indent_level)
{
	var Result;
	var isEnd = false;
	if (this.OnNodeQueryDraw!=null){
		Result = this.OnNodeQueryDraw(node)
		if (typeof(Result)=="string")
			isEnd = true;
	}
	if(!isEnd){
		var display = "";
		if(node.id == 0){
			display = "display:none;"
		}
		Result = "<table style='"+display+"' id='TVN_"+node.id+"' cellpadding=0 cellspacing=0 border=0><tr>"
			+"<td valign=middle nowrap>"+this.DrawNodeIndentHTML(node, indent_level)+"</td>"
			+"<td style='width:100%;' valign=middle nowrap "+(this.currentNodeID==node.id?"class=TVN_TreeNode_Current":"")+"><div class='model-menu-td'>";
		if((node.isShowCheckBox==true || node.isShowCheckBox==null && this.isShowCheckBox==true) && node.value!=null)
			var ChkStr = "<input onClick='"+this.refName+".SelectNode("+node.id+")' type="+(this.isMultSel?"checkbox":"radio")
				+" id='CHK_"+node.id+"' value=\""+Com_HtmlEscape(node.value)+"\" name=List_Selected "+(node.isChecked?"Checked":"")+">";
		else
			var ChkStr = "";
		Result += "<a class='nav_tree_node_title' lks_nodeid="+node.id+" title=\""+Com_HtmlEscape(node.text)+"\" href=\"javascript:void(0)\" onClick=\""+this.refName+".ClickNode("+node.id+");\"";
		Result += " onMouseOver=\""+this.refName+".MouseOverNode("+node.id+");\"";
		Result += " onMouseOut=\""+this.refName+".MouseOutNode("+node.id+");\"";
		if(this.DblClickNode!=null)
			Result += " ondblclick=\""+this.refName+".DblClickNode("+node.id+");\"";
		if (indent_level != 0) {
			Result += " ondblclick='Modeling_NavEdit(this);'";
		}
		var innerText = Com_HtmlEscape(node.text);
		Result += ">" + ChkStr + innerText +"</a>";
		if (indent_level != 0) {
			//innerText = "<a name='tmpNodeName' style='font-weight: bold; margin-left: 4px; position: relative; display: inline;' ondblclick='Modeling_NavEdit(this);'></a>"
			var parameter = node.parameter;
			var isShow = parameter && parameter.isEdit;
			var display = isShow ? "inline-block;'" : "none;'";
			Result += "<input name='tmpNodeName' value='" + node.text + "' style='width:60%;display:" + display +  " placeholder='"+lang.enterNodeName+"' onblur='Modeling_NavNameBlur(this);' class='inputsgl'>";
		}
		
		if(indent_level != 0) {
			Result += "<div class='node_operation' style='float:right;display:none;'>";
			Result += "<div class='lui_tabpanel_container_opt_edit' title='"+lang.edit+"' onClick='Modeling_NavEdit(this)'>"+lang.edit+"</div>";
			if(node.nextSibling != null){
				Result += "<div class='lui_tabpanel_container_opt_down' title='"+lang.down+"' onClick='Modeling_NavDown(" + node.id + ")'>"+lang.down+"</div>";
			}
			if(node.prevSibling != null){
				Result += "<div class='lui_tabpanel_container_opt_up' title='"+lang.up+"' onClick='Modeling_NavUp(" + node.id + ")'>"+lang.up+"</div>";
			}
			Result += "<div class='lui_tabpanel_container_opt_delete' title='"+lang.delete+"' onClick='Modeling_NavDelete(" + node.id + ")'>"+lang.delete+"</div>";
			Result += "</div>";
		}
		Result += "</div></td>";
		Result += "</tr></table>";
		if (this.OnNodePostDraw!=null){
			var tmpStr = this.OnNodePostDraw(node,Result)
			if (typeof(tmpStr)=="string")
				Result = tmpStr;
		}
	}
	if(TREENODESTYLE.isOneoff)
		Tree_ResumeStyle();
	return Result;
}

function Modeling_Nav_DrawNodeIndentHTML(node, indent_level){
	TREENODESTYLE.imgNodeUp = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/nav/up.png";
	TREENODESTYLE.imgNodeDown = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/nav/down.png";
	var CannotExpand = (node.FetchChildrenNode==null && node.firstChild==null);
	var Result;
	if(!CannotExpand){
		if(node.isExpanded){
			Result = "<a href=\"javascript:"+this.refName+".ExpandNode("+node.id+")\" style='margin:0 5px;position: relative;top: -2px;'><img src=" + TREENODESTYLE.imgNodeUp + " border=0 style='margin:0 5px'></a>";
		}else{
			Result = "<a href=\"javascript:"+this.refName+".ExpandNode("+node.id+")\" style='margin:0 5px;position: relative;top: -2px;'><img src=" + TREENODESTYLE.imgNodeDown + " border=0 style='margin:0 5px'></a>";
		}
	}
	if(indent_level <= 0)
		return Result;
	if(CannotExpand){
		Result = "<div style='width:18px;display:inline-block;'></div>";
	}else{
		Result = "<a href=\"javascript:"+this.refName+".ExpandNode("+node.id+")\">"
		+(node.isExpanded?"<img src="+TREENODESTYLE.imgNodeUp+" border=0 style='margin:0 5px;'>":"<img src="+TREENODESTYLE.imgNodeDown+" border=0 style='margin:0 5px'>")+"</a>";
	}

	var now = node.parent;
	for(i=indent_level-1; i>0; i--){
		if(now == null)
			break;
		Result = "<div style='width:18px;display:inline-block;'></div>"+Result;
		now = now.parent;
	}
	return Result;
}

function Modeling_Nav_DrawNodeIndentHTML_LEFT(node, indent_level){
	TREENODESTYLE.imgNodeUp = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/nav/up.png";
	TREENODESTYLE.imgNodeDown = Com_Parameter.ContextPath + "sys/modeling/base/resources/images/nav/down.png";
	var CannotExpand = (node.FetchChildrenNode==null && node.firstChild==null);
	var Result;
	if(!CannotExpand){
		if(node.isExpanded){
			Result = "<a href=\"javascript:"+this.refName+".ExpandNode("+node.id+")\" style='margin:0 5px;position: relative;top: -2px;'><img src=" + TREENODESTYLE.imgNodeUp + " border=0 style='margin:0 5px'></a>";
		}else{
			Result = "<a href=\"javascript:"+this.refName+".ExpandNode("+node.id+")\" style='margin:0 5px;position: relative;top: -2px;'><img src=" + TREENODESTYLE.imgNodeDown + " border=0 style='margin:0 5px'></a>";
		}
	}
	if(indent_level <= 0)
		return Result;
	if(CannotExpand){
		Result = "<div style='width:18px;display:inline-block;'></div>";
	}else{
		Result = "<a href=\"javascript:"+this.refName+".ExpandNode("+node.id+")\">"
		+(node.isExpanded?"<img src="+TREENODESTYLE.imgNodeUp+" border=0 style='margin:0 5px;position: relative;top: -2px;'>":"<img src="+TREENODESTYLE.imgNodeDown+" border=0 style='margin:0 5px'>")+"</a>";
	}

	var now = node.parent;
	for(i=indent_level-1; i>=0; i--){
		if(now == null)
			break;
		Result = "<div style='width:18px;display:inline-block;'></div>"+Result;
		now = now.parent;
	}
	return Result;
}

function Modeling_Nav_DrawNodeInnerHTML_LEFT(node, indent_level)
{
	var Result;
	var isEnd = false;
	if (this.OnNodeQueryDraw!=null){
		Result = this.OnNodeQueryDraw(node)
		if (typeof(Result)=="string")
			isEnd = true;
	}
	if(!isEnd){
		Result = "<table id='TVN_"+node.id+"' cellpadding=0 cellspacing=0 border=0><tr>"
			+"<td valign=middle nowrap>"+this.DrawNodeIndentHTML(node, indent_level)+"</td>"
			+"<td style='width:100%;' valign=middle nowrap "+(this.currentNodeID==node.id?"class=TVN_TreeNode_Current":"")+"><div class='model-menu-td'>";
		if((node.isShowCheckBox==true || node.isShowCheckBox==null && this.isShowCheckBox==true) && node.value!=null)
			var ChkStr = "<input onClick='"+this.refName+".SelectNode("+node.id+")' type="+(this.isMultSel?"checkbox":"radio")
				+" id='CHK_"+node.id+"' value=\""+Com_HtmlEscape(node.value)+"\" name=List_Selected "+(node.isChecked?"Checked":"")+">";
		else
			var ChkStr = "";
		Result += "<a lks_nodeid="+node.id+" title=\""+Com_HtmlEscape(node.title)+"\" href=\"javascript:void(0)\" onClick=\""+this.refName+".ClickNode("+node.id+");\"";
		if(this.DblClickNode!=null)
			Result += " ondblclick=\""+this.refName+".DblClickNode("+node.id+");\"";
		if (indent_level != 0) {
			Result += " ondblclick='Modeling_NavEdit(this);'";
		}
		var innerText = Com_HtmlEscape(node.text);
		Result += ">" + ChkStr + innerText +"</a>";
		if (indent_level != 0) {
			//innerText = "<a name='tmpNodeName' style='font-weight: bold; margin-left: 4px; position: relative; display: inline;' ondblclick='Modeling_NavEdit(this);'></a>"
			var parameter = node.parameter;
			var isShow = parameter && parameter.isEdit;
			var display = isShow ? "inline-block;'" : "none;'";
			Result += "<input name='tmpNodeName' value='" + node.text + "' style='width:60%;display:" + display +  " placeholder='"+lang.enterNodeName+"' onblur='Modeling_NavNameBlur(this);' class='inputsgl'>";
		}

		Result += "</div></td>";
		Result += "</tr></table>";
		if (this.OnNodePostDraw!=null){
			var tmpStr = this.OnNodePostDraw(node,Result)
			if (typeof(tmpStr)=="string")
				Result = tmpStr;
		}
	}
	if(TREENODESTYLE.isOneoff)
		Tree_ResumeStyle();
	return Result;
}
