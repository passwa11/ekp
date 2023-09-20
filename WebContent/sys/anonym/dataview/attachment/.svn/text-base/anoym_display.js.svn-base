var self = this;
var _reads = File_EXT_READ.split(";");
var _prints = File_EXT_PRINT.split(";");
var _videos = File_EXT_VIDEO.split(";");
var _mp3s = File_EXT_MP3.split(";");
var _edits = File_EXT_EDIT.split(";");
var _noPrints = File_EXT_NO_PRINT.split(";");
var _noReads = File_EXT_NO_READ.split(";");
var _pics = File_EXT_PIC_READ.split(";");
var _read_downloads = File_EXT_READDOWNLOAD.split(";");

// 判断“点击上传”是否有蓝色（针对表单中的“模板附件”样式）
var __text = $("#uploader_" + self.fdKey + "_container .lui_text_primary");
var __color = __text.css("color");
if(__color && __color.indexOf("102, 102, 102") > -1) {
	__text.css("color", "rgb(66, 133, 244)");
}

self._resizeAllUploader = function() {
	setTimeout(function() {
		self.resizeAllUploader();
		if(self.resizeCount < 5) {
			self.resizeCount++;
			self._resizeAllUploader();
		}
	}, 100);
}
// 监听窗口变化
$(window).on("resize", function() {setTimeout(function(){self.resizeAllUploader3();}, 500);});
// 监听右则收缩变化
$(document).on("slideSpread", function() {setTimeout(function(){self.resizeAllUploader3();}, 500);});

var xparent = $("<div id='att_xtable_"+this.fdKey+"'></div>");
if(this.editMode=='view'){
	//查看视图
	if(this.fileList.length > 0){
		if(this.fileList.length > 1){
			xparent.append(createViewOperation());
		}
		for (var i=0;i<this.fileList.length;i++){
			xparent.append(createViewFileTr(this.fileList[i]));
		}
	}
}else{
	if (self.canMove && self.fdMulti) {
		xparent.append("<div class='lui_upload_tip tip_info upload_item_hide'><i></i>"+Attachment_MessageInfo["layout.upload.note11"]+"</div>");
	}
	//编辑视图
	var showUploader = true;
	for (var i=0;i<this.fileList.length;i++){
		if(this.fileList[i].isDelete != -1) {
			showUploader = false;
			break;
		}
	}
	if (!self.fdMulti && !showUploader) {
		$("#uploader_"+this.fdKey+" tr.tips").removeClass("upload_item_hide");
		$("#uploader_"+this.fdKey+" tr.uploader").addClass("upload_item_hide");
	}
	for (var i=0;i<this.fileList.length;i++){
		xparent.append(createEditFileTr(this.fileList[i], true));
	}
}

//图片预览
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
    			var imageUrl = self.getUrl("download", "#####");
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
	        		    			imageUrl = imageUrl.replace("sys/attachment/sys_att_main/sysAttMain.do?method=download","sys/anonym/sysAnonymData.do?method=downLoadAtt");
	        		 				attrContainer.find('#'+imageId_new).attr('src',imageUrl);
        					 	}
    				 	 }
        				 attrContainer.find('#'+imageId).click();
        			 }); 
    			 }
    			 var targetFileNameDiv = targetTr.find("div.upload_list_filename_view");
    			 //兼容fdLayoutType === "pic"
    			 if (self.fdLayoutType === "pic") {
    				 targetFileNameDiv = targetTr.find(".upload_pic_filename_view");
    			 }
    			 if(targetFileNameDiv.length > 0){
    				 targetFileNameDiv.unbind('click');
    				 targetFileNameDiv.bind('click',function(){
    					 var fileId = $(this).parents('div.upload_list_tr').attr('id');
    					 if(self.fdLayoutType === "pic") {
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
	        		    			imageUrl = imageUrl.replace("sys/attachment/sys_att_main/sysAttMain.do?method=download","sys/anonym/sysAnonymData.do?method=downLoadAtt");
	        		 				attrContainer.find('#'+imageId_new).attr('src',imageUrl);
        					 	}
    				 	 }
        				 attrContainer.find('#'+imageId).click();
        			 }); 
    			 }
    		 }
			 CKResize.____resetWidth____(imageViewName, true);
    		 self.imageList.length=0;
    	 }
	 }
};

