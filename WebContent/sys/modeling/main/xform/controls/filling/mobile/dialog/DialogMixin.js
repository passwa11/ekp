define(["dojo/_base/declare", "dojo/_base/lang", "dojo/query", "dojo/dom-construct",
        "sys/modeling/main/xform/controls/filling/mobile/_DialogCategoryBase"
        ,"mui/dialog/Tip","mui/util","dojo/request","dijit/_WidgetBase","sys/modeling/main/xform/controls/filling/mobile/FillingEventBase"],
    function (declare, lang, query, domConstruct, _DialogCategoryBase,Tip,util,request,_WidgetBase,FillingEventBase) {

        var DialogMixin = declare("sys.modeling.main.xform.controls.filling.mobile.dialog.DialogMixin", [_WidgetBase,_DialogCategoryBase,FillingEventBase], {
            //列表数据获取URL
            listDataUrl: null,
            //详细数据获取url
            detailUrl: null,
            //搜索获取URL
            searchDataUrl: null,
            //字段参数
            fieldParam: null,

            isMul: false,

            templURL: "sys/modeling/main/xform/controls/filling/mobile/filling_mul.jsp",

            title: "",

            //绑定事件
            bindEvent:null,

            widgetId:null,
            controlId:null,

            startup:function (){
                this.inherited(arguments);
                this.subscribe("/mui/category/submit", lang.hitch(this, "returnDialog"))
            },

            buildRendering: function () {
                this.inherited(arguments);
            },

            _setIsMulAttr: function (mul) {
                this._set('isMul', mul);
                this.templURL = "sys/modeling/main/xform/controls/filling/mobile/filling_mul.jsp";
            },

            returnDialog: function(srcObj, evt) {
                if (evt) {
                    if (srcObj.key == this.key) {
                        this.closeDialog(srcObj)
                        if (this.afterSelect) {
                            this.afterSelect(srcObj,evt);
                        }
                    }
                }
            },
        });
        return DialogMixin;
    });