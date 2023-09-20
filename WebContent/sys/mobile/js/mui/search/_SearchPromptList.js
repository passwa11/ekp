/**
 * 搜索列表
 */
define([
  "dojo/_base/declare",
  "dojo/parser",
  "dojo/dom-construct",
  "mui/util",
  "dojo/dom-style",
  "dojo/_base/query",
  "dojo/topic"
], function(declare, parser, domConstruct, util, domStyle, query, topic) {
  return declare("mui.search._SearchPromptList", null, {
    postCreate: function() {
      this.inherited(arguments)
      this.subscribe("/mui/list/loaded", "_onLoaded")
    },

    _onLoaded: function(evt) {
      if (evt == this.jsonList) {
        var size = evt.totalSize
        query(".muiSearchTitle")[0].innerHTML = "搜索到约" + size + "项结果"
      }
    },
    _onCancel: function() {
      this.inherited(arguments)
      this.hideList()
    },

    hideList: function() {
      if (this.listNode) {
        domStyle.set(this.listNode, {
          display: "none"
        })
      }
    },

    showList: function() {
      if (this.listNode) {
        domStyle.set(this.listNode, {
          display: "block"
        })
      }
    },

    _onClear: function() {
      this.inherited(arguments)
      this.hideList()
    },

    // 提交时候渲染搜索列表
    _onSubmit: function(obj) {
      this.inherited(arguments)
      var keyword = obj.keyword
      var _searchFields = obj.searchFields || ''
  	  var _docStatus = obj.docStatus || ''
  	  var _modelName = this.modelName
      var self = this

      if (this.listNode) {
        this.showList()
        var url = self.jsonList.url
        self.jsonList.url = util.setUrlParameterMap(url, {
        	queryString: keyword,
        	modelName: _modelName,
        	searchFields: _searchFields,
        	docStatus: _docStatus
        })
        topic.publish("/mui/list/toTop", self.jsonList, {time: 0})
        self.jsonList.reload()
      } else { 
    	keyword = encodeURIComponent(obj.keyword)
        this.listNode = domConstruct.create(
          "div",
          {
            className: "muiSearchPromptList",
            innerHTML: util.urlResolver(this.templateStr(), {
              modelName: this.modelName,
              keyword: keyword,
              searchFields: _searchFields,
              docStatus:_docStatus
            })
          },
          this.domNode
        )
        parser
          .parse({
            rootNode: this.listNode,
            noStart: false
          })
          .then(function(widgets) {
            self.jsonList = widgets[1]
          })
      }
    },

    templateStr: function() {
      return (
        '<div data-dojo-type="mui/list/StoreScrollableView"> ' +
        '<div class="muiSearchTitle">' +
        "</div>" +
        '<ul data-dojo-type="mui/list/JsonStoreList" ' +
        'data-dojo-mixins="sys/ftsearch/mobile/list/SysFtsearchItemListMixin" ' +
        "data-dojo-props=\"lazy:false,url:'/sys/ftsearch/searchBuilder.do?method=search&queryString=!{keyword}&modelName=!{modelName}&newLUI=true&forward=mobileList&searchFields=!{searchFields}&docStatus=!{docStatus}'\">" +
        "</ul>" +
        "</div>"
      )
    }
  })
})
