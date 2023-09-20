/**
 * 
 */

/**
 * 隐藏元素
 * @param elems
 * @returns
 */
function _Designer_Control_hiddenChildElems(context){
	var elems = _Designer_Control_GetVisibleEle(context);
	for (var i = 0; i < elems.length; i++) {
		$(elems[i]).attr("__hiddenInMobile","true");
		$(elems[i]).hide();
	}
}

var PLEASE_SELECT = "请选择";

var PLEASE_INPUT = "请输入";
/**
 * 获取可视子元素
 * @param domElement
 * @returns
 */
function _Designer_Control_GetVisibleEle(domElement){
	var $childrens = $(domElement).children();
	var visibleChilds = [];
	$childrens.each(function(index,child){
		$(child).is(":visible");
		visibleChilds.push(child);
	});
	return visibleChilds;
}

function _Designer_Control_ModifyWidth(domElement){
	if (domElement) {
		domElement.style.width = "";
		domElement.style.minWidth = "";	
		if ($(domElement).parents(".detailTableNormalTd").length > 0) {
			domElement.style.height = "";
			domElement.style.minHeight = "";
		}
	}
}

function _Designer_Control_DrawRequired(control,context) {
	if (control.options.values.required == "true") {
		$(context).append('<div class="muiFormRequired">*</div>');
	}
}

//生成单行文本移动端dom对象
function _Designer_Control_InputText_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	var style = _Designer_Control_Style(this.options.values);
	if(this.options.values.defaultValue){
		$(domElement).append("<div class='muiSelInput'><span style='" + style + "'> " + this.options.values.defaultValue + "</span></div>");
		$(domElement).append("<div class='muiSelInputRight'><span class='mui mui mui-insert mui-rotate-45'></span></div>");
	}else{
		$(domElement).append("<div class='muiSelInput muiSelInputSize' style='" + style + "' placeholder='" + PLEASE_INPUT + "'/>");
		$(domElement).append("<div class='muiSelInputRight'><span class='mui mui mui-insert mui-rotate-45'></span></div>");
	}
	return domElement;
}

//生成多行文本移动端dom对象
function _Designer_Control_Textarea_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	var style = _Designer_Control_Style(this.options.values);
	if(this.options.values.defaultValue){
		$(domElement).append("<div class='muiSelInput'><span style='" + style + "'> " + this.options.values.defaultValue + "</span></div>");
		$(domElement).append("<div class='muiSelInputRight'><span class='mui mui mui-insert mui-rotate-45'></span></div>");
	}else {
		$(domElement).append("<div class='muiSelInput muiSelInputSize' style='" + style + "' placeholder='" + PLEASE_INPUT + "'/>")
		$(domElement).append("<div class='muiSelInputRight'><span class='mui mui mui-insert mui-rotate-45'></span></div>");
	}
	return domElement;
}



/**
 * 生成日期控件移动端dom对象
 * @returns
 */
function _Designer_Control_Datetime_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	var $input = $(this.options.domElement).find("input");
	$input.each(function(index,obj){
		obj.style.width = "";
	});
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).append(_Designer_Control_GetSelectHtml());
	$(domElement).append("<div class='muiSelInput muiSelInputSize' placeholder='" + PLEASE_SELECT + "'/>");
	return domElement;
}

function _Designer_Control_RemoveEmptyChar(domElement) {
	$(domElement).contents().each(function(index,obj){
		if (obj.nodeType == 3 && $(obj).val() == "") {
			$(obj).remove();
		}
	});
}

