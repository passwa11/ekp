/**
 * 更新视图
 */
define(function (require, exports, module) {

    require("resource/js/treeview.js");
    var $ = require("lui/jquery");
    var crudBaseView = require("sys/modeling/base/relation/trigger/behavior/js/view/crudBaseView");
    var msgSendGenerator = require("sys/modeling/base/relation/trigger/behavior/js/msgSendGenerator");
    var creatorGenerator = require("sys/modeling/base/relation/trigger/behavior/js/creatorGenerator");
    var preModelGenerator = require("sys/modeling/base/relation/trigger/behavior/js/preModelGenerator");

    var modelingLang = require("lang!sys-modeling-base");
    var CreateView = crudBaseView.CrudBaseView.extend({

        build: function () {
            this.prefix = "create";
            if($("[mdlng-bhvr-data='precfg']").find(".behavior_update_preview").length != 1){
        		this.buildPreWhereView();
        	}
            if($("[mdlng-bhvr-data='precfg']").find(".behavior_detail_query_preview").length != 1){
                this.buildDetailQueryView();
            }
            var $element = $("<div class='behavior_create_view'/>");
            var $table = $("<table class='tb_simple modeling_form_table view_table' width='100%'/>");
            //明细表
            this.buildDetailRule($table, this.parent.detailRuleTmpStr, this.parent.detailTmpStr);
            //消息
            this.buildMsg($table, "send");
            //目标表单名称
            this.modelMainName = this.parent.modelMainName;
            // 设置流程
            this.creatorConfig = new creatorGenerator.CreatorGenerator({$table: $table, parent: this});
            // 前置表单
            this.preModelConfig = new preModelGenerator.PreModelGenerator({$table: $table, parent: this});
            //查询
            this.buildWhere($table, this.parent.whereTmpStr);
            //查询所有数据隐藏
            $table.find(".WhereTypeinput3").hide();
            //查询条件切换事件
            $table.find(".WhereTypediv input[type='radio']").on("change", function () {
                var $whereBlockDom = $table.find(".view_field_where_div");
                if (this.value === "3") {
                	$(".mainModelWhereTip").remove();
                    $whereBlockDom.hide();
                } else {
                    $whereBlockDom.show();
                }
            });
            var whereType = $table.find(".WhereTypediv input[type='radio']:checked").val();
            if(whereType === "2"){
                $table.find(".WhereTypediv input[type='radio'][value='0']").prop("checked","checked");
            }
            $table.find(".WhereTypediv input[type='radio']").filter(':checked').trigger($.Event("change"));
            $table.find(".view_fdId_where_table").remove();

            //前置查询
            this.buildPreQuery($table,this.parent.preQueryTmpStr);
            //目标
            var authProperty = this.parent.targetData ? this.parent.targetData.authProperty : null;
            this.buildTarget($table, this.parent.targetTmpStr, authProperty);
            $element.append($table);
            this.config.viewContainer.append($element);
            return $element;
        },

        buildMsg: function ($table, sendOrRemove) {
            var self = this;
            var targetData = self.getTargetData();

            var msgData = {
                "modelId": targetData.modelId,
                "modelMainId": self.parent.config.modeMainId,
                "sendOrRemove": sendOrRemove,
                "dataList": [],
            };
            //发送消息
            this.sendMsg = new msgSendGenerator.MsgSendGenerator(msgData);
            var $sendMsgTr = $("<tr/>");
            $sendMsgTr.append("<td class='td_normal_title'>"+modelingLang['behavior.message.processing']+"</td>");
            var $msgDiv = $("<div class=\"model-mask-panel-table-base\" />");
            $msgDiv.append(this.sendMsg.element);
            var $sendMsgTd = $("<td/>");
            $sendMsgTd.append($msgDiv);
            $sendMsgTr.append($sendMsgTd);
            $table.append($sendMsgTr);

        },

        show: function ($super) {
            $super();
            var detailRule = this.detailRule;
            if (!detailRule || detailRule != "2") {
                this.creatorConfig.show();
            }
        },

        hide: function ($super) {
            $super();
            this.creatorConfig.hide();
        },
        getKeyData: function ($super, cfg) {
            var keyData = $super(cfg);
            var c = this.creatorConfig.getKeyData();
            if (c) {
                if (c.flow) {
                    keyData.flow = c.flow;
                }
                if (c.noflow) {
                    keyData.noflow = c.noflow;
                }
                keyData.behaviorRule = c.behaviorRule;
            }

            //设置消息
            keyData.msgArray = this.sendMsg.getKeyData();

            //设置前置表单
            if(this.preModelConfig){
                keyData.preModel = this.preModelConfig.getKeyData();
            }

            return keyData;
        },

        initByStoreData: function ($super, storeData) {
            $super(storeData);
            // 设置流程
            this.creatorConfig.initByStoreData(storeData);
            //设置消息
            var msg = storeData.msgArray;
            this.sendMsg.initByStoreData(msg);
            //设置前置表单
            if(this.preModelConfig){
                this.preModelConfig.initByStoreData(storeData);
            }
        }
    });

    exports.CreateView = CreateView;
});