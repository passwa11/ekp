/**
 * 红点提示插件
 */
define(["dojo/_base/declare", "dojo/dom-construct", "dojo/dom-style"], function(
  declare,
  domConstruct,
  domStyle
) {
  return declare("mui.nav.NavItemPromptMixin", null, {
    buildRendering: function() {
      this.inherited(arguments)
      this.subscribe("/mui/navitem/prompt", "onPrompt")
    },

    onPrompt: function(obj, evt) {
      if (obj.key != this.tabIndex) {
        return
      }

      if (evt.show) {
        if (this.doteNode) {
          domStyle.set(this.doteNode, "display", "block")
          return
        }

        this.doteNode = domConstruct.create(
          "i",
          {className: "muiNavItemDote"},
          this.domNode
        )
      } else {
        if (!this.doteNode) {
          return
        }
        domStyle.set(this.doteNode, "display", "none")
      }
    }
  })
})
