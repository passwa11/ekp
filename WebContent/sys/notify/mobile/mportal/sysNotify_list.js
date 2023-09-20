define(["mui/util"], function(util) {
  function portletLoad(params, load) {
	  
    var rowsize = util.getUrlParameter(params, "rowsize");
    var type = util.getUrlParameter(params, "type"); 
    
    var html = '<div class="muiPortalNotifySimple"' 
   	 +'data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList"'
   	 +'data-dojo-mixins="sys/notify/mobile/mportal/js/NotifyListMixin"'
   	 +"data-dojo-props=\"url:'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=mngList&rowsize=!{rowsize}',lazy:false,type:'!{type}'\">"
   	 +'</div>';
    
    html = util.urlResolver(html, {
      rowsize: rowsize,
      type: type
    });
    
    load({
    	html: html,
    	cssUrls: ["/sys/notify/mobile/mportal/css/notify.css"]
    });
    
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load);
    },
    dependences: [
      "/sys/notify/mobile/mportal/js/NotifyListMixin.js",
      "/sys/notify/mobile/mportal/js/item/NotifyItemMixin.js"
    ]
  }
})
