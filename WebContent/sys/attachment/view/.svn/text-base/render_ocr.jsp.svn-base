var self = this;
 var ___width = self.fdPicContentWidth ? self.fdPicContentWidth.indexOf('%') > 0
	? self.fdPicContentWidth
	: parseInt(self.fdPicContentWidth) : 160, ___height = self.fdPicContentHeight
	? self.fdPicContentHeight.indexOf('%') > 0
			? self.fdPicContentHeight
			: parseInt(self.fdPicContentHeight)
	: 116;
if (this.fdViewType == "pic_kmsKmaps") {
	var xdiv = $("");
	if (this.editMode == 'view') {
		// 查看视图
		if (this.fileList.length > 0) {
			for (var i = 0; i < this.fileList.length; i++) {
				$("#attachmentObject_" + self.fdKey + "_content_div").prepend(createViewFileDiv(this.fileList[i]));
			}
		}
	} else {
		// 编辑视图
		for (var i = 0; i < this.fileList.length; i++) {
			$("#attachmentObject_" + self.fdKey + "_content_div").prepend(createEditFileDiv(this.fileList[i]));
		}
	}
	done(xdiv);
}

/** 查看视图 开始 * */
function createViewFileDiv(file) {
	var xdiv = $("<div id='" + file.fdId
			+ "' class='lui_upload_img_item'></div>");
	var xbox = $("<div class='imgbox'  />");
	xdiv.append(xbox);
	var imgExtend = "";
	if (self.fdImgHtmlProperty != null && self.fdImgHtmlProperty != "")
		imgExtend = self.fdImgHtmlProperty;

	var __href = self.getUrl("download", file.fdId);
	var $___img = $("<img  src=\"" + __href + "\"   onclick=\"location.href='"+__href+"'\" "+imgExtend+" border=0 >");
	xbox.append($___img);
	$___img.each(function(){
		 var img = $(this);
		 img.load(function(evt){
			 var ___w2h = resizeImg();
			 xbox.css(___w2h);
			 $___img.css(___w2h);
			self.emit('imgLoaded', {
				target : this
			});
			if (self.fdShowMsg == false)
				return;
		 });
		 if(self.fdShowMsg != false){
		    var name_msg =$("<p class='name upload_pic_filename_view' title='"+file.fileName+"' style='width:"+___width+"px;text-align:center;'>"+ file.fileName + "</p>");
		    var size_msg =$("<p class='size'>"+self.formatSize(file.fileSize)+ "</p>");
		    xdiv.append(name_msg).append(size_msg);
		    
		 }
	});
	return xdiv;
}

/** 查看视图 结束 * */

/** 编辑视图 开始 * */
function createEditFileDiv(file) {
	var uploadDiv =$("#" + self.renderId).find('[data-lui-mark="attachmentlist"]');
	var xdiv = $("<div id='" + file.fdId
			+ "' class='lui_upload_img_item'></div>");
	var xbox_div = $("<div class='imgbox_div'  />");
	xdiv.append(xbox_div);	
	var xbox = $("<div class='imgbox'  />");
	xbox_div.append(xbox);
	var $___img = $("<img src=\"" + self.getUrl("download", file.fdId)
			+ "\"  border=0 >");
	xbox.append($___img);
	$___img.load(function(evt) {
				var ___w2h = resizeImg();
				xbox.css(___w2h);
				$___img.css(___w2h);
			})
	var btn = $("<div class='imgbox_change_btn' style='display: inline-block;min-width: 70px;border: 1px solid #DDDDDD; border-radius: 2px;height: 31px;margin-left: 10px;line-height: 31px;cursor: pointer;margin-bottom: 5px;text-align: center;text-align: center;vertical-align: bottom;'>" 
		+ Data_GetResourceString("kms-kmaps:kmsKmaps.sysAttMain.button.cover.change") + "</div>");
	btn.click(function(){
		self.___changeImg(file, uploadDiv);
	});
	xbox_div.append(btn);
	if(self.fdShowMsg != false){
		    var msg =$("<p class='name' title='"+file.fileName+"' style='width:"+___width+"px'>"+ file.fileName + "</p>");
		    xdiv.append(msg);
	}		
	return xdiv;
}

