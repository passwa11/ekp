define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    // 刷新时间
    var refreshtime = util.getUrlParameter(params, "refreshtime")
    var showSubject = util.getUrlParameter(params, "showSubject")
    var fdIds = util.getUrlParameter(params, "fdIds")
    var height = util.getUrlParameter(params, "height")

    var html =
      '<div data-dojo-type="mui/picslide/PicSlide" ' +
      "data-dojo-props=\"picTensile:true,refreshTime:'!{refreshtime}',showType:'dot',showSubject:'!{showSubject}',openByProxy:true,url:'/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=getImg&fdIds=!{fdIds}&orderby=docAlterTime&ordertype=down',height:'!{height}px',width:'100%'\" " +
      'data-dojo-mixins="sys/mportal/mobile/header/PicSlideMixin">' +
      "</div>"
    html = util.urlResolver(html, {
      height: height,
      fdIds: fdIds,
      showSubject: showSubject,
      refreshtime: refreshtime
    })
    load(html)
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    }
  }
})
