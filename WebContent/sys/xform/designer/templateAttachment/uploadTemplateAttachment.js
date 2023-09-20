/**********************************************************
功能：模板附件上传
使用：
	
作者：李文昌
创建时间：2017-12-15
**********************************************************/
document.write('<script>Com_IncludeFile("base64.js","../sys/attachment/js/");</script>');
Com_IncludeFile("webuploader.min.js",Com_Parameter.ContextPath + "sys/attachment/webuploader/","js",true);
Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
//引入附件js
Com_IncludeFile("swf_attachment.js?mode=edit",Com_Parameter.ContextPath	+ "sys/attachment/js/","js",true);
Designer_Config.controls['uploadTemplateAttachment'] = {
		dragRedraw : false,
		type : "uploadTemplateAttachment",
		storeType : 'none',
		inherit    : 'base',
		destroy : _Designer_Control_UploadTemplate_Destroy,
		_destroy : Designer_Control_Destroy,
		onDraw : _Designer_Control_uploadTemplateAttachment_OnDraw,
		onDrawEnd : _Designer_Control_uploadTemplateAttachment_OnDrawEnd,
		onInitialize : _Designer_Control_uploadTemplateAttachment_OnInitialize,
		drawMobile : _Designer_Control_UploadTemplateAttachment_DrawMobile,
		drawXML : _Designer_Control_uploadTemplateAttachment_DrawXML,
		implementDetailsTable : false,
		getFdValues:getFdValues_get,
		info : {
			name: Designer_Lang.controlUploadTemplateAttachment_info_name
		},
		resizeMode : 'all'
};
function getFdValues_get(name,attr,value,controlValue){
	var idValueqq=this.options.values.id;
    var controlValue = {};
	var text="";
	 controlValue.id=idValueqq;
	if(idValueqq == null||idValueqq == undefined ||idValueqq==''){
		return text;
	}
	 var idv="attachmentObject_"+idValueqq;
	 var fileList=   Designer.instance[idv].fileList;
     if(fileList== undefined &&fileList.length==0){
	      return text;
     }
     for(var i=0;i<fileList.length;i++){
	   var fileName =  fileList[i].fileName;
       var fileStatus =  fileList[i].fileStatus;
        if(fileStatus==-1){
	       text=text+""
        }else{
	       if(text!=""){
	        text=text+";"
           }
	       text=text+fileName;
         }
         
     }
    controlValue.fileName=text;
   
    return controlValue;
  
       
}

function _Designer_Control_GetTemplateId(domElement){
	var fdId = Com_GetUrlParameter(window.top.location.href, "fdId");
	if (!fdId) {
		fdId = $("input[type='hidden'][name='fdId']",window.top.document).val();
	}
	var fdModelId = $(domElement).attr("fdModelId");
	if (!fdId) {
		fdId = fdModelId;
	}
	return fdId;
}
var config = _Designer_Control_uploadTemplateAttachment_LoadConfig();
if (config.wpsoaassist == "true") {
    Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);
}

