var self = this;
var _reads = File_EXT_READ.split(";");
var _videos = File_EXT_VIDEO.split(";");
var _mp3s = File_EXT_MP3.split(";");
var _edits = File_EXT_EDIT.split(";");
var _noPrints = File_EXT_NO_PRINT.split(";");
var _noReads = File_EXT_NO_READ.split(";");

if(this.fdViewType=="byte") { 
	var xtable = $("<table style='border:none' id='att_xtable_"+this.fdKey+"'></table>");
	if(this.editMode=='view'){
		//查看视图
		if(this.fileList.length > 0){
			if(this.fileList.length > 1){
				xtable.append(createViewOperation());
			}
			for (var i=0;i<this.fileList.length;i++){
				xtable.append(createViewFileTr(this.fileList[i]));
			}
		}
	}else{
		//编辑视图
		for (var i=0;i<this.fileList.length;i++){
			xtable.append(createEditFileTr(this.fileList[i]));
		}
	}
	done(xtable);
}

/** 编辑视图 开始 **/
function createEditFileTr(file){
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	var fileNameWidoutExt = file.fileName.substring(0, file.fileName.lastIndexOf("."));
	var fileIcon = window.GetIconNameByFileName(file.fileName);
	var xtr = $("<tr id='"+file.fdId+"' class='upload_list_tr'></tr>");
	xtr.append("<td class='upload_list_icon'><img src='"+Com_Parameter.ResPath+"style/common/fileIcon/"+fileIcon+"' height='16' width='16' border='0' align='absmiddle' style='margin-right:3px;' /></td>");
	xtr.append("<td class='upload_list_filename_edit'>"+Com_HtmlEscape(file.fileName)+"</td>");  
	xtr.append("<td class='upload_list_progress_img' style='display:none'></td>");
	xtr.append("<td class='upload_list_progress_text' style='display:none'></td>");
	xtr.append("<td class='zone_upload_list_size'>("+self.formatSize(file.fileSize)+")</td>");
	//加入操作列
	xtr.append(createFileOpers(file));
	
	
	//定制新增的输入框，填写用户登录名，只能采用内联样式，因为tr tb都被公用样式占用了
	xtr.append("<td style='padding-left: 40px;'>登录名：</td>")
	xtr.append("<td class='zone_multi_resume_input'><input subject='登录名' validate='required maxLength(200) isExist' name='fdLoginNames' type='text' class='zone_multi_resume_text' value='" 
				+ Com_HtmlEscape(fileNameWidoutExt) +"'/><span class='txtstrong'>*</span></td>")
	
	xtr.append($("<td class='upload_list_status'></td>").append(getStatus(file)));			
	return xtr;
}
function getStatus(file){
	if(file.fileStatus != 0){//上传成功,失败，错误
		return $("<div class='upload_status com_btn_link'>"+Attachment_MessageInfo["button.delete"]+"</div>").click(function(){
			seajs.use(['lui/dialog'], function(dialog) {
				dialog.confirm(""+Attachment_MessageInfo["button.confimdelte"]+"", function(value) {
					if(value) {
						file.fileStatus = -1;
						$("#"+file.fdId).remove();
						// 编辑状态下删除发送事件
						self.emit('editDelete',{"file":file});
					}
				});
			});
		});
	}
}
if(this.editMode != 'view'){
	//this.off();
	this.on("uploadCreate",function(data){
		var file = data.file;
		$('#att_xtable_'+self.fdKey+'').append(createEditFileTr(file));
	});
	this.on("uploadStart", function(data){
		var file = data.file; 
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").show();
		$("#"+file.fdId).find(".upload_list_size,.upload_list_operation").hide();
		$("#"+file.fdId).find(".upload_list_progress_img").append("<div class='upload_progress_border'><div class='upload_progress_val'></div></div>");
		$("#"+file.fdId).find(".upload_list_progress_text").append("<div class='upload_progress_text'>"+Attachment_MessageInfo["button.progress"]+"0%</div>");

	});
	this.on("uploadProgress", function(data){
		var file = data.file;
		// var bytesLoaded = data.bytesLoaded;
		// var bytesTotal = data.bytesTotal;
		// var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		// 进度，之前进度计算方式存在问题都是undefine
		var percent = Math.ceil(data.totalPercent * 100);
		$("#"+file.fdId).find(".upload_progress_val").css("width",percent+"%");
		$("#"+file.fdId).find(".upload_progress_text").html(""+Attachment_MessageInfo["button.progress"]+""+percent+"%");
	});
	this.on("uploadSuccess", function(data){
		var file = data.file;
		var serverData = data.serverData; 
		if(file.id != file.fdId){
			$("#"+file.id).attr("id",file.fdId);
		}		
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").hide();
		$("#"+file.fdId).find(".upload_list_size,.upload_list_operation").show();
		$("#"+file.fdId).find(".upload_list_operation").empty();
		$("#"+file.fdId).find(".upload_list_operation").html(Attachment_MessageInfo["msg.uploadSucess"]);
		$("#"+file.fdId).find(".upload_list_status").html(getStatus(file));
	});
	this.on("uploadFaied", function(data){
		var file = data.file;
		var serverData = data.serverData;	
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").hide();
		$("#"+file.fdId).find(".upload_list_size,.upload_list_operation").show();
		$("#"+file.fdId).find(".upload_list_operation").empty();
		$("#"+file.fdId).find(".upload_list_operation").html(Attachment_MessageInfo["msg.uploadFail"]);
		$("#"+file.fdId).find(".upload_list_status").html(getStatus(file));
		file.fileStatus = -1;
		alert(serverData);
	});
	this.on("uploadError", function(data){
		var file = data.file;
		var errorCode = data.errorCode;
		var message = data.message;
		$("#"+file.fdId).find(".upload_list_progress_img,.upload_list_progress_text").hide();
		$("#"+file.fdId).find(".upload_list_size,.upload_list_operation").show();
		$("#"+file.fdId).find(".upload_list_operation").empty();
		if(errorCode == -280){
			$("#"+file.fdId).find(".upload_list_operation").html(Attachment_MessageInfo["button.cancelupload"]);
		}else{
			$("#"+file.fdId).find(".upload_list_operation").html(Attachment_MessageInfo["msg.uploadFail"]);
		}
		$("#"+file.fdId).find(".upload_list_status").html(getStatus(file));
		file.fileStatus = -1;
		//alert($("#"+file.fdId).find(".upload_list_operation").html());
	});
}




