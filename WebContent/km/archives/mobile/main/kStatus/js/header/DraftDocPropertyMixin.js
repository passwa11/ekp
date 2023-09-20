/**
 * 筛选静态数据源
 */
define(["dojo/_base/declare", "mui/i18n/i18n!km-archives:kmArchivesMain"], function(declare, msg) {
  return declare("km.archives.ApplyAssetPropertyMixin", null, {
    modelName: "com.landray.kmss.km.archives.model.KmArchivesCategory",
    filters: [
          {
  	        filterType: "FilterSearch",
  	        name: "docSubject",
  	        subject: msg['kmArchivesMain.docSubject']
  	    },
  	    {
  		    filterType: "FilterDatetime",
  		    type: "date",
  		    name: "fdFileDate",
  		    subject: msg['kmArchivesMain.fdFileDate']
  		}
    ]
  })
})