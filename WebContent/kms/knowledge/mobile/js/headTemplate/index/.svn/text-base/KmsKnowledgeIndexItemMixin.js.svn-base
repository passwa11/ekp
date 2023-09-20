define(
    [ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
        "dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
        "mui/list/item/_ListLinkItemMixin", "mui/util",
        "dojo/html",
    ],
    function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
             _ListLinkItemMixin, util,html) {
        var item = declare(
            "mui.knowledge.list.KmsKnowledgeIndexItemMixin",
            [ ItemBase],
            {
                tag : "div",

                buildRendering : function() {
                    this.inherited(arguments);
                    this.buildInternalRender();
                },
                buildInternalRender : function() {

                    var self = this;
                    var dhs = new html._ContentSetter({
                        parseContent : true,
                        onBegin : function() {
                            this.inherited("onBegin", arguments);
                        }
                    });
                    var container = domConstruct.create('div', {
                        className : 'kmsKnowledgeIndexMainBody'
                    }, this.domNode, 'last');

                    // 设置容器样式
                    domStyle.set(container,"padding","0 1.25rem");
                    domStyle.set(container,"margin-top","1rem");

                    dhs.node = container;
                    dhs.set(this.allCount + this.myCount + this.rank);
                    dhs.tearDown();
                },

                startup : function() {
                    this.inherited(arguments);
                },

                _setLabelAttr : function(text) {
                    if (text)
                        this._set("label", text);
                }
            });
        return item;
    });