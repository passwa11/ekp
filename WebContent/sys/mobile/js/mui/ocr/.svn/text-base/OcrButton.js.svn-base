define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "mui/ocr/OcrConstants",
    "dojo/topic"
],function(declare,_WidgetBase,OcrConstants,topic) {
    return declare("mui.ocr.OcrButton", [_WidgetBase,OcrConstants], {
        html:'',

        type:'',

        fdKey:'',

        buildRendering : function() {
            this.inherited(arguments);
            this.domNode.innerHTML = this.html || "";
        },

        postCreate:function(){
            this.inherited(arguments);
            this.connect(this.domNode,'click','_todo');
        },

        _todo:function(){
            if(this.type == 'cancel' || this.type == 'opt_del'){
                topic.publish(this.OCR_EVENT_CANCEL+this.fdKey,this);
            }else if(this.type == 'reUpload' || this.type == 'opt_reUpload'){
                topic.publish(this.OCR_EVENT_REUPLOAD+this.fdKey,this);
            }else if(this.type == 'onlyUpload'){
                topic.publish(this.OCR_EVENT_ONLY_UPLOAD+this.fdKey,this);
            }else if(this.type == 'opt_viewImg'){
                topic.publish(this.OCR_EVENT_OPT_VIEWIMG+this.fdKey,this);
            }else if(this.type == 'opt_replaceImg'){
                topic.publish(this.OCR_EVENT_OPT_REPLACEIMG+this.fdKey,this);
            }else if(this.type == 'opt_cancel'){
                topic.publish(this.OCR_EVENT_OPT_CANCEL+this.fdKey,this);
            }else if(this.type == 'fail_cancel'){
                topic.publish(this.OCR_EVENT_FAIL_CANCEL+this.fdKey,this);
            }
        }
    });
});