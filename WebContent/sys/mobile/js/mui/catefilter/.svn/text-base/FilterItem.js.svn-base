define([
  "dojo/_base/declare",
  "dojox/mobile/_ItemBase",
  "mui/form/_CategoryBase",
  "dojo/dom-construct",
  "dojo/dom-class",
  "mui/util",
  "mui/catefilter/ConstMixin",
  "mui/catefilter/TitleMixin",
  "mui/catefilter/HashMixin",
  "dojo/topic"
], function(
  declare,
  _ItemBase,
  _CategoryBase,
  domConstruct,
  domClass,
  util,
  ConstMixin,
  TitleMixin,
  HashMixin,
  topic
) {
  return declare(
    "mui.catefilter.filter.item",
    [_ItemBase, _CategoryBase, ConstMixin, TitleMixin, HashMixin],
    {
      baseClass: "muiHeaderItem muiHeaderItemIcon",

      modelName: null,
      curIds: "",

      key: "__default__",

      // 分类属性名;列表url拼接query时使用,形如q.docCategory=xxx
      catekey: "docCategory",

      showFavoriteCate: "false",

      authType: "03",

      confirm: true,

      postCreate: function() {
        /**
         * SysCategoryMixin的type优先级高<br>
         * 写在此处纯粹为了覆盖SysCategoryMixin里面的类型，兼容已经使用的代码
         */
        this.type = window.SYS_CATEGORY_TYPE_CATEGORY
        this.inherited(arguments)
        this.eventBind()
      },

      startup: function() {
        if (this.curIds) {
          topic.publish(this.CONFIRM, this, {
        	  	value: {
                  prefix: this.prefix,
                  value: this.curIds
            },
          })
        }
        this.inherited(arguments)
      },

      buildRendering: function() {
        this.inherited(arguments)
        this.connect(this.domNode, "click", "openFilter")
        window.SEL_CATEGORY_DEFAULT_TITLE = document.title
        this.labelNode = domConstruct.create(
          "div",
          {
            className: "muiHeaderItemLable",
            innerHTML: util.formatText(this.label)
          },
          this.domNode
        )

        domConstruct.create(
          "i",
          {
            className: "fontmuis muis-menu"
          },
          this.labelNode
        )
        domClass.toggle(this.domNode, "selected", this.curIds)
      },

      openFilter: function() {
          //#165145 打开分类窗口时，计数
          if (window.cateFilterSetSelc) {
              window.cateFilterSetSelc.openCount++;
          }
        this._selectCate()
      },

      returnDialog: function(srcObj, evt) {
        this.inherited(arguments)
        if (srcObj.key == this.key) {
          if (!evt) {
            return
          }
          this.submit(evt)
        }
      },

      submit: function(evt) {
        topic.publish(this.CONFIRM, this, {
          name: this.catekey,
          value: {
            prefix: this.prefix,
            value: evt.curIds || ""
          },
          text: evt.curNames
        })
        domClass.toggle(this.domNode, "selected", this.curIds)
        this.inherited(arguments)
      },

      _setLabelAttr: function(label) {}
    }
  )
})
