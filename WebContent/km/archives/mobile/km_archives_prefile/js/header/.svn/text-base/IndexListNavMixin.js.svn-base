define(["dojo/_base/declare", 
        "dojo/store/Memory",
        "mui/i18n/i18n!km-archives:py.YuGuiDangKu"]
		, function(declare, Memory, Msg1) {
  return declare("km.archives.mobile.prefile.indexListNavMixin", null, {
    store: new Memory({
      data:[
	            {
	         		url : "/km/archives/km_archives_main/kmArchivesMain.do?method=listPreFile&q.docStatus=10&q.j_path=%2FpreFileArchives&orderby=fdFileDate&ordertype=down",
	         		text:  Msg1["py.YuGuiDangKu"]
	         	}
     	   ]
    })
  })
})