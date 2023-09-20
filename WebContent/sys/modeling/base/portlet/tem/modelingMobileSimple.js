define(["mui/util"], function(util) {
  function portletLoad(params, load) {

    var rowsize = util.getUrlParameter(params, "rowsize")
    var cfgId = util.getUrlParameter(params, "cfgId")
    var html =
      "<div data-dojo-type='sys/mportal/mobile/card/JsonStoreCardList' " +
      "data-dojo-mixins='sys/mportal/mobile/card/CardListMixin' " +
      "data-dojo-props=\"url:'/sys/modeling/main/modelingPortletCfg.do?method=listPortlet&cfgId=!{cfgId}&rowsize=!{rowsize}',lazy:false,pic:false\">" +
      "</div>"
    html = util.urlResolver(html, {
      rowsize: rowsize,
      cfgId:cfgId
    })
    load(html)
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    }
  }
})
