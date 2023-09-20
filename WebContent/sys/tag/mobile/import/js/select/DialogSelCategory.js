define([
    "dojo/_base/declare",
    "mui/form/Select",
    "dojo/dom-construct",
    "dojo/_base/lang",
    "dojo/topic",
    "dojo/_base/array",
    "./CategoryDialog",
    "mui/util",
    "dojo/dom-class",
    "mui/i18n/i18n!sys-mobile",
], function(declare, Select, domConstruct, lang, topic, array,
    CategoryDialog, util, domClass, msg) {
    var selectNode = declare( "mui.tag.DialogSelCategory", [ Select ], {

        store: [],

        name: 'category',

        mul: false,

        renderListItem : function(contentNode) {
            var values = this.value.split(';');
            array.forEach(this.values, lang.hitch(this,
            function(value, index) {
                value.selected = false;
                array.forEach(values, function(v) {
                    if (String(v) == String(value.value)) {
                        value.selected = true;
                        return;
                    }
                });
                var item = this.createListItem(value);
                contentNode.appendChild(item);
            }));
        },

        createListItem : function(props) {
            var itemRenderer = this.itemRenderer;
            var propsText = (typeof(props.text)!="undefined" && props.text!=null) ? util.formatText(props.text.toString().replace("'","&#039;")) : "";
            var propsValue = (typeof(props.value)!="undefined" && props.value!=null) ? util.formatText(props.value.toString()) : "";
            itemRenderer = itemRenderer.replace('!{text}',propsText);
            itemRenderer = itemRenderer.replace('!{checked}', props.selected);
            itemRenderer = itemRenderer.replace('!{value}',propsValue);
            itemRenderer = itemRenderer.replace('!{mul}',this.mul);
            itemRenderer = itemRenderer.replace('!{valueField}',this.valueField);
            itemRenderer = itemRenderer.replace('!{pop}',this.pop);
            var item = domConstruct.toDom(itemRenderer);
            return item;
        },

        _onClick : function(evt) {
            if (this.dialog)
                return;
            this.contentNode = domConstruct.create('div', {
                className : 'muiCheckBoxPopWarp'
            });
            var listNode = domConstruct.create('ul', {
                className : 'muiRadioGroupPopList'
            },this.contentNode);
            this.renderListItem(listNode);
            var buttons = [];
            if (this.mul) {
                buttons = [
                    {
                        title : msg['mui.button.clear'], // 清空
                        fn :  lang.hitch(this, this._clearClick)
                    },
                    {
                        title : "", // 取消
                    },
                    {
                        title : msg['mui.button.ok'], // 确定
                        fn :  lang.hitch(this, this._DoneClick)
                    },
                ];
            }
            this.dialog = CategoryDialog.element({
                canClose : false,
                element : this.contentNode,
                buttons : buttons,
                position:'bottom',
                scrollable : false,
                parseable : true,
                showClass : 'muiFormSelect',
                callback : lang.hitch(this, function() {
                    topic.publish(this.SELECT_CALLBACK, this);
                    this.dialog = null;
                }),
                onDrawed:lang.hitch(this, function(evt) {
                    this.resizeTop(evt);
                    if(this.mul){
                        this.value_s=[];
                        var values = this.value.split(";");
                        for(var i=0;i<values.length;i++){
                            if(values[i]!='')
                                this.value_s.push(values[i]);
                        }
                    }
                })
            });
        },
        // renderListItem: function() {
        //     array.forEach(this.values, function(value) {
        //         var node = domConstruct.create( "div", {
        //             className: "muiTagDialogButton", innerHTML: value.text
        //         },this.contentNode);
        //
        //         this.connect(node, "click", lang.hitch(this, function() {
        //             topic.publish(this.CHECK_CHANGE, this, {
        //                 value: String(value.value),
        //                 name: this.selectBoxPrefix + this.valueField
        //             });
        //         }))
        //
        //     }, this);
        // },
    });
    return selectNode;
});
