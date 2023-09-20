define(["mui/util"], function(util) {
  function portletLoad(params, load) {
	//选中的模块
    var selectModuleStr = util.getUrlParameter(params, "selectModules");
    var showBookmark = false;
    var showNote = false;
    var showEvaluation = false;

    if(selectModuleStr){
    	var selectModules =  selectModuleStr.split(",");
    	for(var i=0; i < selectModules.length; i++){
    		var module = selectModules[i];
    		if( "bookmark" == module){
    			showBookmark = true;
    		}
    		if( "note" == module){
    			showNote = true;
    		}
    		if( "evaluation" == module){
    			showEvaluation = true;
    		}
    	}
    }    

    var html =
      '<div data-dojo-type="kms/lservice/mobile/mportal/myBookmarkAndNoteAndEvaluation/MyBookmarkAndNoteAndEvaluationList" ' +
      "data-dojo-props=\"showBookmark:!{showBookmark},showNote:!{showNote},showEvaluation:!{showEvaluation}\">" +
      "</div>"
    
    html = util.urlResolver(html, {
    	showBookmark: showBookmark,
    	showNote: showNote,
    	showEvaluation: showEvaluation,
    })
    
    load({html: html, cssUrls: ["/kms/lservice/mobile/mportal/myBookmarkAndNoteAndEvaluation/myBookmarkAndNoteAndEvaluation.css"]})
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: [
     
    ]
  }
})
