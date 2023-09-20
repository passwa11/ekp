//只有在后台开启了云平台才会出现携程控件
var _ctripIsVisibel = Designer_Control_Ctrip_IsVisibel();
function Designer_Control_Ctrip_IsVisibel(){
	var flag = false;
	var url = Com_Parameter.ContextPath + 'third/ctrip/ctripCommon.do?method=isVisibel';
	$.ajax({ url: url, async: false, dataType: "json", cache: false, success: function(rtn){
		if("1"==rtn.status)
			flag = true;
	}});
	return flag;
}

function Designer_Control_Ctrip_SetAttr(domElement, targetValue, attr){
	if(targetValue==null || targetValue=='') {
		domElement.setAttribute(attr,'');
	} else {
		$(domElement).attr(attr,targetValue);
	}
}

//普通属性
function Designer_Control_Ctrip_Attr_Draw(name, attr, value, form, attrs, values, control){
	var buff = [];
	buff.push('<div id="control_attr_'+name+'" style="display:inline;" >' );
	buff.push('<input type="text" name="' + name + '" style="display:none" ');
	if (values[name] != null) {
		buff.push(' value="' + values[name] + '"');
	}
	buff.push('>');
	buff.push('<input type="text" name="ctrip_attr' + name + '" style="width:78%" class="inputread" readOnly fd_name='+name+' ');
	if (values["ctrip_attr"+name] != null) {
		buff.push(' value="' + values["ctrip_attr"+name] + '"');
	}
	buff.push('>');	
	//弹框根据类型显示相应的控件
	var showType = attr.showControlType?attr.showControlType:'';
	buff.push('<input type=button onclick="Designer_Control_Ctrip_All_Attr_Dialog(this,\''+showType+'\');" class="btnopt" value="..."></nobr>');
	buff.push('</div>');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}

//特殊属性 节点选择
function Designer_Control_Ctrip_SelectNode(name, attr, value, form, attrs, values, control){
	var html = [];
	html.push("<input type='text' name='"+name+"' readonly='true' style='width:74%;' class='inputsgl' value='"+(control.options.values[name]||"")+"'/>");
	html.push("<input type='hidden' name='bookNodeValue' value='"+(control.options.values['bookNodeValue']||"")+"'/>");
	html.push("<a href='#' id='selectNode' onclick=\"Designer_Control_Ctrip_SelectNode_Dialog('bookNodeValue','"+name+"', ';','',true);\">选择</a>");
	html.push("</br><span style='color:#9e9e9e;word-break: break-word;'>"+ Designer_Lang.bookTicket_allPeopleCanBookWhenNull +"</span>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(''));
}

function Designer_Control_Ctrip_SelectNode_Dialog(idField, nameField, splitStr, isMulField, isMul, action){
	var dialog = new KMSSDialog(isMul, true);
	dialog.BindingField(idField, nameField, splitStr, isMulField);	
	dialog.SetAfterShow(action);	
	//获取流程中所有节点
	var wfNodes=(window.XForm_GetWfAuditNodes == null)?[]:XForm_GetWfAuditNodes();
	var data=new KMSSData();
	var ary = new Array();
	for(var i=0;i<wfNodes.length;i++){
		//只有审批节点和签字节点可供选择
		if('signNode'==wfNodes[i].type || 'reviewNode'==wfNodes[i].type){
			//设置选中节点
			var temp = new Array();
			temp["id"]= wfNodes[i].value;
			temp["name"]=wfNodes[i].value + " " + wfNodes[i].name;
			ary.push(temp);					
		}
	}	
	data.AddHashMapArray(ary);
	dialog.optionData.AddKMSSData(data);
	dialog.Show(520, 400);
}

//列举出该表单所有字段
function Designer_Control_Ctrip_All_Attr_Dialog(dom,showType){
	var dialog = new KMSSDialog();
	showType = showType == null ? '' : showType;
	dialog.varInfo = {obj:Designer.instance.getObj(),type:showType};	
	dialog.previousSibling = dom.previousSibling;//输入框
	dialog.previousSiblingHidden = dom.previousSibling.previousSibling;
	dialog.SetAfterShow(function(){Designer_AttrPanel.showButtons(dom)});
	dialog.URL = Com_Parameter.ContextPath + "third/ctrip/xform/resource/jsp/thirdCtripXformTemplateAllAttr_edit.jsp";
	dialog.Show(400, 480);
}

function Designer_Control_Ctrip_GetTicketJson(fields,values){
	var rs = {};
	for(var i in fields){
		if(values[fields[i]]){
			rs[fields[i]] = values[fields[i]];
		}
	}
	return rs;
}

//提交校验所选节点是否存在于当前流程中
function Designer_Control_Ctrip_SelectNode_Checkout(msg, name, attr, value, values, control){
	//流程节点
	var bookNodeValueHidden = values.bookNodeValue;
	if(bookNodeValueHidden && bookNodeValueHidden != '' && bookNodeValueHidden != 'undefined'){
		var bookNodeValueArray = bookNodeValueHidden.split(";");
		for(var i = 0; i < bookNodeValueArray.length; i++){
			//获取流程中所有节点
			var wfNodes=(window.XForm_GetWfAuditNodes == null)?[]:XForm_GetWfAuditNodes();
			//遍历目前的所有流程节点，查找是否有和目前已选的节点一致的，没有则提示
			for(var j=0;j<wfNodes.length;j++){
				//只有审批节点和签字节点可供选择
				if('signNode'==wfNodes[j].type || 'reviewNode'==wfNodes[j].type){
					if(wfNodes[j].value == bookNodeValueArray[i]){
						break;
					}else if(j == wfNodes.length){
						msg.push(control.info.name, ':', attr.text, Designer_Lang.bookTicket_notInCurrentNode);
						return false;
					}
				}
			}		
		}	
	}	
	return true;
}