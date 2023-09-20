define(["dojo/_base/declare", "dojo/request", "mui/util","mui/device/adapter",], function(
  declare,
  request,
  util,
  adapter
) {
  return declare("mui.catefilter.TitleMixin", null, {
	  
    buildRendering: function() {
      this.inherited(arguments)
      if (this.title) {
        this.subscribe(this.CONFIRM, "onTitle")
      }
    },
    
    title: true,

    onTitle: function(obj, evt) {
      if (!evt) return

      if (evt.text) {
    	  setTimeout(function() {
    	        // adapter.setTitle(evt.text);
    	        return
		}, 100);
      }
      var value = evt.value
      if(!this.detailUrl)return;
      var url = util.urlResolver(this.detailUrl , {
        modelName: this.modelName,
        currentId: value.value
      })
      url = util.formatUrl(url)

      request
        .post(url, {
          headers: {
            Accept: "application/json"
          },
          handleAs: "json"
        })
        .then(function(result) {
          if (result.length > 0) {
            var title = result[0].label
            if (title) {
              //#165145 标题固定，不再随分类变动
              // adapter.setTitle(title);
            }
          }
        })
    }
  })
})
