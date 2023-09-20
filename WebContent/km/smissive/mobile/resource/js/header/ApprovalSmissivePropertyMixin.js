/**
 * “待我审的” 筛选静态数据源
 */
define(["dojo/_base/declare", "mui/i18n/i18n!km-smissive:mobile"], function(declare, msg) {
  return declare("km.smissive.ApprovalPropertyMixin", null, {
    modelName: "com.landray.kmss.km.smissive.model.KmSmissiveTemplate",
    filters: [
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docCreateTime",
          subject: msg['mobile.kmSmissive.draftDate']
      }
    ]
  })
})
