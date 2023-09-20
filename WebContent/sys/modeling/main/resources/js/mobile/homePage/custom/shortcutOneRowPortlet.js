/**
 * 快捷方式 -- 单行切换
 */
define(['dojo/_base/declare',"dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
        "sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin", "dojo/dom-class", "dojo/parser",
        "sys/modeling/main/resources/js/mobile/homePage/common/menuSlide", "sys/mportal/mobile/extend/MenuItemMixin",
        "sys/modeling/main/resources/js/mobile/homePage/common/CountMenuItem","dojo/dom-style"],
    function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util,
             _IndexMixin, domClass, dojoParser, menuSlide, MenuItemMixin, CountMenuItem,domStyle){

        return declare('sys.modeling.main.resources.js.mobile.homePage.mportalList.statistics', [WidgetBase, openProxyMixin, _IndexMixin] , {

            url : "",

            listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&arrIndex=!{arrIndex}&fdMobileId=!{fdMobileId}&nodeType=!{nodeType}&order=!{order}&area=statistics#path=!{tabIndex}",

            DATALOAD : "/sys/modeling/mobile/index/load",

            fixNum : 3,	// 最多展示的方块

            fdMobileId:"",

            postCreate : function() {
                this.inherited(arguments);
            },
            startup: function() {
                this.inherited(arguments)
            },
            buildRendering : function() {
                this.inherited(arguments);
                domClass.add(this.domNode, "mportalList-statistics modelAppSpaceWidgetDemoModel");
                var attrs = this.portletInfo.fdPortletConfig.attr;
                if(attrs.title.isHide === "0"){
                    var titleDom = domConstruct.create('div',{
                        className:"modelAppSpaceWidgetDemoTypeTitle",
                    },this.domNode);
                    titleDom.innerText = attrs.title.value;
                }
                this.createContent(attrs.listViews.value);
            },
            createContent : function(items){
                items = this.formatItems(items);
                if(items.length === 0){
                    // 没有展示项时
                    var style1 = "background: #4285f4;border-radius: 0.4rem;";
                    var style2 = "height:8rem;";
                    var fontStyle = "color:#FFFFFF;";
                    var imageStyle = "margin-top:2rem;";
                    var textStyle  = "margin-top:2rem;";
                    this.showNoAuth(this.domNode,style1,style2,fontStyle,imageStyle,textStyle);
                }else{
                    // 创建滑动块
                    this.createMenuSlide(items);
                }
            },

            createMenuSlide : function(items){
                var style = "background :" + this.portletInfo.fdPortletConfig.backgroundColor;
                var menuSlideDom = domConstruct.create("div", {
                    "className" : "mportalList-swiper",
                    "data-dojo-type" : "sys/modeling/main/resources/js/mobile/homePage/common/menuSlide",
                    "data-dojo-mixins" : "sys/mportal/mobile/extend/MenuItemMixin",
                    style:style
                }, this.domNode);

                var slideContainerNode = domConstruct.create("div", {
                    "className" : "mportalList-item-container",
                    style:style
                }, menuSlideDom);
                var numberColor = this.portletInfo.fdPortletConfig.numberColor;
                var textColor = this.portletInfo.fdPortletConfig.textColor;
                var menuSlides = dojoParser.instantiate([menuSlideDom],{
                    itemRenderer : CountMenuItem,
                    columns : this.fixNum,
                    width : "inherit",
                    height: "70px",
                    containerNode : slideContainerNode,
                    numberColor: numberColor,
                    textColor :textColor
                });
                menuSlides[0].render(items);
            },

            formatItems : function(items){
                var rs = [];
                for(var i = 0;i < items.length;i++){
                    if(items[i].auth === "true"){
                        var tabIndex = this.getTabIndex(items[i].countLv, items[i].lvCollection);
                        items[i].url = util.urlResolver(this.listViewUrl, {listViewId: items[i].listView,arrIndex: i,fdMobileId:this.fdMobileId,tabIndex:tabIndex,order:this.portletInfo.fdOrder})
                        rs.push(items[i]);
                    }
                }
                return rs;
            }

        });
    });