//再次编辑
function _Designer_Control_uploadTemplateAttachment_OnInitialize(){
	if (this["attachmentObject_"+this.options.values.id]){
		return ;
	}
	
	if (Designer.instance.isMobile) {
		var pcDesigner = Designer.instance.pcDesigner;
		if (pcDesigner) {
			this.attachmentObj = pcDesigner["attachmentObject_" + this.options.values.id];
			$(this.options.domElement).css("width","100%");
		}
	} else {
		//构建附件对象
		var attachmentConfig = _Designer_Control_uploadTemplateAttachment_LoadConfig();
		var config = _Designer_Control_uploadTemplateAttachment_BuildConfig(attachmentConfig);
		//获取附件列表
		var attachmentsTable = document.getElementById("att_xtable_" + this.options.values.id);
		var fdId = _Designer_Control_GetTemplateId(this.options.domElement);
		var domElement = this.options.domElement;
		$(domElement).find('[name="attachmentForms.' + this.options.values.id + '.fdModelId"]').val(fdId);
        var fdModelId = $(domElement).attr("fdModelId");
        var originalId = fdId;
        if (fdModelId != fdId) {
            originalId = fdModelId;
        }
		$(domElement).attr("fdModelId",fdId);
		var fdModelName = Designer.instance.fdModelName || $(domElement).attr("fdmodelname");
		$(domElement).attr("fdModelName",fdModelName);
		$(domElement).find('[name="attachmentForms.' + this.options.values.id + '.fdModelName"]').val(fdModelName);
		//多表单切换会调用此方法，导致一个控件重复生成多个swf_att对象
		var self = this["attachmentObject_"+this.options.values.id] = new Swf_AttachmentObject(this.options.values.id,fdModelName,fdId,true,"byte","edit","","",true,"",config);
		
		self.canMove = true;
//		self.required = true;
		self.uploadAfterSelect = true;
		// 模板附件不支持拖拽
		self._supportDnd = false;
		self._supportDndSort = false;
		//设置附件上传大小,类型配置
		_Designer_Control_setAttachmentObjConfig(self,attachmentConfig);
		
		
		if (Designer.instance["attachmentObject_" + this.options.values.id]){
			var oldAttachObj = Designer.instance["attachmentObject_" + this.options.values.id];
			self.fileList = oldAttachObj.fileList;
		}else{
			Designer.instance["attachmentObject_"+this.options.values.id] = self;
		}
		Designer.instance["__attachmentObject_"+this.options.values.id] = this;
		var param = {fdModelName:fdModelName,fdModelId:originalId,
					fdKey:this.options.values.id,fdMulti:'',fdAttType:'byte'};
		//发送请求,获取附件
		$.ajax({
			url: Com_Parameter.ContextPath + "sys/xform/sys_xform/sysFormTemplateAttachmentAction.do?method=getAttMain",
			type: 'POST',
			dataType: 'json',
			data: param,
			async: false,
			success:function(data){
				for (var i = 0; i < data.length; i++){
					attMain = data[i];
					var isAdd = true;
					for (var j = 0; j < self.fileList.length; j++){
						var fileObj = self.fileList[j];
						if (fileObj.fdId === attMain.fdId){
							isAdd = false;
						}
					}
					if (isAdd){
						self.addDoc(attMain["fdFileName"],attMain["fdId"],true,
								attMain["fdContentType"],attMain["fdSize"],attMain["fdFileId"]);
					}
				}
				self.show();
			},
			error:function(data){
				alert("获取上传文件失败!");
				$(domElement).find("[data-lui-mark='attachmentlist']").remove();
				self.show();
			}
		});
		var uploadTemplateControl = this;
		var contextWin = Designer.instance.parentWindow;
		var form = contextWin.document.forms[0]
		self.win = contextWin;
		contextWin.Com_Parameter.event["confirm"].push(function() {		 					 				
			if (self.editMode == "edit" || self.editMode == "add") {
				$(form).append($("#_List_" + uploadTemplateControl.options.values.id + "_Attachment_Table")[0]);
				return true;
			} 
		});
	}
}

/****************************************
 * 需改附件名称
 ***************************************/
window.alterName = function(docId,self) {
	for (var i = 0; i < self.fileList.length; i++){
		if(self.fileList[i].fdId == docId){
			reName(self,i,self);
			break;
		}
	}
};

