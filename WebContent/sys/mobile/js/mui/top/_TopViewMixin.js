define(["dojo/_base/declare", "dojo/topic", "dojo/_base/lang"], function (
  declare,
  topic,
  lang
) {
  return declare("mui.list._TopViewMixin", null, {
    adjustDestination: "/mui/list/adjustDestination",

    listTop: "/mui/list/toTop",

    toTop: function (evt) {
      topic.publish(this.listTop, this);
    },

    connectToggle: function () {
      this.subscribe(
        this.adjustDestination,
        lang.hitch(function (srcObj, to, pos, dim) {
          var beShow =
            to.y < -5 && ((dim != null && dim.c.h >= dim.d.h) || dim == null);
          if (beShow && !this._show) {
            topic.publish("mui/view/addBottomTip");
          }
          this.show();
          if (!beShow && this._show) this.hide();
        })
      );
      this.subscribe("mui/view/showTop", "show");
      this.subscribe("mui/view/hideTop", "hide");
    },
  });
});
