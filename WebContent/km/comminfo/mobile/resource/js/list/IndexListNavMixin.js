define(["dojo/_base/declare", 
        "dojo/store/Memory",
        "mui/i18n/i18n!km-comminfo:module.km.comminfo"], function(declare, Memory, Msg1) {
  return declare("km.asset.mobile.applyAsset.indexListNavMixin", null, {
    store: new Memory({
      data:[ 
	        	{ 
	        		//url : "/km/comminfo/mobile/resource/js/list/_test.json", 
	        		url : "/km/comminfo/km_comminfo_main/kmComminfoMainIndex.do?method=listMobile&rowsize=500&orderby=kmComminfoMain.docCategory.fdOrder asc,kmComminfoMain.docCreateTime desc", 
	        		text : Msg1["module.km.comminfo"]
	        	}
        ]
    })
  })
})