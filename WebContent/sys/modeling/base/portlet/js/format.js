define(function (require, exports, module) {
  //系统组件
  var $ = require("lui/jquery");
  var base = require("lui/base");
  var dialog = require('lui/dialog');
  //公式定义器
  // var viewProps_portlet = require("sys/modeling/base/portlet/js/viewProps_portlet");
  var portletCfgLink = require("sys/modeling/base/portlet/js/portletCfgLink");
  var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
  var modelingLang = require("lang!sys-modeling-base");
  function  getFormat (){
    var format =  {
      "sys.ui.classic": {
        "device": "pc",
        "name": modelingLang['portal.simple.list.presentation'],
        "id": "sys.ui.classic",
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "text","title": modelingLang['modeling.form.Content'],"type":"string"},
          {"key": "catename","title": modelingLang['portal.category.info'],"type":"string"},
          {"key": "created","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "creator","title": modelingLang['modelingAppListview.docCreator'],"type":"string"}
        ]
      },
      "sys.ui.image.desc": {
        "id": "sys.ui.image.desc",
        "type": "pc",
        "name": modelingLang['modelingPortletCfg.format.sys.ui.image.desc'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "text","title": modelingLang['portal.content'],"type":"string"},
          {"key": "catename","title": modelingLang['portal.category.info'],"type":"string"},
          {"key": "created","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "creator","title":modelingLang['modelingAppListview.docCreator'],"type":"string"},
          {"key": "image","title": modelingLang['portal.picture'],"type":"string"},
          {"key": "description","title": modelingLang['calendar.summary'],"type":"string"}
        ]
      },
      "sys.ui.slide": {
        "id": "sys.ui.slide",
        "type": "pc",
        "name": modelingLang['modelingPortletCfg.format.sys.ui.slide'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "text","title": modelingLang['portal.content'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "image","title": modelingLang['portal.picture'],"type":"string"}
        ]
      },
      "sys.ui.listtable": {
        "id": "sys.ui.listtable",
        "type": "pc",
        "name": modelingLang['table.modelingCollectionView'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "text","title": modelingLang['portal.content'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "docSubject","title": modelingLang['kmReviewMain.docSubject'],"type":"string"},
          {"key": "desc","title": modelingLang['modelingGanttMilepost.fdDescription'],"type":"string"},
          {"key": "docCreateTime","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"}

        ]
      },
      "sys.ui.image": {
        "id": "sys.ui.image",
        "type": "pc",
        "name": modelingLang['modelingPortletCfg.format.sys.ui.image'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "text","title": modelingLang['portal.picture.title'],"type":"string"},
          {"key": "href","title":  modelingLang['portal.jump.view'],"type":"view"},
          {"key": "image","title": modelingLang['portal.picture'],"type":"string"}
        ]
      },
      "sys.ui.card": {
        "id": "sys.ui.card",
        "type": "pc",
        "name": modelingLang['modelingPortletCfg.format.sys.ui.card'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "text","title": modelingLang['portal.picture.title'],"type":"string"},
          {"key": "created","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "creator","title": modelingLang['modelingAppListview.docCreator'],"type":"string"},
          {"key": "description","title": modelingLang['modelingGanttMilepost.fdDescription'],"type":"string"}
        ]
      },
      "modeling.mobile.textImg": {
        "id": "modeling.mobile.textImg",
        "type": "mobile",
        "name": modelingLang['modelingPortletCfg.format.sys.ui.image.desc'],

        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "label","title": modelingLang['portal.content'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "icon","title": modelingLang['portal.picture'],"type":"string"},
          {"key": "creator","title": modelingLang['modelingAppListview.docCreator'],"type":"string"},
          {"key": "created","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"}
        ]
      },
      "modeling.mobile.simple": {
        "id": "modeling.mobile.simple",
        "type": "mobile",
        "name": modelingLang['modelingPortletCfg.format.modeling.mobile.simple'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "label","title": modelingLang['portal.content'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "creator","title": modelingLang['modelingAppListview.docCreator'],"type":"string"},
          {"key": "created","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"}
        ]
      },
      "modeling.mobile.picSlide": {
        "id": "modeling.mobile.picSlide",
        "type": "mobile",
        "name": modelingLang['modelingPortletCfg.format.modeling.mobile.picSlidee'],

        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "image","title": modelingLang['portal.picture'],"type":"string"},
          {"key": "text","title": modelingLang['portal.content'],"type":"string"}
        ]
      },
      "modeling.mobile.cardList": {
        "id": "modeling.mobile.cardList",
        "type": "mobile",
        "name": modelingLang['modelingPortletCfg.format.modeling.mobile.cardList'],

        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "label","title": modelingLang['portal.content'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "icon","title": modelingLang['portal.picture'],"type":"string"},
          {"key": "creator","title": modelingLang['modelingAppListview.docCreator'],"type":"string"},
          {"key": "created","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"}
        ]
      },
      "data.list": {
        "device": "pc",
        "name": modelingLang['portal.simple.list.presentation'],
        "id": "data.list",
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "text","title": modelingLang['modeling.form.Content'],"type":"string"},
            {"key": "cateName","title": modelingLang['portal.category.info'],"type":"string"},
          {"key": "created","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "creator","title": modelingLang['modelingAppListview.docCreator'],"type":"string"}
        ]
      },
      "data.table": {
        "id": "data.table",
        "type": "pc",
        "name": modelingLang['table.modelingCollectionView'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "column1","title": modelingLang['portal.content'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
          {"key": "column2","title": modelingLang['kmReviewMain.docSubject'],"type":"string"},
          {"key": "column3","title": modelingLang['modelingGanttMilepost.fdDescription'],"type":"string"},
          {"key": "column4","title": modelingLang['modeling.modelMain.docCreateTime'],"type":"string"}

        ]
      },
      "data.person": {
        "id": "data.person",
        "type": "pc",
        "name": modelingLang['modelingPortletCfg.format.sys.ui.image.desc'],
        "varMapping": [
        ],
        "operationMapping": [
          {
            "key": "more",
            "name": modelingLang['portal.more.links'],
            "inputGenerator": "listview"
          }
        ],
        "dataMapping": [
          {"key": "image","title": modelingLang['portal.picture'],"type":"string"},
          {"key": "text1","title":modelingLang['modelingAppListview.docCreator'],"type":"string"},
          {"key": "text2","title": modelingLang['portal.content'],"type":"string"},
          {"key": "text3","title": modelingLang['portal.category.info'],"type":"string"},
          {"key": "text4","title": modelingLang['modelingAppSpace.fdDesc'],"type":"string"},
          {"key": "text5","title": modelingLang['calendar.summary'],"type":"string"},
          {"key": "href","title": modelingLang['portal.jump.view'],"type":"view"},
        ]
      },
    }
    return format;
  }
  exports.getFormat = getFormat;
});