/**
 * 关联文档对象 
 */

// Xform_ObjectInfo 在sysForm_main.jsp里面定义
if(typeof Xform_ObjectInfo.Xform_Controls.relevanceObj == "undefined")
	Xform_ObjectInfo.Xform_Controls.relevanceObj = new Array();


/**
 * 删除关联文档对象
 * @param controlId
 * @returns
 */
function Relevance_DeleteObj(xformflag){
	if(xformflag && xformflag != ''){
		for(var i = 0;i < Xform_ObjectInfo.Xform_Controls.relevanceObj.length;i++){
			var relObj = Xform_ObjectInfo.Xform_Controls.relevanceObj[i];
			if(relObj.$xformflag[0] == xformflag){
				Xform_ObjectInfo.Xform_Controls.relevanceObj.splice(i, 1);
			}
		}
	}
}


function RelevanceDocObj(config){
	
	this.controlId = '';
	
	// 已经解析过的控件id，即去除明细表id和索引的控件id
	this.parsedControlId = '';
	
	this.modelRange = '';
	
	// 保存值的隐藏input
	this.domValNode;
	
	//属性node
	this.attrNode;
	
	this.reminder = null;
	
	this.required = false;
	
	this.isMul = true;
	
	this.btnName = Data_GetResourceString('sys-xform:sysFormMain.relevance.selectRelevanceDoc');
	if(Com_Parameter.dingXForm){
		this.btnName = Data_GetResourceString('sys-xform:sysFormMain.relevance.relevanceDoc');
	}	
	this.btnDelete = Data_GetResourceString('sys-xform:sysFormMain.relevance.btnDelete');
	
	this.inputParams = null;
	
	this.outputParams = null;
	
	this.$xformflag;
	
	this.inDetails = false;
	
	// fdId:明细表id trId:行id
	this.detailsInfo = {};
	
	// 展示信息的ul
	this.$displayUl;
	
	this.showStatus = 'view';
	
	this.value = [];
	
	this._init = RelevanceDoc_Init;
	this.buildDetailsInfo = RelevanceDoc_BuildDetailsInfo;
	this.display = RelevanceDoc_Display;
	this.openDialog = RelevanceDoc_OpenDialog;
	this.updateDataList = RelevanceDoc_UpdateDataList;
	this.showWarnTip = RelevanceDoc_ShowWarnTip;
	this.clear = RelevanceDoc_Clear;
	this.setRequire = RelevanceDoc_SetRequire;
	this.setOutParams = RelevanceDoc_SetOutputParams;
	this.checkIsChange = RelevanceDoc_CheckIsChange;
	
	this._init(config);
}

function RelevanceDoc_Init(config){
	var self = this;
	self.controlId = config.controlId || '';
	self.showStatus = config.showStatus || '';
	self.showStatus = (self.showStatus == 'add' || self.showStatus == 'edit') ? 'edit':'view'; 
	self.domValNode = config.domValNode || null;
	self.$xformflag = config.$xformflag || null;
	self.attrNode = config.attrNode || null;
	if(self.attrNode!=null){
		var required = self.attrNode.getAttribute("required")=="true"?true:false;
		self.required = required;
		var isMul = $(self.attrNode).attr("isMul");
		if(isMul){
			self.isMul = isMul=="true"?true:false;
		}
		var btnName = $(self.attrNode).attr("btnName")?$(self.attrNode).attr("btnName"):$(self.domValNode).attr("btnName");
		if(btnName){
			self.btnName = btnName;
		}
		var inputParams = $(self.attrNode).attr("inputParams");
		if(inputParams){
			self.inputParams = inputParams;
		}
		var outputParams = $(self.attrNode).attr("outputParams");
		if(outputParams){
			self.outputParams = outputParams;
		}
	}
	
	var controlId = self.controlId;
	if(controlId != ''){
		if(/\w+\.\d+\./g.test(controlId)){
			self.parsedControlId = controlId.replace(/\w+\.\d+\./g,'');
			self.inDetails = true;
			self.buildDetailsInfo();
		}else{
			self.parsedControlId = controlId;
		}
	}
	
	if(self.domValNode && self.domValNode.value != ''){
		self.value = RelevanceDoc_Commom_ToJson(self.domValNode.value);
	}
    RelevanceObj_AddOrUpdate(self);
}

function RelevanceObj_AddOrUpdate(src) {
    var relevanceObjs = Xform_ObjectInfo.Xform_Controls.relevanceObj;
    var isUpate = false;
    for (var i = 0; i < relevanceObjs.length; i++) {
        var relevanceObj = relevanceObjs[i];
        if (relevanceObj.controlId === src.controlId) {
            isUpate = true;
            relevanceObjs[i] = src;
            break;
        }
    }
    if (!isUpate) {
        relevanceObjs.push(src);
    }
}

