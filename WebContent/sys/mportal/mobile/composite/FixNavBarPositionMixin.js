define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/topic",
  "dojo/Deferred",
  "dojo/_base/lang",
  "dojo/query",
  "dojo/dom-geometry",
  "dojo/dom-class",
], function (
  declare,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  topic,
  Deferred,
  lang,
  query,
  domGeom,
  domClass
) {
   var fixNavBarPositionMixin =  declare(
    "sys.mportal.fixNavBarPositionMixin", null ,
    {
      
      
      //固定底部导航布局navBar位置
      __fixNavBarPositionWithTabBarBottom: function(){ 
    	  if( this.getParent && this.getParent() && this.getParent().compositeNavLayout != 2){
        	  return;
          }
    	 
    	  var content = this.contentMap[this.pageId];
          if(!content){
         	 return;
          }
          
          // 快捷方式导航栏
          var tabContainer = query(".et-portal-tabs-container",content.domNode)[0];  
          
          if(!tabContainer){
        	  return;
          }
          
          // 快捷方式组
          var tabs = query(".et-portal-tabs-container .et-portal-tab",content.domNode)[0];
          // 快捷方式组
          var boxFixed = query(".et-portal-tabs-box-fixed",content.domNode)[0];
          //门户配置更多按钮
          var more = query(".et-portal-tabs-more",content.domNode)[0];
          //门户配置box
          var navBox = query(".et-portal-tabs-box",content.domNode)[0]

          var offset = domGeom.position(query(".muiHeaderBox")[0]).h; 
          
          var isFixedNavBar = false;
          var scrollTop = 0;
           
          /**
		   * 1:安卓、iPhone都为 position:absolute布局
		   * 2:安卓为 position: fixed布局，iPhone为 position:absolute 布局
		   */
          if(window.__mportal_bottomTabBarPositionType == "1" && !this.getParent().isIOS()){
        	  scrollTop =  domGeom.position(query(".muiHeaderBox")[0]).y;
        	  if(scrollTop < 0 && Math.abs(scrollTop) >= offset){
        		  isFixedNavBar = true;
        	  }
          }else{
        	  scrollTop =
                document.documentElement.scrollTop ||
                document.body.scrollTop ||
                window.pageYOffset;
        	  if(scrollTop  >= offset){
        		  isFixedNavBar = true;
        	  }
          }       	   
          
          
          
          if (isFixedNavBar) {
        	if(this.hadFixNavBar && this.preFixNavBarId == this.pageId){
        		return;
        	}
        	if(tabContainer){
        		domClass.add(tabContainer, "et-portal-tabs-container--top")
        	}
            if(boxFixed){
            	 domClass.add(boxFixed, "et-portal-tabs-container-shadow")
            }
 
            if (more) domClass.add(more, "et-portal-tabs-more-fixed")
            
            domStyle.set(navBox, {
              position: "fixed",
              top: "0"
            })
            this.hadFixNavBar = true;
            this.preFixNavBarId = this.pageId;
          } else {
        	if(!this.hadFixNavBar && this.preFixNavBarId == this.pageId){
          		return;
          	}
        	if(tabContainer){
          		domClass.remove(tabContainer, "et-portal-tabs-container--top")
          	}
            if(boxFixed){
              	domClass.remove(boxFixed, "et-portal-tabs-container-shadow")
            }

            if (more) domClass.remove(more, "et-portal-tabs-more-fixed")

            domStyle.set(navBox, {
              position: "relative",
              top: "0"
            })
            this.hadFixNavBar = false;
            this.preFixNavBarId = this.pageId;
          }
      }
    }
  );
   return fixNavBarPositionMixin;
});
