define([
  "dojo/dom",
  "dojo/request/notify",
  "dojo/dom-class",
  "dojo/dom-style"
], function(dom, notify, domClass, domStyle) {
  var hideDone = false
  var canHide = false
  var ajaxRequestConut = 0

  var hide = function() {
    if (canHide && 0 <= ajaxRequestConut) {
      setTimeout(function(){
    	  	var loading = dom.byId("pageLoading")
    	  	if(loading){
    	  		domStyle.set(loading, "display", "none")
        	  	hideDone = true
    	  	}
    	  	
      },11)
      
    }
  }

  // var opcity = function() {
  //     var loading = dom.byId("pageLoading");
  //     domClass.add(loading, "opacity");
  // }

  notify("start", function() {
    if (hideDone) return
    ajaxRequestConut++
  })

  notify("stop", function() {
    if (hideDone) return
    ajaxRequestConut--
    hide()
  })

  return {
    hide: function() {
      canHide = true
      hide()
    },
    isHide: function() {
      return hideDone
    },
    opcity: function() {
        opcity();
    }
  }
})
