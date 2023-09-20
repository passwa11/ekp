/**
 * 排序生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    /*
     {
                    "isThrough":true|false,
                    "viewModelId":"",
                    "viewModelName":"",
                    "viewSet":{
                        "type":"0|列表，1|查看，2|新建",
                        "id":"16dfc842701f63570bb8ca443af88e45",
                        "name":"name",
                        "def":true,
                    },
                    "view":[{
                        "idx":"0",
                        "ele":"fdId",
                        "expression": {
                            "text": "fdId",
                            "value": "$ID$"
                        }
                    }],
                    "viewNew":[{
                        "idx":"0",
                        "ele":"fdId",
                        "expression": {
                            "text": "fdId",
                            "value": "$ID$"
                        }
                    }]
                }
     */
    var ThroughRecordGenerator = base.Component.extend({
        //lines,
        initProps: function ($super, cfg) {
            //console.log("SortRecordGenerator", cfg);
            $super(cfg);
            this.feildName = cfg.fieldName;
            this.pcfg = cfg.pcfg;
            this.valueName = cfg.fieldName + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.build();
            this.lines = [];
        },
        build: function () {
            var self = this;
            var $pele = self.pcfg.container;
            var $ele = $pele.find("[mdlng-rltn-property=\"" + self.feildName + "\"]");

            var $valEle = $ele.find("[mdlng-rltn-prprty-value=\"" + self.feildName + "\"]");
            var $isThrough = $valEle.find("[mdlng-rltn-prprty-type=\"throughradio\"]");
            self.radioele = $isThrough;
            $isThrough.on("change", function () {
                var isThrough = self.element.find("[name='"+self.feildName+"IsThrough']:checked").val();
                self.draw(isThrough);
            });
            var $viewSelect = $valEle.find("[mdlng-rltn-prprty-type=\"throughdialog\"]");
            $viewSelect.on("click", function () {
                self.selectView();
            });
            self.viewSelect = $viewSelect;
            self.element = $ele;
            $isThrough.trigger($.Event("change"));
        },
        draw: function (isThrough) {
            //console.log(isThrough)
            var self = this;
            if (isThrough === "1") {
                self.viewSelect.show();
            } else {
                self.viewSelect.hide();
            }
        },
        selectView: function () {
            var self = this;
            dialog.iframe("/sys/modeling/base/relation/import/model_view_select.jsp?method=none&fdModelId=" + self.pcfg.modelPassiveId+"&fdId="+self.viewId, modelingLang['relation.select.view'], function (value) {
                if (value == null)
                    return;
                var $p = $("<p modeling-mark-data='" + value.fdId + "'></p>");
                $p.append(value.fdName);
                self.viewSelect.html($p)
                self.viewId = value.fdId;
                self.fdType = value.fdType;
            }, {
                width: 1010,
                height: 600
            });
        },

        getKeyData: function () {
            var self = this;
            var isThrough = self.element.find("[name='"+this.feildName+"IsThrough']:checked").val();
            var $p= self.viewSelect.find("p");
            var id  =  $p.attr("modeling-mark-data");
            var isDef = false;
            if ( !id || id=="_def" || id === "undefined")
                isDef = true;
            var keyData = {
                "isThrough": isThrough==="1",
                "viewModelId": self.pcfg.modelPassiveId,
                "viewModelName": "",
                "viewSet": {
                    "type": self.fdType,
                    "id": $p.attr("modeling-mark-data"),
                    "name": $p.text(),
                    "def": isDef,
                }
            };

            return keyData;
        },

        initByStoreData: function (storeData) {
            var self = this;
              console.debug("initByStoreData",storeData);
            if (storeData.isThrough === true) {
                self.radioele.find("#"+this.feildName+"IsThrough1").prop('checked', true);
                self.radioele.find("#"+this.feildName+"IsThrough0").prop('checked', false);
                self.draw("1");
                var value = storeData.viewSet;
                if(value.id){
                    var $p = $("<p modeling-mark-data='" + value.id + "'></p>");
                    $p.append(value.name);
                    self.viewSelect.html($p)
                    self.fdType = value.type;
                    self.viewId = value.id;
                }else {
                    self.viewSelect.html("<p modeling-mark-data=\"_def\">"+modelingLang['relation.default.view']+"</p>");
                }
            } else {
                self.radioele.find("#"+this.feildName+"IsThrough1").prop('checked', false);
                self.radioele.find("#"+this.feildName+"IsThrough0").prop('checked', true);
            }
            // self.radioele.trigger($.Event("change"));

        }
    });

    exports.ThroughRecordGenerator = ThroughRecordGenerator;
});