function uploadCreateFileDiv(file) {
	var xdiv = $("<div id='" + file.fdId
			+ "' class='lui_upload_img_item  lui_upload_img_running' ></div>");
	var xbox_div = $("<div class='imgbox_div'  />");
	xdiv.append(xbox_div);	
	var ibox =$("<div class='imgbox' />");
	xbox_div.append(ibox);
	var ___w2h = resizeImg();
	ibox.css(___w2h);
	var $___name = $("<p class='name' title='"+file.fileName+"' style='width:"+___width+"px'>"+file.fileName+"</p>");
	xdiv.append($___name);
	return xdiv;
}


function resizeImg() {
		return {
		width : ___width,
		height : ___height 
	};
}
function hideInIE() {
		return {
		width : 0,
		height : 0 
	};
}
function showInIE() {
		return {
		width : '',
		height :''
	};
}


// 变更图片
self.___changeImg = function(file,uploadDiv) {
    var webuploader =  uploadDiv.find(".webuploader-element-invisible");
    webuploader.change(function(e){
    	self.___deleteCurrentImg(file);
    });
    webuploader.click();     
	
}

self.___deleteCurrentImg = function(file){
 	file.fileStatus = -1;
 	$("#"+file.fdId).remove();
    self.emit('editDelete', {"file" : file});
}

// 删除图片
self.___delImg = function(file,uploadDiv) {
	if (typeof(seajs) != 'undefined') {
		seajs.use('lui/dialog', function(___dialog) {
					___dialog.confirm(
							Attachment_MessageInfo["button.confimdelte"],
							function(___val) {
								if (___val) {
									file.fileStatus = -1;
									$("#"+file.fdId).remove();
                                    uploadDiv.show();
                                    uploadDiv.css(showInIE());	      
									self.emit('editDelete', {
												"file" : file
											});
								}
							});
				})
	} else {
		if (confirm("" + Attachment_MessageInfo["button.confimdelte"] + "")) {
			file.fileStatus = -1;
			$("#"+file.fdId).remove();
            uploadDiv.show(); 
            uploadDiv.css(showInIE());	     
			self.emit('editDelete', {
						"file" : file
					});
		}
	}
}

