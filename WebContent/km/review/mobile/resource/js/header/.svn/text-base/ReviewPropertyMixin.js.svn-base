/**
 * 筛选静态数据源
 */
define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"mui/util",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.status",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docDraft",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docReject",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docPending",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docPublish",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docFeedback",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docAbandoned",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docCreatetime",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docEndTime"
], function(declare,lang,util,msg1,msg2,msg3,msg4,msg5,msg6,msg7,msg8,msg9) {
  return declare("km.review.ReviewPropertyMixin", null, {
    modelName: "com.landray.kmss.km.review.model.KmReviewTemplate",
    filters: [
      {
        filterType: "FilterRadio",
        name: "docStatus",
        subject: msg1['mui.kmReviewMain.status'],
        options: [
          {name: msg2['mui.kmReviewMain.docDraft'], value: "10"},
          {name: msg3['mui.kmReviewMain.docReject'], value: "11"},
          {name: msg4['mui.kmReviewMain.docPending'], value: "20"},
          {name: msg5['mui.kmReviewMain.docPublish'], value: "30"},
          {name: msg6['mui.kmReviewMain.docFeedback'], value: "31"},
          {name: msg7['mui.kmReviewMain.docAbandoned'], value: "00"}
//          {name: "过期", expr: "publishTime=2008"}
        ]
      },
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docCreateTime",
          subject: msg8['mui.kmReviewMain.docCreatetime']
      },
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docPublishTime",
          subject: msg9['mui.kmReviewMain.docEndTime']
      }
    ]
  })
})
