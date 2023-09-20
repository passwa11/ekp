/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');

    var FieldMappingMark = base.Container.extend({
        markCss: {
            "titleRed": {
                "color": "#F25643"
            },
            "titleRed_Tips": {
                "color": "#F25643",
                "cursor": "help"
            }
        },
        initProps: function ($super, cfg) {
            $super(cfg);
            //title提示，可选关闭
            console.debug(">>>initProps FieldMappingMark ---\n cfg::", cfg);
            this.titleTips = true;
            this.titleTipsText = "此处内容可能已在表单设计中被修改";
            this.baseFMMarkUrl = Com_Parameter.ContextPath + "sys/modeling/base/modelingFormModified.do?method=getNeedModifiedData";
        },
        startup: function ($super, cfg) {
            $super(cfg);
            var self = this;
            if (self.getNeedModifiedDataUrl) {
                console.debug(">>>startup FieldMappingMark --- url :: \n", self.getNeedModifiedDataUrl);
                $.ajax({
                    url: self.getNeedModifiedDataUrl,
                    type: "get",
                    success: function (rtn) {
                        console.debug("\n>>>getNeedModifiedDataUrl::", self.getNeedModifiedDataUrl,
                            "\n\ndata::", rtn);
                        try {
                            rtn = JSON.parse(rtn);
                        } catch (e) {
                            console.error("获取映射格式化失败：：", e)
                            return;
                        }
                        if (rtn.status === "success") {
                            self.fieldMapping = rtn.fieldMapping;
                            self.render()
                            console.debug(" --- render FieldMappingMark end <<<<");
                        }

                    },
                    error: function (rtn) {
                        console.error(rtn.msg || "获取映射失败", "\nURL:: \t", self.getNeedModifiedDataUrl)
                    }
                });
            }
        },
        render: function () {
            var self = this;
            console.debug("render", self.fieldMapping);
            if (!self.localMapping) {
                return;
            }
            //console.log(self.fieldMapping)
            for (var local in self.fieldMapping) {
                console.debug("local::", local);
                var fun = self.localMapping[local];
                if (fun && self.fieldMapping[local]) {
                    self[fun](self.fieldMapping[local])
                }
            }
        },
        __judgeIn: function ($ele, fieldList) {
            var exp = $ele.find("input[type='hidden']").val();
            if (exp){
                for (var key in fieldList) {
                    if (exp.indexOf(key) > -1) {
                        return true;
                    }
                }
            }
            return false;

        },
        __renderTitle: function ($ele) {
            if (this.titleTips) {
                $ele.attr("title", this.titleTipsText);
                $ele.css(this.markCss.titleRed_Tips);
            } else {
                $ele.css(this.markCss.titleRed);
            }

        },
        __renderRadio: function (name, fieldList) {
            var self = this;
            var $radios = $("input[name='" + name + "']");
            if ($radios) {
                if ($radios.length > 0) {
                    if ($radios.length == 1) {
                        for (var fieldId in fieldList) {
                            if ($radios.val() === fieldId) {
                                $radios.next("span").css(self.markCss.titleRed);
                            }
                        }
                        return;
                    }
                    $radios.each(function (sidx, sitem) {
                        //console.log("__renderRadio", sidx, $(sitem).val())
                        for (var fieldId in fieldList) {
                            if ($(sitem).val() === fieldId) {
                                $(sitem).next("span").css(self.markCss.titleRed);
                            }
                        }
                    });
                }
            }
        },
        __renderTableSelect: function ($table, selectClass, fieldList) {
            var self = this;
            self.__renderContainerSelect($table.find("tr"), selectClass, fieldList)
        },
        __renderContainerSelect: function ($containers, selectClass, fieldList) {
            var self = this;
            $containers.each(function (idx, item) {
                var $select = $(item).find("select." + selectClass);
                if ($select.length > 0) {
                    if ($select.length == 1) {
                        for (var fieldId in fieldList) {
                            if ($select.val() === fieldId) {
                                $select.css(self.markCss.titleRed);
                            }
                        }
                        return;
                    }
                    $select.each(function (sidx, sitem) {
                        for (var fieldId in fieldList) {
                            if ($(sitem).val() === fieldId) {
                                $(sitem).css(self.markCss.titleRed);
                            }
                        }
                    });
                }
            });
        },
        __renderFormula: function (fieldList, $ele, val) {
            var self = this;
            for (var fieldId in fieldList) {
                if (val.indexOf(fieldId) > -1) {
                    $ele.css(self.markCss.titleRed);
                }
            }
        },
        __renderFormulaOfTr: function (fieldList, $tr, formulaDivClass) {
            var self = this;
            if (!formulaDivClass) {
                formulaDivClass = "modeling_formula ";
            }
            var $formulaDiv = $tr.find("." + formulaDivClass);
            if ($formulaDiv && $formulaDiv.length > 0) {
                var val = $formulaDiv.find("input[type='hidden']").val();
                var ele = $formulaDiv.find("input[type='text']");
                self.__renderFormula(fieldList, ele, val);
            }
        }

    });
    exports.FieldMappingMark = FieldMappingMark;
});
