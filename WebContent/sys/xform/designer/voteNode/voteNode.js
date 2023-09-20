/**********************************************************
功能：投票节点控件
使用：
作者：李文昌
创建时间：2018-10-9
**********************************************************/
Designer_Config.operations['voteNode']={
		lab : "6",
		imgIndex : 68,
		title : Designer_Lang.voteNode_title,
		run : function (designer) {
			designer.toolBar.selectButton('voteNode');
		},
		type : 'cmd',
		order: 11,
		shortcut : 'S',
		select: true,
		cursorImg: 'style/cursor/voteNode.cur'
};


Designer_Config.buttons.tool.push("voteNode");
Designer_Menus.tool.menu['voteNode'] = Designer_Config.operations['voteNode'];

Designer_Config.controls.voteNode = {
		type : "voteNode",
		storeType : 'field',
		inherit : 'base',
		onDraw : _Designer_Control_VoteNode_OnDraw,
		drawXML : _Designer_Control_VoteNode_DrawXML,
		implementDetailsTable : false,
		attrs : {
			label : Designer_Config.attrs.label,
			mold_detail_name:{
				text: Designer_Lang.voteNode_chooseNode,
				value : '',
				type: 'hidden',
				//该属性的作用：校验moldDetail属性是否有值
				validator: [Designer_Control_Attr_Required_Validator],
				show: true
			},
			mold : {//类型
				text: Designer_Lang.voteNode_attr_mold,
				value : '11',
				type : 'select',
				opts: [
					{name: 'pieChart', text: Designer_Lang.voteNode_mold_pieChart, value:'11'},//饼图
					{name: 'barGraph', text: Designer_Lang.voteNode_mold_barGraph, value:'12'}//柱状图
				],
				show: true
			},
			moldDetail : {//选择节点
				text: Designer_Lang.voteNode_chooseNode,
				value : '',
				type: 'self',
				draw: _Designer_Control_VoteNode_Attr_MoldDetail_Self_Draw,
				checkout:function(msg, name, attr, value, values, control){
					if(!value||value=='~'){
						msg.push(values.label+',' + Designer_Lang.voteNode_requiredAttributeNotNull);
						return false;
					}
					return true;
				},
				show: true
			},
			/*width : {
				text: Designer_Lang.controlAttrWidth,
				value: "240",
				type: 'text',
				show: true,
				validator: Designer_Control_Attr_Width_Validator,
				checkout: Designer_Control_Attr_Width_Checkout
			},
			height: {
				text: Designer_Lang.div_height,
				value: "240",
				type: 'text',
				show: true
			},*/
			width : {
				text: Designer_Lang.controlAttrWidth,
				value: "240",
				type: 'self',
				draw: _Designer_Control_VoteNode_Width_Self_Draw,
				show: true,
				validator: Designer_Control_Attr_Int_Validator,
				checkout: Designer_Control_Attr_Int_Checkout
			},
			height : {
				text: Designer_Lang.vote_heigth,
				value: "240",
				type: 'self',
				draw: _Designer_Control_VoteNode_Height_Self_Draw,
				show: true,
				validator: Designer_Control_Attr_Int_Validator,
				checkout: Designer_Control_Attr_Int_Checkout
			}		
		},
		info : {
			name: Designer_Lang.controlVoteNodeInfoName
		},
		resizeMode : 'onlyWidth'
}

//生成投票节点Dom对象
function _Designer_Control_VoteNode_OnDraw(parentNode, childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	$(domElement).attr("id",this.options.values.id);
	$(domElement).attr("label",_Get_Designer_Control_Label(this.options.values, this));
	//背景样式
	with(domElement.style) {
		width='24px';
		display = 'inline-block';
		textAlign = "left";
	}
	var values =this.options.values;
	//设置宽度
	if (!values.width) {
		values.width = this.attrs.width.value;
	}
	//设置高度
	if (!values.height) {
		values.height = this.attrs.height.value;
	}
	if (!values.mold){
		values.mold = this.attrs.mold.value;
	}
	//背景图标
	var label = document.createElement("label");
	$(label).addClass("voteNodeControl");
	domElement.appendChild(label);
}

function _Designer_Control_VoteNode_Attr_MoldDetail_Self_Draw(name, attr, value, form, attrs, values,control){	
	var moldValue = attrs.mold.value;
	if(values.mold){
		moldValue=values.mold;
	}
	html="<div id='voteNode_moldDetail_html'>"+_Designer_Control_VoteNode_GetHTMLByHanderType(moldValue,value)+"</div>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_VoteNode_GetHTMLByHanderType(moldValue,val){
	var html=[];
	var names="";
	var labels="";
	if(val){
		names=val.split("~")[0];
		labels=val.split("~")[1];
	}
	//选择展示节点
	lableText=' ' + Designer_Lang.voteNote_chooseNode;
	html.push("<input type='text' id='detail_attr_name' name='detail_attr_name'   readonly='true'  class='inputsgl' value='"+labels+"'/>");
	html.push("<span class='txtstrong'>*</span>");
	html.push("<input type='hidden' id='detail_attr_value' name='detail_attr_value'  value='"+names+"'/>");
	html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Dialog_List_ShowVoteNode('detail_attr_value','detail_attr_name', ';','',_VoteNode_After_MouldDetailSelect_Set,false);\">"+Designer_Lang.voteNote_choose+"</a>");
		
	//设置mouldDetail Label
	if($("#voteNode_moldDetail_html")[0]){
		$("#voteNode_moldDetail_html").parent().prev().text(lableText);
	}
	html=html.join('');
	return html;
}

