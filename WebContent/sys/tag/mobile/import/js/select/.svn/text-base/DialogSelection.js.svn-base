define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dijit/_WidgetBase",
    "dojo/_base/array",
    "dojo/dom",
    "dojo/dom-construct",
    "dojo/request",
    "dojo/topic",
    "mui/util",
    "mui/dialog/Dialog",
    "mui/dialog/Tip",
    "mui/iconUtils",
    "mui/history/listener",
    "mui/i18n/i18n!sys-mobile",
    "./tagUtil",
], function (
    declare,
    lang,
    WidgetBase,
    array,
    dom,
    domConstruct,
    request,
    topic,
    util,
    Dialog,
    Tip,
    iconUtils,
    listener,
    Msg,
    tagUtil
) {
    var selection = declare("mui.tag.CategorySelection", [WidgetBase], {
        //非必填项,用于初始值
        curIds: null,

        curNames: null,

        // 打开分类选择弹窗前的历史记录Id
        beforeSelectCateHistoryId: null,

        //获取详细信息地址
        detailUrl: "",

        primaryKey: "curIds",

        isMul: true,

        baseClass: "muiCateSec muiCateSecBottom",

        //已选列表id, name, icon
        cateSelArr: [],

        itemPrefix: "_CateSecItem_",

        //对外事件唯一标示
        key: null,

        buildRendering: function () {
            this.curNames = tagUtil.decodeHTML(this.curNames);
            this.inherited(arguments)
            this.cateSelArr = []
            this.containerNode = domConstruct.create(
                "div",
                {className: "muiCateSecContainer"},
                this.domNode
            )
            // 左侧
            this.leftArea = domConstruct.create(
                "div",
                {className: "muiCateSecLeft"},
                this.containerNode
            )

            // 右侧按钮
            this.rightArea = domConstruct.create(
                "div",
                {className: "muiCateSecRight"},
                this.containerNode
            )
            this.clearButtonNode = domConstruct.create(
                "span",
                {
                    className: "muiCateClearBtn",
                    innerHTML: Msg["mui.button.clear"]
                },
                this.rightArea
            )
            this.buttonNode = domConstruct.create(
                "span",
                {
                    className: "muiCateSecBtn ",
                    innerHTML: Msg["mui.button.ok"]
                },
                this.rightArea
            )
        },

        postCreate: function () {
            this.inherited(arguments)
            this.subscribe("/mui/category/selected", "_addSelItme")
            this.subscribe("/mui/category/unselected", "_delSelItem")
            this.subscribe("/mui/category/clearSelected", "_clearSelItem")
        },

        startup: function () {
            if (this._started) {
                return
            }
            this.inherited(arguments)
            this._initSelection()
        },

        destroy: function () {
            this.inherited(arguments)
        },

        _subSelItem: function () {
            topic.publish("/mui/category/submit", this, this._calcCurSel())
        },

        _clearSelItem: function (obj, evt) {
            if (obj.key != this.key) {
                return
            }

            // 例外id
            var fdId
            if (evt) {
                fdId = evt.fdId
            }

            var index = 0
            while (this.cateSelArr.length > index) {
                var item = this.cateSelArr[0]
                if (item.fdId == fdId) {
                    index++
                    continue
                }
                this._delSelItem(this, item)
                topic.publish("/mui/category/cancelSelected", this, item)
            }
        },

        _popSelItem: function () {
            var previousId;
            var dialog;
            // 打开弹窗
            var forwardCallback = lang.hitch(this, function () {
                var element = domConstruct.create("div", {className: "muiCateSecItems"});
                array.forEach(this.cateSelArr, function (evt) {
                    var selItem = this._buildSelItem(evt);
                    element.appendChild(selItem);
                }, this);
                dialog = Dialog.element({
                    canClose: false,
                    element: element,
                    position: "bottom",
                    scrollable: true,
                    parseable: true,
                    showClass: "muiTagCateSecPop",
                    callback: lang.hitch(this, function () {
                        if (previousId) {
                            listener.go({
                                historyId: previousId,
                                callback: function () {
                                } //空操作
                            })
                        }
                        dialog = null
                        previousId = null;
                    })
                })
            });
            // 关闭弹窗
            var backCallback = lang.hitch(this, function () {
                if (dialog) {
                    dialog.hide();
                    dialog = null;
                    previousId = null;
                }
            });
            var result = listener.push({
                forwardCallback: forwardCallback,
                backCallback: backCallback
            });
            previousId = result.previousId;
        },

        _initSelection: function () {
            var _self = this
            if (this[this.primaryKey]) {
                // 存在url时加载数据
                if (this.detailUrl) {
                    var _url = util.urlResolver(this.detailUrl, this)
                    _url = util.formatUrl(_url)

                    // #137964 URL参数超长会导致请求失败，这里将URL进行解析，参数使用请求体提交
                    var promise;
                    if (_url.length > 2048 && _url.indexOf(".jsp?") > -1) {
                        var __url = _url.split("?");
                        var __params = __url[1].split("&");
                        var params1 = [];
                        var params2 = {};
                        for (var i = 0; i < __params.length; i++) {
                            if (__params[i].length > 0) {
                                var temp = __params[i].split("=");
                                if (temp[1].length > 500) {
                                    params2[temp[0]] = temp[1];
                                } else {
                                    params1.push(__params[i]);
                                }
                            }
                        }
                        _url = __url[0] + "?" + params1.join("&");
                        promise = request.post(_url, {
                            handleAs: "json",
                            data: params2
                        })
                    } else {
                        promise = request.post(_url, {
                            handleAs: "json"
                        })
                    }
                    promise.then(function (items) {
                        if (items.length > 0) {
                            array.forEach(items, function (item) {
                                _self._addSelItme(_self, item)
                            })
                        }
                    })
                } else {
                    var ids = this.curIds.split(";")
                    var names = this.curNames.split(";")
                    var data = [];
                    array.forEach(names, function (name, i) {
                        var id = ids[i]? ids[i] : tagUtil.GenerateId();
                        var args = {
                            fdId: id,
                            label: name,
                            labelLevel: name
                        }
                        data.push(args);
                    })
                    this.cateSelArr = data;
                    this.defer(function () {
                        this._resizeSelection()
                    }, 250)
                }
            } else {
                this._resizeSelection()
            }
        },

        // 构建描述信息
        buildDesc: function () {
        },

        _buildSelItem: function (item) {
            var selDom = domConstruct.create("div", {
                id: this.itemPrefix + item.fdId,
                className: "muiCateSecItem"
            })

            this.buildIcon(selDom, item)

            var leftArea = domConstruct.create(
                "div",
                {className: "muiCateSecItemLabel"},
                selDom
            )

            domConstruct.create("div", {innerHTML: item.label}, leftArea)

            this.buildDesc(leftArea, item)

            var delArea = domConstruct.create(
                "div",
                {
                    className: "muiCateSecItemDelArea"
                },
                selDom
            )
            domConstruct.create(
                "div",
                {
                    className: "muiCateSecItemDel"
                },
                delArea
            )
            this.connect(delArea, "click", function (evt) {
                this._delSelItem(this, item)
                domConstruct.destroy(dom.byId(this.itemPrefix + item.fdId))
                topic.publish("/mui/category/cancelSelected", this, item)
            })
            return selDom
        },
        buildIcon: function (iconNode, item) {
            iconUtils.setIcon("mui mui-file-text", null, null, null,
                iconNode);
        },

        _checkInSelArr: function (value) {
            var flag = false
            for (var i = 0; i < this.cateSelArr.length; i++) {
                if (this.cateSelArr[i].fdId == value) {
                    flag = true
                    break
                }
            }
            return flag
        },

        _addSelItme: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt) {
                    if (typeof evt.labelLevel == "undefined") {
                        evt.labelLevel =
                            typeof srcObj.labelLevel != "undefined"
                                ? srcObj.labelLevel
                                : srcObj.label
                    }
                    if (!this._checkInSelArr(evt.fdId)) {
                        this.cateSelArr.push(evt)
                    }
                }
                this._resizeSelection()
            }
        },

        _delSelItem: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt && evt.fdId) {
                    for (var i = 0; i < this.cateSelArr.length; i++) {
                        if (this.cateSelArr[i].fdId == evt.fdId) {
                            this.cateSelArr.splice(i, 1)
                            break
                        }
                    }
                    this._resizeSelection()
                }
            }
        },

        _resizeSelection: function () {
            var xPos = 0
            var childCount = this.cateSelArr.length
            // 确定事件
            if (this.subHandle == null) {
                this.subHandle = this.connect(this.buttonNode, "click", "_subSelItem")
            }
            if (childCount > 0) {
                this.leftArea.className = "muiCateSecLeft"
//        this.buttonNode.className = "muiCateSecBtn"
                this.clearButtonNode.className = "muiCateClearBtn"

                // 清空事件
                if (this.clearHandle == null) {
                    this.clearHandle = this.connect(
                        this.clearButtonNode,
                        "click",
                        lang.hitch(this, function () {
                            this._clearSelItem(this)
                            this.clearButtonNode.className =
                                "muiCateClearBtn muiCateSecBtnDis"
                            if (this.clearHandle) {
                                this.disconnect(this.clearHandle)
                                this.clearHandle = null
                            }
                        })
                    )
                }
                // 已选人员弹窗弹出事件
                if (this.popHandle == null) {
                    this.popHandle = this.connect(this.leftArea, "click", "_popSelItem")
                }
            } else {
                this.leftArea.className = "muiCateSecLeft muiCateSecBtnDis"
//        this.buttonNode.className = "muiCateSecBtn muiCateSecBtnDis"
                this.clearButtonNode.className = "muiCateClearBtn muiCateSecBtnDis"
//        if (this.subHandle) {
//          this.disconnect(this.subHandle)
//          this.subHandle = null
//        }
                if (this.popHandle) {
                    this.disconnect(this.popHandle)
                    this.popHandle = null
                }
                if (this.clearHandle) {
                    this.disconnect(this.clearHandle)
                    this.clearHandle = null
                }
            }
            this.leftArea.innerHTML = Msg["mui.button.count"].replace(
                "%count%",
                childCount
            )
            // topic.publish("/mui/category/selChanged", this, this._calcCurSel())
        },

        _calcCurSel: function () {
            var eCxt = {
                curIds: "",
                curNames: "",
                key: this.key
            }
            if (this.cateSelArr.length > 0) {
                var ids = ""
                var names = ""
                array.forEach(this.cateSelArr, function (selItem) {
                    ids += ";" + selItem.fdId
                    names += ";" + selItem.label
                })
                if (ids != "") {
                    ids = ids.substring(1)
                    names = names.substring(1)
                    eCxt.curIds = ids
                    eCxt.curNames = names
                }
            }
            if (eCxt.curNames == "") {
                eCxt.curNames = window.SEL_CATEGORY_DEFAULT_TITLE;
            }
            return eCxt
        },

    })
    return selection
})
