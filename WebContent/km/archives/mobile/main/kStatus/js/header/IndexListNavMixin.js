define(["dojo/_base/declare", 
        "dojo/store/Memory",
        "mui/i18n/i18n!km-archives:py.SuoYouDangAn",
        "mui/i18n/i18n!km-archives:enums.doc_status.10",
        "mui/i18n/i18n!km-archives:mui.borrow.nav.approval",
        "mui/i18n/i18n!km-archives:enums.doc_status.11",
        "mui/i18n/i18n!km-archives:enums.doc_status.30"]
		, function(declare, Memory, Msg1, Msg2, Msg3, Msg4, Msg5) {
  return declare("km.archives.mobile.create.indexListNavMixin", null, {
    store: new Memory({
      data:[ 
	        	{ 
	        		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=all&orderby=fdFileDate&ordertype=down",
	        		text : Msg1["py.SuoYouDangAn"],
	        		headerTemplate : "/km/archives/mobile/main/kStatus/js/header/AllDocHeaderTemplate.js"
	        	},
	            {
	         		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=all&q.docStatus=10&orderby=fdFileDate&ordertype=down",
	         		text:  Msg2["enums.doc_status.10"],
	        		headerTemplate : "/km/archives/mobile/main/kStatus/js/header/DraftDocHeaderTemplate.js"
	         	},
	            {
	         		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=all&q.docStatus=20&orderby=fdFileDate&ordertype=down",
	         		text:  Msg3["mui.borrow.nav.approval"]
	         	},
	            {
	         		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=all&q.docStatus=11&orderby=fdFileDate&ordertype=down",
	         		text:  Msg4["enums.doc_status.11"]
	         	},
	            {
	         		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=data&q.mydoc=all&q.docStatus=30&orderby=fdFileDate&ordertype=down",
	         		text:  Msg5["enums.doc_status.30"]
	         	}
     	   ]
    })
  })
})