define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var rowsize = util.getUrlParameter(params, "rowsize")
    var cateid = util.getUrlParameter(params, "cateid")
    var scope = util.getUrlParameter(params, "scope")

    var html ='<div ' +
      'data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList" ' +
      'data-dojo-mixins="sys/mportal/mobile/card/SimpleItemListMixin" ' +
      "data-dojo-props=\"url:'/km/smissive/km_smissive_main/kmSmissiveMainPortlet.do?method=getlatest&mPortal=true&fdCategoryId=!{cateid}&rowsize=!{rowsize}&scope=!{scope}',lazy:false\">" +
      "</div>"
   
    html = util.urlResolver(html, {
      rowsize: rowsize,
      cateid: cateid,
      scope: scope
    })

    load({
      html: html
    })
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: [

    ]
  }
})