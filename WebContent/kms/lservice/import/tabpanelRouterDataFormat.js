define(function(require, exports, module) {
  var fn = function(datas) {
    var rtn = []
    if (!datas || datas.length <= 0) {
      return rtn
    }
    for (var i = 0; i < datas.length; i++) {
    	if(!datas[i]){
    		continue;
    	}
      var id = datas[i].id,
        path = datas[i].path

      var route = {
        path: path,
        action: {
          type: 'tabpanel',
          options: {
            contents: {}
          }
        }
      }

      var tc = route.action.options.contents

      for (var j = 0; j < datas.length; j++) {
    	  if(!datas[j]){
      		continue;
      	}
        var panelRoute = {
          route: { path: datas[j].path }
        }
        if (datas[j].id == datas[i].id) {
          panelRoute.selected = true
          if(datas[j].cri){
            panelRoute.cri = datas[j].cri
          }
        }
        tc[datas[j].id] = panelRoute
      }

      rtn.push(route)
    }
    return rtn
  }

  module.exports = fn
})
