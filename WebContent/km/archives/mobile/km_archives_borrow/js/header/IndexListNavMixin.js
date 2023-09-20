define(["dojo/_base/declare", 
        "dojo/store/Memory",
        "mui/i18n/i18n!km-archives:lbpm.approval.my",
        "mui/i18n/i18n!km-archives:lbpm.approved.my",
        "mui/i18n/i18n!km-archives:py.WoTiJiaoDe"]
		, function(declare, Memory, Msg1, Msg2, Msg3) {
  return declare("km.archives.mobile.borrow.indexListNavMixin", null, {
    store: new Memory({
      data:[ 
	        	{
	        		url : "/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=data&q.mydoc=approval&orderby=docCreateTime&ordertype=down",
	        		text : Msg1["lbpm.approval.my"]
	        	},
	            {
	        		url : "/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=data&q.mydoc=approved&orderby=docCreateTime&ordertype=down",
	         		text:  Msg2["lbpm.approved.my"]
	         	},
	            {
	        		url : "/km/archives/km_archives_borrow/kmArchivesBorrow.do?method=data&q.mydoc=create&orderby=docCreateTime&ordertype=down",
	         		text:  Msg3["py.WoTiJiaoDe"]
	         	}
     	   ]
    })
  })
})