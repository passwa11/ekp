/**
 * 页面全局对象
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var msgSendGenerator = require("sys/modeling/base/relation/trigger/behavior/js/msgSendGenerator");
    var modelingLang = require("lang!sys-modeling-base");
    var Scenes = base.Container.extend({
        txt: {
            "0": "消息",
            "1": "新建",	// 新建
            "2": "删除",	// 删除
            "3": "更新",	// 更新
            "5": "自定义"	// 自定义
        },
        targetData: {},
        initProps: function ($super, cfg) {
            $super(cfg);
            var loading = dialog.loading();
            this.element = cfg.viewContainer;
            this.behaviorDiv = this.element.find("[lui-mark-prop = \"behavior\"]");
            this.scenesType = cfg.scenesType;
            this.build();
            this.bindSelectBehavior();
            loading.hide();
        },
        build: function () {
            var modelMainId = $("[name='modelMainId']").val();
            var url = Com_Parameter.ContextPath +
                "sys/modeling/base/modeling.do?method=getFlowInfoByModelId&modelId=" + modelMainId;
            $.ajax({
                url: url,
                method: 'GET',
                async: false
            }).success(function (resultStr) {
                var result = JSON.parse(resultStr);
                //#121406 非流程隐藏流程类型
                if (result && result.fdEnableFlow == "false"){
                    $("input[name='fdType'][value='2']").parent().hide();
                    $("input[name='fdType'][value='2']").prop("checked","");
                }
                console.debug("加载字段::", result)
            })
        },
        startup: function ($super, cfg) {

        },
        bindSelectBehavior: function () {
            var self = this;
            var behaviorSelect = self.behaviorDiv.find(".behaviorSelect");
            behaviorSelect.on("click", function (e) {
                self.selectBehavior();
            });

        },
        selectBehavior: function () {
            var self = this;
            var fdBehaviorBox = self.behaviorDiv.find(".fdBehaviorBox");
            var selectBehaviorParams = {
                oldData: $("[name='fdBehaviorIds']").val()
            };
            var oldDataIds = $("[name='fdBehaviorIds']").val().split(";");
            var ext = {};
            $.each(oldDataIds, function (idx, id) {
                var $idEle = fdBehaviorBox.find("[msgId='" + id + "']");
                var exitem = {
                    doMsgType: $idEle.attr("dataMsgType"),
                }
                ext[id] = exitem;
            });
            selectBehaviorParams.ext = ext;
            console.log("selectBehaviorParams", ext);
            var modelMainId = $("[name='modelMainId']").val();
            var url = "/sys/modeling/base/sysModelingBehavior.do?method=behaviorSelectIframe&scenesType=" + self.scenesType + "&modelMainId=" + modelMainId;
            dialog.iframe(url, modelingLang['behavior.select.trigger.action'],
                function (r) {
                    if (r == null) {
                        return
                    }
                    fdBehaviorBox.find(".dataItem").remove();
                    fdBehaviorBox.hide();
                    for (var i = 0; i < r.ids.length; i++) {
                        var $ele = self.createNewItem(r.ids[i], r.names[i], r.ext[r.ids[i]])
                        fdBehaviorBox.append($ele);
                        fdBehaviorBox.show();
                    }
                    $("[name='fdBehaviorIds']").val(r.ids.join(";"));
                }, {
                    width: 1060,
                    height: 600,
                    params: selectBehaviorParams
                });
        },
        createNewItem: function (id, name, ext) {
            console.log("createNewItem", id, name);
            var $ele = $("<tr class='dataItem'></tr>");
            $ele.attr("msgId", id);
            $ele.attr("msgName", name);
            $ele.append("<td>" + name + "</td>");
            var typeStr = ""
            if (ext) {

                $ele.attr("dataMsgType", ext.doMsgType);
                $ele.attr("dataType", ext.type);
                if (ext.type === "0") {
                    typeStr = ext.doMsgType == "remove" ? "-消除" : "-发送";
                }
                $ele.append("<td>" + this.txt[ext.type] + typeStr + "</td>");
            } else {
                $ele.append("<td>" + "[数据缺失，请重新选择保存]" + "</td>");
            }
            return $ele;
        },
        selectScenesType: function (val, name) {
            this.scenesType = val;
            if (val == "0") {
                //定时场景下需要重新选择动作
                var fdBehaviorBox = this.behaviorDiv.find(".fdBehaviorBox");
                fdBehaviorBox.find(".dataItem").remove();
                fdBehaviorBox.hide();
                $("[name='fdBehaviorIds']").val("");
                $("[name='fdBehaviorNames']").val("")
            }


        },
        initByStoreData: function (ext) {

            var fdBehaviorBox = this.behaviorDiv.find(".fdBehaviorBox");
            fdBehaviorBox.find(".dataItem").remove();
            fdBehaviorBox.hide();
            var ids = $("[name=fdBehaviorIds]").val();
            var names = $("[name=fdBehaviorNames]").val();

            var idarr = ids.split(";");
            var nameArr = names.split(";");
            for (var i = 0; i < idarr.length; i++) {
                var id = idarr[i];
                if (id) {
                    var $ele = this.createNewItem(id, nameArr[i], ext[id])
                    fdBehaviorBox.append($ele);
                    fdBehaviorBox.show();
                }
            }
        },

        getKeyData: function () {
            var keyData = this.currentView.getKeyData();
            this.setEmptyStr4Undef(keyData);
            return keyData;
        }

    });

    exports.Scenes = Scenes;
})