//植入阅读代码
var _readUrl = Com_Parameter.ContextPath+'sys/anonym/sysAnonymData.do?method=view&fdId=';
var _downLoadUrl=Com_Parameter.ContextPath+'sys/anonym/sysAnonymData.do?method=downLoadAtt&fdId=';
this.readDoc = function(docId) {
	var url = _readUrl+docId
	Com_OpenWindow(url, "_blank");
};
this.downDoc = function(docId) {
	var url = _downLoadUrl+docId
	Com_OpenWindow(url, "_blank");
};
done(xparent);
/** 查看视图 开始 **/
function createViewOperation(){
	var xul = $("<div class='upload_list_title'></div>"); 
	var xli = $("<div class='upload_opt_td'></div>");
	
	xli.append($("<div class='upload_opt_td_l'><label><input type='checkbox' name='List_File_Tongle_" + self.fdKey + "'>"+Attachment_MessageInfo["button.selectAll"]+"</label></div>").click(
		function() {
			self.checkAll();
		}));

	if (self.canDownload) {
		xli.append($("<div class='upload_opt_td_r'/>").append($("<div class='upload_opt_batchdown'><i class='upload_opt_icon'></i>"
				+ Attachment_MessageInfo["button.batchdown"] + "</div>").click(
				function() {
					self.downloadFiiles();
				})).append($("<div class='upload_opt_downall'><i class='upload_opt_icon'></i>" + Attachment_MessageInfo["button.downall"] + "</div>").click(
				function() {
					self.downloadFiiles(true);
				})));
	}else{
		xli.css('display','none');
	}
	return xul.append(xli);
}

function _canRead(fileExt){
    fileExt = fileExt.toLowerCase();
	if($.inArray(fileExt,_noReads) >= 0){
		return "";
	}
	var text = ""; 
	if ($.inArray(fileExt,_reads) > -1 
		|| $.inArray(fileExt,_pics) > -1 
		|| $.inArray(fileExt,['.txt']) > -1
		|| $.inArray(fileExt,_read_downloads) > -1){
		text = Attachment_MessageInfo["button.read"];
	} else if ($.inArray(fileExt,_videos) > -1 || $.inArray(fileExt,_mp3s) > -1){
		if(self.editMode=='view'){
			text = Attachment_MessageInfo["button.play"];
		} 
	}
	
	return text;
}

function createViewFileTr(file){
	var fileIcon = window.GetIconClassByFileName(file.fileName);
	var xtr = $("<div id='"+file.fdId+"' class='upload_list_tr upload_list_tr_view'></div>");
	var xtd_l = $("<div class='upload_list_tr_view_l'></div>");
	var xtd_r = $("<div class='upload_list_tr_view_r'></div>");
	if(self.fileList.length > 1 && self.canDownload){
		var _xtd = $("<div class='upload_list_ck'></div>");
		_xtd.append($("<input type='checkbox' name='List_File_Selected_" + file.fdId + "' value='" + file.fdId + "'>").click(
				function() {
					self.checkSelected();
				}));
		xtd_l.append(_xtd);
	}
	// 默认执行第一个操作按钮的点击事件
	var fileName = file.fileName.substring(0, file.fileName.lastIndexOf("."));
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	xtd_l.append("<div class='upload_list_icon'><i class='"+fileIcon+"'></i></div>");
	xtd_l.append($("<div class='upload_list_filename_view' title='"+file.fileName+"'>"
					+ "<span class='upload_list_filename_title'>" + fileName + "</span>"
					+ "<span class='upload_list_filename_ext'>" + fileExt + "</span>"
					+ "</div>"));  
	//加入操作列
	xtd_l.append("<div class='upload_list_size'>"+self.formatSize(file.fileSize)+"</div>");
	// 操作栏
	xtd_r.append(createFileOpers(file));
	xtr.append(xtd_l);
	xtr.append(xtd_r);
	return xtr;
}

