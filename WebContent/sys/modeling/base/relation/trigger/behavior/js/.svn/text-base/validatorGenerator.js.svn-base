/**
 * 新建规则生成器
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var TEMP_Validator = "<div class=\"validation-advice\" _reminder=\"true\">\n" +
        "<table class=\"validation-table\">\n" +
        "     <tbody>\n" +
        "        <tr>\n" +
        "            <td>\n" +
        "               <div class=\"lui_icon_s lui_icon_s_icon_validator\"></div>\n" +
        "            </td>\n" +
        "            <td class=\"validation-advice-msg\">\n" +
        "                <span class=\"validation-advice-title\">名称</span><span class='validation-advice-content'>不能为空</span>\n" +
        "            </td>\n" +
        "        </tr>\n" +
        "     </tbody>\n" +
        " </table>\n" +
        "</div>";
    var ValidatorGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.title = cfg.title || "";
            this.content = cfg.content || "";
            this._isShow = cfg.show || false;
            this.container = cfg.container || null;
            this.draw();
        },
        draw: function () {
            var self = this;
            self.element = $(TEMP_Validator);
            self.element.find(".validation-advice-title").text(self.title);
            self.element.find(".validation-advice-content").text(self.content);
            this.container.append(self.element);
        },
        hide: function () {
            this.element.hide();
            this._isShow = false;
        },
        show: function () {
            this.element.show();
            this._isShow = true;
        },
        isShow:function (){
            return this._isShow;
        }
    })
    exports.ValidatorGenerator = ValidatorGenerator;

})
