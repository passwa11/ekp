/**
 * 查询条件生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var validatorGenerator = require("sys/modeling/base/relation/trigger/behavior/js/validatorGenerator")

    var DetailQueryRecordGenerator = base.Component.extend({
        operationType:{
            "0":modelingLang["relation.meet.all.conditions"],
            "1":modelingLang["relation.meet.any.conditions"],
            "3":modelingLang["behavior.no.query.conditions"]
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.currentData = cfg.currentData || {};
            this.sourceData = cfg.sourceData;
            this.xformId = cfg.parent.xformId || "";
            this.appId = cfg.parent.appId || "";
            this.parent = cfg.parent;
        },

        draw: function (container) {
            var self = this;
            self.element = $("<tr class='view_field_tr'></tr>");
            // 字段td
            self.$fieldTd = $("<td ></td>");
            var $fieldInput = $("<div class=\"pre_query_content\">\n" +
                "                        <div onclick=\"\"\n" +
                "                             class=\"model-mask-panel-table-show\">\n" +
                "                            <p class=\"detailQueryTargetNameBox\"></p>\n" +
                "                        </div>\n" +
                "                    </div>");
                // "<span class=\"txtstrong\">*</span>");

            self.$fieldTd.append($fieldInput);

            $fieldInput.on("click",function (){
                self.setTargetModel();
            })

            // 条件td
            self.$valueTd = $("<td></td>");

            //查询条件
            self.$queryWhereTd = $("<td></td>");

            // 操作td
            var $delTd = $("<td></td>");
            //设置
            var $setSpan = $("<span class='table_opera'>设置</span>");
            $setSpan.on("click", function () {
                self.setTargetModel();
            });
            $delTd.append($setSpan);
            //删除
            var $delSpan = $("<span class='table_opera'>"+modelingLang['modeling.page.delete']+"</span>");
            $delSpan.on("click", function () {
                self.destroy();
            });
            $delTd.append($delSpan);

            self.element.append(self.$fieldTd);
            self.element.append(self.$valueTd);
            self.element.append(self.$queryWhereTd);
            self.element.append($delTd);
            container.append(self.element);
            self.parent.addWgt(this, "detailQuery");
        },

        setTargetModel:function () {
            var self = this;
            var detailIds = self.buildExistDetailIds();
            var behaviorId = $("[name=fdId]").val();
            dialog.iframe("/sys/modeling/base/relation/trigger/behavior/behavior_detailQuery_dialog.jsp?appId="+this.appId, "明细表查询条件",
                function (value) {
                    if (value) {
                        self.formatBackValue(value);
                    }
                }, {
                    width: 1010,
                    height: 600,
                    params : {
                        data : self.currentData,
                        xformId:self.xformId,
                        sData : self.sourceData,
                        detailIds : detailIds,
                        detailId:self.detailId,
                        behaviorId:behaviorId
                    }
                },);
        },

        //构建已经存在的明细表Id
        buildExistDetailIds:function (){
            var detailQueryCollection = this.parent.parent.detailQueryCollection;
            var detailIds = [];
            if(detailQueryCollection){
                for (var i = 0; i < detailQueryCollection.length; i++) {
                    var detailQueryWgt = detailQueryCollection[i];
                    var detailQueryWgtKeyData = detailQueryWgt.getKeyData();
                    if (!detailQueryWgtKeyData || !detailQueryWgtKeyData.target) {
                        continue;
                    }
                    detailIds.push(detailQueryWgtKeyData.target.value);
                }
            }
            return detailIds;
        },

        //格式化回填数据
        formatBackValue : function(data) {
            this.currentData.detailQueryWhere = data.detailQueryWhere;
            this.currentData.whereType = data.whereType;
            this.currentData.target = data.target;
            this.buildBackValue();
        },
        destroy: function ($super, cfg) {
            this.parent.deleteWgt(this, "detailQuery");
            $super(cfg);
        },

        getKeyData: function () {
            var keyData = {};
            if(this.detailId){
                keyData.target = {};
                if(this.currentData.target){
                    keyData.target = this.currentData.target;
                }
                keyData.detailQueryWhere = [];
                if(this.currentData.detailQueryWhere){
                    keyData.detailQueryWhere = this.currentData.detailQueryWhere;
                }
                keyData.whereType = "";
                if(this.currentData.whereType){
                    keyData.whereType = this.currentData.whereType;
                }
            }
            return keyData;
        },

        //回写明细表查询条件
        buildBackValue: function () {
            var self = this;
            self.element.find(".detailQueryTargetNameBox").html("");
            self.$valueTd.empty();
            self.$queryWhereTd.empty();

            if(this.currentData && this.currentData.target){
                self.element.find(".detailQueryTargetNameBox").html(this.currentData.target.text);
                this.detailId = this.currentData.target.value;

                if (this.currentData.whereType) {
                    self.$valueTd.text(this.operationType[this.currentData.whereType]);
                }

                if (this.currentData.detailQueryWhere) {
                    self.buildQueryCondition(this.currentData.detailQueryWhere);
                }
            }
        },
        //构建显示查询条件
        buildQueryCondition:function(where) {
            for (var i = 0; i < where.length; i++) {
                var html = "";
                html += '<div class="displayWhereItem">';
                html += '<span class="displayWhereItem_title">' + where[i].name.text + '</span>';
                html += this.getWhereItemHtml(where[i]);
                html += '<span class="displayWhereItem_type">“' + where[i].type.text + '”</span>';
                if (JSON.stringify(where[i].expression) != "{}" && where[i].expression.text != "") {
                    html += '<span class="displayWhereItem_text">“' + decodeURI(where[i].expression.text) + '”</span>';
                }
                html += '</div>';
                this.$queryWhereTd.append(html);
            }
        },

        initByStoreData: function (storeData) {
            if(!storeData){
                return;
            }
            var self = this;
            if (storeData.hasOwnProperty("target")){
                this.currentData.target = storeData["target"];
                this.detailId = this.currentData.target.value;
                self.element.find(".detailQueryTargetNameBox").text(this.currentData.target.text);
            }
            if (storeData.hasOwnProperty("detailQueryWhere")){
                this.currentData.detailQueryWhere = storeData["detailQueryWhere"];
                self.buildQueryCondition(this.currentData.detailQueryWhere);
            }
            if (storeData.hasOwnProperty("whereType")){
                this.currentData.whereType = storeData["whereType"];
                self.$valueTd.text(this.operationType[this.currentData.whereType]);
            }
        },
        validators : function() {
            var validator = true;
            //目标表单为空校验
            if(!this.targetValidatorWgt && (!this.currentData.target || !this.currentData.target.value)){
                this.targetValidatorWgt = new validatorGenerator.ValidatorGenerator({
                    title:modelingLang["sysModelingBehavior.modelTarget"],
                    content:modelingLang["modeling.behavior.notEmpty"],
                    container:this.$fieldTd
                });
            }
            this.buildValidator(!this.currentData.target || !this.currentData.target.value,this.targetValidatorWgt);
            if(this.targetValidatorWgt && this.targetValidatorWgt.isShow()){
                validator = false;
            }

            return validator;
        },
        buildValidator: function (expr,weiget) {
            if (expr) {
                if(weiget && !weiget.isShow()){
                    weiget.show();
                }
            } else {
                if (weiget) {
                    weiget.hide();
                }
            }
        },
        //显示查询条件
        getWhereItemHtml:function (data){
            var html = "";
            if(data.match == "="){
                html += '<span class="compare">“等于”</span>';
            }else if(data.match == "like"){
                html += '<span class="compare">“包含”</span>';
            }else if(data.match == "eq"){
                html += '<span class="compare">“等于”</span>';
            }else if(data.match == "lt"){
                if (data.name.type == "DateTime"
                    || data.name.type == "Date"
                    || data.name.type == "Time"  ){
                    html += '<span class="compare">“早于”</span>';
                }else{
                    html += '<span class="compare">“小于”</span>';
                }
            }else if(data.match == "le"){
                if (data.name.type == "DateTime"
                    || data.name.type == "Date"
                    || data.name.type == "Time"  ){
                    html += '<span class="compare">“不晚于”</span>';
                }else{
                    html += '<span class="compare">“小于等于”</span>';
                }
            }else if(data.match == "gt"){
                if (data.name.type == "DateTime"
                    || data.name.type == "Date"
                    || data.name.type == "Time"  ){
                    html += '<span class="compare">“晚于”</span>';
                }else{
                    html += '<span class="compare">“大于”</span>';
                }
            }else if(data.match == "ge"){
                if (data.name.type == "DateTime"
                    || data.name.type == "Date"
                    || data.name.type == "Time"  ){
                    html += '<span class="compare">“不早于”</span>';
                }else{
                    html += '<span class="compare">“大于等于”</span>';
                }
            }else if(data.match == "!{notequal}"){
                html += '<span class="compare">“不等于”</span>';
            }else if(data.match == "!{notContain}"){
                html += '<span class="compare">“不包含”</span>';
            }
            return html;
        }
    });

    exports.DetailQueryRecordGenerator = DetailQueryRecordGenerator;
});
