/**
 * 可切换列表
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojox/mobile/View",
  "dojo/parser",
  "dojo/_base/lang",
  "mui/util",
  "dojo/request",
  "dojo/Deferred"
], function(
    declare,
    domConstruct,
    View,
    parser,
    lang,
    util,
    request,
    Deferred
) {
  return declare("mui.simplecategory.SwapCategoryList", [View], {
    // 栏数
    mixins: [
      "mui/category/ACategoryListMixin",
      "mui/category/BCategoryListMixin"
    ],

    index: 0,

    tmpl:
    // 滑动
        '<div data-dojo-type="dojox/mobile/ScrollableView" ' +
        'data-dojo-mixins="mui/category/AppBarsMixin"' +
        "data-dojo-props=\"scrollBar:false,threshold:100,key:'{key}'\">" +
        // 列表
        '<ul data-dojo-type="mui/simplecategory/SimpleCategoryList" ' +
        'data-dojo-mixins="mui/simplecategory/SimpleCategoryItemListMixin,!{mixin}" ' +
        "data-dojo-props=\"___urlParam:'{___urlParam}',lazy:false,isMul:{isMul},key:'{key}',parentId:'!{fdId}',selType:{selType},modelName:'{modelName}',authType:'{authType}',curIds:'{curIds}',confirm:{confirm},index:{index}\" >" +
        "</ul>" +
        "</div>",

    buildRendering: function() {
      this.inherited(arguments)
      this.containerNode = domConstruct.create(
          "div",
          {className: "muiSimpleCateContainer"},
          this.domNode
      )
      this.subscribe("/mui/category/changed", "_cateChange")
    },

    // 是否请求过权限
    authed: false,

    // 分类切换事件
    _cateChange: function(obj, evt) {
      if (obj.key == this.key) {
        this.authRequest(evt)
      }
    },

    authUrl: "/sys/category/mobile/sysSimpleCategory.do?method=authData",

    // 权限请求
    authRequest: function(evt) {
      var deferred = new Deferred()

      if (this.authed) {
        deferred.resolve()
      } else {
        this.authed = true
        request
            .post(util.formatUrl(this.authUrl), {
              handleAs: "json",
              data: {
                modelName: this.modelName,
                authType: this.authType
              }
            })
            .then(
                lang.hitch(this, function(evt) {
                  this.authCateIds = evt.authIds
                  deferred.resolve()
                })
            )
      }

      deferred.promise.then(
          lang.hitch(this, function() {
            this.generateList(evt)
          })
      )
    },

    generateList: function(evt) {
      if (this.index >= this.mixins.length) {
        return
      }

      evt.mixin = this.mixins[this.index]

      var tmpl = this.tmpl
      tmpl = util.urlResolver(tmpl, evt)
      tmpl = lang.replace(tmpl, this)

      this.index++

      var contentNode = domConstruct.create(
          "div",
          {innerHTML: tmpl, className: "muiSimpleCateContent"},
          this.containerNode
      )

      parser.parse(contentNode).then(function(widgets) {
        widgets[0].resize()
      })
    }
  })
})
