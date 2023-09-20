/**
 * 低代码平台附件控件（对外分享）
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var attachmentLang = require("lang!sys-attachment");
    var ModelingAttachment = base.Container.extend({
        fileList : {},
        enabledType:[],
        initProps: function ($super, cfg) {
            $super(cfg);
            this.config = cfg;
            this.fdMulti = cfg.fdMulti || false;
            this.required = cfg.required || false;
            this.fdAttType = cfg.fdAttType || "";
            this.fdModelId = cfg.fdModelId || "";
            this.fdModelName = cfg.fdModelName || "";
            this.fdKey = cfg.fdKey || "";
            this.container = cfg.container || null;
            if(externalConfig.fileEnabledType){
                this.enabledType = externalConfig.fileEnabledType.split(",");
            }
            this.enabledFileType = cfg.enabledFileType || "";
            this.render();
            this.subscribe();
        },
        render: function () {
            var $content = $("<div class='lui_upload_img_box'>");
            var hiddenHtml = '<input type="hidden" name="attachmentForms.'+this.fdKey+'.fdModelId" value="'+this.fdModelId+'">' +
                '<input type="hidden" name="attachmentForms.'+this.fdKey+'.extParam" value="">' +
                '<input type="hidden" name="attachmentForms.'+this.fdKey+'.fdModelName" value="'+this.fdModelName+'">' +
                '<input type="hidden" name="attachmentForms.'+this.fdKey+'.fdKey" value="'+this.fdKey+'">' +
                '<input type="hidden" name="attachmentForms.'+this.fdKey+'.fdAttType" value="'+this.fdAttType+'">' +
                '<input type="hidden" name="attachmentForms.'+this.fdKey+'.fdMulti" value="'+this.fdMulti+'">' +
                '<input type="hidden" name="attachmentForms.'+this.fdKey+'.deletedAttachmentIds" value="">' +
                '<input type="hidden" name="attachmentForms.'+this.fdKey+'.attachmentIds" value="">';
            $content.append(hiddenHtml);
            var tipInfo = modelingLang["modeling.attachment.upload.note"];
            var tableHtml = '<table class="tb_noborder" width="100%" border="0" cellpadding="0" cellpadding="0">' +
                '<tr class="uploader">' +
                '<td><div class="lui_queueList lui_queueList_s lui_queueList_block" style="width: 100%">' +
                '<div class="lui_upload_container webuploader-container">' +
                '<div class="webuploader-pick"><i></i>'+ attachmentLang['attachment.layout.upload.note2'] +
                '<span class="lui_text_primary">'+attachmentLang['attachment.layout.upload']+'</span>'+
                (this.required ? '<span class="txtstrong">*</span>':'') +
                '</div>'+
                '<div style="position: absolute; inset: -10px auto auto -11px; width: 382px; height: 51px; overflow: hidden;">' +
                '<input type="file" name="landray_file'+ this.fdKey+'" class="webuploader-element-invisible" accept="'+externalConfig.fileEnabledType+'" '+(this.fdMulti?'multiple="true"':'')+' >'+
                '<label style="opacity: 0; width: 100%; height: 100%; display: block; cursor: pointer; background: rgb(255, 255, 255);"></label>'+
                '</div>'+
                '</div>'+
                '</div></td>' +
                '</tr>' +
                '<tr>' +
                '<td data-lui-mark="attachmentlist">' +
                '<div class="lui_upload_tip tip_info"><i></i>'+tipInfo+'</div>' +
                '<div class="att-list"></div>' +
                '</td>'+
                '</tr>'+
                '</table>';
            $content.append(tableHtml);
            this.container.append($content);
        },
        subscribe: function () {
            var self = this;
            self.container.find(".uploader .lui_upload_container.webuploader-container label").on("click",function(e) {
                e.preventDefault();
                e.stopPropagation();
                if(!self.fdMulti && !$.isEmptyObject(self.fileList)){
                    //仅支持单文件附件，如需再次上传，请先删除已有文件。
                    dialog.failure(modelingLang['modeling.attachment.upload.single.file']);
                    return;
                }
                self.container.find("input[type=file]").click();
            })
            self.fileList = {};
            self.container.find("input[type=file]").on("change",function (){
                for (var i = this.files.length - 1; i >= 0; i--) {
                    var file = this.files[i];
                    var key = file.name;
                    var fileItem = {
                        'file': file,
                        'cover': ''
                    }

                    //附件白名单校验
                    if(self._isDisabledType(key)){
                        break;
                    }

                    //防止重复上传
                    if(self.fileList.hasOwnProperty(key)){
                        dialog.failure(attachmentLang['sysAttMain.msg.fail.fileName']);
                        break;
                    }
                    //单选附件，重新选择附件后，删除原来的附件
                    if(!self.fdMulti && !$.isEmptyObject(self.fileList)){
                        for(var name in self.fileList){
                            attachmentCount.fileSizeCount = attachmentCount.fileSizeCount - self.fileList[name].file.size;
                            attachmentCount.fileCount--;
                        }
                        self.fileList = {};
                    }
                    //校验整体附件个数
                    if(attachmentCount.fileCount + 1 > externalConfig.fileLimitCount){
                        dialog.failure(modelingLang['modeling.attachment.upload.note1.start']
                            + externalConfig.fileLimitCount
                            + modelingLang['modeling.attachment.upload.note1.end']);
                        break;
                    }
                    //校验单个文件大小
                    if(file.size > externalConfig.singleFileSize * 1024 * 1024){
                        dialog.failure(modelingLang['modeling.attachment.upload.note3.start']
                            + externalConfig.singleFileSize
                            + modelingLang['modeling.attachment.upload.note3.end']);
                        break;
                    }
                    //校验整体附件大小
                    if(attachmentCount.fileSizeCount + file.size > externalConfig.fileMaxSize * 1024 * 1024){
                        dialog.failure(modelingLang['modeling.attachment.upload.note4.start']
                            + externalConfig.fileMaxSize
                            + modelingLang['modeling.attachment.upload.note3.end']);
                        break;
                    }
                    attachmentCount.fileCount += 1;
                    attachmentCount.fileSizeCount += file.size;
                    self.fileList[key] = fileItem;

                    self.showFile(key);
                }
                this.value = '';
            })
        },

        //校验附件白名单
        _isDisabledType:function(key) {
            var fileExt = key.substring(key.lastIndexOf("."));
            if(fileExt!=""){
                fileExt=fileExt.toLowerCase();
            }
            var fileTypes= this.enabledType;
            var self = this;
            if(this.enabledFileType) {
                fileTypes = this.enabledFileType.split(',');
                fileTypes = self.enabledType.filter(function(val){
                    return fileTypes.indexOf(val.substring(1))>-1;
                });
            }
            //校验白名单格式
            if(fileTypes.indexOf(fileExt)<0){
                // dialog.failure(modelingLang['modeling.attachment.upload.note2.start']
                //     + fileTypes
                //     + modelingLang['modeling.attachment.upload.note2.end']);
                dialog.failure(modelingLang['modeling.attachment.upload.not.support.fileType']);
                return true;
            }
            return false;
        },
        showFile : function(key){
            if(!this.fdMulti){
                this.container.find("[data-lui-mark='attachmentlist'] .att-list").empty();
            }
            var fileItem = this.fileList[key];
            var iconClass = GetIconClassByFileName(key);
            var fileName = key.substring(0,key.lastIndexOf("."));
            var fileExt = key.substring(key.lastIndexOf("."));
            if(fileExt!=""){
                fileExt=fileExt.toLowerCase();
            }
            if(fileName.length>20){
                fileName = fileName.substring(0,20)+"...";
            }
            var fileSize = this.formatFileSize(fileItem.file.size);
            var $html =$('<div>' +
                '<div class="upload_list_tr upload_list_tr_edit">' +
                '<div class="upload_list_tr_edit_l">' +
                '<div class="upload_list_icon"><i class="'+iconClass+'"></i></div>' +
                '<div class="upload_list_filename_edit" title="'+key+'">' +
                '<span class="upload_list_filename_title">'+fileName+'</span>' +
                '<span class="upload_list_filename_ext">'+fileExt+'</span>' +
                '</div>' +
                '<div class="upload_list_size">'+fileSize+'</div>'+
                '<div class="upload_opt_icon upload_opt_delete">' +
                '<span class="upload_opt_tip"><i class="upload_opt_tip_arrow"></i>' +
                '</span>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>');
            var self = this;
            $html.find(".upload_opt_delete").on("click",function (){
                attachmentCount.fileSizeCount = attachmentCount.fileSizeCount - self.fileList[key].file.size;
                attachmentCount.fileCount--;
                delete self.fileList[key];
                $html.remove();
            })
            this.container.find("[data-lui-mark='attachmentlist'] .att-list").append($html);
        },
        // 格式化文件大小
        formatFileSize:function(fileSize) {
            if (fileSize < 1024) {
                return fileSize + 'B';
            } else if (fileSize < (1024*1024)) {
                var temp = fileSize / 1024;
                temp = temp.toFixed(2);
                return temp + 'KB';
            } else if (fileSize < (1024*1024*1024)) {
                var temp = fileSize / (1024*1024);
                temp = temp.toFixed(2);
                return temp + 'MB';
            } else {
                var temp = fileSize / (1024*1024*1024);
                temp = temp.toFixed(2);
                return temp + 'GB';
            }
        },
        getKeyData:function() {
            return this.fileList;
        },
        validate:function() {
            if(this.required && $.isEmptyObject(this.fileList)){
                dialog.alert(attachmentLang['sysAttMain.msg.null']);
                return false;
            }
            return true;
        }
    });

    exports.ModelingAttachment = ModelingAttachment;
});
