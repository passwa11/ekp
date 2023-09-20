define([
  "dojo/_base/declare",
  "mui/dialog/Dialog",
  "dojo/dom-construct"
], function(declare, Dialog, domConstruct) {
  var claz = declare(
    "sys.attachment.mobile.js.AttachmentOptDialog",
    [Dialog.claz],
    {
      buildRendering: function() {
        this.domNode = domConstruct.create(
          "div",
          {
            className:
              "muiDialogElement" +
              (this.showClass != null ? " " + this.showClass : "")
          },
          document.body,
          "last"
        );

        this.containerNode = domConstruct.create(
          "div",
          {
            className: "muiAttDialogContainer"
          },
          this.domNode
        );

        domConstruct.place(this.element, this.containerNode);

        domConstruct.create(
          "div",
          { className: "muiAttDialogBottom", innerHTML: "取消" },
          this.containerNode
        );
        this.connect(this.domNode, "click", "_onClose");
      },
      _onClose: function(evt) {
        if (this.callback) this.callback(window, this);
        this.hide();
      }
    }
  );

  return {
    element: function(options) {
      var obj = new claz(options);
      return obj.show();
    }
  };
});
