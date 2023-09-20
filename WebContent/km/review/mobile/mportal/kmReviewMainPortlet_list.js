define(["mui/util"], function(util) {
  function portletLoad(params, load) {

	var fdTemplate = util.getUrlParameter(params, "fdTemplate");
	var mydoc = util.getUrlParameter(params, "mydoc");
    var rowsize = util.getUrlParameter(params, "rowsize");
    
    var html = '<div class="muiPortalProcessSimple"' 
   	 +'data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList"'
   	 +'data-dojo-mixins="sys/mportal/mobile/card/ProcessListMixin"'
   	 +"data-dojo-props=\"url:'/km/review/km_review_index/kmReviewIndex.do?method=list&q.fdTemplate=!{fdTemplate}&q.mydoc=!{mydoc}&rowsize=!{rowsize}&orderby=docCreateTime&ordertype=down',lazy:false\">"
   	 +'</div>';
       
    
    html = util.urlResolver(html, {
      fdTemplate: fdTemplate,
      mydoc: mydoc,
      rowsize: rowsize
    });
    
    load({
        html: html,
        cssUrls: []
    }); 
    
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: []
  }
})