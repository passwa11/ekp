define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/request",
  "mui/util",
  "dojo/topic",
  "./TabItem"
], function(
  declare,
  lang,
  array,
  domStyle,
  WidgetBase,
  Contained,
  Container,
  request,
  util,
  topic,
  TabItem
) {
  var tabBar = declare(
    "sys.mportal.Tabbar",
    [WidgetBase, Container, Contained],
    {

      pageId: "",
      
      tabId: "",

      baseClass: "muiBottomTabBarBox",
      
      buildRendering: function() {
    	  
        this.inherited(arguments)
        domStyle.set(this.domNode,"touch-action", "none");
        var self = this;
        
        if (history.pushState) {
        	
			this.connect(window, 'popstate', function(evt) {
				if(evt.state)
					if(evt.state.id){
						topic.publish('/sys/mportal/tabItem/changed',{
							pageId : evt.state.id,
							back : true
						});
					}
			})
		}
        
        if (this.data) {       	
          self.render(this.data)         
          return
        }

      },
      
      render: function(data) {
        this.values = data;
        var width = (1 / this.values.length) * 100 + "%";
        array.forEach(
          this.values,
          function(item) {       	 
        	  var isSelected = false;      	 
        	  if(item.fdId == this.pageId){
        		  isSelected = true;
        	  }
        	  var _item = new TabItem({
        		  	id: item.fdId,
            		text: item.fdName,
            		icon: item.fdIcon,
                    img: item.fdImg,
            		type: item.fdType,
            		width: width,
            		pageType: item.pageType,
            		pageId: item.pageId,
            		pageUrl: item.pageUrl,
            		pageUrlOpenType: item.pageUrlOpenType || 1,
            		isSelected:  isSelected
        	  });
           
        	  if (_item) {
        		  this.addChild(_item)
        	  }
          },this);
        
      },
      
    }
  )

  return tabBar
})
