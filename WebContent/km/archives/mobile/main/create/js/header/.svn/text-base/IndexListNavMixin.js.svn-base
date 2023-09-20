define(["dojo/_base/declare", 
        "dojo/store/Memory",
        "mui/i18n/i18n!km-archives:py.WoLuRuDe",
        "mui/i18n/i18n!km-archives:enums.doc_status.10"]
		, function(declare, Memory, Msg1, Msg2) {
  return declare("km.archives.mobile.create.indexListNavMixin", null, {
    store: new Memory({
      data:[ 
	        	{ 
	        		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=create&orderby=docCreateTime&ordertype=down",
	        		text : Msg1["py.WoLuRuDe"]
	        	},
	            {
	         		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=create&q.docStatus=10&orderby=docCreateTime&ordertype=down",
	         		text:  Msg2["enums.doc_status.10"]
	         	}
     	   ]
    })
  })
})