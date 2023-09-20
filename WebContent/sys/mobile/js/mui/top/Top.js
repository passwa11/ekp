define([
  "dojo/_base/declare",
  "dijit/_Contained",
  "dijit/_Container",
  "dijit/_WidgetBase",
  "dojo/dom-class",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/_base/array",
  "dojo/topic",
  "dojo/dom-geometry",
  "dojo/window"
], function(
  declare,
  Contained,
  Container,
  WidgetBase,
  domClass,
  domConstruct,
  domStyle,
  array,
  topic,
  domGeometry,
  win
) {
  return declare("mui.top.Top", [WidgetBase, Container, Contained], {
    bottom: "80px",
    right: "1.25rem",
    // 当前是否显示
    _show: false,
    //是否可拖拽
    canDrag: true,

    buildRendering: function() {
      this.inherited(arguments)
      if (!this.containerNode) this.containerNode = this.domNode
      domClass.replace(this.containerNode, "muiTop")
      domStyle.set(this.domNode, {
        bottom: this.bottom,
        right: this.right
      })
      this.topNode = domConstruct.create(
        "i",
        {
          className: "fontmuis muis-stick"
        },
        this.domNode
      )
      this.handleDragEvent()
    },

    startup: function() {
      if (this._started) return
      this.inherited(arguments)
    },

    postCreate: function() {
      this.connect(this.domNode, "onclick", "_onClick")
      this.connectToggle()
      var ctx = this
      //@param digitObj 组件对象
      this.subscribe("/mui/top/viewChanged", function(digitObj) {
        ctx.hide()
      })
      this.subscribe(
        "/dojox/mobile/viewChanged,/dojox/mobile/afterTransitionIn",
        function(view) {
          if (view.isShowTop) {
            //兼容新的滚动
            if (view.isShowTop()) {
              this.show()
            } else {
              this.hide()
            }
            return
          }
          this.hide()
          if (view._v && view.toTop) {
            if (view.getPos().y < -5) {
              this.show()
              topic.publish("mui/view/addBottomTip")
            }
          } else {
            array.forEach(
              view.getChildren(),
              function(child) {
                if (child._v && child.toTop) {
                  if (child.getPos().y < -5) {
                    this.show()
                  }
                  return false
                }
              },
              this
            )
          }
        }
      )
    },

    destroy: function(){
      this.destoryDragEvent();
      this.inherited(arguments);
    },

    _onClick: function(evt) {
      this.toTop(evt)
    },

    show: function() {
      domStyle.set(this.domNode, {
        display: "block"
      })
      this.set("_show", true)
    },

    hide: function() {
      //隐藏时重置位置
      domStyle.set(this.domNode, {
        display: "none",
        bottom: this.bottom,
        right: this.right,
        top: "initial",
        left: "initial"
      })
      this.set("_show", false)
    },

    // 以下mixin实现

    // 置顶实现
    toTop: function(evt) {},
    // 显示隐藏切换
    connectToggle: function() {},

    /**************************
     *
     *   拖拽  Starts
     */

    /**
     * 处理拖拽事件
     */
    handleDragEvent: function(){
      if(!this.canDrag){
        return;
      }
      //移动端手指的滑动开始事件
      this.domNode.addEventListener('touchstart', this.touchstartEvent.bind(this));
      //移动端手指的滑动事件
      this.domNode.addEventListener('touchmove', this.touchmoveEvent.bind(this), {
        passive: false});
      this.handleDragSuccess = true;
      this.dragInfo = {};
    },

    /**
     * 触摸开始
     */
    touchstartEvent : function(e) {
      // 记录起点
      this.dragInfo.startPoint = this.getDragPoint(e);
      // 重置上次move的点
      this.dragInfo.startPoint = this.dragInfo.startPoint;
      this.drag_initBoundary();
    },

    /**
     * 滑动
     */
    touchmoveEvent: function(e){
      //阻止浏览器默认事件
      e.preventDefault();
      var curPoint = this.getDragPoint(e);// 当前点
      // 和起点比,移动的距离,大于0向下拉,小于0向上拉
      var moveY = curPoint.y - this.dragInfo.startPoint.y;
      // 和起点比,移动的距离,大于0向右拉,小于0向左拉
      var moveX = curPoint.x - this.dragInfo.startPoint.x;

      //置顶按钮位置 Y
      var top = this.dragInfo.originPosition.y;
      //置顶按钮位置 X
      var left = this.dragInfo.originPosition.x;

      //top位置计算
      if(moveY > 0){
        top += moveY;
      }else{
        top += moveY;
      }

      top = this.drag_checkTopBoundary(top);

      //left位置计算
      if(moveX > 0){
        left += moveX;
      }else{
        left += moveX;
      }

      left = this.drag_checkLeftBoundary(left);

      domStyle.set(this.domNode, {
        top: top + "px",
        left: left + "px",
        bottom: 'initial',
        right: 'initial',
      });

      this.dragInfo.lastPoint = curPoint;
    },

    /**
     * 初始化边界信息
     */
    drag_initBoundary: function(){
      //拖拽原始位置信息
      this.dragInfo.originPosition = domGeometry.position(this.domNode);
      //边界信息
      this.dragInfo.boundary = {
        maxTop: win.getBox().h - this.dragInfo.originPosition.h,
        minTop: 0,
        maxLeft: win.getBox().w - this.dragInfo.originPosition.w,
        minLeft: 0
      };
    },

    /**
     * 顶部/底部边界检测
     */
    drag_checkTopBoundary: function(top){
      var boundaryInfo = this.dragInfo.boundary;
      if(top < boundaryInfo.minTop){
        top = boundaryInfo.minTop;
      }
      if(top > boundaryInfo.maxTop){
        top = boundaryInfo.maxTop;
      }
      return top;
    },

    /**
     * 左侧/右侧边界检测
     */
    drag_checkLeftBoundary: function(left){
      var boundaryInfo = this.dragInfo.boundary;
      if(left < boundaryInfo.minLeft){
        left = boundaryInfo.minLeft;
      }
      if(left > boundaryInfo.maxLeft){
        left = boundaryInfo.maxLeft;
      }
      return left;
    },

    /**
     * 获取拖拽点的位置
     */
    getDragPoint :function (e) {
      return {
        x: e.touches ? e.touches[0].pageX : e.clientX,
        y: e.touches ? e.touches[0].pageY : e.clientY
      }
    },

    /**
     * 移除拖拽事件
     */
    destoryDragEvent: function(){
      if(!this.canDrag){
        return;
      }
      this.domNode.revomeEventListener('touchstart', this.touchstartEvent.bind(this));
      this.domNode.revomeEventListener('touchmove', this.touchmoveEvent.bind(this), {
        passive: false});
    }

    /**
     *   拖拽  Ends
     *
     *************************/
  })
})