//生成移动端配置单选按钮dom对象
function _Designer_Control_InputCheckbox_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_RemoveEmptyChar(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var className = "oldMui muiFormEleWrap muiCheckBoxWrap";
	if(this.options.values.alignment == "V"){
		className += " blockCheck";
	}
	var mobileEle = _Designer_Control_CreateMobileWrapElement(this.options.domElement,className);
	_Designer_Control_DrawRequired(this,mobileEle);
	var htmlCode = "";
	if(this.options.values.items==null || this.options.values.items==''){
		var style = _Designer_Control_Style(this.options.values);
		htmlCode = '<label class="muiCheckItem hasText" ><span class="mui mui-checked muiCheckBlock"></span><span  style="' + style + '" class="mui-radio-base">选项</span></label>';
	}else{
		var itemStr = this.options.values.items;
		var items = [];
		if(itemStr.indexOf("\r\n")>-1){
			items = itemStr.split("\r\n");
		}else{
			items = itemStr.split("\n");
		}
		if(this.options.values.alignment == 'V'){
			//明细表里面的单元格默认居中，当单选竖向的时候，居中的单元格显示会错乱，故这里设置为居左
			if($(this.options.domElement).closest('td')){
				$(this.options.domElement).closest('td').attr('align','left');
			}
		}
		for(var i=0; i<items.length; i++){
			if(items[i]=="")
				continue;
			var index = items[i].lastIndexOf("|");
			if(index == -1){
				var text = items[i];
				var value = items[i];
			}else{
				var text = items[i].substring(0, index);
				var value = items[i].substring(index+1);
			}
			var style = _Designer_Control_Style(this.options.values);
			htmlCode += '<label class="muiCheckItem hasText" ><span class="mui mui-checked muiCheckBlock"></span><span  style ="'+ style +'" class="mui-radio-base">' + text + '</span></label>';
			
		}
	}
	$(mobileEle).append(htmlCode);
}

//生成移动端配置单选按钮dom对象
function _Designer_Control_InputRadio_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_RemoveEmptyChar(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var className = "oldMui muiFormEleWrap muiRadioGroupWrap";
	if(this.options.values.alignment == "V"){
		className += " blockRadio";
	}
	var mobileEle = _Designer_Control_CreateMobileWrapElement(this.options.domElement,className);
	_Designer_Control_DrawRequired(this,mobileEle);
	var htmlCode = "";
	if(this.options.values.items==null || this.options.values.items==''){
		var style = _Designer_Control_Style(this.options.values);
		htmlCode = '<label class="muiRadioItem" ><span class="mui mui-sort muiRadioCircle"></span><span style ="'+ style +'" class="mui-radio-base">选项</span></label>';
	}else{
		var itemStr = this.options.values.items;
		var items = [];
		if(itemStr.indexOf("\r\n")>-1){
			items = itemStr.split("\r\n");
		}else{
			items = itemStr.split("\n");
		}
		if(this.options.values.alignment == 'V'){
			//明细表里面的单元格默认居中，当单选竖向的时候，居中的单元格显示会错乱，故这里设置为居左
			if($(this.options.domElement).closest('td')){
				$(this.options.domElement).closest('td').attr('align','left');
			}
		}
		for(var i=0; i<items.length; i++){
			if(items[i]=="")
				continue;
			var index = items[i].lastIndexOf("|");
			if(index == -1){
				var text = items[i];
				var value = items[i];
			}else{
				var text = items[i].substring(0, index);
				var value = items[i].substring(index+1);
			}
			var style = _Designer_Control_Style(this.options.values);
			htmlCode += '<label class="muiRadioItem" ><span class="mui mui-sort muiRadioCircle"></span><span  style ="'+ style +'" class="mui-radio-base">' + text + '</span></label>';
			
		}
	}
	$(mobileEle).append(htmlCode);
}
// 控件样式
   function _Designer_Control_Style(mobileStyle){
	var mobileUnderline;
	if(mobileStyle.underline == 'true'){
		mobileUnderline='underline';
	}

	var style = "color:" + mobileStyle.color +
		";font-family:"+ mobileStyle.font +
		";font-size:"+ mobileStyle.size +
		"!important ;font-weight:"+ mobileStyle.b +
		";font-style:"+ mobileStyle.i +
		";text-decoration:"+ mobileUnderline;
	return style;
}

