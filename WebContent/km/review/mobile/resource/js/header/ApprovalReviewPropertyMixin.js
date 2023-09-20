/**
 * “待我审的” 筛选静态数据源
 */
define(["dojo/_base/declare","mui/i18n/i18n!km-review:mui.kmReviewMain.docCreatetime"], function(declare,msg) {
  return declare("km.review.ReviewPropertyMixin", null, {
    modelName: "com.landray.kmss.km.review.model.KmReviewTemplate",
    filters: [
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docCreateTime",
          subject: msg['mui.kmReviewMain.docCreatetime']
      }
    ]
  })
})