// 支持系统底层框架
function RelevanceDoc_SetRequire(isRequire){
	var self = this;
	// 只有编辑状态才能控制必填非必填
	if(self.showStatus != 'edit'){
		return;
	}
	self.required = isRequire;
	if(isRequire){
		if(!$(self.attrNode).attr(KMSSValidation.ValidateConfig.attribute)){
			//设置序列化属性，不设置的话有多个控件时会导致提示语都出现在第一个的位置上	
			self.attrNode.setAttribute(KMSSValidation.ValidateConfig.attribute,
					KMSSValidation.ValidateConfig.prefix + (KMSSValidation.ValidateConfig.index++));
		}
		var message = Data_GetResourceString('sys-xform:sysFormMain.relevance.notNull');
		//设置提示语
		var subject = $(self.attrNode).attr("subject")?$(self.attrNode).attr("subject"):$(self.domValNode).attr("subject");
		self.reminder = new Reminder(self.attrNode,"<span class='validation-advice-title'>"+subject+"</span> "+message);
		//为隐藏域设置vilidate属性(隐藏域即使有validate属性也不会校验，此处为业务模块暂存时，不做必填校验做准备)
		$(self.domValNode).attr("validate","required");
		// 查找有没有必填的*，没则补
		var $span = self.$xformflag.find("span.txtstrong");
		if($span.length == 0){
			$(self.domValNode).after("<span class=txtstrong>*</span>");
		}
	}else{
		if(window.Reminder){
			new Reminder($(self.attrNode)).hide();
		}
		//$(self.attrNode).removeAttr(KMSSValidation.ValidateConfig.attribute);
		self.reminder = null;
		var validate = $(self.domValNode).attr('validate');
		if(validate){
			$(self.domValNode).attr('validate', validate.replace(/(\s*required\s*)/, ''));	
		}
		var $span = self.$xformflag.find("span.txtstrong");
		if($span.length > 0){
			$span.remove();
		}
	}
}

// 暂无用
function RelevanceDoc_BuildDetailsInfo(){
	var self = this;
	// 设置行id
	var $tr = self.$xformflag.closest("tr[kmss_iscontentrow='1']");
	var arr = self.controlId.match(/\w+/g);
	if(arr && arr.length > 1){
		self.detailsInfo.fdId = arr[0];
		// 查找行id
		var trId = arr[0] + "." + arr[1] + ".fdId";
		self.detailsInfo.trId = $tr.find("input[name*='"+ trId +"']").val();
	}
}

function RelevanceDoc_Display(){
	var self = this;
	var isEdit = false;
	// 构建列表数据Dom
	var $displayDiv = $('<div>');
	self.$displayUl = $('<ul>');
	self.$displayUl.addClass('relevance_main_showUl');
	$displayDiv.append(self.$displayUl);
	self.$xformflag.append($displayDiv);
	// 构建按钮
	if(self.showStatus == 'edit'){
			// 起草人,审批人有编辑权限的也可以添加关联文档
			var $divBtn = $('<div>');
			self.$xformflag.prepend($divBtn);
			$divBtn.addClass("lui_linkDoc_btn");
			var url= location.href;
			var method = Com_GetUrlParameter(url,"method");
			if (method != "preview") {
				$divBtn.on('click',function(){self.openDialog();});
			}
			$divBtn.append("<i></i>"+self.btnName);
			self.setRequire(self.required);
			isEdit = true;
	}
	self.updateDataList(isEdit,true);
}