//生成下拉框移动端dom对象
function _Designer_Control_Select_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).append(_Designer_Control_GetSelectHtml());
	var style = _Designer_Control_Style(this.options.values);
	$(domElement).append("<div class='muiSelInput muiSelInputSize' style='" + style + "' placeholder='" + PLEASE_SELECT + "'/>");
	return domElement;
}

//生成地址本移动端dom对象
function _Designer_Control_Address_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap muiCateFiledShow");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).attr("placeholder",this.options.values.label);
	$(domElement).append("<div class='muiCategoryAdd fontmuis muis-new muiNewAdd' style='display: inline-block;'></div>");
	return domElement;
}

//生成高级地址本移动端dom对象
function _Designer_Control_New_Address_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap muiCateFiledShow");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).append("<div class='muiCategoryAdd fontmuis muis-new muiNewAdd' style='display: inline-block;'></div>");
	return domElement;
}

//生成附件移动端dom对象
function _Designer_Control_Attachment_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"muiFormUploadWrap muiAttachmentEditItem muiAttachmentEditOptItem");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).attr("placeholder",this.options.values.label);
	$(domElement).append("<div class='muiAttachmentItemT'><div class='muiAttachmentItemIcon'><i class='fontmuis muis-uploader'></i></div><div class='muiAttachmentUploadFile'></div></div>");
	return domElement;
}

//生成图片上传移动端dom对象
function _Designer_Control_DocImg_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"muiFormUploadWrap muiAttachmentEditItem muiAttachmentEditOptItem");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).attr("placeholder",this.options.values.label);
	$(domElement).append("<div class='muiAttachmentItemT'><div class='muiAttachmentItemIcon'><i class='fontmuis muis-images-upload'></i></div><div class='muiAttachmentUploadFile'></div></div>");
	return domElement;
}

//标题图片
function _Designer_Control_Uploadimg_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
}


//模板附件
function _Designer_Control_UploadTemplateAttachment_DrawMobile(){
	var designer = this.owner.owner;
	var pcDesigner = designer.pcDesigner;
	var attachmentObj = this.attachmentObj;
	var fileList = attachmentObj.fileList;
	$(this.options.domElement).empty();
	for (var i = 0; i < fileList.length; i++) {
		if (fileList[i].fileStatus == 1) {
			_Designer_Control_UploadTemplateAttachment_BuildItem(this.options.domElement,fileList[i],attachmentObj);
		}
	}
}

function _Designer_Control_UploadTemplateAttachment_BuildItem(parentNode,fileObj,attachmentObj){
	var html = [];
	var name = fileObj.fileName;
	var attContainerType = _Designer_Control_UploadTemplateGetType(name);
	var iconClass = "mui-file-" + attContainerType; ;
	var size = attachmentObj.formatSize(fileObj.fileSize);
	html.push("<div class='muiAttachmentItem'>");
	html.push("<div class='muiAttachmentItemL mui-files-" + attContainerType + "'>");
	html.push("<div class='muiAttachmentItemIcon'>");
	html.push("<i class='mui " + iconClass + "'></i>");
	html.push("</div>");
	html.push("</div>");
	html.push("<div class='muiAttachmentItemC'>");
	html.push("<span class='muiAttachmentItemName'>" + name + "</span>");
	html.push("<span class='muiAttachmentItemSize'>"+ size + "</span>")
	html.push("</div>");
	html.push("</div>");
	$(parentNode).append(html.join(""));
}

