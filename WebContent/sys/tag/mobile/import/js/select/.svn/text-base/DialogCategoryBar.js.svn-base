define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "./DialogSelCategory",
    "dojo/dom-construct",
    "dojo/_base/lang",
    "dojo/topic",
    "dojo/_base/array",
    "mui/createUtils",
    "dojo/parser",
    "dojo/dom",
    "dijit/registry",
    "dojo/dom-style",
    "mui/i18n/i18n!sys-tag",
], function(declare, widgetBase, Select, domConstruct, lang, topic, array, createUtils, parser,
            dom, registry, domStyle, tagMsg) {
    var template = createUtils.createTemplate;

    var _field = declare( "mui.tag.DialogSelCategory", [ widgetBase ], {

        SELECT_CALLBACK : 'mui/form/select/callback',
        LIST_LOADED_KEY : '/mui/list/loaded',
        CATEGORY_CHANGE_KEY : '/mui/category/changed',
        TAG_CATEGORY_CHANGE_KEY : '/mui/tag/category/changed',

        option:[],

        parentIndex: "",

        isSleep: false,

        startup : function() {
            this.inherited(arguments);
        },

        buildRendering: function () {
            this.inherited(arguments);
            window.TAG_CATEGORY_LEVEL = 0;

            window.TAG_CATEGORY_VALUE = [];
            this.subscribe(this.SELECT_CALLBACK, function(evt) {
                if(!this.isSleep) {
                    window.TAG_CATEGORY_LEVEL ++;
                    this.isSleep = true;
                    var data = {
                        fdId: evt.value,
                        label: evt.text,
                    }
                    var index = window.TAG_CATEGORY_LEVEL;
                    window.TAG_CATEGORY_VALUE[index] = data;
                    topic.publish(this.TAG_CATEGORY_CHANGE_KEY, this, data);
                    this.defer(function () {
                        this.isSleep = false;
                    }, 350);
                }
            })
            this.subscribe(this.LIST_LOADED_KEY, 'buildSelect');
            this.buildSelect();
        },
        buildSelect: function (obj, args) {
            var data = [];
            var searchBar = dom.byId("tag_DialogSearchBar");
            if(args && args.categorys) {
                data = args.categorys;
                this.select.destroy();
            }
            if(searchBar) {
                if(data.length > 0 ) {
                    domStyle.set(this.domNode, "display", "inline-block");
                    domStyle.set(searchBar, "width", "50%");
                } else {
                    domStyle.set(this.domNode, "display", "none");
                    domStyle.set(searchBar, "width", "100%");
                }
            }
            // 测试数据
            // for(var i=0; i<20; i++) {
            //     data.push({
            //         value: i,
            //         text: "标题"+i,
            //         callback: function() {
            //         }
            //     })
            // }
            this.option = data;
            var select = template('div', {
                id: "muiTagDialogTagSelect",
                className: "muiDialogTagSelect",
                dojoType: 'sys/tag/mobile/import/js/select/DialogSelCategory',
                dojoProps: {
                    store: this.option,
                    mul: true,
                    showPleaseSelect: true,
                    subject: tagMsg['mui.sysTagMain.tags.category.subject']
                }
            });
            var element = domConstruct.toDom(select);

            domConstruct.place(element, this.domNode);
            parser.parse({
                rootNode: dom.byId("tag_DialogCategorySelect")
            })
            this.select = registry.byId("muiTagDialogTagSelect");
        }
    });
    return _field;
});
