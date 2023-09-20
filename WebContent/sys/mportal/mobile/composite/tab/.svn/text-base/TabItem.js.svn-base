define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/request",
  "mui/util",
  "dojo/on",
  "dojo/topic"
], function(
  declare,
  lang,
  array,
  domStyle,
  domClass,
  domConstruct,
  WidgetBase,
  Contained,
  Container,
  request,
  util,
  on,
  topic
) {
  var tabItem = declare(
    "sys.mportal.tabItem",
    [WidgetBase, Container, Contained],
    {
      baseClass: "muiBottomTabItem",

      id: "",

      text: "",
      
      textStyle: "",//"font-size: 1.2rem;margin-top:0px;color:#2A304A;",
      
      icon: "", //系统图标

	  img: "", //素材库图标

      iconStyle: "",//"font-size:2.6rem;color:#2A304A;",
      
      //1:页面，2:页签
      type: "",
      
      //页面Id
      pageId: "",
      
      //1:公共门户，2:外部链接
      pageType: "",
      
      //外部链接
      pageUrl: "",
      
      //外部链接打开方式 1:内部窗口 2:新窗口
      pageUrlOpenType: 1,
      
      //是否选中
      isSelected: false,
      
      selectedColor: "#3484F5",        
      
      unSelectedColor: "#2A304A",
      
      width: "",

      buildRendering: function() {    	  
        this.inherited(arguments);
        
        topic.subscribe(
          "/sys/mportal/tabItem/changed",
          lang.hitch(this, this.tabItemChange)
        );
        
        domStyle.set(this.domNode,"display", "flex");
        domStyle.set(this.domNode,"justify-content", "center");
        domStyle.set(this.domNode,"align-items", "center");
        domStyle.set(this.domNode,"width", this.width);
        
        var container = domConstruct.create("div",{
        	style: "text-align: center;width: 100%;padding-left: 5px;padding-right: 5px;"
        },this.domNode);        

        if(this.icon){
        	if(this.icon.startsWith("mui")){
        		this.iconNode = domConstruct.create("i",{
                	className: "muiCompositeTabItemIcon mui " + this.icon,
                	style: this.iconStyle
                },container);
        	}else{
        		this.iconNode = domConstruct.create("i",{
                	className: "muiCompositeTabItemIcon mui ",
                	style: this.iconStyle,
                	innerHTML: this.icon
                },container);
        	}
        }

        if(this.img){
			var url = this.img;
			if(url.indexOf("/") == 0){
				url = url.substring(1);
			}
			url = dojoConfig.baseUrl+ url;
			this.iconNode = domConstruct.create("i",{
				className: "muiCompositeTabItemIcon mui ",
				style: "width: 20px;height: 20px;background:url('"+url+"') no-repeat center;background-size:contain;"
			},container);
        }

        if(this.text){
        	this.textNode = domConstruct.create("div",{
        		className: "muiCompositeTabItemText",
            	style: this.textStyle,
            	innerHTML: this.text
            },container);       	
        }
        
        var self = this;
        on(this.domNode, "click",function(e){
        	self.tabClick(e);
        });
        if(this.isSelected){
        	self.tabClick();
        }
      },
      
      tabItemChange: function(data){
    	  if(data.pageId != this.id){
    		  this.isSelected = false;
    		  if(this.iconNode){
        		  //domStyle.set(this.iconNode,"color", this.unSelectedColor);
        		  domClass.remove(this.iconNode,"muiCompositeTabItemIconSelected");
        	  }
        	  if(this.textNode){
        		  //domStyle.set(this.textNode,"color", this.unSelectedColor);
        		  domClass.remove(this.textNode,"muiCompositeTabItemTextSelected");
        	  } 
    	  }else{
    		  this.isSelected = true;
    		  if(this.iconNode){       		  
        		  domClass.add(this.iconNode,"muiCompositeTabItemIconSelected");
        	  }
    		  if(this.textNode){
        		  domClass.add(this.textNode,"muiCompositeTabItemTextSelected");
        	  } 
    	  }
      },
      
      tabClick: function(e){
    	  if(e && this.isSelected){
    		  return;
    	  }
    	  this.isSelected = true;
    	  if(this.iconNode){
    		  //domStyle.set(this.iconNode,"color", this.selectedColor);
    		  domClass.add(this.iconNode,"muiCompositeTabItemIconSelected");
    	  }
    	  if(this.textNode){
    		  domClass.add(this.textNode,"muiCompositeTabItemTextSelected");
    		  //domStyle.set(this.textNode,"color", this.selectedColor);
    	  }
    	  //topic.publish("/sys/mportal/tabItem/click",{id: this.id}, this);   	  
    	  var needChangeUrl = true;
    	 //页面类型
		  if(this.type == 1){
			 //外部链接
			 if(this.pageType == 2){
				 var url = util.formatUrl(this.pageUrl,true);
				 if(this.pageUrlOpenType == 2){
					 needChangeUrl = false;
					 window.open(url, '_self');
				 }else{
					 topic.publish("/sys/mportal/tabItem/changed",{pageId: this.id}, this); 
				 }
			 }else{//公共门户
				 topic.publish("/sys/mportal/tabItem/changed",{pageId: this.id}, this);  
			 }	
		   }else{//页签类型
			  topic.publish("/sys/mportal/tabItem/changed",{pageId: this.id}, this);  			 
		  }  
		  if(needChangeUrl){
			//变更url参数
	    	  if((!e || !e.back) && history.pushState){
					var url = location.href;
					url = util.setUrlParameter(url,'fdId',this.id);
					url = util.setUrlParameter(url,'fdName',encodeURIComponent(this.text));
					history.pushState({id:this.id},'',url);
	    	  }  
		  }
      }     
      
    }
  )

  return tabItem
})