function _Designer_Control_UploadTemplateGetType(fileName){
	var typeStr = '';
	if (fileName.lastIndexOf('.') > -1) {
		var fileExt = fileName
				.substring(fileName.lastIndexOf('.') + 1);
		if (fileExt != '')
			fileExt = fileExt.toLowerCase();
		switch (fileExt) {
		case 'doc':
		case 'docx':
			typeStr = 'word';
			break;
		case 'xls':
		case 'xlsx':
			typeStr = 'excel';
			break;
		case 'pps':
		case 'ppt':
		case 'pptx':
			typeStr = 'ppt';
			break;
		case 'ofd':
			typeStr = 'ofd';
			break;
		case 'pdf':
			typeStr = 'pdf';
			break;
		case 'txt':
			typeStr = 'text';
			break;
		case 'jpg':
		case 'jpeg':
		case 'ico':
		case 'bmp':
		case 'gif':
		case 'png':
		case 'tif':
			typeStr = 'img';
			break;
		case 'mht':
		case 'html':
		case 'htm':
			typeStr = 'html';
			break;
		case 'mpp':
			typeStr = 'project';
			break;
		case 'vsd':
			typeStr = 'visio';
			break;
		case 'zip':
		case 'rar':
		case '7z':
			typeStr = 'zip';
			break;
		case 'mp4':
		case 'm4v':
			typeStr = 'video';
			break;
		case 'mp3':
			typeStr = 'audio';
			break;
		}
	}
	return typeStr;
}

//前端计算
function _Designer_Control_Calculation_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).attr("removeInPreview","true");
	$(domElement).append("<div class='muiSelInput' placeholder='' style='height:3rem;'/></div>");
}

//隐藏控件
function _Designer_Control_Hidden_DrawMobile(){
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"");
	$(domElement).attr("removeInPreview","true");
	$(domElement).append("<div class='mui_hidden_img'></div>");
	
}

//大小写控件
function _Designer_Control_ChinaValue_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	var style = _Designer_Control_Style(this.options.values);
	$(domElement).append("<div class='muiInput_View' style ='+ style +'></div>");
	$(domElement).append("<div class='muiSelInputRight muiSelInputRightShow'><i class='mui mui-uper'></i></div>");
	return domElement;
}

//生成动态下拉框移动端dom对象
function _Designer_Control_RelationSelect_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).append(_Designer_Control_GetSelectHtml());
	var style = _Designer_Control_Style(this.options.values);
	$(domElement).append("<div class='muiSelInput muiSelInputSize'  style='" + style + "' placeholder='" + PLEASE_SELECT + "'/>");
	return domElement;
}

function _Designer_Control_GetSelectHtml() {
	return "<div class='muiSelInputRight muiSelInputRightShow'><span class='fontmuis muis-to-right'></span></div>";
}

//生成动态单选移动端dom对象
function _Designer_Control_RelationRadio_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var className = "oldMui muiFormEleWrap muiRadioGroupWrap";
	if(this.options.values.alignment == "V"){
		className += " blockRadio";
	}
	var mobileEle = _Designer_Control_CreateMobileWrapElement(this.options.domElement,className);
	_Designer_Control_DrawRequired(this,mobileEle);
	var style = _Designer_Control_Style(this.options.values);
	var htmlCode = "";
	for (var i = 1; i <= 3; i++) {
		htmlCode += '<label class="muiRadioItem"><span class="mui mui-sort muiRadioCircle"></span><span class="mui-radio-base" style ="'+ style +'">选项' + i + '</span></label>';
	}
	$(mobileEle).append(htmlCode);
}


//生成动态多选移动端dom对象
function _Designer_Control_RelationCheckbox_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var className = "oldMui muiFormEleWrap muiCheckBoxWrap";
	if(this.options.values.alignment == "V"){
		className += " blockCheck";
	}
	var mobileEle = _Designer_Control_CreateMobileWrapElement(this.options.domElement,className);
	_Designer_Control_DrawRequired(this,mobileEle);
	var style = _Designer_Control_Style(this.options.values);
	var htmlCode = "";
	for (var i = 1; i <= 3; i++) {
		htmlCode += '<label class="muiCheckItem hasText"><span class="mui mui-checked muiCheckBlock"></span><span class="mui-radio-base" style ="'+ style +'">选项' + i + '</span></label>';
	}
	$(mobileEle).append(htmlCode);
}

