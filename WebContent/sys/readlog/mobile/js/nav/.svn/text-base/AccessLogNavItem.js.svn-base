define([
  "dojo/_base/declare",
  "mui/nav/NavItem",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-style",
    "dojo/topic"
], function(
  declare,
  NavItem,
  domConstruct,
  domClass,
  domStyle,
  topic
) {
  return declare("sys.readlog.mobile.js.nav.AccessLogNavItem", [NavItem], {   
	/**
	* 修改设置角标的显示值
	*/
    modifyNavCount: function(badgeNum) {
      this.inherited(arguments);
      this.badge = badgeNum;
      if (badgeNum > 99999) {
    	  badgeNum = "99999+";
      }
      if(this.tabIndex == 0) {
          //发布导航对应列表总数改变事件
          topic.publish("accesslog/nav/list/sizeupdate", this, {size: badgeNum});
      }
      var html = this.textNode.innerHTML;
      var itemDivNode = domConstruct.create("div",{className:"muiNavitemDiv"});
      if(!this.itemDivNode){
    	  this.itemDivNode = itemDivNode;
    	  var spanNode = domConstruct.create("span",{className:"muiAccessLogNavItem muiNavitemSpan",innerHTML:html});
          var spanCountNode = domConstruct.create("div",{className:"muiNavitemSpanCount",innerHTML:"("+badgeNum+")"});
          if(!this.spanNode){
        	  this.spanNode = spanNode;
        	  domConstruct.place(spanNode,itemDivNode,"only");
          }
          if(!this.spanCountNode){
        	  this.spanCountNode = spanCountNode;
        	  domConstruct.place(spanCountNode,itemDivNode,"last");
          }
          domConstruct.place(itemDivNode,this.textNode,"after");
          domStyle.set(this.textNode,"display","none");
      }
    }
    
  });
})
