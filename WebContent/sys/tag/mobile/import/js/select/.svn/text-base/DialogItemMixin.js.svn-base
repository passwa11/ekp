define([
    "dojo/_base/declare",
    "dojo/dom-class",
    "dojo/dom-style",
    "mui/iconUtils",
    "./_DialoagItemMixinBase",
    "mui/util",
    "dijit/registry",
], function (declare, domClass, domStyle, iconUtils, DialoagItemMixinBase, util, registry) {
    var item = declare("mui.tag.DialogItemMixin", [DialoagItemMixinBase], {
        buildRendering: function () {
            this.fdId = this.value;
            this.label = this.text;
            this.icon = 'mui mui-organization';
            this.type = window.TAG_TYPE_DOCUMENT
            // this.type = this.nodeType == 'CATEGORY' ?
            //     window.TAG_TYPE_CATEGORY : (this.nodeType == 'TEMPLATE' ?
            //         window.TAG_TYPE_TEMPLATE : window.TAG_TYPE_DOCUMENT);
            this.inherited(arguments);
            if (this.name && this.name != "") {
                this.titleNode.innerHTML = util.formatText(this.name);
            }
        },
        //获取分组标题信息
        getTitle: function () {
            return this.label;
        },

        //是否显示往下一级
        showMore: function () {
            // if (this.type == window.TAG_TYPE_CATEGORY || this.type == window.TAG_TYPE_TEMPLATE) {
            //     return true;
            // }
            return false;
        },

        //是否显示选择框
        showSelect: function () {
            var pWeiget = this.getParent();
            if (pWeiget && this.type == window.TAG_TYPE_DOCUMENT) {
                return true;
            }
            return false;
        },

        //是否选中
        isSelected: function () {
            var pWeiget = this.getParent();
            var list = registry.byId("tag_DialogItemListMixin")

            if (pWeiget && pWeiget[pWeiget.primaryKey]) {

                var curValues = pWeiget[pWeiget.primaryKey].split(";");
                var key = "fdId";
                if(pWeiget.primaryKey == "curNames") {
                    key = "label";
                }
                if(curValues.indexOf(this[key]) > -1) {
                    return true;
                }
            }
            return false;
        },

        buildIcon: function (iconNode) {
            if (this.icon) {
                if (this.nodeType != "CATEGORY") {
                    domStyle.set(iconNode, "display", "none");
                }
                iconUtils.setIcon(this.icon, null,
                    this._headerIcon, null, iconNode);
            }
        }
    });
    return item;
});