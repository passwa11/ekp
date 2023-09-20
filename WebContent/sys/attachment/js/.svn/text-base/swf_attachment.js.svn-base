Com_RegisterFile("swf_attachment.js");
Com_IncludeFile("jquery.js");
Com_IncludeFile('jquery.ui.js', 'js/jquery-ui/');
Com_IncludeFile('ckresize.js', 'ckeditor/');
Com_IncludeFile("fileIcon.js",Com_Parameter.ResPath + "style/common/fileIcon/","js",true);
Com_IncludeFile("upload.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
Com_IncludeFile("dnd.css",Com_Parameter.ContextPath	+ "sys/attachment/view/img/","css",true);
Com_IncludeFile("base64.js",Com_Parameter.ContextPath + "sys/attachment/js/","js",true);
Com_IncludeFile("sysAttMain_MessageInfo.jsp?locale="+ Com_Parameter.__sysAttMainlocale__,Com_Parameter.ContextPath + "sys/attachment/sys_att_main/", 'js',true);
//var srcObj = document.getElementById("swf_attachment_js");
//if(srcObj!=null){
//	if("edit" == Com_GetUrlParameter(srcObj.src, "mode")){	//TODO 仅仅编辑模式加载此JS，节省查看时页面流量，暂时注释该功能，
Com_IncludeFile("webuploader.min.js",Com_Parameter.ContextPath + "sys/attachment/webuploader/","js",true);
//	}
//}
 
String.prototype.trim = function(){
     return this.replace(/(^\s*)(\s*$)/g, "");
};

if(typeof Attachment_SyncAjax_Cache  == "undefined")
	Attachment_SyncAjax_Cache = {};

try {
	if( (Com_Parameter.top || window.top).window && typeof (Com_Parameter.top || window.top).window.Attachment_ObjectInfo == "undefined"){
		(Com_Parameter.top || window.top).window.Attachment_ObjectInfo = [];
	}
} catch (e) {
	if(typeof window.Attachment_ObjectInfo == "undefined"){
		window.Attachment_ObjectInfo = [];
	}	
}

if (typeof $ != "undefined") {
	$.fn.extend({
		actual : function(method, options) {
			if (this[method]) {
		       var actual = this[method]();
		       return actual;
			}
		}
	});
}

var FILE_EXT_OFFICE = "All Files (*.*)|*.*|Office Files|*.doc;*.xls;*.ppt;*.vsd;*.rtf;*.csv";
var FILE_EXT_PIC = "Picture Files|*.gif;*.jpg;*.jpeg;*.bmp;*.png;*.tif|All Files (*.*)|*.*";
var File_EXT_READ = ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.mppx;.pptx;.xlcx;.wps;.et;.vsd;.rtf;.dwg;.dxf;.dps";
var File_EXT_PRINT =  window.File_EXT_PRINT ? window.File_EXT_PRINT : ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.mppx;.pptx;.xlcx;.wps;.et;.vsd;.rtf;";//允许打印的文件
var File_EXT_READDOWNLOAD = ".pdf;.ofd;.html";
var File_EXT_EDIT = ".doc;.xls;.ppt;.xlc;.docx;.xlsx;.mppx;.pptx;.xlcx;.wps;.et;.vsd;.rtf";
var File_EXT_PIC_READ = ".gif;.jpg;.jpeg;.bmp;.png;.tif";
var File_EXT_VIDEO = ".flv;.mp4;.f4v;.mp4;.m3u8;.webm;.ogg;.theora;.mp4;.avi;.mpg;.wmv;.3gp;.mov;.asf;.asx;.wmv9;.rm;.rmvb;.wrf;.m4v" ;
var File_EXT_MP3 = ".mp3";
var File_EXT_NO_PRINT = ".ppt;.pptx";//不可打印的文件后缀
var File_EXT_NO_READ = ".mpp;.mppx";//不可在线阅读的文件
var File_EXT_JG_SIGNATURE_PDF = ".pdf";
var File_EXT_SIGNATURE_OFD = ".ofd";
var File_EXT_WPS_PPT = ".ppt;.pptx;.dps";//WPS加载项时，不可编辑


/*************************************
 * 附件对象，用于附件机制上传和下载
*************************************/
function Swf_AttachmentObject(
		_fdKey, 
		_fdModelName, 
		_fdModelId, 
		_fdMulti, 
		_fdAttType, 
		_editMode ,
		_fdSupportLarge, 
		enabledFileType,
		_isUseByXForm, 
		_uploadurl,config, 
		_fileNumLimit, 
		_disabledImageView 
) {
	if("edit"==_editMode){}
	
	var self = this;
	
	this.fdKey = _fdKey; 				// 附件的标识
	this.fdLabel = "附件";
	this.fdGroup = "附件";
	
	// 获取文件key值
	this.getFileVal = function(){
		return ("__landray_file"+self.fdKey);
	}
	
	var extendConfig = {
			methodName: 'POST',
			isSupportDirect: false,
			fileVal: self.getFileVal(),
			uploadurl: '/sys/attachment/uploaderServlet?gettype=upload&format=json'
		};
	$.extend(extendConfig,config);

	// 是否支持直连模式
	this.isSupportDirect = extendConfig.isSupportDirect;
		
		
	this.disabled = false;			//附件对象是否不可用
	this.wpsoaassist=false; 		//是否是wps加载项
	this.wpsoaassistEmbed=false; 		//是否是内嵌wps加载项
	this.redhead=false; 			//是否是wps加载项套红
	this.cleardraft=false; 			//加载项清稿
	this.bookMarks=null; 			//wps加载项套红书签
	this.nodevalue=null; 			//wps加载项套红节点信息
	this.signTrue=null; 			//wps加载项印章
	this.newFlag=null; 				//是否打开留痕
	this.forceRevisions=null; 		//强制不留痕
	this.saveRevisions=null; 		//保存痕迹稿
	this.wpsExtAppModel=null; 		
    this.eventsCache = {};
	//还未弄明白的属性
	this.fdShowMsg = true;
	this.showDefault = null;
	this.buttonDiv = null;
	this.isTemplate = null;
	this.enabledFileType = enabledFileType;
	this.showRevisions = null;
	this.addToPreview = true;
	this.slideDown = false;
	//标识是否在表单模板中上传附件
	this.isUseByXForm = (typeof _isUseByXForm === "undefined") ? false : _isUseByXForm;
	
	//附件额外功能支持
	this.uploadAfterSelect = false; //附件上传时,是否选择附件后就马上产生附件文档。
	//附件删除事件,提供外部回调,回调返回删除id和当前对象
	this.uploadFileDeleteEvent = window.onUploadFileDeleteEvent?window.onUploadFileDeleteEvent:null;
	//附件上传成功后提供对外的回调函数,把对象当做参数提供给外部使用
	this.uploadAfterSuccessEvent = window.onUploadAfterSuccessEvent?window.onUploadAfterSuccessEvent:null;
	this.uploadAfterCustom = window.onFinishPostCustom?window.onFinishPostCustom:null;
									//和uploadAfterSelect配合使用，单个附件上传后，如果立即产生附件文档，执行此函数，参数: 当前文档
									//兼容旧接口window.onFinishPostCustom。
	this.onFinishPostCustom = null; //附件上传时,上传后，文档提交时，额外的信息处理,参数：当前的附件列表。
	this.onClickCustom = null;  	//附件展现时,附件点击后，自定义点击事件函数,参数: 当前附件; 返回值 继续true 中断false。
	this.showCustomMenu = null; 	//附件展现时,附件点击后，自定义右键菜单展现,参数：当前menu。
	this.drawFunction = null;		//附件上传或展现，用于绘制附件的列表展现界面,参数：当前附件对象
	
	this.showAfterCustom = null;	// 附件绘画完毕后执行方法
	
	//图片类型附件属性
	this.fdImgHtmlProperty = null;
	this.width = null;
	this.height = null;
	//是否按比例压缩
	this.proportion = true;
	//是否必填
	this.required = false; 
	/**
	 * 强制不允许使用的操作，
	 * edit，open，read，print，download，copy，可多值用";"分隔，如不允许编辑和下载:"edit;download".
	 * 默认按文档权限显示对应操作
	 */
	this.forceDisabledOpt = null;
	this.hideTips = false; //单文件时，是否隐藏提示
	this.hideReplace = false; //单文件时，是否隐藏【替换】
	this.hidePicName = false;//是否隐藏图片名称（名称，后缀，文件大小）
	this.canChangeName = true;//是否可以重新命名
	this.filenameWidth = '';

	// 文件名长度的限制
	this._fileNameMax = null;
	//是否强制使用金格，公文和合同场景,只影响查看和编辑
	this.fdForceUseJG = null;
	this.setFdForceUseJG = function(_fdForceUseJG){
		if(_fdForceUseJG != null && _fdForceUseJG != "" && _fdForceUseJG == "true"){
			self.fdForceUseJG = true;	
		}
	};
	
	this.setWpsoaassist=function(_setWpsoaassist){
		self.wpsoaassist=_setWpsoaassist;
		if(self.wpsoaassist=="true"){
			Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);
		}
	}	
	
	this.setHideTips=function(_hideTips){
		self.hideTips=_hideTips;
	}
	this.setHideReplace=function(_hideReplace){
		self.hideReplace=_hideReplace;
	}
	this.setHidePicName=function(_hidePicName){
		self.hidePicName=_hidePicName;
	}
	this.setFilenameWidth=function(_filenameWidth){
		self.filenameWidth=_filenameWidth;
	}
	
	this.setCanChangeName=function(_canChangeName){
		self.canChangeName=_canChangeName;
	}
	
	this.setRedhead=function(_setRedHead){
		self.redhead=_setRedHead;
	}
	
	this.setCanEdit=function(_setCanEdit){
		self.canEdit=_setCanEdit;
	}
	
	this.setForceRevisions=function(_setForceRevisions){
		self.forceRevisions=_setForceRevisions;
	}
	this.setSaveRevisions=function(_setSaveRevisions){
		self.saveRevisions=_setSaveRevisions;
	}
	this.setNewFlag=function(_setNewFlag){
		self.newFlag=_setNewFlag;
	}
	this.setCleardraft=function(_setCleardraft){
		self.cleardraft=_setCleardraft;
	}
	
	this.setWpsExtAppModel=function(_setWpsExtAppModel){
		self.wpsExtAppModel=_setWpsExtAppModel;
		if(self.wpsExtAppModel == "kmImissive"){
			Com_IncludeFile("kmImissiveSendRedhead_script.js",Com_Parameter.ContextPath + "km/imissive/wps/","js",true);
		}
	}
	
	this.setBookMarks=function(_setBookMarks){
		self.bookMarks=_setBookMarks;
	}
	
	this.setNodevalue=function(_setNodevalue){
		self.nodevalue=_setNodevalue;
	}
	
	this.setSignTrue=function(_setSignTrue){
		self.signTrue=_setSignTrue;
	}
	
	
	//是否启用金格iSignaturePDF网页签章
	this.isJGSignaturePDFEnabled=null;
	this.setJGSignaturePDFEnabled = function(enable){	
		if(self.canEdit){
			self.isJGSignaturePDFEnabled = enable;
		}else{
			self.isJGSignaturePDFEnabled = false;
		}
	}
    //是否使用附件签章
	this.enableAttachmentSignature = false;
	this.setEnableAttachmentSignature = function(enable) {
		if(self.canEdit){
			self.enableAttachmentSignature = enable;
		}else{
			self.enableAttachmentSignature = false;
		}
	}

	// 是否使用了金格签章（不用admin.do的配置）
	this.enableAttachmentSignatureByJG = false;
	this.setEnableAttachmentSignatureByJG = function(enable) {
		if(self.canEdit){
			self.enableAttachmentSignatureByJG = enable;
		}else{
			self.enableAttachmentSignatureByJG = false;
		}
	}
	//是否启用点聚签章
	this.isDianjuSignatureEnabled = false;
	this.setDianjuSignatureEnabled = function(enable){
		if(self.canEdit){
			self.isDianjuSignatureEnabled = enable;
		}else{
			self.isDianjuSignatureEnabled = false;
		}
	}

	// 在线预览是什么
	this.readOnlyFoxit = false;
	this.setReadOnlyFoxit = function(readOnly) {
		this.readOnlyFoxit = readOnly;
	}

	//只是用于在线编辑的时候
	this.actionObj = null;
	
	//附件基本属性定义
	this.fdModelName = _fdModelName; 	// 对应域模型
	this.fdModelId = _fdModelId; 		// 主文档ID
	if(_fdMulti==null || _fdMulti=="" || _fdMulti=="true" || _fdMulti=="1") 
		this.fdMulti = true;
	else 
		this.fdMulti = false;			//是否允许多附件
	this.fdAttType = (_fdAttType==null||_fdAttType=="")?"byte":_fdAttType; 		
										//附件类型 office,pic,byte
	this.fdViewType = this.fdAttType; 	//附件显示样式 office,pic,link
	this.setFdViewType = function(_fdViewType){
		self.fdViewType = _fdViewType;		
		if (_fdViewType.substring(0, 1) == '/') {
			self.renderurl=Com_Parameter.ContextPath+_fdViewType.substring(1);
		} else {
			self.renderurl=Com_Parameter.ContextPath+"sys/attachment/view/render_"+_fdViewType+".tmpl";
		}
	};
	this.setFdLabel = function(_fdLabel){
		self.fdLabel = _fdLabel;
	};
	this.setFdGroup = function(_fdGroup){
		self.fdGroup = _fdGroup;
	};
	this.fdLayoutType = 'new';
	this.setFdLayoutType = function(___fdLayoutType) {
		if(___fdLayoutType.substring(0, 1) == '/'){
			self.fdLayoutType=___fdLayoutType;
			self.layouturl=Com_Parameter.ContextPath+___fdLayoutType.substring(1);
		}else{
			self.fdLayoutType = ___fdLayoutType;
			self.layouturl = Com_Parameter.ContextPath + "sys/attachment/view/layout_"+___fdLayoutType+".tmpl";
		}
		
	};
	//大附件使用属性
	this.fdSupportLarge = _fdSupportLarge;	//是否支持大附件
	this.fdBigAttUploaded = true;			//大附件是否上传完
	this.bigAttFrameObj = null;  			//大附件弹出框对象
	this.uploadText = null; //上传按钮旁文本显示
	
	// 编辑模式，修改或查看，若参数未提供则尝试去URL里面取。
	if (_editMode == null)
		this.editMode = Com_GetUrlParameter(location.href, "method");
	else
		this.editMode = _editMode;
	
	this.initMode = true;
	// 附件权限属性定义
	if (this.editMode == "edit" || this.editMode == "add") {
		this.canDelete = true;
		this.canRead = true;
		this.canDownload = true;
		this.canEdit = true;
		this.canPrint = true;
		this.canAdd = true;
	} else {
		this.canDelete = false;
		this.canRead = false;
		this.canDownload = false;
		this.canEdit = false;
		this.canPrint = false;
		this.canAdd = false;
	}	
	
	this.canDownPic = true;
	this.canMove = false;
	this.isNew = false;
	this.canReadHistory = false; 
	
	var top = Com_Parameter.top || window.top;
	
	try {
		if(top.window){
			top.window.Attachment_ObjectInfo[this.fdKey] = this;      //记录全局附件对象列表
		}
	} catch (e) {
		window.Attachment_ObjectInfo[this.fdKey] = this;      //记录全局附件对象列表
	}
	
	this.jsname = "attachmentObject_" + this.fdKey; //当前附件对象js变量名
	
	this.fileNumLimit = _fileNumLimit ? _fileNumLimit : 100;
	
	//单文件最大限制
	this.singleMaxSizeTxt = null;
	this.singleMaxSize = 1000000;
	
	//所有文件最大限制
	this.totalMaxSizeTxt = null;
	this.totalMaxSize = 10;
	this.smallMaxSizeLimit = 1000000;           //附件定义最大大小
	this.fileLimitType = "1";  //"0":不限制 "1":不允许 "2":只允许	
	this.disabledFileType = ".js;.bat;.exe;.sh;.cmd;.jsp;.jspx";
	//是否显示下载次数,默认显示
	this.isShowDownloadCount = true;
	
	//swfupload小附件使用变量
	this.btnIntial = false;
	this.renderId = "attachmentObject_" + this.fdKey + "_content_div";  //附件列表显示html区域ID
    this.tokenurl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=getuserkey&format=json";
    this.md5Url = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=checkMd5&format=json";
    // 保存sysAttFile信息，OSS相关存储回调使用
    this.addFileUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=addFile";
    this.attachurl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=handleAttUpload&gettype=submit&format=json";
    this.deleteUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=delete&format=json";
    this.uploadurl = _uploadurl ? _uploadurl: extendConfig.uploadurl;
    this.alterUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=updateFileName&format=json";
    this.layouturl = Com_Parameter.ContextPath + "sys/attachment/view/layout_"+this.fdLayoutType+".tmpl";
    this.renderurl = Com_Parameter.ContextPath + "sys/attachment/view/render_"+this.fdViewType+".tmpl";
	//文件列表数组，数组对象
	this.fileList = [];
	// 附件图片预览开关
	this.disabledImageView = _disabledImageView ? _disabledImageView : false;
	// 预览图片集合
	this.imageList = [];
	// 系统弹窗
	this.dialogAlert = alert;
	// 检查当前环境是否支持图片预览
	var win = top;
	try {
		typeof (win['seajs']);// 跨域错误判断
	} catch (e) {
		win = window;
	}
	if(typeof(seajs) == 'undefined' && typeof( win['seajs'] ) == 'undefined'){
		this.disabledImageView = true;
	}else{
		try
		{
			seajs.use(['lui/dialog'], function(dialog) {
				self.dialogAlert = dialog.alert;
		    });
		}catch(err)
		{
			console.log(err);
		}
		
	}
	
	// 是否支持拖拽上传
	this._supportDnd = true;
	this.setSupportDnd = function(_supportDnd){
		if(_supportDnd == 'false') {
			self._supportDnd = false;
		}
	};
	// 是否支持拖拽排序
	this._supportDndSort = true;
	this.setSupportDndSort = function(_supportDndSort){
		if(_supportDndSort == 'false') {
			self._supportDndSort = false;
		}
	};
	// 固定宽度
	this.fixedWidth = undefined;
	this.setFixedWidth = function(_fixedWidth){
		if(!isNaN(_fixedWidth)) {
			self.fixedWidth = _fixedWidth;
		}
	};
	// 总宽度（可传入：90% 或 980px），默认：100%
	this.totalWidth = undefined;
	this.setTotalWidth = function(_totalWidth){
		self.totalWidth = _totalWidth;
	};
	// 是否打印模式（或者通过全局设置：window.isPrintModel = true）
	this.isPrintModel = window.isPrintModel == undefined ? false : window.isPrintModel;
	this.setIsPrintModel = function(_isPrintModel){
		self.isPrintModel = "true" == _isPrintModel;
	};
	// 是否允许删除（只针对单附件模式），默认允许删除
	this.showDelete = true;
	this.setShowDelete = function(_showDelete){
		if("false" == _showDelete) {
			self.showDelete = false;
		}
	};
	this.supportDnd = function() {
		if(self._supportDnd) {
			self._supportDnd = typeof(Worker) !== "undefined";
		}
		return self._supportDnd;
	}
	
	/****************************************
	功能：格式化大小
	****************************************/
	this.parseSize = function(maxSize){
		var result = 0;
		if(maxSize!=null && maxSize!="") {
			maxSize = maxSize.toLowerCase();
			var lastC = maxSize.charAt(maxSize.length-1);
			if(lastC=="g") {
				maxSize = maxSize.substring(0,maxSize.length-1);
				result = parseInt(maxSize)*1024*1024*1024;			
			}else if(lastC=="m") {
				maxSize = maxSize.substring(0,maxSize.length-1);
				result = parseInt(maxSize)*1024*1024;
			}else if(lastC=="b") {
				if(maxSize.charAt(maxSize.length-2)=="k") {
					maxSize = maxSize.substring(0,maxSize.length-2);
					result = parseInt(maxSize)*1000;
				}else {
					maxSize = maxSize.substring(0,maxSize.length-1);
					result = parseInt(maxSize);
				}
			}else result = parseInt(maxSize);			
		}
		return result;
	};
	/***************************************
	功能：设置单个附件大小控制
	参数：maxSize 大小
	****************************************/
	this.setSingleMaxSize = function(maxSize){
		self.singleMaxSizeTxt = maxSize;
		self.singleMaxSize = self.parseSize(maxSize);
	};
	/*****************************************
	功能：设置总体附件大小控制
	参数：maxSize 大小
	******************************************/
	this.setTotalMaxSize = function(maxSize){
		self.totalMaxSizeTxt = maxSize;
		self.totalMaxSize = self.parseSize(maxSize);
	}; 	
	/*****************************************
	 * 功能：设置普通附件大小
	 * 参数：size 大小 单位兆
	 ****************************************/
	this.setSmallMaxSizeLimit = function(size) {
		self.smallMaxSizeLimit = size;
	};

	this.setFileLimitType = function(limitType){
		if(limitType){
			self.fileLimitType = limitType;
		}
	};
	this.setDisableFileType = function(disableType){
		if(disableType!=null){
			self.disabledFileType = disableType.toLowerCase();
		}
	};
	/*****************************************
	功能：重新计算权限信息
	*****************************************/
	this.recalcRightInfo = function(){
		if(self.forceDisabledOpt!=null && self.forceDisabledOpt){
			var features = self.forceDisabledOpt.split(";");
			for ( var i = 0; i < features.length; i++) {
				var tmpFeature = features[i];
				tmpFeature = "can" + tmpFeature.substring(0,1).toUpperCase() + tmpFeature.substring(1);
				self[tmpFeature] = false;
			}
		}
	};
	
	this.beforeShow= function() {
		setTimeout(function(){
			var winObj;
			var top = Com_Parameter.top || window.top;
			try {
				if(top.window){
					winObj = top.window;
				}
			} catch (e) {
				winObj = window;
			}
			if(winObj.previewEvn && self.addToPreview && (self.editMode != "edit" || self.uploadAfterSelect==true)){
				winObj.previewEvn.emit("beforeShow",self);
				winObj.previewEvn.on("previewDelete",function(data){
					var SwfAttObj= winObj.Attachment_ObjectInfo[data.fdKey];
					var delFile = data.file;
					delFile.fileStatus = -1;
					$("#"+delFile.fdId).remove();
					SwfAttObj.delFileList(delFile.fdId);
					SwfAttObj.emit('editDelete',{"file":data.file});
				});
			}
		},100);
	};
	
	/*****************************************
	功能：显示附件内容方法
	参数：显示位置Dom元素ID
	*****************************************/
	this.show = function() {
		if(self.showed)
			return;
		if(self.recalcRightInfo){
			self.recalcRightInfo();
		}
		if(!self.btnIntial){
			self.showButton(function(){
				if(self.drawFunction != null){
					self.drawFunction(self);
				}else{
					self.emit("buildRender",self);
					var renderExe=function(){
						try{
							//#164400 业务关联多行数据回填明细表，附件可能重复显示，需在此处再次判断是否已显示，已显示则返回
							if(self.showed){
								return;
							}
							if(self.renderFn){
								self.renderFn.apply(self, [$, function(dom) {
									var rlist = self.fdAttType!="pic"?$("#" + self.renderId).find("[data-lui-mark='attachmentlist']"):$("#" + self.renderId);
				                    if(self.fdAttType!="pic"){
				                    	  rlist.empty();
				                    }
									if(dom)
									   rlist.append(dom);
									if(self.showAfterCustom){
										self.showAfterCustom();
									}
									self.showed = true;
									self.resizeAllUploader();
								},self.dialogAlert]);
							}
						 }catch(e){ 
						 	if(window.console)window.console.error(e.stack);
						 }
						// 构建图片预览
						if (self.fdLayoutType === "pic" || self.fdLayoutType == "/sys/attachment/view/layout_ocr.tmpl") {
							self.createImageView($("#" + self.renderId));
						} else {
							self.createImageView($("#" + self.renderId).find("[data-lui-mark='attachmentlist']"));
						}
					};
					if(self.renderFn == null){
						//页面引入了jquery，不知道什么原因经常会报$找不到，暂时加一点点延迟
						setTimeout(function(){
							var result;
							if(window._loadAttArray && window._loadAttArray["render_"+self.fdViewType]!=null){
								result = window._loadAttArray["render_"+self.fdViewType];
							}else{
								$.ajax({
									type:"GET",
									url:self.renderurl,
									dataType:'text',
									async:false,
									success:function(data){
										if(!window._loadAttArray){
											window._loadAttArray=[];
											window._loadAttArray["render_"+self.fdViewType] = data;
											result = data;
										}else{
											window._loadAttArray["render_"+self.fdViewType] = data;
											result = data;
										}
									},
									error:function(res){
										if(window.console)window.console.error(errorInfo);
									}
								});
							}

							self.renderFn = new Function('$','done','alert',result);
							renderExe();
							self.sortable();
							self.resizeAllUploader();
							resizeWidth();
						},800);
					 }else{
						 renderExe();
					 }
				}
			});
		}
	};

	function resizeWidth() {
		if(Com_Parameter['AttrMains']) {
			for(var key in Com_Parameter['AttrMains']) {
				Com_Parameter['AttrMains'][key].resizeAllUploader2();
			}
		}
	}

    this.submitValidate = function() {
        if(self.disabled){
            return true;
        }
        var upOk = true;
        upOk = self.checkRequired();
        if(!upOk){		//校验必填
            if(self.fdAttType == "pic"){//单图片上传为空提示语
                self.dialogAlert(Attachment_MessageInfo["msg.singleNull"]);
            }else{
                self.dialogAlert(Attachment_MessageInfo["msg.null"]);
            }
            return upOk;
        }
        upOk = self.isUploaded();
        if(!upOk){      //校验是否上传中
            self.dialogAlert(Attachment_MessageInfo["msg.uploading"]);
        }
        return upOk;
    }

    /*****************************************
	 * 功能：编辑模式的时候更新隐藏域里面的附件Id和删除的附件ID
	 ******************************************/
	this.updateInput = function() {
		var attIds = document.getElementsByName("attachmentForms." + self.fdKey + ".attachmentIds");
		if (attIds && attIds.length > 0) {
			var tempIds = "";
			var tempDelIds = "";
			for ( var i = 0; i < self.fileList.length; i++) {
				var doc = self.fileList[i];
				if (doc.fileStatus == -1) {
					if(doc.fdId.indexOf("WU_FILE") >=0){
					}else{
						tempDelIds += ";" + doc.fdId;
					}
				} else if(doc.fileStatus == 1) {
					if((self.fdAttType!="pic" || self.uploadAfterSelect==false )&& doc.fdId.indexOf("WU_FILE") >=0){
						doc.fdId = self.createAttMainInfo(doc);
					}
					tempIds += ";" + doc.fdId;
					if(doc.fdId=='' || doc.fdId==null){
						self.dialogAlert(doc.fileName + Attachment_MessageInfo["msg.uploadFail"]);
						return false;
					}
				}
			}
			if (tempIds.length > 0) {
				if(self.onFinishPostCustom){
					self.onFinishPostCustom(self.fileList);
				} 
				tempIds = tempIds.substring(1, tempIds.length);
			}
			if (tempDelIds.length > 0) {
				tempDelIds = tempDelIds.substring(1, tempDelIds.length);
			}
			attIds[0].value = tempIds;
			document.getElementsByName("attachmentForms." + self.fdKey + ".deletedAttachmentIds")[0].value = tempDelIds;
			return true;
		}
		return true;
	};
	/*****************************************
	 * 功能：添加附件信息方法
	 * 参数：
	 *_fileName 文件名称
	 *_fileType 文件类型
     *_fileKey 附件中对应文件ID
	 *_fdId 附件fdId
	 *_fileSize 文件大小
	 *_isUpload 是否已上传   存到文件状态fileStatus中  1表示正常状态，-1表示删除, -2错误  0表示未上传
	 *localFile 暂未使用，本地文件路径，可以在选择文件后IE时临时显示图片用
	 ******************************************/
	this.addDoc = function(_fileName, _fdId, _isUpload, _fileType, _fileSize, _fileKey, _fileDownloadCount, _showDeleteIcon,_hideReplace) {
		
		var att = {};
		
		/*
		 * 在新建页面有可能是模板带过来的附件，这个时候附件id不能相同，新建的时候附件id是在提交的时候由后台生成，
		 * 这里以WU_FILE开头来标识是新上传的，所以由模板带过来的附件id也必须以WU_FILE开头，但是阅读和下载必须要有附件原始id，
		 * 所以在这里定个规则，由模板带过来的附件id用“|”隔开，“|”后面的就是附件原始id，使用fdTemplateAttId来存储，用来点击阅读、下载等针对附件的操作，
		 * “|”前面就是以WU_FILE开头的模拟id,标识是新上传的
		 * （暂时只有文档知识库使用）
		 */
		att.fdTemplateAttId = "";
		att.fdIsCreateAtt = true;    //用于在移动附件的时候判断是否未生成sysAttMain对象（最初的uploadAfterSelect是否为false）
		var tids = _fdId.split("|");
		if(tids && tids.length == 2) {
			att.fdTemplateAttId = tids[1];
			_fdId = tids[0];
		}
			
		att.fdId = _fdId;
		att._fdId = _fdId;			//记录界面附件的原始id
		att.id = _fdId;				//兼容老的处理逻辑
		att.fileIsNew = false;
		if(_isUpload) {   			//大附件使用及已上传的附件展示时使用。
			att.fileStatus = 1;
			att.fileName = _fileName;
		}else{
			var i= _fileName.lastIndexOf("\\");
			if(i>0) {
				att.fileName = _fileName.substring(i+1);
				att.localFile = _fileName;
			}else {
				att.fileName = _fileName;
				att.localFile = _fileName;			
			}
			att.fileIsNew = true;
			att.fileStatus = 0;
		}
		att.fileKey = _fileKey;
		att.fileType = _fileType;
		att.fileSize = _fileSize;
		att.fileDownloadCount = _fileDownloadCount || 0;
		// 避免影响其它地方
		att.showDeleteIcon = "undefined" == typeof _showDeleteIcon ? true : (_showDeleteIcon || false);
		if( _hideReplace != undefined ){
     		self.hideReplace = _hideReplace;		
		}
		
		self.fileList[self.fileList.length] = att;
		if(!_isUpload)
			self.emit("uploadCreate",{"file":att});
	}; 
	/*******************************************
	 * 功能：在fileList中根据docId查找文档对象
	 ******************************************/
	this.getDoc = function(docId) {
		for (var i = 0; i < self.fileList.length; i++) {
			if (self.fileList[i].fdId == docId || self.fileList[i]._fdId == docId ) {
				return self.fileList[i];
			}
		}
		return null;
	};
	/*******************************************
	 * 功能：增加支持阅读的文件后缀
	 ******************************************/
	this.appendReadFile =function(extensions) {
		if (File_EXT_READ.indexOf(extensions) == -1) {
             File_EXT_READ+=";."+extensions;
		}
	};
	
	/*******************************************
	 * 功能：增加支持打印的文件后缀
	 ******************************************/
	this.appendPrintFile =function(extensions) {
		if (File_EXT_PRINT.indexOf(extensions) == -1) {
			File_EXT_PRINT+=";."+extensions;
		}
	};
	/**
	 * 校验附件编辑权限
	 */
	this.checkEditAuth = function(docId){
		var checkUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=checkEditName";
		var flag = false;
		$.ajax({
			url: checkUrl,
			type: 'POST',
			data:{id:docId},
			dataType: 'json',
			async:false, 
			success: function(data){
				flag = data.auth;
			}
	   });
		return flag;
	};
	/****************************************
	 * 功能：重置支持阅读的文件后缀
	 ***************************************/
	this.resetReadFile =function(extensions) {
		File_EXT_READ = extensions;
	};
	
	/**
	 * 检查wps加载项打开
	 */
	this.checkOAassistView = function(docId){
		//如果是公文不判断是否WPS加载项打开，因为可能使用的是强制使用加载项。
		if(self.wpsExtAppModel == "kmImissive"){
		  return true;
		}
		var checkUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=checkOAassistView";
		var flag = false;
		$.ajax({
			url: checkUrl,
			type: 'POST',
			data:{id:docId},
			dataType: 'json',
			async:false, 
			success: function(data){
				flag = data.flag;
			}
	   });
		return flag;
	};

	this.checkUseReadOLConfig = function(docId){
		var checkUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=checkUseReadOLConfig&fdId=" + docId;
    		var flag = false;
    		$.ajax({
    			url: checkUrl,
    			type: 'POST',
    			async:false,
    			success: function(data){
    				console.log("###################################");
    				console.log(data);
    				flag = data.flag;
    			}
    	   });
    		return flag;
    	};

	/****************************************
	 * 查询是否勾选在线预览强制使用 金格 or 加载项
	 ***************************************/
	this.getUseWpsLinuxView = function(){
		var checkUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=getUseWpsLinuxView";
		var flag = false;
		$.ajax({
			url: checkUrl,
			type: 'POST',
			dataType: 'json',
			async:false,
			success: function(data){
				flag = data.flag;
			}
	   });
		return flag;
	};

	/**
	 * @desc 判断当前系统类型
	 * @returns { bool } true代表windows系统，false代表linux系统
	 */
	this.isWindowsPlatform = function(){
		var platform;

		platform = navigator.platform;
		if (platform == 'Win32' || platform == "Windows") {
			return true;
		} else {
			return false;
		}
	};

	/****************************************
	 * 阅读操作
	 ***************************************/
	this.readDoc = function(docId) {
		var doc = self.getDoc(docId);
        var url = self.getUrl("view", docId);
		if(self.fdForceUseJG){
			url += "&fdForceUseJG=true"
		}
		//点聚阅读参数
		url += "&dj_ifr=true";
		//启用在线阅读配置
        if(this.checkUseReadOLConfig(docId)){
            Com_OpenWindow(url, "_blank");
            return;
        }

		var isUseWpsoaassit=false;
		if(this.isWindowsPlatform())
			isUseWpsoaassit=true;
		if(this.wpsoaassistEmbed=="true"&&this.isWindowsPlatform()){
			isUseWpsoaassit=true;
		}
		if(this.wpsoaassistEmbed=="false") {
			isUseWpsoaassit = true;
		}

		if(this.wpsoaassist=="true"&&this.checkOAassistView(docId)&&!this.getUseWpsLinuxView()&&isUseWpsoaassit){
			var fileName = doc.fileName;
			var fileExt = fileName.substring(fileName.lastIndexOf("."));
			if(fileExt.toLowerCase()==".docx"||fileExt.toLowerCase()==".doc"||fileExt.toLowerCase()==".wps"){
				var wpsParam = {};
				wpsParam['signTrue']=this.signTrue;
				wpsParam['bookMarks']=this.bookMarks;
				wpsParam['wpsExtAppModel']=this.wpsExtAppModel;
				openWpsOAAssit(docId,wpsParam);
				return;
			}
			if(fileExt==".xlsx"||fileExt==".xls"){
				openExcelOAAssit(docId);
				return;
			}
			if(fileExt.toLowerCase()==".et"||fileExt==".XLSX"||fileExt==".XLS"){
				openEtOAAssit(docId);
				return;
			}
			/*if(fileExt==".pptx"||fileExt==".ppt"||fileExt==".dps"){
				openPptOAAssit(docId);
				return;
			}*/
			
		}

		Com_OpenWindow(url, "_blank");
	};
	/****************************************
	 * 播放操作 kms特有，不通用
	 ***************************************/
	this.startVideo = function(docId) {
	
		var url = self.getUrl("view", docId);
		Com_OpenWindow(url, "_blank");
		
	};
	/****************************************
	 * 播放操作 kms特有，不通用
	 ***************************************/
	this.startMp3 = function(docId) {
		var url =Com_Parameter.ContextPath+"sys/attachment/viewer/audio_mp3.jsp";
			url = Com_SetUrlParameter(url,"attId",docId);

		    Com_OpenWindow(url, "_blank");
	};
	
	/****************************************
	 * 编辑操作
	 ***************************************/
	this.editDoc = function(docId) {
		var doc = self.getDoc(docId);
		var isUseWpsoaassit=false;
		if(this.isWindowsPlatform())
			isUseWpsoaassit=true;
		if(this.wpsoaassistEmbed=="true"&&this.isWindowsPlatform()){
			isUseWpsoaassit=true;
		}
		if(this.wpsoaassistEmbed=="false") {
			isUseWpsoaassit = true;
		}
		if(this.wpsoaassist=="true"&& isUseWpsoaassit ){
			var fileName = doc.fileName;
			var fileExt = fileName.substring(fileName.lastIndexOf("."));
			if(fileExt==".docx"||fileExt==".doc"){
				var wpsParam = {};
				var books = "";
				if(this.wpsExtAppModel == "kmImissive" && self.fdModelName.indexOf("Template") == "-1"){
					books = addBookMarksByWpsAddIn(this.bookMarks);
					wpsParam['bookMarks']=books;
				}
				
				if( this.wpsExtAppModel == "kmSmissive")
				{
					books = this.bookMarks;
				}
			
				wpsParam['redhead']=this.redhead;
				wpsParam['bookMarks']=books;
				wpsParam['nodevalue']=this.nodevalue;
				wpsParam['signTrue']=this.signTrue;
				wpsParam['wpsExtAppModel']=this.wpsExtAppModel;
				wpsParam['cleardraft']=this.cleardraft;
				wpsParam['newFlag']=this.newFlag;
				wpsParam['forceRevisions']=this.forceRevisions;
				wpsParam['saveRevisions']=this.saveRevisions;
				editWpsOAAssit(docId,wpsParam);
				return;
			}
			if(fileExt==".WPS"||fileExt==".wps"||fileExt==".DOCX"||fileExt==".DOC"){
				var wpsParam = {};
				if(this.wpsExtAppModel == "kmImissive"  && self.fdModelName.indexOf("Template") == "-1"){
					var books = addBookMarksByWpsAddIn(this.bookMarks);
					wpsParam['bookMarks']=books;
				}
				wpsParam['redhead']=this.redhead;
				wpsParam['bookMarks']=books;
				wpsParam['nodevalue']=this.nodevalue;
				wpsParam['signTrue']=this.signTrue;
				wpsParam['wpsExtAppModel']=this.wpsExtAppModel;
				wpsParam['cleardraft']=this.cleardraft;
				wpsParam['newFlag']=this.newFlag;
				wpsParam['forceRevisions']=this.forceRevisions;
				wpsParam['saveRevisions']=this.saveRevisions;
				editDaXieWPSOAAssit(docId,wpsParam);
				return;
			}
			if(fileExt.toLowerCase()==".xlsx"||fileExt.toLowerCase()==".xls"||fileExt.toLowerCase()==".et"){
				editExcelOAAssit(docId);
				return;
			}
			/*if(fileExt==".pptx"||fileExt==".ppt"||fileExt==".dps"){
				editPptOAAssit(docId);
				return;
			}*/
			
		}
		
		var url = self.getUrl("edit", docId);
		if(self.fdForceUseJG){
			url += "&fdForceUseJG=true"
		}
		Com_OpenWindow(url, "_blank");
	};
	/****************************************
	 * 金格PDF签章操作
	 ***************************************/
	this.jgSignaturePDF = function(docId) {
	
		var url = self.getUrl("edit", docId);
		url += "&isJGSignaturePDF=true"
		Com_OpenWindow(url, "_blank");
		
	};

	/****************************************
	 * OFD签章操作
	 ***************************************/
	this.signatureOFD = function(docId) {

		var url = self.getUrl("edit", docId);
		//url += "&isJGSignaturePDF=true"
		Com_OpenWindow(url, "_blank");

	};
	/****************************************
	 * 打印操作
	 ***************************************/
	this.printDoc = function(docId) {

		var isUseWpsoaassit=false;
		if(this.isWindowsPlatform())
			isUseWpsoaassit=true;
		if(this.wpsoaassistEmbed=="true"&&this.isWindowsPlatform()){
			isUseWpsoaassit=true;
		}
		if(this.wpsoaassistEmbed=="false") {
			isUseWpsoaassit = true;
		}
		if(this.readOnlyFoxit) {
			isUseWpsoaassit = false;
		}
		if(this.wpsoaassist=="true"&&isUseWpsoaassit){
			var doc = self.getDoc(docId);
			var fileName = doc.fileName;
			var fileExt = fileName.substring(fileName.lastIndexOf("."));
			if(fileExt.toLowerCase()==".docx"||fileExt.toLowerCase()==".doc"||fileExt.toLowerCase()==".wps"){
				var wpsParam = {};
				wpsParam['signTrue']=this.signTrue;
				wpsParam['bookMarks']=this.bookMarks;
				wpsParam['wpsExtAppModel']=this.wpsExtAppModel;
				openWpsOAAssit(docId,wpsParam);
				return;
			}
			if(fileExt==".xlsx"||fileExt==".xls"){
				openExcelOAAssit(docId);
				return;
			}
			if(fileExt==".XLSX"||fileExt==".XLS" || fileExt.toLowerCase()==".et"){
				openEtOAAssit(docId);
				return;
			}
			if(fileExt.toLowerCase()==".pptx"||fileExt.toLowerCase()==".ppt"||fileExt.toLowerCase()==".dps"){
				openPptOAAssit(docId);
				return;
			}
			
		}
		
		var url = self.getUrl("print", docId);
		Com_OpenWindow(url, "_blank");
	};
	/****************************************
	 * 下载操作
	 ***************************************/
	this.downDoc = function (docId) {
		var url = self.getUrl("download", docId);
		url += "&downloadType=manual&downloadFlag=" + (new Date()).getTime();  //记录下载日志标识

		//钉钉套件已开，且当前是从钉钉浏览器中打开
		if (Com_Parameter.dingXForm == "true") {
			var iframe = $("#downfile_iframe");
			if(iframe.length == 0) {
				iframe = $("<iframe id='downfile_iframe' style='display:none;height:0'></iframe>");
				$(document.body).append(iframe);
			}
			iframe.attr("src" , url);
		} else {
			window.open(url, "_blank");
		}
	};
	/****************************************
	 *删除操作
	 ***************************************/
	this.delDoc = function(docId,_refresh) {
		for (i = 0; i < self.fileList.length; i++) {
			if (self.fileList[i].fdId == docId) {
				self.fileList[i].fileStatus = -1;
			}
		}
		//回调外部删除函数
		if(this.uploadFileDeleteEvent){
			this.uploadFileDeleteEvent(docId,self);
		}
		if(_refresh == null){
			self.show();
		}else if(_refresh){
			self.show();
		}
	};
	/****************************************
	 * 打开操作
	 ***************************************/
	this.openDoc = function(docId) {
		var url = self.getUrl("readDownload", docId);
		url = Com_SetUrlParameter(url,"open","1");
		Com_OpenWindow(url, "_blank");
	};	
	
	/****************************************
	 * 向上移动附件
	 ***************************************/
	this.moveUpDoc = function(docId) {
		var arrayObj = [];
		var moveIndex = 0;
		for (var i = 0; i < this.fileList.length; i++){
			if(this.fileList[i].fdId == docId)
				moveIndex = i;
		}
		
		var j = this.getDelfiles(); //获取状态为-1的文件数后，已此作为临界点进行向上移动操作
		
		var attIds = "";
		
		for(var i = 0; i<j ; i++){
			arrayObj.push(this.fileList[i]);
		}
		
		if(moveIndex!=j){
			for (var i = j; i < this.fileList.length; i++){
				
				if(i==(moveIndex-1)){
					continue;
				}
				
				if(i==moveIndex){
					arrayObj.push(this.fileList[i]);
					arrayObj.push(this.fileList[moveIndex-1]);
					if(attIds == ""){
						attIds += this.fileList[i].fdId + ";" + this.fileList[moveIndex-1].fdId;
					}else{
						attIds +=";"+ this.fileList[i].fdId + ";" + this.fileList[moveIndex-1].fdId;
					}
				}else{
					arrayObj.push(this.fileList[i]);
					if(attIds == ""){
						attIds += this.fileList[i].fdId;
					}else{
						attIds +=";"+ this.fileList[i].fdId;
					}
				}
			}
			
			document.getElementsByName("attachmentForms." + self.fdKey + ".attachmentIds")[0].value = attIds;
			self.showed = false;
			self.btnIntial = false;
			this.fileList = arrayObj;
			this.initMode = false;
			this.show();

			// 调整样式
			self.resizeAllUploader2();
		}
	};
	
	/****************************************
	 * 向下移动附件
	 ***************************************/
	this.moveDownDoc = function(docId) {
		var arrayObj = [];
		var moveIndex = this.fileList.length;
		for (var i = 0; i < this.fileList.length; i++){
			if(this.fileList[i].fdId == docId){
				moveIndex = i;
			}
		}
		var attIds = "";
		if(this.fileList.length>0 && moveIndex!=(this.fileList.length-1)){
			
			for (var i = 0; i < this.fileList.length; i++){
				
				if(i==moveIndex || i==(moveIndex+1)){
				}else{
					arrayObj.push(this.fileList[i]);
					if(attIds == ""){
						attIds += this.fileList[i].fdId;
					}else{
						attIds +=";"+ this.fileList[i].fdId;
					}
				}	
				
				if(i==(moveIndex+1)){
					arrayObj.push(this.fileList[moveIndex+1]);
					arrayObj.push(this.fileList[moveIndex]);
					if(attIds == ""){
						attIds += this.fileList[moveIndex+1].fdId + ";" + this.fileList[moveIndex].fdId;
					}else{
						attIds +=";"+ this.fileList[moveIndex+1].fdId + ";" + this.fileList[moveIndex].fdId;
					}
					
				}
			}
			
			document.getElementsByName("attachmentForms." + self.fdKey + ".attachmentIds")[0].value = attIds;
			self.showed = false;
			self.btnIntial = false;
			this.fileList = arrayObj;
			this.initMode = false;
			this.show();

			// 调整样式
			self.resizeAllUploader2();
		}
	};
	
	/****************************************
	 * 删除附件后的列表(把附件的状态置为-1，并且发到列表最前列，为了后续的移动操作方便进行)
	 ***************************************/
	this.delFileList = function(docId) {
		var arrayObj = [];
		var obj = null;
		for (var i = 0; i < this.fileList.length; i++){
			if(this.fileList[i].fdId == docId){
				this.fileList[i].fileStatus = -1;
				this.fileList[i].isDelete = -1;
				obj = this.fileList[i];
				//arrayObj.push(this.fileList[i]);
				break;
			}
		}
		
		if(obj!=null)
			arrayObj.push(obj);
		
		for (var i = 0; i < this.fileList.length; i++){
			if(this.fileList[i].fdId != docId){
				arrayObj.push(this.fileList[i]);
			}
		}
		
		self.showed = false;
		self.btnIntial = false;
		this.fileList = arrayObj;
		this.initMode = false;
		//回调外部删除函数
		if(this.uploadFileDeleteEvent){
			this.uploadFileDeleteEvent(docId,self);
		}
		this.show();		
	};	
	
	/****************************************
	 * 获取附件的状态为-1的文件个数
	 ***************************************/
	this.getDelfiles = function(){
		var j = 0;
		for (var i = 0; i < this.fileList.length; i++){
			if(this.fileList[i].fileStatus == -1)
				j++;
		}		
		
		return j;
	}
	
	/****************************************
	 * 全部下载
	 ***************************************/
	this.downloadFiiles = function(isAll){
		var docIds = "";
//		for (var i=0;i<this.fileList.length;i++){
//			docIds += ";" + this.fileList[i].fdId;
//		}
		
		var isSpecial = false;
		
		for (var i=0;i<this.fileList.length;i++){
			var fileDom = document.getElementsByName("List_File_Selected_"+this.fileList[i].fdId);
			var check = document.getElementsByName("List_File_Selected_"+this.fileList[i].fdId)[0];
			if(fileDom.length>1 && $ ){// 判断是否存在多个附件，如果存在多个，则通过当前父节点的ID去匹配到具体的子节点数据
				var dom = document.getElementById("att_xtable_"+this.fdKey);//获取父对象。
				check = $(dom).find("[name='List_File_Selected_"+this.fileList[i].fdId+"']")[0];
			}
			if(isAll) { // 下载全部，不管是否勾选
				docIds += ";" + check.value;
			} else {
				if(typeof(check) == "undefined"){
					isSpecial = true;
					docIds = "";
					break;
				}
				
				if(true==check.checked)
					docIds += ";" + check.value;
			}
		}
		
		//兼容附件特殊页面，走原来的逻辑(文档知识库)
		if(isSpecial){
			for (var i=0;i<this.fileList.length;i++){
				docIds += ";" + this.fileList[i].fdId;
			}
		}

		if(docIds.length <= 0){
			self.dialogAlert(Attachment_MessageInfo["msg.noChoice"]);
		}else{
			docIds = docIds.substring(1, docIds.length);
			var url = self.getUrl("download", docIds);
			url += "&downloadType=manual&downloadFlag="+(new Date()).getTime(); //记录下载日志标识
			if(url.length<2000){
				if (Com_Parameter.dingXForm === "true") {
					location.href = url;
				} else {
					window.open(url, "_blank");
				}
			}else{	//url超长处理
				self.postOpen(url.substring(0,url.indexOf("?")),url.substring(url.indexOf("?")+1));
			}
		}
	};
	
	//选取所有附件
	this.checkAll = function(){
		var obr = this.fileList;
		var tongle = document.getElementsByName("List_File_Tongle_"+this.fdKey)[0];
		var dom = document.getElementById("att_xtable_"+this.fdKey);//获取到这个唯一值id
		for(var i = 0; i < obr.length; i++){
			var check = document.getElementsByName("List_File_Selected_"+obr[i].fdId)[0];
			var selectDom = document.getElementsByName("List_File_Selected_"+obr[i].fdId);
			if($ && selectDom && selectDom.length>1){//判断是否存在多个的情况，并且能够使用jq
				 check = $(dom).find("[name='List_File_Selected_"+obr[i].fdId+"']")[0];//根据唯一id去查找下面的dom对象。
			}
			if(true==tongle.checked){
				check.checked = true;
			}else{
				check.checked = false;
			}
		}
	};
	
	//选取附件联动
	this.checkSelected = function(){
		var obr = this.fileList;
		
		var isAll = true;
		
		var tongle = document.getElementsByName("List_File_Tongle_"+this.fdKey)[0];
		for(var i = 0; i < obr.length; i++){
			var check = document.getElementsByName("List_File_Selected_"+obr[i].fdId)[0];
			if(false == check.checked){
				isAll = false;
				break;
			}
		}
		
		if(isAll == true){
			tongle.checked = true;
		}else{
			tongle.checked = false;
		}
	};
	
	this.postOpen = function(url, param){
		var form = null;
		var params= {};
		try{
			var tmpParams = param.split("&");
			for ( var i = 0; i < tmpParams.length; i++) {
				var tIndex = tmpParams[i].indexOf("=");
				if(tIndex>-1){
					params[tmpParams[i].substring(0,tIndex)] = tmpParams[i].substring(tIndex+1);
				}
			}
			form = $("<form></form>").attr({'action':url,'method':'POST','target':'_top'});
			for ( var key in params) {
				form.append($("<input type='hidden' name='" + key +"'/>").val(params[key]));
			}
			form.css({'display':'none'});
			form.appendTo($(document.body));
			form.submit();
			form.remove();
		}catch(e){
			if(form!=null){
				form.remove();
			}
		}
	};
	/****************************************
	 * 获取域名
	 ***************************************/
	this.getHost = function(){
		var host = location.protocol.toLowerCase()+"//" + location.hostname;
		if(location.port!='' && location.port!='80'){
			host = host+ ":" + location.port;
		}
		return host;
	}
	
	/****************************************
	 * 获取上传路径
	 ***************************************/
	this.getUploadUrl = function() {
		if(self.uploadurl.substring(0, 1) == '/') {
			var url = self.uploadurl.substring(1, self.uploadurl.length);
			return (self.getHost() + Com_Parameter.ContextPath + url);
		}
		return self.uploadurl;
	}
	
	/****************************************
	 * 功能函数，获取URL地址，传入参数method和文档fdId
	 ***************************************/
	this.getUrl = function(method, docId) {
		var url = Com_Parameter.ContextPath;
		if(url.substring(0,4) !== 'http') {
			url = self.getHost() + url;
		}
		url += "sys/attachment/sys_att_main/sysAttMain.do?method=" + method
		+ "&fdId=" + docId +"&useBrowserOpen=" + self.browserType();
		return url;
	};

	/**
	 * #161059 PDF附件可以支持使用谷歌、火狐、Edge直接打开查看（先只开放给EIS的中小客户使用）
	 * @returns {string}
	 */
	this.browserType = function() {
		if(navigator.userAgent.indexOf("Firefox") != -1 || navigator.userAgent.indexOf("Chrome") != -1 || navigator.userAgent.indexOf("Edge") != -1) {
			return "true";
		}

		return "false";
	}
	/****************************************
	 * 功能函数，格式化文件大小的显示，以MB，B，G等单位结尾
	 ***************************************/
	this.formatSize = function(filesize) {
		var result = "";
		var index;
		filesize = String(filesize);
		if (filesize != null && filesize != "") {
			if ((index = filesize.indexOf("E")) > 0) {
				var size = parseFloat(filesize.substring(0, index))
						* Math.pow(10, parseInt(filesize.substring(index + 1)));
			} else
				var size = parseInt(filesize);
			if (size < 1024)
				result = size + "B";
			else {
				var size = Math.round(size * 100 / 1024) / 100;
				if (size < 1024)
					result = size + "KB";
				else {
					var size = Math.round(size * 100 / 1024) / 100;
					if (size < 1024)
						result = size + "M";
					else {
						var size = Math.round(size * 100 / 1024) / 100;
						result = size + "G";
					}
				}
			}
		}
		return result;
	};
	/*****************************************
	 * 创建sysattmain对象
	 *****************************************/
	this.createAttMainInfo = function(doc){
		var docId = null;
		var fileName = encodeURIComponent(doc.fileName);
		var xdata = "filekey=" + doc.fileKey + "&filename=" + fileName + "&fdKey=" + self.fdKey
				+ "&fdModelName=" + self.fdModelName + "&fdAttType=" + self.fdAttType 
				+ "&width="+ self.width + "&height="+ self.height + "&proportion=" + self.proportion;
		//如果是上传附件类型为非图片类型或是上传后需要马上生成附件文档，则将关联fdModelId加上
		//否则不应该立刻关联fdModelId，而是要等到整主文档提交或是暂存之后关联fdModelId
//		if (self.fdAttType!='pic' || this.uploadAfterSelect == true){ 
//			xdata = xdata + "&fdModelId=" + self.fdModelId;
//		}
		
		var fdSign = BASE64.encoder(doc.fileName);
		fdSign = fdSign.replace(/\+/g,"");
		fdSign = fdSign.replace(/\//g,"");
		fdSign = fdSign.replace(/\=/g,"");
		
		xdata = xdata + "&fdSign=" + fdSign;

		var errorMsg = "";
		$.ajax(self.attachurl + '&' + xdata,{dataType:'json',type:'GET',async:false,success:function(res){
			doc.fdId = res.attid;
			docId = res.attid;
			errorMsg = res.msg;
		},error:function(xhr, status, errorInfo){
			docId = null;
			if(window.console)window.console.error(errorInfo);
			errorMsg = errorInfo;
		}});
		doc.fdIsCreateAtt = true;

		if(docId=='' || docId==null){
			doc.fileStatus = 0;
			try{
				self.emit("uploadFaied",{"serverData":errorMsg,"file":doc});
			}catch(e){}
		}

		return docId;
	};
	/*****************************************
	 * 删除sysattmain对象
	 *****************************************/
	this.deleteAttMainInfo = function(doc){
		var delResult = 0;
		var xdata = "filekey=" + doc.fileKey + "&fdKey=" + self.fdKey + "&fdId=" + doc.fdId
				+ "&fdModelName=" + self.fdModelName + "&fdModelId=" + self.fdModelId + "&fdAttType=" + self.fdAttType;
		$.ajax(self.deleteUrl + '&' + xdata,{dataType:'json',type:'GET',async:false,success:function(res){
			if (res.status == 1) {
				for (i = 0; i < self.fileList.length; i++) {
					if (self.fileList[i].fdId == doc.fdId) {
						// 删除成功后，会从fileList中将其剔除，在外部遍历fileList且有调用本方法时请注意调整循环变量，避免遍历不完整
						self.fileList.splice(i,1);
						delResult = 1;
						break;
					}
				}
			}
		},error:function(xhr, status, errorInfo){
			delResult = 0;
			if(window.console)window.console.error(errorInfo);
		}});
		return delResult;
	};
	/*****************************************
	 * 上传前的校验
	 *****************************************/
	
	this.beforeUpload = function(file){
		var availFileList = [];

		for(var i=0; i<self.fileList.length; i++) {
			if(self.fileList[i].isDelete != -1) {
				availFileList.push(self.fileList[i]);
			}
		}

		if(availFileList.length>=self.fileNumLimit){//校验附件上传最大数量
			self.dialogAlert(Attachment_MessageInfo["error.exceedNumber"].replace("{0}", self.fileNumLimit+"个"));
			return false;
		}

		var _fileName = file.name;
		if (/\n/g.test(_fileName)) {
			seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
				dialog.alert(Attachment_MessageInfo["msg.fileName.error"]);
			});
			return false;
		}
		if(file.size==0){
			seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
				dialog.alert(Attachment_MessageInfo["msg.fileSize.null"]);
		    });
			return false;
		}

		// 增加文件名长度校验
		if (this._fileNameMax){
			var _checkFileName = _fileName;
			if(_fileName.lastIndexOf(".")>-1){
				_checkFileName = _fileName.substring(0, _fileName.lastIndexOf("."));
			}
			var newvalue = _checkFileName.replace(/[^\x00-\xff]/g, "***");
			if (newvalue.length > this._fileNameMax){
				var _msg_fileNameMax_num = this._fileNameMax;
				seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
					dialog.alert(Attachment_MessageInfo["msg.fileNameMax"].replace("{0}",_msg_fileNameMax_num).replace("{1}",_fileName));
				});
				return false;
			}
		}



		var fileType = null;
		if(_fileName.lastIndexOf(".")>-1){			
			fileType = _fileName.substring(_fileName.lastIndexOf("."));
			fileType = fileType.toLowerCase();
			var fileTypes= [];
			fileTypes = self.disabledFileType.split(';');
			if("1"==self.fileLimitType){
				
				var isPass = true;
				
				for (i=0;i<fileTypes.length ;i++ )
				{
					if(fileType == fileTypes[i]){
						isPass = false;
						break;
					}
				}
				
				if(!isPass){
					seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
						dialog.alert(Attachment_MessageInfo["msg.disableFileType"].replace("{0}",self.disabledFileType).replace("{1}",_fileName));
				    });
					return false;
				}
				
