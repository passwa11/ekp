define(['dojo/_base/declare', "dojo/_base/lang",
        "dojo/dom-style", "dojo/parser",
        "dojo/dom-class",
        "dojo/_base/array",
        "dojo/request",
        'dojo/topic',
        "mui/util"],
    function (declare, lang,
              domStyle, parser,
              domClass,
              array,
              req,
              topic,
              util) {
        window.SIMPLE_CATEGORY_TYPE_CATEGORY = 0;
        return declare('km.calendar.mobile.resource.js.label.LabelCategoryListMixin', null, {

            property: '',

            myCateSelArr: [],

            tmpl:
            // 滑动
                '<div data-dojo-type="dojox/mobile/ScrollableView" ' +
                'data-dojo-mixins="mui/category/AppBarsMixin"' +
                "data-dojo-props=\"scrollBar:false,threshold:100,key:'{key}'\">" +
                // 列表
                '<ul data-dojo-type="mui/listcategory/ListCategoryList" ' +
                'data-dojo-mixins="km/calendar/mobile/resource/js/label/LabelCategoryItemListMixin,!{mixin}" ' +
                "data-dojo-props=\"lazy:false,isMul:{isMul},dataUrl:'{dataUrl}',key:'{key}',parentId:'!{fdId}',selType:{selType},modelName:'{modelName}',authType:'{authType}',curIds:'{curIds}',confirm:{confirm},index:{index}\" >" +
                "</ul>" +
                "</div>",

            startup: function () {
                if (this._started) {
                    return;
                }
                this.inherited(arguments);
                this._started = true;
                // 监听本类发布的“选中”和“取消”事件
                this.subscribe("/mui/category/selected", "_selected");
                this.subscribe("/mui/category/unselected", "_unselected");
                //提交
                this.subscribe("/mui/category/submit", "_returnDialog");
            },

            generateList: function (evt) {
                window.LABEL_INIT = true;
                this.inherited(arguments);
            },

            _selected: function (srcObj, evt) {
                if (srcObj.key == this.key) {
                    if (evt) {
                        this.reSetLabelArr({fdId: evt.fdId, selectedFlag: true});
                    }
                }
            },
            _unselected: function (srcObj, evt) {
                if (srcObj.key == this.key) {
                    if (evt) {
                        //去掉evt.fdId
                        this.reSetLabelArr({fdId: evt.fdId, selectedFlag: false});
                    }
                }
            },

            _returnDialog: function (srcObj, evt) {
                if (evt) {
                    if (srcObj.key == this.key) {
                        // 批量更新
                        req(util.formatUrl("/km/calendar/km_calendar_label/kmCalendarLabel.do?method=batchUpdateLabel2Selected"), {
                            handleAs: 'json',
                            method: 'post',
                            data: {
                                myCateSelArr: JSON.stringify(this.myCateSelArr)
                            }
                        }).then(lang.hitch(this, function (results) {
                            // 重新加载数据
                            require(['dojo/dom', 'dijit/registry'], function(dom, registry) {
                                var widget = registry.byNode(dom.byId('myCalendarContent'));
                                widget.reload();
                            });
                            //关闭弹框
                            this._closeDialog(srcObj, evt);
                        }));
                    }
                }
            },

            // 关闭弹窗
            _closeDialog: function () {
                // 发布关闭弹窗事件
                topic.publish("km/calendar/LabelSelect/closeDialog", this.myCateSelArr);
            },

            //设置label的选中状态
            reSetLabelArr: function (labelJson) {
                var existFlag = false;
                for (var i = 0; i < this.myCateSelArr.length; i++) {
                    var vv = this.myCateSelArr[i];
                    //存在则更新
                    if (vv.fdId == labelJson.fdId) {
                        vv.selectedFlag = labelJson.selectedFlag;
                        existFlag = true;
                        break;
                    }
                }
                //不存在则添加
                if (!existFlag) {
                    this.myCateSelArr.push(labelJson);
                }
            }
        });

    });