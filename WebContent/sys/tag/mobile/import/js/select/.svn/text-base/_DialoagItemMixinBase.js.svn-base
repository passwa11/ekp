define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/topic",
    "dojox/mobile/_ItemBase",
    "mui/util",
    "mui/history/listener",
    "mui/iconUtils",
    "mui/i18n/i18n!sys-tag",
    "dijit/registry",
], function (declare, domConstruct, domClass, topic, ItemBase,
             util, listener, iconUtils, tagMsg, registry) {
    var item = declare("mui.category.CategoryItemMixin", [ItemBase], {
        fdId: "",

        //名称
        label: "",

        // 文档附件类型
        icon: "",

        //组织架构,类别,模板类型
        type: null,

        //是否是分类
        header: "false",

        tag: "li",

        //事件key
        key: null,

        buildRendering: function () {
            this._templated = !!this.templateString
            var isNodeNull =
                this.domNode == null &&
                this.containerNode == null &&
                this.srcNodeRef == null
            if (!this._templated && isNodeNull) {
                this.domNode = this.containerNode =
                    this.srcNodeRef ||
                    domConstruct.create(this.tag, {
                        className: "muiCateItem fadeIn animated"
                    })
                var className = "muiCateInfoItem"
                if (this.header == "true") {
                    className = " muiGroupItem"
                }
                this.contentNode = domConstruct.create(
                    "div",
                    {
                        className: className
                    },
                    this.domNode
                )
            }
            this.parentNode = registry.byId("tag_DialogItemListMixin");

            if(this.parentNode && this.parentNode.showTagInfo) {
                this.showTagInfo = this.parentNode.showTagInfo;
            }

            this.inherited(arguments)

            if (!this._templated) this._buildItemBase()
        },

        postCreate: function () {
            this.inherited(arguments)
            this.subscribe("/mui/category/cancelSelected", "_cancelSelected")
            this.subscribe("/mui/category/setSelected", "_setSelected")
            this.subscribe("/mui/category/commonSelected", "commonSelected")
            this.subscribe("/mui/category/commonUnSelected", "commonUnSelected")
        },

        //构建基本框架
        _buildItemBase: function () {

            this.cateContainer = domConstruct.create(
                "div",
                {className: "muiCateContainer"},
                this.contentNode
            )
            if (this.type != 0) {
                this.iconNode = domConstruct.create(
                    "div",
                    {
                        className: "muiCateIcon"
                    },
                    this.cateContainer
                )
                this.buildIcon(this.iconNode)
                if (this.fdIsAvailable === false) {
                    this.availableNode = domConstruct.create(
                        "div",
                        {
                            className: "muiAddressIsAvailableFalse"
                        },
                        this.iconNode
                    )
                }

                this.infoNode = domConstruct.create(
                    "div",
                    {
                        className: "muiCateInfo"
                    },
                    this.cateContainer
                )

                this.titleNode = domConstruct.create("div",
                    {
                        className: "muiCateName",
                        innerHTML: util.formatText(this.label)
                    },
                    this.infoNode
                )

                // 显示标签信息：分类、引用次数
                if(this.showTagInfo) {
                    this.cateBoxNode = domConstruct.create("div", {
                        className: "muiCateBox"
                    }, this.infoNode)

                    this.cateMsgRightNode = domConstruct.create("div", {
                        className: "muiCateMsgCite",
                        innerHTML: "<span>"+tagMsg['mui.sysTagMain.tags.fdQuoteTimes']+"：</span><span>"+this.quoteNum+"</span>"
                    }, this.cateBoxNode)

                    this.cateMsgLeftNode = domConstruct.create("div", {
                        className: "muiCateMsgClassify",
                        innerHTML: "<span>"+tagMsg['mui.sysTagMain.tags.category']+"：</span><span>"+util.formatText(this.cateName)+"</span>"
                    }, this.cateBoxNode)
                }

                this.connect(this.iconNode, "click", "_selectCate")
                this.connect(this.infoNode, "click", "_selectCate")
            }
            // else {
            //     this.titleNode = domConstruct.create(
            //         "div",
            //         {
            //             className: "muiCateName muiCateTitle",
            //             innerHTML: this.getTitle()
            //         },
            //         this.cateContainer
            //     )
            // }
            this.moreArea = domConstruct.create(
                "div",
                {className: "muiCateMore"},
                this.cateContainer
            )
        },

        startup: function () {
            if (this._started) {
                return
            }
            this.inherited(arguments)
            var parent = this.getParent()
            this.key = parent.key
            if (this.type != 0) {
                if (parent.showMore && this.showMore()) {
                    // 构建更多
                    if (this.moreArea.childElementCount == 0) {
                        domConstruct.create(
                            "i",
                            {className: "mui mui-forward"},
                            this.moreArea
                        )
                    }
                    this.connect(this.moreArea, "click", "_openCate")
                } else {
                    domConstruct.destroy(this.moreArea)
                }

                if (parent.selType != null) {
                    //构建选择区域
                    if (this.showSelect()) {
                        this.selectArea = domConstruct.create(
                            "div",
                            {
                                className: "muiCateSelArea"
                            },
                            this.cateContainer,
                            "first"
                        ) //用于占位
                        this.selectNode = domConstruct.create(
                            "div",
                            {
                                className: "muiCateSel"
                            },
                            this.selectArea
                        )

                        var pWeiget = this.getParent()
                        if (pWeiget.isMul) {
                            domClass.add(this.selectNode, "muiCateSelMul")
                        }

                        if (this.isSelected()) {
                            this.checkedIcon = domConstruct.create(
                                "i",
                                {
                                    className: "mui mui-checked muiCateSelected"
                                },
                                this.selectNode
                            )
                            domClass.add(this.selectNode, "muiCateSeled")
                        }
                        domClass.add(this.domNode, "muiCateHasSel")
                        this.connect(this.selectArea, "click", "_selectCate")
                    }
                }
            } else {
                topic.publish("/mui/category/addNav", this, {label: this.label})
            }
        },
        _openCate: function (evt) {
            evt && evt.stopPropagation()
            topic.publish("/mui/category/changed", this, {
                fdId: this.fdId,
                label: this.label
            })
            return
        },

        _cancelSelectedTrigger: function (evt) {
            topic.publish("/mui/category/unselected", this, {
                label: this.label,
                fdId: this.fdId,
                icon: this.icon,
                type: this.type
            })
            topic.publish("/mui/category/cate_unselected", this, {
                label: this.label,
                fdId: this.fdId,
                icon: this.icon,
                type: this.type
            })
        },

        _cancelSelected: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt && evt.fdId) {
                    if (evt.fdId.indexOf(this.fdId) > -1) {
                        if (this.checkedIcon) {
                            domClass.remove(this.selectNode, "muiCateSeled")
                            domConstruct.destroy(this.checkedIcon)
                            this.checkedIcon = null
                            this._cancelSelectedTrigger(evt)
                        }
                    }
                }
            }
        },

        commonUnSelected: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt && evt.fdId) {
                    if (evt.fdId.indexOf(this.fdId) > -1) {
                        if (this.checkedIcon) {
                            domClass.remove(this.selectNode, "muiCateSeled")
                            domConstruct.destroy(this.checkedIcon)
                            this.checkedIcon = null
                        }
                    }
                }
            }
        },

        commonSelected: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt && evt.fdId) {
                    if (evt.fdId == this.fdId) {
                        domClass.add(this.selectNode, "muiCateSeled")
                        if (!this.checkedIcon) {
                            this.checkedIcon = domConstruct.create(
                                "i",
                                {
                                    className: "mui mui-checked muiCateSelected"
                                },
                                this.selectNode
                            )
                        }
                    }
                }
            }
        },

        _setSelectedTrigger: function () {
            topic.publish("/mui/category/selected", this, {
                label: this.label,
                fdId: this.fdId,
                icon: this.icon,
                type: this.type
            })
            topic.publish("/mui/category/cate_selected", this, {
                label: this.label,
                fdId: this.fdId,
                icon: this.icon,
                type: this.type
            })
        },

        _setSelected: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt && evt.fdId) {
                    if (evt.fdId == this.fdId) {
                        if (this.checkedIcon) {
                            domConstruct.destroy(this.checkedIcon)
                            this.checkedIcon = null
                        }

                        if (!this.selectNode) {
                            return
                        }

                        if (!domClass.contains(this.selectNode, "muiCateSeled")) {
                            domClass.add(this.selectNode, "muiCateSeled")
                        }

                        this.checkedIcon = domConstruct.create(
                            "i",
                            {
                                className: "mui mui-checked muiCateSelected"
                            },
                            this.selectNode
                        )
                        this._setSelectedTrigger(evt)
                    }
                }
            }
        },

        startTime: 0,

        _toggleSelect: function (select) {
            if (select) {
                this._setSelected(this, this)
            } else {
                this._cancelSelected(this, this)
            }
            var list = this.getParent();

        },

        _selectCate: function (evt) {
            if (evt) {
                if (evt.stopPropagation) evt.stopPropagation()
                if (evt.cancelBubble) evt.cancelBubble = true
                if (evt.preventDefault) evt.preventDefault()
                if (evt.returnValue) evt.returnValue = false
            }

            /* 连续点击不能超过500毫秒，防止快速双击
            （click事件未知原因会连续触发两次，下面的的 nowTime、clickTime的条件判断只是为了防止一次点击多次处理，是一种变通的方式来防止双击）*/
            var nowTime = new Date().getTime();
            var lastClickTime = this.ctime;
            if ((lastClickTime != 'undefined' && (nowTime - lastClickTime < 500)) || this.selectCateCalling) {
                return false;
            } else {
                this.ctime = new Date().getTime();
                this.selectCateCalling = true;

                if (this.selectNode) {
                    //存在选择区域时设置是否选中
                    if (this.checkedIcon != null) {
                        this._toggleSelect(false)
                    } else {
                        this._toggleSelect(true)
                    }
                } else {
                    var parent = this.getParent()
                    if (this.showMore() && parent.showMore) {
                        this._openCate()
                    } else {
                        this.showItemDetail()
                    }
                }
                this.ctime = new Date().getTime();
                this.selectCateCalling = false;
                return true;
            }
        },

        //获取分组标题信息
        getTitle: function () {
            return this.label
        },

        //是否显示往下一级
        showMore: function () {
            return true
        },

        //是否显示选择框
        showSelect: function () {
            return true
        },

        //是否选中
        isSelected: function () {
            return true
        },

        showItemDetail: function () {
        },

        buildIcon: function (iconNode) {
            if (this.icon) {
                iconUtils.setIcon(this.icon, null, this._headerIcon, null, iconNode)
            }
        },

        _setLabelAttr: function (text) {
            if (text) this._set("label", text)
        }
    })
    return item
})