function readDoc(file) {
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf(".")).toLowerCase();
	var attId = file.fdTemplateAttId || file.fdId;
	if ($.inArray(fileExt,_reads) > -1) {
		self.readDoc(attId);
		// pdf特殊处理下
	} else if($.inArray(fileExt,_read_downloads) > -1){ 
		self.readDoc(attId);
	} else if($.inArray(fileExt,_videos) > -1) {
		if (self.editMode == 'view') {
			self.startVideo(attId);
		}
	} else if ($.inArray(fileExt,_mp3s) > -1) {
		if (self.editMode == 'view' ) {
			self.startMp3(attId);
		}
	} else {
		self.openDoc(attId);
	}
}

function createFileOpers(file){
	var xtd = $("<div class='upload_list_operation'></div>");
	if(!file.fileKey) {return xtd;}
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	var attId = file.fdTemplateAttId || file.fdId;
	if (self.canRead) {
		var text = _canRead(fileExt);
		if (text){
			if(file.fdIsCreateAtt){
				xtd.append($("<div class='upload_opt_icon upload_opt_view'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+text+"</i></span></div>").click(function(e){
					readDoc(file);
				}));
			}
		}
	}
	if(!file.isNew){
		if (self.canEdit && !file.fdTemplateAttId) {
			if ($.inArray(fileExt.toLowerCase(),_edits) > -1){
				xtd.append($("<div class='upload_opt_icon upload_opt_edit'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.edit"]+"</i></span></div>").click(function(e){
					self.editDoc(attId);
				}));
			}
		}
		if (self.canPrint) {
			if ($.inArray(fileExt.toLowerCase(),_noPrints) < 0){
				if ($.inArray(fileExt.toLowerCase(),_prints) > -1){
					xtd.append($("<div class='upload_opt_icon upload_opt_print'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.print"]+"</i></span></div>").click(function(e){
						self.printDoc(attId);
					}));
				}
			}
		}
		if (self.canDownload) {
			xtd.append($("<div class='upload_opt_icon upload_opt_down'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.download"]+"</i></span></div>").click(function(e){
				self.downDoc(attId);
			}));
			if (self.isShowDownloadCount && self.editMode=='view') {
				xtd.append("<span class='upload_opt_tip_count'>("+file.fileDownloadCount+")</span>");
			}
		}
	}

	if (self.canMove) {
		if(!self._supportDndSort && self.fdMulti){
			xtd.append($("<div class='upload_opt_icon upload_opt_move_up'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.moveup"]+"</i></span></div>").click(function(){
				self.moveUpDoc(attId);
			}));
			xtd.append($("<div class='upload_opt_icon upload_opt_move_down'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.movedown"]+"</i></span></div>").click(function(){
				self.moveDownDoc(attId);
			}));
		}
		//if(file.fdIsCreateAtt){
			if (typeof(seajs) != 'undefined') {
				xtd.append($("<div class='upload_opt_icon upload_opt_alter_name'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.rename"]+"</i></span></div>").click(function(e){
					
					if(window.alterName){
						window.alterName(attId,self);
					} else {
						self.alterName(attId,self);
					}
				}));
			}
		//}
	}	
	return xtd;
}
/** 查看视图 结束 **/


