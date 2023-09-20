/**********************************************************
功能：构造id为流程节点的Div
使用：
	
作者：朱国荣
创建时间：2016-7-29
**********************************************************/
Designer_Config.controls['approvalnode'] = {
		type : "approvalnode",
		storeType : 'none',
		inherit    : 'base',
		onDraw : _Designer_Control_Approvalnode_OnDraw,
		onDrawEnd : _Designer_Control_Approvalnode_OnDrawEnd,
		drawXML : _Designer_Control_Approvalnode_DrawXML,
		destroy : _Designer_Control_Approvalnode_Destroy,
		_destroy : Designer_Control_Destroy,
		implementDetailsTable : true,
		info : {
			name: Designer_Lang.paperSignature
		},
		resizeMode : 'all',
		attrs : {
			mouldDetail : {
				text: Designer_Lang.paperSignature_flowNode,
				value: "",
				type: 'self',
				draw: _Designer_Control_Attr_MouldDetail_Self_Draw,
				show: true,
				validator: Designer_Control_Attr_Required_Validator,
				checkout: Designer_Control_Attr_Required_Checkout
			}
		}
};

Designer_Config.operations['approvalnode'] = {
		lab : "2",
		imgIndex : 46,
		order : 10,
		title : Designer_Lang.paperSignature,
		run : function (designer) {
			designer.toolBar.selectButton('approvalnode');
		},
		type : 'cmd',
		select: true,
		cursorImg: 'style/cursor/approvalNode.cur'
	};

Designer_Config.buttons.tool.push("approvalnode");

//Designer_Menus.tool.menu['approvalnode'] = Designer_Config.operations['approvalnode'];

function _Designer_Control_Approvalnode_OnDraw(parentNode,childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	domElement.id = this.options.values.id;		
	domElement.style.width='25px';
	domElement.style.height='25px';
	var values =this.options.values;
	$(domElement).attr("mouldDetail",values.mouldDetail);
	var img = document.createElement('img');
	img.setAttribute("src","style/img/approvalDiv.png");
	domElement.appendChild(img);
}

function _Designer_Control_Attr_MouldDetail_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = [];
	var names="";
	var labels="";
	if(value){
		mames=value.split("~")[0];
		labels=value.split("~")[1];
	}
	html.push("<input type='text' id='detail_attr_name' name='detail_attr_name'   readonly='true'  class='inputsgl'  value='"+labels+"'/>");
	html.push("<span class='txtstrong'>*</span>");
	html.push("<input type='hidden' id='detail_attr_value' name='detail_attr_value'  value='"+names+"'/>");
	html.push("<a href='javascript:void(0)' id='handlerSelect' onclick=\"Dialog_List_ShowNode_Paper('detail_attr_value','detail_attr_name', ';',false,After_MouldDetailSelect_Set);\">"+Designer_Lang.attrpanelSelect+"</a>");
	return Designer_AttrPanel.wrapTitle(name, attr, value, html.join(''));
}

function Dialog_List_ShowNode_Paper(idField, nameField, splitStr, isMulField,action){
	var dialog = new KMSSDialog(false, true);
	dialog.BindingField(idField, nameField, splitStr, isMulField);
	
	dialog.SetAfterShow(action);
	
	//获取流程中所有节点
	var key = window.PrefixKey;	
	var modelName = window.SignatureModelName;
	var typeVe = top.window.$("input[name='sysWfTemplateForms."+key+".fdType']:checked").val();
	var wfNodes=LBPM_Template_getNodes_Signature(typeVe, key, modelName);
	var data=new KMSSData();
	var ary = new Array();
	if (wfNodes) {
		for(var i=0;i<wfNodes.length;i++){
			//只有审批节点和签字节点可供选择
			if('signNode'==wfNodes[i].type || 'reviewNode'==wfNodes[i].type || 'sendNode'==wfNodes[i].type){
				//设置选中节点
				var temp = new Array();
				temp["id"]= wfNodes[i].value;
				temp["name"]=wfNodes[i].name;
				ary.push(temp);					
			}
		}	
	}
	data.AddHashMapArray(ary);
	dialog.optionData.AddKMSSData(data);
	dialog.Show(window.screen.width*520/1366,window.screen.height*400/768);
}

function LBPM_Template_getNodes_Signature(typeVe, key, modelName) {
	var SignatureProjectPath = window.SignatureProjectPath;
	if(typeVe == "3") {// 自定义
		var FlowChartObject = top.window.document.getElementById("sysWfTemplateForms."+key+".WF_IFrame").contentWindow.FlowChartObject;
		return LBPM_Template_getNodes_Paper(FlowChartObject.BuildFlowData());
	} else if (typeVe == "2") {// 其它
		var commonId = top.window.$("input[name='sysWfTemplateForms."+key+".fdCommonId']").val();
		if (commonId == '') {
			return [];
		}
		var rtn = [];
		var data = new KMSSData();
		var url = SignatureProjectPath+'sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=findNodes&tempId=';
		data.SendToUrl(url + commonId, function(rq){
			var xml = rq.responseText;
			if (xml.indexOf('<error>') > -1) {
				alert(xml);
				rtn = [];
			} else {
				rtn = LBPM_Template_getNodes_Paper(top.window.WorkFlow_LoadXMLData(xml));
			}
		}, false);
		return rtn;
	} else if (typeVe == "1") {// 取默认
		var rtn = [];
		var data = new KMSSData();
		var url = SignatureProjectPath+'sys/lbpmservice/support/lbpm_template/lbpmTemplate.do?method=findNodes&modelName='+modelName+'&key='+key;
		data.SendToUrl(url, function(rq){
			var xml = rq.responseText;
			if (xml.indexOf('<error>') > -1) {
				alert(xml);
				rtn = [];
			} else {
				rtn = LBPM_Template_getNodes_Paper(top.window.WorkFlow_LoadXMLData(xml));
			}
		}, false);
		return rtn;
	}
}

function LBPM_Template_getNodes_Paper(processData) {
	var nodes = [];
	
	if (null == processData || "undefined" == processData) {
		return nodes;
	}
	//当引用默认流程模板，但是实际系统并没有默认流程模板的时候，此时processData是没有nodes的
	if(processData.nodes){
		for(var i=0; i<processData.nodes.length; i++) {
			var n = processData.nodes[i];
			if(n.variants){
				for(var j=0;j<n.variants.length;j++){
					if(n.variants[j].name=="signaturehtml"){
						nodes.push({value:n.id,name:n.name,type:n.XMLNODENAME});
					}
				}
			}
		}
	}
	return nodes;
}

function _Designer_Control_Approvalnode_OnDrawEnd(){
	
}

function _Designer_Control_Approvalnode_DrawXML(){
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('node="',values.mouldDetail,'" ');
	buf.push('type= "String" ');
	buf.push('/>');
	return buf.join('');
}

function _Designer_Control_Approvalnode_Destroy(){
	var domElement = this.options.domElement;
	var div = document.getElementById(domElement.id);
	if (div != null) {
		div.parentNode.removeChild(div);
	}
	this._destroy();
}