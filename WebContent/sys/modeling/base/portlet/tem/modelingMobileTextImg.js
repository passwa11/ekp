define(["mui/util"], function(util) {
  function portletLoad(params, load) {

    var rowsize = util.getUrlParameter(params, "rowsize");
    var cfgId = util.getUrlParameter(params, "cfgId");
    
    var html = '<div class="ComplexRItemListNews"'
        +'data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList"'
   	 +'data-dojo-mixins="sys/mportal/mobile/card/ComplexRListMixin"'
   	 +"data-dojo-props=\"url:'/sys/modeling/main/modelingPortletCfg.do?method=listPortlet&cfgId=!{cfgId}&rowsize=!{rowsize}',lazy:false\">"
   	 +'</div>';

    html = util.urlResolver(html, {
      cfgId: cfgId,
      rowsize: rowsize
    });
    
    load(html);
    
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load);
    },
    dependences: [
      "/sys/mportal/mobile/card/ComplexLListMixin.js"
    ]
  }
})
