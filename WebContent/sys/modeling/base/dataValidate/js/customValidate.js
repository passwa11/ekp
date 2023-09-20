/**
 * 数据提交校验----自定义校验规则
 */
define(function(require, exports, module) {
    var base = require('lui/base');
    var $ = require('lui/jquery');
    var topic = require('lui/topic');
    var dialog = require('lui/dialog');
    var whereRecordGenerator = require("sys/modeling/base/dataValidate/js/whereRecordGenerator");
    var detailWhereRecordGenerator = require("sys/modeling/base/dataValidate/js/detailWhereRecordGenerator");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var modelingLang = require("lang!sys-modeling-base");
    var CustomValidate = base.Component.extend({
        initProps: function($super, cfg) {
            $super(cfg);
            this.contentContainer = cfg.contentContainer;
            this.modelId = cfg.modelId;
            this.fdCfg = cfg.fdCfg;
            this.detailId = cfg.detailId || "";
            this.detailIds = cfg.detailIds || [];
            this.whereWgtCollection = [];
            this.detailwhereWgtCollection = [];
            this.isCustomValidate = true;
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        draw : function($super, cfg){
            $super(cfg);
            //初始化当前表单数据
            this.initTargetInfo();
            this.addEvent();
            this.drawDetail();
            if(this.fdCfg){
                this.initByStoreData(this.fdCfg);
            }else{
                //新建时，默认触发主表
                $("[name='judgeType'][value='0']").trigger($.Event("change"));
            }
        },
        doRender : function($super, cfg) {
            $super(cfg);
        },
        addEvent :function(){
            var self = this;
            this.judgeTypeClickEvent();
            this.mainWhereClickEvent();
        },
        drawDetail : function(){
            var self = this;
            var targetDetail = {};
            if ($.isEmptyObject(self.getTargetData())) {
                return;
            }
            //获取当前表单所有明细表
            targetDetail = self.filterDetailNameAndId(self.getTargetData().data);
            //一定要目标存在明细表 目标若不存在明细表则返回,本身可以没有明细表
            if (!targetDetail) {
                //没有明细表时，将判断类型明细表屏蔽
                $("[name='judgeType'][value='1']").css("display","none");
                $("[name='judgeType'][value='1']").next().css("display","none");
                return;
            } else {
                $("[name='judgeType'][value='1']").css("display","inline-block");
                $("[name='judgeType'][value='1']").next().css("display","inline-block");
                //1、构建明细表下拉框
                this.targetDetail = targetDetail;
                this.$select = $("[name='detailId']");
                for (var key in targetDetail) {
                    if(key === this.detailId || this.detailIds.indexOf(key) < 0){
                        var $option = $("<option value='"+key+"'>" + targetDetail[key] + "</option>")
                        $option.appendTo(this.$select);
                        if(key === this.detailId){
                            this.$select.val(key);
                            this.$select.change();
                        }
                    }
                }
                //2、明细表下拉框切换事件
                this.$select.on("change", function (e) {
                    //当前选中的明细表id
                    var detailId = $(this).val();
                    //初始化的明细表查询条件
                    var detailWhereData = self.detailWhere;
                    if(self.detailId && detailId != self.detailId){
                        //切换的时候不做初始化
                        detailWhereData = {};
                    }
                    //明细表只有一条查询条件，先做清空
                    self.clearWgt();
                    self.detailId = detailId;
                    if ($.isEmptyObject(self.getDetailTargetData())) {
                        dialog.alert("'"+modelingLang['sysModelingRelation.fdTargetDetailPlease']+"'");
                        return;
                    }
                    var whereWgt = new detailWhereRecordGenerator.DetailWhereRecordGenerator({parent: self});
                    var $whereTable = $(".detail_where_tmp_html").find(".view_field_detail_where_table");
                    whereWgt.draw($whereTable,true);
                    if(detailWhereData && JSON.stringify(detailWhereData) != "{}"){
                        whereWgt.initByStoreData(detailWhereData);
                    }
                });
            }
        },
        clearWgt: function () {
            for (var i = 0; i < this.detailwhereWgtCollection.length; i++) {
                this.detailwhereWgtCollection[i].destroy();
                i--;
            }
        },
        filterDetailNameAndId: function (data) {
            var detail = undefined;
            for (var controlId in data) {
                if (controlId.indexOf(".") < 0) {
                    continue;
                }
                if (!detail) {
                    detail = {};
                }
                var item = data[controlId];
                var names = item.label.split(".");
                var ids = controlId.split(".");
                detail[ids[0]] = names[0];
            }
            return detail;
        },
        judgeTypeClickEvent : function(){
            var self = this;
            $("[name='judgeType']").on("change",function () {
                $(this).prop("checked","checked");
                if($(this).val() == "1"){
                    $(".data-other-detail").show();
                    $(".data-other-main").hide();
                    //切换到明细表时，触发明细表下拉框事件
                    if(!self.detailId){
                        $("[name='detailId'] option:first").trigger("change");
                    }else{
                        $("[name='detailId'] option[value='"+self.detailId+"']").trigger("change");
                    }
                }else{
                    $(".data-other-detail").hide();
                    $(".data-other-main").show();
                }
            })
        },
        mainWhereClickEvent : function(){
            var self = this;
            this.contentContainer.find(".model-mask-panel-table-create").off("click").on("click",function (e) {
                e.stopPropagation();
                if ($.isEmptyObject(self.getTargetData())) {
                    return;
                }
                var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self});
                var $whereTable = self.contentContainer.find(".view_field_pre_model_where_table");
                whereWgt.draw($whereTable);
            })
        },
        initTargetInfo:function () {
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + this.modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    data = data?JSON.parse(data):null;
                    self.formatTargetData(data);
                    //获取当前表单中所有业务关联控件的信息
                    self.getRelationControlInfo();
                    //获取关联表单，并初始化公式定义器的关联表单常量
                    self.getRelationModel();
                    //初始化公式定义器变量
                    formulaBuilder.initFieldList(data.xformId,true);
                }
            });
        },
        getRelationModel : function(){
            var self = this;
            var placeHolders = this.placeHolders;
            for(var i = 0;i < placeHolders.length; i++){
                var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingAppDataValidate.do?method=getRelation&modelId=" + this.modelId+"&widgetId="+placeHolders[i].name;
                $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    success: function (data, status) {
                        data = data?JSON.parse(data):null;
                        //初始化关联表单常量
                        self.formatRelationData(data,placeHolders[i].label);
                    }
                });
            }
        },
        formatRelationData : function(rtn,name){
            if(rtn  == null){
                return;
            }
            this.relationData = {};
            //改变数据格式，转换成数组
            var dataArr = this.changeWidget(rtn.mainWidgets);
            this.relationData.data = dataArr;
            var varName = modelingLang['modeling.dataValidate.RelationForm']+"("+name.split("(")[0]+")";
            formulaBuilder.initOtherFieldList(dataArr,varName,"preModel","customValidate");
        },
        changeWidget: function(data){
            var dataArr = [];
            for(var key in data){
                if(data[key].name.indexOf(".") > -1 ){
                    continue;
                }
                dataArr.push(data[key]);
            }
            return dataArr;
        },
        getPreModelData: function () {
            return this.relationData;
        },
        getRelationControlInfo : function(){
            this.placeHolders = [];
            var fields = this.targetData.data;
            for(var key in fields){
                if(fields[key].businessType == "placeholder"){
                    if(fields[key].name.indexOf("_text") > -1){
                        continue;
                    }
                    //业务关联控件
                    this.placeHolders.push(fields[key]);
                }
            }
        },
        formatTargetData : function(data){
            this.targetData = data;
            this.targetData.modelId = this.modelId;
            this.targetData.modelName= this.modelName;
        },
        getTargetData:function (){
            return this.targetData;
        },
        getDetailTargetData : function(){
            var sourceData = this.targetData;
            var targetData = {data: {}};
            var targetDetailId = this.detailId;
            if (!sourceData || !sourceData.data || !targetDetailId) {
                return null;
            }
            for (var controlId in sourceData.data) {
                if(typeof(String.prototype.endsWith) === "function" && controlId.endsWith("_config") || this.endsWith(controlId,"_config")){
                    //#127245 过滤关联文档 ,关联文档控件有controlId，controlId_config,暂时屏蔽controlId_config
                    continue;
                }
                var info = sourceData.data[controlId];
                if (info.name.indexOf(targetDetailId) >= 0)
                    targetData.data[controlId] = info;
            }
            return targetData;

        },
        endsWith : function(str,pattern){
            var reg = new RegExp(pattern + "$");
            return reg.test(str);
        },
        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "where") {
                collect = this.whereWgtCollection;
            }else if(type === "detailWhere"){
                collect = this.detailwhereWgtCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },
        addWgt: function (wgt, type) {
            if (type === "where") {
                this.whereWgtCollection.push(wgt);
            }else if(type === "detailWhere"){
                this.detailwhereWgtCollection.push(wgt);
            }
        },
        getKeyData : function(){
            /*****需要到对应的edit页面提交保存方法中增加对应的字段到config中*****/
            var keyData = {};
            keyData.judgeType = $("[name='judgeType']:checked").val();
            keyData.detailId = $("[name='detailId'] option:selected").val();
            keyData.mainWhereType = $("[name='mainWhereType']:checked").val();
            keyData.detailWhereType = $("[name='detailWhereType']:checked").val();
            keyData.where = [];
            if(keyData.judgeType == "0"){
                for(var i = 0;i < this.whereWgtCollection.length;i++){
                    var whereData = this.whereWgtCollection[i].getKeyData();
                    keyData.where.push(whereData);
                }
            }else{
                for(var i = 0;i < this.detailwhereWgtCollection.length;i++){
                    var detailWhere = this.detailwhereWgtCollection[i].getKeyData();
                    keyData.where.push(detailWhere);
                }
            }
            return keyData;
        },
        initByStoreData: function (storeData) {
            var self = this;
            if(storeData){
                storeData = $.parseJSON(storeData);
                var judgeType = storeData.judgeType;
                this.judgeType = judgeType;
                var mainWhereType = storeData.mainWhereType;
                var detailWhereType = storeData.detailWhereType;
                $("[name='mainWhereType'][value='"+mainWhereType+"']").prop("checked","checked");
                $("[name='detailWhereType'][value='"+detailWhereType+"']").prop("checked","checked");
                this.detailId = storeData.detailId;
                var storeWhere = storeData.where;
                if(judgeType == "1"){
                    this.detailWhere = storeData.where[0];
                    //明细表分支
                    $("[name='judgeType'][value='1']").trigger($.Event("change"));
                    //触发选中的明细表
                    if(this.detailId){
                        $("[name='detailId'] option[value='"+this.detailId+"']").prop("selected",true);
                        $("[name='detailId'] option[value='"+this.detailId+"']").trigger("change");
                    }else {
                        $("[name='detailId'] option:first").trigger("change");
                    }
                }else{
                    $("[name='judgeType'][value='0']").trigger($.Event("change"));
                    if(storeWhere){
                        for (var i = 0; i < storeWhere.length; i++) {
                            var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self});
                            var $whereTable = self.contentContainer.find(".view_field_pre_model_where_table");
                            whereWgt.draw($whereTable);
                            whereWgt.initByStoreData(storeWhere[i]);
                        }
                    }
                }
            }else{
                $("[name='judgeType'][value='0']").trigger($.Event("change"));
            }
        }
    })
    exports.CustomValidate = CustomValidate;
});