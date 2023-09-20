define([
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/Deferred",
  "dojox/mobile/ScrollableView",
  "mui/list/_ViewScrollEventPublisherMixin",
  "mui/scrollable/IPhoneXScrollableMixin",
  "dojo/dom-style",
  "dojo/dom-attr",
  "dojo/dom-class",
  "dojox/mobile/_css3",
  "dojo/query",
  "dojo/_base/window",
  "./AddBottomTipMixins",
  "dojo/_base/lang"
], function(
  declare,
  topic,
  Deferred,
  ScrollableView,
  _ViewScrollEventPublisherMixin,
  IPhoneXScrollableMixin,
  domStyle,
  domAttr,
  domClass,
  css3,
  query,
  win,
  AddBottomTipMixins,
  lang
) {
  return declare(
    "mui.view.DocScrollableView",
    [
      ScrollableView,
      _ViewScrollEventPublisherMixin,
      IPhoneXScrollableMixin,
      AddBottomTipMixins
    ],
    {
      scrollBar: false,
      optionConfig: [],
      startup: function() {
        this.inherited(arguments)
        this.connect(window, "orientationchange", "_handleOrientation")
        this.addBottomTip(this.containerNode)
        var scrollViewDom = this.domNode;//document.getElementById("scrollView")
        var scrollViewOriHeight = 0 // 滚动区域原始高度（用于ios下当软键盘收起的时候重置回原始高度）
        if (scrollViewDom) {
          //监听ios键盘收起
          document.body.addEventListener("focusout", function() {
            setTimeout(function() {
              if (scrollViewOriHeight == 0) {
                return
              }
              //window.scroll(0, 0)
              scrollViewDom.style.height = scrollViewOriHeight + "px"
            }, 100)
          })
          //监听ios键盘弹出
          document.body.addEventListener("focusin", function() {
        	if(scrollViewDom.style.display=="none"){
        		return;
        	}
            if (scrollViewOriHeight == 0) {
              scrollViewOriHeight =
                scrollViewDom.clientHeight || scrollViewDom.offsetHeight
            }
            setTimeout(function() {
            	scrollViewDom.style.height = scrollViewOriHeight + "px"
            }, 100)
          })
        }

        /**
         * #172832 在多页签情况下，如果某个页签超长，下拉到最底部时，再切换到其它页签时，会出现空白
         * 原因是多页签共享了父元素的transform属性
         * 这里需要对每个页签记录自己的transform属性值
         */
        // 切换前的页签
        this.subscribe("/dojox/mobile/afterTransitionOut", function (view) {
          var parentNode = view.domNode.parentNode;
          // 记录当前view的高度定位
          view._style = domAttr.get(parentNode, "style");
        });
        // 切换后的页签
        this.subscribe("/dojox/mobile/afterTransitionIn", function (view) {
          var parentNode = view.domNode.parentNode;
          // 从当前view中获取上次记录的定位，如果有就使用原来的定位
          var style = view._style;
          if(!style) {
            style = "position: absolute; top: 0px; width: 100%; transform: translate3d(0px, 0px, 0px);";
          }
          domAttr.set(parentNode, "style", style);
        });
      },
      buildRendering: function() {
        this.inherited(arguments)
      },
      _handleOrientation: function() {
        try {
          var self = this
          var timeout = 120
          var deferred = new Deferred()
          function detect(i, height0) {
            window.innerHeight != height0 || i >= timeout
              ? deferred.resolve()
              : window.requestAnimationFrame(function() {
                  detect(i + 1, height0)
                })
          }
          detect(0, window.innerHeight)
          deferred.then(function() {
            self.resize && self.resize()
          })
        } catch (e) {}
      },

      isFormElement: function(/* DOMNode */ node) {
        if (node && node.nodeType !== 1) {
          node = node.parentNode
        }
        if (!node || node.nodeType !== 1) {
          return false
        }

        var t = node.tagName
        // 兼容编辑器，避免编辑器无法获取焦点
        return (
          t === "SELECT" ||
          t === "INPUT" ||
          t === "TEXTAREA" ||
          t === "BUTTON" ||
          t === "I" ||
          domAttr.get(node, "contenteditable") == "true" ||
          this.isSelectableElement(node)
        )
      },

      //#57480 为了实现选中复制功能,CSS中设置了user-select:text的节点不取消默认事件
      isSelectableElement: function(node) {
        var userSelectName = css3.name("userSelect"),
          userSelect =
            domStyle.getComputedStyle(node).userSelect ||
            domStyle.getComputedStyle(node).webkitUserSelect
        if (userSelect === "text") {
          this.fixAppBar()
          return true
        }
        return false
      },

      fixAppBar: function() {
        //防抖动
        function debounce(func, wait, options) {
          var timer
          return function() {
            if (timer) {
              window.clearTimeout(timer)
              timer = null
            }
            var context = (options && options.context) || this,
              args = arguments
            timer = window.setTimeout(function() {
              func.apply(context, arguments)
            }, wait)
          }
        }
        //部分客户端下拖动user-select:text的元素时会导致底部(absolute组件)抖动，滚动过程使用fixed替代
        //back hack
        var node = query('[fixed="bottom"]')
        for (var i = 0; i < node.length; i++) {
          domClass.add(node[i], "fixed")
        }
        win.global.addEventListener(
          "scroll",
          debounce(function() {
            for (var i = 0; i < node.length; i++) {
              domClass.remove(node[i], "fixed")
            }
          }, 1000)
        )
      },

      onAfterTransitionIn: function() {
        topic.publish("mui/view/currentView", this)
      },

      holdTime: 250,

      lastTime: null,

      isdbClick: function(evt) {
        var time = this.lastTime
        this.lastTime = new Date().getTime()
        if (time && this.lastTime - time <= this.holdTime) return true
        return false
      }
      //造成 ##93177 的问题，先注释掉
      /*      resize: function() {
        if (this.timer) {
          clearTimeout(this.timer)
          this.timer = null
        }
        var args = arguments

        this.timer = setTimeout(
          lang.hitch(this, function() {
            this.inherited(args)
          }),
          10
        )
      }*/
    }
  )
})
