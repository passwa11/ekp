/**
 * 数据来源生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");

    var detailRecordGenerator = require("sys/modeling/base/relation/res/js/detailRecordGenerator");

    var SourceRecordGenerator = base.Component.extend({
        initProps: function ($super, cfg) {
            $super(cfg);
            this.feildName = cfg.fieldName;
            this.pcfg = cfg.pcfg;
            this.detailCollection = [];
            this.sourceData = cfg.pcfg.widgets.passive;
            this.parent = cfg.parent;
            this.build();
        },
        build: function () {
            var self = this;
            var $pele = self.pcfg.container;
            var $ele = $pele.find("[mdlng-rltn-property=\"" + self.feildName + "\"]");
            this.detailEle = $pele.find("[mdlng-rltn-property=\"fdTargetDetail\"]");

            self.buildDetailRule($pele,$ele);
            self.element = $ele;

        },
        draw: function () {
        },
        destroy: function ($super, cfg) {

        },
        //明细表规则
        buildDetailRule: function ($table, detailRuleTmpStr) {
            var self = this;
            //判断是否需要明细表
            var targetDetail = {};
            var targetData = self.getTargetData();
            if (targetData && targetData.passive) {
                targetDetail = self.filterDetailNameAndId(targetData.passive);
            }
            var $detailRuleTmp = $(detailRuleTmpStr);
            var detailOrMainName = "fdSourceType";
            //一定要目标存在明细表 目标若不存在明细表则返回,本身可以没有明细表
            if (!targetDetail) {
                return;
            } else {
                this.targetDetail = targetDetail;
                var index = 0;
                for (var key in targetDetail) {
                    var $checked = $(" <label style=\"margin-right: 18px;display: inline-block;height:20px;line-height:20px;\"></label>");
                    var checked ="";
                    if(index == 0){
                        checked = "checked";
                    }
                    $("<input  value='" + key + "' " +
                        " style=\"display: inline-block;vertical-align: middle;\" " +
                        "type='radio' " +
                        (checked?"checked='" + checked + "'":"")+
                        "name=\"detailChecked\" " +
                        " subject =\"选择明细表\">").appendTo($checked);
                    $("<span>" + targetDetail[key] + "</span>").appendTo($checked);
                    $checked.appendTo(self.detailEle.find("#detailChecked"));
                    index++;
                }
                //明细表切换事件监听
                self.detailEle.find("[name=detailChecked]").on("click", function (e) {
                    e.stopPropagation();
                    var checked = $(this).prop("checked");
                    if (checked) {
                        var checkedDetailId = $(this).val();
                        self.buildDetail($table, checkedDetailId);
                        self.resetFieldValue(checkedDetailId);
                        self.detailId = checkedDetailId;
                    }
                    //循环所有明细表，删除非勾选的明细表查询条件
                    self.detailEle.find("[name=detailChecked]").each(function (idx, dom) {
                        var checked = $(dom).prop("checked");
                        var detailId = $(dom).val();
                        if (!checked) {
                            self.removeDetail(detailId);
                        }
                    });
                });
                //数据来源切换事件
                $detailRuleTmp.find("[name=" + detailOrMainName + "]").on("click", function (e) {
                    e.stopPropagation();
                    var checked = $(this).prop("checked");
                    if (checked) {
                        var val = $(this).val();
                        self.detailRule = val;
                        if(val === "0"){
                            // 仅主表
                            $table.find("[mdlng-rltn-property=\"fdTargetDetail\"]").hide();
                            $table.find("[mdlng-rltn-property=\"fdDetailWhere\"]").hide();
                            self.removeDetail(self.detailId);
                        }else if (val === "1") {
                            // 仅明细
                            $table.find("[mdlng-rltn-property=\"fdTargetDetail\"]").show();
                            $table.find("[name=detailChecked]").each(function (idx, dom) {
                                var checked = $(dom).prop("checked");
                                if (checked) {
                                    $(dom).trigger($.Event("click"))
                                }
                            });
                        }
                    }
                });
            }
            $table.find("[name=" + detailOrMainName + "]").each(function (idx, dom) {
                var checked = $(dom).prop("checked");
                if (checked) {
                    $(dom).trigger($.Event("click"))
                }
            });
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
        //切换明细表，重置字段值
        resetFieldValue:function (detailId){
            //切换明细表，清空返回值、传出参数、显示项值
            if(this.detailId && this.detailId!=detailId){
                //重置返回值
                this.parent.element.find("[mdlng-rltn-data=\"fdReturn\"]").val("[]");
                this.parent._fdReturn();
                //重置显示项
                this.parent.element.find("[mdlng-rltn-data=\"fdOutSelect\"]").val("[]");
                this.parent._fdOutSelect();
                //重置传出参数
                this.parent.element.find("[mdlng-rltn-prprty-value=\"fdOutParam\"]").find(".model-mask-panel-table-opt p").each(function(idx,dom){
                    $(dom).trigger($.Event("click"))
                });
            }
        },
        //画明细表
        buildDetail: function ($table, detailId) {
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
                pcfg:self.pcfg
            });
            detailItem.draw($table);
            $table.find("[mdlng-rltn-property=\"fdDetailWhere\"]").show();
        },
        removeDetail: function (detailId) {
            for (var i = 0; i < this.detailCollection.length; i++) {
                var dc = this.detailCollection[i];
                if (dc.detailId == detailId) {
                    this.detailCollection.splice(i, 1);
                    dc.destroy();
                    break;
                }
            }
        },
        addWgt: function (wgt, type) {
            if (type === "detail") {
                this.detailCollection.push(wgt);
            }
        },
        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "detail") {
                collect = this.detailCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },

        hideElement: function () {
            for (var i = 0; i < this.detailCollection.length; i++) {
                var dc = this.detailCollection[i];
                dc.hideElement();
            }
            this.detailEle.hide();
            this.element.hide();
        },
        showElement: function () {
            if(this.detailRule === "1"){
                for (var i = 0; i < this.detailCollection.length; i++) {
                    var dc = this.detailCollection[i];
                    dc.showElement();
                }
                this.detailEle.show();
            }
            this.element.show();
        },
        getTargetData: function () {
            return this.pcfg.widgets;
        },
        getKeyData: function () {
            var self = this;
            var keyData = [];
            keyData.detail = [];
            //有明细表才补充
            if ("1" == this.detailRule) {
                if(self.detailId){
                    self.pcfg.container.find("[mdlng-rltn-data=\"fdTargetDetail\"]").val(self.detailId);
                }
                for (var i = 0; i < this.detailCollection.length; i++) {
                    var detailWgt = this.detailCollection[i];
                    var detailWgtKeyData = detailWgt.getKeyData();
                    // 索引，用来进来记录排序，暂无用
                    detailWgtKeyData.idx = i;
                    keyData.detail.push(detailWgtKeyData);
                }
            }else{
                self.pcfg.container.find("[mdlng-rltn-data=\"fdTargetDetail\"]").val("");
            }
            return keyData;
        },

        initByStoreData: function (storeData) {
            if (!storeData || storeData === "0") {
                //数据来源为主表则直接返回
                return;
            }
            var self = this;
            var vs = self.pcfg.container.find(".detailWhereTemp [mdlng-rltn-data=\"fdDetailWhereTemp\"]").val();
            if (vs && vs.length > 0) {
                var value = JSON.parse(vs);
                for (var i = 0; i < value.length; i++) {
                    var data = value[i];
                    self.pcfg.container.find("input[name=fdTargetDetail]").val(data.id);
                    //明细表切换事件监听
                    self.pcfg.container.find("[name=detailChecked]").each(function (idx, dom) {
                        var detailId = $(dom).val();
                        if (data.id === detailId) {
                            self.detailId = detailId;
                            $(dom).prop("checked",true);
                            $(dom).trigger($.Event("click"))
                        }else{
                            $(dom).prop("checked",false);
                        }
                    });
                    //不重复绘制
                    for (var i = 0; i < self.detailCollection.length; i++) {
                        var dc = self.detailCollection[i];
                        if (dc.detailId == data.id) {
                            dc.initByStoreData(data);
                            return;
                        }
                    }
                }
            }

        }
    });

    exports.SourceRecordGenerator = SourceRecordGenerator;
});
