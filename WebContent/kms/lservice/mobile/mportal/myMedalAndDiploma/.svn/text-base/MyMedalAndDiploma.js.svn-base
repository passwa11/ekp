define(["mui/util"], function(util) {
  function portletLoad(params, load) {
	//选中的模块
    var selectModuleStr = util.getUrlParameter(params, "selectModules");
    var showMedal = false;
    var showDiploma = false;

    if(selectModuleStr){
    	var selectModules =  selectModuleStr.split(",");
    	for(var i=0; i < selectModules.length; i++){
    		var module = selectModules[i];
    		if( "medal" == module){
    			showMedal = true;
    		}
    		if( "diploma" == module){
    			showDiploma = true;
    		}
    	}
    }    

    var html =
      '<div data-dojo-type="kms/lservice/mobile/mportal/myMedalAndDiploma/MyMedalAndDiplomaNode" ' +
      'class="muiMyMedalAndDiploma" ' +
      "data-dojo-props=\"showMedal:!{showMedal},showDiploma:!{showDiploma}\">" +
      "</div>"
      
    
    html = util.urlResolver(html, {
    	showMedal: showMedal,
    	showDiploma: showDiploma
    })
    
    load({html: html, cssUrls: []})
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: [
     
    ]
  }
})
