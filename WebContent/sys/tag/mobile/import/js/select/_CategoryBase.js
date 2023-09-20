define([
    "dojo/_base/declare",
    "dojo/topic",
    "dojo/dom",
    "dojo/dom-construct",
    "dojo/dom-style",
    "dojo/dom-class",
    "dojo/_base/lang",
    "dojo/html",
    "dojo/_base/array",
    "mui/util",
    "dojo/touch",
    "mui/device/adapter",
    "mui/history/listener"
], function (declare, topic, dom, domConstruct, domStyle,
             domClass, lang, html, array, util, touch, adapter, listener) {
    var cateOpt = declare("mui.form._CategoryBase", null, {
        key: null,

        type: null,

        //当前id值`
        curIds: "",

        //显示值
        curNames: "",

        splitStr: ";",

        // 单选情况下是否需要确定才触发提交，分类选择有效
        confirm: false,

        //是否多选
        isMul: false,

        //模板地址
        templURL: null,

        // 脚本模板地址
        jsURL: null,

        _cateDialogPrefix: "__cate_dialog_",

        afterSelect: null,

        authType: "02",

        eventBind: function () {
            this.subscribe("/mui/category/submit", lang.hitch(this, "returnDialog"))
            this.subscribe("/mui/category/cancel", lang.hitch(this, "closeDialog"))
            this.subscribe("/mui/category/clear", lang.hitch(this, "clearDialog"))

            this.subscribe(
                "/mui/category/changeprops",
                lang.hitch(this, "doChangeProps")
            )
        },

        doChangeProps: function (newProps) {
            for (var key in newProps) {
                this[key] = newProps[key]
            }
        },

        returnDialog: function (srcObj, evt) {
            if (evt) {
                if (srcObj.key == this.key) {
                    this.curIds = evt.curIds
                    this.curNames = evt.curNames
                    this.closeDialog(srcObj)
                    if (this.afterSelect) {
                        this.afterSelect(evt)
                    }
                }
            }
        },

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
            domClass.add(this.dialogDiv, "fadeOut animated")

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

        _closeDialog: function (evt) {
            var target = evt.target
            if (this.dialogDiv && target === this.dialogDiv) {
                this.__doCloseDialog()
            }
        },

        closeDialog: function (srcObj) {
            if (this.dialogDiv && srcObj.key == this.key) {
                this.__doCloseDialog()
            }
        },

        clearDialog: function (srcObj) {
            if (srcObj.key == this.key) {
                this.curIds = ""
                this.curNames = ""
                this.closeDialog(srcObj)
            }
        },

        showAnimate: function () {
            domStyle.set(this.dialogContainerDiv, "display", "block")
            domStyle.set(this.dialogDiv, "display", "block")
            domClass.add(this.dialogDiv, "fadeIn animated")
            domStyle.set(this.dialogDiv, "background-color", "rgba(0, 0, 0, 0.6)")
            this.defer(function () {
                domStyle.set(this.dialogDiv, {opacity: 1})
                domClass.remove(this.dialogDiv, "fadeIn")
            }, 500)
        },

        // 格式化路径
        urlResolver: function (url) {
            if (url && !this._working) {
                this._working = true
                this.dialogDiv = dom.byId(this._cateDialogPrefix + this.key)
                if (this.dialogDiv == null) {
                    var _url = util.urlResolver(url, this)
                    if (_url.startsWith("/") && !_url.startsWith(dojoConfig.baseUrl)) {
                        _url = util.formatUrl(_url)
                    }
                    return _url
                }
            }
            return false
        },

        _selectCate$1: function () {
            var url
            if (this.templURL) {
                url = this.urlResolver(this.templURL)
                if (!url) {
                    return
                }
                url = "dojo/text!" + url
            } else if (this.jsURL) {
                url = this.urlResolver(this.jsURL)
                if (!url) {
                    return
                }
            }

            var _self = this

            var dialogId = this._cateDialogPrefix + this.key

            require([url], function (tmplStr) {
                _self.dialogDiv = domConstruct.create(
                    "div",
                    {id: dialogId, className: "muiCateDiaglogN", tabindex: "0"},
                    document.body,
                    "last"
                )
                _self.dialogContainerDiv = domConstruct.create(
                    "div",
                    {className: "muiCateDiaglogContainer "},
                    _self.dialogDiv
                )
                _self.dialogDiv.focus()

                _self.defer(function () {
                    _self.connect(_self.dialogDiv, "click", "_closeDialog")
                }, 500)
                util.disableTouch(_self.dialogDiv, touch.move)
                var dhs = new html._ContentSetter({
                    node: _self.dialogContainerDiv,
                    parseContent: true,
                    cleanContent: true,
                    onBegin: function () {
                        this.content = lang.replace(this.content, {categroy: _self})
                        this.inherited("onBegin", arguments)
                    }
                })

                dhs.set(tmplStr)
                dhs.parseDeferred.then(function (results) {
                    _self.parseResults = results
                    topic.publish("/mui/category/tmploaded", {
                        dom: _self.dialogDiv,
                        widgetList: results
                    })
                })
                dhs.tearDown()
                _self.showAnimate()
            })
        },

        _selectCate: function () {
            var forwardCallback = lang.hitch(this, function () {
                this._selectCate$1()
                this.previousTitle = document.title
                if (this.subject) {
                    adapter.setTitle(this.subject)
                }
            })
            var backCallback = lang.hitch(this, function () {
                // 浏览器后退，清掉beforeSelectCateHistoryId
                this.beforeSelectCateHistoryId = null;
                this.__doCloseDialog()
            });
            var listenerResult = listener.push({
                forwardCallback: forwardCallback,
                backCallback: backCallback
            });
            // 记录进入分类前的页面ID
            this.beforeSelectCateHistoryId = listenerResult.previousId
        }
    })
    return cateOpt
})