if (this.editMode == 'view') {
	// 查看时不需要绑定上传时间
} else if(this.initMode){

	this.on("md5Exists", function(data){
		var uploadDiv =$("#" + data.renderId).find('[data-lui-mark="attachmentlist"]');
        uploadDiv.hide();
	});

	this.on("uploadCreate", function(data) {
				var file = data.file;
				$("#attachmentObject_" + self.fdKey + "_content_div").prepend(uploadCreateFileDiv(file));
			});
	this.on("uploadStart", function(data) {
		var uploadDiv =$("#" + data.renderId).find('[data-lui-mark="attachmentlist"]');
        uploadDiv.hide();
		var file = data.file;
		var idiv = $("#" + file.fdId).find(".imgbox");
		var iprogress = $("<span class='lui_upload_sr_only'>0</span>");
		iprogress.css({ 'padding-top' : (___height/2-40)+'px'});
		idiv.append(iprogress);
		idiv.append("<div class='lui_upload_progress'><div class='lui_upload_progress_bar'></div></div>");
	});

	this.on("uploadProgress", function(data) {
		 var uploadDiv =$("#" + data.renderId).find('[data-lui-mark="attachmentlist"]');
	     if (navigator.userAgent.indexOf('MSIE') == -1) {
            uploadDiv.hide();
        }else{
            uploadDiv.css(hideInIE());
        }
		var file = data.file;
		var percent = data.totalPercent;
		if(percent==null){
			var bytesLoaded = data.bytesLoaded;
			var bytesTotal = data.bytesTotal;
			percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		}else{
			percent = Math.ceil(percent*100);
		}
		$("#" + file.fdId).find(".lui_upload_sr_only").html( percent +"%");
		$("#" + file.fdId).find(".lui_upload_progress_bar").css("width", percent + "%");
	});

	this.on("uploadSuccess", function(data) {
		var uploadDiv =$("#" + data.renderId).find('[data-lui-mark="attachmentlist"]');
		var file = data.file;
		var idiv =$("#" + file.id);
		idiv.removeClass(" lui_upload_img_running");
		if(file.id!=file.fdId)
		   idiv.attr("id",file.fdId);
		var ibox =idiv.find(".imgbox");
		ibox.empty();
		var imgExtend = "";
        if(self.fdImgHtmlProperty!=null && self.fdImgHtmlProperty!="")	
	       imgExtend = self.fdImgHtmlProperty;
		var $___img = $("<img src=\""
				+ self.getUrl("download", file.fdId) + "\" "+imgExtend+"  border=0 >");
		ibox.append($___img);
		var imgbox_div = idiv.find(".imgbox_div");
		var btn = $("<div class='imgbox_change_btn' style='display: inline-block;min-width: 70px;border: 1px solid #DDDDDD; border-radius: 2px;height: 31px;margin-left: 10px;line-height: 31px;cursor: pointer;margin-bottom: 5px;text-align: center;text-align: center;vertical-align: bottom;'>" 
		+ Data_GetResourceString("kms-kmaps:kmsKmaps.sysAttMain.button.cover.change") + "</div>");
		btn.click(function(){
			self.___changeImg(file, uploadDiv);
		});
	    imgbox_div.append(btn);
		$___img.bind('load', function(evt) {
					var ___w2h = resizeImg();
					ibox.css(___w2h);
					$___img.css(___w2h);
				});
		
	});
	this.on("uploadFaied", function(data) {
		var file = data.file;
		var serverData = data.serverData;
		var uploadDiv =$("#" + data.renderId).find('[data-lui-mark="attachmentlist"]');
		$("#" + file.id).prop("class","lui_upload_img_item lui_upload_fail");
		var itxt=$("<span class='lui_upload_img_txt'>"+Attachment_MessageInfo["msg.uploadFail"]+"</span>");
		var icon =	$("<i class='icon icon-fail'></i>")
		var ___w2h = resizeImg();						
        itxt.css(___w2h);
		itxt.prepend( icon);
		icon.css({
           'margin-top' : (___height/2-30)+'px'
        });
		$("#" + file.id).empty().append(itxt).append($("<span class='btn_close'><i class='icon icon-close'></i></span>")
				.click(function() {
				  $("#" + file.id).remove();
					var uploadDiv =$("#" + data.renderId).find('[data-lui-mark="attachmentlist"]');
                     uploadDiv.show();      
	                 uploadDiv.css(showInIE());	         
		}));;
});
	this.on("error", function(data){
	    var fileName = data.file.fileName || data.file.name;
		var serverData = data.serverData;	
		 $("#"+data.file.fdId).remove();
		if("Q_EXCEED_SIZE_LIMIT"==serverData){
			alert(Attachment_MessageInfo["error.exceedMaxSize"].replace("{0}", self.totalMaxSize+'MB'));
		}
		else if("F_EXCEED_SIZE"==serverData){
			
			alert(Attachment_MessageInfo["error.exceedSingleMaxSize"].replace("{0}",fileName).replace("{1}",self.smallMaxSizeLimit+' MB'));
		}
		else{
		    
			alert(Attachment_MessageInfo["error.fileType"].replace("{0}", self.enabledFileType));
		}
	});
	this.on("uploadError", function(data) {
		var file = data.file;
		var errorCode = data.errorCode;
		var message = data.message;
		$('#att_xdiv_' + self.fdKey).empty()
				.append(Attachment_MessageInfo["msg.uploadFail"]);
	});
}