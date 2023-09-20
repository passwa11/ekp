/**
 * 日程部件 -- 普通待办
 */
define(['dojo/_base/declare',"dijit/_WidgetBase","dojo/dom-style", "dojo/dom-class", "dojo/dom-construct", "mui/openProxyMixin", "mui/util",
        "sys/modeling/main/resources/js/mobile/homePage/common/_IndexMixin","mui/i18n/i18n!sys-modeling-main"],
    function(declare, WidgetBase,domStyle, domClass, domConstruct, openProxyMixin, util, _IndexMixin,modelingLang){

        return declare('sys.modeling.main.resources.js.mobile.homePage.default.notify', [WidgetBase, openProxyMixin, _IndexMixin], {

            url : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}&nodeType=!{nodeType}&order=!{order}&isDirectlyThrouth=true#path=!{tabIndex}",

            DATALOAD : "/sys/modeling/mobile/index/load",

            postCreate : function() {
                this.inherited(arguments);
            },

            buildRendering : function() {
                this.inherited(arguments);
                domClass.add(this.domNode, "modelAppSpaceWidgetDemoModel");
                var attrs = this.portletInfo.fdPortletConfig.attr;
                if(attrs.title.isHide === "0"){
                    var titleDom = domConstruct.create('div',{
                        className:"modelAppSpaceWidgetDemoTypeTitle",
                    },this.domNode);
                    titleDom.innerText = attrs.title.value;
                }
                var info = attrs.listView.value;
                if(info.auth === "true"){
                    info.count = info.count || 0;
                    var html = "";
                    html += "<div class='mui_pph_toast'><div class='mui_pph_toast_content'>";

                    html += "<i class='mui_pph_toast_icon'></i>";

                    html += "<div class='mui_pph_tc_text'>";
                    html += modelingLang['mui.modeling.you.have'];
                    html += "<span";
                    if(info.count === 0){
                        html += " class='zero_status_color' ";
                    }
                    html += " >"+ info.count +"</span>";
                    html += modelingLang['mui.modeling.To.do.process'];
                    html += "</div>";
                    if(info.count > 0){
                        html += "<i class='mui_pph_new_icon'></i>";
                    }
                    html += "</div><i class='mui_pph_close_icon'></i></div>";
                    domConstruct.place(domConstruct.toDom(html),this.domNode,"last");
                    // domConstruct.place(domConstruct.toDom("<i class='mui_pph_close_icon'></i>"),this.domNode,"last");
                    if(info.sourceType === "system"){
                        this.proxyClick(this.domNode, "/sys/notify/mobile", '_self');
                    }else{
                        var tabIndex = this.getTabIndex(info.countLv, info.lvCollection);
                        this.proxyClick(this.domNode, util.urlResolver(this.url, {listViewId: info.listView,tabIndex: tabIndex,order:this.portletInfo.fdOrder}), '_self');
                    }
                }else{
                    var style1 = "color:#3E4665;height: .68rem;";
                    var style2= "height: .68rem;width: 100%;";
                    var fontStyle = "color:#FFFFFF;font-size:.32rem;display:inline-block;margin-top:.1rem;";
                    var imageStyle = "width:.5rem;height:.5rem;margin-top:.1rem;"
                    this.showNoAuth(this.domNode,style1,style2,fontStyle,imageStyle,null,true);
                    domStyle.set(this.domNode,{background:"rgb(16 16 16 / 20%)"});
                }
            }
        });
    });