//生成选择框移动端dom对象
function _Designer_Control_RelationChoose_DrawMobile() {
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_RemoveEmptyChar(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).append(_Designer_Control_GetSelectHtml());
	$(domElement).append("<div class='muiSelInput muiSelInputSize' placeholder='" + PLEASE_SELECT + "'/>");
	return domElement;
}

//生成关联控件移动端dom对象
function _Designer_Control_Relevance_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).append("<div class='muiFormRelevanceBtnPlus'><span class='muiFormRelevanceBtnIcon fontmuis muis-new'></span></div>");
	return domElement;
}

//生成数据填充控件移动端对象
function _Designer_Control_RelationEvent_DrawMobile() {
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"");
	$(domElement).attr("removeInPreview","true");
	$(domElement).append("<div class='mui_relationEvent_img'></div>");
}

//生成属性变更移动端dom对象
function _Designer_Control_RelationRule_DrawMobile(){
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"");
	$(domElement).attr("removeInPreview","true");
	$(domElement).append("<div class='mui_relationRule_img'></div>");
}


//生成div控件移动端dom对象
function _Designer_Control_Divcontrol_DrawMobile(){
	var self = this;
	$(this.options.domElement).find("div:contains(DIV):first").each(function(index,dom){
		if(!self.options.values.isSupportMobile || self.options.values.isSupportMobile == "disable") {
			$(dom).text("");
			$(self.options.domElement).removeAttr("style");
			$(dom).hide();
			$(dom).attr("__hiddenInMobile","true");
		} else {
			$(dom).css("border","0px");
			$(dom).css("background-color","#fff");
			$(dom).text("");
			$(dom).hide();
			var domElement = _Designer_Control_CreateMobileWrapElement(self.options.domElement,"");
			$(domElement).css("min-height","24px");
			$(domElement).css("height","auto");
		}
	});
}

//生成复合控件移动端dom对象
function _Designer_Control_CompositeControl_DrawMobile(){
	var self = this;
	$(this.options.domElement).addClass("compositie");
	var compositeBar = $(this.options.domElement).find("div[compositeBar='true']")[0];
	$(compositeBar).css("background-color","");
	$(compositeBar).css("display","block");
	var controls = $(this.options.domElement).find("[formdesign='landray']");
	var mobileEles = $(this.options.domElement).find("div[mobileele='true']");
	mobileEles.css("width","100%");
	controls.css("display","inline-block");
	controls.css("width",100/controls.length+ "%");
	var designer = self.owner.owner;
}

var generateMobileHtmlUrl = Com_Parameter.ContextPath + "sys/xform/sys_xform/sysFormTemplateHtml.do?method=getHtml";

//生成明细表控件移动端dom对象
function _Designer_Control_DetailsTable_DrawMobile(){
	var domElement = this.options.domElement;
	var mobile = $(domElement).attr("mobile");
	$(domElement).attr("fd_type", this.type);
	var layout2col = $(domElement).attr("layout2col");
	if (mobile) {
		if (this.options.__oldValues 
				&& (this.options.__oldValues.layout2col != this.options.values.layout2col)) {
			_Designer_Control_Request_MobileHtml(this);
			this.options.__oldValues = null;
		}
		if (layout2col == "desktop") {
			$(domElement).css({"transform":"translate3d(0px,0px,0px)"});
			$(domElement).css("webkitTransform","translate3d(0px,0px,0px)");
			$(domElement).removeClass("muiTable").removeClass("detailsTable");
			$(domElement).addClass("detailTableNormal");
		}
		return 
	} else {
		_Designer_Control_Request_MobileHtml(this);
	}
}

//生成校验器控件移动端dom对象
function _Designer_Control_Validator_DrawMobile(){
	_Designer_Control_hiddenChildElems(this.options.domElement);
	$(this.options.domElement).css("margin-left","0px");
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"");
	$(domElement).attr("removeInPreview","true");
	$(domElement).append("<div class='mui_validator_img'></div>");
}