//获取投票节点
function Dialog_List_ShowVoteNode(idField, nameField, splitStr, isMulField,action,isContainDraftNode){
	var dialog = new KMSSDialog(true, true);
	dialog.BindingField(idField, nameField, splitStr, isMulField);
	
	dialog.SetAfterShow(action);
	
	//获取流程中所有节点
	var wfNodes=(window.XForm_GetWfAuditNodes == null)?[]:XForm_GetWfAuditNodes();
	var data=new KMSSData();
	var ary = new Array();
	for(var i=0;i<wfNodes.length;i++){
		//只有投票可供选择(新增起草节点)
		if('voteNode'==wfNodes[i].type){
			//设置选中节点
			var temp = new Array();
			temp["id"]= wfNodes[i].value;
			temp["name"]= wfNodes[i].name;
			ary.push(temp);
					
		}
	}
	
	data.AddHashMapArray(ary);
	dialog.optionData.AddKMSSData(data);
	dialog.Show(window.screen.width*520/1366,window.screen.height*400/768);
}

function _VoteNode_After_MouldDetailSelect_Set(rtnvalue){
	if(!rtnvalue){
		return;
	}
	var control=Designer.instance.attrPanel.panel.control;
	var values=control.options.values;
	
	var names=[];
	var labels=[];
	
	for(var i=0;i<rtnvalue.data.length;i++){
		names.push(rtnvalue.data[i].id);
		labels.push(rtnvalue.data[i].name);
	}
	if(names.length>0){
		values.moldDetail=names.join(";")+"~"+labels.join(";");
	}
	else{
		values.moldDetail="";
	}
	document.getElementsByName("mold_detail_name")[0].value=values.moldDetail;
}

function _Designer_Control_VoteNode_Width_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = "<input type='text' style='width:79%' class='attr_td_text' name='" + name;
	value = typeof(value) == 'undefined' ? Designer_Config.controls.voteNode.attrs.width.value : value;
	html += "' value='" + value;	
	html += "' onKeyUp='xform_voteNode_autoResizeWrap(1,this.value)";
	html += "'>";
	var locked = "<img src='style/img/lock.png' name='locked' title='" + Designer_Lang.voteNode_locked +"' style='cursor:pointer;width:16px;height:16px' checked='true' onclick='xform_voteNode_changeLocked(this)'/>";
	html += locked;
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_VoteNode_Height_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = "<input type='text' style='width:79%' class='attr_td_text' name='" + name;
	value = typeof(value) == 'undefined' ? Designer_Config.controls.voteNode.attrs.height.value : value;
	html += "' value='" + value;	
	html += "' onKeyUp='xform_voteNode_autoResizeWrap(2,this.value)";
	html += "'>";
	html += "<br/>";
	html += "<span>" + Designer_Lang.voteNode_heigth_isAuto + "</span>"
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//等比压缩标志更改
function xform_voteNode_changeLocked(object){
	var type = object.getAttribute("checked");
	if(type == 'true'){
		object.setAttribute("checked","false");
		object.setAttribute("src","style/img/lock-open.png");
	}else if(type == 'false'){
		object.setAttribute("checked","true");
		object.setAttribute("src","style/img/lock.png");
	}
}

//等比压缩外层
function xform_voteNode_autoResizeWrap(type,value){
	xform_voteNode_autoResize(type,value,
			Designer_Config.controls.voteNode.attrs.width.value,
			Designer_Config.controls.voteNode.attrs.height.value);
}

//等比压缩
function xform_voteNode_autoResize(type,value,origWidth,origHeight){	
	//1为宽度，2为高度
	var checked = $("img[name='locked']")[0].getAttribute("checked");//是否设置为等比压缩
	if(origWidth && origWidth != null && origHeight && origHeight != null){
		if(checked == 'true'){
			if(type == '1'){	
				$("input[name='height']").val(value);
			}
			if(type == '2'){
				$("input[name='width']").val(value);
			}
		}
	}
}

// 生成XML
function _Designer_Control_VoteNode_DrawXML() {
	var values = this.options.values;
	var content = {mold:values.mold};
	content = JSON.stringify(content);
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="String" ');
	buf.push('store="false" ');
	buf.push('customElementProperties="', Designer.HtmlEscape(content), '" ');
	buf.push('businessType="', this.type, '" ');
	buf.push('/>');
	return buf.join('');
}