// 打开弹窗
function RelevanceDoc_OpenDialog(){
	var self = this;
	var inputParams = null;
	if(self.inputParams){
		var inputParamsMapping = JSON.parse(self.inputParams.replace(/quot;/g,"\""));
		var inputParamsValueMap = {};
		for(var fid in inputParamsMapping){
			var param = inputParamsMapping[fid];
			//当前表单字段
			var id = param.fieldId;
			//入参字段
			var idform = param.fieldIdForm;
			if(!id || !idform){
				continue;
			}
			inputParamsValueMap[fid] = RelevanceDoc_GetFormFiledValue(id);
		}
		inputParams = JSON.stringify(inputParamsValueMap).replace(/"/g,"quot;")
	}
	seajs.use(['lui/dialog'], function(dialog) {
		/*跳转的时候就对数据字典进行处理，以字符串的形式存放在弹出框里面，以免每次查询的时候需要查数据字典*/
		var url = '/sys/xform/controls/relevance.do?method=relevanceDialog&mainModelName='+ Xform_ObjectInfo.mainModelName + '&fdControlId='+ self.parsedControlId + '&inputParams='+ encodeURIComponent(inputParams) + '&isMul='+ self.isMul;
		var height = document.documentElement.clientHeight * 0.78;
		var width = document.documentElement.clientWidth * 0.7;
		self.$dialog = dialog.iframe(url,self.btnName,null,{width:width,height : height,params:{relevanceObj:self,dialogInfo:{value:self.value}}});
//		self.$dialog.parentRelevanceObj = self;
//		self.$dialog.dialogInfo = {};
//		self.$dialog.dialogInfo.value = self.value;
	});
}

function RelevanceDoc_GetFormFiledValue(filedId){
	var filedsInfo = RelevanceDoc_GetXFormFieldById(filedId);
	//文本框根据同一个名字获取多个对象后，取同名称最匹配的对象。
	if(filedsInfo&&filedsInfo.length>1){
		for(var j=0;j<filedsInfo.length;j++){
			if(filedsInfo[j].type=='text' && filedsInfo[j].name==filedId){
				var temp=filedsInfo[j];
				filedsInfo=[];
				filedsInfo.push(temp);
				break;
			}
		}
	}
	var valueInfo=[];
	for(var j=0;j<filedsInfo.length;j++){
		if (filedsInfo[j].type == 'radio' || filedsInfo[j].type == 'checkbox' || filedsInfo[j].type == 'select' || filedsInfo[j].type == 'select-one') {
			if(filedsInfo[j].name == "_"+filedId) {
				// 排除标签的特殊处理
				continue;
			}
		}
		if (filedsInfo[j].type == 'radio' || filedsInfo[j].type == 'checkbox') {
			if (filedsInfo[j].checked) valueInfo.push(filedsInfo[j].value);
			continue;
		}
		valueInfo.push(filedsInfo[j].value);
	}
	
	if(!valueInfo || valueInfo.length==0){
		valueInfo = "null";
	}
	return valueInfo;
}

function RelevanceDoc_GetXFormFieldById(id) {
	id = "extendDataFormInfo.value(" + id + ")";
	var forms = document.forms;
	obj = [];
	for (var i = 0, l = forms.length; i < l; i ++) {
		var elems = forms[i].elements;
		for (var j = 0, m = elems.length; j < m; j ++) {
			if (elems[j].name != null && elems[j].name == id) {
				obj.push(elems[j]);
			}
		}
	}
	return obj;
}

// 刷新数据列表
function RelevanceDoc_UpdateDataList(isEdit,isInit){
	var self = this;
	if(isEdit == null){
		isEdit = false;
	}
	if(self.reminder){
		self.reminder.hide();
	}
	var temp = "<table>";
	for(var i = 0; i < self.value.length;i++){
		var item = self.value[i];
		var html = [];
		html.push("<tr>");
		var url = Com_Parameter.ContextPath + 'sys/xform/controls/relevance.do?method=viewDoc&fdId='+ Xform_ObjectInfo.mainFormId +'&fdControlId='+self.controlId
					+ '&fdDocId='+ item.docId + '&fdModelName=' + item.fdModelName + '&fdMainModelName=' + Xform_ObjectInfo.mainModelName;
		html.push("<td><a class='relevance_main_url' title='"+Relevance_StringEscape(item.subject)+"' href='" + url + "' target='_blank' ondblclick='return false;'>"
					+ Relevance_StringEscape(item.subject)+"</a></td>");
		if(isEdit){
			//增加删除按钮
			html.push("<td><a class='relevance_main_selectedbox_del' data-fdControlId='"+(self.controlId.replace(/\./g,'_'))+"' data-docId='"+item.docId+"' onclick='Relevance_DeleteDoc(this);'>"+self.btnDelete+"</a></td>");
		}
		//根据创建者是否为当前用户来决定权限扩散能否被勾选
		// 由于需求原因，“是否开启权限”这个功能去掉，故下面label中的内容隐藏掉
		html.push("<td>&nbsp;&nbsp;<label class='relvance_main_rightSpan'><input type='checkbox' name='openRight' data-fdControlId='"+self.controlId+"' data-docId='"+item.docId+"' ");
		if(item.isCreator && item.isCreator == 'false'){
			html.push("disabled='disabled'");
		}else{
			html.push(" checked='checked' ");
		}
		
		var message = "开启权限";
		html.push(" >"+message+"</input></label></td>");
		html.push("</tr>");
		temp += html.join('');
	}
	temp += "</table>";
	self.$displayUl.html(temp);
	if(isEdit && !isInit && self.value && self.outputParams){
		self.setOutParams();
	}
	if(isEdit){
		self.checkIsChange();
	}
}

function RelevanceDoc_CheckIsChange(){
	var curValue = this.value.length==0?"":JSON.stringify(this.value);
	if(this.domValNode.value!=curValue){
		this.domValNode.value = curValue;
		__xformDispatch(curValue,this.domValNode);
	}
}

function RelevanceDoc_SetOutputParams(){
	var self = this;
	var fdModelIds = [];
	var fdModelNames = [];
	for(var i = 0; i < self.value.length;i++){
		var item = self.value[i];
		fdModelIds.push(item.docId);
		fdModelNames.push(item.fdModelName);
	}
	var outputParamsMapping = JSON.parse(self.outputParams.replace(/quot;/g,"\""));
	for(var fid in outputParamsMapping){
		var param = outputParamsMapping[fid];
		//要传到的表单字段
		var fieldIdForm = param.fieldIdForm;
		//传出的模板字段
		var fieldId = param.fieldId;
		if(!fieldIdForm || !fieldId){
			continue;
		}
		var fieldDictType = param.fieldDictType;
		var fieldDataTypeForm = param.fieldDataTypeForm;
		var msg = '';
		if(self.value.length>0){
			var info = [];
			$.ajax({
				  url: Com_Parameter.ContextPath + "sys/xform/controls/relevance.do?method=getOutInfo&fieldId="+fieldId+"&fieldDictType="+fieldDictType+"&fieldDataTypeForm="+fieldDataTypeForm+"&fdModelIds="+fdModelIds.join(";")+"&fdModelNames="+fdModelNames.join(";"),
				  type:'GET',
				  async:false,//同步请求
				  success: function(json){
					  info=json;
				  },
				  dataType: 'json'
			});
			for(var i=0;i<info.length-1;i++){
				msg+=info[i].outValue+';';
			}
			msg += info[info.length-1].outValue;
		}
		var filedsInfo = GetXFormFieldById(fieldIdForm, true);
		for(var i = 0;i<filedsInfo.length;i++){
			if(filedsInfo[i].type == 'checkbox'){
				filedsInfo[i].checked = false;
			}
		}
		SetXFormFieldValueById(fieldIdForm, msg);
		for(var i = 0;i<filedsInfo.length;i++){
			// 手动触发change
			// blur 用于jsp片段监控的；change 用于普通input的
			$(filedsInfo[i]).trigger($.Event("blur"));
			$(filedsInfo[i]).trigger($.Event("change"));
		}
	}
}

function RelevanceDoc_ShowWarnTip(){
	var self = this;
	var message = Data_GetResourceString('sys-xform:sysFormMain.relevance.onlyEditBycreator');
	var html = [];
	html.push("<div>");
	html.push("<span style='color:red;'>"+message+"</span>");
	html.push("</div>");
	self.$xformflag.append(html.join(''));
}

// 清空全部数据
function RelevanceDoc_Clear(){
	
}

function RelevanceDoc_Commom_ToJson(str){
	var json = {};
	try{
		json = JSON.parse(str);
	}catch(e){
		console.log("关联文档控件json转换失败！>> " + str);
	}
	return json;
}

// 根据控件id找到对应的关联文档对象
function Relevance_FindObjByControlId(id){
	var controls = Xform_ObjectInfo.Xform_Controls.relevanceObj;
	for(var i = 0;i < controls.length;i++){
		if(controls[i].controlId.replace(/\./g,'_') == id){
			return controls[i];
		}
	}
}

//删除文档
function Relevance_DeleteDoc(dom){
	if(dom){			
		//查找到对应关联控件的数据
		var releObj = Relevance_FindObjByControlId($(dom).attr('data-fdControlId'));
		var controlData = releObj.value; 
		//遍历数据，如果存在对应的id，则删除
		for(var i = 0;i < controlData.length;i ++){
			if(controlData[i].docId == $(dom).attr('data-docId')){
				$(dom).closest('tr').remove();
				controlData.splice(i, 1);
			}
		}
		releObj.checkIsChange();
		if(releObj.value && releObj.outputParams){
			releObj.setOutParams();
		}
	}
}

//防止html注入
function Relevance_StringEscape(str){
	 var s = "";
	    if (str.length == 0) return "";
	    for (var i = 0; i < str.length; i++) {
	        switch (str.substr(i, 1)) {
	            case "<": s += "&lt;"; break;
	            case ">": s += "&gt;"; break;
	            case "&": s += "&amp;"; break;
	            case " ": s += "&nbsp;"; break;
	            case "\"": s += "&quot;"; break;
	            case "\'": s += "&apos;"; break;
	            default: s += str.substr(i, 1); break;
	        }
	    }
	    return s;
}