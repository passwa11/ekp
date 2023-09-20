/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var Temp1 = "   <table class=\"tb-simple item-promotion-table\"  model-id='${modelId}'>\n" +
        "                            <tr>\n" +
        "                                <th class=\"td-left\"><i model-id='${modelId}'></i>${modelName}</th>\n" +
        "                                <th class=\"td-right\">共<span>${modeSize}</span>项</th>\n" +
        "                            </tr>\n" +
        "                        </table>";
    var Temp2 = "    <tr data-lui-mark='dataSelect'>\n" +
        "       <td class=\"td-left\">\n" +
        "           <i data-id='${dataId}'></i>\n" +
        "           <span>${dataName}</span>\n" +
        "       </td>\n" +
        "       <td class=\"td-right\">\n" +
        "          ${dataDesc}" +
        "       </td>\n" +
        "   </tr>";
    var SelectItem = base.Container.extend({
        initProps: function ($super, cfg) {
            console.log(cfg);
            this.step = 0;
            this.element = cfg.element;
            this.cfgParam = cfg._param;
            this.initElement();
            this.bindEvent();
            this.step = 1;
        },
        initElement: function () {
            var s = this;
            var $e = s.element;
            s.$contianer = $e.find(".toolbox-dialog-container");
            s.$status = $e.find(".toolbox-dialog-status");
        },
        bindEvent: function () {
            var self = this
            this.$status.find(".statusBtn").on("click", function () {
                if ($(this).hasClass("continue")) {
                    self.doClick()
                }
                if ($(this).hasClass("cancel")) {
                    var $e = self.element;
                    self.step = 1;
                    var $c = $("[data-lui-mark=\"step_1\"]").find(".form-item-content");
                    $c.empty();
                    $e.find("[data-lui-mark=\"step_0\"]").show();
                    $e.find("[data-lui-mark=\"step_1\"]").hide();
                }
            });
        },
        doClick: function () {
            var step = this.step;
            if (step == 0) {
                console.log("init")
                return;
            }
            var fun = "doClick_" + step;
            if (this[fun]) {
                this[fun]();
            }
        },
        doClick_1: function () {
            var self = this;
            var $e = self.element;
            var $c = self.$contianer;
            //选中检测项
            var selectItem = [];
            $c.find("input[name='selectItem']:checked").each(function () {
                selectItem.push($(this).val());

            });
            if (selectItem.length == 0) {
                dialog.alert(modelingLang["modeling.maintenance.no.selected"])
                return;
            }
            window.selectItem_load = dialog.loading();
            var param = self.cfgParam;
            var historyFix = {};
            historyFix.fixed = selectItem;
            param.historyFix = JSON.stringify(historyFix);
            this.ajaxParam = param;
            $.ajax({
                url: Com_Parameter.ContextPath + self.cfgParam.url,
                type: "POST",
                data: param,
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result && result.success) {
                        var data = result.data;
                        var ids = data ? data.ids : [];
                        var modelJson = data ? data.modelJson : {};
                        if (ids.length == 0) {
                            dialog.success(modelingLang["modeling.maintenance.no.fix.data"])
                        } else {
                            //检测成功
                            self.buildDataList(modelJson);
                            self.step = 2;
                            $e.find("[data-lui-mark=\"step_0\"]").hide();
                            $e.find("[data-lui-mark=\"step_1\"]").show();
                        }
                    } else {
                        dialog.alert("ingify(modelJson));"+modelingLang["modeling.maintenance.check.fail"])
                    }
                    selectItem_load.hide();
                }
            });
        },
        doClick_2: function () {
            window.selectItem_load = dialog.loading();
            var self = this;
            var param = self.ajaxParam;
            //应用
            var datas = $('[data-lui-mark=\'dataSelect\']');
            var dataIds = [];
            for (var i = 0; i < datas.length; i++) {
                var di = $(datas[i]).find("td i");
                if ((di.hasClass('itemSelected'))) {
                    dataIds.push(di.attr("data-id"))
                }
            }
            if (!param.historyFix){
                dialog.alert(modelingLang["modeling.maintenance.param.fail"]);
                return
            }
            var fixData =JSON.parse( param.historyFix);
            fixData.dataIds=dataIds;
            param.historyFix =JSON.stringify(fixData);
            console.log(param)
            $.ajax({
                url: Com_Parameter.ContextPath + self.cfgParam.url,
                type: "POST",
                data: param,
                dataType: "json",
                async: false,
                success: function (result) {
                    if (result && result.success) {
                        dialog.success(result.msg);
                        Com_CloseWindow();
                    } else {
                        dialog.alert(modelingLang["modeling.maintenance.check.fail"])
                    }
                    selectItem_load.hide();
                }
            });
            console.log("dataIds", param);
        },
        buildDataList: function (data) {
            var $c = $("[data-lui-mark=\"step_1\"]").find(".form-item-content");
            $c.empty();
            for (var modelId in data) {
                var arr = data[modelId];
                if (arr.length == 0) {
                    continue;
                }
                console.log("arr", data, arr);
                var t1 = Temp1.replace("${modelId}", modelId)
                    .replace("${modeSize}", arr.length)
                    .replace("${modelName}", arr[0].modelName);
                var $modelTable = $(t1);
                for (var i = 0; i < arr.length; i++) {
                    var item = arr[i];
                    var desc = modelingLang["modeling.maintenance.cur.creator"]+"：" + item.creatorName + "  "+modelingLang["modeling.maintenance.act.creator"]+"：" + item.handlerName;
                    var t2 = Temp2.replace("${dataId}", item.fdId)
                        .replace("${dataName}", item.docSubject)
                        .replace("${dataDesc}", desc);
                    $(t2).appendTo($modelTable);
                }
                $c.append($modelTable);
                $("<div style='width: 100%;height: 15px;'>").appendTo($c)
            }
            this.bindDataListEvent();
        },
        bindDataListEvent: function () {
            var $c = $("[data-lui-mark=\"step_1\"]").find(".form-item-content");
            var self = this;
            //选择模块
            $c.find('.item-promotion-table th i').on('click', function () {
                if ($(this).hasClass('itemSelected')) {
                    $(this).removeClass('itemSelected');
                    $(this).parent().parent().parent().find("tr td.td-left i").removeClass('itemSelected');
                } else {
                    $(this).addClass('itemSelected');
                    $(this).parent().parent().parent().find("tr td.td-left i").addClass("itemSelected");
                }
                self.checkAll();
            });
            $c.find('.item-promotion-table tr td.td-left i').on('click', function () {
                if ($(this).hasClass('itemSelected')) {
                    $(this).removeClass('itemSelected');
                } else {
                    $(this).addClass('itemSelected');
                }
                self.checkAll();
            })
            $(".toolbox-dialog-status i.selectAll").on('click', function () {
                if ($(this).hasClass('itemSelected')) {
                    $(this).removeClass('itemSelected');
                    $c.find('.item-promotion-table i').removeClass('itemSelected');
                } else {
                    $(this).addClass('itemSelected');
                    $c.find('.item-promotion-table i').addClass('itemSelected');
                }
                self.checkAll();
            })
        },
        //判断表单是否全选
        checkAll: function () {
            var modelsTable = $('.item-promotion-table');
            var checkModelCount = 0;
            for (var i = 0; i < modelsTable.length; i++) {
                var $model = $(modelsTable[i])
                var checkItem = $model.find('tr td.td-left i');
                var itemCount = 0;
                for (var j = 0; j < checkItem.length; j++) {
                    var $item = $(checkItem[j])
                    if (!($item.hasClass('itemSelected'))) {
                        //取消表单全选
                        $model.find("th i").removeClass('itemSelected');
                    } else {
                        itemCount++;
                    }
                }
                if (itemCount != 0 && itemCount == checkItem.length) {
                    //表单全选
                    checkModelCount++;
                    $model.find("th i").addClass('itemSelected');
                }
            }
            if (checkModelCount != 0 && checkModelCount == modelsTable.length) {
                $(".toolbox-dialog-status i.selectAll").addClass('itemSelected');
            } else {
                $(".toolbox-dialog-status i.selectAll").removeClass('itemSelected');
            }
        },

    })

    exports.SelectItem = SelectItem;
});
