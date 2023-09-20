define(["mui/util"], function(util) {
  function portletLoad(params, load) {
	var listViewsId = util.getUrlParameter(params, "listViewsId");
	var viewId = util.getUrlParameter(params, "viewId");
    var appModelId = util.getUrlParameter(params, "appModelId");
    var rowsize = util.getUrlParameter(params, "rowsize");
    var isNewList = util.getUrlParameter(params, "isNewList");
      var fieldName = util.getUrlParameter(params, "fieldName");
      var fieldValue = util.getUrlParameter(params, "fieldValue");

    var html ='';
    if (isNewList ==="false"){
     html += '<ul class="muiPortalProcessSimple"'
   	 + 'data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/common/JsonStoreCardList"'
   	 + 'data-dojo-mixins="sys/modeling/main/resources/js/mobile/listView/DocItemListMixin"'
   	 + "data-dojo-props=\"url:'/sys/modeling/main/listview4m.do?method=data&listViewsId=!{listViewsId}&viewId=!{viewId}&fdAppModelId=!{appModelId}&rowsize=!{rowsize}&fieldName=!{fieldName}&fieldValue=!{fieldValue}',"
   	 + "listViewsId:'!{listViewsId}',viewId:'!{viewId}',lazy:false\">"
   	 + '</ul>';
    }else {
        html += '<ul class="muiPortalProcessSimple"'
            + 'data-dojo-type="sys/modeling/main/resources/js/mobile/homePage/common/JsonStoreCardList"'
            + 'data-dojo-mixins="sys/modeling/main/resources/js/mobile/listView/DocItemListMixin"'
            + "data-dojo-props=\"url:'/sys/modeling/main/mobile/modelingAppMobileCollectionView.do?method=data&listViewsId=!{listViewsId}&viewId=!{viewId}&fdAppModelId=!{appModelId}&rowsize=!{rowsize}&fieldName=!{fieldName}&fieldValue=!{fieldValue}',"
            + "listViewsId:'!{listViewsId}',viewId:'!{viewId}',lazy:false\">"
            + '</ul>';
    }
    html = util.urlResolver(html, {
    	listViewsId: listViewsId,
    	viewId: viewId,
    	appModelId: appModelId,
    	rowsize : rowsize,
        fieldName : fieldName,
        fieldValue : fieldValue
    });
    load({
        html: html,
        cssUrls: []
    }); 
    
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: []
  }
})