define(["dojo/_base/declare", "dojo/topic"], function (declare, topic) {
  return declare("km.forum.SwitchMixin", null, {
    property: "",
    leftLabel: "",
    rightLabel: "",

    buildRendering: function () {
      this.inherited(arguments);
      this.propDom.value = this.value == "on" ? 1 : 0;
    },
    //添加事件,供外部监听
    onStateChanged: function (/*String*/ newState) {
      this.inherited(arguments);
      var _value = newState == "on" ? 1 : 0;
      this.propDom.value = _value;
      topic.publish("km/forum/statChanged", this, _value);
    },
  });
});
