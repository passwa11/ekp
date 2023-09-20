/**
 * 目标行生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var tempHtml = " " +
        "<div >\n" +
        "    <span class=\"highLight\" style='margin-bottom: 10px'>"+modelingLang['button.select']+"</span>\n"+
    "        <table class=\"tb_normal field_table fdMsgBox\" width=\"100%\">\n" +
    "            <thead>\n" +
    "                <tr>\n" +
    "                    <td >"+modelingLang['behavior.trigger.name']+"</td>\n" +
    "                    <td width=\"30%\">"+modelingLang['behavior.type']+"</td>\n" +
    "                </tr>\n" +
    "            </thead>\n" +
    "        </table>\n" +
    " </div>"
    var MsgSendGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.sendOrRemove = cfg.sendOrRemove;
            this.dataList = [];
            this.modelId = cfg.modelId;
            this.modelMainId = cfg.modelMainId;
            this.draw();
        },
        draw: function () {
            var self = this;
            var $ele = $(tempHtml);
            var $sendMsgSelect = $ele.find(".highLight");
            $sendMsgSelect.on("click", function (e) {
                self.selectBehavior();
            });
            $ele.find(".fdMsgBox").hide();
            // $ele.append($sendMsgSelect);
            // $ele.append("<div class='fdMsgBox'></div>")
            // $ele = $()

            self.element = $ele;
            return $ele;
        },
        selectBehavior: function () {
            var self = this;
            var selectBehaviorParams = self.getSelectBehaviorParams();
            var modelMainId = this.modelMainId;
            var targetModelId = this.modelId;
            var url = "/sys/modeling/base/sysModelingBehavior.do?method=messageSelect&modelMainId=" + modelMainId + "&targetModelId=" + targetModelId + "&msgType=" + self.sendOrRemove;
            dialog.iframe(url, modelingLang['behavior.select.trigger.action'],
                function (r) {
                    if (r == null) {
                        return
                    }
                    var $msgBox = self.element.find(".fdMsgBox");
                    $msgBox.find(".dataItem").remove();
                    $msgBox.hide();
                    for (var i = 0; i < r.ids.length; i++) {
                        var $ele = self.createNewItem(r.ids[i], r.names[i], r.ext[r.ids[i]])
                        $msgBox.append($ele);
                        $msgBox.show();
                    };
                }, {
                    width: 1060,
                    height: 600,
                    params: selectBehaviorParams
                });
        },
        createNewItem: function (id, name, ext) {
            var $ele = $("<tr class='dataItem'></tr>");
            $ele.attr("msgId", id);
            $ele.attr("msgName", name);
            $ele.attr("dataMsgType", ext.doMsgType);
            $ele.append("<td>"+name+"</td>");
            var typeStr=ext.doMsgType=="remove"?modelingLang['behavior.eliminate']:modelingLang['behavior.send']
            $ele.append("<td>"+typeStr+"</td>");
            return $ele;
        },
        getKeyData: function () {
            var self = this;
            var msgList = self.element.find(".fdMsgBox  .dataItem");
            var dataList = [];
            $.each(msgList, function (idx, item) {
                var msg = {
                    behaviorId: $(item).attr("msgId"),
                    behaviorName:$(item).attr("msgName"),
                    "type": $(item).attr("dataMsgType")
                }
                dataList.push(msg);
            })
            return dataList;
        },
        getSelectBehaviorParams: function () {
            var self = this;
            var msgList = self.element.find(".fdMsgBox .dataItem");
            var ids = [];
            var ex = {};
            $.each(msgList, function (idx, item) {
                ids.push($(item).attr("msgId"));
                var exitem = {
                    doMsgType: $(item).attr("dataMsgType"),
                };
                ex[$(item).attr("msgId")] = exitem;
            });
            return {
                oldData: ids.join(";"),
                ext: ex
            };
        },
        initByStoreData: function (storeData) {
            var self = this;
            var $msgBox = self.element.find(".fdMsgBox");
            $.each(storeData, function (idx, item) {
                if (item.behaviorId) {
                    var $ele = self.createNewItem(item.behaviorId, item.behaviorName, {"doMsgType":item.type});
                    $msgBox.append($ele);
                    $msgBox.show();
                }
            })
        },

    })
    exports.MsgSendGenerator = MsgSendGenerator;

})