//****************以下为查看视图需要的，这里不用重写，也用不着和render_byte.js一样 ***************************//
/** 查看视图 开始 **/
function createViewOperation(){
	var xtr = $("<tr ></tr>"); 
	var xtd = $("<td colspan='5' class='upload_opt_td'></td>");
	if (self.canDownload) {
		xtd.append($("<div class='upload_opt_batchdown'>"
				+ Attachment_MessageInfo["button.batchdown"] + "</div>").click(
				function() {
					self.downloadFiiles();
				}));
	}else{
		xtd.css('display','none');
	}
	return xtr.append(xtd);
}
function createViewFileTr(file){
	var fileIcon = window.GetIconNameByFileName(file.fileName);
	var xtr = $("<tr id='"+file.fdId+"' class='upload_list_tr'></tr>");
	xtr.append("<td class='upload_list_icon'><img src='"+Com_Parameter.ResPath+"style/common/fileIcon/"+fileIcon+"' height='16' width='16' border='0' align='absmiddle' style='margin-right:3px;' /></td>");
	// 默认执行第一个操作按钮的点击事件
	var fileExt = file.fileName.substring(file.fileName.lastIndexOf("."));
	xtr
			.append($("<td class='upload_list_filename_view' style='cursor: pointer'>" + file.fileName
					+ "</td>").click(function() {
						if (self.canRead) {
							readDoc(file);
							return;
						}
						if (self.canDownload) {
							self.downDoc(file.fdId);
							return;
						}
						if (self.canEdit) {
							if ($.inArray(fileExt.toLowerCase(),_edits) > -1) {
								self.editDoc(file.fdId);
								return;
							}
						}
						if (self.canPrint) {
							self.printDoc(file.fdId);
							return;
						}
					}));  
	//加入操作列
	xtr.append("<td class='upload_list_size'>("+self.formatSize(file.fileSize)+")</td>");
	xtr.append(createFileOpers(file));
	return xtr;
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
	//只能采用内联样式，因为class不能变（js中有各种调用）
	var xtd = $("<td class='upload_list_operation' style='margin: 10px;'></td>");
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
			xtd.append($("<div class='upload_opt_view' title='"+text+"'></div>").click(function(){
				readDoc(file);
			}));
		}
	}
	if (self.canDownload) {
		xtd.append($("<div class='upload_opt_down' title='"+Attachment_MessageInfo["button.download"]+"'></div>").click(function(){
			self.downDoc(file.fdId);
		}));
	}
	if (self.canEdit) {
		if ($.inArray(fileExt.toLowerCase(),_edits) > -1){
			xtd.append($("<div class='upload_opt_edit' title='"+Attachment_MessageInfo["button.edit"]+"'></div>").click(function(){
				self.editDoc(file.fdId);
			}));
		}
	}
	if (self.canPrint) {
		if ($.inArray(fileExt.toLowerCase(),_noPrints) < 0){
			if ($.inArray(fileExt.toLowerCase(),_reads) > -1){
				xtd.append($("<div class='upload_opt_print' title='"+Attachment_MessageInfo["button.print"]+"'></div>").click(function(){
					self.printDoc(file.fdId);
				}));
			}
		}
	}
	return xtd;
}
/** 查看视图 结束 **/