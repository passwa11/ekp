var self = this;

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
$(document).on("resize", function() {self.resizeCount = 0;self._resizeAllUploader();});

var xparent = $("<div id='att_xtable_"+this.fdKey+"'></div>");
	//编辑视图
for (var i=0;i<this.fileList.length;i++) {
	//xtable.append(createCheckOperation(this.fileList[i]));
	xparent.append(createEditFileTr(this.fileList[i]),true);
}
done(xparent);

function checkUploadCheckAll(temp){
	var len =  $('.upload_list_tr_edit_block').length;
	if(len == 0){
		$('.upload_list_title').find('input[type=checkbox]').attr('checked', false);
		$('.upload_list_title').find('.upload_opt_select').text('全选');
		if(temp){
		  $('.upload_list_title').hide();
		}else{
			$('.upload_list_title').show();
		}
	}else {
		$('.upload_list_title').show();
	}
}

function readDoc(file) {
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	if (File_EXT_READ.indexOf(fileExt.toLowerCase()) > -1) {
		self.readDoc(file.fdId);
	} else if (File_EXT_VIDEO.indexOf(fileExt.toLowerCase()) > -1) {
		if (self.editMode == 'view' && self.fdModelName == 'kmsMultimediaMain') {
			self.startVideo(file.fdId);
		}
	} else if (File_EXT_MP3.indexOf(fileExt.toLowerCase()) > -1) {
		if (self.editMode == 'view' && self.fdModelName == 'kmsMultimediaMain') {
			self.startMp3(file.fdId);
		}
	} else {
		self.openDoc(file.fdId);
	}
}

function createFileOpers(file){
	var xtd = $("<div class='upload_list_operation'></div>");
	if(!file.fileKey) {return xtd;}
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	var attId = file.fdTemplateAttId || file.fdId;
	if (self.canRead) {
		var text = "";
		if (File_EXT_READ.indexOf(fileExt.toLowerCase()) > -1){
			text = (Attachment_MessageInfo["button.read"]);
		}else if(File_EXT_VIDEO.indexOf(fileExt.toLowerCase()) > -1){
			if(self.editMode=='view' && self.fdModelName=='kmsMultimediaMain'){
				text = ""+Attachment_MessageInfo["button.play"]+"";
			} 
		}else if(File_EXT_MP3.indexOf(fileExt.toLowerCase()) > -1){
			if(self.editMode=='view' && self.fdModelName=='kmsMultimediaMain'){
				text = ""+Attachment_MessageInfo["button.play"]+"";
			}
		}else{
			text = (Attachment_MessageInfo["button.open"]);
		}
		xtd.append($("<div class='upload_opt_view' title='"+text+"'></div>").click(function(){
			readDoc(file);
		}));
	}
	if (self.canDownload) {
		xtd.append($("<div class='upload_opt_down' title='"+Attachment_MessageInfo["button.download"]+"'></div>").click(function(){
			self.downDoc(file.fdId);
		}));
	}
	if (self.canEdit) {
		if (File_EXT_EDIT.indexOf(fileExt.toLowerCase()) > -1){
			xtd.append($("<div class='upload_opt_edit' title='"+Attachment_MessageInfo["button.edit"]+"'></div>").click(function(){
				self.editDoc(file.fdId);
			}));
		}
	}
	if (self.canPrint) {
		if (File_EXT_READ.indexOf(fileExt.toLowerCase()) > -1){
			xtd.append($("<div class='upload_opt_print' title='"+Attachment_MessageInfo["button.print"]+"'></div>").click(function(){
				self.printDoc(file.fdId);
			}));
		}
	}
	return xtd;
}
/** 查看视图 结束 **/
function createCheckOperation(){
	var checkText=$('.upload_opt_select');
	if(checkText==undefined||checkText.text()=='')
	{	
		//var xtr = $("<tr ></tr>"); 
		//var xtd = $("<td colspan='5' class='upload_opt_td'></td>");
		var xul = $("<div class='upload_list_title'></div>"); 
		var xli = $("<div class='upload_opt_td'></div>");
		xli.append($("<div class='upload_opt_checkbox' name='upload_opt_checkbox'style='width:100px'></div>").append($("<LABEL><input type='checkbox' /><span class='upload_opt_select'>"+Attachment_MessageInfo["button.selectAll"]+"</span></LABEL>").click(function(){
			if($(this).find(":checkbox").is(":checked")){
				$(this).find("span").html(Attachment_MessageInfo["button.cancelAll"]);
				$('#att_xtable_'+self.fdKey+'').find(".upload_list_checkbox :checkbox").prop("checked",true);
			}else{			
				$(this).find("span").html(Attachment_MessageInfo["button.selectAll"]);
				$('#att_xtable_'+self.fdKey+'').find(".upload_list_checkbox :checkbox").prop("checked",false);
			}
		})));
		return xul.append(xli);
	}
}


