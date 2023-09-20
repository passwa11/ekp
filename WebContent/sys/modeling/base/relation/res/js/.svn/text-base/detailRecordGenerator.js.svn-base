/**
 * 明细表选择生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var whereRecordGenerator = require("sys/modeling/base/relation/res/js/whereRecordGenerator");

    var DetailRecordGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.detailId = cfg.detailId;
            this.detailName = cfg.detailName;
            this.valueName = "fd_detail_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.whereWgtCollection = []
            this.pcfg = cfg.pcfg;
        },

        startup: function ($super, cfg) {
            $super(cfg);
        },
        draw: function (container) {
            var self = this;
            var $temp = container.find("[mdlng-rltn-property=\"fdDetailWhereTemp\"]");
            var $inWhereTr = container.find("[mdlng-rltn-property=\"fdInWhere\"]");
            var $tr = $("<tr mdlng-rltn-property=\"fdDetailWhere\"></tr>");
            var trHtml = $temp.html();
            trHtml = trHtml.replace(/fdDetailWhereTemp/g,"fdDetailWhere");
            $tr.html(trHtml);
            $inWhereTr.after($tr);
            $tr.find(".detail_table_name").html(this.detailName+"查询条件");
            $tr.find(".detailWhereTypediv input[type='radio'][value='0']").prop("checked",true);
            //查询条件切换事件
            $tr.find(".detailWhereTypediv input[type='radio']").on("change", function () {
                var $whereBlockDom = $tr.find("[mdlng-rltn-prprty-value='fdDetailWhere']");
                if (this.value === "2") {
                    $whereBlockDom.hide();
                } else {
                    $whereBlockDom.show();
                }
            });
            $tr.attr("detail-temp-item-id", this.valueName);
            //修改明细表查询条件类型的name
            $tr.find(".detailWhereTypediv input[type='radio']").attr("name","fdDetailWhereType_"+this.detailId);
            //画查询字段
            self.buildWhere($tr);
            self.element = $tr;

            self.parent.addWgt(this, "detail");
            self.element.show();
        },
        //画查询字段
        buildWhere: function ($table) {
            var self = this;
            var whereWgt = new whereRecordGenerator.WhereRecordGenerator({
                fieldName: "fdDetailWhere",
                pcfg: self.pcfg,
                isDetail: true,
                detailId: self.detailId,
                parent:self
            });
            var $whereTable = $table.find(".view_field_detail_where_table");
            whereWgt.draw($whereTable, true);
            self.addWgt(whereWgt,"where");
        },


        addWgt: function (wgt, type) {
            if (type === "where") {
                this.whereWgtCollection.push(wgt);
            }
        },
        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "where") {
                collect = this.whereWgtCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },
        destroy : function($super,cfg) {
            for (var i = 0; i < this.whereWgtCollection.length; i++) {
                this.whereWgtCollection[i].destroy();
            }
            $super(cfg);
        },
        removeWgt: function () {
            for (var i = 0; i < this.whereWgtCollection.length; i++) {
                this.whereWgtCollection.splice(i, 1);
            }
        },

        /**
         * 整个模块隐藏
         */
        hideElement: function () {
            this.element.hide();
        },
        showElement: function () {
            this.element.show();
        },
        getKeyData: function () {
            var keyData = {};
            //数据来源为明细时
            if(this.parent.detailRule == "1"){
                keyData.id = this.detailId;
                keyData.name = this.detailName;
                keyData.where = [];
                //明细表查询条件类型
                keyData.detailWhereType = $("input[name='fdDetailWhereType_"+this.detailId+"']:checked").val();
                //明细表查询条件类型为“查询所有数据”，则不记录查询条件
                if(keyData.detailWhereType != "2"){
                    for (var i = 0; i < this.whereWgtCollection.length; i++) {
                        var whereWgt = this.whereWgtCollection[i];
                        var whereWgtKeyData = whereWgt.getKeyData();
                        if (!whereWgtKeyData) {
                            continue;
                        }
                        // 索引，用来进来记录排序，暂无用
                        whereWgtKeyData.idx = i;
                        keyData.where.push(whereWgtKeyData);
                    }
                }
            }

            return keyData;
        },
        initByStoreData: function (storeData) {
            //where
            if (storeData.hasOwnProperty("where")) {
                var storeWhere = storeData["where"];
                for (var i = 0; i < storeWhere.length; i++) {
                    var data = storeWhere[i];
                    var whereWgt = new whereRecordGenerator.WhereRecordGenerator({
                        fieldName: "fdDetailWhere",
                        pcfg: this.pcfg,
                        isDetail: true,
                        detailId: storeData["id"]
                    });
                    var $whereTable = this.element.find(".view_field_detail_where_table");
                    whereWgt.draw($whereTable, true);
                    whereWgt.initByStoreData(data);
                }
            }
            //明细表查询条件类型
            if (storeData.hasOwnProperty("detailWhereType")) {
                var detailWhereType = storeData["detailWhereType"];
                $("[name='fdDetailWhereType_"+storeData["id"]+"'][value='" + detailWhereType + "']").prop("checked", "checked");
                //查询条件类型为“查询所有数据”时，隐藏条件配置
                if(detailWhereType == "2"){
                    $("[name='fdDetailWhereType_"+storeData["id"]+"']").closest("td").find("div[mdlng-rltn-prprty-value=\"fdDetailWhere\"]").hide();
                }
            }
        },

        endsWith : function(str,pattern){
            var reg = new RegExp(pattern + "$");
            return reg.test(str);
        },
    });
    exports.DetailRecordGenerator = DetailRecordGenerator;
});
