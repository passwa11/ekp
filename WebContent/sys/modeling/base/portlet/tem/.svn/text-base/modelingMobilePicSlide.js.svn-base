define(["mui/util"], function(util) {
  function portletLoad(params, load) {

    var rowsize = util.getUrlParameter(params, "rowsize")
    var cfgId = util.getUrlParameter(params, "cfgId")
    var html =
      "<div data-dojo-type='mui/picslide/PicSlide' " +
      "data-dojo-mixins='sys/mportal/mobile/header/PicSlideMixin' " +
      "data-dojo-props=\" picTensile:true,showType:'dot',showSubject:true,openByProxy:true,url:'/sys/modeling/main/modelingPortletCfg.do?method=listPortlet&cfgId=!{cfgId}&rowsize=!{rowsize}',height:'260px',width:'100%'\">" +
      "</div>"
    html = util.urlResolver(html, {
      rowsize: rowsize,
      cfgId:cfgId,
      height: "260",
      refreshtime: "0",
      scope: "no",
      showSubject: "true"
    })
    load(html)
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    }
  }
})