/** 编辑视图 开始 **/
function createEditFileTr(file, isView){
	if(file.isDelete == -1)
		return;
	var fileName = file.fileName.substring(0, file.fileName.lastIndexOf("."));
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	var fileIcon = window.GetIconClassByFileName(file.fileName);
	var maxLenght=file.fileName.lastIndexOf('.')>199?199:file.fileName.lastIndexOf('.');
	var attFileName=Com_HtmlEscape(file.fileName.substring(0,maxLenght));
	var eachAttIdAndNameJson=file.fdId+';'+attFileName;
	
	var xtr = $("<div id='"+file.fdId+"' class='upload_list_tr upload_list_tr_edit'></div>");
	var xtd_l = $("<div class='upload_list_tr_edit_l'></div>");
	var xtd_r = $("<div class='upload_list_tr_edit_r'></div>");
	xtd_l.append("<div class='upload_list_checkbox'><input type='checkbox' value='"+file.fdId+"'/></div>");
	xtd_l.append("<div class='upload_list_icon'><i class='"+fileIcon+"'></i></div>");
	xtd_l.append("<div class='upload_list_filename_edit' title='"+file.fileName+"'><span class='upload_list_filename_title'>"+Com_HtmlEscape(fileName)+"</span><span class='upload_list_filename_ext'>"+fileExt+"</span></div>");  
	xtd_l.append("<div class='upload_list_size'>"+self.formatSize(file.fileSize)+"</div>");
	xtd_r.append("<div class='upload_list_progress_img upload_item_hide'></div>");
	xtd_r.append("<div class='upload_list_progress_text upload_item_hide'></div>");
	xtr.append("<input type='hidden' id='"+file.fdId+"_idAndName' name='attIdNameJson' value='"+eachAttIdAndNameJson+"'>");
	xtr.append("<input type='hidden' name='realFileId' value='"+file.fdId+"'>");
	//保存编辑后产生的缓存formID,方便提交后去缓存查找对应的文档详细信息
	xtr.append("<input type='hidden' id='"+file.fdId+"_doc' name='mainDocId' value=''>");
	xtr.append("<input type='hidden' id='"+file.fdId+"_partDoc' name='partDoc' value=''>");
	//标示那些附件是属于批量操作的，如果为空则说明不是批量
	xtr.append("<input type='hidden' id='"+file.fdId+"_batch' name='bachOperate' value=''>");
	// 文件状态
	var status = $("<div class='upload_list_status'></div>");
	if(file.fileStatus == 1) {
		var success = "<div class='upload_opt_status success' title='"+Attachment_MessageInfo["msg.uploadSucess"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadSucess"]+"</div>";
		status.html(success);
	}
	xtd_r.append(status);
	
	xtr.append($("<input type='text' id='"+file.fdId+"_fileName' name='inputFileName' style='width:150px;display:none;'  value='"+attFileName+"'>").change(function(){
		
	}));
	
	//加入操作列
	//xtd_r.append(createFileOpers(file));
	xtd_r.append(createFileOpers(file).append(getDeleteOpt(file)));
	
	//xtd_r.append("<div style='width:50px;text-align:center'><a href='javascript:void(0)' name='"+file.fdId+"_edit' id='"+eachAttIdAndNameJson+"' onclick='redirectEditPage(this)'>"+Attachment_MessageInfo['button.edit']+"</a></div>");
	//xtd_r.append("<div style='width:50px;text-align:center'><a href='javascript:void(0)' name='"+file.fdId+"_delete'  data-attr='"+file.fdId+"'  id='"+eachAttIdAndNameJson+"' onclick='redirectDeletePage(this)'>"+Attachment_MessageInfo['button.delete']+"</a></div>");
	
	
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
		xbtn.append($("<div class='upload_opt_icon upload_opt_delete'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.delete"]+"</i></span></div>").click(function(e){
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.confirm(Attachment_MessageInfo["button.confimdelte"], function(value) {

					if(value) {
						if(file.fileStatus != -1){
							$("#uploader_" + self.fdKey + " .uploader").removeClass("upload_item_hide");
							$("#uploader_" + self.fdKey + " .tips").addClass("upload_item_hide");
							file.fileStatus = -1;
							file.isDelete = -1;
							$("#"+file.fdId).remove();
							//self.delFileList(file.fdId);
							// 编辑状态下删除发送事件
							self.emit('editDelete',{"file":file});
							//if(top.window.previewEvn){
							//	top.window.previewEvn.emit('editDelete',{"file":file});
							//}
						}else{
							file.isDelete = -1;
							$("#"+file.fdId).remove();
							self.emit('editDelete',{"file":file});
							$("#uploader_" + self.fdKey + " .uploader").removeClass("upload_item_hide");
							$("#uploader_" + self.fdKey + " .tips").addClass("upload_item_hide");
							self.showDragTip();
						}
						checkUploadCheckAll("true");
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
						self.uploadObj.cancelFile(file.fdId);
						self.delFileList(file.fdId);
						// 编辑状态下删除发送事件
						self.emit('editDelete',{"file":file});
						if(top.window.previewEvn){
							top.window.previewEvn.emit('editDelete',{"file":file});
						}
						checkUploadCheckAll();
					}
				});
			});
		});
	}
}