//view页面
function _Designer_Control_uploadTemplateAttachment_view(id){
	//获取附件列表
	var attachmentsTable = document.getElementById("att_xtable_" + id);
	var domElement = $("#" + id)[0];
    var fdModelName = Designer.instance.fdModelName || $(domElement).attr("fdmodelname");
	var fdId = _Designer_Control_GetTemplateId(domElement);
	//构建附件对象
	var self = this["attachmentObject_"+id] = new Swf_AttachmentObject(id,fdModelName,fdId,true,"byte","view","","",true);
    var attachmentConfig = _Designer_Control_uploadTemplateAttachment_LoadConfig();
	self.canDownload = true;
	self.canEdit = true;
	self.canPrint = true;
    self.canRead = true;
//	self.canMove = true;
	// 模板附件不支持拖拽
	self._supportDnd = false;
	self._supportDndSort = false;
    _Designer_Control_setAttachmentObjConfig(self, attachmentConfig);
    var fdModelId = $(domElement).attr("fdModelId");
    var originalId = fdId;
    if (fdModelId != fdId) {
        originalId = fdModelId;
    }
	var param = {fdModelName:fdModelName,fdModelId:originalId,
				fdKey:id,fdMulti:'',fdAttType:'byte'};
	//发送请求,获取附件
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/xform/sys_xform/sysFormTemplateAttachmentAction.do?method=getAttMain",
		type: 'POST',
		dataType: 'json',
		data: param,
		success:function(data){
			for (var i = 0; i < data.length; i++){
				attMain = data[i];
				self.addDoc(attMain["fdFileName"],attMain["fdId"],true,
							attMain["fdContentType"],attMain["fdSize"],attMain["fdFileId"],attMain["downloadSum"]);
			}
			self.show();
            setTimeout(function(){
                var iframe = $("[id*='IFrame_FormTemplate_']", window.top.document)[0];
                if (iframe) {
                    XForm_AdjustViewHeight(iframe);
                }
            }, 1000);
		},
		error:function(data){
			alert("获取上传文件失败!");
		}
	});
}

//view页面
function _Designer_Control_uploadAttachment_loadAttrMain(){
	if(!Designer.instance.shortcuts){
		//获取所有的附件控件
		var atts = $(document).find("div[fd_type='uploadTemplateAttachment']");
		for (var i = 0; i < atts.length; i++){
			var id = $(atts[i]).attr("id");
			_Designer_Control_uploadTemplateAttachment_view(id);
		}
	}
}

Designer_Config.operations['uploadTemplateAttachment'] = {
		lab : "2",
		imgIndex : 63,
		title : Designer_Lang.controlUploadTemplateAttachment_title,
		run : function (designer) {
			designer.toolBar.selectButton('uploadTemplateAttachment');
		},
		type : 'cmd',
		order: 40,
		select: true,
		cursorImg: 'style/cursor/uploadTemplateAttachment.cur',
		isShow: _Designer_Control_UploadTemplateAttachment_isShow
	};

Designer_Config.buttons.layout.push("uploadTemplateAttachment");
Designer_Menus.layout.menu['uploadTemplateAttachment'] = Designer_Config.operations['uploadTemplateAttachment'];

//在designer中引入seajs
/*if (top["seajs"] && !window.seajs){
	window.seajs = top.seajs;*/
	seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
		var changeName;
		
		window.reName = function(att,i,self) {
			var fdFileName = att.fileList[i].fileName;
			if(fdFileName !=null &&fdFileName !=""){
				fdFileName = fdFileName.substring(0,fdFileName.lastIndexOf("."))
				fdFileName = encodeURIComponent(fdFileName);
			}
			
			var iframeUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=editName&fdFileName="+fdFileName;
			var title = '<bean:message bundle="sys-attachment" key="sysAttMain.button.rename"/>' ;
			dialog.iframe(iframeUrl, title, function(data) {
				if (null != data && undefined != data) {
					changeName = data.fdFileName;
					var fileName = att.fileList[i].fileName;
					var fileExt = fileName.substring(fileName.lastIndexOf("."));
					att.fileList[i].fileName = changeName+fileExt;
					$.ajax({
						url: self.alterUrl,
						type: 'POST',
						data:{id:att.fileList[i].fdId,name:att.fileList[i].fileName},
						dataType: 'json',
						async:false, 
						success: function(data){
							self.showed = false;
							self.btnIntial = false;
							att.initMode = false;
							att.show();
						}
				   });
	
				}
			}, {
				width : 450,
				height : 200
			});
		};
	});
