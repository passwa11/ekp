/**
 * 搜索联想
 */
define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/dom-style",
  "mui/util",
  "dojo/request",
  "dojo/_base/lang",
  "dojo/topic"
], function(
  declare,
  array,
  WidgetBase,
  domConstruct,
  domStyle,
  util,
  request,
  lang,
  topic
) {
  return declare("mui.search._SearchComplete", [WidgetBase], {
    url:
      "/sys/ftsearch/sysFtsearchAutoComplete.do?method=searchTip&q=!{keyword}",

    baseClass: "muiSearchComplete fadeIn animated",

    buildRendering: function() {
      this.inherited(arguments)

      this.containerNode = domConstruct.create(
        "ul",
        {className: "muiSearchCompleteContainer "},
        this.domNode
      )

      domConstruct.place(this.domNode, document.body, "last")
      this.show()

      this.subscribe("/mui/search/onChange", "_onChange")
      this.subscribe("/mui/search/submit", "_onSubmit")
    },

    _onSubmit: function() {
      this.hide()
    },

    show: function() {
      // 显示联想面板
      domStyle.set(this.domNode, {
        display: "block"
      })
      if(!this.keyword && this.parent){
    	  this.keyword = encodeURIComponent(this.parent.keyword)
      }
      request
        .get(
          util.formatUrl(util.urlResolver(this.url, {keyword: this.keyword})),
          {
            handleAs: "json"
          }
        )
        .response.then(
          lang.hitch(this, function(result) {
            var datas = result.data
            domConstruct.empty(this.containerNode)

            if (datas && datas.length > 0) {
              array.forEach(
                datas,
                function(item) {
                  var itemNode = domConstruct.create(
                    "li",
                    {innerHTML: item},
                    this.containerNode
                  )
                  this.connect(itemNode, "click", "_selectRecord")
                },
                this
              )
            }
          })
        )
    },

    // 进行搜索
    _selectRecord: function(evt) {
      var target = evt.target || evt.srcElement
      var record = target.innerHTML

      topic.publish("/mui/search/keyword", this, {
        keyword: record
      })
    },

    _onChange: function(srcObj) {
      if (srcObj != this.parent) {
        return
      }
      this.keyword = encodeURIComponent(srcObj.keyword)
      if (this.keyword) {
        this.show()
      } else {
        this.hide()
      }
    },

    hide: function() {
      domStyle.set(this.domNode, {
        display: "none"
      })
    }
  })
})
