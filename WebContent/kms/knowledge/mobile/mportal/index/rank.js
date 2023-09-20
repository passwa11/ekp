define(["mui/util"], function(util) {
    function portletLoad(params, load) {
        var type = util.getUrlParameter(params, "type")
        var rowsize = util.getUrlParameter(params, "rowsize")
        var categoryId = util.getUrlParameter(params, "categoryId")
        var orderbyParam = util.getUrlParameter(params, "orderby");
        var orderby = "kmsKnowledgeBaseDoc.fdTotalCount"
        var ordertype = "down"
        if("fdTotalCount" == orderbyParam){
            orderby = "fdTotalCount"
        }else if("docPublishTime" == orderbyParam){
            orderby = "docPublishTime"
        }
        var docIsIntroduced = "0"
        if ("2" == type) {
            docIsIntroduced = "1"
        }

        var html =
            '<div data-dojo-type="sys/mportal/mobile/card/JsonStoreCardList" ' +
            'data-dojo-mixins="sys/mportal/mobile/card/ComplexLImgTextListMixin" ' +
            "data-dojo-props=\"url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&orderby=!{orderby}&ordertype=!{ordertype}&rowsize=!{rowsize}&dataType=pic&categoryId=!{categoryId}&q.docIsIntroduced=!{docIsIntroduced}&q.docStatus=30',lazy:false,pic:true\"></div>"
        html = util.urlResolver(html, {
            rowsize: rowsize,
            categoryId: categoryId,
            orderby: orderby,
            ordertype: ordertype,
            docIsIntroduced: docIsIntroduced
        })
        load(html)
    }

    return {
        load: function(params, require, load) {
            portletLoad(params, load)
        }
    }
})
