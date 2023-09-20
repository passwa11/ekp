/**
 * 原生滚动view
 * 暂只支持竖向滚动，后面扩展横向
 **/
define([
  "dojo/_base/declare",
  "dojox/mobile/View",
  "dijit/registry",
  "dojo/dom-class",
  "dojo/dom-style",
], function (declare, View, registry, domClass, domStyle) {
  return declare("mui.view.NativeView", [View], {
    baseClass: "mblView mblScrollableView",
    // h 代表竖向，v代表横向
    scrollDir: "v",
    keepScrollPos: false,
    fixedTop: null,
    fixedBottom: null,

    buildRendering: function () {
      this.inherited(arguments);
      this.domNode.dojoClick = false;
    },

    startup: function () {
      this._v = this.scrollDir.indexOf("v") != -1;
      this._h = this.scrollDir.indexOf("h") != -1;
      this.init();
      this.inherited(arguments);
    },

    init: function () {
      if (this._v) {
        this.findAppBars();
        domClass.add(this.domNode, "muiViewV");
        return;
      }
      if (this._h) {
        domClass.add(this.domNode, "muiViewH");
      }
    },

    checkFixedBar: function (node) {
      if (node.nodeType === 1) {
        var fixed =
          node.getAttribute("fixed") ||
          (registry.byNode(node) && registry.byNode(node).fixed);
        {
          if (fixed === "top") {
            this.fixedTop = node;
            domClass.add(node, "muiViewTop");
            return;
          }
          if (fixed === "bottom") {
            domClass.add(node, "muiViewBottom");
            this.fixedBottom = node;
            domStyle.set(
              this.domNode,
              "padding-bottom",
              node.offsetHeight + "px"
            );
          }
        }
      }
    },

    resize: function () {
      this.inherited(arguments);

      if (this.fixedBottom) {
        domStyle.set(
          this.domNode,
          "padding-bottom",
          this.fixedBottom.offsetHeight + "px"
        );
      }
    },

    scrollTo: function (x) {
      this.domNode.scrollTop = -x.y;
    },

    findAppBars: function () {
      var i, len;

      for (i = 0, len = this.domNode.childNodes.length; i < len; i++) {
        this.checkFixedBar(this.domNode.childNodes[i]);
      }

      var parentNode = this.domNode.parentNode;

      if (parentNode) {
        for (i = 0, len = parentNode.childNodes.length; i < len; i++) {
          this.checkFixedBar(parentNode.childNodes[i]);
        }
      }
    },

    // 获取当前滚动位置
    getPos: function () {
      return { y: document.documentElement.scrollTop || document.body.scrollTop };
    },

    onBeforeTransitionOut: function () {
        if (this._v) {
            this.scrollTop = document.documentElement.scrollTop;
            this._setScrollTop(0);
        }
    },
    

    onAfterTransitionIn: function () {
      if (this._v) {
        if (this.scrollTop && this.scrollTop > 0) {
			this.defer(function() {
				this._setScrollTop(this.scrollTop);
	        }, 200);
        }
      }
    },
    
	/**
	* 设置滚动条位置
	* @param value  滚动位置scollTop值(Y轴滚动条)
	* @return
	*/
	_setScrollTop: function(value){
		document.body.scrollTo && document.body.scrollTo(0,value);
		document.documentElement.scrollTo && document.documentElement.scrollTo(0,value);
	}
    
  });
});
