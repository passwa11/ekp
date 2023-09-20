/**
 * 业务空间-部件设置的视图组件-基类
 */
define(function(require, exports, module) {
    var $ = require('lui/jquery'),
        base = require('lui/base'),
        dataPortlet = require('sys/modeling/base/space/res/js/dataPortlet'),
        textPortlet = require('sys/modeling/base/space/res/js/textPortlet'),
        imagePortlet = require('sys/modeling/base/space/res/js/imagePortlet');
    var topic = require("lui/topic");
    var dialog = require("lui/dialog");

    var ViewPortlet = base.Container.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.parts = [];    //容器部件数组
            this.viewContainer = $(".panel-portlet-main");
            this.storeData = cfg.storeDataConfig;
            this.dataPortlet = {};
            this.textPortlet = {};
            this.picturePortlet = {};
            this.fdType = cfg.fdPortletType;
            if(this.storeData){
                this.initByStoreData(this.storeData);
            }
        },

        startup : function($super, cfg) {
            $super(cfg);
        },
        doRender : function($super, cfg) {
            $super(cfg);
        },
        draw : function($super, cfg){
            var self = this;
            //根据类型去创建部件
            var partObj = {};
            if(this.fdType  == "2"){
                //创建数据部件
                var viewWgt = new dataPortlet.DataPortlet({parent:this,container:this.viewContainer});
                viewWgt.startup();
                viewWgt.draw();
                partObj.type ="data";
                partObj.wgt = viewWgt;
            }else if(this.fdType == "0"){
                //文本部件
                var textWgt = new textPortlet.TextPortlet({parent:this,container:this.viewContainer});
                textWgt.startup();
                textWgt.draw();
                partObj.type ="text";
                partObj.wgt = textWgt;
            }else{
                //图片部件
                var imageWgt = new imagePortlet.ImagePortlet({parent:this,container:this.viewContainer});
                imageWgt.startup();
                imageWgt.draw();
                partObj.type ="image";
                partObj.wgt = imageWgt;
            }
            this.parts.push(partObj);
        },
        submitChecked : function(){
            var isPass = true;
            var parts =  this.parts;
            for(var i = 0; i <parts.length; i++){
                var partObj = parts[i];
                isPass = partObj.wgt.submitChecked();
                if(isPass == false){
                    break;
                }
            }
            return isPass;
        },
        getKeyData : function(){
            var keyData = [];
            var parts =  this.parts;
            for(var i = 0; i <parts.length; i++){
                var partObj = parts[i];
                var dataObj = {};
                if(partObj.type == "data"){
                    dataObj.fdType = "2";
                    dataObj.dataPortlet = partObj.wgt.getKeyData();
                }else if(partObj.type == "text"){
                    dataObj.fdType = "0";
                    dataObj.textPortlet = partObj.wgt.getKeyData();
                }else if(partObj.type == "image"){
                    dataObj.fdType = "1";
                    dataObj.picturePortlet = partObj.wgt.getKeyData();
                }
                keyData.push(dataObj);
            }
            return keyData;
        },
        initByStoreData : function(storeData){
            if(JSON.stringify(storeData) === "{}"){
                return;
            }
            var fdPart = storeData;
            this.fdType = fdPart.fdType;
            if(fdPart.fdType == "2"){
                //数据部件
                this.dataPortlet =  fdPart.dataPortlet;
            }else if(fdPart.fdType == "0"){
                //文本部件处理
                this.textPortlet = fdPart.textPortlet;
            }else{
                this.picturePortlet = fdPart.picturePortlet;
            }
        }

    });

    exports.ViewPortlet = ViewPortlet;
})