//生成富文本框移动端dom对象
function _Designer_Control_Rtf_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	$("img",this.options.domElement).attr("toFilter",true).attr("src",Com_Parameter.ContextPath + "sys/xform/designer/style/img/rtf_mobile.png")
	$("img",this.options.domElement).attr("width","98%").css("border","1px solid #d1d1d1");
//	if ($(this.options.domElement).parent("[name='rtf_wrap']").length > 0) {
//		return;
//	}
//	$(this.options.domElement).wrap("<div style='text-align:right;overflow: hidden;' name='rtf_wrap'></div>");
}

/**
 * 请求后台解析生成移动端配置的html
 * @param control
 * @returns
 */
function _Designer_Control_Request_MobileHtml(control) {
	var html = control.options.__pcDomElementOuterHtml;
	var self = control;
	var data = {"html":html};
    var vChar = "\u4645\u5810\u4d40";
    if (data.html && data.html.indexOf(vChar) < 0) {
        data.html = base64Encodex(data.html);
    }
	$.ajax({
		url: generateMobileHtmlUrl,
		async: false,
		data: data,
		type: "POST",
		dataType: 'json',
		success: function (result) {
			self.options.domElement.outerHTML = result.html;
			_AdjustAttr("formDesign",self.owner.owner);
			var html = self.owner.owner.getHTML();
			self.owner.owner.setHTML(html);
		},
		error: function (er) {
			console.error("generate mobile form html error : " + er)
		}
	});
}

/**
 * 调整属性,后台解析htlm时会移除掉formdesign,故加上__formdesign来标识
 * @param attr
 * @param designer
 * @returns
 */
function _AdjustAttr(attr,designer){
	var context = designer.builder.domElement;
	$(context).find("[__" + attr + "]").each(function(index,obj){
		var val = $(obj).attr("__" + attr);
		$(obj).attr(attr,val);
	});
}

//生成多标签移动端dom对象
function _Designer_Control_MutiTab_DrawMobile(){
	var mobile = $(this.options.domElement).attr("mobile");
	$(this.options.domElement).attr("fd_type","mutiTab");
	if (mobile) {
		return 
	} else {
		var self = this;
		setTimeout(function(){
			_Designer_Control_Request_MobileHtml(self);
		},1);
	}
}

//生成jsp控件移动端dom对象
function _Designer_Control_JSP_DrawMobile(){
	_Designer_Control_hiddenChildElems(this.options.domElement);
	$(this.options.domElement).css("margin-left","0px");
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"");
	$(domElement).attr("removeInPreview","true");
	$(domElement).append("<div class='mui_jsp_img'></div>");
}

//生成二维码移动端dom对象
function _Designer_Control_QRCode_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	$(domElement).append("<div class='muiQrCodeExample_img'></div>");
}

//生成地图控件移动端
function _Designer_Control_Map_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	$(domElement).append("<div class='muiSelInputRight muiSelInputRightShow muiSelInputRightText'><span>定位</span></div>");
	$(domElement).append("<div class='muiMapInput muiSelInput' placeholder='请输入" + this.options.values.label +"'/>");
	return domElement;
}

function _Designer_Control_SetCss(element,name,value) {
	$(element).css(name,value);
}

//生成新审批操作控件移动端
function _Designer_Control_NewAuditNote_DrawMobile(){
	_Designer_Control_SetCss(this.options.domElement,"height","20px");
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"handingWay");
	$(domElement).append("<div class='iconArea'>+ 签批</div>");
}

//生成审批意见控件移动端
function _Designer_Control_AuditShow_DrawMobile(){
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"muiFormEleAuditShowWrap");
	$(domElement).append("<div style='width:100%; height:auto;'><p>同意</p><p align='left'></p><p align='right'></p><p align='right'>张三</p><p align='right'>2019-12-24 11:10</p></div>");
}

