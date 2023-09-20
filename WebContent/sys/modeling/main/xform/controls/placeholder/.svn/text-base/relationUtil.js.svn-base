/**
 * 用于获取业务关联的配置信息
 */

define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");

    var RelationUtil = base.Base.extend({

        cfg: {},

        get: function (fdAppModelId) {
            var self = this;
            if ($.isEmptyObject(self.cfg)) {
                var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppXFormMain.do?method=getRelationDynamic&modelName="
                    + Xform_ObjectInfo.mainModelName + "&fdAppModelId=" + fdAppModelId;
                $.ajax({
                    url: url,
                    type: "get",
                    async: false,
                    cache: false,
                    success: function (data) {
                        self.cfg = self.formatData(JSON.parse(data));
                    }
                });
            }
            return this.cfg;
        },
        getAttachmentClone: function (d, callBack,callBackParam) {
            var url = Com_Parameter.ContextPath + "sys/modeling/main/modelingAppXFormMain.do?method=getCloneDocAtts";
            $.ajax({
                type: "get",
                data: {param: JSON.stringify(d)},
                url: url,
                async: false,
                cache: false,
                success: function (result) {
                    if (result) {
                        var r = JSON.parse(result);
                        if (result && r.success) {
                            if (callBack) {
                                callBack(r.data,callBackParam)
                            }
                            return
                        }
                    }
                    console.error("getAttachmentClone error::", result);

                }
            });
        },
        // 合并输出参数的明细表key
        formatData: function (data) {
            for (var controlId in data) {
                if (data[controlId].status === "00" && data[controlId]["outputs"]) {
                    // 1、把目标控件是明细表的，单独放置；2、把源控件的明细表ID合并
                    var commonFields = data[controlId]["outputs"]["fields"] || [];
                    var details = data[controlId]["outputs"]["details"] = {};
                    for (var sourceControlId in commonFields) {
                        var outCfg = commonFields[sourceControlId];
                        // 如果目标控件是明细表，则放置到details里面
                        for (var i = outCfg.target.length - 1; i >= 0; i--) {
                            var targetInfo = outCfg.target[i];
                            if (targetInfo.controlId.indexOf(".") > -1) {
                                if (!details.hasOwnProperty(sourceControlId)) {
                                    details[sourceControlId] = {target: [], type: outCfg.type};
                                }
                                details[sourceControlId].target.push(targetInfo);
                                outCfg.target.splice(i, 1);
                            }
                        }
                    }
                    // 把源控件的明细表ID合并
                    data[controlId]["outputs"]["fields"] = this._divideDetailsId(commonFields, "sourceCommon", "sourceDetails");
                    data[controlId]["outputs"]["details"] = this._divideDetailsId(details, "sourceCommon", "sourceDetails");
                }
            }
            return data;
        },

        _divideDetailsId: function (fields, sourceCommonKey, sourceDetailsKey) {
            var temp = {};
            temp[sourceCommonKey] = {};
            temp[sourceDetailsKey] = {};
            for (var key in fields) {
                var field = fields[key];
                if (key.indexOf(".") > -1) {
                    // fd_380da16ff9ebfa.fd_380da171fa821e
                    var keyArr = key.split(".");
                    if (!temp[sourceDetailsKey].hasOwnProperty(keyArr[0])) {
                        temp[sourceDetailsKey][keyArr[0]] = {};
                    }
                    temp[sourceDetailsKey][keyArr[0]][keyArr[1]] = field;
                } else {
                    if (!temp[sourceCommonKey].hasOwnProperty(key)) {
                        temp[sourceCommonKey][key] = {};
                    }
                    temp[sourceCommonKey][key] = field;
                }
            }
            return temp;
        },
        formatFillingData: function (data) {
            if (data.outputs) {
                // 1、把目标控件是明细表的，单独放置；2、把源控件的明细表ID合并
                var commonFields = data.outputs.fields || [];
                var details = data.outputs.details || {};
                for (var sourceControlId in commonFields) {
                    var outCfg = commonFields[sourceControlId];
                    // 如果目标控件是明细表，则放置到details里面
                    for (var i = outCfg.target.length - 1; i >= 0; i--) {
                        var targetInfo = outCfg.target[i];
                        if (targetInfo.controlId.indexOf(".") > -1) {
                            if (!details.hasOwnProperty(sourceControlId)) {
                                details[sourceControlId] = {target: [], type: outCfg.type};
                            }
                            details[sourceControlId].target.push(targetInfo);
                            outCfg.target.splice(i, 1);
                        }
                    }
                }
                // 把源控件的明细表ID合并
                data.outputs.fields  = this._divideDetailsId(commonFields, "sourceCommon", "sourceDetails");
                data.outputs.details  = this._divideDetailsId(details, "sourceCommon", "sourceDetails");
            }
            return data;
        },
    });

    module.exports = new RelationUtil();
})