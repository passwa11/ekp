define(["dojo/_base/declare", 
        "dojo/store/Memory",
        "mui/i18n/i18n!km-archives:lbpm.approval.my",
        "mui/i18n/i18n!km-archives:lbpm.approved.my"]
		, function(declare, Memory, Msg1, Msg2) {
  return declare("km.archives.mobile.approval.indexListNavMixin", null, {
    store: new Memory({
      data:[ 
	        	{ 
	        		url : "/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=data&q.j_path=%2Foperation%2FmyAppraiseDetails&q.mydoc=approval&orderby=docCreateTime&ordertype=down",
	        		text : Msg1["lbpm.approval.my"]
	        	},
	            {
	         		url : "/km/archives/km_archives_appraise/kmArchivesAppraise.do?method=data&q.j_path=%2Foperation%2FmyAppraiseDetails&q.mydoc=approved&orderby=docCreateTime&ordertype=down",
	         		text:  Msg2["lbpm.approved.my"]
	         	}
     	   ]
    })
  })
})