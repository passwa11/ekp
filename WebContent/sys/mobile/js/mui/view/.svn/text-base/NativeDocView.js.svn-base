define(["dojo/_base/declare", "mui/NativeView", "dojo/topic", "./AddBottomTipMixins"], function (
  declare,
  NativeView,
  topic,
  AddBottomTipMixins
) {
  return declare("mui.view.NativeDocView", [NativeView, AddBottomTipMixins], {
    buildRendering: function () {
      this.inherited(arguments);
      this.addBottomTip(this.containerNode)
      window.addEventListener("scroll", this.scrollEvent.bind(this));
      this.subscribe("/mui/list/toTop", "toTop");
    },

    //滑动到顶部
    toTop: function (obj, evt) {
      var y = 0;
      if (evt) {
        y = evt.y || 0;
      }

      var end = -y;
      var start = this.getPos().y;
      var diff = end - start; // 差值
      var t = 300; // 时长 300ms
      var rate = 30; // 周期 30ms
      var count = 10; // 次数
      var step = diff / count; // 步长
      var i = 0; // 计数
      var timer = window.setInterval(function () {
        if (i <= count - 1) {
          start += step;
          i++;
          window.scrollTo(0, start);
        } else {
          window.clearInterval(timer);
        }
      }, rate);
    },

    scrollEvent: function () {
      var scrollTop = this.getPos().y;
      if (scrollTop >= 50) {
        topic.publish("mui/view/showTop");
      } else {
        topic.publish("mui/view/hideTop");
      }
    },

    /**
     * 进行空实现 #
     * @param srcObj
     * @param evt
     */
    handleToTopTopic: function(srcObj, evt) {
      if(window.console){
        window.console.log("function is null");
      }
    }
  });
});
