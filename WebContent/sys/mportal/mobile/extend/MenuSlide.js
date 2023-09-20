define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dijit/_WidgetBase",
  "dijit/_Container",
  "dojo/dom-construct",
  "dojo/_base/array",
  "dojo/request",
  "dojo/query",
  "mui/util",
  "dojo/dom-class",
  "dojo/_base/lang",
  "dojo/dom-geometry",
  "dojox/mobile/SwapView",
  "dojox/mobile/sniff"
], function(
  declare,
  domStyle,
  WidgetBase,
  Container,
  domConstruct,
  array,
  request,
  query,
  util,
  domClass,
  lang,
  domGeometry,
  SwapView,
  has
) {
  var menu = declare("sys.mportal.MenuSlide", [WidgetBase, Container], {
    columns: 4,

    rows: 1,

    width: "100%",

    height: "",

    url: "",

    baseClass: "muiPortalMenuSlide",

    postMixInProperties: function() {
      this.inherited(arguments)
      if (!this.columns) {
        this.columns = 4
      }
      if (!this.rows) {
        this.rows = 1
      }
    },

    startup: function() {
      this.inherited(arguments)
      this.subscribe("/dojox/mobile/viewChanged", "handleViewChanged")
      this.source()
    },

    reload: function() {
      this.source()
    },

    buildRendering: function() {
      this.inherited(arguments)
      this.domNode.dojoClick = !has("ios")
    },

    source: function() {
      this.__items = []
      var promise = request.get(util.formatUrl(this.url), {
        handleAs: "json"
      })
      var self = this
      promise.response.then(function(data) {
        if (data.data) {
          if (self.__items && self.__items.length > 0) {
            array.forEach(self.__items, function(item) {
              item.destroyRecursive(false)
            })
          }
          self.render(data.data)
        }
      })
    },

    render: function(items) {
      if (!items || items.length <= 0) {
        domStyle.set(this.domNode, "height", 0)
        return
      }

      array.forEach(this.getChildren(), function(child) {
        if (child instanceof SwapView) {
          child.destroyRecursive()
        }
      })
      var preSwitchNode = query(".swiper-pagination-bullets", this.domNode)
      if (preSwitchNode.length > 0) {
        for (var i = 0; i < preSwitchNode.length; i++) {
          domConstruct.destroy(preSwitchNode[i])
        }
      }

      var length = items.length
      if (this.columns >= length) {
        this.rows = 1
      }

      this.defer(function() {
        domStyle.set(this.domNode, "width", this.width)
        var swap = null
        array.forEach(
          items,
          function(child, index) {
        	// 剩余个数
        	var num = items.length - (index + 1);
        	var withbg = '';
        	if(num < this.columns && num <= 2)
        		withbg = 'withbg';
        	var ul = domConstruct.create("ul", {
        		className : withbg
        	});
            if (index % (this.columns * this.rows) == 0) {
              swap = new SwapView({
                height: "100%",
                w: 4
              }, ul)
              var num = 0;
              this.addChild(swap)
            }
            this._createItem(child, swap, index)
          },
          this
        )

        this._createSwitch()
        this._resetScrollable()

        this.initDomNodeHeight();
      }, 100)
    },
    
    initDomNodeHeight : function(){
    	var height = 0
        if (this.itemPosition) {
          height += this.rows * this.itemPosition.h
        }
        if (this.swithNode) {
          height += domGeometry.position(this.swithNode).h
        }
        domStyle.set(this.domNode, "height", height + "px")
    },

    _createItem: function(child, swap, index) {
      var pros = child
      if (this.itemBaseClass) {
        pros = lang.mixin(
          {
            baseClass: this.itemBaseClass
          },
          child
        )
      }
      var menuItem = new this.itemRenderer(pros)
      if (!this.__items) {
        this.__items = []
      }
      this.__items.push(menuItem)
      swap.domNode.appendChild(menuItem.domNode)
      //#53362 需求要求跟平铺快捷方式保持一致，8个为一次循环，目前CSS没办法做到，改用JS添加类名
      domClass.add(menuItem.domNode, "MenuModuleIndex" + (index % 8))
      this.itemGrometry(menuItem.domNode)
      return menuItem
    },

    _createSwitch: function() {
      //滑动面板个数
      var count = Math.ceil(this.__items.length / (this.columns * this.rows))
      if (count > 1) {
        this.swithNode = domConstruct.create(
          "div",
          {className: "swiper-pagination-bullets"},
          this.domNode
        )
        for (var i = 0; i < count; i++) {
          var node = domConstruct.create(
            "span",
            {className: "swiper-pagination-bullet"},
            this.swithNode
          )
          if (i == 0) {
            domClass.add(node, "swiper-pagination-bullet-active")
          }
        }
      }
    },

    _resetScrollable: function() {
      var count = Math.ceil(this.__items.length / (this.columns * this.rows))
      if (count <= 1) {
        //屏蔽所有SwapView的滑动
        array.forEach(this.getChildren(), function(c, index) {
          if (c.isInstanceOf(SwapView)) {
            c.onTouchStart = function() {
              this.stopAnimation()
              this.abort()
            }
          }
        })
      }
    },

    //单个快捷方式计算
    itemGrometry: function(domNode) {
      domStyle.set(domNode, "width", 100 / this.columns + "%")
      if (!this.itemPosition) {
        this.itemPosition = domGeometry.position(domNode)
      }
    },

    handleViewChanged: function(evt) {
      if (evt instanceof SwapView && evt.getParent() === this) {
        var view = evt,
          index = this.getIndexOfChild(view)
        var switchSpan = query(".swiper-pagination-bullet.swiper-pagination-bullet-active", this.domNode)
        if (switchSpan && switchSpan.length > 0) {
          domClass.remove(switchSpan[0], "swiper-pagination-bullet-active")
        }
        var currentSpan = query(".swiper-pagination-bullet", this.domNode)
        if (currentSpan && currentSpan[index]) {
          domClass.add(currentSpan[index], "swiper-pagination-bullet-active")
        }
      }
    }
  })
  return menu
})