if(this.editMode=='view'){
	
	
	//查看时不需要绑定上传时间
}else if(this.initMode){
	//this.off();
	this.on("uploadCreate",function(data){
		var file = data.file;
		checkUploadCheckAll();
		$('#att_xtable_'+self.fdKey+'').append(createCheckOperation(file));
		$('#att_xtable_'+self.fdKey+'').append(createEditFileTr(file, false));
	});
	this.on("uploadStart", function(data){
		checkUploadCheckAll();
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
		var read = "", moveup = "", movedown = "", alterName = "",editHref="";
		
		if (typeof(seajs) != 'undefined') { //sysAttMain_edit_swf.jsp reName
			alterName = $("<div class='upload_opt_icon upload_opt_alter_name'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.rename"]+"</i></span></div>").click(function(e){
				/*if(window.alterName){
					window.alterName(file.fdId,self);
				} else {
					self.alterName(file.fdId,self);
				}*/
				
				reNameInKms(file.fdId,self);
				
				if(e.preventDefault) {
					e.preventDefault();
				} else {
					window.event.returnValue == false;
				}
			});
		}
		
		if (typeof(seajs) != 'undefined') { //editHref
			editHref = $("<div class='upload_opt_icon upload_opt_edit'><span class='upload_opt_tip'><i class='upload_opt_tip_arrow'></i><i class='upload_opt_tip_inner'>"+Attachment_MessageInfo["button.edit"]+"</i></span></div>").click(function(e){
				redirectEditPageByfile(file);
			});
		}
		
		
		var success = "<div class='upload_opt_status success' title='"+Attachment_MessageInfo["msg.uploadSucess"]+"'><i></i>"+Attachment_MessageInfo["msg.uploadSucess"]+"</div>";
		$("#"+file.fdId).find(".upload_list_status").removeClass("upload_item_hide").append(success);
		$("#"+file.fdId).find(".upload_list_operation").append(read).append(moveup).append(movedown).append(alterName).append(editHref).append(getDeleteOpt(file, data._file));
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