define([
    "dojo/_base/declare",
    "dojo/topic",
    "dojo/dom-construct",
    "dojo/_base/array",
    "dojo/_base/lang",
    "mui/util",
    "mui/list/HashJsonStoreList",
    "dojo/request",
    "dojo/dom-style",
    "dijit/registry",
    "dojo/dom",
    "dojo/dom-attr",
    "dojo/dom-class",
    "dojo/query",
    "./tagUtil",
    "mui/i18n/i18n!sys-tag",
], function (declare, topic, domConstruct, array, lang, util, HashJsonStoreList, request,
    domStyle, registry, dom, domAttr, domClass, query, tagUtil, tagMsg
) {
    return declare("mui.category.CategoryList", [HashJsonStoreList], {
        //数据请求URL
        dataUrl: "",

        //父分类ID
        parentId: null,

        //选择类型要求
        selType: null,

        //当前值初始
        curIds: null,

        //当前值初始
        curNames: null,

        //单选|多选
        isMul: false,

        baseClass: "muiCateLists",

        //对外事件对应的唯一标示
        key: null,

        //搜索后不允许再往下查看子分类
        showMore: true,

        //不能参与选择的id
        exceptValue: "",

        //字段参数
        fieldParam: null,

        isSearched: false,

        rowsize: 5,

        modelName: "",

        modelId: "",

        tagsRecord: "",

        queryCondition: "",

        TAG_CATEGORY_CHANGE_KEY: '/mui/tag/category/changed',

        TAG_BUILD_RECORD_KEY: '/mui/tag/build/record',

        TAG_EVENT_ADD_VALUE : '/mui/tag/addValue',

        buildRendering: function () {
            this.curNames = tagUtil.decodeHTML(this.curNames);
            this.url = util.urlResolver(this.dataUrl, this)
            this.inherited(arguments)
            this.domNode.className = this.baseClass
            // this.getTagsRecordData(); // 展示使用过的标签 此版本不要求，先注释
        },
        postCreate: function () {
            this.inherited(arguments)
            this.subscribe("/mui/category/changed", "_cateChange")
            // this.subscribe("/mui/search/submit","_cateChange");
            this.subscribe("/mui/search/cancel/back", "_cateReback");
            this.subscribe("/mui/category/selected", "_cateSelected")
            this.subscribe("/mui/cate/navTo", "_scrollToCate")
            this.subscribe("/mui/category/selChanged", "_setCurSel")
            this.subscribe("/mui/view/afterScroll", "_setNavInfo")
            this.subscribe("/mui/search/submit", "dataFresh");
            // 展示使用过的标签 此版本不要求，先注释
            // this.subscribe(this.TAG_BUILD_RECORD_KEY, "buildTagsRecord");
            this.subscribe(this.TAG_CATEGORY_CHANGE_KEY, "dataFresh");
            this.subscribe("/mui/search/cancel", "dataReset");
        },

        dataReset: function (obj, data) {
            if (this.isSearched) {
                this.isSearched = false;
                var index = window.TAG_CATEGORY_LEVEL;
                var evt = window.TAG_CATEGORY_VALUE[index];
                topic.publish("/mui/category/changed", this, evt);
            }
        },

        dataFresh: function (obj, data) {
            var evt = null;
            if (data.keyword) {
                this.isSearched = true;
                evt = {
                    url: data.url,
                }
            }
            if(data.fdId && data.label) {
                evt = {
                    fdId: data.fdId,
                    label: data.label
                }
            }
            if(evt) {
                topic.publish("/mui/category/changed", this, evt);
            }
        },

        resolveItems : function(items) {
            // if(this.tagsRecordBox) this.tagsRecordBox.remove(); // 标签记录
            this._loadOver = false;
            var page = {};
            if (items) {
                if (items['datas']){//分页数据
                    this.listDatas = items['datas'];
                    page = items['page'];
                    if (page) {
                        this.pageno = parseInt(page.currentPage, 10) + 1;
                        this.rowsize = parseInt(page.pageSize, 10);
                        this.totalSize = parseInt(page.totalSize, 10);
                        if(parseInt(page.totalSize || 0, 10) <= (this.pageno-1) * this.rowsize) {
                            this._loadOver = true;
                        }
                    }
                }else{//直接数据,不分页
                    this.listDatas = items;
                    this.totalSize = items.length;
                    this.pageno = 1;
                    this._loadOver = true;
                }
            }

            if (this._loadOver) {
                topic.publish('/mui/list/pushDomHide',this);
            } else {
                topic.publish('/mui/list/pushDomShow',this);
            }
            return this.listDatas;
        },

        resizeTagsRecord: function () {
            if (!this.tagsRecordBox) return;
            var parent = this.getParent();
            if (!parent) return;

            var offsetHeight = parent.domNode.offsetHeight;

            if (offsetHeight < 189 && parent.containerNode) {
                offsetHeight = domStyle.get(
                    parent.containerNode.parentElement,
                    "height"
                );
                if (offsetHeight < 189) {
                    offsetHeight = domStyle.get(
                        parent.containerNode.parentElement.parentElement,
                        "height"
                    );
                }
            }

            // 父元素高度超过一屏幕，则无数据样式只设置189
            var screenHeight = window.globalThis.innerHeight || window.document.documentElement.clientHeight;
            offsetHeight = offsetHeight <= 0 || screenHeight <= offsetHeight ? 189 : offsetHeight; // 如取不到高度则默认189

            var h = offsetHeight - 64;
            h = h < 189? 189 : h;
            domStyle.set(this.tagsRecordBox, {
                height: h-64 + "px"
            });
        },

        buildNoDataItem: function (obj) {
            this.inherited(arguments) // 若需要标签使用情况注释此行
            // if(this.tagsRecordBox) this.tagsRecordBox.remove();
            // if(obj.totalSize == 0) {
            //     this.buildTagsRecord();
            //     this.resizeTagsRecord();
            // }
        },

        buildTagsRecord: function () {
            if(!this.tagsRecord) return;

            this.tagsRecordBox = domConstruct.create("div", {
                className: "muiTagsRecordBox",
            }, this.domNode, "first");
            var prompt = tagMsg["mui.sysTagMain.tags.prompt.nodata"];
            if(window.TAG_CATEGORY_LEVEL == 0) {
                prompt = tagMsg["mui.sysTagMain.tags.category.select"];
            }
            domConstruct.create("div", {
                className: "muiTagsRecordPrompt",
                innerHTML: prompt
            }, this.tagsRecordBox);

            if(this.tagsRecord.hotTags &&
                this.tagsRecord.hotTags.length > 0) {
                var hotTags = this.tagsRecord.hotTags;
                var hotBox = domConstruct.create("div", {
                    className: "muiTagsHotRecord muiTagsDiv",
                }, this.tagsRecordBox);
                domConstruct.create("div", {
                    className: "muiTagsHotRecordTitle",
                    innerHTML: tagMsg["mui.sysTagMain.tags.hot"]+"："
                }, hotBox);
                var content = domConstruct.create("div", {
                    className: "muiTagsHotRecordContent",
                }, hotBox);
                for(var i=0; i < hotTags.length; i++) {
                    var tagStr = hotTags[i];
                    var item = domConstruct.create("div", {
                        className: "muiTagsRecordItem",
                        "data-name": tagStr,
                        innerHTML: util.formatText(tagStr)
                    }, content);
                    this.connect(item, "click", lang.hitch(this,
                        function(e) {
                        var el = e.target;
                        var name = domAttr.get(el, "data-name");
                        topic.publish(this.TAG_EVENT_ADD_VALUE, this, {
                            curIds: tagUtil.GenerateId(),
                            curNames: name,
                        });
                        topic.publish("/mui/category/cancel", this);
                    }))
                }
            }
            if(this.tagsRecord.usedTags &&
                this.tagsRecord.usedTags.length > 0) {
                var usedTags = this.tagsRecord.usedTags;
                var usedBox = domConstruct.create("div", {
                    className: "muiTagsUsedRecord muiTagsDiv",
                }, this.tagsRecordBox);
                domConstruct.create("div", {
                    className: "muiTagsUsedRecordTitle",
                    innerHTML: tagMsg["mui.sysTagMain.tags.used"]+"："
                }, usedBox);
                var content = domConstruct.create("div", {
                    className: "muiTagsUsedRecordContent",
                }, usedBox);
                for(var i=0; i < usedTags.length; i++) {
                    var tagStr = usedTags[i];
                    var item = domConstruct.create("div", {
                        className: "muiTagsRecordItem",
                        "data-name": tagStr,
                        innerHTML: util.formatText(tagStr)
                    }, content);
                    this.connect(item, "click", lang.hitch(this,
                        function(e) {
                        var el = e.target;
                        var name = domAttr.get(el, "data-name")
                        topic.publish(this.TAG_EVENT_ADD_VALUE, this, {
                            curIds: tagUtil.GenerateId(),
                            curNames: name,
                        });
                        topic.publish("/mui/category/cancel", this);
                    }))
                }
            }
        },

        getTagsRecordData: function() {
            var url = "/sys/tag/sys_tag_tags/sysTagTags.do?method=getTagsDialogData";
            url = util.formatUrl(url);
            var promise = request.post(url,{
                handleAs: 'json',
                data: {
                    kind: 'msg',
                    queryCondition: this.queryCondition,
                    modelName: this.modelName,
                }
            });
            var _this = this;
            promise.then(function (items) {
                _this.tagsRecord = items;
            })
        },

        buildQuery: function () {
            var params = this.inherited(arguments);
            var exParams = {exceptValue: this.exceptValue};
            if (this.fieldParam != null && this.fieldParam != "") {
                var fieldParams = this.fieldParam.split(",");
                for (var i = 0; i < fieldParams.length; i++) {
                    var idx = fieldParams[i].indexOf(":");
                    if (idx > 0) {
                        var fieldObj = document.getElementsByName(fieldParams[i].substring(idx + 1));
                        if (fieldObj.length > 0) {
                            var fieldVal = "";
                            if (fieldObj[0].type == "radio" || fieldObj[0].type == "checkbox") {
                                for (var x = 0; x < fieldObj.length; x++) {
                                    if (fieldObj[x].checked) fieldVal += ("" == fieldVal ? "" : ";") + fieldObj[x].value;
                                }
                            } else if (fieldObj[0].tagName == "SELECT") {
                                for (var x = 0; x < fieldObj[0].options.length; x++) {
                                    if (fieldObj[0].options[x].selected) fieldVal = fieldObj[0].options[x].value;
                                }
                            } else {
                                fieldVal = fieldObj[0].value;
                            }
                            if (fieldVal != "") {
                                exParams[fieldParams[i].substring(0, idx)] = fieldVal;
                            }
                        }
                    }
                }
            }
            return lang.mixin(params, exParams);
        },

        reload: function () {
            this.buildLoading()
            this.inherited(arguments)
        },

        startup: function () {
            this.inherited(arguments)
        },

        buildLoading: function () {
            if (this.tmpLoading == null) {
                array.forEach(this.getChildren(), function (child) {
                    child.destroyRecursive()
                })
                this.tmpLoading = domConstruct.create(
                    "li",
                    {
                        className: "muiCateLoading",
                        innerHTML: '<i class="mui mui-loading mui-spin"></i>'
                    },
                    this.domNode, "first"
                )
            }
        },

        onComplete: function () {
            if (this.tmpLoading) {
                domConstruct.destroy(this.tmpLoading)
                this.tmpLoading = null
            }
            this.inherited(arguments)

        },

        _setCurSel: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt) {
                    this.curIds = evt.curIds
                    this.curNames = evt.curNames
                }
            }
        },

        _setNavInfo: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                var chs = this.getChildren()
                var selItem = null
                if (evt.to.y < 0) {
                    var redraw = false
                    for (var i = 0; i < chs.length; i++) {
                        if (
                            chs[i].header == "true" &&
                            0 - evt.to.y >
                            chs[i].domNode.offsetTop - chs[i].domNode.offsetHeight
                        ) {
                            topic.publish("/mui/category/navChange", this, {
                                label: chs[i].getTitle()
                            })
                            redraw = true
                        }
                    }
                    if (!redraw) topic.publish("/mui/category/navChange", this, null)
                } else {
                    topic.publish("/mui/category/navChange", this, null)
                }
            }
        },

        //滚动到header
        _scrollToCate: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt) {
                    var chs = this.getChildren()
                    var selItem = null
                    for (var i = 0; i < chs.length; i++) {
                        if (chs[i].header == "true" && chs[i].label == evt.label) {
                            selItem = chs[i]
                            break
                        }
                    }
                    if (selItem) {
                        topic.publish("/mui/view/scrollTo", this, {
                            y: 0 - selItem.domNode.offsetTop
                        })
                    }
                }
            }
        },

        //搜索后返回
        _cateReback: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (this._addressUrl) {
                    this.url = this._addressUrl
                }

                //清空侧边导航栏数据
                topic.publish("/mui/category/clearNav", this)

                //TODO  loading
                this.showMore = true
                this.buildLoading()
                this.reload()
            }
        },

        //往下查看数据
        _cateChange: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (evt && evt.url) {
                    if (!this._addressUrl) {
                        this._addressUrl = this.url
                    }
                    this.showMore = false
                    this.url = evt.url
                } else {
                    this.showMore = true
                    var fdId = "";
                    if(evt && evt.fdId) {
                        fdId = evt.fdId;
                    }
                    this.parentId = fdId;
                    this.url = util.urlResolver(util.formatUrl(this.dataUrl), this)
                }
                //清空侧边导航栏数据
                topic.publish("/mui/category/clearNav", this)

                this.buildLoading()
                this.reload()
            }
        },

        //选中
        _cateSelected: function (srcObj, evt) {
            if (srcObj.key == this.key) {
                if (!this.isMul) {
                    this.curIds = evt.fdId
                    this.curNames = evt.label
                    topic.publish("/mui/category/clearSelected", this, {fdId: evt.fdId})
                    if (!this.confirm) {
                        topic.publish("/mui/category/submit", this, {
                            key: this.key,
                            curIds: evt.fdId,
                            curNames: evt.label
                        })
                    }
                }
            }
        }
    })
})