/** 编辑视图 开始 **/
function createEditFileTr(file, isView){
	if(file.isDelete == -1)
		return;
	var fileName = file.fileName.substring(0, file.fileName.lastIndexOf("."));
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	var fileIcon = window.GetIconClassByFileName(file.fileName);
	var xtr = $("<div id='"+file.fdId+"' class='upload_list_tr upload_list_tr_edit'></div>");
	var xtd_l = $("<div class='upload_list_tr_edit_l'></div>");
	var xtd_r = $("<div class='upload_list_tr_edit_r'></div>");
	xtd_l.append("<div class='upload_list_icon'><i class='"+fileIcon+"'></i></div>");
	xtd_l.append("<div class='upload_list_filename_edit' title='"+file.fileName+"'><span class='upload_list_filename_title'>"+Com_HtmlEscape(fileName)+"</span><span class='upload_list_filename_ext'>"+fileExt+"</span></div>");  
	xtd_l.append("<div class='upload_list_size'>"+self.formatSize(file.fileSize)+"</div>");
	
	xtd_r.append("<div class='upload_list_progress_img upload_item_hide'></div>");
	xtd_r.append("<div class='upload_list_progress_text upload_item_hide'></div>");
	// 文件状态
	var status = $("<div class='upload_list_status'></div>");
	if(isView) {
		if(file.fileStatus == 1) {
			var success = "<div class='upload_opt_status success' title='"+Attachment_MessageInfo["msg.uploadSucess"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadSucess"]+"</div>";
			status.html(success);
		} else if(!file.fileKey) {
			var fail = "<div class='upload_opt_status fail' title='"+Attachment_MessageInfo["msg.uploadFail"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadFail"]+"</div>";
			status.html(fail);
		}
	}
	xtd_r.append(status);
	
	//加入操作列
	xtd_r.append(createFileOpers(file).append(getDeleteOpt(file)));
	
	xtr.append(xtd_l);
	xtr.append(xtd_r);
	self.showDragTip();
	return xtr;
}

function getDeleteOpt(file, obj){
	if(file.fileStatus != 0){//上传成功,失败，错误
		var xbtn = $("<div></div>");
		if(file.fileStatus == -1) {
			// 重新上传
			xbtn.append($("<div class='upload_opt_icon upload_opt_reupload'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.reupload"]+"</i></span></div>").click(function(e){
				self.uploadObj.retry(obj);
			}));
		}
		// 删除
		xbtn.append($("<div class='upload_opt_icon upload_opt_delete'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.delete"]+"</i></span></div>").click(function(e){
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.confirm(Attachment_MessageInfo["button.confimdelte"], function(value) {
					if(value) {
						if(file.fileStatus != -1){
							$("#uploader_" + self.fdKey + " .uploader").removeClass("upload_item_hide");
							$("#uploader_" + self.fdKey + " .tips").addClass("upload_item_hide");
							file.fileStatus = -1;
							file.isDelete = -1;
							//$("#"+file.fdId).remove();
							self.delFileList(file.fdId);
							// 编辑状态下删除发送事件
							self.emit('editDelete',{"file":file});
							if(top.window.previewEvn){
								top.window.previewEvn.emit('editDelete',{"file":file});
							}
						}else{
							file.isDelete = -1;
							$("#"+file.fdId).remove();
							$("#uploader_" + self.fdKey + " .uploader").removeClass("upload_item_hide");
							$("#uploader_" + self.fdKey + " .tips").addClass("upload_item_hide");
							self.showDragTip();
						}
					}
				});
			});
		}));
		return xbtn;
	}else{
		return $("<div class='upload_opt_icon upload_opt_cancel'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.cancelAll"]+"</i></span></div>").click(function(e){
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.confirm(Attachment_MessageInfo["button.confirmCancel"], function(value) {
					if(value) {
						$("#uploader_" + self.fdKey + " .uploader").removeClass("upload_item_hide");
						$("#uploader_" + self.fdKey + " .tips").addClass("upload_item_hide");
						file.fileStatus = -1;
						file.isDelete = -1;
						$("#"+file.fdId).remove();
						self.uploadObj.cancelFile(file.fdId);
						// 编辑状态下删除发送事件
						self.emit('editDelete',{"file":file});
						if(top.window.previewEvn){
							top.window.previewEvn.emit('editDelete',{"file":file});
						}	
					}
				});
			});
		});
	}
}

