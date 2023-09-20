/**
 * 明细表目标行生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var whereRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/whereRecordGenerator");
    var targetRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/targetRecordGenerator");
    var modelingLang = require("lang!sys-modeling-base");
    var DetailRecordGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.viewType = cfg.viewType;
            this.sourceData = cfg.sourceData;
            this.detailId = cfg.detailId;
            this.detailName = cfg.detailName;
            this.valueName = "fd_detail_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.whereWgtCollection = [];
            this.targetWgtCollection = [];
            this.modelMainName = cfg.parent.modelMainName;
        },

        startup: function ($super, cfg) {
            $super(cfg);
        },
        draw: function (container, detailTmpStr) {
            var self = this;
            detailTmpStr = detailTmpStr.replace(/!{明细表}/g, this.detailName);
            var $tr = $(detailTmpStr);
            if(self.parent.prefix == "create"){
            	//明细表查询所有数据隐藏
            	$tr.find(".detailWhereTypeinput3").hide();
            	 //查询条件切换事件
                $tr.find(".detailWhereTypediv input[type='radio']").on("change", function () {
                    var $whereBlockDom = $tr.find(".view_field_detail_where_div");
                    if (this.value === "3") {
                        $whereBlockDom.hide();
                    } else {
                        $whereBlockDom.show();
                    }
                });
            }else if(self.parent.prefix == "update" || self.parent.prefix == "delete"||self.parent.prefix == "notify"){
            	//明细表无查询条件隐藏
                $tr.find(".detailWhereTypeinput4").hide();
                //查询条件切换事件
                $tr.find(".detailWhereTypediv input[type='radio']").on("change",function(){
                	 var $whereBlockDom = $tr.find(".view_field_detail_where_div");
                	 if(this.value === "2"){
                		 $whereBlockDom.hide();
                	 }else{
                		 $whereBlockDom.show();
                	 }
                });
            }
            $tr.attr("detail-temp-item-id", this.valueName);
            //修改明细表查询条件类型的name
            $tr.find(".detailWhereTypediv input[type='radio']").attr("name","fdDetailWhereType_"+this.detailId);
            //画查询字段
            self.buildWhere($tr);
            //画目标字段
            self.buildTarget($tr);
            self.element = $tr;

            self.parent.addWgt(this, "detail");
            container.append(self.element);
            //#118006 ,#134616
            if (self.parent.prefix == "delete"||self.parent.prefix == "notify")  {
                container.find(".detail_target").remove();
            }
        },
        //画查询字段
        buildWhere: function ($table) {
            var self = this;
            // if (self.viewType == "create") {
            //     $table.find(".detail_where").hide()
            //     return;
            // }
            var $whereDiv = $table.find(".view_field_detail_where_div");
            // 添加事件
            $whereDiv.find(".table_opera").on("click", function (e) {

                e.stopPropagation();
                var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self, isDetail: true});
                var $whereTable = $table.find(".view_field_detail_where_table");
                whereWgt.draw($whereTable, true);
            });
        },
        // 画目标字段
        buildTarget: function ($table) {
            var self = this;
            var $targetTable = $table.find(".view_field_detail_target_table");
            var targetData = self.getTargetData();
            if (targetData && targetData.data) {
                for (var controlId in targetData.data) {
                    var targetWgt = new targetRecordGenerator.TargetRecordGenerator({parent: self,sourceData:self.sourceData});
                    $targetTable.append(targetWgt.draw(targetData.data[controlId]));
                }
            }
        },
        /**
         * 绘制select
         * @param $source
         * @param sourceData
         * @param cls
         * @param changeFun
         */
        buildSelect: function ($source, sourceData, cls, changeFun) {
            var $select = $("<select class='" + cls + "'></selct>");
            $select.append("<option value=''>"+modelingLang['relation.please.choose']+"</option>");
            if (sourceData) {
                for (var id in sourceData) {
                    var name = sourceData[id];
                    $select.append("<option value='" + id + "'>" + name + "</option>");
                }
            }
            $select.on("change", function (e) {
                if (changeFun) {
                    changeFun(e)
                }

            });
            $source.append($select);
        },

        /**
         * 获取目标明细表字段
         * @returns {{data: {}}}
         */
        getTargetData: function () {
            var sourceData = this.parent.getTargetData();
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
        /**
         * 获取前置表单字段
         * @returns {{data: {}}}
         */
        getPreModelData: function () {
            var sourceData = this.parent.getPreModelData();
            var preModelData = {data: {}};
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
                preModelData.data[controlId] = info;
            }
            preModelData.modelName = sourceData.modelName;
            return preModelData;
        },
        // type : where|target
        addWgt: function (wgt, type) {
            if (type === "where") {
                this.whereWgtCollection.push(wgt);
            } else if (type === "target") {
                this.targetWgtCollection.push(wgt);
            }
        },
        // type : where|target
        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "where") {
                collect = this.whereWgtCollection;
            } else if (type === "target") {
                collect = this.targetWgtCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },
        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "detail");
            $super(cfg);
        },

        /**
         * 整个模块清理
         */
        clear: function () {
            for (var i = 0; i < this.whereWgtCollection.length; i++) {
                this.whereWgtCollection[i].destroy();
            }
            for (var i = 0; i < this.targetWgtCollection.length; i++) {
                this.targetWgtCollection[i].destroy();
            }
            this.parent.deleteWgt(this, "detail");
            this.element.remove();
        },
        getKeyData: function () {
            var keyData = {};
            keyData.id = this.detailId;
            keyData.name = this.detailName;
            keyData.where = [];
            keyData.target = [];
            //明细表查询条件类型
            keyData.detailWhereType = $("input[name='fdDetailWhereType_"+this.detailId+"']:checked").val();
            for (var i = 0; i < this.whereWgtCollection.length; i++) {
                var whereWgt = this.whereWgtCollection[i];
                var whereWgtKeyData = whereWgt.getKeyData();
                if (!whereWgtKeyData || keyData.detailWhereType == "3" || keyData.detailWhereType == "2") {
                    continue;
                }
                // 索引，用来进来记录排序，暂无用
                whereWgtKeyData.idx = i;
                keyData.where.push(whereWgtKeyData);
            }
            for (var i = 0; i < this.targetWgtCollection.length; i++) {
                var targetWgt = this.targetWgtCollection[i];
                var targetWgtKeyData = targetWgt.getKeyData();
                // 索引，用来进来记录排序，暂无用
                targetWgtKeyData.idx = i;
                keyData.target.push(targetWgtKeyData);
            }
            return keyData;
        },
        initByStoreData: function (storeData) {
            //where
            if (storeData.hasOwnProperty("where")) {
                var storeWhere = storeData["where"];
                for (var i = 0; i < storeWhere.length; i++) {
                    var data = storeWhere[i];
                    var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: this, isDetail: true});
                    var $whereTable = this.element.find(".view_field_detail_where_table");
                    whereWgt.draw($whereTable, true);
                    whereWgt.initByStoreData(data);
                }
            }
            //target
            if (storeData.hasOwnProperty("target")) {
                var storeTarget = storeData["target"];
                for (var i = 0; i < storeTarget.length; i++) {
                    var data = storeTarget[i];
                    var targetWgt = this.getTargetWgtByControlId(data.name.value);
                    if (targetWgt) {
                        targetWgt.initByStoreData(data);
                    }
                }
            }
            //明细表查询条件类型
            if (storeData.hasOwnProperty("detailWhereType")) {
                var detailWhereType = storeData["detailWhereType"];
                $("[name='fdDetailWhereType_"+storeData["id"]+"'][value='" + detailWhereType + "']").prop("checked", "checked");
                if(detailWhereType == "2" || detailWhereType == "3"){
                	$("[name='fdDetailWhereType_"+storeData["id"]+"']").closest("div").next().hide();
                }
            }
        },
        getTargetWgtByControlId: function (controlId) {
            var wgt = null;
            for (var i = 0; i < this.targetWgtCollection.length; i++) {
                var targetWgt = this.targetWgtCollection[i];
                if (targetWgt.targetData.name === controlId) {
                    wgt = targetWgt;
                    break;
                }
            }
            return wgt;
        },

        endsWith : function(str,pattern){
            var reg = new RegExp(pattern + "$");
            return reg.test(str);
        },
        validators:function (){
            var validator = true;
            for (var i = 0; i < this.targetWgtCollection.length; i++) {
                var targetWgt = this.targetWgtCollection[i];
                var validate = targetWgt.validators();
                if(!validate){
                    validator = false;
                }
            }
            return validator;
        }
    });
    exports.DetailRecordGenerator = DetailRecordGenerator;

});
