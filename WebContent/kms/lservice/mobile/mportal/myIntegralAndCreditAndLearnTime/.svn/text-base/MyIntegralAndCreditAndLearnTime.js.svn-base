define(["mui/util"], function(util) {
  function portletLoad(params, load) {
	//选中的模块
    var selectModuleStr = util.getUrlParameter(params, "selectModules");
    var layoutType = util.getUrlParameter(params, "layoutType");
    var showIntegral = false;
    var showCredit = false;
    var showLearnTime = false;
    var integralShowProp = "fdTotalScore";

    if(selectModuleStr){
    	var selectModules =  selectModuleStr.split(",");
    	for(var i=0; i < selectModules.length; i++){
    		var module = selectModules[i];
    		if( "integral_fdTotalScore" == module){
    			showIntegral = true;
    			integralShowProp = "fdTotalScore";
    		}
    		if( "integral_fdTotalRiches" == module){
    			showIntegral = true;
    			integralShowProp = "fdTotalRiches";	
    		}
    		if( "credit" == module){
    			showCredit = true;
    		}
    		if( "learnTime" == module){
    			showLearnTime = true;
    		}
    	}
    }    

    var html =
      '<div data-dojo-type="kms/lservice/mobile/mportal/myIntegralAndCreditAndLearnTime/MyIntegralAndCreditAndLearnTimeLayoutListNode" ' +
      "data-dojo-props=\"integralShowProp:'!{integralShowProp}',showIntegral:!{showIntegral},showCredit:!{showCredit},showLearnTime:!{showLearnTime}\">" +
      "</div>"
      
    if("number" == layoutType){
    	 html =
    	      '<div data-dojo-type="kms/lservice/mobile/mportal/myIntegralAndCreditAndLearnTime/MyIntegralAndCreditAndLearnTimeLayoutNumberNode" ' +
    	      "data-dojo-props=\"integralShowProp:'!{integralShowProp}',showIntegral:!{showIntegral},showCredit:!{showCredit},showLearnTime:!{showLearnTime}\">" +
    	      "</div>"
    }
    
    html = util.urlResolver(html, {
    	showIntegral: showIntegral,
    	showCredit: showCredit,
    	showLearnTime: showLearnTime,
    	integralShowProp: integralShowProp
    })
    
    load({html: html, cssUrls: ["/kms/lservice/mobile/mportal/myIntegralAndCreditAndLearnTime/myIntegralAndCreditAndLearnTime.css"]})
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: [
     
    ]
  }
})
