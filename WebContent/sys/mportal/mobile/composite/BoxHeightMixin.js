define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dojo/query",
  "dojo/dom-geometry",
  "dojo/dom-class",
  "dojo/window"
], function (
  declare,
  domStyle,
  query,
  domGeom,
  domClass,
  win
) {
   var boxHeightMixin =  declare(
    "sys.mportal.boxHeightMixin", null ,
    {
    	//获取头部高度
    	getHeightOfHeaderBox: function(){
    		var height = 0;
    		var muiHeaderBoxNode = query(".muiHeaderBox");
    		if(muiHeaderBoxNode && muiHeaderBoxNode[0]){
    			height = domStyle.get(muiHeaderBoxNode[0], "height");
    		}
    		return height;
    	},
    	
    	//获取底部tabbar高度
    	getHeightOfBottomTabBar: function(){
    		var height = 50;
    		var botttomTabBarNode = query(".muiBottomTabBarBox");
    		if(botttomTabBarNode && botttomTabBarNode[0]){
    			height = domStyle.get(botttomTabBarNode[0], "height") || 50;
    		}
    		return height;
    	},
    	
    	//获取navBar高度
        getHeightOfNavPortalTabs: function(){
        	var height = 50;
        	var portalTabsNode = query(".et-portal-tabs");
        	if(portalTabsNode && portalTabsNode[0]){
        		height =  domStyle.get(portalTabsNode[0], "height") || 50; 
        	}
        	return height;
        },
        
        //获取所有高度
        getBoxTotalHeight: function(){
        	return this.getHeightOfHeaderBox() + this.getHeightOfBottomTabBar() + this.getHeightOfNavPortalTabs();
        },
        
        //顶部导航固定高度
        getBoxOfTopLayout: function(){
        	return this.getHeightOfHeaderBox() + this.getHeightOfNavPortalTabs();
        },
        
        //获取底部导航容器高度
        getBoxOfBottomLayoutCommon: function(){
        	var height = win.getBox().h;
            height = height - this.getHeightOfHeaderBox() - this.getHeightOfBottomTabBar();
        	return height;
        },
        
        setTopLayoutMinHeight: function(domNode){    
        	if(domNode){
        		var height = win.getBox().h;
                height = height - this.getBoxOfTopLayout();
                domStyle.set(domNode, "min-height", height + "px");
        	}
        },
        
    }
  );
   return boxHeightMixin;
});
