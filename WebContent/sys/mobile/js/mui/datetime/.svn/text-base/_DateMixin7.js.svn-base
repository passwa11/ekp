define([
  "dojo/_base/declare",
  "mui/datetime/_EditDateTimeMixin",
  "mui/i18n/i18n!sys-mobile:mui"
], function(declare, _EditDateTimeMixin, msg) {
  return declare("mui.datetime._DateMixin", [_EditDateTimeMixin], {
    type: "date",

    tmplStr:
      '<div data-dojo-type="mui/datetime/DatePicker7" id="_date_picker" data-dojo-props="value:\'!{dateValue}\'"></div>',

    title: msg["mui.date.select"],

    _contentExtendClass: "muiDateDialogDisContent"
  })
})
