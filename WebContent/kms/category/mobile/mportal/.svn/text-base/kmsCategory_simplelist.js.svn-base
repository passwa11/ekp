define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var categoryId = util.getUrlParameter(params, "categoryId")
    var rowsize = util.getUrlParameter(params, "rowsize")
    var type = util.getUrlParameter(params, "type")
    type = Number(type) + 1;
    
    var html =
      '<div data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList" ' +
      'data-dojo-mixins="sys/mportal/mobile/card/CardListMixin" ' +
      "data-dojo-props=\"url:'/kms/category/kms_category_main_ui/kmsCategoryIndex.do?method=listChildren&orderby=docPublishTime&ordertype=down&rowsize=!{rowsize}&q.docCategory=!{categoryId}&q.template=!{type}&q.docStatus=30&fromType=moblie',lazy:false,pic:false\"></div>"

    html = util.urlResolver(html, {
      rowsize: rowsize,
      categoryId: categoryId,
      type: type
    })
    load({
      html: html
    })
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    // 声明依赖--非mui和sys/mportal下的依赖都要在此处声明
    dependences: [
    ]
  }
})
