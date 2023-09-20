/**
 *
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var lang = require("lang!sys-modeling-base");
    var viewDom = "<div mdlng-prtn-prprty-value=\"fdView_model\" mdlng-prtn-prprty-type=\"dialog\" class=\"model-mask-panel-table-show\"><p>"+lang['modeling.page.choose']+"</p></div>"
    var CfgLink = base.Container.extend({
        /*
        container;
        modelId:
        viewType:
        title,
         */
        initProps: function ($super, cfg) {
            this.cfg = cfg;
            this.randomId = parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.build();
            this.bindEvent();
            this.initByStoreData(cfg);
        },
        //视图结构生成
        build: function () {
            var self = this;
            var cfg = self.cfg;
            var $tr = $("<tr/>");
            $tr.attr("id", self.randomId)
            $tr.addClass(cfg.trClass);
            $tr.append("<td class=\"td_normal_title\" width=\"15%\">" + cfg.title + "</td>");
            $tr.append(viewDom);
            self.$ele = $tr;
            $tr.append(this.viewIncEle);
            cfg.mainContainer.append($tr)
        },
        bindEvent: function () {
            var self = this;
            var $ele = self.$ele;
            $ele.find("[mdlng-prtn-prprty-type=\"dialog\"]").each(function (idx, dom) {
                $(dom).on("click", function () {
                    var $e = $(this);
                    self.selectView($e);
                })
            });
        },
        selectView: function ($e) {
            var url = this.cfg.viewType === "0" ?
                "/sys/modeling/base/relation/import/model_listview_select.jsp?method=none&fdModelId="
                : "/sys/modeling/base/relation/import/model_view_select.jsp?method=none&fdModelId=";
            dialog.iframe(url + this.cfg.modelId+"&fdDevice="+this.cfg.fdDevice, lang['relation.select.view'], function (value) {
                if (value == null)
                    return;
                var $p = $("<p modeling-mark-data='" + value.fdId + "'></p>");
                $p.append(value.fdName);
                $e.html($p);
            }, {
                width: 1010,
                height: 600
            });
        },
        initByStoreData: function (storeData) {
            console.debug("初始化视图选择 ::", storeData);
            if (storeData.data) {
                var viewSet = storeData.data.viewSet;
                if (viewSet) {
                    if (!viewSet.id||viewSet.id ==="undefined"){
                        return
                    }
                    var $name = this.$ele.find("[mdlng-prtn-prprty-type=\"dialog\"]")
                    var $p = $("<p modeling-mark-data='" + viewSet.id + "'></p>");
                    $p.append(viewSet.name);
                    $name.html($p)
                }
            }

        },
        getKeyData: function () {
            var self = this;
            var type = self.cfg.viewType;
            var $name = this.$ele.find("[mdlng-prtn-prprty-type=\"dialog\"]")
            var viewSet = {
                "id": $name.find("p").attr("modeling-mark-data"),
                "name": $name.find("p").text(),
                "type": type,
                "def": "0"
            };
            if (viewSet.id === "_def") {
                viewSet.def = "1";
            }

            if (!viewSet.id) {
                return {};
            }
            var data = {
                "viewModelId": self.cfg.modelId,
                "viewSet": viewSet
            };
            return data;

        }

    });

    exports.CfgLink = CfgLink;

})