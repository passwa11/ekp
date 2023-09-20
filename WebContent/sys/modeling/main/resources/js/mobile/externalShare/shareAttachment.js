define([
    "dojo/_base/declare",
    "dojo/dom-class",
    "dojo/dom-attr",
    "dojo/_base/array",
    "dojo/topic",
    "mui/dialog/Tip",
    "dojox/mobile/viewRegistry",
    "dojo/dom-style",
    "dojo/query",
    "mui/i18n/i18n!sys-attachment:mui",
    "mui/i18n/i18n!sys-attachment:sysAttMain",
    "mui/i18n/i18n!sys-modeling-base:modeling",
    "dojo/_base/lang"
], function(
    declare,
    domClass,
    domAttr,
    array,
    topic,
    Tip,
    viewRegistry,
    domStyle,
    query,
    Msg,
    attachmentLang,
    modelingLang,
    lang
) {
    //附件列表
    return declare(
        "sys.modeling.main.resources.js.mobile.externalShare.shareAttachmentList",
        [],
        {
            enabledType:[],
            initEvent:function() {
                this.subscribe(this.eventPrefix + "share", "_share");
                if(externalConfig.fileEnabledType){
                    this.enabledType = externalConfig.fileEnabledType.split(",");
                }
            },
            getValidateView: function(domNode) {
                var node = this.domNode || domNode;
                var view = viewRegistry.getEnclosingView(node);
                while (view != null && !view._validation) {
                    view = viewRegistry.getParentView(view);
                }
                return view;
            },
            checkAttRules: function() {
                if (!this.validateAttUploading()) {
                    Tip.tip({
                        text: Msg["mui.sysAttMain.upload"] + ".."
                    });
                    return false;
                }
                if (this.filekeys.length > 0) {
                    //#112219 移动端上传附件顺序还是乱的(因为上传成功回调顺序可能会错乱，这里提交前重新按照文件的创建时间排序)
                    var arr = this.orderFiles;
                    var arr2 = this.filekeys;
                    var newFileKeys = [];
                    for (var i = 0; i < arr.length; i++) {
                        for (var j = 0; j < arr2.length; j++) {
                            if (arr[i]._fdId == arr2[j]._fdId) {
                                newFileKeys.push(arr2[j]);
                                continue;
                            }
                        }
                    }
                    this.filekeys = newFileKeys;

                    var attachmentObj = window.AttachmentList[this.fdKey];
                    if (attachmentObj != null && attachmentObj.fileStatus != -1) {
                        array.forEach(
                            this.filekeys,
                            function(file) {
                                var fdid = attachmentObj.registFile({
                                    filekey: file.filekey,
                                    name: file.name
                                });
                                if (fdid) {
                                    this.addAtts.push(fdid);
                                }
                            },
                            this
                        );
                    }
                }
                this.fillAttInfo();
                return true;
            },

            _share:function(srcObj, evt) {
                if (evt.file) {
                    var file= evt.file;
                    //附件白名单
                    if(this._isDisabledType(file)){
                        return;
                    }
                    //同名文件校验
                    for (var i = 0; i < this.filekeys.length;i++) {
                        var fileTmp = this.filekeys[i];
                        if (file.name === fileTmp.name) {
                            Tip.tip({
                                icon: "mui mui-fail",
                                text : attachmentLang['sysAttMain.msg.fail.fileName']
                            });
                            return;
                        }
                    }
                    //单个文件大小校验
                    if(parseFloat(file.size) > externalConfig.singleFileSize * 1024 * 1024){
                        Tip.tip({
                            icon: "mui mui-fail",
                            text : modelingLang['modeling.attachment.upload.note3.start']
                                + externalConfig.singleFileSize
                                + modelingLang['modeling.attachment.upload.note3.end']
                        });
                        return;
                    }
                    //整体附件大小校验
                    if(attachmentCount.fileSizeCount + parseFloat(file.size) > externalConfig.fileMaxSize * 1024 * 1024){
                        Tip.tip({
                            icon: "mui mui-fail",
                            text : modelingLang['modeling.attachment.upload.note4.start']
                                + externalConfig.fileMaxSize
                                + modelingLang['modeling.attachment.upload.note3.end']
                        });
                        return;
                    }
                    //整个表单附件个数校验
                    if(attachmentCount.fileCount + 1 > externalConfig.fileLimitCount){
                        Tip.tip({
                            icon: "mui mui-fail",
                            text : modelingLang['modeling.attachment.upload.note1.start']
                                + externalConfig.fileLimitCount
                                + modelingLang['modeling.attachment.upload.note1.end']
                        });
                        return;
                    }

                    if (file._fdId == null || file._fdId == '') {
                        file._fdId = this.guid();
                    }

                    if (window.console) {
                        window.console.log("startUploadFile begin..");
                    }

                    file.edit = "edit";

                    file.key = file.key ? file.key : this.fdKey;

                    file.status = -1;

                    var self = this;

                    self._start(srcObj,lang
                        .mixin({}, {
                            file : file
                        }));
                    file.filekey = this.guid();
                    file.status = 2;

                    self._success(srcObj,lang
                        .mixin({}, {
                            file : file
                        }));
                    if (window.console) {
                        window.console.log("startUploadFile end..");
                    }
                }
            },
            //文件格式白名单校验
            _isDisabledType:function(file) {
                if(file.name){
                    var _fileName = file.name;
                    var fileExt = _fileName.substring(_fileName.lastIndexOf("."));
                    if(fileExt!=""){
                        fileExt=fileExt.toLowerCase();
                    }
                    var fileTypes= this.enabledType;
                    if(this.enabledFileType) {
                        fileTypes = this.enabledFileType.split('|');
                        fileTypes = this.enabledType.filter(function(val){
                           return fileTypes.indexOf(val)>-1;
                        });
                    }
                    var isPass = false;
                    for (var i=0;i<fileTypes.length ;i++ ){
                        if(fileExt === fileTypes[i]){
                            isPass = true;
                            break;
                        }
                    }

                    if(!isPass){
                        Tip.tip({
                            icon: "mui mui-fail",
                            // text : modelingLang['modeling.attachment.upload.note2.start']
                            //     + fileTypes
                            //     + modelingLang['modeling.attachment.upload.note2.end']
                            text:modelingLang['modeling.attachment.upload.not.support.fileType']
                        });
                        return true;
                    }

                }
                return false;
            },

            _start: function(srcObj, evt) {
                if (evt.file) {
                    this.orderFiles.push(evt.file);
                }
                topic.publish(this.eventPrefix + "addItem", this, evt);
            },

            _success: function(srcObj, evt) {
                var widget = this.getChildByFdId(evt.file._fdId);
                if (widget) {
                    if (evt.file) {
                        widget.filekey = evt.file.filekey;
                        widget.status = evt.file.status;
                        widget.href = evt.file.href;
                        attachmentCount.fileCount++;
                        attachmentCount.fileSizeCount += parseFloat(evt.file.size);
                        this.filekeys.push(evt.file);
                    }
                    if (widget.uploaded) {
                        widget.uploaded();
                    }
                    if (this.pView) {
                        this.pView._validation.validateElement(this.domNode);
                    }
                }
            },
            _del: function(srcObj, evt) {
                if (evt) {
                    var widget = evt.widget;
                    var filekey = widget.filekey;
                    var fdId = widget.fdId;
                    var fileName = widget.name;
                    if (filekey != null) {
                        attachmentCount.fileCount--;
                        this.filekeys = array.filter(this.filekeys, function(file) {
                            if(file.filekey == filekey){
                                attachmentCount.fileSizeCount -= parseFloat(file.size);
                            }
                            return file.filekey != filekey;
                        });
                    }
                    if (fdId != null) {
                        this.addAtts = array.filter(this.addAtts, function(delId) {
                            return delId != fdId;
                        });
                        this.delAtts.push(fdId);
                    }
                    if (fileName != null) {
                        this.nameAtts = array.filter(this.nameAtts, function(delId) {
                            return delId != fileName;
                        });
                    }

                    if(srcObj.isDing){
                        topic.publish("/third/ding/del/"+this.fdKey, {_srcObj:srcObj,_widget:widget,_this:this});
                    }else{
                        this.removeChild(widget);
                        widget.destroy();
                        topic.publish("/mui/list/resize", this);
                    }

                }
            },
        }
    );
});
