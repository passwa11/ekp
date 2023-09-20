/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var env = require('lui/util/env');
    var topic = require("lui/topic");
    var temHtml = "" +
        "          <div class=\"modeling-dylink-item\">\n" +
        "                <div class=\"dylink-content\">\n" +
        "                    <div class=\"dylink-content-link\">\n" +
        "                        <div class=\"link-pref\"></div>\n" +
        "                        <div class=\"link-dynamic\">\n" +
        "                            <input value=\"\"/>\n" +
        "                        </div>\n" +
        "                    </div>\n" +
        "                    <div class=\"dylink-content-btn\">\n" +
        "                        <input class=\"full-uri\" type=\"hidden\" value=\"\">\n" +
        "                        <i class=\"dynamic-i-copy\" title=\"复制\" ></i>\n" +
        "                        <i class=\"dynamic-i-visited\" title=\"访问\" > </i>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "                <div class=\"dylink-btn\">\n" +
        "                    <div>\n" +
        "                        <i class=\"dynamic-i-del\" title=\"删除\" ></i>\n" +
        "                    </div>\n" +
        "                </div>\n" +
        "            </div>\n" +

        "        </div>"

    window.copyText = function ($this, text) {
        var $input = $("<input style='position: absolute;' />");//创建input对象
        var currentFocus = document.activeElement;//当前获得焦点的元素
        $this.after($input);//添加元素
        $input.val(text);
        $input.focus();
        if ($input.setSelectionRange)
            $input.setSelectionRange(0, $input.value.length);//获取光标起始位置到结束位置
        else
            $input.select();
        try {
            var flag = document.execCommand("copy");//执行复制
        } catch (eo) {
            var flag = false;
        }
        $input.remove()//删除元素
        currentFocus.focus();
        return flag;
    };

    window.dyDel = function () {
        var $this = $(event.target);
        $this.parent().parent().parent().remove();
        dialog.alert("删除成功！")
    };
    var MOBILE_PREF = "mobile/"

    var ModelingDynamicLink = base.Container.extend({
        /*
        cfg:{
        type:pc|mobile,
        original:true|false,
        }
         */
        initProps: function ($super, cfg) {
            $super(cfg);
            this.valueName = "fd_dyLink_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.status = "init";
            this.type = cfg.type;
            this.appId = cfg.appId;
            this.original = cfg.original ? true : false;
            this.valCache = {
                dbValue: "",
                lastInput: "",
                currentInput: ""
            }
            this.build();
        },
        build: function () {
            var $ele = $(temHtml);
            $ele.attr("id", this.valueName);
            var self = this;
            self.valCache.dbValue = $ele.find(".link-dynamic input").val();
            if (self.original) {
                $ele.addClass("dylink-original");
                self.buildOriginalUrl($ele);
                $ele.find(".dynamic-i-del").remove();
            } else {
                $ele.addClass("dynamic-empty");
                self.buildDynamicUrl($ele);
                $ele.find(".dynamic-i-del").on("click", function () {
                    self.ajaxDelete();
                });
                $ele.find(".link-dynamic input").on("focus", function () {
                    self.removeMsg();
                    $ele.addClass("dynamic-empty");
                    $ele.addClass("dynamic-focus");
                });
                $ele.find(".link-dynamic input").on("blur", function () {
                    $ele.removeClass("dynamic-focus");
                    if (self.validateUrl($(this))) {
                        if (self.valCache.dbValue != self.valCache.currentInput) {
                            self.ajaxSave();
                        } else {
                            $ele.removeClass("dynamic-empty");
                        }
                    }
                });
            }
            $ele.find(".dynamic-i-copy").on("click", function () {
                var flag = copyText($(this), self.getUrl($ele)); //传递文本
                dialog.alert(flag ? "复制成功！" : "复制失败！")
            });
            $ele.find(".dynamic-i-visited").on("click", function () {
                window.open(self.getUrl($ele), "_blank");
            });
            self.element = $ele;
            var $c = self.getContainer();
            $c.find(".modeling-dylink-add").before(self.element);
            //前缀宽度自动调整
            var width = 400;
            $(".modeling-dylink-item").each(function (i, e) {
                if (!$(e).hasClass("dylink-original")) {
                    width = $(e).find(".link-pref").width();
                    return;
                }
            });
            width = 640 - width;
            $ele.find(".link-dynamic").find("input").css("width", width + "px");
            this.status = "builded";
        },

        getContainer: function () {
            var cls = "." + this.type + "IndexUrlPath";
            return $(cls)
        },
        buildDynamicUrl: function ($ele) {
            var urlPre = this.type == "mobile" ? env.fn.formatUrl("/sys/modeling/main/dy/" + MOBILE_PREF, true) : env.fn.formatUrl("/sys/modeling/main/dy/", true)
            $ele.find(".link-pref").html(urlPre);
            $ele.find(".link-dynamic").val("");
        },
        buildOriginalUrl: function ($ele) {
            var idxUrl = "";
            if (this.type == "pc") {
                idxUrl = env.fn.formatUrl("/sys/modeling/main/index.jsp?fdAppId=" + this.appId, true)
            } else {
                idxUrl = env.fn.formatUrl("/sys/modeling/main/mobile/modelingAppMainMobile.do?method=index&fdId="
                    + this.appId, true);
            }
            $ele.find(".link-pref").text(idxUrl);
            $ele.find(".link-dynamic").val("");
        },
        getUrl: function ($ele) {
            return $ele.find(".link-pref").text() + $ele.find(".link-dynamic input").val();
        },
        validateUrl: function ($e) {
            var self = this;
            self.valCache.currentInput = $e.val();
            var v = self.valCache.currentInput;
            if (!v) {
                isTrueLink = false;
                self.addMsg(false, "自定义地址不能为空!");
                return false;
            }
            if (v.length > 100) {
                isTrueLink = false;
                self.addMsg(false, "自定义部分最大长度为100 ");
                return false;
            }
            var reg = new RegExp('^[a-zA-Z0-9\_]{1,}[a-zA-Z0-9\_\/]{0,}$');
            if (reg.test(v)) {
                isTrueLink = true;
                return true;
            } else {
                isTrueLink = false;
                self.addMsg(false, "只允许英文字母,数字,下划线‘_’，以及斜杠‘/’,并且不能以‘/’开头 ");
                return false;

            }

        },
        ajaxSave: function () {
            var self = this;
            var data = {
                "oldValue": self.valCache.dbValue,
                "newValue": self.valCache.currentInput,
                "appId": this.appId,
                "type": this.type
            };
            if (this.type == "mobile") {
                data.newValue = MOBILE_PREF + self.valCache.currentInput;
                data.oldValue = MOBILE_PREF + self.valCache.dbValue;
            }
            console.debug("ajaxSave::", self.valCache, data)
            $.ajax({
                url: Com_Parameter.ContextPath + "sys/modeling/base/dynamicLink.do?method=ajaxSave",
                method: 'POST',
                contentType: "application/json; charset=utf-8",
                cache: false,
                data: JSON.stringify(data),
                async: false
            }).success(function (resultStr) {
                var result = JSON.parse(resultStr);
                if (result.success) {
                    self.valCache.dbValue = self.valCache.currentInput;
                    self.element.removeClass("dynamic-empty");
                } else {
                    self.valCache.lastInput = "";
                }
                self.addMsg(result.success, result.msg);
            });
        },
        ajaxDelete: function () {
            var self = this;
            //165960,165962 点击删除按钮，直接删除数据库的这条数据，key取缓存的最新保存数据库的key
            var key = self.valCache.dbValue;
            if (key) {
                if (this.type == "mobile") {
                    key = MOBILE_PREF + key;
                }
                $.ajax({
                    url: Com_Parameter.ContextPath + "sys/modeling/base/dynamicLink.do?method=ajaxDelete&key=" + key,
                    method: 'GET',
                    async: false
                }).success(function (resultStr) {
                    var result = JSON.parse(resultStr);
                    if (result.success) {
                        self.element.remove();
                        self.status = "delete";
                        dialog.alert("删除成功！");
                        topic.channel("modelingDynamicLink").publish("dyLink.remove", {
                            "type": self.type
                        });
                    } else {
                        self.addMsg(result.success, result.msg);
                    }
                });
            } else {
                self.element.remove();
                self.status = "delete";
                dialog.alert("删除成功！");
                topic.channel("modelingDynamicLink").publish("dyLink.remove", {
                    "type": self.type
                });
            }
        },
        addMsg: function (flag, msg) {
            var self = this;
            var cls = flag ? "dynamic-success" : "dynamic-error";
            if (flag && !msg) {
                msg = "操作成功！";
            }
            msg = msg.replace(MOBILE_PREF, "");
            self.element.append("<p class='" + cls + "-p'>" + msg + "</p>");
            self.element.addClass(cls)
        },
        removeMsg: function () {
            var self = this;
            self.element.find(".dynamic-error-p").remove();
            self.element.removeClass("dynamic-error");
            self.element.find(".dynamic-success-p").remove();
            self.element.removeClass("dynamic-success");
        },
        setValue: function (value) {
            if (this.type == "mobile") {
                value = value.replace(MOBILE_PREF, "");
            }
            this.element.removeClass("dynamic-empty");
            this.element.find(".link-dynamic input").val(value);
            this.valCache.dbValue = value;
        },
        startup: function ($super, cfg) {
            $super(cfg);
        }

    });

    exports.ModelingDynamicLink = ModelingDynamicLink;
});
