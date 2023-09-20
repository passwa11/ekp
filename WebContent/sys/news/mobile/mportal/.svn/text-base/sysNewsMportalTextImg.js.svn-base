define(["mui/util"], function(util) {
  function portletLoad(params, load) {

    var cateid = util.getUrlParameter(params, "cateid");
    var rowsize = util.getUrlParameter(params, "rowsize");
    var scope = util.getUrlParameter(params, "scope"); 
    
    var html = '<div class="ComplexRItemListNews"' 
   	 +'data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList"'
   	 +'data-dojo-mixins="sys/mportal/mobile/card/ComplexRListMixin"'
   	 +"data-dojo-props=\"url:'/sys/news/sys_news_main/sysNewsMainPortlet.do?method=getNewsMportal&mPortal=true&cateid=!{cateid}&rowsize=!{rowsize}&scope=!{scope}',lazy:false\">"
   	 +'</div>';
    
    html = util.urlResolver(html, {
      cateid: cateid,
      rowsize: rowsize,
      scope: scope
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
