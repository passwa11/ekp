/*
 * Ocr证件组件对应的能力，包括上传和识别，以及操作
 * 提供给Ocr.js继承
 */
define([
    "dojo/_base/declare",
    "sys/mobile/js/mui/ocr/OcrConstants",
    "mui/util",
    "mui/dialog/Dialog",
    "mui/dialog/Tip",
    "mui/dialog/Modal",
    "dojo/request",
    "dojo/topic",
    "dojo/_base/lang",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/query",
    "dojo/on",
    "mui/i18n/i18n!sys-mobile:mui.ocr"
],function(declare,OcrConstants,util,Dialog,Tip,Modal,request,topic,lang,domConstruct,domStyle,domClass,query,on,Msg){
    return declare("mui.ocr._OcrMixin",[OcrConstants], {

        postCreate:function(){
            this.inherited(arguments);
            //对接附件机制的事件
            this.subscribe("attachmentObject_"+this.fdKey+"_start","uploadStart");
            this.subscribe("attachmentObject_"+this.fdKey+"_success","uploadSuccess");
            this.subscribe("attachmentObject_"+this.fdKey+"_fail","uploadError");
            this.subscribe("attachmentObject_"+this.fdKey+"_afterView","_createImg");

            //ocr操作事件
            this.subscribe(this.OCR_EVENT_REUPLOAD+this.fdKey,"reUpload");
            this.subscribe(this.OCR_EVENT_ONLY_UPLOAD+this.fdKey,"onlyUpload");
            this.subscribe(this.OCR_EVENT_CANCEL+this.fdKey,"cancelIdentify");
            this.subscribe(this.OCR_EVENT_OPT_VIEWIMG+this.fdKey, "viewImg");
            this.subscribe(this.OCR_EVENT_OPT_REPLACEIMG+this.fdKey,"replaceImg");
            this.subscribe(this.OCR_EVENT_OPT_CANCEL+this.fdKey,"hideOptDialog");
            this.subscribe(this.OCR_EVENT_FAIL_CANCEL+this.fdKey,"hideFailDialog");
        },

        //点击事件调用的方法
        selectImg: function (obj) {
            var showFlag = this.showFlag;
            if (obj && obj.showFlag == false) {
                showFlag = false;
            }
            if (showFlag) {
                this.showOperations();
            } else {
                //点击ocr，对接附件的点击事件，响应附件的点击事件，eventType是事件类型，targetName，是执行的dom，key是唯一标识，作为事件过滤，提供给附件进行判断
                topic.publish("attachmentOptListItem_emit_events", this, {
                    eventType: 'click',
                    targetName: 'uploadDom',
                    key: this.fdKey
                });
            }
        },

        //打开下拉列表（按钮）
        showOperations: function () {;
            var _self = this;
            var jspUrl = this.operDialogJspUrl;
            //添加参数
            if(jspUrl.indexOf("?") == -1){
                jspUrl += "?" + this.operDialogJspUrlParams;
            }
            //格式化
            jspUrl = util.formatUrl(util.urlResolver(jspUrl,this));
            //请求并渲染下拉列表
            require({async: false}, ["dojo/text!" + jspUrl], function (text) {
                text = lang.replace(text, {categroy: _self});
                _self.dialogDom = domConstruct.toDom(text);
                _self.optDialog = Dialog.element({
                    canClose: false,
                    showClass: "muiOcrOptDialog",
                    element: _self.dialogDom,
                    position: "bottom",
                    scrollable: false,
                    parseable: true,
                    callback: lang.hitch(_self, function () {
                        _self.optDialog = null;
                    }),
                    onDrawed: function () {
                    }
                })
            })
        },

        //上传前执行
        uploadStart:function(srcObj,data){
            this.delItem();
            if(this.isUpload){
                this.showUploadStatus();
            }
            if(!this.isUpload)
                this.showProcess()
        },

        //上传成功后执行
        uploadSuccess:function(srcObj,data){
            this.hideUploadStatus();
            if(this.isUpload){
                this.showImg(srcObj,data);
                this.showFlag = true;
            }
            if(this.toIdentify){
                //进行识别
                this.identify(srcObj,data);
            }
            this.toIdentify = true;
            this.reValidate();
        },

        //上传失败后执行
        uploadError:function(src,data){
            this.showFlag = false;
            if(!this.isUpload){
                this.hideProcess();
                Tip.fail({text:Msg['mui.ocr.identify.success.tip']});
            }
        },

        //识别
        identify:function(context,data){
            if(this.isUpload)
                this.showProcess();
            this.beforeIdentify(data.file,context);

            var url = this.identifyUrl;
            var file = data.file;
            //缓存数据
            this.attData = data;

            //参数处理
            var ocrparm={};
            ocrparm.type=this.typeNum || '0';
            ocrparm.attId=this.attId || "";
            ocrparm.modelName='';
            ocrparm.formName='';
            ocrparm.docId='';

            //格式化
            url = util.formatUrl(util.urlResolver(url));
            var _self = this;
            //调用接口
            request(url,{data:ocrparm,handleAs:'json',sync:true,method:'POST'}).then(function(data){
                _self.identifySuccess(data);
                _self.afterIdentify(context);
            }, function(err){
                _self.identifyError(err);
                _self.afterIdentify(context);
            });
        },

        //识别成功后执行
        identifySuccess:function(data){
            var result = this.callbackAfterIdentifySuccess(data);
            this.hideFailTip();
            this.hideProcess();
            if(result){
                Tip.success({text:Msg['mui.ocr.identify.success.tip']});
            }else{
                this.identifyError();
            }
            topic.publish(this.OCR_EVENT_IDENTIFY_SUCCESS+this.fdKey,this,data);
        },

        //识别成功后进行数据回调，包括样式更替和填充数据等操作，父组件默认不做操作，由子组件继承重写
        callbackAfterIdentifySuccess:function(data){
        },

        //识别失败后执行
        identifyError:function(err){
            this.showFailTip();
            this.hideProcess();
            this.callbackAfterIdentifyError();
            topic.publish(this.OCR_EVENT_IDENTIFY_FAIL+this.fdKey,this);
        },

        //识别失败后进行数据回调，包括样式更替和填充数据等操作，父组件有默认操作，子组件可以进行覆盖
        callbackAfterIdentifyError:function(err){
            var title = Msg['mui.ocr.identify.fail.dialog.title'];
            if(!this.isUpload){
                this.delItem();
                title = Msg['mui.ocr.identify.fail.tip'];
            }
            var _self = this;
            var jspUrl = this.failJsp;
            //添加参数
            if(jspUrl.indexOf("?") == -1){
                jspUrl += "?" + this.failJspParams;
            }
            var jspUrl = util.formatUrl(util.urlResolver(jspUrl,this));
            require({async:false},["dojo/text!"+jspUrl],function(text){
                text = lang.replace(text, {categroy: _self});
                var contentHtml = text;
                var buttons = [];
                _self.failDialog = Modal(contentHtml,title,buttons);
                _self.failDialog.destroyAfterClose = true;
            });
        },

        //由于附件没有做暂存attMain的操作，识别前先暂存附件信息，不允许覆盖（若后续附件提供了配置方式，可以去掉）
        beforeIdentify:function(file,context){
            if(file){
                var attachmentObj = window.AttachmentList[this.fdKey] || context;
                if (attachmentObj != null && attachmentObj.fileStatus != -1) {
                    var fdModelId = attachmentObj.fdModelId;
                    var fdModelName = attachmentObj.fdModelName;
                    attachmentObj.fdModelId = "";
                    attachmentObj.fdModelName = "";
                    this.attId = attachmentObj.registFile({
                        filekey: file.filekey,
                        name: file.name
                    });
                    attachmentObj.fdModelId = fdModelId;
                    attachmentObj.fdModelName = fdModelName;
                }
            }
        },

        //由于附件没有做暂存attMain的操作，识别后删除附件信息，不允许覆盖（若后续附件提供了配置方式，可以去掉）
        afterIdentify:function(context){
            if(this.attId){
                var attachmentObj = window.AttachmentList[this.fdKey] || context;
                if (attachmentObj != null) {
                    var xdata={
                        "fdId":this.attId
                    }
                    request.post(attachmentObj.deleteUrl, {
                        data : xdata,
                        sync : true
                    }).then(function(data) {
                        console.info("删除成功！")
                    },function(){
                        console.info("删除失败！")
                    })
                }
            }
        },

        //仅上传图片
        onlyUpload:function(){
            this.hideFailDialog();
        },

        //取消识别
        cancelIdentify:function(){
            this.hideFailDialog();
            this.delItem();
        },

        //删除上传的内容
        delItem:function(){
            if(this.isUpload){
                this.hideFailTip();
                this.hideFailDialog();
                this.hideOptDialog();
                this.deleteImg();
                this.showFlag = false;
            }
            this.reValidate();
            topic.publish("attachmentEditListItem_delItem",this,{key:this.fdKey});
        },

        //查看图片大图
        viewImg:function(){
            this.hideOptDialog();
            topic.publish("attachmentListItem_viewItem",this,{key:this.fdKey});
        },

        //重新上传
        reUpload:function(){
            this.hideOptDialog();
            this.hideFailDialog();
            this.selectImg({showFlag:false});
        },

        //替换图片，不进行识别
        replaceImg:function(){
            this.toIdentify = false;
            this.hideOptDialog();
            this.selectImg({showFlag:false});
        },

        //上传后显示上传的图片
        showImg:function(attObj,fileData){
            if(fileData && fileData.file){
                var file = fileData.file;
                this.createImg(file);
            }
        },

        //创建一个图片节点，给showImg调用
        createImg:function(file){
            if(this.imgNode){
                //创建前先进行销毁，保证是单选
                this.deleteImg();
                //隐藏背景图
                domClass.add(this.imgNode,"muiOcrNoBackground");
                this.imageNode=domConstruct.create("img",{
                    className:"muiOcrDefaultImg",
                    align:"middle",
                    src: util.formatUrl(file.href)}, this.imgNode);
            }
        },

        //创建一个图片节点，该方法提供给事件调研
        _createImg:function(srcObj,data){
            if(data && this.isUpload == true){
                this.createImg(data);
                if(data.pageType && data.pageType == 'edit'){
                    this.showFlag = true;
                }else{
                    on(this.imageNode,'click',lang.hitch(this,this.viewImg));
                }
            }
        },

        //删除图片节点
        deleteImg:function(){
            if(this.imageNode){
                domConstruct.destroy(this.imageNode);
                this.imageNode = null;
                domClass.remove(this.imgNode,"muiOcrNoBackground");
            }
        },

        //隐藏识别失败提示
        hideFailTip:function(){
            var node = query(".muiOcrDefaultFail",this.domNode)[0];
            if(node){
                domStyle.set(node,{
                    "display":"none"
                })
            }
        },

        //显示识别失败提示
        showFailTip:function(){
            var node = query(".muiOcrDefaultFail",this.domNode)[0];
            if(node){
                domStyle.set(node,{
                    "display":""
                })
            }
        },

        //隐藏操作下拉列表
        hideOptDialog:function(){
            if(this.optDialog){
                this.optDialog.hide();
                this.optDialog = null;
            }
        },

        //隐藏识别识别的弹窗
        hideFailDialog:function(){
            if(this.failDialog){
                this.failDialog.hide();
                this.failDialog = null;
            }
        },

        //显示识别进度提示
        showProcess:function(){
            if(!this._processing){
                var tip = Msg['mui.ocr.identify.process'];
                this._processing = Tip.progressing({text:tip});
                this._processing.show();
            }
        },

        //隐藏识别进度提示
        hideProcess:function(){
            if(this._processing) {
                this._processing.hide();
                this._processing = null;
            }
        },

        //显示上传进度
        showUploadStatus:function(){
            this.statusDiv = domConstruct.create("div",{
                className: "muiAttItemStatus"}, this.imgNode);
            domConstruct.create("i",{
                className: "fontmuis mui-spin muis-refresh"}, this.statusDiv);
        },

        //隐藏上传进度
        hideUploadStatus:function(){
            if(this.statusDiv){
                domConstruct.destroy(this.statusDiv);
                this.statusDiv = null;
            }
        }
    });
})