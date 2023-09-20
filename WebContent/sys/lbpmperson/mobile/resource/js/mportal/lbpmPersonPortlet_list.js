define(["mui/util"], function(util) {
  function portletLoad(params, load) {

	var key = util.getUrlParameter(params, "key");
    var rowsize = util.getUrlParameter(params, "rowsize");
    
    var html = '<div class="muiPortalProcessSimple"' 
   	 +'data-dojo-type="mui/list/HashJsonStoreList"'
   	 +'data-dojo-mixins="sys/lbpmperson/mobile/resource/js/list/LbpmPersonItemListMixin"'
   	 +"data-dojo-props=\"url:'/sys/lbpmperson/sys_lbpmperson_myprocess/SysLbpmPersonMyProcess.do?method=list4Mobile&q.key=!{key}&rowsize=!{rowsize}&orderby=fdCreateTime&ordertype=down',lazy:false\">"
   	 +'</div>';
       
    
    html = util.urlResolver(html, {
      key: key,
      rowsize: rowsize
    });
    
    load({
        html: html,
        cssUrls: ['/sys/lbpmperson/mobile/resource/css/list.css']
    }); 
    
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: []
  }
})