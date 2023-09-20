/**
 * 按钮
 */
define([
  "dojo/_base/declare",
  "dojox/mobile/_ItemBase",
  "dojo/dom-construct",
  "sys/mportal/mobile/OpenProxyMixin",
  "mui/iconUtils",
  "dojo/_base/array",
  "dojo/_base/lang",
  "dojo/dom-class",
  "dojo/dom-style",
  "mui/i18n/i18n!km-imeeting:kmImeeting"
], function(
  declare,
  _ItemBase,
  domConstruct,
  OpenProxyMixin,
  iconUtils,
  array,
  lang,
  domClass,
  domStyle,
  msg
) {
  return declare("", [_ItemBase, OpenProxyMixin], {
    buildRendering: function() {
      this.inherited(arguments)
      this.buildDialog()
    },

    buildDialog: function() {
      if (!this.dialogNode) {
        this.dialogNode = domConstruct.create(
          "div",
          {className: "muiModuleButtonDialog mui_ekp_meeting_guide_view"},
          document.body
        )
        this.buildCover()
       this.buildMenu()
        this.buildClose()
      }

      domStyle.set(this.dialogNode, "display", "block")
      this.defer(function() {
        domClass.add(this.dialogNode, "muiModuleButtonDialogShow")
      }, 1)
    },

    buildMenu: function() {
    	var MenuNode = domConstruct.create(
    	        "div",
    	        {className: "meeting_guide_content"},
    	        this.dialogNode
    	      );
    	domConstruct.create(
    	        "i",
    	        {className: "meeting_icon icon_list"},
    	        MenuNode
    	      );
    	domConstruct.create(
    	        "div",
    	        {className: "meeting_guide_txt",innerHTML:msg['kmImeeting.guide.tip']},
    	        MenuNode
    	      );
    },

    buildClose: function() {
      var closeNode = domConstruct.create(
        "div",
        {className: "muiModuleButtonClose"},
        this.dialogNode
      )
      var iconNode = iconUtils.createIcon(
        "muis-pop-close",
        null,
        null,
        null,
        closeNode
      )

      this.connect(iconNode, "click", "onClose")
    },

    onClose: function() {
      domClass.remove(this.dialogNode, "muiModuleButtonDialogShow")
      this.defer(function() {
        domStyle.set(this.dialogNode, "display", "none")
      }, 500)
    },

    buildCover: function() {
      domConstruct.create(
        "div",
        {className: "muiModuleButtonCover"},
        this.dialogNode
      )
    }
  })
})
