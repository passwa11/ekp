define([
  "dojo/_base/declare",
  "dojo/dom-class",
  "mui/datetime/_EditDateTimeMixin",
  "mui/i18n/i18n!sys-mobile:mui"
], function(declare, domClass, _EditDateTimeMixin, msg) {
  return declare("mui.datetime._TimeMixin", _EditDateTimeMixin, {
    type: "time",

    tmplStr:
      '<div data-dojo-type="mui/datetime/TimePicker" id="_time_picker" data-dojo-props="value:\'!{timeValue}\', minuteStep:\'!{minuteStep}\'"></div>',

    title: msg["mui.time.select"],

    _contentExtendClass: "muiTimeDialogDisContent",

    _buildValue: function() {
      this.inherited(arguments)
      domClass.add(this.inputContent, "muiTimeInputContnet")
    }
  })
})