//				if(self.disabledFileType.indexOf(fileType)>-1){
//					alert(Attachment_MessageInfo["msg.disableFileType"].replace("{0}",self.disabledFileType).replace("{1}",_fileName)); 
//					return false;
//				}
			}else if("2"==self.fileLimitType){
				var isPass = false;
				
				for (i=0;i<fileTypes.length ;i++ )
				{
					if(fileType == fileTypes[i]){
						isPass = true;
						break;
					}
				}
				
				if(!isPass){
					seajs.use(['lui/dialog', 'lui/jquery'], function(dialog, $) {
						dialog.alert(Attachment_MessageInfo["msg.disableFileType2"].replace("{0}",self.disabledFileType).replace("{1}",_fileName));
				    });
					return false;
				}
//				if(self.disabledFileType.indexOf(fileType)==-1){
//					alert(Attachment_MessageInfo["msg.disableFileType2"].replace("{0}",self.disabledFileType).replace("{1}",_fileName)); 
//					return false;
//				}
			}
		}
		for (var i = 0, size = self.fileList.length; i < size; i++) {
			if (self.fileList[i].fileStatus > -1 && self.fileList[i].fileName == _fileName) {
				//alert(Attachment_MessageInfo["msg.fail.fileName"]);
				var true_fdMulti = self.fdMulti == true; // 必须确定是多文件（没有该属性也不处理）
				var false_showDeleteIcon = self.fileList[i].showDeleteIcon == false ;//必须确定为false
				if(true_fdMulti && false_showDeleteIcon){
					alert(Attachment_MessageInfo["msg.fileExist.confirm"].replace("{0}",_fileName));
					return false;
				}
				var isConfirm ;

				if(self.fdLayoutType == "/sys/attachment/view/layout_ocr.tmpl"){ // 判断是ocr，则直接替换，不给提示
					isConfirm = true;
				} else {
					isConfirm = window.confirm(Attachment_MessageInfo["msg.fileName.confirm"].replace("{0}",_fileName));
				}
				if(isConfirm){
					if (navigator && navigator.userAgent && navigator.userAgent.indexOf("Firefox") > -1) {
						self.deleteByFirefox(_fileName);
					} else {
						//删除原有附件，上传新的附件
						var delFile = self.fileList[i];
	    				delFile.fileStatus = -1;
	    				delFile.isDelete = -1;
	    				var xdiv = $("#" + delFile.fdId); 
	    				xdiv.remove();
	    				
	    				$('.upload_list_tr').each(function(){
	    					if(this.id == delFile.fdId){
	    						this.remove();
	    					}
	    				});
						self.emit('editDelete',{"file":delFile});//删除发布事件
					}
				}else{
					return false;
				}
				//return false;
			}
		}
    	if(!self.fdMulti){
    		 var len = 0;
    		 for (i = 0; i < self.fileList.length; i++) {
    			 if (self.fileList[i].fileStatus > -1) {
    				 len++;
    			 }
    		 }
    		 if(len>0){
    			var isConfirm = true;
    			if (self.fdAttType!= 'pic' ){
    				isConfirm = window.confirm(Attachment_MessageInfo["msg.single.confirm"]);
    			}
    			if(isConfirm == true){
    				//删除原有的附件
    				var delFile = self.fileList[self.fileList.length - 1];
    				delFile.fileStatus = -1;
    				delFile.isDelete = -1;
    				var xdiv = $("#" + delFile.fdId);
    				var psize = xdiv.parent('div').find('.lui_upload_list_size_div');
    				if (psize && psize.length>0){
    					psize.remove();//移除分辨率
    				}   
    				xdiv.remove(); 	
    				$('.upload_list_tr').each(function(){
    					if(this.id == delFile.fdId){
    						this.remove();
    					}
    				});
    				self.emit('editDelete',{"file":delFile});//删除发布事件
                 }else{
                     return false; 
                 }
    		 }	
    	}
		self.addDoc(file.name, file.id ,false, '', file.size);
		return true;
	};
	
	// 火狐浏览器在删除重复文件时，需要特殊处理
	this.deleteByFirefox = function(_fileName){
		for (var i = 0, size = self.fileList.length; i < size; i++) {
			if (self.fileList[i].fileStatus > -1 && self.fileList[i].fileName == _fileName) {
				//删除原有附件，上传新的附件
				var delFile = self.fileList[i];
				delFile.fileStatus = -1;
				delFile.isDelete = -1;
				var xdiv = $("#" + delFile.fdId); 
				xdiv.remove();
				
				$('.upload_list_tr').each(function(){
					if(this.id == delFile.fdId){
						this.remove();
					}
				});
				self.emit('editDelete',{"file":delFile});//删除发布事件
			}
		}
	}
	
	/*****************************************
	 * 设置必填
	 *****************************************/
	this.requiredExec = function(value){
		
		if(value == null){
			value = true;
		}
		var requirdFlag = false;
		var span;
		var uploaderPick;
		var attEl=self.$tdDisplay?self.$tdDisplay:self.__$tdDisplay; 
		if(attEl){ 
			var isPic = false;
			uploaderPick = attEl.find("div.webuploader-pick");
			isPic = self.fdAttType && self.fdAttType == "pic" && uploaderPick.length == 0 ;
			if(isPic){
				//attEl = self.__$tdDisplay;
			   uploaderPick = attEl.find("div.webuploader-pick");
				if(uploaderPick.length ==0 ){
					if(self.__$tdDisplay){
						attEl=self.__$tdDisplay;
					}
					uploaderPick = attEl.find("div.webuploader-pick");
				}
			}
			span = attEl.find("span.txtstrong");
		} else {
			return false;
		}  
		//当前是否存在 必填*号 如果存在，先清楚，后续根据必填重新新增
		if(span && span.length > 0){
			requirdFlag = true;
			span.remove(); 
		}
		
		if(value){
			// 设置必填
			self.required = true; 
			if(uploaderPick && uploaderPick.length > 0){ 
				var uploadTitle =uploaderPick.find('span.lui_text_primary');
				if(isPic){
					uploadTitle=uploaderPick.find('div.lui_text_primary');
				}
				if(uploadTitle && uploadTitle.length > 0){
					uploadTitle.after("<span class='txtstrong'>*</span>"); 
				} 
			} 
		}else{
			// 设置非必填
			self.required = false;  
		}
		return true;
	},
	
	/*****************************************
	 * 为空校验
	 *****************************************/
	this.checkRequired = function(){
		if(!self.required){ 
			return true;//非必填不校验
		}
		if(!self.fileList || self.fileList.length==0) {
			return false;//上传队列为空，必须校验
		}
		var m=0;
		for(var i = 0; i < self.fileList.length; i++) {//上传成功的没有一个
			if(self.fileList[i].fileStatus>-1){
				m++;
				break;
			}
		}
		if(m==0) return false;
		return true;
	};
	
	this.isUploaded = function() {
	   	try{
	   		 var isUploaded = true;
	   		 for(var i=0 ;i<self.fileList.length;i++){
	   			 if(self.fileList[i].fileStatus==0){
	   				 isUploaded = false;
	   			 }
	   		 } 
	   		return  isUploaded && self.fdBigAttUploaded
	   	}catch(ex){
	   		return false;
	   	}
    };
	/*****************************************
	 * webuploader 相应函数
	 *****************************************/
	// 文件加入队列前
	this.__beforeFileQueued = function(file){
		// TODO 判断文件是否合法
		if(!self.beforeUpload(file)){
   		 	return false;
   	 	}
		var token = null;
		var errorMsg = "";
		var md5 = "";
		var fileName = encodeURIComponent(file.name);
		
		var xdata = self.buildTokenData(file.size, md5, fileName);
		
		$.ajax(self.tokenurl + '&' + xdata,{dataType:'json',type:'GET',async:false,success:function(res){
			token = res.token;
		},error:function(xhr, status, errorInfo){
			token = null;
			errorMsg = errorInfo;
			if(window.console)window.console.error(errorInfo);
		}});
		var fileInfo = self.getDoc(file.id);
		if(token!=null){
			fileInfo.token = token;
			// 直连模式 -- 记录路径
			if(self.isSupportDirect) {
				fileInfo.direct = {
					path: token.path,
					fileId: token.fileId,
					fileSize: file.size
				}
			}
			self.emit("uploadStart",{"file":fileInfo});
			return true;
		}else{
			self.emit("uploadFaied",{"file":fileInfo,"serverData":errorMsg,"_file":file});
			return false;
		}
	};
	
	// 封装获取token的数据包
	this.buildTokenData = function(size, md5, fileName) {
		
		var xdata = "filesize="+size+"&md5="+md5+"&fileName="+fileName;
		
		/**
		 * 强制走代理上传
		 * 上传路径被重写了，如果是扩展存储会导致token前后不一致，不走直连，走代理上传
		 */
		if (extendConfig.uploadurl != self.uploadConfig.server) {
			xdata += '&isSupportDirect=false';
			this.isSupportDirect = false;
		}
		
		return xdata;
		
	}
	
	// 文件加入队列
	this.__fileQueued = function(file){
	};
	
	// 文件上传之前作md5校验
	this.__beforeSendFile = function(file){
		var doc = self.getDoc(file.id);
		if(!doc) {
			return null;	
		}
		
		self.emit("uploadMD5",{"file":self.getDoc(file.id),"renderId":self.renderId});
		
		var deferred = WebUploader.Deferred();
        self.uploadObj.md5File(file).then(function (md5Value) {
        	if(doc && doc.direct){
        		doc.direct.md5 = md5Value;
        	}
    		var fileName = encodeURIComponent(file.name);
    		
    		var xdata = self.buildTokenData(file.size, md5Value, fileName);
    		
    		$.ajax(self.md5Url + '&' + xdata,{dataType:'json',type:'GET',async:true,success:function(res){
    			/**
    			 * 上传路径被重写了，如果是扩展存储会导致token前后不一致，不走直连，强制走代理上传，不校难md5
    			 */
    			if(res.md5Exists && //已有相同md5的文件存在，直接标记为已上传状态
    					extendConfig.uploadurl == self.uploadConfig.server){
        			var doc = self.getDoc(file.id);        			
        			doc.token.fileId = res.fileId;
        			doc.token.path = res.path;
        			doc.direct = {};
        			doc.direct.fileId = res.fileId;
        			doc.direct.path = res.path;
        			doc.md5Exists = true;        		
        			self.uploadObj.skipFile(file);
        			
        			self.emit("md5Exists",{"file":self.getDoc(file.id),"renderId":self.renderId});
        			deferred.resolve();
        		}else{
        			deferred.resolve();
        		}
    		},error:function(xhr, status, errorInfo){
    			if(window.console)window.console.error(errorInfo);
    			deferred.reject(errorInfo);
    		}});
        	
        });
        return deferred.promise();
	};
	
	// 文件上传服务器时前
	this.__uploadBeforeSend = function(block, data, headers){
		var fileInfo = self.getDoc(data.id);
		
		if(fileInfo.token.header) {
			// 置于头部标志
			$.extend(headers,fileInfo.token.header);	
		}
		
		if(fileInfo.token.body) { 
			// 置于内容体
			$.extend(data,fileInfo.token.body);	
		}
		
		//@TODO 秘钥传递方式，目前有三种url、postdata、header，v13采用header，不适用postdata原因是数据随附件提交，比较慢。
		//如该代码用于v13以下版本，请注意使用下面两种方式
		//1、block.transport.options.server = Com_SetUrlParameter(self.getHost() + self.uploadurl,"userkey",fileInfo.userkey);
		//2、$.extend( data,{"userkey":fileInfo.userkey});
	};
	// 文件上传中
	this.__uploadProgress = function(file, percentage){
		try{
			 self.emit("uploadProgress",{"file":self.getDoc(file.id),"totalPercent":percentage,"renderId":self.renderId}); 
		}catch(e){}
	};
	// 文件上传错误
	this.__uploadError = function(file, reason ){
		try{
			 var fileInfo = self.getDoc(file.id);
			 fileInfo.fileStatus = -2;
			 self.emit("uploadFaied",{"file":fileInfo, "serverData":reason,"_file":file}); 
		}catch(e){}
		// 调整样式
		self.resizeAllUploader2();
	};
	// 文件上传成功
	this.__uploadSuccess = function(file, resObj){
		resObj = resObj ||　{_raw : ""};
		if(resObj.status == -1) {
			try{
				var fileInfo = self.getDoc(file.id);
	    		self.emit("uploadFaied",{"file":fileInfo,"serverData":resObj.msg,"_file":file});
			 }catch(e){}
		} else {
			self.uploadSuccess(file, resObj);
		}
		// 调整样式
		self.resizeAllUploader2();
	};
	
	/*************************************
	 * 与直连相关的方法--开始
	 *************************************/
   	 	
	// 由于图片上传完后需要立马看到，所以在上传成功后需要构建sysAttMain对象
	this.buildPicAttMain = function(doc, resObj) {
			 var top = Com_Parameter.top || window.top;
	    	 if(self.fdAttType=='pic' || self.uploadAfterSelect == true){
	    		 doc.fdId = self.createAttMainInfo(doc);
	    		 if(doc.fdId=='' || doc.fdId==null){
	    			//  doc.fileStatus = 0;
	    			//  try{
    			 	// self.emit("uploadFaied",{"file":doc});
					//  }catch(e){}
	    		 }else{
	    			 doc.fileStatus = 1;
	    			 try{
	    			 	self.emit("uploadSuccess",{"file":doc,"serverData":resObj._raw,"uploadAfterSelect":self.uploadAfterSelect,"renderId":self.renderId});
	    			 	try {
							if(top.window.previewEvn){
		    			 		top.window.previewEvn.emit("uploadSuccess",{"file":doc,"swfObj":self});
		    			 	}
						} catch (e) {
							if(window.previewEvn){
								window.previewEvn.emit("uploadSuccess",{"file":doc,"swfObj":self});
							}
						}
					 }catch(e){}
	    		 }
    	 } else{
	    		 doc.fileStatus = 1;
	    		 try{
	    			 self.emit("uploadSuccess",{"file":doc,"serverData":resObj._raw,"uploadAfterSelect":self.uploadAfterSelect,"renderId":self.renderId});
    			 	try {
						if(top.window.previewEvn){
							 top.window.previewEvn.emit("uploadSuccess",{"file":doc,"swfObj":self});
	    			 	}
					} catch (e) {
						if(window.previewEvn){
							 window.previewEvn.emit("uploadSuccess",{"file":doc,"swfObj":self});
						}
					}
				 }catch(e){}
	    	 } 
		 
			 if(self.uploadAfterSelect == true && doc.fileStatus == 1){
				 if(self.uploadAfterCustom){
	 				self.uploadAfterCustom(doc.fdId);
	 			 } 
			 }
			//回调上传成功事件
			if(doc.fileStatus == 1 && self.uploadAfterSuccessEvent){
				self.uploadAfterSuccessEvent(doc.fdId,self);
			}

	}
	
	// 上传完毕回调
	this.uploadSuccess = function(file, resObj) {
		var doc = self.getDoc(file.id);
   	 	if(!doc) return;
   	 	
   	 	//上传中取消会把文档状态置为-1
   	 	if(doc.fileStatus == -1) return;
   	 	
   	 	doc.isNew = true;
	 	doc.fdIsCreateAtt = false;
   	 	
   	 	// 是否为直连模式
		if(!self.isSupportDirect && !doc.md5Exists) {
			
			if(resObj.status.trim() == "1"){
				 doc.fileKey = resObj.filekey;
		    	 self.buildPicAttMain(doc, resObj);
	     }else{
	     	 doc.fileStatus = -1; 
	    	 try{
	    		self.emit("uploadFaied",{"file":doc,"serverData":resObj.msg,"_file":file});
			 }catch(e){}
	     }	
			
			return;
		}
		
		if(doc.md5Exists || doc.token.md5Exists){//md5秒传不增加SysAttFile记录
			doc.fileKey = doc.direct.fileId;
			self.buildPicAttMain(doc, {_raw:""});
			return;
		}
		// 直连模式则先保存SysAttFile对象再渲染展现
		$.ajax(
			this.addFileUrl,
			{
				dataType: 'json',
				data: doc.direct, 
				type: 'POST'
			}
		).done(function(res){
			doc.fileKey = doc.direct.fileId;
			self.buildPicAttMain(doc, resObj);
		}).fail(function(xhr, status, errorInfo){
			doc.fileStatus = -1; 
			try{
    		 	self.emit("uploadFaied",{"file":doc,"serverData":errorInfo,"_file":file});
    	 	}catch(e){}	
		});
		
	};
	
	/*************************************
	 * 与直连相关的方法--结束
	 *************************************/
	
	
	// 错误监控
	this.__error = function(type,file,fileInfo){
		try{
			if("F_EXCEED_SIZE"==type || "Q_EXCEED_NUM_LIMIT"==type || "Q_EXCEED_SIZE_LIMIT"==type) {
				 self.emit("error",{"file":fileInfo, "serverData":type, "max":file}); 
			}
			else{
				 var fileInfo = self.getDoc(file.id);
				 fileInfo.fileStatus = -2;
				 self.emit("error",{"file":fileInfo, "serverData":type}); 
			}
		}catch(e){}
	};
	/*****************************************
	 * 默认配置
	 *****************************************/
	this.uploadConfig={
		swf: self.getHost() + Com_Parameter.ContextPath + "sys/attachment/webuploader/uploader.swf",
		server: self.getUploadUrl(),
		disableGlobalDnd: true,
		paste: null,					//document.body粘贴
		accept:{
			title:'',					//文字描述
			extensions:''				//允许的文件后缀，允许的文件后缀，不带点，多个用逗号分割。
		},
		dnd:'',
		pick:{
			id:'',
			multiple:false,
			name : self.getFileVal()    //修改组件产生的<input type="flie">的name属性，防止与表单的字段冲突
		},
		thumb:{
			width:110,
			height:110,
			quality:70,  				// 图片质量，只有type为`image/jpeg`的时候才有效
			allowMagnify: true,			// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
			crop: true,					// 是否允许裁剪
			type: 'image/jpeg'			// 为空的话则保留原有图片格式,否则强制转换成指定的类型。
		},
		compress:false,					// 不允许压缩
		/*compress:{
				width:1600,
			    height:1600,
			    quality: 90, 				// 图片质量，只有type为`image/jpeg`的时候才有效。
			    allowMagnify: false,		// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
			    crop: false,				// 是否允许裁剪。
			    preserveHeaders: true,		// 是否保留头部meta信息。
			    noCompressIfLarger: false,	// 如果发现压缩后文件大小比原来还大，则使用原来图片,此属性可能会影响图片自动纠正功能
			    compressSize: 0				// 单位字节，如果图片大小小于此值，不会采用压缩。 	
		},*/
		auto:true,
		runtimeOrder:'html5,flash',		// 默认值：html5,flash
		prepareNextFile:true,
		chunked:false,					// 切片
		chunkSize:5*1024*1024,			// 切片大小默认5m
		chunkRetry:2, 					// 切片失败尝试次数
		threads:3,						// 上传并发数
		formData:{},					// 文件上传请求的参数表，每次发送都会发送此对象中的参数
		fileVal:extendConfig.fileVal,		// 设置文件上传域的name。
		method:extendConfig.methodName,					// 文件上传方式，POST或者GET。
		sendAsBinary:extendConfig.fileVal?false:true,				// 是否已二进制的流的方式发送文件，这样整个上传内容php://input都为文件内容， 其他参数在$_GET数组中。
		fileNumLimit:self.fileNumLimit || 100,				// 验证文件总数量, 超出则不允许加入队列。
		fileSizeLimit:10*1024*1024*1024,			// 验证文件总大小是否超出限制, 超出则不允许加入队列。
		fileSingleSizeLimit:100*1024*2400, 	// 验证单个文件大小是否超出限制, 超出则不允许加入队列。
		duplicate:true,					// 去重， 根据文件名字、文件大小和最后修改时间来生成hash Key.
		disableWidgets:undefined,
		timeout: 0						// 文件比较大时，默认值2 * 60 * 1000会超时，导致上传失败  
	};
	/*****************************************
	 * 初始化上传参数
	 *****************************************/
	this.initUploadConfig = function(){
		if(self.uploadConfig){
			var formatEnabledFileType = function(){
				if (self.enabledFileType==null || self.enabledFileType=='')
					return null;
				self.enabledFileType = self.enabledFileType.replace(/\|/g, ",");
				//这里兼容格式: *.ppt;*.doc，应该是用,隔开，webuploader的格式为 doc,ppt,xls
				self.enabledFileType = self.enabledFileType.replace(/\;/g, ",");
				self.enabledFileType = self.enabledFileType.replace(/[\.|\*]/g,"");
				
				return self.enabledFileType;
			};
			//缩略图配置
			if(self.width!=null){
				self.uploadConfig.thumb.width = self.width;
			}
			if(self.height!=null){
				self.uploadConfig.thumb.height = self.height;
			}
			if (self.fdAttType == "pic" && self.enabledFileType == "") {
				self.enabledFileType = "gif,jpg,jpeg,bmp,png,tif";
			}
			//文件类型
			var enableFiles = formatEnabledFileType();
				
			if(enableFiles!=null){
				//TODO 奇葩问题，chrome下h5 file设置文件类型为image/**/时，文件选择对话框点击后需要好久(3秒以上)才能出现，
				//故以下此处对于图像的处理逻辑，变更为和自定义类型一致。
				if (self.fdAttType == "pic") {
					self.uploadConfig.accept.title = "Picture Files";
				}else{
					self.uploadConfig.accept.title = "Custom Files";
				}
				self.uploadConfig.accept.extensions = enableFiles;
				$.ajax(Com_Parameter.ContextPath + "sys/attachment/js/mime.jsp",{dataType:'json',
					data:"extFileNames=" + enableFiles, type:'POST', async:false, success:function(res){
					if(res.status==1){
						// #102070 只需要验证正确性就允许上传了，不然后缀无法精确匹配
						var resultArr = [];
						$.each(enableFiles.split(','),function(index,obj){
							resultArr.push("." + obj);
						});
						self.uploadConfig.accept.mimeTypes = resultArr.join(",");
					}else{
						self.uploadConfig.accept.mimeTypes = "*";
					}
				},error:function(xhr, status, errorInfo){
					self.uploadConfig.accept.mimeTypes = "*";
					if(window.console)window.console.error(errorInfo);
				}});
			} else {
				self.uploadConfig.accept.title = "All Files";
				self.uploadConfig.accept.extensions = "*";
				self.uploadConfig.accept.mimeTypes = "*";
			}
			self.uploadConfig.pick.id = '#uploader_' + self.fdKey + '_container';
			//支持拖拽
			if(self.supportDnd()) {
				self.uploadConfig.dnd = '#uploader_' + self.fdKey + '_queueList';
			}
			//多选
		    if(self.fdMulti){
		    	self.uploadConfig.pick.multiple = true;
		    }else{
		    	self.uploadConfig.pick.multiple = false;
		    }
		    //大小限制
		    if(self.smallMaxSizeLimit!=null){//单附件大小限制
		    	self.uploadConfig.fileSingleSizeLimit = parseFloat(self.smallMaxSizeLimit)*1024*1024;
		    }
			
		}
		
		if(extendConfig.beforeSendFile){
			WebUploader.Uploader.register({
				"before-send-file" : "beforeSendFile"
			}, {
				beforeSendFile : self.__beforeSendFile
			});
		}
	};
	/*****************************************
	 * 多附件时，上传文件可以通过拖拽来完成排序
	 *****************************************/
	this.sortable = function() {
		if(self._supportDndSort && self.fdMulti && self.canMove && self.fileList.length > 1 && self.showDragTip()) {
			try{
				// 列表
				var _document = $('#att_xtable_' + self.fdKey);
				if(_document.length > 0 && _document.sortable) {
					_document.sortable({
						items: "div.upload_list_tr", 	// 拖拽的元素
						opacity: 0.6,                   //拖动时，透明度为0.6
						update: self.sortableCallback   //更新排序之后
					});
				}
			} catch (e) {}
			try{
				// 图片
				var _document = $('#uploader_' + self.fdKey + '_list');
				if(_document.length > 0 && _document.sortable) {
					_document.sortable({
						items: "div.lui_upload_img_item", 	// 拖拽的元素
						opacity: 0.6,                   //拖动时，透明度为0.6
						update: self.sortableCallback   //更新排序之后
					});
				}
			} catch (e) {}
		}
	};
	this.showDragTip = function() {
		if(!self._supportDndSort || !self.fdMulti) {return;}
		var count = 0;
		var isShow = false;
		for(var i=0; i<self.fileList.length; i++) {
			if(self.fileList[i].isDelete != -1) {
				count++;
			}
			if(count > 1) {
				isShow = true;
				break;
			}
		}
		if(isShow) {
			$('#attachmentObject_'+self.fdKey+'_content_div .tip_info').removeClass("upload_item_hide");
		} else {
			$('#attachmentObject_'+self.fdKey+'_content_div .tip_info').addClass("upload_item_hide");
		}
		return isShow;
	};
	// 拖拽排序回调
	this.sortableCallback = function(event, ui) {
		if(self.fileList.length > 0) {
			var datas = ui.item.parent().children().map(function(i, n) {
				return $(n).attr("id");
			}).get();
			// 新文件列表
			var newFileList = [];
			// 先加入已删除的文件（状态为-1）
			var tempFiles = {};
			for(var i=0; i<self.fileList.length; i++) {
				var obj = self.fileList[i];
				if(obj["fileStatus"] == -1) {
					newFileList.push(obj);
				} else {
					tempFiles[obj["fdId"]] = obj;
				}
			}
			// 根据页面上的文件顺序加入到列表中
			for(var i=0; i<datas.length; i++) {
				var obj = tempFiles[datas[i]];
				if(obj) {
					newFileList.push(obj);
				}
			}
			// 设置新顺序
			self.fileList = newFileList;
			self.emit("sortable");
	    	self.createImageView($("#" + self.renderId).find("[data-lui-mark='attachmentlist']"));
		}
	};
	/*****************************************
	 * 初始化上传事件监听
	 *****************************************/
	this.initUploadEvent = function(){
		if(self.uploadObj){
			/*采用 onBeforeFileQueued = funtcion() {}  保证最后一个执行，不然一旦return false，webupload里面的beforeFileQueued事件就不执行了
			 * 而且最后一个执行能保证超过上传的限制个数的时候不会添加到fileList
			 */
			self.uploadObj.onBeforeFileQueued = self.__beforeFileQueued;
			//self.uploadObj.on("beforeFileQueued",self.__beforeFileQueued);	// 文件加入队列前
			self.uploadObj.on("fileQueued",self.__fileQueued);				// 文件加入队列
			self.uploadObj.on("uploadBeforeSend",self.__uploadBeforeSend);	// 文件上传服务器时前
			self.uploadObj.on("uploadProgress",self.__uploadProgress);		// 文件上传中		
			self.uploadObj.on("uploadError",self.__uploadError);			// 文件上传错误
			self.uploadObj.on("uploadSuccess",self.__uploadSuccess);		// 文件上传成功
			self.uploadObj.on("uploadFinished",self.__uploadFinished);		// 所有文件上传结束
			self.uploadObj.on("error",self.__error);						// 文件上传过程中错误监控
			// 有些隐藏的区域无法重置上传区域，这里通过事件来驱动
			$("#uploader_" + self.fdKey + "_queueList").mousemove(function() {self.readjusUploadArea();});
		}
	};
	/*****************************************
	 * 重新调整点击上传区域
	 * 由于集合了拖拽和点击上传，WebUpload在绘制区域后，区域发生了变化，此时需要重新调整
	 *****************************************/
	this.readjusUploadArea = function() {
		if(self.uploadObj) {
			// 获取拖拽区域元素，并取到外层的宽和高
			var container = $("#uploader_" + self.fdKey + "_queueList"),
			width = container.outerWidth ?
					container.outerWidth(true) : container.width(),
	        height = container.outerHeight ?
	        		container.outerHeight(true) : container.height();
	        // 重新调整上传点击区域
			var area = container.find("[id^='rt_']");
			if(self.fdAttType == "pic") {
				area.css({width: width+"px",height: height+"px"});
			} else {
				area.css({top: "-10px",left: "-11px",width: width+"px",height: height+"px"});
			}
		}
	};
	/*****************************************
	 * 绘制附件区域框架
	 *****************************************/
     this.drawFrame = function(renderCallback){
    	 var layoutExe = function(){
    		 if(self.layoutFn){
    			 self.layoutFn.apply(self,[$,function(dom){
     			 	var xdiv = $("#" + self.renderId);
     			 	xdiv.empty();
     			 	xdiv.append(dom);
     			 	window.setTimeout(function(){
     			 		if (self.editMode == "edit" || self.editMode == "add"){  
     			 			self.initUploadConfig();
     			 			self.uploadObj = WebUploader.create(self.uploadConfig);
     			 			self.initUploadEvent();
							//必填*渲染
     			 			if(self.required){
     			 				self.requiredExec(true);
     			 			}
     			 			if (self.canMove) {
     			 				self.sortable();
     			 			}
     			 			if(self.fdAttType=="pic") {
         			 			$("#uploader_" + self.fdKey + " .lui_upload_img_support").html(self.enabledFileType.replace(/[;,]/g,"<span>/</span>"));
         			 			if(self.uploadConfig.fileSingleSizeLimit) {
         			 				$("#uploader_" + self.fdKey + " .lui_upload_img_size").text(Attachment_MessageInfo["layout.upload.note9"] + self.formatSize(self.uploadConfig.fileSingleSizeLimit));
         			 			} else {
         			 				$("#uploader_" + self.fdKey + " .lui_upload_img_size").text(Attachment_MessageInfo["layout.upload.note10"]);
         			 			}
     			 			}
     			 			//校验放在submit事件里面
     			 			var win = window;
     			 			if (self.isUseByXForm){
     			 				win = self.win;
     			 			}
     			 			win.Com_Parameter.event["submit"].unshift(function(){
                                return self.submitValidate();
     			 			});
     			 			win.Com_Parameter.event["confirm"].unshift(function() {		 					 				
     		 					if (self.editMode == "edit" || self.editMode == "add") {
     		 						return self.updateInput();
     		 					} 
     			 			}); 
     			 		}
     			 	},300);
     			 	if(renderCallback!=null){
     			 		renderCallback();
     			 	}
     			 },self.dialogAlert]);
    		 }
    	 };
		 if(self.layoutFn == null){	
			 //要求快速显示附件，取消延迟
			 var result;
			 if(window._loadAttArray && window._loadAttArray["layout_"+self.fdLayoutType]!=null){
				 result = window._loadAttArray["layout_"+self.fdLayoutType];
			 }else{
				 $.ajax({
					 type:"GET",
					 url:self.layouturl,
					 dataType:'text',
					 async:false,
					 success:function(data){
						 if(!window._loadAttArray){
							 window._loadAttArray=[];
							 window._loadAttArray["layout_"+self.fdLayoutType] = data;
							 result = data;
						 }else{
							 window._loadAttArray["layout_"+self.fdLayoutType] = data;
							 result = data;
						 }
					 },
					 error:function(res){
						 if(window.console)window.console.error(errorInfo);
					 }
				 });
			 }

			 self.layoutFn = new Function('$','done','alert',result);
			 layoutExe();
			 self.sortable();
			 self.resizeAllUploader();
		 }else{
			 layoutExe();
		 }
     };
     
     this.showButton = function(renderCallback){
    	 self.btnIntial = true;
	     self.drawFrame(renderCallback);
     };
     
     /*****************************************
 	 * 基于ckresize实现附件图片预览 WUZB
 	 *****************************************/
     this.isImage = function(file){
    	if(file != null){
    		return "image.png" == window.GetIconNameByFileName(file.fileName);
 		}
 		return false;
     };
     
     this.createImageView = function(attrContainer){
    	 if(self.disabledImageView) return;
    	 if(self.editMode == 'view' || self.editMode == "edit"){
	    	 for (var i=0;i<self.fileList.length;i++){
	    		 if(this.fileList[i].fileStatus==1 && self.isImage(this.fileList[i]) && this.fileList[i].fdId.indexOf("WU_FILE")==-1){
	    			 self.imageList.push(this.fileList[i]);
				 }
	    	 }
	    	 if(self.imageList.length > 0){
	    		 var imageViewContainer = attrContainer.find("div[name='imageViewContainer']");
	        	 if(imageViewContainer.length > 0) 
	        		 imageViewContainer.remove();
	    		 
	    		 var imageViewName = 'imageview_'+self.fdKey;
	    		 imageViewContainer = $('<div name="imageViewContainer" id="imageViewContainer"/>');
	    		 var ckresizeDiv = $('<div id="_____rtf_____'+imageViewName+'" style="display:none" />');
	    		 var ckresizeTempDiv = $('<div id="_____rtf__temp_____'+imageViewName+'" />');
	    		 var imageViewDiv = $('<div name="rtf_'+imageViewName+'" style="display:none" />');
	    		 for (var i=0;i<self.imageList.length;i++){
	    			var imageFile = self.imageList[i];
	    			
	    			//解决进入view页面就全部加载图片的问题
	    			var imageUrl = self.getUrl("readDownload", "#####");
	 				var imageId = "image_" + imageFile.fdId;
	 				imageViewDiv.append($('<p><img id="'+imageId+'" src="'+imageUrl+'"/></p>'));
	    		 }
	    		 ckresizeDiv.append(imageViewDiv);
	    		 imageViewContainer.append(ckresizeDiv);
	    		 imageViewContainer.append(ckresizeTempDiv);
	    		 attrContainer.append(imageViewContainer);
	    		 
	    		 // 初始化图片预览事件
	    		 for (var i=0;i<self.imageList.length;i++){
	    			 var imageFile = self.imageList[i];
	    			 var targetTr = attrContainer.find("#"+imageFile.fdId);
	    			 var targetViewDiv = targetTr.find("div.upload_opt_view");
	    			 if(targetViewDiv.length > 0){
	    				 targetViewDiv.unbind('click');
	    				 if(self.canEdit || self.canPrint || self.canRead || self.canDownload){
		        			 targetViewDiv.bind('click',function(){
		        				 var fileId = $(this).parents('div.upload_list_tr').attr('id');
		        				 var imageId = "image_" + fileId;
		        				 
		        				//解决进入view页面就全部加载图片的问题
		        				 var imgList = attrContainer.find('#imageViewContainer').find('img');
		        				 if($('#'+imageId)[0].src.indexOf("#####")!=-1){
		        					 	for (var i=0;i<imgList.length;i++){
		        					 		var imageId_new = imgList[i].id;
		        					 		var fdId = imageId_new.replace("image_","");
			        		    			var imageUrl = imgList[i].src;
			        		    			imageUrl = imageUrl.replace("#####",fdId) + "&open=1";
			        		 				attrContainer.find('#'+imageId_new).attr('src',imageUrl);
		        					 	}
		    				 	 }
		        				 
		        				 attrContainer.find('#'+imageId).click();
		        			 }); 
	    				 }
	    			 }
	    			 var targetFileNameDiv = targetTr.find("div.upload_list_filename_view");
	    			 //兼容fdLayoutType === "pic"
	    			 if (self.fdLayoutType === "pic" || self.fdLayoutType == "/sys/attachment/view/layout_ocr.tmpl") {
	    				 targetFileNameDiv = targetTr.find(".upload_pic_filename_view");
	    			 }
	    			 if(targetFileNameDiv.length > 0){
	    				 targetFileNameDiv.unbind('click');
	    				 if(self.canEdit || self.canPrint || self.canRead || self.canDownload){
		    				 targetFileNameDiv.bind('click',function(){
		    					 var fileId = $(this).parents('div.upload_list_tr').attr('id');
		    					 if(self.fdLayoutType === "pic" || self.fdLayoutType == "/sys/attachment/view/layout_ocr.tmpl") {
		    						 fileId = $(this).parent(".lui_upload_img_item").attr('id');
		    					 }
		        				 var imageId = "image_" + fileId;
		        				 
		        				 //解决进入view页面就全部加载图片的问题
		        				 var imgList = attrContainer.find('#imageViewContainer').find('img');
		        				 if($('#'+imageId)[0].src.indexOf("#####")!=-1){
		        					 	for (var i=0;i<imgList.length;i++){
		        					 		var imageId_new = imgList[i].id;
		        					 		var fdId = imageId_new.replace("image_","");
			        		    			var imageUrl = imgList[i].src;
			        		    			imageUrl = imageUrl.replace("#####",fdId) + "&open=1";
			        		 				attrContainer.find('#'+imageId_new).attr('src',imageUrl);
		        					 	}
		    				 	 }
		        				 
		        				 attrContainer.find('#'+imageId).click();
		        			 }); 
	    				 }
	    			 }
	    		 }
	    		     		 
    			 CKResize.____resetWidth____(imageViewName, true);
	    		 
	    		 self.imageList.length=0;
	    	 }
    	 }
     };
     
     this.__uploadFinished = function(){
    	 self.sortable();
    	 if(self.disabledImageView) return;
    	 self.createImageView($("#" + self.renderId).find("[data-lui-mark='attachmentlist']"));
		 // 调整样式
		 self.resizeAllUploader2();
     };
     
     this.on("editDelete",function(rtn){
    	 // 发送事件让uploadObj对像中的文件队列数量-1
    	 self.uploadObj.trigger( 'fileDequeued', rtn.file );
    	 if(self.disabledImageView) return;
    	 var imageId = 'image_'+rtn.file.fdId;
    	 var targetImageEle = $('#'+imageId)
    	 if(targetImageEle.length > 0){
    		 targetImageEle.parent().remove();
    	 }
    	 self.createImageView($("#" + self.renderId).find("[data-lui-mark='attachmentlist']"));
		 // 调整样式
		 self.resizeAllUploader2();
     });

     /*****************************************
 	 * 调整附件文件样式
 	 * 根据元素宽度自动调整为显示一行或二行
 	 *****************************************/
	 this.resizeAllUploader = function() {
		 Com_AddAttrMain(self);
	 };

	// 右则审批伸缩时调用
	this.resizeAllUploader3 = function() {
		// 因为右则伸缩有一个动画的过程，这里的渲染需要延迟一会
		self.resizeAllUploader2();
		// 由于延迟的时机不好把握，1秒后再调整一次
		setTimeout(function(){self.resizeAllUploader2();}, 1000);
	};

	// 页面加载完后调用
	this.resizeAllUploader2 = function() {
		// 获取当前附件的总宽度
		var _cls = self.getAllClass(),
			parent = $("#att_xtable_" + self.fdKey);
		// 打印模式不需要操作栏，所以不需要分行，宽度统一使用100%
		if(self.isPrintModel) {
			var left = parent.find("." + _cls.left);
			left.css("width", "100%");
			return;
		}
		var total_width = 0,
			isShowBlock = false,
			tr = parent.find("." + _cls.tr);	// 获取行元素

		// 固定宽度
		if(self.fixedWidth && self.fixedWidth > 0) {
			parent.css("width", self.fixedWidth + "px");
		}
		// 处理有文件附件的区域
		if(tr && tr.length > 0) {
			total_width = parent.width();		// 获取总宽度
			// 如果没有获取到宽度，不处理样式
			if(total_width < 1) {
				//console.log("没有获取到宽度，不进行渲染。");
				return;
			}
			// if(window.console) {
			// 	console.log("正在重新渲染附件：" + self.fdKey + "，总宽度：" + total_width);
			// }
			isShowBlock = total_width >= 520;	// 如果总宽度大于等于520，就显示一行，否则就显示二行
			total_width = $(tr[0]).width();
			// 处理2个DIV的宽度
			if(isShowBlock) { // 显示一行
				// 增加block样式
				tr.addClass(_cls.trBlock);
				// 重置右则宽度，获取右则所有子元素的宽度
				var right_width = 0, left_width = 0;
				// 获取所有文件行的右则DIV
				parent.find("." + _cls.right).each(function(i, n) {
					var __width = 0;
					$(n).children().each(function(j, m) {
						if($(m).hasClass("upload_item_hide")) return true;
						__width += $(m).outerWidth(true);
					});
					// 因为每个文件的操作权限不一样，为了保证所有列的对齐，这里要取最长的宽度
					if(__width > right_width) {
						right_width = __width;
					}
				});
				// 右则宽度最小限制为200px
				var def_right_width = 180;
				if(Com_Parameter.Lang && Com_Parameter.Lang.indexOf('en') > -1) {
					def_right_width = 200;
				}
				if(right_width < def_right_width) {
					right_width = def_right_width;
				} else {
					right_width += 5;
				}
				// 左则宽度 = 总宽度 - 右则宽度 - 间隔(30px)
				left_width = total_width - right_width - 30;
				// 设置右则宽度
				parent.find("." + _cls.right).css("width", right_width + "px");
				// 设置左则宽度
				parent.find("." + _cls.left).css("width", left_width + "px");
			} else { // 显示二行
				// 删除block样式
				tr.removeClass(_cls.trBlock);
				// 删除2个DIV的宽度
				parent.find("." + _cls.left).css("width", "");
				parent.find("." + _cls.right).css("width", "");
				// 如果操作按钮太多，需要把状态调整少一点
				parent.find("." + _cls.right).each(function(i, n) {
					var __width = 0;
					$(n).children().each(function(j, m) {
						if($(m).hasClass("upload_item_hide")) return true;
						__width += $(m).outerWidth(true);
					});
					if(__width >= total_width) {
						$(n).find(".upload_list_status").css({"margin-right": "0px","max-width": "32px"});
						$(n).find(".upload_opt_status").html("<i></i>...");
						return false;
					}
				});
			}
			// 处理文件名的长度(文件名在左则的DIV，左则的DIV分别有：多选框(可能有)，文件图标，文件名(前缀[需要动态计算宽度] + 后缀)，文件大小)
			var left_width = parent.find("." + _cls.left).width(),
				right_width = parent.find("." + _cls.right).width(),
				icon_width = parent.find(".upload_list_icon").outerWidth() || 0,
				ck_width = parent.find(".upload_list_ck").outerWidth() || 0,
				file_div = parent.find("." + _cls.fileName),
				file_ext_width = file_div.find(".upload_list_filename_ext").outerWidth() || 0,
				size_width = 0;
			// 取当前列表宽度最长的文件大小
			parent.find("." + _cls.left).each(function(i, n) {
				var _size_width = $(n).find(".upload_list_size").outerWidth();
				if(_size_width > size_width) {
					size_width = _size_width;
				}
			});
			// 计算文件名宽度
			var file_width = left_width - icon_width - size_width - ck_width - 45;
			if(isShowBlock) {
				if(file_width < 10) {
					file_width = 10;
				}
				// 设置文件DIV宽度
				file_div.css("width", file_width + "px");
			} else {
				file_div.css("width", "");
			}
			// 设置文件名最大宽度
			var filename_max_width = file_width - file_ext_width - 20;
			if(filename_max_width < 20) {
				filename_max_width = 20;
			}
			if(self.filenameWidth != '')
			{
				file_div.find(".upload_list_filename_title").css("max-width", self.filenameWidth + "px");
			}else
			{
				file_div.find(".upload_list_filename_title").css("max-width", filename_max_width + "px");
			}

			// 记录所有宽度值
			if(!self._width_info) {
				self._width_info = [];
			}
			self._width_info.push("总宽度：" + total_width + "，左则DIV宽度：" + left_width + "，右则DIV宽度：" + right_width
				+ "。多选框：" + ck_width + "，图标：" + icon_width + "，文件大小：" + size_width + "，文件名：" + file_width
				+ "(前缀：" + (file_width - file_ext_width) + "，后缀：" + file_ext_width + ")");
		}
	};
 	// 获取内置的一些元素样式
 	this.getAllClass = function() {
 		var _cls = {
			"tr": "upload_list_tr_edit",
			"trBlock": "upload_list_tr_edit_block",
			"left": "upload_list_tr_edit_l",
			"right": "upload_list_tr_edit_r",
			"fileName": "upload_list_filename_edit"
		}
		if(self.editMode == 'view') {
			_cls = {
				"tr": "upload_list_tr_view",
				"trBlock": "upload_list_tr_view_block",
				"left": "upload_list_tr_view_l",
				"right": "upload_list_tr_view_r",
				"fileName": "upload_list_filename_view"
			}
		}
 		return _cls;
 	};
 	
 	//重命名
 	this.reName = function(att,i,self) {
 		if (typeof(seajs) != 'undefined') {
 			seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
 				var changeName;
				var fdFileName = att.fileList[i].fileName;
				if(fdFileName !=null &&fdFileName !=""){
					fdFileName = fdFileName.substring(0,fdFileName.lastIndexOf("."));
					// #150149
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
						if(att.fileList[i].isNew && !att.fileList[i].fdIsCreateAtt){
							var _filename = $("#" + att.fileList[i].fdId + " .upload_list_filename_title");
							_filename.text(changeName);
							_filename.parent().attr("title", changeName + fileExt);
						}else{
							$.ajax({
								url: self.alterUrl,
								type: 'POST',
								data:{id:att.fileList[i].fdId,name:att.fileList[i].fileName},
								dataType: 'json',
								async:false, 
								success: function(data){
									// 文件名称修改成功，不需要刷新控件，只需要修改元素名称
									var _filename = $("#" + att.fileList[i].fdId + " .upload_list_filename_title");
									_filename.text(changeName);
									_filename.parent().attr("title", changeName + fileExt);
								}
						   });
						}
					}
				}, {
					width : 450,
					height : 200
				});
 		});
	}
 	}
 	
 	this.alterName = function(docId,self) {
		for (var i = 0; i < self.fileList.length; i++){
			if(self.fileList[i].fdId == docId){
				this.reName(self,i,self);
				break;
			}
		}
	};
	
 	//查看历史版本
 	this.viewHistory = function(docId,isReadDownLoad,realCanPrint,realCanDownload,self) {
 		if (typeof(seajs) != 'undefined') {
 			seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
				var iframeUrl = "/sys/attachment/sys_att_main/sysAttMainHistory_list.jsp?fdOriginId="+docId+"&isReadDownLoad="+isReadDownLoad+"&realCanPrint="+realCanPrint+"&realCanDownload="+realCanDownload;
				if(self.fdForceUseJG){
					iframeUrl += "&fdForceUseJG=true"
				}
				var title = Attachment_MessageInfo["sysAttMain.view.history"] ;
				dialog.iframe(iframeUrl, title, null, {
					width : 1000,
					height : 600
				});
 			});
 		}
 	}
 		
}
Swf_AttachmentObject.prototype.on = function(event, callback) {
    if (!callback) return this;
    var list = this.eventsCache[event] || (this.eventsCache[event] = []);
    list.push(callback);
    return this;
}
Swf_AttachmentObject.prototype.cover = function(event, callback) {
	if (!callback) return this;
	this.eventsCache[event] = [callback];
	return this;
}
Swf_AttachmentObject.prototype.off = function(event, callback) {
    // Remove *all* events
    if (!(event || callback)) {
        this.eventsCache = {};
        return this;
    }
    var list = this.eventsCache[event];
    if (list) {
        if (callback) {
            for (var i = list.length - 1; i >= 0; i--) {
                if (list[i] === callback) {
                    list.splice(i, 1);
                }
            }
        } else {
            delete this.eventsCache[event];
        }
    }
    return this;
}
Swf_AttachmentObject.prototype.emit = function(event, data) {
    var list = this.eventsCache[event];
    var fn;
    if (list) {
        // Copy callback lists to prevent modification
        list = list.slice();
        // Execute event callbacks
        while ((fn = list.shift())) {
            fn(data);
        }
    }
    return this;
}

/**
 * 是否是WPS加载项预览
 */
getWpsoaassistConfig = function(){
	var checkUrl = Com_Parameter.ContextPath + "sys/attachment/sys_att_main/sysAttMain.do?method=getWpsoaassistConfig";
	var flag = false;
	$.ajax({
		url: checkUrl,
		type: 'POST',
		data:'',
		dataType: 'json',
		async:false, 
		success: function(data){
			if(data.onlineToolType == '3')
				  flag = true;
		}
   });
	return flag;
}
