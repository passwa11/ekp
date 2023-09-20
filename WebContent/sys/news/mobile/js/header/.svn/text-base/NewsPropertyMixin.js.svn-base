/**
 * 筛选静态数据源
 */
define(["dojo/_base/declare","mui/i18n/i18n!sys-news:sysNewsPublishCategory.fdImportance", "mui/i18n/i18n!sys-news:mobile"],
		function(declare, fdImportanceMsg, msg) {
  return declare("sys.news.NewsPropertyMixin", null, {
	  modelName: "com.landray.kmss.sys.news.model.SysNewsTemplate",  
    filters: [              
      {
        filterType: "FilterRadio",
        name: "docStatus",
        subject: msg['mobile.sysNews.status'],
        options: [
          {name: msg['mobile.sysNews.status.draft'], value: "10"},
          {name: msg['mobile.sysNews.status.pending'], value: "20"},
          {name: msg['mobile.sysNews.status.overrule'], value: "11"},
          {name: msg['mobile.sysNews.status.publish'], value: "30"}
        ]
      },
      {
          filterType: "FilterRadio",
          name: "fdImportance",
          subject: fdImportanceMsg['sysNewsPublishCategory.fdImportance.importance'],
          options: [
            {name: msg['mobile.sysNews.noLimit'], value: ""},
            {name: fdImportanceMsg["sysNewsPublishCategory.fdImportance.very.importance"], value: "1"},
            {name: fdImportanceMsg["sysNewsPublishCategory.fdImportance.importance"], value: "2"},
            {name: fdImportanceMsg["sysNewsPublishCategory.fdImportance.common"], value: "3"}
          ]
      },             
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docPublishTime",
          subject: msg['mobile.sysNews.docPublishTime']
      }      
    ]
  })
})