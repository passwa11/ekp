var self = this;
var _reads = File_EXT_READ.split(";");
var _videos = File_EXT_VIDEO.split(";");
var _mp3s = File_EXT_MP3.split(";");
var _edits = File_EXT_EDIT.split(";");
var _noReads = File_EXT_NO_READ.split(";");

if(this.fdViewType.indexOf('right') > -1) { 
	self.fixedWidth = $("#assignmentRow").actual('width');
	var xtable = $("<div id='att_xtable_"+this.fdKey+"'></div>");
	if (self.canMove && self.fdMulti) {
		xtable.append("<div class='lui_upload_tip tip_info upload_item_hide'><i></i>"+Attachment_MessageInfo["layout.upload.note11"]+"</div>");
	}
	for (var i=0;i<this.fileList.length;i++){
		if(this.fileList[i].isDelete == -1) {
			continue;
		}
		xtable.append(createEditFileTr(this.fileList[i]));
	}
	done(xtable);
}

function readDoc(file) {
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	if ($.inArray(fileExt.toLowerCase(),_reads) > -1) {
		self.readDoc(file.fdId);
	} else if($.inArray(fileExt.toLowerCase(),_videos) > -1) {
		if (self.editMode == 'view' && self.fdModelName == 'kmsMultimediaMain') {
			self.startVideo(file.fdId);
		}
	} else if ($.inArray(fileExt.toLowerCase(),_mp3s) > -1) {
		if (self.editMode == 'view' && self.fdModelName == 'kmsMultimediaMain') {
			self.startMp3(file.fdId);
		}
	} else {
		self.openDoc(file.fdId);
	}
}
function createFileOpers(file){	
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	var xtd = $("<div class='upload_list_operation'></div>");
	if (self.canRead) {
		var text = "";
		if ($.inArray(fileExt.toLowerCase(),_reads) > -1){
			text = (Attachment_MessageInfo["button.read"]);
		}else if($.inArray(fileExt.toLowerCase(),_videos) > -1){
			if(self.editMode=='view' && self.fdModelName=='kmsMultimediaMain'){
				text = ""+Attachment_MessageInfo["button.play"]+"";
			} 
		}else if($.inArray(fileExt.toLowerCase(),_mp3s) > -1){
			if(self.editMode=='view' && self.fdModelName=='kmsMultimediaMain'){
				text = ""+Attachment_MessageInfo["button.play"]+"";
			}
		}else{
			text = (Attachment_MessageInfo["button.open"]);
		}
		if ($.inArray(fileExt.toLowerCase(),_noReads) < 0){
		    // xtd.append($("<div class='upload_opt_view' title='"+text+"'></div>").click(function(){
			//#146035 右侧审批模式 没有阅读图标
			xtd.append($("<div class='upload_opt_icon upload_opt_view'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+text+"</i></span></div>").click(function(){
				if ($.inArray(fileExt.toLowerCase(),_reads) > -1){
					self.readDoc(file.fdId);
				}else if($.inArray(fileExt.toLowerCase(),_videos) > -1){
					if(self.editMode=='view' && self.fdModelName=='kmsMultimediaMain'){
						self.startVideo(file.fdId);
					} 
				}else if($.inArray(fileExt.toLowerCase(),_mp3s) > -1){
					if(self.editMode=='view' && self.fdModelName=='kmsMultimediaMain'){
						self.startMp3(file.fdId);
					}
				}else{
					self.openDoc(file.fdId);
				}
			}));
		}
	}
	if (self.canDownload) {
		xtd.append($("<div class='upload_opt_icon upload_opt_down'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.download"]+"</i></span></div>").click(function(){
			self.downDoc(file.fdId);
		}));
	}
	if (self.canEdit) {
		if ($.inArray(fileExt.toLowerCase(),_edits) > -1){
			xtd.append($("<div class='upload_opt_icon upload_opt_edit'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.edit"]+"</i></span></div>").click(function(){
				self.editDoc(file.fdId);
			}));
		}
	}
	if (self.canPrint) {
		if ($.inArray(fileExt.toLowerCase(),_reads) > -1){
			xtd.append($("<div class='upload_opt_icon upload_opt_print'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.print"]+"</i></span></div>").click(function(){
				self.printDoc(file.fdId);
			}));
		}
	}
	return xtd;
}

