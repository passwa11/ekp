/**
 列表视图基础设置——数据过滤
 **/
define(function (require, exports, module) {
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');
    var whereGenerator = require('sys/modeling/base/resources/js/views/collection/whereGenerator');
    var lang=require("lang!sys-modeling-base");

    var CollectionDataFilter = base.Component.extend({
        initProps : function($super, cfg) {
            $super(cfg);
            this.whereContent = cfg.whereContent;
            this.whereTemp = cfg.whereTemp;
            this.fdWhereBlock = cfg.fdWhereBlock;
            this.modeligWhereBlock = cfg.modeligWhereBlock;
            this.commonCfgObj = {};
            this.pcCfgObj = {};
            this.mobileCfgObj = {};
            this.valueName = "fd_"+ parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.build(this.whereContent,this.fdWhereBlock);
        },
        build:function(whereContent,fdWhereBlock){
            var self = this;
            //统一设置或独立设置
           if(fdWhereBlock.whereBlockSettingType){
                this.settingType = fdWhereBlock.whereBlockSettingType;
                whereContent.find("input[name='data-filter-type']").removeAttr("checked");
                whereContent.find("input[name='data-filter-type'][value='"+this.settingType+"']").prop("checked",true);
            }else{
                this.settingType = whereContent.find("input[name='data-filter-type']:checked").val();
            }
            whereContent.find("input[type='radio'][name='data-filter-type']").change(function () {
                self.settingType = whereContent.find("input[name='data-filter-type']:checked").val();
                self.commonCfgObj = {};
                self.pcCfgObj = {};
                self.mobileCfgObj = {};
                self.drawWhere(whereContent);
            });
            whereContent.find("input[name='data-filter-type']:checked").trigger("change");
        },
        drawWhere:function(whereContent){
            var self = this;
            $whereBlock = $(this.whereTemp);
            if(self.settingType === "0"){
                //统一设置
               self.drawUnifyWhere($whereBlock,whereContent);
            }else{
                //独立设置
                self.drawAloneWhere($whereBlock,whereContent);
            }
         },
        drawAloneWhere:function($whereBlock,whereContent){
            whereContent.find(".data-filter-pc").html("<div class='data-filter-pc-title'>pc</div>");
            whereContent.find(".data-filter-mobile").html("<div class='data-filter-pc-title'>"+lang['listview.mobile']+"</div>");
            $whereBlock.find(".data-filter-whereType").attr("name","data-filter-whereType-pc-"+this.valueName);
            $whereBlock.find(".sys-filter-whereType").attr("name","sys-filter-whereType-pc-"+this.valueName);
            whereContent.find(".data-filter-pc").append($whereBlock.html());
            $whereBlock.find(".data-filter-whereType").attr("name","data-filter-whereType-mobile-"+this.valueName);
            $whereBlock.find(".sys-filter-whereType").attr("name","sys-filter-whereType-mobile-"+this.valueName);
            whereContent.find(".data-filter-content-alone").css("display","block");
            whereContent.find(".data-filter-content-unify").css("display","none");
            whereContent.find(".data-filter-mobile").append($whereBlock.html());
            if(this.modeligWhereBlock && JSON.stringify(this.modeligWhereBlock)!="{}"){
                var pcCfgArr =  this.modeligWhereBlock.pcCfg;
                var mobileCfgArr =  this.modeligWhereBlock.mobileCfg;
                this.drawInitAloneWhere(pcCfgArr,whereContent,"pc");
                this.drawInitAloneWhere(mobileCfgArr,whereContent,"mobile");
            } else if(this.fdWhereBlock){
                this.drawInitWhere(whereContent);
            }
            this.buildEnvent4Where(whereContent.find(".data-filter-content-alone"));
        },
        drawUnifyWhere:function($whereBlock,whereContent){
            $whereBlock.find(".data-filter-whereType").attr("name","data-filter-whereType-unify-"+this.valueName);
            $whereBlock.find(".sys-filter-whereType").attr("name","sys-filter-whereType-unify-"+this.valueName);
            whereContent.find(".data-filter-content-unify").css("display","block");
            whereContent.find(".data-filter-content-alone").css("display","none");
            var customOrSysIndex = whereContent.find(".data-filter-content-unify").find(".common-data-filter-whereType ul .active").index();
            whereContent.find(".data-filter-content-unify").html("");
            whereContent.find(".data-filter-content-unify").append($whereBlock.html());
            whereContent.find(".data-filter-content-unify").find(".common-data-filter-whereType ul li").eq(customOrSysIndex).trigger("click");
            if(this.modeligWhereBlock && JSON.stringify(this.modeligWhereBlock)!="{}"){
                var whereBlockType = this.modeligWhereBlock.whereBlockSettingType;
                var commonCfgArr =  this.modeligWhereBlock.commonCfg;
                if(whereBlockType == "0"){
                    this.drawInitUnify(whereContent,commonCfgArr);
                }
            }else if(JSON.stringify(this.fdWhereBlock)!="{}"){
                this.drawInitWhere(whereContent);
            }

            this.buildEnvent4Where(whereContent.find(".data-filter-content-unify"));
        },
        drawInitWhere:function(whereContent){
            var self = this;
            var settingType = this.fdWhereBlock.whereBlockSettingType;
            var commonCfgArr = this.fdWhereBlock.commonCfg;
            if(settingType == "0"){
                self.drawInitUnify(whereContent,commonCfgArr);
            }else{
                //独立设置
                self.drawInitAlone(whereContent);
            }
        },
        drawInitAlone:function(whereContent){
            var self = this;
            var pcCfgArr = this.fdWhereBlock.pcCfg;
            var mobileCfgArr = this.fdWhereBlock.mobileCfg;
            self.drawInitAloneWhere(pcCfgArr,whereContent,"pc");
            self.drawInitAloneWhere(mobileCfgArr,whereContent,"mobile");
        },
        drawInitAloneWhere:function(pcCfgArr,whereContent,pcOrMobile){
            var self = this;
            var $target = whereContent.find(".data-filter-"+pcOrMobile);
            if(!pcCfgArr){
                return;
            }
            for(var i = 0;i < pcCfgArr.length;i++){
                var whereObj = pcCfgArr[i];
                var whereType = whereObj.whereType;
                var whereBlockType = whereObj.whereBlockType;
                if(whereType == "0"){
                    var $table = $target.find(".data-filter-content").find("table");
                    var whereWgt = new whereGenerator({container:$table ,parent:this,data:whereObj,wheretype:"custom_query"});
                    whereWgt.startup();
                    whereWgt.draw();
                    if(pcOrMobile == "pc"){
                        if(whereBlockType == "0"){
                            //满足所有
                            whereContent.find("input[name*='data-filter-whereType-pc'][value='0']").prop("checked",true);
                        }else{
                            //满足任一
                            whereContent.find("input[name*='data-filter-whereType-pc'][value='1']").prop("checked",true);
                        }
                        if(!self.pcCfgObj.hasOwnProperty("whereCollection")){
                            self.pcCfgObj["whereCollection"] = [];
                        }
                        self.pcCfgObj["whereCollection"].push(whereWgt);
                    }else if(pcOrMobile == "mobile"){
                        if(whereBlockType == "0"){
                            //满足所有
                            whereContent.find("input[name*='data-filter-whereType-mobile'][value='0']").prop("checked",true);
                        }else{
                            //满足任一
                            whereContent.find("input[name*='data-filter-whereType-mobile'][value='1']").prop("checked",true);
                        }
                        if(!self.mobileCfgObj.hasOwnProperty("whereCollection")){
                            self.mobileCfgObj["whereCollection"] = [];
                        }
                        self.mobileCfgObj["whereCollection"].push(whereWgt);
                    }
                }else{
                    var $table = $target.find(".data-filter-content-sys").find("table");
                    var whereWgt = new whereGenerator({container:$table ,parent:this,data:whereObj,wheretype:"sys_query"});
                    whereWgt.startup();
                    whereWgt.draw();
                    if(pcOrMobile == "pc"){
                        if(whereBlockType == "0"){
                            //满足所有
                            whereContent.find("input[name*='sys-filter-whereType-pc'][value='0']").prop("checked",true);
                        }else{
                            //满足任一
                            whereContent.find("input[name*='sys-filter-whereType-pc'][value='1']").prop("checked",true);
                        }
                        if(!self.pcCfgObj.hasOwnProperty("sysWhereCollection")){
                            self.pcCfgObj["sysWhereCollection"] = [];
                        }
                        self.pcCfgObj["sysWhereCollection"].push(whereWgt);
                    }else if(pcOrMobile == "mobile"){
                        if(whereBlockType == "0"){
                            //满足所有
                            whereContent.find("input[name*='sys-filter-whereType-mobile'][value='0']").prop("checked",true);
                        }else{
                            //满足任一
                            whereContent.find("input[name*='sys-filter-whereType-mobile'][value='1']").prop("checked",true);
                        }
                        if(!self.mobileCfgObj.hasOwnProperty("sysWhereCollection")){
                            self.mobileCfgObj["sysWhereCollection"] = [];
                        }
                        self.mobileCfgObj["sysWhereCollection"].push(whereWgt);
                    }
                }
            }
        },
        drawInitUnify:function(whereContent,commonCfgArr){
            var self = this;
            /*var commonCfgArr = this.fdWhereBlock.commonCfg;*/
            for(var i = 0;i < commonCfgArr.length;i++){
                var whereObj = commonCfgArr[i];
                var whereType = whereObj.whereType;
                var whereBlockType = whereObj.whereBlockType;
                if(whereType == "0"){
                    if(whereBlockType == "0"){
                        //满足所有
                        whereContent.find("input[name*='data-filter-whereType-unify'][value='0']").prop("checked",true);
                    }else{
                        //满足任一
                        whereContent.find("input[name*='data-filter-whereType-unify'][value='1']").prop("checked",true);
                    }
                    //自定义
                    var $table = whereContent.find(".data-filter-content-unify .data-filter-content").find("table");
                    var whereWgt = new whereGenerator({container:$table ,parent:this,data:whereObj,wheretype:"custom_query"});
                    whereWgt.startup();
                    whereWgt.draw();
                    if(!self.commonCfgObj.hasOwnProperty("whereCollection")){
                        self.commonCfgObj["whereCollection"] = [];
                    }
                    self.commonCfgObj["whereCollection"].push(whereWgt);
                }else{
                    if(whereBlockType == "0"){
                        //满足所有
                        whereContent.find("input[name*='sys-filter-whereType-unify'][value='0']").prop("checked",true);
                    }else{
                        //满足任一
                        whereContent.find("input[name*='sys-filter-whereType-unify'][value='1']").prop("checked",true);
                    }
                    //内置
                    var $table = whereContent.find(".data-filter-content-unify .data-filter-content-sys").find("table");
                    var whereWgt = new whereGenerator({container:$table ,parent:this,data:whereObj,wheretype:"sys_query"});
                    whereWgt.startup();
                    whereWgt.draw();
                    if(!self.commonCfgObj.hasOwnProperty("sysWhereCollection")){
                        self.commonCfgObj["sysWhereCollection"] = [];
                    }
                    self.commonCfgObj["sysWhereCollection"].push(whereWgt);
                }
            }
        },
        buildEnvent4Where:function(whereContent){
            var self = this;
            //自定义查询或内置查询
            whereContent.on("click", ".common-data-filter-whereType li",function() {
                $(this).siblings().removeClass("active");
                $(this).addClass("active");
                var authValue = $(this).attr("value");
                var $whereType = $(this).closest(".common-data-filter-whereType");
                if(authValue === "1"){
                    //内置
                    $whereType.siblings(".data-filter-content").css("display","none");
                    $whereType.siblings(".data-filter-content-sys").css("display","block");
                    //触发查询条件的内置和自定义的事件
                    $whereType.siblings(".data-filter-content-sys").find("[data-where-value='1']").trigger("click");
                }else{
                    //自定义
                    $whereType.siblings(".data-filter-content").css("display","block");
                    $whereType.siblings(".data-filter-content-sys").css("display","none");
                }
            })
            //新增
            whereContent.find(".model-data-create").on("click",function(){
                var whereType = $(this).siblings(".common-data-filter-whereType").find("li.active").attr("value");
                if(whereType === "1"){
                    var contentContainer = $(this).siblings(".data-filter-content-sys");
                    var whereWgt = new whereGenerator({container:contentContainer.find("table"),parent:self,wheretype:"sys_query"});
                }else{
                    var contentContainer = $(this).siblings(".data-filter-content");
                    var whereWgt = new whereGenerator({container:contentContainer.find("table"),parent:self,wheretype:"custom_query"});
                }
                whereWgt.startup();
                whereWgt.draw();
                if(self.settingType === "0"){
                    //统一设置
                    if(!self.commonCfgObj.hasOwnProperty("sysWhereCollection")){
                        self.commonCfgObj["sysWhereCollection"] = [];
                    }
                    if(!self.commonCfgObj.hasOwnProperty("whereCollection")){
                        self.commonCfgObj["whereCollection"] = [];
                    }
                    if(whereType === "1"){
                        //内置查询
                        self.commonCfgObj["sysWhereCollection"].push(whereWgt);
                    }else{
                        //自定义查询
                        self.commonCfgObj["whereCollection"].push(whereWgt);
                    }
                }else{
                    //独立设置
                    if(!self.pcCfgObj.hasOwnProperty("sysWhereCollection")){
                        self.pcCfgObj["sysWhereCollection"] = [];
                    }
                    if(!self.pcCfgObj.hasOwnProperty("whereCollection")){
                        self.pcCfgObj["whereCollection"] = [];
                    }
                    if(!self.mobileCfgObj.hasOwnProperty("sysWhereCollection")){
                        self.mobileCfgObj["sysWhereCollection"] = [];
                    }
                    if(!self.mobileCfgObj.hasOwnProperty("whereCollection")){
                        self.mobileCfgObj["whereCollection"] = [];
                    }
                    var pcOrMobile = $(this).closest(".data-filter-div").attr("name");
                    if(whereType === "1"){
                        if(pcOrMobile == "pc"){
                            self.pcCfgObj["sysWhereCollection"].push(whereWgt);
                        }else{
                            self.mobileCfgObj["sysWhereCollection"].push(whereWgt);
                        }
                    }else{
                        if(pcOrMobile == "pc"){
                            self.pcCfgObj["whereCollection"].push(whereWgt);
                        }else{
                            self.mobileCfgObj["whereCollection"].push(whereWgt);
                        }
                    }
                }
            })
            whereContent.find(".common-data-filter-whereType li.active").trigger("click");
        },
        delTr:function($dom){
            var $tb = $dom.closest("table");
            var $tr = $dom.closest("tr");
            var curIndex = $tr.index();
            var whereType = $tb.attr("name");
            var aloneOrUnify = $tb.closest(".data-filter").attr("name");
            if(aloneOrUnify == "alone"){
                //独立设置
                var pcOrMobile = $tb.closest(".data-filter-div").attr("name");
                if(pcOrMobile == "pc"){
                    if(whereType == "custom_query"){
                        //自定义
                        var wgt = this.pcCfgObj.whereCollection[curIndex];
                        this.pcCfgObj.whereCollection.splice(curIndex,1);
                        topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                        wgt.destroy();
                        return;
                    }else if(whereType == "sys_query"){
                        //内置
                        var wgt = this.pcCfgObj.sysWhereCollection[curIndex];
                        this.pcCfgObj.sysWhereCollection.splice(curIndex,1);
                        topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                        wgt.destroy();
                        return;
                    }
                }else if(pcOrMobile == "mobile"){
                    if(whereType == "custom_query"){
                        //自定义
                        var wgt = this.mobileCfgObj.whereCollection[curIndex];
                        this.mobileCfgObj.whereCollection.splice(curIndex,1);
                        topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                        wgt.destroy();
                        return;
                    }else if(whereType == "sys_query"){
                        //内置
                        var wgt = this.mobileCfgObj.sysWhereCollection[curIndex];
                        this.mobileCfgObj.sysWhereCollection.splice(curIndex,1);
                        topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                        wgt.destroy();
                        return;
                    }
                }
            }else if(aloneOrUnify == "unify"){
                //统一设置
                if(whereType == "custom_query"){
                    //自定义
                    var wgt = this.commonCfgObj.whereCollection[curIndex];
                    this.commonCfgObj.whereCollection.splice(curIndex,1);
                    topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                    wgt.destroy();
                    return;
                }else if(whereType == "sys_query"){
                    //内置
                    var wgt = this.commonCfgObj.sysWhereCollection[curIndex];
                    this.commonCfgObj.sysWhereCollection.splice(curIndex,1);
                    topic.channel("modeling").unsubscribe("field.change", wgt.fieldChange, wgt);
                    wgt.destroy();
                    return;
                }
            }
        },
        moveWgt:function($dom,type, curIndex, targetIndex){
            var $tb = $dom.closest("table");
            var $tr = $dom.closest("tr");
            var whereType = $tb.attr("name");
            var aloneOrUnify = $tb.closest(".data-filter").attr("name");
            if(aloneOrUnify == "alone"){
                //独立设置
                var pcOrMobile = $tb.closest(".data-filter-div").attr("name");
                if(pcOrMobile == "pc"){
                    if(whereType == "custom_query"){
                        //自定义
                        var srcWgt = this.pcCfgObj.whereCollection[curIndex];
                        var targetWgt = this.pcCfgObj.whereCollection[targetIndex];
                        this.pcCfgObj.whereCollection[curIndex] = targetWgt;
                        this.pcCfgObj.whereCollection[targetIndex] = srcWgt;
                    }else if(whereType == "sys_query"){
                        //内置
                        var srcWgt = this.pcCfgObj.sysWhereCollection[curIndex];
                        var targetWgt = this.pcCfgObj.sysWhereCollection[targetIndex];
                        this.pcCfgObj.sysWhereCollection[curIndex] = targetWgt;
                        this.pcCfgObj.sysWhereCollection[targetIndex] = srcWgt;
                    }
                }else if(pcOrMobile == "mobile"){
                    if(whereType == "custom_query"){
                        //自定义
                        var wgt = this.mobileCfgObj.whereCollection[curIndex];
                        var srcWgt = this.mobileCfgObj.whereCollection[curIndex];
                        var targetWgt = this.mobileCfgObj.whereCollection[targetIndex];
                        this.mobileCfgObj.whereCollection[curIndex] = targetWgt;
                        this.mobileCfgObj.whereCollection[targetIndex] = srcWgt;
                    }else if(whereType == "sys_query"){
                        //内置
                        var wgt = this.mobileCfgObj.sysWhereCollection[curIndex];
                        var srcWgt = this.mobileCfgObj.sysWhereCollection[curIndex];
                        var targetWgt = this.mobileCfgObj.sysWhereCollection[targetIndex];
                        this.mobileCfgObj.sysWhereCollection[curIndex] = targetWgt;
                        this.mobileCfgObj.sysWhereCollection[targetIndex] = srcWgt;
                    }
                }
            }else if(aloneOrUnify == "unify"){
                //统一设置
               if(whereType == "custom_query"){
                    //自定义
                   var srcWgt = this.commonCfgObj.whereCollection[curIndex];
                   var targetWgt = this.commonCfgObj.whereCollection[targetIndex];
                   this.commonCfgObj.whereCollection[curIndex] = targetWgt;
                   this.commonCfgObj.whereCollection[targetIndex] = srcWgt;
                }else if(whereType == "sys_query"){
                    //内置
                   var srcWgt = this.commonCfgObj.sysWhereCollection[curIndex];
                   var targetWgt = this.commonCfgObj.sysWhereCollection[targetIndex];
                   this.commonCfgObj.sysWhereCollection[curIndex] = targetWgt;
                   this.commonCfgObj.sysWhereCollection[targetIndex] = srcWgt;
                }
            }
        },
        /**
         "commonCfg":[
                {
                    "whereType":"0",
                    "field":"docCreator|fdName",
                    "fieldType":"com.landray.kmss.sys.organization.model.SysOrgPerson|String",
                    "orgType":"ORG_TYPE_PERSON",
                    "fieldOperator":"!{equal}",
                    "fieldValue":"!{dynamic}"
                }
         ],
         commonCfgObj:{sysWhereCollection:[wgt],whereCollection:[wgt]}
         **/
        getKeyData : function() {
            var self = this;
            var fdWhereBlock = {};
            fdWhereBlock["whereBlockSettingType"] = this.settingType;
            if(this.settingType === "0"){
                //统一设置
                var whereBlockType = this.whereContent.find(".data-filter-content-unify")
                    .find("[name*='data-filter-whereType-unify']:checked").val();
                var syswhereBlockType = this.whereContent.find(".data-filter-content-unify")
                    .find("[name*='sys-filter-whereType-unify']:checked").val();
                fdWhereBlock["commonCfg"] = [];
                for(var key in this.commonCfgObj){
                    if(this.commonCfgObj[key] != null){
                        var whereArr = this.commonCfgObj[key];
                        for(var j = 0; j < whereArr.length; j++){
                            var whereObj = whereArr[j].getKeyData();
                            if(key === "sysWhereCollection"){
                                whereObj["whereBlockType"] = syswhereBlockType;
                            }else if(key === "whereCollection"){
                                whereObj["whereBlockType"] = whereBlockType;
                            }
                            fdWhereBlock["commonCfg"].push(whereObj);
                        }
                    }
                }
            }else{
                //独立设置
                var pcwhereBlockType = this.whereContent.find(".data-filter-pc")
                    .find("[name*='data-filter-whereType-pc']:checked").val();
                var pcsyswhereBlockType = this.whereContent.find(".data-filter-pc")
                    .find("[name*='sys-filter-whereType-pc']:checked").val();
                var mobilewhereBlockType = this.whereContent.find(".data-filter-mobile")
                    .find("[name*='data-filter-whereType-mobile']:checked").val();
                var mobilesyswhereBlockType = this.whereContent.find(".data-filter-mobile")
                    .find("[name*='sys-filter-whereType-mobile']:checked").val();
                fdWhereBlock["pcCfg"] = [];
                fdWhereBlock["mobileCfg"] = [];
                //处理pc
                for(var key in this.pcCfgObj){
                    if(this.pcCfgObj[key] != null){
                        var whereArr = this.pcCfgObj[key];
                        for(var j = 0; j < whereArr.length; j++){
                            var whereObj = whereArr[j].getKeyData();
                            if(key === "sysWhereCollection"){
                                whereObj["whereBlockType"] = pcsyswhereBlockType;
                            }else if(key === "whereCollection"){
                                whereObj["whereBlockType"] = pcwhereBlockType;
                            }
                            fdWhereBlock["pcCfg"].push(whereObj);
                        }
                    }
                }
                //处理移动
                for(var key in this.mobileCfgObj){
                    if(this.mobileCfgObj[key] != null){
                        var whereArr = this.mobileCfgObj[key];
                        for(var j = 0; j < whereArr.length; j++){
                            var whereObj = whereArr[j].getKeyData();
                            if(key === "sysWhereCollection"){
                                whereObj["whereBlockType"] = mobilesyswhereBlockType;
                            }else if(key === "whereCollection"){
                                whereObj["whereBlockType"] = mobilewhereBlockType;
                            }
                            fdWhereBlock["mobileCfg"].push(whereObj);
                        }
                    }
                }
            }
           return fdWhereBlock;
        },
        startup: function ($super, cfg) {

        }
    });
    exports.CollectionDataFilter = CollectionDataFilter;
})