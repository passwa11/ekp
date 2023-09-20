define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojox/mobile/_ItemBase",
  "mui/util",
  "sys/mportal/mobile/OpenProxyMixin"
], function(declare, domConstruct, domStyle, ItemBase, util, OpenProxyMixin) {
  var item = declare(
    "sys.mportal.item.GridSimpleItemMixin",
    [ItemBase, OpenProxyMixin],
    {
      label: "",

      fdImageUrl: "",

      docSubject: "",

      fdRefUrl: "",

      icon: "",

      href: "",

      buildRendering: function() {
        this.domNode = this.containerNode =
          this.srcNodeRef ||
          domConstruct.create("li", {
            className: this.baseClass
          })

        this.inherited(arguments)

        var aNode = domConstruct.create(
          "a",
          {
            href: "javascript:;"
          },
          this.domNode
        )

        if (!this.href) this.href = this.fdRefUrl

        this.proxyClick(aNode, this.href, "_blank", true)

        if (!this.icon) this.icon = this.fdImageUrl

        if (this.icon) {
          var imgNode = domConstruct.create(
            "div",
            {
              className: "muiDripImgbox"
            },
            aNode
          )

          this.icon = util.formatUrl(this.icon)

          domStyle.set(imgNode, {
            "background-image": "url(" + this.icon + ")"
          })
        }

        if (!this.label) this.label = this.docSubject

        domConstruct.create(
          "div",
          {
            className: "muiDripListTitle",
            innerHTML: this.label
          },
          aNode
        )
      },

      _setLabelAttr: function(text) {
        if (text) this._set("label", text)
      }
    }
  )
  return item
})
