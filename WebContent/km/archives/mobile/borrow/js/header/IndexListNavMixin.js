define(["dojo/_base/declare", 
        "dojo/store/Memory",
        "mui/i18n/i18n!km-archives:py.WoDeJieYue"]
		, function(declare, Memory, Msg1) {
  return declare("km.archives.mobile.borrow.indexListNavMixin", null, {
    store: new Memory({
      data:[ 
	        	{ 
	        		url: '/km/archives/km_archives_details/kmArchivesDetails.do?method=data&q.mydoc=all&orderby=fdReturnDate&ordertype=down',
	        		text : Msg1["py.WoDeJieYue"]
	        	}
        ]
    })
  })
})