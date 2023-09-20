define(	["dojo/_base/declare",
    "dojox/mobile/_ItemBase",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/dom-style",
    "mui/util",
    "mui/list/item/_ListLinkItemMixin",
    "mui/list/item/DocStatusMixin", "mui/list/item/TopMixin",
    "mui/list/item/IntroduceMixin","mui/i18n/i18n!sys-mobile:mui.item.views"], function(
    declare, ItemBase, domConstruct, domClass, domStyle, util, _ListLinkItemMixin, IntroduceMixin, TopMixin, DocStatusMixin,msg) {

    // 没图（返回流都是空的）别用左图右文啊，建议去使用TextItemListMixin.js

    return declare("mui.list.item.ComplexLItemListItem", [ItemBase, _ListLinkItemMixin, IntroduceMixin, TopMixin, DocStatusMixin], {

        // 标题
        label : '',
        // 概括
        summary : '',
        /**
         *  文档状态状态
         *	传入的数据源请不要带<div>,<span>之类的标签
         *  并且格式请统一，如:文档状态传入:00,10,20,30等，DocStatusMixin会进行转换
         */
        status : '',
        // 标签默认样式
        tagClass : 'muiComplexLStatus muiFontSizeXS',
        /**
         * 是否置顶，传入true/false，
         */
        isTop : '',
        /**
         * 是否精华，传入true/false
         */
        isIntroduce : '',
        // 创建时间
        created : '',
        // 创建者
        creator : '',
        tag : 'li',
        // 图片url
        icon : '',
        // 链接
        href : 'javascript:;',
        // 主键
        fdId : '',
        // 创建者
        creator : '',
        // 创建时间
        created : '',
        // 数量
        count : 0,

        buildRendering : function() {
            this._templated = !!this.templateString;
            if (!this._templated) {
                this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create(this.tag, {
                    className : 'muiComplexLItem clearfix'
                });
                this.contenNode = domConstruct.create('div', {
                    className : 'muiComplexLBox'
                }, this.containerNode);
            }
            this.inherited(arguments);
            if (!this._templated)
                // 构建内部元素
                this.buildInternalRender();
            if(this.contentNode)
                domConstruct.place(this.contentNode,this.domNode);
        },

        buildInternalRender : function() {

            if (this.href) {
                this.makeLinkNode(this.contenNode);
            }

            this.buildLeft();

            this.buildRight();

        },

        buildLeft : function() {

            this.buildImg();

            this.buildStatus();

        },

        buildRight : function() {

            this.muiComplexRight = domConstruct.create('div', {
                className : 'muiComplexLRight'
            }, this.contenNode);

            this.buildLabel();
            this.buildSummary();
            this.buildTips();

        },

        buildImg : function() {

            this.muiComplexLeft = domConstruct.create('div', {
                className : 'muiComplexLLeft'
            }, this.contenNode);

            if (this.icon) {

                var imgUrl = util.formatUrl(this.icon);

            } else {

                // 默认图
                var imgUrl = util.formatUrl('/sys/mobile/css/themes/default/images/complxItemDefaultIcon.png');

            }

            domStyle.set(this.muiComplexLeft, {
                'background' : 'url(' + imgUrl + ') center no-repeat',
                'background-size' : '100%'
            });
        },


        buildStatus : function() {

            // 这是临时方案，兼容数据源带标签和样式的情况，之后的数据源请不要传入
            if(this.muiComplexLeft && this.status && this.status.indexOf('<') > -1){
                domConstruct.create('span', {
                    className : this.tagClass,
                    innerHTML : this.status
                }, this.muiComplexLeft);
            } else {
                this.buildTag(this.muiComplexLeft);
            }
        },

        buildLabel : function() {
            if (this.label) {
                domConstruct.create('div', {
                    className : 'muiFontSizeMS',
                    innerHTML:'<div style="text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">'+this.label+'<div/>'
                }, this.muiComplexRight);
            }
        },

        buildSummary : function() {
            if (this.summary) {
                domConstruct.create('span', {
                    className : 'summary-cut',
                    innerHTML : this.summary
                }, this.muiComplexRight);
            }
        },

        buildTips : function() {

            if(!this.muiComplexRight)
                return;

            var muiComplexLTipsLeft = domConstruct.create('div', {
                className : 'muiComplexLTipsLeft'
            }, this.muiComplexRight);

            if (this.creator) {
                domConstruct.create('div', {
                    className : 'muiComplexLTipsCreator muiFontSizeSS line-cut',
                    innerHTML : this.creator
                }, muiComplexLTipsLeft);
            }

            if (this.created) {
                domConstruct.create('div', {
                    className : 'muiComplexLTipsTime muiFontSizeSS',
                    innerHTML : this.created + "<span style='margin-left: 0.5rem' class='fontmuis muis-views'></span>"
                }, muiComplexLTipsLeft);
            }

            if (this.count) {
                if(this.count > 999)
                    this.count = '999+';
                domConstruct.create('div', {
                    className : 'muiComplexLTipsRight muiFontSizeSS',
                    innerHTML : this.count + ' '+msg['mui.item.views']
                }, this.muiComplexRight);
            }

        },

        startup : function() {
            if (this._started) {
                return;
            }
            this.inherited(arguments);
        },

        _setLabelAttr : function(text) {
            if (text)
                this._set("label", text);
        }
    });
});