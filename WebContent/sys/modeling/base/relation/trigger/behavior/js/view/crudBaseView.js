/**
 *
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var baseView = require("sys/modeling/base/relation/trigger/behavior/js/view/baseView");
    var whereRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/whereRecordGenerator");
    var sqlWhereRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/sqlWhereRecordGenerator");
    var targetRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/targetRecordGenerator");
    var detailRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/detailRecordGenerator");
    var preQueryRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/preQueryRecordGenerator");
    var detailQueryRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/detailQueryRecordGenerator");
    var modelingLang = require("lang!sys-modeling-base");
    /**
     * 处理明细表、查询、目标
     */
    var CrudBaseView = baseView.BaseView.extend({


        initProps: function ($super, cfg) {
            $super(cfg);
            this.sourceData = cfg.parent.sourceData;
            this.whereWgtCollection = [];
            //this.preWhereWgtCollection = [];		//提到父类behavior.js中，切换视图时不会重新渲染
            this.targetWgtCollection = [];
            this.detailCollection = [];
            this.preQueryCollection = [];
            this.detailRule = this.CONST.DETAIL_RULE_MAIN;
            this.appId = cfg.parent.appId || "";
            this.modelMainName = cfg.parent.modelMainName;
            this.xformId = cfg.parent.xformId || "";
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
        },

        initByStoreData: function (storeData) {
            if (storeData) {
                if (storeData.hasOwnProperty("where")) {
                    var storeWhere = storeData["where"];
                    for (var i = 0; i < storeWhere.length; i++) {
                        var data = storeWhere[i];
                        var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: this});
                        whereWgt.draw(this.getWhereContainer());
                        whereWgt.initByStoreData(data);
                    }
                }
                if (storeData.hasOwnProperty("detailQuery")) {
                    var storeWhere = storeData["detailQuery"];
                    for (var i = 0; i < storeWhere.length; i++) {
                        var data = storeWhere[i];
                        var whereWgt = new detailQueryRecordGenerator.DetailQueryRecordGenerator({parent: this,sourceData:this.parent.currentData});
                        whereWgt.draw(this.getDetailQueryContainer());
                        whereWgt.initByStoreData(data);
                    }
                }
                if (storeData.hasOwnProperty("sqlWhereParams")) {
                    var storeWhere = storeData["sqlWhereParams"];
                    var sql = storeData["sqlWhere"];
                    for (var i = 0; i < storeWhere.length; i++) {
                        var data = storeWhere[i];
                        var whereWgt = new sqlWhereRecordGenerator.WhereRecordGenerator({parent: this});
                        whereWgt.draw(this.getSqlWhereContainer());
                        whereWgt.initByStoreData(data);
                    }
                    this.element.find("#sqlWhereArea").val(sql || "");
                }
                if (storeData.hasOwnProperty("sqlWhereType")) {
                    var sqlWhereType = storeData["sqlWhereType"];
                    this.sqlWhereType = sqlWhereType;
                    this.element.closest("#relationEditContainer").find("input[name='fdSqlWhereType'][value='"+sqlWhereType+"']").attr("checked","checked");
                    this.element.closest("#relationEditContainer").find(".WhereCodeDiv input[type='radio']").filter(':checked').trigger($.Event("change"));
                }
                if (storeData.hasOwnProperty("preWhere")) {
                    var storePreWhere = storeData["preWhere"];
                    for (var i = 0; i < storePreWhere.length; i++) {
                        var data = storePreWhere[i];
                        var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: this,isPreWhere:true,currentData:this.parent.currentData});
                        whereWgt.draw(this.getPreWhereContainer());
                        whereWgt.initByStoreData(data);
                    }
                }
                
                if (storeData.hasOwnProperty("preWhereType")) {
                	var preWhereType = storeData["preWhereType"];
                	this.element.closest("#relationEditContainer").find("input[name='fdPreWhereType'][value='"+preWhereType+"']").attr("checked","checked");
                	this.element.closest("#relationEditContainer").find(".preWhereTypediv input[type='radio']").filter(':checked').trigger($.Event("change"));
                }

                if(storeData.hasOwnProperty("preSqlWhere")){
                    this.element.closest("#relationEditContainer").find("textarea[name='preSqlWhere']").val(storeData["preSqlWhere"]);
                }

                //初始化前置查询
                if (storeData.hasOwnProperty("preQuery")) {
                    var storePreQuery = storeData["preQuery"];
                    for (var i = 0; i < storePreQuery.length; i++) {
                        var data = storePreQuery[i];
                        var preQueryWgt = new preQueryRecordGenerator.PreQueryRecordGenerator({parent: this});
                        preQueryWgt.draw(this.getPreQueryContainer());
                        preQueryWgt.initByStoreData(data);
                    }
                }

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
                if (storeData.hasOwnProperty("detailRule")) {
                    var detailOrMainName = this.prefix + "detailOrMain";
                    this.element.find("[name=" + detailOrMainName + "]").each(function (idx, dom) {
                        if ($(this).val() == storeData.detailRule) {
                            $(this).prop("checked", "true");
                            $(this).trigger($.Event("click"))
                        }
                    });
                }
                if (storeData.hasOwnProperty("detail")) {
                    var storeDetail = storeData["detail"];
                    var detailCheckedName = this.prefix + "detailChecked";
                    this.element.find("[name=" + detailCheckedName + "]").each(function (idx, dom) {

                        for (var i = 0; i < storeDetail.length; i++) {
                            if ($(dom).val() == storeDetail[i].id) {
                                $(dom).trigger($.Event("click"));
                                break;
                            }
                        }
                    });
                    for (var i = 0; i < this.detailCollection.length; i++) {
                        var detailItem = this.detailCollection[i];
                        for (var j = 0; j < storeDetail.length; j++) {
                            if (detailItem.detailId == storeDetail[j].id) {
                                detailItem.initByStoreData(storeDetail[j]);
                                break;
                            }
                        }
                    }
                }
            }
        },
        buildPreWhereView : function(){
        	var $preElement = $("<div class='behavior_update_preview'/>");
            var $preTable = $("<table class='tb_simple modeling_form_table preview_table' width='100%'/>");
            //前置查询
            this.buildPreWhere($preTable, this.parent.preWhereTmpStr,this.parent.currentData);
            //查询条件切换事件
            $preTable.find(".preWhereTypediv input[type='radio']").on("change",function(){
            	 var $preWhereBlockDom = $preTable.find(".view_field_pre_where_div");
            	 if(this.value === "3"){
            		 $preWhereBlockDom.hide();
            	 }else{
            		 $preWhereBlockDom.show();
            	 }
            });
            $preTable.find(".view_fdId_where_table").remove();
            $preElement.append($preTable);
            this.config.preViewContainer.append($preElement);
        },

        // 画本表单前置查询字段
        buildPreWhere: function ($table, whereTmpStr,currentData) {
            var self = this;
            $table.append(whereTmpStr);
            var $whereDiv = $table.find(".view_field_pre_where_div");
            //$table.find("[name='fdPreWhereType']").attr("name", "fdPreWhereType" + this.valueName);
            // 添加事件
            $whereDiv.find(".table_opera").on("click", function (e) {
                e.stopPropagation();
                if ($.isEmptyObject(currentData)) {
                    dialog.alert(modelingLang['behavior.information.form.empty']);
                    return;
                }
                var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self,isPreWhere:true,currentData:currentData});
                var $whereTable = $table.find(".view_field_pre_where_table");
                whereWgt.draw($whereTable);
            });
        },

        buildDetailQueryView : function(){
            var detailData = this.filterDetailNameAndId(this.parent.currentData.data);
            if (detailData) {
                var $detailElement = $("<div class='behavior_detail_query_preview'/>");
                var $detailTable = $("<table class='tb_simple modeling_form_table preview_table' width='100%'/>");
                //明细表查询
                this.buildDetailQuery($detailTable, this.parent.detailQueryTmpStr,this.parent.currentData);
                $detailElement.append($detailTable);
                this.config.preViewContainer.append($detailElement);
            }
        },

        buildDetailQuery : function($table, detailQueryTmpStr,currentData){
            var self = this;
            $table.append(detailQueryTmpStr);
            var $detailQueryDiv = $table.find(".view_field_detail_query_div");
            // 添加事件
            $detailQueryDiv.find(".table_opera").on("click", function (e) {
                e.stopPropagation();
                var targetWgt = new detailQueryRecordGenerator.DetailQueryRecordGenerator({parent: self,sourceData:currentData});
                var $targetTable = $table.find(".view_field_detail_query_table");
                targetWgt.draw($targetTable);
            });
        },

        buildWhereMain: function($table, whereTmpStr){
            var $whereTmp = $(whereTmpStr);
            if(this.prefix === "create"){
                $whereTmp.find("td.td_normal_title").text(modelingLang["sysModelingBehavior.Deduplication"]);
            }else {
                $whereTmp.find("td.td_normal_title").text(modelingLang["modelingAppListview.fdWhereBlock"]);
            }
            $table.append($whereTmp);
        },

        // 画目标表单查询字段
        buildWhere: function ($table, whereTmpStr) {
            var self = this;

            self.buildWhereMain($table,self.parent.whereMainTmpStr);

            $table.append(whereTmpStr);
            var $whereDiv = $table.find(".view_field_where_div");
            $table.find("[name='fdWhereType']").attr("name", "fdWhereType" + this.valueName);
            // 添加事件
            $whereDiv.find(".table_opera").on("click", function (e) {
            	$(".mainModelWhereTip").remove();
                e.stopPropagation();
                if ($.isEmptyObject(self.getTargetData())) {
                    dialog.alert(modelingLang['behavior.select.target.form.first']);
                    return;
                }
                var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self});
                var $whereTable = $table.find(".view_field_where_table");
                whereWgt.draw($whereTable);
            });

            self.buildSqlWhere($table,self.parent.sqlWhereTmpStr);
        },

        buildSqlWhere: function ($table, whereTmpStr) {
            var self = this;
            $table.append(whereTmpStr);
            var $whereDiv = $table.find(".view_field_where_sql_div");
            //$table.find("[name='fdWhereType']").attr("name", "fdWhereType" + this.valueName);
            // 添加事件
            $whereDiv.find(".table_sql_opera").on("click", function (e) {
                $(".mainModelWhereTip").remove();
                e.stopPropagation();
                if ($.isEmptyObject(self.getTargetData())) {
                    dialog.alert("请先选择目标表单！");
                    return;
                }
                var whereWgt = new sqlWhereRecordGenerator.WhereRecordGenerator({parent: self});
                var $whereTable = $table.find(".view_field_where_sql_table");
                whereWgt.draw($whereTable);
            });

            $table.find(".WhereCodeDiv input[type='radio']").on("change",function(){
                self.sqlWhereType = this.value;
                var $whereBlockDom0 = $table.find(".where_sql_0");
                var $whereBlockDom1 = $table.find(".where_sql_1");
                if(this.value === "0"){
                    $whereBlockDom0.show();
                    $whereBlockDom1.hide();
                }else{
                    $whereBlockDom0.hide();
                    $whereBlockDom1.show();
                }
            });
            $table.find(".WhereCodeDiv input[type='radio']").filter(':checked').trigger($.Event("change"));
        },

        //画前置查询
        buildPreQuery: function ($table, preQueryTmpStr) {
            var self = this;
            $table.append(preQueryTmpStr);
            var $preQueryDiv = $table.find(".view_field_pre_query_div");
            // 添加事件
            $preQueryDiv.find(".table_opera").on("click", function (e) {
                e.stopPropagation();
                var targetWgt = new preQueryRecordGenerator.PreQueryRecordGenerator({parent: self});
                var $targetTable = $table.find(".view_field_pre_query_table");
                targetWgt.draw($targetTable);
            });
        },

        //明细表规则
        buildDetailRule: function ($table, detailRuleTmpStr, detailTmpStr) {
            var self = this;
            //判断是否需要明细表
            var targetDetail = {};
            var targetData = self.getTargetData();
            if (targetData && targetData.data) {
                targetDetail = self.filterDetailNameAndId(targetData.data);
            }
            var $detailRuleTmp = $(detailRuleTmpStr);
            if (self.prefix == "notify"){
                $detailRuleTmp.find("#type").html(modelingLang['behavior.query.range']);
            }

            //一定要目标存在明细表 目标若不存在明细表则返回,本身可以没有明细表
            if (!targetDetail) {
                return;
            } else {
                this.targetDetail = targetDetail;
                for (var key in targetDetail) {
                    var $checked = $(" <label style=\"margin-right: 18px;display: inline-block;height:20px;line-height:20px;\"></label>");
                    var detailCheckedName = self.prefix + "detailChecked";
                    var checkedType;
                    if (self.prefix == "notify"){
                        checkedType = "radio";
                    }else {
                        checkedType = "checkbox";
                    }
                    $("<input  value='" + key + "' " +
                        " style=\"display: inline-block;vertical-align: middle;\" " +
                        "type="+checkedType+ " " +
                        "name=\"" + detailCheckedName + "\" " +
                        " subject =\""+modelingLang['sysModelingRelation.fdTargetDetail']+"\">").appendTo($checked);
                    $("<span>" + targetDetail[key] + "</span>").appendTo($checked);
                    $checked.appendTo($detailRuleTmp.find("#detailChecked"));
                }
                $detailRuleTmp.find("[name=" + detailCheckedName + "]").on("click", function (e) {
                    e.stopPropagation();
                        var checked = $(this).prop("checked");
                        var detailId = $(this).val();
                        if (checked) {
                            self.buildDetail($table, detailTmpStr, detailId);
                        } else {
                            self.removeDetail(detailId);
                        }
                });
                var detailOrMainName = self.prefix + "detailOrMain";
                $detailRuleTmp.find("[name=detailOrMain]").attr("name", detailOrMainName);
                $detailRuleTmp.find("[name=" + detailOrMainName + "]").on("click", function (e) {
                    e.stopPropagation();
                    var checked = $(this).prop("checked");
                    if (checked) {
                        var val = $(this).val();
                        self.detailRule = val;
                        switch (val) {
                            case self.CONST.DETAIL_RULE_MAIN:
                                // 仅主表
                                $table.find(".detailChecked_Tr").hide();
                                $table.find(".detail_where").hide();
                                $table.find(".detail_target").hide();
                                $table.find(".main_target").show();
                                if (self.creatorConfig) {
                                    if (!self.updateOrCreateFlag || self.updateOrCreateFlag == "1") {
                                        self.creatorConfig.show();
                                    }
                                }
                                break;
                            case self.CONST.DETAIL_RULE_DETAIL:
                                // 仅明细
                                $table.find(".detailChecked_Tr").show();
                                $table.find(".detail_where").show();
                                $table.find(".detail_target").show();
                                $table.find(".main_target").hide();
                                if (self.creatorConfig) {
                                    self.creatorConfig.hide();
                                }
                                break;
                            case self.CONST.DETAIL_RULE_BOTH:
                                $table.find(".detailChecked_Tr").show();
                                $table.find(".detail_where").show();
                                $table.find(".detail_target").show();
                                $table.find(".main_target").show();
                                if (self.creatorConfig) {
                                    if (!self.updateOrCreateFlag || self.updateOrCreateFlag == "1") {
                                        self.creatorConfig.show();
                                    }
                                }
                                break;
                        }
                    }
                });

                //#118006,#134616
                if (self.prefix == "delete"||self.prefix == "notify") {
                    $detailRuleTmp.find("[ detailormain_label='3']").hide();

                }
            }
            //明细表
            $table.append($detailRuleTmp);
            $table.find("[name=" + detailOrMainName + "]").each(function (idx, dom) {
                var checked = $(dom).prop("checked");
                if (checked) {
                    $(dom).trigger($.Event("click"))
                }
            });
        },
        //画明细表
        buildDetail: function ($table, detailTmpStr, detailId) {
            var self = this;
            //不重复绘制
            for (var i = 0; i < self.detailCollection.length; i++) {
                var dc = self.detailCollection[i];
                if (dc.detailId == detailId) {
                    return;
                }
            }
            var detailItem = new detailRecordGenerator.DetailRecordGenerator({
                parent: self,
                detailId: detailId,
                detailName: self.targetDetail[detailId],
                sourceData: self.sourceData,
                viewType: self.detailViewType,
            });
            detailItem.draw($table, detailTmpStr);
        },
        removeDetail: function (detailId) {
            for (var i = 0; i < this.detailCollection.length; i++) {
                var dc = this.detailCollection[i];
                if (dc.detailId == detailId) {
                    dc.destroy();
                }
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
        // 画目标字段
        buildTarget: function ($table, targetTmpStr, authData) {
            var self = this;
            $table.append(targetTmpStr);
            var $targetTable = $table.find(".view_field_target_table");
            var targetData = self.getTargetData();
            if (targetData && targetData.data) {
                self.drawTargetData(targetData.data, $targetTable, authData);
            }
        },

        doRenderWhenDataLoad: function (targetData) {
            //this.drawTargetData(targetData.data);
            this.clear();
            this.init();
        },
        drawTargetData: function (data, container, authData) {
            var self = this;
            if (!container) {
                container = this.getTargetContainer();
            }

            for (var controlId in data) {
                //#122770 过滤创建时间
                if (controlId=="docCreateTime" )
                    continue;
                if (controlId=="fdProcessEndTime" )
                    continue;
                //过滤明细表
                if (controlId.indexOf(".") > 0)
                    continue;
                var targetWgt = new targetRecordGenerator.TargetRecordGenerator({parent: self,sourceData:self.sourceData});
                container.append(targetWgt.draw(data[controlId]));
            }
            if (authData) {
                for (var controlId in authData) {
                    var targetWgt = new targetRecordGenerator.TargetRecordGenerator({parent: self,sourceData:self.sourceData});
                    container.append(targetWgt.draw(authData[controlId]));
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

        getTargetContainer: function () {
            return this.element.find(".view_field_target_table");
        },
        getWhereContainer: function () {
            return this.element.find(".view_field_where_table");
        },
        getDetailQueryContainer: function () {
            return this.element.closest("#relationEditContainer").find(".view_field_detail_query_table");
        },
        getSqlWhereContainer: function () {
            return this.element.find(".view_field_where_sql_table");
        },
        getPreWhereContainer: function () {
            return this.element.closest("#relationEditContainer").find(".view_field_pre_where_table");
        },

        getPreQueryContainer: function () {
            return this.element.find(".view_field_pre_query_table");
        },

        getTargetData: function () {
            return this.parent.targetData;
        },

        getPreModelData: function () {
            return this.parent.preModelData;
        },
        
        // type : where|target
        addWgt: function (wgt, type) {
            if (type === "where") {
                this.whereWgtCollection.push(wgt);
            } else if (type === "target") {
                this.targetWgtCollection.push(wgt);
            } else if (type === "detail") {
                this.detailCollection.push(wgt)
            }else if(type === "preWhere"){
            	this.parent.preWhereWgtCollection.push(wgt);
            }else if(type === "sqlWhere"){
                this.parent.sqlWhereWgtCollection.push(wgt);
            }else if(type === "preQuery"){
                this.preQueryCollection.push(wgt);
            }else if(type === "detailQuery"){
                this.parent.detailQueryCollection.push(wgt);
            }
        },

        // type : where|target|detail
        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "where") {
                collect = this.whereWgtCollection;
            } else if (type === "target") {
                collect = this.targetWgtCollection;
            } else if (type === "detail") {
                collect = this.detailCollection;
            }else if(type === "preWhere"){
            	 collect = this.parent.preWhereWgtCollection;
            }else if(type === "sqlWhere"){
                collect = this.parent.sqlWhereWgtCollection;
            }else if(type === "preQuery"){
                collect = this.preQueryCollection;
            }else if(type === "detailQuery"){
                collect = this.parent.detailQueryCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },

        clear: function () {
            for (var i = 0; i < this.whereWgtCollection.length; i++) {
                this.whereWgtCollection[i].destroy();
                i--;
            }
            for (var i = 0; i < this.targetWgtCollection.length; i++) {
                this.targetWgtCollection[i].destroy();
                i--;	//#130887 每次修改目标表单没有将原目标表单字段清除
            }
            for (var i = 0; i < this.detailCollection.length; i++) {
                this.detailCollection[i].destroy();
                i--;
            }
            for (var i = 0; i < this.preQueryCollection.length; i++) {
                this.preQueryCollection[i].destroy();
                i--;
            }
            this.element.remove();
        },

        show: function ($super){
            $super();
            if(!this.sqlWhereType){
                this.sqlWhereType = '0';
            }
            var radio = this.element.find("input[name='fdSqlWhereType']");
            for (var i=0; i<radio.length; i++) {
                if (this.sqlWhereType == radio[i].value) {
                    radio[i].checked = true;
                }
            }
            this.element.closest("#relationEditContainer").find(".WhereCodeDiv input[type='radio']").filter(':checked').trigger($.Event("change"));
        },

        getKeyData: function () {
            var keyData = {};
            keyData.where = [];
            keyData.sqlWhereParams = [];
            keyData.preWhere = [];
            keyData.target = [];
            keyData.detail = [];
            keyData.preSqlWhere = "";
            keyData.detailRule = this.detailRule;
            keyData.preQuery = [];
            keyData.detailQuery = [];
            var WhereTypeChecked = this.element.find(".WhereTypediv :checked").val();
            var WhereSqlTypeChecked = this.element.find(".WhereCodeDiv :checked").val();
            this.element.find(".WhereTypediv input[type='radio']").attr("name", "fdWhereType");
            if (WhereTypeChecked != "2" && WhereTypeChecked != "3" && WhereSqlTypeChecked != "1") {
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
            }else if(WhereSqlTypeChecked == "1"){
                for (var i = 0; i < this.parent.sqlWhereWgtCollection.length; i++) {
                    var whereWgt = this.parent.sqlWhereWgtCollection[i];
                    var whereWgtKeyData = whereWgt.getKeyData();
                    if (!whereWgtKeyData) {
                        continue;
                    }
                    // 索引，用来进来记录排序，暂无用
                    whereWgtKeyData.idx = i;
                    keyData.sqlWhereParams.push(whereWgtKeyData);
                }
                var sqlwhere = this.element.find("#sqlWhereArea").val();
                keyData.sqlWhere = sqlwhere || "";
            }
            keyData.sqlWhereType = WhereSqlTypeChecked || "0";

            var preWhereTypeChecked = this.element.closest("#relationEditContainer").find(".preWhereTypediv :checked").val();
            if (preWhereTypeChecked != "2" && preWhereTypeChecked != "3") {
                for (var i = 0; i < this.parent.preWhereWgtCollection.length; i++) {
                    var preWhereWgt = this.parent.preWhereWgtCollection[i];
                    var preWhereWgtKeyData = preWhereWgt.getKeyData();
                    if (!preWhereWgtKeyData) {
                        continue;
                    }
                    // 索引，用来进来记录排序，暂无用
                    preWhereWgtKeyData.idx = i;
                    keyData.preWhere.push(preWhereWgtKeyData);
                }
            }

            keyData.preWhereType = preWhereTypeChecked || "0";
            for (var i = 0; i < this.targetWgtCollection.length; i++) {
                var targetWgt = this.targetWgtCollection[i];
                var targetWgtKeyData = targetWgt.getKeyData();
                // 索引，用来进来记录排序，暂无用
                targetWgtKeyData.idx = i;
                keyData.target.push(targetWgtKeyData);
            }
            if (this.CONST.DETAIL_RULE_MAIN!= keyData.detailRule) {
                //有明细表才补充
                for (var i = 0; i < this.detailCollection.length; i++) {
                    var detailWgt = this.detailCollection[i];
                    var detailWgtKeyData = detailWgt.getKeyData();
                    // 索引，用来进来记录排序，暂无用
                    detailWgtKeyData.idx = i;
                    keyData.detail.push(detailWgtKeyData);
                }
            }
            var preQueryModelIds = "";
            if(this.preQueryCollection && this.preQueryCollection.length > 0){
                for (var i = 0; i < this.preQueryCollection.length; i++) {
                    var preQueryWgt = this.preQueryCollection[i];
                    var preQueryWgtKeyData = preQueryWgt.getKeyData();
                    // 索引，用来进来记录排序，暂无用
                    preQueryWgtKeyData.idx = i;
                    keyData.preQuery.push(preQueryWgtKeyData);
                    if(preQueryWgtKeyData.target && preQueryWgtKeyData.target.value){
                        preQueryModelIds += preQueryWgtKeyData.target.value + ",";
                    }
                }
                if(preQueryModelIds){
                    preQueryModelIds = preQueryModelIds.substring(0,preQueryModelIds.length - 1);
                }
            }
            //记录前置查询所有目标表单id
            $("[name=fdPreQueryModelIds]").val(preQueryModelIds);
            if(this.prefix !== "defined"){
                for (var i = 0; i < this.parent.detailQueryCollection.length; i++) {
                    var detailQueryWgt = this.parent.detailQueryCollection[i];
                    var detailQueryWgtKeyData = detailQueryWgt.getKeyData();
                    if (!detailQueryWgtKeyData || !detailQueryWgtKeyData.target) {
                        continue;
                    }
                    // 索引，用来进来记录排序，暂无用
                    detailQueryWgtKeyData.idx = i;
                    keyData.detailQuery.push(detailQueryWgtKeyData);
                }
            }
            return keyData;
        },
        validators:function() {
            var validate = true;
            if(this.preQueryCollection && this.preQueryCollection.length > 0){
                for (var i = 0; i < this.preQueryCollection.length; i++) {
                    var preQueryWgt = this.preQueryCollection[i];
                    var result = preQueryWgt.validators();
                    if (!result){
                        validate = false;
                    }
                }
            }
            for (var i = 0; i < this.targetWgtCollection.length; i++) {
                var targetWgt = this.targetWgtCollection[i];
                var result = targetWgt.validators();
                if (!result){
                    validate = false;
                }
            }
            if (this.CONST.DETAIL_RULE_MAIN!= this.detailRule) {
                //有明细表才补充
                for (var i = 0; i < this.detailCollection.length; i++) {
                    var detailWgt = this.detailCollection[i];
                    var result = detailWgt.validators();
                    if (!result){
                        validate = false;
                    }
                }
            }
            return validate;
        }
    });

    exports.CrudBaseView = CrudBaseView;
});