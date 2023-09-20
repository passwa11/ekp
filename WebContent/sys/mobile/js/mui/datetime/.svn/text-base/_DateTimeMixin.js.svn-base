define([
  "dojo/_base/declare",
  "mui/datetime/_EditDateTimeMixin",
  "mui/i18n/i18n!sys-mobile:mui"
], function(declare, _EditDateTimeMixin, msg) {
  return declare("mui.datetime._DateTimeMixin", [_EditDateTimeMixin], {
    type: "datetime",

    tmplStr:
      '<div data-dojo-type="mui/datetime/DatePicker" id="_date_picker" data-dojo-props="value:\'!{dateValue}\'"></div><div data-dojo-type="mui/datetime/TimePicker" id="_time_picker" data-dojo-props="value:\'!{timeValue}\', minuteStep:\'!{minuteStep}\'"></div>',

    title: msg["mui.datetime.select"],

    _contentExtendClass: "muiDateTimeDialogDisContent"
  })
})
