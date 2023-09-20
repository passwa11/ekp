define(['dojo/_base/declare', 'dijit/_WidgetBase', 'mui/form/_StoreFormMixin', 'dojo/topic', "dojo/_base/lang",
        'dojo/dom-construct', "mui/form/_CategoryBase", 'mui/i18n/i18n!km-calendar:mui.kmCalendar.label'],
    function (declare, WidgetBase, _StoreFormMixin, topic, lang, domConstruct, CategoryBase, msg) {

        return declare('km.calendar.mobile.resource.js.LabelSelect', [WidgetBase, _StoreFormMixin, CategoryBase], {

            templURL: "/km/calendar/mobile/resource/tmpl/label.jsp",

            prefix: '_LabelSelectItem_',

            key: 'label',

            buildRendering: function () {
                this.inherited(arguments);

                var muiCalendarGroupBox = domConstruct.create('div', {
                    className: 'muiCalendarGroupBox'
                }, this.domNode);

                //右侧筛选日程label标签-按钮
                var muiCalendarGroupRight = domConstruct.create('div', {
                    className: 'muiCalendarGroupRight',
                    innerHTML: msg['mui.kmCalendar.label']
                }, muiCalendarGroupBox);
                this.connect(muiCalendarGroupRight, 'touchend', function () {
                    this.defer(function () {
                        this._selectCate();
                    }, 350);
                });

                // 监听弹窗关闭事件
                topic.subscribe("km/calendar/LabelSelect/closeDialog", lang.hitch(this, "__closeDialog"));
            },

            // 关闭弹窗
            __closeDialog: function () {
                this._working = false;
                // 删除弹窗组件和元素
                require(['dojo/dom', 'dojo/query', 'dijit/registry', "dojo/dom-construct"], function (dom, query, registry, domConstruct) {
                    var _label = dom.byId('__cate_dialog_label');
                    query('*[widgetid]', _label).forEach(function (widgetDom) {
                        var widget = registry.byNode(widgetDom);
                        if (widget && widget.destroy) {
                            widget.destroy();
                        }
                    });
                    domConstruct.destroy(_label);
                });
            }

        });
    });