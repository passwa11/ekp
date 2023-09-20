define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var rowsize = util.getUrlParameter(params, "rowsize")
    var type = util.getUrlParameter(params, "type")
    
    var html =
      '<div data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList" ' +
      'class="mui_portal_km_forum_list" ' +
      'data-dojo-mixins="km/forum/mobile/mportal/js/ForumListMixin" ' +
      "data-dojo-props=\"url:'/km/forum/km_forum/kmForumTopicIndex.do?method=listChildren&orderby=fdLastPostTime&ordertype=down&rowsize=!{rowsize}',lazy:false,type:\'!{type}\' \"></div>"

    html = util.urlResolver(html, {
      rowsize: rowsize,
      type: type
    })
    load({
      html: html,
      cssUrls: ["/km/forum/mobile/mportal/css/forum.css"]
    })
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    // 声明依赖--非mui和sys/mportal下的依赖都要在此处声明
    dependences: [
      "/km/forum/mobile/mportal/js/ForumListMixin.js",
      "/km/forum/mobile/resource/js/ForumTopicItemMportalMixin.js",
      "/km/forum/mobile/resource/js/MixContentBItemMixin.js"
    ]
  }
})