//}

function _Designer_Control_uploadTemplateAttachment_OnDraw(parentNode,childNode){
	if(!Designer.instance.shortcuts){
		return;
	}
	
	if (Designer.instance.isMobile) {
		var domElement = _CreateDesignElement('div', this, parentNode, childNode);
		if(this.options.values.id == null) {
			this.options.values.id = "fd_" + Designer.generateID();
		}
		domElement.id = this.options.values.id;
		var fdId = _Designer_Control_GetTemplateId(this.options.domElement);
		var fdModelName = Designer.instance.fdModelName;
		$(domElement).attr("fdModelId",fdId);
		$(domElement).attr("fdModelName",fdModelName);
		var pcDesigner = Designer.instance.pcDesigner;
		this.attachmentObj = pcDesigner["attachmentObject_" + this.options.values.id];
		$(this.options.domElement).css("width","100%");
	} else {
		if(this.options.values.id != null){
			return ;
		}
		
		var domElement = _CreateDesignElement('div', this, parentNode, childNode);
		if(this.options.values.id == null)
			this.options.values.id = "fd_" + Designer.generateID();
		domElement.id = this.options.values.id;
		
		var contentDiv = document.createElement("div");
		var attachmentDiv = document.createElement("div");
		$(domElement).attr("validate","required");
		var fdId = _Designer_Control_GetTemplateId(this.options.domElement);
		var fdModelName = Designer.instance.fdModelName;
		$(domElement).attr("fdModelId",fdId);
		$(domElement).attr("fdModelName",fdModelName);
		$(attachmentDiv).attr("id","_List_" + this.options.values.id + "_Attachment_Table");
		var fdModelId = $("<input type='hidden' name='attachmentForms." + this.options.values.id +".fdModelId' value=\"" + fdId +  "\" />");
		var extParam = $("<input type='hidden' name='attachmentForms." + this.options.values.id +".extParam' />");
		var modelName = $("<input type='hidden' name='attachmentForms." + this.options.values.id +".fdModelName' value=\"" + fdModelName + "\" />");
		var fdKey = $("<input type='hidden' name='attachmentForms." + this.options.values.id +".fdKey' value=\"" + this.options.values.id +"\" />");
		var fdAttrType =  $("<input type='hidden' name='attachmentForms." + this.options.values.id +".fdType' value='byte' />");
		var fdMulti =  $("<input type='hidden' name='attachmentForms." + this.options.values.id +".fdMulti' value='true' />");
		var deletedAttachmentIds =  $("<input type='hidden' name='attachmentForms." + this.options.values.id +".deletedAttachmentIds' />");
		var attachmentIds =  $("<input type='hidden' name='attachmentForms." + this.options.values.id +".attachmentIds' />");
		$(contentDiv).attr("id","attachmentObject_" + this.options.values.id + "_content_div");
		$(attachmentDiv).append(fdModelId);
		$(attachmentDiv).append(extParam);
		$(attachmentDiv).append(fdModelId);
		$(attachmentDiv).append(modelName);
		$(attachmentDiv).append(fdKey);
		$(attachmentDiv).append(fdAttrType);
		$(attachmentDiv).append(fdMulti);
		$(attachmentDiv).append(deletedAttachmentIds);
		$(attachmentDiv).append(attachmentIds);
		$(domElement).append(attachmentDiv);
		$(domElement).append(contentDiv);
		$(domElement).css("width","100%");
		$(domElement).css("height","80%");
		//构建附件对象
		var attachmentConfig = _Designer_Control_uploadTemplateAttachment_LoadConfig();
		var config = _Designer_Control_uploadTemplateAttachment_BuildConfig(attachmentConfig);
		var attachment = this["attachmentObject_" + this.options.values.id] = new Swf_AttachmentObject(this.options.values.id,fdModelName,fdId,true
				,"byte","edit","","",true,"",config);
		attachment.canMove = true;
		attachment.uploadAfterSelect = true;
		// 模板附件不支持拖拽
		attachment._supportDnd = false;
		attachment._supportDndSort = false;
		 //设置必填属性
//		 this["attachmentObject_" + this.options.values.id].required = true;
		//设置附件上传大小,类型配置
		_Designer_Control_setAttachmentObjConfig(attachment,attachmentConfig);
		var self = this;
		var attch = self.owner.resizeDashBox.attach;
		this["attachmentObject_" + this.options.values.id].showAfterCustom =function(){
			jQuery.proxy(attch,self.owner.resizeDashBox)(self);
		}
		Designer.instance["attachmentObject_"+this.options.values.id] = attachment;
		Designer.instance["__attachmentObject_"+this.options.values.id] = this;
		this["attachmentObject_" + this.options.values.id].show();
		$("input[type='file'][name='__landray_file" + this.options.values.id + "']").mousedown(function(){});
		var contextWin = Designer.instance.parentWindow;
		attachment.win = contextWin;
		var form = contextWin.document.forms[0];
		contextWin.Com_Parameter.event["confirm"].push(function() {		 					 				
			if (attachment.editMode == "edit" || attachment.editMode == "add") {
				$(form).append($("#_List_" + self.options.values.id + "_Attachment_Table")[0]);
				return true;
			} 
		});
	}
}

