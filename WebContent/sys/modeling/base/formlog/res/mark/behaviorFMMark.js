/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var FieldMappingMark = require("sys/modeling/base/formlog/res/mark/fieldMappingMark");

    var BehaviorFMMark = FieldMappingMark.FieldMappingMark.extend({
        localMapping: {
            target: "render_target",
            where: "render_where",
            creator: "render_creator",
            message: "render_message",
            messageTarget: "render_messageTarget",
            detail: "render_detail",
            detail_target: "render_detail_target",
            detail_where: "render_detail_where",
            detail_query: "render_detail_query"
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            if (cfg.fdId) {
                this.getNeedModifiedDataUrl = this.baseFMMarkUrl + "&fdComClass=com.landray.kmss.sys.modeling.base.model.SysModelingBehavior&fdComId=" + cfg.fdId;
            } else {
                console.error("BehaviorFMMark no fdId")
            }
            //初始化1
        },

        render_target: function (fieldList, ele) {
            console.debug("render_target", fieldList);
            if (!ele) {
                ele = $(".main_target");
            }
            this.__renderTitle(ele.find(".td_normal_title"));
            var $table = ele.find(".model-mask-panel-table-base >table");
            var self = this;
            $table.find("tr").each(function (idx, tr) {
                var $tds = $(tr).find("td");
                if ($tds.length > 0) {
                    self.__renderFormulaOfTr(fieldList, $(tr));
                    var td = $tds[0];
                    var tdFieldId = $(td).attr("target-filed-td-id");
                    for (var fieldId in fieldList) {
                        if (tdFieldId === fieldId) {
                            $(td).css(self.markCss.titleRed);
                        }
                    }
                }
            });
        },
        render_where: function (fieldList, ele) {
            console.debug("render_where", fieldList);
            if (!ele) {
                ele = $(".main_where");
            }
            this.__renderTitle(ele.find(".td_normal_title"));
            var $table = ele.find(".model-mask-panel-table-base >table");
            this.__renderTableSelect($table, "where_field", fieldList);
          var self = this;
            $table.find("tr").each(function (idx, tr) {
                self.__renderFormulaOfTr(fieldList, $(tr));
            })
        },
        render_creator: function (fieldList) {
            console.debug("render_creator", fieldList);
            var ele = $(".main_create");
            this.__renderTitle(ele.find(".td_normal_title"));
            this.__renderFormulaOfTr(fieldList,ele,"view_no_flow_creator");
            this.__renderFormulaOfTr(fieldList,ele,"view_flow_creator");
        },
        render_message: function (fieldList) {
            console.debug("render_message", fieldList);
            var ele = $(".view_notify_msg");
            this.__renderTitle(ele.find(".td_normal_title"));
        },
        render_messageTarget: function (fieldList) {
            console.debug("render_messageTarget", fieldList);
            var ele = $(".view_notify_target_org");
            this.__renderTitle(ele.find(".td_normal_title"));
        },
        render_detail: function (fieldList) {
            console.debug("render_detail", fieldList);
            var ele = $(".detailChecked_Tr");
            this.__renderTitle(ele.find(".td_normal_title"));
            this.__renderRadio("detailChecked", fieldList);
        },
        render_detail_target: function (fieldList) {
            console.debug("render_detail_target", fieldList);
            var tr = $(".detail_target");
            var trs = [];
            if (tr) {
                if (tr.length == 1) {
                    this.render_target(fieldList,tr)
                } else {
                    trs = tr;
                }
            }
            for (var i = 0; i < trs.length; i++) {
                this.render_target(fieldList,$(trs[i]))
            }
        },
        render_detail_where: function (fieldList) {
            console.debug("render_detail_where", fieldList);
            var tr = $(".detail_where");
            var trs = [];
            if (tr) {
                if (tr.length == 1) {
                    this.render_where(fieldList, tr)
                } else {
                    trs = tr;
                }
            }
            for (var i = 0; i < trs.length; i++) {
                this.render_where(fieldList, $(trs[i]))
            }
        },
        render_detail_query: function (fieldList) {
            console.debug("render_detail_query", fieldList);
            var tr = $(".detail_query_where");
            var trs = [];
            if (tr) {
                if (tr.length == 1) {
                    this.render_where(fieldList, tr)
                } else {
                    trs = tr;
                }
            }
            for (var i = 0; i < trs.length; i++) {
                this.render_where(fieldList, $(trs[i]))
            }
        }
    });
    exports.BehaviorFMMark = BehaviorFMMark;
});
