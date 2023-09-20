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
    "./_DialogCategoryBase",
    "./tagUtil"
], function (declare, topic, dom, domConstruct, domStyle, domClass, lang, html, array,
             util, touch, _CategoryBase, tagUtil) {

    // window.TAG_TYPE_CATEGORY = 0; //"CATEGORY" 类别
    //
    // window.TAG_TYPE_TEMPLATE = 1; //"TEMPLATE" 模板

    window.TAG_TYPE_DOCUMENT = 2; //"DOC" 文档

    var dialogMixin =  declare("mui.tag.DialogMixin", [_CategoryBase], {
        //列表数据获取URL
        listDataUrl: "",
        //详细数据获取url
        detailUrl: "",
        //搜索获取URL
        searchDataUrl: "",
        //字段参数
        fieldParam: "",

        isMul: false,

        templURL: "",

        title: "",

        primaryKey: "",

        modelName: "",

        modelId: "",

        queryCondition: "",

        fdKey: "",

        isClick: false,

        buildRendering: function () {
            this.inherited(arguments);
        },

        clearClick: function(){
            this.defer(function () {
                this.isClick = false;
            }, 350);
        },

        _setIsMulAttr: function (mul) {
            this._set('isMul', mul);
            if (mul == "true" || mul == true) {
                this.templURL = "sys/tag/mobile/import/js/tmpl/dialog_mul.jsp";
            } else {
                this.templURL = "sys/tag/mobile/import/js/tmpl/dialog_sgl.jsp";
            }
        },

        _selectCate: function () {
            if(this.isClick) return
            this.isClick = true;
            this.clearClick();
            this.inherited(arguments);
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
                        _self.curNames = tagUtil.encodeHTML(_self.curNames);
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
    });

    // var exports = {
    //     address : function(mulSelect, idField, nameField, selectType,
    //                        action) {
    //         var isMul = this.isMul == "true" || this.isMul == true ? true: false
    //         var required = this.required == "true" || this.required == true ? true: false
    //         var addressObj = new _CategoryBase();
    //         addressObj.templURL = (mulSelect == true ? "sys/tag/mobile/import/js/tmpl/dialog_mul.jsp"
    //             : "sys/tag/mobile/import/js/tmpl/dialog_sgl.jsp");
    //         addressObj.key = idField;
    //         addressObj.type = window.SYS_CATEGORY_TYPE_DOCUMENT;
    //         addressObj.listDataUrl = this.listDataUrl;
    //         addressObj.searchDataUrl = this.searchDataUrl;
    //         addressObj.title = this.title;
    //         addressObj.align = this.align;
    //         addressObj.isMul = isMul;
    //         addressObj.fieldParam = this.fieldParam;
    //         addressObj.required = required;
    //         addressObj.queryCondition = this.queryCondition;
    //         addressObj.modelId = this.modelId;
    //         addressObj.modelName = this.modelName;
    //         var idObj = query("[name='" + idField + "']")[0];
    //         var nameObj = query("[name='" + nameField + "']")[0];
    //         addressObj.curIds = idObj.value;
    //         addressObj.curNames = nameObj.value;
    //         addressObj.afterSelect = function(obj) {
    //             idObj.value = obj.curIds;
    //             nameObj.value = obj.curNames;
    //             if (action) {
    //                 action(obj);
    //             }
    //         };
    //         addressObj.eventBind();
    //         addressObj._selectCate();
    //     }
    // };

    // return lang.mixin(dialogMixin, exports);
    return dialogMixin;
});