/**
 * 筛选静态数据源
 */
define(["dojo/_base/declare", "mui/i18n/i18n!km-smissive:mobile"], function(declare, msg) {
  return declare("sys.news.NewsPropertyMixin", null, {
	modelName: "com.landray.kmss.km.smissive.model.KmSmissiveTemplate",  
    filters: [
      {
        filterType: "FilterRadio",
        name: "docStatus",
        subject: msg['mobile.kmSmissive.status'],
        options: [
        	 {name: msg['mobile.kmSmissive.status.draft'], value: "10"},
             {name: msg['mobile.kmSmissive.status.pending'], value: "20"},
             {name: msg['mobile.kmSmissive.status.overrule'], value: "11"},
             {name: msg['mobile.kmSmissive.status.publish'] , value: "30"}
        ]
      },
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docCreateTime",
          subject: msg['mobile.kmSmissive.draftDate']
      },
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docPublishTime",
          subject: msg['mobile.kmSmissive.releaseDate']
      }
    ]
  })
})
