/**
 * “待我审的” 筛选静态数据源
 */
define(["dojo/_base/declare","mui/i18n/i18n!sys-news:sysNewsPublishCategory.fdImportance"], function(declare,fdImportanceMsg) {
  return declare("sys.news.AllNewsPropertyMixin", null, {
    modelName: "com.landray.kmss.sys.news.model.SysNewsTemplate",
    filters: [
      {
          filterType: "FilterRadio",
          name: "fdImportance",
          subject: fdImportanceMsg['sysNewsPublishCategory.fdImportance.importance'],
          options: [
            {name: fdImportanceMsg["sysNewsPublishCategory.fdImportance.very.importance"], value: "1"},
            {name: fdImportanceMsg["sysNewsPublishCategory.fdImportance.importance"], value: "2"},
            {name: fdImportanceMsg["sysNewsPublishCategory.fdImportance.common"], value: "3"}
          ]
      }
    ]
  })
})
