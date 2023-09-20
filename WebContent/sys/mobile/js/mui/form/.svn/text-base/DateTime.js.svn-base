define([
  "dojo/_base/declare",
  "mui/form/_SelectBase",
  "dojo/query",
  "mui/datetime/_DateMixin",
  "mui/datetime/_DateMixin7",
  "mui/datetime/_TimeMixin",
  "mui/datetime/_DateTimeMixin",
  "dojo/_base/lang",
  "dojo/topic",
  "mui/form/_CompositeContainedMixin",
], function (
  declare,
  _SelectBase,
  query,
  _DateMixin,
  _DateMixin7,
  _TimeMixin,
  _DateTimeMixin,
  lang,
  topic,
  _CompositeContainedMixin
) {
  var _field = declare(
    "mui.form.DateTime",
    [_SelectBase, _CompositeContainedMixin],
    {
      // 在明细表删除行操作中，需要更新索引的属性
      needToUpdateAttInDetail: ["valueField", "nameField"],

      nameField: null,

      value: "",

      VALUE_CHANGE: "/mui/form/datetime/change",

      opt: true,

      tipMsg: "",

      optClass: "muis-to-right",

      muiSingleRowViewClass: "muiSelInput",

      isValidate: false,

      dimension:  "",

      buildRendering: function () {
        this.inherited(arguments);
        if (this.getParent())
          if (this.getParent().validateDate) this.isValidate = true;
      },

      _setValueAttr: function (value) {
        var changed = value != this.value;
        this.inherited(arguments);
        var isPublish = true;
        if (this.isValidate) {
          if (this.getParent().validateDate(this)) {
            // 值发生改变发出事件
            if (changed) topic.publish(this.VALUE_CHANGE, this);
          }
        } else {
          // 值发生改变发出事件
          if (changed) topic.publish(this.VALUE_CHANGE, this);
        }
      },
    }
  );

  // 对外方法
  var exports = {
	selectDate7: function (event, fieldname, format, callback, type) {
      var dom = query('[name="' + fieldname + '"]');
      if (dom.length == 0) return;
      var date = new declare([_field, _DateMixin7])({
        valueDom: dom[0],
        value: dom[0].value,
      });
      date.openDateTime();
    },
    selectDate: function (event, fieldname, format, callback, type) {
      var dom = query('[name="' + fieldname + '"]');
      if (dom.length == 0) return;
      var date = new declare([_field, _DateMixin])({
        valueDom: dom[0],
        value: dom[0].value,
      });
      date.openDateTime();
    },

    selectTime: function (event, fieldname, format, callback, type) {
      var dom = query('[name="' + fieldname + '"]');
      if (dom.length == 0) return;
      var date = new declare([_field, _TimeMixin])({
        valueDom: dom[0],
        value: dom[0].value,
      });
      date.openDateTime();
    },

    selectDateTime: function (event, fieldname, format, callback, type) {
      var dom = query('[name="' + fieldname + '"]');
      if (dom.length == 0) return;
      var date = new declare([_field, _DateTimeMixin])({
        valueDom: dom[0],
        value: dom[0].value,
      });
      date.openDateTime();
    },
  };

  return lang.mixin(_field, exports);
});