function _Designer_Control_setAttachmentObjConfig(attachment,config){
	if (config.smallMaxSize){
		attachment.setSmallMaxSizeLimit(config.smallMaxSize);
		attachment.setSingleMaxSize(config.smallMaxSize);
	}
	if (config.fileLimitType){
		attachment.setFileLimitType(config.fileLimitType);
	}			
	if (config.disabledFileType){
		attachment.setDisableFileType(config.disabledFileType);
	}
    //加载项
    //attachment.setWpsoaassist(config.wpsoaassist);
    attachment.wpsoaassist=config.wpsoaassist;
    attachment.wpsoaassistEmbed=config.wpsoaassistEmbed;
}

//加载附件配置
function _Designer_Control_uploadTemplateAttachment_LoadConfig(){
	var config;
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/xform/sys_xform/sysFormTemplateAttachmentAction.do?method=getAttMainConfig",
		type: 'GET',
		dataType: 'json',
		async: false,
		success:function(data){
			 config = data;
		},
		error:function(data){
			alert("获取附件上传配置失败!");
		}
	});
	return config;
}

function _Designer_Control_uploadTemplateAttachment_BuildConfig(config){
	var attachmentConfig = {};
	 attachmentConfig.uploadurl = config.uploadUrl;
	 attachmentConfig.methodName = config.methodName;
	 attachmentConfig.isSupportDirect = config.isSupportDirect;
	 attachmentConfig.fileVal = config.fileVal;
	 attachmentConfig.beforeSendFile = config.beforeSendFile;
	return attachmentConfig;
}

function _Designer_Control_uploadTemplateAttachment_OnDrawEnd(){
	
}

function _Designer_Control_uploadTemplateAttachment_DrawXML(){
	var values = this.options.values;
	var buf = ['<attachmentProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="templateAttachment"');
	buf.push('/>');
	return buf.join('');
}

function _Designer_Control_UploadTemplate_Destroy(){
	//如果删除控件则将附件对象必填校验设置为false
	var attObj = this["attachmentObject_" + this.options.values.id];
	if (attObj) {
		attObj.required = false;
	}
	this._destroy();
}

function _Designer_Control_UploadTemplateAttachment_isShow(){
	//屏蔽通用
	if (Designer.instance.fdModel){
		return true;
	}else{
		return false;
	}
}