//生成公式加载控件移动端
function _Designer_Control_FormulaLoad_DrawMobile(){
	var returnType = this.options.values.returnType || "select";
	if (returnType === "select") {
		_Designer_Control_RelationSelect_DrawMobile.call(this);
	} else if (returnType === "radio") {
		_Designer_Control_RelationRadio_DrawMobile.call(this);
	} else if (returnType === "checkbox") {
		_Designer_Control_RelationCheckbox_DrawMobile.call(this);
	} else if (returnType  === "text") {
		_Designer_Control_InputText_DrawMobile.call(this);
	} else if (returnType === "address") {
		_Designer_Control_Address_DrawMobile.call(this);
	}
}

//旧审批操作
function _Designer_Control_AuditNote_DrawMobile(){
	_Designer_Control_ModifyWidth(this.options.domElement);
	_Designer_Control_hiddenChildElems(this.options.domElement);
	var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"oldMui muiFormEleWrap");
	_Designer_Control_DrawRequired(this,domElement);
	$(domElement).attr("removeInPreview","true");
	$(domElement).append("<div class='muiSelInput' placeholder='' style='height:3rem;'/></div>");
}

//解析string
function _parseStr(str){
	var val;
	try{
		if (!str){
			return val;
		}
		str = Designer.HtmlUnEscape(str);
		val = JSON.parse(str);
	}catch(e){
		console.log(str + " 转换为json时出错！");
	}
	return val;
}

//阶段图
function _Designer_Control_StageDiagram_DrawMobile(){
	var domElement = this.options.domElement;
	var values = this.options.values;
	_Designer_Control_ModifyWidth(domElement);
	_Designer_Control_hiddenChildElems(domElement);
	var mobileElement = _Designer_Control_CreateMobileWrapElement(domElement,"oldMui muiFormEleWrap");
	var stageVals = values.stage || "[]";
	stageVals = _parseStr(stageVals);
	var length = stageVals ? stageVals.length : 3;
	if (values.startEndNode === "true"){
		length += 2;
	}
	var width = $(domElement).parent().width();
	var html = [];
	//默认一个
	var lineLiNum = 1;
	//计算需要多少行,也就是需要多少个ul
	var lineNum = Math.ceil(length/lineLiNum);
	//圆环流程图表 Starts
	html.push("<div class='lui-flow-rotundity'>");
	for (var i = 0; i < lineNum; i++){
		html.push("<ul class='lui-flow-rotundity-list'>");
		for(var j = 0; j < lineLiNum; j++){
			if ((i * lineLiNum + j) == length){
				break;
			}
			var stageName = "";
			var index = i * lineLiNum + j;
			if(values.startEndNode === "true"){
				//开始节点
				if (i == 0 && j == 0){
					stageName = "开始节点";
				}else if(i == lineNum - 1 && j == (length - (i * lineLiNum + 1))){//结束节点
					stageName = "结束节点";
				}else{//阶段节点
					var stageObj = stageVals[index - 1];
					if (stageObj){
						stageName = stageObj.stageName;
					}
				}
			}else{//不显示开始/结束节点
				var stageObj = stageVals[index];
				if (stageObj){
					stageName = stageObj.stageName;
				}
			}
			//i != lineNum -1 说明多行非最后一行,j == lineLiNum - 1说明当前行的最后一个,就是除了最后一行,其它行的最后一个阶段都要添加一个last-child样式
			if (i != lineNum - 1 && j == lineLiNum - 1){
				html.push("<li class='last-child'>");
			}else{
				html.push("<li>");
			}
			html.push("<span class='spot'></span>");
			html.push("<p class='title'>" + stageName + "</p>");
			html.push("</li>")
		}
		html.push("</ul>");
	}
	html.push("</div>");
	//圆环流程图表 Ends
	$(mobileElement).append(html.join(""));
}

function _Designer_Control_CreateMobileWrapElement(context,className) {
	$(context).find("div[mobileEle='true']").remove();
	var domElement = document.createElement("div");
	domElement.setAttribute("mobileEle", "true");
	$(domElement).addClass(className);
	$(context).append(domElement);
	return domElement;
}
