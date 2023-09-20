define(["mui/util"], function(util) {
	
  function portletLoad(params, load) {
	  
    var rowsize = util.getUrlParameter(params, "rowsize");
    var cateid = util.getUrlParameter(params, "cateid");
    var scope = util.getUrlParameter(params, "scope");
    var refreshtime = util.getUrlParameter(params, "refreshtime");
    var showSubject = util.getUrlParameter(params, "showSubject");
    var height = util.getUrlParameter(params, "height");
     
    var html = '<div data-dojo-type="mui/picslide/PicSlide" '
   	           +'data-dojo-mixins="sys/mportal/mobile/header/PicSlideMixin"'
   	           +"data-dojo-props=\"picTensile:true,openByProxy:true,refreshTime:'!{refreshtime}',showType:'dot',showSubject:'!{showSubject}',url:'/sys/news/sys_news_main/sysNewsMainPortlet.do?method=getNewsMportal&cateid=!{cateid}&rowsize=!{rowsize}&type=pic&scope=!{scope}',height:'!{height}px',width:'100%'\">"
   	           +'</div>';         
       
    html = util.urlResolver(html, {
        rowsize: rowsize,
        cateid: cateid,
        scope: scope,
        refreshtime: refreshtime,
        showSubject: showSubject,
        height: height
    });
    
    load({
        html: html,
        cssUrls: []
    });  
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load);
    },
    dependences: []
  };
  
})