if(this.editMode=='view'){
	//查看时不需要绑定上传事件
}else if(this.initMode){
	//this.off();
	this.on("uploadCreate",function(data){
		var file = data.file;
		$('#att_xtable_'+self.fdKey+'').append(createEditFileTr(file, false));
	});
	this.on("uploadStart", function(data){
		if(!self.fdMulti) {
			// 单附件上传时，在选择了一个文件后，隐藏上传按钮
			$("#uploader_" + self.fdKey + " .uploader").addClass("upload_item_hide");
			// 显示提示信息
			$("#uploader_" + self.fdKey + " .tips").removeClass("upload_item_hide");
		}
		var file = data.file;
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation,.upload_list_status").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_progress_size").append("<div class='upload_progress_size'></div>");
		$("#"+file.fdId).find(".upload_list_progress_img").append("<div class='upload_progress_border'><div class='upload_progress_val'></div></div>");
		$("#"+file.fdId).find(".upload_list_progress_text").append("<div class='upload_progress_text'>"+Attachment_MessageInfo["button.progress"]+"0%</div>");
		$("#"+file.fdId).find(".upload_list_operation").html(getDeleteOpt(file, data._file));
		// 获取操作栏的最小宽度
		var optMinWidth = $("#"+file.fdId).find(".upload_list_operation").css("min-width");
		file.optMinWidth = optMinWidth;
		$("#"+file.fdId).find(".upload_list_operation").css("min-width", "0px");
		self.resizeAllUploader();
	});

	this.on("uploadMD5", function(data){
		var file = data.file; 
		$("#"+file.fdId).find(".upload_progress_text").html(Attachment_MessageInfo['button.md5']);
	});
		
	this.on("uploadProgress", function(data){
		var file = data.file;
		var percent = data.totalPercent;
		var total = self.formatSize(file.fileSize);
		var cur;
		if(percent==null){
			var bytesLoaded = data.bytesLoaded;
			var bytesTotal = data.bytesTotal;
			percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
			cur = file.fileSize * (bytesLoaded / bytesTotal);
		}else{
			percent = Math.ceil(percent*100);
			cur = file.fileSize * percent;
		}
		cur = self.formatSize(cur/100);
		$("#"+file.fdId).find(".upload_list_operation").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_size").html(cur + "/" + total);
		$("#"+file.fdId).find(".upload_progress_val").css("width", percent + "%");
		$("#"+file.fdId).find(".upload_progress_text").html(parseInt(percent) + "%");
		self.resizeAllUploader();
	});
	this.on("uploadSuccess", function(data){
		var file = data.file;
		var serverData = data.serverData; 
		if(file._fdId != file.fdId){
			$("#"+file._fdId).attr("id",file.fdId);
		}
		$("#"+file.fdId).find(".upload_list_size").html(self.formatSize(file.fileSize));
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").empty();
		var read = "", moveup = "", movedown = "", alterName = "";
		if(data.uploadAfterSelect){
			var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
			var text = _canRead(fileExt);
			if (text) {
				read = $("<div class='upload_opt_icon upload_opt_view'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+text+"</i></span></div>").click(function(e){
					readDoc(file);
					if(e.preventDefault) {
						e.preventDefault();
					} else {
						window.event.returnValue == false;
					}
				});
			}
		}
		if (self.canMove) {
			if(!self._supportDndSort && self.fdMulti){
				moveup = $("<div class='upload_opt_icon upload_opt_move_up'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.moveup"]+"</i></span></div>").click(function(){
					self.moveUpDoc(file.fdId);
				});
				movedown = $("<div class='upload_opt_icon upload_opt_move_down'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.movedown"]+"</i></span></div>").click(function(){
					self.moveDownDoc(file.fdId);
				});
			}
		}
		if (typeof(seajs) != 'undefined') { //sysAttMain_edit_swf.jsp reName
			alterName = $("<div class='upload_opt_icon upload_opt_alter_name'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.rename"]+"</i></span></div>").click(function(e){
				if(window.alterName){
					window.alterName(file.fdId,self);
				} else {
					self.alterName(file.fdId,self);
				}
				if(e.preventDefault) {
					e.preventDefault();
				} else {
					window.event.returnValue == false;
				}
			});
		}
		
		var success = "<div class='upload_opt_status success' title='"+Attachment_MessageInfo["msg.uploadSucess"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadSucess"]+"</div>";
		$("#"+file.fdId).find(".upload_list_status").removeClass("upload_item_hide").append(success);
		$("#"+file.fdId).find(".upload_list_operation").append(read).append(moveup).append(movedown).append(alterName).append(getDeleteOpt(file, data._file));
		// 恢复操作栏最小宽度
		$("#"+file.fdId).find(".upload_list_operation").css("min-width", file.optMinWidth);
		self.resizeAllUploader();
		self.sortable();
	});
	this.on("uploadFaied", function(data){
		var file = data.file;
		var serverData = data.serverData;	
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").empty();
		var fail = "<div class='upload_opt_status fail' title='"+Attachment_MessageInfo["msg.uploadFail"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadFail"]+"</div>";
		$("#"+file.fdId).find(".upload_list_status").removeClass("upload_item_hide").html(fail);
		file.fileStatus = -1;
		$("#"+file.fdId).find(".upload_list_operation").html(getDeleteOpt(file, data._file));
		$("#"+file.fdId).find(".upload_list_size").html(self.formatSize(file.fileSize));
		// 恢复操作栏最小宽度
		$("#"+file.fdId).find(".upload_list_operation").css("min-width", file.optMinWidth);
		self.resizeAllUploader();
		self.sortable();
		alert(serverData);
	});
	
	this.on("error", function(data){
		var file = data.file;
		file.isDelete = -1;
		var serverData = data.serverData;	
		if("Q_EXCEED_SIZE_LIMIT"==serverData){
		    $("#"+file.fdId).remove();
			alert(Attachment_MessageInfo["error.exceedMaxSize"].replace("{0}", self.totalMaxSize+'MB'));
		}
		else if("Q_EXCEED_NUM_LIMIT"==serverData){
		   $("#"+file.fdId).remove();
		   alert(Attachment_MessageInfo["error.exceedNumber"].replace("{0}", data.max+"个"));
		}
		else if("F_EXCEED_SIZE"==serverData){
		    $("#"+file.fdId).remove();
			alert(Attachment_MessageInfo["error.smallMaxSize"].replace("{0}",self.singleMaxSize+' MB'));
		}
		else{
		    $("#"+file.fdId).remove();
			alert(Attachment_MessageInfo["error.enabledFileType"].replace("{0}", self.enabledFileType));
		}
		if(!self.fdMulti) {
			// 显示上传按钮
			$("#uploader_" + self.fdKey + " .uploader").removeClass("upload_item_hide");
			// 隐藏提示信息
			$("#uploader_" + self.fdKey + " .tips").addClass("upload_item_hide");
		}
	});
	this.on("uploadError", function(data){
		var file = data.file;
		var errorCode = data.errorCode;
		var message = data.message;
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").empty();
		var error = "<div class='upload_opt_status error'><i></i>";
		if(errorCode == -280){
			error += Attachment_MessageInfo["button.cancelupload"];
		}else{
			error += Attachment_MessageInfo["msg.uploadFail"];
		}
		error += "</div>";
		$("#"+file.fdId).find(".upload_list_status").removeClass("upload_item_hide").html(error);
		$("#"+file.fdId).find(".upload_list_operation").html(getDeleteOpt(file, data._file));
		file.fileStatus = -1;
		file.isDelete = -1;
	});
}
