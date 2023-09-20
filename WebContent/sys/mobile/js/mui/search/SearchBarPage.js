define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/dom-attr",
  "dojo/dom",
  "mui/util"
], function(declare, WidgetBase, domConstruct, domAttr, dom, util) {
  return declare("mui.search.SearchBarPage", [WidgetBase], {
    icon: "mui mui-Csearch",

    label: "",

    modelName: "",

    searchUrl:
      "/sys/ftsearch/mobile/index.jsp?keyword=!{keyword}&modelName=!{modelName}",

    buildRendering: function() {
      this.inherited(arguments)
      this.buildSearchBar()
    },

    buildSearchBar: function() {
      var searchBar_inputWrap = domConstruct.create(
        "div",
        {
          className: "searchBar_inputWrap"
        },
        this.domNode
      )

      this.formNode = domConstruct.create(
        "form",
        {
          onsubmit: "return false;"
        },
        searchBar_inputWrap
      )

      domConstruct.create(
        "i",
        {
          className: this.icon
        },
        this.formNode
      )
      this.connect(this.formNode, "onsubmit", "_onSearch")
      this.buildSearchInput(this.formNode)
    },

    buildSearchInput: function() {
      this.inputNode = domConstruct.create(
        "input",
        {
          type: "search"
        },
        this.formNode
      )
      this.connect(this.inputNode, "onfocus", "_onfocus")
      this.connect(this.inputNode, "onclick", "_onfocus")
      this.connect(this.inputNode, "onblur", "_onblur")

      return this.inputNode
    },

    _onSearch: function(e) {
      var keyword = this.inputNode.value
      if (keyword && keyword != "") {
        var url = this.searchUrl
          .replace("!{keyword}", keyword)
          .replace("!{modelName}", this.modelName)

        url = util.formatUrl(url, "_self")
        location = url
      }
    },

    _onfocus: function(e) {
      var dom = e.target
      domAttr.set(dom, "placeholder", this.label)
    },

    _onblur: function(e) {
      var dom = e.target
      domAttr.set(dom, "placeholder", "")
    }
  })
})
