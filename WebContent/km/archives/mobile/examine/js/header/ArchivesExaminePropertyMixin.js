/**
 * 筛选静态数据源
 */
define(["dojo/_base/declare", "mui/i18n/i18n!km-archives:kmArchivesMain"], function(declare, msg) {
  return declare("km.archives.ArchivesExaminePropertyMixin", null, {
	  modelName: 'com.landray.kmss.km.archives.model.KmArchivesCategory',
    filters: [
        {
	        filterType: "FilterSearch",
	        name: "docNumber",
	        subject: msg['kmArchivesMain.docNumber']
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
