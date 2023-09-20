define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var type = util.getUrlParameter(params, "type")
    var rowsize = util.getUrlParameter(params, "rowsize")
    var categoryId = util.getUrlParameter(params, "categoryId")
    var forward = util.getUrlParameter(params, "forward")
    if ("0" == forward) {
      forward = ""
    }
    var orderby = "docPublishTime"
    var ordertype = "down"
    var docIsIntroduced = "0"

    if ("docPublishTime" == type) {
      orderby = "docPublishTime"
    } else if ("fdTotalCount" == type) {
      orderby = "fdTotalCount"
    }

    var html =
      '<div data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList" ' +
      'data-dojo-mixins="kms/knowledge/mobile/mportal/js/SummaryListMixin" ' +
      "data-dojo-props=\"url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&orderby=!{orderby}&ordertype=!{ordertype}&rowsize=!{rowsize}&dataType=pic&categoryId=!{categoryId}&q.docIsIntroduced=!{docIsIntroduced}&q.docStatus=30',lazy:false,forward:'!{forward}'\"></div>"
    html = util.urlResolver(html, {
      rowsize: rowsize,
      categoryId: categoryId,
      orderby: orderby,
      ordertype: ordertype,
      forward: forward,
      docIsIntroduced: docIsIntroduced
    })
    load({html: html, cssUrls: ["/kms/knowledge/mobile/style/view.css"]})
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    // 声明依赖--非mui和sys/mportal下的依赖都要在此处声明
    dependences: [
      "/kms/knowledge/mobile/mportal/js/SummaryListMixin.js",
      "/kms/knowledge/mobile/mportal/js/item/SummaryItemMixin.js"
    ]
  }
})
