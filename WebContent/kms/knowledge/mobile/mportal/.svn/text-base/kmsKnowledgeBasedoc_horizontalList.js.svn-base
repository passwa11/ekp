define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var refreshtime = util.getUrlParameter(params, "refreshtime")
    var showSubject = util.getUrlParameter(params, "showSubject")
    var height = util.getUrlParameter(params, "height")
    var type = util.getUrlParameter(params, "type")
    var fdIds = util.getUrlParameter(params, "fdIds")
    var rowsize = util.getUrlParameter(params, "rowsize")
    var categoryId = util.getUrlParameter(params, "categoryId")
    var orderbyParam = util.getUrlParameter(params, "orderby");
    var orderby = "docPublishTime"
    var ordertype = "down"
	if("fdTotalCount" == orderbyParam){
    	orderby = "fdTotalCount"
    }
    var docIsIntroduced = "0"
    if ("2" == type) {
      docIsIntroduced = "1"
    }

    var html =
      '<div class="muiCardContainer">' +
      '<div class="muiDripAnnoun hasList">' +
      '<div data-dojo-type="mui/picslide/PicSlide" ' +
      'class="muiDripImgbox" ' +
      "data-dojo-props=\"picTensile:true,refreshTime:'!{refreshtime}',showType:'dot',showSubject:'!{showSubject}',url:'/sys/mportal/sys_mportal_imgsource/sysMportalImgSource.do?method=getImg&fdIds=!{fdIds}&orderby=docAlterTime&ordertype=down',height:'!{height}px',width:'100%'\" " +
      'data-dojo-mixins="sys/mportal/mobile/header/PicSlideMixin"></div>' +
      '<ul data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList" class="muiDripList" ' +
      'data-dojo-mixins="kms/knowledge/mobile/mportal/js/HorizonItemListMixin" ' +
      "data-dojo-props=\"url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&dataType=pic&orderby=!{orderby}&ordertype=!{ordertype}&rowsize=!{rowsize}&categoryId=!{categoryId}&q.docIsIntroduced=!{docIsIntroduced}&q.docStatus=30',lazy:false,pic:true\"></ul>" +
      "</div></div>"
    html = util.urlResolver(html, {
      refreshtime: refreshtime,
      showSubject: showSubject,
      height: height,
      fdIds: fdIds,
      rowsize: rowsize,
      categoryId: categoryId,
      orderby: orderby,
      ordertype: ordertype,
      docIsIntroduced: docIsIntroduced
    })
    load({
      html: html,
      cssUrls: ["/kms/knowledge/mobile/css/knowledge.css"]
    })
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    }
  }
})