function createEditFileTr(file){
	var fileName = file.fileName.substring(0, file.fileName.lastIndexOf("."));
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	var fileIcon = window.GetIconClassByFileName(file.fileName);
	var xtr = $("<div id='"+file.fdId+"' class='upload_list_tr upload_list_tr_edit'></div>");
	var xtd_l = $("<div class='upload_list_tr_edit_l'></div>");
	var xtd_r = $("<div class='upload_list_tr_edit_r'></div>");
	
	xtd_l.append("<div class='upload_list_icon'><i class='"+fileIcon+"'></i></div>");
	xtd_l.append("<div class='upload_list_filename_edit' title='"+file.fileName+"'><span class='upload_list_filename_title' style='max-width: 120px;'>"+fileName+"</span><span class='upload_list_filename_ext'>"+fileExt+"</span></div>");  
	xtd_l.append("<div class='upload_list_size'>"+self.formatSize(file.fileSize)+"</div>");
	
	xtd_r.append("<div class='upload_list_progress_img upload_item_hide'></div>");
	xtd_r.append("<div class='upload_list_progress_text upload_item_hide'></div>");
	xtd_r.append($("<div class='upload_list_status'></div>"));
	xtd_r.append(createFileOpers(file).append(getStatus(file)));
	
	xtr.append(xtd_l);
	xtr.append(xtd_r);
	self.showDragTip();
	return xtr;
}
function getStatus(file, obj){
	var xbtn = $("<div></div>");
	if(file.fileStatus == -1) {
		// 重新上传
		xbtn.append($("<div class='upload_opt_icon upload_opt_reupload'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.reupload"]+"</i></span></div>").click(function(){
			self.uploadObj.retry(obj);
		}));
	}
	// 删除
	xbtn.append($("<div class='upload_opt_icon upload_opt_delete'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.delete"]+"</i></span></div>").click(function(){
		seajs.use(['lui/dialog'], function(dialog) {
			dialog.confirm(Attachment_MessageInfo["button.confimdelte"], function(value) {
				if(value) {
					if(file.fileStatus != -1){
						file.fileStatus = -1;
						file.isDelete = -1;
						$("#"+file.fdId).remove();
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
					}
					self.showDragTip();
				}
			});
		});
	}));
	return xbtn;
}
if(this.editMode=='view'){
	//查看时不需要绑定上传时间
}else{
	//this.off();
	this.on("uploadCreate",function(data){
		var file = data.file;
		$('#att_xtable_'+self.fdKey+'').append(createEditFileTr(file));
	});
	this.on("uploadStart", function(data){
		var file = data.file; 
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_size_r").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_progress_img").append("<div class='upload_progress_border'><div class='upload_progress_val'></div></div>");
		$("#"+file.fdId).find(".upload_list_progress_text").append("<div class='upload_progress_text'>"+Attachment_MessageInfo["button.progress"]+"0%</div>");
		$("#"+file.fdId).find(".upload_list_operation").html(
			$("<div class='upload_opt_icon upload_opt_cancel'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.cancelAll"]+"</i></span></div>").click(function(e){
				seajs.use(['lui/dialog'], function(dialog) {
					dialog.confirm(Attachment_MessageInfo["button.confirmCancel"], function(value) {
						if(value) {
							file.fileStatus = -1;
							file.isDelete = -1;
							self.uploadObj.cancelFile(file.fdId);
							$("#"+file.fdId).remove();
							self.emit('editDelete',{"file":file});
						}
					});
				});
		}));
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
		$("#"+file.fdId).find(".upload_progress_val").css("width",percent+"%");
		$("#"+file.fdId).find(".upload_progress_text").html(parseInt(percent)+"%");
		$("#" + file.fdId).find(".upload_list_size").html(cur + "/" + total);
	});
	this.on("uploadSuccess", function(data){
		var file = data.file;
		var serverData = data.serverData; 
		if(file._fdId != file.fdId){
			$("#"+file._fdId).attr("id",file.fdId);
		}	
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_size_r,.upload_list_operation").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").empty();
		var success = "<div class='upload_opt_status success' title='"+Attachment_MessageInfo["msg.uploadSucess"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadSucess"]+"</div>";
		$("#"+file.fdId).find(".upload_list_status").append(success);
		var read = "";
		var alter = "";
		if(self.uploadAfterSelect) {
			read = $("<div class='upload_opt_icon upload_opt_view'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.read"]+"</i></span></div>").click(function(e){
				readDoc(file);
			});
			if (typeof(seajs) != 'undefined' && self.checkEditAuth(file.fdId)) {
				alter = $("<div class='upload_opt_icon upload_opt_alter_name'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.rename"]+"</i></span></div>").click(function(e){
					window.alterName(file.fdId, self);
				});
			}
		}
		$("#"+file.fdId).find(".upload_list_operation").append(read).append(alter).append(getStatus(file, data._file));
		$("#" + file.fdId).find(".upload_list_size").html(self.formatSize(file.fileSize));
		self.resizeAllUploader();
	});
	this.on("uploadFaied", function(data){
		var file = data.file;
		var serverData = data.serverData;	
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_size_r,.upload_list_operation").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").empty();
		var fail = "<div class='upload_opt_status fail' title='"+Attachment_MessageInfo["msg.uploadFail"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadFail"]+"</div>";
		$("#"+file.fdId).find(".upload_list_status").html(fail);
		file.fileStatus = -1;
		$("#"+file.fdId).find(".upload_list_operation").html(getStatus(file, data._file));
		$("#" + file.fdId).find(".upload_list_size").html(self.formatSize(file.fileSize));
		self.resizeAllUploader();
		alert(serverData);
	});
	
	this.on("error", function(data){
		var file = data.file;
		var fileName = file.fileName;
		var serverData = data.serverData;	
		if("Q_EXCEED_SIZE_LIMIT"==serverData){
		   $("#"+file.fdId).remove();
		alert(Attachment_MessageInfo["error.exceedMaxSize"].replace("{0}", self.totalMaxSize+'MB'));
		}else if("F_EXCEED_SIZE"==serverData){
		   $("#"+file.fdId).remove();
			alert(Attachment_MessageInfo["error.exceedSingleMaxSize"].replace("{0}",fileName).replace("{1}",self.smallMaxSizeLimit+' MB'));
		}else{
			$("#"+file.fdId).remove();
			alert(Attachment_MessageInfo["error.enabledFileType"].replace("{0}", self.enabledFileType));
		}
		});
	this.on("uploadError", function(data){
		var file = data.file;
		var errorCode = data.errorCode;
		var message = data.message;
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").addClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_size_r,.upload_list_operation").removeClass("upload_item_hide");
		$("#"+file.fdId).find(".upload_list_operation").empty();
		if(errorCode == -280){
			$("#"+file.fdId).find(".upload_list_status").html(Attachment_MessageInfo["button.cancelupload"]);
		}else{
			$("#"+file.fdId).find(".upload_list_status").html(Attachment_MessageInfo["msg.uploadFail"]);
		}
		$("#"+file.fdId).find(".upload_list_operation").html(getStatus(file, data._file));
		
		file.fileStatus = -1;
		alert(message);
	});
}