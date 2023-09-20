define([
    "dojo/_base/declare",
    "mui/form/_CategoryBase",
    // "./_CategoryBase",
    "dojo/dom",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/_base/lang",
    "dojo/_base/array",
    "mui/device/adapter",
    "mui/history/listener"
], function (declare, _CategoryBase, dom, domConstruct, domStyle,
             domClass, lang, array, adapter, listener) {
    var cateOpt = declare("mui.tag.DialogCategoryBase", _CategoryBase, {
        //列表数据获取URL
        listDataUrl: "",
        //当多选的时候，获取底部选中的的信息（头部信息只能分类，底部有可能是分类或是文档或是模板）
        selectionUrl: "",
        //搜索获取URL
        searchDataUrl: "",
        //字段参数
        fieldParam: "",

        __doCloseDialog: function () {
            if (this.previousTitle) {
                adapter.setTitle(this.previousTitle)
                this.previousTitle = null
            }
            if (this.beforeSelectCateHistoryId) {
                listener.goNoop({
                    historyId: this.beforeSelectCateHistoryId
                });
                this.beforeSelectCateHistoryId = null;
            }
            if(this.dialogDiv) {
                domClass.add(this.dialogDiv, "fadeOut animated")
            }

            this.defer(function () {
                if (this.dialogContainerDiv) {
                    domStyle.set(this.dialogContainerDiv, "display", "none")
                }
                if (this.dialogDiv) {
                    domStyle.set(this.dialogDiv, "display", "none")
                }
            }, 500)

            setTimeout(
                lang.hitch(this, function () {
                    if (this.parseResults && this.parseResults.length) {
                        array.forEach(this.parseResults, function (w) {
                            if (w.destroy) {
                                w.destroy()
                            }
                        })
                        delete this.parseResults
                    }
                    domConstruct.destroy(this.dialogDiv)
                    this.dialogDiv = null
                    this._working = false
                }),
                410
            )
        },
        showAnimate: function() {
            domStyle.set(this.dialogContainerDiv, "display", "block")
            if (this.dialogDiv) {
                domStyle.set(this.dialogDiv, "display", "block")
                domClass.add(this.dialogDiv, "fadeIn animated")
                domStyle.set(this.dialogDiv, "background-color", "rgba(0, 0, 0, 0.6)")
                this.defer(function () {
                    if(this.dialogDiv) {
                        domStyle.set(this.dialogDiv, {opacity: 1})
                        domClass.remove(this.dialogDiv, "fadeIn")
                    }
                }, 500)
            }
        },
    });
    return cateOpt;
});