Com_RegisterFile("cropper_material.js");
Com_IncludeFile("jquery.js");
Com_IncludeFile('ckresize.js', 'ckeditor/');
Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath + "style/common/fileIcon/", "js", true);
Com_IncludeFile("upload.css", Com_Parameter.ContextPath + "sys/attachment/view/img/", "css", true);
Com_IncludeFile("base64.js", Com_Parameter.ContextPath + "sys/attachment/js/", "js", true);
Com_IncludeFile("sysAttMain_MessageInfo.jsp?locale=" + Com_Parameter.__sysAttMainlocale__, Com_Parameter.ContextPath + "sys/attachment/sys_att_main/", 'js', true);
Com_IncludeFile("webuploader.min.js", Com_Parameter.ContextPath + "sys/attachment/webuploader/", "js", true);
Com_IncludeFile("swf_attachment.js");

/*************************************
 * 附件对象，用于附件机制上传和下载
 *************************************/
function cropper_Att_Material(_fdKey, _fdModelName, _fdModelId,config) {
    //-----------------------------------------------init --------------------------------------------------//
    //继承附件对象
    var __swfAttObj = new Swf_AttachmentObject(_fdKey, _fdModelName, _fdModelId, "",
        "pic", "edit", false,
        "gif,jpg,jpeg,bmp,png,tif", false, "", config, "", true);

    // 引入附件对象的局部变量
    var extendConfig = {
        methodName: 'POST',
        isSupportDirect: false,
        fileVal: __swfAttObj.getFileVal(),
        uploadurl: '/sys/attachment/uploaderServlet?gettype=upload&format=json'
    };
    $.extend(extendConfig, config);

    __swfAttObj.renderId = __swfAttObj.fdKey + "_renderId"; //附件列表显示html区域ID




    /*****************************************
    功能：显示附件内容方法
    参数：显示位置Dom元素ID
    *****************************************/
    __swfAttObj.show = function() {
        if (__swfAttObj.showed)
            return;
        if (__swfAttObj.recalcRightInfo) {
            __swfAttObj.recalcRightInfo();
        }
        if (!__swfAttObj.btnIntial) {
            __swfAttObj.showButton(function() {
                if (__swfAttObj.drawFunction != null) {
                    __swfAttObj.drawFunction(__swfAttObj);
                } else {
                    __swfAttObj.emit("buildRender", __swfAttObj);
                    var renderExe = function() {
                        try {
                            __swfAttObj.renderFn.apply(__swfAttObj, [$, function(dom) {
                                var rlist = $("#" + __swfAttObj.renderId);
                                if (dom)
                                    rlist.append(dom);
                                if (__swfAttObj.showAfterCustom) {
                                    __swfAttObj.showAfterCustom();
                                }
                                __swfAttObj.showed = true;
                            }]);
                        } catch (e) {
                            if (window.console) window.console.error(e.stack);
                        }
                        // 构建图片预览
                        __swfAttObj.createImageView($("#" + __swfAttObj.renderId).find("[data-lui-mark='attachmentlist']"));
                    };
                    if (__swfAttObj.renderFn == null) {
                        $.ajax(__swfAttObj.renderurl, {
                            dataType: 'text',
                            type: 'GET',
                            success: function(result) {
                                __swfAttObj.renderFn = new Function('$', 'done', result);
                                renderExe();
                            },
                            error: function(xhr, status, errorInfo) {
                                if (window.console) window.console.error(errorInfo);
                            }
                        });
                    } else {
                        renderExe();
                    }
                }
            });
        }
    };


    /*************************************
     * 与直连相关的方法--开始
     *************************************/

    // 由于图片上传完后需要立马看到，所以在上传成功后需要构建sysAttMain对象
    __swfAttObj.buildPicAttMain = function(doc, resObj) {
        doc.fdId = __swfAttObj.createAttMainInfo(doc);
        if (doc.fdId == '' || doc.fdId == null) {
            doc.fileStatus = 0;
            try {
                __swfAttObj.emit("uploadFaied", { "file": doc });
            } catch (e) {}
        } else {
            doc.fileStatus = 1;
            try {
                __swfAttObj.emit("uploadSuccess", { "file": doc, "serverData": resObj._raw, "uploadAfterSelect": false, "renderId": __swfAttObj.renderId });
                if (top.window.previewEvn) {
                    top.window.previewEvn.emit("uploadSuccess", { "file": doc, "swfObj": __swfAttObj });
                }
            } catch (e) {}
        }
    };

    /*****************************************
     * 初始化上传事件监听 #eventInit
     *****************************************/

    __swfAttObj.createImageView = function() {
        for (var i = 0; i < __swfAttObj.fileList.length; i++) {
            if (__swfAttObj.fileList[i].fileStatus == 1 &&
                __swfAttObj.fileList[i].fdId.indexOf("WU_FILE") == -1) {
                __swfAttObj.imageList.push(__swfAttObj.fileList[i]);
            }
        }
        if (__swfAttObj.imageList.length > 0) {
            for (var i = 0; i < __swfAttObj.imageList.length; i++) {
                var image = __swfAttObj.imageList[i];
                $("#att_id").val(image.fdId)
                
            }
        }

    };
    __swfAttObj.finished = false;
    __swfAttObj.__uploadFinished = function() {
        __swfAttObj.finished = true;
        __swfAttObj.createImageView();
        console.log(__swfAttObj.finished)
    };
    __swfAttObj.getFinishedId = function(){
    	var id = "";
    	 for (var i = 0; i < __swfAttObj.fileList.length; i++) {
             if (__swfAttObj.fileList[i].fileStatus == 1 &&
                 __swfAttObj.fileList[i].fdId.indexOf("WU_FILE") == -1) {
                 __swfAttObj.imageList.push(__swfAttObj.fileList[i]);
             }
         }
         if (__swfAttObj.imageList.length > 0) {
             for (var i = 0; i < __swfAttObj.imageList.length; i++) {
                 var image = __swfAttObj.imageList[i];
                 id=(image.fdId)
             }
         }
         return id;
    	}
    	return __swfAttObj

};

