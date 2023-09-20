define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/topic",
  "sys/mportal/mobile/OpenProxyMixin",
  "mui/iconUtils"
], function(
  declare,
  _WidgetBase,
  domConstruct,
  domStyle,
  topic,
  OpenProxyMixin,
  iconUtils
) {
  return declare(
    "mui.calendar.CardItem",
    [_WidgetBase, OpenProxyMixin],
    {
      // 点击链接
      href: "",
      // 标题
      text: "",
      // 图标
      icon: "",
      // 宽度百分比
      width: "",
      // 自定义点击事件
      click: null,
      
      select:false,

      baseClass: "muiCalendarRightNodeMenuItem",

      buildRendering: function() {
        this.inherited(arguments)
        domStyle.set(this.domNode, "width", this.width)
        this.renderIcon()
        this.renderText()
        this.bindClick()
      },

      // 绑定点击事件
      bindClick: function() {
    	  if(!this.select){
    		  if (this.href) {
		          this.proxyClick(this.domNode, this.href, "_blank")
		          return
		      }
    	  }else{
    		  this.connect(this.domNode, "click", this._closeDialog);
    	  }

        if (this.click) {
          this.customClick()
        }
      },

      // 自定义点击事件
      customClick: function() {
        this.connect(this.domNode, "click", "click")
      },
      
      _closeDialog:function(){
    	  topic.publish('/mui/calendar/rightNodeDialogClose');
      },

      // 渲染图标
      renderIcon: function() {
        if (this.icon) {
        	if(!this.select){
        		domConstruct.create("div",{className: this.icon},this.domNode)
        	}else{
        		domConstruct.create("div",{className: " mui_ekp_meeting_switch_select " + this.icon},this.domNode)
        	}
          
        }
        
      },

      // 渲染文本
      renderText: function() {
        this.textNode = domConstruct.create(
          "div",
          {className: "muiCalendarRightNodeMenuItemText"},
          this.domNode
        )
        this.textNode.appendChild(domConstruct.create("div",{className: this.textIcon}))
         this.textNode.appendChild(domConstruct.create("span",{innerHTML: this.text}))
      }
    }
  )
})
