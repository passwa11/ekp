define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/_base/window",
    "mui/tabbar/TabBarButton",
    "./OperTabBarButtonMixin"
], function(declare, lang, win, TabBarButton, OperTabBarButtonMixin) {

    return declare("sys.modeling.main.resources.js.mobile.formview.OperButton", [TabBarButton, OperTabBarButtonMixin], {

        key: '_cateSelect',

        createUrl:'',

        eventBind: function() {
            this.subscribe("/mui/category/submit", lang.hitch(this, "returnDialog"));
            this.subscribe("/mui/category/cancel", lang.hitch(this, "closeDialog"));
            this.subscribe("/mui/category/clear", lang.hitch(this, "clearDialog"));
        },

        buildRendering : function() {
            this.inherited(arguments);
        },

        postCreate : function() {
            this.inherited(arguments);
            this.eventBind();
        },

        returnDialog:function(srcObj , evt){
            this.inherited(arguments);
            if(srcObj.key == this.key){
                this.defer(function(){
                    this.afterSelectCate(evt);
                },1);

            }
        },

        startup : function() {
            if (this._started)
                return;
            this.inherited(arguments);
        }
    });
});