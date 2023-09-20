/**
 * 筛选静态数据源
 */
define(["dojo/_base/declare","mui/i18n/i18n!sys-lbpmext-authorize:*"], function(declare, Msg) {
  return declare("sys.lbpmext.authorize.LbpmAuthorizePropertyMixin", null, {
    filters: [
      {
        filterType: "FilterRadio",
        name: "fdAuthorizeType",
        subject: ""+Msg['lbpmAuthorize.fdAuthorizeType']+"",
        options: [
          {name: ""+Msg['lbpmAuthorize.fdAuthorize.process']+"", value: "0"},
          {name: ""+Msg['lbpmAuthorize.fdAuthorize.reading']+"", value: "1"},
          {name: ""+Msg['lbpmAuthorize.fdAuthorize.proxy']+"", value: "2"}
        ]
      },
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "fdStartTime",
          subject: ""+Msg['lbpmAuthorize.fdStartTime']+""
      },
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "fdEndTime",
          subject: ""+Msg['lbpmAuthorize.fdEndTime']+""
      }
    ]
  })
})
