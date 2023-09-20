/**
 * 快捷方式 -- 图标切换
 */
define(['dojo/_base/declare',"dijit/_WidgetBase", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
        "sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin", "sys/modeling/main/resources/js/mobile/homePage/common/menuSlide",
        "sys/mportal/mobile/extend/MenuItemMixin", "dojo/parser"],
    function(declare, WidgetBase, domClass, domConstruct, openProxyMixin, util, _IndexMixin, menuSlide, MenuItemMixin, dojoParser){

        return declare('sys.modeling.main.resources.js.mobile.homePage.custom.shortcutSlideIconPortlet', [WidgetBase, openProxyMixin, _IndexMixin] , {

            url : "",

            listViewUrl : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&nodeType=!{nodeType}&order=!{order}&isDirectlyThrouth=true#path=!{tabIndex}",

            DATALOAD : "/sys/modeling/mobile/index/load",

            postCreate : function() {
                this.inherited(arguments);
            },

            buildRendering : function() {
                this.inherited(arguments);
                domClass.add(this.domNode, "mportal-iconArea modelAppSpaceWidgetDemoModel");
                var values = this.portletInfo.fdPortletConfig.attr.listViews.value;
                if(this.portletInfo.fdPortletConfig.attr.title.isHide === "0"){
                    var titleDom = domConstruct.create('div',{
                        className:"modelAppSpaceWidgetDemoTypeTitle",
                    },this.domNode);
                    titleDom.innerText = this.portletInfo.fdPortletConfig.attr.title.value;
                }
                // 发送请求获取总数
                this.createContent(values);
            },
            createContent : function(items){
                items = this.formatItems(items);
                var iconItems = this.filterByAuth(items);
                if(iconItems.length === 0){
                    // 没有展示项时
                    var style1 = "border: 1px dashed #D4D6DB;border-radius: 2px;margin-top: 2rem;";
                    var style2 = "height:11.5rem;";
                    var imageStyle = "margin-top:3.2rem;";
                    var textStyle = "margin-top:2.6rem;";
                    this.showNoAuth(this.domNode,style1,style2,null,imageStyle,textStyle);
                }else{
                    // 创建滑动块
                    this.createMenuSlide(items);
                }
            },

            // 根据后台的权限进行数组的过滤
            filterByAuth : function(items){
                var rs = [];
                for(var i = 0;i < items.length;i++){
                    if(items[i].auth === "true"){
                        rs.push(items[i]);
                    }
                }
                return rs;
            },
            formatItems : function(items){
                var rs = [];
                for(var i = 0;i < items.length;i++){
                    items[i].text = items[i].title;
                    var tabIndex = this.getTabIndex(items[i].countLv, items[i].lvCollection);
                    items[i].url = util.urlResolver(this.listViewUrl, {listViewId: items[i].listView, tabIndex:tabIndex,nodeType:items[i].nodeType,order:this.portletInfo.fdOrder});
                    rs.push(items[i]);
                }
                return rs;
            },

            createMenuSlide : function(items){
                var menuSlideDom = domConstruct.create("div", {
                    "className" : "mportal-menu-slide",
                    "data-dojo-type" : "sys/modeling/main/resources/js/mobile/homePage/common/menuSlide",
                    "data-dojo-mixins" : "sys/mportal/mobile/extend/MenuItemMixin"
                }, this.domNode);

                var menuSlides = dojoParser.instantiate([menuSlideDom],{
                    baseClass : "muiPortalMenuSlide mui_ekp_portal_item",
                    isCalSwitchHeight : false,
                    columns: 5,
                });
                menuSlides[0].render(items);
            }
        });
    });