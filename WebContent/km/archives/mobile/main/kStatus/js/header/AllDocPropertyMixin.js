/**
 * （待我审的）筛选静态数据源
 */
define(["dojo/_base/declare", "mui/i18n/i18n!km-archives:kmArchivesMain"], function(declare, msg) {
  return declare("km.archives.AllDocPropertyMixin", null, {
    modelName: "com.landray.kmss.km.archives.model.KmArchivesCategory",
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
  		},
		{
	          filterType: "FilterRadio",
	          name: "kStatus",
	          subject: msg['kmArchivesMain.kStatus'],
	          options: [
	             {name: msg['kmArchivesMain.kStatus.library'], value: "library"}
	            ,{name: msg['kmArchivesMain.kStatus.expired'], value: "expire"}
	          ]
		}
    ]
  })
})