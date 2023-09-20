define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var html =
      '<div data-dojo-type="sys/mportal/mobile/extend/Mphtml" data-dojo-props="htmlId:\'!{htmlId}\'"></div>'
    html = util.urlResolver(html, {
      htmlId: util.getUrlParameter(params, "htmlId")
    })
    load(html)
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    }
  }
})
