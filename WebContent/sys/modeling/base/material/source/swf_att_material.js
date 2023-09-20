Com_RegisterFile("swf_att_material.js");
Com_IncludeFile("jquery.js");
Com_IncludeFile('ckresize.js', 'ckeditor/');
Com_IncludeFile("fileIcon.js", Com_Parameter.ResPath + "style/common/fileIcon/", "js", true);
Com_IncludeFile("upload.css", Com_Parameter.ContextPath + "sys/attachment/view/img/", "css", true);
Com_IncludeFile("base64.js", Com_Parameter.ContextPath + "sys/attachment/js/", "js", true);
Com_IncludeFile("sysAttMain_MessageInfo.jsp?locale=" + Com_Parameter.__sysAttMainlocale__, Com_Parameter.ContextPath + "sys/attachment/sys_att_main/", 'js', true);
Com_IncludeFile("webuploader.min.js", Com_Parameter.ContextPath + "sys/attachment/webuploader/", "js", true);
Com_IncludeFile("swf_attachment.js");

/*************************************
 * 附件对象，用于附件机制上传和下载 继承swf_attachment.js 特殊为图片素材库定制
 *************************************/
function Swf_AttObject_Material(_fdKey, _fdModelName, _fdModelId, size, config) {


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


    //预设附件对象相关值
    __swfAttObj.setSmallMaxSizeLimit(size); //附件定义最大大小M
    __swfAttObj.renderId = __swfAttObj.fdKey + "_renderId"; //附件列表显示html区域ID
    /*****************************************
     功能：显示附件内容方法
     参数：显示位置Dom元素ID
     *****************************************/
    __swfAttObj.show = function () {
        if (__swfAttObj.showed)
            return;
        if (__swfAttObj.recalcRightInfo) {
            __swfAttObj.recalcRightInfo();
        }
        if (!__swfAttObj.btnIntial) {
            __swfAttObj.showButton(function () {
                if (__swfAttObj.drawFunction != null) {
                    __swfAttObj.drawFunction(__swfAttObj);
                } else {
                    __swfAttObj.emit("buildRender", __swfAttObj);
                    var renderExe = function () {
                        try {
                            __swfAttObj.renderFn.apply(__swfAttObj, [$, function (dom) {
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
                            success: function (result) {
                                __swfAttObj.renderFn = new Function('$', 'done', result);
                                renderExe();
                            },
                            error: function (xhr, status, errorInfo) {
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

    /*****************************************
     * 上传前的校验
     *****************************************/

    __swfAttObj.beforeUpload = function (file) {
        var _fileName = file.name;
        if (file.size == 0) {
            alert(Attachment_MessageInfo["msg.fileSize.null"]);
            return false;
        }

        var fileType = null;
        if (_fileName.lastIndexOf(".") > -1) {
            fileType = _fileName.substring(_fileName.lastIndexOf("."));
            fileType = fileType.toLowerCase();
            var fileTypes = new Array();
            fileTypes = __swfAttObj.disabledFileType.split(';');
            if ("1" == __swfAttObj.fileLimitType) {

                var isPass = true;

                for (i = 0; i < fileTypes.length; i++) {
                    if (fileType == fileTypes[i]) {
                        isPass = false;
                        break;
                    }
                }

                if (!isPass) {
                    alert(Attachment_MessageInfo["msg.disableFileType"].replace("{0}", __swfAttObj.disabledFileType).replace("{1}", _fileName));
                    return false;
                }
            } else if ("2" == __swfAttObj.fileLimitType) {
                var isPass = false;

                for (i = 0; i < fileTypes.length; i++) {
                    if (fileType == fileTypes[i]) {
                        isPass = true;
                        break;
                    }
                }

                if (!isPass) {
                    alert(Attachment_MessageInfo["msg.disableFileType2"].replace("{0}", __swfAttObj.disabledFileType).replace("{1}", _fileName));
                    return false;
                }
                //				if(__swfAttObj.disabledFileType.indexOf(fileType)==-1){
                //					alert(Attachment_MessageInfo["msg.disableFileType2"].replace("{0}",__swfAttObj.disabledFileType).replace("{1}",_fileName));
                //					return false;
                //				}
            }
        }
        for (var i = 0, size = __swfAttObj.fileList.length; i < size; i++) {
            if (__swfAttObj.fileList[i].fileStatus > -1 && __swfAttObj.fileList[i].fileName == _fileName) {
                alert(Attachment_MessageInfo["msg.fail.fileName"]);
                return false;
            }
        }

        __swfAttObj.addDoc(file.name, file.id, false, '', file.size);
        return true;
    };

    __swfAttObj.createImgLi = function (file) {
        var c_main = $("#" + __swfAttObj.renderId)
        var img = __swfAttObj.getDoc(file.id);
        var c_temp = c_main.find("#formTemple");
        var c_img = c_main.find("li[fileId='" + file.id + "']");

        if (c_img.length > 0)
            c_img.remove();

        c_img = c_temp.clone(true);
        c_img.attr("fileId", file.id);
        c_img.attr("name", "imageViewContainer");

        // 创建缩略图
        // 如果为非图片文件，可以不用调用此方法。
        __swfAttObj.uploadObj.makeThumb(file, function (error, src) {
            if (error) {
                c_img.append('<span>No Preview</span>');
                return;
            }
            c_img.find(".material_upload_li_img").attr("style",
                "background-image:url(" + src + ")");
        }, 197, 107);
        c_img.show();
        c_img.attr("class", "material_upload_li material_upload_uping")

        c_main.append(c_img);
        $(".lui_material_upload_dlg ").addClass("hasIcon");
    };
    /*****************************************
     * 文件加入队列
     *****************************************/
    __swfAttObj.__fileQueued = function (file) {
        __swfAttObj.createImgLi(file)
    };

    __swfAttObj.setImgLiProgress = function (file, percentage) {
        var c_main = $("#" + __swfAttObj.renderId);
        var img = __swfAttObj.getDoc(file.id);
        var c_img = c_main.find("li[fileId='" + file.id + "']");
        var percent = percentage * 100;
        c_img.find(".progress_text").html(percent + "%");

        /*
        0%-50% left=45;right=45-225;==> 1%=3.6deg
        50%-100% left=45-225;right=225;==> 1%=3.6deg
        ex:30% right:153=45+5.6*30,left:45
    */
        var deg = {
            l: 45,
            r: 45
        }
        if (percent > 50) {
            deg.r = 225;
            deg.l = 45 + 3.6 * (percent - 50);
        } else {
            deg.r = 45 + 3.6 * percent;
            deg.l = 45;
        }

        c_img.find(".progress_wrapper_r").children(".circle_progress")
            .css({"-webkit-transform": "rotate(" + deg.r + "deg)", "-ms-transform": "rotate(" + deg.r + "deg)"})
        c_img.find(".progress_wrapper_l").children(".circle_progress")
            .css({"-webkit-transform": "rotate(" + deg.l + "deg)", "-ms-transform": "rotate(" + deg.l + "deg)"})

    }

    // 文件上传中
    __swfAttObj.__uploadProgress = function (file, percentage) {
        try {
            __swfAttObj.emit("uploadProgress", {
                "file": __swfAttObj.getDoc(file.id),
                "totalPercent": percentage,
                "renderId": __swfAttObj.renderId
            });
            __swfAttObj.setImgLiProgress(file, percentage);

        } catch (e) {
        }
    };
    //处理成功或失败的展示
    __swfAttObj.setImgLiError = function (file, reason) {
        var c_main = $("#" + __swfAttObj.renderId);
        var img = __swfAttObj.getDoc(file.id);
        var c_img = c_main.find("li[fileId='" + file.id + "']");
        var c_status = c_img.find(".material_upload_status");
        c_status.empty();

        var c_s_p = "<p>"+ Attachment_MessageInfo["sysAttMain.material.upload.error"] +"<span class='material_upload_mark_failed failedTips' onclick='showErrorMsg(\"" + file.id + "\")'>?</span></p>";
        c_status.append(c_s_p);

        var c_s_msg = " <div class='failedMsg failedMsgShow'><span class='uparrow'></span><p>reason</p></div>"


        c_status.append(c_s_msg)

        c_img.find(".progress_text").html("<span class='material_upload_mark_failed'></span>");
        c_img.attr("class", "material_upload_li material_upload_failed")
    }
    __swfAttObj.setImgLiSuccess = function (file, reason) {
        var image = __swfAttObj.getDoc(file.id);
        var c_main = $("#" + __swfAttObj.renderId);
        var img = __swfAttObj.getDoc(file.id);
        var c_img = c_main.find("li[fileId='" + file.id + "']");
        var c_status = c_img.find(".material_upload_status");

        var url = __swfAttObj.getUrl("download", image.fdId);
        url = Com_SetUrlParameter(url, "open", "1");

        c_img.find(".material_upload_li_img").attr("style",
            "background-image:url(" + url + ")");
        c_img.find(".material_upload_li_img").attr("fdId", image.fdId)

        var divId = "image_" + image.fdId;
        c_img.attr("id", divId);
        c_img.attr("name", "imageViewContainer");

        c_img.attr("class", "material_upload_li material_upload_success")
        //input
        c_img.find(".material_upload_title").attr("onchange",
            "uploadTitleChange('" + divId + "')");
        c_img.find(".material_upload_tags").attr("onchange",
            "uploadTagChange('" + divId + "')");
        //hiddeninput

        c_img.find("input[name='fdSize']").val(image.fileSize);
        c_img.find("input[name='fdWidth']").val(file._info.width);
        c_img.find("input[name='fdLength']").val(file._info.height);
        //close
        c_img.find(".material_upload_li_close").attr("onclick",
            "deleteViewContainer('" + divId + "')");

    }
    // 文件上传错误
    __swfAttObj.__uploadError = function (file, reason) {
        try {
            var fileInfo = __swfAttObj.getDoc(file.id);
            fileInfo.fileStatus = -2;
            __swfAttObj.emit("uploadFaied", {
                "file": fileInfo,
                "serverData": reason
            });
            __swfAttObj.setImgLiError(file, resObj);
        } catch (e) {
        }
    };
    // 文件上传成功
    __swfAttObj.__uploadSuccess = function (file, resObj) {
        resObj = resObj || {_raw: ""};
        if (resObj.status == -1) {
            try {
                var fileInfo = __swfAttObj.getDoc(file.id);
                __swfAttObj.emit("uploadFaied", {"file": fileInfo, "serverData": resObj.msg, "_file": file});
            } catch (e) {
            }
        } else {
            __swfAttObj.uploadSuccess(file, resObj);
            __swfAttObj.setImgLiSuccess(file, resObj);
        }
    };


    // 错误监控
    __swfAttObj.__error = function (type, file, fileInfo) {
        try {
            if ("F_EXCEED_SIZE" == type || "Q_EXCEED_NUM_LIMIT" == type || "Q_EXCEED_SIZE_LIMIT" == type) {
                var sizeStr;
                if (__swfAttObj.smallMaxSizeLimit > 1) {
                    sizeStr = __swfAttObj.smallMaxSizeLimit + "M";
                } else {
                    sizeStr = __swfAttObj.smallMaxSizeLimit * 1000 + "K";
                }
                var sizeStr2;
                var localSize = (fileInfo.size / 1000000).toFixed(2);
                if (localSize > 1) {
                    sizeStr2 = localSize + "M";
                } else {
                    sizeStr2 = localSize * 1000 + "K";
                }
                alert("【" + Attachment_MessageInfo["sysAttMain.material.limit1"] + sizeStr + "】，" + Attachment_MessageInfo["sysAttMain.material.limit2"] + sizeStr2);
                __swfAttObj.emit("error", {
                    "file": fileInfo,
                    "serverData": type,
                    "max": file
                });
            } else {
                var fileInfo = __swfAttObj.getDoc(file.id);
                fileInfo.fileStatus = -2;
                __swfAttObj.emit("error", {
                    "file": fileInfo,
                    "serverData": type
                });
            }
        } catch (e) {
        }
    };
    /***********************************
     * 图片预览
     ***********************************/
    __swfAttObj.createImageView = function (attrContainer) {
        for (var i = 0; i < __swfAttObj.fileList.length; i++) {
            if (__swfAttObj.fileList[i].fileStatus == 1 &&
                __swfAttObj.isImage(__swfAttObj.fileList[i]) &&
                __swfAttObj.fileList[i].fdId.indexOf("WU_FILE") == -1) {
                __swfAttObj.imageList.push(__swfAttObj.fileList[i]);
            }
        }
        if (__swfAttObj.imageList.length > 0) {
            var containerTemp = attrContainer.find("#formTemple");
            var imageViewContainer = attrContainer.find("li[name='imageViewContainer']");
            if (imageViewContainer.length > 0)
                imageViewContainer.remove();
            for (var i = 0; i < __swfAttObj.imageList.length; i++) {
                var image = __swfAttObj.imageList[i];
                var url = __swfAttObj.getUrl("download", image.fdId);
                url = Com_SetUrlParameter(url, "open", "1");
                var divId = "image_" + image.fdId;
                imageViewContainer = containerTemp.clone(true);
                imageViewContainer.attr("id", divId);
                imageViewContainer.attr("name", "imageViewContainer");
                //img
                imageViewContainer.find(".material_upload_li_img").attr("style",
                    "background-image:url(" + url + ")");
                imageViewContainer.find(".material_upload_li_img").attr("fdId", image.fdId)
                imageViewContainer.show();
                //input
                imageViewContainer.find(".material_upload_title").attr("onchange",
                    "uploadTitleChange('" + divId + "')");
                imageViewContainer.find(".material_upload_tags").attr("onchange",
                    "uploadTagChange('" + divId + "')");
                attrContainer.append(imageViewContainer);
            }
            __swfAttObj.imageList.length = 0;
        }

    };
    /*****************************************
     * 上传完成
     *****************************************/
    // __swfAttObj.__uploadFinished = function () {
    // };

    /*****************************************
     * 初始化上传事件监听 #eventInit
     *****************************************/
    __swfAttObj.initUploadEvent = function () {
        if (__swfAttObj.uploadObj) {
            /*采用 onBeforeFileQueued = funtcion() {}  保证最后一个执行，不然一旦return false，webupload里面的beforeFileQueued事件就不执行了
             * 而且最后一个执行能保证超过上传的限制个数的时候不会添加到fileList
             */
            __swfAttObj.uploadObj.onBeforeFileQueued = __swfAttObj.__beforeFileQueued; // 文件加入队列前
            __swfAttObj.uploadObj.on("fileQueued", __swfAttObj.__fileQueued); // 文件加入队列
            __swfAttObj.uploadObj.on("uploadBeforeSend", __swfAttObj.__uploadBeforeSend); // 文件上传服务器时前
            __swfAttObj.uploadObj.on("uploadProgress", __swfAttObj.__uploadProgress); // 文件上传中
            __swfAttObj.uploadObj.on("uploadError", __swfAttObj.__uploadError); // 文件上传错误
            __swfAttObj.uploadObj.on("uploadSuccess", __swfAttObj.__uploadSuccess); // 文件上传成功
            __swfAttObj.uploadObj.on("uploadFinished", __swfAttObj.__uploadFinished); // 所有文件上传结束
            __swfAttObj.uploadObj.on("error", __swfAttObj.__error); // 文件上传过程中错误监控
        }
    };
    /*****************************************
     * 绘制附件区域框架
     *****************************************/

    __swfAttObj.drawFrame = function (renderCallback) {
        var layoutExe = function () {
            var xdiv = $("#" + __swfAttObj.renderId);
            xdiv.empty();
            window.setTimeout(function () {
                if (__swfAttObj.editMode == "edit" || __swfAttObj.editMode == "add") {
                    __swfAttObj.initUploadConfig();
                    __swfAttObj.uploadConfig.dnd = '#' + __swfAttObj.fdKey;
                    __swfAttObj.uploadConfig.pick = {
                        id: '#' + __swfAttObj.fdKey,
                        multiple: true,
                        name: __swfAttObj.getFileVal(), //修改组件产生的<input type="flie">的name属性，防止与表单的字段冲突
                        label: Attachment_MessageInfo["sysAttMain.material.select.pic"]
                    };
                    __swfAttObj.uploadObj = WebUploader.create(__swfAttObj.uploadConfig);
                    __swfAttObj.initUploadEvent();
                    if (__swfAttObj.canMove) {
                        __swfAttObj.sortable();
                    }
                    var btnDiv = $("#" + __swfAttObj.fdKey).find(".webuploader-pick");
                    btnDiv.empty();
                    btnDiv.append("<i class='material_upload_li_add_icon'></i>");
                    btnDiv.append("<p class='material_upload_li_help_title'>"+ Attachment_MessageInfo["sysAttMain.material.select.pic"] +"</p>");
                    var sizeStr;
                    if (__swfAttObj.smallMaxSizeLimit > 1) {
                        sizeStr = __swfAttObj.smallMaxSizeLimit + "M";
                    } else {
                        sizeStr = __swfAttObj.smallMaxSizeLimit * 1000 + "K";
                    }
                    btnDiv.append("<p class='material_upload_li_help_tips'>" + Attachment_MessageInfo["sysAttMain.material.pic.type.limit"] + sizeStr + "</p>");
                    //校验放在submit事件里面
                    var win = window;
                    if (__swfAttObj.isUseByXForm) {
                        win = __swfAttObj.win;
                    }
                    win.Com_Parameter.event["submit"].unshift(function () {
                        if (__swfAttObj.disabled) {
                            return true;
                        }
                        var upOk = true;
                        upOk = __swfAttObj.checkRequired();
                        if (!upOk) {		//校验必填
                            if (__swfAttObj.fdAttType == "pic") {//单图片上传为空提示语
                                __swfAttObj.dialogAlert(Attachment_MessageInfo["msg.singleNull"]);
                            } else {
                                __swfAttObj.dialogAlert(Attachment_MessageInfo["msg.null"]);
                            }
                            return upOk;
                        }
                        upOk = __swfAttObj.isUploaded();
                        if (!upOk) {      //校验是否上传中
                            __swfAttObj.dialogAlert(Attachment_MessageInfo["msg.uploading"]);
                        }
                        return upOk;
                    });
                    win.Com_Parameter.event["confirm"].unshift(function () {
                        if (__swfAttObj.editMode == "edit" || __swfAttObj.editMode == "add") {
                            return __swfAttObj.updateInput();
                        }
                    });
                }
            }, 300);
            if (renderCallback != null) {
                renderCallback();
            }
        }
        layoutExe();
    };


    __swfAttObj.showButton = function (renderCallback) {
        __swfAttObj.btnIntial = true;
    };


    __swfAttObj.on("editDelete", function (rtn) {
        var imageId = 'image_' + rtn.file.fdId;
        var targetImageEle = $('#' + imageId)
        if (targetImageEle.length > 0) {
            targetImageEle.parent().remove();
        }
    });
    return __swfAttObj
};


function previewImage(f, callback) {
    if (!f || !/image\//.test(f.type)) return; //确保文件是图片
    if (f.type == 'image/gif') { //gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
        var fr = new mOxie.FileReader();
        fr.onload = function () {
            callback(fr.result);
            fr.destroy();
            fr = null;
        }
        fr.readAsDataURL(f.getSource());
    } else {
        var preloader = new mOxie.Image();
        preloader.onload = function () {
            preloader.downsize(300, 300); //先压缩一下要预览的图片,宽300，高300
            var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
            callback && callback(imgsrc); //callback传入的参数为预览图片的url
            preloader.destroy();
            preloader = null;
        };
        preloader.load(f.getSource());
    }
}