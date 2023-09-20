/**
 * 可跟随页签切换的分类筛选组件
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojox/mobile/_ItemBase",
  "dojo/parser",
  "dojo/_base/array",
  "mui/util",
  "dojo/dom-style"
], function(declare, domConstruct, ItemBase, parser, array, util, domStyle) {
  return declare("mui.catefilter.NavFiler", [ItemBase], {
    templateString:
      '<div data-dojo-type="mui/catefilter/FilterItem" ' +
      'data-dojo-mixins="mui/simplecategory/SimpleCategoryMixin" ' +
      "data-dojo-props=\"modelName:'!{modelName}',title:false,hash:false,key:'cate_!{id}'\"></div>",

    baseClass: "muiNavFilter",

    postCreate: function() {
      this.inherited(arguments)
      this.subscribe("/dojox/mobile/viewChanged", "handleViewChanged")
      this.subscribe("/mui/nav/onComplete", "handleNavOnComplete")
    },

    handleNavOnComplete: function(widget) {
      this.generateList(widget.getChildren(), widget)
    },

    addChild: function(widget, index) {
      this.inherited(arguments)
      domStyle.set(widget.domNode, {display: "none"})
    },

    generateList: function(items) {
      var self = this,
        loadIndex = 0
      var template

      array.forEach(items, function(item) {
        template += util.urlResolver(self.templateString, item)
      })
      parser
        .parse(domConstruct.create("div", {innerHTML: template}))
        .then(function(widgetList) {
          array.forEach(widgetList, function(widget, index) {
            self.addChild(widget, index)
          })
          self.change(loadIndex)
        })
    },

    change: function(index) {
      this.index = index

      array.forEach(
        this.getChildren(),
        function(child, index) {
          if (this.index == index) {
            domStyle.set(child.domNode, {display: "block"})
          } else {
            domStyle.set(child.domNode, {display: "none"})
          }
        },
        this
      )
    },

    handleViewChanged: function(view) {
      this.change(view.rel.tabIndex)
    }
  })
})
